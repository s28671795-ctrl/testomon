/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Cancellable$;
import akka.actor.Scheduler$;
import akka.actor.SchedulerException;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.time.Duration;
import scala.Function0;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\t-baB\u000f\u001f!\u0003\r\ta\t\u0005\u0006U\u0001!\ta\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\u0006_\u0001!)a\u0015\u0005\u0006_\u0001!)A\u0018\u0005\b_\u0002\t\n\u0011\"\u0002q\u0011\u0015y\u0003\u0001\"\u0002\u0000\u0011\u001d\ti\u0001\u0001C\u0003\u0003\u001fAq!!\u0004\u0001\t\u000b\ty\u0002C\u0004\u0002\u000e\u0001!)!!\u000b\t\u0013\u0005e\u0002!%A\u0005\u0006\u0005m\u0002bBA\u0007\u0001\u0011\u0015\u0011Q\t\u0005\b\u0003'\u0002AQAA+\u0011%\t9\bAI\u0001\n\u000b\tI\bC\u0004\u0002T\u0001!)!a!\t\u000f\u0005M\u0003\u0001\"\u0002\u0002\u0014\"9\u00111\u000b\u0001\u0007\u0002\u0005-\u0006bBA*\u0001\u0011\u0005\u0011\u0011\u0018\u0005\b\u0003\u000f\u0004AQAAe\u0011%\t9\u000eAI\u0001\n\u000b\tI\u000eC\u0004\u0002H\u0002!)!!9\t\u000f\u0005\u001d\u0007\u0001\"\u0002\u0002n\"9\u0011q\u0019\u0001\u0007\u0002\u0005e\bbBAd\u0001\u0011\u0005!1\u0001\u0005\b\u0005\u001b\u0001a\u0011\u0001B\b\u000f\u001d\u00119B\bE\u0001\u000531a!\b\u0010\t\u0002\tm\u0001b\u0002B\u000f5\u0011\u0005!q\u0004\u0004\n\u0005CQ\u0002\u0013aI\u0001\u0005G\u0011\u0011bU2iK\u0012,H.\u001a:\u000b\u0005}\u0001\u0013!B1di>\u0014(\"A\u0011\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001A\u0005\u0005\u0002&Q5\taEC\u0001(\u0003\u0015\u00198-\u00197b\u0013\tIcE\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u00031\u0002\"!J\u0017\n\u000592#\u0001B+oSR\fac]2iK\u0012,H.Z,ji\"4\u0015\u000e_3e\t\u0016d\u0017-\u001f\u000b\u0004c%\u000bFC\u0001\u001a@)\t\u0019t\u0007\u0005\u00025k5\ta$\u0003\u00027=\tY1)\u00198dK2d\u0017M\u00197f\u0011\u0015A$\u0001q\u0001:\u0003!)\u00070Z2vi>\u0014\bC\u0001\u001e>\u001b\u0005Y$B\u0001\u001f'\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003}m\u0012\u0001#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\t\u000b\u0001\u0013\u0001\u0019A!\u0002\u0011I,hN\\1cY\u0016\u0004\"AQ$\u000e\u0003\rS!\u0001R#\u0002\t1\fgn\u001a\u0006\u0002\r\u0006!!.\u0019<b\u0013\tA5I\u0001\u0005Sk:t\u0017M\u00197f\u0011\u0015Q%\u00011\u0001L\u00031Ig.\u001b;jC2$U\r\\1z!\tau*D\u0001N\u0015\tq5(\u0001\u0005ekJ\fG/[8o\u0013\t\u0001VJ\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\t\u000bI\u0013\u0001\u0019A&\u0002\u000b\u0011,G.Y=\u0015\u000bM\"6\fX/\t\u000b)\u001b\u0001\u0019A+\u0011\u0005YKV\"A,\u000b\u0005a+\u0015\u0001\u0002;j[\u0016L!AW,\u0003\u0011\u0011+(/\u0019;j_:DQAU\u0002A\u0002UCQ\u0001Q\u0002A\u0002\u0005CQ\u0001O\u0002A\u0002e\"Ra\u00184hQ*$2a\r1b\u0011\u0015AD\u0001q\u0001:\u0011\u001d\u0011G\u0001%AA\u0004\r\faa]3oI\u0016\u0014\bC\u0001\u001be\u0013\t)gD\u0001\u0005BGR|'OU3g\u0011\u0015QE\u00011\u0001L\u0011\u0015\u0011F\u00011\u0001L\u0011\u0015IG\u00011\u0001d\u0003!\u0011XmY3jm\u0016\u0014\b\"B6\u0005\u0001\u0004a\u0017aB7fgN\fw-\u001a\t\u0003K5L!A\u001c\u0014\u0003\u0007\u0005s\u00170\u0001\u0011tG\",G-\u001e7f/&$\bNR5yK\u0012$U\r\\1zI\u0011,g-Y;mi\u00122D#B9|yvt(FA2sW\u0005\u0019\bC\u0001;z\u001b\u0005)(B\u0001<x\u0003%)hn\u00195fG.,GM\u0003\u0002yM\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005i,(!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\")!*\u0002a\u0001\u0017\")!+\u0002a\u0001\u0017\")\u0011.\u0002a\u0001G\")1.\u0002a\u0001YRi1'!\u0001\u0002\u0004\u0005\u0015\u0011qAA\u0005\u0003\u0017AQA\u0013\u0004A\u0002UCQA\u0015\u0004A\u0002UCQ!\u001b\u0004A\u0002\rDQa\u001b\u0004A\u00021DQ\u0001\u000f\u0004A\u0002eBQA\u0019\u0004A\u0002\r\f1c]2iK\u0012,H.Z!u\r&DX\r\u001a*bi\u0016$b!!\u0005\u0002\u001a\u0005mA\u0003BA\n\u0003/!2aMA\u000b\u0011\u0015At\u0001q\u0001:\u0011\u0015\u0001u\u00011\u0001B\u0011\u0015Qu\u00011\u0001L\u0011\u0019\tib\u0002a\u0001\u0017\u0006A\u0011N\u001c;feZ\fG\u000eF\u00054\u0003C\t\u0019#!\n\u0002(!)!\n\u0003a\u0001+\"1\u0011Q\u0004\u0005A\u0002UCQ\u0001\u0011\u0005A\u0002\u0005CQ\u0001\u000f\u0005A\u0002e\"\"\"a\u000b\u00022\u0005M\u0012QGA\u001c)\u0015\u0019\u0014QFA\u0018\u0011\u0015A\u0014\u0002q\u0001:\u0011\u001d\u0011\u0017\u0002%AA\u0004\rDQAS\u0005A\u0002-Ca!!\b\n\u0001\u0004Y\u0005\"B5\n\u0001\u0004\u0019\u0007\"B6\n\u0001\u0004a\u0017!H:dQ\u0016$W\u000f\\3Bi\u001aK\u00070\u001a3SCR,G\u0005Z3gCVdG\u000f\n\u001c\u0015\u0013E\fi$a\u0010\u0002B\u0005\r\u0003\"\u0002&\u000b\u0001\u0004Y\u0005BBA\u000f\u0015\u0001\u00071\nC\u0003j\u0015\u0001\u00071\rC\u0003l\u0015\u0001\u0007A\u000eF\u00074\u0003\u000f\nI%a\u0013\u0002N\u0005=\u0013\u0011\u000b\u0005\u0006\u0015.\u0001\r!\u0016\u0005\u0007\u0003;Y\u0001\u0019A+\t\u000b%\\\u0001\u0019A2\t\u000b-\\\u0001\u0019\u00017\t\u000baZ\u0001\u0019A\u001d\t\u000b\t\\\u0001\u0019A2\u0002\u0011M\u001c\u0007.\u001a3vY\u0016$\"\"a\u0016\u0002^\u0005}\u0013\u0011MA2)\u0015\u0019\u0014\u0011LA.\u0011\u0015AD\u0002q\u0001:\u0011\u001d\u0011G\u0002%AA\u0004\rDQA\u0013\u0007A\u0002-Ca!!\b\r\u0001\u0004Y\u0005\"B5\r\u0001\u0004\u0019\u0007\"B6\r\u0001\u0004a\u0007F\u0003\u0007\u0002h-\fi'!\u001d\u0002tA\u0019Q%!\u001b\n\u0007\u0005-dE\u0001\u0006eKB\u0014XmY1uK\u0012\f#!a\u001c\u0002\u0003w)6/\u001a\u0011tG\",G-\u001e7f/&$\bNR5yK\u0012$U\r\\1zA=\u0014\be]2iK\u0012,H.Z!u\r&DX\r\u001a*bi\u0016\u0004\u0013N\\:uK\u0006$g\u0006\t+iSN\u0004\u0003.Y:!i\",\u0007e]1nK\u0002\u001aX-\\1oi&\u001c7\u000fI1tAM\u001c\u0007.\u001a3vY\u0016\fEOR5yK\u0012\u0014\u0016\r^3-A\t,H\u000fI:dQ\u0016$W\u000f\\3XSRDg)\u001b=fI\u0012+G.Y=!SN\u0004sN\u001a;f]\u0002\u0002(/\u001a4feJ,GML\u0001\u0006g&t7-Z\u0011\u0003\u0003k\nQA\r\u00187]A\n!c]2iK\u0012,H.\u001a\u0013eK\u001a\fW\u000f\u001c;%mQI\u0011/a\u001f\u0002~\u0005}\u0014\u0011\u0011\u0005\u0006\u00156\u0001\ra\u0013\u0005\u0007\u0003;i\u0001\u0019A&\t\u000b%l\u0001\u0019A2\t\u000b-l\u0001\u0019\u00017\u0015\u001bM\n))a\"\u0002\n\u0006-\u0015QRAH\u0011\u0015Qe\u00021\u0001V\u0011\u0019\tiB\u0004a\u0001+\")\u0011N\u0004a\u0001G\")1N\u0004a\u0001Y\")\u0001H\u0004a\u0001s!)!M\u0004a\u0001G\"Ra\"a\u001al\u0003[\n\t(a\u001d\u0015\r\u0005U\u0015QUAT)\u0011\t9*a'\u0015\u0007M\nI\nC\u00039\u001f\u0001\u000f\u0011\b\u0003\u0005\u0002\u001e>!\t\u0019AAP\u0003\u00051\u0007\u0003B\u0013\u0002\"2J1!a)'\u0005!a$-\u001f8b[\u0016t\u0004\"\u0002&\u0010\u0001\u0004Y\u0005BBA\u000f\u001f\u0001\u00071\n\u000b\u0006\u0010\u0003OZ\u0017QNA9\u0003g\"\u0002\"!,\u00022\u0006M\u0016Q\u0017\u000b\u0004g\u0005=\u0006\"\u0002\u001d\u0011\u0001\bI\u0004\"\u0002&\u0011\u0001\u0004Y\u0005BBA\u000f!\u0001\u00071\nC\u0003A!\u0001\u0007\u0011\t\u000b\u0006\u0011\u0003OZ\u0017QNA9\u0003g\"\u0002\"a/\u0002@\u0006\u0005\u00171\u0019\u000b\u0004g\u0005u\u0006\"\u0002\u001d\u0012\u0001\bI\u0004\"\u0002&\u0012\u0001\u0004)\u0006BBA\u000f#\u0001\u0007Q\u000bC\u0003A#\u0001\u0007\u0011\t\u000b\u0006\u0012\u0003OZ\u0017QNA9\u0003g\nAb]2iK\u0012,H.Z(oG\u0016$\u0002\"a3\u0002R\u0006M\u0017Q\u001b\u000b\u0006g\u00055\u0017q\u001a\u0005\u0006qI\u0001\u001d!\u000f\u0005\bEJ\u0001\n\u0011q\u0001d\u0011\u0015\u0011&\u00031\u0001L\u0011\u0015I'\u00031\u0001d\u0011\u0015Y'\u00031\u0001m\u0003Y\u00198\r[3ek2,wJ\\2fI\u0011,g-Y;mi\u0012*DcB9\u0002\\\u0006u\u0017q\u001c\u0005\u0006%N\u0001\ra\u0013\u0005\u0006SN\u0001\ra\u0019\u0005\u0006WN\u0001\r\u0001\u001c\u000b\fg\u0005\r\u0018Q]At\u0003S\fY\u000fC\u0003S)\u0001\u0007Q\u000bC\u0003j)\u0001\u00071\rC\u0003l)\u0001\u0007A\u000eC\u00039)\u0001\u0007\u0011\bC\u0003c)\u0001\u00071\r\u0006\u0003\u0002p\u0006]H\u0003BAy\u0003k$2aMAz\u0011\u0015AT\u0003q\u0001:\u0011!\ti*\u0006CA\u0002\u0005}\u0005\"\u0002*\u0016\u0001\u0004YECBA~\u0003\u007f\u0014\t\u0001F\u00024\u0003{DQ\u0001\u000f\fA\u0004eBQA\u0015\fA\u0002-CQ\u0001\u0011\fA\u0002\u0005#bA!\u0002\u0003\n\t-AcA\u001a\u0003\b!)\u0001h\u0006a\u0002s!)!k\u0006a\u0001+\")\u0001i\u0006a\u0001\u0003\u0006aQ.\u0019=Ge\u0016\fX/\u001a8dsV\u0011!\u0011\u0003\t\u0004K\tM\u0011b\u0001B\u000bM\t1Ai\\;cY\u0016\f\u0011bU2iK\u0012,H.\u001a:\u0011\u0005QR2C\u0001\u000e%\u0003\u0019a\u0014N\\5u}Q\u0011!\u0011\u0004\u0002\u000f)\u0006\u001c8NU;o\u001f:\u001cEn\\:f'\u0011a\"QE!\u0011\u0007\t\u00139#C\u0002\u0003*\r\u0013aa\u00142kK\u000e$\b")
public interface Scheduler {
    public static /* synthetic */ Cancellable scheduleWithFixedDelay$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration delay, Runnable runnable2, ExecutionContext executor2) {
        return $this.scheduleWithFixedDelay(initialDelay, delay, runnable2, executor2);
    }

    default public Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, Runnable runnable2, ExecutionContext executor2) {
        Cancellable cancellable;
        try {
            cancellable = new Cancellable(this, initialDelay, runnable2, delay, executor2){
                private final /* synthetic */ Scheduler $outer;

                public void akka$actor$Scheduler$$anon$$swap(Cancellable c) {
                    Cancellable cancellable;
                    do {
                        if ((cancellable = (Cancellable)this.get()) != null) continue;
                        if (c != null) {
                            c.cancel();
                        }
                        break;
                    } while (!this.compareAndSet(cancellable, c));
                }

                public final boolean cancel() {
                    return this.tailrecCancel$1();
                }

                public boolean isCancelled() {
                    return this.get() == null;
                }

                public /* synthetic */ Scheduler akka$actor$Scheduler$$anon$$$outer() {
                    return this.$outer;
                }

                private final boolean tailrecCancel$1() {
                    Cancellable cancellable;
                    do {
                        if ((cancellable = (Cancellable)this.get()) == null) {
                            return false;
                        }
                        if (!cancellable.cancel()) continue;
                        return this.compareAndSet(cancellable, null);
                    } while (!this.compareAndSet(cancellable, null));
                    return true;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super(Cancellable$.MODULE$.initialNotCancelled());
                    this.compareAndSet(Cancellable$.MODULE$.initialNotCancelled(), $outer.scheduleOnce(initialDelay$1, new Runnable(this, runnable$1, delay$1, executor$1){
                        private final /* synthetic */ $anon$1 $outer;
                        private final Runnable runnable$1;
                        private final FiniteDuration delay$1;
                        private final ExecutionContext executor$1;

                        public void run() {
                            try {
                                this.runnable$1.run();
                                if (this.$outer.get() != null) {
                                    this.$outer.akka$actor$Scheduler$$anon$$swap(this.$outer.akka$actor$Scheduler$$anon$$$outer().scheduleOnce(this.delay$1, (Runnable)this, this.executor$1));
                                }
                            }
                            catch (Throwable throwable) {
                                IllegalStateException illegalStateException;
                                Throwable throwable2 = throwable;
                                if (throwable2 instanceof SchedulerException) {
                                }
                                if (throwable2 instanceof IllegalStateException && (illegalStateException = (IllegalStateException)throwable2).getCause() != null && illegalStateException.getCause() instanceof SchedulerException) {
                                }
                                throw throwable;
                            }
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.runnable$1 = runnable$1;
                            this.delay$1 = delay$1;
                            this.executor$1 = executor$1;
                        }
                    }, executor$1));
                }
            };
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable2 instanceof SchedulerException) {
                SchedulerException schedulerException = (SchedulerException)throwable2;
                String msg = schedulerException.msg();
                throw new IllegalStateException(msg);
            }
            throw throwable;
        }
        return cancellable;
    }

    public static /* synthetic */ Cancellable scheduleWithFixedDelay$(Scheduler $this, Duration initialDelay, Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return $this.scheduleWithFixedDelay(initialDelay, delay, runnable2, executor2);
    }

    default public Cancellable scheduleWithFixedDelay(Duration initialDelay, Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return this.scheduleWithFixedDelay(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)), runnable2, executor2);
    }

    public static /* synthetic */ Cancellable scheduleWithFixedDelay$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.scheduleWithFixedDelay(initialDelay, delay, receiver, message, executor2, sender);
    }

    default public Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.scheduleWithFixedDelay(initialDelay, delay, new Runnable(null, receiver, message, sender){
            private final ActorRef receiver$1;
            private final Object message$1;
            private final ActorRef sender$1;

            public void run() {
                this.receiver$1.$bang(this.message$1, this.sender$1);
                if (this.receiver$1.isTerminated()) {
                    throw new SchedulerException("timer active for terminated actor");
                }
            }
            {
                this.receiver$1 = receiver$1;
                this.message$1 = message$1;
                this.sender$1 = sender$1;
            }
        }, executor2);
    }

    public static /* synthetic */ Cancellable scheduleWithFixedDelay$(Scheduler $this, Duration initialDelay, Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.scheduleWithFixedDelay(initialDelay, delay, receiver, message, executor2, sender);
    }

    default public Cancellable scheduleWithFixedDelay(Duration initialDelay, Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.scheduleWithFixedDelay(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)), receiver, message, executor2, sender);
    }

    public static /* synthetic */ Cancellable scheduleAtFixedRate$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration interval, Runnable runnable2, ExecutionContext executor2) {
        return $this.scheduleAtFixedRate(initialDelay, interval, runnable2, executor2);
    }

    default public Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, Runnable runnable2, ExecutionContext executor2) {
        return this.schedule(initialDelay, interval, runnable2, executor2);
    }

    public static /* synthetic */ Cancellable scheduleAtFixedRate$(Scheduler $this, Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return $this.scheduleAtFixedRate(initialDelay, interval, runnable2, executor2);
    }

    default public Cancellable scheduleAtFixedRate(Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return this.scheduleAtFixedRate(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)), runnable2, executor2);
    }

    public static /* synthetic */ Cancellable scheduleAtFixedRate$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.scheduleAtFixedRate(initialDelay, interval, receiver, message, executor2, sender);
    }

    default public Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.schedule(initialDelay, interval, receiver, message, executor2, sender);
    }

    public static /* synthetic */ Cancellable scheduleAtFixedRate$(Scheduler $this, Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.scheduleAtFixedRate(initialDelay, interval, receiver, message, executor2, sender);
    }

    default public Cancellable scheduleAtFixedRate(Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.scheduleAtFixedRate(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)), receiver, message, executor2, sender);
    }

    public static /* synthetic */ Cancellable schedule$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.schedule(initialDelay, interval, receiver, message, executor2, sender);
    }

    default public Cancellable schedule(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.schedule(initialDelay, interval, new Runnable(null, receiver, message, sender){
            private final ActorRef receiver$2;
            private final Object message$2;
            private final ActorRef sender$2;

            public void run() {
                this.receiver$2.$bang(this.message$2, this.sender$2);
                if (this.receiver$2.isTerminated()) {
                    throw new SchedulerException("timer active for terminated actor");
                }
            }
            {
                this.receiver$2 = receiver$2;
                this.message$2 = message$2;
                this.sender$2 = sender$2;
            }
        }, executor2);
    }

    public static /* synthetic */ Cancellable schedule$(Scheduler $this, Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.schedule(initialDelay, interval, receiver, message, executor2, sender);
    }

    default public Cancellable schedule(Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.schedule(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)), receiver, message, executor2, sender);
    }

    public static /* synthetic */ Cancellable schedule$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration interval, Function0 f, ExecutionContext executor2) {
        return $this.schedule(initialDelay, interval, f, executor2);
    }

    default public Cancellable schedule(FiniteDuration initialDelay, FiniteDuration interval, Function0<BoxedUnit> f, ExecutionContext executor2) {
        return this.schedule(initialDelay, interval, new Runnable(null, f){
            private final Function0 f$1;

            public void run() {
                this.f$1.apply$mcV$sp();
            }
            {
                this.f$1 = f$1;
            }
        }, executor2);
    }

    public Cancellable schedule(FiniteDuration var1, FiniteDuration var2, Runnable var3, ExecutionContext var4);

    public static /* synthetic */ Cancellable schedule$(Scheduler $this, Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return $this.schedule(initialDelay, interval, runnable2, executor2);
    }

    default public Cancellable schedule(Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return this.schedule(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)), runnable2, executor2);
    }

    public static /* synthetic */ ActorRef scheduleWithFixedDelay$default$6$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message) {
        return $this.scheduleWithFixedDelay$default$6(initialDelay, delay, receiver, message);
    }

    default public ActorRef scheduleWithFixedDelay$default$6(FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public static /* synthetic */ ActorRef scheduleAtFixedRate$default$6$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return $this.scheduleAtFixedRate$default$6(initialDelay, interval, receiver, message);
    }

    default public ActorRef scheduleAtFixedRate$default$6(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public static /* synthetic */ ActorRef schedule$default$6$(Scheduler $this, FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return $this.schedule$default$6(initialDelay, interval, receiver, message);
    }

    default public ActorRef schedule$default$6(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public static /* synthetic */ Cancellable scheduleOnce$(Scheduler $this, FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.scheduleOnce(delay, receiver, message, executor2, sender);
    }

    default public Cancellable scheduleOnce(FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.scheduleOnce(delay, new Runnable(null, receiver, message, sender){
            private final ActorRef receiver$3;
            private final Object message$3;
            private final ActorRef sender$3;

            public void run() {
                this.receiver$3.$bang(this.message$3, this.sender$3);
            }
            {
                this.receiver$3 = receiver$3;
                this.message$3 = message$3;
                this.sender$3 = sender$3;
            }
        }, executor2);
    }

    public static /* synthetic */ Cancellable scheduleOnce$(Scheduler $this, Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return $this.scheduleOnce(delay, receiver, message, executor2, sender);
    }

    default public Cancellable scheduleOnce(Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return this.scheduleOnce(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)), receiver, message, executor2, sender);
    }

    public static /* synthetic */ Cancellable scheduleOnce$(Scheduler $this, FiniteDuration delay, Function0 f, ExecutionContext executor2) {
        return $this.scheduleOnce(delay, f, executor2);
    }

    default public Cancellable scheduleOnce(FiniteDuration delay, Function0<BoxedUnit> f, ExecutionContext executor2) {
        return this.scheduleOnce(delay, new Runnable(null, f){
            private final Function0 f$2;

            public void run() {
                this.f$2.apply$mcV$sp();
            }
            {
                this.f$2 = f$2;
            }
        }, executor2);
    }

    public Cancellable scheduleOnce(FiniteDuration var1, Runnable var2, ExecutionContext var3);

    public static /* synthetic */ Cancellable scheduleOnce$(Scheduler $this, Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return $this.scheduleOnce(delay, runnable2, executor2);
    }

    default public Cancellable scheduleOnce(Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return this.scheduleOnce(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)), runnable2, executor2);
    }

    public static /* synthetic */ ActorRef scheduleOnce$default$5$(Scheduler $this, FiniteDuration delay, ActorRef receiver, Object message) {
        return $this.scheduleOnce$default$5(delay, receiver, message);
    }

    default public ActorRef scheduleOnce$default$5(FiniteDuration delay, ActorRef receiver, Object message) {
        return Actor$.MODULE$.noSender();
    }

    public double maxFrequency();

    public static void $init$(Scheduler $this) {
    }

    public static interface TaskRunOnClose
    extends Runnable {
    }
}

