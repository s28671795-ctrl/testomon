/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.internal;

import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.pattern.internal.CircuitBreakerTelemetry;
import akka.pattern.internal.CircuitBreakerTelemetryProvider$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005;a!\u0002\u0004\t\u0002)aaA\u0002\b\u0007\u0011\u0003Qq\u0002C\u0003\u0017\u0003\u0011\u0005\u0001\u0004C\u0003\u001a\u0003\u0011\u0005!\u0004C\u00034\u0003\u0011\u0005A'A\u0010DSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:UK2,W.\u001a;ssB\u0013xN^5eKJT!a\u0002\u0005\u0002\u0011%tG/\u001a:oC2T!!\u0003\u0006\u0002\u000fA\fG\u000f^3s]*\t1\"\u0001\u0003bW.\f\u0007CA\u0007\u0002\u001b\u00051!aH\"je\u000e,\u0018\u000e\u001e\"sK\u0006\\WM\u001d+fY\u0016lW\r\u001e:z!J|g/\u001b3feN\u0011\u0011\u0001\u0005\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012\u0001D\u0001\u0006gR\f'\u000f\u001e\u000b\u00047yY\u0003CA\u0007\u001d\u0013\tibAA\fDSJ\u001cW/\u001b;Ce\u0016\f7.\u001a:UK2,W.\u001a;ss\")qd\u0001a\u0001A\u0005I!M]3bW\u0016\u0014\u0018\n\u001a\t\u0003C!r!A\t\u0014\u0011\u0005\r\u0012R\"\u0001\u0013\u000b\u0005\u0015:\u0012A\u0002\u001fs_>$h(\u0003\u0002(%\u00051\u0001K]3eK\u001aL!!\u000b\u0016\u0003\rM#(/\u001b8h\u0015\t9#\u0003C\u0003-\u0007\u0001\u0007Q&\u0001\u0004tsN$X-\u001c\t\u0003]Ej\u0011a\f\u0006\u0003a)\tQ!Y2u_JL!AM\u0018\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7\u0002\r\r\u0014X-\u0019;f)\u0011YRGN\u001c\t\u000b}!\u0001\u0019\u0001\u0011\t\u000b1\"\u0001\u0019A\u0017\t\u000ba\"\u0001\u0019\u0001\u0011\u0002\t\u0019\f8M\u001c\u0015\u0003\u0003i\u0002\"a\u000f \u000e\u0003qR!!\u0010\u0006\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002@y\tY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001!\b")
@InternalApi
public final class CircuitBreakerTelemetryProvider {
    public static CircuitBreakerTelemetry create(String breakerId, ExtendedActorSystem system, String fqcn) {
        return CircuitBreakerTelemetryProvider$.MODULE$.create(breakerId, system, fqcn);
    }

    public static CircuitBreakerTelemetry start(String breakerId, ExtendedActorSystem system) {
        return CircuitBreakerTelemetryProvider$.MODULE$.start(breakerId, system);
    }
}

