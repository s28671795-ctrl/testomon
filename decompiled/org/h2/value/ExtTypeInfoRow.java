/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.h2.message.DbException;
import org.h2.util.ParserUtil;
import org.h2.value.ExtTypeInfo;
import org.h2.value.TypeInfo;
import org.h2.value.Typed;

public final class ExtTypeInfoRow
extends ExtTypeInfo {
    private final LinkedHashMap<String, TypeInfo> fields;
    private int hash;

    public ExtTypeInfoRow(Typed[] typedArray) {
        this(typedArray, typedArray.length);
    }

    public ExtTypeInfoRow(Typed[] typedArray, int n) {
        if (n > 16384) {
            throw DbException.get(54011, "16384");
        }
        LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>((int)Math.ceil((double)n / 0.75));
        int n2 = 0;
        while (n2 < n) {
            TypeInfo typeInfo = typedArray[n2].getType();
            linkedHashMap.put("C" + ++n2, typeInfo);
        }
        this.fields = linkedHashMap;
    }

    public ExtTypeInfoRow(LinkedHashMap<String, TypeInfo> linkedHashMap) {
        if (linkedHashMap.size() > 16384) {
            throw DbException.get(54011, "16384");
        }
        this.fields = linkedHashMap;
    }

    public Set<Map.Entry<String, TypeInfo>> getFields() {
        return this.fields.entrySet();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append('(');
        boolean bl = false;
        for (Map.Entry<String, TypeInfo> entry : this.fields.entrySet()) {
            if (bl) {
                stringBuilder.append(", ");
            }
            bl = true;
            ParserUtil.quoteIdentifier(stringBuilder, entry.getKey(), n).append(' ');
            entry.getValue().getSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }

    public int hashCode() {
        int n = this.hash;
        if (n != 0) {
            return n;
        }
        n = 67378403;
        for (Map.Entry<String, TypeInfo> entry : this.fields.entrySet()) {
            n = (n * 31 + entry.getKey().hashCode()) * 37 + entry.getValue().hashCode();
        }
        this.hash = n;
        return this.hash;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object.getClass() != ExtTypeInfoRow.class) {
            return false;
        }
        LinkedHashMap<String, TypeInfo> linkedHashMap = ((ExtTypeInfoRow)object).fields;
        int n = this.fields.size();
        if (n != linkedHashMap.size()) {
            return false;
        }
        Iterator<Map.Entry<String, TypeInfo>> iterator2 = this.fields.entrySet().iterator();
        Iterator<Map.Entry<String, TypeInfo>> iterator3 = linkedHashMap.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, TypeInfo> entry = iterator2.next();
            Map.Entry<String, TypeInfo> entry2 = iterator3.next();
            if (entry.getKey().equals(entry2.getKey()) && entry.getValue().equals(entry2.getValue())) continue;
            return false;
        }
        return true;
    }
}

