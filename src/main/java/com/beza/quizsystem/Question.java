package com.beza.quizsystem;

public class Question {
    private int id;
    private String text;
    private String optA,optB,optC, optD;
    private String correctAnswer;
    private String subjectName;

    public Question(){}

    public Question(int id, String text, String optA, String optB, String optC, String optD, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.correctAnswer = correctAnswer;
    }

    public Question(int id, String text, String optA, String optB, String optC, String optD, String correctAnswer, String subjectName) {
        this.id = id;
        this.text = text;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.correctAnswer = correctAnswer;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getOptA() {
        return optA;
    }

    public String getOptB() {
        return optB;
    }

    public String getOptC() {
        return optC;
    }

    public String getOptD() {
        return optD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSubjectName() {
        return subjectName;
    }


}
