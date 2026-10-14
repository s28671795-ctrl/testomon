/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.nio.charset.Charset;
import org.jooq.CharsetProvider;

final class DefaultCharsetProvider
implements CharsetProvider {
    DefaultCharsetProvider() {
    }

    @Override
    public final Charset provide() {
        return Charset.defaultCharset();
    }
}

