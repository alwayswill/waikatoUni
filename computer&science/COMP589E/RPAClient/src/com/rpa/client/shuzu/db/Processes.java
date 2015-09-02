package com.rpa.client.shuzu.db;

import java.sql.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rpa.client.shuzu.objects.RPAProcess;

public class Processes {
	final static Logger logger = Logger.getLogger(Processes.class);
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public void addProcesses(RPAProcess RPAprocess){
		connect = null;
		statement = null;
		preparedStatement = null;
		resultSet = null;
		String sql = "INSERT INTO RPA_Threads"
				+ "(pid, memory, startTime, runningTime, CPU, command, stat, IP, status) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";		
		try {
			connect = Database.getConnection();
			
			//debug
			preparedStatement = connect.prepareStatement(sql);
	        preparedStatement.setInt(1, RPAprocess.id);
			preparedStatement.setInt(2, RPAprocess.memory);
			preparedStatement.setString(3, RPAprocess.startTime);
			preparedStatement.setString(4, RPAprocess.runningTime);
			preparedStatement.setString(5, RPAprocess.CPU);
			preparedStatement.setString(6, RPAprocess.command);
			preparedStatement.setString(7, RPAprocess.stat);
			preparedStatement.setString(8, "192.168.1.1");
			preparedStatement.setInt(9, 0);
	        preparedStatement.executeUpdate();
	        logger.debug("add process: " + preparedStatement.getParameterMetaData());
	    } 
	    catch (Exception e) {
	    	logger.debug("getProcesses: " + e.toString());
	    } 
	    finally {
	    	Database.closeConnection(connect);
	    }
	}
}
