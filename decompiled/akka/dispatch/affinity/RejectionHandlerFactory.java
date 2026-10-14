/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.dispatch.affinity.RejectionHandler;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]1qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u0012\u0001\u0019\u0005!CA\fSK*,7\r^5p]\"\u000bg\u000e\u001a7fe\u001a\u000b7\r^8ss*\u0011A!B\u0001\tC\u001a4\u0017N\\5us*\u0011aaB\u0001\tI&\u001c\b/\u0019;dQ*\t\u0001\"\u0001\u0003bW.\f7\u0001A\n\u0003\u0001-\u0001\"\u0001D\b\u000e\u00035Q\u0011AD\u0001\u0006g\u000e\fG.Y\u0005\u0003!5\u0011a!\u00118z%\u00164\u0017AB2sK\u0006$X\rF\u0001\u0014!\t!R#D\u0001\u0004\u0013\t12A\u0001\tSK*,7\r^5p]\"\u000bg\u000e\u001a7fe\u0002")
public interface RejectionHandlerFactory {
    public RejectionHandler create();
}

