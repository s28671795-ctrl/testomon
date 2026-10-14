/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function10;
import scala.Tuple10;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2QAA\u0002\u0002\u0002!AQA\u000f\u0001\u0005\u0002m\u0012!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82a)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\r\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\n\u0004\u0001)q\u0001CA\u0006\r\u001b\u0005)\u0011BA\u0007\u0006\u0005\u0019\te.\u001f*fMBi1bD\t\u001d?\t*\u0003f\u000b\u00182i]J!\u0001E\u0003\u0003\u0015\u0019+hn\u0019;j_:\f\u0004\u0007\u0005\u0002\u0013'1\u0001AA\u0002\u000b\u0001\u0011\u000b\u0007QC\u0001\u0002UcE\u0011a#\u0007\t\u0003\u0017]I!\u0001G\u0003\u0003\u000f9{G\u000f[5oOB\u00111BG\u0005\u00037\u0015\u00111!\u00118z!\t\u0011R\u0004\u0002\u0004\u001f\u0001!\u0015\r!\u0006\u0002\u0003)J\u0002\"A\u0005\u0011\u0005\r\u0005\u0002\u0001R1\u0001\u0016\u0005\t!6\u0007\u0005\u0002\u0013G\u00111A\u0005\u0001EC\u0002U\u0011!\u0001\u0016\u001b\u0011\u0005I1CAB\u0014\u0001\u0011\u000b\u0007QC\u0001\u0002UkA\u0011!#\u000b\u0003\u0007U\u0001A)\u0019A\u000b\u0003\u0005Q3\u0004C\u0001\n-\t\u0019i\u0003\u0001#b\u0001+\t\u0011Ak\u000e\t\u0003%=\"a\u0001\r\u0001\t\u0006\u0004)\"A\u0001+9!\t\u0011\"\u0007\u0002\u00044\u0001!\u0015\r!\u0006\u0002\u0003)f\u0002\"AE\u001b\u0005\rY\u0002\u0001R1\u0001\u0016\u0005\r!\u0016\u0007\r\t\u0003%a\"a!\u000f\u0001\u0005\u0006\u0004)\"!\u0001*\u0002\rqJg.\u001b;?)\u0005a\u0004#D\u001f\u0001#qy\"%\n\u0015,]E\"t'D\u0001\u0004\u0001")
public abstract class AbstractFunction10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R>
implements Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, R>>>>>>>>>> curried() {
        return Function10.curried$(this);
    }

    @Override
    public Function1<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, R> tupled() {
        return Function10.tupled$(this);
    }

    @Override
    public String toString() {
        return Function10.toString$(this);
    }
}

