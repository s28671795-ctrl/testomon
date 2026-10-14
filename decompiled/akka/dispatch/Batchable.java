/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.annotation.InternalApi;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r2\u0001BA\u0002\u0011\u0002G\u0005Qa\u0002\u0005\u0006'\u00011\t!\u0006\u0002\n\u0005\u0006$8\r[1cY\u0016T!\u0001B\u0003\u0002\u0011\u0011L7\u000f]1uG\"T\u0011AB\u0001\u0005C.\\\u0017mE\u0002\u0001\u0011A\u0001\"!\u0003\b\u000e\u0003)Q!a\u0003\u0007\u0002\t1\fgn\u001a\u0006\u0002\u001b\u0005!!.\u0019<b\u0013\ty!B\u0001\u0004PE*,7\r\u001e\t\u0003\u0013EI!A\u0005\u0006\u0003\u0011I+hN\\1cY\u0016\f1\"[:CCR\u001c\u0007.\u00192mK\u000e\u0001Q#\u0001\f\u0011\u0005]QR\"\u0001\r\u000b\u0003e\tQa]2bY\u0006L!a\u0007\r\u0003\u000f\t{w\u000e\\3b]\"\u0012\u0001!\b\t\u0003=\u0005j\u0011a\b\u0006\u0003A\u0015\t!\"\u00198o_R\fG/[8o\u0013\t\u0011sDA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public interface Batchable
extends Runnable {
    public boolean isBatchable();
}

