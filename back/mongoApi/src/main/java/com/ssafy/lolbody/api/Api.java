package com.ssafy.lolbody.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Api {
	private final static String token = "RGAPI-4bc20f6a-6865-4a8d-844d-abb9bdabdcf6";
	private final static String[] tokens = {
			"RGAPI-ef42d362-2fb1-4df0-a570-110abb6171fc",
			"RGAPI-3b743023-04d5-4ec1-ade0-61d7eb4cbd1b",
			"RGAPI-4a186891-bc0b-48ee-8b17-0263360c977f",
			"RGAPI-4bc20f6a-6865-4a8d-844d-abb9bdabdcf6",
			"RGAPI-726874f6-d6d4-493a-843b-f36d995b5fce",
			"RGAPI-c7eef392-61a4-4a0d-a284-8831096c5b7a",
//			"RGAPI-3ecb19f7-233b-4684-bce4-941c34356b54",
			"RGAPI-6c63c01b-837a-4881-911e-432464587a73",
			"RGAPI-4aa80ba8-4260-478d-87ec-e011b0980e54"
	};
	private static int idx = 0;
	
	public static String multi(String input, String summonerName) {
		boolean isOk = false;
		String result = "";
		try {
			URL url = new URL(input+"/"+summonerName);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.addRequestProperty("X-Riot-Token", tokens[idx]);
			con.setRequestMethod("GET");
			idx = (idx + 1) % tokens.length;
			
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
	
	private final static String championUrl = "http://ddragon.leagueoflegends.com/cdn/10.14.1/data/en_US/champion.json";
	public static String getAllChampionsInfo() {
		String result = "";
		try {
			URL url = new URL(championUrl);
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
		return result;
	}
}
