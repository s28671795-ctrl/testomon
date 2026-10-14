/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.Done$;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.event.LoggingAdapter;
import akka.io.Inet;
import akka.io.Tcp;
import akka.io.Tcp$Unbind$;
import akka.io.Tcp$Unbound$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.BindFailedException;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.impl.io.ConnectionSourceStage$;
import akka.stream.impl.io.IncomingConnectionStage;
import akka.stream.impl.io.TcpIdleTimeout$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.Tcp;
import akka.stream.scaladsl.Tcp$ServerBinding$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.stream.stage.TimerGraphStageLogic;
import akka.util.ByteString;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import scala.Function0;
import scala.Function1;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Iterable;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\tea!\u0002\u0010 \u0001\r:\u0003\u0002C$\u0001\u0005\u000b\u0007I\u0011A%\t\u0011A\u0003!\u0011!Q\u0001\n)C\u0001\"\u0015\u0001\u0003\u0006\u0004%\tA\u0015\u0005\t7\u0002\u0011\t\u0011)A\u0005'\"AA\f\u0001BC\u0002\u0013\u0005Q\f\u0003\u0005c\u0001\t\u0005\t\u0015!\u0003_\u0011!\u0019\u0007A!b\u0001\n\u0003!\u0007\u0002\u0003?\u0001\u0005\u0003\u0005\u000b\u0011B3\t\u0011u\u0004!Q1A\u0005\u0002yD\u0011\"!\u0002\u0001\u0005\u0003\u0005\u000b\u0011B@\t\u0015\u0005\u001d\u0001A!b\u0001\n\u0003\tI\u0001\u0003\u0006\u0002\u0018\u0001\u0011\t\u0011)A\u0005\u0003\u0017A!\"!\u0007\u0001\u0005\u000b\u0007I\u0011AA\u000e\u0011)\t\u0019\u0003\u0001B\u0001B\u0003%\u0011Q\u0004\u0005\b\u0003K\u0001A\u0011AA\u0014\u0011%\tY\u0004\u0001b\u0001\n\u0003\ti\u0004\u0003\u0005\u0002F\u0001\u0001\u000b\u0011BA \u0011\u001d\t9\u0005\u0001C!\u0003\u0013B\u0011\"!\u0015\u0001\u0005\u0004%\t!a\u0015\t\u000f\u0005U\u0003\u0001)A\u0005]!9\u0011q\u000b\u0001\u0005B\u0005e\u0003bBA,\u0001\u0011\u0005\u0013\u0011P\u0004\t\u0003k|\u0002\u0012A\u0012\u0002x\u001a9ad\bE\u0001G\u0005e\bbBA\u00131\u0011\u0005!\u0011\u0001\u0005\n\u0005\u0007A\"\u0019!C\u0001\u0005\u000bA\u0001Ba\u0005\u0019A\u0003%!q\u0001\u0005\n\u0005+A\"\u0019!C\u0001\u0005\u000bA\u0001Ba\u0006\u0019A\u0003%!q\u0001\u0002\u0016\u0007>tg.Z2uS>t7k\\;sG\u0016\u001cF/Y4f\u0015\t\u0001\u0013%\u0001\u0002j_*\u0011!eI\u0001\u0005S6\u0004HN\u0003\u0002%K\u000511\u000f\u001e:fC6T\u0011AJ\u0001\u0005C.\\\u0017m\u0005\u0002\u0001QA!\u0011\u0006\f\u0018=\u001b\u0005Q#BA\u0016$\u0003\u0015\u0019H/Y4f\u0013\ti#FA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u00042a\f\u00193\u001b\u0005\u0019\u0013BA\u0019$\u0005-\u0019v.\u001e:dKNC\u0017\r]3\u0011\u0005MJdB\u0001\u001b8\u001b\u0005)$B\u0001\u001c$\u0003!\u00198-\u00197bINd\u0017B\u0001\u001d6\u0003\r!6\r]\u0005\u0003um\u0012!#\u00138d_6LgnZ\"p]:,7\r^5p]*\u0011\u0001(\u000e\t\u0004{\t#U\"\u0001 \u000b\u0005}\u0002\u0015AC2p]\u000e,(O]3oi*\t\u0011)A\u0003tG\u0006d\u0017-\u0003\u0002D}\t1a)\u001e;ve\u0016\u0004\"aM#\n\u0005\u0019[$!D*feZ,'OQ5oI&tw-\u0001\u0006uGBl\u0015M\\1hKJ\u001c\u0001!F\u0001K!\tYe*D\u0001M\u0015\tiU%A\u0003bGR|'/\u0003\u0002P\u0019\nA\u0011i\u0019;peJ+g-A\u0006uGBl\u0015M\\1hKJ\u0004\u0013\u0001C3oIB|\u0017N\u001c;\u0016\u0003M\u0003\"\u0001V-\u000e\u0003US!AV,\u0002\u00079,GOC\u0001Y\u0003\u0011Q\u0017M^1\n\u0005i+&!E%oKR\u001cvnY6fi\u0006#GM]3tg\u0006IQM\u001c3q_&tG\u000fI\u0001\bE\u0006\u001c7\u000e\\8h+\u0005q\u0006CA0a\u001b\u0005\u0001\u0015BA1A\u0005\rIe\u000e^\u0001\tE\u0006\u001c7\u000e\\8hA\u00059q\u000e\u001d;j_:\u001cX#A3\u0011\u0007\u0019\\W.D\u0001h\u0015\tA\u0017.A\u0005j[6,H/\u00192mK*\u0011!\u000eQ\u0001\u000bG>dG.Z2uS>t\u0017B\u00017h\u0005!IE/\u001a:bE2,\u0007C\u00018z\u001d\tygO\u0004\u0002qk:\u0011\u0011\u000f^\u0007\u0002e*\u00111\u000fS\u0001\u0007yI|w\u000e\u001e \n\u0003\u0019J!\u0001I\u0013\n\u0005]D\u0018\u0001B%oKRT!\u0001I\u0013\n\u0005i\\(\u0001D*pG.,Go\u00149uS>t'BA<y\u0003!y\u0007\u000f^5p]N\u0004\u0013!\u00035bY\u001a\u001cEn\\:f+\u0005y\bcA0\u0002\u0002%\u0019\u00111\u0001!\u0003\u000f\t{w\u000e\\3b]\u0006Q\u0001.\u00197g\u00072|7/\u001a\u0011\u0002\u0017%$G.\u001a+j[\u0016|W\u000f^\u000b\u0003\u0003\u0017\u0001B!!\u0004\u0002\u00145\u0011\u0011q\u0002\u0006\u0004\u0003#q\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\n\t\u0005U\u0011q\u0002\u0002\t\tV\u0014\u0018\r^5p]\u0006a\u0011\u000e\u001a7f)&lWm\\;uA\u0005\u0019\"-\u001b8e'\",H\u000fZ8x]RKW.Z8viV\u0011\u0011Q\u0004\t\u0005\u0003\u001b\ty\"\u0003\u0003\u0002\"\u0005=!A\u0004$j]&$X\rR;sCRLwN\\\u0001\u0015E&tGm\u00155vi\u0012|wO\u001c+j[\u0016|W\u000f\u001e\u0011\u0002\rqJg.\u001b;?)A\tI#!\f\u00020\u0005E\u00121GA\u001b\u0003o\tI\u0004E\u0002\u0002,\u0001i\u0011a\b\u0005\u0006\u000f>\u0001\rA\u0013\u0005\u0006#>\u0001\ra\u0015\u0005\u00069>\u0001\rA\u0018\u0005\u0006G>\u0001\r!\u001a\u0005\u0006{>\u0001\ra \u0005\b\u0003\u000fy\u0001\u0019AA\u0006\u0011\u001d\tIb\u0004a\u0001\u0003;\t1a\\;u+\t\ty\u0004\u0005\u00030\u0003\u0003\u0012\u0014bAA\"G\t1q*\u001e;mKR\fAa\\;uA\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0005\u0005-\u0003cA\u0018\u0002N%\u0019\u0011qJ\u0012\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001/\u0003\u0019\u0019\b.\u00199fA\u0005y2M]3bi\u0016dunZ5d\u0003:$W*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0015\t\u0005m\u0013Q\u000f\t\b?\u0006u\u0013\u0011MA4\u0013\r\ty\u0006\u0011\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0007%\n\u0019'C\u0002\u0002f)\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\t\u0005{\t\u000bI\u0007E\u0002\u0002l\u0015s1!!\u001c8\u001d\u0011\ty'a\u001d\u000f\u0007A\f\t(\u0003\u0002%K%\u0011ag\t\u0005\b\u0003o*\u0002\u0019AA&\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t)\u0019\tY(a7\u0002^B9q,!\u0018\u0002~\u0005\u001d$CBA@\u0003\u000f\u000biIB\u0004\u0002\u0002\u0006\r\u0005!! \u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \t\r\u0005\u0015e\u0003AA?\u0003\u0015awnZ5d!\rI\u0013\u0011R\u0005\u0004\u0003\u0017S#\u0001\u0006+j[\u0016\u0014xI]1qQN#\u0018mZ3M_\u001eL7\rE\u0002*\u0003\u001fK1!!%+\u00051\u0019F/Y4f\u0019><w-\u001b8h\u0011\u001d\t)*a \u0005\u0004%\u000bAa]3mM\"Q\u0011\u0011TA@\u0005\u0004%\t!a'\u0002K\r|gN\\3di&|gN\u00127poN\fu/Y5uS:<\u0017J\\5uS\u0006d\u0017N_1uS>tWCAAO!\u0011\ty*a+\u000e\u0005\u0005\u0005&\u0002BAR\u0003K\u000ba!\u0019;p[&\u001c'bA \u0002(*\u0019\u0011\u0011V,\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003[\u000b\tK\u0001\u0006Bi>l\u0017n\u0019'p]\u001eD1\"!-\u0002\u0000\u0001\u0007\t\u0019!C\u0001\u0013\u0006AA.[:uK:,'\u000f\u0003\u0007\u00026\u0006}\u0004\u0019!a\u0001\n\u0003\t9,\u0001\u0007mSN$XM\\3s?\u0012*\u0017\u000f\u0006\u0003\u0002:\u0006}\u0006cA0\u0002<&\u0019\u0011Q\u0018!\u0003\tUs\u0017\u000e\u001e\u0005\n\u0003\u0003\f\u0019,!AA\u0002)\u000b1\u0001\u001f\u00132\u0011)\t)-a C\u0002\u0013\u0005\u0011qY\u0001\u000ek:\u0014\u0017N\u001c3Qe>l\u0017n]3\u0016\u0005\u0005%\u0007#B\u001f\u0002L\u0006e\u0016bAAg}\t9\u0001K]8nSN,\u0007\"CAi\u0003\u007f\u0002\r\u0011\"\u0001\u007f\u00035)hNY5oIN#\u0018M\u001d;fI\"Q\u0011Q[A@\u0001\u0004%\t!a6\u0002#Ut'-\u001b8e'R\f'\u000f^3e?\u0012*\u0017\u000f\u0006\u0003\u0002:\u0006e\u0007\"CAa\u0003'\f\t\u00111\u0001\u0000\u0011\u001d\t9H\u0006a\u0001\u0003\u0017Bq!a8\u0017\u0001\u0004\t\t/\u0001\tfC\u001e,'/T1uKJL\u0017\r\u001c>feB\u0019q&a9\n\u0007\u0005\u00158E\u0001\u0007NCR,'/[1mSj,'\u000fK\u0002\u0001\u0003S\u0004B!a;\u0002r6\u0011\u0011Q\u001e\u0006\u0004\u0003_,\u0013AC1o]>$\u0018\r^5p]&!\u00111_Aw\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002+\r{gN\\3di&|gnU8ve\u000e,7\u000b^1hKB\u0019\u00111\u0006\r\u0014\u0007a\tY\u0010E\u0002`\u0003{L1!a@A\u0005\u0019\te.\u001f*fMR\u0011\u0011q_\u0001\n\u0005&tG\rV5nKJ,\"Aa\u0002\u0011\t\t%!qB\u0007\u0003\u0005\u0017Q1A!\u0004X\u0003\u0011a\u0017M\\4\n\t\tE!1\u0002\u0002\u0007'R\u0014\u0018N\\4\u0002\u0015\tKg\u000e\u001a+j[\u0016\u0014\b%A\tCS:$7\u000b[;uI><h\u000eV5nKJ\f!CQ5oINCW\u000f\u001e3po:$\u0016.\\3sA\u0001")
@InternalApi
public class ConnectionSourceStage
extends GraphStageWithMaterializedValue<SourceShape<Tcp.IncomingConnection>, Future<Tcp.ServerBinding>> {
    private final ActorRef tcpManager;
    private final InetSocketAddress endpoint;
    private final int backlog;
    private final Iterable<Inet.SocketOption> options;
    private final boolean halfClose;
    private final Duration idleTimeout;
    private final FiniteDuration bindShutdownTimeout;
    private final Outlet<Tcp.IncomingConnection> out;
    private final SourceShape<Tcp.IncomingConnection> shape;

    public static String BindShutdownTimer() {
        return ConnectionSourceStage$.MODULE$.BindShutdownTimer();
    }

    public static String BindTimer() {
        return ConnectionSourceStage$.MODULE$.BindTimer();
    }

    public ActorRef tcpManager() {
        return this.tcpManager;
    }

    public InetSocketAddress endpoint() {
        return this.endpoint;
    }

    public int backlog() {
        return this.backlog;
    }

    public Iterable<Inet.SocketOption> options() {
        return this.options;
    }

    public boolean halfClose() {
        return this.halfClose;
    }

    public Duration idleTimeout() {
        return this.idleTimeout;
    }

    public FiniteDuration bindShutdownTimeout() {
        return this.bindShutdownTimeout;
    }

    public Outlet<Tcp.IncomingConnection> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ConnectionSource");
    }

    @Override
    public SourceShape<Tcp.IncomingConnection> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Future<Tcp.ServerBinding>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        throw new UnsupportedOperationException("Not used");
    }

    @Override
    public Tuple2<TimerGraphStageLogic, Future<Tcp.ServerBinding>> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterialzer) {
        Promise bindingPromise = Promise$.MODULE$.apply();
        StageLogging logic = new StageLogging(this, bindingPromise){
            private final AtomicLong connectionFlowsAwaitingInitialization;
            private ActorRef listener;
            private final Promise<BoxedUnit> unbindPromise;
            private boolean unbindStarted;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ ConnectionSourceStage $outer;
            private final Promise bindingPromise$1;

            public Class<?> logSource() {
                return StageLogging.logSource$(this);
            }

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public ActorRef self() {
                return this.stageActor().ref();
            }

            public AtomicLong connectionFlowsAwaitingInitialization() {
                return this.connectionFlowsAwaitingInitialization;
            }

            public ActorRef listener() {
                return this.listener;
            }

            public void listener_$eq(ActorRef x$1) {
                this.listener = x$1;
            }

            public Promise<BoxedUnit> unbindPromise() {
                return this.unbindPromise;
            }

            public boolean unbindStarted() {
                return this.unbindStarted;
            }

            public void unbindStarted_$eq(boolean x$1) {
                this.unbindStarted = x$1;
            }

            public void preStart() {
                this.getStageActor((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)evt -> {
                    $anon$1.$anonfun$preStart$1(this, evt);
                    return BoxedUnit.UNIT;
                });
                this.$outer.tcpManager().$bang(new Tcp.Bind(this.self(), this.$outer.endpoint(), this.$outer.backlog(), this.$outer.options(), true), this.self());
            }

            private void receive(Tuple2<ActorRef, Object> evt) {
                ActorRef sender = evt._1();
                Object msg = evt._2();
                Object object = msg;
                if (object instanceof Tcp.Bound) {
                    Tcp.Bound bound2 = (Tcp.Bound)object;
                    InetSocketAddress localAddress = bound2.localAddress();
                    this.listener_$eq(sender);
                    this.stageActor().watch(this.listener());
                    if (this.isAvailable(this.$outer.out())) {
                        this.listener().$bang(new Tcp.ResumeAccepting(1), this.self());
                    }
                    ActorRef thisStage = this.self();
                    this.bindingPromise$1.success(Tcp$ServerBinding$.MODULE$.apply(localAddress, (Function0<Future<BoxedUnit>>)(Function0<Future> & Serializable)() -> {
                        block0: {
                            if (this.unbindPromise().isCompleted()) break block0;
                            thisStage.tell(Tcp$Unbind$.MODULE$, thisStage);
                        }
                        return this.unbindPromise().future();
                    }, this.unbindPromise().future().map((Function1<BoxedUnit, Done$> & Serializable)x$2 -> Done$.MODULE$, ExecutionContexts$.MODULE$.parasitic())));
                    return;
                }
                if (object instanceof Tcp.CommandFailed) {
                    Tcp.CommandFailed commandFailed = (Tcp.CommandFailed)object;
                    BindFailedException ex = new BindFailedException(null, commandFailed){
                        private final Tcp.CommandFailed x3$1;

                        public String getMessage() {
                            return new StringBuilder(11).append("Bind failed").append(this.x3$1.causedByString()).toString();
                        }
                        {
                            this.x3$1 = x3$1;
                        }
                    };
                    commandFailed.cause().foreach((Function1<Throwable, Throwable> & Serializable)x$1 -> ex.initCause((Throwable)x$1));
                    this.bindingPromise$1.failure(ex);
                    this.unbindPromise().tryFailure(ex);
                    this.failStage(ex);
                    return;
                }
                if (object instanceof Tcp.Connected) {
                    Tcp.Connected connected2 = (Tcp.Connected)object;
                    this.push(this.$outer.out(), this.connectionFor(connected2, sender));
                    return;
                }
                if (Tcp$Unbind$.MODULE$.equals(object)) {
                    if (!this.isClosed(this.$outer.out()) && this.listener() != null) {
                        this.akka$stream$impl$io$ConnectionSourceStage$$anon$$tryUnbind();
                        return;
                    }
                    return;
                }
                if (Tcp$Unbound$.MODULE$.equals(object)) {
                    this.unbindCompleted();
                    return;
                }
                if (object instanceof Terminated) {
                    ActorRef ref;
                    Terminated terminated = (Terminated)object;
                    ActorRef actorRef2 = ref = terminated.actor();
                    ActorRef actorRef3 = this.listener();
                    if (!(actorRef2 != null ? !((Object)actorRef2).equals(actorRef3) : actorRef3 != null)) {
                        if (this.unbindStarted()) {
                            this.unbindCompleted();
                            return;
                        }
                        IllegalStateException ex = new IllegalStateException(new StringBuilder(65).append("IO Listener actor terminated unexpectedly for remote endpoint [").append(this.$outer.endpoint().getHostString()).append(":").append(this.$outer.endpoint().getPort()).append("]").toString());
                        this.unbindPromise().tryFailure(ex);
                        this.failStage(ex);
                        return;
                    }
                }
                this.log().warning("Unexpected message to TcpStage: [{}]", object.getClass());
            }

            private Tcp.IncomingConnection connectionFor(Tcp.Connected connected2, ActorRef connection) {
                Flow<I2, O1, Mat> flow;
                this.connectionFlowsAwaitingInitialization().incrementAndGet();
                Flow<I2, O1, Mat> tcpFlow = Flow$.MODULE$.fromGraph(new IncomingConnectionStage(connection, connected2.remoteAddress(), this.$outer.halfClose(), () -> this.connectionFlowsAwaitingInitialization().decrementAndGet())).via(GraphStages$.MODULE$.detacher());
                Duration duration = this.$outer.idleTimeout();
                if (duration instanceof FiniteDuration) {
                    FiniteDuration finiteDuration = (FiniteDuration)duration;
                    flow = tcpFlow.join(TcpIdleTimeout$.MODULE$.apply(finiteDuration, new Some<InetSocketAddress>(connected2.remoteAddress())));
                } else {
                    flow = tcpFlow;
                }
                Flow<I2, O1, Mat> handler = flow;
                return new Tcp.IncomingConnection(connected2.localAddress(), connected2.remoteAddress(), (Flow<ByteString, ByteString, NotUsed>)handler);
            }

            public void akka$stream$impl$io$ConnectionSourceStage$$anon$$tryUnbind() {
                if (this.listener() != null && !this.unbindStarted()) {
                    this.unbindStarted_$eq(true);
                    this.setKeepGoing(true);
                    this.listener().$bang(Tcp$Unbind$.MODULE$, this.self());
                    return;
                }
            }

            private void unbindCompleted() {
                this.stageActor().unwatch(this.listener());
                this.unbindPromise().trySuccess(BoxedUnit.UNIT);
                if (this.connectionFlowsAwaitingInitialization().get() == 0L) {
                    this.completeStage();
                    return;
                }
                this.scheduleOnce((Object)ConnectionSourceStage$.MODULE$.BindShutdownTimer(), this.$outer.bindShutdownTimeout());
            }

            public void onTimer(Object timerKey) {
                Object object = timerKey;
                String string = ConnectionSourceStage$.MODULE$.BindShutdownTimer();
                Object object2 = object;
                if (!(string != null ? !string.equals(object2) : object2 != null)) {
                    this.completeStage();
                    return;
                }
                throw new IllegalArgumentException(new StringBuilder(18).append("Unknown timer key ").append(object).toString());
            }

            public void postStop() {
                this.unbindPromise().trySuccess(BoxedUnit.UNIT);
                this.bindingPromise$1.tryFailure(new NoSuchElementException("Binding was unbound before it was completely finished"));
            }

            public /* synthetic */ ConnectionSourceStage akka$stream$impl$io$ConnectionSourceStage$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$1 $this, Tuple2 evt) {
                $this.receive(evt);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.bindingPromise$1 = bindingPromise$1;
                super($outer.shape());
                StageLogging.$init$(this);
                this.connectionFlowsAwaitingInitialization = new AtomicLong();
                this.unbindPromise = Promise$.MODULE$.apply();
                this.unbindStarted = false;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        if (this.$outer.listener() != null) {
                            this.$outer.listener().$bang(new Tcp.ResumeAccepting(1), this.$outer.self());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        if (this.$outer.log().isDebugEnabled()) {
                            Throwable throwable = cause;
                            if (throwable instanceof SubscriptionWithCancelException.NonFailureCancellation) {
                                this.$outer.log().debug("Unbinding from {}:{} because downstream cancelled stream", this.$outer.akka$stream$impl$io$ConnectionSourceStage$$anon$$$outer().endpoint().getHostString(), BoxesRunTime.boxToInteger(this.$outer.akka$stream$impl$io$ConnectionSourceStage$$anon$$$outer().endpoint().getPort()));
                            } else {
                                this.$outer.log().debug("Unbinding from {}:{} because of downstream failure: {}", this.$outer.akka$stream$impl$io$ConnectionSourceStage$$anon$$$outer().endpoint().getHostString(), BoxesRunTime.boxToInteger(this.$outer.akka$stream$impl$io$ConnectionSourceStage$$anon$$$outer().endpoint().getPort()), throwable);
                            }
                        }
                        this.$outer.akka$stream$impl$io$ConnectionSourceStage$$anon$$tryUnbind();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                    }
                });
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$connectionFor$1(akka.stream.impl.io.ConnectionSourceStage$$anon$1 ), $anonfun$preStart$1$adapted(akka.stream.impl.io.ConnectionSourceStage$$anon$1 scala.Tuple2 ), $anonfun$receive$1(akka.stream.impl.io.ConnectionSourceStage$$anon$1 akka.actor.ActorRef ), $anonfun$receive$2(scala.runtime.BoxedUnit ), $anonfun$receive$3(akka.stream.BindFailedException java.lang.Throwable )}, serializedLambda);
            }
        };
        return new Tuple2<TimerGraphStageLogic, Future<Tcp.ServerBinding>>((TimerGraphStageLogic)((Object)logic), bindingPromise.future());
    }

    public ConnectionSourceStage(ActorRef tcpManager, InetSocketAddress endpoint, int backlog, Iterable<Inet.SocketOption> options, boolean halfClose, Duration idleTimeout, FiniteDuration bindShutdownTimeout) {
        this.tcpManager = tcpManager;
        this.endpoint = endpoint;
        this.backlog = backlog;
        this.options = options;
        this.halfClose = halfClose;
        this.idleTimeout = idleTimeout;
        this.bindShutdownTimeout = bindShutdownTimeout;
        this.out = Outlet$.MODULE$.apply("IncomingConnections.out");
        this.shape = new SourceShape<Tcp.IncomingConnection>(this.out());
    }
}

