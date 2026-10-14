/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.AllDeadLetters;
import akka.actor.Dropped$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%e\u0001B\u0010!\u0005\u0016B\u0001b\u0010\u0001\u0003\u0016\u0004%\t\u0001\u0011\u0005\t\t\u0002\u0011\t\u0012)A\u0005\u0003\"AQ\t\u0001BK\u0002\u0013\u0005a\t\u0003\u0005P\u0001\tE\t\u0015!\u0003H\u0011!\u0001\u0006A!f\u0001\n\u0003\t\u0006\u0002C+\u0001\u0005#\u0005\u000b\u0011\u0002*\t\u0011Y\u0003!Q3A\u0005\u0002EC\u0001b\u0016\u0001\u0003\u0012\u0003\u0006IA\u0015\u0005\u00061\u0002!\t!\u0017\u0005\b?\u0002\t\t\u0011\"\u0001a\u0011\u001d)\u0007!%A\u0005\u0002\u0019Dq!\u001d\u0001\u0012\u0002\u0013\u0005!\u000fC\u0004u\u0001E\u0005I\u0011A;\t\u000f]\u0004\u0011\u0013!C\u0001k\"9\u0001\u0010AA\u0001\n\u0003J\b\"CA\u0002\u0001\u0005\u0005I\u0011AA\u0003\u0011%\ti\u0001AA\u0001\n\u0003\ty\u0001C\u0005\u0002\u0016\u0001\t\t\u0011\"\u0011\u0002\u0018!I\u0011Q\u0005\u0001\u0002\u0002\u0013\u0005\u0011q\u0005\u0005\n\u0003c\u0001\u0011\u0011!C!\u0003gA\u0011\"a\u000e\u0001\u0003\u0003%\t%!\u000f\t\u0013\u0005m\u0002!!A\u0005B\u0005u\u0002\"CA \u0001\u0005\u0005I\u0011IA!\u000f\u001d\t)\u0005\tE\u0001\u0003\u000f2aa\b\u0011\t\u0002\u0005%\u0003B\u0002-\u001a\t\u0003\t)\u0006C\u0004\u0002Xe!\t!!\u0017\t\u0013\u0005]\u0013$!A\u0005\u0002\u0006\u0005\u0004\"CA63\u0005\u0005I\u0011QA7\u0011%\ty(GA\u0001\n\u0013\t\tIA\u0004Ee>\u0004\b/\u001a3\u000b\u0005\u0005\u0012\u0013!B1di>\u0014(\"A\u0012\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u0001a\u0005\f\u00194!\t9#&D\u0001)\u0015\u0005I\u0013!B:dC2\f\u0017BA\u0016)\u0005\u0019\te.\u001f*fMB\u0011QFL\u0007\u0002A%\u0011q\u0006\t\u0002\u000f\u00032dG)Z1e\u0019\u0016$H/\u001a:t!\t9\u0013'\u0003\u00023Q\t9\u0001K]8ek\u000e$\bC\u0001\u001b=\u001d\t)$H\u0004\u00027s5\tqG\u0003\u00029I\u00051AH]8pizJ\u0011!K\u0005\u0003w!\nq\u0001]1dW\u0006<W-\u0003\u0002>}\ta1+\u001a:jC2L'0\u00192mK*\u00111\bK\u0001\b[\u0016\u001c8/Y4f+\u0005\t\u0005CA\u0014C\u0013\t\u0019\u0005FA\u0002B]f\f\u0001\"\\3tg\u0006<W\rI\u0001\u0007e\u0016\f7o\u001c8\u0016\u0003\u001d\u0003\"\u0001\u0013'\u000f\u0005%S\u0005C\u0001\u001c)\u0013\tY\u0005&\u0001\u0004Qe\u0016$WMZ\u0005\u0003\u001b:\u0013aa\u0015;sS:<'BA&)\u0003\u001d\u0011X-Y:p]\u0002\naa]3oI\u0016\u0014X#\u0001*\u0011\u00055\u001a\u0016B\u0001+!\u0005!\t5\r^8s%\u00164\u0017aB:f]\u0012,'\u000fI\u0001\ne\u0016\u001c\u0017\u000e]5f]R\f!B]3dSBLWM\u001c;!\u0003\u0019a\u0014N\\5u}Q)!l\u0017/^=B\u0011Q\u0006\u0001\u0005\u0006\u007f%\u0001\r!\u0011\u0005\u0006\u000b&\u0001\ra\u0012\u0005\u0006!&\u0001\rA\u0015\u0005\u0006-&\u0001\rAU\u0001\u0005G>\u0004\u0018\u0010F\u0003[C\n\u001cG\rC\u0004@\u0015A\u0005\t\u0019A!\t\u000f\u0015S\u0001\u0013!a\u0001\u000f\"9\u0001K\u0003I\u0001\u0002\u0004\u0011\u0006b\u0002,\u000b!\u0003\u0005\rAU\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u00059'FA!iW\u0005I\u0007C\u00016p\u001b\u0005Y'B\u00017n\u0003%)hn\u00195fG.,GM\u0003\u0002oQ\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005A\\'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#A:+\u0005\u001dC\u0017AD2paf$C-\u001a4bk2$HeM\u000b\u0002m*\u0012!\u000b[\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t!\u0010E\u0002|\u0003\u0003i\u0011\u0001 \u0006\u0003{z\fA\u0001\\1oO*\tq0\u0001\u0003kCZ\f\u0017BA'}\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\t9\u0001E\u0002(\u0003\u0013I1!a\u0003)\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\r\t\u0015\u0011\u0003\u0005\n\u0003'\t\u0012\u0011!a\u0001\u0003\u000f\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\r!\u0015\tY\"!\tB\u001b\t\tiBC\u0002\u0002 !\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\u0019#!\b\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003S\ty\u0003E\u0002(\u0003WI1!!\f)\u0005\u001d\u0011un\u001c7fC:D\u0001\"a\u0005\u0014\u0003\u0003\u0005\r!Q\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002{\u0003kA\u0011\"a\u0005\u0015\u0003\u0003\u0005\r!a\u0002\u0002\u0011!\f7\u000f[\"pI\u0016$\"!a\u0002\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A_\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005%\u00121\t\u0005\t\u0003'9\u0012\u0011!a\u0001\u0003\u00069AI]8qa\u0016$\u0007CA\u0017\u001a'\u0011Ib%a\u0013\u0011\t\u00055\u00131K\u0007\u0003\u0003\u001fR1!!\u0015\u007f\u0003\tIw.C\u0002>\u0003\u001f\"\"!a\u0012\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000fi\u000bY&!\u0018\u0002`!)qh\u0007a\u0001\u0003\")Qi\u0007a\u0001\u000f\")ak\u0007a\u0001%RI!,a\u0019\u0002f\u0005\u001d\u0014\u0011\u000e\u0005\u0006\u007fq\u0001\r!\u0011\u0005\u0006\u000br\u0001\ra\u0012\u0005\u0006!r\u0001\rA\u0015\u0005\u0006-r\u0001\rAU\u0001\bk:\f\u0007\u000f\u001d7z)\u0011\ty'a\u001f\u0011\u000b\u001d\n\t(!\u001e\n\u0007\u0005M\u0004F\u0001\u0004PaRLwN\u001c\t\bO\u0005]\u0014i\u0012*S\u0013\r\tI\b\u000b\u0002\u0007)V\u0004H.\u001a\u001b\t\u0011\u0005uT$!AA\u0002i\u000b1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\u0019\tE\u0002|\u0003\u000bK1!a\"}\u0005\u0019y%M[3di\u0002")
public final class Dropped
implements AllDeadLetters,
Product,
Serializable {
    private final Object message;
    private final String reason;
    private final ActorRef sender;
    private final ActorRef recipient;

    public static Option<Tuple4<Object, String, ActorRef, ActorRef>> unapply(Dropped x$0) {
        return Dropped$.MODULE$.unapply(x$0);
    }

    public static Dropped apply(Object message, String reason, ActorRef sender, ActorRef recipient) {
        return Dropped$.MODULE$.apply(message, reason, sender, recipient);
    }

    public static Dropped apply(Object message, String reason, ActorRef recipient) {
        return Dropped$.MODULE$.apply(message, reason, recipient);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Object message() {
        return this.message;
    }

    public String reason() {
        return this.reason;
    }

    @Override
    public ActorRef sender() {
        return this.sender;
    }

    @Override
    public ActorRef recipient() {
        return this.recipient;
    }

    public Dropped copy(Object message, String reason, ActorRef sender, ActorRef recipient) {
        return new Dropped(message, reason, sender, recipient);
    }

    public Object copy$default$1() {
        return this.message();
    }

    public String copy$default$2() {
        return this.reason();
    }

    public ActorRef copy$default$3() {
        return this.sender();
    }

    public ActorRef copy$default$4() {
        return this.recipient();
    }

    @Override
    public String productPrefix() {
        return "Dropped";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.message();
            }
            case 1: {
                return this.reason();
            }
            case 2: {
                return this.sender();
            }
            case 3: {
                return this.recipient();
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
        return x$1 instanceof Dropped;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "message";
            }
            case 1: {
                return "reason";
            }
            case 2: {
                return "sender";
            }
            case 3: {
                return "recipient";
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
        if (!(object instanceof Dropped)) return false;
        boolean bl = true;
        if (!bl) return false;
        Dropped dropped = (Dropped)x$1;
        if (!BoxesRunTime.equals(this.message(), dropped.message())) return false;
        String string = this.reason();
        String string2 = dropped.reason();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        ActorRef actorRef2 = this.sender();
        ActorRef actorRef3 = dropped.sender();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        ActorRef actorRef4 = this.recipient();
        ActorRef actorRef5 = dropped.recipient();
        if (actorRef4 == null) {
            if (actorRef5 == null) return true;
            return false;
        } else {
            if (!((Object)actorRef4).equals(actorRef5)) return false;
            return true;
        }
    }

    public Dropped(Object message, String reason, ActorRef sender, ActorRef recipient) {
        this.message = message;
        this.reason = reason;
        this.sender = sender;
        this.recipient = recipient;
        Product.$init$(this);
    }
}

