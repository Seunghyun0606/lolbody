package com.ssafy.lolbody.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class Api {
	private final static String token = "RGAPI-4630b7c8-6bdc-446f-b81e-3e7244ab970c";
	private final static String[] tokens = {
			"RGAPI-673600b9-88ff-4459-8a6e-195e87cd0057",
			"RGAPI-3408ce53-b363-45f0-bc6d-b45ac19dacd5",
			"RGAPI-597023c5-75d2-49a8-8f89-46183a436bad",
			"RGAPI-51abf473-5ff6-4d25-94c0-9153b676379f",
			"RGAPI-d7e025f7-0ea6-4324-b2d3-1f969ba6eccd",
			"RGAPI-ee48e977-68a8-4222-92c6-6d5f381bb7f5",
			"RGAPI-4630b7c8-6bdc-446f-b81e-3e7244ab970c",
			"RGAPI-80dc4c4d-07f9-4800-a344-d969b5967b47",
			"RGAPI-9c1047a7-7adc-47c4-a7c8-646aff5c4404",
			"RGAPI-45f06699-a20f-4287-94d0-7d4396aeff53",
			"RGAPI-b129e076-891d-4776-a6a1-83e42b331744",
			"RGAPI-9a08de7d-b0b0-41d1-950b-8052f695ea81"
	};
	// 안먹히는 키 찾는 방법
	// https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/parkjamal?api_key=여기에키입력
	private static int idx = 0;
	
	public static String multi(String input, String summonerName) {
		boolean isOk = false;
		String result = "";
		int cnt = 10 * tokens.length;
		while (cnt-- > 0) {
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
					break;
				} else if (con.getResponseMessage().equals("Too Many Requests") || con.getResponseMessage().equals("Gateway Timeout")) {
					continue;
				} else {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			String name = summonerName.replaceAll("\\s", "%20");
			URL url = new URL(input+"/"+name);
			if(summonerName.length() == 0)
				url = new URL(input);
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
	
	public static String getAllChampionsInfo() {
		JSONArray jsonArray = new JSONArray(Api.get("https://ddragon.leagueoflegends.com/api/versions.json", ""));
		String version = jsonArray.getString(0);
		String championUrl = "http://ddragon.leagueoflegends.com/cdn/"+version+"/data/en_US/champion.json";
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
	
	public static String getHttpsRequest(String urlParam) {
		String result = "";
		try {
			URL url = new URL(urlParam);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setRequestMethod("GET");
			
			StringBuilder sb = new StringBuilder();
			if(con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
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
	
	public static void postHttpsRequest(Exception e, String s) {
		try {
			URL url = new URL("https://meeting.ssafy.com/hooks/euubt1fgfprqieijqc4y5whyqh");
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("charset", "UTF-8");
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setRequestMethod("POST");
			
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			JSONObject obj = new JSONObject(), props = new JSONObject();
			obj.put("text", "Exception log - " + s + "\n(i) 버튼을 눌러 확인");
			props.put("card", sw.toString());
			obj.put("props", props);
			
			OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
			os.write(obj.toString());
			os.flush();
			os.close();
			con.getInputStream();
			
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public static String getAnalysisData(String fileName, String argument) throws IOException {
		
		Process process = Runtime.getRuntime().exec("python "+fileName+" "+argument);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader er = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String s = "";
		StringBuilder sb = new StringBuilder();
		while((s = er.readLine()) != null) {
			sb.append(s);
		}
//		System.out.println(sb.toString());
		sb.setLength(0);
		while((s = br.readLine()) != null) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	
}
