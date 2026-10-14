/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Envelope$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005ec\u0001B\r\u001b\u0005~A\u0001\"\u000e\u0001\u0003\u0016\u0004%\tA\u000e\u0005\tu\u0001\u0011\t\u0012)A\u0005o!A1\b\u0001BK\u0002\u0013\u0005A\b\u0003\u0005D\u0001\tE\t\u0015!\u0003>\u0011\u0015!\u0005\u0001\"\u0003F\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u001dq\u0005!%A\u0005\u0002=CqA\u0017\u0001\u0012\u0002\u0013\u00051\fC\u0004^\u0001\u0005\u0005I\u0011\t0\t\u000f\u001d\u0004\u0011\u0011!C\u0001Q\"9A\u000eAA\u0001\n\u0003i\u0007b\u00029\u0001\u0003\u0003%\t%\u001d\u0005\bq\u0002\t\t\u0011\"\u0001z\u0011\u001dq\b!!A\u0005B}D\u0011\"a\u0001\u0001\u0003\u0003%\t%!\u0002\t\u0013\u0005\u001d\u0001!!A\u0005B\u0005%\u0001\"CA\u0006\u0001\u0005\u0005I\u0011IA\u0007\u000f\u001d\t\tB\u0007E\u0001\u0003'1a!\u0007\u000e\t\u0002\u0005U\u0001B\u0002#\u0014\t\u0003\t\t\u0003C\u0004\u0002$M!\t!!\n\t\u0013\u0005\r2#!A\u0005\u0002\u0006U\u0002\"CA\u001e'\u0005\u0005I\u0011QA\u001f\u0011%\tyeEA\u0001\n\u0013\t\tF\u0001\u0005F]Z,Gn\u001c9f\u0015\tYB$\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005i\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001A\u0019J\u0003CA\u0011%\u001b\u0005\u0011#\"A\u0012\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0015\u0012#AB!osJ+g\r\u0005\u0002\"O%\u0011\u0001F\t\u0002\b!J|G-^2u!\tQ#G\u0004\u0002,a9\u0011AfL\u0007\u0002[)\u0011aFH\u0001\u0007yI|w\u000e\u001e \n\u0003\rJ!!\r\u0012\u0002\u000fA\f7m[1hK&\u00111\u0007\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003c\t\nq!\\3tg\u0006<W-F\u00018!\t\t\u0003(\u0003\u0002:E\t\u0019\u0011I\\=\u0002\u00115,7o]1hK\u0002\naa]3oI\u0016\u0014X#A\u001f\u0011\u0005y\nU\"A \u000b\u0005\u0001c\u0012!B1di>\u0014\u0018B\u0001\"@\u0005!\t5\r^8s%\u00164\u0017aB:f]\u0012,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\u0019C\u0015\n\u0005\u0002H\u00015\t!\u0004C\u00036\u000b\u0001\u0007q\u0007C\u0003<\u000b\u0001\u0007Q(\u0001\u0003d_BLHc\u0001$M\u001b\"9QG\u0002I\u0001\u0002\u00049\u0004bB\u001e\u0007!\u0003\u0005\r!P\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0005\u0001&FA\u001cRW\u0005\u0011\u0006CA*Y\u001b\u0005!&BA+W\u0003%)hn\u00195fG.,GM\u0003\u0002XE\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005e#&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T#\u0001/+\u0005u\n\u0016!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001`!\t\u0001W-D\u0001b\u0015\t\u00117-\u0001\u0003mC:<'\"\u00013\u0002\t)\fg/Y\u0005\u0003M\u0006\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#A5\u0011\u0005\u0005R\u0017BA6#\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\t9d\u000eC\u0004p\u0017\u0005\u0005\t\u0019A5\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005\u0011\bcA:wo5\tAO\u0003\u0002vE\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005]$(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"A_?\u0011\u0005\u0005Z\u0018B\u0001?#\u0005\u001d\u0011un\u001c7fC:Dqa\\\u0007\u0002\u0002\u0003\u0007q'\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GcA0\u0002\u0002!9qNDA\u0001\u0002\u0004I\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003%\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002?\u00061Q-];bYN$2A_A\b\u0011\u001dy\u0017#!AA\u0002]\n\u0001\"\u00128wK2|\u0007/\u001a\t\u0003\u000fN\u0019Ba\u0005\u0011\u0002\u0018A!\u0011\u0011DA\u0010\u001b\t\tYBC\u0002\u0002\u001e\r\f!![8\n\u0007M\nY\u0002\u0006\u0002\u0002\u0014\u0005)\u0011\r\u001d9msR9a)a\n\u0002*\u0005-\u0002\"B\u001b\u0016\u0001\u00049\u0004\"B\u001e\u0016\u0001\u0004i\u0004bBA\u0017+\u0001\u0007\u0011qF\u0001\u0007gf\u001cH/Z7\u0011\u0007y\n\t$C\u0002\u00024}\u00121\"Q2u_J\u001c\u0016p\u001d;f[R)a)a\u000e\u0002:!)QG\u0006a\u0001o!)1H\u0006a\u0001{\u00059QO\\1qa2LH\u0003BA \u0003\u0017\u0002R!IA!\u0003\u000bJ1!a\u0011#\u0005\u0019y\u0005\u000f^5p]B)\u0011%a\u00128{%\u0019\u0011\u0011\n\u0012\u0003\rQ+\b\u000f\\33\u0011!\tieFA\u0001\u0002\u00041\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u000b\t\u0004A\u0006U\u0013bAA,C\n1qJ\u00196fGR\u0004")
public final class Envelope
implements Product,
Serializable {
    private final Object message;
    private final ActorRef sender;

    public static Option<Tuple2<Object, ActorRef>> unapply(Envelope x$0) {
        return Envelope$.MODULE$.unapply(x$0);
    }

    public static Envelope apply(Object message, ActorRef sender) {
        return Envelope$.MODULE$.apply(message, sender);
    }

    public static Envelope apply(Object message, ActorRef sender, ActorSystem system) {
        return Envelope$.MODULE$.apply(message, sender, system);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Object message() {
        return this.message;
    }

    public ActorRef sender() {
        return this.sender;
    }

    public Envelope copy(Object message, ActorRef sender) {
        return Envelope$.MODULE$.apply(message, sender);
    }

    public Object copy$default$1() {
        return this.message();
    }

    public ActorRef copy$default$2() {
        return this.sender();
    }

    @Override
    public String productPrefix() {
        return "Envelope";
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
                return this.message();
            }
            case 1: {
                return this.sender();
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
        return x$1 instanceof Envelope;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "message";
            }
            case 1: {
                return "sender";
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
        if (!(object instanceof Envelope)) return false;
        boolean bl = true;
        if (!bl) return false;
        Envelope envelope = (Envelope)x$1;
        if (!BoxesRunTime.equals(this.message(), envelope.message())) return false;
        ActorRef actorRef2 = this.sender();
        ActorRef actorRef3 = envelope.sender();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public Envelope(Object message, ActorRef sender) {
        this.message = message;
        this.sender = sender;
        Product.$init$(this);
    }
}

