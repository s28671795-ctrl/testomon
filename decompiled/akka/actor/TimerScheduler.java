/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.annotation.DoNotInherit;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001db!B\t\u0013\u0003\u00039\u0002\"\u0002\u0010\u0001\t\u0003y\u0002\"\u0002\u0012\u0001\r\u0003\u0019\u0003\"\u0002\u0012\u0001\r\u0003A\u0004\"\u0002\u0012\u0001\t\u000bq\u0004\"\u0002\u0012\u0001\t\u000bQ\u0005\"B(\u0001\r\u0003\u0001\u0006\"B(\u0001\r\u0003)\u0006\"B(\u0001\t\u000bQ\u0006\"B(\u0001\t\u000bq\u0006\"B2\u0001\r\u0003!\u0007\"B2\u0001\t\u000b\u0011\b\"B<\u0001\r\u0003A\b\"B<\u0001\t\u000bi\bbBA\u0002\u0001\u0019\u0005\u0011Q\u0001\u0005\b\u0003\u001f\u0001a\u0011AA\t\u0011\u001d\t)\u0002\u0001D\u0001\u0003/\u0011a\u0002V5nKJ\u001c6\r[3ek2,'O\u0003\u0002\u0014)\u0005)\u0011m\u0019;pe*\tQ#\u0001\u0003bW.\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000f\u000e\u0003iQ\u0011aG\u0001\u0006g\u000e\fG.Y\u0005\u0003;i\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001!!\t\t\u0003!D\u0001\u0013\u0003a\u0019H/\u0019:u)&lWM],ji\"4\u0015\u000e_3e\t\u0016d\u0017-\u001f\u000b\u0005I\u001dbc\u0006\u0005\u0002\u001aK%\u0011aE\u0007\u0002\u0005+:LG\u000fC\u0003)\u0005\u0001\u0007\u0011&A\u0002lKf\u0004\"!\u0007\u0016\n\u0005-R\"aA!os\")QF\u0001a\u0001S\u0005\u0019Qn]4\t\u000b=\u0012\u0001\u0019\u0001\u0019\u0002\u000b\u0011,G.Y=\u0011\u0005E2T\"\u0001\u001a\u000b\u0005M\"\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005UR\u0012AC2p]\u000e,(O]3oi&\u0011qG\r\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o)\u0015!\u0013HO\u001e>\u0011\u0015A3\u00011\u0001*\u0011\u0015i3\u00011\u0001*\u0011\u0015a4\u00011\u00011\u00031Ig.\u001b;jC2$U\r\\1z\u0011\u0015y3\u00011\u00011)\u0011!s\bQ!\t\u000b!\"\u0001\u0019A\u0015\t\u000b5\"\u0001\u0019A\u0015\t\u000b=\"\u0001\u0019\u0001\"\u0011\u0005\rCU\"\u0001#\u000b\u0005\u00153\u0015\u0001\u0002;j[\u0016T\u0011aR\u0001\u0005U\u00064\u0018-\u0003\u0002J\t\nAA)\u001e:bi&|g\u000eF\u0003%\u00172ke\nC\u0003)\u000b\u0001\u0007\u0011\u0006C\u0003.\u000b\u0001\u0007\u0011\u0006C\u0003=\u000b\u0001\u0007!\tC\u00030\u000b\u0001\u0007!)A\u000bti\u0006\u0014H\u000fV5nKJ\fEOR5yK\u0012\u0014\u0016\r^3\u0015\t\u0011\n&k\u0015\u0005\u0006Q\u0019\u0001\r!\u000b\u0005\u0006[\u0019\u0001\r!\u000b\u0005\u0006)\u001a\u0001\r\u0001M\u0001\tS:$XM\u001d<bYR)AEV,Y3\")\u0001f\u0002a\u0001S!)Qf\u0002a\u0001S!)Ah\u0002a\u0001a!)Ak\u0002a\u0001aQ!Ae\u0017/^\u0011\u0015A\u0003\u00021\u0001*\u0011\u0015i\u0003\u00021\u0001*\u0011\u0015!\u0006\u00021\u0001C)\u0015!s\fY1c\u0011\u0015A\u0013\u00021\u0001*\u0011\u0015i\u0013\u00021\u0001*\u0011\u0015a\u0014\u00021\u0001C\u0011\u0015!\u0016\u00021\u0001C\u0003I\u0019H/\u0019:u!\u0016\u0014\u0018n\u001c3jGRKW.\u001a:\u0015\t\u0011*gm\u001a\u0005\u0006Q)\u0001\r!\u000b\u0005\u0006[)\u0001\r!\u000b\u0005\u0006)*\u0001\r\u0001\r\u0015\u0007\u0015%dWn\u001c9\u0011\u0005eQ\u0017BA6\u001b\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005q\u0017!a\u0013Vg\u0016\u00043\u000f^1siRKW.\u001a:XSRDg)\u001b=fI\u0012+G.Y=!_J\u00043\u000f^1siRKW.\u001a:Bi\u001aK\u00070\u001a3SCR,\u0007%\u001b8ti\u0016\fGM\f\u0011UQ&\u001c\b\u0005[1tAQDW\rI:b[\u0016\u00043/Z7b]RL7m\u001d\u0011bg\u0002\u001aH/\u0019:u)&lWM]!u\r&DX\r\u001a*bi\u0016d\u0003EY;uAM$\u0018M\u001d;US6,'oV5uQ\u001aK\u00070\u001a3EK2\f\u0017\u0010I5tA=4G/\u001a8!aJ,g-\u001a:sK\u0012t\u0013!B:j]\u000e,\u0017%A9\u0002\u000bIrcG\f\u0019\u0015\t\u0011\u001aH/\u001e\u0005\u0006Q-\u0001\r!\u000b\u0005\u0006[-\u0001\r!\u000b\u0005\u0006).\u0001\rA\u0011\u0015\u0007\u0017%dWn\u001c9\u0002!M$\u0018M\u001d;TS:<G.\u001a+j[\u0016\u0014H\u0003\u0002\u0013zunDQ\u0001\u000b\u0007A\u0002%BQ!\f\u0007A\u0002%BQ\u0001 \u0007A\u0002A\nq\u0001^5nK>,H\u000fF\u0003%}~\f\t\u0001C\u0003)\u001b\u0001\u0007\u0011\u0006C\u0003.\u001b\u0001\u0007\u0011\u0006C\u0003}\u001b\u0001\u0007!)A\u0007jgRKW.\u001a:BGRLg/\u001a\u000b\u0005\u0003\u000f\ti\u0001E\u0002\u001a\u0003\u0013I1!a\u0003\u001b\u0005\u001d\u0011un\u001c7fC:DQ\u0001\u000b\bA\u0002%\naaY1oG\u0016dGc\u0001\u0013\u0002\u0014!)\u0001f\u0004a\u0001S\u0005I1-\u00198dK2\fE\u000e\u001c\u000b\u0002I!\u001a\u0001!a\u0007\u0011\t\u0005u\u00111E\u0007\u0003\u0003?Q1!!\t\u0015\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003K\tyB\u0001\u0007E_:{G/\u00138iKJLG\u000f")
@DoNotInherit
public abstract class TimerScheduler {
    public abstract void startTimerWithFixedDelay(Object var1, Object var2, FiniteDuration var3);

