package checkit_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class PostJob {
	
	public String doPost(String url, Map<String, Object> params) throws IOException {
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		String response = "";

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		for (int c = in.read(); c != -1; c = in.read()) {
			response = response.concat(String.valueOf((char) c));
		}

		return response;
	}
	
}
