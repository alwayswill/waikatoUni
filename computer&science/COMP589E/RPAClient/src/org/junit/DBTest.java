package org.junit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rpa.client.shuzu.db.Processes;
import com.rpa.client.shuzu.db.Scripts;
import com.rpa.client.shuzu.objects.Script;

public class DBTest {

	@Test
	public void testAddProcesses() {
		Processes processes = new Processes();
		processes.addProcesses();
	}
	
	@Test
	public void testGetScripts() {
		List<Script> scriptsList = new ArrayList<Script>();
		Scripts scripts = new Scripts();
		scriptsList = scripts.getScripts();
		
		Iterator<Script> iterator = scriptsList.iterator();
		while(iterator.hasNext()){
		    Script obj = iterator.next();
		    System.out.println(obj.toString());
		}
	}

}
