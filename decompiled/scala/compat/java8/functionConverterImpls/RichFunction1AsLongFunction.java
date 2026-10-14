/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsLongFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bEBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'a\u0007*jG\"4UO\\2uS>t\u0017'Q:M_:<g)\u001e8di&|gN\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0019OM\u0011\u0001!\u0007\t\u00035mi\u0011\u0001F\u0005\u00039Q\u0011a!\u00118z-\u0006d\u0017!U:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:M_:<g)\u001e8di&|g\u000e\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012a\b\t\u00055\u0001\u0012S%\u0003\u0002\")\tIa)\u001e8di&|g.\r\t\u00035\rJ!\u0001\n\u000b\u0003\t1{gn\u001a\t\u0003M\u001db\u0001\u0001B\u0003)\u0001\t\u0007\u0011FA\u0001S#\tQS\u0006\u0005\u0002\u001bW%\u0011A\u0006\u0006\u0002\b\u001d>$\b.\u001b8h!\tQb&\u0003\u00020)\t\u0019\u0011I\\=\u0002%N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003NduN\\4Gk:\u001cG/[8oI\u0011*h\u000eZ3sYfLgn\u001a\u0011\u0002\rqJg.\u001b;?)\t\u0019T\u0007E\u00025\u0001\u0015j\u0011A\u0004\u0005\u0006m\r\u0001\raH\u0001\u000bk:$WM\u001d7zS:<\u0017AB1t\u0015\u00064\u0018-F\u0001:!\rQ\u0014)J\u0007\u0002w)\u0011A(P\u0001\tMVt7\r^5p]*\u0011ahP\u0001\u0005kRLGNC\u0001A\u0003\u0011Q\u0017M^1\n\u0005\t[$\u0001\u0004'p]\u001e4UO\\2uS>t\u0007F\u0001\u0003E!\tQR)\u0003\u0002G)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0013B\u0011!DS\u0005\u0003\u0017R\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011a*\u0015\t\u00035=K!\u0001\u0015\u000b\u0003\u000f\t{w\u000e\\3b]\"9!KBA\u0001\u0002\u0004i\u0013a\u0001=%c\u0005Y\"+[2i\rVt7\r^5p]F\n5\u000fT8oO\u001a+hn\u0019;j_:\u0004\"\u0001\u000e\u0005\u0014\u0005!1\u0006C\u0001\u000eX\u0013\tAFC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002)\u0006\u0001\u0012m\u001d&bm\u0006$S\r\u001f;f]NLwN\\\u000b\u00039~#\"!\u00181\u0011\u0007i\ne\f\u0005\u0002'?\u0012)\u0001F\u0003b\u0001S!)\u0011M\u0003a\u0001E\u0006)A\u0005\u001e5jgB\u0019A\u0007\u00010)\u0005)!\u0015A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:,\"A\u001a6\u0015\u0005!;\u0007\"B1\f\u0001\u0004A\u0007c\u0001\u001b\u0001SB\u0011aE\u001b\u0003\u0006Q-\u0011\r!K\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,\"!\\:\u0015\u00059\u0004HC\u0001(p\u0011\u001d\u0011F\"!AA\u00025BQ!\u0019\u0007A\u0002E\u00042\u0001\u000e\u0001s!\t13\u000fB\u0003)\u0019\t\u0007\u0011\u0006")
public final class RichFunction1AsLongFunction<R> {
    private final Function1<Object, R> scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying;

    public static <R> boolean equals$extension(Function1<Object, R> function1, Object object) {
        return RichFunction1AsLongFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <R> int hashCode$extension(Function1<Object, R> function1) {
        return RichFunction1AsLongFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <R> LongFunction<R> asJava$extension(Function1<Object, R> function1) {
        return RichFunction1AsLongFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, R> scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying;
    }

    public LongFunction<R> asJava() {
        return RichFunction1AsLongFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsLongFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsLongFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying(), x$1);
    }

    public RichFunction1AsLongFunction(Function1<Object, R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsLongFunction$$underlying = underlying;
    }
}

