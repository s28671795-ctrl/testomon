/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.Wrapper;

public interface Unwrapper {
    public <T> T unwrap(Wrapper var1, Class<T> var2);
}

