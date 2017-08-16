package app.client.enums;

/**
 * <p>
 * This file contains the Encription Types we can use in the system.
 * </p>
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
public enum EncryptionTypes {

	MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA256("SHA-256"), SHA384("SHA-384"), SHA512("SHA-512");

	EncryptionTypes(String type) {
		this.type = type;
	}

	private String type;

	public String getType() {
		return type;
	}

}
