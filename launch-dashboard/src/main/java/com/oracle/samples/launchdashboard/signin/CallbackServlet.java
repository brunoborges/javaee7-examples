/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.signin;

import com.oracle.samples.launchdashboard.User;
import com.oracle.samples.launchdashboard.events.UserLoggedIn;
import java.io.IOException;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@WebServlet(name = "callback", urlPatterns = {"/callback"})
public class CallbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1657390011452788111L;
    @Inject
    @UserLoggedIn
    Event<User> userLoggedIn;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");

        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");

            String screenName = "@" + twitter.getScreenName();
            userLoggedIn.fire(new User(screenName));
            request.getSession().setAttribute("screenName", screenName);
        } catch (TwitterException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}
