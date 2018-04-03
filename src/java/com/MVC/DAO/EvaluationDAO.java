/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.Evaluation;
import com.MVC.Model.Question;
import com.MVC.Model.QuestionRating;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class EvaluationDAO {

    public boolean addQuestion(Question q) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`questions` (`question`, `weight`, `questionType`) VALUES (?, ?, ?);";

            p = con.prepareStatement(query);
            p.setString(1, q.getQuestion());
            p.setDouble(2, q.getWeight());
            p.setInt(3, q.getQuestionType());

            p.executeUpdate();
            p.close();

            con.commit();
            con.close();
            success = true;

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public boolean editQuestion(Question q) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`questions` SET `question`=?, `weight`=? WHERE `questionID`=?";

            p = con.prepareStatement(query);
            p.setString(1, q.getQuestion());
            p.setDouble(2, q.getWeight());
            p.setInt(3, q.getQuestionID());

            p.executeUpdate();
            p.close();

            con.commit();
            con.close();
            success = true;

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public Question getQuestionByID(int questionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        Question q = new Question();
        try {
            String query = "SELECT * FROM `questions` q "
                    + "JOIN `ref_questionType` t ON q.questionType=t.questionType "
                    + "WHERE q.questionID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, questionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                q.setQuestionID(rs.getInt("questionID"));
                q.setQuestion(rs.getString("question"));
                q.setWeight(rs.getDouble("weight"));
                q.setQuestionType(rs.getInt("questionType"));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    public ArrayList<Question> getAllQuestionsByType(int type) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Question> questions = new ArrayList();
        try {
            String query = "SELECT * FROM `questions` q "
                    + "JOIN `ref_questionType` t ON q.questionType=t.questionType "
                    + "WHERE t.questionType=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionID(rs.getInt("questionID"));
                q.setQuestion(rs.getString("question"));
                q.setWeight(rs.getDouble("weight"));
                q.setQuestionType(rs.getInt("questionType"));
                questions.add(q);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    public int addEvaluation(Evaluation e) {
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`evaluations` (`arbID`, `evaluationDate`, `evaluationStartDate`, "
                    + "`evaluationEndDate`, `evaluationDTN`, `evaluationType`) "
                    + "VALUES (?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, e.getArbID());
            p.setDate(2, e.getEvaluationDate());
            p.setDate(3, e.getEvaluationStartDate());
            p.setDate(4, e.getEvaluationEndDate());
            p.setString(5, e.getEvaluationDTN());
            p.setInt(6, e.getEvaluationType());

            p.executeUpdate();
            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean setEvaluationRating(double rating, int id, int userID) {
        PreparedStatement p = null;
        Connection con = null;
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `evaluations` SET rating=?, evaluatedBy=? WHERE evaluationID=?";
            p = con.prepareStatement(query);
            p.setDouble(1, rating);
            p.setInt(2, userID);
            p.setInt(3, id);
            p.executeUpdate();
            success = true;

            p.close();
            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public Evaluation getEvaluationByID(int id) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        Evaluation e = new Evaluation();
        try {
            String query = "SELECT * FROM `evaluations` e "
                    + "JOIN `ref_questionType` t ON e.evaluationType=t.questionType "
                    + "WHERE e.evaluationID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                e.setEvaluationID(rs.getInt("evaluationID"));
                e.setArbID(rs.getInt("arbID"));
                e.setEvaluationDate(rs.getDate("evaluationDate"));
                e.setEvaluationStartDate(rs.getDate("evaluationStartDate"));
                e.setEvaluationEndDate(rs.getDate("evaluationEndDate"));
                e.setEvaluationDTN(rs.getString("evaluationDTN"));
                e.setRating(rs.getDouble("rating"));
                e.setEvaluatedBy(rs.getInt("evaluatedBy"));
                e.setEvaluationType(rs.getInt("evaluationType"));
                e.setEvaluationTypeDesc(rs.getString("questionTypeDesc"));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public ArrayList<Evaluation> getEvaluationPerARBID(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Evaluation> evaluations = new ArrayList();
        try {
            String query = "SELECT * FROM `evaluations` e "
                    + "JOIN `ref_questionType` t ON e.evaluationType=t.questionType "
                    + "WHERE e.arbID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Evaluation e = new Evaluation();
                e.setEvaluationID(rs.getInt("evaluationID"));
                e.setArbID(rs.getInt("arbID"));
                e.setEvaluationDate(rs.getDate("evaluationDate"));
                e.setEvaluationStartDate(rs.getDate("evaluationStartDate"));
                e.setEvaluationEndDate(rs.getDate("evaluationEndDate"));
                e.setEvaluationDTN(rs.getString("evaluationDTN"));
                e.setRating(rs.getDouble("rating"));
                e.setEvaluatedBy(rs.getInt("evaluatedBy"));
                e.setEvaluationType(rs.getInt("evaluationType"));
                e.setEvaluationTypeDesc(rs.getString("questionTypeDesc"));
                evaluations.add(e);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evaluations;
    }

    public ArrayList<Evaluation> getEvaluationPerARBIDByType(int arbID, int type) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Evaluation> evaluations = new ArrayList();
        try {
            String query = "SELECT * FROM `evaluations` e "
                    + "JOIN `ref_questionType` t ON e.evaluationType=t.questionType "
                    + "WHERE e.arbID=? AND t.questionType=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            pstmt.setInt(2, type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Evaluation e = new Evaluation();
                e.setEvaluationID(rs.getInt("evaluationID"));
                e.setArbID(rs.getInt("arbID"));
                e.setEvaluationDate(rs.getDate("evaluationDate"));
                e.setEvaluationStartDate(rs.getDate("evaluationStartDate"));
                e.setEvaluationEndDate(rs.getDate("evaluationEndDate"));
                e.setEvaluationDTN(rs.getString("evaluationDTN"));
                e.setRating(rs.getDouble("rating"));
                e.setEvaluatedBy(rs.getInt("evaluatedBy"));
                e.setEvaluationType(rs.getInt("evaluationType"));
                e.setEvaluationTypeDesc(rs.getString("questionTypeDesc"));
                e.setQuestionRatings(getQuestionRatingsByEvaluationID(rs.getInt("evaluationID")));
                evaluations.add(e);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return evaluations;
    }
    
    

    public boolean addQuestionRating(QuestionRating r) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`questionratings` "
                    + "(`evaluationID`, `questionID`, `rating`) "
                    + "VALUES (?, ?, ?);";

            p = con.prepareStatement(query);
            p.setInt(1, r.getEvaluationID());
            p.setInt(2, r.getQuestionID());
            p.setDouble(3, r.getRating());

            p.executeUpdate();
            p.close();

            con.commit();
            con.close();
            success = true;

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<QuestionRating> getQuestionRatingsByEvaluationID(int evaluationID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<QuestionRating> qrs = new ArrayList();
        try {
            String query = "SELECT * FROM `questionRatings` r "
                    + "JOIN `questions` q ON r.questionID=q.questionID "
                    + "WHERE `evaluationID`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, evaluationID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuestionRating r = new QuestionRating();
                r.setEvaluationID(rs.getInt("evaluationID"));
                r.setQuestionID(rs.getInt("questionID"));
                r.setRating(rs.getInt("rating"));
                r.setQuestion(getQuestionByID(rs.getInt("questionID")));
                qrs.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qrs;
    }
    
    public boolean addLINKSFARMEvaluation(Evaluation e){
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`evaluations` (`arbID`, `evaluationDate`, `evaluationStartDate`, "
                    + "`evaluationEndDate`, `evaluationDTN`, `evaluationType`,`rating`, `evaluatedBy`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, e.getArbID());
            p.setDate(2, e.getEvaluationDate());
            p.setDate(3, e.getEvaluationStartDate());
            p.setDate(4, e.getEvaluationEndDate());
            p.setString(5, e.getEvaluationDTN());
            p.setInt(6, e.getEvaluationType());
            p.setDouble(7, e.getRating());
            p.setInt(8, e.getEvaluatedBy());

            p.executeUpdate();
            
            p.close();
            con.commit();
            con.close();
            success = true;

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(EvaluationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
}
