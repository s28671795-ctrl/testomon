/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Props$;
import akka.io.ChannelRegistry;
import akka.io.SelectionHandler;
import akka.io.UdpConnected;
import akka.io.UdpConnectedExt;
import akka.io.UdpConnection;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u00152Q\u0001B\u0003\u0001\u000b%A\u0001B\u0005\u0001\u0003\u0002\u0003\u0006I\u0001\u0006\u0005\u0006/\u0001!\t\u0001\u0007\u0005\u00067\u0001!\t\u0001\b\u0002\u0014+\u0012\u00048i\u001c8oK\u000e$X\rZ'b]\u0006<WM\u001d\u0006\u0003\r\u001d\t!![8\u000b\u0003!\tA!Y6lCN\u0011\u0001A\u0003\t\u0003\u0017=q!\u0001D\u0007\u000e\u0003\u0015I!AD\u0003\u0002!M+G.Z2uS>t\u0007*\u00198eY\u0016\u0014\u0018B\u0001\t\u0012\u0005Q\u0019V\r\\3di>\u0014()Y:fI6\u000bg.Y4fe*\u0011a\"B\u0001\bk\u0012\u00048i\u001c8o\u0007\u0001\u0001\"\u0001D\u000b\n\u0005Y)!aD+ea\u000e{gN\\3di\u0016$W\t\u001f;\u0002\rqJg.\u001b;?)\tI\"\u0004\u0005\u0002\r\u0001!)!C\u0001a\u0001)\u00059!/Z2fSZ,W#A\u000f\u0011\u0005yyR\"\u0001\u0001\n\u0005\u0001\n#a\u0002*fG\u0016Lg/Z\u0005\u0003E\r\u0012Q!Q2u_JT!\u0001J\u0004\u0002\u000b\u0005\u001cGo\u001c:")
public class UdpConnectedManager
extends SelectionHandler.SelectorBasedManager {
    public final UdpConnectedExt akka$io$UdpConnectedManager$$udpConn;

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.workerForCommandHandler((PartialFunction<SelectionHandler.HasFailureMessage, Function1<ChannelRegistry, Props>>)((Object)new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpConnectedManager $outer;

            public final <A1 extends SelectionHandler.HasFailureMessage, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof UdpConnected.Connect) {
                    UdpConnected.Connect connect2 = (UdpConnected.Connect)A1;
                    ActorRef commander = this.$outer.sender();
                    return (B1)(Function1<ChannelRegistry, Props> & Serializable)registry -> Props$.MODULE$.apply(UdpConnection.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{$this.$outer.akka$io$UdpConnectedManager$$udpConn, registry, commander, connect2}));
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(SelectionHandler.HasFailureMessage x1) {
                SelectionHandler.HasFailureMessage hasFailureMessage = x1;
                return hasFailureMessage instanceof UdpConnected.Connect;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.UdpConnectedManager$$anonfun$receive$1 akka.actor.ActorRef akka.io.UdpConnected$Connect akka.io.ChannelRegistry )}, serializedLambda);
            }
        }));
    }

    public UdpConnectedManager(UdpConnectedExt udpConn) {
        this.akka$io$UdpConnectedManager$$udpConn = udpConn;
        super(udpConn.settings(), udpConn.settings().NrOfSelectors());
    }
}

