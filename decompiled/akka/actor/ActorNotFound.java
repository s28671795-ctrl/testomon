/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorNotFound$;
import akka.actor.ActorSelection;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005mb\u0001B\u000b\u0017\u0005nA\u0001\"\r\u0001\u0003\u0016\u0004%\tA\r\u0005\to\u0001\u0011\t\u0012)A\u0005g!)\u0001\b\u0001C\u0001s!9A\bAA\u0001\n\u0003i\u0004bB \u0001#\u0003%\t\u0001\u0011\u0005\b\u0017\u0002\t\t\u0011\"\u0011M\u0011\u001d)\u0006!!A\u0005\u0002YCqA\u0017\u0001\u0002\u0002\u0013\u00051\fC\u0004b\u0001\u0005\u0005I\u0011\t2\t\u000f%\u0004\u0011\u0011!C\u0001U\"9q\u000eAA\u0001\n\u0003\u0002\bb\u0002:\u0001\u0003\u0003%\te\u001d\u0005\bi\u0002\t\t\u0011\"\u0011v\u000f\u001dih#!A\t\u0002y4q!\u0006\f\u0002\u0002#\u0005q\u0010\u0003\u00049\u001f\u0011\u0005\u0011q\u0003\u0005\n\u00033y\u0011\u0011!C#\u00037A\u0011\"!\b\u0010\u0003\u0003%\t)a\b\t\u0013\u0005\rr\"!A\u0005\u0002\u0006\u0015\u0002\"CA\u0019\u001f\u0005\u0005I\u0011BA\u001a\u00055\t5\r^8s\u001d>$hi\\;oI*\u0011q\u0003G\u0001\u0006C\u000e$xN\u001d\u0006\u00023\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001\u0001\u000f+]A\u0011Qd\n\b\u0003=\u0011r!a\b\u0012\u000e\u0003\u0001R!!\t\u000e\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0019\u0013!B:dC2\f\u0017BA\u0013'\u0003\u001d\u0001\u0018mY6bO\u0016T\u0011aI\u0005\u0003Q%\u0012\u0001CU;oi&lW-\u0012=dKB$\u0018n\u001c8\u000b\u0005\u00152\u0003CA\u0016-\u001b\u00051\u0013BA\u0017'\u0005\u001d\u0001&o\u001c3vGR\u0004\"!H\u0018\n\u0005AJ#\u0001D*fe&\fG.\u001b>bE2,\u0017!C:fY\u0016\u001cG/[8o+\u0005\u0019\u0004C\u0001\u001b6\u001b\u00051\u0012B\u0001\u001c\u0017\u00059\t5\r^8s'\u0016dWm\u0019;j_:\f!b]3mK\u000e$\u0018n\u001c8!\u0003\u0019a\u0014N\\5u}Q\u0011!h\u000f\t\u0003i\u0001AQ!M\u0002A\u0002M\nAaY8qsR\u0011!H\u0010\u0005\bc\u0011\u0001\n\u00111\u00014\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012!\u0011\u0016\u0003g\t[\u0013a\u0011\t\u0003\t&k\u0011!\u0012\u0006\u0003\r\u001e\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005!3\u0013AC1o]>$\u0018\r^5p]&\u0011!*\u0012\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001N!\tq5+D\u0001P\u0015\t\u0001\u0016+\u0001\u0003mC:<'\"\u0001*\u0002\t)\fg/Y\u0005\u0003)>\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#A,\u0011\u0005-B\u0016BA-'\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\tav\f\u0005\u0002,;&\u0011aL\n\u0002\u0004\u0003:L\bb\u00021\t\u0003\u0003\u0005\raV\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0003\r\u00042\u0001Z4]\u001b\u0005)'B\u00014'\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003Q\u0016\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u00111N\u001c\t\u0003W1L!!\u001c\u0014\u0003\u000f\t{w\u000e\\3b]\"9\u0001MCA\u0001\u0002\u0004a\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$\"!T9\t\u000f\u0001\\\u0011\u0011!a\u0001/\u0006A\u0001.Y:i\u0007>$W\rF\u0001X\u0003\u0019)\u0017/^1mgR\u00111N\u001e\u0005\bA6\t\t\u00111\u0001]Q\u0011\u0001\u0001p\u001f?\u0011\u0005-J\u0018B\u0001>'\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u00035\t5\r^8s\u001d>$hi\\;oIB\u0011AgD\n\u0006\u001f\u0005\u0005\u0011Q\u0002\t\u0007\u0003\u0007\tIa\r\u001e\u000e\u0005\u0005\u0015!bAA\u0004M\u00059!/\u001e8uS6,\u0017\u0002BA\u0006\u0003\u000b\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\ty!!\u0006\u000e\u0005\u0005E!bAA\n#\u0006\u0011\u0011n\\\u0005\u0004a\u0005EA#\u0001@\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!T\u0001\u0006CB\u0004H.\u001f\u000b\u0004u\u0005\u0005\u0002\"B\u0019\u0013\u0001\u0004\u0019\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0003O\ti\u0003\u0005\u0003,\u0003S\u0019\u0014bAA\u0016M\t1q\n\u001d;j_:D\u0001\"a\f\u0014\u0003\u0003\u0005\rAO\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA\u001b!\rq\u0015qG\u0005\u0004\u0003sy%AB(cU\u0016\u001cG\u000f")
public final class ActorNotFound
extends RuntimeException
implements Product {
    private static final long serialVersionUID = 1L;
    private final ActorSelection selection;

    public static Option<ActorSelection> unapply(ActorNotFound x$0) {
        return ActorNotFound$.MODULE$.unapply(x$0);
    }

    public static ActorNotFound apply(ActorSelection selection) {
        return ActorNotFound$.MODULE$.apply(selection);
    }

    public static <A> Function1<ActorSelection, A> andThen(Function1<ActorNotFound, A> g2) {
        return ActorNotFound$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, ActorNotFound> compose(Function1<A, ActorSelection> g2) {
        return ActorNotFound$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorSelection selection() {
        return this.selection;
    }

    public ActorNotFound copy(ActorSelection selection) {
        return new ActorNotFound(selection);
    }

    public ActorSelection copy$default$1() {
        return this.selection();
    }

    @Override
    public String productPrefix() {
        return "ActorNotFound";
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
                return this.selection();
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
        return x$1 instanceof ActorNotFound;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "selection";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ActorNotFound)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorNotFound actorNotFound = (ActorNotFound)x$1;
        ActorSelection actorSelection = this.selection();
        ActorSelection actorSelection2 = actorNotFound.selection();
        if (actorSelection != null) {
            if (!((Object)actorSelection).equals(actorSelection2)) return false;
            return true;
        }
        if (actorSelection2 == null) return true;
        return false;
    }

    public ActorNotFound(ActorSelection selection) {
        this.selection = selection;
        super(new StringBuilder(21).append("Actor not found for: ").append(selection).toString());
        Product.$init$(this);
    }
}

