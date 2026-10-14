/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.ClientAuth;
import com.typesafe.sslconfig.ssl.ClientAuth$Default$;
import com.typesafe.sslconfig.ssl.SSLParametersConfig;
import scala.collection.immutable.Seq;
import scala.package$;

public final class SSLParametersConfig$ {
    public static final SSLParametersConfig$ MODULE$ = new SSLParametersConfig$();

    public ClientAuth $lessinit$greater$default$1() {
        return ClientAuth$Default$.MODULE$;
    }

    public Seq<String> $lessinit$greater$default$2() {
        return package$.MODULE$.Nil();
    }

    public SSLParametersConfig apply() {
        return new SSLParametersConfig(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2());
    }

    public SSLParametersConfig getInstance() {
        return this.apply();
    }

    private SSLParametersConfig$() {
    }
}

