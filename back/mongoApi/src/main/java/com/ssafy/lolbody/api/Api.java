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
			"RGAPI-2cbf249f-8e80-4d6c-9b04-a2ac08f343bd",
			"RGAPI-6e18418f-6750-44ab-8efa-e30d2cef421f",
			"RGAPI-c2249e88-6e49-48c2-9aa7-9faaf65b1dbc",
			"RGAPI-0d370615-8fb7-4a4d-a59f-d599ca2eadf5",
			"RGAPI-79c1843f-8202-4df7-9a7b-f22c860fede2",
			"RGAPI-4630b7c8-6bdc-446f-b81e-3e7244ab970c",
			"RGAPI-2624c4e1-7f18-4223-95ff-a8c1c1e121a0",
			"RGAPI-fe5202a5-2e85-4327-899c-93c2c236375e",
			"RGAPI-1e2201d2-67a9-4296-b820-1bfb96d33f1a",
			"RGAPI-610acc60-fc0d-463c-8546-0e2f735d4325",
			"RGAPI-d96d28c9-f17b-4a81-a029-7a0c007fba81"
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
	
	public String getAnalysisData(String fileName, String result) throws IOException {
		
		Process process = Runtime.getRuntime().exec("python "+fileName+" "+result);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String s = "";
		StringBuilder sb = new StringBuilder();
		while((s = br.readLine()) != null) {
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	
}
