/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import java.util.concurrent.ExecutorService;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u0012\u0001\u0019\u0005!C\u0001\tSK*,7\r^5p]\"\u000bg\u000e\u001a7fe*\u0011A!B\u0001\tC\u001a4\u0017N\\5us*\u0011aaB\u0001\tI&\u001c\b/\u0019;dQ*\t\u0001\"\u0001\u0003bW.\f7\u0001A\n\u0003\u0001-\u0001\"\u0001D\b\u000e\u00035Q\u0011AD\u0001\u0006g\u000e\fG.Y\u0005\u0003!5\u0011a!\u00118z%\u00164\u0017A\u0002:fU\u0016\u001cG\u000fF\u0002\u0014-\u0001\u0002\"\u0001\u0004\u000b\n\u0005Ui!\u0001B+oSRDQaF\u0001A\u0002a\tqaY8n[\u0006tG\r\u0005\u0002\u001a=5\t!D\u0003\u0002\u001c9\u0005!A.\u00198h\u0015\u0005i\u0012\u0001\u00026bm\u0006L!a\b\u000e\u0003\u0011I+hN\\1cY\u0016DQ!I\u0001A\u0002\t\nqa]3sm&\u001cW\r\u0005\u0002$Q5\tAE\u0003\u0002&M\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005\u001db\u0012\u0001B;uS2L!!\u000b\u0013\u0003\u001f\u0015CXmY;u_J\u001cVM\u001d<jG\u0016\u0004")
public interface RejectionHandler {
    public void reject(Runnable var1, ExecutorService var2);
}

