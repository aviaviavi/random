package org.json;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.charset.*;


public class Download {
	public static void main(String[] args) throws Exception {
		URL page = new URL(args[0]);
		URLConnection connection = page.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String jsonString = "";
		String htmlLine = in.readLine();
		while (htmlLine != "var TralbumData = {") {
			htmlLine = in.readLine();
		}
		jsonString = htmlLine;
		while (htmlLine != "};") {
			htmlLine = in.readLine();
			jsonString += htmlLine;
		}
		System.out.println(jsonString);
		//JSONObject myjson = new JSONObject("[]");
	}

	// public static void getSong(String[] url) throws Exception {
	// 	URL site = new URL(url);
	// 	ReadableByteChannel rbc = Channels.newChannel(site.openStream());
	// 	FileOutputStream fos = new FileOutputStream("test.mp3");
	// 	fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
	// }


}