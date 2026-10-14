/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyManagerConfig;
import com.typesafe.sslconfig.ssl.KeyStoreConfig;
import javax.net.ssl.KeyManagerFactory;
import scala.collection.immutable.Seq;
import scala.package$;

public final class KeyManagerConfig$ {
    public static final KeyManagerConfig$ MODULE$ = new KeyManagerConfig$();

    public String $lessinit$greater$default$1() {
        return KeyManagerFactory.getDefaultAlgorithm();
    }

    public Seq<KeyStoreConfig> $lessinit$greater$default$2() {
        return package$.MODULE$.Nil();
    }

    public KeyManagerConfig apply() {
        return new KeyManagerConfig(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2());
    }

    public KeyManagerConfig getInstance() {
        return this.apply();
    }

    private KeyManagerConfig$() {
    }
}

