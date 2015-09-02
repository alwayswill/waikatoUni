package com.rpa.client.shuzu.objects;

public class RPAProcess {
//ps -e -o pid,cpu,rss,start,time,,stat,command
	public int id;
	public int pid;
	public String CPU;
	public int memory;
	public String startTime;
	public String runningTime;
	public String stat;
	public String command;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Process [id=" + id + ", pid=" + pid + ", CPU=" + CPU + ", memory=" + memory + ", startTime=" + startTime
				+ ", runningTime=" + runningTime + ", stat=" + stat + ", command=" + command + "]";
	}
	
}
