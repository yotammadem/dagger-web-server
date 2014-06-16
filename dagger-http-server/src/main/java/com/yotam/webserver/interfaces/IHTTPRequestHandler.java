package com.yotam.webserver.interfaces;

import java.io.IOException;

import com.yotam.webserver.HTTPRequest;
import com.yotam.webserver.HTTPResponse;

public interface IHTTPRequestHandler {
	public void handle(HTTPRequest request, HTTPResponse response) throws IOException;
}
