/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import java.util.concurrent.ExecutorService;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m1qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001C\u0001\fFq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\r\u0006\u001cGo\u001c:z\u0015\t!Q!\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u00051\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u0013A\u0011!\"D\u0007\u0002\u0017)\tA\"A\u0003tG\u0006d\u0017-\u0003\u0002\u000f\u0017\t1\u0011I\\=SK\u001a\fQc\u0019:fCR,W\t_3dkR|'oU3sm&\u001cW-F\u0001\u0012!\t\u0011\u0012$D\u0001\u0014\u0015\t!R#\u0001\u0006d_:\u001cWO\u001d:f]RT!AF\f\u0002\tU$\u0018\u000e\u001c\u0006\u00021\u0005!!.\u0019<b\u0013\tQ2CA\bFq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\u0001")
public interface ExecutorServiceFactory {
    public ExecutorService createExecutorService();
}

