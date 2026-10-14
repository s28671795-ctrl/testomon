/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichDoubleFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bUBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'!\b*jG\"$u.\u001e2mK\u001a+hn\u0019;j_:\f5OR;oGRLwN\\\u0019\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005aY3C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007\u000eR8vE2,g)\u001e8di&|g.Q:Gk:\u001cG/[8oc\u0011\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u00012\u0001I\u0014*\u001b\u0005\t#B\u0001\u0012$\u0003!1WO\\2uS>t'B\u0001\u0013&\u0003\u0011)H/\u001b7\u000b\u0003\u0019\nAA[1wC&\u0011\u0001&\t\u0002\u000f\t>,(\r\\3Gk:\u001cG/[8o!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019A\u0017\u0003\u0003I\u000b\"AL\u0019\u0011\u0005iy\u0013B\u0001\u0019\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0007\u001a\n\u0005M\"\"aA!os\u0006!6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\t>,(\r\\3Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00028sA\u0019\u0001\bA\u0015\u000e\u00039AQAO\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012!\u0010\t\u00055y\u0002\u0015&\u0003\u0002@)\tIa)\u001e8di&|g.\r\t\u00035\u0005K!A\u0011\u000b\u0003\r\u0011{WO\u00197fQ\t!A\t\u0005\u0002\u001b\u000b&\u0011a\t\u0006\u0002\u0007S:d\u0017N\\3\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!\u0013\t\u00035)K!a\u0013\u000b\u0003\u0007%sG/\u0001\u0004fcV\fGn\u001d\u000b\u0003\u001dF\u0003\"AG(\n\u0005A#\"a\u0002\"p_2,\u0017M\u001c\u0005\b%\u001a\t\t\u00111\u00012\u0003\rAH%M\u0001\u001e%&\u001c\u0007\u000eR8vE2,g)\u001e8di&|g.Q:Gk:\u001cG/[8ocA\u0011\u0001\bC\n\u0003\u0011Y\u0003\"AG,\n\u0005a#\"AB!osJ+g\rF\u0001U\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\\\u000b\u00039~#\"!\u00181\u0011\tiq\u0004I\u0018\t\u0003U}#Q\u0001\f\u0006C\u00025BQ!\u0019\u0006A\u0002\t\fQ\u0001\n;iSN\u00042\u0001\u000f\u0001_Q\tQA)\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tWC\u00014k)\tAu\rC\u0003b\u0017\u0001\u0007\u0001\u000eE\u00029\u0001%\u0004\"A\u000b6\u0005\u000b1Z!\u0019A\u0017\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWCA7t)\tq\u0007\u000f\u0006\u0002O_\"9!\u000bDA\u0001\u0002\u0004\t\u0004\"B1\r\u0001\u0004\t\bc\u0001\u001d\u0001eB\u0011!f\u001d\u0003\u0006Y1\u0011\r!\f")
public final class RichDoubleFunctionAsFunction1<R> {
    private final DoubleFunction<R> scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying;

    public static <R> boolean equals$extension(DoubleFunction<R> doubleFunction, Object object) {
        return RichDoubleFunctionAsFunction1$.MODULE$.equals$extension(doubleFunction, object);
    }

    public static <R> int hashCode$extension(DoubleFunction<R> doubleFunction) {
        return RichDoubleFunctionAsFunction1$.MODULE$.hashCode$extension(doubleFunction);
    }

    public static <R> Function1<Object, R> asScala$extension(DoubleFunction<R> doubleFunction) {
        return RichDoubleFunctionAsFunction1$.MODULE$.asScala$extension(doubleFunction);
    }

    public DoubleFunction<R> scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying;
    }

    public Function1<Object, R> asScala() {
        return RichDoubleFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichDoubleFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichDoubleFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying(), x$1);
    }

    public RichDoubleFunctionAsFunction1(DoubleFunction<R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichDoubleFunctionAsFunction1$$underlying = underlying;
    }
}

