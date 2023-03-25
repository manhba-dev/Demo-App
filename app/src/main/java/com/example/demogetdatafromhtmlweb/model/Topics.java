package com.example.demogetdatafromhtmlweb.model;

public class Topics {
    String TopicName;
    String TopicImage;
    String UrlCat;

    public String getUrlCat() {
        return UrlCat;
    }

    public void setUrlCat(String urlCat) {
        UrlCat = urlCat;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getTopicImage() {
        return TopicImage;
    }

    public void setTopicImage(String topicImage) {
        TopicImage = topicImage;
    }

    public Topics(String topicName, String topicImage,String urlCat) {
        TopicName = topicName;
        TopicImage = topicImage;
        UrlCat = urlCat;
    }
}
