/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.Objects;
import org.h2.util.geometry.EWKTUtils;
import org.h2.value.ExtTypeInfo;

public final class ExtTypeInfoGeometry
extends ExtTypeInfo {
    private final int type;
    private final Integer srid;

    static StringBuilder toSQL(StringBuilder stringBuilder, int n, Integer n2) {
        if (n == 0 && n2 == null) {
            return stringBuilder;
        }
        stringBuilder.append('(');
        if (n == 0) {
            stringBuilder.append("GEOMETRY");
        } else {
            EWKTUtils.formatGeometryTypeAndDimensionSystem(stringBuilder, n);
        }
        if (n2 != null) {
            stringBuilder.append(", ").append(n2);
        }
        return stringBuilder.append(')');
    }

    public ExtTypeInfoGeometry(int n, Integer n2) {
        this.type = n;
        this.srid = n2;
    }

    public int hashCode() {
        return 31 * (this.srid == null ? 0 : this.srid.hashCode()) + this.type;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != ExtTypeInfoGeometry.class) {
            return false;
        }
        ExtTypeInfoGeometry extTypeInfoGeometry = (ExtTypeInfoGeometry)object;
        return this.type == extTypeInfoGeometry.type && Objects.equals(this.srid, extTypeInfoGeometry.srid);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return ExtTypeInfoGeometry.toSQL(stringBuilder, this.type, this.srid);
    }

    public int getType() {
        return this.type;
    }

    public Integer getSrid() {
        return this.srid;
    }
}

