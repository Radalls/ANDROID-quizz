package com.example.quizzapp.models;

import java.util.ArrayList;
import java.util.List;

public class Friend {
    private String id;
    private String email;
    private String password;
    private String name;
    private int score;
    private String avatar;
    private ArrayList<Friend> friends;

    public Friend() {
        this.friends = new ArrayList<Friend>();
    }

    public Friend(String name, int score, String avatar) {
        this.name = name;
        this.score = score;
        this.avatar = avatar;

        this.friends = new ArrayList<Friend>();
    }

    public Friend(String id, String email, String password, String name, int score, String avatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.score = score;
        this.avatar = avatar;

        this.friends = new ArrayList<Friend>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }
}
