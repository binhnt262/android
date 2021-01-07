package com.example.myapplication;

public class User {
    private Integer id;
    private int score;

    public User(Integer id, int score) {
        this.id = id;
        this.score = score;
    }

    public User( int score) {
        this.score = score;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
