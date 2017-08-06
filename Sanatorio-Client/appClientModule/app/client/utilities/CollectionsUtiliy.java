package app.client.utilities;

import java.util.Map;

public class CollectionsUtiliy {

	private CollectionsUtiliy() {
		// This method is private to avoid class inicialization
	}

	public static boolean isEmptyList() {
		return false;
	}

	public static <K, V> boolean isEmptyMap(Map<K, V> map) {
		return false;
	}

	public static boolean isEmptyArray() {
		return false;
	}
}
