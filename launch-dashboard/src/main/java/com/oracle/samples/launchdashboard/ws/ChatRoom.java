/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@ServerEndpoint(value = "/chatroom", configurator = ChatConfigurator.class)
public class ChatRoom {

    private static final Logger LOGGER = Logger.getLogger(ChatRoom.class.getName());
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String message, Session c) {
        if (c.getUserProperties().containsKey("screenName") == false) {
            return;
        }

        String screenName = c.getUserProperties().get("screenName").toString();
        LOGGER.log(Level.INFO, "User {0} sent a message: {1}", new Object[]{screenName, message});
        notifyAllUsers("chat " + screenName + "> " + message);
    }

    private void notifyOfNewUser(String screenName) {
        notifyAllUsers("add " + screenName);
    }

    @OnOpen
    public void onOpen(Session client, EndpointConfig config) {
        if (config.getUserProperties().containsKey("screenName")) {
            String screenName = (String) config.getUserProperties().get("screenName");
            LOGGER.log(Level.INFO, "User connected: {0}", screenName);
            client.getUserProperties().put("screenName", screenName);
            sessions.add(client);
            try {
                client.getBasicRemote().sendText("loggedin");

                for (Session _s : sessions) {
                    notifyOfNewUser(_s.getUserProperties().get("screenName").toString());
                }

            } catch (IOException ex) {
                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //try {
            // do not open this session until user connects on Twitter
            LOGGER.log(Level.WARNING, "User is not connected on Twitter");
            //s.close(new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE, ""));
            //} catch (IOException ex) {
            //  LOGGER.log(Level.SEVERE, null, ex);
            // }
        }
    }

    @OnClose
    public void onClose(Session s) {
        Object screenName = s.getUserProperties().get("screenName");
        if (screenName == null) {
            screenName = "<not_logged>";
        } else {
            notifyUserHasGone(screenName.toString());
        }
        LOGGER.log(Level.INFO, "User disconnected: {0}", screenName);
        sessions.remove(s);
    }

    private void notifyUserHasGone(String screenName) {
        notifyAllUsers("del screenName");
    }

    private void notifyAllUsers(String message) {
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
