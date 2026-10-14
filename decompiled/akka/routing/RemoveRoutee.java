/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.RemoveRoutee$;
import akka.routing.Routee;
import akka.routing.RouterManagementMesssage;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rc\u0001\u0002\f\u0018\u0005rA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\tw\u0001\u0011\t\u0012)A\u0005q!)A\b\u0001C\u0001{!9\u0001\tAA\u0001\n\u0003\t\u0005bB\"\u0001#\u0003%\t\u0001\u0012\u0005\b\u001f\u0002\t\t\u0011\"\u0011Q\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u0011\u001dQ\b!!A\u0005Bm<\u0011\"a\u0002\u0018\u0003\u0003E\t!!\u0003\u0007\u0011Y9\u0012\u0011!E\u0001\u0003\u0017Aa\u0001\u0010\t\u0005\u0002\u0005\r\u0002b\u0002=\u0011\u0003\u0003%)%\u001f\u0005\n\u0003K\u0001\u0012\u0011!CA\u0003OA\u0011\"a\u000b\u0011\u0003\u0003%\t)!\f\t\u0013\u0005e\u0002#!A\u0005\n\u0005m\"\u0001\u0004*f[>4XMU8vi\u0016,'B\u0001\r\u001a\u0003\u001d\u0011x.\u001e;j]\u001eT\u0011AG\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u000b\u0001i2e\n\u0016\u0011\u0005y\tS\"A\u0010\u000b\u0003\u0001\nQa]2bY\u0006L!AI\u0010\u0003\r\u0005s\u0017PU3g!\t!S%D\u0001\u0018\u0013\t1sC\u0001\rS_V$XM]'b]\u0006<W-\\3oi6+7o]:bO\u0016\u0004\"A\b\u0015\n\u0005%z\"a\u0002)s_\u0012,8\r\u001e\t\u0003WMr!\u0001L\u0019\u000f\u00055\u0002T\"\u0001\u0018\u000b\u0005=Z\u0012A\u0002\u001fs_>$h(C\u0001!\u0013\t\u0011t$A\u0004qC\u000e\\\u0017mZ3\n\u0005Q*$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001a \u0003\u0019\u0011x.\u001e;fKV\t\u0001\b\u0005\u0002%s%\u0011!h\u0006\u0002\u0007%>,H/Z3\u0002\u000fI|W\u000f^3fA\u00051A(\u001b8jiz\"\"AP \u0011\u0005\u0011\u0002\u0001\"\u0002\u001c\u0004\u0001\u0004A\u0014\u0001B2paf$\"A\u0010\"\t\u000fY\"\u0001\u0013!a\u0001q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A#+\u0005a25&A$\u0011\u0005!kU\"A%\u000b\u0005)[\u0015!C;oG\",7m[3e\u0015\tau$\u0001\u0006b]:|G/\u0019;j_:L!AT%\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002#B\u0011!kV\u0007\u0002'*\u0011A+V\u0001\u0005Y\u0006twMC\u0001W\u0003\u0011Q\u0017M^1\n\u0005a\u001b&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001\\!\tqB,\u0003\u0002^?\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0001m\u0019\t\u0003=\u0005L!AY\u0010\u0003\u0007\u0005s\u0017\u0010C\u0004e\u0011\u0005\u0005\t\u0019A.\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u00059\u0007c\u00015lA6\t\u0011N\u0003\u0002k?\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u00051L'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"a\u001c:\u0011\u0005y\u0001\u0018BA9 \u0005\u001d\u0011un\u001c7fC:Dq\u0001\u001a\u0006\u0002\u0002\u0003\u0007\u0001-\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GCA)v\u0011\u001d!7\"!AA\u0002m\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u00027\u0006AAo\\*ue&tw\rF\u0001R\u0003\u0019)\u0017/^1mgR\u0011q\u000e \u0005\bI:\t\t\u00111\u0001aQ\u0019\u0001a0a\u0001\u0002\u0006A\u0011ad`\u0005\u0004\u0003\u0003y\"\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011\u0001\u0004*f[>4XMU8vi\u0016,\u0007C\u0001\u0013\u0011'\u0015\u0001\u0012QBA\r!\u0019\ty!!\u00069}5\u0011\u0011\u0011\u0003\u0006\u0004\u0003'y\u0012a\u0002:v]RLW.Z\u0005\u0005\u0003/\t\tBA\tBEN$(/Y2u\rVt7\r^5p]F\u0002B!a\u0007\u0002\"5\u0011\u0011Q\u0004\u0006\u0004\u0003?)\u0016AA5p\u0013\r!\u0014Q\u0004\u000b\u0003\u0003\u0013\tQ!\u00199qYf$2APA\u0015\u0011\u001514\u00031\u00019\u0003\u001d)h.\u00199qYf$B!a\f\u00026A!a$!\r9\u0013\r\t\u0019d\b\u0002\u0007\u001fB$\u0018n\u001c8\t\u0011\u0005]B#!AA\u0002y\n1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\ti\u0004E\u0002S\u0003\u007fI1!!\u0011T\u0005\u0019y%M[3di\u0002")
public final class RemoveRoutee
implements RouterManagementMesssage,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Routee routee;

    public static Option<Routee> unapply(RemoveRoutee x$0) {
        return RemoveRoutee$.MODULE$.unapply(x$0);
    }

    public static RemoveRoutee apply(Routee routee) {
        return RemoveRoutee$.MODULE$.apply(routee);
    }

    public static <A> Function1<Routee, A> andThen(Function1<RemoveRoutee, A> g2) {
        return RemoveRoutee$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, RemoveRoutee> compose(Function1<A, Routee> g2) {
        return RemoveRoutee$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Routee routee() {
        return this.routee;
    }

    public RemoveRoutee copy(Routee routee) {
        return new RemoveRoutee(routee);
    }

    public Routee copy$default$1() {
        return this.routee();
    }

    @Override
    public String productPrefix() {
        return "RemoveRoutee";
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
                return this.routee();
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
        return x$1 instanceof RemoveRoutee;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "routee";
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
        if (!(object instanceof RemoveRoutee)) return false;
        boolean bl = true;
        if (!bl) return false;
        RemoveRoutee removeRoutee = (RemoveRoutee)x$1;
        Routee routee = this.routee();
        Routee routee2 = removeRoutee.routee();
        if (routee != null) {
            if (!routee.equals(routee2)) return false;
            return true;
        }
        if (routee2 == null) return true;
        return false;
    }

    public RemoveRoutee(Routee routee) {
        this.routee = routee;
        Product.$init$(this);
    }
}

