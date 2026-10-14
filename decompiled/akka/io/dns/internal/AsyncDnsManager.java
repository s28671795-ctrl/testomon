/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.Deploy$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy;
import akka.actor.TimerScheduler;
import akka.actor.TimerSchedulerImpl;
import akka.actor.Timers;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.LoggingAdapter;
import akka.io.Dns;
import akka.io.Dns$Resolved$;
import akka.io.DnsExt;
import akka.io.DnsProvider;
import akka.io.PeriodicCacheCleanup;
import akka.io.dns.AAAARecord;
import akka.io.dns.ARecord;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Resolve$;
import akka.io.dns.DnsSettings;
import akka.io.dns.ResourceRecord;
import akka.io.dns.internal.AsyncDnsManager$;
import akka.io.dns.internal.AsyncDnsManager$CacheCleanup$;
import akka.io.dns.internal.AsyncDnsManager$GetCache$;
import akka.io.dns.internal.DnsClient;
import akka.pattern.AskableActorRef$;
import akka.pattern.package$;
import akka.routing.FromConfig$;
import akka.util.Timeout;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u001drA\u0002\u00180\u0011\u0003)tG\u0002\u0004:_!\u0005QG\u000f\u0005\u0006\u0003\u0006!\taQ\u0004\u0006\t\u0006AI)\u0012\u0004\u0006\u000f\u0006AI\t\u0013\u0005\u0006\u0003\u0012!\t\u0001\u0017\u0005\b3\u0012\t\t\u0011\"\u0011[\u0011\u001d\u0019G!!A\u0005\u0002\u0011Dq\u0001\u001b\u0003\u0002\u0002\u0013\u0005\u0011\u000eC\u0004p\t\u0005\u0005I\u0011\t9\t\u000f]$\u0011\u0011!C\u0001q\"9Q\u0010BA\u0001\n\u0003r\b\u0002C@\u0005\u0003\u0003%\t%!\u0001\t\u0013\u0005\rA!!A\u0005\n\u0005\u0015qaBA\u0007\u0003!\u0005\u0015q\u0002\u0004\b\u0003#\t\u0001\u0012QA\n\u0011\u0019\tu\u0002\"\u0001\u0002\u0016!9\u0011lDA\u0001\n\u0003R\u0006bB2\u0010\u0003\u0003%\t\u0001\u001a\u0005\tQ>\t\t\u0011\"\u0001\u0002\u0018!9qnDA\u0001\n\u0003\u0002\b\u0002C<\u0010\u0003\u0003%\t!a\u0007\t\u000fu|\u0011\u0011!C!}\"AqpDA\u0001\n\u0003\n\t\u0001C\u0005\u0002\u0004=\t\t\u0011\"\u0003\u0002\u0006\u00191\u0011h\f\u00024\u0003_A!\"a\u0017\u001a\u0005\u0003\u0005\u000b\u0011BA/\u0011)\tY'\u0007B\u0001B\u0003%\u0011Q\u000e\u0005\u000b\u0003gJ\"\u0011!Q\u0001\n\u0005U\u0004BCAE3\t\u0005\t\u0015!\u0003\u0002\f\"Q\u00111S\r\u0003\u0002\u0003\u0006I!!\u0018\t\u0015\u0005U\u0015D!A!\u0002\u0013\t9\n\u0003\u0004B3\u0011\u0005\u0011Q\u0014\u0005\u0007\u0003f!\t!!,\t\u0013\u0005e\u0016D1A\u0005\u0004\u0005m\u0006\u0002CAe3\u0001\u0006I!!0\t\u0013\u0005-\u0017D1A\u0005\u0002\u00055\u0007\u0002CAl3\u0001\u0006I!a4\t\u0013\u0005e\u0017D1A\u0005\u0004\u0005m\u0007\u0002CAu3\u0001\u0006I!!8\t\u0013\u0005-\u0018D1A\u0005\n\u00055\b\u0002CA{3\u0001\u0006I!a<\t\u0013\u0005]\u0018D1A\u0005\n\u0005e\b\u0002\u0003B\u00073\u0001\u0006I!a?\t\u000f\t=\u0011\u0004\"\u0011\u0003\u0012!9!\u0011D\r\u0005B\tm\u0011aD!ts:\u001cGI\\:NC:\fw-\u001a:\u000b\u0005A\n\u0014\u0001C5oi\u0016\u0014h.\u00197\u000b\u0005I\u001a\u0014a\u00013og*\u0011A'N\u0001\u0003S>T\u0011AN\u0001\u0005C.\\\u0017\r\u0005\u00029\u00035\tqFA\bBgft7\r\u00128t\u001b\u0006t\u0017mZ3s'\t\t1\b\u0005\u0002=\u007f5\tQHC\u0001?\u0003\u0015\u00198-\u00197b\u0013\t\u0001UH\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\tq'\u0001\u0007DC\u000eDWm\u00117fC:,\b\u000f\u0005\u0002G\t5\t\u0011A\u0001\u0007DC\u000eDWm\u00117fC:,\bo\u0005\u0003\u0005w%c\u0005C\u0001\u001fK\u0013\tYUHA\u0004Qe>$Wo\u0019;\u0011\u00055+fB\u0001(T\u001d\ty%+D\u0001Q\u0015\t\t&)\u0001\u0004=e>|GOP\u0005\u0002}%\u0011A+P\u0001\ba\u0006\u001c7.Y4f\u0013\t1vK\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002U{Q\tQ)A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u00027B\u0011A,Y\u0007\u0002;*\u0011alX\u0001\u0005Y\u0006twMC\u0001a\u0003\u0011Q\u0017M^1\n\u0005\tl&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001f!\tad-\u0003\u0002h{\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011!.\u001c\t\u0003y-L!\u0001\\\u001f\u0003\u0007\u0005s\u0017\u0010C\u0004o\u0011\u0005\u0005\t\u0019A3\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005\t\bc\u0001:vU6\t1O\u0003\u0002u{\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Y\u001c(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"!\u001f?\u0011\u0005qR\u0018BA>>\u0005\u001d\u0011un\u001c7fC:DqA\u001c\u0006\u0002\u0002\u0003\u0007!.\u0001\u0005iCND7i\u001c3f)\u0005)\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003m\u000bAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a\u0002\u0011\u0007q\u000bI!C\u0002\u0002\fu\u0013aa\u00142kK\u000e$\u0018\u0001C$fi\u000e\u000b7\r[3\u0011\u0005\u0019{!\u0001C$fi\u000e\u000b7\r[3\u0014\t=Y\u0014\n\u0014\u000b\u0003\u0003\u001f!2A[A\r\u0011\u001dq7#!AA\u0002\u0015$2!_A\u000f\u0011\u001dqW#!AA\u0002)D3!AA\u0011!\u0011\t\u0019#!\u000b\u000e\u0005\u0005\u0015\"bAA\u0014k\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005-\u0012Q\u0005\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eK\u0002\u0001\u0003C\u0019\"\"G\u001e\u00022\u0005u\u0012qJA+!\u0011\t\u0019$!\u000f\u000e\u0005\u0005U\"bAA\u001ck\u0005)\u0011m\u0019;pe&!\u00111HA\u001b\u0005\u0015\t5\r^8s!\u0019\ty$!\u0012\u0002J5\u0011\u0011\u0011\t\u0006\u0004\u0003\u0007*\u0014\u0001\u00033jgB\fGo\u00195\n\t\u0005\u001d\u0013\u0011\t\u0002\u0015%\u0016\fX/\u001b:fg6+7o]1hKF+X-^3\u0011\t\u0005}\u00121J\u0005\u0005\u0003\u001b\n\tE\u0001\u0010V]\n|WO\u001c3fI6+7o]1hKF+X-^3TK6\fg\u000e^5dgB!\u00111GA)\u0013\u0011\t\u0019&!\u000e\u0003\u0019\u0005\u001bGo\u001c:M_\u001e<\u0017N\\4\u0011\t\u0005M\u0012qK\u0005\u0005\u00033\n)D\u0001\u0004US6,'o]\u0001\u0005]\u0006lW\r\u0005\u0003\u0002`\u0005\u001dd\u0002BA1\u0003G\u0002\"aT\u001f\n\u0007\u0005\u0015T(\u0001\u0004Qe\u0016$WMZ\u0005\u0004E\u0006%$bAA3{\u000511/_:uK6\u0004B!a\r\u0002p%!\u0011\u0011OA\u001b\u0005M)\u0005\u0010^3oI\u0016$\u0017i\u0019;peNK8\u000f^3n\u00039\u0011Xm]8mm\u0016\u00148i\u001c8gS\u001e\u0004B!a\u001e\u0002\u00066\u0011\u0011\u0011\u0010\u0006\u0005\u0003w\ni(\u0001\u0004d_:4\u0017n\u001a\u0006\u0005\u0003\u007f\n\t)\u0001\u0005usB,7/\u00194f\u0015\t\t\u0019)A\u0002d_6LA!a\"\u0002z\t11i\u001c8gS\u001e\fQaY1dQ\u0016\u0004B!!$\u0002\u00106\t1'C\u0002\u0002\u0012N\u00121\u0001\u00128t\u0003)!\u0017n\u001d9bi\u000eDWM]\u0001\taJ|g/\u001b3feB!\u0011QRAM\u0013\r\tYj\r\u0002\f\t:\u001c\bK]8wS\u0012,'\u000f\u0006\b\u0002 \u0006\u0005\u00161UAS\u0003O\u000bI+a+\u0011\u0005aJ\u0002bBA.A\u0001\u0007\u0011Q\f\u0005\b\u0003W\u0002\u0003\u0019AA7\u0011\u001d\t\u0019\b\ta\u0001\u0003kBq!!#!\u0001\u0004\tY\tC\u0004\u0002\u0014\u0002\u0002\r!!\u0018\t\u000f\u0005U\u0005\u00051\u0001\u0002\u0018R!\u0011qTAX\u0011\u001d\t\t,\ta\u0001\u0003g\u000b1!\u001a=u!\u0011\ti)!.\n\u0007\u0005]6G\u0001\u0004E]N,\u0005\u0010^\u0001\u0003K\u000e,\"!!0\u0011\t\u0005}\u0016QY\u0007\u0003\u0003\u0003T1!a1>\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0003\u000f\f\tM\u0001\rFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\f1!Z2!\u0003!\u0019X\r\u001e;j]\u001e\u001cXCAAh!\u0011\t\t.a5\u000e\u0003EJ1!!62\u0005-!en]*fiRLgnZ:\u0002\u0013M,G\u000f^5oON\u0004\u0013a\u0002;j[\u0016|W\u000f^\u000b\u0003\u0003;\u0004B!a8\u0002f6\u0011\u0011\u0011\u001d\u0006\u0004\u0003G,\u0014\u0001B;uS2LA!a:\u0002b\n9A+[7f_V$\u0018\u0001\u0003;j[\u0016|W\u000f\u001e\u0011\u0002\u0011I,7o\u001c7wKJ,\"!a<\u0011\t\u0005M\u0012\u0011_\u0005\u0005\u0003g\f)D\u0001\u0005BGR|'OU3g\u0003%\u0011Xm]8mm\u0016\u0014\b%\u0001\u0007dC\u000eDWm\u00117fC:,\b/\u0006\u0002\u0002|B)A(!@\u0003\u0002%\u0019\u0011q`\u001f\u0003\r=\u0003H/[8o%\u0019\u0011\u0019!a#\u0003\b\u00191!Q\u0001\u0001\u0001\u0005\u0003\u0011A\u0002\u0010:fM&tW-\\3oiz\u0002B!!$\u0003\n%\u0019!1B\u001a\u0003)A+'/[8eS\u000e\u001c\u0015m\u00195f\u00072,\u0017M\\;q\u00035\u0019\u0017m\u00195f\u00072,\u0017M\\;qA\u0005A\u0001O]3Ti\u0006\u0014H\u000f\u0006\u0002\u0003\u0014A\u0019AH!\u0006\n\u0007\t]QH\u0001\u0003V]&$\u0018a\u0002:fG\u0016Lg/Z\u000b\u0003\u0005;\u0001BAa\b\u0003\"5\t\u0011$\u0003\u0003\u0003$\u0005e\"a\u0002*fG\u0016Lg/\u001a\u0015\u00043\u0005\u0005\u0002")
@InternalApi
public final class AsyncDnsManager
implements RequiresMessageQueue<UnboundedMessageQueueSemantics>,
ActorLogging,
Timers {
    private final Config resolverConfig;
    public final Dns akka$io$dns$internal$AsyncDnsManager$$cache;
    private final ExecutionContextExecutor ec;
    private final DnsSettings settings;
    private final Timeout timeout;
    private final ActorRef akka$io$dns$internal$AsyncDnsManager$$resolver;
    private final Option<Dns> akka$io$dns$internal$AsyncDnsManager$$cacheCleanup;
    private TimerSchedulerImpl akka$actor$Timers$$_timers;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPreRestart(Throwable reason, Option message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundReceive(PartialFunction receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    public final TimerScheduler timers() {
        return Timers.timers$(this);
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Timers.aroundPreRestart$(this, reason, message);
    }

    @Override
    public void aroundPostStop() {
        Timers.aroundPostStop$(this);
    }

    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Timers.aroundReceive$(this, receive2, msg);
    }

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
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
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
    public void postStop() throws Exception {
        Actor.postStop$(this);
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
    public TimerSchedulerImpl akka$actor$Timers$$_timers() {
        return this.akka$actor$Timers$$_timers;
    }

    @Override
    public final void akka$actor$Timers$_setter_$akka$actor$Timers$$_timers_$eq(TimerSchedulerImpl x$1) {
        this.akka$actor$Timers$$_timers = x$1;
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

    public ExecutionContextExecutor ec() {
        return this.ec;
    }

    public DnsSettings settings() {
        return this.settings;
    }

    public Timeout timeout() {
        return this.timeout;
    }

    public ActorRef akka$io$dns$internal$AsyncDnsManager$$resolver() {
        return this.akka$io$dns$internal$AsyncDnsManager$$resolver;
    }

    public Option<Dns> akka$io$dns$internal$AsyncDnsManager$$cacheCleanup() {
        return this.akka$io$dns$internal$AsyncDnsManager$$cacheCleanup;
    }

    @Override
    public void preStart() {
        this.akka$io$dns$internal$AsyncDnsManager$$cacheCleanup().foreach((Function1<Dns, Object> & Serializable)x$1 -> {
            AsyncDnsManager.$anonfun$preStart$1(this, x$1);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ AsyncDnsManager $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof DnsProtocol.Resolve) {
                    DnsProtocol.Resolve resolve = (DnsProtocol.Resolve)A1;
                    this.$outer.log().debug("Resolution request for {} {} from {}", resolve.name(), resolve.requestType(), this.$outer.sender());
                    this.$outer.akka$io$dns$internal$AsyncDnsManager$$resolver().forward(resolve, this.$outer.context());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Dns.Resolve) {
                    Dns.Resolve resolve = (Dns.Resolve)A1;
                    String name = resolve.name();
                    this.$outer.log().debug("(deprecated) Resolution request for {} from {}", name, this.$outer.sender());
                    DnsProtocol.Resolve adapted = DnsProtocol$Resolve$.MODULE$.apply(name);
                    Future<S> reply = AskableActorRef$.MODULE$.$qmark$extension(package$.MODULE$.ask(this.$outer.akka$io$dns$internal$AsyncDnsManager$$resolver()), adapted, this.$outer.timeout(), this.$outer.self()).mapTo(ClassTag$.MODULE$.apply(DnsProtocol.Resolved.class)).map((Function1<DnsProtocol.Resolved, Dns.Resolved> & Serializable)asyncResolved -> {
                        Seq ips = (Seq)asyncResolved.records().collect(new Serializable(null){
                            private static final long serialVersionUID = 0L;

                            public final <A1 extends ResourceRecord, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                                A1 A1 = x1;
                                if (A1 instanceof ARecord) {
                                    ARecord aRecord = (ARecord)A1;
                                    return (B1)aRecord.ip();
                                }
                                if (A1 instanceof AAAARecord) {
                                    AAAARecord aAAARecord = (AAAARecord)A1;
                                    return (B1)aAAARecord.ip();
                                }
                                return function1.apply(x1);
                            }

                            public final boolean isDefinedAt(ResourceRecord x1) {
                                ResourceRecord resourceRecord = x1;
                                if (resourceRecord instanceof ARecord) {
                                    return true;
                                }
                                return resourceRecord instanceof AAAARecord;
                            }
                        });
                        return Dns$Resolved$.MODULE$.apply(asyncResolved.name(), ips);
                    }, this.$outer.ec());
                    package$.MODULE$.pipe(reply, this.$outer.ec()).pipeTo(this.$outer.sender(), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (AsyncDnsManager$CacheCleanup$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$dns$internal$AsyncDnsManager$$cacheCleanup().foreach((Function1<Dns, Object> & Serializable)x$2 -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$2(x$2);
                        return BoxedUnit.UNIT;
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                if (AsyncDnsManager$GetCache$.MODULE$.equals(A1)) {
                    this.$outer.sender().$bang(this.$outer.akka$io$dns$internal$AsyncDnsManager$$cache, this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof DnsProtocol.Resolve) {
                    return true;
                }
                if (object instanceof Dns.Resolve) {
                    return true;
                }
                if (AsyncDnsManager$CacheCleanup$.MODULE$.equals(object)) {
                    return true;
                }
                return AsyncDnsManager$GetCache$.MODULE$.equals(object);
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$2(Dns x$2) {
                ((PeriodicCacheCleanup)((Object)x$2)).cleanup();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.dns.DnsProtocol$Resolved ), $anonfun$applyOrElse$2$adapted(akka.io.Dns )}, serializedLambda);
            }
        };
    }

    public static final /* synthetic */ void $anonfun$preStart$1(AsyncDnsManager $this, Dns x$1) {
        FiniteDuration interval = Duration$.MODULE$.apply($this.resolverConfig.getDuration("cache-cleanup-interval", TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
        $this.timers().startTimerWithFixedDelay((Object)AsyncDnsManager$CacheCleanup$.MODULE$, (Object)AsyncDnsManager$CacheCleanup$.MODULE$, interval);
    }

    public AsyncDnsManager(String name, ExtendedActorSystem system, Config resolverConfig, Dns cache, String dispatcher, DnsProvider provider) {
        Option option;
        this.resolverConfig = resolverConfig;
        this.akka$io$dns$internal$AsyncDnsManager$$cache = cache;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        Timers.$init$(this);
        this.ec = this.context().dispatcher();
        this.settings = new DnsSettings(system, resolverConfig);
        this.timeout = new Timeout(this.settings().ResolveTimeout());
        Props props = FromConfig$.MODULE$.props(Props$.MODULE$.apply(provider.actorClass(), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.settings(), cache, (Function2<ActorRefFactory, List, List> & Serializable)(factory2, dns) -> dns.map((Function1<InetSocketAddress, ActorRef> & Serializable)ns -> factory2.actorOf(Props$.MODULE$.apply((Function0<DnsClient> & Serializable)() -> new DnsClient((InetSocketAddress)ns), ClassTag$.MODULE$.apply(DnsClient.class))))})).withDeploy(Deploy$.MODULE$.local()).withDispatcher(dispatcher));
        this.akka$io$dns$internal$AsyncDnsManager$$resolver = this.context().actorOf(props, name);
        Dns dns2 = cache;
        if (dns2 instanceof PeriodicCacheCleanup) {
            Dns dns3 = dns2;
            option = new Some<Dns>(dns3);
        } else {
            option = None$.MODULE$;
        }
        this.akka$io$dns$internal$AsyncDnsManager$$cacheCleanup = option;
        Statics.releaseFence();
    }

    public AsyncDnsManager(DnsExt ext) {
        this(ext.Settings().Resolver(), ext.system(), ext.Settings().ResolverConfig(), ext.cache(), ext.Settings().Dispatcher(), ext.provider());
    }
}

