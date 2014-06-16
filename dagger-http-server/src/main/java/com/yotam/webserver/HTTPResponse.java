package com.yotam.webserver;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class HTTPResponse {
	private TreeMap<String, String> headers = new TreeMap<String, String>();
	private OutputStream out = null;
	private boolean wroteHeaders = false;
	private String status = null;
	
	
	public HTTPResponse(OutputStream out){
		this.out = out;
		setStatus(200, "OK"); //default status
	}
	
	public void setHeader(String header, String value){
		headers.put(header, value);
	}
	
	public Map<String,String> getHeaders(){
		return headers;
	}
	
	public OutputStream getOutputStream(){
		writeHeadersAndStatusIfNeed();
		return out;
	}

	
	private void writeHeadersAndStatusIfNeed() {
		if (wroteHeaders){
			return;
		}
		wroteHeaders = true;
		PrintWriter pw = new PrintWriter(out);
		pw.print(status+"\r\n");
		for (String header : getHeaders().keySet()){
			String value = getHeaders().get(header);
			pw.print(header+": "+value+"\r\n");
		}
		
		pw.print("\r\n"); //empty line at the end
		
		pw.flush();
	}

	public void setStatus(int status, String message) {
		this.status = "HTTP/1.0 "+status+" "+message;
	}
}
