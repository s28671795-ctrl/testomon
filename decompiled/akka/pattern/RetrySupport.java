/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Scheduler;
import akka.pattern.BackoffSupervisor$;
import akka.pattern.RetrySupport$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dcaB\u0006\r!\u0003\r\t!\u0005\u0005\u00061\u0001!\t!\u0007\u0005\u0006;\u0001!\tA\b\u0005\u0006;\u0001!\t!\u0011\u0005\u0006;\u0001!\tA\u0019\u0005\u0006;\u0001!\ta\\\u0004\b\u0003\u000ba\u0001\u0012AA\u0004\r\u0019YA\u0002#\u0001\u0002\f!9\u0011qB\u0004\u0005\u0002\u0005E\u0001BB\u000f\b\t\u0013\t\u0019\u0002\u0003\u0004\u001e\u000f\u0011%\u0011Q\u0006\u0002\r%\u0016$(/_*vaB|'\u000f\u001e\u0006\u0003\u001b9\tq\u0001]1ui\u0016\u0014hNC\u0001\u0010\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0005\t\u0003'Yi\u0011\u0001\u0006\u0006\u0002+\u0005)1oY1mC&\u0011q\u0003\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005Q\u0002CA\n\u001c\u0013\taBC\u0001\u0003V]&$\u0018!\u0002:fiJLXCA\u0010*)\r\u0001s\u0007\u0010\u000b\u0003CI\u00022AI\u0013(\u001b\u0005\u0019#B\u0001\u0013\u0015\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003M\r\u0012aAR;ukJ,\u0007C\u0001\u0015*\u0019\u0001!QA\u000b\u0002C\u0002-\u0012\u0011\u0001V\t\u0003Y=\u0002\"aE\u0017\n\u00059\"\"a\u0002(pi\"Lgn\u001a\t\u0003'AJ!!\r\u000b\u0003\u0007\u0005s\u0017\u0010C\u00034\u0005\u0001\u000fA'\u0001\u0002fGB\u0011!%N\u0005\u0003m\r\u0012\u0001#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\t\u000ba\u0012\u0001\u0019A\u001d\u0002\u000f\u0005$H/Z7qiB\u00191CO\u0011\n\u0005m\"\"!\u0003$v]\u000e$\u0018n\u001c81\u0011\u0015i$\u00011\u0001?\u0003!\tG\u000f^3naR\u001c\bCA\n@\u0013\t\u0001ECA\u0002J]R,\"A\u0011$\u0015\r\r\u0003&kU.^)\r!u\t\u0013\t\u0004E\u0015*\u0005C\u0001\u0015G\t\u0015Q3A1\u0001,\u0011\u0015\u00194\u0001q\u00015\u0011\u0015I5\u0001q\u0001K\u0003%\u00198\r[3ek2,'\u000f\u0005\u0002L\u001d6\tAJ\u0003\u0002N\u001d\u0005)\u0011m\u0019;pe&\u0011q\n\u0014\u0002\n'\u000eDW\rZ;mKJDQ\u0001O\u0002A\u0002E\u00032a\u0005\u001eE\u0011\u0015i4\u00011\u0001?\u0011\u0015!6\u00011\u0001V\u0003)i\u0017N\u001c\"bG.|gM\u001a\t\u0003-fk\u0011a\u0016\u0006\u00031\u000e\n\u0001\u0002Z;sCRLwN\\\u0005\u00035^\u0013aBR5oSR,G)\u001e:bi&|g\u000eC\u0003]\u0007\u0001\u0007Q+\u0001\u0006nCb\u0014\u0015mY6pM\u001aDQAX\u0002A\u0002}\u000bAB]1oI>lg)Y2u_J\u0004\"a\u00051\n\u0005\u0005$\"A\u0002#pk\ndW-\u0006\u0002dOR!AM\u001b7n)\r)\u0007.\u001b\t\u0004E\u00152\u0007C\u0001\u0015h\t\u0015QCA1\u0001,\u0011\u0015\u0019D\u0001q\u00015\u0011\u0015IE\u0001q\u0001K\u0011\u0015AD\u00011\u0001l!\r\u0019\"(\u001a\u0005\u0006{\u0011\u0001\rA\u0010\u0005\u0006]\u0012\u0001\r!V\u0001\u0006I\u0016d\u0017-_\u000b\u0003aR$B!]<zuR\u0019!/\u001e<\u0011\u0007\t*3\u000f\u0005\u0002)i\u0012)!&\u0002b\u0001W!)1'\u0002a\u0002i!)\u0011*\u0002a\u0002\u0015\")\u0001(\u0002a\u0001qB\u00191C\u000f:\t\u000bu*\u0001\u0019\u0001 \t\u000bm,\u0001\u0019\u0001?\u0002\u001b\u0011,G.Y=Gk:\u001cG/[8o!\u0011\u0019RPP@\n\u0005y$\"!\u0003$v]\u000e$\u0018n\u001c82!\u0011\u0019\u0012\u0011A+\n\u0007\u0005\rAC\u0001\u0004PaRLwN\\\u0001\r%\u0016$(/_*vaB|'\u000f\u001e\t\u0004\u0003\u00139Q\"\u0001\u0007\u0014\t\u001d\u0011\u0012Q\u0002\t\u0004\u0003\u0013\u0001\u0011A\u0002\u001fj]&$h\b\u0006\u0002\u0002\bU!\u0011QCA\u000f)!\t9\"!\t\u0002&\u0005%B\u0003BA\r\u0003?\u0001BAI\u0013\u0002\u001cA\u0019\u0001&!\b\u0005\u000b)J!\u0019A\u0016\t\u000bMJ\u00019\u0001\u001b\t\raJ\u0001\u0019AA\u0012!\u0011\u0019\"(!\u0007\t\r\u0005\u001d\u0012\u00021\u0001?\u0003-i\u0017\r_!ui\u0016l\u0007\u000f^:\t\r\u0005-\u0012\u00021\u0001?\u0003%\tG\u000f^3naR,G-\u0006\u0003\u00020\u0005]BCCA\u0019\u0003{\t\t%a\u0011\u0002FQ1\u00111GA\u001d\u0003w\u0001BAI\u0013\u00026A\u0019\u0001&a\u000e\u0005\u000b)R!\u0019A\u0016\t\u000bMR\u00019\u0001\u001b\t\u000b%S\u00019\u0001&\t\raR\u0001\u0019AA !\u0011\u0019\"(a\r\t\r\u0005\u001d\"\u00021\u0001?\u0011\u0015Y(\u00021\u0001}\u0011\u0019\tYC\u0003a\u0001}\u0001")
public interface RetrySupport {
    public static /* synthetic */ Future retry$(RetrySupport $this, Function0 attempt, int attempts, ExecutionContext ec) {
        return $this.retry(attempt, attempts, ec);
    }

