/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LogMarker;
import akka.util.ccompat.package$JavaConverters$;
import java.util.Map;
import scala.$less$colon$less$;
import scala.Predef$;

public final class LogMarker$ {
    public static final LogMarker$ MODULE$ = new LogMarker$();
    private static final LogMarker Security = MODULE$.apply("SECURITY");

    public final String MDCKey() {
        return "marker";
    }

    public LogMarker apply(String name) {
        return new LogMarker(name, (scala.collection.immutable.Map<String, Object>)Predef$.MODULE$.Map().empty());
    }

    public LogMarker apply(String name, scala.collection.immutable.Map<String, Object> properties) {
        return new LogMarker(name, properties);
    }

    public LogMarker create(String name) {
        return this.apply(name);
    }

    public LogMarker create(String name, Map<String, Object> properties) {
        return this.apply(name, package$JavaConverters$.MODULE$.MapHasAsScala(properties).asScala().toMap($less$colon$less$.MODULE$.refl()));
    }

    public final LogMarker Security() {
        return Security;
    }

    private LogMarker$() {
    }
}

