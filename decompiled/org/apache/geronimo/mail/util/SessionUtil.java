/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.mail.util;

import javax.mail.Session;

public class SessionUtil {
    public static String getProperty(Session session, String name) {
        if (session == null) {
            return null;
        }
        return session.getProperty(name);
    }

    public static String getProperty(Session session, String name, String defaultValue) {
        String result = SessionUtil.getProperty(session, name);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    public static boolean isPropertyTrue(Session session, String name) {
        String property = SessionUtil.getProperty(session, name);
        if (property != null) {
            return property.equals("true");
        }
        return false;
    }

    public static boolean isPropertyFalse(Session session, String name) {
        String property = SessionUtil.getProperty(session, name);
        if (property != null) {
            return property.equals("false");
        }
        return false;
    }

    public static int getIntProperty(Session session, String name, int defaultValue) {
        String result = SessionUtil.getProperty(session, name);
        if (result != null) {
            try {
                return Integer.parseInt(result);
            }
            catch (NumberFormatException e) {
                // empty catch block
            }
        }
        return defaultValue;
    }

    public static boolean getBooleanProperty(Session session, String name, boolean defaultValue) {
        String result = SessionUtil.getProperty(session, name);
        if (result != null) {
            return Boolean.valueOf(result);
        }
        return defaultValue;
    }

    public static boolean getBooleanProperty(String name, boolean defaultValue) {
        try {
            String result = System.getProperty(name);
            if (result != null) {
                return Boolean.valueOf(result);
            }
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return defaultValue;
    }

    public static String getProperty(String name, String defaultValue) {
        try {
            String result = System.getProperty(name);
            if (result != null) {
                return result;
            }
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        return defaultValue;
    }

    public static String getProperty(String name) {
        try {
            return System.getProperty(name);
        }
        catch (SecurityException securityException) {
            return null;
        }
    }
}

