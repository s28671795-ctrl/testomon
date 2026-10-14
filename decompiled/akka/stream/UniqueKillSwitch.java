/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.Done;
import akka.Done$;
import akka.stream.KillSwitch;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!3Aa\u0002\u0005\u0003\u001b!A\u0001\u0004\u0001BC\u0002\u0013%\u0011\u0004\u0003\u0005%\u0001\t\u0005\t\u0015!\u0003\u001b\u0011\u0019)\u0003\u0001\"\u0001\tM!)\u0011\u0006\u0001C\u0001U!)a\u0006\u0001C\u0001_!)a\b\u0001C!\u007f\t\u0001RK\\5rk\u0016\\\u0015\u000e\u001c7To&$8\r\u001b\u0006\u0003\u0013)\taa\u001d;sK\u0006l'\"A\u0006\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001a\u0002\u0006\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005U1R\"\u0001\u0005\n\u0005]A!AC&jY2\u001cv/\u001b;dQ\u00069\u0001O]8nSN,W#\u0001\u000e\u0011\u0007mq\u0002%D\u0001\u001d\u0015\ti\u0002#\u0001\u0006d_:\u001cWO\u001d:f]RL!a\b\u000f\u0003\u000fA\u0013x.\\5tKB\u0011\u0011EI\u0007\u0002\u0015%\u00111E\u0003\u0002\u0005\t>tW-\u0001\u0005qe>l\u0017n]3!\u0003\u0019a\u0014N\\5u}Q\u0011q\u0005\u000b\t\u0003+\u0001AQ\u0001G\u0002A\u0002i\t\u0001b\u001d5vi\u0012|wO\u001c\u000b\u0002WA\u0011q\u0002L\u0005\u0003[A\u0011A!\u00168ji\u0006)\u0011MY8siR\u00111\u0006\r\u0005\u0006c\u0015\u0001\rAM\u0001\u0003Kb\u0004\"aM\u001e\u000f\u0005QJdBA\u001b9\u001b\u00051$BA\u001c\r\u0003\u0019a$o\\8u}%\t\u0011#\u0003\u0002;!\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001f>\u0005%!\u0006N]8xC\ndWM\u0003\u0002;!\u0005AAo\\*ue&tw\rF\u0001A!\t\tUI\u0004\u0002C\u0007B\u0011Q\u0007E\u0005\u0003\tB\ta\u0001\u0015:fI\u00164\u0017B\u0001$H\u0005\u0019\u0019FO]5oO*\u0011A\t\u0005")
public final class UniqueKillSwitch
implements KillSwitch {
    private final Promise<Done> promise;

    private Promise<Done> promise() {
        return this.promise;
    }

    @Override
    public void shutdown() {
        this.promise().trySuccess(Done$.MODULE$);
    }

    @Override
    public void abort(Throwable ex) {
        this.promise().tryFailure(ex);
    }

    public String toString() {
        return new StringBuilder(18).append("SingleKillSwitch(").append(this.hashCode()).append(")").toString();
    }

    public UniqueKillSwitch(Promise<Done> promise) {
        this.promise = promise;
    }
}

