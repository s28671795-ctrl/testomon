/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichToLongFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bUBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'!\b*jG\"$v\u000eT8oO\u001a+hn\u0019;j_:\f5OR;oGRLwN\\\u0019\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005aY3C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007\u000eV8M_:<g)\u001e8di&|g.Q:Gk:\u001cG/[8oc\u0011\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u00012\u0001I\u0014*\u001b\u0005\t#B\u0001\u0012$\u0003!1WO\\2uS>t'B\u0001\u0013&\u0003\u0011)H/\u001b7\u000b\u0003\u0019\nAA[1wC&\u0011\u0001&\t\u0002\u000f)>duN\\4Gk:\u001cG/[8o!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019A\u0017\u0003\u0003Q\u000b\"AL\u0019\u0011\u0005iy\u0013B\u0001\u0019\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0007\u001a\n\u0005M\"\"aA!os\u0006!6oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i)>duN\\4Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u00028sA\u0019\u0001\bA\u0015\u000e\u00039AQAO\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u001d\t7oU2bY\u0006,\u0012!\u0010\t\u00055yJ\u0003)\u0003\u0002@)\tIa)\u001e8di&|g.\r\t\u00035\u0005K!A\u0011\u000b\u0003\t1{gn\u001a\u0015\u0003\t\u0011\u0003\"AG#\n\u0005\u0019#\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005I\u0005C\u0001\u000eK\u0013\tYECA\u0002J]R\fa!Z9vC2\u001cHC\u0001(R!\tQr*\u0003\u0002Q)\t9!i\\8mK\u0006t\u0007b\u0002*\u0007\u0003\u0003\u0005\r!M\u0001\u0004q\u0012\n\u0014!\b*jG\"$v\u000eT8oO\u001a+hn\u0019;j_:\f5OR;oGRLwN\\\u0019\u0011\u0005aB1C\u0001\u0005W!\tQr+\u0003\u0002Y)\t1\u0011I\\=SK\u001a$\u0012\u0001V\u0001\u0012CN\u001c6-\u00197bI\u0015DH/\u001a8tS>tWC\u0001/`)\ti\u0006\r\u0005\u0003\u001b}y\u0003\u0005C\u0001\u0016`\t\u0015a#B1\u0001.\u0011\u0015\t'\u00021\u0001c\u0003\u0015!C\u000f[5t!\rA\u0004A\u0018\u0015\u0003\u0015\u0011\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u0011aM\u001b\u000b\u0003\u0011\u001eDQ!Y\u0006A\u0002!\u00042\u0001\u000f\u0001j!\tQ#\u000eB\u0003-\u0017\t\u0007Q&\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]V\u0011Qn\u001d\u000b\u0003]B$\"AT8\t\u000fIc\u0011\u0011!a\u0001c!)\u0011\r\u0004a\u0001cB\u0019\u0001\b\u0001:\u0011\u0005)\u001aH!\u0002\u0017\r\u0005\u0004i\u0003")
public final class RichToLongFunctionAsFunction1<T> {
    private final ToLongFunction<T> scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying;

    public static <T> boolean equals$extension(ToLongFunction<T> toLongFunction, Object object) {
        return RichToLongFunctionAsFunction1$.MODULE$.equals$extension(toLongFunction, object);
    }

    public static <T> int hashCode$extension(ToLongFunction<T> toLongFunction) {
        return RichToLongFunctionAsFunction1$.MODULE$.hashCode$extension(toLongFunction);
    }

    public static <T> Function1<T, Object> asScala$extension(ToLongFunction<T> toLongFunction) {
        return RichToLongFunctionAsFunction1$.MODULE$.asScala$extension(toLongFunction);
    }

    public ToLongFunction<T> scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying;
    }

    public Function1<T, Object> asScala() {
        return RichToLongFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichToLongFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichToLongFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying(), x$1);
    }

    public RichToLongFunctionAsFunction1(ToLongFunction<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichToLongFunctionAsFunction1$$underlying = underlying;
    }
}

