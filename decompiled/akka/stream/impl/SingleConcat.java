/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u00014Qa\u0003\u0007\u0003!IA\u0001\"\f\u0001\u0003\u0002\u0003\u0006IA\b\u0005\u0006]\u0001!\ta\f\u0005\bg\u0001\u0011\r\u0011\"\u00015\u0011\u0019A\u0004\u0001)A\u0005k!9\u0011\b\u0001b\u0001\n\u0003Q\u0004B\u0002 \u0001A\u0003%1\bC\u0004@\u0001\t\u0007I\u0011\t!\t\r\u0005\u0003\u0001\u0015!\u0003\u001b\u0011\u0015\u0011\u0005\u0001\"\u0011D\u0011\u0015a\u0005\u0001\"\u0011N\u00051\u0019\u0016N\\4mK\u000e{gnY1u\u0015\tia\"\u0001\u0003j[Bd'BA\b\u0011\u0003\u0019\u0019HO]3b[*\t\u0011#\u0001\u0003bW.\fWCA\n!'\t\u0001A\u0003E\u0002\u00161ii\u0011A\u0006\u0006\u0003/9\tQa\u001d;bO\u0016L!!\u0007\f\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u0003\u001c9yqR\"\u0001\b\n\u0005uq!!\u0003$m_^\u001c\u0006.\u00199f!\ty\u0002\u0005\u0004\u0001\u0005\u000b\u0005\u0002!\u0019A\u0012\u0003\u0003\u0015\u001b\u0001!\u0005\u0002%UA\u0011Q\u0005K\u0007\u0002M)\tq%A\u0003tG\u0006d\u0017-\u0003\u0002*M\t9aj\u001c;iS:<\u0007CA\u0013,\u0013\tacEA\u0002B]f\f!b]5oO2,W\t\\3n\u0003\u0019a\u0014N\\5u}Q\u0011\u0001G\r\t\u0004c\u0001qR\"\u0001\u0007\t\u000b5\u0012\u0001\u0019\u0001\u0010\u0002\u0005%tW#A\u001b\u0011\u0007m1d$\u0003\u00028\u001d\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/F\u0001<!\rYBHH\u0005\u0003{9\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012AG\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003\t\u001e\u0003\"!F#\n\u0005\u00193\"aD$sCBD7\u000b^1hK2{w-[2\t\u000b!K\u0001\u0019A%\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\u0005mQ\u0015BA&\u000f\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\ta\n\u0005\u0002P-:\u0011\u0001\u000b\u0016\t\u0003#\u001aj\u0011A\u0015\u0006\u0003'\n\na\u0001\u0010:p_Rt\u0014BA+'\u0003\u0019\u0001&/\u001a3fM&\u0011q\u000b\u0017\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005U3\u0003F\u0001\u0001[!\tYf,D\u0001]\u0015\ti\u0006#\u0001\u0006b]:|G/\u0019;j_:L!a\u0018/\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class SingleConcat<E>
extends GraphStage<FlowShape<E, E>> {
    public final E akka$stream$impl$SingleConcat$$singleElem;
    private final Inlet<E> in;
    private final Outlet<E> out;
    private final FlowShape<E, E> shape;

    public Inlet<E> in() {
        return this.in;
    }

    public Outlet<E> out() {
        return this.out;
    }

    @Override
    public FlowShape<E, E> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final /* synthetic */ SingleConcat $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onPush() {
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                this.emit(this.$outer.out(), this.$outer.akka$stream$impl$SingleConcat$$singleElem, () -> this.completeStage());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onUpstreamFinish$1(akka.stream.impl.SingleConcat$$anon$1 )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return new StringBuilder(14).append("SingleConcat(").append(this.akka$stream$impl$SingleConcat$$singleElem).append(")").toString();
    }

    public SingleConcat(E singleElem) {
        this.akka$stream$impl$SingleConcat$$singleElem = singleElem;
        this.in = Inlet$.MODULE$.apply("SingleConcat.in");
        this.out = Outlet$.MODULE$.apply("SingleConcat.out");
        this.shape = new FlowShape<E, E>(this.in(), this.out());
    }
}

