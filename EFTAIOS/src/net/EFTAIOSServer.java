package net;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.GameEngine;

public class EFTAIOSServer {
	public void hostServer(int port){
		try{
			ServerSocket server = new ServerSocket(port);
			Socket clientSocket = server.accept();
			ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
			GameEngine eng = (GameEngine)inStream.readObject();
			handle(eng);
			ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outStream.writeObject(eng);
		}catch(IOException io){
			System.err.println("IO Exception!");
		}catch(ClassNotFoundException cnf){
			System.err.println("Wrong object sent!");
		}
		
	}

	private void handle(GameEngine eng) {
		// TODO Auto-generated method stub
		
	}
}
