/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.ListenerMessage;
import akka.routing.WithListeners$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%c\u0001\u0002\f\u0018\u0005rA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\t\t\u0002\u0011\t\u0012)A\u0005q!)Q\t\u0001C\u0001\r\"9\u0011\nAA\u0001\n\u0003Q\u0005b\u0002'\u0001#\u0003%\t!\u0014\u0005\b1\u0002\t\t\u0011\"\u0011Z\u0011\u001d\u0011\u0007!!A\u0005\u0002\rDqa\u001a\u0001\u0002\u0002\u0013\u0005\u0001\u000eC\u0004o\u0001\u0005\u0005I\u0011I8\t\u000fY\u0004\u0011\u0011!C\u0001o\"9A\u0010AA\u0001\n\u0003j\b\u0002C@\u0001\u0003\u0003%\t%!\u0001\t\u0013\u0005\r\u0001!!A\u0005B\u0005\u0015\u0001\"CA\u0004\u0001\u0005\u0005I\u0011IA\u0005\u000f%\tiaFA\u0001\u0012\u0003\tyA\u0002\u0005\u0017/\u0005\u0005\t\u0012AA\t\u0011\u0019)\u0005\u0003\"\u0001\u0002*!I\u00111\u0001\t\u0002\u0002\u0013\u0015\u0013Q\u0001\u0005\n\u0003W\u0001\u0012\u0011!CA\u0003[A\u0011\"!\r\u0011\u0003\u0003%\t)a\r\t\u0013\u0005}\u0002#!A\u0005\n\u0005\u0005#!D,ji\"d\u0015n\u001d;f]\u0016\u00148O\u0003\u0002\u00193\u00059!o\\;uS:<'\"\u0001\u000e\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u0001QdI\u0014+!\tq\u0012%D\u0001 \u0015\u0005\u0001\u0013!B:dC2\f\u0017B\u0001\u0012 \u0005\u0019\te.\u001f*fMB\u0011A%J\u0007\u0002/%\u0011ae\u0006\u0002\u0010\u0019&\u001cH/\u001a8fe6+7o]1hKB\u0011a\u0004K\u0005\u0003S}\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002,g9\u0011A&\r\b\u0003[Aj\u0011A\f\u0006\u0003_m\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0011\n\u0005Iz\u0012a\u00029bG.\fw-Z\u0005\u0003iU\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!AM\u0010\u0002\u0003\u0019,\u0012\u0001\u000f\t\u0005=eZ\u0014)\u0003\u0002;?\tIa)\u001e8di&|g.\r\t\u0003y}j\u0011!\u0010\u0006\u0003}e\tQ!Y2u_JL!\u0001Q\u001f\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\u0004\"A\b\"\n\u0005\r{\"\u0001B+oSR\f!A\u001a\u0011\u0002\rqJg.\u001b;?)\t9\u0005\n\u0005\u0002%\u0001!)ag\u0001a\u0001q\u0005!1m\u001c9z)\t95\nC\u00047\tA\u0005\t\u0019\u0001\u001d\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\taJ\u000b\u00029\u001f.\n\u0001\u000b\u0005\u0002R-6\t!K\u0003\u0002T)\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003+~\t!\"\u00198o_R\fG/[8o\u0013\t9&KA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001.\u0011\u0005m\u0003W\"\u0001/\u000b\u0005us\u0016\u0001\u00027b]\u001eT\u0011aX\u0001\u0005U\u00064\u0018-\u0003\u0002b9\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\u001a\t\u0003=\u0015L!AZ\u0010\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005%d\u0007C\u0001\u0010k\u0013\tYwDA\u0002B]fDq!\u001c\u0005\u0002\u0002\u0003\u0007A-A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002aB\u0019\u0011\u000f^5\u000e\u0003IT!a]\u0010\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002ve\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\tA8\u0010\u0005\u0002\u001fs&\u0011!p\b\u0002\b\u0005>|G.Z1o\u0011\u001di'\"!AA\u0002%\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0011!L \u0005\b[.\t\t\u00111\u0001e\u0003!A\u0017m\u001d5D_\u0012,G#\u00013\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012AW\u0001\u0007KF,\u0018\r\\:\u0015\u0007a\fY\u0001C\u0004n\u001d\u0005\u0005\t\u0019A5\u0002\u001b]KG\u000f\u001b'jgR,g.\u001a:t!\t!\u0003cE\u0003\u0011\u0003'\ty\u0002\u0005\u0004\u0002\u0016\u0005m\u0001hR\u0007\u0003\u0003/Q1!!\u0007 \u0003\u001d\u0011XO\u001c;j[\u0016LA!!\b\u0002\u0018\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u0005\u0005\u0012qE\u0007\u0003\u0003GQ1!!\n_\u0003\tIw.C\u00025\u0003G!\"!a\u0004\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007\u001d\u000by\u0003C\u00037'\u0001\u0007\u0001(A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005U\u00121\b\t\u0005=\u0005]\u0002(C\u0002\u0002:}\u0011aa\u00149uS>t\u0007\u0002CA\u001f)\u0005\u0005\t\u0019A$\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002DA\u00191,!\u0012\n\u0007\u0005\u001dCL\u0001\u0004PE*,7\r\u001e")
public final class WithListeners
implements ListenerMessage,
Product,
Serializable {
    private final Function1<ActorRef, BoxedUnit> f;

    public static Option<Function1<ActorRef, BoxedUnit>> unapply(WithListeners x$0) {
        return WithListeners$.MODULE$.unapply(x$0);
    }

    public static WithListeners apply(Function1<ActorRef, BoxedUnit> f) {
        return WithListeners$.MODULE$.apply(f);
    }

    public static <A> Function1<Function1<ActorRef, BoxedUnit>, A> andThen(Function1<WithListeners, A> g2) {
        return WithListeners$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, WithListeners> compose(Function1<A, Function1<ActorRef, BoxedUnit>> g2) {
        return WithListeners$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Function1<ActorRef, BoxedUnit> f() {
        return this.f;
    }

    public WithListeners copy(Function1<ActorRef, BoxedUnit> f) {
        return new WithListeners(f);
    }

    public Function1<ActorRef, BoxedUnit> copy$default$1() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "WithListeners";
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
                return this.f();
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
        return x$1 instanceof WithListeners;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "f";
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
        if (!(object instanceof WithListeners)) return false;
        boolean bl = true;
        if (!bl) return false;
        WithListeners withListeners = (WithListeners)x$1;
        Function1<ActorRef, BoxedUnit> function1 = this.f();
        Function1<ActorRef, BoxedUnit> function12 = withListeners.f();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public WithListeners(Function1<ActorRef, BoxedUnit> f) {
        this.f = f;
        Product.$init$(this);
    }
}

