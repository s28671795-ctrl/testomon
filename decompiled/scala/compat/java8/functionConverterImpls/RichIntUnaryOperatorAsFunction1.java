/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichIntUnaryOperatorAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001di\u0004!!A\u0005By:qa\u0012\b\u0002\u0002#\u0005\u0001JB\u0004\u000e\u001d\u0005\u0005\t\u0012A%\t\u000b%BA\u0011A'\t\u000b9CAQA(\t\u000fMC\u0011\u0011!C\u0003)\"9a\u000bCA\u0001\n\u000b9&a\b*jG\"Le\u000e^+oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8oc)\u0011q\u0002E\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0011CE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003'Q\taaY8na\u0006$(\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001\u0001\u0007\t\u00033ii\u0011\u0001F\u0005\u00037Q\u0011a!\u00118z-\u0006d\u0017!V:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"Le\u000e^+oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8oc\u0011\"SO\u001c3fe2L\u0018N\\4\u0016\u0003y\u0001\"a\b\u0014\u000e\u0003\u0001R!!\t\u0012\u0002\u0011\u0019,hn\u0019;j_:T!a\t\u0013\u0002\tU$\u0018\u000e\u001c\u0006\u0002K\u0005!!.\u0019<b\u0013\t9\u0003E\u0001\tJ]R,f.\u0019:z\u001fB,'/\u0019;pe\u000616oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\u0013:$XK\\1ss>\u0003XM]1u_J\f5OR;oGRLwN\\\u0019%IUtG-\u001a:ms&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005-j\u0003C\u0001\u0017\u0001\u001b\u0005q\u0001\"\u0002\u0018\u0004\u0001\u0004q\u0012AC;oI\u0016\u0014H._5oO\u00069\u0011m]*dC2\fW#A\u0019\u0011\te\u0011D\u0007N\u0005\u0003gQ\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005e)\u0014B\u0001\u001c\u0015\u0005\rIe\u000e\u001e\u0015\u0003\ta\u0002\"!G\u001d\n\u0005i\"\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005!\u0014AB3rk\u0006d7\u000f\u0006\u0002@\u0005B\u0011\u0011\u0004Q\u0005\u0003\u0003R\u0011qAQ8pY\u0016\fg\u000eC\u0004D\r\u0005\u0005\t\u0019\u0001#\u0002\u0007a$\u0013\u0007\u0005\u0002\u001a\u000b&\u0011a\t\u0006\u0002\u0004\u0003:L\u0018a\b*jG\"Le\u000e^+oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8ocA\u0011A\u0006C\n\u0003\u0011)\u0003\"!G&\n\u00051#\"AB!osJ+g\rF\u0001I\u0003E\t7oU2bY\u0006$S\r\u001f;f]NLwN\u001c\u000b\u0003cACQ!\u0015\u0006A\u0002-\nQ\u0001\n;iSND#A\u0003\u001d\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003yUCQ!U\u0006A\u0002-\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005aSFCA Z\u0011\u001d\u0019E\"!AA\u0002\u0011CQ!\u0015\u0007A\u0002-\u0002")
public final class RichIntUnaryOperatorAsFunction1 {
    private final IntUnaryOperator scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying;

    public static boolean equals$extension(IntUnaryOperator intUnaryOperator, Object object) {
        return RichIntUnaryOperatorAsFunction1$.MODULE$.equals$extension(intUnaryOperator, object);
    }

    public static int hashCode$extension(IntUnaryOperator intUnaryOperator) {
        return RichIntUnaryOperatorAsFunction1$.MODULE$.hashCode$extension(intUnaryOperator);
    }

    public static Function1<Object, Object> asScala$extension(IntUnaryOperator intUnaryOperator) {
        return RichIntUnaryOperatorAsFunction1$.MODULE$.asScala$extension(intUnaryOperator);
    }

    public IntUnaryOperator scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying;
    }

    public Function1<Object, Object> asScala() {
        return RichIntUnaryOperatorAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichIntUnaryOperatorAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichIntUnaryOperatorAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying(), x$1);
    }

    public RichIntUnaryOperatorAsFunction1(IntUnaryOperator underlying) {
        this.scala$compat$java8$functionConverterImpls$RichIntUnaryOperatorAsFunction1$$underlying = underlying;
    }
}

