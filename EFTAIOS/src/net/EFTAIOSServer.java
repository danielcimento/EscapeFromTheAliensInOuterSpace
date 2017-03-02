package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.Action;
import model.ChatMessage;
import model.GameEngine;
import model.ServerLobby;

public class EFTAIOSServer implements Runnable {
	private static EFTAIOSServer instance = null;
	private static int defaultPort = 6789; 

	private GameEngine aEngine = new GameEngine();
	private ServerLobby aLobby = new ServerLobby();
	private BroadcastType currentBroadcast = BroadcastType.LOBBY;
	
	protected ServerSocket aServerSocket;
	protected int aPort; //default port
	protected boolean isStopped = false;
	
	private EFTAIOSServer(int pPort) {
		this.aPort = pPort;
	}
	
	public static void hostNewServer(int pPort){
		instance = new EFTAIOSServer(pPort);
	}
	
	@Override
	public void run() {

		/*
		 * tutorials include 
		 * synchronized(this){
         * this.runningThread = Thread.currentThread();
         * }
         * 
         * but I dont know why so I won't include it for now	
		 * */
		
		
		runServer();
		while(!isStopped()){
			Socket clientSocket;
			try{
				clientSocket = this.aServerSocket.accept();
			}catch (IOException e){
				if(isStopped()){
					System.out.println("Server stopped.");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			new Thread(new EFTAIOSServerThread(clientSocket)).start();
		}
		System.out.println("Server stopped.");
	}
	
	private synchronized boolean isStopped(){
		return isStopped;
	}
	
	public synchronized void stop(){
		this.isStopped = true;
		try{
			this.aServerSocket.close();
		}catch (IOException e){
			throw new RuntimeException("Error closing server", e);
		}
	}
	
	public void runServer(){
		
		try{
			this.aServerSocket = new ServerSocket(aPort);
		}catch(IOException io){
			System.err.println("IO Exception!");
		}
		
	}
	
	public static EFTAIOSServer getInstance(){
		if(instance == null){
			instance = new EFTAIOSServer(defaultPort);
		}
		return instance;
	}
	
	public Object getBroadcastedObject(){
		switch(currentBroadcast){
		case LOBBY:
			return aLobby;
		case GAME:
			return aEngine;
		}
		
		//this should never happen
		//TODO: Find a way to handle missing cases
		return null;
	}

	public void addNewMessage(ChatMessage clientInput) {
		aEngine.addMessage(clientInput);
	}

	public void addNewAction(Action clientInput) {
		aEngine.addAction(clientInput);
		
	}


}
