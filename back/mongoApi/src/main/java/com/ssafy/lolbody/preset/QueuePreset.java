package com.ssafy.lolbody.preset;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class QueuePreset implements CommandLineRunner{
	@Autowired
	QueueRepository queueRepository;

	@Override
	public void run(String... args) throws Exception {
		String result = "";
		try {
			URL url = new URL("http://static.developer.riotgames.com/docs/lol/queues.json");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setRequestMethod("GET");
			
			StringBuilder sb = new StringBuilder();
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
				String line;
				while((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				result = sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray arr = new JSONArray(result);
		for (int i = 0; i < arr.length(); i++) {
			String json = arr.get(i).toString();
			QueueDto queue = new Gson().fromJson(json, QueueDto.class);
			queueRepository.findById(queue.getQueueId()).orElseGet(() -> queueRepository.save(queue));
		}
	}
	
}
