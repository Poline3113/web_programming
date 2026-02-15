package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashGenerator {
    private static final String algorithm = "SHA-256";

    public static byte[] hashPassword(char[] password) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] passwordBytes = new byte[password.length];
            for (int i = 0; i < password.length; i++) {
                passwordBytes[i] = (byte) password[i];
            }
            md.update(passwordBytes);
            Arrays.fill(password, '\0');
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unsupported algorithm: " + algorithm);
        }
    }

    public static String generate(String password) {
        byte[] hash = hashPassword(password.toCharArray());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) {
        String inputHash = generate(inputPassword);
        return inputHash.equals(storedHash);
    }
}