package app.client.utilities;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * <p>
 * Useful String utilities for common task
 * </p>
 * 
 * @author Angel Alfaro
 * @since 1.0
 */
public class StringUtility {

	private StringUtility() {
		// This constructor is private for avoid class initialization
		super();
	}

	private static final char[] _hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	/**
	 * <p>
	 * Return the length of a {@link String}}
	 * </p>
	 * 
	 * @param value
	 * @param useTrim
	 *            Indicates that if you are going to be using the
	 *            {@link String#trim()} method
	 * @return
	 */
	private static int length(final String value, final boolean useTrim) {
		return (value == null) ? 0 : (useTrim ? value.trim().length() : value.length());
	}

	/**
	 * <p>
	 * Return the length of a the trimmed {@link String}}
	 * </p>
	 * <p>
	 * If the {@value value} is null the it return 0
	 * </p>
	 * 
	 */
	public static int length(final String value) {
		return length(value, false);
	}

	/**
	 * <p>
	 * Return the length of a {@link String}}
	 * </p>
	 * <p>
	 * If the {@value value} is null the it return 0
	 * </p>
	 * 
	 */
	public static int lengthTrim(final String value) {
		return length(value, true);
	}

	/**
	 * <p>
	 * Indicate if a {@link String} is empty or null
	 * </p>
	 * 
	 */
	public static boolean isEmpty(final String value) {
		return lengthTrim(value) == 0;
	}

	/**
	 * /**
	 * <p>
	 * Compares two values of type {@link String} if they are equals
	 * </p>
	 * 
	 * @param ignoreCase
	 *            indicates if are check the Case of the two values
	 * @param useTrime
	 *            indicate if are use the {@link String#trim()} in the two
	 *            {@link String} values
	 * @return
	 */
	private static boolean equals(String value1, String value2, final boolean ignoreCase, final boolean useTrime) {
		if (isEmpty(value1) && isEmpty(value2)) {
			return true;
		}

		if ((isEmpty(value1) && !isEmpty(value2)) || (!isEmpty(value1) && isEmpty(value2))) {
			return true;
		}

		if (useTrime) {
			value1 = value1.trim();
			value2 = value2.trim();
		}

		if (ignoreCase) {
			return value1.equalsIgnoreCase(value2);
		} else {
			return value1.equals(value2);
		}
	}

	/**
	 * <p>
	 * Compare two values of type {@link String} if they are equals
	 * </p>
	 * <p>
	 * <ol>
	 * <li>This method not use ignoreCase</li>
	 * <li>This method not use the {@link String#trim()}</li>
	 * </ol>
	 * </p>
	 */
	public static boolean equals(final String value1, final String value2) {
		return equals(value1, value2, false, false);
	}

	/**
	 * <p>
	 * Compare two values of type {@link String} if they are equals
	 * </p>
	 * <p>
	 * <ol>
	 * <li>This method not use ignoreCase</li>
	 * <li>This method use the {@link String#trim()}</li>
	 * </ol>
	 * </p>
	 */
	public static boolean equalTrim(final String value1, final String value2) {
		return equals(value1, value2, false, true);
	}

	/**
	 * <p>
	 * Compare two values of type {@link String} if they are equals
	 * </p>
	 * <p>
	 * <ol>
	 * <li>This method use ignoreCase</li>
	 * <li>This method not use the {@link String#trim()}</li>
	 * </ol>
	 * </p>
	 */
	public static boolean equalsIgnoreCase(final String value1, final String value2) {
		return equals(value1, value2, true, false);
	}

	/**
	 * <p>
	 * Compare two values of type {@link String} if they are equals
	 * </p>
	 * <p>
	 * <ol>
	 * <li>This method use ignoreCase</li>
	 * <li>This method use the {@link String#trim()}</li>
	 * </ol>
	 * </p>
	 */
	public static boolean equalsIgnoreCaseTrim(final String value1, final String value2) {
		return equals(value1, value2, true, true);
	}

	/**
	 * <p>
	 * Turn hex {@link String} into {@link Byte}
	 * </p>
	 * <p>
	 * If the {@link String} is not even length, return null
	 * <p>
	 */
	public static byte[] decode(final String value) {
		final int length = value.length();
		if (length % 2 != 0) {
			return null;
		}

		byte[] bytes = new byte[length / 2];
		int mainPosition = 0;

		for (int position = 0; position < length; position += 2) {
			byte higth = (byte) Character.digit(value.charAt(position), 16);
			byte low = (byte) Character.digit(value.charAt(position + 1), 16);
			bytes[mainPosition++] = (byte) (higth * 16 + low);
		}

		return bytes;
	}

	/**
	 * Convert the specified value (0 .. 15) to the corresponding hex digit.
	 *
	 * @param value
	 *            to be converted
	 * @return '0'..'F' in char format.
	 */
	private static char convertDigit(int value) {
		return _hex[value & 0x0f];
	}

	/**
	 * <p>
	 * Convert a byte array into a printable format containing a {@link String}
	 * of hex digit characters (two per byte).
	 * </p>
	 */
	public static String encode(final byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length << 1);
		for (byte aByte : bytes) {
			sb.append(convertDigit(aByte >> 4));
			sb.append(convertDigit(aByte & 0x0f));
		}
		return sb.toString();
	}

	public static String getRandomString(Random random, final int minLength, final int maxLength) {
		StringBuilder builder = new StringBuilder();
		int length = minLength + random.nextInt(maxLength - minLength + 1);
		for (int i = 0; i < length; i++) {
			builder.append(getRandomChar(random, i == 0));
		}
		return builder.toString();
	}

	private static String getRandomChar(Random random, boolean upper) {
		int r = random.nextInt(26);
		return upper ? "" + (char) ((int) 'A' + r) : "" + (char) ((int) 'a' + r);
	}

	public static byte[] getBytes(String value, String encoding) {
		try {
			return value == null ? null : value.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(String.format("Encoding (%s) is not supported by your JVM", encoding),
					e);
		}
	}
}
