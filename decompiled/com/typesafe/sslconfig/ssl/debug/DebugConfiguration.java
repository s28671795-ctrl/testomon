/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.debug;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3A\u0001C\u0005\u0001)!A1\u0004\u0001B\u0001B\u0003%A\u0004C\u0003#\u0001\u0011\u00051\u0005C\u0004(\u0001\t\u0007I\u0011\u0002\u0015\t\r1\u0002\u0001\u0015!\u0003*\u0011\u0015i\u0003\u0001\"\u0001/\u0011\u0015A\u0004\u0001\"\u0001:\u0011\u0015Y\u0004\u0001\"\u0001=\u0005I!UMY;h\u0007>tg-[4ve\u0006$\u0018n\u001c8\u000b\u0005)Y\u0011!\u00023fEV<'B\u0001\u0007\u000e\u0003\r\u00198\u000f\u001c\u0006\u0003\u001d=\t\u0011b]:mG>tg-[4\u000b\u0005A\t\u0012\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003I\t1aY8n\u0007\u0001\u0019\"\u0001A\u000b\u0011\u0005YIR\"A\f\u000b\u0003a\tQa]2bY\u0006L!AG\f\u0003\r\u0005s\u0017PU3g\u0003!i7\u000eT8hO\u0016\u0014\bCA\u000f!\u001b\u0005q\"BA\u0010\u000e\u0003\u0011)H/\u001b7\n\u0005\u0005r\"!\u0004'pO\u001e,'OR1di>\u0014\u00180\u0001\u0004=S:LGO\u0010\u000b\u0003I\u0019\u0002\"!\n\u0001\u000e\u0003%AQa\u0007\u0002A\u0002q\ta\u0001\\8hO\u0016\u0014X#A\u0015\u0011\u0005uQ\u0013BA\u0016\u001f\u00051qu\u000eR3qg2{wmZ3s\u0003\u001dawnZ4fe\u0002\n\u0011bY8oM&<WO]3\u0015\u0005=\u0012\u0004C\u0001\f1\u0013\t\ttC\u0001\u0003V]&$\b\"B\u001a\u0006\u0001\u0004!\u0014!\u00013\u0011\u0005U2T\"A\u0006\n\u0005]Z!AD*T\u0019\u0012+'-^4D_:4\u0017nZ\u0001\u0017G>tg-[4ve\u0016T\u0015M^1y\u001d\u0016$H)\u001a2vOR\u0011qF\u000f\u0005\u0006g\u0019\u0001\r\u0001N\u0001\u001bG>tg-[4ve\u0016T\u0015M^1TK\u000e,(/\u001b;z\t\u0016\u0014Wo\u001a\u000b\u0003_uBQaM\u0004A\u0002QBc\u0001A C\u0007\u00163\u0005C\u0001\fA\u0013\t\tuC\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001E\u0003%$UMY;h\u0007>tg-[4ve\u0006$\u0018n\u001c8!Q\u0006\u001c\bEY3f]\u0002\"W\r\u001d:fG\u0006$X\r\u001a\u0011b]\u0012\u0004Cm\\3tA9|G\u000f[5oO:\u0002\u0003\u0005\u00157fCN,\u0007%^:fAQDW\r\t6bm\u0006DhF\\3u]\u0011,'-^4!gf\u001cH/Z7!aJ|\u0007/\u001a:us:\nQa]5oG\u0016\f\u0013aR\u0001\u0006a9\"d\u0006\r")
public class DebugConfiguration {
    private final NoDepsLogger logger;

    private NoDepsLogger logger() {
        return this.logger;
    }

    public void configure(SSLDebugConfig d) {
        this.logger().warn("DebugConfiguration has been deprecated and does nothing.  Please use the javax.net.debug system property.");
    }

    public void configureJavaxNetDebug(SSLDebugConfig d) {
        this.logger().warn("DebugConfiguration has been deprecated and does nothing.  Please use the javax.net.debug system property.");
    }

    public void configureJavaSecurityDebug(SSLDebugConfig d) {
        this.logger().warn("DebugConfiguration has been deprecated and does nothing.  Please use the javax.net.debug system property.");
    }

    public DebugConfiguration(LoggerFactory mkLogger) {
        this.logger = mkLogger.apply(this.getClass());
    }
}

