package com.yotam.webserver.interfaces;

import java.io.IOException;
import java.net.Socket;

import com.yotam.webserver.HTTPRequest;

public interface IHTTPRequestParser {
	HTTPRequest parseRequest(Socket connection) throws IOException;
}
