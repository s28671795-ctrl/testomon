/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.TrustManagerConfig;
import com.typesafe.sslconfig.ssl.TrustStoreConfig;
import javax.net.ssl.TrustManagerFactory;
import scala.collection.immutable.Seq;
import scala.package$;

public final class TrustManagerConfig$ {
    public static final TrustManagerConfig$ MODULE$ = new TrustManagerConfig$();

    public String $lessinit$greater$default$1() {
        return TrustManagerFactory.getDefaultAlgorithm();
    }

    public Seq<TrustStoreConfig> $lessinit$greater$default$2() {
        return package$.MODULE$.Nil();
    }

    public TrustManagerConfig apply() {
        return new TrustManagerConfig(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2());
    }

    public TrustManagerConfig getInstance() {
        return this.apply();
    }

    private TrustManagerConfig$() {
    }
}

