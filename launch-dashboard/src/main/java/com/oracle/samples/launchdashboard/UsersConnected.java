/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard;

import com.oracle.samples.launchdashboard.events.UserLoggedIn;
import com.oracle.samples.launchdashboard.events.UserLoggedOut;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@ApplicationScoped
public class UsersConnected {

    private final Map<String, User> users = Collections.synchronizedMap(new HashMap<String, User>());
    private static final Logger LOGGER = Logger.getLogger("UsersConnected");

    public void addUser(@Observes @UserLoggedIn User u) {
        LOGGER.log(Level.INFO, "User {0} logged IN", u.getTwitterName());
        users.put(u.getTwitterName(), u);
        LOGGER.log(Level.INFO, "Users.Size: {0}", users.size());
    }

    public void removeUser(@Observes @UserLoggedOut String u) {
        Logger.getLogger("UsersConnected").log(Level.INFO, "User {0} logged OUT", u);
        users.remove(u);
        LOGGER.log(Level.INFO, "Users.Size: {0}", users.size());
    }

    public User findUser(String u) {
        return users.get(u);
    }
}
