package com.ssafy.lolbody.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Api {
	private final static String token = "RGAPI-d301d131-9bfc-4fb3-ba75-f83d8e35bae6";
	public static String get(String input, String summonerName) {
		boolean isOk = false;
		String result = "";
		try {
			URL url = new URL(input+"/"+summonerName);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.addRequestProperty("X-Riot-Token", token);
			
			con.setRequestMethod("GET");
			
			StringBuilder sb = new StringBuilder();
			if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
				String line;
				while((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				isOk = true;
				result = sb.toString();
			} else {
				System.out.println(con.getResponseMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(isOk)
			return result;
		else
			return "Fail";
	}
}
