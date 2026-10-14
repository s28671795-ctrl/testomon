/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoopLogger;

public final class NoopLogger$ {
    public static final NoopLogger$ MODULE$ = new NoopLogger$();
    private static final NoopLogger com$typesafe$sslconfig$util$NoopLogger$$_noop = new NoopLogger();

    public NoopLogger com$typesafe$sslconfig$util$NoopLogger$$_noop() {
        return com$typesafe$sslconfig$util$NoopLogger$$_noop;
    }

    public LoggerFactory factory() {
        return new LoggerFactory(){

            public NoopLogger apply(Class<?> clazz) {
                return NoopLogger$.MODULE$.com$typesafe$sslconfig$util$NoopLogger$$_noop();
            }

            public NoopLogger apply(String name) {
                return NoopLogger$.MODULE$.com$typesafe$sslconfig$util$NoopLogger$$_noop();
            }
        };
    }

    private NoopLogger$() {
    }
}

