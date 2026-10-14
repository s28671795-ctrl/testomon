/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongBiFunction;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsToLongBiFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bQBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f%D\u0011\u0011!C\u0003U\"9!\u000fCA\u0001\n\u000b\u0019(a\b*jG\"4UO\\2uS>t''Q:U_2{gn\u001a\"j\rVt7\r^5p]*\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019\u0001\u0004\n\u0018\u0014\u0005\u0001I\u0002C\u0001\u000e\u001c\u001b\u0005!\u0012B\u0001\u000f\u0015\u0005\u0019\te.\u001f,bY\u0006)6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]J\n5\u000fV8M_:<')\u001b$v]\u000e$\u0018n\u001c8%IUtG-\u001a:ms&tw-F\u0001 !\u0015Q\u0002EI\u00171\u0013\t\tCCA\u0005Gk:\u001cG/[8oeA\u00111\u0005\n\u0007\u0001\t\u0015)\u0003A1\u0001'\u0005\u0005!\u0016CA\u0014+!\tQ\u0002&\u0003\u0002*)\t9aj\u001c;iS:<\u0007C\u0001\u000e,\u0013\taCCA\u0002B]f\u0004\"a\t\u0018\u0005\u000b=\u0002!\u0019\u0001\u0014\u0003\u0003U\u0003\"AG\u0019\n\u0005I\"\"\u0001\u0002'p]\u001e\fak]2bY\u0006$3m\\7qCR$#.\u0019<bq\u00112WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001cHEU5dQ\u001a+hn\u0019;j_:\u0014\u0014i\u001d+p\u0019>twMQ5Gk:\u001cG/[8oI\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t1\u0004\b\u0005\u00038\u0001\tjS\"\u0001\b\t\u000be\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0004bg*\u000bg/Y\u000b\u0002yA!Q\b\u0012\u0012.\u001b\u0005q$BA A\u0003!1WO\\2uS>t'BA!C\u0003\u0011)H/\u001b7\u000b\u0003\r\u000bAA[1wC&\u0011QI\u0010\u0002\u0011)>duN\\4CS\u001a+hn\u0019;j_:D#\u0001B$\u0011\u0005iA\u0015BA%\u0015\u0005\u0019Ig\u000e\\5oK\u0006A\u0001.Y:i\u0007>$W\rF\u0001M!\tQR*\u0003\u0002O)\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\t\tF\u000b\u0005\u0002\u001b%&\u00111\u000b\u0006\u0002\b\u0005>|G.Z1o\u0011\u001d)f!!AA\u0002)\n1\u0001\u001f\u00132\u0003}\u0011\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHk\u001c'p]\u001e\u0014\u0015NR;oGRLwN\u001c\t\u0003o!\u0019\"\u0001C-\u0011\u0005iQ\u0016BA.\u0015\u0005\u0019\te.\u001f*fMR\tq+\u0001\tbg*\u000bg/\u0019\u0013fqR,gn]5p]V\u0019qL\u00193\u0015\u0005\u0001,\u0007\u0003B\u001fEC\u000e\u0004\"a\t2\u0005\u000b\u0015R!\u0019\u0001\u0014\u0011\u0005\r\"G!B\u0018\u000b\u0005\u00041\u0003\"\u00024\u000b\u0001\u00049\u0017!\u0002\u0013uQ&\u001c\b\u0003B\u001c\u0001C\u000eD#AC$\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0004W>\fHCA&m\u0011\u001517\u00021\u0001n!\u00119\u0004A\u001c9\u0011\u0005\rzG!B\u0013\f\u0005\u00041\u0003CA\u0012r\t\u0015y3B1\u0001'\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g.F\u0002uur$\"!^<\u0015\u0005E3\bbB+\r\u0003\u0003\u0005\rA\u000b\u0005\u0006M2\u0001\r\u0001\u001f\t\u0005o\u0001I8\u0010\u0005\u0002$u\u0012)Q\u0005\u0004b\u0001MA\u00111\u0005 \u0003\u0006_1\u0011\rA\n")
public final class RichFunction2AsToLongBiFunction<T, U> {
    private final Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying;

    public static <T, U> boolean equals$extension(Function2<T, U, Object> function2, Object object) {
        return RichFunction2AsToLongBiFunction$.MODULE$.equals$extension(function2, object);
    }

    public static <T, U> int hashCode$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsToLongBiFunction$.MODULE$.hashCode$extension(function2);
    }

    public static <T, U> ToLongBiFunction<T, U> asJava$extension(Function2<T, U, Object> function2) {
        return RichFunction2AsToLongBiFunction$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, U, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying;
    }

    public ToLongBiFunction<T, U> asJava() {
        return RichFunction2AsToLongBiFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsToLongBiFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsToLongBiFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying(), x$1);
    }

    public RichFunction2AsToLongBiFunction(Function2<T, U, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsToLongBiFunction$$underlying = underlying;
    }
}

