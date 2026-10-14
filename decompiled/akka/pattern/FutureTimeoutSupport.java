/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ClassicActorSystemProvider;
import akka.actor.Scheduler;
import akka.dispatch.Futures$;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import scala.Function0;
import scala.Option;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005Y4qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003\u0014\u0001\u0011\u0005A\u0003C\u0003\u0019\u0001\u0011\u0005\u0011\u0004C\u0003C\u0001\u0011\u00051\tC\u0003\u0019\u0001\u0011\u0005a\u000bC\u0003C\u0001\u0011\u0005!N\u0001\u000bGkR,(/\u001a+j[\u0016|W\u000f^*vaB|'\u000f\u001e\u0006\u0003\u0011%\tq\u0001]1ui\u0016\u0014hNC\u0001\u000b\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001!\u0004\t\u0003\u001dEi\u0011a\u0004\u0006\u0002!\u0005)1oY1mC&\u0011!c\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005)\u0002C\u0001\b\u0017\u0013\t9rB\u0001\u0003V]&$\u0018!B1gi\u0016\u0014XC\u0001\u000e&)\tY2\b\u0006\u0002\u001dmQ\u0011QD\f\t\u0004=\u0005\u001aS\"A\u0010\u000b\u0005\u0001z\u0011AC2p]\u000e,(O]3oi&\u0011!e\b\u0002\u0007\rV$XO]3\u0011\u0005\u0011*C\u0002\u0001\u0003\u0006M\t\u0011\ra\n\u0002\u0002)F\u0011\u0001f\u000b\t\u0003\u001d%J!AK\b\u0003\u000f9{G\u000f[5oOB\u0011a\u0002L\u0005\u0003[=\u00111!\u00118z\u0011\u0015y#\u0001q\u00011\u0003\u0019\u0019\u0018p\u001d;f[B\u0011\u0011\u0007N\u0007\u0002e)\u00111'C\u0001\u0006C\u000e$xN]\u0005\u0003kI\u0012!d\u00117bgNL7-Q2u_J\u001c\u0016p\u001d;f[B\u0013xN^5eKJDaa\u000e\u0002\u0005\u0002\u0004A\u0014!\u0002<bYV,\u0007c\u0001\b:;%\u0011!h\u0004\u0002\ty\tLh.Y7f}!)AH\u0001a\u0001{\u0005AA-\u001e:bi&|g\u000e\u0005\u0002?\u00016\tqH\u0003\u0002=?%\u0011\u0011i\u0010\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003Q\tg\r^3s\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKV\u0011A)\u0015\u000b\u0003\u000bV#\"AR*\u0015\u0005\u001d\u0013\u0006c\u0001%O!6\t\u0011J\u0003\u0002!\u0015*\u00111\nT\u0001\u0005kRLGNC\u0001N\u0003\u0011Q\u0017M^1\n\u0005=K%aD\"p[BdW\r^5p]N#\u0018mZ3\u0011\u0005\u0011\nF!\u0002\u0014\u0004\u0005\u00049\u0003\"B\u0018\u0004\u0001\b\u0001\u0004BB\u001c\u0004\t\u0003\u0007A\u000bE\u0002\u000fs\u001dCQ\u0001P\u0002A\u0002u*\"a\u0016/\u0015\u0007a#W\r\u0006\u0002ZER\u0011!,\u0018\t\u0004=\u0005Z\u0006C\u0001\u0013]\t\u00151CA1\u0001(\u0011\u0015qF\u0001q\u0001`\u0003\t)7\r\u0005\u0002\u001fA&\u0011\u0011m\b\u0002\u0011\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRDaa\u000e\u0003\u0005\u0002\u0004\u0019\u0007c\u0001\b:5\")A\b\u0002a\u0001{!)a\r\u0002a\u0001O\u0006)Qo]5oOB\u0011\u0011\u0007[\u0005\u0003SJ\u0012\u0011bU2iK\u0012,H.\u001a:\u0016\u0005-\u0004Hc\u00017ukR\u0011QN\u001d\u000b\u0003]F\u00042\u0001\u0013(p!\t!\u0003\u000fB\u0003'\u000b\t\u0007q\u0005C\u0003_\u000b\u0001\u000fq\f\u0003\u00048\u000b\u0011\u0005\ra\u001d\t\u0004\u001der\u0007\"\u0002\u001f\u0006\u0001\u0004i\u0004\"\u00024\u0006\u0001\u00049\u0007")
public interface FutureTimeoutSupport {
    public static /* synthetic */ Future after$(FutureTimeoutSupport $this, FiniteDuration duration, Function0 value, ClassicActorSystemProvider system) {
        return $this.after(duration, value, system);
    }

