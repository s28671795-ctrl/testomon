/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.UniformFanInShape;
import akka.stream.javadsl.Merge$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-<Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaG\u0001\u0005\u0002eBQaG\u0001\u0005\u0002QCQaG\u0001\u0005\u0002\u0001\fQ!T3sO\u0016T!!\u0003\u0006\u0002\u000f)\fg/\u00193tY*\u00111\u0002D\u0001\u0007gR\u0014X-Y7\u000b\u00035\tA!Y6lC\u000e\u0001\u0001C\u0001\t\u0002\u001b\u0005A!!B'fe\u001e,7CA\u0001\u0014!\t!r#D\u0001\u0016\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012aD\u0001\u0007GJ,\u0017\r^3\u0016\u0005u9CC\u0001\u00105!\u0011y\u0002E\t\u0019\u000e\u0003)I!!\t\u0006\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\t}\u0019S%J\u0005\u0003I)\u0011\u0011#\u00168jM>\u0014XNR1o\u0013:\u001c\u0006.\u00199f!\t1s\u0005\u0004\u0001\u0005\u000b!\u001a!\u0019A\u0015\u0003\u0003Q\u000b\"AK\u0017\u0011\u0005QY\u0013B\u0001\u0017\u0016\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u0006\u0018\n\u0005=*\"aA!osB\u0011\u0011GM\u0007\u0002\u0019%\u00111\u0007\u0004\u0002\b\u001d>$Xk]3e\u0011\u0015)4\u00011\u00017\u0003)Ig\u000e];u!>\u0014Ho\u001d\t\u0003)]J!\u0001O\u000b\u0003\u0007%sG/\u0006\u0002;}Q\u00191hP*\u0011\t}\u0001C\b\r\t\u0005?\rjT\b\u0005\u0002'}\u0011)\u0001\u0006\u0002b\u0001S!)\u0001\t\u0002a\u0001\u0003\u0006)1\r\\1{uB\u0019!)S\u001f\u000f\u0005\r;\u0005C\u0001#\u0016\u001b\u0005)%B\u0001$\u000f\u0003\u0019a$o\\8u}%\u0011\u0001*F\u0001\u0007!J,G-\u001a4\n\u0005)[%!B\"mCN\u001c(B\u0001%\u0016Q\tyT\n\u0005\u0002O#6\tqJ\u0003\u0002Q\u0019\u0005!Q\u000f^5m\u0013\t\u0011vJ\u0001\u0004v]V\u001cX\r\u001a\u0005\u0006k\u0011\u0001\rAN\u000b\u0003+f#2A\u0016.\\!\u0011y\u0002e\u0016\u0019\u0011\t}\u0019\u0003\f\u0017\t\u0003Me#Q\u0001K\u0003C\u0002%BQ!N\u0003A\u0002YBQ\u0001X\u0003A\u0002u\u000bQ\"Z1hKJ\u001cu.\u001c9mKR,\u0007C\u0001\u000b_\u0013\tyVCA\u0004C_>dW-\u00198\u0016\u0005\u0005,G\u0003\u00022gS*\u0004Ba\b\u0011daA!qd\t3e!\t1S\rB\u0003)\r\t\u0007\u0011\u0006C\u0003A\r\u0001\u0007q\rE\u0002C\u0013\u0012D#AZ'\t\u000bU2\u0001\u0019\u0001\u001c\t\u000bq3\u0001\u0019A/")
public final class Merge {
    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int inputPorts, boolean eagerComplete) {
        return Merge$.MODULE$.create(clazz, inputPorts, eagerComplete);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputPorts, boolean eagerComplete) {
        return Merge$.MODULE$.create(inputPorts, eagerComplete);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(Class<T> clazz, int inputPorts) {
        return Merge$.MODULE$.create(clazz, inputPorts);
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> create(int inputPorts) {
        return Merge$.MODULE$.create(inputPorts);
    }
}

