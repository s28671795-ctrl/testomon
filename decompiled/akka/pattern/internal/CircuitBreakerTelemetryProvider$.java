/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.pattern.internal.CircuitBreakerEnsembleTelemetry;
import akka.pattern.internal.CircuitBreakerNoopTelemetry$;
import akka.pattern.internal.CircuitBreakerTelemetry;
import akka.util.ccompat.package$JavaConverters$;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ClassTag$;
import scala.runtime.Nothing$;

@InternalApi
public final class CircuitBreakerTelemetryProvider$ {
    public static final CircuitBreakerTelemetryProvider$ MODULE$ = new CircuitBreakerTelemetryProvider$();

    public CircuitBreakerTelemetry start(String breakerId, ExtendedActorSystem system) {
        String configPath = "akka.circuit-breaker.telemetry.implementations";
        if (!system.settings().config().hasPath(configPath)) {
            return CircuitBreakerNoopTelemetry$.MODULE$;
        }
        java.util.List<String> telemetryFqcns = system.settings().config().getStringList(configPath);
        int n = telemetryFqcns.size();
        switch (n) {
            case 0: {
                return CircuitBreakerNoopTelemetry$.MODULE$;
            }
            case 1: {
                String fqcn = telemetryFqcns.get(0);
                return this.create(breakerId, system, fqcn);
            }
        }
        return new CircuitBreakerEnsembleTelemetry(package$JavaConverters$.MODULE$.ListHasAsScala(telemetryFqcns).asScala().toSeq(), breakerId, system);
    }

    public CircuitBreakerTelemetry create(String breakerId, ExtendedActorSystem system, String fqcn) {
        return (CircuitBreakerTelemetry)system.dynamicAccess().createInstanceFor(fqcn, new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(String.class), breakerId)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ExtendedActorSystem.class), system)), Nil$.MODULE$)), ClassTag$.MODULE$.apply(CircuitBreakerTelemetry.class)).get();
    }

    private CircuitBreakerTelemetryProvider$() {
    }
}

