package com.yotam.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.TreeMap;

public class HTTPRequest {
	private Socket socket;
	private String requestLine;
	private InputStream in = null;
	private TreeMap<String, String> headers;
	

	public HTTPRequest(Socket socket, String requestLine, TreeMap<String, String> headers) {
		super();
		this.socket = socket;
		this.requestLine = requestLine;
		this.headers = headers;
	}

	public Socket getSocket() {
		return socket;
	}

	public String getRequestLine() {
		return requestLine;
	}
	
	public InputStream getInputStream() throws IOException{
		if (in == null){
			in = socket.getInputStream();
		}
		return in;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getPath() {
		return requestLine.substring(4, requestLine.length() - 9).trim();
	}

	public String getSenderIP() {
		SocketAddress sockAddr = socket.getRemoteSocketAddress();
		String sockAddrStr = sockAddr.toString();
		sockAddrStr = sockAddrStr.substring(1, sockAddrStr.indexOf(':'));
		return sockAddrStr;
	}

}
