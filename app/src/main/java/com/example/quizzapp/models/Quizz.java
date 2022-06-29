package com.example.quizzapp.models;

public class Quizz {
    private String question;
    private boolean answer;
    private String image;

    public Quizz() {

    }

    public Quizz(String question, boolean answer, String image) {
        this.question = question;
        this.answer = answer;
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public boolean getAnswer() {
        return answer;
    }

    public String getImage() { return image; }
}
