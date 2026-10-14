/*
 * Decompiled with CFR 0.152.
 */
package akka.event.jul;

import akka.event.DummyClassForStringSources;
import akka.event.Logging$;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Logger$ {
    public static final Logger$ MODULE$ = new Logger$();

    public Logger apply(String logger2) {
        return Logger.getLogger(logger2);
    }

    public Logger apply(Class<?> logClass, String logSource) {
        Class<?> clazz;
        Class<?> clazz2 = clazz = logClass;
        Class<DummyClassForStringSources> clazz3 = DummyClassForStringSources.class;
        if (!(clazz2 != null ? !clazz2.equals(clazz3) : clazz3 != null)) {
            return this.apply(logSource);
        }
        return Logger.getLogger(logClass.getName());
    }

    public Logger root() {
        return Logger.getGlobal();
    }

    public Level mapLevel(int level) {
        int n = level;
        if (Logging$.MODULE$.InfoLevel() == n) {
            return Level.INFO;
        }
        if (Logging$.MODULE$.DebugLevel() == n) {
            return Level.CONFIG;
        }
        if (Logging$.MODULE$.WarningLevel() == n) {
            return Level.WARNING;
        }
        if (Logging$.MODULE$.ErrorLevel() == n) {
            return Level.SEVERE;
        }
        return Level.FINE;
    }

    private Logger$() {
    }
}

