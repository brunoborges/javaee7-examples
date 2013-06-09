/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.encoders;

import com.mycompany.Statistics;
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
public class StatisticsEncoder implements Encoder.Text<Statistics> {

    @Override
    public String encode(Statistics t) throws EncodeException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = Json.createGenerator(sw);
        jg.writeStartObject();
        jg.write("imageCount", t.getImageCount());
        jg.write("keywords", t.getKeywords());
        jg.write("startedOn", t.getStartedOn());
        jg.write("tweetCount", t.getTweetCount());
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
