package com.example.demogetdatafromhtmlweb.model;

import java.util.ArrayList;

public class QuestionVocab {
    int type;
    ArrayList<String> listImage;
    ArrayList<String> listMp3;
    ArrayList<String> listAns;

    public QuestionVocab(int type, ArrayList<String> listImage, ArrayList<String> listMp3, ArrayList<String> listAns) {
        this.type = type;
        this.listImage = listImage;
        this.listMp3 = listMp3;
        this.listAns = listAns;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<String> getListImage() {
        return listImage;
    }

    public void setListImage(ArrayList<String> listImage) {
        this.listImage = listImage;
    }

    public ArrayList<String> getListMp3() {
        return listMp3;
    }

    public void setListMp3(ArrayList<String> listMp3) {
        this.listMp3 = listMp3;
    }

    public ArrayList<String> getListAns() {
        return listAns;
    }

    public void setListAns(ArrayList<String> listAns) {
        this.listAns = listAns;
    }
}
