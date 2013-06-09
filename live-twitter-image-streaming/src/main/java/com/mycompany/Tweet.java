package com.mycompany;

public class Tweet {
    private String name;
    private String text;
    private String url;

    public Tweet withName(String name) {
        this.name = name;
        return this;
    }

    public Tweet withText(String text) {
        this.text = text;
        return this;
    }

    public Tweet withUrl(String url) {
        this.url = url;
        return this;
    }
    
    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
