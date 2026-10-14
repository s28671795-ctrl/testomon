/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ReceiveTimeout$;
import akka.actor.Status;
import akka.io.ChannelRegistration;
import akka.io.ChannelRegistry;
import akka.io.Dns$;
import akka.io.Inet;
import akka.io.SelectionHandler$ChannelConnectable$;
import akka.io.Tcp;
import akka.io.TcpConnection;
import akka.io.TcpExt;
import akka.io.TcpOutgoingConnection$;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Resolve$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u00055d!B\n\u0015\u0001QA\u0002\"C\u000f\u0001\u0005\u0003\u0005\u000b\u0011B\u0010#\u0011!!\u0003A!A!\u0002\u0013)\u0003\u0002\u0003\u0015\u0001\u0005\u0003\u0005\u000b\u0011B\u0015\t\u0011=\u0002!\u0011!Q\u0001\nABQA\u0010\u0001\u0005\u0002}BQ!\u0012\u0001\u0005\n\u0019CQ\u0001\u0017\u0001\u0005\neCQa\u0018\u0001\u0005\u0002\u0001DQa\u001a\u0001\u0005\u0002!DQA\u001c\u0001\u0005\u0002=DQa\u001f\u0001\u0005\u0002qDq!a\u0002\u0001\t\u0013\tIa\u0002\u0005\u0002\fQA\t\u0001FA\u0007\r\u001d\u0019B\u0003#\u0001\u0015\u0003\u001fAaA\u0010\b\u0005\u0002\u0005]\u0001\"CA\r\u001d\t\u0007I\u0011AA\u000e\u0011!\t\u0019C\u0004Q\u0001\n\u0005u\u0001bBA\u001e\u001d\u0011\u0005\u0011Q\b\u0002\u0016)\u000e\u0004x*\u001e;h_&twmQ8o]\u0016\u001cG/[8o\u0015\t)b#\u0001\u0002j_*\tq#\u0001\u0003bW.\f7C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBCA\u0007UGB\u001cuN\u001c8fGRLwN\\\u0001\u0005?R\u001c\u0007o\u0001\u0001\u0011\u0005i\u0001\u0013BA\u0011\u0015\u0005\u0019!6\r]#yi&\u00111eG\u0001\u0004i\u000e\u0004\u0018aD2iC:tW\r\u001c*fO&\u001cHO]=\u0011\u0005i1\u0013BA\u0014\u0015\u0005=\u0019\u0005.\u00198oK2\u0014VmZ5tiJL\u0018!C2p[6\fg\u000eZ3s!\tQS&D\u0001,\u0015\tac#A\u0003bGR|'/\u0003\u0002/W\tA\u0011i\u0019;peJ+g-A\u0004d_:tWm\u0019;\u0011\u0005EZdB\u0001\u001a:\u001d\t\u0019\u0004H\u0004\u00025o5\tQG\u0003\u00027=\u00051AH]8pizJ\u0011aF\u0005\u0003+YI!A\u000f\u000b\u0002\u0007Q\u001b\u0007/\u0003\u0002={\t91i\u001c8oK\u000e$(B\u0001\u001e\u0015\u0003\u0019a\u0014N\\5u}Q)\u0001)\u0011\"D\tB\u0011!\u0004\u0001\u0005\u0006;\u0015\u0001\ra\b\u0005\u0006I\u0015\u0001\r!\n\u0005\u0006Q\u0015\u0001\r!\u000b\u0005\u0006_\u0015\u0001\r\u0001M\u0001\u0005gR|\u0007\u000f\u0006\u0002H\u001bB\u0011\u0001jS\u0007\u0002\u0013*\t!*A\u0003tG\u0006d\u0017-\u0003\u0002M\u0013\n!QK\\5u\u0011\u0015qe\u00011\u0001P\u0003\u0015\u0019\u0017-^:f!\t\u0001VK\u0004\u0002R':\u0011AGU\u0005\u0002\u0015&\u0011A+S\u0001\ba\u0006\u001c7.Y4f\u0013\t1vKA\u0005UQJ|w/\u00192mK*\u0011A+S\u0001\u0015e\u0016\u0004xN\u001d;D_:tWm\u0019;GC&dWO]3\u0015\u0005\u001dS\u0006BB.\b\t\u0003\u0007A,A\u0003uQVt7\u000eE\u0002I;\u001eK!AX%\u0003\u0011q\u0012\u0017P\\1nKz\nqA]3dK&4X-F\u0001b!\t\u00117-D\u0001\u0001\u0013\t!WMA\u0004SK\u000e,\u0017N^3\n\u0005\u0019\\#!B!di>\u0014\u0018!\u0003:fg>dg/\u001b8h)\t\t\u0017\u000eC\u0003k\u0013\u0001\u00071.\u0001\u0007sK\u001eL7\u000f\u001e:bi&|g\u000e\u0005\u0002\u001bY&\u0011Q\u000e\u0006\u0002\u0014\u0007\"\fgN\\3m%\u0016<\u0017n\u001d;sCRLwN\\\u0001\te\u0016<\u0017n\u001d;feR\u0019q\t\u001d>\t\u000bET\u0001\u0019\u0001:\u0002\u000f\u0005$GM]3tgB\u00111\u000f_\u0007\u0002i*\u0011QO^\u0001\u0004]\u0016$(\"A<\u0002\t)\fg/Y\u0005\u0003sR\u0014\u0011#\u00138fiN{7m[3u\u0003\u0012$'/Z:t\u0011\u0015Q'\u00021\u0001l\u0003)\u0019wN\u001c8fGRLgn\u001a\u000b\u0004Cvt\b\"\u00026\f\u0001\u0004Y\u0007BB@\f\u0001\u0004\t\t!A\u000fsK6\f\u0017N\\5oO\u001aKg.[:i\u0007>tg.Z2u%\u0016$(/[3t!\rA\u00151A\u0005\u0004\u0003\u000bI%aA%oi\u0006\t2m\u001c8oK\u000e$\u0018n\u001c8US6,w.\u001e;\u0015\u0003\u001d\u000bQ\u0003V2q\u001fV$xm\\5oO\u000e{gN\\3di&|g\u000e\u0005\u0002\u001b\u001dM\u0019a\"!\u0005\u0011\u0007!\u000b\u0019\"C\u0002\u0002\u0016%\u0013a!\u00118z%\u00164GCAA\u0007\u0003\u001d2\u0015N\\5tQ\u000e{gN\\3di:+g/\u001a:SKR,(O\\3e)J,X-\u0012=dKB$\u0018n\u001c8\u0016\u0005\u0005u!CBA\u0010\u0003K\tYC\u0002\u0004\u0002\"E\u0001\u0011Q\u0004\u0002\ryI,g-\u001b8f[\u0016tGOP\u0001)\r&t\u0017n\u001d5D_:tWm\u0019;OKZ,'OU3ukJtW\r\u001a+sk\u0016,\u0005pY3qi&|g\u000e\t\t\u0004g\u0006\u001d\u0012bAA\u0015i\n\u00012i\u001c8oK\u000e$X\t_2faRLwN\u001c\t\u0005\u0003[\t9$\u0004\u0002\u00020)!\u0011\u0011GA\u001a\u0003\u001d\u0019wN\u001c;s_2T1!!\u000eJ\u0003\u0011)H/\u001b7\n\t\u0005e\u0012q\u0006\u0002\r\u001d>\u001cF/Y2l)J\f7-Z\u0001\u0016G>tg.Z2u)&lWm\\;u\u000bb\u0004\u0018N]3e)\u0011\ty$a\u0011\u0013\r\u0005\u0005\u0013QEA\u0016\r\u0019\t\tC\u0005\u0001\u0002@!9\u0011Q\t\nA\u0002\u0005\u001d\u0013a\u0002;j[\u0016|W\u000f\u001e\t\u0006\u0011\u0006%\u0013QJ\u0005\u0004\u0003\u0017J%AB(qi&|g\u000e\u0005\u0003\u0002P\u0005eSBAA)\u0015\u0011\t\u0019&!\u0016\u0002\u0011\u0011,(/\u0019;j_:T1!a\u0016J\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u00037\n\tF\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8)\u00079\ty\u0006\u0005\u0003\u0002b\u0005\u001dTBAA2\u0015\r\t)GF\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA5\u0003G\u00121\"\u00138uKJt\u0017\r\\!qS\"\u001aQ\"a\u0018")
public class TcpOutgoingConnection
extends TcpConnection {
    public final ChannelRegistry akka$io$TcpOutgoingConnection$$channelRegistry;
    public final ActorRef akka$io$TcpOutgoingConnection$$commander;
    public final Tcp.Connect akka$io$TcpOutgoingConnection$$connect;

    public static ConnectException connectTimeoutExpired(Option<FiniteDuration> timeout2) {
        return TcpOutgoingConnection$.MODULE$.connectTimeoutExpired(timeout2);
    }

    public static ConnectException FinishConnectNeverReturnedTrueException() {
        return TcpOutgoingConnection$.MODULE$.FinishConnectNeverReturnedTrueException();
    }

    public void akka$io$TcpOutgoingConnection$$stop(Throwable cause) {
        this.stopWith(new TcpConnection.CloseInformation((Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new ActorRef[]{this.akka$io$TcpOutgoingConnection$$commander})), new Tcp.CommandFailed(this.akka$io$TcpOutgoingConnection$$connect).withCause(cause)), true);
    }

    public void akka$io$TcpOutgoingConnection$$reportConnectFailure(Function0<BoxedUnit> thunk) {
        try {
            thunk.apply$mcV$sp();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.log().debug("Could not establish connection to [{}] due to {}", this.akka$io$TcpOutgoingConnection$$connect.remoteAddress(), e);
                this.akka$io$TcpOutgoingConnection$$stop(e);
            }
            throw throwable;
        }
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpOutgoingConnection $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ChannelRegistration) {
                    ChannelRegistration channelRegistration = (ChannelRegistration)A1;
                    this.$outer.setRegistration(channelRegistration);
                    this.$outer.akka$io$TcpOutgoingConnection$$reportConnectFailure(() -> {
                        if ($this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress().isUnresolved()) {
                            $this.$outer.log().debug("Resolving {} before connecting", $this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress().getHostName());
                            Option<DnsProtocol.Resolved> option = Dns$.MODULE$.resolve(DnsProtocol$Resolve$.MODULE$.apply($this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress().getHostName()), $this.$outer.context().system(), $this.$outer.self());
                            if (None$.MODULE$.equals(option)) {
                                $this.$outer.context().become($this.$outer.resolving(channelRegistration));
                                return;
                            }
                            if (option instanceof Some) {
                                Some some = (Some)option;
                                DnsProtocol.Resolved resolved = (DnsProtocol.Resolved)some.value();
                                $this.$outer.register(new InetSocketAddress(resolved.address(), $this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress().getPort()), channelRegistration);
                                return;
                            }
                            throw new MatchError(option);
                        }
                        $this.$outer.register($this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress(), channelRegistration);
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                if (ReceiveTimeout$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpOutgoingConnection$$connectionTimeout();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof ChannelRegistration) {
                    return true;
                }
                return ReceiveTimeout$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.TcpOutgoingConnection$$anonfun$receive$1 akka.io.ChannelRegistration )}, serializedLambda);
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> resolving(ChannelRegistration registration) {
        return new Serializable(this, registration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpOutgoingConnection $outer;
            private final ChannelRegistration registration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof DnsProtocol.Resolved) {
                    DnsProtocol.Resolved resolved = (DnsProtocol.Resolved)A1;
                    this.$outer.akka$io$TcpOutgoingConnection$$reportConnectFailure(() -> $this.$outer.register(new InetSocketAddress(resolved.address(), $this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress().getPort()), $this.registration$1));
                    return (B1)BoxedUnit.UNIT;
                }
                if (ReceiveTimeout$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpOutgoingConnection$$connectionTimeout();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Status.Failure) {
                    Status.Failure failure = (Status.Failure)A1;
                    Throwable ex = failure.cause();
                    this.$outer.akka$io$TcpOutgoingConnection$$reportConnectFailure((Function0<BoxedUnit>)(Function0<Nothing$> & Serializable)() -> {
                        throw new RuntimeException(ex);
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof DnsProtocol.Resolved) {
                    return true;
                }
                if (ReceiveTimeout$.MODULE$.equals(object)) {
                    return true;
                }
                return object instanceof Status.Failure;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.registration$1 = registration$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$2(akka.io.TcpOutgoingConnection$$anonfun$resolving$1 akka.io.dns.DnsProtocol$Resolved ), $anonfun$applyOrElse$3(java.lang.Throwable )}, serializedLambda);
            }
        };
    }

    public void register(InetSocketAddress address, ChannelRegistration registration) {
        this.akka$io$TcpOutgoingConnection$$reportConnectFailure(() -> {
            this.log().debug("Attempting connection to [{}]", address);
            if (this.channel().connect(address)) {
                this.completeConnect(registration, $this.akka$io$TcpOutgoingConnection$$commander, $this.akka$io$TcpOutgoingConnection$$connect.options());
                return;
            }
            registration.enableInterest(8);
            this.context().become(this.connecting(registration, this.tcp().Settings().FinishConnectRetries()));
        });
    }

    public PartialFunction<Object, BoxedUnit> connecting(ChannelRegistration registration, int remainingFinishConnectRetries) {
        return new Serializable(this, registration, remainingFinishConnectRetries){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpOutgoingConnection $outer;
            private final ChannelRegistration registration$3;
            private final int remainingFinishConnectRetries$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (SelectionHandler$ChannelConnectable$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpOutgoingConnection$$reportConnectFailure(() -> {
                        if ($this.$outer.channel().finishConnect()) {
                            if ($this.$outer.akka$io$TcpOutgoingConnection$$connect.timeout().isDefined()) {
                                $this.$outer.context().setReceiveTimeout(Duration$.MODULE$.Undefined());
                            }
                            $this.$outer.log().debug("Connection established to [{}]", $this.$outer.akka$io$TcpOutgoingConnection$$connect.remoteAddress());
                            $this.$outer.completeConnect($this.registration$3, $this.$outer.akka$io$TcpOutgoingConnection$$commander, $this.$outer.akka$io$TcpOutgoingConnection$$connect.options());
                            return;
                        }
                        if ($this.remainingFinishConnectRetries$1 > 0) {
                            $this.$outer.context().system().scheduler().scheduleOnce(new package.DurationInt(package$.MODULE$.DurationInt(1)).millisecond(), () -> $this.$outer.akka$io$TcpOutgoingConnection$$channelRegistry.register($this.$outer.channel(), 8, $this.$outer.self()), (ExecutionContext)$this.$outer.context().dispatcher());
                            $this.$outer.context().become($this.$outer.connecting($this.registration$3, $this.remainingFinishConnectRetries$1 - 1));
                            return;
                        }
                        $this.$outer.log().debug("Could not establish connection because finishConnect never returned true (consider increasing akka.io.tcp.finish-connect-retries)");
                        $this.$outer.akka$io$TcpOutgoingConnection$$stop(TcpOutgoingConnection$.MODULE$.FinishConnectNeverReturnedTrueException());
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                if (ReceiveTimeout$.MODULE$.equals(A1)) {
                    this.$outer.akka$io$TcpOutgoingConnection$$connectionTimeout();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (SelectionHandler$ChannelConnectable$.MODULE$.equals(object)) {
                    return true;
                }
                return ReceiveTimeout$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.registration$3 = registration$3;
                this.remainingFinishConnectRetries$1 = remainingFinishConnectRetries$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$4(akka.io.TcpOutgoingConnection$$anonfun$connecting$1 ), $anonfun$applyOrElse$5(akka.io.TcpOutgoingConnection$$anonfun$connecting$1 )}, serializedLambda);
            }
        };
    }

    public void akka$io$TcpOutgoingConnection$$connectionTimeout() {
        if (this.akka$io$TcpOutgoingConnection$$connect.timeout().isDefined()) {
            this.context().setReceiveTimeout(Duration$.MODULE$.Undefined());
        }
        this.log().debug("Connect timeout expired, could not establish connection to [{}]", this.akka$io$TcpOutgoingConnection$$connect.remoteAddress());
        this.akka$io$TcpOutgoingConnection$$stop(TcpOutgoingConnection$.MODULE$.connectTimeoutExpired(this.akka$io$TcpOutgoingConnection$$connect.timeout()));
    }

    public static final /* synthetic */ void $anonfun$new$3(TcpOutgoingConnection $this, Duration timeout2) {
        $this.context().setReceiveTimeout(timeout2);
    }

    public TcpOutgoingConnection(TcpExt _tcp, ChannelRegistry channelRegistry, ActorRef commander, Tcp.Connect connect2) {
        this.akka$io$TcpOutgoingConnection$$channelRegistry = channelRegistry;
        this.akka$io$TcpOutgoingConnection$$commander = commander;
        this.akka$io$TcpOutgoingConnection$$connect = connect2;
        super(_tcp, (SocketChannel)SocketChannel.open().configureBlocking(false), connect2.pullMode());
        this.signDeathPact(commander);
        connect2.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x$1 -> {
            x$1.beforeConnect(this.channel().socket());
            return BoxedUnit.UNIT;
        });
        Socket socket = this.channel().socket();
        connect2.localAddress().foreach((Function1<SocketAddress, Object> & Serializable)x$1 -> {
            socket.bind(x$1);
            return BoxedUnit.UNIT;
        });
        channelRegistry.register(this.channel(), 0, this.self());
        connect2.timeout().foreach((Function1<Duration, Object> & Serializable)timeout2 -> {
            TcpOutgoingConnection.$anonfun$new$3(this, timeout2);
            return BoxedUnit.UNIT;
        });
    }
}

