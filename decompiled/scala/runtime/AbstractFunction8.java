/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function8;
import scala.Tuple8;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2QAA\u0002\u0002\u0002!AQ\u0001\u000e\u0001\u0005\u0002U\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c89\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!\u0006\u0006\n'u\u00013EJ\u0015-_I\u001a2\u0001\u0001\u0006\u000f!\tYA\"D\u0001\u0006\u0013\tiQA\u0001\u0004B]f\u0014VM\u001a\t\f\u0017=\tBd\b\u0012&Q-r\u0013'\u0003\u0002\u0011\u000b\tIa)\u001e8di&|g\u000e\u000f\t\u0003%Ma\u0001\u0001\u0002\u0004\u0015\u0001!\u0015\r!\u0006\u0002\u0003)F\n\"AF\r\u0011\u0005-9\u0012B\u0001\r\u0006\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u0003\u000e\n\u0005m)!aA!osB\u0011!#\b\u0003\u0007=\u0001A)\u0019A\u000b\u0003\u0005Q\u0013\u0004C\u0001\n!\t\u0019\t\u0003\u0001#b\u0001+\t\u0011Ak\r\t\u0003%\r\"a\u0001\n\u0001\t\u0006\u0004)\"A\u0001+5!\t\u0011b\u0005\u0002\u0004(\u0001!\u0015\r!\u0006\u0002\u0003)V\u0002\"AE\u0015\u0005\r)\u0002\u0001R1\u0001\u0016\u0005\t!f\u0007\u0005\u0002\u0013Y\u00111Q\u0006\u0001EC\u0002U\u0011!\u0001V\u001c\u0011\u0005IyCA\u0002\u0019\u0001\u0011\u000b\u0007QC\u0001\u0002UqA\u0011!C\r\u0003\u0007g\u0001!)\u0019A\u000b\u0003\u0003I\u000ba\u0001P5oSRtD#\u0001\u001c\u0011\u0017]\u0002\u0011\u0003H\u0010#K!Zc&M\u0007\u0002\u0007\u0001")
public abstract class AbstractFunction8<T1, T2, T3, T4, T5, T6, T7, T8, R>
implements Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, R>>>>>>>> curried() {
        return Function8.curried$(this);
    }

    @Override
    public Function1<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, R> tupled() {
        return Function8.tupled$(this);
    }

    @Override
    public String toString() {
        return Function8.toString$(this);
    }
}

