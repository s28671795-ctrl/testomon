/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.SinkQueue;
import akka.stream.scaladsl.SinkQueueWithCancel$;
import akka.stream.scaladsl.SinkQueueWithCancel$QueueOps$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015ba\u0002\u000b\u0016!\u0003\r\n\u0001\b\u0005\u0006g\u00011\t\u0001N\u0004\u0006qUA\t!\u000f\u0004\u0006)UA\tA\u000f\u0005\u0006w\r!\t\u0001\u0010\u0004\u0005{\r\u0019a\b\u0003\u0005D\u000b\t\u0015\r\u0011\"\u0001E\u0011!AUA!A!\u0002\u0013)\u0005\"B\u001e\u0006\t\u0003I\u0005\"B'\u0006\t\u0003q\u0005b\u0002+\u0006\u0003\u0003%\t%\u0016\u0005\b3\u0016\t\t\u0011\"\u0011[\u000f\u001d\u00017!!A\t\u0002\u00054q!P\u0002\u0002\u0002#\u0005!\rC\u0003<\u001b\u0011\u00051\rC\u0003e\u001b\u0011\u0015Q\rC\u0004n\u001b\u0005\u0005IQ\u00018\t\u000fQl\u0011\u0011!C\u0003k\"9\u0001mAA\u0001\n\u000fi\bbB'\u0004\t\u0003I\u0012\u0011\u0002\u0002\u0014'&t7.U;fk\u0016<\u0016\u000e\u001e5DC:\u001cW\r\u001c\u0006\u0003-]\t\u0001b]2bY\u0006$7\u000f\u001c\u0006\u00031e\taa\u001d;sK\u0006l'\"\u0001\u000e\u0002\t\u0005\\7.Y\u0002\u0001+\ti\"fE\u0002\u0001=\u0011\u0002\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012a!\u00118z%\u00164\u0007cA\u0013'Q5\tQ#\u0003\u0002(+\tI1+\u001b8l#V,W/\u001a\t\u0003S)b\u0001\u0001B\u0003,\u0001\t\u0007AFA\u0001U#\ti\u0003\u0007\u0005\u0002 ]%\u0011q\u0006\t\u0002\b\u001d>$\b.\u001b8h!\ty\u0012'\u0003\u00023A\t\u0019\u0011I\\=\u0002\r\r\fgnY3m)\u0005)\u0004CA\u00107\u0013\t9\u0004E\u0001\u0003V]&$\u0018aE*j].\fV/Z;f/&$\bnQ1oG\u0016d\u0007CA\u0013\u0004'\t\u0019a$\u0001\u0004=S:LGO\u0010\u000b\u0002s\tA\u0011+^3vK>\u00038/\u0006\u0002@\u000fN\u0011Q\u0001\u0011\t\u0003?\u0005K!A\u0011\u0011\u0003\r\u0005s\u0017PV1m\u0003\u0015\tX/Z;f+\u0005)\u0005cA\u0013\u0001\rB\u0011\u0011f\u0012\u0003\u0006W\u0015\u0011\r\u0001L\u0001\u0007cV,W/\u001a\u0011\u0015\u0005)c\u0005cA&\u0006\r6\t1\u0001C\u0003D\u0011\u0001\u0007Q)\u0001\u0004bg*\u000bg/Y\u000b\u0002\u001fB\u0019\u0001k\u0015$\u000e\u0003ES!AU\f\u0002\u000f)\fg/\u00193tY&\u0011A#U\u0001\tQ\u0006\u001c\bnQ8eKR\ta\u000b\u0005\u0002 /&\u0011\u0001\f\t\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002\\=B\u0011q\u0004X\u0005\u0003;\u0002\u0012qAQ8pY\u0016\fg\u000eC\u0004`\u0017\u0005\u0005\t\u0019\u0001\u0019\u0002\u0007a$\u0013'\u0001\u0005Rk\u0016,Xm\u00149t!\tYUb\u0005\u0002\u000e=Q\t\u0011-\u0001\tbg*\u000bg/\u0019\u0013fqR,gn]5p]V\u0011a-\u001b\u000b\u0003O*\u00042\u0001U*i!\tI\u0013\u000eB\u0003,\u001f\t\u0007A\u0006C\u0003l\u001f\u0001\u0007A.A\u0003%i\"L7\u000fE\u0002L\u000b!\f!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V\u0011qn\u001d\u000b\u0003+BDQa\u001b\tA\u0002E\u00042aS\u0003s!\tI3\u000fB\u0003,!\t\u0007A&\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]V\u0011a\u000f \u000b\u0003of$\"a\u0017=\t\u000f}\u000b\u0012\u0011!a\u0001a!)1.\u0005a\u0001uB\u00191*B>\u0011\u0005%bH!B\u0016\u0012\u0005\u0004aSc\u0001@\u0002\u0004Q\u0019q0!\u0002\u0011\t-+\u0011\u0011\u0001\t\u0004S\u0005\rA!B\u0016\u0013\u0005\u0004a\u0003BB\"\u0013\u0001\u0004\t9\u0001\u0005\u0003&\u0001\u0005\u0005Q\u0003BA\u0006\u0003#!B!!\u0004\u0002\u0014A!\u0001kUA\b!\rI\u0013\u0011\u0003\u0003\u0006WM\u0011\r\u0001\f\u0005\u0007\u0007N\u0001\r!!\u0006\u0011\t\u0015\u0002\u0011q\u0002\u0015\u0004'\u0005e\u0001\u0003BA\u000e\u0003Ci!!!\b\u000b\u0007\u0005}\u0011$\u0001\u0006b]:|G/\u0019;j_:LA!a\t\u0002\u001e\tY\u0011J\u001c;fe:\fG.\u00119j\u0001")
public interface SinkQueueWithCancel<T>
extends SinkQueue<T> {
    public static SinkQueueWithCancel QueueOps(SinkQueueWithCancel queue) {
        return SinkQueueWithCancel$.MODULE$.QueueOps(queue);
    }

    public void cancel();

    public static final class QueueOps<T> {
        private final SinkQueueWithCancel<T> queue;

        public SinkQueueWithCancel<T> queue() {
            return this.queue;
        }

        public akka.stream.javadsl.SinkQueueWithCancel<T> asJava() {
            return SinkQueueWithCancel$QueueOps$.MODULE$.asJava$extension(this.queue());
        }

        public int hashCode() {
            return SinkQueueWithCancel$QueueOps$.MODULE$.hashCode$extension(this.queue());
        }

        public boolean equals(Object x$1) {
            return SinkQueueWithCancel$QueueOps$.MODULE$.equals$extension(this.queue(), x$1);
        }

        public QueueOps(SinkQueueWithCancel<T> queue) {
            this.queue = queue;
        }
    }
}

