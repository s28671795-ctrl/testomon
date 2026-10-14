/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function17;
import scala.Tuple17;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M3QAA\u0002\u0002\u0002!AQa\u0014\u0001\u0005\u0002A\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82o)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0014\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?\u0003\u0012;%*T\n\u0004\u0001)q\u0001CA\u0006\r\u001b\u0005)\u0011BA\u0007\u0006\u0005\u0019\te.\u001f*fMB!2bD\t\u001d?\t*\u0003f\u000b\u00182i]RT\bQ\"G\u00132K!\u0001E\u0003\u0003\u0015\u0019+hn\u0019;j_:\ft\u0007\u0005\u0002\u0013'1\u0001AA\u0002\u000b\u0001\u0011\u000b\u0007QC\u0001\u0002UcE\u0011a#\u0007\t\u0003\u0017]I!\u0001G\u0003\u0003\u000f9{G\u000f[5oOB\u00111BG\u0005\u00037\u0015\u00111!\u00118z!\t\u0011R\u0004\u0002\u0004\u001f\u0001!\u0015\r!\u0006\u0002\u0003)J\u0002\"A\u0005\u0011\u0005\r\u0005\u0002\u0001R1\u0001\u0016\u0005\t!6\u0007\u0005\u0002\u0013G\u00111A\u0005\u0001EC\u0002U\u0011!\u0001\u0016\u001b\u0011\u0005I1CAB\u0014\u0001\u0011\u000b\u0007QC\u0001\u0002UkA\u0011!#\u000b\u0003\u0007U\u0001A)\u0019A\u000b\u0003\u0005Q3\u0004C\u0001\n-\t\u0019i\u0003\u0001#b\u0001+\t\u0011Ak\u000e\t\u0003%=\"a\u0001\r\u0001\t\u0006\u0004)\"A\u0001+9!\t\u0011\"\u0007\u0002\u00044\u0001!\u0015\r!\u0006\u0002\u0003)f\u0002\"AE\u001b\u0005\rY\u0002\u0001R1\u0001\u0016\u0005\r!\u0016\u0007\r\t\u0003%a\"a!\u000f\u0001\t\u0006\u0004)\"a\u0001+2cA\u0011!c\u000f\u0003\u0007y\u0001A)\u0019A\u000b\u0003\u0007Q\u000b$\u0007\u0005\u0002\u0013}\u00111q\b\u0001EC\u0002U\u00111\u0001V\u00194!\t\u0011\u0012\t\u0002\u0004C\u0001!\u0015\r!\u0006\u0002\u0004)F\"\u0004C\u0001\nE\t\u0019)\u0005\u0001#b\u0001+\t\u0019A+M\u001b\u0011\u0005I9EA\u0002%\u0001\u0011\u000b\u0007QCA\u0002UcY\u0002\"A\u0005&\u0005\r-\u0003\u0001R1\u0001\u0016\u0005\r!\u0016g\u000e\t\u0003%5#aA\u0014\u0001\u0005\u0006\u0004)\"!\u0001*\u0002\rqJg.\u001b;?)\u0005\t\u0006\u0003\u0006*\u0001#qy\"%\n\u0015,]E\"tGO\u001fA\u0007\u001aKE*D\u0001\u0004\u0001")
public abstract class AbstractFunction17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R>
implements Function17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, R>>>>>>>>>>>>>>>>> curried() {
        return Function17.curried$(this);
    }

    @Override
    public Function1<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, R> tupled() {
        return Function17.tupled$(this);
    }

    @Override
    public String toString() {
        return Function17.toString$(this);
    }
}

