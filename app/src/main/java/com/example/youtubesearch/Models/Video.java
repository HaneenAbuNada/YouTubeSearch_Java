package com.example.youtubesearch.Models;

public class Video {
    private final String title;
    private final String description;
    private final String channelTitle;
    private final String thumbnailUrl;
    private final String publishTime;

    public Video(String title, String description, String channelTitle, String thumbnailUrl, String publishTime) {
        this.title = title;
        this.description = description;
        this.channelTitle = channelTitle;
        this.thumbnailUrl = thumbnailUrl;
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }
}