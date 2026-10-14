/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LoggingAdapter;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;

public final class NoLogging$
implements LoggingAdapter {
    public static final NoLogging$ MODULE$ = new NoLogging$();

    static {
        LoggingAdapter.$init$(MODULE$);
    }

    @Override
    public Map<String, Object> mdc() {
        return LoggingAdapter.mdc$(this);
    }

    @Override
    public void error(Throwable cause, String message) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, message);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, template, arg1);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1, Object arg2) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, template, arg1, arg2);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, template, arg1, arg2, arg3);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.error$(this, cause, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void error(String message) {
        LoggingAdapter.error$(this, message);
    }

    @Override
    public void error(String template, Object arg1) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1);
    }

    @Override
    public void error(String template, Object arg1, Object arg2) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1, arg2);
    }

    @Override
    public void error(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1, arg2, arg3);
    }

    @Override
    public void error(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void warning(Throwable cause, String message) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, message);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, template, arg1);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1, Object arg2) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, template, arg1, arg2);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, template, arg1, arg2, arg3);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.warning$(this, cause, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void warning(String message) {
        LoggingAdapter.warning$(this, message);
    }

    @Override
    public void warning(String template, Object arg1) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1);
    }

    @Override
    public void warning(String template, Object arg1, Object arg2) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1, arg2);
    }

    @Override
    public void warning(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1, arg2, arg3);
    }

    @Override
    public void warning(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void info(String message) {
        LoggingAdapter.info$(this, message);
    }

    @Override
    public void info(String template, Object arg1) {
        LoggingAdapter.info$(this, template, arg1);
    }

    @Override
    public void info(String template, Object arg1, Object arg2) {
        LoggingAdapter.info$(this, template, arg1, arg2);
    }

    @Override
    public void info(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.info$(this, template, arg1, arg2, arg3);
    }

    @Override
    public void info(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.info$(this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void debug(String message) {
        LoggingAdapter.debug$(this, message);
    }

    @Override
    public void debug(String template, Object arg1) {
        LoggingAdapter.debug$(this, template, arg1);
    }

    @Override
    public void debug(String template, Object arg1, Object arg2) {
        LoggingAdapter.debug$(this, template, arg1, arg2);
    }

    @Override
    public void debug(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.debug$(this, template, arg1, arg2, arg3);
    }

    @Override
    public void debug(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.debug$(this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void log(int level, String message) {
        LoggingAdapter.log$(this, level, message);
    }

    @Override
    public void log(int level, String template, Object arg1) {
        LoggingAdapter.log$(this, level, template, arg1);
    }

    @Override
    public void log(int level, String template, Object arg1, Object arg2) {
        LoggingAdapter.log$(this, level, template, arg1, arg2);
    }

    @Override
    public void log(int level, String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.log$(this, level, template, arg1, arg2, arg3);
    }

    @Override
    public void log(int level, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.log$(this, level, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public final boolean isEnabled(int level) {
        return LoggingAdapter.isEnabled$(this, level);
    }

    @Override
    public final void notifyLog(int level, String message) {
        LoggingAdapter.notifyLog$(this, level, message);
    }

    @Override
    public String format(String t2, Seq<Object> arg) {
        return LoggingAdapter.format$(this, t2, arg);
    }

    public NoLogging$ getInstance() {
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

    private NoLogging$() {
    }
}

