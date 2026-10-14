/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.ZipWithN$;
import java.util.List;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-;Q\u0001B\u0003\t\u000211QAD\u0003\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e\t\u0001BW5q/&$\bN\u0014\u0006\u0003\r\u001d\tqA[1wC\u0012\u001cHN\u0003\u0002\t\u0013\u000511\u000f\u001e:fC6T\u0011AC\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u00055\tQ\"A\u0003\u0003\u0011iK\u0007oV5uQ:\u001b\"!\u0001\t\u0011\u0005E!R\"\u0001\n\u000b\u0003M\tQa]2bY\u0006L!!\u0006\n\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tA\"\u0001\u0004de\u0016\fG/Z\u000b\u00045\u0011rCcA\u000e5\rB!A$H\u00101\u001b\u00059\u0011B\u0001\u0010\b\u0005\u00159%/\u00199i!\u0011a\u0002EI\u0017\n\u0005\u0005:!!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u00111\u0005\n\u0007\u0001\t\u0015)3A1\u0001'\u0005\u0005\t\u0015CA\u0014+!\t\t\u0002&\u0003\u0002*%\t9aj\u001c;iS:<\u0007CA\t,\u0013\ta#CA\u0002B]f\u0004\"a\t\u0018\u0005\u000b=\u001a!\u0019\u0001\u0014\u0003\u0003=\u0003\"!\r\u001a\u000e\u0003%I!aM\u0005\u0003\u000f9{G/V:fI\")Qg\u0001a\u0001m\u00051!0\u001b9qKJ\u0004Ba\u000e\u001f?[5\t\u0001H\u0003\u0002:u\u0005Aa-\u001e8di&|gN\u0003\u0002<\u0013\u0005!!.\u00199j\u0013\ti\u0004H\u0001\u0005Gk:\u001cG/[8o!\ryDII\u0007\u0002\u0001*\u0011\u0011IQ\u0001\u0005kRLGNC\u0001D\u0003\u0011Q\u0017M^1\n\u0005\u0015\u0003%\u0001\u0002'jgRDQaR\u0002A\u0002!\u000b\u0011A\u001c\t\u0003#%K!A\u0013\n\u0003\u0007%sG\u000f")
public final class ZipWithN {
    public static <A, O> Graph<UniformFanInShape<A, O>, NotUsed> create(Function<List<A>, O> zipper, int n) {
        return ZipWithN$.MODULE$.create(zipper, n);
    }
}

