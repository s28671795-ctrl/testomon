/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.stream.scaladsl.SourceQueue;
import akka.stream.scaladsl.SourceQueueWithComplete$;
import akka.stream.scaladsl.SourceQueueWithComplete$QueueOps$;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005da\u0002\f\u0018!\u0003\r\nA\b\u0005\u0006k\u00011\tA\u000e\u0005\u0006u\u00011\ta\u000f\u0005\u0006\u0015\u00021\taS\u0004\u0006-^A\ta\u0016\u0004\u0006-]A\t\u0001\u0017\u0005\u00063\u0016!\tA\u0017\u0004\u00057\u0016\u0019A\f\u0003\u0005b\u000f\t\u0015\r\u0011\"\u0001c\u0011!1wA!A!\u0002\u0013\u0019\u0007\"B-\b\t\u00039\u0007\"B6\b\t\u0003a\u0007b\u0002:\b\u0003\u0003%\te\u001d\u0005\bo\u001e\t\t\u0011\"\u0011y\u000f\u001dqX!!A\t\u0002}4\u0001bW\u0003\u0002\u0002#\u0005\u0011\u0011\u0001\u0005\u00073>!\t!a\u0001\t\u000f\u0005\u0015q\u0002\"\u0002\u0002\b!I\u0011qC\b\u0002\u0002\u0013\u0015\u0011\u0011\u0004\u0005\n\u0003Ky\u0011\u0011!C\u0003\u0003OA\u0001B`\u0003\u0002\u0002\u0013\u001d\u0011q\u0007\u0005\bW\u0016!\taGA#\u0005]\u0019v.\u001e:dKF+X-^3XSRD7i\\7qY\u0016$XM\u0003\u0002\u00193\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u001b7\u000511\u000f\u001e:fC6T\u0011\u0001H\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0005}a3c\u0001\u0001!MA\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t1\u0011I\\=SK\u001a\u00042a\n\u0015+\u001b\u00059\u0012BA\u0015\u0018\u0005-\u0019v.\u001e:dKF+X-^3\u0011\u0005-bC\u0002\u0001\u0003\u0006[\u0001\u0011\rA\f\u0002\u0002)F\u0011qF\r\t\u0003CAJ!!\r\u0012\u0003\u000f9{G\u000f[5oOB\u0011\u0011eM\u0005\u0003i\t\u00121!\u00118z\u0003!\u0019w.\u001c9mKR,G#A\u001c\u0011\u0005\u0005B\u0014BA\u001d#\u0005\u0011)f.\u001b;\u0002\t\u0019\f\u0017\u000e\u001c\u000b\u0003oqBQ!\u0010\u0002A\u0002y\n!!\u001a=\u0011\u0005}:eB\u0001!F\u001d\t\tE)D\u0001C\u0015\t\u0019U$\u0001\u0004=e>|GOP\u0005\u0002G%\u0011aII\u0001\ba\u0006\u001c7.Y4f\u0013\tA\u0015JA\u0005UQJ|w/\u00192mK*\u0011aII\u0001\u0010o\u0006$8\r[\"p[BdW\r^5p]R\tA\nE\u0002N!Jk\u0011A\u0014\u0006\u0003\u001f\n\n!bY8oGV\u0014(/\u001a8u\u0013\t\tfJ\u0001\u0004GkR,(/\u001a\t\u0003'Rk\u0011aG\u0005\u0003+n\u0011A\u0001R8oK\u000692k\\;sG\u0016\fV/Z;f/&$\bnQ8na2,G/\u001a\t\u0003O\u0015\u0019\"!\u0002\u0011\u0002\rqJg.\u001b;?)\u00059&\u0001C)vKV,w\n]:\u0016\u0005u+7CA\u0004_!\t\ts,\u0003\u0002aE\t1\u0011I\\=WC2\fQ!];fk\u0016,\u0012a\u0019\t\u0004O\u0001!\u0007CA\u0016f\t\u0015isA1\u0001/\u0003\u0019\tX/Z;fAQ\u0011\u0001N\u001b\t\u0004S\u001e!W\"A\u0003\t\u000b\u0005T\u0001\u0019A2\u0002\r\u0005\u001c(*\u0019<b+\u0005i\u0007c\u00018rI6\tqN\u0003\u0002q3\u00059!.\u0019<bINd\u0017B\u0001\fp\u0003!A\u0017m\u001d5D_\u0012,G#\u0001;\u0011\u0005\u0005*\u0018B\u0001<#\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u0005ed\bCA\u0011{\u0013\tY(EA\u0004C_>dW-\u00198\t\u000ful\u0011\u0011!a\u0001e\u0005\u0019\u0001\u0010J\u0019\u0002\u0011E+X-^3PaN\u0004\"![\b\u0014\u0005=\u0001C#A@\u0002!\u0005\u001c(*\u0019<bI\u0015DH/\u001a8tS>tW\u0003BA\u0005\u0003\u001f!B!a\u0003\u0002\u0012A!a.]A\u0007!\rY\u0013q\u0002\u0003\u0006[E\u0011\rA\f\u0005\b\u0003'\t\u0002\u0019AA\u000b\u0003\u0015!C\u000f[5t!\u0011Iw!!\u0004\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0005\u00037\t\u0019\u0003F\u0002t\u0003;Aq!a\u0005\u0013\u0001\u0004\ty\u0002\u0005\u0003j\u000f\u0005\u0005\u0002cA\u0016\u0002$\u0011)QF\u0005b\u0001]\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0005\u0003S\t)\u0004\u0006\u0003\u0002,\u0005=BcA=\u0002.!9QpEA\u0001\u0002\u0004\u0011\u0004bBA\n'\u0001\u0007\u0011\u0011\u0007\t\u0005S\u001e\t\u0019\u0004E\u0002,\u0003k!Q!L\nC\u00029*B!!\u000f\u0002@Q!\u00111HA!!\u0011Iw!!\u0010\u0011\u0007-\ny\u0004B\u0003.)\t\u0007a\u0006\u0003\u0004b)\u0001\u0007\u00111\t\t\u0005O\u0001\ti$\u0006\u0003\u0002H\u00055C\u0003BA%\u0003\u001f\u0002BA\\9\u0002LA\u00191&!\u0014\u0005\u000b5*\"\u0019\u0001\u0018\t\r\u0005,\u0002\u0019AA)!\u00119\u0003!a\u0013)\u0007U\t)\u0006\u0005\u0003\u0002X\u0005uSBAA-\u0015\r\tYfG\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA0\u00033\u00121\"\u00138uKJt\u0017\r\\!qS\u0002")
public interface SourceQueueWithComplete<T>
extends SourceQueue<T> {
    public static SourceQueueWithComplete QueueOps(SourceQueueWithComplete queue) {
        return SourceQueueWithComplete$.MODULE$.QueueOps(queue);
    }

    public void complete();

    public void fail(Throwable var1);

    @Override
    public Future<Done> watchCompletion();

    public static final class QueueOps<T> {
        private final SourceQueueWithComplete<T> queue;

        public SourceQueueWithComplete<T> queue() {
            return this.queue;
        }

        public akka.stream.javadsl.SourceQueueWithComplete<T> asJava() {
            return SourceQueueWithComplete$QueueOps$.MODULE$.asJava$extension(this.queue());
        }

        public int hashCode() {
            return SourceQueueWithComplete$QueueOps$.MODULE$.hashCode$extension(this.queue());
        }

        public boolean equals(Object x$1) {
            return SourceQueueWithComplete$QueueOps$.MODULE$.equals$extension(this.queue(), x$1);
        }

        public QueueOps(SourceQueueWithComplete<T> queue) {
            this.queue = queue;
        }
    }
}

