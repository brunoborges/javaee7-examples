/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.twitterimage;

import com.oracle.samples.launchdashboard.xcoders.TwitterImageEncoder;
import javax.websocket.EncodeException;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
public class TwitterImage {

    public String tweetUrl;
    public String imageUrl;

    public String toJsonString() {
        try {
            return new TwitterImageEncoder().encode(this);
        } catch (EncodeException ex) {
            throw new RuntimeException(ex);
        }
    }
}
