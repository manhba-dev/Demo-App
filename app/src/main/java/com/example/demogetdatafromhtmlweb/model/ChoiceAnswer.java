package com.example.demogetdatafromhtmlweb.model;

public class ChoiceAnswer {
    String choice;
    int idChoice;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getIdChoice() {
        return idChoice;
    }

    public void setIdChoice(int idChoice) {
        this.idChoice = idChoice;
    }

    public ChoiceAnswer(String choice, int idChoice) {
        this.choice = choice;
        this.idChoice = idChoice;
    }
}
