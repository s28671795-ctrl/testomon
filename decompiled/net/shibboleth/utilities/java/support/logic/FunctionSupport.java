/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import javax.annotation.Nonnull;

public final class FunctionSupport {
    private FunctionSupport() {
    }

    @Nonnull
    public static <T1, T2> Function<T1, T2> constant(@Nonnull T2 target) {
        return Functions.constant(target);
    }
}

