/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.pattern.internal.CircuitBreakerTelemetry;
import akka.pattern.internal.CircuitBreakerTelemetryProvider$;
import java.io.Serializable;
import scala.Function1;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u000514Qa\u0004\t\u0001)YA\u0001\"\t\u0001\u0003\u0002\u0003\u0006Ia\t\u0005\to\u0001\u0011\t\u0011)A\u0005_!A\u0001\b\u0001B\u0001B\u0003%\u0011\bC\u0003@\u0001\u0011\u0005\u0001\tC\u0004F\u0001\t\u0007I\u0011\u0002$\t\r9\u0003\u0001\u0015!\u0003H\u0011\u0015y\u0005\u0001\"\u0011Q\u0011\u0015!\u0006\u0001\"\u0011Q\u0011\u0015)\u0006\u0001\"\u0011Q\u0011\u00151\u0006\u0001\"\u0011X\u0011\u0015i\u0006\u0001\"\u0011_\u0011\u0015\u0001\u0007\u0001\"\u0011b\u0011\u0015\u0019\u0007\u0001\"\u0011Q\u0011\u0015!\u0007\u0001\"\u0011Q\u0005}\u0019\u0015N]2vSR\u0014%/Z1lKJ,en]3nE2,G+\u001a7f[\u0016$(/\u001f\u0006\u0003#I\t\u0001\"\u001b8uKJt\u0017\r\u001c\u0006\u0003'Q\tq\u0001]1ui\u0016\u0014hNC\u0001\u0016\u0003\u0011\t7n[1\u0014\u0007\u00019R\u0004\u0005\u0002\u001975\t\u0011DC\u0001\u001b\u0003\u0015\u00198-\u00197b\u0013\ta\u0012D\u0001\u0004B]f\u0014VM\u001a\t\u0003=}i\u0011\u0001E\u0005\u0003AA\u0011qcQ5sGVLGO\u0011:fC.,'\u000fV3mK6,GO]=\u0002\u001dQ,G.Z7fiJLh)]2og\u000e\u0001\u0001c\u0001\u0013-_9\u0011QE\u000b\b\u0003M%j\u0011a\n\u0006\u0003Q\t\na\u0001\u0010:p_Rt\u0014\"\u0001\u000e\n\u0005-J\u0012a\u00029bG.\fw-Z\u0005\u0003[9\u00121aU3r\u0015\tY\u0013\u0004\u0005\u00021i9\u0011\u0011G\r\t\u0003MeI!aM\r\u0002\rA\u0013X\rZ3g\u0013\t)dG\u0001\u0004TiJLgn\u001a\u0006\u0003ge\t\u0011B\u0019:fC.,'/\u00133\u0002\rML8\u000f^3n!\tQT(D\u0001<\u0015\taD#A\u0003bGR|'/\u0003\u0002?w\t\u0019R\t\u001f;f]\u0012,G-Q2u_J\u001c\u0016p\u001d;f[\u00061A(\u001b8jiz\"B!\u0011\"D\tB\u0011a\u0004\u0001\u0005\u0006C\u0011\u0001\ra\t\u0005\u0006o\u0011\u0001\ra\f\u0005\u0006q\u0011\u0001\r!O\u0001\fi\u0016dW-\\3ue&,7/F\u0001H!\rAU*H\u0007\u0002\u0013*\u0011!jS\u0001\nS6lW\u000f^1cY\u0016T!\u0001T\r\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002.\u0013\u0006aA/\u001a7f[\u0016$(/[3tA\u00051qN\\(qK:$\u0012!\u0015\t\u00031IK!aU\r\u0003\tUs\u0017\u000e^\u0001\b_:\u001cEn\\:f\u0003)yg\u000eS1mM>\u0003XM\\\u0001\u000e_:\u001c\u0015\r\u001c7Tk\u000e\u001cWm]:\u0015\u0005EC\u0006\"B-\u000b\u0001\u0004Q\u0016\u0001D3mCB\u001cX\r\u001a(b]>\u001c\bC\u0001\r\\\u0013\ta\u0016D\u0001\u0003M_:<\u0017!D8o\u0007\u0006dGNR1jYV\u0014X\r\u0006\u0002R?\")\u0011l\u0003a\u00015\u0006!rN\\\"bY2$\u0016.\\3pkR4\u0015-\u001b7ve\u0016$\"!\u00152\t\u000bec\u0001\u0019\u0001.\u00021=t7)\u00197m\u0005J,\u0017m[3s\u001fB,gNR1jYV\u0014X-A\u0004ti>\u0004\b/\u001a3)\u0005\u00011\u0007CA4k\u001b\u0005A'BA5\u0015\u0003)\tgN\\8uCRLwN\\\u0005\u0003W\"\u00141\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public class CircuitBreakerEnsembleTelemetry
implements CircuitBreakerTelemetry {
    private final String breakerId;
    private final ExtendedActorSystem system;
    private final Seq<CircuitBreakerTelemetry> telemetries;

    private Seq<CircuitBreakerTelemetry> telemetries() {
        return this.telemetries;
    }

    @Override
    public void onOpen() {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$1 -> {
            x$1.onOpen();
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void onClose() {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$2 -> {
            x$2.onClose();
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void onHalfOpen() {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$3 -> {
            x$3.onHalfOpen();
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void onCallSuccess(long elapsedNanos) {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$4 -> {
            x$4.onCallSuccess(elapsedNanos);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void onCallFailure(long elapsedNanos) {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$5 -> {
            x$5.onCallFailure(elapsedNanos);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void onCallTimeoutFailure(long elapsedNanos) {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$6 -> {
            x$6.onCallTimeoutFailure(elapsedNanos);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void onCallBreakerOpenFailure() {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$7 -> {
            x$7.onCallBreakerOpenFailure();
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void stopped() {
        this.telemetries().foreach((Function1<CircuitBreakerTelemetry, Object> & Serializable)x$8 -> {
            x$8.stopped();
            return BoxedUnit.UNIT;
        });
    }

    public CircuitBreakerEnsembleTelemetry(Seq<String> telemetryFqcns, String breakerId, ExtendedActorSystem system) {
        this.breakerId = breakerId;
        this.system = system;
        this.telemetries = (Seq)telemetryFqcns.map((Function1<String, CircuitBreakerTelemetry> & Serializable)fqcn -> CircuitBreakerTelemetryProvider$.MODULE$.create($this.breakerId, $this.system, (String)fqcn));
    }
}

