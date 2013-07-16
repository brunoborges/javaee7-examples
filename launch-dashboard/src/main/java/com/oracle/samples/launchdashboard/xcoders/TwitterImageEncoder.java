/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.samples.launchdashboard.xcoders;

import com.oracle.samples.launchdashboard.twitterimage.TwitterImage;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
public class TwitterImageEncoder implements Encoder.Text<TwitterImage> {

    @Override
    public String encode(TwitterImage t) throws EncodeException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = Json.createGenerator(sw);
        jg.writeStartObject();
        jg.write("imageUrl", t.imageUrl);
        jg.write("tweetUrl", t.tweetUrl);
        jg.writeEnd();
        jg.flush();
        return sw.toString();
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
}
