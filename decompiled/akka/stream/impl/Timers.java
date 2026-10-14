/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.BackpressureTimeoutException;
import akka.stream.BidiShape;
import akka.stream.CompletionTimeoutException;
import akka.stream.FlowShape;
import akka.stream.InitialTimeoutException;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.StreamIdleTimeoutException;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.Timers$;
import akka.stream.impl.Timers$GraphStageLogicTimer$;
import akka.stream.impl.Timers$IdleTimeoutBidi$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.stream.stage.TimerGraphStageLogic;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.FiniteDuration$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t=wA\u0002*T\u0011\u00039\u0016L\u0002\u0004\\'\"\u0005q\u000b\u0018\u0005\u0006G\u0006!\t!\u001a\u0005\u0007M\u0006!\taV4\u0007\tI\f!a\u001d\u0005\nc\u0012\u0011)\u0019!C\u0001\u0003SA\u0011\"a\u000b\u0005\u0005\u0003\u0005\u000b\u0011\u00025\t\r\r$A\u0011AA\u0017\u0011\u001d\t)\u0004\u0002C!\u0003oAq!!\u0011\u0005\t\u0003\n\u0019\u0005C\u0004\u0002V\u0011!\t%a\u0016\u0007\r\u0005%\u0014AAA6\u0011%\t8B!b\u0001\n\u0003\tI\u0003C\u0005\u0002,-\u0011\t\u0011)A\u0005Q\"11m\u0003C\u0001\u0003kBq!!\u000e\f\t\u0003\n9\u0004C\u0004\u0002B-!\t%a\u001f\t\u000f\u0005U3\u0002\"\u0011\u0002X\u00191\u0011qP\u0001\u0003\u0003\u0003C\u0011\"\u001d\n\u0003\u0006\u0004%\t!!\u000b\t\u0013\u0005-\"C!A!\u0002\u0013A\u0007BB2\u0013\t\u0003\tY\tC\u0004\u00026I!\t%a\u000e\t\u000f\u0005\u0005#\u0003\"\u0011\u0002\u0012\"9\u0011Q\u000b\n\u0005B\u0005]cABAK\u0003\t\t9\nC\u0005r3\t\u0015\r\u0011\"\u0001\u0002*!I\u00111F\r\u0003\u0002\u0003\u0006I\u0001\u001b\u0005\u0007Gf!\t!!)\t\u000f\u0005U\u0012\u0004\"\u0011\u00028!9\u0011\u0011I\r\u0005B\u0005\u001d\u0006bBA+3\u0011\u0005\u0013q\u000b\u0004\u0007\u0003W\u000b!!!,\t\u0013E\u0004#Q1A\u0005\u0002\u0005%\u0002\"CA\u0016A\t\u0005\t\u0015!\u0003i\u0011\u0019\u0019\u0007\u0005\"\u0001\u0002J\"I\u0011q\u001a\u0011C\u0002\u0013\u0005\u0011\u0011\u001b\u0005\t\u00033\u0004\u0003\u0015!\u0003\u0002T\"I\u00111\u001c\u0011C\u0002\u0013\u0005\u0011Q\u001c\u0005\t\u0003C\u0004\u0003\u0015!\u0003\u0002`\"I\u00111\u001d\u0011C\u0002\u0013\u0005\u0011Q\u001d\u0005\t\u0003[\u0004\u0003\u0015!\u0003\u0002h\"I\u0011q\u001e\u0011C\u0002\u0013\u0005\u0011\u0011\u001f\u0005\t\u0003k\u0004\u0003\u0015!\u0003\u0002t\"I\u0011q\u001f\u0011C\u0002\u0013\u0005\u0011\u0011 \u0005\t\u0003w\u0004\u0003\u0015!\u0003\u00028\"9\u0011Q\u0007\u0011\u0005B\u0005]\u0002bBA!A\u0011\u0005\u0013Q \u0005\b\u0003+\u0002C\u0011IA,\r\u0019\u0011\t!\u0001\u0002\u0003\u0004!Q!QB\u0019\u0003\u0006\u0004%\t!!\u000b\t\u0013\t=\u0011G!A!\u0002\u0013A\u0007BB22\t\u0003\u0011\t\u0002C\u0004\u00026E\"\t%a\u000e\t\u000f\u0005\u0005\u0013\u0007\"\u0011\u0003\u0018!9\u0011QK\u0019\u0005B\u0005]cA\u0002B\u000e\u0003\t\u0011i\u0002C\u0005rq\t\u0015\r\u0011\"\u0001\u0002*!I\u00111\u0006\u001d\u0003\u0002\u0003\u0006I\u0001\u001b\u0005\u000b\u0005gA$Q1A\u0005\u0002\tU\u0002B\u0003B\u001fq\t\u0005\t\u0015!\u0003\u00038!11\r\u000fC\u0001\u0005\u007fA\u0011Ba\u00129\u0005\u0004%\tA!\u0013\t\u0011\t5\u0003\b)A\u0005\u0005\u0017B\u0011Ba\u00149\u0005\u0004%\tA!\u0015\t\u0011\tU\u0003\b)A\u0005\u0005'Bq!!\u000e9\t\u0003\n9\u0004C\u0005\u0002xb\u0012\r\u0011\"\u0011\u0003X!A\u00111 \u001d!\u0002\u0013\u0011\u0019\u0003C\u0004\u0002Ba\"\tE!\u0017\t\u000f\u0005U\u0003\b\"\u0011\u0002X\u001d9!QL\u0001\t\u0002\n}ca\u0002B1\u0003!\u0005%1\r\u0005\u0007G\"#\tA! \t\u0013\t}\u0004*!A\u0005B\t\u0005\u0005\"\u0003BB\u0011\u0006\u0005I\u0011\u0001BC\u0011%\u0011i\tSA\u0001\n\u0003\u0011y\tC\u0005\u0003\u0016\"\u000b\t\u0011\"\u0011\u0003\u0018\"I!Q\u0015%\u0002\u0002\u0013\u0005!q\u0015\u0005\n\u0005cC\u0015\u0011!C!\u0005gC\u0011\"!\u0016I\u0003\u0003%\t%a\u0016\t\u0013\tU\u0006*!A\u0005\n\t]\u0016A\u0002+j[\u0016\u00148O\u0003\u0002U+\u0006!\u0011.\u001c9m\u0015\t1v+\u0001\u0004tiJ,\u0017-\u001c\u0006\u00021\u0006!\u0011m[6b!\tQ\u0016!D\u0001T\u0005\u0019!\u0016.\\3sgN\u0011\u0011!\u0018\t\u0003=\u0006l\u0011a\u0018\u0006\u0002A\u0006)1oY1mC&\u0011!m\u0018\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!W\u0001\u0015i&lWm\\;u\u0007\",7m[%oi\u0016\u0014h/\u00197\u0015\u0005!\u0004\bCA5o\u001b\u0005Q'BA6m\u0003!!WO]1uS>t'BA7`\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003_*\u0014aBR5oSR,G)\u001e:bi&|g\u000eC\u0003r\u0007\u0001\u0007\u0001.A\u0004uS6,w.\u001e;\u0003\u000f%s\u0017\u000e^5bYV\u0019A/a\u0006\u0014\u0005\u0011)\b#\u0002<\u0002\u000e\u0005MabA<\u0002\b9\u0019\u00010a\u0001\u000f\u0007e\f\tA\u0004\u0002{\u007f:\u00111P`\u0007\u0002y*\u0011Q\u0010Z\u0001\u0007yI|w\u000e\u001e \n\u0003aK!AV,\n\u0005Q+\u0016bAA\u0003'\u00061a-^:j]\u001eLA!!\u0003\u0002\f\u0005YqI]1qQN#\u0018mZ3t\u0015\r\t)aU\u0005\u0005\u0003\u001f\t\tB\u0001\fTS6\u0004H.\u001a'j]\u0016\f'o\u0012:ba\"\u001cF/Y4f\u0015\u0011\tI!a\u0003\u0011\t\u0005U\u0011q\u0003\u0007\u0001\t\u001d\tI\u0002\u0002b\u0001\u00037\u0011\u0011\u0001V\t\u0005\u0003;\t\u0019\u0003E\u0002_\u0003?I1!!\t`\u0005\u001dqu\u000e\u001e5j]\u001e\u00042AXA\u0013\u0013\r\t9c\u0018\u0002\u0004\u0003:LX#\u00015\u0002\u0011QLW.Z8vi\u0002\"B!a\f\u00024A)\u0011\u0011\u0007\u0003\u0002\u00145\t\u0011\u0001C\u0003r\u000f\u0001\u0007\u0001.A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!!\u000f\u0011\t\u0005m\u0012QH\u0007\u0002+&\u0019\u0011qH+\u0003\u0015\u0005#HO]5ckR,7/A\u0006de\u0016\fG/\u001a'pO&\u001cG\u0003BA#\u0003#\u0002B!a\u0012\u0002N5\u0011\u0011\u0011\n\u0006\u0004\u0003\u0017*\u0016!B:uC\u001e,\u0017\u0002BA(\u0003\u0013\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u0003'J\u0001\u0019AA\u001d\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GCAA-!\u0011\tY&!\u001a\u000e\u0005\u0005u#\u0002BA0\u0003C\nA\u0001\\1oO*\u0011\u00111M\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002h\u0005u#AB*ue&twM\u0001\u0006D_6\u0004H.\u001a;j_:,B!!\u001c\u0002tM\u00191\"a\u001c\u0011\u000bY\fi!!\u001d\u0011\t\u0005U\u00111\u000f\u0003\b\u00033Y!\u0019AA\u000e)\u0011\t9(!\u001f\u0011\u000b\u0005E2\"!\u001d\t\u000bEt\u0001\u0019\u00015\u0015\t\u0005\u0015\u0013Q\u0010\u0005\b\u0003'\u0002\u0002\u0019AA\u001d\u0005\u0011IE\r\\3\u0016\t\u0005\r\u0015\u0011R\n\u0004%\u0005\u0015\u0005#\u0002<\u0002\u000e\u0005\u001d\u0005\u0003BA\u000b\u0003\u0013#q!!\u0007\u0013\u0005\u0004\tY\u0002\u0006\u0003\u0002\u000e\u0006=\u0005#BA\u0019%\u0005\u001d\u0005\"B9\u0016\u0001\u0004AG\u0003BA#\u0003'Cq!a\u0015\u0018\u0001\u0004\tIDA\nCC\u000e\\\u0007O]3tgV\u0014X\rV5nK>,H/\u0006\u0003\u0002\u001a\u0006}5cA\r\u0002\u001cB)a/!\u0004\u0002\u001eB!\u0011QCAP\t\u001d\tI\"\u0007b\u0001\u00037!B!a)\u0002&B)\u0011\u0011G\r\u0002\u001e\")\u0011\u000f\ba\u0001QR!\u0011QIAU\u0011\u001d\t\u0019F\ba\u0001\u0003s\u0011q\"\u00133mKRKW.Z8vi\nKG-[\u000b\u0007\u0003_\u000by,!2\u0014\u0007\u0001\n\t\f\u0005\u0004\u0002H\u0005M\u0016qW\u0005\u0005\u0003k\u000bIE\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004B\"a\u000f\u0002:\u0006u\u0016QXAb\u0003\u0007L1!a/V\u0005%\u0011\u0015\u000eZ5TQ\u0006\u0004X\r\u0005\u0003\u0002\u0016\u0005}FaBAaA\t\u0007\u00111\u0004\u0002\u0002\u0013B!\u0011QCAc\t\u001d\t9\r\tb\u0001\u00037\u0011\u0011a\u0014\u000b\u0005\u0003\u0017\fi\rE\u0004\u00022\u0001\ni,a1\t\u000bE\u001c\u0003\u0019\u00015\u0002\u0007%t\u0017'\u0006\u0002\u0002TB1\u00111HAk\u0003{K1!a6V\u0005\u0015Ie\u000e\\3u\u0003\u0011Ig.\r\u0011\u0002\u0007%t''\u0006\u0002\u0002`B1\u00111HAk\u0003\u0007\fA!\u001b83A\u0005!q.\u001e;2+\t\t9\u000f\u0005\u0004\u0002<\u0005%\u0018QX\u0005\u0004\u0003W,&AB(vi2,G/A\u0003pkR\f\u0004%\u0001\u0003pkR\u0014TCAAz!\u0019\tY$!;\u0002D\u0006)q.\u001e;3A\u0005)1\u000f[1qKV\u0011\u0011qW\u0001\u0007g\"\f\u0007/\u001a\u0011\u0015\t\u0005\u0015\u0013q \u0005\b\u0003'z\u0003\u0019AA\u001d\u00051!U\r\\1z\u0013:LG/[1m+\u0011\u0011)Aa\u0003\u0014\u0007E\u00129\u0001E\u0003w\u0003\u001b\u0011I\u0001\u0005\u0003\u0002\u0016\t-AaBA\rc\t\u0007\u00111D\u0001\u0006I\u0016d\u0017-_\u0001\u0007I\u0016d\u0017-\u001f\u0011\u0015\t\tM!Q\u0003\t\u0006\u0003c\t$\u0011\u0002\u0005\u0007\u0005\u001b!\u0004\u0019\u00015\u0015\t\u0005\u0015#\u0011\u0004\u0005\b\u0003'2\u0004\u0019AA\u001d\u0005)IE\r\\3J]*,7\r^\u000b\u0007\u0005?\u0011YCa\f\u0014\u0007a\u0012\t\u0003\u0005\u0004\u0002H\u0005M&1\u0005\t\t\u0003w\u0011)C!\u000b\u0003.%\u0019!qE+\u0003\u0013\u0019cwn^*iCB,\u0007\u0003BA\u000b\u0005W!q!!19\u0005\u0004\tY\u0002\u0005\u0003\u0002\u0016\t=BaBAdq\t\u0007!\u0011G\t\u0005\u0005S\t\u0019#\u0001\u0004j]*,7\r^\u000b\u0003\u0005o\u0001RA\u0018B\u001d\u0005[I1Aa\u000f`\u0005%1UO\\2uS>t\u0007'A\u0004j]*,7\r\u001e\u0011\u0015\r\t\u0005#1\tB#!\u001d\t\t\u0004\u000fB\u0015\u0005[AQ!]\u001fA\u0002!DqAa\r>\u0001\u0004\u00119$\u0001\u0002j]V\u0011!1\n\t\u0007\u0003w\t)N!\u000b\u0002\u0007%t\u0007%A\u0002pkR,\"Aa\u0015\u0011\r\u0005m\u0012\u0011\u001eB\u0017\u0003\u0011yW\u000f\u001e\u0011\u0016\u0005\t\rB\u0003BA#\u00057Bq!a\u0015F\u0001\u0004\tI$\u0001\u000bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d)&lWM\u001d\t\u0004\u0003cA%\u0001F$sCBD7\u000b^1hK2{w-[2US6,'o\u0005\u0004I;\n\u0015$1\u000e\t\u0004=\n\u001d\u0014b\u0001B5?\n9\u0001K]8ek\u000e$\b\u0003\u0002B7\u0005orAAa\u001c\u0003t9\u00191P!\u001d\n\u0003\u0001L1A!\u001e`\u0003\u001d\u0001\u0018mY6bO\u0016LAA!\u001f\u0003|\ta1+\u001a:jC2L'0\u00192mK*\u0019!QO0\u0015\u0005\t}\u0013!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002Z\u0005a\u0001O]8ek\u000e$\u0018I]5usV\u0011!q\u0011\t\u0004=\n%\u0015b\u0001BF?\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u00111\u0005BI\u0011%\u0011\u0019\nTA\u0001\u0002\u0004\u00119)A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u00053\u0003bAa'\u0003\"\u0006\rRB\u0001BO\u0015\r\u0011yjX\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002BR\u0005;\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!!\u0011\u0016BX!\rq&1V\u0005\u0004\u0005[{&a\u0002\"p_2,\u0017M\u001c\u0005\n\u0005's\u0015\u0011!a\u0001\u0003G\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0005\u000f\u000bAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!/\u0011\t\u0005m#1X\u0005\u0005\u0005{\u000biF\u0001\u0004PE*,7\r\u001e\u0015\u0004\u0003\t\u0005\u0007\u0003\u0002Bb\u0005\u0013l!A!2\u000b\u0007\t\u001dw+\u0001\u0006b]:|G/\u0019;j_:LAAa3\u0003F\nY\u0011J\u001c;fe:\fG.\u00119jQ\r\u0001!\u0011\u0019")
@InternalApi
public final class Timers {

