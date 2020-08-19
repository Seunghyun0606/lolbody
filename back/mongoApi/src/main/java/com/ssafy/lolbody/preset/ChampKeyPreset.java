package com.ssafy.lolbody.preset;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.api.Api;

@Component
public class ChampKeyPreset implements CommandLineRunner{
	@Autowired
	ChampkeyRepository preset;

	@Override
	public void run(String... args) throws Exception {
		JSONObject obj = new JSONObject(Api.getAllChampionsInfo());
		JSONObject data = obj.getJSONObject("data");
		for(String name: data.keySet()) {
			JSONObject info = data.getJSONObject(name);
			String key = info.getString("key");
			ChampKeyDto champ = new ChampKeyDto();
			champ.setKey(key);
			champ.setName(name);
			preset.findById(key).orElseGet(() -> preset.save(champ));
		}
	}
	
}