    public abstract void startTimerWithFixedDelay(Object var1, Object var2, FiniteDuration var3, FiniteDuration var4);

    public final void startTimerWithFixedDelay(Object key, Object msg, Duration delay) {
        this.startTimerWithFixedDelay(key, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)));
    }

    public final void startTimerWithFixedDelay(Object key, Object msg, Duration initialDelay, Duration delay) {
        this.startTimerWithFixedDelay(key, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(delay)));
    }

    public abstract void startTimerAtFixedRate(Object var1, Object var2, FiniteDuration var3);

    public abstract void startTimerAtFixedRate(Object var1, Object var2, FiniteDuration var3, FiniteDuration var4);

    public final void startTimerAtFixedRate(Object key, Object msg, Duration interval) {
        this.startTimerAtFixedRate(key, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public final void startTimerAtFixedRate(Object key, Object msg, Duration initialDelay, Duration interval) {
        this.startTimerAtFixedRate(key, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(initialDelay)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public abstract void startPeriodicTimer(Object var1, Object var2, FiniteDuration var3);

    public final void startPeriodicTimer(Object key, Object msg, Duration interval) {
        this.startPeriodicTimer(key, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }

    public abstract void startSingleTimer(Object var1, Object var2, FiniteDuration var3);

    public final void startSingleTimer(Object key, Object msg, Duration timeout2) {
        this.startSingleTimer(key, msg, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2)));
    }

    public abstract boolean isTimerActive(Object var1);

    public abstract void cancel(Object var1);

    public abstract void cancelAll();
}

