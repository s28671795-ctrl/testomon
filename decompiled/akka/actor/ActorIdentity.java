/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorIdentity$;
import akka.actor.ActorRef;
import java.io.Serializable;
import java.util.Optional;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=d\u0001\u0002\u000e\u001c\u0005\u0002B\u0001B\u000e\u0001\u0003\u0016\u0004%\ta\u000e\u0005\tw\u0001\u0011\t\u0012)A\u0005q!AA\b\u0001BK\u0002\u0013\u0005Q\b\u0003\u0005F\u0001\tE\t\u0015!\u0003?\u0011\u00151\u0005\u0001\"\u0001H\u0011\u0015Y\u0005\u0001\"\u0001M\u0011\u001d)\u0006!!A\u0005\u0002YCq!\u0017\u0001\u0012\u0002\u0013\u0005!\fC\u0004f\u0001E\u0005I\u0011\u00014\t\u000f!\u0004\u0011\u0011!C!S\"9\u0001\u000fAA\u0001\n\u0003\t\bbB;\u0001\u0003\u0003%\tA\u001e\u0005\bs\u0002\t\t\u0011\"\u0011{\u0011%\t\u0019\u0001AA\u0001\n\u0003\t)\u0001C\u0005\u0002\u0010\u0001\t\t\u0011\"\u0011\u0002\u0012!I\u0011Q\u0003\u0001\u0002\u0002\u0013\u0005\u0013q\u0003\u0005\n\u00033\u0001\u0011\u0011!C!\u00037A\u0011\"!\b\u0001\u0003\u0003%\t%a\b\b\u0013\u0005=2$!A\t\u0002\u0005Eb\u0001\u0003\u000e\u001c\u0003\u0003E\t!a\r\t\r\u0019#B\u0011AA&\u0011%\tI\u0002FA\u0001\n\u000b\nY\u0002C\u0005\u0002NQ\t\t\u0011\"!\u0002P!I\u0011Q\u000b\u000b\u0002\u0002\u0013\u0005\u0015q\u000b\u0005\n\u0003K\"\u0012\u0011!C\u0005\u0003O\u0012Q\"Q2u_JLE-\u001a8uSRL(B\u0001\u000f\u001e\u0003\u0015\t7\r^8s\u0015\u0005q\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001C\u001dR\u0003C\u0001\u0012&\u001b\u0005\u0019#\"\u0001\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001a#AB!osJ+g\r\u0005\u0002#Q%\u0011\u0011f\t\u0002\b!J|G-^2u!\tY3G\u0004\u0002-c9\u0011Q\u0006M\u0007\u0002])\u0011qfH\u0001\u0007yI|w\u000e\u001e \n\u0003\u0011J!AM\u0012\u0002\u000fA\f7m[1hK&\u0011A'\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003e\r\nQbY8se\u0016d\u0017\r^5p]&#W#\u0001\u001d\u0011\u0005\tJ\u0014B\u0001\u001e$\u0005\r\te._\u0001\u000fG>\u0014(/\u001a7bi&|g.\u00133!\u0003\r\u0011XMZ\u000b\u0002}A\u0019!eP!\n\u0005\u0001\u001b#AB(qi&|g\u000e\u0005\u0002C\u00076\t1$\u0003\u0002E7\tA\u0011i\u0019;peJ+g-\u0001\u0003sK\u001a\u0004\u0013A\u0002\u001fj]&$h\bF\u0002I\u0013*\u0003\"A\u0011\u0001\t\u000bY*\u0001\u0019\u0001\u001d\t\u000bq*\u0001\u0019\u0001 \u0002\u0017\u001d,G/Q2u_J\u0014VMZ\u000b\u0002\u001bB\u0019ajU!\u000e\u0003=S!\u0001U)\u0002\tU$\u0018\u000e\u001c\u0006\u0002%\u0006!!.\u0019<b\u0013\t!vJ\u0001\u0005PaRLwN\\1m\u0003\u0011\u0019w\u000e]=\u0015\u0007!;\u0006\fC\u00047\u000fA\u0005\t\u0019\u0001\u001d\t\u000fq:\u0001\u0013!a\u0001}\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A.+\u0005ab6&A/\u0011\u0005y\u001bW\"A0\u000b\u0005\u0001\f\u0017!C;oG\",7m[3e\u0015\t\u00117%\u0001\u0006b]:|G/\u0019;j_:L!\u0001Z0\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0003\u001dT#A\u0010/\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005Q\u0007CA6o\u001b\u0005a'BA7R\u0003\u0011a\u0017M\\4\n\u0005=d'AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001s!\t\u00113/\u0003\u0002uG\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0001h\u001e\u0005\bq2\t\t\u00111\u0001s\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\t1\u0010E\u0002}\u007fbj\u0011! \u0006\u0003}\u000e\n!bY8mY\u0016\u001cG/[8o\u0013\r\t\t! \u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002\b\u00055\u0001c\u0001\u0012\u0002\n%\u0019\u00111B\u0012\u0003\u000f\t{w\u000e\\3b]\"9\u0001PDA\u0001\u0002\u0004A\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2A[A\n\u0011\u001dAx\"!AA\u0002I\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002e\u0006AAo\\*ue&tw\rF\u0001k\u0003\u0019)\u0017/^1mgR!\u0011qAA\u0011\u0011\u001dA(#!AA\u0002aBs\u0001AA\u0013\u0003W\ti\u0003E\u0002#\u0003OI1!!\u000b$\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u00035\t5\r^8s\u0013\u0012,g\u000e^5usB\u0011!\tF\n\u0006)\u0005U\u0012\u0011\t\t\b\u0003o\ti\u0004\u000f I\u001b\t\tIDC\u0002\u0002<\r\nqA];oi&lW-\u0003\u0003\u0002@\u0005e\"!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA!\u00111IA%\u001b\t\t)EC\u0002\u0002HE\u000b!![8\n\u0007Q\n)\u0005\u0006\u0002\u00022\u0005)\u0011\r\u001d9msR)\u0001*!\u0015\u0002T!)ag\u0006a\u0001q!)Ah\u0006a\u0001}\u00059QO\\1qa2LH\u0003BA-\u0003C\u0002BAI \u0002\\A)!%!\u00189}%\u0019\u0011qL\u0012\u0003\rQ+\b\u000f\\33\u0011!\t\u0019\u0007GA\u0001\u0002\u0004A\u0015a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u000e\t\u0004W\u0006-\u0014bAA7Y\n1qJ\u00196fGR\u0004")
public final class ActorIdentity
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Object correlationId;
    private final Option<ActorRef> ref;

    public static Option<Tuple2<Object, Option<ActorRef>>> unapply(ActorIdentity x$0) {
        return ActorIdentity$.MODULE$.unapply(x$0);
    }

    public static ActorIdentity apply(Object correlationId, Option<ActorRef> ref) {
        return ActorIdentity$.MODULE$.apply(correlationId, ref);
    }

    public static Function1<Tuple2<Object, Option<ActorRef>>, ActorIdentity> tupled() {
        return ActorIdentity$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<ActorRef>, ActorIdentity>> curried() {
        return ActorIdentity$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Object correlationId() {
        return this.correlationId;
    }

    public Option<ActorRef> ref() {
        return this.ref;
    }

    public Optional<ActorRef> getActorRef() {
        return OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(this.ref()));
    }

    public ActorIdentity copy(Object correlationId, Option<ActorRef> ref) {
        return new ActorIdentity(correlationId, ref);
    }

    public Object copy$default$1() {
        return this.correlationId();
    }

    public Option<ActorRef> copy$default$2() {
        return this.ref();
    }

    @Override
    public String productPrefix() {
        return "ActorIdentity";
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
                return this.correlationId();
            }
            case 1: {
                return this.ref();
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
        return x$1 instanceof ActorIdentity;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "correlationId";
            }
            case 1: {
                return "ref";
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
        if (!(object instanceof ActorIdentity)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorIdentity actorIdentity = (ActorIdentity)x$1;
        if (!BoxesRunTime.equals(this.correlationId(), actorIdentity.correlationId())) return false;
        Option<ActorRef> option = this.ref();
        Option<ActorRef> option2 = actorIdentity.ref();
        if (option != null) {
            if (!option.equals(option2)) return false;
            return true;
        }
        if (option2 == null) return true;
        return false;
    }

    public ActorIdentity(Object correlationId, Option<ActorRef> ref) {
        this.correlationId = correlationId;
        this.ref = ref;
        Product.$init$(this);
        if (ref.isDefined() && ref.get() == null) {
            throw new IllegalArgumentException("ActorIdentity created with ref = Some(null) is not allowed, this could happen when serializing with Scala 2.12 and deserializing with Scala 2.11 which is not supported.");
        }
    }
}

