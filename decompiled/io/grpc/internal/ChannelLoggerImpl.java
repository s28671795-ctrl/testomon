/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.ChannelLogger;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import io.grpc.internal.ChannelTracer;
import io.grpc.internal.TimeProvider;
import java.text.MessageFormat;
import java.util.logging.Level;

final class ChannelLoggerImpl
extends ChannelLogger {
    private final ChannelTracer tracer;
    private final TimeProvider time;

    ChannelLoggerImpl(ChannelTracer tracer, TimeProvider time) {
        this.tracer = Preconditions.checkNotNull(tracer, "tracer");
        this.time = Preconditions.checkNotNull(time, "time");
    }

    @Override
    public void log(ChannelLogger.ChannelLogLevel level, String msg) {
        ChannelLoggerImpl.logOnly(this.tracer.getLogId(), level, msg);
        if (this.isTraceable(level)) {
            this.trace(level, msg);
        }
    }

    @Override
    public void log(ChannelLogger.ChannelLogLevel level, String messageFormat, Object ... args2) {
        String msg = null;
        Level javaLogLevel = ChannelLoggerImpl.toJavaLogLevel(level);
        if (this.isTraceable(level) || ChannelTracer.logger.isLoggable(javaLogLevel)) {
            msg = MessageFormat.format(messageFormat, args2);
        }
        this.log(level, msg);
    }

    static void logOnly(InternalLogId logId, ChannelLogger.ChannelLogLevel level, String msg) {
        Level javaLogLevel = ChannelLoggerImpl.toJavaLogLevel(level);
        if (ChannelTracer.logger.isLoggable(javaLogLevel)) {
            ChannelTracer.logOnly(logId, javaLogLevel, msg);
        }
    }

    static void logOnly(InternalLogId logId, ChannelLogger.ChannelLogLevel level, String messageFormat, Object ... args2) {
        Level javaLogLevel = ChannelLoggerImpl.toJavaLogLevel(level);
        if (ChannelTracer.logger.isLoggable(javaLogLevel)) {
            String msg = MessageFormat.format(messageFormat, args2);
            ChannelTracer.logOnly(logId, javaLogLevel, msg);
        }
    }

    private boolean isTraceable(ChannelLogger.ChannelLogLevel level) {
        return level != ChannelLogger.ChannelLogLevel.DEBUG && this.tracer.isTraceEnabled();
    }

    private void trace(ChannelLogger.ChannelLogLevel level, String msg) {
        if (level == ChannelLogger.ChannelLogLevel.DEBUG) {
            return;
        }
        this.tracer.traceOnly(new InternalChannelz.ChannelTrace.Event.Builder().setDescription(msg).setSeverity(ChannelLoggerImpl.toTracerSeverity(level)).setTimestampNanos(this.time.currentTimeNanos()).build());
    }

    private static InternalChannelz.ChannelTrace.Event.Severity toTracerSeverity(ChannelLogger.ChannelLogLevel level) {
        switch (level) {
            case ERROR: {
                return InternalChannelz.ChannelTrace.Event.Severity.CT_ERROR;
            }
            case WARNING: {
                return InternalChannelz.ChannelTrace.Event.Severity.CT_WARNING;
            }
        }
        return InternalChannelz.ChannelTrace.Event.Severity.CT_INFO;
    }

    private static Level toJavaLogLevel(ChannelLogger.ChannelLogLevel level) {
        switch (level) {
            case ERROR: 
            case WARNING: {
                return Level.FINE;
            }
            case INFO: {
                return Level.FINER;
            }
        }
        return Level.FINEST;
    }
}

