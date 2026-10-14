/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.NoopHostnameVerifier;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3A\u0001C\u0005\u0001%!AA\u0005\u0001B\u0001B\u0003%Q\u0005C\u0003,\u0001\u0011\u0005A\u0006C\u00041\u0001\t\u0007I\u0011B\u0019\t\rU\u0002\u0001\u0015!\u00033\u0011\u001d1\u0004A1A\u0005\n]Baa\u000f\u0001!\u0002\u0013A\u0004\"\u0002\u001f\u0001\t\u0003j$a\t#jg\u0006\u0014G.\u001a3D_6\u0004H.Y5oS:<\u0007j\\:u]\u0006lWMV3sS\u001aLWM\u001d\u0006\u0003\u0015-\t1a]:m\u0015\taQ\"A\u0005tg2\u001cwN\u001c4jO*\u0011abD\u0001\tif\u0004Xm]1gK*\t\u0001#A\u0002d_6\u001c\u0001aE\u0002\u0001'm\u0001\"\u0001F\r\u000e\u0003UQ!AF\f\u0002\t1\fgn\u001a\u0006\u00021\u0005!!.\u0019<b\u0013\tQRC\u0001\u0004PE*,7\r\u001e\t\u00039\tj\u0011!\b\u0006\u0003\u0015yQ!a\b\u0011\u0002\u00079,GOC\u0001\"\u0003\u0015Q\u0017M^1y\u0013\t\u0019SD\u0001\tI_N$h.Y7f-\u0016\u0014\u0018NZ5fe\u0006AQn\u001b'pO\u001e,'\u000f\u0005\u0002'S5\tqE\u0003\u0002)\u0017\u0005!Q\u000f^5m\u0013\tQsEA\u0007M_\u001e<WM\u001d$bGR|'/_\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00055z\u0003C\u0001\u0018\u0001\u001b\u0005I\u0001\"\u0002\u0013\u0003\u0001\u0004)\u0013A\u00027pO\u001e,'/F\u00013!\t13'\u0003\u00025O\taaj\u001c#faNdunZ4fe\u00069An\\4hKJ\u0004\u0013a\u00063fM\u0006,H\u000e\u001e%pgRt\u0017-\\3WKJLg-[3s+\u0005A\u0004C\u0001\u0018:\u0013\tQ\u0014B\u0001\u000bO_>\u0004\bj\\:u]\u0006lWMV3sS\u001aLWM]\u0001\u0019I\u00164\u0017-\u001e7u\u0011>\u001cHO\\1nKZ+'/\u001b4jKJ\u0004\u0013A\u0002<fe&4\u0017\u0010F\u0002?\tF\u0003\"a\u0010\"\u000e\u0003\u0001S\u0011!Q\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0007\u0002\u0013qAQ8pY\u0016\fg\u000eC\u0003F\u000f\u0001\u0007a)\u0001\u0005i_N$h.Y7f!\t9eJ\u0004\u0002I\u0019B\u0011\u0011\nQ\u0007\u0002\u0015*\u00111*E\u0001\u0007yI|w\u000e\u001e \n\u00055\u0003\u0015A\u0002)sK\u0012,g-\u0003\u0002P!\n11\u000b\u001e:j]\u001eT!!\u0014!\t\u000bI;\u0001\u0019A*\u0002\u0015M\u001cHnU3tg&|g\u000e\u0005\u0002\u001d)&\u0011Q+\b\u0002\u000b'Nc5+Z:tS>t\u0007")
public class DisabledComplainingHostnameVerifier
implements HostnameVerifier {
    private final NoDepsLogger logger;
    private final NoopHostnameVerifier defaultHostnameVerifier;

    private NoDepsLogger logger() {
        return this.logger;
    }

    private NoopHostnameVerifier defaultHostnameVerifier() {
        return this.defaultHostnameVerifier;
    }

    @Override
    public boolean verify(String hostname, SSLSession sslSession) {
        block0: {
            boolean hostNameMatches = this.defaultHostnameVerifier().verify(hostname, sslSession);
            if (hostNameMatches) break block0;
            String msg = new StringBuilder(165).append(new StringBuilder(43).append("Hostname verification failed on hostname ").append(hostname).append(", ").toString()).append("but the connection was accepted because ssl-config.loose.disableHostnameVerification is enabled. ").append("Please fix the X.509 certificate on the host to remove this warning.").toString();
            this.logger().warn(msg);
        }
        return true;
    }

    public DisabledComplainingHostnameVerifier(LoggerFactory mkLogger) {
        this.logger = mkLogger.apply(this.getClass());
        this.defaultHostnameVerifier = new NoopHostnameVerifier();
    }
}

