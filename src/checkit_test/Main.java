package checkit_test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.google.gson.Gson;

import client.Client;
import helper.Helper;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		
		// de local post
//		main.doLocalPost();
		main.doClouDant();
	}
	
	void doClouDant() {
		CloudantClient client = new CloudantClient(API.CLOUDANT_ACCOUNT,API.CLOUDANT_KEY,API.CLOUDANT_PASSWORD);

		System.out.println("Connected to Cloudant");
		System.out.println("Server Version: " + client.serverVersion());

//		List<String> databases = client.getAllDbs();
//		System.out.println("All my databases : ");
//		for ( String db : databases ) {
//		    System.out.println(db);
//		}
		
		Database db = client.database(API.CLOUDANT_DATABASE_NAME, false);
		Client clientData = new Client();
		clientData.setMac(Helper.getMac());
		clientData.setSenderTime(Helper.getTime());
		clientData.setSenderFormatedTime(Helper.getFormatedTime());
		Response lastInserted = db.save(clientData);
		System.out.println("You have inserted the clientData");
		
		Client c = db.find(Client.class, lastInserted.getId());
		Gson gson = new Gson();
		System.out.println(gson.toJson(c));

	}
	
	void doLocalPost() {
		PostJob postJob = new PostJob();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("mac", Helper.getMac());
		params.put("sender_time", Helper.getTime());
		params.put("sender_formated_time", Helper.getFormatedTime());

		String response = "";
		try {
			response = postJob.doPost(API.LOCAL_URL, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("response");
		System.out.println(response);
	}

}
