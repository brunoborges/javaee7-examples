package com.mycompany;

import com.mycompany.events.StatisticsEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class StatisticsProcessor {

    @Inject
    @StatisticsEvent
    Event<Statistics> statisticsEvent;
    private Statistics statistics = new Statistics();

    public void gotTweet(boolean withImage) {
        if (withImage) {
            statistics.increaseImageAndTweetCount();
        } else {
            statistics.increaseTweetCount();
        }

        statisticsEvent.fire(statistics);
    }

    void setKeywords(String keywords) {
        statistics.setKeywords(keywords);
    }
}
