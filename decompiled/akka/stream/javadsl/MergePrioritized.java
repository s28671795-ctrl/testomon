/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.MergePrioritized$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00059<Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaG\u0001\u0005\u0002qBQaG\u0001\u0005\u0002]CQaG\u0001\u0005\u0002\r\f\u0001#T3sO\u0016\u0004&/[8sSRL'0\u001a3\u000b\u0005%Q\u0011a\u00026bm\u0006$7\u000f\u001c\u0006\u0003\u00171\taa\u001d;sK\u0006l'\"A\u0007\u0002\t\u0005\\7.Y\u0002\u0001!\t\u0001\u0012!D\u0001\t\u0005AiUM]4f!JLwN]5uSj,Gm\u0005\u0002\u0002'A\u0011AcF\u0007\u0002+)\ta#A\u0003tG\u0006d\u0017-\u0003\u0002\u0019+\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\b\u0002\r\r\u0014X-\u0019;f+\tir\u0005\u0006\u0002\u001fiA!q\u0004\t\u00121\u001b\u0005Q\u0011BA\u0011\u000b\u0005\u00159%/\u00199i!\u0011y2%J\u0013\n\u0005\u0011R!!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u0011ae\n\u0007\u0001\t\u0015A3A1\u0001*\u0005\u0005!\u0016C\u0001\u0016.!\t!2&\u0003\u0002-+\t9aj\u001c;iS:<\u0007C\u0001\u000b/\u0013\tySCA\u0002B]f\u0004\"!\r\u001a\u000e\u00031I!a\r\u0007\u0003\u000f9{G/V:fI\")Qg\u0001a\u0001m\u0005Q\u0001O]5pe&$\u0018.Z:\u0011\u0007Q9\u0014(\u0003\u00029+\t)\u0011I\u001d:bsB\u0011ACO\u0005\u0003wU\u00111!\u00138u+\ti\u0014\tF\u0002?\u0005Z\u0003Ba\b\u0011@aA!qd\t!A!\t1\u0013\tB\u0003)\t\t\u0007\u0011\u0006C\u0003D\t\u0001\u0007A)A\u0003dY\u0006T(\u0010E\u0002F\u0019\u0002s!A\u0012&\u0011\u0005\u001d+R\"\u0001%\u000b\u0005%s\u0011A\u0002\u001fs_>$h(\u0003\u0002L+\u00051\u0001K]3eK\u001aL!!\u0014(\u0003\u000b\rc\u0017m]:\u000b\u0005-+\u0002F\u0001\"Q!\t\tF+D\u0001S\u0015\t\u0019F\"\u0001\u0003vi&d\u0017BA+S\u0005\u0019)h.^:fI\")Q\u0007\u0002a\u0001mU\u0011\u0001\f\u0018\u000b\u00043vs\u0006\u0003B\u0010!5B\u0002BaH\u0012\\7B\u0011a\u0005\u0018\u0003\u0006Q\u0015\u0011\r!\u000b\u0005\u0006k\u0015\u0001\rA\u000e\u0005\u0006?\u0016\u0001\r\u0001Y\u0001\u000eK\u0006<WM]\"p[BdW\r^3\u0011\u0005Q\t\u0017B\u00012\u0016\u0005\u001d\u0011un\u001c7fC:,\"\u0001\u001a5\u0015\t\u0015LG.\u001c\t\u0005?\u00012\u0007\u0007\u0005\u0003 G\u001d<\u0007C\u0001\u0014i\t\u0015AcA1\u0001*\u0011\u0015\u0019e\u00011\u0001k!\r)Ej\u001a\u0015\u0003SBCQ!\u000e\u0004A\u0002YBQa\u0018\u0004A\u0002\u0001\u0004")
public final class MergePrioritized {
    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int[] priorities, boolean eagerComplete) {
        return MergePrioritized$.MODULE$.create(clazz, priorities, eagerComplete);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int[] priorities, boolean eagerComplete) {
        return MergePrioritized$.MODULE$.create(priorities, eagerComplete);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int[] priorities) {
        return MergePrioritized$.MODULE$.create(clazz, priorities);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int[] priorities) {
        return MergePrioritized$.MODULE$.create(priorities);
    }
}

