/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape2;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipLatestWith2$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U4A!\u0005\n\u00013!A\u0011\b\u0001BC\u0002\u0013\u0005!\b\u0003\u0005?\u0001\t\u0005\t\u0015!\u0003<\u0011!y\u0004A!b\u0001\n\u0003\u0001\u0005\u0002\u0003#\u0001\u0005\u0003\u0005\u000b\u0011B!\t\u000b\u0015\u0003A\u0011\u0001$\t\u000b-\u0003A\u0011\t'\t\u000fA\u0003!\u0019!C!#\"1!\u000b\u0001Q\u0001\n\u0005BQa\u0015\u0001\u0005\u0002QCq\u0001\u0017\u0001C\u0002\u0013\u0005\u0011\f\u0003\u0004^\u0001\u0001\u0006IA\u0017\u0005\b=\u0002\u0011\r\u0011\"\u0001`\u0011\u0019\t\u0007\u0001)A\u0005A\")Q\t\u0001C\u0001E\")A\r\u0001C!K\")1\u000e\u0001C!Y\nq!,\u001b9MCR,7\u000f^,ji\"\u0014$BA\n\u0015\u0003!\u00198-\u00197bINd'BA\u000b\u0017\u0003\u0019\u0019HO]3b[*\tq#\u0001\u0003bW.\f7\u0001A\u000b\u00055\u001d\"tg\u0005\u0002\u00017A\u0019AdH\u0011\u000e\u0003uQ!A\b\u000b\u0002\u000bM$\u0018mZ3\n\u0005\u0001j\"AC$sCBD7\u000b^1hKB)!eI\u00134m5\tA#\u0003\u0002%)\tYa)\u00198J]NC\u0017\r]33!\t1s\u0005\u0004\u0001\u0005\u000b!\u0002!\u0019A\u0015\u0003\u0005\u0005\u000b\u0014C\u0001\u00161!\tYc&D\u0001-\u0015\u0005i\u0013!B:dC2\f\u0017BA\u0018-\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aK\u0019\n\u0005Ib#aA!osB\u0011a\u0005\u000e\u0003\u0006k\u0001\u0011\r!\u000b\u0002\u0003\u0003J\u0002\"AJ\u001c\u0005\u000ba\u0002!\u0019A\u0015\u0003\u0003=\u000baA_5qa\u0016\u0014X#A\u001e\u0011\u000b-bTe\r\u001c\n\u0005ub#!\u0003$v]\u000e$\u0018n\u001c83\u0003\u001dQ\u0018\u000e\u001d9fe\u0002\nQ\"Z1hKJ\u001cu.\u001c9mKR,W#A!\u0011\u0005-\u0012\u0015BA\"-\u0005\u001d\u0011un\u001c7fC:\fa\"Z1hKJ\u001cu.\u001c9mKR,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004\u000f&S\u0005#\u0002%\u0001KM2T\"\u0001\n\t\u000be*\u0001\u0019A\u001e\t\u000b}*\u0001\u0019A!\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001N!\t\u0011c*\u0003\u0002P)\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\u0005\naa\u001d5ba\u0016\u0004\u0013aA8viV\tQ\u000bE\u0002#-ZJ!a\u0016\u000b\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u00025B\u0019!eW\u0013\n\u0005q#\"!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\u0005\u0001\u0007c\u0001\u0012\\g\u0005!\u0011N\\\u0019!)\t95\rC\u0003:\u001d\u0001\u00071(A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u00014j!\tar-\u0003\u0002i;\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003k\u001f\u0001\u0007Q*A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\u0005i\u0007C\u00018t\u001b\u0005y'B\u00019r\u0003\u0011a\u0017M\\4\u000b\u0003I\fAA[1wC&\u0011Ao\u001c\u0002\u0007'R\u0014\u0018N\\4")
public class ZipLatestWith2<A1, A2, O>
extends GraphStage<FanInShape2<A1, A2, O>> {
    private final Function2<A1, A2, O> zipper;
    private final boolean eagerComplete;
    private final FanInShape2<A1, A2, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;

    public Function2<A1, A2, O> zipper() {
        return this.zipper;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipLatestWith2");
    }

    @Override
    public FanInShape2<A1, A2, O> shape() {
        return this.shape;
    }

    public Outlet<O> out() {
        return ((FanInShape)this.shape()).out();
    }

    public Inlet<A1> in0() {
        return this.in0;
    }

    public Inlet<A2> in1() {
        return this.in1;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait;
            private final ContextPropagation akka$stream$scaladsl$ZipLatestWith2$$anon$$contextPropagation;
            private final $anon$1$ZipLatestInlet<A1> inlet0;
            private final $anon$1$ZipLatestInlet<A2> inlet1;
            private boolean akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple;
            private boolean akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues;
            private final /* synthetic */ ZipLatestWith2 $outer;

            public int akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait() {
                return this.akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait;
            }

            public void akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith2$$anon$$willShutDown() {
                return this.akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait() == 0;
            }

            public ContextPropagation akka$stream$scaladsl$ZipLatestWith2$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipLatestWith2$$anon$$contextPropagation;
            }

            private $anon$1$ZipLatestInlet<A1> inlet0() {
                return this.inlet0;
            }

            private $anon$1$ZipLatestInlet<A2> inlet1() {
                return this.inlet1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple() {
                return this.akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple;
            }

            public void akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple = x$1;
            }

            public boolean akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues() {
                return this.akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues;
            }

            public void akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
            }

            public boolean akka$stream$scaladsl$ZipLatestWith2$$anon$$hasAllValues() {
                return this.inlet0().hasValue() && this.inlet1().hasValue();
            }

            public void akka$stream$scaladsl$ZipLatestWith2$$anon$$pushOutput() {
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.inlet0().value(), this.inlet1().value()));
                if (this.akka$stream$scaladsl$ZipLatestWith2$$anon$$willShutDown()) {
                    this.completeStage();
                }
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues_$eq(true);
            }

            public void akka$stream$scaladsl$ZipLatestWith2$$anon$$tryPullAllIfNeeded() {
                if (!this.hasBeenPulled(this.$outer.in0())) {
                    this.tryPull(this.$outer.in0());
                }
                if (!this.hasBeenPulled(this.$outer.in1())) {
                    this.tryPull(this.$outer.in1());
                    return;
                }
            }

            public /* synthetic */ ZipLatestWith2 akka$stream$scaladsl$ZipLatestWith2$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$upstreamsToWait = 2;
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.inlet0 = new $anon$1$ZipLatestInlet<A1>(this, $outer.in0());
                this.inlet1 = new $anon$1$ZipLatestInlet<A2>(this, $outer.in1());
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple = false;
                this.akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues = true;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$scaladsl$ZipLatestWith2$$anon$$hasAllValues()) {
                            if (this.$outer.akka$stream$scaladsl$ZipLatestWith2$$anon$$staleTupleValues()) {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple_$eq(true);
                            } else {
                                this.$outer.akka$stream$scaladsl$ZipLatestWith2$$anon$$pushOutput();
                            }
                        } else {
                            this.$outer.akka$stream$scaladsl$ZipLatestWith2$$anon$$waitingForTuple_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$ZipLatestWith2$$anon$$tryPullAllIfNeeded();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in0(), this.inlet0());
                this.setHandler($outer.in1(), this.inlet1());
            }
        };
    }

    public String toString() {
        return "ZipLatestWith2";
    }

    public ZipLatestWith2(Function2<A1, A2, O> zipper, boolean eagerComplete) {
        this.zipper = zipper;
        this.eagerComplete = eagerComplete;
        this.shape = new FanInShape2("ZipLatestWith2");
        this.in0 = ((FanInShape2)this.shape()).in0();
        this.in1 = ((FanInShape2)this.shape()).in1();
    }

    public ZipLatestWith2(Function2<A1, A2, O> zipper) {
        this(zipper, true);
    }
}

