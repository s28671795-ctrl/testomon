/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function12;
import scala.Tuple12;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00113QAA\u0002\u0002\u0002!AQ\u0001\u0011\u0001\u0005\u0002\u0005\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82e)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u000f\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?'\r\u0001!B\u0004\t\u0003\u00171i\u0011!B\u0005\u0003\u001b\u0015\u0011a!\u00118z%\u00164\u0007cD\u0006\u0010#qy\"%\n\u0015,]E\"tGO\u001f\n\u0005A)!A\u0003$v]\u000e$\u0018n\u001c82eA\u0011!c\u0005\u0007\u0001\t\u0019!\u0002\u0001#b\u0001+\t\u0011A+M\t\u0003-e\u0001\"aC\f\n\u0005a)!a\u0002(pi\"Lgn\u001a\t\u0003\u0017iI!aG\u0003\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0013;\u00111a\u0004\u0001EC\u0002U\u0011!\u0001\u0016\u001a\u0011\u0005I\u0001CAB\u0011\u0001\u0011\u000b\u0007QC\u0001\u0002UgA\u0011!c\t\u0003\u0007I\u0001A)\u0019A\u000b\u0003\u0005Q#\u0004C\u0001\n'\t\u00199\u0003\u0001#b\u0001+\t\u0011A+\u000e\t\u0003%%\"aA\u000b\u0001\t\u0006\u0004)\"A\u0001+7!\t\u0011B\u0006\u0002\u0004.\u0001!\u0015\r!\u0006\u0002\u0003)^\u0002\"AE\u0018\u0005\rA\u0002\u0001R1\u0001\u0016\u0005\t!\u0006\b\u0005\u0002\u0013e\u001111\u0007\u0001EC\u0002U\u0011!\u0001V\u001d\u0011\u0005I)DA\u0002\u001c\u0001\u0011\u000b\u0007QCA\u0002UcA\u0002\"A\u0005\u001d\u0005\re\u0002\u0001R1\u0001\u0016\u0005\r!\u0016'\r\t\u0003%m\"a\u0001\u0010\u0001\t\u0006\u0004)\"a\u0001+2eA\u0011!C\u0010\u0003\u0007\u007f\u0001!)\u0019A\u000b\u0003\u0003I\u000ba\u0001P5oSRtD#\u0001\"\u0011\u001f\r\u0003\u0011\u0003H\u0010#K!Zc&\r\u001b8uuj\u0011a\u0001")
public abstract class AbstractFunction12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R>
implements Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, R>>>>>>>>>>>> curried() {
        return Function12.curried$(this);
    }

    @Override
    public Function1<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, R> tupled() {
        return Function12.tupled$(this);
    }

    @Override
    public String toString() {
        return Function12.toString$(this);
    }
}

