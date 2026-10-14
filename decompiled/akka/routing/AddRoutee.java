/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.AddRoutee$;
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

@ScalaSignature(bytes="\u0006\u0005\u0005\rc\u0001\u0002\f\u0018\u0005rA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\tw\u0001\u0011\t\u0012)A\u0005q!)A\b\u0001C\u0001{!9\u0001\tAA\u0001\n\u0003\t\u0005bB\"\u0001#\u0003%\t\u0001\u0012\u0005\b\u001f\u0002\t\t\u0011\"\u0011Q\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004f\u0001\u0005\u0005I\u0011\t4\t\u000f5\u0004\u0011\u0011!C\u0001]\"91\u000fAA\u0001\n\u0003\"\bb\u0002<\u0001\u0003\u0003%\te\u001e\u0005\bq\u0002\t\t\u0011\"\u0011z\u0011\u001dQ\b!!A\u0005Bm<\u0011\"a\u0002\u0018\u0003\u0003E\t!!\u0003\u0007\u0011Y9\u0012\u0011!E\u0001\u0003\u0017Aa\u0001\u0010\t\u0005\u0002\u0005\r\u0002b\u0002=\u0011\u0003\u0003%)%\u001f\u0005\n\u0003K\u0001\u0012\u0011!CA\u0003OA\u0011\"a\u000b\u0011\u0003\u0003%\t)!\f\t\u0013\u0005e\u0002#!A\u0005\n\u0005m\"!C!eIJ{W\u000f^3f\u0015\tA\u0012$A\u0004s_V$\u0018N\\4\u000b\u0003i\tA!Y6lC\u000e\u00011#\u0002\u0001\u001eG\u001dR\u0003C\u0001\u0010\"\u001b\u0005y\"\"\u0001\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\tz\"AB!osJ+g\r\u0005\u0002%K5\tq#\u0003\u0002'/\tA\"k\\;uKJl\u0015M\\1hK6,g\u000e^'fgN\u001c\u0018mZ3\u0011\u0005yA\u0013BA\u0015 \u0005\u001d\u0001&o\u001c3vGR\u0004\"aK\u001a\u000f\u00051\ndBA\u00171\u001b\u0005q#BA\u0018\u001c\u0003\u0019a$o\\8u}%\t\u0001%\u0003\u00023?\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001b6\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u0011t$\u0001\u0004s_V$X-Z\u000b\u0002qA\u0011A%O\u0005\u0003u]\u0011aAU8vi\u0016,\u0017a\u0002:pkR,W\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005yz\u0004C\u0001\u0013\u0001\u0011\u001514\u00011\u00019\u0003\u0011\u0019w\u000e]=\u0015\u0005y\u0012\u0005b\u0002\u001c\u0005!\u0003\u0005\r\u0001O\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005)%F\u0001\u001dGW\u00059\u0005C\u0001%N\u001b\u0005I%B\u0001&L\u0003%)hn\u00195fG.,GM\u0003\u0002M?\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u00059K%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\u0012!\u0015\t\u0003%^k\u0011a\u0015\u0006\u0003)V\u000bA\u0001\\1oO*\ta+\u0001\u0003kCZ\f\u0017B\u0001-T\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t1\f\u0005\u0002\u001f9&\u0011Ql\b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003A\u000e\u0004\"AH1\n\u0005\t|\"aA!os\"9A\rCA\u0001\u0002\u0004Y\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001h!\rA7\u000eY\u0007\u0002S*\u0011!nH\u0001\u000bG>dG.Z2uS>t\u0017B\u00017j\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005=\u0014\bC\u0001\u0010q\u0013\t\txDA\u0004C_>dW-\u00198\t\u000f\u0011T\u0011\u0011!a\u0001A\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\t\tV\u000fC\u0004e\u0017\u0005\u0005\t\u0019A.\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aW\u0001\ti>\u001cFO]5oOR\t\u0011+\u0001\u0004fcV\fGn\u001d\u000b\u0003_rDq\u0001\u001a\b\u0002\u0002\u0003\u0007\u0001\r\u000b\u0004\u0001}\u0006\r\u0011Q\u0001\t\u0003=}L1!!\u0001 \u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0003%\tE\r\u001a*pkR,W\r\u0005\u0002%!M)\u0001#!\u0004\u0002\u001aA1\u0011qBA\u000bqyj!!!\u0005\u000b\u0007\u0005Mq$A\u0004sk:$\u0018.\\3\n\t\u0005]\u0011\u0011\u0003\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BA\u000e\u0003Ci!!!\b\u000b\u0007\u0005}Q+\u0001\u0002j_&\u0019A'!\b\u0015\u0005\u0005%\u0011!B1qa2LHc\u0001 \u0002*!)ag\u0005a\u0001q\u00059QO\\1qa2LH\u0003BA\u0018\u0003k\u0001BAHA\u0019q%\u0019\u00111G\u0010\u0003\r=\u0003H/[8o\u0011!\t9\u0004FA\u0001\u0002\u0004q\u0014a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\b\t\u0004%\u0006}\u0012bAA!'\n1qJ\u00196fGR\u0004")
public final class AddRoutee
implements RouterManagementMesssage,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Routee routee;

    public static Option<Routee> unapply(AddRoutee x$0) {
        return AddRoutee$.MODULE$.unapply(x$0);
    }

    public static AddRoutee apply(Routee routee) {
        return AddRoutee$.MODULE$.apply(routee);
    }

    public static <A> Function1<Routee, A> andThen(Function1<AddRoutee, A> g2) {
        return AddRoutee$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, AddRoutee> compose(Function1<A, Routee> g2) {
        return AddRoutee$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Routee routee() {
        return this.routee;
    }

    public AddRoutee copy(Routee routee) {
        return new AddRoutee(routee);
    }

    public Routee copy$default$1() {
        return this.routee();
    }

    @Override
    public String productPrefix() {
        return "AddRoutee";
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
        return x$1 instanceof AddRoutee;
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
        if (!(object instanceof AddRoutee)) return false;
        boolean bl = true;
        if (!bl) return false;
        AddRoutee addRoutee = (AddRoutee)x$1;
        Routee routee = this.routee();
        Routee routee2 = addRoutee.routee();
        if (routee != null) {
            if (!routee.equals(routee2)) return false;
            return true;
        }
        if (routee2 == null) return true;
        return false;
    }

    public AddRoutee(Routee routee) {
        this.routee = routee;
        Product.$init$(this);
    }
}

