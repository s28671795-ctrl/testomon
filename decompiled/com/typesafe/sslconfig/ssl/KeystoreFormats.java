/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeystoreFormats$;
import java.security.KeyStore;
import java.security.cert.Certificate;
import scala.collection.IterableOnce;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a:Q\u0001B\u0003\t\u000291Q\u0001E\u0003\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002m\tqbS3zgR|'/\u001a$pe6\fGo\u001d\u0006\u0003\r\u001d\t1a]:m\u0015\tA\u0011\"A\u0005tg2\u001cwN\u001c4jO*\u0011!bC\u0001\tif\u0004Xm]1gK*\tA\"A\u0002d_6\u001c\u0001\u0001\u0005\u0002\u0010\u00035\tQAA\bLKf\u001cHo\u001c:f\r>\u0014X.\u0019;t'\t\t!\u0003\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00039\t\u0001\u0003\\8bI\u000e+'\u000f^5gS\u000e\fG/Z:\u0015\u0005q!\u0003CA\u000f#\u001b\u0005q\"BA\u0010!\u0003!\u0019XmY;sSRL(\"A\u0011\u0002\t)\fg/Y\u0005\u0003Gy\u0011\u0001bS3z'R|'/\u001a\u0005\u0006K\r\u0001\rAJ\u0001\u0006G\u0016\u0014Ho\u001d\t\u0004O=\u0012dB\u0001\u0015.\u001d\tIC&D\u0001+\u0015\tYS\"\u0001\u0004=e>|GOP\u0005\u0002+%\u0011a\u0006F\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0001\u0014GA\bUe\u00064XM]:bE2,wJ\\2f\u0015\tqC\u0003\u0005\u00024m5\tAG\u0003\u00026=\u0005!1-\u001a:u\u0013\t9DGA\u0006DKJ$\u0018NZ5dCR,\u0007")
public final class KeystoreFormats {
    public static KeyStore loadCertificates(IterableOnce<Certificate> certs) {
        return KeystoreFormats$.MODULE$.loadCertificates(certs);
    }
}

