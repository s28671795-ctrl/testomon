/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function21;
import scala.Tuple21;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}3QAA\u0002\u0002\u0002!AQa\u0017\u0001\u0005\u0002q\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83c)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0018\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?\u0003\u0012;%*\u0014)T-f\u001b2\u0001\u0001\u0006\u000f!\tYA\"D\u0001\u0006\u0013\tiQA\u0001\u0004B]f\u0014VM\u001a\t\u0019\u0017=\tBd\b\u0012&Q-r\u0013\u0007N\u001c;{\u0001\u001be)\u0013'P%VC\u0016B\u0001\t\u0006\u0005)1UO\\2uS>t''\r\t\u0003%Ma\u0001\u0001\u0002\u0004\u0015\u0001!\u0015\r!\u0006\u0002\u0003)F\n\"AF\r\u0011\u0005-9\u0012B\u0001\r\u0006\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u0003\u000e\n\u0005m)!aA!osB\u0011!#\b\u0003\u0007=\u0001A)\u0019A\u000b\u0003\u0005Q\u0013\u0004C\u0001\n!\t\u0019\t\u0003\u0001#b\u0001+\t\u0011Ak\r\t\u0003%\r\"a\u0001\n\u0001\t\u0006\u0004)\"A\u0001+5!\t\u0011b\u0005\u0002\u0004(\u0001!\u0015\r!\u0006\u0002\u0003)V\u0002\"AE\u0015\u0005\r)\u0002\u0001R1\u0001\u0016\u0005\t!f\u0007\u0005\u0002\u0013Y\u00111Q\u0006\u0001EC\u0002U\u0011!\u0001V\u001c\u0011\u0005IyCA\u0002\u0019\u0001\u0011\u000b\u0007QC\u0001\u0002UqA\u0011!C\r\u0003\u0007g\u0001A)\u0019A\u000b\u0003\u0005QK\u0004C\u0001\n6\t\u00191\u0004\u0001#b\u0001+\t\u0019A+\r\u0019\u0011\u0005IADAB\u001d\u0001\u0011\u000b\u0007QCA\u0002UcE\u0002\"AE\u001e\u0005\rq\u0002\u0001R1\u0001\u0016\u0005\r!\u0016G\r\t\u0003%y\"aa\u0010\u0001\t\u0006\u0004)\"a\u0001+2gA\u0011!#\u0011\u0003\u0007\u0005\u0002A)\u0019A\u000b\u0003\u0007Q\u000bD\u0007\u0005\u0002\u0013\t\u00121Q\t\u0001EC\u0002U\u00111\u0001V\u00196!\t\u0011r\t\u0002\u0004I\u0001!\u0015\r!\u0006\u0002\u0004)F2\u0004C\u0001\nK\t\u0019Y\u0005\u0001#b\u0001+\t\u0019A+M\u001c\u0011\u0005IiEA\u0002(\u0001\u0011\u000b\u0007QCA\u0002Uca\u0002\"A\u0005)\u0005\rE\u0003\u0001R1\u0001\u0016\u0005\r!\u0016'\u000f\t\u0003%M#a\u0001\u0016\u0001\t\u0006\u0004)\"a\u0001+3aA\u0011!C\u0016\u0003\u0007/\u0002A)\u0019A\u000b\u0003\u0007Q\u0013\u0014\u0007\u0005\u0002\u00133\u00121!\f\u0001CC\u0002U\u0011\u0011AU\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003u\u0003\u0002D\u0018\u0001\u00129}\u0011S\u0005K\u0016/cQ:$(\u0010!D\r&cuJU+Y\u001b\u0005\u0019\u0001")
public abstract class AbstractFunction21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R>
implements Function21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, Function1<T19, Function1<T20, Function1<T21, R>>>>>>>>>>>>>>>>>>>>> curried() {
        return Function21.curried$(this);
    }

    @Override
    public Function1<Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, R> tupled() {
        return Function21.tupled$(this);
    }

    @Override
    public String toString() {
        return Function21.toString$(this);
    }
}

