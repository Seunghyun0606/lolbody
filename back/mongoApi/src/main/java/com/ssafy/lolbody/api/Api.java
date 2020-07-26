package com.ssafy.lolbody.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Api {
	private final static String token = "RGAPI-627f6d2f-36a2-4876-ac2d-e52d68a4c092";
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
	
	public static String getHttpRequest(String urlParam) {
		String result = "";
		try {
			URL url = new URL(urlParam);
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
