/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.RestartSettings;
import akka.stream.SinkShape;
import akka.stream.scaladsl.RestartWithBackoffLogic;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageLogic$EagerTerminateInput$;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3A\u0001C\u0005\u0007!!A!\u0006\u0001B\u0001B\u0003%1\u0006\u0003\u00057\u0001\t\u0005\t\u0015!\u00038\u0011\u0015Q\u0004\u0001\"\u0001<\u0011\u001d!\u0005A1A\u0005\u0002\u0015Ca!\u0013\u0001!\u0002\u00131\u0005\"\u0002&\u0001\t\u0003Z\u0005\"\u0002'\u0001\t\u0003j%A\u0006*fgR\f'\u000f^,ji\"\u0014\u0015mY6pM\u001a\u001c\u0016N\\6\u000b\u0005)Y\u0011\u0001C:dC2\fGm\u001d7\u000b\u00051i\u0011AB:ue\u0016\fWNC\u0001\u000f\u0003\u0011\t7n[1\u0004\u0001U\u0011\u0011CH\n\u0003\u0001I\u00012a\u0005\f\u0019\u001b\u0005!\"BA\u000b\f\u0003\u0015\u0019H/Y4f\u0013\t9BC\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u00042!\u0007\u000e\u001d\u001b\u0005Y\u0011BA\u000e\f\u0005%\u0019\u0016N\\6TQ\u0006\u0004X\r\u0005\u0002\u001e=1\u0001A!B\u0010\u0001\u0005\u0004\u0001#!\u0001+\u0012\u0005\u0005:\u0003C\u0001\u0012&\u001b\u0005\u0019#\"\u0001\u0013\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001a#a\u0002(pi\"Lgn\u001a\t\u0003E!J!!K\u0012\u0003\u0007\u0005s\u00170A\u0006tS:\\g)Y2u_JL\bc\u0001\u0012-]%\u0011Qf\t\u0002\n\rVt7\r^5p]B\u0002$a\f\u001b\u0011\tA\nDdM\u0007\u0002\u0013%\u0011!'\u0003\u0002\u0005'&t7\u000e\u0005\u0002\u001ei\u0011IQ'AA\u0001\u0002\u0003\u0015\t\u0001\t\u0002\u0004?\u0012\"\u0014a\u0004:fgR\f'\u000f^*fiRLgnZ:\u0011\u0005eA\u0014BA\u001d\f\u0005=\u0011Vm\u001d;beR\u001cV\r\u001e;j]\u001e\u001c\u0018A\u0002\u001fj]&$h\bF\u0002={\r\u00032\u0001\r\u0001\u001d\u0011\u0015Q3\u00011\u0001?!\r\u0011Cf\u0010\u0019\u0003\u0001\n\u0003B\u0001M\u0019\u001d\u0003B\u0011QD\u0011\u0003\nku\n\t\u0011!A\u0003\u0002\u0001BQAN\u0002A\u0002]\n!!\u001b8\u0016\u0003\u0019\u00032!G$\u001d\u0013\tA5BA\u0003J]2,G/A\u0002j]\u0002\nQa\u001d5ba\u0016,\u0012\u0001G\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002O#B\u0019\u0001g\u0014\r\n\u0005AK!a\u0006*fgR\f'\u000f^,ji\"\u0014\u0015mY6pM\u001adunZ5d\u0011\u0015\u0011v\u00011\u0001T\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t!\tIB+\u0003\u0002V\u0017\tQ\u0011\t\u001e;sS\n,H/Z:")
public final class RestartWithBackoffSink<T>
extends GraphStage<SinkShape<T>> {
    public final Function0<Sink<T, ?>> akka$stream$scaladsl$RestartWithBackoffSink$$sinkFactory;
    public final RestartSettings akka$stream$scaladsl$RestartWithBackoffSink$$restartSettings;
    private final Inlet<T> in;

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public SinkShape<T> shape() {
        return new SinkShape<T>(this.in());
    }

    @Override
    public RestartWithBackoffLogic<SinkShape<T>> createLogic(Attributes inheritedAttributes) {
        return new RestartWithBackoffLogic<SinkShape<T>>(this, inheritedAttributes){
            private final /* synthetic */ RestartWithBackoffSink $outer;
            private final Attributes inheritedAttributes$1;

            public Class<?> logSource() {
                return this.$outer.getClass();
            }

            public void startGraph() {
                GraphStageLogic.SubSourceOutlet<T> sourceOut = this.createSubOutlet(this.$outer.in());
                this.subFusingMaterializer().materialize(Source$.MODULE$.fromGraph(sourceOut.source()).to(this.$outer.akka$stream$scaladsl$RestartWithBackoffSink$$sinkFactory.apply()), this.inheritedAttributes$1);
            }

            public void backoff() {
                this.setHandler(this.$outer.in(), GraphStageLogic$EagerTerminateInput$.MODULE$);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super("Sink", $outer.shape(), inheritedAttributes$1, $outer.akka$stream$scaladsl$RestartWithBackoffSink$$restartSettings, false);
                this.backoff();
            }
        };
    }

    public RestartWithBackoffSink(Function0<Sink<T, ?>> sinkFactory, RestartSettings restartSettings) {
        this.akka$stream$scaladsl$RestartWithBackoffSink$$sinkFactory = sinkFactory;
        this.akka$stream$scaladsl$RestartWithBackoffSink$$restartSettings = restartSettings;
        this.in = Inlet$.MODULE$.apply("RestartWithBackoffSink.in");
    }
}

