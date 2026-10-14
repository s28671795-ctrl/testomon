/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSelection$;
import akka.actor.Status;
import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005=ea\u0002\u0010 !\u0003\r\t\u0001\n\u0005\u0006W\u0001!\t\u0001\f\u0004\u0005a\u0001\u0011\u0011\u0007\u0003\u00054\u0005\t\u0015\r\u0011\"\u00015\u0011!1%A!A!\u0002\u0013)\u0004\u0002C$\u0003\u0005\u0003\u0005\u000b1\u0002%\t\u000b-\u0013A\u0011\u0001'\t\u000bI\u0013A\u0011A*\t\u000f}\u0013\u0011\u0013!C\u0001A\")AN\u0001C\u0001[\"9AOAI\u0001\n\u0003)\b\"B<\u0003\t\u0003A\b\"B<\u0003\t\u0003Q\b\"B<\u0003\t\u0003i\b\"B<\u0003\t\u0003yhABA\u0003\u0001\t\t9\u0001C\u00054\u001f\t\u0015\r\u0011\"\u0001\u0002\f!Iai\u0004B\u0001B\u0003%\u0011Q\u0002\u0005\t\u000f>\u0011\t\u0011)A\u0006\u0011\"11j\u0004C\u0001\u0003_AaAU\b\u0005\u0002\u0005m\u0002\u0002C0\u0010#\u0003%\t!a\u0011\t\r1|A\u0011AA$\u0011!!x\"%A\u0005\u0002\u0005=\u0003BB<\u0010\t\u0003\t\u0019\u0006\u0003\u0004x\u001f\u0011\u0005\u0011q\u000b\u0005\u0007o>!\t!!\u0018\t\r]|A\u0011AA1\u0011\u001d\t9\u0007\u0001C\u0002\u0003SBq!a\u001f\u0001\t\u0007\tiHA\u0007QSB,Gk\\*vaB|'\u000f\u001e\u0006\u0003A\u0005\nq\u0001]1ui\u0016\u0014hNC\u0001#\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001!\n\t\u0003M%j\u0011a\n\u0006\u0002Q\u0005)1oY1mC&\u0011!f\n\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005i\u0003C\u0001\u0014/\u0013\tysE\u0001\u0003V]&$(A\u0004)ja\u0016\f'\r\\3GkR,(/Z\u000b\u0003eu\u001a\"AA\u0013\u0002\r\u0019,H/\u001e:f+\u0005)\u0004c\u0001\u001c:w5\tqG\u0003\u00029O\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005i:$A\u0002$viV\u0014X\r\u0005\u0002={1\u0001A!\u0002 \u0003\u0005\u0004y$!\u0001+\u0012\u0005\u0001\u001b\u0005C\u0001\u0014B\u0013\t\u0011uEA\u0004O_RD\u0017N\\4\u0011\u0005\u0019\"\u0015BA#(\u0005\r\te._\u0001\bMV$XO]3!\u0003A)\u00070Z2vi&|gnQ8oi\u0016DH\u000f\u0005\u00027\u0013&\u0011!j\u000e\u0002\u0011\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR\fa\u0001P5oSRtDCA'R)\tq\u0005\u000bE\u0002P\u0005mj\u0011\u0001\u0001\u0005\u0006\u000f\u001a\u0001\u001d\u0001\u0013\u0005\u0006g\u0019\u0001\r!N\u0001\u0007a&\u0004X\rV8\u0015\u0005QkFCA\u001bV\u0011\u001d1v\u0001%AA\u0004]\u000baa]3oI\u0016\u0014\bC\u0001-\\\u001b\u0005I&B\u0001.\"\u0003\u0015\t7\r^8s\u0013\ta\u0016L\u0001\u0005BGR|'OU3g\u0011\u0015qv\u00011\u0001X\u0003%\u0011XmY5qS\u0016tG/\u0001\tqSB,Gk\u001c\u0013eK\u001a\fW\u000f\u001c;%eQ\u0011\u0011m\u001b\u0016\u0003/\n\\\u0013a\u0019\t\u0003I&l\u0011!\u001a\u0006\u0003M\u001e\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005!<\u0013AC1o]>$\u0018\r^5p]&\u0011!.\u001a\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007\"\u00020\t\u0001\u00049\u0016a\u00049ja\u0016$vnU3mK\u000e$\u0018n\u001c8\u0015\u00059\u0004HCA\u001bp\u0011\u001d1\u0016\u0002%AA\u0004]CQAX\u0005A\u0002E\u0004\"\u0001\u0017:\n\u0005ML&AD!di>\u00148+\u001a7fGRLwN\\\u0001\u001aa&\u0004X\rV8TK2,7\r^5p]\u0012\"WMZ1vYR$#\u0007\u0006\u0002bm\")aL\u0003a\u0001c\u0006\u0011Ao\u001c\u000b\u0003\u001dfDQAX\u0006A\u0002]#2AT>}\u0011\u0015qF\u00021\u0001X\u0011\u00151F\u00021\u0001X)\tqe\u0010C\u0003_\u001b\u0001\u0007\u0011\u000fF\u0003O\u0003\u0003\t\u0019\u0001C\u0003_\u001d\u0001\u0007\u0011\u000fC\u0003W\u001d\u0001\u0007qKA\fQSB,\u0017M\u00197f\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKV!\u0011\u0011BA\u0011'\tyQ%\u0006\u0002\u0002\u000eA1\u0011qBA\u000e\u0003?i!!!\u0005\u000b\u0007a\n\u0019B\u0003\u0003\u0002\u0016\u0005]\u0011\u0001B;uS2T!!!\u0007\u0002\t)\fg/Y\u0005\u0005\u0003;\t\tBA\bD_6\u0004H.\u001a;j_:\u001cF/Y4f!\ra\u0014\u0011\u0005\u0003\u0006}=\u0011\ra\u0010\u0015\u0004%\u0005\u0015\u0002\u0003BA\u0014\u0003Wi!!!\u000b\u000b\u0007\u0005U\u0011%\u0003\u0003\u0002.\u0005%\"AB;okN,G\r\u0006\u0003\u00022\u0005eB\u0003BA\u001a\u0003k\u0001BaT\b\u0002 !)qi\u0005a\u0002\u0011\"\"\u0011QGA\u0013\u0011\u0019\u00194\u00031\u0001\u0002\u000eQ!\u0011QHA!)\u0011\ti!a\u0010\t\u000fY#\u0002\u0013!a\u0002/\")a\f\u0006a\u0001/R\u0019\u0011-!\u0012\t\u000by+\u0002\u0019A,\u0015\t\u0005%\u0013Q\n\u000b\u0005\u0003\u001b\tY\u0005C\u0004W-A\u0005\t9A,\t\u000by3\u0002\u0019A9\u0015\u0007\u0005\f\t\u0006C\u0003_/\u0001\u0007\u0011\u000f\u0006\u0003\u00024\u0005U\u0003\"\u00020\u0019\u0001\u00049FCBA\u001a\u00033\nY\u0006C\u0003_3\u0001\u0007q\u000bC\u0003W3\u0001\u0007q\u000b\u0006\u0003\u00024\u0005}\u0003\"\u00020\u001b\u0001\u0004\tHCBA\u001a\u0003G\n)\u0007C\u0003_7\u0001\u0007\u0011\u000fC\u0003W7\u0001\u0007q+\u0001\u0003qSB,W\u0003BA6\u0003g\"B!!\u001c\u0002xQ!\u0011qNA;!\u0011y%!!\u001d\u0011\u0007q\n\u0019\bB\u0003?9\t\u0007q\bC\u0003H9\u0001\u000f\u0001\n\u0003\u000449\u0001\u0007\u0011\u0011\u0010\t\u0005me\n\t(A\nqSB,7i\\7qY\u0016$\u0018n\u001c8Ti\u0006<W-\u0006\u0003\u0002\u0000\u0005\u001dE\u0003BAA\u0003\u0017#B!a!\u0002\nB!qjDAC!\ra\u0014q\u0011\u0003\u0006}u\u0011\ra\u0010\u0005\u0006\u000fv\u0001\u001d\u0001\u0013\u0005\u0007gu\u0001\r!!$\u0011\r\u0005=\u00111DAC\u0001")
public interface PipeToSupport {
    public static /* synthetic */ PipeableFuture pipe$(PipeToSupport $this, Future future, ExecutionContext executionContext) {
        return $this.pipe(future, executionContext);
    }

