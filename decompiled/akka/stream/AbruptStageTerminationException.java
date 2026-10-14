/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.stage.GraphStageLogic;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u000512Aa\u0001\u0003\u0003\u0013!A\u0001\u0005\u0001B\u0001B\u0003%\u0011\u0005C\u0003(\u0001\u0011\u0005\u0001FA\u0010BEJ,\b\u000f^*uC\u001e,G+\u001a:nS:\fG/[8o\u000bb\u001cW\r\u001d;j_:T!!\u0002\u0004\u0002\rM$(/Z1n\u0015\u00059\u0011\u0001B1lW\u0006\u001c\u0001aE\u0002\u0001\u0015a\u0001\"aC\u000b\u000f\u00051\u0011bBA\u0007\u0011\u001b\u0005q!BA\b\t\u0003\u0019a$o\\8u}%\t\u0011#A\u0003tG\u0006d\u0017-\u0003\u0002\u0014)\u00059\u0001/Y2lC\u001e,'\"A\t\n\u0005Y9\"\u0001\u0005*v]RLW.Z#yG\u0016\u0004H/[8o\u0015\t\u0019B\u0003\u0005\u0002\u001a=5\t!D\u0003\u0002\u001c9\u000591m\u001c8ue>d'BA\u000f\u0015\u0003\u0011)H/\u001b7\n\u0005}Q\"\u0001\u0004(p'R\f7m\u001b+sC\u000e,\u0017!\u00027pO&\u001c\u0007C\u0001\u0012&\u001b\u0005\u0019#B\u0001\u0013\u0005\u0003\u0015\u0019H/Y4f\u0013\t13EA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0003\u0019a\u0014N\\5u}Q\u0011\u0011f\u000b\t\u0003U\u0001i\u0011\u0001\u0002\u0005\u0006A\t\u0001\r!\t")
public final class AbruptStageTerminationException
extends RuntimeException
implements NoStackTrace {
    @Override
    public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable fillInStackTrace() {
        return NoStackTrace.fillInStackTrace$(this);
    }

    public AbruptStageTerminationException(GraphStageLogic logic) {
        super(new StringBuilder(98).append("GraphStage [").append(logic).append("] terminated abruptly, caused by for example materializer or actor system termination.").toString());
        NoStackTrace.$init$(this);
    }
}

