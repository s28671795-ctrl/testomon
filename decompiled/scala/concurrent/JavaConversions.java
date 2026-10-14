/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.concurrent.JavaConversions$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y:Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQaE\u0001\u0005\u0002QAQ!F\u0001\u0005\u0004YAQ!F\u0001\u0005\u0004=\nqBS1wC\u000e{gN^3sg&|gn\u001d\u0006\u0003\u000f!\t!bY8oGV\u0014(/\u001a8u\u0015\u0005I\u0011!B:dC2\f7\u0001\u0001\t\u0003\u0019\u0005i\u0011A\u0002\u0002\u0010\u0015\u00064\u0018mQ8om\u0016\u00148/[8ogN\u0011\u0011a\u0004\t\u0003!Ei\u0011\u0001C\u0005\u0003%!\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\f\u0003I\t7/\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;\u0015\u0005]Q\u0002C\u0001\u0007\u0019\u0013\tIbAA\u0010Fq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yi\u0016CXmY;u_J\u001cVM\u001d<jG\u0016DQaG\u0002A\u0002q\tA!\u001a=fGB\u0011QdI\u0007\u0002=)\u0011qa\b\u0006\u0003A\u0005\nA!\u001e;jY*\t!%\u0001\u0003kCZ\f\u0017B\u0001\u0013\u001f\u0005=)\u00050Z2vi>\u00148+\u001a:wS\u000e,\u0007FB\u0002'S)bS\u0006\u0005\u0002\u0011O%\u0011\u0001\u0006\u0003\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002W\u0005\u0011Tk]3!A\u0016CXmY;uS>t7i\u001c8uKb$hF\u001a:p[\u0016CXmY;u_J\u001cVM\u001d<jG\u0016\u0004\u0007%\u001b8ti\u0016\fG-A\u0003tS:\u001cW-I\u0001/\u0003\u0019\u0011d&M\u001a/aQ\u0011\u0001g\r\t\u0003\u0019EJ!A\r\u0004\u00031\u0015CXmY;uS>t7i\u001c8uKb$X\t_3dkR|'\u000fC\u0003\u001c\t\u0001\u0007A\u0007\u0005\u0002\u001ek%\u0011aG\b\u0002\t\u000bb,7-\u001e;pe\"2AAJ\u00159Y5\n\u0013!O\u0001,+N,\u0007\u0005Y#yK\u000e,H/[8o\u0007>tG/\u001a=u]\u0019\u0014x.\\#yK\u000e,Ho\u001c:aA%t7\u000f^3bI\"2\u0011AJ\u0015<Y5\n\u0013\u0001P\u00016+N,\u0007\u0005\u001e5fA\u0019\f7\r^8ss\u0002jW\r\u001e5pIN\u0004\u0013N\u001c\u0011a\u000bb,7-\u001e;j_:\u001cuN\u001c;fqR\u0004\u0007%\u001b8ti\u0016\fG\r\u000b\u0004\u0001M%ZD&\f")
public final class JavaConversions {
    public static ExecutionContextExecutor asExecutionContext(Executor exec) {
        return JavaConversions$.MODULE$.asExecutionContext(exec);
    }

    public static ExecutionContextExecutorService asExecutionContext(ExecutorService exec) {
        return JavaConversions$.MODULE$.asExecutionContext(exec);
    }
}

