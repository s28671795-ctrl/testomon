/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.Concat$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015<Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaG\u0001\u0005\u0002QBQaG\u0001\u0005\u0002}BQaG\u0001\u0005\u0002-\u000baaQ8oG\u0006$(BA\u0005\u000b\u0003\u001dQ\u0017M^1eg2T!a\u0003\u0007\u0002\rM$(/Z1n\u0015\u0005i\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0011\u00035\t\u0001B\u0001\u0004D_:\u001c\u0017\r^\n\u0003\u0003M\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u0010\u0003\u0019\u0019'/Z1uKV\u0011Qd\n\u000b\u0002=A!q\u0004\t\u00121\u001b\u0005Q\u0011BA\u0011\u000b\u0005\u00159%/\u00199i!\u0011y2%J\u0013\n\u0005\u0011R!!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u0011ae\n\u0007\u0001\t\u0015A3A1\u0001*\u0005\u0005!\u0016C\u0001\u0016.!\t!2&\u0003\u0002-+\t9aj\u001c;iS:<\u0007C\u0001\u000b/\u0013\tySCA\u0002B]f\u0004\"!\r\u001a\u000e\u00031I!a\r\u0007\u0003\u000f9{G/V:fIV\u0011Q'\u000f\u000b\u0003mi\u0002Ba\b\u00118aA!qd\t\u001d9!\t1\u0013\bB\u0003)\t\t\u0007\u0011\u0006C\u0003<\t\u0001\u0007A(\u0001\u0006j]B,HoQ8v]R\u0004\"\u0001F\u001f\n\u0005y*\"aA%oiV\u0011\u0001\t\u0012\u000b\u0004\u0003\u00163\u0005\u0003B\u0010!\u0005B\u0002BaH\u0012D\u0007B\u0011a\u0005\u0012\u0003\u0006Q\u0015\u0011\r!\u000b\u0005\u0006w\u0015\u0001\r\u0001\u0010\u0005\u0006\u000f\u0016\u0001\r\u0001S\u0001\u000fI\u0016$\u0018m\u00195fI&s\u0007/\u001e;t!\t!\u0012*\u0003\u0002K+\t9!i\\8mK\u0006tWC\u0001'Q)\ti\u0015\u000b\u0005\u0003 A9\u0003\u0004\u0003B\u0010$\u001f>\u0003\"A\n)\u0005\u000b!2!\u0019A\u0015\t\u000bI3\u0001\u0019A*\u0002\u000b\rd\u0017M\u001f>\u0011\u0007Q[vJ\u0004\u0002V3B\u0011a+F\u0007\u0002/*\u0011\u0001LD\u0001\u0007yI|w\u000e\u001e \n\u0005i+\u0012A\u0002)sK\u0012,g-\u0003\u0002];\n)1\t\\1tg*\u0011!,\u0006\u0015\u0003#~\u0003\"\u0001Y2\u000e\u0003\u0005T!A\u0019\u0007\u0002\tU$\u0018\u000e\\\u0005\u0003I\u0006\u0014a!\u001e8vg\u0016$\u0007")
public final class Concat {
    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz) {
        return Concat$.MODULE$.create(clazz);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputCount, boolean detachedInputs) {
        return Concat$.MODULE$.create(inputCount, detachedInputs);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputCount) {
        return Concat$.MODULE$.create(inputCount);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create() {
        return Concat$.MODULE$.create();
    }
}

