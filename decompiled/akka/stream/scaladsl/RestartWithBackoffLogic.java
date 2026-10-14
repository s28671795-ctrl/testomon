/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.event.Logging$;
import akka.event.Logging$LogLevel$;
import akka.pattern.BackoffSupervisor$;
import akka.stream.Attributes;
import akka.stream.Attributes$LogLevels$;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.RestartSettings;
import akka.stream.Shape;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.TimerGraphStageLogicWithLogging;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u00055g!B\u000f\u001f\u0003\u0013)\u0003\u0002C\u0017\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0018\t\u0011m\u0002!\u0011!Q\u0001\nqB\u0001\"\u0013\u0001\u0003\u0002\u0003\u0006IA\u0013\u0005\t\u001b\u0002\u0011\t\u0011)A\u0005\u001d\"A\u0011\u000b\u0001B\u0001B\u0003%!\u000bC\u0003V\u0001\u0011\u0005a\u000bC\u0004_\u0001\u0001\u0007I\u0011A0\t\u000f\r\u0004\u0001\u0019!C\u0001I\"1!\u000e\u0001Q!\n\u0001Dqa\u001b\u0001A\u0002\u0013\u0005A\u000eC\u0004v\u0001\u0001\u0007I\u0011\u0001<\t\ra\u0004\u0001\u0015)\u0003n\u0011\u001dI\b\u00011A\u0005\u0002iDqa\u001f\u0001A\u0002\u0013\u0005A\u0010\u0003\u0004\u007f\u0001\u0001\u0006KA\u0015\u0005\u0007\u007f\u0002!\t&!\u0001\t\u000f\u0005e\u0001A\"\u0005\u0002\u001c!9\u0011Q\u0004\u0001\u0007\u0012\u0005m\u0001BBA\u0010\u0001\u0011%!\u0010C\u0004\u0002\"\u0001!)\"a\t\t\u000f\u0005\r\u0003\u0001\"\u0003\u0002F!9\u0011q\f\u0001\u0005\n\u0005\u0005\u0004\"CAF\u0001E\u0005I\u0011BAG\u0011\u001d\t\u0019\u000b\u0001C\u000b\u0003KCq!!0\u0001\t+\ty\fC\u0004\u0002B\u0002!)\"a\u0007\t\u000f\u0005\r\u0007\u0001\"\u0015\u0002F\"9\u00111\u001a\u0001\u0005B\u0005m!a\u0006*fgR\f'\u000f^,ji\"\u0014\u0015mY6pM\u001adunZ5d\u0015\ty\u0002%\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\t\t#%\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002G\u0005!\u0011m[6b\u0007\u0001)\"A\n \u0014\u0005\u00019\u0003C\u0001\u0015,\u001b\u0005I#B\u0001\u0016!\u0003\u0015\u0019H/Y4f\u0013\ta\u0013FA\u0010US6,'o\u0012:ba\"\u001cF/Y4f\u0019><\u0017nY,ji\"dunZ4j]\u001e\fAA\\1nKB\u0011q\u0006\u000f\b\u0003aY\u0002\"!\r\u001b\u000e\u0003IR!a\r\u0013\u0002\rq\u0012xn\u001c;?\u0015\u0005)\u0014!B:dC2\f\u0017BA\u001c5\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011H\u000f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005]\"\u0014!B:iCB,\u0007CA\u001f?\u0019\u0001!Qa\u0010\u0001C\u0002\u0001\u0013\u0011aU\t\u0003\u0003\u0016\u0003\"AQ\"\u000e\u0003QJ!\u0001\u0012\u001b\u0003\u000f9{G\u000f[5oOB\u0011aiR\u0007\u0002A%\u0011\u0001\n\t\u0002\u0006'\"\f\u0007/Z\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\t\u0003\r.K!\u0001\u0014\u0011\u0003\u0015\u0005#HO]5ckR,7/\u0001\u0005tKR$\u0018N\\4t!\t1u*\u0003\u0002QA\ty!+Z:uCJ$8+\u001a;uS:<7/\u0001\bp]2LxJ\u001c$bS2,(/Z:\u0011\u0005\t\u001b\u0016B\u0001+5\u0005\u001d\u0011un\u001c7fC:\fa\u0001P5oSRtDCB,Z5ncV\fE\u0002Y\u0001qj\u0011A\b\u0005\u0006[\u0019\u0001\rA\f\u0005\u0006w\u0019\u0001\r\u0001\u0010\u0005\u0006\u0013\u001a\u0001\rA\u0013\u0005\u0006\u001b\u001a\u0001\rA\u0014\u0005\u0006#\u001a\u0001\rAU\u0001\re\u0016\u001cH/\u0019:u\u0007>,h\u000e^\u000b\u0002AB\u0011!)Y\u0005\u0003ER\u00121!\u00138u\u0003A\u0011Xm\u001d;beR\u001cu.\u001e8u?\u0012*\u0017\u000f\u0006\u0002fQB\u0011!IZ\u0005\u0003OR\u0012A!\u00168ji\"9\u0011\u000eCA\u0001\u0002\u0004\u0001\u0017a\u0001=%c\u0005i!/Z:uCJ$8i\\;oi\u0002\nQB]3tKR$U-\u00193mS:,W#A7\u0011\u00059\u001cX\"A8\u000b\u0005A\f\u0018\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005I$\u0014AC2p]\u000e,(O]3oi&\u0011Ao\u001c\u0002\t\t\u0016\fG\r\\5oK\u0006\t\"/Z:fi\u0012+\u0017\r\u001a7j]\u0016|F%Z9\u0015\u0005\u0015<\bbB5\f\u0003\u0003\u0005\r!\\\u0001\u000fe\u0016\u001cX\r\u001e#fC\u0012d\u0017N\\3!\u0003%1\u0017N\\5tQ&tw-F\u0001S\u000351\u0017N\\5tQ&twm\u0018\u0013fcR\u0011Q- \u0005\bS:\t\t\u00111\u0001S\u0003)1\u0017N\\5tQ&tw\rI\u0001\nY><7k\\;sG\u0016,\"!a\u00011\t\u0005\u0015\u0011Q\u0002\t\u0006_\u0005\u001d\u00111B\u0005\u0004\u0003\u0013Q$!B\"mCN\u001c\bcA\u001f\u0002\u000e\u0011Y\u0011q\u0002\t\u0002\u0002\u0003\u0005)\u0011AA\t\u0005\ryFeN\t\u0004\u0003\u0006M\u0001c\u0001\"\u0002\u0016%\u0019\u0011q\u0003\u001b\u0003\u0007\u0005s\u00170\u0001\u0006ti\u0006\u0014Ho\u0012:ba\"$\u0012!Z\u0001\bE\u0006\u001c7n\u001c4g\u00039awnZ4j]\u001e,e.\u00192mK\u0012\fab\u0019:fCR,7+\u001e2J]2,G/\u0006\u0003\u0002&\u0005UB\u0003BA\u0014\u0003s\u0001b!!\u000b\u0002,\u0005MR\"\u0001\u0001\n\t\u00055\u0012q\u0006\u0002\r'V\u00147+\u001b8l\u0013:dW\r^\u0005\u0004\u0003cI#aD$sCBD7\u000b^1hK2{w-[2\u0011\u0007u\n)\u0004B\u0004\u00028Q\u0011\r!!\u0005\u0003\u0003QCq!a\u000f\u0015\u0001\u0004\ti$A\u0002pkR\u0004RARA \u0003gI1!!\u0011!\u0005\u0019yU\u000f\u001e7fi\u0006AAn\\4MKZ,G\u000e\u0006\u0003\u0002H\u0005m\u0003\u0003BA%\u0003+rA!a\u0013\u0002R5\u0011\u0011Q\n\u0006\u0004\u0003\u001f\u0012\u0013!B3wK:$\u0018\u0002BA*\u0003\u001b\nq\u0001T8hO&tw-\u0003\u0003\u0002X\u0005e#\u0001\u0003'pO2+g/\u001a7\u000b\t\u0005M\u0013Q\n\u0005\b\u0003;*\u0002\u0019AA$\u0003-i\u0017N\u001c'pO2+g/\u001a7\u0002\u000b1|w-\u0013;\u0015\u000f\u0015\f\u0019'a\u001a\u0002\n\"1\u0011Q\r\fA\u00029\nq!\\3tg\u0006<W\rC\u0004\u0002jY\u0001\r!a\u001b\u0002\u0007\u0015D8\r\u0005\u0004\u0002n\u0005M\u0014qO\u0007\u0003\u0003_R1!!\u001d#\u0003\u0011)H/\u001b7\n\t\u0005U\u0014q\u000e\u0002\n\u001fB$\u0018n\u001c8WC2\u0004B!!\u001f\u0002\u0004:!\u00111PA@\u001d\r\t\u0014QP\u0005\u0002k%\u0019\u0011\u0011\u0011\u001b\u0002\u000fA\f7m[1hK&!\u0011QQAD\u0005%!\u0006N]8xC\ndWMC\u0002\u0002\u0002RB\u0011\"!\u0018\u0017!\u0003\u0005\r!a\u0012\u0002\u001f1|w-\u0013;%I\u00164\u0017-\u001e7uIM*\"!a$+\t\u0005\u001d\u0013\u0011S\u0016\u0003\u0003'\u0003B!!&\u0002 6\u0011\u0011q\u0013\u0006\u0005\u00033\u000bY*A\u0005v]\u000eDWmY6fI*\u0019\u0011Q\u0014\u001b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\"\u0006]%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006y1M]3bi\u0016\u001cVOY(vi2,G/\u0006\u0003\u0002(\u0006EF\u0003BAU\u0003g\u0003b!!\u000b\u0002,\u0006=\u0016\u0002BAW\u0003_\u0011qbU;c'>,(oY3PkRdW\r\u001e\t\u0004{\u0005EFaBA\u001c1\t\u0007\u0011\u0011\u0003\u0005\b\u0003kC\u0002\u0019AA\\\u0003\tIg\u000eE\u0003G\u0003s\u000by+C\u0002\u0002<\u0002\u0012Q!\u00138mKR\f!#\\1y%\u0016\u001cH/\u0019:ugJ+\u0017m\u00195fIR\t!+\u0001\u000btG\",G-\u001e7f%\u0016\u001cH/\u0019:u)&lWM]\u0001\b_:$\u0016.\\3s)\r)\u0017q\u0019\u0005\b\u0003\u0013\\\u0002\u0019AA\n\u0003!!\u0018.\\3s\u0017\u0016L\u0018\u0001\u00039sKN#\u0018M\u001d;")
public abstract class RestartWithBackoffLogic<S extends Shape>
extends TimerGraphStageLogicWithLogging {
    private final String name;
    private final Attributes inheritedAttributes;
    public final RestartSettings akka$stream$scaladsl$RestartWithBackoffLogic$$settings;
    public final boolean akka$stream$scaladsl$RestartWithBackoffLogic$$onlyOnFailures;
    private int restartCount;
    private Deadline resetDeadline;
    private boolean finishing;

    public int restartCount() {
        return this.restartCount;
    }

    public void restartCount_$eq(int x$1) {
        this.restartCount = x$1;
    }

    public Deadline resetDeadline() {
        return this.resetDeadline;
    }

    public void resetDeadline_$eq(Deadline x$1) {
        this.resetDeadline = x$1;
    }

    public boolean finishing() {
        return this.finishing;
    }

    public void finishing_$eq(boolean x$1) {
        this.finishing = x$1;
    }

    @Override
    public Class<?> logSource() {
        return RestartWithBackoffLogic.class;
    }

    public abstract void startGraph();

    public abstract void backoff();

    private boolean loggingEnabled() {
        Option option = this.inheritedAttributes.get(ClassTag$.MODULE$.apply(Attributes.LogLevels.class));
        if (option instanceof Some) {
            Some some = (Some)option;
            Attributes.LogLevels levels = (Attributes.LogLevels)some.value();
            return levels.onFailure() != Attributes$LogLevels$.MODULE$.Off();
        }
        if (None$.MODULE$.equals(option)) {
            return Attributes$LogLevels$.MODULE$.defaultErrorLevel(this.materializer().system()) != Attributes$LogLevels$.MODULE$.Off();
        }
        throw new MatchError(option);
    }

    public final <T> GraphStageLogic.SubSinkInlet<T> createSubInlet(Outlet<T> out) {
        GraphStageLogic.SubSinkInlet sinkIn = new GraphStageLogic.SubSinkInlet(this, new StringBuilder(24).append("RestartWithBackoff").append(this.name).append(".subIn").toString());
        sinkIn.setHandler(new InHandler(this, out, sinkIn){
            private final /* synthetic */ RestartWithBackoffLogic $outer;
            private final Outlet out$1;
            private final GraphStageLogic.SubSinkInlet sinkIn$1;

            public void onPush() {
                this.$outer.push(this.out$1, this.sinkIn$1.grab());
            }

            public void onUpstreamFinish() {
                if (this.$outer.finishing() || this.$outer.maxRestartsReached() || this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$onlyOnFailures) {
                    this.$outer.complete(this.out$1);
                    return;
                }
                String string = new StringBuilder(38).append("Restarting stream due to completion [").append(this.$outer.restartCount() + 1).append("]").toString();
                OptionVal$.MODULE$.None();
                this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$logIt(string, null, Logging$.MODULE$.InfoLevel());
                this.$outer.scheduleRestartTimer();
            }

            public void onUpstreamFailure(Throwable ex) {
                if (this.$outer.finishing() || this.$outer.maxRestartsReached() || !BoxesRunTime.unboxToBoolean(this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.restartOn().apply(ex))) {
                    this.$outer.fail(this.out$1, ex);
                    return;
                }
                this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$logIt(new StringBuilder(37).append("Restarting stream due to failure [").append(this.$outer.restartCount() + 1).append("]: ").append(ex).toString(), OptionVal$Some$.MODULE$.apply(ex), this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$logIt$default$3());
                this.$outer.scheduleRestartTimer();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.out$1 = out$1;
                this.sinkIn$1 = sinkIn$1;
                InHandler.$init$(this);
            }
        });
        this.setHandler(out, new OutHandler(this, sinkIn){
            private final /* synthetic */ RestartWithBackoffLogic $outer;
            private final GraphStageLogic.SubSinkInlet sinkIn$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onPull() {
                this.sinkIn$1.pull();
            }

            public void onDownstreamFinish(Throwable cause) {
                this.$outer.finishing_$eq(true);
                this.sinkIn$1.cancel(cause);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.sinkIn$1 = sinkIn$1;
                OutHandler.$init$(this);
            }
        });
        return sinkIn;
    }

    private int logLevel(int minLogLevel) {
        int level;
        int n = level = this.restartCount() >= this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.logSettings().criticalLogLevelAfter() ? this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.logSettings().criticalLogLevel() : this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.logSettings().logLevel();
        if (Logging$LogLevel$.MODULE$.$greater$eq$extension(level, minLogLevel) || level == Logging$.MODULE$.OffLevel()) {
            return level;
        }
        return minLogLevel;
    }

    public void akka$stream$scaladsl$RestartWithBackoffLogic$$logIt(String message, Throwable exc, int minLogLevel) {
        if (this.loggingEnabled()) {
            int n = this.logLevel(minLogLevel);
            if (Logging$.MODULE$.ErrorLevel() == n) {
                Throwable throwable = exc;
                Throwable throwable2 = OptionVal$Some$.MODULE$.unapply(throwable);
                if (!OptionVal$.MODULE$.isEmpty$extension(throwable2)) {
                    Throwable e = OptionVal$.MODULE$.get$extension(throwable2);
                    this.log().error(e, message);
                } else {
                    this.log().error(message);
                }
                return;
            }
            if (Logging$.MODULE$.WarningLevel() == n) {
                if (this.log().isWarningEnabled()) {
                    Throwable e;
                    Throwable throwable = exc;
                    Throwable throwable3 = OptionVal$Some$.MODULE$.unapply(throwable);
                    if (!OptionVal$.MODULE$.isEmpty$extension(throwable3) && !((e = OptionVal$.MODULE$.get$extension(throwable3)) instanceof NoStackTrace)) {
                        this.log().warning(new StringBuilder(0).append(message).append(Logging$.MODULE$.stackTraceFor(e)).toString());
                    } else {
                        this.log().warning(message);
                    }
                    return;
                }
                return;
            }
            if (Logging$.MODULE$.InfoLevel() == n) {
                this.log().info(message);
                return;
            }
            if (Logging$.MODULE$.DebugLevel() == n) {
                this.log().debug(message);
                return;
            }
            return;
        }
    }

    public int akka$stream$scaladsl$RestartWithBackoffLogic$$logIt$default$3() {
        return Logging$.MODULE$.ErrorLevel();
    }

    public final <T> GraphStageLogic.SubSourceOutlet<T> createSubOutlet(Inlet<T> in) {
        GraphStageLogic.SubSourceOutlet sourceOut = new GraphStageLogic.SubSourceOutlet(this, new StringBuilder(25).append("RestartWithBackoff").append(this.name).append(".subOut").toString());
        sourceOut.setHandler(new OutHandler(this, in, sourceOut){
            private final /* synthetic */ RestartWithBackoffLogic $outer;
            private final Inlet in$1;
            private final GraphStageLogic.SubSourceOutlet sourceOut$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onPull() {
                if (this.$outer.isAvailable(this.in$1)) {
                    this.sourceOut$1.push(this.$outer.grab(this.in$1));
                    return;
                }
                if (!this.$outer.hasBeenPulled(this.in$1)) {
                    this.$outer.pull(this.in$1);
                    return;
                }
            }

            public void onDownstreamFinish(Throwable cause) {
                if (this.$outer.finishing() || this.$outer.maxRestartsReached() || this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$onlyOnFailures || !BoxesRunTime.unboxToBoolean(this.$outer.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.restartOn().apply(cause))) {
                    this.$outer.cancel(this.in$1, cause);
                    return;
                }
                this.$outer.scheduleRestartTimer();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.in$1 = in$1;
                this.sourceOut$1 = sourceOut$1;
                OutHandler.$init$(this);
            }
        });
        this.setHandler(in, new InHandler(this, sourceOut, in){
            private final /* synthetic */ RestartWithBackoffLogic $outer;
            private final GraphStageLogic.SubSourceOutlet sourceOut$1;
            private final Inlet in$1;

            public void onPush() {
                if (this.sourceOut$1.isAvailable()) {
                    this.sourceOut$1.push(this.$outer.grab(this.in$1));
                    return;
                }
            }

            public void onUpstreamFinish() {
                this.$outer.finishing_$eq(true);
                this.sourceOut$1.complete();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.$outer.finishing_$eq(true);
                this.sourceOut$1.fail(ex);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.sourceOut$1 = sourceOut$1;
                this.in$1 = in$1;
                InHandler.$init$(this);
            }
        });
        return sourceOut;
    }

    public final boolean maxRestartsReached() {
        if (this.resetDeadline().isOverdue()) {
            this.log().debug("Last restart attempt was more than {} ago, resetting restart count", this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.maxRestartsWithin().toCoarsest());
            this.restartCount_$eq(0);
        }
        return this.restartCount() == this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.maxRestarts();
    }

    public final void scheduleRestartTimer() {
        FiniteDuration restartDelay = BackoffSupervisor$.MODULE$.calculateDelay(this.restartCount(), this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.minBackoff(), this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.maxBackoff(), this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.randomFactor());
        this.log().debug("Restarting graph in {}", restartDelay.toCoarsest());
        this.scheduleOnce((Object)"RestartTimer", restartDelay);
        this.restartCount_$eq(this.restartCount() + 1);
        this.backoff();
    }

    @Override
    public void onTimer(Object timerKey) {
        this.startGraph();
        this.resetDeadline_$eq(this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings.maxRestartsWithin().fromNow());
    }

    @Override
    public void preStart() {
        this.startGraph();
    }

    public RestartWithBackoffLogic(String name, S shape, Attributes inheritedAttributes, RestartSettings settings, boolean onlyOnFailures) {
        this.name = name;
        this.inheritedAttributes = inheritedAttributes;
        this.akka$stream$scaladsl$RestartWithBackoffLogic$$settings = settings;
        this.akka$stream$scaladsl$RestartWithBackoffLogic$$onlyOnFailures = onlyOnFailures;
        super((Shape)shape);
        this.restartCount = 0;
        this.resetDeadline = settings.maxRestartsWithin().fromNow();
        this.finishing = false;
    }
}

