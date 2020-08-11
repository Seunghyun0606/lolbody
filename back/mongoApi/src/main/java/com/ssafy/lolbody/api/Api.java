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
			"RGAPI-4630b7c8-6bdc-446f-b81e-3e7244ab970c",
			"RGAPI-ad13b224-7b6f-4358-8ff9-3c93df6cda80",
			"RGAPI-e1a57063-3429-414b-87f7-891538676189",
			"RGAPI-8cbfda0a-c52d-4a75-8aa5-b87491f13b3c",
			"RGAPI-44c9480b-4f14-4046-ab15-c181222f1d39",
			
			"RGAPI-b91dd012-eb39-41ef-9d5f-5bd2f54612a9",
			"RGAPI-255f85c9-c59c-4754-aee4-96abf7adebbf",
			"RGAPI-18a6cad9-eb35-4437-96ec-be050345e935",
			//////
			
			"RGAPI-4db32c90-d3b1-49e7-9301-95f9611eeda7",
            "RGAPI-808ae878-5828-4e9f-8f4c-adc34751af36",
            "RGAPI-e3d480a8-44b7-41b4-8841-121604a409d9",
            "RGAPI-5497b3e5-f2a8-4212-8fa8-9c24387d0c75",
            "RGAPI-fd677b5f-3658-44c6-9dd6-d6bbc62adbce",
            "RGAPI-1a587246-7bf5-414a-959c-8a37f43f83cb",
            "RGAPI-b9f6a0ba-53c1-4bec-8ec7-72df59579929",
            "RGAPI-959282d4-ad8a-4191-96de-dfdb1da2eb48",
            "RGAPI-547191da-3552-4eb6-9a59-b68a2fbc489f",
            "RGAPI-2e688a6a-d4b5-48bc-94e3-f9ca54200235",
            "RGAPI-23c52658-bf9d-4648-94db-d6285875c269"
	};
	// 안먹히는 키 찾는 방법
	// https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/parkjamal?api_key=여기에키입력
	private static int idx = 0;
	
	public static String multi(String input, String summonerName) {
		boolean isOk = false, isForbidden = false;
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
					isForbidden = true;
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(isOk)
			return result;
		else if(isForbidden)
			return "Forbidden";
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
		
		Process process = Runtime.getRuntime().exec("/usr/bin/python3 "+fileName+" "+argument);
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader er = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String s = "";
		StringBuilder sb = new StringBuilder();
		while((s = er.readLine()) != null) {
			sb.append(s);
		}
		if (sb.toString().length()!=0) {
			throw new IOException(sb.toString());
		}
		sb.setLength(0);
		while((s = br.readLine()) != null) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	
}
