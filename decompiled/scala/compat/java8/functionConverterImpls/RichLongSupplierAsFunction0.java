/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongSupplier;
import scala.Function0;
import scala.compat.java8.functionConverterImpls.RichLongSupplierAsFunction0$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&a\u0007*jG\"duN\\4TkB\u0004H.[3s\u0003N4UO\\2uS>t\u0007G\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u00011A\u0011\u0011DG\u0007\u0002)%\u00111\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002#N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b'p]\u001e\u001cV\u000f\u001d9mS\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c81I\u0011*h\u000eZ3sYfLgnZ\u000b\u0002=A\u0011qDJ\u0007\u0002A)\u0011\u0011EI\u0001\tMVt7\r^5p]*\u00111\u0005J\u0001\u0005kRLGNC\u0001&\u0003\u0011Q\u0017M^1\n\u0005\u001d\u0002#\u0001\u0004'p]\u001e\u001cV\u000f\u001d9mS\u0016\u0014\u0018AU:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"duN\\4TkB\u0004H.[3s\u0003N4UO\\2uS>t\u0007\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002,[A\u0011A\u0006A\u0007\u0002\u001d!)af\u0001a\u0001=\u0005QQO\u001c3fe2L\u0018N\\4\u0002\u000f\u0005\u001c8kY1mCV\t\u0011\u0007E\u0002\u001aeQJ!a\r\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004CA\r6\u0013\t1DC\u0001\u0003M_:<\u0007F\u0001\u00039!\tI\u0012(\u0003\u0002;)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002{A\u0011\u0011DP\u0005\u0003\u007fQ\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011!)\u0012\t\u00033\rK!\u0001\u0012\u000b\u0003\u000f\t{w\u000e\\3b]\"9aIBA\u0001\u0002\u00049\u0015a\u0001=%cA\u0011\u0011\u0004S\u0005\u0003\u0013R\u00111!\u00118z\u0003m\u0011\u0016n\u00195M_:<7+\u001e9qY&,'/Q:Gk:\u001cG/[8oaA\u0011A\u0006C\n\u0003\u00115\u0003\"!\u0007(\n\u0005=#\"AB!osJ+g\rF\u0001L\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\u001c\u000b\u0003cMCQ\u0001\u0016\u0006A\u0002-\nQ\u0001\n;iSND#A\u0003\u001d\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003yaCQ\u0001V\u0006A\u0002-\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005mkFC\u0001\"]\u0011\u001d1E\"!AA\u0002\u001dCQ\u0001\u0016\u0007A\u0002-\u0002")
public final class RichLongSupplierAsFunction0 {
    private final LongSupplier scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying;

    public static boolean equals$extension(LongSupplier longSupplier, Object object) {
        return RichLongSupplierAsFunction0$.MODULE$.equals$extension(longSupplier, object);
    }

    public static int hashCode$extension(LongSupplier longSupplier) {
        return RichLongSupplierAsFunction0$.MODULE$.hashCode$extension(longSupplier);
    }

    public static Function0<Object> asScala$extension(LongSupplier longSupplier) {
        return RichLongSupplierAsFunction0$.MODULE$.asScala$extension(longSupplier);
    }

    public LongSupplier scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying;
    }

    public Function0<Object> asScala() {
        return RichLongSupplierAsFunction0$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying());
    }

    public int hashCode() {
        return RichLongSupplierAsFunction0$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongSupplierAsFunction0$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying(), x$1);
    }

    public RichLongSupplierAsFunction0(LongSupplier underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongSupplierAsFunction0$$underlying = underlying;
    }
}