    public static final class BackpressureTimeout<T>
    extends GraphStages.SimpleLinearGraphStage<T> {
        private final FiniteDuration timeout;

        public FiniteDuration timeout() {
            return this.timeout;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.backpressureTimeout();
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private long nextDeadline;
                private boolean waitingDemand;
                private final /* synthetic */ BackpressureTimeout $outer;

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

                private long nextDeadline() {
                    return this.nextDeadline;
                }

                private void nextDeadline_$eq(long x$1) {
                    this.nextDeadline = x$1;
                }

                private boolean waitingDemand() {
                    return this.waitingDemand;
                }

                private void waitingDemand_$eq(boolean x$1) {
                    this.waitingDemand = x$1;
                }

                public void onPush() {
                    this.push(this.$outer.out(), this.grab(this.$outer.in()));
                    this.nextDeadline_$eq(System.nanoTime() + this.$outer.timeout().toNanos());
                    this.waitingDemand_$eq(true);
                }

                public void onPull() {
                    this.waitingDemand_$eq(false);
                    this.pull(this.$outer.in());
                }

                public final void onTimer(Object key) {
                    if (this.waitingDemand() && this.nextDeadline() - System.nanoTime() < 0L) {
                        this.failStage(new BackpressureTimeoutException(new StringBuilder(33).append("No demand signalled in the last ").append(this.$outer.timeout().toCoarsest()).append(".").toString()));
                        return;
                    }
                }

                public void preStart() {
                    this.scheduleWithFixedDelay((Object)Timers$GraphStageLogicTimer$.MODULE$, Timers$.MODULE$.timeoutCheckInterval(this.$outer.timeout()), Timers$.MODULE$.timeoutCheckInterval(this.$outer.timeout()));
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.nextDeadline = System.nanoTime() + $outer.timeout().toNanos();
                    this.waitingDemand = true;
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public String toString() {
            return "BackpressureTimeout";
        }

        public BackpressureTimeout(FiniteDuration timeout2) {
            this.timeout = timeout2;
        }
    }

    public static final class Completion<T>
    extends GraphStages.SimpleLinearGraphStage<T> {
        private final FiniteDuration timeout;

        public FiniteDuration timeout() {
            return this.timeout;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.completion();
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private final /* synthetic */ Completion $outer;

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
                    this.push(this.$outer.out(), this.grab(this.$outer.in()));
                }

                public void onPull() {
                    this.pull(this.$outer.in());
                }

                public final void onTimer(Object key) {
                    this.failStage(new CompletionTimeoutException(new StringBuilder(38).append("The stream has not been completed in ").append(this.$outer.timeout().toCoarsest()).append(".").toString()));
                }

                public void preStart() {
                    this.scheduleOnce((Object)Timers$GraphStageLogicTimer$.MODULE$, this.$outer.timeout());
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
            };
        }

        public String toString() {
            return "CompletionTimeout";
        }

        public Completion(FiniteDuration timeout2) {
            this.timeout = timeout2;
        }
    }

    public static final class DelayInitial<T>
    extends GraphStages.SimpleLinearGraphStage<T> {
        private final FiniteDuration delay;

        public FiniteDuration delay() {
            return this.delay;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.delayInitial();
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private boolean open;
                private final /* synthetic */ DelayInitial $outer;

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

                private boolean open() {
                    return this.open;
                }

                private void open_$eq(boolean x$1) {
                    this.open = x$1;
                }

                public void preStart() {
                    FiniteDuration finiteDuration = this.$outer.delay();
                    FiniteDuration finiteDuration2 = Duration$.MODULE$.Zero();
                    if (!(finiteDuration != null ? !((Object)finiteDuration).equals(finiteDuration2) : finiteDuration2 != null)) {
                        this.open_$eq(true);
                        return;
                    }
                    this.scheduleOnce((Object)Timers$GraphStageLogicTimer$.MODULE$, this.$outer.delay());
                }

                public void onPush() {
                    this.push(this.$outer.out(), this.grab(this.$outer.in()));
                }

                public void onPull() {
                    if (this.open()) {
                        this.pull(this.$outer.in());
                        return;
                    }
                }

                public void onTimer(Object timerKey) {
                    this.open_$eq(true);
                    if (this.isAvailable(this.$outer.out())) {
                        this.pull(this.$outer.in());
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
                    this.open = false;
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public String toString() {
            return "DelayTimer";
        }

        public DelayInitial(FiniteDuration delay) {
            this.delay = delay;
        }
    }

    public static final class Idle<T>
    extends GraphStages.SimpleLinearGraphStage<T> {
        private final FiniteDuration timeout;

        public FiniteDuration timeout() {
            return this.timeout;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.idle();
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private long nextDeadline;
                private final /* synthetic */ Idle $outer;

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

                private long nextDeadline() {
                    return this.nextDeadline;
                }

                private void nextDeadline_$eq(long x$1) {
                    this.nextDeadline = x$1;
                }

                public void onPush() {
                    this.nextDeadline_$eq(System.nanoTime() + this.$outer.timeout().toNanos());
                    this.push(this.$outer.out(), this.grab(this.$outer.in()));
                }

                public void onPull() {
                    this.pull(this.$outer.in());
                }

                public final void onTimer(Object key) {
                    if (this.nextDeadline() - System.nanoTime() < 0L) {
                        this.failStage(new StreamIdleTimeoutException(new StringBuilder(32).append("No elements passed in the last ").append(this.$outer.timeout().toCoarsest()).append(".").toString()));
                        return;
                    }
                }

                public void preStart() {
                    this.scheduleWithFixedDelay((Object)Timers$GraphStageLogicTimer$.MODULE$, Timers$.MODULE$.timeoutCheckInterval(this.$outer.timeout()), Timers$.MODULE$.timeoutCheckInterval(this.$outer.timeout()));
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.nextDeadline = System.nanoTime() + $outer.timeout().toNanos();
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public String toString() {
            return "IdleTimeout";
        }

        public Idle(FiniteDuration timeout2) {
            this.timeout = timeout2;
        }
    }

    public static final class IdleInject<I, O>
    extends GraphStage<FlowShape<I, O>> {
        private final FiniteDuration timeout;
        private final Function0<O> inject;
        private final Inlet<I> in;
        private final Outlet<O> out;
        private final FlowShape<I, O> shape;

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public Function0<O> inject() {
            return this.inject;
        }

        public Inlet<I> in() {
            return this.in;
        }

        public Outlet<O> out() {
            return this.out;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.idleInject();
        }

        @Override
        public FlowShape<I, O> shape() {
            return this.shape;
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new StageLogging(this){
                private long nextDeadline;
                private final ContextPropagation contextPropagation;
                private LoggingAdapter akka$stream$stage$StageLogging$$_log;
                private final /* synthetic */ IdleInject $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onUpstreamFailure(Throwable ex) throws Exception {
                    InHandler.onUpstreamFailure$(this, ex);
                }

                public Class<?> logSource() {
                    return StageLogging.logSource$(this);
                }

                public LoggingAdapter log() {
                    return StageLogging.log$(this);
                }

                public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                    return this.akka$stream$stage$StageLogging$$_log;
                }

                public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                    this.akka$stream$stage$StageLogging$$_log = x$1;
                }

                private long nextDeadline() {
                    return this.nextDeadline;
                }

                private void nextDeadline_$eq(long x$1) {
                    this.nextDeadline = x$1;
                }

                private ContextPropagation contextPropagation() {
                    return this.contextPropagation;
                }

                public void preStart() {
                    this.pull(this.$outer.in());
                }

                public void onPush() {
                    this.nextDeadline_$eq(System.nanoTime() + this.$outer.timeout().toNanos());
                    this.cancelTimer(Timers$GraphStageLogicTimer$.MODULE$);
                    if (this.isAvailable(this.$outer.out())) {
                        this.push(this.$outer.out(), this.grab(this.$outer.in()));
                        this.pull(this.$outer.in());
                        return;
                    }
                    this.contextPropagation().suspendContext();
                }

                public void onUpstreamFinish() {
                    if (!this.isAvailable(this.$outer.in())) {
                        this.completeStage();
                        return;
                    }
                }

                public void onPull() {
                    if (this.isAvailable(this.$outer.in())) {
                        this.contextPropagation().resumeContext();
                        this.push(this.$outer.out(), this.grab(this.$outer.in()));
                        if (this.isClosed(this.$outer.in())) {
                            this.completeStage();
                            return;
                        }
                        this.pull(this.$outer.in());
                        return;
                    }
                    this.emitInjectedElementOrReschedule(false);
                }

                private void emitInjectedElementOrReschedule(boolean onTimer) {
                    long now = System.nanoTime();
                    long diff = now - this.nextDeadline();
                    if (diff < 0L) {
                        if (onTimer) {
                            this.log().warning(new StringBuilder(183).append("Timer should have triggered only after deadline but now is ").append(now).append(" and deadline was ").append(this.nextDeadline()).append(" diff ").append(diff).append(" (JVM System.nanoTime non-monotonic). Rescheduling, timer may not be triggered at the expected time.").toString());
                        }
                        this.scheduleOnce((Object)Timers$GraphStageLogicTimer$.MODULE$, FiniteDuration$.MODULE$.apply(-diff, TimeUnit.NANOSECONDS));
                        return;
                    }
                    this.push(this.$outer.out(), this.$outer.inject().apply());
                    this.nextDeadline_$eq(now + this.$outer.timeout().toNanos());
                }

                public void onTimer(Object timerKey) {
                    this.emitInjectedElementOrReschedule(true);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    StageLogging.$init$(this);
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.nextDeadline = System.nanoTime() + $outer.timeout().toNanos();
                    this.contextPropagation = ContextPropagation$.MODULE$.apply();
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public String toString() {
            return "IdleInject";
        }

        public IdleInject(FiniteDuration timeout2, Function0<O> inject) {
            this.timeout = timeout2;
            this.inject = inject;
            this.in = Inlet$.MODULE$.apply("IdleInject.in");
            this.out = Outlet$.MODULE$.apply("IdleInject.out");
            this.shape = new FlowShape<I, O>(this.in(), this.out());
        }
    }

    public static final class IdleTimeoutBidi<I, O>
    extends GraphStage<BidiShape<I, I, O, O>> {
        private final FiniteDuration timeout;
        private final Inlet<I> in1;
        private final Inlet<O> in2;
        private final Outlet<I> out1;
        private final Outlet<O> out2;
        private final BidiShape<I, I, O, O> shape;

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public Inlet<I> in1() {
            return this.in1;
        }

        public Inlet<O> in2() {
            return this.in2;
        }

        public Outlet<I> out1() {
            return this.out1;
        }

        public Outlet<O> out2() {
            return this.out2;
        }

        @Override
        public BidiShape<I, I, O, O> shape() {
            return this.shape;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.idleTimeoutBidi();
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new TimerGraphStageLogic(this){
                private long nextDeadline;
                private final /* synthetic */ IdleTimeoutBidi $outer;

                private long nextDeadline() {
                    return this.nextDeadline;
                }

                private void nextDeadline_$eq(long x$1) {
                    this.nextDeadline = x$1;
                }

                public void akka$stream$impl$Timers$IdleTimeoutBidi$$anon$$onActivity() {
                    this.nextDeadline_$eq(System.nanoTime() + this.$outer.timeout().toNanos());
                }

                public final void onTimer(Object key) {
                    if (this.nextDeadline() - System.nanoTime() < 0L) {
                        this.failStage(new StreamIdleTimeoutException(new StringBuilder(32).append("No elements passed in the last ").append(this.$outer.timeout().toCoarsest()).append(".").toString()));
                        return;
                    }
                }

                public void preStart() {
                    this.scheduleWithFixedDelay((Object)Timers$GraphStageLogicTimer$.MODULE$, Timers$.MODULE$.timeoutCheckInterval(this.$outer.timeout()), Timers$.MODULE$.timeoutCheckInterval(this.$outer.timeout()));
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    this.nextDeadline = System.nanoTime() + $outer.timeout().toNanos();
                    this.setHandlers($outer.in1(), $outer.out1(), new IdleTimeoutBidi$$anon$5$IdleBidiHandler<I>(this, $outer.in1(), $outer.out1()));
                    this.setHandlers($outer.in2(), $outer.out2(), new IdleTimeoutBidi$$anon$5$IdleBidiHandler<O>(this, $outer.in2(), $outer.out2()));
                }
            };
        }

        public String toString() {
            return "IdleTimeoutBidi";
        }

        public IdleTimeoutBidi(FiniteDuration timeout2) {
            this.timeout = timeout2;
            this.in1 = Inlet$.MODULE$.apply("in1");
            this.in2 = Inlet$.MODULE$.apply("in2");
            this.out1 = Outlet$.MODULE$.apply("out1");
            this.out2 = Outlet$.MODULE$.apply("out2");
            this.shape = new BidiShape<I, I, O, O>(this.in1(), this.out1(), this.in2(), this.out2());
        }
    }

    public static final class Initial<T>
    extends GraphStages.SimpleLinearGraphStage<T> {
        private final FiniteDuration timeout;

        public FiniteDuration timeout() {
            return this.timeout;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.initial();
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new InHandler(this){
                private boolean initialHasPassed;
                private final /* synthetic */ Initial $outer;

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

                private boolean initialHasPassed() {
                    return this.initialHasPassed;
                }

                private void initialHasPassed_$eq(boolean x$1) {
                    this.initialHasPassed = x$1;
                }

                public void onPush() {
                    this.initialHasPassed_$eq(true);
                    this.push(this.$outer.out(), this.grab(this.$outer.in()));
                }

                public void onPull() {
                    this.pull(this.$outer.in());
                }

                public final void onTimer(Object key) {
                    if (!this.initialHasPassed()) {
                        this.failStage(new InitialTimeoutException(new StringBuilder(49).append("The first element has not yet passed through in ").append(this.$outer.timeout().toCoarsest()).append(".").toString()));
                        return;
                    }
                }

                public void preStart() {
                    this.scheduleOnce((Object)Timers$GraphStageLogicTimer$.MODULE$, this.$outer.timeout());
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.initialHasPassed = false;
                    this.setHandlers($outer.in(), $outer.out(), this);
                }
            };
        }

        public String toString() {
            return "InitialTimeout";
        }

        public Initial(FiniteDuration timeout2) {
            this.timeout = timeout2;
        }
    }
}

