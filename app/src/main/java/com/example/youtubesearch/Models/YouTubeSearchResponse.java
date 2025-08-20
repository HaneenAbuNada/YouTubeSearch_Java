package com.example.youtubesearch.Models;

import java.util.List;

public class YouTubeSearchResponse {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        private Snippet snippet;

        public Snippet getSnippet() {
            return snippet;
        }
    }

    public static class Snippet {
        private String title;
        private String description;
        private String channelTitle;
        private String publishedAt;
        private Thumbnails thumbnails;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }
    }

    public static class Thumbnails {
        private Thumbnail medium;

        public Thumbnail getMedium() {
            return medium;
        }
    }

    public static class Thumbnail {
        private String url;

        public String getUrl() {
            return url;
        }
    }
}