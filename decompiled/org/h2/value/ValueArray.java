/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.CompareMode;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueCollectionBase;

public final class ValueArray
extends ValueCollectionBase {
    public static final ValueArray EMPTY = ValueArray.get(TypeInfo.TYPE_NULL, Value.EMPTY_VALUES, null);
    private TypeInfo type;
    private final TypeInfo componentType;

    private ValueArray(TypeInfo typeInfo, Value[] valueArray, CastDataProvider castDataProvider) {
        super(valueArray);
        int n = valueArray.length;
        if (n > 65536) {
            String string = ValueArray.getTypeName(this.getValueType());
            throw DbException.getValueTooLongException(string, string, n);
        }
        for (int i = 0; i < n; ++i) {
            valueArray[i] = valueArray[i].castTo(typeInfo, castDataProvider);
        }
        this.componentType = typeInfo;
    }

    public static ValueArray get(Value[] valueArray, CastDataProvider castDataProvider) {
        return new ValueArray(TypeInfo.getHigherType(valueArray), valueArray, castDataProvider);
    }

    public static ValueArray get(TypeInfo typeInfo, Value[] valueArray, CastDataProvider castDataProvider) {
        return new ValueArray(typeInfo, valueArray, castDataProvider);
    }

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            TypeInfo typeInfo2 = this.getComponentType();
            this.type = typeInfo = TypeInfo.getTypeInfo(this.getValueType(), this.values.length, 0, typeInfo2);
        }
        return typeInfo;
    }

    @Override
    public int getValueType() {
        return 40;
    }

    public TypeInfo getComponentType() {
        return this.componentType;
    }

    @Override
    public String getString() {
        StringBuilder stringBuilder = new StringBuilder().append('[');
        for (int i = 0; i < this.values.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.values[i].getString());
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        ValueArray valueArray = (ValueArray)value;
        if (this.values == valueArray.values) {
            return 0;
        }
        int n = this.values.length;
        int n2 = valueArray.values.length;
        int n3 = Math.min(n, n2);
        for (int i = 0; i < n3; ++i) {
            Value value2 = this.values[i];
            Value value3 = valueArray.values[i];
            int n4 = value2.compareTo(value3, castDataProvider, compareMode);
            if (n4 == 0) continue;
            return n4;
        }
        return Integer.compare(n, n2);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("ARRAY [");
        int n2 = this.values.length;
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            this.values[i].getSQL(stringBuilder, n);
        }
        return stringBuilder.append(']');
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ValueArray)) {
            return false;
        }
        ValueArray valueArray = (ValueArray)object;
        if (this.values == valueArray.values) {
            return true;
        }
        int n = this.values.length;
        if (n != valueArray.values.length) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.values[i].equals(valueArray.values[i])) continue;
            return false;
        }
        return true;
    }
}

