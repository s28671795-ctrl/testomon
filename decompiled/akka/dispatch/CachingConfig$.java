/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.CachingConfig;
import akka.dispatch.CachingConfig$ValuePathEntry$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public final class CachingConfig$ {
    public static final CachingConfig$ MODULE$ = new CachingConfig$();
    private static final Config emptyConfig = ConfigFactory.empty();
    private static final CachingConfig.ValuePathEntry invalidPathEntry = new CachingConfig.ValuePathEntry(false, true, CachingConfig$ValuePathEntry$.MODULE$.apply$default$3());
    private static final CachingConfig.ValuePathEntry nonExistingPathEntry = new CachingConfig.ValuePathEntry(true, false, CachingConfig$ValuePathEntry$.MODULE$.apply$default$3());
    private static final CachingConfig.ValuePathEntry emptyPathEntry = new CachingConfig.ValuePathEntry(true, true, CachingConfig$ValuePathEntry$.MODULE$.apply$default$3());

    public Config emptyConfig() {
        return emptyConfig;
    }

    public CachingConfig.ValuePathEntry invalidPathEntry() {
        return invalidPathEntry;
    }

    public CachingConfig.ValuePathEntry nonExistingPathEntry() {
        return nonExistingPathEntry;
    }

    public CachingConfig.ValuePathEntry emptyPathEntry() {
        return emptyPathEntry;
    }

    private CachingConfig$() {
    }
}

