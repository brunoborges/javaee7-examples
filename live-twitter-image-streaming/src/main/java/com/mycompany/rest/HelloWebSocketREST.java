/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rest;

import com.mycompany.events.RestToWSEvent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@Path("/hello")
public class HelloWebSocketREST {

    @Inject
    @RestToWSEvent
    Event<String> event;

    @GET
    public void send(@QueryParam("message") String message) {
        System.out.println("Mensagem recebida: " + message);
        event.fire(message);
    }
}
