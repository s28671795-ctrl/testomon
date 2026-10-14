/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsToDoubleBiFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bQBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f%D\u0011\u0011!C\u0003U\"9!\u000fCA\u0001\n\u000b\u0019(!\t*jG\"4UO\\2uS>t''Q:U_\u0012{WO\u00197f\u0005&4UO\\2uS>t'BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u0001Qc\u0001\r%]M\u0011\u0001!\u0007\t\u00035mi\u0011\u0001F\u0005\u00039Q\u0011a!\u00118z-\u0006d\u0017aV:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t''Q:U_\u0012{WO\u00197f\u0005&4UO\\2uS>tG\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0003\u001bA\tj\u0003'\u0003\u0002\")\tIa)\u001e8di&|gN\r\t\u0003G\u0011b\u0001\u0001B\u0003&\u0001\t\u0007aEA\u0001U#\t9#\u0006\u0005\u0002\u001bQ%\u0011\u0011\u0006\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ2&\u0003\u0002-)\t\u0019\u0011I\\=\u0011\u0005\rrC!B\u0018\u0001\u0005\u00041#!A+\u0011\u0005i\t\u0014B\u0001\u001a\u0015\u0005\u0019!u.\u001e2mK\u0006A6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]J\n5\u000fV8E_V\u0014G.\u001a\"j\rVt7\r^5p]\u0012\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011a\u0007\u000f\t\u0005o\u0001\u0011S&D\u0001\u000f\u0011\u0015I4\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\u0007CNT\u0015M^1\u0016\u0003q\u0002B!\u0010##[5\taH\u0003\u0002@\u0001\u0006Aa-\u001e8di&|gN\u0003\u0002B\u0005\u0006!Q\u000f^5m\u0015\u0005\u0019\u0015\u0001\u00026bm\u0006L!!\u0012 \u0003%Q{Gi\\;cY\u0016\u0014\u0015NR;oGRLwN\u001c\u0015\u0003\t\u001d\u0003\"A\u0007%\n\u0005%#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005a\u0005C\u0001\u000eN\u0013\tqECA\u0002J]R\fa!Z9vC2\u001cHCA)U!\tQ\"+\u0003\u0002T)\t9!i\\8mK\u0006t\u0007bB+\u0007\u0003\u0003\u0005\rAK\u0001\u0004q\u0012\n\u0014!\t*jG\"4UO\\2uS>t''Q:U_\u0012{WO\u00197f\u0005&4UO\\2uS>t\u0007CA\u001c\t'\tA\u0011\f\u0005\u0002\u001b5&\u00111\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003]\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007}\u0013G\r\u0006\u0002aKB!Q\bR1d!\t\u0019#\rB\u0003&\u0015\t\u0007a\u0005\u0005\u0002$I\u0012)qF\u0003b\u0001M!)aM\u0003a\u0001O\u0006)A\u0005\u001e5jgB!q\u0007A1dQ\tQq)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWcA6pcR\u00111\n\u001c\u0005\u0006M.\u0001\r!\u001c\t\u0005o\u0001q\u0007\u000f\u0005\u0002$_\u0012)Qe\u0003b\u0001MA\u00111%\u001d\u0003\u0006_-\u0011\rAJ\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,2\u0001\u001e>})\t)x\u000f\u0006\u0002Rm\"9Q\u000bDA\u0001\u0002\u0004Q\u0003\"\u00024\r\u0001\u0004A\b\u0003B\u001c\u0001sn\u0004\"a\t>\u0005\u000b\u0015b!\u0019\u0001\u0014\u0011\u0005\rbH!B\u0018\r\u0005\u00041\u0003")
public final class RichFunction2AsToDoubleBiFunction<T, U> {
    private final Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying;

    public static <T, U> boolean equals$extension(Function2<T, U, Object> function2, Object object) {
        return RichFunction2AsToDoubleBiFunction$.MODULE$.equals$extension(function2, object);
    }

    public static <T, U> int hashCode$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsToDoubleBiFunction$.MODULE$.hashCode$extension(function2);
    }

    public static <T, U> ToDoubleBiFunction<T, U> asJava$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsToDoubleBiFunction$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying;
    }

    public ToDoubleBiFunction<T, U> asJava() {
        return RichFunction2AsToDoubleBiFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsToDoubleBiFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsToDoubleBiFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying(), x$1);
    }

    public RichFunction2AsToDoubleBiFunction(Function2<T, U, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsToDoubleBiFunction$$underlying = underlying;
    }
}

