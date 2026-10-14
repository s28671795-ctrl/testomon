/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.Reduce$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.util.NoSuchElementException;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005}3Qa\u0002\u0005\u0003\u001dAA\u0001b\r\u0001\u0003\u0006\u0004%\t\u0001\u000e\u0005\tq\u0001\u0011\t\u0011)A\u0005k!)\u0011\b\u0001C\u0001u!)a\b\u0001C!\u007f!)A\t\u0001C!\u000b\")a\n\u0001C!\u001f\n1!+\u001a3vG\u0016T!!\u0003\u0006\u0002\r\u0019,8/\u001b8h\u0015\tYA\"\u0001\u0003j[Bd'BA\u0007\u000f\u0003\u0019\u0019HO]3b[*\tq\"\u0001\u0003bW.\fWCA\t('\t\u0001!\u0003E\u0002\u0014E\u0015r!\u0001\u0006\u0011\u000f\u0005UybB\u0001\f\u001f\u001d\t9RD\u0004\u0002\u001995\t\u0011D\u0003\u0002\u001b7\u00051AH]8piz\u001a\u0001!C\u0001\u0010\u0013\tia\"\u0003\u0002\f\u0019%\u0011\u0011BC\u0005\u0003C!\t1b\u0012:ba\"\u001cF/Y4fg&\u00111\u0005\n\u0002\u0017'&l\u0007\u000f\\3MS:,\u0017M]$sCBD7\u000b^1hK*\u0011\u0011\u0005\u0003\t\u0003M\u001db\u0001\u0001B\u0003)\u0001\t\u0007\u0011FA\u0001U#\tQ\u0003\u0007\u0005\u0002,]5\tAFC\u0001.\u0003\u0015\u00198-\u00197b\u0013\tyCFA\u0004O_RD\u0017N\\4\u0011\u0005-\n\u0014B\u0001\u001a-\u0005\r\te._\u0001\u0002MV\tQ\u0007E\u0003,m\u0015*S%\u0003\u00028Y\tIa)\u001e8di&|gNM\u0001\u0003M\u0002\na\u0001P5oSRtDCA\u001e>!\ra\u0004!J\u0007\u0002\u0011!)1g\u0001a\u0001k\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003\u0001\u0003\"!\u0011\"\u000e\u00031I!a\u0011\u0007\u0003\u0015\u0005#HO]5ckR,7/A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u0001$M!\t9%*D\u0001I\u0015\tIE\"A\u0003ti\u0006<W-\u0003\u0002L\u0011\nyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003N\u000b\u0001\u0007\u0001)A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\u0005\u0001\u0006CA)W\u001b\u0005\u0011&BA*U\u0003\u0011a\u0017M\\4\u000b\u0003U\u000bAA[1wC&\u0011qK\u0015\u0002\u0007'R\u0014\u0018N\\4)\u0005\u0001I\u0006C\u0001.^\u001b\u0005Y&B\u0001/\u000f\u0003)\tgN\\8uCRLwN\\\u0005\u0003=n\u00131\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class Reduce<T>
extends GraphStages.SimpleLinearGraphStage<T> {
    private final Function2<T, T, T> f;

    public Function2<T, T, T> f() {
        return this.f;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.reduce().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private T akka$stream$impl$fusing$Reduce$$anon$$aggregator;
            private final T empty;
            private final /* synthetic */ Reduce $outer;
            private final Attributes inheritedAttributes$18;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public String toString() {
                return new StringBuilder(25).append("Reduce.Logic(aggregator=").append(this.aggregator()).append(")").toString();
            }

            private T aggregator() {
                return this.akka$stream$impl$fusing$Reduce$$anon$$aggregator;
            }

            public void akka$stream$impl$fusing$Reduce$$anon$$aggregator_$eq(T x$1) {
                this.akka$stream$impl$fusing$Reduce$$anon$$aggregator = x$1;
            }

            private T empty() {
                return this.empty;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$18.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
            }

            private void setInitialInHandler() {
                this.setHandler(this.$outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$41 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$impl$fusing$Reduce$$anon$$aggregator_$eq(this.$outer.grab(this.$outer.akka$stream$impl$fusing$Reduce$$anon$$$outer().in()));
                        this.$outer.pull(this.$outer.akka$stream$impl$fusing$Reduce$$anon$$$outer().in());
                        this.$outer.setHandler(this.$outer.akka$stream$impl$fusing$Reduce$$anon$$$outer().in(), this.$outer);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.failStage(new NoSuchElementException("reduce over empty stream"));
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
            }

            public void onPush() {
                block10: {
                    T elem = this.grab(this.$outer.in());
                    try {
                        try {
                            this.akka$stream$impl$fusing$Reduce$$anon$$aggregator_$eq(this.$outer.f().apply(this.aggregator(), elem));
                        }
                        catch (Throwable throwable) {
                            Option<Throwable> option;
                            Throwable throwable2 = throwable;
                            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                Throwable ex = option.get();
                                Supervision.Directive directive = this.decider().apply(ex);
                                if (Supervision$Stop$.MODULE$.equals(directive)) {
                                    this.failStage(ex);
                                } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                                    this.akka$stream$impl$fusing$Reduce$$anon$$aggregator_$eq(this.empty());
                                    this.setInitialInHandler();
                                }
                                break block10;
                            }
                            throw throwable;
                        }
                    }
                    finally {
                        if (!this.isClosed(this.$outer.in())) {
                            this.pull(this.$outer.in());
                        }
                    }
                }
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                this.push(this.$outer.out(), this.aggregator());
                this.completeStage();
            }

            public /* synthetic */ Reduce akka$stream$impl$fusing$Reduce$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$18 = inheritedAttributes$18;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.empty = this.aggregator();
                this.setInitialInHandler();
                this.setHandler($outer.out(), this);
            }
        };
    }

    public String toString() {
        return "Reduce";
    }

    public Reduce(Function2<T, T, T> f) {
        this.f = f;
    }
}

