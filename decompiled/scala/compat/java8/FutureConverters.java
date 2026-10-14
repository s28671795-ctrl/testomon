/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.compat.java8.FutureConverters$FutureOps$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tmr!B\u0015+\u0011\u0003\td!B\u001a+\u0011\u0003!\u0004\"B\u001d\u0002\t\u0003Q\u0004\"B\u001e\u0002\t\u0003a\u0004\"\u0002.\u0002\t\u0003Y\u0006\"B2\u0002\t\u0003!\u0007BB2\u0002\t\u0003\t\u0019\u0001C\u0004\u0002\b\u0005!\t!!\u0003\t\u000f\u0005\u001d\u0011\u0001\"\u0001\u0002\u001c!9\u0011qD\u0001\u0005\u0002\u0005\u0005\u0002bBA\u0015\u0003\u0011\u0005\u00111\u0006\u0005\b\u0003s\tA\u0011AA\u001e\u0011\u001d\tI%\u0001C\u0001\u0003\u0017Bq!!\u0017\u0002\t\u0007\tYF\u0002\u0004\u0002d\u0005\u0011\u0011Q\r\u0005\u000b\u0003_r!Q1A\u0005\u0002\u0005E\u0004BCA=\u001d\t\u0005\t\u0015!\u0003\u0002t!1\u0011H\u0004C\u0001\u0003wBaa\u000f\b\u0005\u0002\u0005\u0005\u0005\"CAC\u001d\u0005\u0005I\u0011IAD\u0011%\tyIDA\u0001\n\u0003\n\tjB\u0005\u0002Z\u0005\t\t\u0011#\u0001\u0002&\u001aI\u00111M\u0001\u0002\u0002#\u0005\u0011q\u0015\u0005\u0007sY!\t!!+\t\u000f\u0005-f\u0003\"\u0002\u0002.\"I\u0011Q\u0018\f\u0002\u0002\u0013\u0015\u0011q\u0018\u0005\n\u0003\u00174\u0012\u0011!C\u0003\u0003\u001bDq!!8\u0002\t\u0007\tyN\u0002\u0004\u0002f\u0006\u0011\u0011q\u001d\u0005\u000b\u0003_b\"Q1A\u0005\u0002\u0005-\bBCA=9\t\u0005\t\u0015!\u0003\u0002n\"1\u0011\b\bC\u0001\u0003gDaA\u0017\u000f\u0005\u0002\u0005e\b\"CAC9\u0005\u0005I\u0011IAD\u0011%\ty\tHA\u0001\n\u0003\nipB\u0005\u0002^\u0006\t\t\u0011#\u0001\u0003\n\u0019I\u0011Q]\u0001\u0002\u0002#\u0005!1\u0002\u0005\u0007s\u0011\"\tA!\u0004\t\u000f\t=A\u0005\"\u0002\u0003\u0012!I\u0011Q\u0018\u0013\u0002\u0002\u0013\u0015!q\u0004\u0005\n\u0003\u0017$\u0013\u0011!C\u0003\u0005W\t\u0001CR;ukJ,7i\u001c8wKJ$XM]:\u000b\u0005-b\u0013!\u00026bm\u0006D$BA\u0017/\u0003\u0019\u0019w.\u001c9bi*\tq&A\u0003tG\u0006d\u0017m\u0001\u0001\u0011\u0005I\nQ\"\u0001\u0016\u0003!\u0019+H/\u001e:f\u0007>tg/\u001a:uKJ\u001c8CA\u00016!\t1t'D\u0001/\u0013\tAdF\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003E\na\u0001^8KCZ\fWCA\u001fK)\tq4\u000bE\u0002@\r\"k\u0011\u0001\u0011\u0006\u0003\u0003\n\u000b!bY8oGV\u0014(/\u001a8u\u0015\t\u0019E)\u0001\u0003vi&d'\"A#\u0002\t)\fg/Y\u0005\u0003\u000f\u0002\u0013qbQ8na2,G/[8o'R\fw-\u001a\t\u0003\u0013*c\u0001\u0001B\u0003L\u0007\t\u0007AJA\u0001U#\ti\u0005\u000b\u0005\u00027\u001d&\u0011qJ\f\u0002\b\u001d>$\b.\u001b8h!\t1\u0014+\u0003\u0002S]\t\u0019\u0011I\\=\t\u000bQ\u001b\u0001\u0019A+\u0002\u0003\u0019\u00042A\u0016-I\u001b\u00059&BA!/\u0013\tIvK\u0001\u0004GkR,(/Z\u0001\bi>\u001c6-\u00197b+\tav\f\u0006\u0002^AB\u0019a\u000b\u00170\u0011\u0005%{F!B&\u0005\u0005\u0004a\u0005\"B1\u0005\u0001\u0004\u0011\u0017AA2t!\rydIX\u0001\u0014MJ|W.\u0012=fGV$xN]*feZL7-\u001a\u000b\u0004K\"l\u0007C\u0001,g\u0013\t9wKA\u0010Fq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\u001cVM\u001d<jG\u0016DQ![\u0003A\u0002)\f\u0011!\u001a\t\u0003\u007f-L!\u0001\u001c!\u0003\u001f\u0015CXmY;u_J\u001cVM\u001d<jG\u0016DQA\\\u0003A\u0002=\f\u0001B]3q_J$XM\u001d\t\u0004aN,X\"A9\u000b\u0005I\u0014\u0015\u0001\u00034v]\u000e$\u0018n\u001c8\n\u0005Q\f(\u0001C\"p]N,X.\u001a:\u0011\u0005YthBA<}\u001d\tA80D\u0001z\u0015\tQ\b'\u0001\u0004=e>|GOP\u0005\u0002_%\u0011QPL\u0001\ba\u0006\u001c7.Y4f\u0013\ry\u0018\u0011\u0001\u0002\n)\"\u0014xn^1cY\u0016T!! \u0018\u0015\u0007\u0015\f)\u0001C\u0003j\r\u0001\u0007!.\u0001\u0007ge>lW\t_3dkR|'\u000f\u0006\u0004\u0002\f\u0005E\u0011\u0011\u0004\t\u0004-\u00065\u0011bAA\b/\nAR\t_3dkRLwN\\\"p]R,\u0007\u0010^#yK\u000e,Ho\u001c:\t\r%<\u0001\u0019AA\n!\ry\u0014QC\u0005\u0004\u0003/\u0001%\u0001C#yK\u000e,Ho\u001c:\t\u000b9<\u0001\u0019A8\u0015\t\u0005-\u0011Q\u0004\u0005\u0007S\"\u0001\r!a\u0005\u0002-\u001ddwNY1m\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR,\"!a\t\u0011\u0007Y\u000b)#C\u0002\u0002(]\u0013\u0001#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0002\u000fA\u0014x.\\5tKV!\u0011QFA\u001c)\t\ty\u0003E\u0003W\u0003c\t)$C\u0002\u00024]\u0013q\u0001\u0015:p[&\u001cX\rE\u0002J\u0003o!Qa\u0013\u0006C\u00021\u000b1b[3qiB\u0013x.\\5tKV!\u0011QHA\")\u0011\ty$!\u0012\u0011\u000bY\u000b\t$!\u0011\u0011\u0007%\u000b\u0019\u0005B\u0003L\u0017\t\u0007A\nC\u0004\u0002H-\u0001\r!!\u0011\u0002\u0003Y\fQBZ1jY\u0016$\u0007K]8nSN,W\u0003BA'\u0003'\"B!a\u0014\u0002VA)a+!\r\u0002RA\u0019\u0011*a\u0015\u0005\u000b-c!\u0019\u0001'\t\r\u0005]C\u00021\u0001v\u0003\t)\u00070A\u0005GkR,(/Z(qgV!\u0011QLAP)\u0011\ty&!)\u0011\u000b\u0005\u0005d\"!(\u000e\u0003\u0005\u0011\u0011BR;ukJ,w\n]:\u0016\t\u0005\u001d\u0014qO\n\u0004\u001d\u0005%\u0004c\u0001\u001c\u0002l%\u0019\u0011Q\u000e\u0018\u0003\r\u0005s\u0017PV1m\u0003\u0019yvl]3mMV\u0011\u00111\u000f\t\u0005-b\u000b)\bE\u0002J\u0003o\"Qa\u0013\bC\u00021\u000bqaX0tK24\u0007\u0005\u0006\u0003\u0002~\u0005}\u0004#BA1\u001d\u0005U\u0004bBA8#\u0001\u0007\u00111O\u000b\u0003\u0003\u0007\u0003Ba\u0010$\u0002v\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\nB\u0019a'a#\n\u0007\u00055eFA\u0002J]R\fa!Z9vC2\u001cH\u0003BAJ\u00033\u00032ANAK\u0013\r\t9J\f\u0002\b\u0005>|G.Z1o\u0011!\tY\nFA\u0001\u0002\u0004\u0001\u0016a\u0001=%cA\u0019\u0011*a(\u0005\u000b-k!\u0019\u0001'\t\rQk\u0001\u0019AAR!\u00111\u0006,!(\u0011\u0007\u0005\u0005dc\u0005\u0002\u0017kQ\u0011\u0011QU\u0001\u0011i>T\u0015M^1%Kb$XM\\:j_:,B!a,\u00026R!\u0011\u0011WA\\!\u0011yd)a-\u0011\u0007%\u000b)\fB\u0003L1\t\u0007A\nC\u0004\u0002:b\u0001\r!a/\u0002\u000b\u0011\"\b.[:\u0011\u000b\u0005\u0005d\"a-\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0005\u0003\u0003\fI\r\u0006\u0003\u0002\b\u0006\r\u0007bBA]3\u0001\u0007\u0011Q\u0019\t\u0006\u0003Cr\u0011q\u0019\t\u0004\u0013\u0006%G!B&\u001a\u0005\u0004a\u0015\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\u0011\ty-a7\u0015\t\u0005E\u0017Q\u001b\u000b\u0005\u0003'\u000b\u0019\u000e\u0003\u0005\u0002\u001cj\t\t\u00111\u0001Q\u0011\u001d\tIL\u0007a\u0001\u0003/\u0004R!!\u0019\u000f\u00033\u00042!SAn\t\u0015Y%D1\u0001M\u0003I\u0019u.\u001c9mKRLwN\\*uC\u001e,w\n]:\u0016\t\u0005\u0005(1\u0001\u000b\u0005\u0003G\u0014)\u0001E\u0003\u0002bq\u0011\tA\u0001\nD_6\u0004H.\u001a;j_:\u001cF/Y4f\u001fB\u001cX\u0003BAu\u0003c\u001c2\u0001HA5+\t\ti\u000f\u0005\u0003@\r\u0006=\bcA%\u0002r\u0012)1\n\bb\u0001\u0019R!\u0011Q_A|!\u0015\t\t\u0007HAx\u0011\u001d\tyg\ba\u0001\u0003[,\"!a?\u0011\tYC\u0016q\u001e\u000b\u0005\u0003'\u000by\u0010\u0003\u0005\u0002\u001c\n\n\t\u00111\u0001Q!\rI%1\u0001\u0003\u0006\u0017n\u0011\r\u0001\u0014\u0005\u0007Cn\u0001\rAa\u0002\u0011\t}2%\u0011\u0001\t\u0004\u0003C\"3C\u0001\u00136)\t\u0011I!A\tu_N\u001b\u0017\r\\1%Kb$XM\\:j_:,BAa\u0005\u0003\u001aQ!!Q\u0003B\u000e!\u00111\u0006La\u0006\u0011\u0007%\u0013I\u0002B\u0003LM\t\u0007A\nC\u0004\u0002:\u001a\u0002\rA!\b\u0011\u000b\u0005\u0005DDa\u0006\u0016\t\t\u0005\"\u0011\u0006\u000b\u0005\u0003\u000f\u0013\u0019\u0003C\u0004\u0002:\u001e\u0002\rA!\n\u0011\u000b\u0005\u0005DDa\n\u0011\u0007%\u0013I\u0003B\u0003LO\t\u0007A*\u0006\u0003\u0003.\teB\u0003\u0002B\u0018\u0005g!B!a%\u00032!A\u00111\u0014\u0015\u0002\u0002\u0003\u0007\u0001\u000bC\u0004\u0002:\"\u0002\rA!\u000e\u0011\u000b\u0005\u0005DDa\u000e\u0011\u0007%\u0013I\u0004B\u0003LQ\t\u0007A\n")
public final class FutureConverters {
    public static CompletionStage CompletionStageOps(CompletionStage completionStage) {
        return FutureConverters$.MODULE$.CompletionStageOps(completionStage);
    }

