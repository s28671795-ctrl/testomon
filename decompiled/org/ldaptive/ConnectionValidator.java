/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.ldaptive.Connection;

public interface ConnectionValidator
extends Function<Connection, Boolean> {
    public void applyAsync(Connection var1, Consumer<Boolean> var2);

    public Supplier<Boolean> applyAsync(Connection var1);

    public Duration getValidatePeriod();

    public Duration getValidateTimeout();
}

