/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util.privilegedactions;

import java.security.PrivilegedAction;

public final class IsClassPresent
implements PrivilegedAction<Boolean> {
    private final String className;
    private final ClassLoader classLoader;

    public static IsClassPresent action(String className, ClassLoader classLoader) {
        return new IsClassPresent(className, classLoader);
    }

    private IsClassPresent(String className, ClassLoader classLoader) {
        this.className = className;
        this.classLoader = classLoader;
    }

    @Override
    public Boolean run() {
        try {
            Class.forName(this.className, false, this.classLoader);
            return Boolean.TRUE;
        }
        catch (ClassNotFoundException e) {
            return Boolean.FALSE;
        }
    }
}