    public static Future FutureOps(Future future) {
        return FutureConverters$.MODULE$.FutureOps(future);
    }

    public static <T> Promise<T> failedPromise(Throwable throwable) {
        return FutureConverters$.MODULE$.failedPromise(throwable);
    }

    public static <T> Promise<T> keptPromise(T t2) {
        return FutureConverters$.MODULE$.keptPromise(t2);
    }

    public static <T> Promise<T> promise() {
        return FutureConverters$.MODULE$.promise();
    }

    public static ExecutionContext globalExecutionContext() {
        return FutureConverters$.MODULE$.globalExecutionContext();
    }

    public static ExecutionContextExecutor fromExecutor(Executor executor2) {
        return FutureConverters$.MODULE$.fromExecutor(executor2);
    }

    public static ExecutionContextExecutor fromExecutor(Executor executor2, Consumer<Throwable> consumer) {
        return FutureConverters$.MODULE$.fromExecutor(executor2, consumer);
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService executorService2) {
        return FutureConverters$.MODULE$.fromExecutorService(executorService2);
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService executorService2, Consumer<Throwable> consumer) {
        return FutureConverters$.MODULE$.fromExecutorService(executorService2, consumer);
    }

    public static <T> Future<T> toScala(CompletionStage<T> completionStage) {
        return FutureConverters$.MODULE$.toScala(completionStage);
    }

