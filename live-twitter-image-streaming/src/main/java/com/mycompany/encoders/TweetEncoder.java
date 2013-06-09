/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.encoders;

import com.mycompany.Tweet;
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
public class TweetEncoder implements Encoder.Text<Tweet> {

    @Override
    public String encode(Tweet t) throws EncodeException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = Json.createGenerator(sw);
        jg.writeStartObject();
        jg.write("name", t.getName());
        jg.write("text", t.getText());
        jg.write("url", t.getUrl());
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
