/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ws;

import com.mycompany.events.HashTagChangeEvent;
import com.mycompany.Tweet;
import com.mycompany.TwitterImageProcessor;
import com.mycompany.encoders.TweetEncoder;
import com.mycompany.events.TwitterImageEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@ServerEndpoint(value = "/app/images", encoders = {TweetEncoder.class})
public class TwitterImagesWS {

    @Inject
    private TwitterImageProcessor tip;
    protected static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public final void onOpen(final Session session) {
        if (sessions.isEmpty() && tip.isRunning() == false) {
            tip.start();
        }

        Logger.getLogger(TwitterImagesWS.class.getName()).log(Level.INFO, "IMAGE WS CONNECTED");
        try {
            sessions.add(session);
        } catch (Exception ex) {
            Logger.getLogger(WebSocketEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public final void onClose(final Session session) {
        Logger.getLogger(TwitterImagesWS.class.getName()).log(Level.INFO, "IMAGE WS DISCONNECTED");
        try {
            sessions.remove(session);
        } catch (Exception ex) {
            Logger.getLogger(WebSocketEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onTwitterImage(@Observes @TwitterImageEvent Tweet tweet) {
        Logger.getLogger(TwitterImagesWS.class.getName()).log(Level.INFO, "Got a Tweet! " + tweet.getUrl());
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendObject(tweet);
            }
        } catch (EncodeException | IOException ex) {
            Logger.getLogger(TwitterImagesWS.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void onHashTagChange(@Observes @HashTagChangeEvent String hashtag) {
        Logger.getLogger(TwitterImagesWS.class.getName()).log(Level.INFO, "Got a REST message for WebSockets!");
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendText("hashtag " + hashtag);
            }
        } catch (IOException ex) {
            Logger.getLogger(WebSocketEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
