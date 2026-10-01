package com.ecommerce.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    private static final int BCRYPT_WORK_FACTOR = 12;
    private PasswordUtil() {}
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty");
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_WORK_FACTOR));
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) return false;
        try { return BCrypt.checkpw(plainPassword, hashedPassword); }
        catch (IllegalArgumentException e) { return false; }
    }
}
