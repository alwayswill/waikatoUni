package com.rpa.client.shuzu.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rpa.client.shuzu.objects.Script;

public class Scripts {
	final static Logger logger = Logger.getLogger(Scripts.class);
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public List<Script> getScripts(){
		connect = null;
		statement = null;
		preparedStatement = null;
		resultSet = null;
		List<Script> scriptsList = new ArrayList<Script>();
		String sql = "SELECT * from  RPA_scripts";;		
		try {
			connect = Database.getConnection();
			//debug
			preparedStatement = connect.prepareStatement(sql);
	        
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				Script script = new Script();
				script.id = resultSet.getInt("id");
				script.name = resultSet.getString("name");
				script.path = resultSet.getString("path");
				script.comments = resultSet.getString("comments");
				scriptsList.add(script);
			}
			
	        logger.debug("get scripts: " + preparedStatement.getParameterMetaData());

	    } 
		
	    catch (Exception e) {
	    	logger.debug("getProcesses: " + e.toString());
	    } 
	    finally {
	    	Database.closeConnection(connect);
	    }
		
        return scriptsList;
	}
}
