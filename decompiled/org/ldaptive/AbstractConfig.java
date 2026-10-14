/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfig {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean immutable;

    public void makeImmutable() {
        this.immutable = true;
    }

    public void checkImmutable() {
        if (this.immutable) {
            throw new IllegalStateException("Cannot modify immutable object");
        }
    }

    protected void checkArrayContainsNull(Object[] array) {
        if (array != null) {
            for (Object o : array) {
                if (o != null) continue;
                throw new IllegalArgumentException("Array element cannot be null");
            }
        }
    }

    protected void checkStringInput(String s2, boolean allowNull) {
        if (allowNull) {
            if (s2 != null && "".equals(s2)) {
                throw new IllegalArgumentException("Input cannot be empty");
            }
        } else if (s2 == null || "".equals(s2)) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
    }
}

