package com.yotam.webserver;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import com.yotam.webserver.interfaces.IConnectionHandler;
import com.yotam.webserver.interfaces.IHTTPRequestHandler;
import com.yotam.webserver.interfaces.IHTTPRequestParser;
import com.yotam.webserver.interfaces.IServer;

import dagger.Module;
import dagger.Provides;

@Module(injects={IServer.class})
public class ServerModule {

	private ServerConfiguration configuration = null;

	public ServerModule(ServerConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public ServerModule(){
		
	}

	public ServerModule(String configurationFilePath) throws IOException {
		this.configuration = new ServerConfiguration(configurationFilePath);
	}

	@Provides @Singleton IServer provideServer(Server server){
		return server;
	}
	
	@Provides HTTPResponseRenderer provideResponseRenderer(){
		return new HTTPResponseRenderer();
	}

	@Provides IHTTPRequestParser provideHttpRequestParser(){
		return new HTTPRequestParser();
	}
	
	@Provides IHTTPRequestHandler provideRequestHandler(HTTPFileSystemRequestHandler handler){
		return handler;
	}

	@Provides IConnectionHandler provideConnectionHandler(HTTPConnectionHandler handler){
		return handler;
	}
	
	@Provides @Named(ServerConfiguration.WWW_HOME) String provideWWWHome(){
		return configuration.getWWWHome();
	}
	
	@Provides @Named(ServerConfiguration.PORT) Integer providePort(){
		return configuration.getPort();
	}
	
}
