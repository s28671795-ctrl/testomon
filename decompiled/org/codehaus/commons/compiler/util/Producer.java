/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

import org.codehaus.commons.nullanalysis.Nullable;

public interface Producer<T> {
    @Nullable
    public T produce();
}

