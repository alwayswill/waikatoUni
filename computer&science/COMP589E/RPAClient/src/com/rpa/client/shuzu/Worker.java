package com.rpa.client.shuzu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rpa.client.shuzu.db.Processes;
import com.rpa.client.shuzu.db.Scripts;
import com.rpa.client.shuzu.objects.*;

import org.apache.log4j.Logger;

final public class Worker implements Runnable{
	final static Logger logger = Logger.getLogger(Worker.class);

	public void run() {
		logger.info("main function executed!");
		checkProcess();
	}
	
	public void checkProcess(){
		List<Script> scriptsList = new ArrayList<Script>();
		Scripts scripts = new Scripts();
		scriptsList = scripts.getScripts();
		
		Iterator<Script> iterator = scriptsList.iterator();
		while(iterator.hasNext()){

		    Script obj = iterator.next();
		    //check whether the script is running
		    RPAProcess runningProcess = isExistedProcess(obj);
		    if(runningProcess.stat == null){
		    	logger.info("lanuchScript");
		    	lanuchScript(obj);
		    }
		    logger.info(obj.toString());
		}
	}
	
	public RPAProcess isExistedProcess(Script scriptObj){
		String[] cmd  = { "/bin/sh", "-c", "ps -e -o pid,cpu,rss,start,etime,stat,command|grep "+scriptObj.getPath()+" |tr -s ' '|awk '{printf \"%s@%s@%s@%s@%s@%s@%s@%s@%s\\n\", $1,$2,$3,$4,$5,$6,$7,$8,$9}'" };
		RPAProcess RPAprocess = new RPAProcess();
		
		try {
			String line;
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
					String[] info = line.split("@");
					if(info.length < 9){
						if(info[6].toString().startsWith("grep")){
							continue;
						}
					}else{
						if(info[8].toString().startsWith("ps")){
							continue;
						}
					}
					
					RPAprocess.pid = Integer.parseInt(info[0].toString());
					RPAprocess.CPU = info[1];
					RPAprocess.memory = Integer.parseInt(info[2].toString());
					RPAprocess.startTime = info[3];
					RPAprocess.runningTime = info[4].toString();
					RPAprocess.stat = info[5];
					RPAprocess.command = info[6]+" "+info[7];
					Processes DBProcess = new Processes();
					logger.debug("update process:" + RPAprocess.toString());
					DBProcess.updateInfo(RPAprocess);	
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		return RPAprocess;
	}
	
	public void lanuchScript(Script script){
		String[] cmd  = { "/bin/sh", "-c",  script.path+" &" };
		try {
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
	}
	
	public void updateProcessInfo(){
		
		String[] cmd  = { "/bin/sh", "-c", "ps -e -o pid,cpu,rss,start,etime,stat,command| tr -s ' '|awk '{printf \"%s@%s@%s@%s@%s@%s@%s\\n\", $1,$2,$3,$4,$5,$6,$7}'" };
		try {
			String line;
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			int lineNumber = 1;
			while ((line = input.readLine()) != null) {
				if (lineNumber == 1) {
					lineNumber++;
					continue;
				} else {
					
					String[] info = line.split("@");
					RPAProcess RPAprocess = new RPAProcess();
					RPAprocess.pid = Integer.parseInt(info[0]);
					RPAprocess.CPU = info[1];
					RPAprocess.memory = Integer.parseInt(info[2]);
					RPAprocess.startTime = info[3];
					RPAprocess.runningTime = info[4];
					RPAprocess.stat = info[5];
					RPAprocess.command = info[6];
					Processes DBProcess = new Processes();
					DBProcess.addProcesses(RPAprocess);
					logger.debug("Add process:" + RPAprocess.toString());
					lineNumber++;
				}

				logger.debug(lineNumber);
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
