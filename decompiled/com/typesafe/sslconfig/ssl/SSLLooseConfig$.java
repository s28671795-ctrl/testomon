/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLLooseConfig;
import scala.None$;
import scala.Option;

public final class SSLLooseConfig$ {
    public static final SSLLooseConfig$ MODULE$ = new SSLLooseConfig$();

    public boolean $lessinit$greater$default$1() {
        return false;
    }

    public Option<Object> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public Option<Object> $lessinit$greater$default$3() {
        return None$.MODULE$;
    }

    public boolean $lessinit$greater$default$4() {
        return false;
    }

    public boolean $lessinit$greater$default$5() {
        return false;
    }

    public boolean $lessinit$greater$default$6() {
        return false;
    }

    public boolean $lessinit$greater$default$7() {
        return false;
    }

    public SSLLooseConfig apply() {
        return new SSLLooseConfig(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2(), this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5(), this.$lessinit$greater$default$6(), this.$lessinit$greater$default$7());
    }

    public SSLLooseConfig getInstance() {
        return this.apply();
    }

    private SSLLooseConfig$() {
    }
}

