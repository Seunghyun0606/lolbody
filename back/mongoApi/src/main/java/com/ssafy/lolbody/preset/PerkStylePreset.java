package com.ssafy.lolbody.preset;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.api.Api;

@Component
public class PerkStylePreset implements CommandLineRunner {
	@Autowired
	PerkStyleRepository perkStyleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		JSONArray arr = new JSONObject(Api.get("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/perkstyles.json", "")).getJSONArray("styles");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject info = arr.getJSONObject(i);
			int key = info.getInt("id");
			String name = info.getString("name");
			PerkStyleDto perkStyle = new PerkStyleDto();
			perkStyle.setKey(key);
			perkStyle.setName(name);
			perkStyleRepository.findById(key).orElseGet(() -> perkStyleRepository.save(perkStyle));
		}
	}

}
