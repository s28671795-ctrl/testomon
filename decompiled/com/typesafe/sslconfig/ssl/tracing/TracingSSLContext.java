/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TracingSSLContextSpi;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import javax.net.ssl.SSLContext;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2Q!\u0002\u0004\u0001\u0015AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I!\u0005\u0005\t9\u0001\u0011\t\u0011)A\u0005;!A\u0011\u0005\u0001B\u0001B\u0003-!\u0005C\u0003)\u0001\u0011\u0005\u0011FA\tUe\u0006\u001c\u0017N\\4T'2\u001buN\u001c;fqRT!a\u0002\u0005\u0002\u000fQ\u0014\u0018mY5oO*\u0011\u0011BC\u0001\u0004gNd'BA\u0006\r\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u000e\u001d\u0005AA/\u001f9fg\u00064WMC\u0001\u0010\u0003\r\u0019w.\\\n\u0003\u0001E\u0001\"A\u0005\r\u000e\u0003MQ!!\u0003\u000b\u000b\u0005U1\u0012a\u00018fi*\tq#A\u0003kCZ\f\u00070\u0003\u0002\u001a'\tQ1k\u0015'D_:$X\r\u001f;\u0002\u000f\r|g\u000e^3yi\u000e\u0001\u0011a\u00033fEV<7i\u001c8gS\u001e\u0004\"AH\u0010\u000e\u0003!I!\u0001\t\u0005\u0003\u001dM\u001bF\nR3ck\u001e\u001cuN\u001c4jO\u0006iAn\\4hKJ4\u0015m\u0019;pef\u0004\"a\t\u0014\u000e\u0003\u0011R!!\n\u0006\u0002\tU$\u0018\u000e\\\u0005\u0003O\u0011\u0012Q\u0002T8hO\u0016\u0014h)Y2u_JL\u0018A\u0002\u001fj]&$h\bF\u0002+]=\"\"aK\u0017\u0011\u00051\u0002Q\"\u0001\u0004\t\u000b\u0005\"\u00019\u0001\u0012\t\u000bi!\u0001\u0019A\t\t\u000bq!\u0001\u0019A\u000f")
public class TracingSSLContext
extends SSLContext {
    public TracingSSLContext(SSLContext context, SSLDebugConfig debugConfig, LoggerFactory loggerFactory) {
        super(new TracingSSLContextSpi((Function0<SSLContext>)((Object)new Serializable(context){
            private static final long serialVersionUID = 0L;
            private final SSLContext context$1;

            public final SSLContext apply() {
                return this.context$1;
            }
            {
                this.context$1 = context$1;
            }
        }), debugConfig, loggerFactory), context.getProvider(), context.getProtocol());
    }
}

