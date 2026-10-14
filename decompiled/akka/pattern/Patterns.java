/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.Scheduler;
import akka.japi.Function;
import akka.pattern.Patterns$;
import akka.pattern.PipeToSupport;
import akka.util.Timeout;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.function.IntFunction;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t}w!B\u0010!\u0011\u0003)c!B\u0014!\u0011\u0003A\u0003\"B\u0018\u0002\t\u0003\u0001\u0004\"B\u0019\u0002\t\u0003\u0011\u0004\"B\u0019\u0002\t\u0003i\u0005\"B0\u0002\t\u0003\u0001\u0007\"\u00023\u0002\t\u0003)\u0007\"\u00023\u0002\t\u0003\u0001\b\"B\u0019\u0002\t\u0003I\bB\u00023\u0002\t\u0003\t\u0019\u0001\u0003\u00042\u0003\u0011\u0005\u00111\u0002\u0005\u0007c\u0005!\t!a\u0007\t\rE\nA\u0011AA\u0012\u0011\u0019!\u0017\u0001\"\u0001\u0002,!1A-\u0001C\u0001\u0003gAq!a\u000f\u0002\t\u0003\ti\u0004C\u0004\u0002<\u0005!\t!!\u001d\t\u000f\u0005\u0015\u0015\u0001\"\u0001\u0002\b\"9\u0011QQ\u0001\u0005\u0002\u0005%\u0006bBAC\u0003\u0011\u0005\u0011\u0011\u0017\u0005\b\u0003\u000b\u000bA\u0011AA^\u0011\u001d\t\u0019-\u0001C\u0001\u0003\u000bDq!a1\u0002\t\u0003\t9\u000fC\u0004\u0002D\u0006!\tA!\u0001\t\u000f\tU\u0011\u0001\"\u0001\u0003\u0018!9!QC\u0001\u0005\u0002\tU\u0002b\u0002B\u000b\u0003\u0011\u0005!\u0011\f\u0005\b\u0005+\tA\u0011\u0001B:\u0011\u001d\u0011)\"\u0001C\u0001\u0005\u0017CqA!\u0006\u0002\t\u0003\u0011y\nC\u0004\u0003\u0016\u0005!\tA!.\u0002\u0011A\u000bG\u000f^3s]NT!!\t\u0012\u0002\u000fA\fG\u000f^3s]*\t1%\u0001\u0003bW.\f7\u0001\u0001\t\u0003M\u0005i\u0011\u0001\t\u0002\t!\u0006$H/\u001a:ogN\u0011\u0011!\u000b\t\u0003U5j\u0011a\u000b\u0006\u0002Y\u0005)1oY1mC&\u0011af\u000b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005)\u0013aA1tWR!1'\u000f!F!\r!t'K\u0007\u0002k)\u0011agK\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001\u001d6\u0005\u00191U\u000f^;sK\")!h\u0001a\u0001w\u0005)\u0011m\u0019;peB\u0011AHP\u0007\u0002{)\u0011!HI\u0005\u0003\u007fu\u0012\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006\u0003\u000e\u0001\rAQ\u0001\b[\u0016\u001c8/Y4f!\tQ3)\u0003\u0002EW\t\u0019\u0011I\\=\t\u000b\u0019\u001b\u0001\u0019A$\u0002\u000fQLW.Z8viB\u0011\u0001jS\u0007\u0002\u0013*\u0011!JI\u0001\u0005kRLG.\u0003\u0002M\u0013\n9A+[7f_V$H\u0003\u0002(W/b\u00032a\u0014+*\u001b\u0005\u0001&B\u0001\u001cR\u0015\tQ%KC\u0001T\u0003\u0011Q\u0017M^1\n\u0005U\u0003&aD\"p[BdW\r^5p]N#\u0018mZ3\t\u000bi\"\u0001\u0019A\u001e\t\u000b\u0005#\u0001\u0019\u0001\"\t\u000b\u0019#\u0001\u0019A-\u0011\u0005ikV\"A.\u000b\u0005q\u0013\u0016\u0001\u0002;j[\u0016L!AX.\u0003\u0011\u0011+(/\u0019;j_:\fQ\"Y:l/&$\bn\u0015;biV\u001cH\u0003\u0002(bE\u000eDQAO\u0003A\u0002mBQ!Q\u0003A\u0002\tCQAR\u0003A\u0002e\u000ba\"Y:l/&$\bNU3qYf$v\u000e\u0006\u00034M\u001e|\u0007\"\u0002\u001e\u0007\u0001\u0004Y\u0004\"\u00025\u0007\u0001\u0004I\u0017AD7fgN\fw-\u001a$bGR|'/\u001f\t\u0005U6\\$)D\u0001l\u0015\ta'%\u0001\u0003kCBL\u0017B\u00018l\u0005!1UO\\2uS>t\u0007\"\u0002$\u0007\u0001\u00049E\u0003\u0002(rebDQAO\u0004A\u0002mBQ\u0001[\u0004A\u0002M\u0004B\u0001^<<\u00056\tQO\u0003\u0002wW\u0006Aa-\u001e8di&|g.\u0003\u0002ok\")ai\u0002a\u00013R!1G_>}\u0011\u0015Q\u0004\u00021\u0001<\u0011\u0015\t\u0005\u00021\u0001C\u0011\u0015i\b\u00021\u0001\u007f\u00035!\u0018.\\3pkRl\u0015\u000e\u001c7jgB\u0011!f`\u0005\u0004\u0003\u0003Y#\u0001\u0002'p]\u001e$raMA\u0003\u0003\u000f\tI\u0001C\u0003;\u0013\u0001\u00071\bC\u0003i\u0013\u0001\u0007\u0011\u000eC\u0003~\u0013\u0001\u0007a\u0010F\u00044\u0003\u001b\t9\"!\u0007\t\u000f\u0005=!\u00021\u0001\u0002\u0012\u0005I1/\u001a7fGRLwN\u001c\t\u0004y\u0005M\u0011bAA\u000b{\tq\u0011i\u0019;peN+G.Z2uS>t\u0007\"B!\u000b\u0001\u0004\u0011\u0005\"\u0002$\u000b\u0001\u00049Ec\u0002(\u0002\u001e\u0005}\u0011\u0011\u0005\u0005\b\u0003\u001fY\u0001\u0019AA\t\u0011\u0015\t5\u00021\u0001C\u0011\u001515\u00021\u0001Z)\u001d\u0019\u0014QEA\u0014\u0003SAq!a\u0004\r\u0001\u0004\t\t\u0002C\u0003B\u0019\u0001\u0007!\tC\u0003~\u0019\u0001\u0007a\u0010F\u00044\u0003[\ty#!\r\t\u000f\u0005=Q\u00021\u0001\u0002\u0012!)\u0001.\u0004a\u0001S\")Q0\u0004a\u0001}R9a*!\u000e\u00028\u0005e\u0002bBA\b\u001d\u0001\u0007\u0011\u0011\u0003\u0005\u0006Q:\u0001\r!\u001b\u0005\u0006\r:\u0001\r!W\u0001\u0005a&\u0004X-\u0006\u0003\u0002@\u0005UCCBA!\u0003C\n9\u0007\u0005\u0004\u0002D\u0005%\u0013\u0011\u000b\b\u0004M\u0005\u0015\u0013bAA$A\u00059\u0001/Y2lC\u001e,\u0017\u0002BA&\u0003\u001b\u0012a\u0002U5qK\u0006\u0014G.\u001a$viV\u0014X-C\u0002\u0002P\u0001\u0012Q\u0002U5qKR{7+\u001e9q_J$\b\u0003BA*\u0003+b\u0001\u0001B\u0004\u0002X=\u0011\r!!\u0017\u0003\u0003Q\u000b2!a\u0017C!\rQ\u0013QL\u0005\u0004\u0003?Z#a\u0002(pi\"Lgn\u001a\u0005\b\u0003Gz\u0001\u0019AA3\u0003\u00191W\u000f^;sKB!AgNA)\u0011\u001d\tIg\u0004a\u0001\u0003W\nqaY8oi\u0016DH\u000fE\u00025\u0003[J1!a\u001c6\u0005A)\u00050Z2vi&|gnQ8oi\u0016DH/\u0006\u0003\u0002t\u0005uDCBA;\u0003\u007f\n\u0019\t\u0005\u0004\u0002D\u0005]\u00141P\u0005\u0005\u0003s\niEA\fQSB,\u0017M\u00197f\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKB!\u00111KA?\t\u001d\t9\u0006\u0005b\u0001\u00033Bq!a\u0019\u0011\u0001\u0004\t\t\t\u0005\u0003P)\u0006m\u0004bBA5!\u0001\u0007\u00111N\u0001\rOJ\f7-\u001a4vYN#x\u000e\u001d\u000b\u0007\u0003\u0013\u000b9*a'\u0011\tQ:\u00141\u0012\t\u0005\u0003\u001b\u000b\u0019*\u0004\u0002\u0002\u0010*\u0019\u0011\u0011\u0013*\u0002\t1\fgnZ\u0005\u0005\u0003+\u000byIA\u0004C_>dW-\u00198\t\r\u0005e\u0015\u00031\u0001<\u0003\u0019!\u0018M]4fi\"1a)\u0005a\u0001\u0003;\u0003B!a(\u0002&6\u0011\u0011\u0011\u0015\u0006\u0004\u0003G+\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\n\t\u0005\u001d\u0016\u0011\u0015\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o)\u0019\tY+!,\u00020B!q\nVAF\u0011\u0019\tIJ\u0005a\u0001w!)aI\u0005a\u00013RA\u0011\u0011RAZ\u0003k\u000b9\f\u0003\u0004\u0002\u001aN\u0001\ra\u000f\u0005\u0007\rN\u0001\r!!(\t\r\u0005e6\u00031\u0001C\u0003-\u0019Ho\u001c9NKN\u001c\u0018mZ3\u0015\u0011\u0005-\u0016QXA`\u0003\u0003Da!!'\u0015\u0001\u0004Y\u0004\"\u0002$\u0015\u0001\u0004I\u0006BBA])\u0001\u0007!)A\u0003bMR,'/\u0006\u0003\u0002H\u00065GCCAe\u0003\u001f\f\t.a7\u0002^B!AgNAf!\u0011\t\u0019&!4\u0005\u000f\u0005]SC1\u0001\u0002Z!9\u00111U\u000bA\u0002\u0005u\u0005bBAj+\u0001\u0007\u0011Q[\u0001\ng\u000eDW\rZ;mKJ\u00042\u0001PAl\u0013\r\tI.\u0010\u0002\n'\u000eDW\rZ;mKJDq!!\u001b\u0016\u0001\u0004\tY\u0007C\u0004\u0002`V\u0001\r!!9\u0002\u000bY\fG.^3\u0011\u000b=\u000b\u0019/!3\n\u0007\u0005\u0015\bK\u0001\u0005DC2d\u0017M\u00197f+\u0011\tI/a<\u0015\u0011\u0005-\u0018\u0011_Az\u0003{\u0004Ba\u0014+\u0002nB!\u00111KAx\t\u001d\t9F\u0006b\u0001\u00033Ba!a)\u0017\u0001\u0004I\u0006bBA{-\u0001\u0007\u0011q_\u0001\u0007gf\u001cH/Z7\u0011\u0007q\nI0C\u0002\u0002|v\u0012!d\u00117bgNL7-Q2u_J\u001c\u0016p\u001d;f[B\u0013xN^5eKJDq!a8\u0017\u0001\u0004\ty\u0010E\u0003P\u0003G\fY/\u0006\u0003\u0003\u0004\t%AC\u0003B\u0003\u0005\u0017\u0011iAa\u0004\u0003\u0012A!q\n\u0016B\u0004!\u0011\t\u0019F!\u0003\u0005\u000f\u0005]sC1\u0001\u0002Z!1\u00111U\fA\u0002eCq!a5\u0018\u0001\u0004\t)\u000eC\u0004\u0002j]\u0001\r!a\u001b\t\u000f\u0005}w\u00031\u0001\u0003\u0014A)q*a9\u0003\u0006\u0005)!/\u001a;ssV!!\u0011\u0004B\u0010)!\u0011YB!\t\u0003(\tE\u0002\u0003B(U\u0005;\u0001B!a\u0015\u0003 \u00119\u0011q\u000b\rC\u0002\u0005e\u0003b\u0002B\u00121\u0001\u0007!QE\u0001\bCR$X-\u001c9u!\u0015y\u00151\u001dB\u000e\u0011\u001d\u0011I\u0003\u0007a\u0001\u0005W\t\u0001\"\u0019;uK6\u0004Ho\u001d\t\u0004U\t5\u0012b\u0001B\u0018W\t\u0019\u0011J\u001c;\t\u000f\tM\u0002\u00041\u0001\u0002l\u0005\u0011QmY\u000b\u0005\u0005o\u0011i\u0004\u0006\b\u0003:\t}\"1\tB#\u0005\u0013\u0012iEa\u0016\u0011\t=#&1\b\t\u0005\u0003'\u0012i\u0004B\u0004\u0002Xe\u0011\r!!\u0017\t\u000f\t\r\u0012\u00041\u0001\u0003BA)q*a9\u0003:!9!\u0011F\rA\u0002\t-\u0002B\u0002B$3\u0001\u0007\u0011,\u0001\u0006nS:\u0014\u0015mY6pM\u001aDaAa\u0013\u001a\u0001\u0004I\u0016AC7bq\n\u000b7m[8gM\"9!qJ\rA\u0002\tE\u0013\u0001\u0004:b]\u0012|WNR1di>\u0014\bc\u0001\u0016\u0003T%\u0019!QK\u0016\u0003\r\u0011{WO\u00197f\u0011\u001d\t)0\u0007a\u0001\u0003o,BAa\u0017\u0003bQ\u0001\"Q\fB2\u0005O\u0012IGa\u001b\u0003n\t=$\u0011\u000f\t\u0005\u001fR\u0013y\u0006\u0005\u0003\u0002T\t\u0005DaBA,5\t\u0007\u0011\u0011\f\u0005\b\u0005GQ\u0002\u0019\u0001B3!\u0015y\u00151\u001dB/\u0011\u001d\u0011IC\u0007a\u0001\u0005WAaAa\u0012\u001b\u0001\u0004I\u0006B\u0002B&5\u0001\u0007\u0011\fC\u0004\u0003Pi\u0001\rA!\u0015\t\u000f\u0005M'\u00041\u0001\u0002V\"9!1\u0007\u000eA\u0002\u0005-T\u0003\u0002B;\u0005w\"BBa\u001e\u0003~\t\u0005%1\u0011BD\u0005\u0013\u0003B\u0001N\u001c\u0003zA!\u00111\u000bB>\t\u001d\t9f\u0007b\u0001\u00033BqAa\t\u001c\u0001\u0004\u0011y\bE\u0003P\u0003G\u00149\bC\u0004\u0003*m\u0001\rAa\u000b\t\u000f\t\u00155\u00041\u0001\u0002\u001e\u0006)A-\u001a7bs\"9\u00111[\u000eA\u0002\u0005U\u0007bBA57\u0001\u0007\u00111N\u000b\u0005\u0005\u001b\u0013\u0019\n\u0006\u0006\u0003\u0010\nU%\u0011\u0014BN\u0005;\u0003Ba\u0014+\u0003\u0012B!\u00111\u000bBJ\t\u001d\t9\u0006\bb\u0001\u00033BqAa\t\u001d\u0001\u0004\u00119\nE\u0003P\u0003G\u0014y\tC\u0004\u0003*q\u0001\rAa\u000b\t\r\t\u0015E\u00041\u0001Z\u0011\u001d\t)\u0010\ba\u0001\u0003o,BA!)\u0003(Ra!1\u0015BU\u0005[\u0013yK!-\u00034B!q\n\u0016BS!\u0011\t\u0019Fa*\u0005\u000f\u0005]SD1\u0001\u0002Z!9!1E\u000fA\u0002\t-\u0006#B(\u0002d\n\r\u0006b\u0002B\u0015;\u0001\u0007!1\u0006\u0005\u0007\u0005\u000bk\u0002\u0019A-\t\u000f\u0005MW\u00041\u0001\u0002V\"9!1G\u000fA\u0002\u0005-T\u0003\u0002B\\\u0005{#BB!/\u0003@\n\r'Q\u0019Bn\u0005;\u0004Ba\u0014+\u0003<B!\u00111\u000bB_\t\u001d\t9F\bb\u0001\u00033BqAa\t\u001f\u0001\u0004\u0011\t\rE\u0003P\u0003G\u0014I\fC\u0004\u0003*y\u0001\rAa\u000b\t\u000f\t\u001dg\u00041\u0001\u0003J\u0006iA-\u001a7bs\u001a+hn\u0019;j_:\u0004bAa3\u0003P\nMWB\u0001Bg\u0015\t1\u0018+\u0003\u0003\u0003R\n5'aC%oi\u001a+hn\u0019;j_:\u0004RA!6\u0003Xfk\u0011!U\u0005\u0004\u00053\f&\u0001C(qi&|g.\u00197\t\u000f\u0005Mg\u00041\u0001\u0002V\"9\u0011\u0011\u000e\u0010A\u0002\u0005-\u0004")
public final class Patterns {
    public static <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, IntFunction<Optional<Duration>> delayFunction, Scheduler scheduler, ExecutionContext context) {
        return Patterns$.MODULE$.retry(attempt, attempts, delayFunction, scheduler, context);
    }

    public static <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration delay, Scheduler scheduler, ExecutionContext ec) {
        return Patterns$.MODULE$.retry(attempt, attempts, delay, scheduler, ec);
    }

    public static <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration delay, ClassicActorSystemProvider system) {
        return Patterns$.MODULE$.retry(attempt, attempts, delay, system);
    }

    public static <T> Future<T> retry(Callable<Future<T>> attempt, int attempts, FiniteDuration delay, Scheduler scheduler, ExecutionContext context) {
        return Patterns$.MODULE$.retry(attempt, attempts, delay, scheduler, context);
    }

    public static <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration minBackoff, Duration maxBackoff, double randomFactor, Scheduler scheduler, ExecutionContext ec) {
        return Patterns$.MODULE$.retry(attempt, attempts, minBackoff, maxBackoff, randomFactor, scheduler, ec);
    }

    public static <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, Duration minBackoff, Duration maxBackoff, double randomFactor, ClassicActorSystemProvider system) {
        return Patterns$.MODULE$.retry(attempt, attempts, minBackoff, maxBackoff, randomFactor, system);
    }

    public static <T> CompletionStage<T> retry(Callable<CompletionStage<T>> attempt, int attempts, ExecutionContext ec) {
        return Patterns$.MODULE$.retry(attempt, attempts, ec);
    }

    public static <T> CompletionStage<T> after(Duration duration, Scheduler scheduler, ExecutionContext context, Callable<CompletionStage<T>> value) {
        return Patterns$.MODULE$.after(duration, scheduler, context, value);
    }

    public static <T> CompletionStage<T> after(Duration duration, ClassicActorSystemProvider system, Callable<CompletionStage<T>> value) {
        return Patterns$.MODULE$.after(duration, system, value);
    }

    public static <T> Future<T> after(FiniteDuration duration, Scheduler scheduler, ExecutionContext context, Callable<Future<T>> value) {
        return Patterns$.MODULE$.after(duration, scheduler, context, value);
    }

    public static CompletionStage<Boolean> gracefulStop(ActorRef target, Duration timeout2, Object stopMessage) {
        return Patterns$.MODULE$.gracefulStop(target, timeout2, stopMessage);
    }

    public static Future<Boolean> gracefulStop(ActorRef target, FiniteDuration timeout2, Object stopMessage) {
        return Patterns$.MODULE$.gracefulStop(target, timeout2, stopMessage);
    }

    public static CompletionStage<Boolean> gracefulStop(ActorRef target, Duration timeout2) {
        return Patterns$.MODULE$.gracefulStop(target, timeout2);
    }

    public static Future<Boolean> gracefulStop(ActorRef target, FiniteDuration timeout2) {
        return Patterns$.MODULE$.gracefulStop(target, timeout2);
    }

    public static <T> PipeToSupport.PipeableCompletionStage<T> pipe(CompletionStage<T> future, ExecutionContext context) {
        return Patterns$.MODULE$.pipe(future, context);
    }

    public static <T> PipeToSupport.PipeableFuture<T> pipe(Future<T> future, ExecutionContext context) {
        return Patterns$.MODULE$.pipe(future, context);
    }

    public static CompletionStage<Object> askWithReplyTo(ActorSelection selection, Function<ActorRef, Object> messageFactory, Duration timeout2) {
        return Patterns$.MODULE$.askWithReplyTo(selection, messageFactory, timeout2);
    }

    public static Future<Object> askWithReplyTo(ActorSelection selection, Function<ActorRef, Object> messageFactory, long timeoutMillis) {
        return Patterns$.MODULE$.askWithReplyTo(selection, messageFactory, timeoutMillis);
    }

    public static Future<Object> ask(ActorSelection selection, Object message, long timeoutMillis) {
        return Patterns$.MODULE$.ask(selection, message, timeoutMillis);
    }

    public static CompletionStage<Object> ask(ActorSelection selection, Object message, Duration timeout2) {
        return Patterns$.MODULE$.ask(selection, message, timeout2);
    }

    public static Future<Object> ask(ActorSelection selection, Object message, Timeout timeout2) {
        return Patterns$.MODULE$.ask(selection, message, timeout2);
    }

    public static Future<Object> askWithReplyTo(ActorRef actor, Function<ActorRef, Object> messageFactory, long timeoutMillis) {
        return Patterns$.MODULE$.askWithReplyTo(actor, messageFactory, timeoutMillis);
    }

    public static Future<Object> ask(ActorRef actor, Object message, long timeoutMillis) {
        return Patterns$.MODULE$.ask(actor, message, timeoutMillis);
    }

    public static CompletionStage<Object> askWithReplyTo(ActorRef actor, akka.japi.function.Function<ActorRef, Object> messageFactory, Duration timeout2) {
        return Patterns$.MODULE$.askWithReplyTo(actor, messageFactory, timeout2);
    }

    public static Future<Object> askWithReplyTo(ActorRef actor, Function<ActorRef, Object> messageFactory, Timeout timeout2) {
        return Patterns$.MODULE$.askWithReplyTo(actor, messageFactory, timeout2);
    }

    public static CompletionStage<Object> askWithStatus(ActorRef actor, Object message, Duration timeout2) {
        return Patterns$.MODULE$.askWithStatus(actor, message, timeout2);
    }

    public static CompletionStage<Object> ask(ActorRef actor, Object message, Duration timeout2) {
        return Patterns$.MODULE$.ask(actor, message, timeout2);
    }

    public static Future<Object> ask(ActorRef actor, Object message, Timeout timeout2) {
        return Patterns$.MODULE$.ask(actor, message, timeout2);
    }
}

