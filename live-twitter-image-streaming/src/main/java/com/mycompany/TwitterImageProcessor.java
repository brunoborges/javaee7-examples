/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import com.mycompany.events.TwitterImageEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author Bruno Borges <bruno.borges at oracle.com>
 */
@ApplicationScoped
public class TwitterImageProcessor {

    @Inject
    @TwitterImageEvent
    Event<Tweet> twitterImageEvent;
    @Inject
    private StatisticsProcessor statisticsProcessor;
    @Inject
    private ImageExtractor imageExtractor;
    private String hashtag = "#javaee7";
    private TwitterStream twitterStream;
    private boolean started = false;

    public boolean isRunning() {
        return started;
    }

    public void start() {
        if (started) {
            return;
        }

        statisticsProcessor.setKeywords(hashtag);

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Initializing Twitter Image Processor...");

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                // Do not accept sensitive (XXX) content
                if (status.isPossiblySensitive())
                    return;

                Tweet t = imageExtractor.process(status);
                statisticsProcessor.gotTweet(t != null);
                if (t != null) {
                    twitterImageEvent.fire(t);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void onScrubGeo(long l, long l1) {
            }

            @Override
            public void onStallWarning(StallWarning sw) {
            }
        };

        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Starting Twitter Image Processor...");
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(new String[]{hashtag});
        twitterStream.filter(filterQuery);

        started = true;
    }

    @PreDestroy
    public void stop() {
        if (started == false) {
            return;
        }

        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Stopping Twitter Image Processor...");
        twitterStream.shutdown();
        started = false;
    }

    public void changeHashtag(String hashtag) {
        stop();
        this.hashtag = hashtag;
        start();
    }

    public void setOAuthTokens(OAuthTokens oauth) {
    }
}
