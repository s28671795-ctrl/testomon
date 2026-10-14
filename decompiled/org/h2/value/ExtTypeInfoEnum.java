/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.Arrays;
import java.util.Locale;
import org.h2.engine.CastDataProvider;
import org.h2.message.DbException;
import org.h2.value.ExtTypeInfo;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueEnum;

public final class ExtTypeInfoEnum
extends ExtTypeInfo {
    private final String[] enumerators;
    private final String[] cleaned;
    private TypeInfo type;

    public static ExtTypeInfoEnum getEnumeratorsForBinaryOperation(Value value, Value value2) {
        if (value.getValueType() == 36) {
            return ((ValueEnum)value).getEnumerators();
        }
        if (value2.getValueType() == 36) {
            return ((ValueEnum)value2).getEnumerators();
        }
        throw DbException.get(50004, "type1=" + value.getValueType() + ", type2=" + value2.getValueType());
    }

    private static String sanitize(String string) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        if (n > 1000000000) {
            throw DbException.getValueTooLongException("ENUM", string, n);
        }
        return string.trim().toUpperCase(Locale.ENGLISH);
    }

    private static StringBuilder toSQL(StringBuilder stringBuilder, String[] stringArray) {
        stringBuilder.append('(');
        for (int i = 0; i < stringArray.length; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append('\'');
            String string = stringArray[i];
            int n = string.length();
            for (int j = 0; j < n; ++j) {
                char c = string.charAt(j);
                if (c == '\'') {
                    stringBuilder.append('\'');
                }
                stringBuilder.append(c);
            }
            stringBuilder.append('\'');
        }
        return stringBuilder.append(')');
    }

    public ExtTypeInfoEnum(String[] stringArray) {
        int n;
        if (stringArray == null || (n = stringArray.length) == 0) {
            throw DbException.get(22032);
        }
        if (n > 65536) {
            throw DbException.getValueTooLongException("ENUM", "(" + n + " elements)", n);
        }
        Object[] objectArray = new String[n];
        for (int i = 0; i < n; ++i) {
            String string = ExtTypeInfoEnum.sanitize(stringArray[i]);
            if (string == null || string.isEmpty()) {
                throw DbException.get(22032);
            }
            for (int j = 0; j < i; ++j) {
                if (!string.equals(objectArray[j])) continue;
                throw DbException.get(22033, ExtTypeInfoEnum.toSQL(new StringBuilder(), stringArray).toString());
            }
            objectArray[i] = string;
        }
        this.enumerators = stringArray;
        this.cleaned = Arrays.equals(objectArray, stringArray) ? stringArray : objectArray;
    }

    TypeInfo getType() {
        TypeInfo typeInfo = this.type;
        if (typeInfo == null) {
            int n = 0;
            for (String string : this.enumerators) {
                int n2 = string.length();
                if (n2 <= n) continue;
                n = n2;
            }
            this.type = typeInfo = new TypeInfo(36, n, 0, this);
        }
        return typeInfo;
    }

    public int getCount() {
        return this.enumerators.length;
    }

    public String getEnumerator(int n) {
        return this.enumerators[n];
    }

    public ValueEnum getValue(int n, CastDataProvider castDataProvider) {
        String string;
        if (castDataProvider == null || !castDataProvider.zeroBasedEnums()) {
            if (n < 1 || n > this.enumerators.length) {
                throw DbException.get(22030, this.getTraceSQL(), Integer.toString(n));
            }
            string = this.enumerators[n - 1];
        } else {
            if (n < 0 || n >= this.enumerators.length) {
                throw DbException.get(22030, this.getTraceSQL(), Integer.toString(n));
            }
            string = this.enumerators[n];
        }
        return new ValueEnum(this, string, n);
    }

    public ValueEnum getValue(String string, CastDataProvider castDataProvider) {
        ValueEnum valueEnum = this.getValueOrNull(string, castDataProvider);
        if (valueEnum == null) {
            throw DbException.get(22030, this.toString(), string);
        }
        return valueEnum;
    }

    private ValueEnum getValueOrNull(String string, CastDataProvider castDataProvider) {
        String string2 = ExtTypeInfoEnum.sanitize(string);
        if (string2 != null) {
            int n;
            int n2 = 0;
            int n3 = n = castDataProvider == null || !castDataProvider.zeroBasedEnums() ? 1 : 0;
            while (n2 < this.cleaned.length) {
                if (string2.equals(this.cleaned[n2])) {
                    return new ValueEnum(this, this.enumerators[n2], n);
                }
                ++n2;
                ++n;
            }
        }
        return null;
    }

    public int hashCode() {
        return Arrays.hashCode(this.enumerators) + 203117;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != ExtTypeInfoEnum.class) {
            return false;
        }
        return Arrays.equals(this.enumerators, ((ExtTypeInfoEnum)object).enumerators);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return ExtTypeInfoEnum.toSQL(stringBuilder, this.enumerators);
    }
}

