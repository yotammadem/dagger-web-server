package com.yotam.webserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HTTPResponseRenderer {

	@Inject
	public HTTPResponseRenderer() {
	}

	public void renderError(HTTPResponse response, int code,
			String title, String msg) throws IOException {
		response.setStatus(code, title);
		PrintWriter out = new PrintWriter(response.getOutputStream());
		out.print("<html><title>" + code + " " + title + "</title>"
				+ "</head><body>" + "<h1>" + title + "</h1>" + msg
				+ "<P>" + "</body></html>\r\n");
		out.flush();
	}

}
