/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.package$;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.cert.Certificate;
import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.IterableOnce$;
import scala.collection.IterableOnceExtensionMethods$;
import scala.runtime.BoxedUnit;

public final class KeystoreFormats$ {
    public static final KeystoreFormats$ MODULE$ = new KeystoreFormats$();

    /*
     * WARNING - void declaration
     */
    public KeyStore loadCertificates(IterableOnce<Certificate> certs) {
        void var2_2;
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(null);
        IterableOnceExtensionMethods$.MODULE$.foreach$extension(IterableOnce$.MODULE$.iterableOnceExtensionMethods(certs), (Function1<Certificate, Object> & Serializable)cert -> {
            KeystoreFormats$.$anonfun$loadCertificates$1(keystore, cert);
            return BoxedUnit.UNIT;
        });
        return var2_2;
    }

    public static final /* synthetic */ void $anonfun$loadCertificates$1(KeyStore keystore$1, Certificate cert) {
        String alias = package$.MODULE$.certificate2X509Certificate(cert).getSubjectX500Principal().getName();
        keystore$1.setCertificateEntry(alias, cert);
    }

    private KeystoreFormats$() {
    }
}

