package com.example.demogetdatafromhtmlweb.model;

public class Vocabulary {
    String content;
    String images;
    String describe;
    String pronounce;
    String examples;

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    String speaker;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public Vocabulary(String content, String images, String pronounce,String speaker) {
        this.content = content;
        this.images = images;
        this.pronounce = pronounce;
        this.speaker = speaker;
    }
}
