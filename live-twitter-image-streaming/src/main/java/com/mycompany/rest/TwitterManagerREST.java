/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rest;

import com.mycompany.TwitterImageProcessor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@Path("/tm")
public class TwitterManagerREST {

    @Inject
    private TwitterImageProcessor tip;

    @GET
    @Path("search/{keyword}")
    public void search(@PathParam("keyword") String keyword) {
        System.out.println("SEARCH: " + keyword);
        tip.changeHashtag(keyword);
    }

    @GET
    @Path("start")
    public void start() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Starting Twitter Processing...");
        tip.start();
    }

    @GET
    @Path("stop")
    public void stop() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Stopping Twitter Processing...");
        tip.stop();
    }
}
