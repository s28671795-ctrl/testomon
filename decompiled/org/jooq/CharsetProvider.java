/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.nio.charset.Charset;

@FunctionalInterface
public interface CharsetProvider {
    public Charset provide();
}

