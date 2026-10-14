/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsDoubleFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bEBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'!\b*jG\"4UO\\2uS>t\u0017'Q:E_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005a93C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019Bg\u0012{WO\u00197f\rVt7\r^5p]\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u0001BA\u0007\u0011#K%\u0011\u0011\u0005\u0006\u0002\n\rVt7\r^5p]F\u0002\"AG\u0012\n\u0005\u0011\"\"A\u0002#pk\ndW\r\u0005\u0002'O1\u0001A!\u0002\u0015\u0001\u0005\u0004I#!\u0001*\u0012\u0005)j\u0003C\u0001\u000e,\u0013\taCCA\u0004O_RD\u0017N\\4\u0011\u0005iq\u0013BA\u0018\u0015\u0005\r\te._\u0001Ug\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019Bg\u0012{WO\u00197f\rVt7\r^5p]\u0012\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u00111'\u000e\t\u0004i\u0001)S\"\u0001\b\t\u000bY\u001a\u0001\u0019A\u0010\u0002\u0015UtG-\u001a:ms&tw-\u0001\u0004bg*\u000bg/Y\u000b\u0002sA\u0019!(Q\u0013\u000e\u0003mR!\u0001P\u001f\u0002\u0011\u0019,hn\u0019;j_:T!AP \u0002\tU$\u0018\u000e\u001c\u0006\u0002\u0001\u0006!!.\u0019<b\u0013\t\u00115H\u0001\bE_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8)\u0005\u0011!\u0005C\u0001\u000eF\u0013\t1EC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0011\n\u0005\u0002\u001b\u0015&\u00111\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002O#B\u0011!dT\u0005\u0003!R\u0011qAQ8pY\u0016\fg\u000eC\u0004S\r\u0005\u0005\t\u0019A\u0017\u0002\u0007a$\u0013'A\u000fSS\u000eDg)\u001e8di&|g.M!t\t>,(\r\\3Gk:\u001cG/[8o!\t!\u0004b\u0005\u0002\t-B\u0011!dV\u0005\u00031R\u0011a!\u00118z%\u00164G#\u0001+\u0002!\u0005\u001c(*\u0019<bI\u0015DH/\u001a8tS>tWC\u0001/`)\ti\u0006\rE\u0002;\u0003z\u0003\"AJ0\u0005\u000b!R!\u0019A\u0015\t\u000b\u0005T\u0001\u0019\u00012\u0002\u000b\u0011\"\b.[:\u0011\u0007Q\u0002a\f\u000b\u0002\u000b\t\u0006\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o+\t1'\u000e\u0006\u0002IO\")\u0011m\u0003a\u0001QB\u0019A\u0007A5\u0011\u0005\u0019RG!\u0002\u0015\f\u0005\u0004I\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\ti7\u000f\u0006\u0002oaR\u0011aj\u001c\u0005\b%2\t\t\u00111\u0001.\u0011\u0015\tG\u00021\u0001r!\r!\u0004A\u001d\t\u0003MM$Q\u0001\u000b\u0007C\u0002%\u0002")
public final class RichFunction1AsDoubleFunction<R> {
    private final Function1<Object, R> scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying;

    public static <R> boolean equals$extension(Function1<Object, R> function1, Object object) {
        return RichFunction1AsDoubleFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <R> int hashCode$extension(Function1<Object, R> function1) {
        return RichFunction1AsDoubleFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <R> DoubleFunction<R> asJava$extension(Function1<Object, R> function1) {
        return RichFunction1AsDoubleFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, R> scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying;
    }

    public DoubleFunction<R> asJava() {
        return RichFunction1AsDoubleFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsDoubleFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsDoubleFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying(), x$1);
    }

    public RichFunction1AsDoubleFunction(Function1<Object, R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleFunction$$underlying = underlying;
    }
}

