package com.beza.quizsystem;

public class QuizResult {
    private String username;
    private String subjectName;
    private String date;
    private int total;
    private int score;

    public QuizResult(){}
    public QuizResult(String subjectName, String date, int total, int score){
        this.subjectName = subjectName;
        this.date = date;
        this.total = total;
        this.score = score;
    }

    public QuizResult(String username, String subjectName, String date, int total, int score){
        this.username = username;
        this.subjectName = subjectName;
        this.date = date;
        this.total = total;
        this.score = score;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
