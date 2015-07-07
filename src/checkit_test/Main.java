package checkit_test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import helper.Helper;

public class Main {

	public static void main(String[] args) {
		Helper exe = new Helper();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("mac", Helper.getMac());
		params.put("sender_time", Helper.getTime());

		String url = "http://localhost/checkit/recieve_post.php";
		String response = "";
		try {
			response = exe.doPost(url, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("response");
		System.out.println(response);
	}

}
