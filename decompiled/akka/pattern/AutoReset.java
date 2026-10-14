/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.pattern.AutoReset$;
import akka.pattern.BackoffReset;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005c!\u0002\f\u0018\u0005fY\u0002\u0002\u0003\u001c\u0001\u0005+\u0007I\u0011A\u001c\t\u0011\u0001\u0003!\u0011#Q\u0001\naBQ!\u0011\u0001\u0005\u0002\tCq!\u0012\u0001\u0002\u0002\u0013\u0005a\tC\u0004I\u0001E\u0005I\u0011A%\t\u000fQ\u0003\u0011\u0011!C!+\"9a\fAA\u0001\n\u0003y\u0006bB2\u0001\u0003\u0003%\t\u0001\u001a\u0005\bU\u0002\t\t\u0011\"\u0011l\u0011\u001d\u0011\b!!A\u0005\u0002MDq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0013\u0010C\u0004|\u0001\u0005\u0005I\u0011\t?\t\u000fu\u0004\u0011\u0011!C!}\"Aq\u0010AA\u0001\n\u0003\n\ta\u0002\u0006\u0002\u0006]\t\t\u0011#\u0001\u001a\u0003\u000f1\u0011BF\f\u0002\u0002#\u0005\u0011$!\u0003\t\r\u0005\u0003B\u0011AA\u0011\u0011\u001di\b#!A\u0005FyD\u0011\"a\t\u0011\u0003\u0003%\t)!\n\t\u0013\u0005%\u0002#!A\u0005\u0002\u0006-\u0002\"CA\u001c!\u0005\u0005I\u0011BA\u001d\u0005%\tU\u000f^8SKN,GO\u0003\u0002\u00193\u00059\u0001/\u0019;uKJt'\"\u0001\u000e\u0002\t\u0005\\7.Y\n\u0006\u0001q\u0011c%\u000b\t\u0003;\u0001j\u0011A\b\u0006\u0002?\u0005)1oY1mC&\u0011\u0011E\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\r\"S\"A\f\n\u0005\u0015:\"\u0001\u0004\"bG.|gM\u001a*fg\u0016$\bCA\u000f(\u0013\tAcDA\u0004Qe>$Wo\u0019;\u0011\u0005)\u001adBA\u00162\u001d\ta\u0003'D\u0001.\u0015\tqs&\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005y\u0012B\u0001\u001a\u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001N\u001b\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Ir\u0012\u0001\u0004:fg\u0016$()Y2l_\u001a4W#\u0001\u001d\u0011\u0005erT\"\u0001\u001e\u000b\u0005mb\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005ur\u0012AC2p]\u000e,(O]3oi&\u0011qH\u000f\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u00035\u0011Xm]3u\u0005\u0006\u001c7n\u001c4gA\u00051A(\u001b8jiz\"\"a\u0011#\u0011\u0005\r\u0002\u0001\"\u0002\u001c\u0004\u0001\u0004A\u0014\u0001B2paf$\"aQ$\t\u000fY\"\u0001\u0013!a\u0001q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001&+\u0005aZ5&\u0001'\u0011\u00055\u0013V\"\u0001(\u000b\u0005=\u0003\u0016!C;oG\",7m[3e\u0015\t\tf$\u0001\u0006b]:|G/\u0019;j_:L!a\u0015(\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002-B\u0011q\u000bX\u0007\u00021*\u0011\u0011LW\u0001\u0005Y\u0006twMC\u0001\\\u0003\u0011Q\u0017M^1\n\u0005uC&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001a!\ti\u0012-\u0003\u0002c=\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Q\r\u001b\t\u0003;\u0019L!a\u001a\u0010\u0003\u0007\u0005s\u0017\u0010C\u0004j\u0011\u0005\u0005\t\u0019\u00011\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005a\u0007cA7qK6\taN\u0003\u0002p=\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Et'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"\u0001^<\u0011\u0005u)\u0018B\u0001<\u001f\u0005\u001d\u0011un\u001c7fC:Dq!\u001b\u0006\u0002\u0002\u0003\u0007Q-\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GC\u0001,{\u0011\u001dI7\"!AA\u0002\u0001\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002A\u0006AAo\\*ue&tw\rF\u0001W\u0003\u0019)\u0017/^1mgR\u0019A/a\u0001\t\u000f%t\u0011\u0011!a\u0001K\u0006I\u0011)\u001e;p%\u0016\u001cX\r\u001e\t\u0003GA\u0019R\u0001EA\u0006\u0003/\u0001b!!\u0004\u0002\u0014a\u001aUBAA\b\u0015\r\t\tBH\u0001\beVtG/[7f\u0013\u0011\t)\"a\u0004\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002\u001a\u0005}QBAA\u000e\u0015\r\tiBW\u0001\u0003S>L1\u0001NA\u000e)\t\t9!A\u0003baBd\u0017\u0010F\u0002D\u0003OAQAN\nA\u0002a\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002.\u0005M\u0002\u0003B\u000f\u00020aJ1!!\r\u001f\u0005\u0019y\u0005\u000f^5p]\"A\u0011Q\u0007\u000b\u0002\u0002\u0003\u00071)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u000f\u0011\u0007]\u000bi$C\u0002\u0002@a\u0013aa\u00142kK\u000e$\b")
public final class AutoReset
implements BackoffReset,
Product,
Serializable {
    private final FiniteDuration resetBackoff;

    public static Option<FiniteDuration> unapply(AutoReset x$0) {
        return AutoReset$.MODULE$.unapply(x$0);
    }

    public static AutoReset apply(FiniteDuration resetBackoff) {
        return AutoReset$.MODULE$.apply(resetBackoff);
    }

    public static <A> Function1<FiniteDuration, A> andThen(Function1<AutoReset, A> g2) {
        return AutoReset$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, AutoReset> compose(Function1<A, FiniteDuration> g2) {
        return AutoReset$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public FiniteDuration resetBackoff() {
        return this.resetBackoff;
    }

    public AutoReset copy(FiniteDuration resetBackoff) {
        return new AutoReset(resetBackoff);
    }

    public FiniteDuration copy$default$1() {
        return this.resetBackoff();
    }

    @Override
    public String productPrefix() {
        return "AutoReset";
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
                return this.resetBackoff();
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
        return x$1 instanceof AutoReset;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "resetBackoff";
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
        if (!(object instanceof AutoReset)) return false;
        boolean bl = true;
        if (!bl) return false;
        AutoReset autoReset = (AutoReset)x$1;
        FiniteDuration finiteDuration = this.resetBackoff();
        FiniteDuration finiteDuration2 = autoReset.resetBackoff();
        if (finiteDuration != null) {
            if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            return true;
        }
        if (finiteDuration2 == null) return true;
        return false;
    }

    public AutoReset(FiniteDuration resetBackoff) {
        this.resetBackoff = resetBackoff;
        Product.$init$(this);
    }
}

