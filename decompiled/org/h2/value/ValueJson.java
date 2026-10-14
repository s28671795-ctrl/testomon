/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.util.json.JSONBoolean;
import org.h2.util.json.JSONByteArrayTarget;
import org.h2.util.json.JSONBytesSource;
import org.h2.util.json.JSONItemType;
import org.h2.util.json.JSONNull;
import org.h2.util.json.JSONNumber;
import org.h2.util.json.JSONStringSource;
import org.h2.util.json.JSONStringTarget;
import org.h2.util.json.JSONValue;
import org.h2.util.json.JSONValueTarget;
import org.h2.value.TypeInfo;
import org.h2.value.ValueBytesBase;

public final class ValueJson
extends ValueBytesBase {
    private static final byte[] NULL_BYTES = "null".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] TRUE_BYTES = "true".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] FALSE_BYTES = "false".getBytes(StandardCharsets.ISO_8859_1);
    public static final ValueJson NULL = new ValueJson(NULL_BYTES);
    public static final ValueJson TRUE = new ValueJson(TRUE_BYTES);
    public static final ValueJson FALSE = new ValueJson(FALSE_BYTES);
    public static final ValueJson ZERO = new ValueJson(new byte[]{48});
    private volatile SoftReference<JSONValue> decompositionRef;

    private ValueJson(byte[] byArray) {
        super(byArray);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        String string = JSONBytesSource.parse(this.value, new JSONStringTarget(true));
        return stringBuilder.append("JSON '").append(string).append('\'');
    }

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_JSON;
    }

    @Override
    public int getValueType() {
        return 38;
    }

    @Override
    public String getString() {
        return new String(this.value, StandardCharsets.UTF_8);
    }

    public JSONItemType getItemType() {
        switch (this.value[0]) {
            case 91: {
                return JSONItemType.ARRAY;
            }
            case 123: {
                return JSONItemType.OBJECT;
            }
        }
        return JSONItemType.SCALAR;
    }

    public JSONValue getDecomposition() {
        JSONValue jSONValue;
        SoftReference<JSONValue> softReference = this.decompositionRef;
        if (softReference == null || (jSONValue = softReference.get()) == null) {
            jSONValue = JSONBytesSource.parse(this.value, new JSONValueTarget());
            this.decompositionRef = new SoftReference<JSONValue>(jSONValue);
        }
        return jSONValue;
    }

    public static ValueJson fromJson(String string) {
        byte[] byArray;
        try {
            byArray = JSONStringSource.normalize(string);
        }
        catch (RuntimeException runtimeException) {
            if (string.length() > 80) {
                string = new StringBuilder(83).append(string, 0, 80).append("...").toString();
            }
            throw DbException.get(22018, string);
        }
        return ValueJson.getInternal(byArray);
    }

    public static ValueJson fromJson(byte[] byArray) {
        try {
            byArray = JSONBytesSource.normalize(byArray);
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder().append("X'");
            if (byArray.length > 40) {
                StringUtils.convertBytesToHex(stringBuilder, byArray, 40).append("...");
            } else {
                StringUtils.convertBytesToHex(stringBuilder, byArray);
            }
            throw DbException.get(22018, stringBuilder.append('\'').toString());
        }
        return ValueJson.getInternal(byArray);
    }

    public static ValueJson fromJson(JSONValue jSONValue) {
        if (jSONValue instanceof JSONNull) {
            return NULL;
        }
        if (jSONValue instanceof JSONBoolean) {
            return ((JSONBoolean)jSONValue).getBoolean() ? TRUE : FALSE;
        }
        if (jSONValue instanceof JSONNumber && ((JSONNumber)jSONValue).getBigDecimal().equals(BigDecimal.ZERO)) {
            return ZERO;
        }
        JSONByteArrayTarget jSONByteArrayTarget = new JSONByteArrayTarget();
        jSONValue.addTo(jSONByteArrayTarget);
        ValueJson valueJson = new ValueJson(jSONByteArrayTarget.getResult());
        valueJson.decompositionRef = new SoftReference<JSONValue>(jSONValue);
        return valueJson;
    }

    public static ValueJson get(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    public static ValueJson get(int n) {
        return n != 0 ? ValueJson.getNumber(Integer.toString(n)) : ZERO;
    }

    public static ValueJson get(long l) {
        return l != 0L ? ValueJson.getNumber(Long.toString(l)) : ZERO;
    }

    public static ValueJson get(BigDecimal bigDecimal) {
        if (bigDecimal.signum() == 0 && bigDecimal.scale() == 0) {
            return ZERO;
        }
        String string = bigDecimal.toString();
        int n = string.indexOf(69);
        if (n >= 0 && string.charAt(++n) == '+') {
            int n2 = string.length();
            string = new StringBuilder(n2 - 1).append(string, 0, n).append(string, n + 1, n2).toString();
        }
        return ValueJson.getNumber(string);
    }

    public static ValueJson get(String string) {
        return new ValueJson(JSONByteArrayTarget.encodeString(new ByteArrayOutputStream(string.length() + 2), string).toByteArray());
    }

    public static ValueJson getInternal(byte[] byArray) {
        int n = byArray.length;
        switch (n) {
            case 1: {
                if (byArray[0] != 48) break;
                return ZERO;
            }
            case 4: {
                if (Arrays.equals(TRUE_BYTES, byArray)) {
                    return TRUE;
                }
                if (!Arrays.equals(NULL_BYTES, byArray)) break;
                return NULL;
            }
            case 5: {
                if (!Arrays.equals(FALSE_BYTES, byArray)) break;
                return FALSE;
            }
        }
        return new ValueJson(byArray);
    }

    private static ValueJson getNumber(String string) {
        return new ValueJson(string.getBytes(StandardCharsets.ISO_8859_1));
    }

    @Override
    public int getMemory() {
        return this.value.length + 96;
    }
}

