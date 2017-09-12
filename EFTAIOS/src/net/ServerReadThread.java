package net;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.actions.Action;
import model.actions.ChatMessage;
import model.player.PlayerCharacter;

public class ServerReadThread implements Runnable {

	private Socket aClientSocket;
	private boolean taskComplete = false;
	
	public ServerReadThread(Socket pClientSocket) {
		this.aClientSocket = pClientSocket;
	}
	
	@Override
	public void run() {
		try{
			ObjectInputStream in = new ObjectInputStream(
									new BufferedInputStream(aClientSocket.getInputStream()));
			//check if the client wants to send anything to the server
			Object clientInput = in.readObject();
			
			if(clientInput != null){
				System.out.println("Received " + clientInput.getClass() + " properly");
			}else{
				System.out.println("Received a null signal!");
			}
			
			//nb null returns false on instanceof
			//handle the client's requests
			if(clientInput instanceof ChatMessage){
				MultithreadedServer.getInstance().addNewMessage((ChatMessage)clientInput);
			}else if (clientInput instanceof Action){
				MultithreadedServer.getInstance().addNewAction((Action)clientInput);
			}else if (clientInput instanceof PlayerCharacter){
				MultithreadedServer.getInstance().addNewPlayerCharacter((PlayerCharacter)clientInput);
			}else{
				//useful for debugging
				if(clientInput != null){
					System.out.println("Object of class " + clientInput.getClass() + " rejected!");
				}
			}
			
			taskComplete = true;
		}catch(IOException io){
			io.printStackTrace();
			taskComplete = true;
		}catch(ClassNotFoundException cnf){
			System.err.println("Class not found!");
			taskComplete = true;
		}
	}
	
	public boolean isDone(){
		return taskComplete;
	}
	
}
