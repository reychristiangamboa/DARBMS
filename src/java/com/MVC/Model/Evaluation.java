/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;

/**
 *
 * @author Rey Christian
 */
public class Evaluation {
    
    private int evaluationID;
    private int arbID;
    private Date evalutaionDate;
    private Date evaluationStartDate;
    private Date evaluationEndDate;
    private String evaluationDTN;
    private double apcpRating;
    private double capdevRating;
    private double arbRating;

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

    public Date getEvalutaionDate() {
        return evalutaionDate;
    }

    public void setEvalutaionDate(Date evalutaionDate) {
        this.evalutaionDate = evalutaionDate;
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

    public double getApcpRating() {
        return apcpRating;
    }

    public void setApcpRating(double apcpRating) {
        this.apcpRating = apcpRating;
    }

    public double getCapdevRating() {
        return capdevRating;
    }

    public void setCapdevRating(double capdevRating) {
        this.capdevRating = capdevRating;
    }

    public double getArbRating() {
        return arbRating;
    }

    public void setArbRating(double arbRating) {
        this.arbRating = arbRating;
    }
    
}
