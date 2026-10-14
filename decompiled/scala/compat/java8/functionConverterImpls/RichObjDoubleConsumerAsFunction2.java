/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjDoubleConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichObjDoubleConsumerAsFunction2$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005]4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!IA\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bUBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f\u001dD\u0011\u0011!C\u0003Q\"9a\u000eCA\u0001\n\u000by'\u0001\t*jG\"|%M\u001b#pk\ndWmQ8ogVlWM]!t\rVt7\r^5p]JR!a\u0004\t\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!!\u0005\n\u0002\u000b)\fg/\u0019\u001d\u000b\u0005M!\u0012AB2p[B\fGOC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0007\u0001)\"\u0001G\u0016\u0014\u0005\u0001I\u0002C\u0001\u000e\u001c\u001b\u0005!\u0012B\u0001\u000f\u0015\u0005\u0019\te.\u001f,bY\u000616oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYN$#+[2i\u001f\nTGi\\;cY\u0016\u001cuN\\:v[\u0016\u0014\u0018i\u001d$v]\u000e$\u0018n\u001c83I\u0011*h\u000eZ3sYfLgnZ\u000b\u0002?A\u0019\u0001eJ\u0015\u000e\u0003\u0005R!AI\u0012\u0002\u0011\u0019,hn\u0019;j_:T!\u0001J\u0013\u0002\tU$\u0018\u000e\u001c\u0006\u0002M\u0005!!.\u0019<b\u0013\tA\u0013EA\tPE*$u.\u001e2mK\u000e{gn];nKJ\u0004\"AK\u0016\r\u0001\u0011)A\u0006\u0001b\u0001[\t\tA+\u0005\u0002/cA\u0011!dL\u0005\u0003aQ\u0011qAT8uQ&tw\r\u0005\u0002\u001be%\u00111\u0007\u0006\u0002\u0004\u0003:L\u0018aV:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"|%M\u001b#pk\ndWmQ8ogVlWM]!t\rVt7\r^5p]J\"C%\u001e8eKJd\u00170\u001b8hA\u00051A(\u001b8jiz\"\"aN\u001d\u0011\u0007a\u0002\u0011&D\u0001\u000f\u0011\u0015Q4\u00011\u0001 \u0003))h\u000eZ3sYfLgnZ\u0001\bCN\u001c6-\u00197b+\u0005i\u0004#\u0002\u000e?S\u0001\u001b\u0015BA \u0015\u0005%1UO\\2uS>t'\u0007\u0005\u0002\u001b\u0003&\u0011!\t\u0006\u0002\u0007\t>,(\r\\3\u0011\u0005i!\u0015BA#\u0015\u0005\u0011)f.\u001b;)\u0005\u00119\u0005C\u0001\u000eI\u0013\tIEC\u0001\u0004j]2Lg.Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA\n\u0005\u0002\u001b\u001b&\u0011a\n\u0006\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002R)B\u0011!DU\u0005\u0003'R\u0011qAQ8pY\u0016\fg\u000eC\u0004V\r\u0005\u0005\t\u0019A\u0019\u0002\u0007a$\u0013'\u0001\u0011SS\u000eDwJ\u00196E_V\u0014G.Z\"p]N,X.\u001a:Bg\u001a+hn\u0019;j_:\u0014\u0004C\u0001\u001d\t'\tA\u0011\f\u0005\u0002\u001b5&\u00111\f\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003]\u000b\u0011#Y:TG\u0006d\u0017\rJ3yi\u0016t7/[8o+\ty&\r\u0006\u0002aGB)!DP1A\u0007B\u0011!F\u0019\u0003\u0006Y)\u0011\r!\f\u0005\u0006I*\u0001\r!Z\u0001\u0006IQD\u0017n\u001d\t\u0004q\u0001\t\u0007F\u0001\u0006H\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005%lGCA&k\u0011\u0015!7\u00021\u0001l!\rA\u0004\u0001\u001c\t\u0003U5$Q\u0001L\u0006C\u00025\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0005A4HCA9t)\t\t&\u000fC\u0004V\u0019\u0005\u0005\t\u0019A\u0019\t\u000b\u0011d\u0001\u0019\u0001;\u0011\u0007a\u0002Q\u000f\u0005\u0002+m\u0012)A\u0006\u0004b\u0001[\u0001")
public final class RichObjDoubleConsumerAsFunction2<T> {
    private final ObjDoubleConsumer<T> scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying;

    public static <T> boolean equals$extension(ObjDoubleConsumer<T> objDoubleConsumer, Object object) {
        return RichObjDoubleConsumerAsFunction2$.MODULE$.equals$extension(objDoubleConsumer, object);
    }

    public static <T> int hashCode$extension(ObjDoubleConsumer<T> objDoubleConsumer) {
        return RichObjDoubleConsumerAsFunction2$.MODULE$.hashCode$extension(objDoubleConsumer);
    }

    public static <T> Function2<T, Object, BoxedUnit> asScala$extension(ObjDoubleConsumer<T> objDoubleConsumer) {
        return RichObjDoubleConsumerAsFunction2$.MODULE$.asScala$extension(objDoubleConsumer);
    }

    public ObjDoubleConsumer<T> scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying;
    }

    public Function2<T, Object, BoxedUnit> asScala() {
        return RichObjDoubleConsumerAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichObjDoubleConsumerAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichObjDoubleConsumerAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying(), x$1);
    }

    public RichObjDoubleConsumerAsFunction2(ObjDoubleConsumer<T> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichObjDoubleConsumerAsFunction2$$underlying = underlying;
    }
}

