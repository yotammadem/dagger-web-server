package com.yotam.webserver;

import java.io.File;
import java.io.IOException;

import com.yotam.webserver.interfaces.IServer;

import dagger.ObjectGraph;

public class Main {
	private static final String SERVER_PROPERTIES = "server.properties";

	public static void main(String[] args) {
		try {
			System.out.println("Demo http server");
			System.out.println("Reading configuration from: "+new File(SERVER_PROPERTIES).getAbsolutePath());
			ObjectGraph g = ObjectGraph.create(new ServerModule(SERVER_PROPERTIES));
			IServer srv = g.get(IServer.class);
			srv.start();
			System.out.println("Hit Ctrl-C to stop");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
