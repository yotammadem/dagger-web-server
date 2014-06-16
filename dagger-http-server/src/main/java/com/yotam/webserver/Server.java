package com.yotam.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.inject.Inject;
import javax.inject.Named;

import com.yotam.webserver.interfaces.IConnectionHandler;
import com.yotam.webserver.interfaces.IServer;

public class Server implements IServer{
	
	private IConnectionHandler connectionHandler;
	private Integer port;
	private ServerSocket serverSocket;
	private Thread serverThread = null;
	private boolean done = false;
	
	@Inject
	public Server(IConnectionHandler handler, @Named("port") Integer port){
		this.connectionHandler = handler;
		this.port = port;
	}

	public void runServer(){
		done = false;
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new Error(e);
		}

		System.out.println("Server is running on port: "+port);
		while (!done) {
			try {
				Socket connection =  serverSocket.accept();
				connectionHandler.handleConnection(connection);
			} catch (IOException e) {
				if (!done){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void start(){
		if (serverThread != null){
			System.out.println("Server is already running...");
			return;
		}
		serverThread = new Thread(){
			public void run() {
				runServer();
			};
		};
		serverThread.start();
	}
	
	public void stop(){
		try {
			done=true;
			connectionHandler.stop();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverThread = null;
	}
}
