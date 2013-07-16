package com.oracle.samples.launchdashboard.twitterimage;

import twitter4j.MediaEntity;
import twitter4j.Status;

public class ImageExtractor {

    public TwitterImage process(Status status) {
        if (status.isPossiblySensitive()) {
            return null;
        }

        MediaEntity[] mediaEntities = status.getMediaEntities();

        if (mediaEntities != null && mediaEntities.length > 0) {
            MediaEntity mediaEntity = mediaEntities[0]; // only the first image

            TwitterImage ti = new TwitterImage();
            ti.imageUrl = mediaEntity.getMediaURL().toString();
            ti.tweetUrl = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
            return ti;
        }

        return null;
    }
}
