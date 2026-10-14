/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.UniformFanInShape;
import akka.stream.javadsl.MergeLatest$;
import akka.stream.stage.GraphStage;
import java.util.List;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-;Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQ!G\u0001\u0005\u0002\r\u000b1\"T3sO\u0016d\u0015\r^3ti*\u0011q\u0001C\u0001\bU\u00064\u0018\rZ:m\u0015\tI!\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u0001\"AD\u0001\u000e\u0003\u0019\u00111\"T3sO\u0016d\u0015\r^3tiN\u0011\u0011!\u0005\t\u0003%Ui\u0011a\u0005\u0006\u0002)\u0005)1oY1mC&\u0011ac\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005i\u0011AB2sK\u0006$X-\u0006\u0002\u001cQQ\u0019A$\u000f \u0011\u0007u\u0001#%D\u0001\u001f\u0015\ty\u0002\"A\u0003ti\u0006<W-\u0003\u0002\"=\tQqI]1qQN#\u0018mZ3\u0011\t\r\"c%M\u0007\u0002\u0011%\u0011Q\u0005\u0003\u0002\u0012+:Lgm\u001c:n\r\u0006t\u0017J\\*iCB,\u0007CA\u0014)\u0019\u0001!Q!K\u0002C\u0002)\u0012\u0011\u0001V\t\u0003W9\u0002\"A\u0005\u0017\n\u00055\u001a\"a\u0002(pi\"Lgn\u001a\t\u0003%=J!\u0001M\n\u0003\u0007\u0005s\u0017\u0010E\u00023o\u0019j\u0011a\r\u0006\u0003iU\nA!\u001e;jY*\ta'\u0001\u0003kCZ\f\u0017B\u0001\u001d4\u0005\u0011a\u0015n\u001d;\t\u000bi\u001a\u0001\u0019A\u001e\u0002\u0015%t\u0007/\u001e;Q_J$8\u000f\u0005\u0002\u0013y%\u0011Qh\u0005\u0002\u0004\u0013:$\b\"B \u0004\u0001\u0004\u0001\u0015!D3bO\u0016\u00148i\\7qY\u0016$X\r\u0005\u0002\u0013\u0003&\u0011!i\u0005\u0002\b\u0005>|G.Z1o+\t!\u0005\n\u0006\u0002F\u0015B\u0019Q\u0004\t$\u0011\t\r\"s)\u0013\t\u0003O!#Q!\u000b\u0003C\u0002)\u00022AM\u001cH\u0011\u0015QD\u00011\u0001<\u0001")
public final class MergeLatest {
    public static <T> GraphStage<UniformFanInShape<T, List<T>>> create(int inputPorts) {
        return MergeLatest$.MODULE$.create(inputPorts);
    }

    public static <T> GraphStage<UniformFanInShape<T, List<T>>> create(int inputPorts, boolean eagerComplete) {
        return MergeLatest$.MODULE$.create(inputPorts, eagerComplete);
    }
}

