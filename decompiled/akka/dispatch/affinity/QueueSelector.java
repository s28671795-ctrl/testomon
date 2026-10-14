/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u0012\u0001\u0019\u0005!CA\u0007Rk\u0016,XmU3mK\u000e$xN\u001d\u0006\u0003\t\u0015\t\u0001\"\u00194gS:LG/\u001f\u0006\u0003\r\u001d\t\u0001\u0002Z5ta\u0006$8\r\u001b\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0006\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g\u0003!9W\r^)vKV,GcA\n\u0017AA\u0011A\u0002F\u0005\u0003+5\u00111!\u00138u\u0011\u00159\u0012\u00011\u0001\u0019\u0003\u001d\u0019w.\\7b]\u0012\u0004\"!\u0007\u0010\u000e\u0003iQ!a\u0007\u000f\u0002\t1\fgn\u001a\u0006\u0002;\u0005!!.\u0019<b\u0013\ty\"D\u0001\u0005Sk:t\u0017M\u00197f\u0011\u0015\t\u0013\u00011\u0001\u0014\u0003\u0019\tX/Z;fg\u0002")
public interface QueueSelector {
    public int getQueue(Runnable var1, int var2);
}

