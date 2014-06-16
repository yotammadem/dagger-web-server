package com.yotam.webserver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.yotam.webserver.interfaces.IConnectionHandler;
import com.yotam.webserver.interfaces.IHTTPRequestHandler;
import com.yotam.webserver.interfaces.IHTTPRequestParser;

@Singleton
public class HTTPConnectionHandler implements IConnectionHandler {

	private ExecutorService executor;
	private IHTTPRequestHandler requestHandler;
	private IHTTPRequestParser requestParser;

	@Inject
	public HTTPConnectionHandler(IHTTPRequestHandler requestHandler, IHTTPRequestParser requestParser) {
		this.executor = Executors.newFixedThreadPool(5);
		this.requestHandler = requestHandler;
		this.requestParser = requestParser;
	}

	public void handleConnection(Socket connection) {
		executor.execute(createConnectionHandler(connection));
	}

	private Runnable createConnectionHandler(final Socket connection) {
		return new Runnable() {
			public void run() {
				try {
					HTTPRequest request = requestParser.parseRequest(connection);
					HTTPResponse response = new HTTPResponse(connection.getOutputStream());
					if (request != null){
						requestHandler.handle(request, response);
					}
					
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public void stop() {
		executor.shutdownNow();
	}

}
