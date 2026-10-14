/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LogMarker;
import akka.event.MarkerLoggingAdapter;

public final class NoMarkerLogging$
extends MarkerLoggingAdapter {
    public static final NoMarkerLogging$ MODULE$ = new NoMarkerLogging$();

    public NoMarkerLogging$ getInstance() {
        return this;
    }

    @Override
    public final boolean isErrorEnabled() {
        return false;
    }

    @Override
    public final boolean isWarningEnabled() {
        return false;
    }

    @Override
    public final boolean isInfoEnabled() {
        return false;
    }

    @Override
    public final boolean isDebugEnabled() {
        return false;
    }

    @Override
    public final void notifyError(String message) {
    }

    @Override
    public final void notifyError(Throwable cause, String message) {
    }

    @Override
    public final void notifyWarning(String message) {
    }

    @Override
    public final void notifyWarning(Throwable cause, String message) {
    }

    @Override
    public final void notifyInfo(String message) {
    }

    @Override
    public final void notifyDebug(String message) {
    }

    @Override
    public final void error(LogMarker marker, Throwable cause, String message) {
    }

    @Override
    public final void error(LogMarker marker, Throwable cause, String template, Object arg1) {
    }

    @Override
    public final void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2) {
    }

    @Override
    public final void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
    }

    @Override
    public final void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
    }

    @Override
    public final void error(LogMarker marker, String message) {
    }

    @Override
    public final void error(LogMarker marker, String template, Object arg1) {
    }

    @Override
    public final void error(LogMarker marker, String template, Object arg1, Object arg2) {
    }

    @Override
    public final void error(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
    }

    @Override
    public final void error(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
    }

    @Override
    public final void warning(LogMarker marker, String message) {
    }

    @Override
    public final void warning(LogMarker marker, String template, Object arg1) {
    }

    @Override
    public final void warning(LogMarker marker, String template, Object arg1, Object arg2) {
    }

    @Override
    public final void warning(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
    }

    @Override
    public final void warning(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
    }

    @Override
    public final void info(LogMarker marker, String message) {
    }

    @Override
    public final void info(LogMarker marker, String template, Object arg1, Object arg2) {
    }

    @Override
    public final void info(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
    }

    @Override
    public final void info(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
    }

    @Override
    public final void debug(LogMarker marker, String message) {
    }

    @Override
    public final void debug(LogMarker marker, String template, Object arg1) {
    }

    @Override
    public final void debug(LogMarker marker, String template, Object arg1, Object arg2) {
    }

    @Override
    public final void debug(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
    }

    @Override
    public final void debug(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
    }

    private NoMarkerLogging$() {
        super(null, "source", String.class, null);
    }
}

