/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Deploy$;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.LoggingAdapter;
import akka.io.Dns;
import akka.io.DnsExt;
import akka.io.PeriodicCacheCleanup;
import akka.io.SimpleDnsManager$;
import akka.io.SimpleDnsManager$CacheCleanup$;
import akka.io.dns.DnsProtocol;
import akka.routing.FromConfig$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.TimeUnit;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005-d\u0001\u0002\u000f\u001e\u0005\tB\u0001b\u000f\u0001\u0003\u0006\u0004%\t\u0001\u0010\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005{!)!\t\u0001C\u0001\u0007\"9a\t\u0001b\u0001\n\u00139\u0005BB&\u0001A\u0003%\u0001\nC\u0004M\u0001\t\u0007I\u0011B'\t\ri\u0003\u0001\u0015!\u0003O\u0011\u001dY\u0006A1A\u0005\nqCa!\u0019\u0001!\u0002\u0013i\u0006b\u00022\u0001\u0005\u0004%\ta\u0019\u0005\u0007Q\u0002\u0001\u000b\u0011\u00023\t\u000b%\u0004A\u0011I2\t\u000b)\u0004A\u0011I6\b\u000b=l\u0002\u0012\u00019\u0007\u000bqi\u0002\u0012A9\t\u000b\t{A\u0011\u0001:\b\u000bM|\u0001\u0012\u0012;\u0007\u000bY|\u0001\u0012R<\t\r\t\u0013B\u0011AA\b\u0011%\t\tBEA\u0001\n\u0003\n\u0019\u0002C\u0005\u0002&I\t\t\u0011\"\u0001\u0002(!I\u0011q\u0006\n\u0002\u0002\u0013\u0005\u0011\u0011\u0007\u0005\n\u0003{\u0011\u0012\u0011!C!\u0003\u007fA\u0011\"!\u0014\u0013\u0003\u0003%\t!a\u0014\t\u0013\u0005e##!A\u0005B\u0005m\u0003\"CA/%\u0005\u0005I\u0011IA0\u0011%\t\tGEA\u0001\n\u0013\t\u0019G\u0001\tTS6\u0004H.\u001a#og6\u000bg.Y4fe*\u0011adH\u0001\u0003S>T\u0011\u0001I\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u000b\u0001\u0019\u0013f\f\u001d\u0011\u0005\u0011:S\"A\u0013\u000b\u0003\u0019\nQa]2bY\u0006L!\u0001K\u0013\u0003\r\u0005s\u0017PU3g!\tQS&D\u0001,\u0015\tas$A\u0003bGR|'/\u0003\u0002/W\t)\u0011i\u0019;peB\u0019\u0001gM\u001b\u000e\u0003ER!AM\u0010\u0002\u0011\u0011L7\u000f]1uG\"L!\u0001N\u0019\u0003)I+\u0017/^5sKNlUm]:bO\u0016\fV/Z;f!\t\u0001d'\u0003\u00028c\tqRK\u001c2pk:$W\rZ'fgN\fw-Z)vKV,7+Z7b]RL7m\u001d\t\u0003UeJ!AO\u0016\u0003\u0019\u0005\u001bGo\u001c:M_\u001e<\u0017N\\4\u0002\u0007\u0015DH/F\u0001>!\tqt(D\u0001\u001e\u0013\t\u0001UD\u0001\u0004E]N,\u0005\u0010^\u0001\u0005Kb$\b%\u0001\u0004=S:LGO\u0010\u000b\u0003\t\u0016\u0003\"A\u0010\u0001\t\u000bm\u001a\u0001\u0019A\u001f\u0002\u0011I,7o\u001c7wKJ,\u0012\u0001\u0013\t\u0003U%K!AS\u0016\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0011B]3t_24XM\u001d\u0011\u0002\u0019\r\f7\r[3DY\u0016\fg.\u001e9\u0016\u00039\u00032\u0001J(R\u0013\t\u0001VE\u0001\u0004PaRLwN\u001c\n\u0004%R;f\u0001B*\u0001\u0001E\u0013A\u0002\u0010:fM&tW-\\3oiz\u0002\"AP+\n\u0005Yk\"a\u0001#ogB\u0011a\bW\u0005\u00033v\u0011A\u0003U3sS>$\u0017nY\"bG\",7\t\\3b]V\u0004\u0018!D2bG\",7\t\\3b]V\u0004\b%\u0001\u0007dY\u0016\fg.\u001e9US6,'/F\u0001^!\r!sJ\u0018\t\u0003U}K!\u0001Y\u0016\u0003\u0017\r\u000bgnY3mY\u0006\u0014G.Z\u0001\u000eG2,\u0017M\\;q)&lWM\u001d\u0011\u0002\u000f=dG-\u00119jgV\tA\r\u0005\u0002fM6\t\u0001!\u0003\u0002h[\t9!+Z2fSZ,\u0017\u0001C8mI\u0006\u0003\u0018n\u001d\u0011\u0002\u000fI,7-Z5wK\u0006A\u0001o\\:u'R|\u0007\u000fF\u0001m!\t!S.\u0003\u0002oK\t!QK\\5u\u0003A\u0019\u0016.\u001c9mK\u0012s7/T1oC\u001e,'\u000f\u0005\u0002?\u001fM\u0011qb\t\u000b\u0002a\u0006a1)Y2iK\u000ecW-\u00198vaB\u0011QOE\u0007\u0002\u001f\ta1)Y2iK\u000ecW-\u00198vaN!!c\t=|!\t!\u00130\u0003\u0002{K\t9\u0001K]8ek\u000e$\bc\u0001?\u0002\n9\u0019Q0!\u0002\u000f\u0007y\f\u0019!D\u0001\u0000\u0015\r\t\t!I\u0001\u0007yI|w\u000e\u001e \n\u0003\u0019J1!a\u0002&\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0003\u0002\u000e\ta1+\u001a:jC2L'0\u00192mK*\u0019\u0011qA\u0013\u0015\u0003Q\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA\u000b!\u0011\t9\"!\t\u000e\u0005\u0005e!\u0002BA\u000e\u0003;\tA\u0001\\1oO*\u0011\u0011qD\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002$\u0005e!AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002*A\u0019A%a\u000b\n\u0007\u00055REA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u00024\u0005e\u0002c\u0001\u0013\u00026%\u0019\u0011qG\u0013\u0003\u0007\u0005s\u0017\u0010C\u0005\u0002<Y\t\t\u00111\u0001\u0002*\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\u0011\u0011\r\u0005\r\u0013\u0011JA\u001a\u001b\t\t)EC\u0002\u0002H\u0015\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tY%!\u0012\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003#\n9\u0006E\u0002%\u0003'J1!!\u0016&\u0005\u001d\u0011un\u001c7fC:D\u0011\"a\u000f\u0019\u0003\u0003\u0005\r!a\r\u0002\u0011!\f7\u000f[\"pI\u0016$\"!!\u000b\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0006\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u0015\u0004\u0003BA\f\u0003OJA!!\u001b\u0002\u001a\t1qJ\u00196fGR\u0004")
public final class SimpleDnsManager
implements Actor,
RequiresMessageQueue<UnboundedMessageQueueSemantics>,
ActorLogging {
    private final DnsExt ext;
    private final ActorRef akka$io$SimpleDnsManager$$resolver;
    private final Option<Dns> akka$io$SimpleDnsManager$$cacheCleanup;
    private final Option<Cancellable> cleanupTimer;
    private final PartialFunction<Object, BoxedUnit> oldApis;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public DnsExt ext() {
        return this.ext;
    }

    public ActorRef akka$io$SimpleDnsManager$$resolver() {
        return this.akka$io$SimpleDnsManager$$resolver;
    }

    public Option<Dns> akka$io$SimpleDnsManager$$cacheCleanup() {
        return this.akka$io$SimpleDnsManager$$cacheCleanup;
    }

    private Option<Cancellable> cleanupTimer() {
        return this.cleanupTimer;
    }

    public PartialFunction<Object, BoxedUnit> oldApis() {
        return this.oldApis;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.oldApis().orElse(new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SimpleDnsManager $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof DnsProtocol.Resolve) {
                    DnsProtocol.Resolve resolve = (DnsProtocol.Resolve)A1;
                    this.$outer.log().debug("Resolution request for {} from {}", resolve.name(), this.$outer.sender());
                    this.$outer.akka$io$SimpleDnsManager$$resolver().forward(resolve, this.$outer.context());
                    return (B1)BoxedUnit.UNIT;
                }
                if (SimpleDnsManager$CacheCleanup$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$SimpleDnsManager$$cacheCleanup().foreach((Function1<Dns, Object> & Serializable)x$2 -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$1(x$2);
                        return BoxedUnit.UNIT;
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof DnsProtocol.Resolve) {
                    return true;
                }
                return SimpleDnsManager$CacheCleanup$.MODULE$.equals(object);
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$1(Dns x$2) {
                ((PeriodicCacheCleanup)((Object)x$2)).cleanup();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(akka.io.Dns )}, serializedLambda);
            }
        });
    }

    @Override
    public void postStop() {
        this.cleanupTimer().foreach((Function1<Cancellable, Object> & Serializable)x$3 -> BoxesRunTime.boxToBoolean(x$3.cancel()));
    }

    public SimpleDnsManager(DnsExt ext) {
        Option option;
        this.ext = ext;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.akka$io$SimpleDnsManager$$resolver = this.context().actorOf(FromConfig$.MODULE$.props(Props$.MODULE$.apply(ext.provider().actorClass(), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{ext.cache(), ext.Settings().ResolverConfig()})).withDeploy(Deploy$.MODULE$.local()).withDispatcher(ext.Settings().Dispatcher())), ext.Settings().Resolver());
        Dns dns = ext.cache();
        if (dns instanceof PeriodicCacheCleanup) {
            Dns dns2 = dns;
            option = new Some<Dns>(dns2);
        } else {
            option = None$.MODULE$;
        }
        this.akka$io$SimpleDnsManager$$cacheCleanup = option;
        this.cleanupTimer = this.akka$io$SimpleDnsManager$$cacheCleanup().map((Function1<Dns, Cancellable> & Serializable)x$1 -> {
            FiniteDuration interval = Duration$.MODULE$.apply(this.ext().Settings().ResolverConfig().getDuration("cache-cleanup-interval", TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
            return this.context().system().scheduler().scheduleWithFixedDelay(interval, interval, this.self(), (Object)SimpleDnsManager$CacheCleanup$.MODULE$, (ExecutionContext)this.context().dispatcher(), this.self());
        });
        this.oldApis = new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ SimpleDnsManager $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Dns.Resolve) {
                    Dns.Resolve resolve = (Dns.Resolve)A1;
                    String name = resolve.name();
                    this.$outer.log().debug("(deprecated) Resolution request for {} from {}", name, this.$outer.sender());
                    this.$outer.akka$io$SimpleDnsManager$$resolver().forward(resolve, this.$outer.context());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof Dns.Resolve;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
        Statics.releaseFence();
    }
}

