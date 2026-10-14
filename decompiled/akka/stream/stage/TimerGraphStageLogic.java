/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.actor.Cancellable;
import akka.stream.Shape;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.TimerMessages;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.time.Duration;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.concurrent.duration.FiniteDuration;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005=f!\u0002\u000e\u001c\u0003\u0003\u0011\u0003\u0002C\u0014\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0015\t\u000b1\u0002A\u0011A\u0017\t\u000fA\u0002!\u0019!C\u0005c!1q\t\u0001Q\u0001\nIBq\u0001\u0013\u0001C\u0002\u0013%\u0011\n\u0003\u0004R\u0001\u0001\u0006IA\u0013\u0005\n%\u0002\u0001\r\u00111A\u0005\nMC\u0011B\u0017\u0001A\u0002\u0003\u0007I\u0011B.\t\u0013\u0005\u0004\u0001\u0019!A!B\u0013!\u0006\"\u00022\u0001\t\u0013\u0019\u0006\"B2\u0001\t\u0013!\u0007\"B4\u0001\t#A\u0007\u0002CA\u0004\u0001\u0011ES$!\u0003\t\u000f\u0005-\u0001\u0001\"\u0006\u0002\u000e!9\u00111\u0002\u0001\u0005\u0016\u0005\u0015\u0002bBA\u001e\u0001\u0011U\u0011Q\b\u0005\b\u0003w\u0001AQCA$\u0011\u001d\t\t\u0006\u0001C\u000b\u0003'Bq!!\u0015\u0001\t+\tY\u0006C\u0004\u0002d\u0001!)\"!\u001a\t\u000f\u0005\r\u0004\u0001\"\u0006\u0002\u0002\"9\u00111\u0012\u0001\u0005\u0016\u00055\u0005bBAF\u0001\u0011U\u0011Q\u0013\u0005\b\u0003;\u0003AQCAP\u0011\u001d\t\u0019\u000b\u0001C\u000b\u0003K\u0013A\u0003V5nKJ<%/\u00199i'R\fw-\u001a'pO&\u001c'B\u0001\u000f\u001e\u0003\u0015\u0019H/Y4f\u0015\tqr$\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002A\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0012\u0011\u0005\u0011*S\"A\u000e\n\u0005\u0019Z\"aD$sCBD7\u000b^1hK2{w-[2\u0002\r}\u001b\b.\u00199f!\tI#&D\u0001\u001e\u0013\tYSDA\u0003TQ\u0006\u0004X-\u0001\u0004=S:LGO\u0010\u000b\u0003]=\u0002\"\u0001\n\u0001\t\u000b\u001d\u0012\u0001\u0019\u0001\u0015\u0002\u0017-,\u0017\u0010V8US6,'o]\u000b\u0002eA!1G\u000f\u001fA\u001b\u0005!$BA\u001b7\u0003\u001diW\u000f^1cY\u0016T!a\u000e\u001d\u0002\u0015\r|G\u000e\\3di&|gNC\u0001:\u0003\u0015\u00198-\u00197b\u0013\tYDGA\u0002NCB\u0004\"!\u0010 \u000e\u0003aJ!a\u0010\u001d\u0003\u0007\u0005s\u0017\u0010\u0005\u0002B\t:\u0011AEQ\u0005\u0003\u0007n\tQ\u0002V5nKJlUm]:bO\u0016\u001c\u0018BA#G\u0005\u0015!\u0016.\\3s\u0015\t\u00195$\u0001\u0007lKf$v\u000eV5nKJ\u001c\b%\u0001\u0006uS6,'/\u00133HK:,\u0012A\u0013\t\u0004\u00172sU\"\u0001\u001c\n\u000553$\u0001C%uKJ\fGo\u001c:\u0011\u0005uz\u0015B\u0001)9\u0005\rIe\u000e^\u0001\fi&lWM]%e\u000f\u0016t\u0007%A\n`i&lWM]!ts:\u001c7)\u00197mE\u0006\u001c7.F\u0001U!\r!SkV\u0005\u0003-n\u0011Q\"Q:z]\u000e\u001c\u0015\r\u001c7cC\u000e\\\u0007CA!Y\u0013\tIfIA\u0005TG\",G-\u001e7fI\u00069r\f^5nKJ\f5/\u001f8d\u0007\u0006dGNY1dW~#S-\u001d\u000b\u00039~\u0003\"!P/\n\u0005yC$\u0001B+oSRDq\u0001\u0019\u0005\u0002\u0002\u0003\u0007A+A\u0002yIE\nAc\u0018;j[\u0016\u0014\u0018i]=oG\u000e\u000bG\u000e\u001c2bG.\u0004\u0013!F4fiRKW.\u001a:Bgft7mQ1mY\n\f7m[\u0001\u0010_:Le\u000e^3s]\u0006dG+[7feR\u0011A,\u001a\u0005\u0006M.\u0001\raV\u0001\ng\u000eDW\rZ;mK\u0012\fqa\u001c8US6,'\u000f\u0006\u0002]S\")!\u000e\u0004a\u0001y\u0005AA/[7fe.+\u0017\u0010\u000b\u0002jYB\u0011Q\u000e]\u0007\u0002]*\u0011qnH\u0001\u0005kRLG.\u0003\u0002r]\n1QO\\;tK\u0012DC\u0001D:\u0002\u0006A\u0019Q\b\u001e<\n\u0005UD$A\u0002;ie><8\u000f\u0005\u0002x\u007f:\u0011\u00010 \b\u0003srl\u0011A\u001f\u0006\u0003w\u0006\na\u0001\u0010:p_Rt\u0014\"A\u001d\n\u0005yD\u0014a\u00029bG.\fw-Z\u0005\u0005\u0003\u0003\t\u0019AA\u0005Fq\u000e,\u0007\u000f^5p]*\u0011a\u0010O\u0012\u0002m\u0006i\u0011M\u001a;feB{7\u000f^*u_B$\u0012\u0001X\u0001\rg\u000eDW\rZ;mK>s7-\u001a\u000b\u00069\u0006=\u0011\u0011\u0003\u0005\u0006U:\u0001\r\u0001\u0010\u0005\b\u0003'q\u0001\u0019AA\u000b\u0003\u0015!W\r\\1z!\u0011\t9\"!\t\u000e\u0005\u0005e!\u0002BA\u000e\u0003;\t\u0001\u0002Z;sCRLwN\u001c\u0006\u0004\u0003?A\u0014AC2p]\u000e,(O]3oi&!\u00111EA\r\u000591\u0015N\\5uK\u0012+(/\u0019;j_:$R\u0001XA\u0014\u0003SAQA[\bA\u0002qBq!a\u0005\u0010\u0001\u0004\tY\u0003\u0005\u0003\u0002.\u0005]RBAA\u0018\u0015\u0011\t\t$a\r\u0002\tQLW.\u001a\u0006\u0003\u0003k\tAA[1wC&!\u0011\u0011HA\u0018\u0005!!UO]1uS>t\u0017AF:dQ\u0016$W\u000f\\3XSRDg)\u001b=fI\u0012+G.Y=\u0015\u000fq\u000by$!\u0011\u0002F!)!\u000e\u0005a\u0001y!9\u00111\t\tA\u0002\u0005U\u0011\u0001D5oSRL\u0017\r\u001c#fY\u0006L\bbBA\n!\u0001\u0007\u0011Q\u0003\u000b\b9\u0006%\u00131JA'\u0011\u0015Q\u0017\u00031\u0001=\u0011\u001d\t\u0019%\u0005a\u0001\u0003WAq!a\u0014\u0012\u0001\u0004\tY#\u0001\u0005j]R,'O^1m\u0003M\u00198\r[3ek2,\u0017\t\u001e$jq\u0016$'+\u0019;f)\u001da\u0016QKA,\u00033BQA\u001b\nA\u0002qBq!a\u0011\u0013\u0001\u0004\t)\u0002C\u0004\u0002PI\u0001\r!!\u0006\u0015\u000fq\u000bi&a\u0018\u0002b!)!n\u0005a\u0001y!9\u00111I\nA\u0002\u0005-\u0002bBA('\u0001\u0007\u00111F\u0001%g\u000eDW\rZ;mKB+'/[8eS\u000e\fG\u000e\\=XSRD\u0017J\\5uS\u0006dG)\u001a7bsR9A,a\u001a\u0002j\u0005-\u0004\"\u00026\u0015\u0001\u0004a\u0004bBA\")\u0001\u0007\u0011Q\u0003\u0005\b\u0003\u001f\"\u0002\u0019AA\u000bQ-!\u0012qNA;\u0003o\nY(! \u0011\u0007u\n\t(C\u0002\u0002ta\u0012!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#!!\u001f\u0002\u0003w)6/\u001a\u0011tG\",G-\u001e7f/&$\bNR5yK\u0012$U\r\\1zA=\u0014\be]2iK\u0012,H.Z!u\r&DX\r\u001a*bi\u0016\u0004\u0013N\\:uK\u0006$g\u0006\t+iSN\u0004\u0003.Y:!i\",\u0007e]1nK\u0002\u001aX-\\1oi&\u001c7\u000fI1tAM\u001c\u0007.\u001a3vY\u0016\fEOR5yK\u0012\u0014\u0016\r^3-A\t,H\u000fI:dQ\u0016$W\u000f\\3XSRDg)\u001b=fI\u0012+G.Y=!SN\u0004sN\u001a;f]\u0002\u0002(/\u001a4feJ,GML\u0001\u0006g&t7-Z\u0011\u0003\u0003\u007f\nQA\r\u00187]A\"r\u0001XAB\u0003\u000b\u000b9\tC\u0003k+\u0001\u0007A\bC\u0004\u0002DU\u0001\r!a\u000b\t\u000f\u0005=S\u00031\u0001\u0002,!ZQ#a\u001c\u0002v\u0005]\u00141PA?\u0003Q\u00198\r[3ek2,\u0007+\u001a:j_\u0012L7-\u00197msR)A,a$\u0002\u0012\")!N\u0006a\u0001y!9\u0011q\n\fA\u0002\u0005U\u0001f\u0003\f\u0002p\u0005U\u0014qOA>\u0003{\"R\u0001XAL\u00033CQA[\fA\u0002qBq!a\u0014\u0018\u0001\u0004\tY\u0003K\u0006\u0018\u0003_\n)(a\u001e\u0002|\u0005u\u0014aC2b]\u000e,G\u000eV5nKJ$2\u0001XAQ\u0011\u0015Q\u0007\u00041\u0001=\u00035I7\u000fV5nKJ\f5\r^5wKR!\u0011qUAW!\ri\u0014\u0011V\u0005\u0004\u0003WC$a\u0002\"p_2,\u0017M\u001c\u0005\u0006Uf\u0001\r\u0001\u0010")
public abstract class TimerGraphStageLogic
extends GraphStageLogic {
    private final Map<Object, TimerMessages.Timer> keyToTimers = (Map)Map$.MODULE$.apply(Nil$.MODULE$);
    private final Iterator<Object> timerIdGen = package$.MODULE$.Iterator().from(1);
    private AsyncCallback<TimerMessages.Scheduled> _timerAsyncCallback;

    private Map<Object, TimerMessages.Timer> keyToTimers() {
        return this.keyToTimers;
    }

    private Iterator<Object> timerIdGen() {
        return this.timerIdGen;
    }

    private AsyncCallback<TimerMessages.Scheduled> _timerAsyncCallback() {
        return this._timerAsyncCallback;
    }

    private void _timerAsyncCallback_$eq(AsyncCallback<TimerMessages.Scheduled> x$1) {
        this._timerAsyncCallback = x$1;
    }

    private AsyncCallback<TimerMessages.Scheduled> getTimerAsyncCallback() {
        block0: {
            if (this._timerAsyncCallback() != null) break block0;
            this._timerAsyncCallback_$eq(this.getAsyncCallback((Function1<TimerMessages.Scheduled, Object> & Serializable)scheduled -> {
                this.onInternalTimer(scheduled);
                return BoxedUnit.UNIT;
            }));
        }
        return this._timerAsyncCallback();
    }

    private void onInternalTimer(TimerMessages.Scheduled scheduled) {
        int n;
        Some some;
        TimerMessages.Timer timer;
        int Id2 = scheduled.timerId();
        Object timerKey = scheduled.timerKey();
        Option option = this.keyToTimers().get(timerKey);
        if (option instanceof Some && (timer = (TimerMessages.Timer)(some = (Some)option).value()) != null && Id2 == (n = timer.id())) {
            Object object = !scheduled.repeating() ? this.keyToTimers().$minus$eq(timerKey) : BoxedUnit.UNIT;
            this.onTimer(timerKey);
            return;
        }
    }

    public void onTimer(Object timerKey) throws Exception {
    }

    @Override
    public void afterPostStop() {
        super.afterPostStop();
        if (this.keyToTimers() != null) {
            this.keyToTimers().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(TimerGraphStageLogic.$anonfun$afterPostStop$2(x0$1)));
            this.keyToTimers().clear();
            return;
        }
    }

    public final void scheduleOnce(Object timerKey, FiniteDuration delay) {
        this.cancelTimer(timerKey);
        int id = BoxesRunTime.unboxToInt(this.timerIdGen().next());
        AsyncCallback<TimerMessages.Scheduled> callback = this.getTimerAsyncCallback();
        Cancellable task = this.interpreter().materializer().scheduleOnce(delay, () -> callback.invoke(new TimerMessages.Scheduled(timerKey, id, false)));
        this.keyToTimers().update(timerKey, new TimerMessages.Timer(id, task));
    }

    public final void scheduleOnce(Object timerKey, Duration delay) {
        this.scheduleOnce(timerKey, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)));
    }

    public final void scheduleWithFixedDelay(Object timerKey, FiniteDuration initialDelay, FiniteDuration delay) {
        this.cancelTimer(timerKey);
        int id = BoxesRunTime.unboxToInt(this.timerIdGen().next());
        AsyncCallback<TimerMessages.Scheduled> callback = this.getTimerAsyncCallback();
        Cancellable task = this.interpreter().materializer().scheduleWithFixedDelay(initialDelay, delay, () -> callback.invoke(new TimerMessages.Scheduled(timerKey, id, true)));
        this.keyToTimers().update(timerKey, new TimerMessages.Timer(id, task));
    }

    public final void scheduleWithFixedDelay(Object timerKey, Duration initialDelay, Duration interval) {
        this.scheduleWithFixedDelay(timerKey, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public final void scheduleAtFixedRate(Object timerKey, FiniteDuration initialDelay, FiniteDuration interval) {
        this.cancelTimer(timerKey);
        int id = BoxesRunTime.unboxToInt(this.timerIdGen().next());
        AsyncCallback<TimerMessages.Scheduled> callback = this.getTimerAsyncCallback();
        Cancellable task = this.interpreter().materializer().scheduleAtFixedRate(initialDelay, interval, () -> callback.invoke(new TimerMessages.Scheduled(timerKey, id, true)));
        this.keyToTimers().update(timerKey, new TimerMessages.Timer(id, task));
    }

    public final void scheduleAtFixedRate(Object timerKey, Duration initialDelay, Duration interval) {
        this.scheduleAtFixedRate(timerKey, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public final void schedulePeriodicallyWithInitialDelay(Object timerKey, FiniteDuration initialDelay, FiniteDuration interval) {
        this.scheduleAtFixedRate(timerKey, initialDelay, interval);
    }

    public final void schedulePeriodicallyWithInitialDelay(Object timerKey, Duration initialDelay, Duration interval) {
        this.schedulePeriodicallyWithInitialDelay(timerKey, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public final void schedulePeriodically(Object timerKey, FiniteDuration interval) {
        this.schedulePeriodicallyWithInitialDelay(timerKey, interval, interval);
    }

    public final void schedulePeriodically(Object timerKey, Duration interval) {
        this.schedulePeriodically(timerKey, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public final void cancelTimer(Object timerKey) {
        this.keyToTimers().get(timerKey).foreach((Function1<TimerMessages.Timer, Map> & Serializable)t2 -> {
            t2.task().cancel();
            return (Map)this.keyToTimers().$minus$eq(timerKey);
        });
    }

    public final boolean isTimerActive(Object timerKey) {
        return this.keyToTimers().contains(timerKey);
    }

    public static final /* synthetic */ boolean $anonfun$afterPostStop$2(Tuple2 x0$1) {
        TimerMessages.Timer timer;
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null && (timer = (TimerMessages.Timer)tuple2._2()) != null) {
            Cancellable task = timer.task();
            return task.cancel();
        }
        throw new MatchError(tuple2);
    }

    public TimerGraphStageLogic(Shape _shape) {
        super(_shape);
    }
}

