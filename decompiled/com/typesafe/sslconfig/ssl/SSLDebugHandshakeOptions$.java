/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugHandshakeOptions;

public final class SSLDebugHandshakeOptions$ {
    public static final SSLDebugHandshakeOptions$ MODULE$ = new SSLDebugHandshakeOptions$();

    public boolean $lessinit$greater$default$1() {
        return false;
    }

    public boolean $lessinit$greater$default$2() {
        return false;
    }

    public SSLDebugHandshakeOptions apply() {
        return new SSLDebugHandshakeOptions(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2());
    }

    public SSLDebugHandshakeOptions getInstance() {
        return this.apply();
    }

    private SSLDebugHandshakeOptions$() {
    }
}

