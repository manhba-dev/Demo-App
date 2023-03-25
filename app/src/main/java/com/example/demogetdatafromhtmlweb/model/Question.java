package com.example.demogetdatafromhtmlweb.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    int typeQues;
    String titleQues;
    ArrayList<ContentQuestion> listContentQues;
    ArrayList<ChoiceAnswer> listChoiceQues;
    ArrayList<Integer> listAnswer;

    public int getTypeQues() {
        return typeQues;
    }

    public void setTypeQues(int typeQues) {
        this.typeQues = typeQues;
    }

    public String getTitleQues() {
        return titleQues;
    }

    public void setTitleQues(String titleQues) {
        this.titleQues = titleQues;
    }

    public ArrayList<ContentQuestion> getListContentQues() {
        return listContentQues;
    }

    public void setListContentQues(ArrayList<ContentQuestion> listContentQues) {
        this.listContentQues = listContentQues;
    }

    public ArrayList<ChoiceAnswer> getListChoiceQues() {
        return listChoiceQues;
    }

    public void setListChoiceQues(ArrayList<ChoiceAnswer> listChoiceQues) {
        this.listChoiceQues = listChoiceQues;
    }

    public ArrayList<Integer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(ArrayList<Integer> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public Question(int type, String titleQues, ArrayList<ContentQuestion> listContentQues, ArrayList<ChoiceAnswer> listChoiceQues, ArrayList<Integer> listAnswer) {
        this.typeQues = type;
        this.titleQues = titleQues;
        this.listContentQues = listContentQues;
        this.listChoiceQues = listChoiceQues;
        this.listAnswer = listAnswer;
    }
}
