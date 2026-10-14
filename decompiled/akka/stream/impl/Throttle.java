/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.RateExceededException;
import akka.stream.ThrottleMode;
import akka.stream.ThrottleMode$Enforcing$;
import akka.stream.impl.Throttle$;
import akka.stream.impl.Throttle$TimerKey$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.NanoTimeTokenBucket;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005MvA\u0002\u0012$\u0011\u00039\u0013F\u0002\u0004,G!\u0005q\u0005\f\u0005\u0006g\u0005!\t!\u000e\u0005\bm\u0005\u0011\r\u0011\"\u00028\u0011\u0019Q\u0014\u0001)A\u0007q\u001d)1(\u0001EEy\u0019)a(\u0001EE\u007f!)1G\u0002C\u0001\u001f\"9\u0001KBA\u0001\n\u0003\n\u0006b\u0002.\u0007\u0003\u0003%\ta\u0017\u0005\b?\u001a\t\t\u0011\"\u0001a\u0011\u001d1g!!A\u0005B\u001dDqA\u001c\u0004\u0002\u0002\u0013\u0005q\u000eC\u0004u\r\u0005\u0005I\u0011I;\t\u000fY4\u0011\u0011!C!o\"9\u0001PBA\u0001\n\u0013IhAB\u0016$\u0001\u001d\nY\u0001C\u0005\u0002BA\u0011)\u0019!C\u00017\"I\u00111\t\t\u0003\u0002\u0003\u0006I\u0001\u0018\u0005\u000b\u0003\u000b\u0002\"Q1A\u0005\u0002\u0005\u001d\u0003BCA-!\t\u0005\t\u0015!\u0003\u0002J!I\u00111\f\t\u0003\u0006\u0004%\ta\u0017\u0005\n\u0003;\u0002\"\u0011!Q\u0001\nqC!\"a\u0018\u0011\u0005\u000b\u0007I\u0011AA1\u0011)\tI\u0007\u0005B\u0001B\u0003%\u00111\r\u0005\u000b\u0003W\u0002\"Q1A\u0005\u0002\u00055\u0004BCA<!\t\u0005\t\u0015!\u0003\u0002p!11\u0007\u0005C\u0001\u0003sB\u0011\"a\"\u0011\u0005\u0004%I!!#\t\u0011\u0005E\u0005\u0003)A\u0005\u0003\u0017C\u0011\"a%\u0011\u0005\u0004%\t!!#\t\u0011\u0005U\u0005\u0003)A\u0005\u0003\u0017Cq!a&\u0011\t\u0003\nI\nC\u0003w!\u0011\u0005s/\u0001\u0005UQJ|G\u000f\u001e7f\u0015\t!S%\u0001\u0003j[Bd'B\u0001\u0014(\u0003\u0019\u0019HO]3b[*\t\u0001&\u0001\u0003bW.\f\u0007C\u0001\u0016\u0002\u001b\u0005\u0019#\u0001\u0003+ie>$H\u000f\\3\u0014\u0005\u0005i\u0003C\u0001\u00182\u001b\u0005y#\"\u0001\u0019\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iz#AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005I\u0013!F!vi>l\u0017\r^5d\u001b\u0006D\u0018.\\;n\u0005V\u00148\u000f^\u000b\u0002q=\t\u0011(H\u0001\u0000\u0004Y\tU\u000f^8nCRL7-T1yS6,XNQ;sgR\u0004\u0013\u0001\u0003+j[\u0016\u00148*Z=\u0011\u0005u2Q\"A\u0001\u0003\u0011QKW.\u001a:LKf\u001cBAB\u0017A\u0007B\u0011a&Q\u0005\u0003\u0005>\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002E\u0019:\u0011QI\u0013\b\u0003\r&k\u0011a\u0012\u0006\u0003\u0011R\na\u0001\u0010:p_Rt\u0014\"\u0001\u0019\n\u0005-{\u0013a\u00029bG.\fw-Z\u0005\u0003\u001b:\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!aS\u0018\u0015\u0003q\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001*\u0011\u0005MCV\"\u0001+\u000b\u0005U3\u0016\u0001\u00027b]\u001eT\u0011aV\u0001\u0005U\u00064\u0018-\u0003\u0002Z)\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012\u0001\u0018\t\u0003]uK!AX\u0018\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005\u0005$\u0007C\u0001\u0018c\u0013\t\u0019wFA\u0002B]fDq!\u001a\u0006\u0002\u0002\u0003\u0007A,A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002QB\u0019\u0011\u000e\\1\u000e\u0003)T!a[\u0018\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002nU\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\t\u00018\u000f\u0005\u0002/c&\u0011!o\f\u0002\b\u0005>|G.Z1o\u0011\u001d)G\"!AA\u0002\u0005\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u00029\u0006AAo\\*ue&tw\rF\u0001S\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005Q\bCA*|\u0013\taHK\u0001\u0004PE*,7\r\u001e\u0015\u0003\u0003y\u00042a`A\u0003\u001b\t\t\tAC\u0002\u0002\u0004\u001d\n!\"\u00198o_R\fG/[8o\u0013\u0011\t9!!\u0001\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0003\u0001y,B!!\u0004\u00026M\u0019\u0001#a\u0004\u0011\r\u0005E\u00111FA\u0019\u001d\u0011\t\u0019\"!\n\u000f\t\u0005U\u0011\u0011\u0005\b\u0005\u0003/\tyB\u0004\u0003\u0002\u001a\u0005uab\u0001$\u0002\u001c%\t\u0001&\u0003\u0002'O%\u0011A%J\u0005\u0004\u0003G\u0019\u0013A\u00024vg&tw-\u0003\u0003\u0002(\u0005%\u0012aC$sCBD7\u000b^1hKNT1!a\t$\u0013\u0011\ti#a\f\u0003-MKW\u000e\u001d7f\u0019&tW-\u0019:He\u0006\u0004\bn\u0015;bO\u0016TA!a\n\u0002*A!\u00111GA\u001b\u0019\u0001!q!a\u000e\u0011\u0005\u0004\tIDA\u0001U#\r\tY$\u0019\t\u0004]\u0005u\u0012bAA _\t9aj\u001c;iS:<\u0017\u0001B2pgR\fQaY8ti\u0002\n1\u0001]3s+\t\tI\u0005\u0005\u0003\u0002L\u0005USBAA'\u0015\u0011\ty%!\u0015\u0002\u0011\u0011,(/\u0019;j_:T1!a\u00150\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0005\u0003/\niE\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0002\tA,'\u000fI\u0001\r[\u0006D\u0018.\\;n\u0005V\u00148\u000f^\u0001\u000e[\u0006D\u0018.\\;n\u0005V\u00148\u000f\u001e\u0011\u0002\u001f\r|7\u000f^\"bY\u000e,H.\u0019;j_:,\"!a\u0019\u0011\r9\n)'!\r]\u0013\r\t9g\f\u0002\n\rVt7\r^5p]F\n\u0001cY8ti\u000e\u000bGnY;mCRLwN\u001c\u0011\u0002\t5|G-Z\u000b\u0003\u0003_\u0002B!!\u001d\u0002t5\tQ%C\u0002\u0002v\u0015\u0012A\u0002\u00165s_R$H.Z'pI\u0016\fQ!\\8eK\u0002\"B\"a\u001f\u0002~\u0005}\u0014\u0011QAB\u0003\u000b\u0003BA\u000b\t\u00022!1\u0011\u0011I\u000eA\u0002qCq!!\u0012\u001c\u0001\u0004\tI\u0005\u0003\u0004\u0002\\m\u0001\r\u0001\u0018\u0005\b\u0003?Z\u0002\u0019AA2\u0011\u001d\tYg\u0007a\u0001\u0003_\n!C\\1o_N\u0014U\r^<fK:$vn[3ogV\u0011\u00111\u0012\t\u0004]\u00055\u0015bAAH_\t!Aj\u001c8h\u0003Mq\u0017M\\8t\u0005\u0016$x/Z3o)>\\WM\\:!\u0003U)gMZ3di&4X-T1yS6,XNQ;sgR\fa#\u001a4gK\u000e$\u0018N^3NCbLW.^7CkJ\u001cH\u000fI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002\u001c\u0006\u001d\u0006\u0003BAO\u0003Gk!!a(\u000b\u0007\u0005\u0005V%A\u0003ti\u0006<W-\u0003\u0003\u0002&\u0006}%aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005%\u0006\u00051\u0001\u0002,\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fgB!\u0011\u0011OAW\u0013\r\ty+\n\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\bF\u0001\t\u007f\u0001")
@InternalApi
public class Throttle<T>
extends GraphStages.SimpleLinearGraphStage<T> {
    private final int cost;
    private final FiniteDuration per;
    private final int maximumBurst;
    private final Function1<T, Object> costCalculation;
    private final ThrottleMode mode;
    private final long akka$stream$impl$Throttle$$nanosBetweenTokens;
    private final long effectiveMaximumBurst;

    public static int AutomaticMaximumBurst() {
        return Throttle$.MODULE$.AutomaticMaximumBurst();
    }

    public int cost() {
        return this.cost;
    }

    public FiniteDuration per() {
        return this.per;
    }

    public int maximumBurst() {
        return this.maximumBurst;
    }

    public Function1<T, Object> costCalculation() {
        return this.costCalculation;
    }

    public ThrottleMode mode() {
        return this.mode;
    }

    public long akka$stream$impl$Throttle$$nanosBetweenTokens() {
        return this.akka$stream$impl$Throttle$$nanosBetweenTokens;
    }

    public long effectiveMaximumBurst() {
        return this.effectiveMaximumBurst;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final NanoTimeTokenBucket tokenBucket;
            private T currentElement;
            private final /* synthetic */ Throttle $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private NanoTimeTokenBucket tokenBucket() {
                return this.tokenBucket;
            }

            private T currentElement() {
                return this.currentElement;
            }

            private void currentElement_$eq(T x$1) {
                this.currentElement = x$1;
            }

            public void preStart() {
                this.tokenBucket().init();
            }

            public void onUpstreamFinish() {
                if (!this.isAvailable(this.$outer.out()) || !this.isTimerActive(Throttle$TimerKey$.MODULE$)) {
                    this.completeStage();
                    return;
                }
            }

            public void onPush() {
                T elem = this.grab(this.$outer.in());
                int cost = BoxesRunTime.unboxToInt(this.$outer.costCalculation().apply(elem));
                long delayNanos = this.tokenBucket().offer(cost);
                if (delayNanos == 0L) {
                    this.push(this.$outer.out(), elem);
                    return;
                }
                if (this.$outer.mode() == ThrottleMode$Enforcing$.MODULE$) {
                    this.failStage(new RateExceededException("Maximum throttle throughput exceeded."));
                    return;
                }
                this.currentElement_$eq(elem);
                this.scheduleOnce((Object)Throttle$TimerKey$.MODULE$, new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(delayNanos)).nanos());
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onTimer(Object key) {
                this.push(this.$outer.out(), this.currentElement());
                this.currentElement_$eq(null);
                if (this.isClosed(this.$outer.in())) {
                    this.completeStage();
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.tokenBucket = new NanoTimeTokenBucket($outer.effectiveMaximumBurst(), $outer.akka$stream$impl$Throttle$$nanosBetweenTokens());
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "Throttle";
    }

    public Throttle(int cost, FiniteDuration per, int maximumBurst, Function1<T, Object> costCalculation, ThrottleMode mode) {
        this.cost = cost;
        this.per = per;
        this.maximumBurst = maximumBurst;
        this.costCalculation = costCalculation;
        this.mode = mode;
        Predef$.MODULE$.require(cost > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "cost must be > 0");
        Predef$.MODULE$.require(per.toNanos() > 0L, (Function0<Object>)(Function0<String> & Serializable)() -> "per time must be > 0");
        Predef$.MODULE$.require(per.toNanos() >= (long)cost, (Function0<Object>)(Function0<String> & Serializable)() -> "Rates larger than 1 unit / nanosecond are not supported");
        this.akka$stream$impl$Throttle$$nanosBetweenTokens = per.toNanos() / (long)cost;
        this.effectiveMaximumBurst = maximumBurst == -1 ? package$.MODULE$.max(1L, 100000000L / this.akka$stream$impl$Throttle$$nanosBetweenTokens()) : (long)maximumBurst;
        ThrottleMode throttleMode = mode;
        ThrottleMode$Enforcing$ throttleMode$Enforcing$ = ThrottleMode$Enforcing$.MODULE$;
        Predef$.MODULE$.require((throttleMode != null ? !throttleMode.equals(throttleMode$Enforcing$) : throttleMode$Enforcing$ != null) || this.effectiveMaximumBurst() >= 0L, (Function0<Object>)(Function0<String> & Serializable)() -> "maximumBurst must be > 0 in Enforcing mode");
    }
}

