package utils;

import java.security.SecureRandom;

/**
 * @author Nathan Salez
 */
public class SecurityUtils {

    private static final int SECURITY_LEVEL = 20;

    public static String generateToken()
    {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[SECURITY_LEVEL];
        random.nextBytes(bytes);
        return bytes.toString();
    }
}
