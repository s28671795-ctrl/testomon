/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.SubstreamCancelStrategy$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00192Qa\u0002\u0005\u0002\"5AQ\u0001\u0006\u0001\u0005\u0002U9Qa\b\u0005\t\u0002\u00012Qa\u0002\u0005\t\u0002\u0005BQ\u0001F\u0002\u0005\u0002\tBQaI\u0002\u0005\u0002\u0011BQ!J\u0002\u0005\u0002\u0011\u0012qcU;cgR\u0014X-Y7DC:\u001cW\r\\*ue\u0006$XmZ=\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0004\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u00051\u0002CA\f\u0001\u001b\u0005A\u0011f\u0001\u0001\u001a;)\u0011!dG\u0001\u0006\tJ\f\u0017N\u001c\u0006\u00039!\t\u0011dU;cgR\u0014X-Y7DC:\u001cW\r\\*ue\u0006$XmZ5fg*\u0011adG\u0001\n!J|\u0007/Y4bi\u0016\fqcU;cgR\u0014X-Y7DC:\u001cW\r\\*ue\u0006$XmZ=\u0011\u0005]\u00191CA\u0002\u000f)\u0005\u0001\u0013!\u00039s_B\fw-\u0019;f+\u00051\u0012!\u00023sC&t\u0007")
public abstract class SubstreamCancelStrategy {
    public static SubstreamCancelStrategy drain() {
        return SubstreamCancelStrategy$.MODULE$.drain();
    }

    public static SubstreamCancelStrategy propagate() {
        return SubstreamCancelStrategy$.MODULE$.propagate();
    }
}

