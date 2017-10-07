package net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Deprecated
public class ServerWriteThread implements Runnable {

	private Socket aClientSocket;
	private boolean taskComplete = false;
	
	
	public ServerWriteThread(Socket pClientSocket) {
		this.aClientSocket = pClientSocket;
	}
	
	@Override
	public void run() {
		try{
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(aClientSocket.getOutputStream()));
			Object serverOutput = MultithreadedServer.getInstance().getBroadcastedObject();
			out.writeUnshared(serverOutput);
			out.flush();
			taskComplete = true;
		}catch(IOException io){
			io.printStackTrace();
			taskComplete = true;
		}
	}
	
	public boolean isDone(){
		return taskComplete ;
	}
	
}
