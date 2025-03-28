package com.kelasandroidappsirhaafizee.loginregistration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:jtds:sqlserver://192.168.63.123:1433/loginRegisterDB";
    private static final String USER = "loginAmanda";
    private static final String PASSWORD = "K@kyang144";

    public static Connection connect () {
        Connection conn = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
