/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import java.io.Serializable;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.X509Certificate;
import java.util.List;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.util.Properties$;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public X509Certificate certificate2X509Certificate(Certificate cert) {
        return (X509Certificate)cert;
    }

    public List<Certificate> arrayCertsToListCerts(Certificate[] chain) {
        return JavaConverters$.MODULE$.seqAsJavaListConverter(Predef$.MODULE$.wrapRefArray((Object[])chain).toList()).asJava();
    }

    public PKIXCertPathValidatorResult certResult2PKIXResult(CertPathValidatorResult result) {
        return (PKIXCertPathValidatorResult)result;
    }

    public Seq<String> debugChain(X509Certificate[] chain) {
        return Predef$.MODULE$.copyArrayToImmutableIndexedSeq(ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps(chain), (Function1<X509Certificate, String> & Serializable)cert -> MODULE$.debugCert((X509Certificate)cert), ClassTag$.MODULE$.apply(String.class)));
    }

    public String debugCert(X509Certificate cert) {
        return new StringBuilder(44).append("X509Certificate(serialNumber = ").append(cert.getSerialNumber().toString(16)).append(", subject = ").append(cert.getSubjectDN().getName()).append(")").toString();
    }

    public boolean isOpenJdk() {
        return Properties$.MODULE$.javaVmName().contains("OpenJDK");
    }

    private package$() {
    }
}

