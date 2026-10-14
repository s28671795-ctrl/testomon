/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.ExecutorServiceFactory;
import java.util.concurrent.ThreadFactory;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001C\u0001\u0010Fq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\r\u0006\u001cGo\u001c:z!J|g/\u001b3fe*\u0011A!B\u0001\tI&\u001c\b/\u0019;dQ*\ta!\u0001\u0003bW.\f7\u0001A\n\u0003\u0001%\u0001\"AC\u0007\u000e\u0003-Q\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001d-\u0011a!\u00118z%\u00164\u0017\u0001H2sK\u0006$X-\u0012=fGV$xN]*feZL7-\u001a$bGR|'/\u001f\u000b\u0004#U\u0011\u0003C\u0001\n\u0014\u001b\u0005\u0019\u0011B\u0001\u000b\u0004\u0005Y)\u00050Z2vi>\u00148+\u001a:wS\u000e,g)Y2u_JL\b\"\u0002\f\u0002\u0001\u00049\u0012AA5e!\tArD\u0004\u0002\u001a;A\u0011!dC\u0007\u00027)\u0011AdB\u0001\u0007yI|w\u000e\u001e \n\u0005yY\u0011A\u0002)sK\u0012,g-\u0003\u0002!C\t11\u000b\u001e:j]\u001eT!AH\u0006\t\u000b\r\n\u0001\u0019\u0001\u0013\u0002\u001bQD'/Z1e\r\u0006\u001cGo\u001c:z!\t)C&D\u0001'\u0015\t9\u0003&\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u000b\u0016\u0002\tU$\u0018\u000e\u001c\u0006\u0002W\u0005!!.\u0019<b\u0013\ticEA\u0007UQJ,\u0017\r\u001a$bGR|'/\u001f")
public interface ExecutorServiceFactoryProvider {
    public ExecutorServiceFactory createExecutorServiceFactory(String var1, ThreadFactory var2);
}

