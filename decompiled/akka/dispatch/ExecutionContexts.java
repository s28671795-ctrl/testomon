/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.ExecutionContexts$;
import akka.japi.Procedure;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.ExecutionContextExecutorService;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00055r!\u0002\t\u0012\u0011\u00031b!\u0002\r\u0012\u0011\u0003I\u0002\"\u0002\u0011\u0002\t\u0003\t\u0003\"\u0002\u0012\u0002\t\u0003\u0019\u0003\"\u0002\u0012\u0002\t\u0003)\u0004\"B&\u0002\t\u0003a\u0005\"B&\u0002\t\u0003)\u0006\"\u0002-\u0002\t\u0003I\u0006\u0002\u0003.\u0002\u0005\u0004%\taE.\t\r}\u000b\u0001\u0015!\u0003]\u000f\u00199\u0017\u0001#\u0001\u0014Q\u001a1!.\u0001E\u0001'-DQ\u0001I\u0006\u0005\u0002=DQ\u0001]\u0006\u0005REDQ!`\u0006\u0005RyDq!!\u0002\f\t\u0003\n9!A\tFq\u0016\u001cW\u000f^5p]\u000e{g\u000e^3yiNT!AE\n\u0002\u0011\u0011L7\u000f]1uG\"T\u0011\u0001F\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005]\tQ\"A\t\u0003#\u0015CXmY;uS>t7i\u001c8uKb$8o\u0005\u0002\u00025A\u00111DH\u0007\u00029)\tQ$A\u0003tG\u0006d\u0017-\u0003\u0002 9\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\f\u0002\u0019\u0019\u0014x.\\#yK\u000e,Ho\u001c:\u0015\u0005\u0011R\u0003CA\u0013)\u001b\u00051#BA\u0014\u001d\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003S\u0019\u0012\u0001$\u0012=fGV$\u0018n\u001c8D_:$X\r\u001f;Fq\u0016\u001cW\u000f^8s\u0011\u0015Y3\u00011\u0001-\u0003!)\u00070Z2vi>\u0014\bCA\u00174\u001b\u0005q#BA\u00140\u0015\t\u0001\u0014'\u0001\u0003vi&d'\"\u0001\u001a\u0002\t)\fg/Y\u0005\u0003i9\u0012\u0001\"\u0012=fGV$xN\u001d\u000b\u0004IY:\u0004\"B\u0016\u0005\u0001\u0004a\u0003\"\u0002\u001d\u0005\u0001\u0004I\u0014!D3se>\u0014(+\u001a9peR,'\u000fE\u0002;{}j\u0011a\u000f\u0006\u0003yM\tAA[1qS&\u0011ah\u000f\u0002\n!J|7-\u001a3ve\u0016\u0004\"\u0001\u0011%\u000f\u0005\u00053eB\u0001\"F\u001b\u0005\u0019%B\u0001#\u0016\u0003\u0019a$o\\8u}%\tQ$\u0003\u0002H9\u00059\u0001/Y2lC\u001e,\u0017BA%K\u0005%!\u0006N]8xC\ndWM\u0003\u0002H9\u0005\u0019bM]8n\u000bb,7-\u001e;peN+'O^5dKR\u0011Q\n\u0015\t\u0003K9K!a\u0014\u0014\u0003?\u0015CXmY;uS>t7i\u001c8uKb$X\t_3dkR|'oU3sm&\u001cW\rC\u0003R\u000b\u0001\u0007!+A\bfq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f!\ti3+\u0003\u0002U]\tyQ\t_3dkR|'oU3sm&\u001cW\rF\u0002N-^CQ!\u0015\u0004A\u0002ICQ\u0001\u000f\u0004A\u0002e\naa\u001a7pE\u0006dG#\u0001\u0013\u0002\u0013A\f'/Y:ji&\u001cW#\u0001/\u0011\u0005\u0015j\u0016B\u00010'\u0005A)\u00050Z2vi&|gnQ8oi\u0016DH/\u0001\u0006qCJ\f7/\u001b;jG\u0002B#!C1\u0011\u0005\t,W\"A2\u000b\u0005\u0011\u001c\u0012AC1o]>$\u0018\r^5p]&\u0011am\u0019\u0002\u0012\u0013:$XM\u001d8bYN#\u0018M\u00197f\u0003BL\u0017AG:b[\u0016$\u0006N]3bI\u0016CXmY;uS>t7i\u001c8uKb$\bCA5\f\u001b\u0005\t!AG:b[\u0016$\u0006N]3bI\u0016CXmY;uS>t7i\u001c8uKb$8\u0003B\u0006\u001b92\u0004\"aF7\n\u00059\f\"\u0001\u0005\"bi\u000eD\u0017N\\4Fq\u0016\u001cW\u000f^8s)\u0005A\u0017\u0001E;oE\u0006$8\r[3e\u000bb,7-\u001e;f)\t\u0011X\u000f\u0005\u0002\u001cg&\u0011A\u000f\b\u0002\u0005+:LG\u000fC\u0003w\u001b\u0001\u0007q/\u0001\u0005sk:t\u0017M\u00197f!\tA80D\u0001z\u0015\tQ\u0018'\u0001\u0003mC:<\u0017B\u0001?z\u0005!\u0011VO\u001c8bE2,\u0017a\u0004:fgV\u0014W.\u001b;P]\ncwnY6\u0016\u0003}\u00042aGA\u0001\u0013\r\t\u0019\u0001\b\u0002\b\u0005>|G.Z1o\u00035\u0011X\r]8si\u001a\u000b\u0017\u000e\\;sKR\u0019!/!\u0003\t\r\u0005-q\u00021\u0001@\u0003\u0005!\bfC\u0006\u0002\u0010\u0005U\u0011qCA\u000e\u0003;\u00012aGA\t\u0013\r\t\u0019\u0002\b\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0003\u00033\tq%V:fA\u0015CXmY;uS>t7i\u001c8uKb$8O\f9be\u0006\u001c\u0018\u000e^5dA%t7\u000f^3bI\u0006)1/\u001b8dK\u0006\u0012\u0011qD\u0001\u0006e92d\u0006\u000e\u0015\u0004\u0017\u0005\r\u0002c\u00012\u0002&%\u0019\u0011qE2\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\f\u0015\u0005=\u0011QCA\f\u00037\ti\u0002K\u0002\u000b\u0003G\u0001")
public final class ExecutionContexts {
    public static ExecutionContextExecutor global() {
        return ExecutionContexts$.MODULE$.global();
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService executorService2, Procedure<Throwable> errorReporter) {
        return ExecutionContexts$.MODULE$.fromExecutorService(executorService2, errorReporter);
    }

    public static ExecutionContextExecutorService fromExecutorService(ExecutorService executorService2) {
        return ExecutionContexts$.MODULE$.fromExecutorService(executorService2);
    }

    public static ExecutionContextExecutor fromExecutor(Executor executor2, Procedure<Throwable> errorReporter) {
        return ExecutionContexts$.MODULE$.fromExecutor(executor2, errorReporter);
    }

    public static ExecutionContextExecutor fromExecutor(Executor executor2) {
        return ExecutionContexts$.MODULE$.fromExecutor(executor2);
    }
}

