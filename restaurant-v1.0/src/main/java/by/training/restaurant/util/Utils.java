package by.training.restaurant.util;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String hash(char[] arr) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(new String(arr).getBytes(StandardCharsets.UTF_8));
            byte[] hash = md.digest();
            StringBuilder answ = new StringBuilder();
            for (byte b : hash) {
                answ.append(String.format("%02X", b));
            }
            return answ.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static String getErrMessage(Exception e) {
        return e + "; Caused by: " + e.getCause().toString();
    }

    public static void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/catalog");
    }
}
