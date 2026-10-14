/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FlowMonitorState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0011\u0001\u0019\u0005\u0011CA\u0006GY><Xj\u001c8ji>\u0014(B\u0001\u0003\u0006\u0003\u0019\u0019HO]3b[*\ta!\u0001\u0003bW.\f7\u0001A\u000b\u0003\u0013\t\u001a\"\u0001\u0001\u0006\u0011\u0005-qQ\"\u0001\u0007\u000b\u00035\tQa]2bY\u0006L!a\u0004\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0015\u0019H/\u0019;f+\u0005\u0011\u0002cA\n\u001eA9\u0011Ac\u0007\b\u0003+iq!AF\r\u000e\u0003]Q!\u0001G\u0004\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0011B\u0001\u0003\u0006\u0013\ta2!\u0001\tGY><Xj\u001c8ji>\u00148\u000b^1uK&\u0011ad\b\u0002\f'R\u0014X-Y7Ti\u0006$XM\u0003\u0002\u001d\u0007A\u0011\u0011E\t\u0007\u0001\t\u0019\u0019\u0003\u0001\"b\u0001I\t\tA+\u0005\u0002&QA\u00111BJ\u0005\u0003O1\u0011qAT8uQ&tw\r\u0005\u0002\fS%\u0011!\u0006\u0004\u0002\u0004\u0003:L\b")
public interface FlowMonitor<T> {
    public FlowMonitorState.StreamState<T> state();
}

