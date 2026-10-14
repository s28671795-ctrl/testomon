/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Function1;
import scala.Function16;
import scala.Tuple16;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A3QAA\u0002\u0002\u0002!AQ\u0001\u0014\u0001\u0005\u00025\u0013!#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82m)\u0011A!B\u0001\beVtG/[7f\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0013\u0013Mi\u0002e\t\u0014*Y=\u0012T\u0007O\u001e?\u0003\u0012;%jE\u0002\u0001\u00159\u0001\"a\u0003\u0007\u000e\u0003\u0015I!!D\u0003\u0003\r\u0005s\u0017PU3g!MYq\"\u0005\u000f E\u0015B3FL\u00195oij\u0004i\u0011$J\u0013\t\u0001RA\u0001\u0006Gk:\u001cG/[8ocY\u0002\"AE\n\r\u0001\u00111A\u0003\u0001EC\u0002U\u0011!\u0001V\u0019\u0012\u0005YI\u0002CA\u0006\u0018\u0013\tARAA\u0004O_RD\u0017N\\4\u0011\u0005-Q\u0012BA\u000e\u0006\u0005\r\te.\u001f\t\u0003%u!aA\b\u0001\t\u0006\u0004)\"A\u0001+3!\t\u0011\u0002\u0005\u0002\u0004\"\u0001!\u0015\r!\u0006\u0002\u0003)N\u0002\"AE\u0012\u0005\r\u0011\u0002\u0001R1\u0001\u0016\u0005\t!F\u0007\u0005\u0002\u0013M\u00111q\u0005\u0001EC\u0002U\u0011!\u0001V\u001b\u0011\u0005IICA\u0002\u0016\u0001\u0011\u000b\u0007QC\u0001\u0002UmA\u0011!\u0003\f\u0003\u0007[\u0001A)\u0019A\u000b\u0003\u0005Q;\u0004C\u0001\n0\t\u0019\u0001\u0004\u0001#b\u0001+\t\u0011A\u000b\u000f\t\u0003%I\"aa\r\u0001\t\u0006\u0004)\"A\u0001+:!\t\u0011R\u0007\u0002\u00047\u0001!\u0015\r!\u0006\u0002\u0004)F\u0002\u0004C\u0001\n9\t\u0019I\u0004\u0001#b\u0001+\t\u0019A+M\u0019\u0011\u0005IYDA\u0002\u001f\u0001\u0011\u000b\u0007QCA\u0002UcI\u0002\"A\u0005 \u0005\r}\u0002\u0001R1\u0001\u0016\u0005\r!\u0016g\r\t\u0003%\u0005#aA\u0011\u0001\t\u0006\u0004)\"a\u0001+2iA\u0011!\u0003\u0012\u0003\u0007\u000b\u0002A)\u0019A\u000b\u0003\u0007Q\u000bT\u0007\u0005\u0002\u0013\u000f\u00121\u0001\n\u0001EC\u0002U\u00111\u0001V\u00197!\t\u0011\"\n\u0002\u0004L\u0001\u0011\u0015\r!\u0006\u0002\u0002%\u00061A(\u001b8jiz\"\u0012A\u0014\t\u0014\u001f\u0002\tBd\b\u0012&Q-r\u0013\u0007N\u001c;{\u0001\u001be)S\u0007\u0002\u0007\u0001")
public abstract class AbstractFunction16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R>
implements Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> {
    @Override
    public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, R>>>>>>>>>>>>>>>> curried() {
        return Function16.curried$(this);
    }

    @Override
    public Function1<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, R> tupled() {
        return Function16.tupled$(this);
    }

    @Override
    public String toString() {
        return Function16.toString$(this);
    }
}

