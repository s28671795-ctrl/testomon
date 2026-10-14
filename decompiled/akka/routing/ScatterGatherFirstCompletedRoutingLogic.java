/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.routing.ScatterGatherFirstCompletedRoutees;
import akka.routing.ScatterGatherFirstCompletedRoutingLogic$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055d\u0001B\f\u0019\u0005vA\u0001b\u000e\u0001\u0003\u0016\u0004%\t\u0001\u000f\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005s!)!\t\u0001C\u0001\u0007\")a\t\u0001C!\u000f\"9!\fAA\u0001\n\u0003Y\u0006bB/\u0001#\u0003%\tA\u0018\u0005\bS\u0002\t\t\u0011\"\u0011k\u0011\u001d\u0019\b!!A\u0005\u0002QDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0011\u0010C\u0004}\u0001\u0005\u0005I\u0011I?\t\u0013\u0005\u0015\u0001!!A\u0005\u0002\u0005\u001d\u0001\"CA\t\u0001\u0005\u0005I\u0011IA\n\u0011%\t9\u0002AA\u0001\n\u0003\nI\u0002C\u0005\u0002\u001c\u0001\t\t\u0011\"\u0011\u0002\u001e!I\u0011q\u0004\u0001\u0002\u0002\u0013\u0005\u0013\u0011E\u0004\n\u0003cA\u0012\u0011!E\u0001\u0003g1\u0001b\u0006\r\u0002\u0002#\u0005\u0011Q\u0007\u0005\u0007\u0005F!\t!!\u0014\t\u0013\u0005m\u0011#!A\u0005F\u0005u\u0001\"CA(#\u0005\u0005I\u0011QA)\u0011%\t)&EA\u0001\n\u0003\u000b9\u0006C\u0005\u0002dE\t\t\u0011\"\u0003\u0002f\t93kY1ui\u0016\u0014x)\u0019;iKJ4\u0015N]:u\u0007>l\u0007\u000f\\3uK\u0012\u0014v.\u001e;j]\u001edunZ5d\u0015\tI\"$A\u0004s_V$\u0018N\\4\u000b\u0003m\tA!Y6lC\u000e\u00011#\u0002\u0001\u001fI!Z\u0003CA\u0010#\u001b\u0005\u0001#\"A\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0002#AB!osJ+g\r\u0005\u0002&M5\t\u0001$\u0003\u0002(1\ta!k\\;uS:<Gj\\4jGB\u0011q$K\u0005\u0003U\u0001\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002-i9\u0011QF\r\b\u0003]Ej\u0011a\f\u0006\u0003aq\ta\u0001\u0010:p_Rt\u0014\"A\u0011\n\u0005M\u0002\u0013a\u00029bG.\fw-Z\u0005\u0003kY\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!a\r\u0011\u0002\r]LG\u000f[5o+\u0005I\u0004C\u0001\u001e@\u001b\u0005Y$B\u0001\u001f>\u0003!!WO]1uS>t'B\u0001 !\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003\u0001n\u0012aBR5oSR,G)\u001e:bi&|g.A\u0004xSRD\u0017N\u001c\u0011\u0002\rqJg.\u001b;?)\t!U\t\u0005\u0002&\u0001!)qg\u0001a\u0001s\u000511/\u001a7fGR$2\u0001S&Q!\t)\u0013*\u0003\u0002K1\t1!k\\;uK\u0016DQ\u0001\u0014\u0003A\u00025\u000bq!\\3tg\u0006<W\r\u0005\u0002 \u001d&\u0011q\n\t\u0002\u0004\u0003:L\b\"B)\u0005\u0001\u0004\u0011\u0016a\u0002:pkR,Wm\u001d\t\u0004'bCU\"\u0001+\u000b\u0005U3\u0016!C5n[V$\u0018M\u00197f\u0015\t9\u0006%\u0001\u0006d_2dWm\u0019;j_:L!!\u0017+\u0003\u0015%sG-\u001a=fIN+\u0017/\u0001\u0003d_BLHC\u0001#]\u0011\u001d9T\u0001%AA\u0002e\nabY8qs\u0012\"WMZ1vYR$\u0013'F\u0001`U\tI\u0004mK\u0001b!\t\u0011w-D\u0001d\u0015\t!W-A\u0005v]\u000eDWmY6fI*\u0011a\rI\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00015d\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003-\u0004\"\u0001\\9\u000e\u00035T!A\\8\u0002\t1\fgn\u001a\u0006\u0002a\u0006!!.\u0019<b\u0013\t\u0011XN\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002kB\u0011qD^\u0005\u0003o\u0002\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"!\u0014>\t\u000fmL\u0011\u0011!a\u0001k\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012A \t\u0005\u007f\u0006\u0005Q*D\u0001W\u0013\r\t\u0019A\u0016\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002\n\u0005=\u0001cA\u0010\u0002\f%\u0019\u0011Q\u0002\u0011\u0003\u000f\t{w\u000e\\3b]\"91pCA\u0001\u0002\u0004i\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2a[A\u000b\u0011\u001dYH\"!AA\u0002U\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002k\u0006AAo\\*ue&tw\rF\u0001l\u0003\u0019)\u0017/^1mgR!\u0011\u0011BA\u0012\u0011\u001dYx\"!AA\u00025Cs\u0001AA\u0014\u0003[\ty\u0003E\u0002 \u0003SI1!a\u000b!\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0003\u001d\u001a6-\u0019;uKJ<\u0015\r\u001e5fe\u001aK'o\u001d;D_6\u0004H.\u001a;fIJ{W\u000f^5oO2{w-[2\u0011\u0005\u0015\n2#B\t\u00028\u0005\r\u0003CBA\u001d\u0003\u007fID)\u0004\u0002\u0002<)\u0019\u0011Q\b\u0011\u0002\u000fI,h\u000e^5nK&!\u0011\u0011IA\u001e\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0003\u000b\nY%\u0004\u0002\u0002H)\u0019\u0011\u0011J8\u0002\u0005%|\u0017bA\u001b\u0002HQ\u0011\u00111G\u0001\u0006CB\u0004H.\u001f\u000b\u0004\t\u0006M\u0003\"B\u001c\u0015\u0001\u0004I\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u00033\ny\u0006\u0005\u0003 \u00037J\u0014bAA/A\t1q\n\u001d;j_:D\u0001\"!\u0019\u0016\u0003\u0003\u0005\r\u0001R\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA4!\ra\u0017\u0011N\u0005\u0004\u0003Wj'AB(cU\u0016\u001cG\u000f")
public final class ScatterGatherFirstCompletedRoutingLogic
implements RoutingLogic,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final FiniteDuration within;

    public static Option<FiniteDuration> unapply(ScatterGatherFirstCompletedRoutingLogic x$0) {
        return ScatterGatherFirstCompletedRoutingLogic$.MODULE$.unapply(x$0);
    }

    public static ScatterGatherFirstCompletedRoutingLogic apply(FiniteDuration within) {
        return ScatterGatherFirstCompletedRoutingLogic$.MODULE$.apply(within);
    }

    public static <A> Function1<FiniteDuration, A> andThen(Function1<ScatterGatherFirstCompletedRoutingLogic, A> g2) {
        return ScatterGatherFirstCompletedRoutingLogic$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ScatterGatherFirstCompletedRoutingLogic> compose(Function1<A, FiniteDuration> g2) {
        return ScatterGatherFirstCompletedRoutingLogic$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public FiniteDuration within() {
        return this.within;
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        return new ScatterGatherFirstCompletedRoutees(routees, this.within());
    }

    public ScatterGatherFirstCompletedRoutingLogic copy(FiniteDuration within) {
        return new ScatterGatherFirstCompletedRoutingLogic(within);
    }

    public FiniteDuration copy$default$1() {
        return this.within();
    }

    @Override
    public String productPrefix() {
        return "ScatterGatherFirstCompletedRoutingLogic";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.within();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof ScatterGatherFirstCompletedRoutingLogic;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "within";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ScatterGatherFirstCompletedRoutingLogic)) return false;
        boolean bl = true;
        if (!bl) return false;
        ScatterGatherFirstCompletedRoutingLogic scatterGatherFirstCompletedRoutingLogic = (ScatterGatherFirstCompletedRoutingLogic)x$1;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = scatterGatherFirstCompletedRoutingLogic.within();
        if (finiteDuration != null) {
            if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            return true;
        }
        if (finiteDuration2 == null) return true;
        return false;
    }

    public ScatterGatherFirstCompletedRoutingLogic(FiniteDuration within) {
        this.within = within;
        Product.$init$(this);
    }
}

