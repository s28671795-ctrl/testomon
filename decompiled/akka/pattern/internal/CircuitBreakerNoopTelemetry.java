/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.annotation.InternalApi;
import akka.pattern.internal.CircuitBreakerNoopTelemetry$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0001;aa\u0003\u0007\t\u0002A\u0011bA\u0002\u000b\r\u0011\u0003\u0001R\u0003C\u0003 \u0003\u0011\u0005\u0011\u0005C\u0003#\u0003\u0011\u00053\u0005C\u0003(\u0003\u0011\u00053\u0005C\u0003)\u0003\u0011\u00053\u0005C\u0003*\u0003\u0011\u0005#\u0006C\u00031\u0003\u0011\u0005\u0013\u0007C\u00034\u0003\u0011\u0005C\u0007C\u00037\u0003\u0011\u00053\u0005C\u00038\u0003\u0011\u00053%A\u000eDSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:O_>\u0004H+\u001a7f[\u0016$(/\u001f\u0006\u0003\u001b9\t\u0001\"\u001b8uKJt\u0017\r\u001c\u0006\u0003\u001fA\tq\u0001]1ui\u0016\u0014hNC\u0001\u0012\u0003\u0011\t7n[1\u0011\u0005M\tQ\"\u0001\u0007\u00037\rK'oY;ji\n\u0013X-Y6fe:{w\u000e\u001d+fY\u0016lW\r\u001e:z'\r\ta\u0003\b\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0011\u0005Mi\u0012B\u0001\u0010\r\u0005]\u0019\u0015N]2vSR\u0014%/Z1lKJ$V\r\\3nKR\u0014\u00180\u0001\u0004=S:LGOP\u0002\u0001)\u0005\u0011\u0012AB8o\u001fB,g\u000eF\u0001%!\t9R%\u0003\u0002'1\t!QK\\5u\u0003\u001dygn\u00117pg\u0016\f!b\u001c8IC24w\n]3o\u00035ygnQ1mYN+8mY3tgR\u0011Ae\u000b\u0005\u0006Y\u0019\u0001\r!L\u0001\rK2\f\u0007o]3e\u001d\u0006twn\u001d\t\u0003/9J!a\f\r\u0003\t1{gnZ\u0001\u000e_:\u001c\u0015\r\u001c7GC&dWO]3\u0015\u0005\u0011\u0012\u0004\"\u0002\u0017\b\u0001\u0004i\u0013\u0001F8o\u0007\u0006dG\u000eV5nK>,HOR1jYV\u0014X\r\u0006\u0002%k!)A\u0006\u0003a\u0001[\u0005ArN\\\"bY2\u0014%/Z1lKJ|\u0005/\u001a8GC&dWO]3\u0002\u000fM$x\u000e\u001d9fI\"\u0012\u0011!\u000f\t\u0003uuj\u0011a\u000f\u0006\u0003yA\t!\"\u00198o_R\fG/[8o\u0013\tq4HA\u0006J]R,'O\\1m\u0003BL\u0007F\u0001\u0001:\u0001")
@InternalApi
public final class CircuitBreakerNoopTelemetry {
    public static void stopped() {
        CircuitBreakerNoopTelemetry$.MODULE$.stopped();
    }

    public static void onCallBreakerOpenFailure() {
        CircuitBreakerNoopTelemetry$.MODULE$.onCallBreakerOpenFailure();
    }

    public static void onCallTimeoutFailure(long elapsedNanos) {
        CircuitBreakerNoopTelemetry$.MODULE$.onCallTimeoutFailure(elapsedNanos);
    }

    public static void onCallFailure(long elapsedNanos) {
        CircuitBreakerNoopTelemetry$.MODULE$.onCallFailure(elapsedNanos);
    }

    public static void onCallSuccess(long elapsedNanos) {
        CircuitBreakerNoopTelemetry$.MODULE$.onCallSuccess(elapsedNanos);
    }

    public static void onHalfOpen() {
        CircuitBreakerNoopTelemetry$.MODULE$.onHalfOpen();
    }

    public static void onClose() {
        CircuitBreakerNoopTelemetry$.MODULE$.onClose();
    }

    public static void onOpen() {
        CircuitBreakerNoopTelemetry$.MODULE$.onOpen();
    }
}

