/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ws;

import com.mycompany.Statistics;
import com.mycompany.encoders.StatisticsEncoder;
import com.mycompany.events.StatisticsEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Observes;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@ServerEndpoint(value = "/app/statistics", encoders = {StatisticsEncoder.class})
public class TwitterStatisticsWS {

    public void onStatistics(@Observes @StatisticsEvent Statistics newStatistics) {
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendObject(newStatistics);
            }
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void onMessage(String message, Session client) {
        try {
            client.getBasicRemote().sendText("ECHO: " + message);
        } catch (IOException ex) {
            Logger.getLogger(TwitterStatisticsWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    protected static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public final void onOpen(final Session session) {
        try {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "WEBSOCKET SESSION CONNECTED");
            sessions.add(session);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public final void onClose(final Session session) {
        try {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "WEBSOCKET SESSION DISCONNECTED");
            sessions.remove(session);
        } catch (Exception ex) {
            Logger.getLogger(WebSocketEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
