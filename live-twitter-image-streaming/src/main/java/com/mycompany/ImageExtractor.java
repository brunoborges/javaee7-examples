package com.mycompany;

import twitter4j.MediaEntity;
import twitter4j.Status;

public class ImageExtractor {

    public Tweet process(Status status) {
        MediaEntity[] mediaEntities = status.getMediaEntities();

        if (mediaEntities != null && mediaEntities.length > 0) {
            MediaEntity mediaEntity = mediaEntities[0]; // only the first image

            return new Tweet()
                    .withName(status.getUser().getScreenName())
                    .withText(status.getText())
                    .withUrl(mediaEntity.getMediaURL().toString());
        }

        return null;
    }
}
