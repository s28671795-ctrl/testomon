/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.Option;
import java.time.Duration;

public interface TransactionDefinition {
    public static final Option<IsolationLevel> ISOLATION_LEVEL = Option.valueOf("isolationLevel");
    public static final Option<Boolean> READ_ONLY = Option.valueOf("readOnly");
    public static final Option<String> NAME = Option.valueOf("name");
    public static final Option<Duration> LOCK_WAIT_TIMEOUT = Option.valueOf("lockWaitTimeout");

    @Nullable
    public <T> T getAttribute(Option<T> var1);
}

