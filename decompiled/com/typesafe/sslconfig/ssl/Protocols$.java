/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import scala.Predef$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.runtime.ScalaRunTime$;

public final class Protocols$ {
    public static final Protocols$ MODULE$ = new Protocols$();
    private static final Set<String> deprecatedProtocols = (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{"SSL", "SSLv2Hello", "SSLv3"}));
    private static final String[] recommendedProtocols = (String[])((Object[])new String[]{"TLSv1.2", "TLSv1.1", "TLSv1"});

    public Set<String> deprecatedProtocols() {
        return deprecatedProtocols;
    }

    public String[] recommendedProtocols() {
        return recommendedProtocols;
    }

    public String recommendedProtocol() {
        return "TLSv1.2";
    }

    private Protocols$() {
    }
}

