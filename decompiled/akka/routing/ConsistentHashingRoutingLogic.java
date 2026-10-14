/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.routing.ConsistentHash;
import akka.routing.ConsistentHash$;
import akka.routing.ConsistentHashingRouter;
import akka.routing.ConsistentHashingRouter$;
import akka.routing.ConsistentHashingRouter$emptyConsistentHashMapping$;
import akka.routing.ConsistentHashingRoutingLogic$;
import akka.routing.ConsistentRoutee;
import akka.routing.NoRoutee$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import akka.serialization.Serialization;
import akka.serialization.SerializationExtension$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t%r!\u0002\u0016,\u0011\u0003\u0001d!\u0002\u001a,\u0011\u0003\u0019\u0004\"\u0002\"\u0002\t\u0003\u0019\u0005\"\u0002#\u0002\t\u0003)\u0005bB)\u0002\u0003\u0003%\tI\u0015\u0005\n\u0005\u0007\t\u0011\u0013!C\u0001\u0003CC\u0011B!\u0002\u0002#\u0003%\t!a*\t\u0013\t\u001d\u0011!!A\u0005\u0002\n%\u0001\"\u0003B\u000e\u0003E\u0005I\u0011AAQ\u0011%\u0011i\"AI\u0001\n\u0003\t9\u000bC\u0005\u0003 \u0005\t\t\u0011\"\u0003\u0003\"\u0019!!g\u000b\"U\u0011!i5B!f\u0001\n\u00031\u0007\u0002C4\f\u0005#\u0005\u000b\u0011\u0002(\t\u0011!\\!Q3A\u0005\u0002%D\u0001\"\\\u0006\u0003\u0012\u0003\u0006IA\u001b\u0005\t].\u0011)\u001a!C\u0001_\"Aqo\u0003B\tB\u0003%\u0001\u000fC\u0003C\u0017\u0011\u0005\u0001\u0010C\u0003C\u0017\u0011\u0005A\u0010\u0003\u0005\u007f\u0017!\u0015\r\u0011\"\u0003\u0000\u0011!\t\ta\u0003b\u0001\n\u0003I\u0007bBA\u0002\u0017\u0001\u0006IA\u001b\u0005\u000b\u0003\u000bY\u0001R1A\u0005\n\u0005\u001d\u0001bBA\u000b\u0017\u0011\u0005\u0011q\u0003\u0005\b\u00037YA\u0011AA\u000f\u0011%\tIc\u0003b\u0001\n\u0013\tY\u0003\u0003\u0005\u0002j-\u0001\u000b\u0011BA\u0017\u0011\u001d\tYg\u0003C!\u0003[B\u0011\"! \f\u0003\u0003%\t!a \t\u0013\u0005\u001d5\"%A\u0005\u0002\u0005%\u0005\"CAP\u0017E\u0005I\u0011AAQ\u0011%\t)kCI\u0001\n\u0003\t9\u000bC\u0005\u0002,.\t\t\u0011\"\u0011\u0002.\"A\u00111X\u0006\u0002\u0002\u0013\u0005\u0011\u000eC\u0005\u0002>.\t\t\u0011\"\u0001\u0002@\"I\u0011QY\u0006\u0002\u0002\u0013\u0005\u0013q\u0019\u0005\n\u0003#\\\u0011\u0011!C\u0001\u0003'D\u0011\"!8\f\u0003\u0003%\t%a8\t\u0013\u0005\r8\"!A\u0005B\u0005\u0015\b\"CAt\u0017\u0005\u0005I\u0011IAu\u0011%\tYoCA\u0001\n\u0003\ni/A\u000fD_:\u001c\u0018n\u001d;f]RD\u0015m\u001d5j]\u001e\u0014v.\u001e;j]\u001edunZ5d\u0015\taS&A\u0004s_V$\u0018N\\4\u000b\u00039\nA!Y6lC\u000e\u0001\u0001CA\u0019\u0002\u001b\u0005Y#!H\"p]NL7\u000f^3oi\"\u000b7\u000f[5oOJ{W\u000f^5oO2{w-[2\u0014\u0007\u0005!$\b\u0005\u00026q5\taGC\u00018\u0003\u0015\u00198-\u00197b\u0013\tIdG\u0001\u0004B]f\u0014VM\u001a\t\u0003w\u0001k\u0011\u0001\u0010\u0006\u0003{y\n!![8\u000b\u0003}\nAA[1wC&\u0011\u0011\t\u0010\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003A\na\u0002Z3gCVdG/\u00113ee\u0016\u001c8\u000f\u0006\u0002G\u0019B\u0011qIS\u0007\u0002\u0011*\u0011\u0011*L\u0001\u0006C\u000e$xN]\u0005\u0003\u0017\"\u0013q!\u00113ee\u0016\u001c8\u000fC\u0003N\u0007\u0001\u0007a*\u0001\u0004tsN$X-\u001c\t\u0003\u000f>K!\u0001\u0015%\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0006CB\u0004H.\u001f\u000b\b'\u0006u\u0018q B\u0001!\t\t4bE\u0003\fiUC6\f\u0005\u00022-&\u0011qk\u000b\u0002\r%>,H/\u001b8h\u0019><\u0017n\u0019\t\u0003keK!A\u0017\u001c\u0003\u000fA\u0013x\u000eZ;diB\u0011A\f\u001a\b\u0003;\nt!AX1\u000e\u0003}S!\u0001Y\u0018\u0002\rq\u0012xn\u001c;?\u0013\u00059\u0014BA27\u0003\u001d\u0001\u0018mY6bO\u0016L!!Q3\u000b\u0005\r4T#\u0001(\u0002\u000fML8\u000f^3nA\u0005\u0011b/\u001b:uk\u0006dgj\u001c3fg\u001a\u000b7\r^8s+\u0005Q\u0007CA\u001bl\u0013\tagGA\u0002J]R\f1C^5siV\fGNT8eKN4\u0015m\u0019;pe\u0002\n1\u0002[1tQ6\u000b\u0007\u000f]5oOV\t\u0001\u000f\u0005\u0002ri:\u0011\u0011G]\u0005\u0003g.\nqcQ8og&\u001cH/\u001a8u\u0011\u0006\u001c\b.\u001b8h%>,H/\u001a:\n\u0005U4(!F\"p]NL7\u000f^3oi\"\u000b7\u000f['baBLgn\u001a\u0006\u0003g.\nA\u0002[1tQ6\u000b\u0007\u000f]5oO\u0002\"BaU={w\")QJ\u0005a\u0001\u001d\"9\u0001N\u0005I\u0001\u0002\u0004Q\u0007b\u00028\u0013!\u0003\u0005\r\u0001\u001d\u000b\u0003'vDQ!T\nA\u00029\u000b1b]3mM\u0006#GM]3tgV\ta)\u0001\u0004w]>$Wm]\u0001\bm:|G-Z:!\u0003\rawnZ\u000b\u0003\u0003\u0013\u0001B!a\u0003\u0002\u00125\u0011\u0011Q\u0002\u0006\u0004\u0003\u001fi\u0013!B3wK:$\u0018\u0002BA\n\u0003\u001b\u0011a\u0002T8hO&tw-\u00113baR,'/\u0001\fxSRDg+\u001b:uk\u0006dgj\u001c3fg\u001a\u000b7\r^8s)\r\u0019\u0016\u0011\u0004\u0005\u0007\u0003\u0003A\u0002\u0019\u00016\u0002\u001d]LG\u000f\u001b%bg\"l\u0015\r\u001d9feR\u00191+a\b\t\u000f\u0005\u0005\u0012\u00041\u0001\u0002$\u00051Q.\u00199qKJ\u00042!]A\u0013\u0013\r\t9C\u001e\u0002\u0015\u0007>t7/[:uK:$\b*Y:i\u001b\u0006\u0004\b/\u001a:\u0002#\r|gn]5ti\u0016tG\u000fS1tQJ+g-\u0006\u0002\u0002.A1\u0011qFA\u001f\u0003\u0003j!!!\r\u000b\t\u0005M\u0012QG\u0001\u0007CR|W.[2\u000b\t\u0005]\u0012\u0011H\u0001\u000bG>t7-\u001e:sK:$(bAA\u001e}\u0005!Q\u000f^5m\u0013\u0011\ty$!\r\u0003\u001f\u0005#x.\\5d%\u00164WM]3oG\u0016\u0004r!NA\"\u0003\u000f\ni&C\u0002\u0002FY\u0012a\u0001V;qY\u0016\u0014\u0004CBA%\u0003'\n9&\u0004\u0002\u0002L)!\u0011QJA(\u0003%IW.\\;uC\ndWMC\u0002\u0002RY\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t)&a\u0013\u0003\u0015%sG-\u001a=fIN+\u0017\u000fE\u00022\u00033J1!a\u0017,\u0005\u0019\u0011v.\u001e;fKB)\u0011'a\u0018\u0002d%\u0019\u0011\u0011M\u0016\u0003\u001d\r{gn]5ti\u0016tG\u000fS1tQB\u0019\u0011'!\u001a\n\u0007\u0005\u001d4F\u0001\tD_:\u001c\u0018n\u001d;f]R\u0014v.\u001e;fK\u0006\u00112m\u001c8tSN$XM\u001c;ICND'+\u001a4!\u0003\u0019\u0019X\r\\3diR1\u0011qKA8\u0003sBq!!\u001d\u001d\u0001\u0004\t\u0019(A\u0004nKN\u001c\u0018mZ3\u0011\u0007U\n)(C\u0002\u0002xY\u00121!\u00118z\u0011\u001d\tY\b\ba\u0001\u0003\u000f\nqA]8vi\u0016,7/\u0001\u0003d_BLHcB*\u0002\u0002\u0006\r\u0015Q\u0011\u0005\b\u001bv\u0001\n\u00111\u0001O\u0011\u001dAW\u0004%AA\u0002)DqA\\\u000f\u0011\u0002\u0003\u0007\u0001/\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005-%f\u0001(\u0002\u000e.\u0012\u0011q\u0012\t\u0005\u0003#\u000bY*\u0004\u0002\u0002\u0014*!\u0011QSAL\u0003%)hn\u00195fG.,GMC\u0002\u0002\u001aZ\n!\"\u00198o_R\fG/[8o\u0013\u0011\ti*a%\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005\r&f\u00016\u0002\u000e\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCAAUU\r\u0001\u0018QR\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005=\u0006\u0003BAY\u0003ok!!a-\u000b\u0007\u0005Uf(\u0001\u0003mC:<\u0017\u0002BA]\u0003g\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003g\n\t\r\u0003\u0005\u0002D\u000e\n\t\u00111\u0001k\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011\u0011\u001a\t\u0007\u0003\u0017\fi-a\u001d\u000e\u0005\u0005=\u0013\u0002BAh\u0003\u001f\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011Q[An!\r)\u0014q[\u0005\u0004\u000334$a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003\u0007,\u0013\u0011!a\u0001\u0003g\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qVAq\u0011!\t\u0019MJA\u0001\u0002\u0004Q\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003)\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003_\u000ba!Z9vC2\u001cH\u0003BAk\u0003_D\u0011\"a1*\u0003\u0003\u0005\r!a\u001d)\u000f-\t\u00190!?\u0002|B\u0019Q'!>\n\u0007\u0005]hG\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011\u0001C\u0003N\t\u0001\u0007a\nC\u0004i\tA\u0005\t\u0019\u00016\t\u000f9$\u0001\u0013!a\u0001a\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$#'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00134\u0003\u001d)h.\u00199qYf$BAa\u0003\u0003\u0018A)QG!\u0004\u0003\u0012%\u0019!q\u0002\u001c\u0003\r=\u0003H/[8o!\u0019)$1\u0003(ka&\u0019!Q\u0003\u001c\u0003\rQ+\b\u000f\\34\u0011!\u0011IbBA\u0001\u0002\u0004\u0019\u0016a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u001a\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0012!\u0011\t\tL!\n\n\t\t\u001d\u00121\u0017\u0002\u0007\u001f\nTWm\u0019;")
public final class ConsistentHashingRoutingLogic
implements RoutingLogic,
Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private Address selfAddress;
    private LoggingAdapter log;
    private final ActorSystem system;
    private final int virtualNodesFactor;
    private final PartialFunction<Object, Object> hashMapping;
    private final int vnodes;
    private final AtomicReference<Tuple2<IndexedSeq<Routee>, ConsistentHash<ConsistentRoutee>>> consistentHashRef;
    private volatile byte bitmap$0;

    public static PartialFunction<Object, Object> $lessinit$greater$default$3() {
        return ConsistentHashingRoutingLogic$.MODULE$.$lessinit$greater$default$3();
    }

    public static int $lessinit$greater$default$2() {
        return ConsistentHashingRoutingLogic$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple3<ActorSystem, Object, PartialFunction<Object, Object>>> unapply(ConsistentHashingRoutingLogic x$0) {
        return ConsistentHashingRoutingLogic$.MODULE$.unapply(x$0);
    }

    public static PartialFunction<Object, Object> apply$default$3() {
        return ConsistentHashingRoutingLogic$.MODULE$.apply$default$3();
    }

    public static int apply$default$2() {
        return ConsistentHashingRoutingLogic$.MODULE$.apply$default$2();
    }

    public static ConsistentHashingRoutingLogic apply(ActorSystem system, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping) {
        return ConsistentHashingRoutingLogic$.MODULE$.apply(system, virtualNodesFactor, hashMapping);
    }

    public static Address defaultAddress(ActorSystem system) {
        return ConsistentHashingRoutingLogic$.MODULE$.defaultAddress(system);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorSystem system() {
        return this.system;
    }

    public int virtualNodesFactor() {
        return this.virtualNodesFactor;
    }

    public PartialFunction<Object, Object> hashMapping() {
        return this.hashMapping;
    }

    /*
     * WARNING - void declaration
     */
    private Address selfAddress$lzycompute() {
        ConsistentHashingRoutingLogic consistentHashingRoutingLogic = this;
        synchronized (consistentHashingRoutingLogic) {
            if ((byte)(this.bitmap$0 & 1) == 0) {
                void var2_2;
                Address a = ConsistentHashingRoutingLogic$.MODULE$.defaultAddress(this.system());
                if (a == null) {
                    throw new IllegalStateException("defaultAddress not available yet");
                }
                this.selfAddress = var2_2;
                this.bitmap$0 = (byte)(this.bitmap$0 | 1);
            }
        }
        return this.selfAddress;
    }

    private Address selfAddress() {
        if ((byte)(this.bitmap$0 & 1) == 0) {
            return this.selfAddress$lzycompute();
        }
        return this.selfAddress;
    }

    public int vnodes() {
        return this.vnodes;
    }

    private LoggingAdapter log$lzycompute() {
        ConsistentHashingRoutingLogic consistentHashingRoutingLogic = this;
        synchronized (consistentHashingRoutingLogic) {
            if ((byte)(this.bitmap$0 & 2) == 0) {
                this.log = Logging$.MODULE$.apply(this.system(), ConsistentHashingRoutingLogic.class, LogSource$.MODULE$.fromAnyClass());
                this.bitmap$0 = (byte)(this.bitmap$0 | 2);
            }
        }
        return this.log;
    }

    private LoggingAdapter log() {
        if ((byte)(this.bitmap$0 & 2) == 0) {
            return this.log$lzycompute();
        }
        return this.log;
    }

    public ConsistentHashingRoutingLogic withVirtualNodesFactor(int vnodes) {
        int x$1 = vnodes;
        ActorSystem x$2 = this.copy$default$1();
        PartialFunction<Object, Object> x$3 = this.copy$default$3();
        return this.copy(x$2, x$1, x$3);
    }

    public ConsistentHashingRoutingLogic withHashMapper(ConsistentHashingRouter.ConsistentHashMapper mapper2) {
        PartialFunction<Object, Object> x$1 = ConsistentHashingRouter$.MODULE$.hashMappingAdapter(mapper2);
        ActorSystem x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        return this.copy(x$2, x$3, x$1);
    }

    private AtomicReference<Tuple2<IndexedSeq<Routee>, ConsistentHash<ConsistentRoutee>>> consistentHashRef() {
        return this.consistentHashRef;
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.isEmpty()) {
            return NoRoutee$.MODULE$;
        }
        Object object = message;
        if (this.hashMapping().isDefinedAt(message)) {
            return this.target$1(this.hashMapping().apply(message), routees);
        }
        if (object instanceof ConsistentHashingRouter.ConsistentHashable) {
            ConsistentHashingRouter.ConsistentHashable consistentHashable = (ConsistentHashingRouter.ConsistentHashable)object;
            return this.target$1(consistentHashable.consistentHashKey(), routees);
        }
        this.log().warning("Message [{}] must be handled by hashMapping, or implement [{}] or be wrapped in [{}]", (Object)message.getClass().getName(), (Object)ConsistentHashingRouter.ConsistentHashable.class.getName(), (Object)ConsistentHashingRouter.ConsistentHashableEnvelope.class.getName());
        return NoRoutee$.MODULE$;
    }

    public ConsistentHashingRoutingLogic copy(ActorSystem system, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping) {
        return new ConsistentHashingRoutingLogic(system, virtualNodesFactor, hashMapping);
    }

    public ActorSystem copy$default$1() {
        return this.system();
    }

    public int copy$default$2() {
        return this.virtualNodesFactor();
    }

    public PartialFunction<Object, Object> copy$default$3() {
        return this.hashMapping();
    }

    @Override
    public String productPrefix() {
        return "ConsistentHashingRoutingLogic";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.system();
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.virtualNodesFactor());
            }
            case 2: {
                return this.hashMapping();
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
        return x$1 instanceof ConsistentHashingRoutingLogic;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "system";
            }
            case 1: {
                return "virtualNodesFactor";
            }
            case 2: {
                return "hashMapping";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.system()));
        n = Statics.mix(n, this.virtualNodesFactor());
        n = Statics.mix(n, Statics.anyHash(this.hashMapping()));
        return Statics.finalizeHash(n, 3);
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
        if (!(object instanceof ConsistentHashingRoutingLogic)) return false;
        boolean bl = true;
        if (!bl) return false;
        ConsistentHashingRoutingLogic consistentHashingRoutingLogic = (ConsistentHashingRoutingLogic)x$1;
        if (this.virtualNodesFactor() != consistentHashingRoutingLogic.virtualNodesFactor()) return false;
        ActorSystem actorSystem = this.system();
        ActorSystem actorSystem2 = consistentHashingRoutingLogic.system();
        if (actorSystem == null) {
            if (actorSystem2 != null) {
                return false;
            }
        } else if (!actorSystem.equals(actorSystem2)) return false;
        PartialFunction<Object, Object> partialFunction = this.hashMapping();
        PartialFunction<Object, Object> partialFunction2 = consistentHashingRoutingLogic.hashMapping();
        if (partialFunction == null) {
            if (partialFunction2 == null) return true;
            return false;
        } else {
            if (!partialFunction.equals(partialFunction2)) return false;
            return true;
        }
    }

    private final ConsistentHash updateConsistentHash$1(IndexedSeq routees$1) {
        Tuple2<IndexedSeq<Routee>, ConsistentHash<ConsistentRoutee>> oldConsistentHashTuple = this.consistentHashRef().get();
        Tuple2<IndexedSeq<Routee>, ConsistentHash<ConsistentRoutee>> tuple2 = oldConsistentHashTuple;
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        IndexedSeq<Routee> oldRoutees = tuple2._1();
        ConsistentHash<ConsistentRoutee> oldConsistentHash = tuple2._2();
        Tuple2<IndexedSeq<Routee>, ConsistentHash<ConsistentRoutee>> tuple22 = new Tuple2<IndexedSeq<Routee>, ConsistentHash<ConsistentRoutee>>(oldRoutees, oldConsistentHash);
        IndexedSeq<Routee> oldRoutees2 = tuple22._1();
        ConsistentHash<ConsistentRoutee> oldConsistentHash2 = tuple22._2();
        if (routees$1 != oldRoutees2) {
            IndexedSeq indexedSeq = routees$1;
            IndexedSeq<Routee> indexedSeq2 = oldRoutees2;
            ConsistentHash<ConsistentRoutee> consistentHash = !(indexedSeq != null ? !indexedSeq.equals(indexedSeq2) : indexedSeq2 != null) ? oldConsistentHash2 : ConsistentHash$.MODULE$.apply((Iterable)routees$1.map((Function1<Routee, ConsistentRoutee> & Serializable)x$2 -> new ConsistentRoutee((Routee)x$2, this.selfAddress())), this.vnodes(), ClassTag$.MODULE$.apply(ConsistentRoutee.class));
            this.consistentHashRef().compareAndSet(oldConsistentHashTuple, new Tuple2<IndexedSeq, ConsistentHash<ConsistentRoutee>>(routees$1, consistentHash));
            return consistentHash;
        }
        return oldConsistentHash2;
    }

    private final Routee target$1(Object hashData, IndexedSeq routees$1) {
        Routee routee;
        block7: {
            try {
                ConsistentHash currentConsistenHash = this.updateConsistentHash$1(routees$1);
                if (currentConsistenHash.isEmpty()) {
                    routee = NoRoutee$.MODULE$;
                    break block7;
                }
                Object object = hashData;
                if (object instanceof byte[]) {
                    byte[] byArray = (byte[])object;
                    routee = ((ConsistentRoutee)currentConsistenHash.nodeFor(byArray)).routee();
                    break block7;
                }
                if (object instanceof String) {
                    String string = (String)object;
                    routee = ((ConsistentRoutee)currentConsistenHash.nodeFor(string)).routee();
                    break block7;
                }
                if (object instanceof Object) {
                    Object object2 = object;
                    routee = ((ConsistentRoutee)currentConsistenHash.nodeFor(((Serialization)SerializationExtension$.MODULE$.apply(this.system())).serialize(object2).get())).routee();
                    break block7;
                }
                throw new IllegalArgumentException(new StringBuilder(21).append("Unexpected hashdata: ").append(object).toString());
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable e = option.get();
                    this.log().warning("Couldn't route message with consistent hash key [{}] due to [{}]", hashData, (Object)e.getMessage());
                    routee = NoRoutee$.MODULE$;
                }
                throw throwable;
            }
        }
        return routee;
    }

    public ConsistentHashingRoutingLogic(ActorSystem system, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping) {
        this.system = system;
        this.virtualNodesFactor = virtualNodesFactor;
        this.hashMapping = hashMapping;
        Product.$init$(this);
        this.vnodes = virtualNodesFactor == 0 ? system.settings().DefaultVirtualNodesFactor() : virtualNodesFactor;
        this.consistentHashRef = new AtomicReference<Tuple2<Object, Object>>(new Tuple2<Object, Object>(null, null));
    }

    public ConsistentHashingRoutingLogic(ActorSystem system) {
        this(system, 0, ConsistentHashingRouter$emptyConsistentHashMapping$.MODULE$);
    }
}

