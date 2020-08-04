import java.io.*;

public class Test {
	public static void main(String[] args) {
		try {
			String v = "{\"key\":\"v\"}";
			System.out.println(v);
			Process process = Runtime.getRuntime().exec("python test.py "+v);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String s = "";
			while ((s = input.readLine()) != null)
				System.out.println(s);
			while ((s = error.readLine()) != null)
				System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
