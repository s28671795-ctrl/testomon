/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionId;
import akka.pattern.CircuitBreaker;
import akka.pattern.CircuitBreakersRegistry$;
import akka.pattern.internal.CircuitBreakerTelemetry;
import akka.pattern.internal.CircuitBreakerTelemetryProvider$;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import scala.collection.immutable.Set;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rq!\u0002\n\u0014\u0011\u0003Ab!\u0002\u000e\u0014\u0011\u0003Y\u0002\"B\u0019\u0002\t\u00039\u0007\"\u00025\u0002\t\u0003J\u0007\"B6\u0002\t\u0003b\u0007\"B1\u0002\t\u0003:\b\"B1\u0002\t\u0003bh\u0001\u0002\u000e\u0014\u0005%B\u0001\"L\u0004\u0003\u0002\u0003\u0006IA\f\u0005\u0006c\u001d!\tA\r\u0005\bi\u001d\u0011\r\u0011\"\u00036\u0011\u0019qu\u0001)A\u0005m!9qj\u0002b\u0001\n\u0013\u0001\u0006B\u0002.\bA\u0003%\u0011\u000bC\u0004\\\u000f\t\u0007I\u0011\u0002)\t\rq;\u0001\u0015!\u0003R\u0011\u0015iv\u0001\"\u0003_\u0011\u0019\tw\u0001\"\u0001\u0016E\u000692)\u001b:dk&$(I]3bW\u0016\u00148OU3hSN$(/\u001f\u0006\u0003)U\tq\u0001]1ui\u0016\u0014hNC\u0001\u0017\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0011$A\u0007\u0002'\t92)\u001b:dk&$(I]3bW\u0016\u00148OU3hSN$(/_\n\u0005\u0003q\u0011C\r\u0005\u0002\u001eA5\taDC\u0001 \u0003\u0015\u00198-\u00197b\u0013\t\tcD\u0001\u0004B]f\u0014VM\u001a\t\u0004G\u0019BS\"\u0001\u0013\u000b\u0005\u0015*\u0012!B1di>\u0014\u0018BA\u0014%\u0005-)\u0005\u0010^3og&|g.\u00133\u0011\u0005e91cA\u0004\u001dUA\u00111eK\u0005\u0003Y\u0011\u0012\u0011\"\u0012=uK:\u001c\u0018n\u001c8\u0002\rML8\u000f^3n!\t\u0019s&\u0003\u00021I\t\u0019R\t\u001f;f]\u0012,G-Q2u_J\u001c\u0016p\u001d;f[\u00061A(\u001b8jiz\"\"\u0001K\u001a\t\u000b5J\u0001\u0019\u0001\u0018\u0002\u0011\t\u0014X-Y6feN,\u0012A\u000e\t\u0005oy\u00025*D\u00019\u0015\tI$(\u0001\u0006d_:\u001cWO\u001d:f]RT!a\u000f\u001f\u0002\tU$\u0018\u000e\u001c\u0006\u0002{\u0005!!.\u0019<b\u0013\ty\u0004HA\tD_:\u001cWO\u001d:f]RD\u0015m\u001d5NCB\u0004\"!\u0011%\u000f\u0005\t3\u0005CA\"\u001f\u001b\u0005!%BA#\u0018\u0003\u0019a$o\\8u}%\u0011qIH\u0001\u0007!J,G-\u001a4\n\u0005%S%AB*ue&twM\u0003\u0002H=A\u0011\u0011\u0004T\u0005\u0003\u001bN\u0011abQ5sGVLGO\u0011:fC.,'/A\u0005ce\u0016\f7.\u001a:tA\u000511m\u001c8gS\u001e,\u0012!\u0015\t\u0003%bk\u0011a\u0015\u0006\u0003\u001fRS!!\u0016,\u0002\u0011QL\b/Z:bM\u0016T\u0011aV\u0001\u0004G>l\u0017BA-T\u0005\u0019\u0019uN\u001c4jO\u000691m\u001c8gS\u001e\u0004\u0013\u0001\u00063fM\u0006,H\u000e\u001e\"sK\u0006\\WM]\"p]\u001aLw-A\u000beK\u001a\fW\u000f\u001c;Ce\u0016\f7.\u001a:D_:4\u0017n\u001a\u0011\u0002)\r\u0014X-\u0019;f\u0007&\u00148-^5u\u0005J,\u0017m[3s)\tYu\fC\u0003a!\u0001\u0007\u0001)\u0001\u0002jI\u0006\u0019q-\u001a;\u0015\u0005-\u001b\u0007\"\u00021\u0012\u0001\u0004\u0001\u0005CA\u0012f\u0013\t1GEA\nFqR,gn]5p]&#\u0007K]8wS\u0012,'\u000fF\u0001\u0019\u0003=\u0019'/Z1uK\u0016CH/\u001a8tS>tGC\u0001\u0015k\u0011\u0015i3\u00011\u0001/\u0003\u0019awn\\6vaV\tQ\u000e\r\u0002ocB\u00191EJ8\u0011\u0005A\fH\u0002\u0001\u0003\ne\u0012\t\t\u0011!A\u0003\u0002M\u00141a\u0018\u00132#\t!(\u0006\u0005\u0002\u001ek&\u0011aO\b\u0002\b\u001d>$\b.\u001b8h)\tA\u0003\u0010C\u0003.\u000b\u0001\u0007\u0011\u0010\u0005\u0002$u&\u00111\u0010\n\u0002\f\u0003\u000e$xN]*zgR,W\u000e\u0006\u0002){\")QF\u0002a\u0001}B\u00111e`\u0005\u0004\u0003\u0003!#AG\"mCN\u001c\u0018nY!di>\u00148+_:uK6\u0004&o\u001c<jI\u0016\u0014\b")
public final class CircuitBreakersRegistry
implements Extension {
    private final ExtendedActorSystem system;
    private final ConcurrentHashMap<String, CircuitBreaker> breakers;
    private final Config config;
    private final Config defaultBreakerConfig;

    public static ExtensionId<? extends Extension> lookup() {
        return CircuitBreakersRegistry$.MODULE$.lookup();
    }

    public static CircuitBreakersRegistry createExtension(ExtendedActorSystem system) {
        return CircuitBreakersRegistry$.MODULE$.createExtension(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return CircuitBreakersRegistry$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return CircuitBreakersRegistry$.MODULE$.apply(system);
    }

    private ConcurrentHashMap<String, CircuitBreaker> breakers() {
        return this.breakers;
    }

    private Config config() {
        return this.config;
    }

    private Config defaultBreakerConfig() {
        return this.defaultBreakerConfig;
    }

    private CircuitBreaker createCircuitBreaker(String id) {
        Config breakerConfig = this.config().hasPath(id) ? this.config().getConfig(id).withFallback(this.defaultBreakerConfig()) : this.defaultBreakerConfig();
        int maxFailures = breakerConfig.getInt("max-failures");
        FiniteDuration callTimeout = new package.DurationLong(package$.MODULE$.DurationLong(breakerConfig.getDuration("call-timeout", TimeUnit.MILLISECONDS))).millis();
        FiniteDuration resetTimeout = new package.DurationLong(package$.MODULE$.DurationLong(breakerConfig.getDuration("reset-timeout", TimeUnit.MILLISECONDS))).millis();
        FiniteDuration maxResetTimeout = new package.DurationLong(package$.MODULE$.DurationLong(breakerConfig.getDuration("max-reset-timeout", TimeUnit.MILLISECONDS))).millis();
        double exponentialBackoffFactor = breakerConfig.getDouble("exponential-backoff");
        double randomFactor = breakerConfig.getDouble("random-factor");
        Set<String> allowExceptions = package$JavaConverters$.MODULE$.ListHasAsScala(breakerConfig.getStringList("exception-allowlist")).asScala().toSet();
        CircuitBreakerTelemetry telemetry = CircuitBreakerTelemetryProvider$.MODULE$.start(id, this.system);
        return new CircuitBreaker(this.system.scheduler(), maxFailures, callTimeout, resetTimeout, maxResetTimeout, exponentialBackoffFactor, randomFactor, allowExceptions, telemetry, this.system.dispatcher());
    }

    public CircuitBreaker get(String id2) {
        return this.breakers().computeIfAbsent(id2, id -> this.createCircuitBreaker((String)id));
    }

    public CircuitBreakersRegistry(ExtendedActorSystem system) {
        this.system = system;
        this.breakers = new ConcurrentHashMap();
        this.config = system.settings().config().getConfig("akka.circuit-breaker");
        this.defaultBreakerConfig = this.config().getConfig("default");
    }
}

