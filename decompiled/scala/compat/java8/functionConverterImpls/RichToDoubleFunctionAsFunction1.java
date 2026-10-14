/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichToDoubleFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bUBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'a\b*jG\"$v\u000eR8vE2,g)\u001e8di&|g.Q:Gk:\u001cG/[8oc)\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0001dK\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u0003U\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195U_\u0012{WO\u00197f\rVt7\r^5p]\u0006\u001bh)\u001e8di&|g.\r\u0013%k:$WM\u001d7zS:<W#A\u0010\u0011\u0007\u0001:\u0013&D\u0001\"\u0015\t\u00113%\u0001\u0005gk:\u001cG/[8o\u0015\t!S%\u0001\u0003vi&d'\"\u0001\u0014\u0002\t)\fg/Y\u0005\u0003Q\u0005\u0012\u0001\u0003V8E_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8\u0011\u0005)ZC\u0002\u0001\u0003\u0006Y\u0001\u0011\r!\f\u0002\u0002)F\u0011a&\r\t\u00035=J!\u0001\r\u000b\u0003\u000f9{G\u000f[5oOB\u0011!DM\u0005\u0003gQ\u00111!\u00118z\u0003Y\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195U_\u0012{WO\u00197f\rVt7\r^5p]\u0006\u001bh)\u001e8di&|g.\r\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003oe\u00022\u0001\u000f\u0001*\u001b\u0005q\u0001\"\u0002\u001e\u0004\u0001\u0004y\u0012AC;oI\u0016\u0014H._5oO\u00069\u0011m]*dC2\fW#A\u001f\u0011\tiq\u0014\u0006Q\u0005\u0003\u007fQ\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005i\t\u0015B\u0001\"\u0015\u0005\u0019!u.\u001e2mK\"\u0012A\u0001\u0012\t\u00035\u0015K!A\u0012\u000b\u0003\r%tG.\u001b8f\u0003!A\u0017m\u001d5D_\u0012,G#A%\u0011\u0005iQ\u0015BA&\u0015\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u00059\u000b\u0006C\u0001\u000eP\u0013\t\u0001FCA\u0004C_>dW-\u00198\t\u000fI3\u0011\u0011!a\u0001c\u0005\u0019\u0001\u0010J\u0019\u0002?IK7\r\u001b+p\t>,(\r\\3Gk:\u001cG/[8o\u0003N4UO\\2uS>t\u0017\u0007\u0005\u00029\u0011M\u0011\u0001B\u0016\t\u00035]K!\u0001\u0017\u000b\u0003\r\u0005s\u0017PU3g)\u0005!\u0016!E1t'\u000e\fG.\u0019\u0013fqR,gn]5p]V\u0011Al\u0018\u000b\u0003;\u0002\u0004BA\u0007 _\u0001B\u0011!f\u0018\u0003\u0006Y)\u0011\r!\f\u0005\u0006C*\u0001\rAY\u0001\u0006IQD\u0017n\u001d\t\u0004q\u0001q\u0006F\u0001\u0006E\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005\u0019TGC\u0001%h\u0011\u0015\t7\u00021\u0001i!\rA\u0004!\u001b\t\u0003U)$Q\u0001L\u0006C\u00025\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0016\u00055\u001cHC\u00018q)\tqu\u000eC\u0004S\u0019\u0005\u0005\t\u0019A\u0019\t\u000b\u0005d\u0001\u0019A9\u0011\u0007a\u0002!\u000f\u0005\u0002+g\u0012)A\u0006\u0004b\u0001[\u0001")
public final class RichToDoubleFunctionAsFunction1<T> {
    private final ToDoubleFunction<T> scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying;

    public static <T> boolean equals$extension(ToDoubleFunction<T> toDoubleFunction, Object object) {
        return RichToDoubleFunctionAsFunction1$.MODULE$.equals$extension(toDoubleFunction, object);
    }

    public static <T> int hashCode$extension(ToDoubleFunction<T> toDoubleFunction) {
        return RichToDoubleFunctionAsFunction1$.MODULE$.hashCode$extension(toDoubleFunction);
    }

    public static <T> Function1<T, Object> asScala$extension(ToDoubleFunction<T> toDoubleFunction) {
        return RichToDoubleFunctionAsFunction1$.MODULE$.asScala$extension(toDoubleFunction);
    }

    public ToDoubleFunction<T> scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying;
    }

    public Function1<T, Object> asScala() {
        return RichToDoubleFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichToDoubleFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichToDoubleFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying(), x$1);
    }

    public RichToDoubleFunctionAsFunction1(ToDoubleFunction<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichToDoubleFunctionAsFunction1$$underlying = underlying;
    }
}

