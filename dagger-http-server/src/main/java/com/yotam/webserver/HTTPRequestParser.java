package com.yotam.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.yotam.webserver.interfaces.IHTTPRequestParser;

@Singleton
public class HTTPRequestParser implements IHTTPRequestParser {

	@Inject
	public HTTPRequestParser(){
		
	}
	
	public HTTPRequest parseRequest(Socket connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		String firstLine = in.readLine();
		if (firstLine != null) {
			TreeMap<String, String> headers = readHeaders(in);
			HTTPRequest request = new HTTPRequest(connection, firstLine, headers);
			return request;
		}
		return null;
	}

	private TreeMap<String, String> readHeaders(BufferedReader in)
			throws IOException {
		String requestLine = null;
		TreeMap<String, String> headers = new TreeMap<String, String>();
		do {
			requestLine = in.readLine();
			if (requestLine != null && requestLine.trim().length() > 0) {
				String[] keyValue = requestLine.split(":");
				headers.put(keyValue[0].trim(), keyValue[1].trim());
			}
		} while (requestLine != null && requestLine.trim().length() > 0);
		return headers;
	}

}
