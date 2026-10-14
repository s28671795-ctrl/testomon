/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsIntBinaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001di\u0004!!A\u0005By:qa\u0012\b\u0002\u0002#\u0005\u0001JB\u0004\u000e\u001d\u0005\u0005\t\u0012A%\t\u000b\u0015BA\u0011A'\t\u000b9CAQA(\t\u000fMC\u0011\u0011!C\u0003)\"9a\u000bCA\u0001\n\u000b9&\u0001\t*jG\"4UO\\2uS>t''Q:J]R\u0014\u0015N\\1ss>\u0003XM]1u_JT!a\u0004\t\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!!\u0005\n\u0002\u000b)\fg/\u0019\u001d\u000b\u0005M!\u0012AB2p[B\fGOC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019\"\u0001\u0001\r\u0011\u0005eQR\"\u0001\u000b\n\u0005m!\"AB!osZ\u000bG.\u0001,tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|gNM!t\u0013:$()\u001b8bef|\u0005/\u001a:bi>\u0014H\u0005J;oI\u0016\u0014H._5oOV\ta\u0004E\u0003\u001a?\u0005\n\u0013%\u0003\u0002!)\tIa)\u001e8di&|gN\r\t\u00033\tJ!a\t\u000b\u0003\u0007%sG/A,tG\u0006d\u0017\rJ2p[B\fG\u000f\n6bm\u0006DDEZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0013SS\u000eDg)\u001e8di&|gNM!t\u0013:$()\u001b8bef|\u0005/\u001a:bi>\u0014H\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDCA\u0014*!\tA\u0003!D\u0001\u000f\u0011\u0015Q3\u00011\u0001\u001f\u0003))h\u000eZ3sYfLgnZ\u0001\u0007CNT\u0015M^1\u0016\u00035\u0002\"AL\u001b\u000e\u0003=R!\u0001M\u0019\u0002\u0011\u0019,hn\u0019;j_:T!AM\u001a\u0002\tU$\u0018\u000e\u001c\u0006\u0002i\u0005!!.\u0019<b\u0013\t1tFA\tJ]R\u0014\u0015N\\1ss>\u0003XM]1u_JD#\u0001\u0002\u001d\u0011\u0005eI\u0014B\u0001\u001e\u0015\u0005\u0019Ig\u000e\\5oK\u0006A\u0001.Y:i\u0007>$W\rF\u0001\"\u0003\u0019)\u0017/^1mgR\u0011qH\u0011\t\u00033\u0001K!!\u0011\u000b\u0003\u000f\t{w\u000e\\3b]\"91IBA\u0001\u0002\u0004!\u0015a\u0001=%cA\u0011\u0011$R\u0005\u0003\rR\u00111!\u00118z\u0003\u0001\u0012\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001b\u0018J\u001c;CS:\f'/_(qKJ\fGo\u001c:\u0011\u0005!B1C\u0001\u0005K!\tI2*\u0003\u0002M)\t1\u0011I\\=SK\u001a$\u0012\u0001S\u0001\u0011CNT\u0015M^1%Kb$XM\\:j_:$\"!\f)\t\u000bES\u0001\u0019A\u0014\u0002\u000b\u0011\"\b.[:)\u0005)A\u0014A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$\"\u0001P+\t\u000bE[\u0001\u0019A\u0014\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tGC\u0001-[)\ty\u0014\fC\u0004D\u0019\u0005\u0005\t\u0019\u0001#\t\u000bEc\u0001\u0019A\u0014")
public final class RichFunction2AsIntBinaryOperator {
    private final Function2<Object, Object, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying;

    public static boolean equals$extension(Function2 function2, Object object) {
        return RichFunction2AsIntBinaryOperator$.MODULE$.equals$extension(function2, object);
    }

    public static int hashCode$extension(Function2 function2) {
        return RichFunction2AsIntBinaryOperator$.MODULE$.hashCode$extension(function2);
    }

    public static IntBinaryOperator asJava$extension(Function2 function2) {
        return RichFunction2AsIntBinaryOperator$.MODULE$.asJava$extension(function2);
    }

    public Function2<Object, Object, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying;
    }

    public IntBinaryOperator asJava() {
        return RichFunction2AsIntBinaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsIntBinaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsIntBinaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying(), x$1);
    }

    public RichFunction2AsIntBinaryOperator(Function2<Object, Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsIntBinaryOperator$$underlying = underlying;
    }
}

