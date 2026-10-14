/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LoggingFilter;
import akka.event.LoggingFilterWithMarker;
import akka.event.LoggingFilterWithMarkerWrapper;

public final class LoggingFilterWithMarker$ {
    public static final LoggingFilterWithMarker$ MODULE$ = new LoggingFilterWithMarker$();

    public LoggingFilterWithMarker wrap(LoggingFilter loggingFilter) {
        LoggingFilter loggingFilter2 = loggingFilter;
        if (loggingFilter2 instanceof LoggingFilterWithMarker) {
            LoggingFilterWithMarker loggingFilterWithMarker = (LoggingFilterWithMarker)loggingFilter2;
            return loggingFilterWithMarker;
        }
        return new LoggingFilterWithMarkerWrapper(loggingFilter);
    }

    private LoggingFilterWithMarker$() {
    }
}

