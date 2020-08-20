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
			// 개인키
			"RGAPI-ad13b224-7b6f-4358-8ff9-3c93df6cda80",
			"RGAPI-e1a57063-3429-414b-87f7-891538676189",
			"RGAPI-8cbfda0a-c52d-4a75-8aa5-b87491f13b3c",
			"RGAPI-44c9480b-4f14-4046-ab15-c181222f1d39",
			
			"RGAPI-b91dd012-eb39-41ef-9d5f-5bd2f54612a9",
			"RGAPI-255f85c9-c59c-4754-aee4-96abf7adebbf",
			"RGAPI-18a6cad9-eb35-4437-96ec-be050345e935",
			
			"RGAPI-9041c188-f0f9-42ad-8204-b41f5da22e6a",
			
			// 임시키
			"RGAPI-57f1039a-111f-47ad-b909-0e6b0e03a9f1",
			"RGAPI-1cb6220a-1d83-48da-b291-99906253f78e",
			"RGAPI-4bb21ae8-418a-4eba-bbfe-a2d502c5cc09",
			"RGAPI-20c4332b-9f44-4732-97db-82236465c4b1",
			
			"RGAPI-28d14c16-da02-4309-8e26-f5a55755d15e",
			"RGAPI-7d8fff74-f589-45b8-a9df-4e09d314cc34",
			"RGAPI-aa5ff062-27d9-489f-8d5e-57f75661f5dc",
			"RGAPI-4b77ebb2-71e0-48c4-aaef-7dd0cc94d3dd",
			"RGAPI-ba3b2cd3-9db9-4e79-a1c1-8ac8ba262fab"
			
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
//				System.out.println(url + " " + con.getResponseMessage());
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
//				e.printStackTrace();
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
		boolean isOk = false, isTimeout = false;
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
//			System.out.println(url + " " + con.getResponseMessage());
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
			} else if (con.getResponseMessage().equals("Too Many Requests") || con.getResponseMessage().equals("Gateway Timeout")) {
				isTimeout = true;
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		if(isOk)
			return result;
		else if(isTimeout)
			return "Timeout";
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
//			e.printStackTrace();
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
//			e.printStackTrace();
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
//			e.printStackTrace();
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
	
	public static void runAnalysis(String fileName, String argument) throws IOException {
		Process process = Runtime.getRuntime().exec("/usr/bin/python3 " + fileName + " " + argument);
		BufferedReader er = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		String s = "";
		StringBuilder sb = new StringBuilder();
		while((s = er.readLine()) != null) {
			sb.append(s);
		}
		if (sb.toString().length()!=0) {
			throw new IOException(sb.toString());
		}
	}
	
	
}
