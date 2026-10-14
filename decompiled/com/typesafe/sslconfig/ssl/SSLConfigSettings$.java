/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyManagerConfig;
import com.typesafe.sslconfig.ssl.KeyManagerConfig$;
import com.typesafe.sslconfig.ssl.NoopHostnameVerifier;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.SSLDebugConfig$;
import com.typesafe.sslconfig.ssl.SSLLooseConfig;
import com.typesafe.sslconfig.ssl.SSLLooseConfig$;
import com.typesafe.sslconfig.ssl.SSLParametersConfig;
import com.typesafe.sslconfig.ssl.SSLParametersConfig$;
import com.typesafe.sslconfig.ssl.TrustManagerConfig;
import com.typesafe.sslconfig.ssl.TrustManagerConfig$;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.ScalaRunTime$;

public final class SSLConfigSettings$ {
    public static final SSLConfigSettings$ MODULE$ = new SSLConfigSettings$();

    public boolean $lessinit$greater$default$1() {
        return false;
    }

    public String $lessinit$greater$default$2() {
        return "TLSv1.2";
    }

    public Option<Object> $lessinit$greater$default$3() {
        return None$.MODULE$;
    }

    public Option<Seq<URL>> $lessinit$greater$default$4() {
        return None$.MODULE$;
    }

    public Option<Seq<String>> $lessinit$greater$default$5() {
        return None$.MODULE$;
    }

    public Option<Seq<String>> $lessinit$greater$default$6() {
        return new Some<Object>(package$.MODULE$.List().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"})));
    }

    public Seq<String> $lessinit$greater$default$7() {
        return package$.MODULE$.Nil();
    }

    public SSLParametersConfig $lessinit$greater$default$8() {
        return SSLParametersConfig$.MODULE$.apply();
    }

    public KeyManagerConfig $lessinit$greater$default$9() {
        return KeyManagerConfig$.MODULE$.apply();
    }

    public TrustManagerConfig $lessinit$greater$default$10() {
        return TrustManagerConfig$.MODULE$.apply();
    }

    public Class<? extends HostnameVerifier> $lessinit$greater$default$11() {
        return NoopHostnameVerifier.class;
    }

    public Option<SecureRandom> $lessinit$greater$default$12() {
        return None$.MODULE$;
    }

    public SSLDebugConfig $lessinit$greater$default$13() {
        return SSLDebugConfig$.MODULE$.apply();
    }

    public SSLLooseConfig $lessinit$greater$default$14() {
        return SSLLooseConfig$.MODULE$.apply();
    }

    public SSLConfigSettings apply() {
        return new SSLConfigSettings(this.$lessinit$greater$default$1(), this.$lessinit$greater$default$2(), this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5(), this.$lessinit$greater$default$6(), this.$lessinit$greater$default$7(), this.$lessinit$greater$default$8(), this.$lessinit$greater$default$9(), this.$lessinit$greater$default$10(), this.$lessinit$greater$default$11(), this.$lessinit$greater$default$12(), this.$lessinit$greater$default$13(), this.$lessinit$greater$default$14());
    }

    public SSLConfigSettings getInstance() {
        return this.apply();
    }

    private SSLConfigSettings$() {
    }
}

