/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function22;
import scala.Tuple22;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t4QAA\u0002\u0002\u0002!AQA\u0018\u0001\u0005\u0002}\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83e)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0019\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?\u0003\u0012;%*\u0014)T-fc6c\u0001\u0001\u000b\u001dA\u00111\u0002D\u0007\u0002\u000b%\u0011Q\"\u0002\u0002\u0007\u0003:L(+\u001a4\u00113-y\u0011\u0003H\u0010#K!Zc&\r\u001b8uu\u00025IR%M\u001fJ+\u0006lW\u0005\u0003!\u0015\u0011!BR;oGRLwN\u001c\u001a3!\t\u00112\u0003\u0004\u0001\u0005\rQ\u0001\u0001R1\u0001\u0016\u0005\t!\u0016'\u0005\u0002\u00173A\u00111bF\u0005\u00031\u0015\u0011qAT8uQ&tw\r\u0005\u0002\f5%\u00111$\u0002\u0002\u0004\u0003:L\bC\u0001\n\u001e\t\u0019q\u0002\u0001#b\u0001+\t\u0011AK\r\t\u0003%\u0001\"a!\t\u0001\t\u0006\u0004)\"A\u0001+4!\t\u00112\u0005\u0002\u0004%\u0001!\u0015\r!\u0006\u0002\u0003)R\u0002\"A\u0005\u0014\u0005\r\u001d\u0002\u0001R1\u0001\u0016\u0005\t!V\u0007\u0005\u0002\u0013S\u00111!\u0006\u0001EC\u0002U\u0011!\u0001\u0016\u001c\u0011\u0005IaCAB\u0017\u0001\u0011\u000b\u0007QC\u0001\u0002UoA\u0011!c\f\u0003\u0007a\u0001A)\u0019A\u000b\u0003\u0005QC\u0004C\u0001\n3\t\u0019\u0019\u0004\u0001#b\u0001+\t\u0011A+\u000f\t\u0003%U\"aA\u000e\u0001\t\u0006\u0004)\"a\u0001+2aA\u0011!\u0003\u000f\u0003\u0007s\u0001A)\u0019A\u000b\u0003\u0007Q\u000b\u0014\u0007\u0005\u0002\u0013w\u00111A\b\u0001EC\u0002U\u00111\u0001V\u00193!\t\u0011b\b\u0002\u0004@\u0001!\u0015\r!\u0006\u0002\u0004)F\u001a\u0004C\u0001\nB\t\u0019\u0011\u0005\u0001#b\u0001+\t\u0019A+\r\u001b\u0011\u0005I!EAB#\u0001\u0011\u000b\u0007QCA\u0002UcU\u0002\"AE$\u0005\r!\u0003\u0001R1\u0001\u0016\u0005\r!\u0016G\u000e\t\u0003%)#aa\u0013\u0001\t\u0006\u0004)\"a\u0001+2oA\u0011!#\u0014\u0003\u0007\u001d\u0002A)\u0019A\u000b\u0003\u0007Q\u000b\u0004\b\u0005\u0002\u0013!\u00121\u0011\u000b\u0001EC\u0002U\u00111\u0001V\u0019:!\t\u00112\u000b\u0002\u0004U\u0001!\u0015\r!\u0006\u0002\u0004)J\u0002\u0004C\u0001\nW\t\u00199\u0006\u0001#b\u0001+\t\u0019AKM\u0019\u0011\u0005IIFA\u0002.\u0001\u0011\u000b\u0007QCA\u0002UeI\u0002\"A\u0005/\u0005\ru\u0003AQ1\u0001\u0016\u0005\u0005\u0011\u0016A\u0002\u001fj]&$h\bF\u0001a!e\t\u0007!\u0005\u000f E\u0015B3FL\u00195oij\u0004i\u0011$J\u0019>\u0013V\u000bW.\u000e\u0003\r\u0001")
public abstract class AbstractFunction22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R>
implements Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, Function1<T19, Function1<T20, Function1<T21, Function1<T22, R>>>>>>>>>>>>>>>>>>>>>> curried() {
        return Function22.curried$(this);
    }

    @Override
    public Function1<Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, R> tupled() {
        return Function22.tupled$(this);
    }

    @Override
    public String toString() {
        return Function22.toString$(this);
    }
}

