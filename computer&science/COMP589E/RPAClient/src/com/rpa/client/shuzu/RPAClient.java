package com.rpa.client.shuzu;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RPAClient{
	public static void main(String[] args) {
//		pool size is 10
		ExecutorService service = Executors.newFixedThreadPool(10);
		while(true){
			service.submit(new Worker());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			Worker worker = new Worker();
//			worker.run();
		}
	}
}
