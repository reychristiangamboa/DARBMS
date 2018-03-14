/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Rey Christian
 */
public class Evaluation {
    
    private int evaluationID;
    private int arbID;
    private Date evaluationDate;
    private Date evaluationStartDate;
    private Date evaluationEndDate;
    private String evaluationDTN;
    private double rating;
    private int evaluationType;
    private int evaluatedBy;
    private String evaluationTypeDesc;
    private ArrayList<QuestionRating> questionRatings = new ArrayList();

    public int getEvaluationID() {
        return evaluationID;
    }

    public void setEvaluationID(int evaluationID) {
        this.evaluationID = evaluationID;
    }

    public int getArbID() {
        return arbID;
    }

    public void setArbID(int arbID) {
        this.arbID = arbID;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Date getEvaluationStartDate() {
        return evaluationStartDate;
    }

    public void setEvaluationStartDate(Date evaluationStartDate) {
        this.evaluationStartDate = evaluationStartDate;
    }

    public Date getEvaluationEndDate() {
        return evaluationEndDate;
    }

    public void setEvaluationEndDate(Date evaluationEndDate) {
        this.evaluationEndDate = evaluationEndDate;
    }

    public String getEvaluationDTN() {
        return evaluationDTN;
    }

    public void setEvaluationDTN(String evaluationDTN) {
        this.evaluationDTN = evaluationDTN;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double apcpRating) {
        this.rating = apcpRating;
    }

    public int getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(int evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getEvaluationTypeDesc() {
        return evaluationTypeDesc;
    }

    public void setEvaluationTypeDesc(String evaluationTypeDesc) {
        this.evaluationTypeDesc = evaluationTypeDesc;
    }

    public int getEvaluatedBy() {
        return evaluatedBy;
    }

    public void setEvaluatedBy(int evaluatedBy) {
        this.evaluatedBy = evaluatedBy;
    }

    public ArrayList<QuestionRating> getQuestionRatings() {
        return questionRatings;
    }

    public void setQuestionRatings(ArrayList<QuestionRating> questionRatings) {
        this.questionRatings = questionRatings;
    }
    
    
    
}
