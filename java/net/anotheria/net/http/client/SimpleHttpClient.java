package net.anotheria.net.http.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.anotheria.util.IOUtils;

/**
 * Very simple utility class to communicate with web server via HTTP protocol.
 * Provides just a few low level method to send request to a serve and get
 * response from it.
 * 
 * @author denis
 * 
 */
public class SimpleHttpClient {

	/**
	 * Packs string data to the post HTTP request and sends it on the Web Server
	 * by specific URL. Can be helpful when already prepared JSON, YAML or any other text format request
	 * has to be sent on a Server via HTTP Post.
	 * 
	 * @param url
	 * @param postData
	 * @return text response from the Server
	 * @throws Exception
	 *             delegates to using class exceptions handling.
	 */
	public String sendPostRequest(String url, String postData) throws Exception {

		String agent = "Mozilla/4.0";
		String type = "application/octet-stream";

		URL url_object = new URL(url);
		System.out.println("URL: " + url_object);
		HttpURLConnection conn = (HttpURLConnection) url_object.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", agent);
		conn.setRequestProperty("Content-Type", type);
		conn.setRequestProperty("Content-Length", postData.length() + "");

		OutputStream out = conn.getOutputStream();
		out.write(postData.getBytes("utf-8"));
		IOUtils.closeIgnoringException(out);
		System.out.println("response code:" + conn.getResponseCode());

		System.out.println("Request sent.");
		InputStream sIn = conn.getInputStream();
		StringBuilder ret = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(sIn);
		char[] buf = new char[100];
		int read = 0;
		do {
			read = reader.read(buf);
			if (read > 0)
				ret.append(buf);
		} while (read != -1);

		IOUtils.closeIgnoringException(sIn);
		return ret.toString();
	}

	public static void main(String[] args) throws Exception {
		SimpleHttpClient client = new SimpleHttpClient();
		String res = "";
		res = client.sendPostRequest("http://test.anotheria.net", "FooBar");
		System.out.println("Response: " + res);
	}
}
