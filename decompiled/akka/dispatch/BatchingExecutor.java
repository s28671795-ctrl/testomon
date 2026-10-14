/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.annotation.InternalApi;
import akka.dispatch.internal.ScalaBatchable$;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import scala.Function0;
import scala.Predef$;
import scala.concurrent.BlockContext;
import scala.concurrent.BlockContext$;
import scala.concurrent.CanAwait;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rb\u0001C\n\u0015!\u0003\r\tA\u0006\r\t\u000b%\u0002A\u0011A\u0016\t\u000fI\u0002!\u0019)C\u0005g\u00191\u0011\b\u0001Q\u0002\niBQAQ\u0002\u0005\u0002\rCQ\u0001R\u0002\u0005\u0006\u0015CQaT\u0002\u0005\u0016A3a\u0001\u0016\u0001!\u0002\u001b)\u0006\"\u0002\"\b\t\u00031\u0006\"\u0002-\b\t\u000bZ\u0003bB-\u0001\u0005\u0004&IA\u0017\u0004\u0007C\u0002\u0001\u000bQ\u00022\t\u000b\t[A\u0011A2\t\u000ba[AQI\u0016\t\u000b\u0015\\A\u0011\t4\t\u000by\u0004a\u0011C@\t\u000f\u0005\u0015\u0001A\"\u0005\u0002\b!9\u0011\u0011\u0002\u0001\u0005B\u0005-\u0001bBA\t\u0001\u0011\u0005\u00111\u0003\u0002\u0011\u0005\u0006$8\r[5oO\u0016CXmY;u_JT!!\u0006\f\u0002\u0011\u0011L7\u000f]1uG\"T\u0011aF\u0001\u0005C.\\\u0017mE\u0002\u00013\u0005\u0002\"AG\u0010\u000e\u0003mQ!\u0001H\u000f\u0002\t1\fgn\u001a\u0006\u0002=\u0005!!.\u0019<b\u0013\t\u00013D\u0001\u0004PE*,7\r\u001e\t\u0003E\u001dj\u0011a\t\u0006\u0003I\u0015\n!bY8oGV\u0014(/\u001a8u\u0015\t1S$\u0001\u0003vi&d\u0017B\u0001\u0015$\u0005!)\u00050Z2vi>\u0014\u0018A\u0002\u0013j]&$He\u0001\u0001\u0015\u00031\u0002\"!\f\u0019\u000e\u00039R\u0011aL\u0001\u0006g\u000e\fG.Y\u0005\u0003c9\u0012A!\u00168ji\u0006Yq\f^1tWNdunY1m+\u0005!\u0004c\u0001\u000e6o%\u0011ag\u0007\u0002\f)\"\u0014X-\u00193M_\u000e\fG\u000e\u0005\u00029\u00075\t\u0001AA\u0007BEN$(/Y2u\u0005\u0006$8\r[\n\u0004\u0007mz\u0004c\u0001\u001f>\u007f5\tQ%\u0003\u0002?K\tQ\u0011I\u001d:bs\u0012+\u0017/^3\u0011\u0005i\u0001\u0015BA!\u001c\u0005!\u0011VO\u001c8bE2,\u0017A\u0002\u001fj]&$h\bF\u00018\u00031\u0001(o\\2fgN\u0014\u0015\r^2i)\tac\tC\u0003H\u000b\u0001\u0007q'A\u0003cCR\u001c\u0007\u000e\u000b\u0002\u0006\u0013B\u0011!*T\u0007\u0002\u0017*\u0011AJL\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001(L\u0005\u001d!\u0018-\u001b7sK\u000e\f\u0011C]3tk\nl\u0017\u000e^+oE\u0006$8\r[3e)\u0005\t\u0006CA\u0017S\u0013\t\u0019fFA\u0004C_>dW-\u00198\u0003\u000b\t\u000bGo\u00195\u0014\u0005\u001d9D#A,\u0011\u0005a:\u0011a\u0001:v]\u0006iqL\u00197pG.\u001cuN\u001c;fqR,\u0012a\u0017\t\u00045Ub\u0006CA/`\u001b\u0005q&B\u0001\u0013/\u0013\t\u0001gL\u0001\u0007CY>\u001c7nQ8oi\u0016DHO\u0001\bCY>\u001c7.\u00192mK\n\u000bGo\u00195\u0014\u0007-9D\fF\u0001e!\tA4\"A\u0004cY>\u001c7n\u00148\u0016\u0005\u001d\\GC\u00015z)\tIG\u000f\u0005\u0002kW2\u0001A!\u00027\u000f\u0005\u0004i'!\u0001+\u0012\u00059\f\bCA\u0017p\u0013\t\u0001hFA\u0004O_RD\u0017N\\4\u0011\u00055\u0012\u0018BA:/\u0005\r\te.\u001f\u0005\u0006k:\u0001\u001dA^\u0001\u000ba\u0016\u0014X.[:tS>t\u0007CA/x\u0013\tAhL\u0001\u0005DC:\fu/Y5u\u0011\u0019Qh\u0002\"a\u0001w\u0006)A\u000f[;oWB\u0019Q\u0006`5\n\u0005ut#\u0001\u0003\u001fcs:\fW.\u001a \u0002!Ut'-\u0019;dQ\u0016$W\t_3dkR,Gc\u0001\u0017\u0002\u0002!1\u00111A\bA\u0002}\n\u0011A]\u0001\u0010e\u0016\u001cXOY7ji>s'\t\\8dWV\t\u0011+A\u0004fq\u0016\u001cW\u000f^3\u0015\u00071\ni\u0001\u0003\u0004\u0002\u0010E\u0001\raP\u0001\teVtg.\u00192mK\u0006I!-\u0019;dQ\u0006\u0014G.\u001a\u000b\u0004#\u0006U\u0001BBA\b%\u0001\u0007q\bK\u0002\u0001\u00033\u0001B!a\u0007\u0002 5\u0011\u0011Q\u0004\u0006\u0003\u0019ZIA!!\t\u0002\u001e\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public interface BatchingExecutor
extends Executor {
    public void akka$dispatch$BatchingExecutor$_setter_$akka$dispatch$BatchingExecutor$$_tasksLocal_$eq(ThreadLocal<AbstractBatch> var1);

    public void akka$dispatch$BatchingExecutor$_setter_$akka$dispatch$BatchingExecutor$$_blockContext_$eq(ThreadLocal<BlockContext> var1);

    public ThreadLocal<AbstractBatch> akka$dispatch$BatchingExecutor$$_tasksLocal();

    public ThreadLocal<BlockContext> akka$dispatch$BatchingExecutor$$_blockContext();

    public void unbatchedExecute(Runnable var1);

    public boolean resubmitOnBlock();

    public static /* synthetic */ void execute$(BatchingExecutor $this, Runnable runnable2) {
        $this.execute(runnable2);
    }

    @Override
    default public void execute(Runnable runnable2) {
        if (this.batchable(runnable2)) {
            AbstractBatch abstractBatch = this.akka$dispatch$BatchingExecutor$$_tasksLocal().get();
            if (abstractBatch == null) {
                AbstractBatch newBatch = this.resubmitOnBlock() ? new BlockableBatch(this) : new Batch(this);
                newBatch.add(runnable2);
                this.unbatchedExecute(newBatch);
                return;
            }
            abstractBatch.add(runnable2);
            return;
        }
        this.unbatchedExecute(runnable2);
    }

    public static /* synthetic */ boolean batchable$(BatchingExecutor $this, Runnable runnable2) {
        return $this.batchable(runnable2);
    }

    default public boolean batchable(Runnable runnable2) {
        return ScalaBatchable$.MODULE$.isBatchable(runnable2);
    }

    public static void $init$(BatchingExecutor $this) {
        $this.akka$dispatch$BatchingExecutor$_setter_$akka$dispatch$BatchingExecutor$$_tasksLocal_$eq(new ThreadLocal<AbstractBatch>());
        $this.akka$dispatch$BatchingExecutor$_setter_$akka$dispatch$BatchingExecutor$$_blockContext_$eq(new ThreadLocal<BlockContext>());
    }

    private abstract class AbstractBatch
    extends ArrayDeque<Runnable>
    implements Runnable {
        public final void processBatch(AbstractBatch batch) {
            while (batch == this && !batch.isEmpty()) {
                ((Runnable)batch.poll()).run();
                batch = this.akka$dispatch$BatchingExecutor$AbstractBatch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().get();
            }
        }

        public final boolean resubmitUnbatched() {
            AbstractBatch current = this.akka$dispatch$BatchingExecutor$AbstractBatch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().get();
            this.akka$dispatch$BatchingExecutor$AbstractBatch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().remove();
            if (current == this && !current.isEmpty()) {
                this.akka$dispatch$BatchingExecutor$AbstractBatch$$$outer().unbatchedExecute(current);
                return true;
            }
            return false;
        }

        public /* synthetic */ BatchingExecutor akka$dispatch$BatchingExecutor$AbstractBatch$$$outer() {
            return BatchingExecutor.this;
        }

        public AbstractBatch() {
            if (BatchingExecutor.this == null) {
                throw null;
            }
            super(4);
        }
    }

    private final class Batch
    extends AbstractBatch {
        @Override
        public final void run() {
            Predef$.MODULE$.require(this.akka$dispatch$BatchingExecutor$Batch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().get() == null);
            this.akka$dispatch$BatchingExecutor$Batch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().set(this);
            try {
                try {
                    this.processBatch(this);
                }
                catch (Throwable t2) {
                    this.resubmitUnbatched();
                    throw t2;
                }
            }
            finally {
                this.akka$dispatch$BatchingExecutor$Batch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().remove();
            }
        }

        public /* synthetic */ BatchingExecutor akka$dispatch$BatchingExecutor$Batch$$$outer() {
            return this.$outer;
        }

        public Batch(BatchingExecutor $outer) {
        }
    }

    private final class BlockableBatch
    extends AbstractBatch
    implements BlockContext {
        @Override
        public final void run() {
            boolean firstInvocation;
            Predef$.MODULE$.require(this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().get() == null);
            this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().set(this);
            boolean bl = firstInvocation = this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_blockContext().get() == null;
            if (firstInvocation) {
                this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_blockContext().set(BlockContext$.MODULE$.current());
            }
            BlockContext$.MODULE$.withBlockContext(this, () -> {
                try {
                    try {
                        this.processBatch(this);
                    }
                    catch (Throwable t2) {
                        this.resubmitUnbatched();
                        throw t2;
                    }
                }
                finally {
                    this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_tasksLocal().remove();
                    if (firstInvocation) {
                        this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_blockContext().remove();
                    }
                }
            });
        }

        @Override
        public <T> T blockOn(Function0<T> thunk, CanAwait permission) {
            this.resubmitUnbatched();
            return this.akka$dispatch$BatchingExecutor$BlockableBatch$$$outer().akka$dispatch$BatchingExecutor$$_blockContext().get().blockOn(thunk, permission);
        }

        public /* synthetic */ BatchingExecutor akka$dispatch$BatchingExecutor$BlockableBatch$$$outer() {
            return this.$outer;
        }

        public BlockableBatch(BatchingExecutor $outer) {
        }
    }
}

