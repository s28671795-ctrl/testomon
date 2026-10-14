/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.io.Hex;

public final class LdapUtils {
    private static final int READ_BUFFER_SIZE = 128;
    private static final int HASH_CODE_PRIME = 113;
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    private static final Pattern CNTRL_PATTERN = Pattern.compile("\\p{Cntrl}");

    private LdapUtils() {
    }

    public static String base64Encode(byte ... value) {
        return value != null ? new String(Base64.getEncoder().encode(value), StandardCharsets.UTF_8) : null;
    }

    public static String base64Encode(String value) {
        return value != null ? LdapUtils.base64Encode(value.getBytes(StandardCharsets.UTF_8)) : null;
    }

    public static String utf8Encode(byte[] value) {
        return LdapUtils.utf8Encode(value, true);
    }

    public static String utf8Encode(byte[] value, boolean allowNull) {
        if (!allowNull && value == null) {
            throw new NullPointerException("Cannot UTF-8 encode null value");
        }
        return value != null ? new String(value, StandardCharsets.UTF_8) : null;
    }

    public static byte[] utf8Encode(String value) {
        return LdapUtils.utf8Encode(value, true);
    }

    public static byte[] utf8Encode(String value, boolean allowNull) {
        if (!allowNull && value == null) {
            throw new NullPointerException("Cannot UTF-8 encode null value");
        }
        return value != null ? value.getBytes(StandardCharsets.UTF_8) : null;
    }

    public static char[] hexEncode(byte ... value) {
        return value != null ? Hex.encode(value) : null;
    }

    public static char[] hexEncode(char ... value) {
        return value != null ? LdapUtils.hexEncode(LdapUtils.utf8Encode(String.valueOf(value))) : null;
    }

    public static String percentEncode(String value) {
        if (value == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        block3: for (int i = 0; i < value.length(); ++i) {
            char ch = value.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                sb.append(ch);
                continue;
            }
            if (ch >= 'a' && ch <= 'z') {
                sb.append(ch);
                continue;
            }
            if (ch >= '0' && ch <= '9') {
                sb.append(ch);
                continue;
            }
            switch (ch) {
                case '!': 
                case '$': 
                case '&': 
                case '\'': 
                case '(': 
                case ')': 
                case '*': 
                case '+': 
                case ',': 
                case '-': 
                case '.': 
                case ';': 
                case '=': 
                case '_': 
                case '~': {
                    sb.append(ch);
                    continue block3;
                }
                default: {
                    sb.append("%");
                    if (ch <= '\u007f') {
                        sb.append(LdapUtils.hexEncode((byte)(ch & 0x7F)));
                        continue block3;
                    }
                    sb.append(LdapUtils.hexEncode(LdapUtils.utf8Encode(String.valueOf(ch))));
                }
            }
        }
        return sb.toString();
    }

