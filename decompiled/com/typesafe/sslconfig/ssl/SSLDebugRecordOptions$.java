/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugRecordOptions;

public final class SSLDebugRecordOptions$ {
    public static final SSLDebugRecordOptions$ MODULE$ = new SSLDebugRecordOptions$();

    public boolean $lessinit$greater$default$1() {
        return false;
    }

    public boolean $lessinit$greater$default$2() {
        return false;
    }

    public SSLDebugRecordOptions apply() {
        return new SSLDebugRecordOptions(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2());
    }

    public SSLDebugRecordOptions getInstance() {
        return this.apply();
    }

    private SSLDebugRecordOptions$() {
    }
}

