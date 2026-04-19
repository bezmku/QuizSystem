package com.beza.quizsystem;

import java.util.List;

public class QuizRequest {
    private int subjectId;
    private List<Question> questions;

    public QuizRequest(){}

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
