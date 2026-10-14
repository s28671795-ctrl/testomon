/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Address;
import akka.routing.ActorRefRoutee;
import akka.routing.ActorSelectionRoutee;
import akka.routing.ConsistentRoutee$;
import akka.routing.Routee;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005md!\u0002\u000e\u001c\u0005vy\u0002\u0002\u0003\u001c\u0001\u0005+\u0007I\u0011A\u001c\t\u0011q\u0002!\u0011#Q\u0001\naB\u0001\"\u0010\u0001\u0003\u0016\u0004%\tA\u0010\u0005\t\u000b\u0002\u0011\t\u0012)A\u0005\u007f!)a\t\u0001C\u0001\u000f\")1\n\u0001C!\u0019\")Q\u000b\u0001C\u0005-\"9A\fAA\u0001\n\u0003i\u0006b\u00021\u0001#\u0003%\t!\u0019\u0005\bY\u0002\t\n\u0011\"\u0001n\u0011\u001dy\u0007!!A\u0005BADq\u0001\u001f\u0001\u0002\u0002\u0013\u0005\u0011\u0010C\u0004~\u0001\u0005\u0005I\u0011\u0001@\t\u0013\u0005%\u0001!!A\u0005B\u0005-\u0001\"CA\r\u0001\u0005\u0005I\u0011AA\u000e\u0011%\t)\u0003AA\u0001\n\u0003\n9\u0003C\u0005\u0002,\u0001\t\t\u0011\"\u0011\u0002.!I\u0011q\u0006\u0001\u0002\u0002\u0013\u0005\u0013\u0011G\u0004\u000b\u0003kY\u0012\u0011!E\u0001;\u0005]b!\u0003\u000e\u001c\u0003\u0003E\t!HA\u001d\u0011\u00191E\u0003\"\u0001\u0002R!A1\nFA\u0001\n\u000b\n\u0019\u0006C\u0005\u0002VQ\t\t\u0011\"!\u0002X!I\u0011Q\f\u000b\u0002\u0002\u0013\u0005\u0015q\f\u0005\n\u0003c\"\u0012\u0011!C\u0005\u0003g\u0012\u0001cQ8og&\u001cH/\u001a8u%>,H/Z3\u000b\u0005qi\u0012a\u0002:pkRLgn\u001a\u0006\u0002=\u0005!\u0011m[6b'\u0011\u0001\u0001EJ\u0015\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0003\r\nQa]2bY\u0006L!!\n\u0012\u0003\r\u0005s\u0017PU3g!\t\ts%\u0003\u0002)E\t9\u0001K]8ek\u000e$\bC\u0001\u00164\u001d\tY\u0013G\u0004\u0002-a5\tQF\u0003\u0002/_\u00051AH]8piz\u001a\u0001!C\u0001$\u0013\t\u0011$%A\u0004qC\u000e\\\u0017mZ3\n\u0005Q*$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001a#\u0003\u0019\u0011x.\u001e;fKV\t\u0001\b\u0005\u0002:u5\t1$\u0003\u0002<7\t1!k\\;uK\u0016\fqA]8vi\u0016,\u0007%A\u0006tK24\u0017\t\u001a3sKN\u001cX#A \u0011\u0005\u0001\u001bU\"A!\u000b\u0005\tk\u0012!B1di>\u0014\u0018B\u0001#B\u0005\u001d\tE\r\u001a:fgN\fAb]3mM\u0006#GM]3tg\u0002\na\u0001P5oSRtDc\u0001%J\u0015B\u0011\u0011\b\u0001\u0005\u0006m\u0015\u0001\r\u0001\u000f\u0005\u0006{\u0015\u0001\raP\u0001\ti>\u001cFO]5oOR\tQ\n\u0005\u0002O%:\u0011q\n\u0015\t\u0003Y\tJ!!\u0015\u0012\u0002\rA\u0013X\rZ3g\u0013\t\u0019FK\u0001\u0004TiJLgn\u001a\u0006\u0003#\n\nq\u0003^8TiJLgnZ,ji\"4W\u000f\u001c7BI\u0012\u0014Xm]:\u0015\u00055;\u0006\"\u0002-\b\u0001\u0004I\u0016\u0001\u00029bi\"\u0004\"\u0001\u0011.\n\u0005m\u000b%!C!di>\u0014\b+\u0019;i\u0003\u0011\u0019w\u000e]=\u0015\u0007!sv\fC\u00047\u0011A\u0005\t\u0019\u0001\u001d\t\u000fuB\u0001\u0013!a\u0001\u007f\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u00012+\u0005a\u001a7&\u00013\u0011\u0005\u0015TW\"\u00014\u000b\u0005\u001dD\u0017!C;oG\",7m[3e\u0015\tI'%\u0001\u0006b]:|G/\u0019;j_:L!a\u001b4\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u00039T#aP2\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\t\bC\u0001:x\u001b\u0005\u0019(B\u0001;v\u0003\u0011a\u0017M\\4\u000b\u0003Y\fAA[1wC&\u00111k]\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002uB\u0011\u0011e_\u0005\u0003y\n\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2a`A\u0003!\r\t\u0013\u0011A\u0005\u0004\u0003\u0007\u0011#aA!os\"A\u0011qA\u0007\u0002\u0002\u0003\u0007!0A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003\u001b\u0001R!a\u0004\u0002\u0016}l!!!\u0005\u000b\u0007\u0005M!%\u0001\u0006d_2dWm\u0019;j_:LA!a\u0006\u0002\u0012\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\ti\"a\t\u0011\u0007\u0005\ny\"C\u0002\u0002\"\t\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u0002\b=\t\t\u00111\u0001\u0000\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007E\fI\u0003\u0003\u0005\u0002\bA\t\t\u00111\u0001{\u0003!A\u0017m\u001d5D_\u0012,G#\u0001>\u0002\r\u0015\fX/\u00197t)\u0011\ti\"a\r\t\u0011\u0005\u001d!#!AA\u0002}\f\u0001cQ8og&\u001cH/\u001a8u%>,H/Z3\u0011\u0005e\"2#\u0002\u000b\u0002<\u0005\u001d\u0003cBA\u001f\u0003\u0007Bt\bS\u0007\u0003\u0003\u007fQ1!!\u0011#\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u0012\u0002@\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\u001c\u001a\u0011\t\u0005%\u0013qJ\u0007\u0003\u0003\u0017R1!!\u0014v\u0003\tIw.C\u00025\u0003\u0017\"\"!a\u000e\u0015\u0003E\fQ!\u00199qYf$R\u0001SA-\u00037BQAN\fA\u0002aBQ!P\fA\u0002}\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002b\u00055\u0004#B\u0011\u0002d\u0005\u001d\u0014bAA3E\t1q\n\u001d;j_:\u0004R!IA5q}J1!a\u001b#\u0005\u0019!V\u000f\u001d7fe!A\u0011q\u000e\r\u0002\u0002\u0003\u0007\u0001*A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u001e\u0011\u0007I\f9(C\u0002\u0002zM\u0014aa\u00142kK\u000e$\b")
public final class ConsistentRoutee
implements Product,
Serializable {
    private final Routee routee;
    private final Address selfAddress;

    public static Option<Tuple2<Routee, Address>> unapply(ConsistentRoutee x$0) {
        return ConsistentRoutee$.MODULE$.unapply(x$0);
    }

    public static ConsistentRoutee apply(Routee routee, Address selfAddress) {
        return ConsistentRoutee$.MODULE$.apply(routee, selfAddress);
    }

    public static Function1<Tuple2<Routee, Address>, ConsistentRoutee> tupled() {
        return ConsistentRoutee$.MODULE$.tupled();
    }

    public static Function1<Routee, Function1<Address, ConsistentRoutee>> curried() {
        return ConsistentRoutee$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Routee routee() {
        return this.routee;
    }

    public Address selfAddress() {
        return this.selfAddress;
    }

    public String toString() {
        Routee routee = this.routee();
        if (routee instanceof ActorRefRoutee) {
            ActorRefRoutee actorRefRoutee = (ActorRefRoutee)routee;
            ActorRef ref = actorRefRoutee.ref();
            return this.toStringWithfullAddress(ref.path());
        }
        if (routee instanceof ActorSelectionRoutee) {
            ActorSelectionRoutee actorSelectionRoutee = (ActorSelectionRoutee)routee;
            ActorSelection sel = actorSelectionRoutee.selection();
            return new StringBuilder(0).append(this.toStringWithfullAddress(sel.anchorPath())).append(sel.pathString()).toString();
        }
        return routee.toString();
    }

    private String toStringWithfullAddress(ActorPath path) {
        Address address = path.address();
        if (address != null) {
            Option<String> option = address.host();
            Option<Object> option2 = address.port();
            if (None$.MODULE$.equals(option) && None$.MODULE$.equals(option2)) {
                return path.toStringWithAddress(this.selfAddress());
            }
        }
        return path.toString();
    }

    public ConsistentRoutee copy(Routee routee, Address selfAddress) {
        return new ConsistentRoutee(routee, selfAddress);
    }

    public Routee copy$default$1() {
        return this.routee();
    }

    public Address copy$default$2() {
        return this.selfAddress();
    }

    @Override
    public String productPrefix() {
        return "ConsistentRoutee";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.routee();
            }
            case 1: {
                return this.selfAddress();
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
        return x$1 instanceof ConsistentRoutee;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "routee";
            }
            case 1: {
                return "selfAddress";
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
        if (!(object instanceof ConsistentRoutee)) return false;
        boolean bl = true;
        if (!bl) return false;
        ConsistentRoutee consistentRoutee = (ConsistentRoutee)x$1;
        Routee routee = this.routee();
        Routee routee2 = consistentRoutee.routee();
        if (routee == null) {
            if (routee2 != null) {
                return false;
            }
        } else if (!routee.equals(routee2)) return false;
        Address address = this.selfAddress();
        Address address2 = consistentRoutee.selfAddress();
        if (address == null) {
            if (address2 == null) return true;
            return false;
        } else {
            if (!((Object)address).equals(address2)) return false;
            return true;
        }
    }

    public ConsistentRoutee(Routee routee, Address selfAddress) {
        this.routee = routee;
        this.selfAddress = selfAddress;
        Product.$init$(this);
    }
}

