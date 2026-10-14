/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.SSLDebugHandshakeOptions;
import com.typesafe.sslconfig.ssl.SSLDebugRecordOptions;
import scala.None$;
import scala.Option;

public final class SSLDebugConfig$ {
    public static final SSLDebugConfig$ MODULE$ = new SSLDebugConfig$();

    public boolean $lessinit$greater$default$1() {
        return false;
    }

    public boolean $lessinit$greater$default$2() {
        return false;
    }

    public boolean $lessinit$greater$default$3() {
        return false;
    }

    public Option<SSLDebugHandshakeOptions> $lessinit$greater$default$4() {
        return None$.MODULE$;
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

    public boolean $lessinit$greater$default$8() {
        return false;
    }

    public Option<SSLDebugRecordOptions> $lessinit$greater$default$9() {
        return None$.MODULE$;
    }

    public boolean $lessinit$greater$default$10() {
        return false;
    }

    public boolean $lessinit$greater$default$11() {
        return false;
    }

    public boolean $lessinit$greater$default$12() {
        return false;
    }

    public boolean $lessinit$greater$default$13() {
        return false;
    }

    public boolean $lessinit$greater$default$14() {
        return false;
    }

    public SSLDebugConfig apply() {
        return new SSLDebugConfig(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2(), this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5(), this.$lessinit$greater$default$6(), this.$lessinit$greater$default$7(), this.$lessinit$greater$default$8(), this.$lessinit$greater$default$9(), this.$lessinit$greater$default$10(), this.$lessinit$greater$default$11(), this.$lessinit$greater$default$12(), this.$lessinit$greater$default$13(), this.$lessinit$greater$default$14());
    }

    public SSLDebugConfig getInstance() {
        return this.apply();
    }

    private SSLDebugConfig$() {
    }
}

