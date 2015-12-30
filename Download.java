import org.json.*;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Download {
	/* Will work while the html stays the same. Downloads all the songs on the given url
		$ java Download SOMEURL
	*/
	public static void main(String[] args) throws IOException, JSONException {
		String url = args[0];
		JSONObject mainJSON = getMainJSON(url);
		JSONObject albumJSON = mainJSON.getJSONObject("current");
		String artist = mainJSON.getString("artist");
		String albumName = albumJSON.getString("title");
		JSONArray tracks = mainJSON.getJSONArray("trackinfo");
		System.out.println(tracks);
		JSONObject track;
		String trackTitle;
		String directory = artist + " - " + albumName;
		new File(directory).mkdir();
		for (int i = 0; i < tracks.length(); i ++) {
			track = tracks.getJSONObject(i);
			trackTitle = track.getString("title");
			getSong(track.getJSONObject("file").getString("mp3-128"), directory + "/" + trackTitle);
			System.out.println(trackTitle + " added!");
		}
	}
	/* Gets the JSON of the album data */
	public static JSONObject getMainJSON(String url) throws IOException, JSONException{
		URL page = new URL(url);
		URLConnection connection = page.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String jsonString = "";
		String html = "";
		String line;
		while ((line = in.readLine()) != null) {
			if (line.indexOf("// ") == -1) {
				html += line;
			}
		}
		int startIndex = html.indexOf("var TralbumData = {");
		int endIndex = html.indexOf("};", startIndex) + 2;
		jsonString = html.substring(startIndex + 18, endIndex).replace("\" + \"", "");
		System.out.println(jsonString);
		JSONObject json = new JSONObject(jsonString + "}");
		return json;
	}
	/* Given a URL of a song and a file name (trackTitle), getSong() will download it */
	public static void getSong(String url, String trackTitle) throws IOException{
        if (url.startsWith("//")) {
            url = "https:" + url;
        }
	    URLConnection conn = new URL(url).openConnection();
	    InputStream is = conn.getInputStream();
	    OutputStream outstream = new FileOutputStream(new File(trackTitle + ".mp3"));
	    byte[] buffer = new byte[4096];
	    int len;
	    while ((len = is.read(buffer)) > 0) {
	        outstream.write(buffer, 0, len);
	    }
	    outstream.close();
	}
}
