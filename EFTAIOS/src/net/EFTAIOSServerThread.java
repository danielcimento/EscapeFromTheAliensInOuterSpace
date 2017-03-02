package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Action;
import model.ChatMessage;

public class EFTAIOSServerThread implements Runnable {

	private Socket aClientSocket;
	
	public EFTAIOSServerThread(Socket pClientSocket) {
		this.aClientSocket = pClientSocket;
	}
	
	@Override
	public void run() {
		try{
			ObjectInputStream in = new ObjectInputStream(
									aClientSocket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(
									aClientSocket.getOutputStream());
			
			//check if the client wants to send anything to the server
			Object clientInput = in.readObject();
			
			//nb null returns false on instanceof
			//handle the client's requests
			if(clientInput instanceof ChatMessage){
				EFTAIOSServer.getInstance().addNewMessage((ChatMessage)clientInput);
			}else if (clientInput instanceof Action){
				EFTAIOSServer.getInstance().addNewAction((Action)clientInput);
			}
			
			//update the client with possibly new information
			Object serverOutput = EFTAIOSServer.getInstance().getBroadcastedObject();
			out.writeObject(serverOutput);
			
			out.close();
			in.close();
		}catch(IOException io){
			System.err.println("Bad IO!");
		}catch(ClassNotFoundException cnf){
			System.err.println("Class not found!");
		}
	}
	
}
