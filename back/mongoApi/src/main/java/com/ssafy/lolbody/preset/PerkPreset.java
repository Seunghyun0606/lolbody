package com.ssafy.lolbody.preset;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.api.Api;

@Component
public class PerkPreset implements CommandLineRunner {
	@Autowired
	PerkRepository perkRepository;

	@Override
	public void run(String... args) throws Exception {
		String result = Api.getHttpsRequest("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/perks.json");

		JSONArray arr = new JSONArray(result);
		for (int i = 0; i < arr.length(); i++) {
			JSONObject info = arr.getJSONObject(i);
			int key = info.getInt("id");
			String name = info.getString("name");
			PerkDto perk = new PerkDto();
			perk.setKey(key);
			perk.setName(name);
			perkRepository.findById(key).orElseGet(() -> perkRepository.save(perk));
		}
	}

}
