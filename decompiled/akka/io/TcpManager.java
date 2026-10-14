/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Props$;
import akka.event.LoggingAdapter;
import akka.io.ChannelRegistry;
import akka.io.SelectionHandler;
import akka.io.Tcp;
import akka.io.TcpExt;
import akka.io.TcpListener;
import akka.io.TcpOutgoingConnection;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005%2Q\u0001B\u0003\u0001\u000b%A\u0001\u0002\u0007\u0001\u0003\u0002\u0003\u0006IA\u0007\u0005\u0006;\u0001!\tA\b\u0005\u0006C\u0001!\tA\t\u0002\u000b)\u000e\u0004X*\u00198bO\u0016\u0014(B\u0001\u0004\b\u0003\tIwNC\u0001\t\u0003\u0011\t7n[1\u0014\u0007\u0001Q!\u0003\u0005\u0002\f\u001f9\u0011A\"D\u0007\u0002\u000b%\u0011a\"B\u0001\u0011'\u0016dWm\u0019;j_:D\u0015M\u001c3mKJL!\u0001E\t\u0003)M+G.Z2u_J\u0014\u0015m]3e\u001b\u0006t\u0017mZ3s\u0015\tqQ\u0001\u0005\u0002\u0014-5\tAC\u0003\u0002\u0016\u000f\u0005)\u0011m\u0019;pe&\u0011q\u0003\u0006\u0002\r\u0003\u000e$xN\u001d'pO\u001eLgnZ\u0001\u0004i\u000e\u00048\u0001\u0001\t\u0003\u0019mI!\u0001H\u0003\u0003\rQ\u001b\u0007/\u0012=u\u0003\u0019a\u0014N\\5u}Q\u0011q\u0004\t\t\u0003\u0019\u0001AQ\u0001\u0007\u0002A\u0002i\tqA]3dK&4X-F\u0001$!\t!S%D\u0001\u0001\u0013\t1sEA\u0004SK\u000e,\u0017N^3\n\u0005!\"\"!B!di>\u0014\b")
public class TcpManager
extends SelectionHandler.SelectorBasedManager
implements ActorLogging {
    public final TcpExt akka$io$TcpManager$$tcp;
    private LoggingAdapter akka$actor$ActorLogging$$_log;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
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
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.workerForCommandHandler((PartialFunction<SelectionHandler.HasFailureMessage, Function1<ChannelRegistry, Props>>)((Object)new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TcpManager $outer;

            public final <A1 extends SelectionHandler.HasFailureMessage, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Tcp.Connect) {
                    Tcp.Connect connect2 = (Tcp.Connect)A1;
                    ActorRef commander = this.$outer.sender();
                    return (B1)(Function1<ChannelRegistry, Props> & Serializable)registry -> Props$.MODULE$.apply(TcpOutgoingConnection.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{$this.$outer.akka$io$TcpManager$$tcp, registry, commander, connect2}));
                }
                if (A1 instanceof Tcp.Bind) {
                    Tcp.Bind bind = (Tcp.Bind)A1;
                    ActorRef commander = this.$outer.sender();
                    return (B1)(Function1<ChannelRegistry, Props> & Serializable)registry -> Props$.MODULE$.apply(TcpListener.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{$this.$outer.selectorPool(), $this.$outer.akka$io$TcpManager$$tcp, registry, commander, bind}));
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(SelectionHandler.HasFailureMessage x1) {
                SelectionHandler.HasFailureMessage hasFailureMessage = x1;
                if (hasFailureMessage instanceof Tcp.Connect) {
                    return true;
                }
                return hasFailureMessage instanceof Tcp.Bind;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.TcpManager$$anonfun$receive$1 akka.actor.ActorRef akka.io.Tcp$Connect akka.io.ChannelRegistry ), $anonfun$applyOrElse$2(akka.io.TcpManager$$anonfun$receive$1 akka.actor.ActorRef akka.io.Tcp$Bind akka.io.ChannelRegistry )}, serializedLambda);
            }
        }));
    }

    public TcpManager(TcpExt tcp) {
        this.akka$io$TcpManager$$tcp = tcp;
        super(tcp.Settings(), tcp.Settings().NrOfSelectors());
        ActorLogging.$init$(this);
    }
}

