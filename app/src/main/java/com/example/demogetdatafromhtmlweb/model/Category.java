package com.example.demogetdatafromhtmlweb.model;

import java.util.List;

public class Category {
    private String catName;
    private List<String> listCat;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<String> getListCat() {
        return listCat;
    }

    public void setListCat(List<String> listCat) {
        this.listCat = listCat;
    }

    public Category(String catName, List<String> listCat) {
        this.catName = catName;
        this.listCat = listCat;
    }
}
