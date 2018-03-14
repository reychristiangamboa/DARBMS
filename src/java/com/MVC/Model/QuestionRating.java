/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

/**
 *
 * @author Rey Christian
 */
public class QuestionRating {
    
    private int questionRatingID;
    private int evaluationID;
    private int questionID;
    private double rating;
    private Question question;

    public int getQuestionRatingID() {
        return questionRatingID;
    }

    public void setQuestionRatingID(int questionRatingID) {
        this.questionRatingID = questionRatingID;
    }

    public int getEvaluationID() {
        return evaluationID;
    }

    public void setEvaluationID(int evaluationID) {
        this.evaluationID = evaluationID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
    
    
    
    
}
