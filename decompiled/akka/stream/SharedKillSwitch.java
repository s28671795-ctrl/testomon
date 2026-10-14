/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.Done;
import akka.Done$;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Inlet$;
import akka.stream.KillSwitch;
import akka.stream.Outlet$;
import akka.stream.TerminationSignal;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.util.Failure;
import scala.util.Success;

@ScalaSignature(bytes="\u0006\u0005u4A\u0001E\t\u0003-!A\u0011\u0005\u0001BC\u0002\u0013\u0005!\u0005\u0003\u0005/\u0001\t\u0005\t\u0015!\u0003$\u0011\u0019y\u0003\u0001\"\u0001\u0012a!11\u0007\u0001Q\u0001\nQBaa\u000e\u0001!\u0002\u0013A\u0004\"B!\u0001\t\u0003\u0011\u0005\"\u0002$\u0001\t\u00039\u0005\"B*\u0001\t\u0003!\u0006\"\u00021\u0001\t\u0003\ng\u0001\u00022\u0001\t\rDQa\f\u0006\u0005\u0002)Dq!\u001c\u0006C\u0002\u0013\u0005c\u000e\u0003\u0004p\u0015\u0001\u0006Ia\u000f\u0005\u0006A*!\t%\u0019\u0005\u0006a*!\t%\u001d\u0002\u0011'\"\f'/\u001a3LS2d7k^5uG\"T!AE\n\u0002\rM$(/Z1n\u0015\u0005!\u0012\u0001B1lW\u0006\u001c\u0001aE\u0002\u0001/u\u0001\"\u0001G\u000e\u000e\u0003eQ\u0011AG\u0001\u0006g\u000e\fG.Y\u0005\u00039e\u0011a!\u00118z%\u00164\u0007C\u0001\u0010 \u001b\u0005\t\u0012B\u0001\u0011\u0012\u0005)Y\u0015\u000e\u001c7To&$8\r[\u0001\u0005]\u0006lW-F\u0001$!\t!3F\u0004\u0002&SA\u0011a%G\u0007\u0002O)\u0011\u0001&F\u0001\u0007yI|w\u000e\u001e \n\u0005)J\u0012A\u0002)sK\u0012,g-\u0003\u0002-[\t11\u000b\u001e:j]\u001eT!AK\r\u0002\u000b9\fW.\u001a\u0011\u0002\rqJg.\u001b;?)\t\t$\u0007\u0005\u0002\u001f\u0001!)\u0011e\u0001a\u0001G\u0005\tB/\u001a:nS:\fG/[8o'&<g.\u00197\u0011\u0005y)\u0014B\u0001\u001c\u0012\u0005E!VM]7j]\u0006$\u0018n\u001c8TS\u001et\u0017\r\\\u0001\u0006?\u001adwn\u001e\t\u0005=eZ\u0014'\u0003\u0002;#\t)qI]1qQB!a\u0004\u0010 ?\u0013\ti\u0014CA\u0005GY><8\u000b[1qKB\u0011\u0001dP\u0005\u0003\u0001f\u00111!\u00118z\u0003!\u0019\b.\u001e;e_^tG#A\"\u0011\u0005a!\u0015BA#\u001a\u0005\u0011)f.\u001b;\u0002\u000b\u0005\u0014wN\u001d;\u0015\u0005\rC\u0005\"B%\b\u0001\u0004Q\u0015A\u0002:fCN|g\u000e\u0005\u0002L!:\u0011AJ\u0014\b\u0003M5K\u0011AG\u0005\u0003\u001ff\tq\u0001]1dW\u0006<W-\u0003\u0002R%\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\u001ff\tAA\u001a7poV\u0011QKW\u000b\u0002-B!a$O,2!\u0011qB\b\u0017-\u0011\u0005eSF\u0002\u0001\u0003\u00067\"\u0011\r\u0001\u0018\u0002\u0002)F\u0011QL\u0010\t\u00031yK!aX\r\u0003\u000f9{G\u000f[5oO\u0006AAo\\*ue&tw\rF\u0001$\u0005Q\u0019\u0006.\u0019:fI.KG\u000e\\*xSR\u001c\u0007N\u00127poN\u0011!\u0002\u001a\t\u0005K\"\\\u0014'D\u0001g\u0015\t9\u0017#A\u0003ti\u0006<W-\u0003\u0002jM\nyrI]1qQN#\u0018mZ3XSRDW*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0015\u0003-\u0004\"\u0001\u001c\u0006\u000e\u0003\u0001\tQa\u001d5ba\u0016,\u0012aO\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002?\r\u0014X-\u0019;f\u0019><\u0017nY!oI6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0006\u0002sqB!\u0001d];2\u0013\t!\u0018D\u0001\u0004UkBdWM\r\t\u0003KZL!a\u001e4\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQ!_\bA\u0002i\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\u0004\"AH>\n\u0005q\f\"AC!uiJL'-\u001e;fg\u0002")
public final class SharedKillSwitch
implements KillSwitch {
    private final String name;
    public final TerminationSignal akka$stream$SharedKillSwitch$$terminationSignal;
    private final Graph<FlowShape<Object, Object>, SharedKillSwitch> _flow;

    public String name() {
        return this.name;
    }

    @Override
    public void shutdown() {
        this.akka$stream$SharedKillSwitch$$terminationSignal.tryComplete(new Success<Done>(Done$.MODULE$));
    }

    @Override
    public void abort(Throwable reason) {
        this.akka$stream$SharedKillSwitch$$terminationSignal.tryComplete(new Failure<Done>(reason));
    }

    public <T> Graph<FlowShape<T, T>, SharedKillSwitch> flow() {
        return this._flow;
    }

    public String toString() {
        return new StringBuilder(12).append("KillSwitch(").append(this.name()).append(")").toString();
    }

    public SharedKillSwitch(String name) {
        this.name = name;
        this.akka$stream$SharedKillSwitch$$terminationSignal = new TerminationSignal();
        this._flow = new SharedKillSwitchFlow();
    }

    private class SharedKillSwitchFlow
    extends GraphStageWithMaterializedValue<FlowShape<Object, Object>, SharedKillSwitch> {
        private final FlowShape<Object, Object> shape;

        @Override
        public FlowShape<Object, Object> shape() {
            return this.shape;
        }

        public String toString() {
            return new StringBuilder(30).append("SharedKillSwitchFlow(switch: ").append(this.akka$stream$SharedKillSwitch$SharedKillSwitchFlow$$$outer().name()).append(")").toString();
        }

        @Override
        public Tuple2<GraphStageLogic, SharedKillSwitch> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
            TerminationSignal.Listener shutdownListener = this.akka$stream$SharedKillSwitch$SharedKillSwitchFlow$$$outer().akka$stream$SharedKillSwitch$$terminationSignal.createListener();
            InHandler logic = new InHandler(this, shutdownListener){
                private final /* synthetic */ SharedKillSwitchFlow $outer;
                private final TerminationSignal.Listener shutdownListener$1;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onUpstreamFinish() throws Exception {
                    InHandler.onUpstreamFinish$(this);
                }

                public void onUpstreamFailure(Throwable ex) throws Exception {
                    InHandler.onUpstreamFailure$(this, ex);
                }

                public void onPush() {
                    this.push(((FlowShape)this.$outer.shape()).out(), this.grab(((FlowShape)this.$outer.shape()).in()));
                }

                public void onPull() {
                    this.pull(((FlowShape)this.$outer.shape()).in());
                }

                public void postStop() {
                    this.shutdownListener$1.unregister();
                    super.postStop();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.shutdownListener$1 = shutdownListener$1;
                    super(shutdownListener$1.future(), $outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.setHandler(((FlowShape)$outer.shape()).in(), this);
                    this.setHandler(((FlowShape)$outer.shape()).out(), this);
                }
            };
            return new Tuple2<GraphStageLogic, SharedKillSwitch>((GraphStageLogic)((Object)logic), this.akka$stream$SharedKillSwitch$SharedKillSwitchFlow$$$outer());
        }

        public /* synthetic */ SharedKillSwitch akka$stream$SharedKillSwitch$SharedKillSwitchFlow$$$outer() {
            return SharedKillSwitch.this;
        }

        public SharedKillSwitchFlow() {
            if (SharedKillSwitch.this == null) {
                throw null;
            }
            this.shape = new FlowShape(Inlet$.MODULE$.apply("KillSwitch.in"), Outlet$.MODULE$.apply("KillSwitch.out"));
        }
    }
}

