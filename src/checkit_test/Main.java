package checkit_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

	String getMac() {
		String output = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			output = sb.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return output;
	}

	String getTime() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis());
	}

	String doPost(String url, Map<String, Object> params) throws IOException {
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

	public static void main(String[] args) {
		Main example = new Main();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("mac", example.getMac());
		params.put("sender_time", example.getTime());

		String url = "http://localhost/checkit/recieve_post.php";
		String response = "";
		try {
			response = example.doPost(url, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("response");
		System.out.println(response);
	}

}
