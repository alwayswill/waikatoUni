package com.rpa.client.shuzu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.rpa.client.shuzu.db.Processes;
import com.rpa.client.shuzu.objects.*;

import org.apache.log4j.Logger;

public class Worker {
	final static Logger logger = Logger.getLogger(Worker.class);
	public String[] cmd  = { "/bin/sh", "-c", "ps -e -o pid,cpu,rss,start,time,stat,command| tr -s ' '|awk '{printf \"%s@%s@%s@%s@%s@%s@%s\\n\", $1,$2,$3,$4,$5,$6,$7}'" };
//	public String shell = "ps -e -o pid,cpu,rss,start,time,stat,command";
	public void main() {
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
					logger.debug(info);
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
