/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import org.jetbrains.annotations.Nullable;
import org.jooq.ConnectionProvider;

public class NoConnectionProvider
implements ConnectionProvider {
    @Override
    @Nullable
    public final Connection acquire() {
        return null;
    }

    @Override
    public final void release(Connection connection) {
    }
}

