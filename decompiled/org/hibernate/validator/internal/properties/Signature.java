/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties;

import java.util.Arrays;

public final class Signature {
    private final String name;
    private final Class<?>[] parameterTypes;

    public Signature(String name, Class<?> ... parameterTypes) {
        this.name = name;
        this.parameterTypes = parameterTypes;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Signature other = (Signature)obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return Arrays.equals(this.parameterTypes, other.parameterTypes);
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + Arrays.hashCode(this.parameterTypes);
        return result;
    }
}

