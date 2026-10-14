/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function7;
import scala.Tuple7;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U2QAA\u0002\u0002\u0002!AQ!\r\u0001\u0005\u0002I\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c88\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!F\u0005\n'u\u00013EJ\u0015-_M\u0019\u0001A\u0003\b\u0011\u0005-aQ\"A\u0003\n\u00055)!AB!osJ+g\r\u0005\u0006\f\u001fEarDI\u0013)W9J!\u0001E\u0003\u0003\u0013\u0019+hn\u0019;j_:<\u0004C\u0001\n\u0014\u0019\u0001!a\u0001\u0006\u0001\t\u0006\u0004)\"A\u0001+2#\t1\u0012\u0004\u0005\u0002\f/%\u0011\u0001$\u0002\u0002\b\u001d>$\b.\u001b8h!\tY!$\u0003\u0002\u001c\u000b\t\u0019\u0011I\\=\u0011\u0005IiBA\u0002\u0010\u0001\u0011\u000b\u0007QC\u0001\u0002UeA\u0011!\u0003\t\u0003\u0007C\u0001A)\u0019A\u000b\u0003\u0005Q\u001b\u0004C\u0001\n$\t\u0019!\u0003\u0001#b\u0001+\t\u0011A\u000b\u000e\t\u0003%\u0019\"aa\n\u0001\t\u0006\u0004)\"A\u0001+6!\t\u0011\u0012\u0006\u0002\u0004+\u0001!\u0015\r!\u0006\u0002\u0003)Z\u0002\"A\u0005\u0017\u0005\r5\u0002\u0001R1\u0001\u0016\u0005\t!v\u0007\u0005\u0002\u0013_\u00111\u0001\u0007\u0001CC\u0002U\u0011\u0011AU\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003M\u0002\"\u0002\u000e\u0001\u00129}\u0011S\u0005K\u0016/\u001b\u0005\u0019\u0001")
public abstract class AbstractFunction7<T1, T2, T3, T4, T5, T6, T7, R>
implements Function7<T1, T2, T3, T4, T5, T6, T7, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, R>>>>>>> curried() {
        return Function7.curried$(this);
    }

    @Override
    public Function1<Tuple7<T1, T2, T3, T4, T5, T6, T7>, R> tupled() {
        return Function7.tupled$(this);
    }

    @Override
    public String toString() {
        return Function7.toString$(this);
    }
}

