/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.util.ArrayList;

/**
 *
 * @author Christopher Jorge
 */
public class Cluster {
    
    private int clusterID;
    private String clusterName;
    private int clusterSite;
    private String clusterSiteDesc;
    private ArrayList<ARB> clusterMembers = new ArrayList();

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getClusterSite() {
        return clusterSite;
    }

    public void setClusterSite(int clusterSite) {
        this.clusterSite = clusterSite;
    }

    public String getClusterSiteDesc() {
        return clusterSiteDesc;
    }

    public void setClusterSiteDesc(String clusterSiteDesc) {
        this.clusterSiteDesc = clusterSiteDesc;
    }
    
    public ArrayList<ARB> getClusterMembers() {
        return clusterMembers;
    }

    public void setClusterMembers(ArrayList<ARB> clusterMembers) {
        this.clusterMembers = clusterMembers;
    }
    
    
    
    
}
