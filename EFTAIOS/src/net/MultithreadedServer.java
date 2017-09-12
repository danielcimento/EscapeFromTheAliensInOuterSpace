package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.actions.Action;
import model.actions.ChatMessage;
import model.engine.GameEngine;
import model.player.PlayerCharacter;
import model.engine.ServerLobby;

public class MultithreadedServer implements Runnable {
	private static MultithreadedServer instance = null;
	private static int defaultPort = 6789; 

	private GameEngine aEngine = new GameEngine();
	private ServerLobby aLobby = new ServerLobby();
	private BroadcastType currentBroadcast = BroadcastType.GAME;
	
	protected ServerSocket aServerSocket;
	protected int aPort; //default port
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	
	private MultithreadedServer(int pPort) {
		this.aPort = pPort;
	}
	
	public static void hostNewServer(int pPort){
		instance = new MultithreadedServer(pPort);
	}
	
	@Override
	public void run() {

		synchronized(this){
			 this.runningThread = Thread.currentThread();
		}
		
		
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
			new Thread(new ThreadTuple(clientSocket)).start();
		}
		System.out.println("Server stopped.");
	}
	
	private synchronized boolean isStopped(){
		return isStopped;
	}
	
	public synchronized void stop(){
		this.isStopped = true;
		try{
			System.out.println("Server was closed");
			this.aServerSocket.close();
		}catch (IOException e){
			throw new RuntimeException("Error closing server", e);
		}
	}
	
	public void runServer(){
		
		try{
			this.aServerSocket = new ServerSocket(aPort);
		}catch(IOException io){
			io.printStackTrace();
		}
		
	}
	
	public static MultithreadedServer getInstance(){
		if(instance == null){
			instance = new MultithreadedServer(defaultPort);
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
	
	public void addNewPlayerCharacter(PlayerCharacter clientInput){
		aEngine.addPlayerCharacter(clientInput);
	}


}
