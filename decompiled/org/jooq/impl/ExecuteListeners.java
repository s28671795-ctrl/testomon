/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.conf.InvocationOrder;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.FetchServerOutputListener;
import org.jooq.impl.SQLExceptionLoggerListener;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.LoggerListener;

final class ExecuteListeners
implements ExecuteListener {
    private static final ExecuteListener EMPTY_LISTENER = new DefaultExecuteListener();
    private static final JooqLogger LOGGER_LISTENER_LOGGER = JooqLogger.getLogger(LoggerListener.class);
    private static final JooqLogger SQL_EXCEPTION_LOGGER = JooqLogger.getLogger(SQLExceptionLoggerListener.class);
    private final ExecuteListener[][] listeners;
    private boolean resultStart;
    private boolean fetchEnd;

    static ExecuteListener get(ExecuteContext ctx) {
        ExecuteListener[][] listeners = ExecuteListeners.listeners(ctx);
        if (listeners == null) {
            return EMPTY_LISTENER;
        }
        return new ExecuteListeners(listeners);
    }

    static ExecuteListener getAndStart(ExecuteContext ctx) {
        ExecuteListener result = ExecuteListeners.get(ctx);
        result.start(ctx);
        return result;
    }

    private static final ExecuteListener[][] listeners(ExecuteContext ctx) {
        ExecuteListener[] executeListenerArray;
        List<ExecuteListener> list = null;
        if (SettingsTools.getFetchServerOutputSize(0, ctx.settings()) > 0) {
            list = ExecuteListeners.init(list);
            list.add(new FetchServerOutputListener());
        }
        if (!Boolean.FALSE.equals(ctx.settings().isExecuteLogging()) && LOGGER_LISTENER_LOGGER.isDebugEnabled()) {
            list = ExecuteListeners.init(list);
            list.add(new LoggerListener());
        }
        if (!Boolean.FALSE.equals(ctx.settings().isExecuteLoggingSQLExceptions()) && SQL_EXCEPTION_LOGGER.isInfoEnabled()) {
            list = ExecuteListeners.init(list);
            list.add(new SQLExceptionLoggerListener());
        }
        for (ExecuteListenerProvider provider : ctx.configuration().executeListenerProviders()) {
            if (provider == null) continue;
            list = ExecuteListeners.init(list);
            list.add(provider.provide());
        }
        if (list == null) {
            return null;
        }
        ExecuteListener[] def = list.toArray(Tools.EMPTY_EXECUTE_LISTENER);
        ExecuteListener[] rev = null;
        ExecuteListener[][] executeListenerArray2 = new ExecuteListener[2][];
        if (ctx.settings().getExecuteListenerStartInvocationOrder() != InvocationOrder.REVERSE) {
            v1 = def;
        } else {
            rev = Tools.reverse((ExecuteListener[])def.clone());
            v1 = executeListenerArray2[0] = rev;
        }
        if (ctx.settings().getExecuteListenerEndInvocationOrder() != InvocationOrder.REVERSE) {
            executeListenerArray = def;
        } else if (rev != null) {
            executeListenerArray = rev;
        } else {
            rev = Tools.reverse((ExecuteListener[])def.clone());
            executeListenerArray = rev;
        }
        executeListenerArray2[1] = executeListenerArray;
        return executeListenerArray2;
    }

    private static final List<ExecuteListener> init(List<ExecuteListener> result) {
        return result == null ? new ArrayList() : result;
    }

    private ExecuteListeners(ExecuteListener[][] listeners) {
        this.listeners = listeners;
    }

    @Override
    public final void start(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.start(ctx);
        }
    }

    @Override
    public final void transformStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.transformStart(ctx);
        }
    }

    @Override
    public final void transformEnd(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.transformEnd(ctx);
        }
    }

    @Override
    public final void renderStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.renderStart(ctx);
        }
    }

    @Override
    public final void renderEnd(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.renderEnd(ctx);
        }
    }

    @Override
    public final void prepareStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.prepareStart(ctx);
        }
    }

    @Override
    public final void prepareEnd(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.prepareEnd(ctx);
        }
    }

    @Override
    public final void bindStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.bindStart(ctx);
        }
    }

    @Override
    public final void bindEnd(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.bindEnd(ctx);
        }
    }

    @Override
    public final void executeStart(ExecuteContext ctx) {
        if (ctx instanceof DefaultExecuteContext) {
            DefaultExecuteContext d = (DefaultExecuteContext)ctx;
            d.incrementStatementExecutionCount();
        }
        for (ExecuteListener listener : this.listeners[0]) {
            listener.executeStart(ctx);
        }
    }

    @Override
    public final void executeEnd(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.executeEnd(ctx);
        }
    }

    @Override
    public final void fetchStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.fetchStart(ctx);
        }
    }

    @Override
    public final void outStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.outStart(ctx);
        }
    }

    @Override
    public final void outEnd(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.outEnd(ctx);
        }
    }

    @Override
    public final void resultStart(ExecuteContext ctx) {
        this.resultStart = true;
        for (ExecuteListener listener : this.listeners[0]) {
            listener.resultStart(ctx);
        }
        ++((DefaultExecuteContext)ctx).resultLevel;
    }

    @Override
    public final void recordStart(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.recordStart(ctx);
        }
        ++((DefaultExecuteContext)ctx).recordLevel;
    }

    @Override
    public final void recordEnd(ExecuteContext ctx) {
        --((DefaultExecuteContext)ctx).recordLevel;
        for (ExecuteListener listener : this.listeners[1]) {
            listener.recordEnd(ctx);
        }
    }

    @Override
    public final void resultEnd(ExecuteContext ctx) {
        --((DefaultExecuteContext)ctx).resultLevel;
        this.resultStart = false;
        for (ExecuteListener listener : this.listeners[1]) {
            listener.resultEnd(ctx);
        }
        if (this.fetchEnd) {
            this.fetchEnd(ctx);
        }
    }

    @Override
    public final void fetchEnd(ExecuteContext ctx) {
        if (this.resultStart) {
            this.fetchEnd = true;
        } else {
            for (ExecuteListener listener : this.listeners[1]) {
                listener.fetchEnd(ctx);
            }
        }
    }

    @Override
    public final void end(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[1]) {
            listener.end(ctx);
        }
    }

    @Override
    public final void exception(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.exception(ctx);
        }
    }

    @Override
    public final void warning(ExecuteContext ctx) {
        for (ExecuteListener listener : this.listeners[0]) {
            listener.warning(ctx);
        }
    }
}

