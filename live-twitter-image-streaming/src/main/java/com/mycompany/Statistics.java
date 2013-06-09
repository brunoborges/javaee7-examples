/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author bruno borges
 */
public class Statistics {

    private Date startedOn = Calendar.getInstance().getTime();
    private long tweetCount = 0L;
    private long imageCount = 0L;
    private String keywords = "";

    void increaseTweetCount() {
        tweetCount++;
    }

    void increaseImageAndTweetCount() {
        increaseTweetCount();
        imageCount++;
    }

    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String arg) {
        this.keywords = arg;
    }

    public long getImageCount() {
        return imageCount;
    }

    public long getTweetCount() {
        return tweetCount;
    }

    public long getStartedOn() {
        return this.startedOn.getTime();
    }

    public void clear() {
        tweetCount = 0;
        imageCount = 0;
    }

}
