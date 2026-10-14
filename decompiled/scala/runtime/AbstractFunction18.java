/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function18;
import scala.Tuple18;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3QAA\u0002\u0002\u0002!AQA\u0015\u0001\u0005\u0002M\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82q)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0015\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?\u0003\u0012;%*\u0014)\u0014\u0007\u0001Qa\u0002\u0005\u0002\f\u00195\tQ!\u0003\u0002\u000e\u000b\t1\u0011I\\=SK\u001a\u0004RcC\b\u00129}\u0011S\u0005K\u0016/cQ:$(\u0010!D\r&cu*\u0003\u0002\u0011\u000b\tQa)\u001e8di&|g.\r\u001d\u0011\u0005I\u0019B\u0002\u0001\u0003\u0007)\u0001A)\u0019A\u000b\u0003\u0005Q\u000b\u0014C\u0001\f\u001a!\tYq#\u0003\u0002\u0019\u000b\t9aj\u001c;iS:<\u0007CA\u0006\u001b\u0013\tYRAA\u0002B]f\u0004\"AE\u000f\u0005\ry\u0001\u0001R1\u0001\u0016\u0005\t!&\u0007\u0005\u0002\u0013A\u00111\u0011\u0005\u0001EC\u0002U\u0011!\u0001V\u001a\u0011\u0005I\u0019CA\u0002\u0013\u0001\u0011\u000b\u0007QC\u0001\u0002UiA\u0011!C\n\u0003\u0007O\u0001A)\u0019A\u000b\u0003\u0005Q+\u0004C\u0001\n*\t\u0019Q\u0003\u0001#b\u0001+\t\u0011AK\u000e\t\u0003%1\"a!\f\u0001\t\u0006\u0004)\"A\u0001+8!\t\u0011r\u0006\u0002\u00041\u0001!\u0015\r!\u0006\u0002\u0003)b\u0002\"A\u0005\u001a\u0005\rM\u0002\u0001R1\u0001\u0016\u0005\t!\u0016\b\u0005\u0002\u0013k\u00111a\u0007\u0001EC\u0002U\u00111\u0001V\u00191!\t\u0011\u0002\b\u0002\u0004:\u0001!\u0015\r!\u0006\u0002\u0004)F\n\u0004C\u0001\n<\t\u0019a\u0004\u0001#b\u0001+\t\u0019A+\r\u001a\u0011\u0005IqDAB \u0001\u0011\u000b\u0007QCA\u0002UcM\u0002\"AE!\u0005\r\t\u0003\u0001R1\u0001\u0016\u0005\r!\u0016\u0007\u000e\t\u0003%\u0011#a!\u0012\u0001\t\u0006\u0004)\"a\u0001+2kA\u0011!c\u0012\u0003\u0007\u0011\u0002A)\u0019A\u000b\u0003\u0007Q\u000bd\u0007\u0005\u0002\u0013\u0015\u001211\n\u0001EC\u0002U\u00111\u0001V\u00198!\t\u0011R\n\u0002\u0004O\u0001!\u0015\r!\u0006\u0002\u0004)FB\u0004C\u0001\nQ\t\u0019\t\u0006\u0001\"b\u0001+\t\t!+\u0001\u0004=S:LGO\u0010\u000b\u0002)B)R\u000bA\t\u001d?\t*\u0003f\u000b\u00182i]RT\bQ\"G\u00132{U\"A\u0002")
public abstract class AbstractFunction18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R>
implements Function18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, R>>>>>>>>>>>>>>>>>> curried() {
        return Function18.curried$(this);
    }

    @Override
    public Function1<Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, R> tupled() {
        return Function18.tupled$(this);
    }

    @Override
    public String toString() {
        return Function18.toString$(this);
    }
}

