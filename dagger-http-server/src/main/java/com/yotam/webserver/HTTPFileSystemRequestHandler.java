package com.yotam.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;

import com.yotam.webserver.interfaces.IHTTPRequestHandler;

@Singleton
public class HTTPFileSystemRequestHandler implements IHTTPRequestHandler {

	private String wwwHome;
	private HTTPResponseRenderer responseRenderer;

	@Inject
	public HTTPFileSystemRequestHandler(@Named("wwwHome") String wwwHome, HTTPResponseRenderer responseRenderer) {
		this.wwwHome = wwwHome;
		this.responseRenderer = responseRenderer;
	}

	public void handle(HTTPRequest request, HTTPResponse response) throws IOException {
		if (isBadRequest(request.getRequestLine())) {
			responseRenderer.renderError(response, 400, "Bad Request",
					"This handler only supports get requests");
		} else {
			handleRequest(request, response);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}

	private boolean isBadRequest(String requestLine) {
		return !requestLine.startsWith("GET") || !(requestLine.endsWith("HTTP/1.0") || requestLine.endsWith("HTTP/1.1"));
	}

	private void handleRequest(HTTPRequest request, HTTPResponse response)
			throws IOException {

		String path = wwwHome + "/" + request.getPath();
		
		File file = new File(path);
		if (file.isDirectory()) {
			path = path + "index.html";
			file = new File(path);
		}
		
		if (!file.exists()) {
			responseRenderer.renderError(response, 404, "Not Found", "The requested resource: "
					+ request.getPath()
					+ " was not found on this server.");
			return;
		}

		response.setHeader("Content-Type", guessContentType(path));
		response.setHeader("Date", new Date().toString());
		IOUtils.copy(new FileInputStream(file), response.getOutputStream());
	}

	private String guessContentType(String path) {
		path = path.toLowerCase();
		if (path.endsWith(".html") || path.endsWith(".htm")){
			return "text/html";
		}else if (path.endsWith(".gif")){
			return "image/gif";
		}else if (path.endsWith(".png")){
			return "image/png";
		}else if (path.endsWith(".jpg") || path.endsWith(".jpeg")){
			return "image/jpeg";
		}else{
			return "text/plain";
		}
	}

}
