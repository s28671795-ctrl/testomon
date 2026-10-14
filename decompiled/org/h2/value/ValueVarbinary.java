/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.nio.charset.StandardCharsets;
import org.h2.engine.SysProperties;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBytesBase;

public final class ValueVarbinary
extends ValueBytesBase {
    public static final ValueVarbinary EMPTY = new ValueVarbinary(Utils.EMPTY_BYTES);
    private TypeInfo type;

    private ValueVarbinary(byte[] byArray) {
        super(byArray);
    }

    public static ValueVarbinary get(byte[] byArray) {
        if (byArray.length == 0) {
            return EMPTY;
        }
        byArray = Utils.cloneByteArray(byArray);
        return ValueVarbinary.getNoCopy(byArray);
    }

    public static ValueVarbinary getNoCopy(byte[] byArray) {
        if (byArray.length == 0) {
            return EMPTY;
        }
        ValueVarbinary valueVarbinary = new ValueVarbinary(byArray);
        if (byArray.length > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return valueVarbinary;
        }
        return (ValueVarbinary)Value.cache(valueVarbinary);
    }

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            long l = this.value.length;
            this.type = typeInfo = new TypeInfo(6, l, 0, null);
        }
        return typeInfo;
    }

    @Override
    public int getValueType() {
        return 6;
    }

    @Override
    public String getString() {
        return new String(this.value, StandardCharsets.UTF_8);
    }
}

