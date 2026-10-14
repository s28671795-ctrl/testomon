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

public final class ValueBinary
extends ValueBytesBase {
    private TypeInfo type;

    private ValueBinary(byte[] byArray) {
        super(byArray);
    }

    public static ValueBinary get(byte[] byArray) {
        return ValueBinary.getNoCopy(Utils.cloneByteArray(byArray));
    }

    public static ValueBinary getNoCopy(byte[] byArray) {
        ValueBinary valueBinary = new ValueBinary(byArray);
        if (byArray.length > SysProperties.OBJECT_CACHE_MAX_PER_ELEMENT_SIZE) {
            return valueBinary;
        }
        return (ValueBinary)Value.cache(valueBinary);
    }

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            long l = this.value.length;
            this.type = typeInfo = new TypeInfo(5, l, 0, null);
        }
        return typeInfo;
    }

    @Override
    public int getValueType() {
        return 5;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if ((n & 4) == 0) {
            int n2 = this.value.length;
            return super.getSQL(stringBuilder.append("CAST("), n).append(" AS BINARY(").append(n2 > 0 ? n2 : 1).append("))");
        }
        return super.getSQL(stringBuilder, n);
    }

    @Override
    public String getString() {
        return new String(this.value, StandardCharsets.UTF_8);
    }
}

