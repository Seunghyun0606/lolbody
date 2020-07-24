package com.ssafy.lolbody.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Api {
	private final static String token = "RGAPI-9e581cdc-c05b-4b3f-b36a-8d6483681108";
	private final static String[] tokens = {
			"RGAPI-9e581cdc-c05b-4b3f-b36a-8d6483681108",
			"RGAPI-a84237a2-568a-4768-aaf9-1c750b72ce15",
			"RGAPI-7c0e6df1-1e74-4e76-8149-b35522915787",
			"RGAPI-b1601889-ab7e-4b2f-bc71-ee6fc1b5c3bb",
			"RGAPI-a6db19ff-7039-43d5-b082-aa1d364120db",
			"RGAPI-db3be196-5256-4179-a45e-bf87a8c46c3d",
			"RGAPI-08c89665-c6ef-4e98-bfc3-f242183a457a",
			"RGAPI-dd8501f2-c153-4949-886c-b129257c1826",
			"RGAPI-15280c6a-861c-462f-a23b-56c461c8cbe8"
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
