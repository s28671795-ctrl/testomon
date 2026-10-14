/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.PrintlnLogger;

public final class PrintlnLogger$ {
    public static final PrintlnLogger$ MODULE$ = new PrintlnLogger$();

    public LoggerFactory factory() {
        return new LoggerFactory(){

            public PrintlnLogger apply(Class<?> clazz) {
                return new PrintlnLogger(clazz.getName());
            }

            public PrintlnLogger apply(String name) {
                return new PrintlnLogger(name);
            }
        };
    }

    private PrintlnLogger$() {
    }
}

