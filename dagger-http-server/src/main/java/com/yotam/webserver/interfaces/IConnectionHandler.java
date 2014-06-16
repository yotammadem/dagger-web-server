package com.yotam.webserver.interfaces;

import java.net.Socket;

public interface IConnectionHandler {
	public void handleConnection(Socket connection);
	public void stop();
}
