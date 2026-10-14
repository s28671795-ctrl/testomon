/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichToLongBiFunctionAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!Iq\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006q\u0001!\t!\u000f\u0005\u0006}\u0001!\ta\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000baBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f%D\u0011\u0011!C\u0003U\"9!\u000fCA\u0001\n\u000b\u0019(a\b*jG\"$v\u000eT8oO\nKg)\u001e8di&|g.Q:Gk:\u001cG/[8oe)\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019\u0001dK\u001b\u0014\u0005\u0001I\u0002C\u0001\u000e\u001c\u001b\u0005!\u0012B\u0001\u000f\u0015\u0005\u0019\te.\u001f,bY\u0006)6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i)>duN\\4CS\u001a+hn\u0019;j_:\f5OR;oGRLwN\u001c\u001a%IUtG-\u001a:ms&tw-F\u0001 !\u0011\u0001s%\u000b\u001b\u000e\u0003\u0005R!AI\u0012\u0002\u0011\u0019,hn\u0019;j_:T!\u0001J\u0013\u0002\tU$\u0018\u000e\u001c\u0006\u0002M\u0005!!.\u0019<b\u0013\tA\u0013E\u0001\tU_2{gn\u001a\"j\rVt7\r^5p]B\u0011!f\u000b\u0007\u0001\t\u0015a\u0003A1\u0001.\u0005\u0005!\u0016C\u0001\u00182!\tQr&\u0003\u00021)\t9aj\u001c;iS:<\u0007C\u0001\u000e3\u0013\t\u0019DCA\u0002B]f\u0004\"AK\u001b\u0005\u000bY\u0002!\u0019A\u0017\u0003\u0003U\u000bak]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQR{Gj\u001c8h\u0005&4UO\\2uS>t\u0017i\u001d$v]\u000e$\u0018n\u001c83I\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\tQD\b\u0005\u0003<\u0001%\"T\"\u0001\b\t\u000bu\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003\u0001\u0003RAG!*i\rK!A\u0011\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\u000eE\u0013\t)EC\u0001\u0003M_:<\u0007F\u0001\u0003H!\tQ\u0002*\u0003\u0002J)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0019B\u0011!$T\u0005\u0003\u001dR\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011\u0011\u000b\u0016\t\u00035IK!a\u0015\u000b\u0003\u000f\t{w\u000e\\3b]\"9QKBA\u0001\u0002\u0004\t\u0014a\u0001=%c\u0005y\"+[2i)>duN\\4CS\u001a+hn\u0019;j_:\f5OR;oGRLwN\u001c\u001a\u0011\u0005mB1C\u0001\u0005Z!\tQ\",\u0003\u0002\\)\t1\u0011I\\=SK\u001a$\u0012aV\u0001\u0012CN\u001c6-\u00197bI\u0015DH/\u001a8tS>tWcA0cIR\u0011\u0001-\u001a\t\u00065\u0005\u000b7m\u0011\t\u0003U\t$Q\u0001\f\u0006C\u00025\u0002\"A\u000b3\u0005\u000bYR!\u0019A\u0017\t\u000b\u0019T\u0001\u0019A4\u0002\u000b\u0011\"\b.[:\u0011\tm\u0002\u0011m\u0019\u0015\u0003\u0015\u001d\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u00191n\\9\u0015\u0005-c\u0007\"\u00024\f\u0001\u0004i\u0007\u0003B\u001e\u0001]B\u0004\"AK8\u0005\u000b1Z!\u0019A\u0017\u0011\u0005)\nH!\u0002\u001c\f\u0005\u0004i\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\r!(\u0010 \u000b\u0003k^$\"!\u0015<\t\u000fUc\u0011\u0011!a\u0001c!)a\r\u0004a\u0001qB!1\bA=|!\tQ#\u0010B\u0003-\u0019\t\u0007Q\u0006\u0005\u0002+y\u0012)a\u0007\u0004b\u0001[\u0001")
public final class RichToLongBiFunctionAsFunction2<T, U> {
    private final ToLongBiFunction<T, U> scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying;

    public static <T, U> boolean equals$extension(ToLongBiFunction<T, U> toLongBiFunction, Object object) {
        return RichToLongBiFunctionAsFunction2$.MODULE$.equals$extension(toLongBiFunction, object);
    }

    public static <T, U> int hashCode$extension(ToLongBiFunction<T, U> toLongBiFunction) {
        return RichToLongBiFunctionAsFunction2$.MODULE$.hashCode$extension(toLongBiFunction);
    }

    public static <T, U> Function2<T, U, Object> asScala$extension(ToLongBiFunction<T, U> toLongBiFunction) {
        return RichToLongBiFunctionAsFunction2$.MODULE$.asScala$extension(toLongBiFunction);
    }

    public ToLongBiFunction<T, U> scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying;
    }

    public Function2<T, U, Object> asScala() {
        return RichToLongBiFunctionAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichToLongBiFunctionAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichToLongBiFunctionAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying(), x$1);
    }

    public RichToLongBiFunctionAsFunction2(ToLongBiFunction<T, U> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichToLongBiFunctionAsFunction2$$underlying = underlying;
    }
}

