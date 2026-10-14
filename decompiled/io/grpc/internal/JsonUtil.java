/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.math.LongMath;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class JsonUtil {
    private static final long DURATION_SECONDS_MIN = -315576000000L;
    private static final long DURATION_SECONDS_MAX = 315576000000L;
    private static final long NANOS_PER_SECOND = TimeUnit.SECONDS.toNanos(1L);

    @Nullable
    public static List<?> getList(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (!(value instanceof List)) {
            throw new ClassCastException(String.format("value '%s' for key '%s' in '%s' is not List", value, key, obj));
        }
        return (List)value;
    }

    @Nullable
    public static List<Map<String, ?>> getListOfObjects(Map<String, ?> obj, String key) {
        List<?> list = JsonUtil.getList(obj, key);
        if (list == null) {
            return null;
        }
        return JsonUtil.checkObjectList(list);
    }

    @Nullable
    public static List<String> getListOfStrings(Map<String, ?> obj, String key) {
        List<?> list = JsonUtil.getList(obj, key);
        if (list == null) {
            return null;
        }
        return JsonUtil.checkStringList(list);
    }

    @Nullable
    public static Map<String, ?> getObject(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (!(value instanceof Map)) {
            throw new ClassCastException(String.format("value '%s' for key '%s' in '%s' is not object", value, key, obj));
        }
        return (Map)value;
    }

    @Nullable
    public static Double getNumberAsDouble(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (value instanceof Double) {
            return (Double)value;
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String)value);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("value '%s' for key '%s' is not a double", value, key));
            }
        }
        throw new IllegalArgumentException(String.format("value '%s' for key '%s' in '%s' is not a number", value, key, obj));
    }

    @Nullable
    public static Float getNumberAsFloat(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (value instanceof Float) {
            return (Float)value;
        }
        if (value instanceof String) {
            try {
                return Float.valueOf(Float.parseFloat((String)value));
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("string value '%s' for key '%s' cannot be parsed as a float", value, key));
            }
        }
        throw new IllegalArgumentException(String.format("value %s for key '%s' is not a float", value, key));
    }

    @Nullable
    public static Integer getNumberAsInteger(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (value instanceof Double) {
            Double d = (Double)value;
            int i = d.intValue();
            if ((double)i != d) {
                throw new ClassCastException("Number expected to be integer: " + d);
            }
            return i;
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String)value);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("value '%s' for key '%s' is not an integer", value, key));
            }
        }
        throw new IllegalArgumentException(String.format("value '%s' for key '%s' is not an integer", value, key));
    }

    public static Long getNumberAsLong(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (value instanceof Double) {
            Double d = (Double)value;
            long l = d.longValue();
            if ((double)l != d) {
                throw new ClassCastException("Number expected to be long: " + d);
            }
            return l;
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String)value);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("value '%s' for key '%s' is not a long integer", value, key));
            }
        }
        throw new IllegalArgumentException(String.format("value '%s' for key '%s' is not a long integer", value, key));
    }

    @Nullable
    public static String getString(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (!(value instanceof String)) {
            throw new ClassCastException(String.format("value '%s' for key '%s' in '%s' is not String", value, key, obj));
        }
        return (String)value;
    }

    public static Long getStringAsDuration(Map<String, ?> obj, String key) {
        String value = JsonUtil.getString(obj, key);
        if (value == null) {
            return null;
        }
        try {
            return JsonUtil.parseDuration(value);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static Boolean getBoolean(Map<String, ?> obj, String key) {
        assert (key != null);
        if (!obj.containsKey(key)) {
            return null;
        }
        Object value = obj.get(key);
        if (!(value instanceof Boolean)) {
            throw new ClassCastException(String.format("value '%s' for key '%s' in '%s' is not Boolean", value, key, obj));
        }
        return (Boolean)value;
    }

    public static List<Map<String, ?>> checkObjectList(List<?> rawList) {
        for (int i = 0; i < rawList.size(); ++i) {
            if (rawList.get(i) instanceof Map) continue;
            throw new ClassCastException(String.format(Locale.US, "value %s for idx %d in %s is not object", rawList.get(i), i, rawList));
        }
        return rawList;
    }

    public static List<String> checkStringList(List<?> rawList) {
        for (int i = 0; i < rawList.size(); ++i) {
            if (rawList.get(i) instanceof String) continue;
            throw new ClassCastException(String.format(Locale.US, "value '%s' for idx %d in '%s' is not string", rawList.get(i), i, rawList));
        }
        return rawList;
    }

    private static long parseDuration(String value) throws ParseException {
        int nanos;
        if (value.isEmpty() || value.charAt(value.length() - 1) != 's') {
            throw new ParseException("Invalid duration string: " + value, 0);
        }
        boolean negative = false;
        if (value.charAt(0) == '-') {
            negative = true;
            value = value.substring(1);
        }
        String secondValue = value.substring(0, value.length() - 1);
        String nanoValue = "";
        int pointPosition = secondValue.indexOf(46);
        if (pointPosition != -1) {
            nanoValue = secondValue.substring(pointPosition + 1);
            secondValue = secondValue.substring(0, pointPosition);
        }
        long seconds = Long.parseLong(secondValue);
        int n = nanos = nanoValue.isEmpty() ? 0 : JsonUtil.parseNanos(nanoValue);
        if (seconds < 0L) {
            throw new ParseException("Invalid duration string: " + value, 0);
        }
        if (negative) {
            seconds = -seconds;
            nanos = -nanos;
        }
        try {
            return JsonUtil.normalizedDuration(seconds, nanos);
        }
        catch (IllegalArgumentException e) {
            throw new ParseException("Duration value is out of range.", 0);
        }
    }

    private static int parseNanos(String value) throws ParseException {
        int result = 0;
        for (int i = 0; i < 9; ++i) {
            result *= 10;
            if (i >= value.length()) continue;
            if (value.charAt(i) < '0' || value.charAt(i) > '9') {
                throw new ParseException("Invalid nanoseconds.", 0);
            }
            result += value.charAt(i) - 48;
        }
        return result;
    }

    private static long normalizedDuration(long seconds, int nanos) {
        if ((long)nanos <= -NANOS_PER_SECOND || (long)nanos >= NANOS_PER_SECOND) {
            seconds = LongMath.checkedAdd(seconds, (long)nanos / NANOS_PER_SECOND);
            nanos = (int)((long)nanos % NANOS_PER_SECOND);
        }
        if (seconds > 0L && nanos < 0) {
            nanos = (int)((long)nanos + NANOS_PER_SECOND);
            --seconds;
        }
        if (seconds < 0L && nanos > 0) {
            nanos = (int)((long)nanos - NANOS_PER_SECOND);
            ++seconds;
        }
        if (!JsonUtil.durationIsValid(seconds, nanos)) {
            throw new IllegalArgumentException(String.format("Duration is not valid. See proto definition for valid values. Seconds (%s) must be in range [-315,576,000,000, +315,576,000,000]. Nanos (%s) must be in range [-999,999,999, +999,999,999]. Nanos must have the same sign as seconds", seconds, nanos));
        }
        return JsonUtil.saturatedAdd(TimeUnit.SECONDS.toNanos(seconds), nanos);
    }

    private static boolean durationIsValid(long seconds, int nanos) {
        if (seconds < -315576000000L || seconds > 315576000000L) {
            return false;
        }
        if ((long)nanos < -999999999L || (long)nanos >= NANOS_PER_SECOND) {
            return false;
        }
        return seconds >= 0L && nanos >= 0 || seconds <= 0L && nanos <= 0;
    }

    private static long saturatedAdd(long a, long b) {
        long naiveSum;
        if ((a ^ b) < 0L | (a ^ (naiveSum = a + b)) >= 0L) {
            return naiveSum;
        }
        return Long.MAX_VALUE + (naiveSum >>> 63 ^ 1L);
    }
}