    public static <T> CompletionStage<T> toJava(Future<T> future) {
        return FutureConverters$.MODULE$.toJava(future);
    }

    public static final class CompletionStageOps<T> {
        private final CompletionStage<T> __self;

        public CompletionStage<T> __self() {
            return this.__self;
        }

        public Future<T> toScala() {
            return FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(this.__self());
        }

        public int hashCode() {
            return FutureConverters$CompletionStageOps$.MODULE$.hashCode$extension(this.__self());
        }

        public boolean equals(Object x$1) {
            return FutureConverters$CompletionStageOps$.MODULE$.equals$extension(this.__self(), x$1);
        }

        public CompletionStageOps(CompletionStage<T> __self) {
            this.__self = __self;
        }
    }

    public static final class FutureOps<T> {
        private final Future<T> __self;

        public Future<T> __self() {
            return this.__self;
        }

        public CompletionStage<T> toJava() {
            return FutureConverters$FutureOps$.MODULE$.toJava$extension(this.__self());
        }

        public int hashCode() {
            return FutureConverters$FutureOps$.MODULE$.hashCode$extension(this.__self());
        }

        public boolean equals(Object x$1) {
            return FutureConverters$FutureOps$.MODULE$.equals$extension(this.__self(), x$1);
        }

        public FutureOps(Future<T> __self) {
            this.__self = __self;
        }
    }
}

