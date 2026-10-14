/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.TrustStoreConfig;
import java.security.KeyStore;
import java.util.Optional;
import scala.None$;
import scala.Option;
import scala.Option$;

public final class TrustStoreConfig$ {
    public static final TrustStoreConfig$ MODULE$ = new TrustStoreConfig$();

    public boolean $lessinit$greater$default$4() {
        return false;
    }

    public String $lessinit$greater$default$5() {
        return KeyStore.getDefaultType();
    }

    public TrustStoreConfig apply(Option<String> data, Option<String> filePath) {
        return new TrustStoreConfig(data, filePath, None$.MODULE$, this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5());
    }

    public TrustStoreConfig apply(Option<String> data, Option<String> filePath, Option<String> password) {
        return new TrustStoreConfig(data, filePath, password, this.$lessinit$greater$default$4(), this.$lessinit$greater$default$5());
    }

    public TrustStoreConfig getInstance(Optional<String> data, Optional<String> filePath) {
        return this.apply(Option$.MODULE$.apply(data.orElse(null)), Option$.MODULE$.apply(filePath.orElse(null)));
    }

    private TrustStoreConfig$() {
    }
}

