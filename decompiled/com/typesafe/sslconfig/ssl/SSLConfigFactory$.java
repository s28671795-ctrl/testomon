/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.config.Config;
import com.typesafe.sslconfig.ssl.SSLConfigParser;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.ssl.SSLConfigSettings$;
import com.typesafe.sslconfig.util.EnrichedConfig$;
import com.typesafe.sslconfig.util.LoggerFactory;
import scala.None$;
import scala.Some;

public final class SSLConfigFactory$ {
    public static final SSLConfigFactory$ MODULE$ = new SSLConfigFactory$();

    public SSLConfigSettings parse(Config config) {
        return new SSLConfigParser(EnrichedConfig$.MODULE$.apply(config), this.getClass().getClassLoader(), None$.MODULE$).parse();
    }

    public SSLConfigSettings parse(Config config, LoggerFactory loggerFactory) {
        return new SSLConfigParser(EnrichedConfig$.MODULE$.apply(config), this.getClass().getClassLoader(), new Some<LoggerFactory>(loggerFactory)).parse();
    }

    public SSLConfigSettings defaultConfig() {
        return SSLConfigSettings$.MODULE$.apply();
    }

    private SSLConfigFactory$() {
    }
}

