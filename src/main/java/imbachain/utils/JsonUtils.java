package imbachain.utils;

import com.google.gson.Gson;

public class JsonUtils {
	private final static Gson gson = new Gson();
	// Gson should be threadsafe?
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
}
