/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.h2.jdbc.JdbcException;
import org.h2.mvstore.MVStoreException;
import org.h2.util.StringUtils;

public final class DataUtils {
    public static final int ERROR_READING_FAILED = 1;
    public static final int ERROR_WRITING_FAILED = 2;
    public static final int ERROR_INTERNAL = 3;
    public static final int ERROR_CLOSED = 4;
    public static final int ERROR_UNSUPPORTED_FORMAT = 5;
    public static final int ERROR_FILE_CORRUPT = 6;
    public static final int ERROR_FILE_LOCKED = 7;
    public static final int ERROR_SERIALIZATION = 8;
    public static final int ERROR_CHUNK_NOT_FOUND = 9;
    public static final int ERROR_BLOCK_NOT_FOUND = 50;
    public static final int ERROR_TRANSACTION_CORRUPT = 100;
    public static final int ERROR_TRANSACTION_LOCKED = 101;
    public static final int ERROR_TOO_MANY_OPEN_TRANSACTIONS = 102;
    public static final int ERROR_TRANSACTION_ILLEGAL_STATE = 103;
    public static final int ERROR_TRANSACTION_TOO_BIG = 104;
    public static final int ERROR_TRANSACTIONS_DEADLOCK = 105;
    public static final int ERROR_UNKNOWN_DATA_TYPE = 106;
    public static final int PAGE_TYPE_LEAF = 0;
    public static final int PAGE_TYPE_NODE = 1;
    public static final int PAGE_COMPRESSED = 2;
    public static final int PAGE_COMPRESSED_HIGH = 6;
    public static final int COMPRESSED_VAR_INT_MAX = 0x1FFFFF;
    public static final long COMPRESSED_VAR_LONG_MAX = 0x1FFFFFFFFFFFFL;
    public static final int PAGE_LARGE = 0x200000;
    public static final String META_CHUNK = "chunk.";
    public static final String META_ROOT = "root.";
    public static final String META_NAME = "name.";
    public static final String META_MAP = "map.";

    public static int getVarIntLen(int n) {
        if ((n & 0xFFFFFF80) == 0) {
            return 1;
        }
        if ((n & 0xFFFFC000) == 0) {
            return 2;
        }
        if ((n & 0xFFE00000) == 0) {
            return 3;
        }
        if ((n & 0xF0000000) == 0) {
            return 4;
        }
        return 5;
    }

    public static int getVarLongLen(long l) {
        int n = 1;
        while ((l >>>= 7) != 0L) {
            ++n;
        }
        return n;
    }

    public static int readVarInt(ByteBuffer byteBuffer) {
        byte by = byteBuffer.get();
        if (by >= 0) {
            return by;
        }
        return DataUtils.readVarIntRest(byteBuffer, by);
    }

    private static int readVarIntRest(ByteBuffer byteBuffer, int n) {
        int n2 = n & 0x7F;
        n = byteBuffer.get();
        if (n >= 0) {
            return n2 | n << 7;
        }
        n2 |= (n & 0x7F) << 7;
        n = byteBuffer.get();
        if (n >= 0) {
            return n2 | n << 14;
        }
        n2 |= (n & 0x7F) << 14;
        n = byteBuffer.get();
        if (n >= 0) {
            return n2 | n << 21;
        }
        return n2 |= (n & 0x7F) << 21 | byteBuffer.get() << 28;
    }

    public static long readVarLong(ByteBuffer byteBuffer) {
        long l = byteBuffer.get();
        if (l >= 0L) {
            return l;
        }
        l &= 0x7FL;
        for (int i = 7; i < 64; i += 7) {
            long l2 = byteBuffer.get();
            l |= (l2 & 0x7FL) << i;
            if (l2 >= 0L) break;
        }
        return l;
    }

    public static void writeVarInt(OutputStream outputStream2, int n) throws IOException {
        while ((n & 0xFFFFFF80) != 0) {
            outputStream2.write((byte)(n | 0x80));
            n >>>= 7;
        }
        outputStream2.write((byte)n);
    }

