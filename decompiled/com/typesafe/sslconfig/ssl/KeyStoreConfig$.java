/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyStoreConfig;
import java.security.KeyStore;
import java.util.Optional;
import scala.None$;
import scala.Option;
import scala.Option$;

public final class KeyStoreConfig$ {
    public static final KeyStoreConfig$ MODULE$ = new KeyStoreConfig$();

    public boolean $lessinit$greater$default$3() {
        return false;
    }

    public Option<String> $lessinit$greater$default$4() {
        return None$.MODULE$;
    }

    public String $lessinit$greater$default$5() {
        return KeyStore.getDefaultType();
    }

    public KeyStoreConfig apply(Option<String> data, Option<String> filePath) {
        return new KeyStoreConfig(data, filePath, this.$lessinit$greater$default$3(), this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5());
    }

    public KeyStoreConfig getInstance(Optional<String> data, Optional<String> filePath) {
        return this.apply(Option$.MODULE$.apply(data.orElse(null)), Option$.MODULE$.apply(filePath.orElse(null)));
    }

    private KeyStoreConfig$() {
    }
}

