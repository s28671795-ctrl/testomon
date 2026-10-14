/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.FakeSSLTools$;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U:Q\u0001B\u0003\t\u000291Q\u0001E\u0003\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002m\tABR1lKN\u001bF\nV8pYNT!AB\u0004\u0002\u0007M\u001cHN\u0003\u0002\t\u0013\u0005I1o\u001d7d_:4\u0017n\u001a\u0006\u0003\u0015-\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0002\u0019\u0005\u00191m\\7\u0004\u0001A\u0011q\"A\u0007\u0002\u000b\taa)Y6f'NcEk\\8mgN\u0011\u0011A\u0005\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005q\u0011\u0001\u00062vS2$7i\u001c8uKb$\u0018I\u001c3UeV\u001cH\u000f\u0006\u0002\u001dWA!1#H\u0010)\u0013\tqBC\u0001\u0004UkBdWM\r\t\u0003A\u0019j\u0011!\t\u0006\u0003\r\tR!a\t\u0013\u0002\u00079,GOC\u0001&\u0003\u0015Q\u0017M^1y\u0013\t9\u0013E\u0001\u0006T'2\u001buN\u001c;fqR\u0004\"\u0001I\u0015\n\u0005)\n#\u0001\u0005-6ae\"&/^:u\u001b\u0006t\u0017mZ3s\u0011\u0015a3\u00011\u0001.\u0003!YW-_*u_J,\u0007C\u0001\u00184\u001b\u0005y#B\u0001\u00192\u0003!\u0019XmY;sSRL(\"\u0001\u001a\u0002\t)\fg/Y\u0005\u0003i=\u0012\u0001bS3z'R|'/\u001a")
public final class FakeSSLTools {
    public static Tuple2<SSLContext, X509TrustManager> buildContextAndTrust(KeyStore keyStore) {
        return FakeSSLTools$.MODULE$.buildContextAndTrust(keyStore);
    }
}

