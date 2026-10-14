/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function20;
import scala.Tuple20;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q3QAA\u0002\u0002\u0002!AQ\u0001\u0017\u0001\u0005\u0002e\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83a)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0017\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?\u0003\u0012;%*\u0014)T-N\u0019\u0001A\u0003\b\u0011\u0005-aQ\"A\u0003\n\u00055)!AB!osJ+g\rE\f\f\u001fEarDI\u0013)W9\nDg\u000e\u001e>\u0001\u000e3\u0015\nT(S+&\u0011\u0001#\u0002\u0002\u000b\rVt7\r^5p]J\u0002\u0004C\u0001\n\u0014\u0019\u0001!a\u0001\u0006\u0001\t\u0006\u0004)\"A\u0001+2#\t1\u0012\u0004\u0005\u0002\f/%\u0011\u0001$\u0002\u0002\b\u001d>$\b.\u001b8h!\tY!$\u0003\u0002\u001c\u000b\t\u0019\u0011I\\=\u0011\u0005IiBA\u0002\u0010\u0001\u0011\u000b\u0007QC\u0001\u0002UeA\u0011!\u0003\t\u0003\u0007C\u0001A)\u0019A\u000b\u0003\u0005Q\u001b\u0004C\u0001\n$\t\u0019!\u0003\u0001#b\u0001+\t\u0011A\u000b\u000e\t\u0003%\u0019\"aa\n\u0001\t\u0006\u0004)\"A\u0001+6!\t\u0011\u0012\u0006\u0002\u0004+\u0001!\u0015\r!\u0006\u0002\u0003)Z\u0002\"A\u0005\u0017\u0005\r5\u0002\u0001R1\u0001\u0016\u0005\t!v\u0007\u0005\u0002\u0013_\u00111\u0001\u0007\u0001EC\u0002U\u0011!\u0001\u0016\u001d\u0011\u0005I\u0011DAB\u001a\u0001\u0011\u000b\u0007QC\u0001\u0002UsA\u0011!#\u000e\u0003\u0007m\u0001A)\u0019A\u000b\u0003\u0007Q\u000b\u0004\u0007\u0005\u0002\u0013q\u00111\u0011\b\u0001EC\u0002U\u00111\u0001V\u00192!\t\u00112\b\u0002\u0004=\u0001!\u0015\r!\u0006\u0002\u0004)F\u0012\u0004C\u0001\n?\t\u0019y\u0004\u0001#b\u0001+\t\u0019A+M\u001a\u0011\u0005I\tEA\u0002\"\u0001\u0011\u000b\u0007QCA\u0002UcQ\u0002\"A\u0005#\u0005\r\u0015\u0003\u0001R1\u0001\u0016\u0005\r!\u0016'\u000e\t\u0003%\u001d#a\u0001\u0013\u0001\t\u0006\u0004)\"a\u0001+2mA\u0011!C\u0013\u0003\u0007\u0017\u0002A)\u0019A\u000b\u0003\u0007Q\u000bt\u0007\u0005\u0002\u0013\u001b\u00121a\n\u0001EC\u0002U\u00111\u0001V\u00199!\t\u0011\u0002\u000b\u0002\u0004R\u0001!\u0015\r!\u0006\u0002\u0004)FJ\u0004C\u0001\nT\t\u0019!\u0006\u0001#b\u0001+\t\u0019AK\r\u0019\u0011\u0005I1FAB,\u0001\t\u000b\u0007QCA\u0001S\u0003\u0019a\u0014N\\5u}Q\t!\fE\f\\\u0001EarDI\u0013)W9\nDg\u000e\u001e>\u0001\u000e3\u0015\nT(S+6\t1\u0001")
public abstract class AbstractFunction20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R>
implements Function20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, Function1<T19, Function1<T20, R>>>>>>>>>>>>>>>>>>>> curried() {
        return Function20.curried$(this);
    }

    @Override
    public Function1<Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, R> tupled() {
        return Function20.tupled$(this);
    }

    @Override
    public String toString() {
        return Function20.toString$(this);
    }
}

