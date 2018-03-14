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
public class Question {
    
    private int questionID;
    private String question;
    private double weight;
    private int questionType;
    private String questionTypeDesc;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestionTypeDesc() {
        return questionTypeDesc;
    }

    public void setQuestionTypeDesc(String questionTypeDesc) {
        this.questionTypeDesc = questionTypeDesc;
    }
    
    
    
}
