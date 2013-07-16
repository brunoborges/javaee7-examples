/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.ws;

import com.oracle.samples.launchdashboard.events.TwitterImageEvent;
import com.oracle.samples.launchdashboard.twitterimage.TwitterImage;
import com.oracle.samples.launchdashboard.twitterimage.TwitterImageProcessor;
import com.oracle.samples.launchdashboard.xcoders.TwitterImageEncoder;
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
@ServerEndpoint(value = "/app/images", encoders = {TwitterImageEncoder.class})
public class Images {

    private static final Logger LOGGER = Logger.getLogger(Images.class.getName());
    @Inject
    private TwitterImageProcessor tip;
    protected static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public final void onOpen(final Session session) {
        if (sessions.isEmpty() && tip.isRunning() == false) {
            tip.start();
        }

        try {
            sessions.add(session);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public final void onClose(final Session session) {
        try {
            sessions.remove(session);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onImageFound(@Observes @TwitterImageEvent TwitterImage ti) {
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendObject(ti);
            }
        } catch (EncodeException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
