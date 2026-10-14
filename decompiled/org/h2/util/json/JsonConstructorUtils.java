/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.io.ByteArrayOutputStream;
import org.h2.message.DbException;
import org.h2.util.json.JSONByteArrayTarget;
import org.h2.util.json.JSONBytesSource;
import org.h2.util.json.JSONStringTarget;
import org.h2.util.json.JSONValidationTargetWithUniqueKeys;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;

public final class JsonConstructorUtils {
    public static final int JSON_ABSENT_ON_NULL = 1;
    public static final int JSON_WITH_UNIQUE_KEYS = 2;

    private JsonConstructorUtils() {
    }

    public static void jsonObjectAppend(ByteArrayOutputStream byteArrayOutputStream, String string, Value value) {
        if (byteArrayOutputStream.size() > 1) {
            byteArrayOutputStream.write(44);
        }
        JSONByteArrayTarget.encodeString(byteArrayOutputStream, string).write(58);
        byte[] byArray = value.convertTo(TypeInfo.TYPE_JSON).getBytesNoCopy();
        byteArrayOutputStream.write(byArray, 0, byArray.length);
    }

    public static Value jsonObjectFinish(ByteArrayOutputStream byteArrayOutputStream, int n) {
        byteArrayOutputStream.write(125);
        byte[] byArray = byteArrayOutputStream.toByteArray();
        if ((n & 2) != 0) {
            try {
                JSONBytesSource.parse(byArray, new JSONValidationTargetWithUniqueKeys());
            }
            catch (RuntimeException runtimeException) {
                String string = JSONBytesSource.parse(byArray, new JSONStringTarget());
                throw DbException.getInvalidValueException("JSON WITH UNIQUE KEYS", string.length() < 128 ? byArray : (byte[])(string.substring(0, 128) + "..."));
            }
        }
        return ValueJson.getInternal(byArray);
    }

    public static void jsonArrayAppend(ByteArrayOutputStream byteArrayOutputStream, Value value, int n) {
        if (value == ValueNull.INSTANCE) {
            if ((n & 1) != 0) {
                return;
            }
            value = ValueJson.NULL;
        }
        if (byteArrayOutputStream.size() > 1) {
            byteArrayOutputStream.write(44);
        }
        byte[] byArray = value.convertTo(TypeInfo.TYPE_JSON).getBytesNoCopy();
        byteArrayOutputStream.write(byArray, 0, byArray.length);
    }
}