    default public <T> PipeableFuture<T> pipe(Future<T> future, ExecutionContext executionContext) {
        return new PipeableFuture<T>(this, future, executionContext);
    }

    public static /* synthetic */ PipeableCompletionStage pipeCompletionStage$(PipeToSupport $this, CompletionStage future, ExecutionContext executionContext) {
        return $this.pipeCompletionStage(future, executionContext);
    }

    default public <T> PipeableCompletionStage<T> pipeCompletionStage(CompletionStage<T> future, ExecutionContext executionContext) {
        return new PipeableCompletionStage<T>(this, future, executionContext);
    }

    public static void $init$(PipeToSupport $this) {
    }

    public final class PipeableCompletionStage<T> {
        private final CompletionStage<T> future;

        public CompletionStage<T> future() {
            return this.future;
        }

        public CompletionStage<T> pipeTo(ActorRef recipient, ActorRef sender) {
            return this.future().whenComplete(new BiConsumer<T, Throwable>(null, recipient, sender){
                private final ActorRef recipient$3;
                private final ActorRef sender$3;

                public BiConsumer<T, Throwable> andThen(BiConsumer<? super T, ? super Throwable> x$1) {
                    return BiConsumer.super.andThen(x$1);
                }

                public void accept(T t2, Throwable ex) {
                    if (t2 != null) {
                        this.recipient$3.$bang(t2, this.sender$3);
                    }
                    if (ex != null) {
                        this.recipient$3.$bang(new Status.Failure(ex), this.sender$3);
                        return;
                    }
                }
                {
                    this.recipient$3 = recipient$3;
                    this.sender$3 = sender$3;
                }
            });
        }

        public ActorRef pipeTo$default$2(ActorRef recipient) {
            return Actor$.MODULE$.noSender();
        }

