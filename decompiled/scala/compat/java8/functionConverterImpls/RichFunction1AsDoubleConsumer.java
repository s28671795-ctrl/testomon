/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsDoubleConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00054A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;q!\u0014\b\u0002\u0002#\u0005aJB\u0004\u000e\u001d\u0005\u0005\t\u0012A(\t\u000b!BA\u0011A*\t\u000bQCAQA+\t\u000feC\u0011\u0011!C\u00035\"9A\fCA\u0001\n\u000bi&!\b*jG\"4UO\\2uS>t\u0017'Q:E_V\u0014G.Z\"p]N,X.\u001a:\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001A\u0002CA\r\u001b\u001b\u0005!\u0012BA\u000e\u0015\u0005\u0019\te.\u001f,bY\u0006\u00196oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\rVt7\r^5p]F\n5\u000fR8vE2,7i\u001c8tk6,'\u000f\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012A\b\t\u00053}\tC%\u0003\u0002!)\tIa)\u001e8di&|g.\r\t\u00033\tJ!a\t\u000b\u0003\r\u0011{WO\u00197f!\tIR%\u0003\u0002')\t!QK\\5u\u0003Q\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bHi\\;cY\u0016\u001cuN\\:v[\u0016\u0014H\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u0016-!\tY\u0003!D\u0001\u000f\u0011\u0015i3\u00011\u0001\u001f\u0003))h\u000eZ3sYfLgnZ\u0001\u0007CNT\u0015M^1\u0016\u0003A\u0002\"!\r\u001d\u000e\u0003IR!a\r\u001b\u0002\u0011\u0019,hn\u0019;j_:T!!\u000e\u001c\u0002\tU$\u0018\u000e\u001c\u0006\u0002o\u0005!!.\u0019<b\u0013\tI$G\u0001\bE_V\u0014G.Z\"p]N,X.\u001a:)\u0005\u0011Y\u0004CA\r=\u0013\tiDC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001\t\u0005\u0002\u001a\u0003&\u0011!\t\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002F\u0011B\u0011\u0011DR\u0005\u0003\u000fR\u0011qAQ8pY\u0016\fg\u000eC\u0004J\r\u0005\u0005\t\u0019\u0001&\u0002\u0007a$\u0013\u0007\u0005\u0002\u001a\u0017&\u0011A\n\u0006\u0002\u0004\u0003:L\u0018!\b*jG\"4UO\\2uS>t\u0017'Q:E_V\u0014G.Z\"p]N,X.\u001a:\u0011\u0005-B1C\u0001\u0005Q!\tI\u0012+\u0003\u0002S)\t1\u0011I\\=SK\u001a$\u0012AT\u0001\u0011CNT\u0015M^1%Kb$XM\\:j_:$\"\u0001\r,\t\u000b]S\u0001\u0019\u0001\u0016\u0002\u000b\u0011\"\b.[:)\u0005)Y\u0014A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$\"aP.\t\u000b][\u0001\u0019\u0001\u0016\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tGC\u00010a)\t)u\fC\u0004J\u0019\u0005\u0005\t\u0019\u0001&\t\u000b]c\u0001\u0019\u0001\u0016")
public final class RichFunction1AsDoubleConsumer {
    private final Function1<Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsDoubleConsumer$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsDoubleConsumer$.MODULE$.hashCode$extension(function1);
    }

    public static DoubleConsumer asJava$extension(Function1 function1) {
        return RichFunction1AsDoubleConsumer$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying;
    }

    public DoubleConsumer asJava() {
        return RichFunction1AsDoubleConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsDoubleConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsDoubleConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying(), x$1);
    }

    public RichFunction1AsDoubleConsumer(Function1<Object, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleConsumer$$underlying = underlying;
    }
}

