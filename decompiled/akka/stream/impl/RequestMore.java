/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.DeadLetterSuppression;
import akka.actor.NoSerializationVerificationNeeded;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorSubscription;
import akka.stream.impl.RequestMore$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rf!B\r\u001b\u0005z\u0001\u0003\u0002C!\u0001\u0005+\u0007I\u0011\u0001\"\t\u0011I\u0003!\u0011#Q\u0001\n\rC\u0001b\u0015\u0001\u0003\u0016\u0004%\t\u0001\u0016\u0005\t1\u0002\u0011\t\u0012)A\u0005+\")\u0011\f\u0001C\u00015\"9a\fAA\u0001\n\u0003y\u0006bB4\u0001#\u0003%\t\u0001\u001b\u0005\bk\u0002\t\n\u0011\"\u0001w\u0011\u001dQ\b!!A\u0005BmD\u0011\"!\u0003\u0001\u0003\u0003%\t!a\u0003\t\u0013\u0005M\u0001!!A\u0005\u0002\u0005U\u0001\"CA\u000e\u0001\u0005\u0005I\u0011IA\u000f\u0011%\tY\u0003AA\u0001\n\u0003\ti\u0003C\u0005\u00028\u0001\t\t\u0011\"\u0011\u0002:!I\u0011Q\b\u0001\u0002\u0002\u0013\u0005\u0013q\b\u0005\n\u0003\u0003\u0002\u0011\u0011!C!\u0003\u0007B\u0011\"!\u0012\u0001\u0003\u0003%\t%a\u0012\b\u0015\u0005]#$!A\t\u0002y\tIFB\u0005\u001a5\u0005\u0005\t\u0012\u0001\u0010\u0002\\!1\u0011l\u0005C\u0001\u0003OB\u0011\"!\u0011\u0014\u0003\u0003%)%a\u0011\t\u0013\u0005%4#!A\u0005\u0002\u0006-\u0004\"CA>'\u0005\u0005I\u0011QA?\u0011%\tIjEA\u0001\n\u0013\tYJA\u0006SKF,Xm\u001d;N_J,'BA\u000e\u001d\u0003\u0011IW\u000e\u001d7\u000b\u0005uq\u0012AB:ue\u0016\fWNC\u0001 \u0003\u0011\t7n[1\u0016\u0005\u0005J5C\u0002\u0001#Q9\nD\u0007\u0005\u0002$M5\tAEC\u0001&\u0003\u0015\u00198-\u00197b\u0013\t9CE\u0001\u0004B]f\u0014VM\u001a\t\u0003S1j\u0011A\u000b\u0006\u0003Wy\tQ!Y2u_JL!!\f\u0016\u0003+\u0011+\u0017\r\u001a'fiR,'oU;qaJ,7o]5p]B\u0011\u0011fL\u0005\u0003a)\u0012\u0011ET8TKJL\u0017\r\\5{CRLwN\u001c,fe&4\u0017nY1uS>tg*Z3eK\u0012\u0004\"a\t\u001a\n\u0005M\"#a\u0002)s_\u0012,8\r\u001e\t\u0003kyr!A\u000e\u001f\u000f\u0005]ZT\"\u0001\u001d\u000b\u0005eR\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u0003\u0015J!!\u0010\u0013\u0002\u000fA\f7m[1hK&\u0011q\b\u0011\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003{\u0011\nAb];cg\u000e\u0014\u0018\u000e\u001d;j_:,\u0012a\u0011\t\u0004\t\u0016;U\"\u0001\u000e\n\u0005\u0019S\"!E!di>\u00148+\u001e2tGJL\u0007\u000f^5p]B\u0011\u0001*\u0013\u0007\u0001\t\u0015Q\u0005A1\u0001L\u0005\u0005!\u0016C\u0001'P!\t\u0019S*\u0003\u0002OI\t9aj\u001c;iS:<\u0007CA\u0012Q\u0013\t\tFEA\u0002B]f\fQb];cg\u000e\u0014\u0018\u000e\u001d;j_:\u0004\u0013A\u00023f[\u0006tG-F\u0001V!\t\u0019c+\u0003\u0002XI\t!Aj\u001c8h\u0003\u001d!W-\\1oI\u0002\na\u0001P5oSRtDcA.];B\u0019A\tA$\t\u000b\u0005+\u0001\u0019A\"\t\u000bM+\u0001\u0019A+\u0002\t\r|\u0007/_\u000b\u0003A\u000e$2!\u00193g!\r!\u0005A\u0019\t\u0003\u0011\u000e$QA\u0013\u0004C\u0002-Cq!\u0011\u0004\u0011\u0002\u0003\u0007Q\rE\u0002E\u000b\nDqa\u0015\u0004\u0011\u0002\u0003\u0007Q+\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005%$X#\u00016+\u0005\r[7&\u00017\u0011\u00055\u0014X\"\u00018\u000b\u0005=\u0004\u0018!C;oG\",7m[3e\u0015\t\tH%\u0001\u0006b]:|G/\u0019;j_:L!a\u001d8\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003K\u000f\t\u00071*\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005]LX#\u0001=+\u0005U[G!\u0002&\t\u0005\u0004Y\u0015!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001}!\ri\u0018QA\u0007\u0002}*\u0019q0!\u0001\u0002\t1\fgn\u001a\u0006\u0003\u0003\u0007\tAA[1wC&\u0019\u0011q\u0001@\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\ti\u0001E\u0002$\u0003\u001fI1!!\u0005%\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\ry\u0015q\u0003\u0005\n\u00033Y\u0011\u0011!a\u0001\u0003\u001b\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u0010!\u0015\t\t#a\nP\u001b\t\t\u0019CC\u0002\u0002&\u0011\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI#a\t\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003_\t)\u0004E\u0002$\u0003cI1!a\r%\u0005\u001d\u0011un\u001c7fC:D\u0001\"!\u0007\u000e\u0003\u0003\u0005\raT\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002}\u0003wA\u0011\"!\u0007\u000f\u0003\u0003\u0005\r!!\u0004\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!\u0004\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001`\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005=\u0012\u0011\n\u0005\t\u00033\t\u0012\u0011!a\u0001\u001f\"\u001a\u0001!!\u0014\u0011\t\u0005=\u00131K\u0007\u0003\u0003#R!!\u001d\u0010\n\t\u0005U\u0013\u0011\u000b\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.A\u0006SKF,Xm\u001d;N_J,\u0007C\u0001#\u0014'\u0011\u0019\"%!\u0018\u0011\t\u0005}\u0013QM\u0007\u0003\u0003CRA!a\u0019\u0002\u0002\u0005\u0011\u0011n\\\u0005\u0004\u007f\u0005\u0005DCAA-\u0003\u0015\t\u0007\u000f\u001d7z+\u0011\ti'a\u001d\u0015\r\u0005=\u0014QOA=!\u0011!\u0005!!\u001d\u0011\u0007!\u000b\u0019\bB\u0003K-\t\u00071\n\u0003\u0004B-\u0001\u0007\u0011q\u000f\t\u0005\t\u0016\u000b\t\bC\u0003T-\u0001\u0007Q+A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\u0005}\u0014\u0011\u0013\u000b\u0005\u0003\u0003\u000b\u0019\nE\u0003$\u0003\u0007\u000b9)C\u0002\u0002\u0006\u0012\u0012aa\u00149uS>t\u0007CB\u0012\u0002\n\u00065U+C\u0002\u0002\f\u0012\u0012a\u0001V;qY\u0016\u0014\u0004\u0003\u0002#F\u0003\u001f\u00032\u0001SAI\t\u0015QuC1\u0001L\u0011%\t)jFA\u0001\u0002\u0004\t9*A\u0002yIA\u0002B\u0001\u0012\u0001\u0002\u0010\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u0014\t\u0004{\u0006}\u0015bAAQ}\n1qJ\u00196fGR\u0004")
@InternalApi
public final class RequestMore<T>
implements DeadLetterSuppression,
NoSerializationVerificationNeeded,
Product,
Serializable {
    private final ActorSubscription<T> subscription;
    private final long demand;

    public static <T> Option<Tuple2<ActorSubscription<T>, Object>> unapply(RequestMore<T> x$0) {
        return RequestMore$.MODULE$.unapply(x$0);
    }

    public static <T> RequestMore<T> apply(ActorSubscription<T> subscription, long demand) {
        return RequestMore$.MODULE$.apply(subscription, demand);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorSubscription<T> subscription() {
        return this.subscription;
    }

    public long demand() {
        return this.demand;
    }

    public <T> RequestMore<T> copy(ActorSubscription<T> subscription, long demand) {
        return new RequestMore<T>(subscription, demand);
    }

    public <T> ActorSubscription<T> copy$default$1() {
        return this.subscription();
    }

    public <T> long copy$default$2() {
        return this.demand();
    }

    @Override
    public String productPrefix() {
        return "RequestMore";
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
                return this.subscription();
            }
            case 1: {
                return BoxesRunTime.boxToLong(this.demand());
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
        return x$1 instanceof RequestMore;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "subscription";
            }
            case 1: {
                return "demand";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.subscription()));
        n = Statics.mix(n, Statics.longHash(this.demand()));
        return Statics.finalizeHash(n, 2);
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
        if (!(object instanceof RequestMore)) return false;
        boolean bl = true;
        if (!bl) return false;
        RequestMore requestMore = (RequestMore)x$1;
        if (this.demand() != requestMore.demand()) return false;
        ActorSubscription<T> actorSubscription = this.subscription();
        ActorSubscription<T> actorSubscription2 = requestMore.subscription();
        if (actorSubscription != null) {
            if (!actorSubscription.equals(actorSubscription2)) return false;
            return true;
        }
        if (actorSubscription2 == null) return true;
        return false;
    }

    public RequestMore(ActorSubscription<T> subscription, long demand) {
        this.subscription = subscription;
        this.demand = demand;
        Product.$init$(this);
    }
}

