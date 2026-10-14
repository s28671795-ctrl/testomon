/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.InvalidMessageException$;
import akka.routing.Broadcast$;
import akka.routing.RouterEnvelope;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ub\u0001\u0002\f\u0018\u0005rA\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\tw\u0001\u0011\t\u0012)A\u0005q!)A\b\u0001C\u0001{!9\u0001\tAA\u0001\n\u0003\t\u0005bB\"\u0001#\u0003%\t\u0001\u0012\u0005\b\u001f\u0002\t\t\u0011\"\u0011Q\u0011\u001dI\u0006!!A\u0005\u0002iCqA\u0018\u0001\u0002\u0002\u0013\u0005q\fC\u0004c\u0001\u0005\u0005I\u0011I2\t\u000f)\u0004\u0011\u0011!C\u0001W\"9\u0001\u000fAA\u0001\n\u0003\n\bbB:\u0001\u0003\u0003%\t\u0005\u001e\u0005\bk\u0002\t\t\u0011\"\u0011w\u0011\u001d9\b!!A\u0005Ba<\u0011\"!\u0001\u0018\u0003\u0003E\t!a\u0001\u0007\u0011Y9\u0012\u0011!E\u0001\u0003\u000bAa\u0001\u0010\t\u0005\u0002\u0005u\u0001bB;\u0011\u0003\u0003%)E\u001e\u0005\n\u0003?\u0001\u0012\u0011!CA\u0003CA\u0011\"!\n\u0011\u0003\u0003%\t)a\n\t\u0013\u0005M\u0002#!A\u0005\n\u0005U\"!\u0003\"s_\u0006$7-Y:u\u0015\tA\u0012$A\u0004s_V$\u0018N\\4\u000b\u0003i\tA!Y6lC\u000e\u00011#\u0002\u0001\u001eG\u001dR\u0003C\u0001\u0010\"\u001b\u0005y\"\"\u0001\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\tz\"AB!osJ+g\r\u0005\u0002%K5\tq#\u0003\u0002'/\tq!k\\;uKJ,eN^3m_B,\u0007C\u0001\u0010)\u0013\tIsDA\u0004Qe>$Wo\u0019;\u0011\u0005-\u001adB\u0001\u00172\u001d\ti\u0003'D\u0001/\u0015\ty3$\u0001\u0004=e>|GOP\u0005\u0002A%\u0011!gH\u0001\ba\u0006\u001c7.Y4f\u0013\t!TG\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00023?\u00059Q.Z:tC\u001e,W#\u0001\u001d\u0011\u0005yI\u0014B\u0001\u001e \u0005\r\te._\u0001\t[\u0016\u001c8/Y4fA\u00051A(\u001b8jiz\"\"AP \u0011\u0005\u0011\u0002\u0001\"\u0002\u001c\u0004\u0001\u0004A\u0014\u0001B2paf$\"A\u0010\"\t\u000fY\"\u0001\u0013!a\u0001q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A#+\u0005a25&A$\u0011\u0005!kU\"A%\u000b\u0005)[\u0015!C;oG\",7m[3e\u0015\tau$\u0001\u0006b]:|G/\u0019;j_:L!AT%\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002#B\u0011!kV\u0007\u0002'*\u0011A+V\u0001\u0005Y\u0006twMC\u0001W\u0003\u0011Q\u0017M^1\n\u0005a\u001b&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001\\!\tqB,\u0003\u0002^?\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0001\b\u0019\u0005\bC\"\t\t\u00111\u0001\\\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\tA\rE\u0002fQbj\u0011A\u001a\u0006\u0003O~\t!bY8mY\u0016\u001cG/[8o\u0013\tIgM\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGC\u00017p!\tqR.\u0003\u0002o?\t9!i\\8mK\u0006t\u0007bB1\u000b\u0003\u0003\u0005\r\u0001O\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0002Re\"9\u0011mCA\u0001\u0002\u0004Y\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003m\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0002#\u00061Q-];bYN$\"\u0001\\=\t\u000f\u0005t\u0011\u0011!a\u0001q!\"\u0001a\u001f@\u0000!\tqB0\u0003\u0002~?\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005I!I]8bI\u000e\f7\u000f\u001e\t\u0003IA\u0019R\u0001EA\u0004\u0003'\u0001b!!\u0003\u0002\u0010arTBAA\u0006\u0015\r\tiaH\u0001\beVtG/[7f\u0013\u0011\t\t\"a\u0003\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0002\u0016\u0005mQBAA\f\u0015\r\tI\"V\u0001\u0003S>L1\u0001NA\f)\t\t\u0019!A\u0003baBd\u0017\u0010F\u0002?\u0003GAQAN\nA\u0002a\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002*\u0005=\u0002\u0003\u0002\u0010\u0002,aJ1!!\f \u0005\u0019y\u0005\u000f^5p]\"A\u0011\u0011\u0007\u000b\u0002\u0002\u0003\u0007a(A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u000e\u0011\u0007I\u000bI$C\u0002\u0002<M\u0013aa\u00142kK\u000e$\b")
public final class Broadcast
implements RouterEnvelope,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Object message;

    public static Option<Object> unapply(Broadcast x$0) {
        return Broadcast$.MODULE$.unapply(x$0);
    }

    public static Broadcast apply(Object message) {
        return Broadcast$.MODULE$.apply(message);
    }

    public static <A> Function1<Object, A> andThen(Function1<Broadcast, A> g2) {
        return Broadcast$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, Broadcast> compose(Function1<A, Object> g2) {
        return Broadcast$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Object message() {
        return this.message;
    }

    public Broadcast copy(Object message) {
        return new Broadcast(message);
    }

    public Object copy$default$1() {
        return this.message();
    }

    @Override
    public String productPrefix() {
        return "Broadcast";
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
                return this.message();
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
        return x$1 instanceof Broadcast;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "message";
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
        if (!(object instanceof Broadcast)) return false;
        boolean bl = true;
        if (!bl) return false;
        Broadcast broadcast = (Broadcast)x$1;
        if (!BoxesRunTime.equals(this.message(), broadcast.message())) return false;
        return true;
    }

    public Broadcast(Object message) {
        this.message = message;
        Product.$init$(this);
        if (message == null) {
            throw InvalidMessageException$.MODULE$.apply("[null] is not an allowed message");
        }
    }
}

