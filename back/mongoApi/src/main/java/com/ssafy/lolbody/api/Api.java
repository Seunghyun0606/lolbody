package com.ssafy.lolbody.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Api {
	private final static String token = "RGAPI-2adde83f-a408-46e6-bf94-8830e3700c9d";
	private final static String[] tokens = {
			"RGAPI-1d55e793-4537-4787-806a-f9d6ca6b1c6e",
			"RGAPI-069e95f2-7d12-4fcd-963c-c762183c318e",
			"RGAPI-b7741761-cd43-4d3d-9c9e-9dd55328fdc1",
			"RGAPI-2adde83f-a408-46e6-bf94-8830e3700c9d",
			"RGAPI-bfe812b2-f299-4625-9879-372199313ec5",
			"RGAPI-acb1f578-b44d-46e0-8dac-c22ecc80a33b",
			"RGAPI-973f062c-cb73-463a-a271-c5ffb024f52f",
			"RGAPI-47c0271d-2cbd-467c-9c3b-8c0f5d89cad4",
			"RGAPI-2df57d2d-0fa7-4e43-bec9-ce4b1ee0f909",
			"RGAPI-987954d2-fd43-4bbb-a7ca-4e600a5f428d",
			"RGAPI-e59754f2-f56b-4471-aaf6-75330d5863f6"
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
				System.out.println(con.getResponseMessage() + " - " + url + " - " + tokens[idx]);
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
				System.out.println(con.getResponseMessage() + " - " + url);
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