    public static void writeVarInt(ByteBuffer byteBuffer, int n) {
        while ((n & 0xFFFFFF80) != 0) {
            byteBuffer.put((byte)(n | 0x80));
            n >>>= 7;
        }
        byteBuffer.put((byte)n);
    }

    public static void writeStringData(ByteBuffer byteBuffer, String string, int n) {
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c < '\u0080') {
                byteBuffer.put((byte)c);
                continue;
            }
            if (c >= '\u0800') {
                byteBuffer.put((byte)(0xE0 | c >> 12));
                byteBuffer.put((byte)(c >> 6 & 0x3F));
                byteBuffer.put((byte)(c & 0x3F));
                continue;
            }
            byteBuffer.put((byte)(0xC0 | c >> 6));
            byteBuffer.put((byte)(c & 0x3F));
        }
    }

    public static String readString(ByteBuffer byteBuffer) {
        return DataUtils.readString(byteBuffer, DataUtils.readVarInt(byteBuffer));
    }

    public static String readString(ByteBuffer byteBuffer, int n) {
        char[] cArray = new char[n];
        for (int i = 0; i < n; ++i) {
            int n2 = byteBuffer.get() & 0xFF;
            cArray[i] = n2 < 128 ? (char)n2 : (n2 >= 224 ? (char)(((n2 & 0xF) << 12) + ((byteBuffer.get() & 0x3F) << 6) + (byteBuffer.get() & 0x3F)) : (char)(((n2 & 0x1F) << 6) + (byteBuffer.get() & 0x3F)));
        }
        return new String(cArray);
    }

    public static void writeVarLong(ByteBuffer byteBuffer, long l) {
        while ((l & 0xFFFFFFFFFFFFFF80L) != 0L) {
            byteBuffer.put((byte)(l | 0x80L));
            l >>>= 7;
        }
        byteBuffer.put((byte)l);
    }

    public static void writeVarLong(OutputStream outputStream2, long l) throws IOException {
        while ((l & 0xFFFFFFFFFFFFFF80L) != 0L) {
            outputStream2.write((byte)(l | 0x80L));
            l >>>= 7;
        }
        outputStream2.write((byte)l);
    }

    public static void copyWithGap(Object object, Object object2, int n, int n2) {
        if (n2 > 0) {
            System.arraycopy(object, 0, object2, 0, n2);
        }
        if (n2 < n) {
            System.arraycopy(object, n2, object2, n2 + 1, n - n2);
        }
    }

    public static void copyExcept(Object object, Object object2, int n, int n2) {
        if (n2 > 0 && n > 0) {
            System.arraycopy(object, 0, object2, 0, n2);
        }
        if (n2 < n) {
            System.arraycopy(object, n2 + 1, object2, n2, n - n2 - 1);
        }
    }

    public static void readFully(FileChannel fileChannel, long l, ByteBuffer byteBuffer) {
        try {
            do {
                int n;
                if ((n = fileChannel.read(byteBuffer, l)) < 0) {
                    throw new EOFException();
                }
                l += (long)n;
            } while (byteBuffer.remaining() > 0);
            byteBuffer.rewind();
        }
        catch (IOException iOException) {
            long l2;
            try {
                l2 = fileChannel.size();
            }
            catch (IOException iOException2) {
                l2 = -1L;
            }
            throw DataUtils.newMVStoreException(1, "Reading from file {0} failed at {1} (length {2}), read {3}, remaining {4}", fileChannel, l, l2, byteBuffer.position(), byteBuffer.remaining(), iOException);
        }
    }

    public static void writeFully(FileChannel fileChannel, long l, ByteBuffer byteBuffer) {
        try {
            int n = 0;
            do {
                int n2 = fileChannel.write(byteBuffer, l + (long)n);
                n += n2;
            } while (byteBuffer.remaining() > 0);
        }
        catch (IOException iOException) {
            throw DataUtils.newMVStoreException(2, "Writing to {0} failed; length {1} at {2}", fileChannel, byteBuffer.remaining(), l, iOException);
        }
    }

    public static int encodeLength(int n) {
        assert (n >= 0);
        if (n <= 32) {
            return 0;
        }
        int n2 = Integer.numberOfLeadingZeros(n);
        int n3 = n << n2 + 1;
        n2 += n2;
        if ((n3 & Integer.MIN_VALUE) != 0) {
            --n2;
        }
        if (n3 << 1 != 0) {
            --n2;
        }
        n2 = Math.min(31, 52 - n2);
        return n2;
    }

    public static int getPageChunkId(long l) {
        return (int)(l >>> 38);
    }

    public static int getPageMapId(long l) {
        return (int)(l >>> 38);
    }

    public static int getPageMaxLength(long l) {
        int n = (int)(l >> 1 & 0x1FL);
        return DataUtils.decodePageLength(n);
    }

    public static int decodePageLength(int n) {
        if (n == 31) {
            return 0x200000;
        }
        return 2 + (n & 1) << (n >> 1) + 4;
    }

    public static int getPageOffset(long l) {
        return (int)(l >> 6);
    }

    public static int getPageType(long l) {
        return (int)l & 1;
    }

    public static boolean isLeafPosition(long l) {
        return DataUtils.getPageType(l) == 0;
    }

    public static boolean isPageSaved(long l) {
        return (l & 0xFFFFFFFFFFFFFFFEL) != 0L;
    }

    static boolean isPageRemoved(long l) {
        return l == 1L;
    }

    public static long composePagePos(int n, int n2, int n3, int n4) {
        assert (n2 >= 0);
        assert (n4 == 0 || n4 == 1);
        long l = (long)n << 38;
        l |= (long)n2 << 6;
        l |= (long)DataUtils.encodeLength(n3) << 1;
        return l |= (long)n4;
    }

    public static long composePagePos(int n, long l) {
        return l & 0x3FFFFFFFFFL | (long)n << 38;
    }

    public static long composeTocElement(int n, int n2, int n3, int n4) {
        assert (n >= 0);
        assert (n2 >= 0);
        assert (n4 == 0 || n4 == 1);
        long l = (long)n << 38;
        l |= (long)n2 << 6;
        l |= (long)DataUtils.encodeLength(n3) << 1;
        return l |= (long)n4;
    }

    public static short getCheckValue(int n) {
        return (short)(n >> 16 ^ n);
    }

    public static StringBuilder appendMap(StringBuilder stringBuilder, HashMap<String, ?> hashMap) {
        Object[] objectArray = hashMap.keySet().toArray();
        Arrays.sort(objectArray);
        for (Object object : objectArray) {
            String string = (String)object;
            Object obj = hashMap.get(string);
            if (obj instanceof Long) {
                DataUtils.appendMap(stringBuilder, string, (Long)obj);
                continue;
            }
            if (obj instanceof Integer) {
                DataUtils.appendMap(stringBuilder, string, (Integer)obj);
                continue;
            }
            DataUtils.appendMap(stringBuilder, string, obj.toString());
        }
        return stringBuilder;
    }

    private static StringBuilder appendMapKey(StringBuilder stringBuilder, String string) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(',');
        }
        return stringBuilder.append(string).append(':');
    }

    public static void appendMap(StringBuilder stringBuilder, String string, String string2) {
        DataUtils.appendMapKey(stringBuilder, string);
        if (string2.indexOf(44) < 0 && string2.indexOf(34) < 0) {
            stringBuilder.append(string2);
        } else {
            stringBuilder.append('\"');
            int n = string2.length();
            for (int i = 0; i < n; ++i) {
                char c = string2.charAt(i);
                if (c == '\"') {
                    stringBuilder.append('\\');
                }
                stringBuilder.append(c);
            }
            stringBuilder.append('\"');
        }
    }

    public static void appendMap(StringBuilder stringBuilder, String string, long l) {
        DataUtils.appendMapKey(stringBuilder, string).append(Long.toHexString(l));
    }

    public static void appendMap(StringBuilder stringBuilder, String string, int n) {
        DataUtils.appendMapKey(stringBuilder, string).append(Integer.toHexString(n));
    }

    private static int parseMapValue(StringBuilder stringBuilder, String string, int n, int n2) {
        char c;
        block0: while (n < n2 && (c = string.charAt(n++)) != ',') {
            if (c == '\"') {
                while (n < n2) {
                    if ((c = string.charAt(n++)) == '\\') {
                        if (n == n2) {
                            throw DataUtils.newMVStoreException(6, "Not a map: {0}", string);
                        }
                        c = string.charAt(n++);
                    } else if (c == '\"') continue block0;
                    stringBuilder.append(c);
                }
                continue;
            }
            stringBuilder.append(c);
        }
        return n;
    }

    public static HashMap<String, String> parseMap(String string) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        int n2 = string.length();
        while (n < n2) {
            int n3 = n;
            if ((n = string.indexOf(58, n)) < 0) {
                throw DataUtils.newMVStoreException(6, "Not a map: {0}", string);
            }
            String string2 = string.substring(n3, n++);
            n = DataUtils.parseMapValue(stringBuilder, string, n, n2);
            hashMap.put(string2, stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return hashMap;
    }

    static HashMap<String, String> parseChecksummedMap(byte[] byArray) {
        int n;
        int n2 = byArray.length;
        for (n = 0; n < n2 && byArray[n] <= 32; ++n) {
        }
        while (n < n2 && byArray[n2 - 1] <= 32) {
            --n2;
        }
        String string = new String(byArray, n, n2 - n, StandardCharsets.ISO_8859_1);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        StringBuilder stringBuilder = new StringBuilder();
        int n3 = 0;
        int n4 = string.length();
        while (n3 < n4) {
            int n5 = n3;
            if ((n3 = string.indexOf(58, n3)) < 0) {
                return null;
            }
            if (n3 - n5 == 8 && string.regionMatches(n5, "fletcher", 0, 8)) {
                DataUtils.parseMapValue(stringBuilder, string, n3 + 1, n4);
                int n6 = (int)Long.parseLong(stringBuilder.toString(), 16);
                if (n6 == DataUtils.getFletcher32(byArray, n, n5 - 1)) {
                    return hashMap;
                }
                return null;
            }
            String string2 = string.substring(n5, n3++);
            n3 = DataUtils.parseMapValue(stringBuilder, string, n3, n4);
            hashMap.put(string2, stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return null;
    }

    public static String getMapName(String string) {
        return DataUtils.getFromMap(string, "name");
    }

    public static String getFromMap(String string, String string2) {
        int n = string2.length();
        int n2 = 0;
        int n3 = string.length();
        while (n2 < n3) {
            char c;
            int n4 = n2;
            if ((n2 = string.indexOf(58, n2)) < 0) {
                throw DataUtils.newMVStoreException(6, "Not a map: {0}", string);
            }
            if (n2++ - n4 == n && string.regionMatches(n4, string2, 0, n)) {
                StringBuilder stringBuilder = new StringBuilder();
                DataUtils.parseMapValue(stringBuilder, string, n2, n3);
                return stringBuilder.toString();
            }
            block1: while (n2 < n3 && (c = string.charAt(n2++)) != ',') {
                if (c != '\"') continue;
                while (n2 < n3) {
                    if ((c = string.charAt(n2++)) == '\\') {
                        if (n2++ != n3) continue;
                        throw DataUtils.newMVStoreException(6, "Not a map: {0}", string);
                    }
                    if (c != '\"') continue;
                    continue block1;
                }
            }
        }
        return null;
    }

    public static int getFletcher32(byte[] byArray, int n, int n2) {
        int n3;
        int n4 = 65535;
        int n5 = 65535;
        int n6 = n;
        int n7 = n + (n2 & 0xFFFFFFFE);
        while (n6 < n7) {
            n3 = Math.min(n6 + 720, n7);
            while (n6 < n3) {
                int n8 = (byArray[n6++] & 0xFF) << 8 | byArray[n6++] & 0xFF;
                n5 += (n4 += n8);
            }
            n4 = (n4 & 0xFFFF) + (n4 >>> 16);
            n5 = (n5 & 0xFFFF) + (n5 >>> 16);
        }
        if ((n2 & 1) != 0) {
            n3 = (byArray[n6] & 0xFF) << 8;
            n5 += (n4 += n3);
        }
        n4 = (n4 & 0xFFFF) + (n4 >>> 16);
        n5 = (n5 & 0xFFFF) + (n5 >>> 16);
        return n5 << 16 | n4;
    }

    public static void checkArgument(boolean bl, String string, Object ... objectArray) {
        if (!bl) {
            throw DataUtils.newIllegalArgumentException(string, objectArray);
        }
    }

    public static IllegalArgumentException newIllegalArgumentException(String string, Object ... objectArray) {
        return DataUtils.initCause(new IllegalArgumentException(DataUtils.formatMessage(0, string, objectArray)), objectArray);
    }

    public static UnsupportedOperationException newUnsupportedOperationException(String string) {
        return new UnsupportedOperationException(DataUtils.formatMessage(0, string, new Object[0]));
    }

    public static MVStoreException newMVStoreException(int n, String string, Object ... objectArray) {
        return DataUtils.initCause(new MVStoreException(n, DataUtils.formatMessage(n, string, objectArray)), objectArray);
    }

    private static <T extends Exception> T initCause(T t2, Object ... objectArray) {
        Object object;
        int n = objectArray.length;
        if (n > 0 && (object = objectArray[n - 1]) instanceof Throwable) {
            t2.initCause((Throwable)object);
        }
        return t2;
    }

    public static String formatMessage(int n, String string, Object ... objectArray) {
        objectArray = (Object[])objectArray.clone();
        for (int i = 0; i < objectArray.length; ++i) {
            String string2;
            Object object = objectArray[i];
            if (object instanceof Exception) continue;
            String string3 = string2 = object == null ? "null" : object.toString();
            if (string2.length() > 1000) {
                string2 = string2.substring(0, 1000) + "...";
            }
            objectArray[i] = string2;
        }
        return MessageFormat.format(string, objectArray) + " [" + 2 + "." + 2 + "." + 224 + "/" + n + "]";
    }

    public static long readHexLong(Map<String, ?> map, String string, long l) {
        Object obj = map.get(string);
        if (obj == null) {
            return l;
        }
        if (obj instanceof Long) {
            return (Long)obj;
        }
        try {
            return DataUtils.parseHexLong((String)obj);
        }
        catch (NumberFormatException numberFormatException) {
            throw DataUtils.newMVStoreException(6, "Error parsing the value {0}", obj, numberFormatException);
        }
    }

    public static long parseHexLong(String string) {
        try {
            if (string.length() == 16) {
                return Long.parseLong(string.substring(0, 8), 16) << 32 | Long.parseLong(string.substring(8, 16), 16);
            }
            return Long.parseLong(string, 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw DataUtils.newMVStoreException(6, "Error parsing the value {0}", string, numberFormatException);
        }
    }

    public static int parseHexInt(String string) {
        try {
            return (int)Long.parseLong(string, 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw DataUtils.newMVStoreException(6, "Error parsing the value {0}", string, numberFormatException);
        }
    }

    static int readHexInt(Map<String, ?> map, String string, int n) {
        Object obj = map.get(string);
        if (obj == null) {
            return n;
        }
        if (obj instanceof Integer) {
            return (Integer)obj;
        }
        try {
            return (int)Long.parseLong((String)obj, 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw DataUtils.newMVStoreException(6, "Error parsing the value {0}", obj, numberFormatException);
        }
    }

    static byte[] parseHexBytes(Map<String, ?> map, String string) {
        Object obj = map.get(string);
        if (obj == null) {
            return null;
        }
        return StringUtils.convertHexToBytes((String)obj);
    }

    static int getConfigParam(Map<String, ?> map, String string, int n) {
        Object obj = map.get(string);
        if (obj instanceof Number) {
            return ((Number)obj).intValue();
        }
        if (obj != null) {
            try {
                return Integer.decode(obj.toString());
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return n;
    }

    public static IOException convertToIOException(Throwable throwable) {
        if (throwable instanceof IOException) {
            return (IOException)throwable;
        }
        if (throwable instanceof JdbcException && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return new IOException(throwable.toString(), throwable);
    }
}

