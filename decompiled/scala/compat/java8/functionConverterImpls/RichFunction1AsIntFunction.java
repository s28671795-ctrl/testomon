/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsIntFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001dI\u0005!!A\u0005B);q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000bEBA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000f\u0005D\u0011\u0011!C\u0003E\"9\u0001\u000eCA\u0001\n\u000bI'A\u0007*jG\"4UO\\2uS>t\u0017'Q:J]R4UO\\2uS>t'BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001QC\u0001\r('\t\u0001\u0011\u0004\u0005\u0002\u001b75\tA#\u0003\u0002\u001d)\t1\u0011I\\=WC2\f\u0001k]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\f\u0014i]%oi\u001a+hn\u0019;j_:$C%\u001e8eKJd\u00170\u001b8h+\u0005y\u0002\u0003\u0002\u000e!E\u0015J!!\t\u000b\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0001\u000e$\u0013\t!CCA\u0002J]R\u0004\"AJ\u0014\r\u0001\u0011)\u0001\u0006\u0001b\u0001S\t\t!+\u0005\u0002+[A\u0011!dK\u0005\u0003YQ\u0011qAT8uQ&tw\r\u0005\u0002\u001b]%\u0011q\u0006\u0006\u0002\u0004\u0003:L\u0018!U:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:J]R4UO\\2uS>tG\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDCA\u001a6!\r!\u0004!J\u0007\u0002\u001d!)ag\u0001a\u0001?\u0005QQO\u001c3fe2L\u0018N\\4\u0002\r\u0005\u001c(*\u0019<b+\u0005I\u0004c\u0001\u001eBK5\t1H\u0003\u0002={\u0005Aa-\u001e8di&|gN\u0003\u0002?\u007f\u0005!Q\u000f^5m\u0015\u0005\u0001\u0015\u0001\u00026bm\u0006L!AQ\u001e\u0003\u0017%sGOR;oGRLwN\u001c\u0015\u0003\t\u0011\u0003\"AG#\n\u0005\u0019#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005\u0011\u0013AB3rk\u0006d7\u000f\u0006\u0002L\u001dB\u0011!\u0004T\u0005\u0003\u001bR\u0011qAQ8pY\u0016\fg\u000eC\u0004P\r\u0005\u0005\t\u0019A\u0017\u0002\u0007a$\u0013'\u0001\u000eSS\u000eDg)\u001e8di&|g.M!t\u0013:$h)\u001e8di&|g\u000e\u0005\u00025\u0011M\u0011\u0001b\u0015\t\u00035QK!!\u0016\u000b\u0003\r\u0005s\u0017PU3g)\u0005\t\u0016\u0001E1t\u0015\u00064\u0018\rJ3yi\u0016t7/[8o+\tIF\f\u0006\u0002[;B\u0019!(Q.\u0011\u0005\u0019bF!\u0002\u0015\u000b\u0005\u0004I\u0003\"\u00020\u000b\u0001\u0004y\u0016!\u0002\u0013uQ&\u001c\bc\u0001\u001b\u00017\"\u0012!\u0002R\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g.\u0006\u0002dOR\u0011\u0001\n\u001a\u0005\u0006=.\u0001\r!\u001a\t\u0004i\u00011\u0007C\u0001\u0014h\t\u0015A3B1\u0001*\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g.\u0006\u0002kaR\u00111.\u001c\u000b\u0003\u00172Dqa\u0014\u0007\u0002\u0002\u0003\u0007Q\u0006C\u0003_\u0019\u0001\u0007a\u000eE\u00025\u0001=\u0004\"A\n9\u0005\u000b!b!\u0019A\u0015")
public final class RichFunction1AsIntFunction<R> {
    private final Function1<Object, R> scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying;

    public static <R> boolean equals$extension(Function1<Object, R> function1, Object object) {
        return RichFunction1AsIntFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <R> int hashCode$extension(Function1<Object, R> function1) {
        return RichFunction1AsIntFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <R> IntFunction<R> asJava$extension(Function1<Object, R> function1) {
        return RichFunction1AsIntFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, R> scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying;
    }

    public IntFunction<R> asJava() {
        return RichFunction1AsIntFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsIntFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsIntFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying(), x$1);
    }

    public RichFunction1AsIntFunction(Function1<Object, R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntFunction$$underlying = underlying;
    }
}

