package com.example.gameapplication3;

public class Score {
    String id, rank, name, score;

    public Score(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public Score(String rank, String name, String score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
