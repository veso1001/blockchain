package imbachain.utils;

import java.security.MessageDigest;

import imbachain.infrastructure.UnconfirmedBlock;

public class CryptoUtils {
	public static String toSha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			// Thats synchronized so we shouldn't have problems using it in
			// static method (I hope)
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) { // Make it uncatched for easier coding ;)
			throw new RuntimeException(e);
		}
	}

	public static String getBlockHash(UnconfirmedBlock block){
        return toSha256(block.toString());
	}
}
