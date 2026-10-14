/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function5;
import scala.Tuple5;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=2QAA\u0002\u0002\u0002!AQa\u000b\u0001\u0005\u00021\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c86\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!F\u0004\n'u\u00013EJ\u0015\u0014\u0007\u0001Qa\u0002\u0005\u0002\f\u00195\tQ!\u0003\u0002\u000e\u000b\t1\u0011I\\=SK\u001a\u0004\u0002bC\b\u00129}\u0011S\u0005K\u0005\u0003!\u0015\u0011\u0011BR;oGRLwN\\\u001b\u0011\u0005I\u0019B\u0002\u0001\u0003\u0007)\u0001A)\u0019A\u000b\u0003\u0005Q\u000b\u0014C\u0001\f\u001a!\tYq#\u0003\u0002\u0019\u000b\t9aj\u001c;iS:<\u0007CA\u0006\u001b\u0013\tYRAA\u0002B]f\u0004\"AE\u000f\u0005\ry\u0001\u0001R1\u0001\u0016\u0005\t!&\u0007\u0005\u0002\u0013A\u00111\u0011\u0005\u0001EC\u0002U\u0011!\u0001V\u001a\u0011\u0005I\u0019CA\u0002\u0013\u0001\u0011\u000b\u0007QC\u0001\u0002UiA\u0011!C\n\u0003\u0007O\u0001A)\u0019A\u000b\u0003\u0005Q+\u0004C\u0001\n*\t\u0019Q\u0003\u0001\"b\u0001+\t\t!+\u0001\u0004=S:LGO\u0010\u000b\u0002[AAa\u0006A\t\u001d?\t*\u0003&D\u0001\u0004\u0001")
public abstract class AbstractFunction5<T1, T2, T3, T4, T5, R>
implements Function5<T1, T2, T3, T4, T5, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, R>>>>> curried() {
        return Function5.curried$(this);
    }

    @Override
    public Function1<Tuple5<T1, T2, T3, T4, T5>, R> tupled() {
        return Function5.tupled$(this);
    }

    @Override
    public String toString() {
        return Function5.toString$(this);
    }
}

