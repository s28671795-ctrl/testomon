/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import scala.Function0;
import scala.Function1;
import scala.concurrent.BlockContext;
import scala.concurrent.CanAwait;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.impl.ExecutionContextImpl$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015g!B\u0010!\u0001\u00112\u0003\u0002C\u0018\u0001\u0005\u000b\u0007IQA\u0019\t\u0011m\u0002!\u0011!Q\u0001\u000eIB\u0001\u0002\u0010\u0001\u0003\u0006\u0004%)!\u0010\u0005\t!\u0002\u0011\t\u0011)A\u0007}!1\u0011\u000b\u0001C\u0001AICQa\u0016\u0001\u0005FaCQ!\u0019\u0001\u0005F\t<a!\u001a\u0011\t\u0002\t2gAB\u0010!\u0011\u0003\u0011s\rC\u0003R\u0013\u0011\u0005\u0001N\u0002\u0003j\u0013\tQ\u0007\u0002\u0003=\f\u0005\u000b\u0007IQA=\t\u0011u\\!\u0011!Q\u0001\u000eiD\u0001B`\u0006\u0003\u0006\u0004%)a \u0005\u000b\u0003\u000fY!\u0011!Q\u0001\u000e\u0005\u0005\u0001BCA\u0005\u0017\t\u0015\r\u0011\"\u0002\u0002\f!Q\u0011QD\u0006\u0003\u0002\u0003\u0006i!!\u0004\t\u0015\u0005}1B!b\u0001\n\u000b\t\t\u0003\u0003\u0006\u00022-\u0011\t\u0011)A\u0007\u0003GAa!U\u0006\u0005\u0002\u0005M\u0002\"CA!\u0017\t\u0007IQBA\"\u0011!\tYe\u0003Q\u0001\u000e\u0005\u0015\u0003bBA'\u0017\u0011\u0005\u0011q\n\u0005\b\u0003[ZA\u0011AA8\u0011\u001d\tig\u0003C\u0001\u0003gBq!!\"\n\t\u0003\t9\tC\u0004\u0002\u0012&!\t!a%\t\u0013\u0005m\u0015\"%A\u0005\u0002\u0005u\u0005bBAZ\u0013\u0011\u0005\u0011Q\u0017\u0005\n\u0003\u0007L\u0011\u0013!C\u0001\u0003;\u0013A#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;J[Bd'BA\u0011#\u0003\u0011IW\u000e\u001d7\u000b\u0005\r\"\u0013AC2p]\u000e,(O]3oi*\tQ%A\u0003tG\u0006d\u0017mE\u0002\u0001O-\u0002\"\u0001K\u0015\u000e\u0003\u0011J!A\u000b\u0013\u0003\r\u0005s\u0017PU3g!\taS&D\u0001#\u0013\tq#E\u0001\rFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\f\u0001\"\u001a=fGV$xN]\u0002\u0001+\u0005\u0011\u0004CA\u001a:\u001b\u0005!$BA\u00126\u0015\t1t'\u0001\u0003vi&d'\"\u0001\u001d\u0002\t)\fg/Y\u0005\u0003uQ\u0012\u0001\"\u0012=fGV$xN]\u0001\nKb,7-\u001e;pe\u0002\n\u0001B]3q_J$XM]\u000b\u0002}A!\u0001fP!N\u0013\t\u0001EEA\u0005Gk:\u001cG/[8ocA\u0011!I\u0013\b\u0003\u0007\"s!\u0001R$\u000e\u0003\u0015S!A\u0012\u0019\u0002\rq\u0012xn\u001c;?\u0013\u0005)\u0013BA%%\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0013'\u0003\u0013QC'o\\<bE2,'BA%%!\tAc*\u0003\u0002PI\t!QK\\5u\u0003%\u0011X\r]8si\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0004'V3\u0006C\u0001+\u0001\u001b\u0005\u0001\u0003\"B\u0018\u0006\u0001\u0004\u0011\u0004\"\u0002\u001f\u0006\u0001\u0004q\u0014aB3yK\u000e,H/\u001a\u000b\u0003\u001bfCQA\u0017\u0004A\u0002m\u000b\u0001B];o]\u0006\u0014G.\u001a\t\u00039~k\u0011!\u0018\u0006\u0003=^\nA\u0001\\1oO&\u0011\u0001-\u0018\u0002\t%Vtg.\u00192mK\u0006i!/\u001a9peR4\u0015-\u001b7ve\u0016$\"!T2\t\u000b\u0011<\u0001\u0019A!\u0002\u0003Q\fA#\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;J[Bd\u0007C\u0001+\n'\tIq\u0005F\u0001g\u0005Q!UMZ1vYR$\u0006N]3bI\u001a\u000b7\r^8ssN!1b\u001b8r!\taF.\u0003\u0002n;\n1qJ\u00196fGR\u0004\"aM8\n\u0005A$$!\u0004+ie\u0016\fGMR1di>\u0014\u0018\u0010\u0005\u0002sk:\u00111g]\u0005\u0003iR\nABR8sW*{\u0017N\u001c)p_2L!A^<\u00037\u0019{'o\u001b&pS:<vN]6feRC'/Z1e\r\u0006\u001cGo\u001c:z\u0015\t!H'\u0001\u0005eC\u0016lwN\\5d+\u0005Q\bC\u0001\u0015|\u0013\taHEA\u0004C_>dW-\u00198\u0002\u0013\u0011\fW-\\8oS\u000e\u0004\u0013aC7bq\ncwnY6feN,\"!!\u0001\u0011\u0007!\n\u0019!C\u0002\u0002\u0006\u0011\u00121!\u00138u\u00031i\u0017\r\u001f\"m_\u000e\\WM]:!\u0003\u0019\u0001(/\u001a4jqV\u0011\u0011Q\u0002\t\u0005\u0003\u001f\t9B\u0004\u0003\u0002\u0012\u0005M\u0001C\u0001#%\u0013\r\t)\u0002J\u0001\u0007!J,G-\u001a4\n\t\u0005e\u00111\u0004\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005UA%A\u0004qe\u00164\u0017\u000e\u001f\u0011\u0002\u0011Ut7-Y;hQR,\"!a\t\u0011\t\u0005\u0015\u00121\u0006\b\u00049\u0006\u001d\u0012bAA\u0015;\u00061A\u000b\u001b:fC\u0012LA!!\f\u00020\tARK\\2bk\u001eDG/\u0012=dKB$\u0018n\u001c8IC:$G.\u001a:\u000b\u0007\u0005%R,A\u0005v]\u000e\fWo\u001a5uAQQ\u0011QGA\u001d\u0003w\ti$a\u0010\u0011\u0007\u0005]2\"D\u0001\n\u0011\u0015AH\u00031\u0001{\u0011\u0019qH\u00031\u0001\u0002\u0002!9\u0011\u0011\u0002\u000bA\u0002\u00055\u0001bBA\u0010)\u0001\u0007\u00111E\u0001\u000fE2|7m[3s!\u0016\u0014X.\u001b;t+\t\t)\u0005E\u00024\u0003\u000fJ1!!\u00135\u0005%\u0019V-\\1qQ>\u0014X-A\bcY>\u001c7.\u001a:QKJl\u0017\u000e^:!\u0003\u00119\u0018N]3\u0016\t\u0005E\u0013q\u000b\u000b\u0005\u0003'\nI\u0007\u0005\u0003\u0002V\u0005]C\u0002\u0001\u0003\b\u00033:\"\u0019AA.\u0005\u0005!\u0016\u0003BA/\u0003G\u00022\u0001KA0\u0013\r\t\t\u0007\n\u0002\b\u001d>$\b.\u001b8h!\ra\u0016QM\u0005\u0004\u0003Oj&A\u0002+ie\u0016\fG\rC\u0004\u0002l]\u0001\r!a\u0015\u0002\rQD'/Z1e\u0003%qWm\u001e+ie\u0016\fG\r\u0006\u0003\u0002d\u0005E\u0004\"\u0002.\u0019\u0001\u0004YF\u0003BA;\u0003w\u00022aMA<\u0013\r\tI\b\u000e\u0002\u0015\r>\u00148NS8j]^{'o[3s)\"\u0014X-\u00193\t\u000f\u0005u\u0014\u00041\u0001\u0002\u0000\u0005\u0019aM\u001b9\u0011\u0007M\n\t)C\u0002\u0002\u0004R\u0012ABR8sW*{\u0017N\u001c)p_2\fAd\u0019:fCR,G)\u001a4bk2$X\t_3dkR|'oU3sm&\u001cW\r\u0006\u0003\u0002\n\u0006=\u0005c\u0001\u0017\u0002\f&\u0019\u0011Q\u0012\u0012\u0003?\u0015CXmY;uS>t7i\u001c8uKb$X\t_3dkR|'oU3sm&\u001cW\rC\u0003=5\u0001\u0007a(\u0001\u0007ge>lW\t_3dkR|'\u000fF\u0003,\u0003+\u000bI\n\u0003\u0004\u0002\u0018n\u0001\rAM\u0001\u0002K\"9Ah\u0007I\u0001\u0002\u0004q\u0014A\u00064s_6,\u00050Z2vi>\u0014H\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005}%f\u0001 \u0002\".\u0012\u00111\u0015\t\u0005\u0003K\u000by+\u0004\u0002\u0002(*!\u0011\u0011VAV\u0003%)hn\u00195fG.,GMC\u0002\u0002.\u0012\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\t,a*\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\nge>lW\t_3dkR|'oU3sm&\u001cW\r\u0006\u0004\u0002\n\u0006]\u0016\u0011\u0019\u0005\b\u0003sk\u0002\u0019AA^\u0003\t)7\u000fE\u00024\u0003{K1!a05\u0005=)\u00050Z2vi>\u00148+\u001a:wS\u000e,\u0007b\u0002\u001f\u001e!\u0003\u0005\rAP\u0001\u001eMJ|W.\u0012=fGV$xN]*feZL7-\u001a\u0013eK\u001a\fW\u000f\u001c;%e\u0001")
public class ExecutionContextImpl
implements ExecutionContextExecutor {
    private final Executor executor;
    private final Function1<Throwable, BoxedUnit> reporter;

    public static Function1<Throwable, BoxedUnit> fromExecutorService$default$2() {
        return ExecutionContext$.MODULE$.defaultReporter();
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService es, Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.fromExecutorService(es, reporter);
    }

    public static Function1<Throwable, BoxedUnit> fromExecutor$default$2() {
        return ExecutionContext$.MODULE$.defaultReporter();
    }

    public static ExecutionContextExecutor fromExecutor(Executor e, Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.fromExecutor(e, reporter);
    }

    public static ExecutionContextExecutorService createDefaultExecutorService(Function1<Throwable, BoxedUnit> reporter) {
        return ExecutionContextImpl$.MODULE$.createDefaultExecutorService(reporter);
    }

    @Override
    public ExecutionContext prepare() {
        return ExecutionContext.prepare$(this);
    }

    public final Executor executor() {
        return this.executor;
    }

    public final Function1<Throwable, BoxedUnit> reporter() {
        return this.reporter;
    }

    @Override
    public final void execute(Runnable runnable2) {
        this.executor().execute(runnable2);
    }

    @Override
    public final void reportFailure(Throwable t2) {
        this.reporter().apply(t2);
    }

    public static final /* synthetic */ String $anonfun$new$1() {
        return "Executor must not be null";
    }

    public ExecutionContextImpl(Executor executor2, Function1<Throwable, BoxedUnit> reporter) {
        this.executor = executor2;
        this.reporter = reporter;
        if (!(executor2 != null)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Executor must not be null").toString());
        }
    }

    public static final class DefaultThreadFactory
    implements ThreadFactory,
    ForkJoinPool.ForkJoinWorkerThreadFactory {
        private final boolean daemonic;
        private final int maxBlockers;
        private final String prefix;
        private final Thread.UncaughtExceptionHandler uncaught;
        private final Semaphore scala$concurrent$impl$ExecutionContextImpl$DefaultThreadFactory$$blockerPermits;

        public final boolean daemonic() {
            return this.daemonic;
        }

        public final int maxBlockers() {
            return this.maxBlockers;
        }

        public final String prefix() {
            return this.prefix;
        }

        public final Thread.UncaughtExceptionHandler uncaught() {
            return this.uncaught;
        }

        public final Semaphore scala$concurrent$impl$ExecutionContextImpl$DefaultThreadFactory$$blockerPermits() {
            return this.scala$concurrent$impl$ExecutionContextImpl$DefaultThreadFactory$$blockerPermits;
        }

        public <T extends Thread> T wire(T thread2) {
            thread2.setDaemon(this.daemonic());
            thread2.setUncaughtExceptionHandler(this.uncaught());
            thread2.setName(new StringBuilder(1).append(this.prefix()).append("-").append(thread2.getId()).toString());
            return thread2;
        }

        @Override
        public Thread newThread(Runnable runnable2) {
            return this.wire(new Thread(runnable2));
        }

        @Override
        public ForkJoinWorkerThread newThread(ForkJoinPool fjp) {
            return this.wire(new BlockContext(this, fjp){
                private boolean isBlocked;
                private final /* synthetic */ DefaultThreadFactory $outer;

                public final <T> T blockOn(Function0<T> thunk, CanAwait permission) {
                    if (Thread.currentThread() == this && !this.isBlocked && this.$outer.scala$concurrent$impl$ExecutionContextImpl$DefaultThreadFactory$$blockerPermits().tryAcquire()) {
                        R r;
                        try {
                            ForkJoinPool.ManagedBlocker b = new ForkJoinPool.ManagedBlocker(null, thunk){
                                private T result;
                                private boolean done;
                                private final Function0 thunk$1;

                                public boolean apply$mcZ$sp() {
                                    return Function0.apply$mcZ$sp$(this);
                                }

                                public byte apply$mcB$sp() {
                                    return Function0.apply$mcB$sp$(this);
                                }

                                public char apply$mcC$sp() {
                                    return Function0.apply$mcC$sp$(this);
                                }

                                public double apply$mcD$sp() {
                                    return Function0.apply$mcD$sp$(this);
                                }

                                public float apply$mcF$sp() {
                                    return Function0.apply$mcF$sp$(this);
                                }

                                public int apply$mcI$sp() {
                                    return Function0.apply$mcI$sp$(this);
                                }

                                public long apply$mcJ$sp() {
                                    return Function0.apply$mcJ$sp$(this);
                                }

                                public short apply$mcS$sp() {
                                    return Function0.apply$mcS$sp$(this);
                                }

                                public void apply$mcV$sp() {
                                    Function0.apply$mcV$sp$(this);
                                }

                                public String toString() {
                                    return Function0.toString$(this);
                                }

                                public final boolean block() {
                                    if (!this.done) {
                                        this.result = this.thunk$1.apply();
                                        this.done = true;
                                    }
                                    return this.isReleasable();
                                }

                                public final boolean isReleasable() {
                                    return this.done;
                                }

                                public final T apply() {
                                    return this.result;
                                }
                                {
                                    this.thunk$1 = thunk$1;
                                    this.result = null;
                                    this.done = false;
                                }
                            };
                            this.isBlocked = true;
                            ForkJoinPool.managedBlock(b);
                            r = b.apply();
                        }
                        finally {
                            this.isBlocked = false;
                            this.$outer.scala$concurrent$impl$ExecutionContextImpl$DefaultThreadFactory$$blockerPermits().release();
                        }
                        return (T)r;
                    }
                    return thunk.apply();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super(fjp$1);
                    this.isBlocked = false;
                }
            });
        }

        public static final /* synthetic */ String $anonfun$new$2() {
            return "DefaultThreadFactory.prefix must be non null";
        }

        public static final /* synthetic */ String $anonfun$new$3() {
            return "DefaultThreadFactory.maxBlockers must be greater-or-equal-to 0";
        }

        public DefaultThreadFactory(boolean daemonic, int maxBlockers, String prefix, Thread.UncaughtExceptionHandler uncaught) {
            this.daemonic = daemonic;
            this.maxBlockers = maxBlockers;
            this.prefix = prefix;
            this.uncaught = uncaught;
            if (!(prefix != null)) {
                throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"DefaultThreadFactory.prefix must be non null").toString());
            }
            if (!(maxBlockers >= 0)) {
                throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"DefaultThreadFactory.maxBlockers must be greater-or-equal-to 0").toString());
            }
            this.scala$concurrent$impl$ExecutionContextImpl$DefaultThreadFactory$$blockerPermits = new Semaphore(maxBlockers);
        }
    }
}

