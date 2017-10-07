package net;

import java.io.IOException;
import java.net.Socket;

@Deprecated
public class ThreadTuple implements Runnable{
	private Socket aClientSocket;
	private ServerReadThread firstTask;
	private ServerWriteThread secondTask;
	
	
	public ThreadTuple(Socket pClientSocket){
		aClientSocket = pClientSocket;
	}
	
	@Override
	public void run() {
		firstTask = new ServerReadThread(aClientSocket);
		secondTask = new ServerWriteThread(aClientSocket);
		
		(new Thread(firstTask)).start();
		(new Thread(secondTask)).start();
		
		
		while(!firstTask.isDone() || !secondTask.isDone()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			aClientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
