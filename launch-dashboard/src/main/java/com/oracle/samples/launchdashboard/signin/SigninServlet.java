/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.signin;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@WebServlet(name = "signin", urlPatterns = {"/signin"})
public class SigninServlet extends HttpServlet {

    private static final long serialVersionUID = -6205814293093350242L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Twitter twitter = new TwitterFactory().getInstance();
        request.getSession().setAttribute("twitter", twitter);
        try {
            StringBuffer callbackURL = request.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/callback");

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Callback URL: " + callbackURL);

            RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());

            request.getSession().setAttribute("requestToken", requestToken);
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch (TwitterException e) {
            throw new ServletException(e);
        }

    }
}
