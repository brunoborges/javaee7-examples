/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.io.StringReader;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
public class OAuthTokens {

    public String consumerKey;
    public String consumerSecret;
    public String accessToken;
    public String accessTokenSecret;

    @Override
    public boolean equals(Object o) {
        if (o instanceof OAuthTokens == false) {
            return false;
        }

        OAuthTokens other = (OAuthTokens) o;
        if ((consumerKey != null && consumerKey.equals(other.consumerKey))
                && (consumerSecret != null && consumerSecret.equals(other.consumerSecret))
                && (accessToken != null && accessToken.equals(other.accessToken))
                && (accessTokenSecret != null && accessTokenSecret.equals(other.accessTokenSecret))) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (consumerKey != null ? consumerKey.hashCode() : 1) * 3
                + (consumerSecret != null ? consumerSecret.hashCode() : 1) * 7
                + (accessToken != null ? accessToken.hashCode() : 1) * 11
                + (accessTokenSecret != null ? accessTokenSecret.hashCode() : 1) * 17;
    }

    public static OAuthTokens fromJson(String json) {
        JsonParser jp = Json.createParser(new StringReader(json));
        jp.next();
        OAuthTokens oat = new OAuthTokens();
        oat.consumerKey = jp.getString();
        oat.consumerSecret = jp.getString();
        oat.accessToken = jp.getString();
        oat.accessTokenSecret = jp.getString();
        return oat;
    }

    public static String toJson(OAuthTokens oat) {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = Json.createGenerator(sw);
        jg.writeStartObject();
        jg.write("consumerKey", oat.consumerKey);
        jg.write("consumerSecret", oat.consumerSecret);
        jg.write("accessToken", oat.accessToken);
        jg.write("accessTokenSecret", oat.accessTokenSecret);
        jg.writeEnd();
        jg.flush();
        return sw.toString();
    }
}