        public CompletionStage<T> pipeToSelection(ActorSelection recipient, ActorRef sender) {
            return this.future().whenComplete(new BiConsumer<T, Throwable>(null, recipient, sender){
                private final ActorSelection recipient$4;
                private final ActorRef sender$4;

                public BiConsumer<T, Throwable> andThen(BiConsumer<? super T, ? super Throwable> x$1) {
                    return BiConsumer.super.andThen(x$1);
                }

                public void accept(T t2, Throwable ex) {
                    if (t2 != null) {
                        ActorSelection$.MODULE$.toScala(this.recipient$4).$bang(t2, this.sender$4);
                    }
                    if (ex != null) {
                        ActorSelection$.MODULE$.toScala(this.recipient$4).$bang(new Status.Failure(ex), this.sender$4);
                        return;
                    }
                }
                {
                    this.recipient$4 = recipient$4;
                    this.sender$4 = sender$4;
                }
            });
        }

        public ActorRef pipeToSelection$default$2(ActorSelection recipient) {
            return Actor$.MODULE$.noSender();
        }

        public PipeableCompletionStage<T> to(ActorRef recipient) {
            return this.to(recipient, Actor$.MODULE$.noSender());
        }

        public PipeableCompletionStage<T> to(ActorRef recipient, ActorRef sender) {
            this.pipeTo(recipient, sender);
            return this;
        }

        public PipeableCompletionStage<T> to(ActorSelection recipient) {
            return this.to(recipient, Actor$.MODULE$.noSender());
        }

        public PipeableCompletionStage<T> to(ActorSelection recipient, ActorRef sender) {
            this.pipeToSelection(recipient, sender);
            return this;
        }

        public PipeableCompletionStage(PipeToSupport $outer, CompletionStage<T> future, ExecutionContext executionContext) {
            this.future = future;
        }
    }

    public final class PipeableFuture<T> {
        private final Future<T> future;
        private final ExecutionContext executionContext;

        public Future<T> future() {
            return this.future;
        }

        public Future<T> pipeTo(ActorRef recipient, ActorRef sender) {
            return this.future().andThen(new Serializable(null, recipient, sender){
                private static final long serialVersionUID = 0L;
                private final ActorRef recipient$1;
                private final ActorRef sender$1;

                public final <A1 extends Try<T>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof Success) {
                        Success success = (Success)A1;
                        T r = success.value();
                        this.recipient$1.$bang(r, this.sender$1);
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof Failure) {
                        Failure failure = (Failure)A1;
                        Throwable f = failure.exception();
                        this.recipient$1.$bang(new Status.Failure(f), this.sender$1);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Try<T> x1) {
                    Try<T> try_ = x1;
                    if (try_ instanceof Success) {
                        return true;
                    }
                    return try_ instanceof Failure;
                }
                {
                    this.recipient$1 = recipient$1;
                    this.sender$1 = sender$1;
                }
            }, this.executionContext);
        }

        public ActorRef pipeTo$default$2(ActorRef recipient) {
            return Actor$.MODULE$.noSender();
        }

        public Future<T> pipeToSelection(ActorSelection recipient, ActorRef sender) {
            return this.future().andThen(new Serializable(null, recipient, sender){
                private static final long serialVersionUID = 0L;
                private final ActorSelection recipient$2;
                private final ActorRef sender$2;

                public final <A1 extends Try<T>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    A1 A1 = x1;
                    if (A1 instanceof Success) {
                        Success success = (Success)A1;
                        T r = success.value();
                        ActorSelection$.MODULE$.toScala(this.recipient$2).$bang(r, this.sender$2);
                        return (B1)BoxedUnit.UNIT;
                    }
                    if (A1 instanceof Failure) {
                        Failure failure = (Failure)A1;
                        Throwable f = failure.exception();
                        ActorSelection$.MODULE$.toScala(this.recipient$2).$bang(new Status.Failure(f), this.sender$2);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(Try<T> x1) {
                    Try<T> try_ = x1;
                    if (try_ instanceof Success) {
                        return true;
                    }
                    return try_ instanceof Failure;
                }
                {
                    this.recipient$2 = recipient$2;
                    this.sender$2 = sender$2;
                }
            }, this.executionContext);
        }

        public ActorRef pipeToSelection$default$2(ActorSelection recipient) {
            return Actor$.MODULE$.noSender();
        }

        public PipeableFuture<T> to(ActorRef recipient) {
            return this.to(recipient, Actor$.MODULE$.noSender());
        }

        public PipeableFuture<T> to(ActorRef recipient, ActorRef sender) {
            this.pipeTo(recipient, sender);
            return this;
        }

        public PipeableFuture<T> to(ActorSelection recipient) {
            return this.to(recipient, Actor$.MODULE$.noSender());
        }

        public PipeableFuture<T> to(ActorSelection recipient, ActorRef sender) {
            this.pipeToSelection(recipient, sender);
            return this;
        }

        public PipeableFuture(PipeToSupport $outer, Future<T> future, ExecutionContext executionContext) {
            this.future = future;
            this.executionContext = executionContext;
        }
    }
}

