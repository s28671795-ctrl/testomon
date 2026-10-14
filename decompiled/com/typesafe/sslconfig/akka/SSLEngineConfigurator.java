/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.akka;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2qAA\u0002\u0011\u0002G\u0005A\u0002C\u0003\u0014\u0001\u0019\u0005ACA\u000bT'2+enZ5oK\u000e{gNZ5hkJ\fGo\u001c:\u000b\u0005\u0011)\u0011\u0001B1lW\u0006T!AB\u0004\u0002\u0013M\u001cHnY8oM&<'B\u0001\u0005\n\u0003!!\u0018\u0010]3tC\u001a,'\"\u0001\u0006\u0002\u0007\r|Wn\u0001\u0001\u0014\u0005\u0001i\u0001C\u0001\b\u0012\u001b\u0005y!\"\u0001\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iy!AB!osJ+g-A\u0005d_:4\u0017nZ;sKR\u0019QcH\u0011\u0011\u0005YiR\"A\f\u000b\u0005aI\u0012aA:tY*\u0011!dG\u0001\u0004]\u0016$(\"\u0001\u000f\u0002\u000b)\fg/\u0019=\n\u0005y9\"!C*T\u0019\u0016sw-\u001b8f\u0011\u0015\u0001\u0013\u00011\u0001\u0016\u0003\u0019)gnZ5oK\")!%\u0001a\u0001G\u0005Q1o\u001d7D_:$X\r\u001f;\u0011\u0005Y!\u0013BA\u0013\u0018\u0005)\u00196\u000bT\"p]R,\u0007\u0010\u001e\u0015\u0007\u0001\u001dR3&\f\u0018\u0011\u00059A\u0013BA\u0015\u0010\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005a\u0013AX+tK\u0002\"6\r\u001d\u0011b]\u0012\u0004C\u000bT*!o&$\b\u000eI*T\u0019\u0016sw-\u001b8fAA\f'/Y7fi\u0016\u00148\u000fI5ogR,\u0017\r\u001a\u0018!'\u0016$X\u000f\u001d\u0011uQ\u0016\u00043k\u0015'F]\u001eLg.\u001a\u0011xSRD\u0007E\\3fI\u0016$\u0007\u0005]1sC6,G/\u001a:t]\u0005)1/\u001b8dK\u0006\nq&A\u00033]Yr\u0003\u0007")
public interface SSLEngineConfigurator {
    public SSLEngine configure(SSLEngine var1, SSLContext var2);
}

