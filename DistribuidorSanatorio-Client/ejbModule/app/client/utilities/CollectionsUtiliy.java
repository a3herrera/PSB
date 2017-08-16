package app.client.utilities;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Useful Collections utilities for common task
 * </p>
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
public class CollectionsUtiliy {

	/**
	 * <p>
	 * This constructor method is private for avoid the class initialization
	 * </p>
	 */
	private CollectionsUtiliy() {
		super();
	}

	/**
	 * /** This method check if a {@link List} is null or not contain any value
	 * 
	 * @param List<E>
	 *            list
	 * @return true if is null or empty
	 */
	public static <E> boolean isEmptyList(final List<E> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * This method check if a Collection of type {@link Map} is null or empty
	 * 
	 * @param Map<K,V>,
	 *            map
	 * @return true if is null or empty
	 */
	public static <K, V> boolean isEmptyMap(final Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Check if a map contains a key
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K, V> boolean mapContainsKey(final Map<K, V> map, final V key) {
		if (!isEmptyMap(map) && map.containsKey(key)) {
			return true;
		}
		return false;
	}

	/**
	 * Return the value of the key in the map, if the key not exist in the map
	 * return null
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K, V> V getMapValue(final Map<K, V> map, final V key) {
		if (mapContainsKey(map, key)) {
			return map.get(key);
		}
		return null;
	}

	/**
	 * This method check if a <code>arrayList</code> is null or empty
	 * 
	 * @param E[]
	 *            array
	 * @return true if is null or empty
	 */
	public static <E> boolean isEmptyArray(final E[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}
}
