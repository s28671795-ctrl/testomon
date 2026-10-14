/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.UniformFanOutShape;
import akka.stream.UniformFanOutShape$;
import akka.stream.scaladsl.Partition$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\tUq!B\u0015+\u0011\u0003\td!B\u001a+\u0011\u0003!\u0004\"B\u001e\u0002\t\u0003ad\u0001B\u001f\u0002\u0001zB\u0001\"W\u0002\u0003\u0016\u0004%\tA\u0017\u0005\tG\u000e\u0011\t\u0012)A\u00057\")1h\u0001C\u0001I\"9\u0001nAA\u0001\n\u0003I\u0007bB6\u0004#\u0003%\t\u0001\u001c\u0005\bo\u000e\t\t\u0011\"\u0011y\u0011%\t\taAA\u0001\n\u0003\t\u0019\u0001C\u0005\u0002\f\r\t\t\u0011\"\u0001\u0002\u000e!I\u0011\u0011D\u0002\u0002\u0002\u0013\u0005\u00131\u0004\u0005\n\u0003S\u0019\u0011\u0011!C\u0001\u0003WA\u0011\"!\u000e\u0004\u0003\u0003%\t%a\u000e\t\u0013\u0005m2!!A\u0005B\u0005u\u0002\"CA \u0007\u0005\u0005I\u0011IA!\u000f%\t)%AA\u0001\u0012\u0003\t9E\u0002\u0005>\u0003\u0005\u0005\t\u0012AA%\u0011\u0019Y$\u0003\"\u0001\u0002b!I\u00111\r\n\u0002\u0002\u0013\u0015\u0013Q\r\u0005\n\u0003O\u0012\u0012\u0011!CA\u0003SB\u0011\"!\u001c\u0013\u0003\u0003%\t)a\u001c\t\u0013\u0005m$#!A\u0005\n\u0005u\u0004bBA4\u0003\u0011\u0005\u0011Q\u0011\u0004\u0006g)\u0012\u00111\u0012\u0005\u000b\u0003gK\"Q1A\u0005\u0002\u0005\r\u0001BCA[3\t\u0005\t\u0015!\u0003\u0002\u0006!Q\u0011qW\r\u0003\u0006\u0004%\t!!/\t\u0015\u0005\u0005\u0017D!A!\u0002\u0013\tY\f\u0003\u0006\u0002Df\u0011)\u0019!C\u0001\u0003\u000bD!\"a2\u001a\u0005\u0003\u0005\u000b\u0011BA\u0017\u0011\u0019Y\u0014\u0004\"\u0001\u0002J\"I\u00111[\rC\u0002\u0013\u0005\u0011Q\u001b\u0005\t\u0003;L\u0002\u0015!\u0003\u0002X\"I\u0011q\\\rC\u0002\u0013\u0005\u0011\u0011\u001d\u0005\t\u0003_L\u0002\u0015!\u0003\u0002d\"I\u0011\u0011_\rC\u0002\u0013\u0005\u00131\u001f\u0005\t\u0003kL\u0002\u0015!\u0003\u0002\u001c\"9\u0011q_\r\u0005B\u0005e\bbBA23\u0011\u0005\u0013QM\u0001\n!\u0006\u0014H/\u001b;j_:T!a\u000b\u0017\u0002\u0011M\u001c\u0017\r\\1eg2T!!\f\u0018\u0002\rM$(/Z1n\u0015\u0005y\u0013\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u00023\u00035\t!FA\u0005QCJ$\u0018\u000e^5p]N\u0011\u0011!\u000e\t\u0003mej\u0011a\u000e\u0006\u0002q\u0005)1oY1mC&\u0011!h\u000e\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\t$!\b)beRLG/[8o\u001fV$xJ\u001a\"pk:$7/\u0012=dKB$\u0018n\u001c8\u0014\u000b\ry4j\u0015,\u0011\u0005\u0001CeBA!G\u001d\t\u0011U)D\u0001D\u0015\t!\u0005'\u0001\u0004=e>|GOP\u0005\u0002q%\u0011qiN\u0001\ba\u0006\u001c7.Y4f\u0013\tI%JA\rJ]\u0012,\u0007pT;u\u001f\u001a\u0014u.\u001e8eg\u0016C8-\u001a9uS>t'BA$8!\ta\u0015+D\u0001N\u0015\tqu*A\u0004d_:$(o\u001c7\u000b\u0005A;\u0014\u0001B;uS2L!AU'\u0003\u00199{7\u000b^1dWR\u0013\u0018mY3\u0011\u0005Y\"\u0016BA+8\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001Q,\n\u0005aS%\u0001D*fe&\fG.\u001b>bE2,\u0017aA7tOV\t1\f\u0005\u0002]A:\u0011QL\u0018\t\u0003\u0005^J!aX\u001c\u0002\rA\u0013X\rZ3g\u0013\t\t'M\u0001\u0004TiJLgn\u001a\u0006\u0003?^\nA!\\:hAQ\u0011Qm\u001a\t\u0003M\u000ei\u0011!\u0001\u0005\u00063\u001a\u0001\raW\u0001\u0005G>\u0004\u0018\u0010\u0006\u0002fU\"9\u0011l\u0002I\u0001\u0002\u0004Y\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0002[*\u00121L\\\u0016\u0002_B\u0011\u0001/^\u0007\u0002c*\u0011!o]\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001^\u001c\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002wc\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005I\bC\u0001>\u0000\u001b\u0005Y(B\u0001?~\u0003\u0011a\u0017M\\4\u000b\u0003y\fAA[1wC&\u0011\u0011m_\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000b\u00012ANA\u0004\u0013\r\tIa\u000e\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u001f\t)\u0002E\u00027\u0003#I1!a\u00058\u0005\r\te.\u001f\u0005\n\u0003/Y\u0011\u0011!a\u0001\u0003\u000b\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u000f!\u0019\ty\"!\n\u0002\u00105\u0011\u0011\u0011\u0005\u0006\u0004\u0003G9\u0014AC2pY2,7\r^5p]&!\u0011qEA\u0011\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u00055\u00121\u0007\t\u0004m\u0005=\u0012bAA\u0019o\t9!i\\8mK\u0006t\u0007\"CA\f\u001b\u0005\u0005\t\u0019AA\b\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\u0007e\fI\u0004C\u0005\u0002\u00189\t\t\u00111\u0001\u0002\u0006\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u0006\u00051Q-];bYN$B!!\f\u0002D!I\u0011q\u0003\t\u0002\u0002\u0003\u0007\u0011qB\u0001\u001e!\u0006\u0014H/\u001b;j_:|U\u000f^(g\u0005>,h\u000eZ:Fq\u000e,\u0007\u000f^5p]B\u0011aME\n\u0006%\u0005-\u0013q\u000b\t\u0007\u0003\u001b\n\u0019fW3\u000e\u0005\u0005=#bAA)o\u00059!/\u001e8uS6,\u0017\u0002BA+\u0003\u001f\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\tI&a\u0018\u000e\u0005\u0005m#bAA/{\u0006\u0011\u0011n\\\u0005\u00041\u0006mCCAA$\u0003!!xn\u0015;sS:<G#A=\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007\u0015\fY\u0007C\u0003Z+\u0001\u00071,A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005E\u0014q\u000f\t\u0005m\u0005M4,C\u0002\u0002v]\u0012aa\u00149uS>t\u0007\u0002CA=-\u0005\u0005\t\u0019A3\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u0000A\u0019!0!!\n\u0007\u0005\r5P\u0001\u0004PE*,7\r^\u000b\u0005\u0003\u000f\u0013i\u0001\u0006\u0004\u0002\n\n=!\u0011\u0003\t\u0005ee\u0011Y!\u0006\u0003\u0002\u000e\u0006\u001d6cA\r\u0002\u0010B1\u0011\u0011SAL\u00037k!!a%\u000b\u0007\u0005UE&A\u0003ti\u0006<W-\u0003\u0003\u0002\u001a\u0006M%AC$sCBD7\u000b^1hKBA\u0011QTAP\u0003G\u000b\u0019+D\u0001-\u0013\r\t\t\u000b\f\u0002\u0013+:Lgm\u001c:n\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u0005\u0003\u0002&\u0006\u001dF\u0002\u0001\u0003\b\u0003SK\"\u0019AAV\u0005\u0005!\u0016\u0003BAW\u0003\u001f\u00012ANAX\u0013\r\t\tl\u000e\u0002\b\u001d>$\b.\u001b8h\u0003-yW\u000f\u001e9viB{'\u000f^:\u0002\u0019=,H\u000f];u!>\u0014Ho\u001d\u0011\u0002\u0017A\f'\u000f^5uS>tWM]\u000b\u0003\u0003w\u0003rANA_\u0003G\u000b)!C\u0002\u0002@^\u0012\u0011BR;oGRLwN\\\u0019\u0002\u0019A\f'\u000f^5uS>tWM\u001d\u0011\u0002\u0017\u0015\fw-\u001a:DC:\u001cW\r\\\u000b\u0003\u0003[\tA\"Z1hKJ\u001c\u0015M\\2fY\u0002\"\u0002\"a3\u0002N\u0006=\u0017\u0011\u001b\t\u0005ee\t\u0019\u000bC\u0004\u00024\u0002\u0002\r!!\u0002\t\u000f\u0005]\u0006\u00051\u0001\u0002<\"9\u00111\u0019\u0011A\u0002\u00055\u0012AA5o+\t\t9\u000e\u0005\u0004\u0002\u001e\u0006e\u00171U\u0005\u0004\u00037d#!B%oY\u0016$\u0018aA5oA\u0005\u0019q.\u001e;\u0016\u0005\u0005\r\b#\u0002!\u0002f\u0006%\u0018bAAt\u0015\n\u00191+Z9\u0011\r\u0005u\u00151^AR\u0013\r\ti\u000f\f\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0003\u00037\u000baa\u001d5ba\u0016\u0004\u0013aC2sK\u0006$X\rT8hS\u000e$B!a?\u0003\u0002A!\u0011\u0011SA\u007f\u0013\u0011\ty0a%\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDqAa\u0001(\u0001\u0004\u0011)!A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u0005\u0003\u0002\u001e\n\u001d\u0011b\u0001B\u0005Y\tQ\u0011\t\u001e;sS\n,H/Z:\u0011\t\u0005\u0015&Q\u0002\u0003\b\u0003SC\"\u0019AAV\u0011\u001d\t\u0019\f\u0007a\u0001\u0003\u000bAq!a.\u0019\u0001\u0004\u0011\u0019\u0002E\u00047\u0003{\u0013Y!!\u0002")
public final class Partition<T>
extends GraphStage<UniformFanOutShape<T, T>> {
    private final int outputPorts;
    private final Function1<T, Object> partitioner;
    private final boolean eagerCancel;
    private final Inlet<T> in;
    private final Seq<Outlet<T>> out;
    private final UniformFanOutShape<T, T> shape;

    public static <T> Partition<T> apply(int outputPorts, Function1<T, Object> partitioner) {
        return Partition$.MODULE$.apply(outputPorts, partitioner);
    }

    public int outputPorts() {
        return this.outputPorts;
    }

    public Function1<T, Object> partitioner() {
        return this.partitioner;
    }

    public boolean eagerCancel() {
        return this.eagerCancel;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Seq<Outlet<T>> out() {
        return this.out;
    }

    @Override
    public UniformFanOutShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private Object akka$stream$scaladsl$Partition$$anon$$outPendingElem;
            private int akka$stream$scaladsl$Partition$$anon$$outPendingIdx;
            private int akka$stream$scaladsl$Partition$$anon$$downstreamRunning;
            private volatile boolean bitmap$0;
            private final /* synthetic */ Partition $outer;
            private Attributes inheritedAttributes$1;

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$16 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$1 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            public Object akka$stream$scaladsl$Partition$$anon$$outPendingElem() {
                return this.akka$stream$scaladsl$Partition$$anon$$outPendingElem;
            }

            public void akka$stream$scaladsl$Partition$$anon$$outPendingElem_$eq(Object x$1) {
                this.akka$stream$scaladsl$Partition$$anon$$outPendingElem = x$1;
            }

            public int akka$stream$scaladsl$Partition$$anon$$outPendingIdx() {
                return this.akka$stream$scaladsl$Partition$$anon$$outPendingIdx;
            }

            private void outPendingIdx_$eq(int x$1) {
                this.akka$stream$scaladsl$Partition$$anon$$outPendingIdx = x$1;
            }

            public int akka$stream$scaladsl$Partition$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$Partition$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$Partition$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$Partition$$anon$$downstreamRunning = x$1;
            }

            public void onPush() {
                int n;
                T elem = this.grab(this.$outer.in());
                try {
                    int i = BoxesRunTime.unboxToInt(this.$outer.partitioner().apply(elem));
                    if (i < 0 || i >= this.$outer.outputPorts()) {
                        throw new PartitionOutOfBoundsException(new StringBuilder(78).append("partitioner must return an index in the range [0,").append(this.$outer.outputPorts() - 1).append("]. returned: [").append(i).append("] for input ").append("[").append(elem.getClass().getName()).append("].").toString());
                    }
                    n = i;
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
                            this.pull(this.$outer.in());
                        } else if (Supervision$Resume$.MODULE$.equals(directive)) {
                            this.pull(this.$outer.in());
                        } else {
                            throw new MatchError(directive);
                        }
                        n = Integer.MIN_VALUE;
                    }
                    throw throwable;
                }
                int idx = n;
                if (idx != Integer.MIN_VALUE) {
                    if (!this.isClosed((Outlet)this.$outer.out().apply(idx))) {
                        if (this.isAvailable((Outlet)this.$outer.out().apply(idx))) {
                            this.push((Outlet)this.$outer.out().apply(idx), elem);
                            this.pullIfAnyOutIsAvailable();
                            return;
                        }
                        this.akka$stream$scaladsl$Partition$$anon$$outPendingElem_$eq(elem);
                        this.outPendingIdx_$eq(idx);
                        return;
                    }
                    this.pullIfAnyOutIsAvailable();
                    return;
                }
            }

            private void pullIfAnyOutIsAvailable() {
                if (this.$outer.out().exists((Function1<Outlet, Object> & Serializable)x$7 -> BoxesRunTime.boxToBoolean($anon$16.$anonfun$pullIfAnyOutIsAvailable$1(this, x$7)))) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.akka$stream$scaladsl$Partition$$anon$$outPendingElem() == null) {
                    this.completeStage();
                    return;
                }
            }

            public /* synthetic */ Partition akka$stream$scaladsl$Partition$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ boolean $anonfun$pullIfAnyOutIsAvailable$1($anon$16 $this, Outlet x$7) {
                return $this.isAvailable(x$7);
            }

            public static final /* synthetic */ void $anonfun$new$11($anon$16 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    Outlet o = (Outlet)tuple2._1();
                    int idx = tuple2._2$mcI$sp();
                    $this.setHandler(o, new OutHandler($this, idx, o){
                        private final /* synthetic */ $anon$16 $outer;
                        private final int idx$1;
                        private final Outlet o$1;

                        public void onDownstreamFinish() throws Exception {
                            OutHandler.onDownstreamFinish$(this);
                        }

                        public void onPull() {
                            if (this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingElem() != null) {
                                Object elem = this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingElem();
                                if (this.idx$1 == this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingIdx()) {
                                    this.$outer.push(this.o$1, elem);
                                    this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingElem_$eq(null);
                                    if (this.$outer.isClosed(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in())) {
                                        this.$outer.completeStage();
                                        return;
                                    }
                                    if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in())) {
                                        this.$outer.pull(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in());
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in())) {
                                this.$outer.pull(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in());
                                return;
                            }
                        }

                        public void onDownstreamFinish(Throwable cause) {
                            if (this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().eagerCancel()) {
                                this.$outer.cancelStage(cause);
                                return;
                            }
                            this.$outer.akka$stream$scaladsl$Partition$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$Partition$$anon$$downstreamRunning() - 1);
                            if (this.$outer.akka$stream$scaladsl$Partition$$anon$$downstreamRunning() == 0) {
                                this.$outer.cancelStage(cause);
                                return;
                            }
                            if (this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingElem() != null && this.idx$1 == this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingIdx()) {
                                this.$outer.akka$stream$scaladsl$Partition$$anon$$outPendingElem_$eq(null);
                                if (this.$outer.isClosed(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in())) {
                                    this.$outer.cancelStage(cause);
                                    return;
                                }
                                if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in())) {
                                    this.$outer.pull(this.$outer.akka$stream$scaladsl$Partition$$anon$$$outer().in());
                                    return;
                                }
                                return;
                            }
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.idx$1 = idx$1;
                            this.o$1 = o$1;
                            OutHandler.$init$(this);
                        }
                    });
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                InHandler.$init$(this);
                this.akka$stream$scaladsl$Partition$$anon$$outPendingElem = null;
                this.akka$stream$scaladsl$Partition$$anon$$downstreamRunning = $outer.outputPorts();
                this.setHandler($outer.in(), this);
                $outer.out().iterator().zipWithIndex().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$16.$anonfun$new$11(this, x0$1);
                    return BoxedUnit.UNIT;
                });
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$11$adapted(akka.stream.scaladsl.Partition$$anon$16 scala.Tuple2 ), $anonfun$pullIfAnyOutIsAvailable$1$adapted(akka.stream.scaladsl.Partition$$anon$16 akka.stream.Outlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return new StringBuilder(11).append("Partition(").append(this.outputPorts()).append(")").toString();
    }

    public Partition(int outputPorts, Function1<T, Object> partitioner, boolean eagerCancel) {
        this.outputPorts = outputPorts;
        this.partitioner = partitioner;
        this.eagerCancel = eagerCancel;
        this.in = Inlet$.MODULE$.apply("Partition.in");
        this.out = (Seq)package$.MODULE$.Seq().tabulate(outputPorts, (Function1<Object, Outlet> & Serializable)i -> Outlet$.MODULE$.apply(new StringBuilder(13).append("Partition.out").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.shape = UniformFanOutShape$.MODULE$.apply(this.in(), this.out());
    }

    public static class PartitionOutOfBoundsException
    extends IndexOutOfBoundsException
    implements NoStackTrace,
    Product {
        private final String msg;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        @Override
        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }

        public String msg() {
            return this.msg;
        }

        public PartitionOutOfBoundsException copy(String msg) {
            return new PartitionOutOfBoundsException(msg);
        }

        public String copy$default$1() {
            return this.msg();
        }

        @Override
        public String productPrefix() {
            return "PartitionOutOfBoundsException";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.msg();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof PartitionOutOfBoundsException;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "msg";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof PartitionOutOfBoundsException)) return false;
            boolean bl = true;
            if (!bl) return false;
            PartitionOutOfBoundsException partitionOutOfBoundsException = (PartitionOutOfBoundsException)x$1;
            String string = this.msg();
            String string2 = partitionOutOfBoundsException.msg();
            if (string == null) {
                if (string2 != null) {
                    return false;
                }
            } else if (!string.equals(string2)) return false;
            if (!partitionOutOfBoundsException.canEqual(this)) return false;
            return true;
        }

        public PartitionOutOfBoundsException(String msg) {
            this.msg = msg;
            super(msg);
            NoStackTrace.$init$(this);
            Product.$init$(this);
        }
    }
}

