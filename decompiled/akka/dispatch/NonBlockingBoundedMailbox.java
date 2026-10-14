/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.BoundedNodeMessageQueue;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import akka.dispatch.NonBlockingBoundedMailbox$;
import akka.dispatch.ProducesMessageQueue;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Ee\u0001\u0002\r\u001a\u0001zA\u0001B\u0010\u0001\u0003\u0016\u0004%\ta\u0010\u0005\t\u0007\u0002\u0011\t\u0012)A\u0005\u0001\")A\t\u0001C\u0001\u000b\")A\t\u0001C\u0001\u0011\")\u0001\r\u0001C#C\"91\u000fAA\u0001\n\u0003!\bb\u0002<\u0001#\u0003%\ta\u001e\u0005\n\u0003\u000b\u0001\u0011\u0011!C!\u0003\u000fA\u0001\"!\u0007\u0001\u0003\u0003%\ta\u0010\u0005\n\u00037\u0001\u0011\u0011!C\u0001\u0003;A\u0011\"!\u000b\u0001\u0003\u0003%\t%a\u000b\t\u0013\u0005e\u0002!!A\u0005\u0002\u0005m\u0002\"CA#\u0001\u0005\u0005I\u0011IA$\u0011%\tY\u0005AA\u0001\n\u0003\ni\u0005C\u0005\u0002P\u0001\t\t\u0011\"\u0011\u0002R!I\u00111\u000b\u0001\u0002\u0002\u0013\u0005\u0013QK\u0004\n\u00033J\u0012\u0011!E\u0001\u000372\u0001\u0002G\r\u0002\u0002#\u0005\u0011Q\f\u0005\u0007\tJ!\t!!\u001e\t\u0013\u0005=##!A\u0005F\u0005E\u0003\"CA<%\u0005\u0005I\u0011QA=\u0011%\tiHEA\u0001\n\u0003\u000by\bC\u0005\u0002\bJ\t\t\u0011\"\u0003\u0002\n\nIbj\u001c8CY>\u001c7.\u001b8h\u0005>,h\u000eZ3e\u001b\u0006LGNY8y\u0015\tQ2$\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005a\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0004\u0001?\u0015JsF\r\t\u0003A\rj\u0011!\t\u0006\u0002E\u0005)1oY1mC&\u0011A%\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0019:S\"A\r\n\u0005!J\"aC'bS2\u0014w\u000e\u001f+za\u0016\u00042A\n\u0016-\u0013\tY\u0013D\u0001\u000bQe>$WoY3t\u001b\u0016\u001c8/Y4f#V,W/\u001a\t\u0003M5J!AL\r\u0003/\t{WO\u001c3fI:{G-Z'fgN\fw-Z)vKV,\u0007C\u0001\u00111\u0013\t\t\u0014EA\u0004Qe>$Wo\u0019;\u0011\u0005MZdB\u0001\u001b:\u001d\t)\u0004(D\u00017\u0015\t9T$\u0001\u0004=e>|GOP\u0005\u0002E%\u0011!(I\u0001\ba\u0006\u001c7.Y4f\u0013\taTH\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002;C\u0005A1-\u00199bG&$\u00180F\u0001A!\t\u0001\u0013)\u0003\u0002CC\t\u0019\u0011J\u001c;\u0002\u0013\r\f\u0007/Y2jif\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002G\u000fB\u0011a\u0005\u0001\u0005\u0006}\r\u0001\r\u0001\u0011\u000b\u0004\r&+\u0006\"\u0002&\u0005\u0001\u0004Y\u0015\u0001C:fiRLgnZ:\u0011\u00051\u0013fBA'Q\u001b\u0005q%BA(\u001c\u0003\u0015\t7\r^8s\u0013\t\tf*A\u0006BGR|'oU=ti\u0016l\u0017BA*U\u0005!\u0019V\r\u001e;j]\u001e\u001c(BA)O\u0011\u00151F\u00011\u0001X\u0003\u0019\u0019wN\u001c4jOB\u0011\u0001LX\u0007\u00023*\u0011aK\u0017\u0006\u00037r\u000b\u0001\u0002^=qKN\fg-\u001a\u0006\u0002;\u0006\u00191m\\7\n\u0005}K&AB\"p]\u001aLw-\u0001\u0004de\u0016\fG/\u001a\u000b\u0004E\u0016l\u0007C\u0001\u0014d\u0013\t!\u0017D\u0001\u0007NKN\u001c\u0018mZ3Rk\u0016,X\rC\u0003g\u000b\u0001\u0007q-A\u0003po:,'\u000fE\u0002!Q*L!![\u0011\u0003\r=\u0003H/[8o!\ti5.\u0003\u0002m\u001d\nA\u0011i\u0019;peJ+g\rC\u0003o\u000b\u0001\u0007q.\u0001\u0004tsN$X-\u001c\t\u0004A!\u0004\bCA'r\u0013\t\u0011hJA\u0006BGR|'oU=ti\u0016l\u0017\u0001B2paf$\"AR;\t\u000fy2\u0001\u0013!a\u0001\u0001\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#\u0001=+\u0005\u0001K8&\u0001>\u0011\u0007m\f\t!D\u0001}\u0015\tih0A\u0005v]\u000eDWmY6fI*\u0011q0I\u0001\u000bC:tw\u000e^1uS>t\u0017bAA\u0002y\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tI\u0001\u0005\u0003\u0002\f\u0005UQBAA\u0007\u0015\u0011\ty!!\u0005\u0002\t1\fgn\u001a\u0006\u0003\u0003'\tAA[1wC&!\u0011qCA\u0007\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA\u0010\u0003K\u00012\u0001IA\u0011\u0013\r\t\u0019#\t\u0002\u0004\u0003:L\b\u0002CA\u0014\u0015\u0005\u0005\t\u0019\u0001!\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\ti\u0003\u0005\u0004\u00020\u0005U\u0012qD\u0007\u0003\u0003cQ1!a\r\"\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003o\t\tD\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u001f\u0003\u0007\u00022\u0001IA \u0013\r\t\t%\t\u0002\b\u0005>|G.Z1o\u0011%\t9\u0003DA\u0001\u0002\u0004\ty\"\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u0005\u0003\u0013B\u0001\"a\n\u000e\u0003\u0003\u0005\r\u0001Q\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001)\u0001\u0005u_N#(/\u001b8h)\t\tI!\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003{\t9\u0006C\u0005\u0002(A\t\t\u00111\u0001\u0002 \u0005Ibj\u001c8CY>\u001c7.\u001b8h\u0005>,h\u000eZ3e\u001b\u0006LGNY8y!\t1#cE\u0003\u0013\u0003?\nY\u0007\u0005\u0004\u0002b\u0005\u001d\u0004IR\u0007\u0003\u0003GR1!!\u001a\"\u0003\u001d\u0011XO\u001c;j[\u0016LA!!\u001b\u0002d\t\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\u00055\u00141O\u0007\u0003\u0003_RA!!\u001d\u0002\u0012\u0005\u0011\u0011n\\\u0005\u0004y\u0005=DCAA.\u0003\u0015\t\u0007\u000f\u001d7z)\r1\u00151\u0010\u0005\u0006}U\u0001\r\u0001Q\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\t\t)a!\u0011\u0007\u0001B\u0007\t\u0003\u0005\u0002\u0006Z\t\t\u00111\u0001G\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003\u0017\u0003B!a\u0003\u0002\u000e&!\u0011qRA\u0007\u0005\u0019y%M[3di\u0002")
public class NonBlockingBoundedMailbox
implements MailboxType,
ProducesMessageQueue<BoundedNodeMessageQueue>,
Product,
Serializable {
    private final int capacity;

    public static Option<Object> unapply(NonBlockingBoundedMailbox x$0) {
        return NonBlockingBoundedMailbox$.MODULE$.unapply(x$0);
    }

    public static NonBlockingBoundedMailbox apply(int capacity) {
        return NonBlockingBoundedMailbox$.MODULE$.apply(capacity);
    }

    public static <A> Function1<Object, A> andThen(Function1<NonBlockingBoundedMailbox, A> g2) {
        return NonBlockingBoundedMailbox$.MODULE$.andThen(g2);
    }

    public static <A> Function1<A, NonBlockingBoundedMailbox> compose(Function1<A, Object> g2) {
        return NonBlockingBoundedMailbox$.MODULE$.compose(g2);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int capacity() {
        return this.capacity;
    }

    @Override
    public final MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new BoundedNodeMessageQueue(this.capacity());
    }

    public NonBlockingBoundedMailbox copy(int capacity) {
        return new NonBlockingBoundedMailbox(capacity);
    }

    public int copy$default$1() {
        return this.capacity();
    }

    @Override
    public String productPrefix() {
        return "NonBlockingBoundedMailbox";
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
                return BoxesRunTime.boxToInteger(this.capacity());
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
        return x$1 instanceof NonBlockingBoundedMailbox;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "capacity";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.capacity());
        return Statics.finalizeHash(n, 1);
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
        if (!(object instanceof NonBlockingBoundedMailbox)) return false;
        boolean bl = true;
        if (!bl) return false;
        NonBlockingBoundedMailbox nonBlockingBoundedMailbox = (NonBlockingBoundedMailbox)x$1;
        if (this.capacity() != nonBlockingBoundedMailbox.capacity()) return false;
        if (!nonBlockingBoundedMailbox.canEqual(this)) return false;
        return true;
    }

    public NonBlockingBoundedMailbox(int capacity) {
        this.capacity = capacity;
        Product.$init$(this);
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity for NonBlockingBoundedMailbox can not be negative");
        }
    }

    public NonBlockingBoundedMailbox(ActorSystem.Settings settings, Config config) {
        this(config.getInt("mailbox-capacity"));
    }
}

