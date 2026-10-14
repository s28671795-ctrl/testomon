/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000552qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u0013\u0001\u0019\u00051CA\u0005TS:\\\u0017+^3vK*\u0011A!B\u0001\bU\u00064\u0018\rZ:m\u0015\t1q!\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001)\"a\u0003\u0013\u0014\u0005\u0001a\u0001CA\u0007\u0011\u001b\u0005q!\"A\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Eq!AB!osJ+g-\u0001\u0003qk2dG#\u0001\u000b\u0011\u0007Uab$D\u0001\u0017\u0015\t9\u0002$\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u0007\u000e\u0002\tU$\u0018\u000e\u001c\u0006\u00027\u0005!!.\u0019<b\u0013\tibCA\bD_6\u0004H.\u001a;j_:\u001cF/Y4f!\ry\u0002EI\u0007\u00021%\u0011\u0011\u0005\u0007\u0002\t\u001fB$\u0018n\u001c8bYB\u00111\u0005\n\u0007\u0001\t\u0015)\u0003A1\u0001'\u0005\u0005!\u0016CA\u0014+!\ti\u0001&\u0003\u0002*\u001d\t9aj\u001c;iS:<\u0007CA\u0007,\u0013\tacBA\u0002B]f\u0004")
public interface SinkQueue<T> {
    public CompletionStage<Optional<T>> pull();
}

