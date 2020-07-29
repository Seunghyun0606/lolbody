package com.ssafy.lolbody.preset;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.api.Api;

@Component
public class SpellPreset implements CommandLineRunner {
	@Autowired
	SpellRepository spellRepository;

	@Override
	public void run(String... args) throws Exception {
		String result = Api.getHttpRequest("http://ddragon.leagueoflegends.com/cdn/10.15.1/data/en_US/summoner.json");
		JSONObject data = new JSONObject(result).getJSONObject("data");
		for (String s : data.keySet()) {
			JSONObject info = data.getJSONObject(s);
			String key = info.getString("key");
			String name = info.getString("name");
			SpellDto spell = new SpellDto();
			spell.setKey(key);
			spell.setName(name);
			spellRepository.findById(key).orElseGet(() -> spellRepository.save(spell));
		}
	}

}
