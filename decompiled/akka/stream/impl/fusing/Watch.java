/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.WatchedActorTerminatedException;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.Watch$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=f!\u0002\r\u001a\u0005~\t\u0003\u0002\u0003)\u0001\u0005+\u0007I\u0011A)\t\u0011a\u0003!\u0011#Q\u0001\nICQ!\u0017\u0001\u0005\u0002iCQA\u0018\u0001\u0005B}CQ\u0001\u001a\u0001\u0005B\u0015DqA\u001c\u0001\u0002\u0002\u0013\u0005q\u000eC\u0004v\u0001E\u0005I\u0011\u0001<\t\u0013\u0005\u001d\u0001!!A\u0005B\u0005%\u0001\"CA\u000e\u0001\u0005\u0005I\u0011AA\u000f\u0011%\t)\u0003AA\u0001\n\u0003\t9\u0003C\u0005\u0002.\u0001\t\t\u0011\"\u0011\u00020!I\u0011Q\b\u0001\u0002\u0002\u0013\u0005\u0011q\b\u0005\n\u0003\u0013\u0002\u0011\u0011!C!\u0003\u0017B\u0011\"a\u0014\u0001\u0003\u0003%\t%!\u0015\t\u0013\u0005M\u0003!!A\u0005B\u0005U\u0003\"CA,\u0001\u0005\u0005I\u0011IA-\u000f)\tI'GA\u0001\u0012\u0003y\u00121\u000e\u0004\n1e\t\t\u0011#\u0001 \u0003[Ba!\u0017\n\u0005\u0002\u0005}\u0004\"CA*%\u0005\u0005IQIA+\u0011%\t\tIEA\u0001\n\u0003\u000b\u0019\tC\u0005\u0002\u0010J\t\t\u0011\"!\u0002\u0012\"I\u0011Q\u0015\n\u0002\u0002\u0013%\u0011q\u0015\u0002\u0006/\u0006$8\r\u001b\u0006\u00035m\taAZ;tS:<'B\u0001\u000f\u001e\u0003\u0011IW\u000e\u001d7\u000b\u0005yy\u0012AB:ue\u0016\fWNC\u0001!\u0003\u0011\t7n[1\u0016\u0005\tB4\u0003\u0002\u0001$\t\u001e\u00032\u0001J\u001a7\u001d\t)\u0013G\u0004\u0002'a9\u0011qe\f\b\u0003Q9r!!K\u0017\u000e\u0003)R!a\u000b\u0017\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011\u0001I\u0005\u0003=}I!\u0001H\u000f\n\u0005iY\u0012B\u0001\u001a\u001a\u0003-9%/\u00199i'R\fw-Z:\n\u0005Q*$AF*j[BdW\rT5oK\u0006\u0014xI]1qQN#\u0018mZ3\u000b\u0005IJ\u0002CA\u001c9\u0019\u0001!Q!\u000f\u0001C\u0002i\u0012\u0011\u0001V\t\u0003w\u0005\u0003\"\u0001P \u000e\u0003uR\u0011AP\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0001v\u0012qAT8uQ&tw\r\u0005\u0002=\u0005&\u00111)\u0010\u0002\u0004\u0003:L\bC\u0001\u001fF\u0013\t1UHA\u0004Qe>$Wo\u0019;\u0011\u0005!keBA%L\u001d\tI#*C\u0001?\u0013\taU(A\u0004qC\u000e\\\u0017mZ3\n\u00059{%\u0001D*fe&\fG.\u001b>bE2,'B\u0001'>\u0003%!\u0018M]4fiJ+g-F\u0001S!\t\u0019f+D\u0001U\u0015\t)v$A\u0003bGR|'/\u0003\u0002X)\nA\u0011i\u0019;peJ+g-\u0001\u0006uCJ<W\r\u001e*fM\u0002\na\u0001P5oSRtDCA.^!\ra\u0006AN\u0007\u00023!)\u0001k\u0001a\u0001%\u0006\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003\u0001\u0004\"!\u00192\u000e\u0003uI!aY\u000f\u0003\u0015\u0005#HO]5ckR,7/A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u00014m!\t9'.D\u0001i\u0015\tIW$A\u0003ti\u0006<W-\u0003\u0002lQ\nyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003n\u000b\u0001\u0007\u0001-A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0003d_BLXC\u00019t)\t\tH\u000fE\u0002]\u0001I\u0004\"aN:\u0005\u000be2!\u0019\u0001\u001e\t\u000fA3\u0001\u0013!a\u0001%\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTcA<\u0002\u0006U\t\u0001P\u000b\u0002Ss.\n!\u0010E\u0002|\u0003\u0003i\u0011\u0001 \u0006\u0003{z\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005}l\u0014AC1o]>$\u0018\r^5p]&\u0019\u00111\u0001?\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003:\u000f\t\u0007!(A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u0017\u0001B!!\u0004\u0002\u00185\u0011\u0011q\u0002\u0006\u0005\u0003#\t\u0019\"\u0001\u0003mC:<'BAA\u000b\u0003\u0011Q\u0017M^1\n\t\u0005e\u0011q\u0002\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005}\u0001c\u0001\u001f\u0002\"%\u0019\u00111E\u001f\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0007\u0005\u000bI\u0003C\u0005\u0002,)\t\t\u00111\u0001\u0002 \u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\r\u0011\u000b\u0005M\u0012\u0011H!\u000e\u0005\u0005U\"bAA\u001c{\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005m\u0012Q\u0007\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002B\u0005\u001d\u0003c\u0001\u001f\u0002D%\u0019\u0011QI\u001f\u0003\u000f\t{w\u000e\\3b]\"A\u00111\u0006\u0007\u0002\u0002\u0003\u0007\u0011)\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u0006\u0003\u001bB\u0011\"a\u000b\u000e\u0003\u0003\u0005\r!a\b\u0002\u0011!\f7\u000f[\"pI\u0016$\"!a\b\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u0003\u0002\r\u0015\fX/\u00197t)\u0011\t\t%a\u0017\t\u0011\u0005-\u0002#!AA\u0002\u0005C3\u0001AA0!\u0011\t\t'!\u001a\u000e\u0005\u0005\r$BA@ \u0013\u0011\t9'a\u0019\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0006/\u0006$8\r\u001b\t\u00039J\u0019RAEA8\u0003k\u00022\u0001PA9\u0013\r\t\u0019(\u0010\u0002\u0007\u0003:L(+\u001a4\u0011\t\u0005]\u0014QP\u0007\u0003\u0003sRA!a\u001f\u0002\u0014\u0005\u0011\u0011n\\\u0005\u0004\u001d\u0006eDCAA6\u0003\u0015\t\u0007\u000f\u001d7z+\u0011\t))a#\u0015\t\u0005\u001d\u0015Q\u0012\t\u00059\u0002\tI\tE\u00028\u0003\u0017#Q!O\u000bC\u0002iBQ\u0001U\u000bA\u0002I\u000bq!\u001e8baBd\u00170\u0006\u0003\u0002\u0014\u0006\rF\u0003BAK\u00037\u0003B\u0001PAL%&\u0019\u0011\u0011T\u001f\u0003\r=\u0003H/[8o\u0011%\tiJFA\u0001\u0002\u0004\ty*A\u0002yIA\u0002B\u0001\u0018\u0001\u0002\"B\u0019q'a)\u0005\u000be2\"\u0019\u0001\u001e\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005%\u0006\u0003BA\u0007\u0003WKA!!,\u0002\u0010\t1qJ\u00196fGR\u0004")
@InternalApi
public final class Watch<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final ActorRef targetRef;

    public static <T> Option<ActorRef> unapply(Watch<T> x$0) {
        return Watch$.MODULE$.unapply(x$0);
    }

    public static <T> Watch<T> apply(ActorRef targetRef) {
        return Watch$.MODULE$.apply(targetRef);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public ActorRef targetRef() {
        return this.targetRef;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.watch();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ Watch $outer;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

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

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return Watch.class;
            }

            public void preStart() {
                GraphStageLogic.StageActor self = this.getStageActor((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$31.$anonfun$preStart$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                self.watch(this.$outer.targetRef());
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$31 $this, Tuple2 x0$1) {
                T2 T2;
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null && (T2 = tuple2._2()) instanceof Terminated) {
                    Terminated terminated = (Terminated)T2;
                    ActorRef actorRef2 = terminated.actor();
                    ActorRef actorRef3 = $this.$outer.targetRef();
                    ActorRef actorRef4 = actorRef2;
                    if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                        $this.failStage(new WatchedActorTerminatedException("Watch", $this.$outer.targetRef()));
                        return;
                    }
                }
                if (tuple2 != null) {
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                StageLogging.$init$(this);
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.impl.fusing.Watch$$anon$31 scala.Tuple2 )}, serializedLambda);
            }
        };
    }

    public <T> Watch<T> copy(ActorRef targetRef) {
        return new Watch<T>(targetRef);
    }

    public <T> ActorRef copy$default$1() {
        return this.targetRef();
    }

    @Override
    public String productPrefix() {
        return "Watch";
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
                return this.targetRef();
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
        return x$1 instanceof Watch;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "targetRef";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Watch)) return false;
        boolean bl = true;
        if (!bl) return false;
        Watch watch = (Watch)x$1;
        ActorRef actorRef2 = this.targetRef();
        ActorRef actorRef3 = watch.targetRef();
        if (actorRef2 != null) {
            if (!((Object)actorRef2).equals(actorRef3)) return false;
            return true;
        }
        if (actorRef3 == null) return true;
        return false;
    }

    public Watch(ActorRef targetRef) {
        this.targetRef = targetRef;
        Product.$init$(this);
    }
}

