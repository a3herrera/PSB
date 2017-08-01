package app.client.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * It contains utilities for the handling of data within the systems
 * </p>
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
public class TypesUtility {

	private TypesUtility() {
		// This method is private for avoid class initialization
		super();
	}

	public static boolean isEmptyObject(Object object) {
		return object == null;
	}

	/**
	 * <p>
	 * Converts from {@link Object} to {@link String}.
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2Str(Object obj) {
		if (isEmptyObject(obj)) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * <p>
	 * Converts from {@link Object} to primitive type of {@link Integer}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param obj
	 * @param defValue
	 * @return
	 */
	public static int obj2Int(Object obj, int defValue) {
		if (isEmptyObject(obj)) {
			return defValue;
		}
		if (obj instanceof Integer) {
			return str2Int(obj2Str(obj), defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link Object} to primitive type of {@link Byte}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param obj
	 * @param defValue
	 * @return
	 */
	public static byte obj2Byte(Object obj, byte defValue) {
		if (isEmptyObject(obj)) {
			return defValue;
		}
		if (obj instanceof Byte) {
			return str2Byte(obj2Str(obj), defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link Object} to primitive type of {@link Long}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param obj
	 * @param defValue
	 * @return
	 */
	public static long obj2Long(Object obj, long defValue) {
		if (isEmptyObject(obj)) {
			return defValue;
		}
		if (obj instanceof Long) {
			return str2Long(obj2Str(obj), defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link Object} to primitive type of {@link Double}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param obj
	 * @param defValue
	 * @return
	 */
	public static double obj2Double(Object obj, double defValue) {
		if (isEmptyObject(obj)) {
			return defValue;
		}
		if (obj instanceof Double) {
			return str2Double(obj2Str(obj), defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link Object} to primitive type of {@link Boolean}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param obj
	 * @param defValue
	 * @return
	 */
	public static boolean obj2Boolean(Object obj, boolean defValue) {
		if (isEmptyObject(obj)) {
			return defValue;
		}
		if (obj instanceof Double) {
			return str2Boolean(obj2Str(obj), defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Tries to convert from {@link Object} to {@link Date}.
	 * </p>
	 * 
	 * @param obj
	 * @return null if obj can't be converted.
	 */
	public static Date obj2Date(Object obj) {
		if (isEmptyObject(obj)) {
			return null;
		}
		if (obj instanceof Date) {
			return (Date) obj;
		}

		try {
			return new SimpleDateFormat().parse(obj.toString());
		} catch (ParseException e) {
			// Ignore...
		}

		return null;
	}

	public static boolean isEmptyString(String value) {
		return value == null || value.trim().equals("");
	}

	/**
	 * <p>
	 * Converts from {@link String} to primitive type of {@link Byte}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static byte str2Byte(String value, @NotNull byte defValue) {
		if (isEmptyString(value)) {
			return defValue;
		}
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ex) {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link String} to primitive type of {@link Integer}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static int str2Int(String value, @NotNull int defValue) {
		if (isEmptyString(value)) {
			return defValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link String} to primitive type of {@link Long}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static long str2Long(String value, @NotNull long defValue) {
		if (isEmptyString(value)) {
			return defValue;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link String} to primitive type of {@link Double}.
	 * </p>
	 * <p>
	 * If conversion fails returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static double str2Double(String value, @NotNull double defValue) {
		if (isEmptyString(value)) {
			return defValue;
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return defValue;
		}
	}

	/**
	 * <p>
	 * Converts from {@link String} to primitive type of {@link Boolean}.
	 * </p>
	 * <p>
	 * String is null or empty returns <code>defValue</code>.
	 * </p>
	 * 
	 * @param value
	 * @param defValue
	 * @return
	 */
	public static boolean str2Boolean(String value, @NotNull boolean defValue) {
		if (isEmptyString(value)) {
			return defValue;
		}
		return Boolean.parseBoolean(value);
	}

	/**
	 * <p>
	 * Tries to convert from {@link String} to {@link Date}.
	 * </p>
	 * 
	 * @param date
	 * @return null if {@value date}} can't be converted.
	 */
	public static Date str2Date(String date) {
		try {
			if (date.contains("/")) {
				return new SimpleDateFormat("dd/MM/yyyy").parse(date);
			} else {
				return new SimpleDateFormat("yyyyMMddhhmmss").parse(date);
			}
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * Compare two {@link String}'s and returns if they are equal or not after
	 * trimming them.
	 * </p>
	 * 
	 * @param value1
	 * @param value2
	 * @param ignoreCase
	 * @return
	 */
	public static boolean compareStrings(String value1, String value2, boolean ignoreCase) {
		if (isEmptyString(value1) && isEmptyString(value2)) {
			return true;
		}
		if ((isEmptyString(value1) && !isEmptyString(value2)) || (!isEmptyString(value1) && isEmptyString(value2))) {
			return false;
		}
		value1 = value1.trim();
		value2 = value2.trim();
		if (ignoreCase) {
			return value1.equalsIgnoreCase(value2);
		} else {
			return value1.equals(value2);
		}
	}

	/**
	 * <p>
	 * Compare two {@link String}'s and returns if they are equal or not after
	 * trimming them.
	 * </p>
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static boolean compareStrings(String value1, String value2) {
		return compareStrings(value1, value2, false);
	}

	/**
	 * <p>
	 * Concatenate a first {@link String} with a second {@String} if this
	 * seconds is not empty
	 * </p>
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String concatKey(String value1, String value2) {
		return value1.concat(isEmptyString(value2) ? "" : value2);
	}
}
