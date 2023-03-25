package com.example.demogetdatafromhtmlweb.model;

public class ConversationTopic {
    String linkTopic;
    String contentTopic;

    public String getLinkTopic() {
        return linkTopic;
    }

    public void setLinkTopic(String linkTopic) {
        this.linkTopic = linkTopic;
    }

    public String getContentTopic() {
        return contentTopic;
    }

    public void setContentTopic(String contentTopic) {
        this.contentTopic = contentTopic;
    }

    public ConversationTopic(String linkTopic, String contentTopic) {
        this.linkTopic = linkTopic;
        this.contentTopic = contentTopic;
    }
}
