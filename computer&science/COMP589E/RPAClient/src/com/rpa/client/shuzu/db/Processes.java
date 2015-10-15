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
	
	
	public void updateInfo(RPAProcess RPAprocess){
		int id = getProcess(RPAprocess);
//		TODO: If the status of the process is -1  and running, kill it.
		logger.debug("updateInfo id:"+id);
		if(id != 0){
			updateProcesses(id, RPAprocess);
		}else{
			addProcesses(RPAprocess);
		}
	}
	public void addProcesses(RPAProcess RPAprocess){
		logger.debug("addProcesses: ");
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
	        preparedStatement.setInt(1, RPAprocess.pid);
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
	
	public void updateProcesses(int id, RPAProcess RPAprocess){
		logger.debug("updateProcesses: "+RPAprocess.pid);
		connect = null;
		statement = null;
		preparedStatement = null;
		resultSet = null;
		String sql = "UPDATE RPA_Threads SET memory=?, startTime=?, runningTime=?, CPU=?, command=?, stat=?, IP=?, pid=? WHERE id=?";		
		try {
			connect = Database.getConnection();
			
			//debug
			preparedStatement = connect.prepareStatement(sql);
	        
			preparedStatement.setInt(1, RPAprocess.memory);
			preparedStatement.setString(2, RPAprocess.startTime);
			preparedStatement.setString(3, RPAprocess.runningTime);
			preparedStatement.setString(4, RPAprocess.CPU);
			preparedStatement.setString(5, RPAprocess.command);
			preparedStatement.setString(6, RPAprocess.stat);
			preparedStatement.setString(7, "192.168.1.1");
			preparedStatement.setInt(8, RPAprocess.pid);
			preparedStatement.setInt(9, id);
	        preparedStatement.executeUpdate();
	        logger.debug("updateProcesses: " + preparedStatement.getParameterMetaData());
	    } 
	    catch (Exception e) {
	    	logger.debug("getProcesses: " + e.toString());
	    } 
	    finally {
	    	Database.closeConnection(connect);
	    }
	}
	
	
	public int getProcess(RPAProcess RPAprocess){
		connect = null;
		statement = null;
		preparedStatement = null;
		resultSet = null;
		int id = 0;
		String sql = "SELECT * FROM RPA_Threads WHERE command=?";		
		try {
			connect = Database.getConnection();
			//debug
			preparedStatement = connect.prepareStatement(sql);
	        preparedStatement.setString(1, RPAprocess.command);
	        ResultSet result = preparedStatement.executeQuery();
	        result.next();
	        id = result.getInt("id");
	        logger.debug("add process: " + preparedStatement.getParameterMetaData());
	    } 
	    catch (Exception e) {
	    	logger.debug("getProcesses: " + e.toString());
	    } 
	    finally {
	    	Database.closeConnection(connect);
	    }
		
		return id;
	}
}
