package com.ssafy.lolbody.preset;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;

@Component
public class QueuePreset implements CommandLineRunner{
	@Autowired
	QueueRepository queueRepository;

	@Override
	public void run(String... args) throws Exception {
		String result = Api.getHttpRequest("http://static.developer.riotgames.com/docs/lol/queues.json");
		JSONArray arr = new JSONArray(result);
		for (int i = 0; i < arr.length(); i++) {
			String json = arr.get(i).toString();
			QueueDto queue = new Gson().fromJson(json, QueueDto.class);
			queueRepository.findById(queue.getQueueId()).orElseGet(() -> queueRepository.save(queue));
		}
	}
	
}
