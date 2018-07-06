/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;   
import java.sql.Time;

/**
 *
 * @author Rey Christian
 */
public class Message {
    private int messageID;
    private String body;
    private Date dateSent;
    private Time timeSent;
    private int sentBy;
    private int sentTo;
    private boolean isRead;
    
    
    public Message() {
    
    }

    public int getSentTo() {
        return sentTo;
    }

    public void setSentTo(int sentTo) {
        this.sentTo = sentTo;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Time getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Time timeSent) {
        this.timeSent = timeSent;
    }

    public int getSentBy() {
        return sentBy;
    }

    public void setSentBy(int sentBy) {
        this.sentBy = sentBy;
    }
    
    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

}
