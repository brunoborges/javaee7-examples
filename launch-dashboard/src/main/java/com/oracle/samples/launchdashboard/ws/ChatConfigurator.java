/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.ws;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
public class ChatConfigurator extends ServerEndpointConfig.Configurator {

    private static final Logger LOGGER = Logger.getLogger(ChatConfigurator.class.getName());

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        String screenName = (String) httpSession.getAttribute("screenName");

        LOGGER.log(Level.INFO, "Found screenName? {0}", screenName);

        if (screenName != null) {
            sec.getUserProperties().put("screenName", screenName);
        } else {
            sec.getUserProperties().remove("screenName");
        }
    }
}
