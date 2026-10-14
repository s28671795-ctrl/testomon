/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.BidiShape;
import akka.stream.BidiShape$;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.TLSProtocol;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.BidiFlow$;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$;
import akka.util.ByteString;
import java.io.Serializable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import scala.Function1;

public final class TLSPlacebo$ {
    public static final TLSPlacebo$ MODULE$ = new TLSPlacebo$();
    private static final SSLSession dummySession = SSLContext.getDefault().createSSLEngine().getSession();
    private static final BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> instance = BidiFlow$.MODULE$.fromGraph(GraphDSL$.MODULE$.create((Function1<GraphDSL.Builder, BidiShape> & Serializable)b -> {
        FlowShape top = (FlowShape)b.add((Graph)((Object)Flow$.MODULE$.apply().collect(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final <A1 extends TLSProtocol.SslTlsOutbound, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof TLSProtocol.SendBytes) {
                    TLSProtocol.SendBytes sendBytes = (TLSProtocol.SendBytes)A1;
                    ByteString bytes = sendBytes.bytes();
                    return (B1)bytes;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(TLSProtocol.SslTlsOutbound x1) {
                TLSProtocol.SslTlsOutbound sslTlsOutbound = x1;
                return sslTlsOutbound instanceof TLSProtocol.SendBytes;
            }
        })));
        FlowShape bottom = (FlowShape)b.add((Graph)((Object)Flow$.MODULE$.apply().map((Function1<ByteString, TLSProtocol.SessionBytes> & Serializable)x$6 -> new TLSProtocol.SessionBytes(MODULE$.dummySession(), (ByteString)x$6))));
        return BidiShape$.MODULE$.fromFlows(top, bottom);
    }));

    public SSLSession dummySession() {
        return dummySession;
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> apply() {
        return this.instance();
    }

    private BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> instance() {
        return instance;
    }

    private TLSPlacebo$() {
    }
}

