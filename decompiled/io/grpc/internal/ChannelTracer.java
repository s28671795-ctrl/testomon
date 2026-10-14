/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import io.grpc.ChannelLogger;
import io.grpc.InternalChannelz;
import io.grpc.InternalLogId;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class ChannelTracer {
    static final Logger logger = Logger.getLogger(ChannelLogger.class.getName());
    private final Object lock = new Object();
    private final InternalLogId logId;
    @Nullable
    @GuardedBy(value="lock")
    private final Collection<InternalChannelz.ChannelTrace.Event> events;
    private final long channelCreationTimeNanos;
    @GuardedBy(value="lock")
    private int eventsLogged;

    ChannelTracer(InternalLogId logId, final int maxEvents, long channelCreationTimeNanos, String description) {
        Preconditions.checkNotNull(description, "description");
        this.logId = Preconditions.checkNotNull(logId, "logId");
        this.events = maxEvents > 0 ? new ArrayDeque<InternalChannelz.ChannelTrace.Event>(){

            @Override
            @GuardedBy(value="lock")
            public boolean add(InternalChannelz.ChannelTrace.Event event) {
                if (this.size() == maxEvents) {
                    this.removeFirst();
                }
                ChannelTracer.this.eventsLogged++;
                return super.add(event);
            }
        } : null;
        this.channelCreationTimeNanos = channelCreationTimeNanos;
        this.reportEvent(new InternalChannelz.ChannelTrace.Event.Builder().setDescription(description + " created").setSeverity(InternalChannelz.ChannelTrace.Event.Severity.CT_INFO).setTimestampNanos(channelCreationTimeNanos).build());
    }

    void reportEvent(InternalChannelz.ChannelTrace.Event event) {
        Level logLevel;
        switch (event.severity) {
            case CT_ERROR: {
                logLevel = Level.FINE;
                break;
            }
            case CT_WARNING: {
                logLevel = Level.FINER;
                break;
            }
            default: {
                logLevel = Level.FINEST;
            }
        }
        this.traceOnly(event);
        ChannelTracer.logOnly(this.logId, logLevel, event.description);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean isTraceEnabled() {
        Object object = this.lock;
        synchronized (object) {
            return this.events != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void traceOnly(InternalChannelz.ChannelTrace.Event event) {
        Object object = this.lock;
        synchronized (object) {
            if (this.events != null) {
                this.events.add(event);
            }
        }
    }

    static void logOnly(InternalLogId logId, Level logLevel, String msg) {
        if (logger.isLoggable(logLevel)) {
            LogRecord lr = new LogRecord(logLevel, "[" + logId + "] " + msg);
            lr.setLoggerName(logger.getName());
            lr.setSourceClassName(logger.getName());
            lr.setSourceMethodName("log");
            logger.log(lr);
        }
    }

    InternalLogId getLogId() {
        return this.logId;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void updateBuilder(InternalChannelz.ChannelStats.Builder builder) {
        ArrayList<InternalChannelz.ChannelTrace.Event> eventsSnapshot;
        int eventsLoggedSnapshot;
        Object object = this.lock;
        synchronized (object) {
            if (this.events == null) {
                return;
            }
            eventsLoggedSnapshot = this.eventsLogged;
            eventsSnapshot = new ArrayList<InternalChannelz.ChannelTrace.Event>(this.events);
        }
        builder.setChannelTrace(new InternalChannelz.ChannelTrace.Builder().setNumEventsLogged(eventsLoggedSnapshot).setCreationTimeNanos(this.channelCreationTimeNanos).setEvents(eventsSnapshot).build());
    }
}