    default public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, ExecutionContext ec) {
        return RetrySupport$.MODULE$.akka$pattern$RetrySupport$$retry(attempt, attempts, 0, ec);
    }

    public static /* synthetic */ Future retry$(RetrySupport $this, Function0 attempt, int attempts, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, ExecutionContext ec, Scheduler scheduler) {
        return $this.retry(attempt, attempts, minBackoff, maxBackoff, randomFactor, ec, scheduler);
    }

    default public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, ExecutionContext ec, Scheduler scheduler) {
        Predef$.MODULE$.require(attempt != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter attempt should not be null.");
        Predef$.MODULE$.require(minBackoff != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter minBackoff should not be null.");
        Predef$.MODULE$.require(maxBackoff != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter maxBackoff should not be null.");
        Predef$.MODULE$.require(minBackoff.$greater(Duration$.MODULE$.Zero()), (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter minBackoff must be > 0");
        Predef$.MODULE$.require(maxBackoff.$greater$eq(minBackoff), (Function0<Object>)(Function0<String> & Serializable)() -> "Parameter maxBackoff must be >= minBackoff");
        Predef$.MODULE$.require(0.0 <= randomFactor && randomFactor <= 1.0, (Function0<Object>)(Function0<String> & Serializable)() -> "randomFactor must be between 0.0 and 1.0");
        return this.retry(attempt, attempts, (Function1<Object, Option<FiniteDuration>>)(Function1<Object, Some> & Serializable)attempted -> RetrySupport.$anonfun$retry$7(minBackoff, maxBackoff, randomFactor, BoxesRunTime.unboxToInt(attempted)), ec, scheduler);
    }

    public static /* synthetic */ Future retry$(RetrySupport $this, Function0 attempt, int attempts, FiniteDuration delay, ExecutionContext ec, Scheduler scheduler) {
        return $this.retry(attempt, attempts, delay, ec, scheduler);
    }

    default public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, FiniteDuration delay, ExecutionContext ec, Scheduler scheduler) {
        return this.retry(attempt, attempts, (Function1<Object, Option<FiniteDuration>>)(Function1<Object, Some> & Serializable)x$1 -> RetrySupport.$anonfun$retry$8(delay, BoxesRunTime.unboxToInt(x$1)), ec, scheduler);
    }

    public static /* synthetic */ Future retry$(RetrySupport $this, Function0 attempt, int attempts, Function1 delayFunction, ExecutionContext ec, Scheduler scheduler) {
        return $this.retry(attempt, attempts, delayFunction, ec, scheduler);
    }

    default public <T> Future<T> retry(Function0<Future<T>> attempt, int attempts, Function1<Object, Option<FiniteDuration>> delayFunction, ExecutionContext ec, Scheduler scheduler) {
        return RetrySupport$.MODULE$.akka$pattern$RetrySupport$$retry(attempt, attempts, delayFunction, 0, ec, scheduler);
    }

    public static /* synthetic */ Some $anonfun$retry$7(FiniteDuration minBackoff$1, FiniteDuration maxBackoff$1, double randomFactor$1, int attempted) {
        return new Some<FiniteDuration>(BackoffSupervisor$.MODULE$.calculateDelay(attempted, minBackoff$1, maxBackoff$1, randomFactor$1));
    }

    public static /* synthetic */ Some $anonfun$retry$8(FiniteDuration delay$1, int x$1) {
        return new Some<FiniteDuration>(delay$1);
    }

    public static void $init$(RetrySupport $this) {
    }
}

