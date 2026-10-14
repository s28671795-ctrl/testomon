/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function4;
import scala.Tuple4;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000512QAA\u0002\u0002\u0002!AQ\u0001\u000b\u0001\u0005\u0002%\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c85\u0015\t!Q!A\u0004sk:$\u0018.\\3\u000b\u0003\u0019\tQa]2bY\u0006\u001c\u0001!\u0006\u0004\n'u\u00013EJ\n\u0004\u0001)q\u0001CA\u0006\r\u001b\u0005)\u0011BA\u0007\u0006\u0005\u0019\te.\u001f*fMB91bD\t\u001d?\t*\u0013B\u0001\t\u0006\u0005%1UO\\2uS>tG\u0007\u0005\u0002\u0013'1\u0001AA\u0002\u000b\u0001\u0011\u000b\u0007QC\u0001\u0002UcE\u0011a#\u0007\t\u0003\u0017]I!\u0001G\u0003\u0003\u000f9{G\u000f[5oOB\u00111BG\u0005\u00037\u0015\u00111!\u00118z!\t\u0011R\u0004\u0002\u0004\u001f\u0001!\u0015\r!\u0006\u0002\u0003)J\u0002\"A\u0005\u0011\u0005\r\u0005\u0002\u0001R1\u0001\u0016\u0005\t!6\u0007\u0005\u0002\u0013G\u00111A\u0005\u0001EC\u0002U\u0011!\u0001\u0016\u001b\u0011\u0005I1CAB\u0014\u0001\t\u000b\u0007QCA\u0001S\u0003\u0019a\u0014N\\5u}Q\t!\u0006E\u0004,\u0001EarDI\u0013\u000e\u0003\r\u0001")
public abstract class AbstractFunction4<T1, T2, T3, T4, R>
implements Function4<T1, T2, T3, T4, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, R>>>> curried() {
        return Function4.curried$(this);
    }

    @Override
    public Function1<Tuple4<T1, T2, T3, T4>, R> tupled() {
        return Function4.tupled$(this);
    }

    @Override
    public String toString() {
        return Function4.toString$(this);
    }
}

