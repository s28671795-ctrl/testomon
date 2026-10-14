/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.pattern.BackoffSupervisor$;
import akka.stream.Attributes;
import akka.stream.BidiShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.impl.RetryFlowCoordinator$;
import akka.stream.impl.RetryFlowCoordinator$RetryCurrentElement$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.TimerGraphStageLogic;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015f!\u0002\u0011\"\u0005\u0015:\u0003\u0002C#\u0001\u0005\u0003\u0005\u000b\u0011\u0002$\t\u00119\u0003!\u0011!Q\u0001\n\u0019C\u0001b\u0014\u0001\u0003\u0002\u0003\u0006I\u0001\u0015\u0005\t'\u0002\u0011\t\u0011)A\u0005)\"Aq\u000b\u0001B\u0001B\u0003%\u0001\fC\u0003_\u0001\u0011\u0005q\fC\u0004h\u0001\t\u0007I\u0011\u00025\t\r1\u0004\u0001\u0015!\u0003j\u0011\u001di\u0007A1A\u0005\n9DaA\u001d\u0001!\u0002\u0013y\u0007bB:\u0001\u0005\u0004%I\u0001\u001e\u0005\u0007m\u0002\u0001\u000b\u0011B;\t\u000f]\u0004!\u0019!C\u0005q\"1!\u0010\u0001Q\u0001\neDqa\u001f\u0001C\u0002\u0013\u0005C\u0010\u0003\u0004~\u0001\u0001\u0006Ia\f\u0005\u0006}\u0002!\te`\u0004\b\u0003?\t\u0003\u0012BA\u0011\r\u0019\u0001\u0013\u0005#\u0003\u0002$!1al\u0005C\u0001\u0003W9q!!\f\u0014\u0011\u0003\u000byCB\u0004\u00024MA\t)!\u000e\t\ry3B\u0011AA+\u0011%\t9FFA\u0001\n\u0003\nI\u0006C\u0005\u0002lY\t\t\u0011\"\u0001\u0002n!I\u0011q\u000e\f\u0002\u0002\u0013\u0005\u0011\u0011\u000f\u0005\n\u0003o2\u0012\u0011!C!\u0003sB\u0011\"a\"\u0017\u0003\u0003%\t!!#\t\u0013\u0005Me#!A\u0005B\u0005U\u0005\"CAL-\u0005\u0005I\u0011IAM\u0011%\tYJFA\u0001\n\u0013\tiJ\u0001\u000bSKR\u0014\u0018P\u00127po\u000e{wN\u001d3j]\u0006$xN\u001d\u0006\u0003E\r\nA![7qY*\u0011A%J\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0019\nA!Y6lCV\u0019\u0001&N\"\u0014\u0005\u0001I\u0003c\u0001\u0016._5\t1F\u0003\u0002-G\u0005)1\u000f^1hK&\u0011af\u000b\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007C\u0002\u00192gM\u0012%)D\u0001$\u0013\t\u00114EA\u0005CS\u0012L7\u000b[1qKB\u0011A'\u000e\u0007\u0001\t\u00151\u0004A1\u00019\u0005\tIen\u0001\u0001\u0012\u0005ez\u0004C\u0001\u001e>\u001b\u0005Y$\"\u0001\u001f\u0002\u000bM\u001c\u0017\r\\1\n\u0005yZ$a\u0002(pi\"Lgn\u001a\t\u0003u\u0001K!!Q\u001e\u0003\u0007\u0005s\u0017\u0010\u0005\u00025\u0007\u0012)A\t\u0001b\u0001q\t\u0019q*\u001e;\u0002\u00155LgNQ1dW>4g\r\u0005\u0002H\u00196\t\u0001J\u0003\u0002J\u0015\u0006AA-\u001e:bi&|gN\u0003\u0002Lw\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u00055C%A\u0004$j]&$X\rR;sCRLwN\\\u0001\u000b[\u0006D()Y2l_\u001a4\u0017\u0001\u0004:b]\u0012|WNR1di>\u0014\bC\u0001\u001eR\u0013\t\u00116H\u0001\u0004E_V\u0014G.Z\u0001\u000b[\u0006D(+\u001a;sS\u0016\u001c\bC\u0001\u001eV\u0013\t16HA\u0002J]R\f1\u0002Z3dS\u0012,'+\u001a;ssB)!(W\u001aC7&\u0011!l\u000f\u0002\n\rVt7\r^5p]J\u00022A\u000f/4\u0013\ti6H\u0001\u0004PaRLwN\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\r\u0001\u00147\rZ3g!\u0011\t\u0007a\r\"\u000e\u0003\u0005BQ!\u0012\u0004A\u0002\u0019CQA\u0014\u0004A\u0002\u0019CQa\u0014\u0004A\u0002ACQa\u0015\u0004A\u0002QCQa\u0016\u0004A\u0002a\u000b!\"\u001a=uKJt\u0017\r\\%o+\u0005I\u0007c\u0001\u0019kg%\u00111n\t\u0002\u0006\u0013:dW\r^\u0001\fKb$XM\u001d8bY&s\u0007%A\u0006fqR,'O\\1m\u001fV$X#A8\u0011\u0007A\u0002()\u0003\u0002rG\t1q*\u001e;mKR\fA\"\u001a=uKJt\u0017\r\\(vi\u0002\n1\"\u001b8uKJt\u0017\r\\(viV\tQ\u000fE\u00021aN\nA\"\u001b8uKJt\u0017\r\\(vi\u0002\n!\"\u001b8uKJt\u0017\r\\%o+\u0005I\bc\u0001\u0019k\u0005\u0006Y\u0011N\u001c;fe:\fG.\u00138!\u0003\u0015\u0019\b.\u00199f+\u0005y\u0013AB:iCB,\u0007%A\u0006de\u0016\fG/\u001a'pO&\u001cG\u0003BA\u0001\u0003\u000f\u00012AKA\u0002\u0013\r\t)a\u000b\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"9\u0011\u0011B\tA\u0002\u0005-\u0011AC1uiJL'-\u001e;fgB\u0019\u0001'!\u0004\n\u0007\u0005=1E\u0001\u0006BiR\u0014\u0018NY;uKND3\u0001AA\n!\u0011\t)\"a\u0007\u000e\u0005\u0005]!bAA\rK\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005u\u0011q\u0003\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u000bSKR\u0014\u0018P\u00127po\u000e{wN\u001d3j]\u0006$xN\u001d\t\u0003CN\u00192aEA\u0013!\rQ\u0014qE\u0005\u0004\u0003SY$AB!osJ+g\r\u0006\u0002\u0002\"\u0005\u0019\"+\u001a;ss\u000e+(O]3oi\u0016cW-\\3oiB\u0019\u0011\u0011\u0007\f\u000e\u0003M\u00111CU3uef\u001cUO\u001d:f]R,E.Z7f]R\u001crAFA\u0013\u0003o\ti\u0004E\u0002;\u0003sI1!a\u000f<\u0005\u001d\u0001&o\u001c3vGR\u0004B!a\u0010\u0002P9!\u0011\u0011IA&\u001d\u0011\t\u0019%!\u0013\u000e\u0005\u0005\u0015#bAA$o\u00051AH]8pizJ\u0011\u0001P\u0005\u0004\u0003\u001bZ\u0014a\u00029bG.\fw-Z\u0005\u0005\u0003#\n\u0019F\u0001\u0007TKJL\u0017\r\\5{C\ndWMC\u0002\u0002Nm\"\"!a\f\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tY\u0006\u0005\u0003\u0002^\u0005\u001dTBAA0\u0015\u0011\t\t'a\u0019\u0002\t1\fgn\u001a\u0006\u0003\u0003K\nAA[1wC&!\u0011\u0011NA0\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\tA+\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007}\n\u0019\b\u0003\u0005\u0002vi\t\t\u00111\u0001U\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u0010\t\u0006\u0003{\n\u0019iP\u0007\u0003\u0003\u007fR1!!!<\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003\u000b\u000byH\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAF\u0003#\u00032AOAG\u0013\r\tyi\u000f\u0002\b\u0005>|G.Z1o\u0011!\t)\bHA\u0001\u0002\u0004y\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003Q\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u00037\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!a(\u0011\t\u0005u\u0013\u0011U\u0005\u0005\u0003G\u000byF\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class RetryFlowCoordinator<In, Out>
extends GraphStage<BidiShape<In, In, Out, Out>> {
    public final FiniteDuration akka$stream$impl$RetryFlowCoordinator$$minBackoff;
    public final FiniteDuration akka$stream$impl$RetryFlowCoordinator$$maxBackoff;
    public final double akka$stream$impl$RetryFlowCoordinator$$randomFactor;
    public final int akka$stream$impl$RetryFlowCoordinator$$maxRetries;
    public final Function2<In, Out, Option<In>> akka$stream$impl$RetryFlowCoordinator$$decideRetry;
    private final Inlet<In> akka$stream$impl$RetryFlowCoordinator$$externalIn;
    private final Outlet<Out> akka$stream$impl$RetryFlowCoordinator$$externalOut;
    private final Outlet<In> akka$stream$impl$RetryFlowCoordinator$$internalOut;
    private final Inlet<Out> akka$stream$impl$RetryFlowCoordinator$$internalIn;
    private final BidiShape<In, In, Out, Out> shape;

    public Inlet<In> akka$stream$impl$RetryFlowCoordinator$$externalIn() {
        return this.akka$stream$impl$RetryFlowCoordinator$$externalIn;
    }

    public Outlet<Out> akka$stream$impl$RetryFlowCoordinator$$externalOut() {
        return this.akka$stream$impl$RetryFlowCoordinator$$externalOut;
    }

    public Outlet<In> akka$stream$impl$RetryFlowCoordinator$$internalOut() {
        return this.akka$stream$impl$RetryFlowCoordinator$$internalOut;
    }

    public Inlet<Out> akka$stream$impl$RetryFlowCoordinator$$internalIn() {
        return this.akka$stream$impl$RetryFlowCoordinator$$internalIn;
    }

    @Override
    public BidiShape<In, In, Out, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes attributes) {
        return new TimerGraphStageLogic(this){
            private In akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress;
            private int akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo;
            private final /* synthetic */ RetryFlowCoordinator $outer;

            public In akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress() {
                return this.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress;
            }

            public void akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress_$eq(In x$1) {
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress = x$1;
            }

            public int akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo() {
                return this.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo;
            }

            public void akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo_$eq(int x$1) {
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo = x$1;
            }

            public void akka$stream$impl$RetryFlowCoordinator$$anon$$pushInternal(In element) {
                this.push(this.$outer.akka$stream$impl$RetryFlowCoordinator$$internalOut(), element);
            }

            public void akka$stream$impl$RetryFlowCoordinator$$anon$$pushExternal(Out result) {
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress_$eq(OptionVal$.MODULE$.none());
                this.push(this.$outer.akka$stream$impl$RetryFlowCoordinator$$externalOut(), result);
                if (this.isClosed(this.$outer.akka$stream$impl$RetryFlowCoordinator$$externalIn())) {
                    this.completeStage();
                    return;
                }
                if (this.isAvailable(this.$outer.akka$stream$impl$RetryFlowCoordinator$$internalOut())) {
                    this.pull(this.$outer.akka$stream$impl$RetryFlowCoordinator$$externalIn());
                    return;
                }
            }

            public void akka$stream$impl$RetryFlowCoordinator$$anon$$planRetry(In element) {
                FiniteDuration delay = BackoffSupervisor$.MODULE$.calculateDelay(this.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo(), this.$outer.akka$stream$impl$RetryFlowCoordinator$$minBackoff, this.$outer.akka$stream$impl$RetryFlowCoordinator$$maxBackoff, this.$outer.akka$stream$impl$RetryFlowCoordinator$$randomFactor);
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress_$eq(OptionVal$Some$.MODULE$.apply(element));
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo_$eq(this.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo() + 1);
                this.pull(this.$outer.akka$stream$impl$RetryFlowCoordinator$$internalIn());
                this.scheduleOnce((Object)RetryFlowCoordinator$RetryCurrentElement$.MODULE$, delay);
            }

            public void onTimer(Object timerKey) {
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$pushInternal(OptionVal$.MODULE$.get$extension(this.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress()));
            }

            public /* synthetic */ RetryFlowCoordinator akka$stream$impl$RetryFlowCoordinator$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress = OptionVal$.MODULE$.none();
                this.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo = 0;
                this.setHandler($outer.akka$stream$impl$RetryFlowCoordinator$$externalIn(), new InHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        In element = this.$outer.grab(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$externalIn());
                        this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress_$eq(OptionVal$Some$.MODULE$.apply(element));
                        this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo_$eq(0);
                        this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$pushInternal(element);
                    }

                    public void onUpstreamFinish() {
                        if (OptionVal$.MODULE$.isEmpty$extension(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress())) {
                            this.$outer.completeStage();
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.akka$stream$impl$RetryFlowCoordinator$$internalOut(), new OutHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        if (OptionVal$.MODULE$.isEmpty$extension(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress())) {
                            if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$externalIn()) && !this.$outer.isClosed(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$externalIn())) {
                                this.$outer.pull(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$externalIn());
                                return;
                            }
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        if (OptionVal$.MODULE$.isEmpty$extension(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress()) || !(cause instanceof SubscriptionWithCancelException.NonFailureCancellation)) {
                            OutHandler.onDownstreamFinish$(this, cause);
                            return;
                        }
                        this.$outer.setKeepGoing(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                    }
                });
                this.setHandler($outer.akka$stream$impl$RetryFlowCoordinator$$internalIn(), new InHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Out result = this.$outer.grab(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$internalIn());
                        In In2 = this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$elementInProgress();
                        In In3 = OptionVal$Some$.MODULE$.unapply(In2);
                        if (!OptionVal$.MODULE$.isEmpty$extension(In3) && this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$retryNo() == this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$maxRetries) {
                            this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$pushExternal(result);
                            return;
                        }
                        In In4 = OptionVal$Some$.MODULE$.unapply(In2);
                        if (!OptionVal$.MODULE$.isEmpty$extension(In4)) {
                            In in = OptionVal$.MODULE$.get$extension(In4);
                            Option<In> option = this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$decideRetry.apply(in, result);
                            if (None$.MODULE$.equals(option)) {
                                this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$pushExternal(result);
                            } else if (option instanceof Some) {
                                Some some = (Some)option;
                                A element = some.value();
                                this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$planRetry(element);
                            } else {
                                throw new MatchError(option);
                            }
                            return;
                        }
                        this.$outer.failStage(new IllegalStateException(new StringBuilder(71).append("inner flow emitted unexpected element ").append(result).append("; the flow must be one-in one-out").toString()));
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.akka$stream$impl$RetryFlowCoordinator$$externalOut(), new OutHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$internalIn())) {
                            this.$outer.pull(this.$outer.akka$stream$impl$RetryFlowCoordinator$$anon$$$outer().akka$stream$impl$RetryFlowCoordinator$$internalIn());
                            return;
                        }
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
        };
    }

    public RetryFlowCoordinator(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRetries, Function2<In, Out, Option<In>> decideRetry) {
        this.akka$stream$impl$RetryFlowCoordinator$$minBackoff = minBackoff;
        this.akka$stream$impl$RetryFlowCoordinator$$maxBackoff = maxBackoff;
        this.akka$stream$impl$RetryFlowCoordinator$$randomFactor = randomFactor;
        this.akka$stream$impl$RetryFlowCoordinator$$maxRetries = maxRetries;
        this.akka$stream$impl$RetryFlowCoordinator$$decideRetry = decideRetry;
        this.akka$stream$impl$RetryFlowCoordinator$$externalIn = Inlet$.MODULE$.apply("RetryFlow.externalIn");
        this.akka$stream$impl$RetryFlowCoordinator$$externalOut = Outlet$.MODULE$.apply("RetryFlow.externalOut");
        this.akka$stream$impl$RetryFlowCoordinator$$internalOut = Outlet$.MODULE$.apply("RetryFlow.internalOut");
        this.akka$stream$impl$RetryFlowCoordinator$$internalIn = Inlet$.MODULE$.apply("RetryFlow.internalIn");
        this.shape = new BidiShape<In, In, Out, Out>(this.akka$stream$impl$RetryFlowCoordinator$$externalIn(), this.akka$stream$impl$RetryFlowCoordinator$$internalOut(), this.akka$stream$impl$RetryFlowCoordinator$$internalIn(), this.akka$stream$impl$RetryFlowCoordinator$$externalOut());
    }
}

