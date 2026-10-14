/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntPredicate;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsIntPredicate$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qa\u0012\b\u0002\u0002#\u0005\u0001JB\u0004\u000e\u001d\u0005\u0005\t\u0012A%\t\u000b!BA\u0011A'\t\u000b9CAQA(\t\u000fMC\u0011\u0011!C\u0003)\"9a\u000bCA\u0001\n\u000b9&a\u0007*jG\"4UO\\2uS>t\u0017'Q:J]R\u0004&/\u001a3jG\u0006$XM\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u00011A\u0011\u0011DG\u0007\u0002)%\u00111\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002#N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NLe\u000e\u001e)sK\u0012L7-\u0019;fI\u0011*h\u000eZ3sYfLgnZ\u000b\u0002=A!\u0011dH\u0011%\u0013\t\u0001CCA\u0005Gk:\u001cG/[8ocA\u0011\u0011DI\u0005\u0003GQ\u00111!\u00138u!\tIR%\u0003\u0002')\t9!i\\8mK\u0006t\u0017AU:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:J]R\u0004&/\u001a3jG\u0006$X\r\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002+YA\u00111\u0006A\u0007\u0002\u001d!)Qf\u0001a\u0001=\u0005QQO\u001c3fe2L\u0018N\\4\u0002\r\u0005\u001c(*\u0019<b+\u0005\u0001\u0004CA\u00199\u001b\u0005\u0011$BA\u001a5\u0003!1WO\\2uS>t'BA\u001b7\u0003\u0011)H/\u001b7\u000b\u0003]\nAA[1wC&\u0011\u0011H\r\u0002\r\u0013:$\bK]3eS\u000e\fG/\u001a\u0015\u0003\tm\u0002\"!\u0007\u001f\n\u0005u\"\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005\t\u0013AB3rk\u0006d7\u000f\u0006\u0002%\u0005\"91IBA\u0001\u0002\u0004!\u0015a\u0001=%cA\u0011\u0011$R\u0005\u0003\rR\u00111!\u00118z\u0003m\u0011\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001b\u0018J\u001c;Qe\u0016$\u0017nY1uKB\u00111\u0006C\n\u0003\u0011)\u0003\"!G&\n\u00051#\"AB!osJ+g\rF\u0001I\u0003A\t7OS1wC\u0012*\u0007\u0010^3og&|g\u000e\u0006\u00021!\")\u0011K\u0003a\u0001U\u0005)A\u0005\u001e5jg\"\u0012!bO\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002@+\")\u0011k\u0003a\u0001U\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\u001c\u000b\u00031j#\"\u0001J-\t\u000f\rc\u0011\u0011!a\u0001\t\")\u0011\u000b\u0004a\u0001U\u0001")
public final class RichFunction1AsIntPredicate {
    private final Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsIntPredicate$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsIntPredicate$.MODULE$.hashCode$extension(function1);
    }

    public static IntPredicate asJava$extension(Function1 function1) {
        return RichFunction1AsIntPredicate$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying;
    }

    public IntPredicate asJava() {
        return RichFunction1AsIntPredicate$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsIntPredicate$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsIntPredicate$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying(), x$1);
    }

    public RichFunction1AsIntPredicate(Function1<Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntPredicate$$underlying = underlying;
    }
}

