/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.classmate.util;

import com.fasterxml.classmate.ResolvedType;

public class ResolvedTypeKey {
    private final Class<?> _erasedType;
    private final ResolvedType[] _typeParameters;
    private final int _hashCode;

    public ResolvedTypeKey(Class<?> simpleType) {
        this(simpleType, null);
    }

    public ResolvedTypeKey(Class<?> erasedType, ResolvedType[] tp) {
        if (tp != null && tp.length == 0) {
            tp = null;
        }
        this._erasedType = erasedType;
        this._typeParameters = tp;
        int h2 = erasedType.getName().hashCode();
        if (tp != null) {
            h2 += tp.length;
        }
        this._hashCode = h2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[CacheKey: ");
        sb.append(this._erasedType.getName()).append('(');
        if (this._typeParameters != null) {
            for (int i = 0; i < this._typeParameters.length; ++i) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(this._typeParameters[i]);
            }
        }
        sb.append(")]");
        return sb.toString();
    }

    public int hashCode() {
        return this._hashCode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        ResolvedTypeKey other = (ResolvedTypeKey)o;
        if (other._erasedType != this._erasedType) {
            return false;
        }
        ResolvedType[] otherTP = other._typeParameters;
        if (this._typeParameters == null) {
            return otherTP == null;
        }
        if (otherTP == null || otherTP.length != this._typeParameters.length) {
            return false;
        }
        int len = this._typeParameters.length;
        for (int i = 0; i < len; ++i) {
            if (this._typeParameters[i].equals(otherTP[i])) continue;
            return false;
        }
        return true;
    }
}

