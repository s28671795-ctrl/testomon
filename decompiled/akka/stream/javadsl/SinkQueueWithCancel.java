/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.javadsl.SinkQueue;
import akka.stream.javadsl.SinkQueueWithCancel$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2qAB\u0004\u0011\u0002G\u0005a\u0002C\u0003&\u0001\u0019\u0005aeB\u0003+\u000f!\u00051FB\u0003\u0007\u000f!\u0005A\u0006C\u0003.\u0007\u0011\u0005a\u0006C\u00030\u0007\u0011\u0005\u0001GA\nTS:\\\u0017+^3vK^KG\u000f[\"b]\u000e,GN\u0003\u0002\t\u0013\u00059!.\u0019<bINd'B\u0001\u0006\f\u0003\u0019\u0019HO]3b[*\tA\"\u0001\u0003bW.\f7\u0001A\u000b\u0003\u001fq\u00192\u0001\u0001\t\u0017!\t\tB#D\u0001\u0013\u0015\u0005\u0019\u0012!B:dC2\f\u0017BA\u000b\u0013\u0005\u0019\te.\u001f*fMB\u0019q\u0003\u0007\u000e\u000e\u0003\u001dI!!G\u0004\u0003\u0013MKgn[)vKV,\u0007CA\u000e\u001d\u0019\u0001!Q!\b\u0001C\u0002y\u0011\u0011\u0001V\t\u0003?\t\u0002\"!\u0005\u0011\n\u0005\u0005\u0012\"a\u0002(pi\"Lgn\u001a\t\u0003#\rJ!\u0001\n\n\u0003\u0007\u0005s\u00170\u0001\u0004dC:\u001cW\r\u001c\u000b\u0002OA\u0011\u0011\u0003K\u0005\u0003SI\u0011A!\u00168ji\u0006\u00192+\u001b8l#V,W/Z,ji\"\u001c\u0015M\\2fYB\u0011qcA\n\u0003\u0007A\ta\u0001P5oSRtD#A\u0016\u0002\u000f\u0005\u001c8kY1mCV\u0011\u0011\u0007\u000f\u000b\u0003ee\u00022a\r\u001c8\u001b\u0005!$BA\u001b\n\u0003!\u00198-\u00197bINd\u0017B\u0001\u00045!\tY\u0002\bB\u0003\u001e\u000b\t\u0007a\u0004C\u0003;\u000b\u0001\u00071(A\u0003rk\u0016,X\rE\u0002\u0018\u0001]\u0002")
public interface SinkQueueWithCancel<T>
extends SinkQueue<T> {
    public static <T> akka.stream.scaladsl.SinkQueueWithCancel<T> asScala(SinkQueueWithCancel<T> queue) {
        return SinkQueueWithCancel$.MODULE$.asScala(queue);
    }

    public void cancel();
}

