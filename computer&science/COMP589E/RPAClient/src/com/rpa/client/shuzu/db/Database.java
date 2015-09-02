package com.rpa.client.shuzu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class Database {
	final static Logger logger = Logger.getLogger(Database.class);
    static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "comp589_rpa";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "lishuzu511";

        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url + dbName, userName,password);
        logger.debug("DB connected");
        return conn;
    }
    
    public static void closeConnection(Connection conn) {
        try {

            conn.close();

        } catch (SQLException e) {
        	if(logger.isDebugEnabled()){
    			logger.debug("This is debug : " + e.getMessage());
    		}
        }
    }
	public static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
}