package com.ssafy.lolbody.preset;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SpellPreset implements CommandLineRunner {
	@Autowired
	SpellRepository spellRepository;

	@Override
	public void run(String... args) throws Exception {
		String result = "";
		try {
			URL url = new URL("http://ddragon.leagueoflegends.com/cdn/10.15.1/data/en_US/summoner.json");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			con.setRequestMethod("GET");

			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				result = sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
