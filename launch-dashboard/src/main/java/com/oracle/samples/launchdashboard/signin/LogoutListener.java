/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.signin;

import com.oracle.samples.launchdashboard.events.UserLoggedOut;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@WebListener
public class LogoutListener implements HttpSessionListener {

    @Inject
    @UserLoggedOut
    Event<String> userLoggedOut;

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        String screenName = (String) hse.getSession().getAttribute("screenName");
        if (screenName != null && userLoggedOut != null) {
            userLoggedOut.fire(screenName);
        }
    }
}
