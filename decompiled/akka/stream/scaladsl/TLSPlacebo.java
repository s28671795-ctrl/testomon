/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.TLSProtocol;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.TLSPlacebo$;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00051;Q\u0001C\u0005\t\u0002A1QAE\u0005\t\u0002MAQAG\u0001\u0005\u0002mA\u0001\u0002H\u0001C\u0002\u0013\u0005Q\"\b\u0005\u0007Q\u0005\u0001\u000b\u0011\u0002\u0010\t\u000b%\nA\u0011\u0001\u0016\t\u000f%\u000b!\u0019!C\u0005\u0015\"11*\u0001Q\u0001\n-\n!\u0002\u0016'T!2\f7-\u001a2p\u0015\tQ1\"\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b\u0007\u0001\u0001\"!E\u0001\u000e\u0003%\u0011!\u0002\u0016'T!2\f7-\u001a2p'\t\tA\u0003\u0005\u0002\u001615\taCC\u0001\u0018\u0003\u0015\u00198-\u00197b\u0013\tIbC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003A\tA\u0002Z;n[f\u001cVm]:j_:,\u0012A\b\t\u0003?\u0019j\u0011\u0001\t\u0006\u0003C\t\n1a]:m\u0015\t\u0019C%A\u0002oKRT\u0011!J\u0001\u0006U\u00064\u0018\r_\u0005\u0003O\u0001\u0012!bU*M'\u0016\u001c8/[8o\u00035!W/\\7z'\u0016\u001c8/[8oA\u0005)\u0011\r\u001d9msR\t1\u0006E\u0004\u0012Y9bDHQ#\n\u00055J!\u0001\u0003\"jI&4En\\<\u0011\u0005=JdB\u0001\u00198\u001d\t\tdG\u0004\u00023k5\t1G\u0003\u00025\u001f\u00051AH]8pizJ\u0011AD\u0005\u0003\u00195I!\u0001O\u0006\u0002\u0017Qc5\u000b\u0015:pi>\u001cw\u000e\\\u0005\u0003um\u0012abU:m)2\u001cx*\u001e;c_VtGM\u0003\u00029\u0017A\u0011Q\bQ\u0007\u0002})\u0011q(D\u0001\u0005kRLG.\u0003\u0002B}\tQ!)\u001f;f'R\u0014\u0018N\\4\u0011\u0005=\u001a\u0015B\u0001#<\u00051\u0019Vm]:j_:\u0014\u0015\u0010^3t!\t1u)D\u0001\u000e\u0013\tAUBA\u0004O_R,6/\u001a3\u0002\u0011%t7\u000f^1oG\u0016,\u0012aK\u0001\nS:\u001cH/\u00198dK\u0002\u0002")
public final class TLSPlacebo {
    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> apply() {
        return TLSPlacebo$.MODULE$.apply();
    }
}

