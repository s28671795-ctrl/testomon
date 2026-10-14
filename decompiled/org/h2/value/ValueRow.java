/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.result.SimpleResult;
import org.h2.value.CompareMode;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueCollectionBase;

public final class ValueRow
extends ValueCollectionBase {
    public static final ValueRow EMPTY = ValueRow.get(Value.EMPTY_VALUES);
    private TypeInfo type;

    private ValueRow(TypeInfo typeInfo, Value[] valueArray) {
        super(valueArray);
        int n = valueArray.length;
        if (n > 16384) {
            throw DbException.get(54011, "16384");
        }
        if (typeInfo != null) {
            if (typeInfo.getValueType() != 41 || ((ExtTypeInfoRow)typeInfo.getExtTypeInfo()).getFields().size() != n) {
                throw DbException.getInternalError();
            }
            this.type = typeInfo;
        }
    }

    public static ValueRow get(Value[] valueArray) {
        return new ValueRow(null, valueArray);
    }

    public static ValueRow get(ExtTypeInfoRow extTypeInfoRow, Value[] valueArray) {
        return new ValueRow(new TypeInfo(41, -1L, -1, extTypeInfoRow), valueArray);
    }

    public static ValueRow get(TypeInfo typeInfo, Value[] valueArray) {
        return new ValueRow(typeInfo, valueArray);
    }

    @Override
    public TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            this.type = typeInfo = TypeInfo.getTypeInfo(41, 0L, 0, new ExtTypeInfoRow(this.values));
        }
        return typeInfo;
    }

    @Override
    public int getValueType() {
        return 41;
    }

    @Override
    public String getString() {
        StringBuilder stringBuilder = new StringBuilder("ROW (");
        for (int i = 0; i < this.values.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.values[i].getString());
        }
        return stringBuilder.append(')').toString();
    }

    public SimpleResult getResult() {
        SimpleResult simpleResult = new SimpleResult();
        int n = 0;
        int n2 = this.values.length;
        while (n < n2) {
            Value value = this.values[n++];
            simpleResult.addColumn("C" + n, value.getType());
        }
        simpleResult.addRow(this.values);
        return simpleResult;
    }

    @Override
    public int compareTypeSafe(Value value, CompareMode compareMode, CastDataProvider castDataProvider) {
        ValueRow valueRow = (ValueRow)value;
        if (this.values == valueRow.values) {
            return 0;
        }
        int n = this.values.length;
        if (n != valueRow.values.length) {
            throw DbException.get(21002);
        }
        for (int i = 0; i < n; ++i) {
            Value value2 = this.values[i];
            Value value3 = valueRow.values[i];
            int n2 = value2.compareTo(value3, castDataProvider, compareMode);
            if (n2 == 0) continue;
            return n2;
        }
        return 0;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append("ROW (");
        int n2 = this.values.length;
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            this.values[i].getSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ValueRow)) {
            return false;
        }
        ValueRow valueRow = (ValueRow)object;
        if (this.values == valueRow.values) {
            return true;
        }
        int n = this.values.length;
        if (n != valueRow.values.length) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.values[i].equals(valueRow.values[i])) continue;
            return false;
        }
        return true;
    }
}

