/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.impl.io.TcpIdleTimeout$;
import akka.stream.scaladsl.BidiFlow;
import akka.util.ByteString;
import java.net.InetSocketAddress;
import scala.Option;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-;a\u0001B\u0003\t\u0002-iaAB\b\u0006\u0011\u0003Y\u0001\u0003C\u0003\u0018\u0003\u0011\u0005\u0011\u0004C\u0003\u001b\u0003\u0011\u00051$\u0001\bUGBLE\r\\3US6,w.\u001e;\u000b\u0005\u00199\u0011AA5p\u0015\tA\u0011\"\u0001\u0003j[Bd'B\u0001\u0006\f\u0003\u0019\u0019HO]3b[*\tA\"\u0001\u0003bW.\f\u0007C\u0001\b\u0002\u001b\u0005)!A\u0004+da&#G.\u001a+j[\u0016|W\u000f^\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u00035\tQ!\u00199qYf$2\u0001\b\u00177!\u001di\u0002E\t\u0012#E!j\u0011A\b\u0006\u0003?%\t\u0001b]2bY\u0006$7\u000f\\\u0005\u0003Cy\u0011\u0001BQ5eS\u001acwn\u001e\t\u0003G\u0019j\u0011\u0001\n\u0006\u0003K-\tA!\u001e;jY&\u0011q\u0005\n\u0002\u000b\u0005f$Xm\u0015;sS:<\u0007CA\u0015+\u001b\u0005Y\u0011BA\u0016\f\u0005\u001dqu\u000e^+tK\u0012DQ!L\u0002A\u00029\n1\"\u001b3mKRKW.Z8viB\u0011q\u0006N\u0007\u0002a)\u0011\u0011GM\u0001\tIV\u0014\u0018\r^5p]*\u00111gE\u0001\u000bG>t7-\u001e:sK:$\u0018BA\u001b1\u000591\u0015N\\5uK\u0012+(/\u0019;j_:DQaN\u0002A\u0002a\nQB]3n_R,\u0017\t\u001a3sKN\u001c\bc\u0001\n:w%\u0011!h\u0005\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005q\nU\"A\u001f\u000b\u0005yz\u0014a\u00018fi*\t\u0001)\u0001\u0003kCZ\f\u0017B\u0001\">\u0005EIe.\u001a;T_\u000e\\W\r^!eIJ,7o\u001d\u0015\u0003\u0003\u0011\u0003\"!\u0012%\u000e\u0003\u0019S!aR\u0006\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002J\r\nY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001A\t")
@InternalApi
public final class TcpIdleTimeout {
    public static BidiFlow<ByteString, ByteString, ByteString, ByteString, NotUsed> apply(FiniteDuration idleTimeout, Option<InetSocketAddress> remoteAddress) {
        return TcpIdleTimeout$.MODULE$.apply(idleTimeout, remoteAddress);
    }
}