    default public <T> Future<T> after(FiniteDuration duration, Function0<Future<T>> value, ClassicActorSystemProvider system) {
        return this.after(duration, system.classicSystem().scheduler(), value, system.classicSystem().dispatcher());
    }

    public static /* synthetic */ CompletionStage afterCompletionStage$(FutureTimeoutSupport $this, FiniteDuration duration, Function0 value, ClassicActorSystemProvider system) {
        return $this.afterCompletionStage(duration, value, system);
    }

    default public <T> CompletionStage<T> afterCompletionStage(FiniteDuration duration, Function0<CompletionStage<T>> value, ClassicActorSystemProvider system) {
        return this.afterCompletionStage(duration, system.classicSystem().scheduler(), value, system.classicSystem().dispatcher());
    }

    public static /* synthetic */ Future after$(FutureTimeoutSupport $this, FiniteDuration duration, Scheduler using2, Function0 value, ExecutionContext ec) {
        return $this.after(duration, using2, value, ec);
    }

    default public <T> Future<T> after(FiniteDuration duration, Scheduler using2, Function0<Future<T>> value, ExecutionContext ec) {
        if (duration.isFinite() && duration.length() < 1L) {
            Future<T> future;
            try {
                future = value.apply();
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable t2 = option.get();
                    future = Future$.MODULE$.failed(t2);
                }
                throw throwable;
            }
            return future;
        }
        Promise p = Promise$.MODULE$.apply();
        using2.scheduleOnce(duration, () -> p.completeWith(FutureTimeoutSupport.liftedTree1$1(value)), ec);
        return p.future();
    }

    public static /* synthetic */ CompletionStage afterCompletionStage$(FutureTimeoutSupport $this, FiniteDuration duration, Scheduler using2, Function0 value, ExecutionContext ec) {
        return $this.afterCompletionStage(duration, using2, value, ec);
    }

    default public <T> CompletionStage<T> afterCompletionStage(FiniteDuration duration, Scheduler using2, Function0<CompletionStage<T>> value, ExecutionContext ec) {
        if (duration.isFinite() && duration.length() < 1L) {
            CompletionStage<T> completionStage;
            try {
                completionStage = value.apply();
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable t2 = option.get();
                    completionStage = Futures$.MODULE$.failedCompletionStage(t2);
                }
                throw throwable;
            }
            return completionStage;
        }
        CompletableFuture p = new CompletableFuture();
        using2.scheduleOnce(duration, () -> {
            try {
                CompletionStage future = (CompletionStage)value.apply();
                future.whenComplete(new BiConsumer<T, Throwable>(null, p){
                    private final CompletableFuture p$2;

                    public BiConsumer<T, Throwable> andThen(BiConsumer<? super T, ? super Throwable> x$1) {
                        return BiConsumer.super.andThen(x$1);
                    }

                    public void accept(T t2, Throwable ex) {
                        Serializable serializable = t2 != null ? BoxesRunTime.boxToBoolean(this.p$2.complete(t2)) : BoxedUnit.UNIT;
                        if (ex != null) {
                            this.p$2.completeExceptionally(ex);
                            return;
                        }
                    }
                    {
                        this.p$2 = p$2;
                    }
                });
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable ex = option.get();
                    p.completeExceptionally(ex);
                }
                throw throwable;
            }
        }, ec);
        return p;
    }

    private static /* synthetic */ Future liftedTree1$1(Function0 value$1) {
        Future future;
        try {
            future = (Future)value$1.apply();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable t2 = option.get();
                future = Future$.MODULE$.failed(t2);
            }
            throw throwable;
        }
        return future;
    }

    public static void $init$(FutureTimeoutSupport $this) {
    }
}