    public static String percentEncodeControlChars(String value) {
        Matcher m4;
        if (value != null && (m4 = CNTRL_PATTERN.matcher(value)).find()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < value.length(); ++i) {
                char ch = value.charAt(i);
                if (ch <= '\u001f' || ch == '\u007f') {
                    sb.append("%");
                    sb.append(LdapUtils.hexEncode((byte)(ch & 0x7F)));
                    continue;
                }
                sb.append(ch);
            }
            return sb.toString();
        }
        return value;
    }

    public static byte[] base64Decode(String value) {
        try {
            return value != null ? Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)) : null;
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error decoding value: " + value, e);
        }
    }

    public static byte[] hexDecode(char[] value) {
        return value != null ? Hex.decode(value) : null;
    }

    public static String percentDecode(String value) {
        if (value == null || !value.contains("%")) {
            return value;
        }
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        while (pos < value.length()) {
            char c;
            if ((c = value.charAt(pos++)) == '%') {
                char[] hex = new char[]{value.charAt(pos++), value.charAt(pos++)};
                sb.append(LdapUtils.utf8Encode(LdapUtils.hexDecode(hex)));
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static boolean shouldBase64Encode(String value) {
        return LdapUtils.shouldBase64Encode(value.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean shouldBase64Encode(byte[] value) {
        if (value == null || value.length == 0) {
            return false;
        }
        boolean encode = false;
        switch (value[0] & 0xFF) {
            case 32: 
            case 58: 
            case 60: {
                encode = true;
                break;
            }
        }
        if (!encode) {
            if ((value[value.length - 1] & 0xFF) == 32) {
                encode = true;
            } else {
                for (byte b : value) {
                    switch (b & 0xFF) {
                        case 0: 
                        case 10: 
                        case 13: {
                            encode = true;
                            break;
                        }
                        default: {
                            if ((b & 0x80) == 0) break;
                            encode = true;
                        }
                    }
                    if (encode) break;
                }
            }
        }
        return encode;
    }

    public static byte[] readInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try (InputStream inputStream2 = is;
             ByteArrayOutputStream byteArrayOutputStream = data;){
            int length;
            byte[] buffer = new byte[128];
            while ((length = is.read(buffer)) != -1) {
                data.write(buffer, 0, length);
            }
        }
        return data.toByteArray();
    }

    public static <T> T[] concatArrays(T[] first, T[] ... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            if (array == null) continue;
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            if (array == null) continue;
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static boolean areEqual(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        boolean areEqual = o1 instanceof boolean[] && o2 instanceof boolean[] ? Arrays.equals((boolean[])o1, (boolean[])o2) : (o1 instanceof byte[] && o2 instanceof byte[] ? Arrays.equals((byte[])o1, (byte[])o2) : (o1 instanceof char[] && o2 instanceof char[] ? Arrays.equals((char[])o1, (char[])o2) : (o1 instanceof double[] && o2 instanceof double[] ? Arrays.equals((double[])o1, (double[])o2) : (o1 instanceof float[] && o2 instanceof float[] ? Arrays.equals((float[])o1, (float[])o2) : (o1 instanceof int[] && o2 instanceof int[] ? Arrays.equals((int[])o1, (int[])o2) : (o1 instanceof long[] && o2 instanceof long[] ? Arrays.equals((long[])o1, (long[])o2) : (o1 instanceof short[] && o2 instanceof short[] ? Arrays.equals((short[])o1, (short[])o2) : (o1 instanceof Object[] && o2 instanceof Object[] ? Arrays.deepEquals((Object[])o1, (Object[])o2) : o1 != null && o1.equals(o2)))))))));
        return areEqual;
    }

    public static int computeHashCode(int seed, Object ... objects) {
        if (objects == null || objects.length == 0) {
            return seed * 113;
        }
        int hc = seed;
        for (Object object : objects) {
            hc = 113 * hc;
            if (object == null) continue;
            if (object instanceof List || object instanceof Queue) {
                int index = 1;
                for (Object o : (Collection)object) {
                    hc += LdapUtils.computeHashCode(o) * index++;
                }
                continue;
            }
            if (object instanceof Collection) {
                for (Object o : (Collection)object) {
                    hc += LdapUtils.computeHashCode(o);
                }
                continue;
            }
            hc += LdapUtils.computeHashCode(object);
        }
        return hc;
    }

    private static int computeHashCode(Object object) {
        int hc = 0;
        hc = object instanceof boolean[] ? (hc += Arrays.hashCode((boolean[])object)) : (object instanceof byte[] ? (hc += Arrays.hashCode((byte[])object)) : (object instanceof char[] ? (hc += Arrays.hashCode((char[])object)) : (object instanceof double[] ? (hc += Arrays.hashCode((double[])object)) : (object instanceof float[] ? (hc += Arrays.hashCode((float[])object)) : (object instanceof int[] ? (hc += Arrays.hashCode((int[])object)) : (object instanceof long[] ? (hc += Arrays.hashCode((long[])object)) : (object instanceof short[] ? (hc += Arrays.hashCode((short[])object)) : (object instanceof Object[] ? (hc += Arrays.deepHashCode((Object[])object)) : (hc += object.hashCode())))))))));
        return hc;
    }

    public static boolean isIPAddress(String s2) {
        return s2 != null && (IPV4_PATTERN.matcher(s2).matches() || IPV6_STD_PATTERN.matcher(s2).matches() || IPV6_HEX_COMPRESSED_PATTERN.matcher(s2).matches());
    }

    public static Constructor<?> createConstructorFromProperty(String property) {
        String clazz = System.getProperty(property);
        if (clazz != null) {
            try {
                return Class.forName(clazz).getDeclaredConstructor(new Class[0]);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Error getting declared constructor for " + clazz, e);
            }
        }
        return null;
    }
}

