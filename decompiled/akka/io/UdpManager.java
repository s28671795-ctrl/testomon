/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Props$;
import akka.io.ChannelRegistry;
import akka.io.SelectionHandler;
import akka.io.Udp;
import akka.io.UdpExt;
import akka.io.UdpListener;
import akka.io.UdpSender;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u00152Q\u0001B\u0003\u0001\u000b%A\u0001B\u0005\u0001\u0003\u0002\u0003\u0006I\u0001\u0006\u0005\u0006/\u0001!\t\u0001\u0007\u0005\u00067\u0001!\t\u0001\b\u0002\u000b+\u0012\u0004X*\u00198bO\u0016\u0014(B\u0001\u0004\b\u0003\tIwNC\u0001\t\u0003\u0011\t7n[1\u0014\u0005\u0001Q\u0001CA\u0006\u0010\u001d\taQ\"D\u0001\u0006\u0013\tqQ!\u0001\tTK2,7\r^5p]\"\u000bg\u000e\u001a7fe&\u0011\u0001#\u0005\u0002\u0015'\u0016dWm\u0019;pe\n\u000b7/\u001a3NC:\fw-\u001a:\u000b\u00059)\u0011aA;ea\u000e\u0001\u0001C\u0001\u0007\u0016\u0013\t1RA\u0001\u0004VIB,\u0005\u0010^\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005eQ\u0002C\u0001\u0007\u0001\u0011\u0015\u0011\"\u00011\u0001\u0015\u0003\u001d\u0011XmY3jm\u0016,\u0012!\b\t\u0003=}i\u0011\u0001A\u0005\u0003A\u0005\u0012qAU3dK&4X-\u0003\u0002#G\t)\u0011i\u0019;pe*\u0011AeB\u0001\u0006C\u000e$xN\u001d")
public class UdpManager
extends SelectionHandler.SelectorBasedManager {
    public final UdpExt akka$io$UdpManager$$udp;

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.workerForCommandHandler((PartialFunction<SelectionHandler.HasFailureMessage, Function1<ChannelRegistry, Props>>)((Object)new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpManager $outer;

            public final <A1 extends SelectionHandler.HasFailureMessage, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Udp.Bind) {
                    Udp.Bind bind = (Udp.Bind)A1;
                    ActorRef commander = this.$outer.sender();
                    return (B1)(Function1<ChannelRegistry, Props> & Serializable)registry -> Props$.MODULE$.apply(UdpListener.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{$this.$outer.akka$io$UdpManager$$udp, registry, commander, bind}));
                }
                if (A1 instanceof Udp.SimpleSender) {
                    Udp.SimpleSender simpleSender = (Udp.SimpleSender)A1;
                    ActorRef commander = this.$outer.sender();
                    return (B1)(Function1<ChannelRegistry, Props> & Serializable)registry -> Props$.MODULE$.apply(UdpSender.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{$this.$outer.akka$io$UdpManager$$udp, registry, commander, simpleSender.options()}));
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(SelectionHandler.HasFailureMessage x1) {
                SelectionHandler.HasFailureMessage hasFailureMessage = x1;
                if (hasFailureMessage instanceof Udp.Bind) {
                    return true;
                }
                return hasFailureMessage instanceof Udp.SimpleSender;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.UdpManager$$anonfun$receive$1 akka.actor.ActorRef akka.io.Udp$Bind akka.io.ChannelRegistry ), $anonfun$applyOrElse$2(akka.io.UdpManager$$anonfun$receive$1 akka.actor.ActorRef akka.io.Udp$SimpleSender akka.io.ChannelRegistry )}, serializedLambda);
            }
        }));
    }

    public UdpManager(UdpExt udp) {
        this.akka$io$UdpManager$$udp = udp;
        super(udp.settings(), udp.settings().NrOfSelectors());
    }
}

