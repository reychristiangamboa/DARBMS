/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Database;

/**
 *
 * @author Rey Christian
 */
import java.sql.Connection;

public abstract class DBConnectionFactory {
        String url = "jdbc:mysql://localhost:3306/dar-bms";
        String username = "root";
        String password = "pass";

   public static DBConnectionFactory getInstance(){
        return new DBConnectionFactoryImpl();
    }
    
    public abstract Connection getConnection();
}
