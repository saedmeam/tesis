package net.macrosigma.util.ent;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptacion {
		public static String toMD5(String basic) {
			MessageDigest md;
			String hashtext = null;
			try {
				md = MessageDigest.getInstance("MD5");
				md.reset();
				md.update(basic.getBytes());
				byte[] digest = md.digest();
				BigInteger bigInt = new BigInteger(1, digest);
				hashtext = bigInt.toString(16);
				while (hashtext.length() < 32) {
					hashtext = "0" + hashtext;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return hashtext;
		}

		public static void main(String[] args) {
			System.out.println(toMD5("admin"));
		}
}
