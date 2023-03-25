package com.example.demogetdatafromhtmlweb.model;

import java.util.ArrayList;

public class Conversation {
    int typeConverSenten;
    String listMP3;
    String listSentence;

    public int getTypeConverSenten() {
        return typeConverSenten;
    }

    public void setTypeConverSenten(int typeConverSenten) {
        this.typeConverSenten = typeConverSenten;
    }

    public String getListMP3() {
        return listMP3;
    }

    public void setListMP3(String listMP3) {
        this.listMP3 = listMP3;
    }

    public String getListSentence() {
        return listSentence;
    }

    public void setListSentence(String listSentence) {
        this.listSentence = listSentence;
    }

    public Conversation(int typeConverSenten, String listMP3, String listSentence) {
        this.typeConverSenten = typeConverSenten;
        this.listMP3 = listMP3;
        this.listSentence = listSentence;
    }

}
