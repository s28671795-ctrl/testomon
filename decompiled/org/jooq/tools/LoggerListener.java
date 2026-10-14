/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteType;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Parameter;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Routine;
import org.jooq.VisitContext;
import org.jooq.VisitListener;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

public class LoggerListener
implements ExecuteListener {
    private static final JooqLogger log = JooqLogger.getLogger(LoggerListener.class);
    private static final String BUFFER = "org.jooq.tools.LoggerListener.BUFFER";
    private static final String DO_BUFFER = "org.jooq.tools.LoggerListener.DO_BUFFER";
    private static final String BATCH_SIZE = "org.jooq.tools.LoggerListener.BATCH_SIZE";
    private static final int maxLength = 2000;

    @Override
    public void renderEnd(ExecuteContext ctx) {
        if (log.isDebugEnabled()) {
            String newline;
            Configuration configuration = ctx.configuration();
            String string = newline = Boolean.TRUE.equals(configuration.settings().isRenderFormatted()) ? "\n" : "";
            if (!log.isTraceEnabled()) {
                configuration = configuration.deriveAppending(new BindValueAbbreviator());
            }
            String[] batchSQL = ctx.batchSQL();
            if (ctx.query() != null) {
                log.debug((Object)"Executing query", newline + ctx.sql());
                String inlined2 = DSL.using(configuration).renderInlined(ctx.query());
                if (!ctx.sql().equals(inlined2)) {
                    log.debug((Object)"-> with bind values", newline + inlined2);
                }
            } else if (ctx.routine() != null) {
                log.debug((Object)"Calling routine", newline + ctx.sql());
                String inlined3 = DSL.using(configuration).renderInlined(ctx.routine());
                if (!ctx.sql().equals(inlined3)) {
                    log.debug((Object)"-> with bind values", newline + inlined3);
                }
            } else if (!StringUtils.isBlank(ctx.sql())) {
                if (ctx.type() == ExecuteType.BATCH) {
                    log.debug((Object)"Executing batch query", newline + ctx.sql());
                } else {
                    log.debug((Object)"Executing query", newline + ctx.sql());
                }
            }
        }
    }

    @Override
    public void bindEnd(ExecuteContext ctx) {
        if (ctx.type() == ExecuteType.BATCH && log.isDebugEnabled()) {
            ctx.data().compute(BATCH_SIZE, (k, v) -> v == null ? 1 : (Integer)v + 1);
        }
    }

    @Override
    public void executeStart(ExecuteContext ctx) {
        if (ctx.type() == ExecuteType.BATCH && log.isDebugEnabled()) {
            log.debug((Object)"Batch size", ctx.data().getOrDefault(BATCH_SIZE, ctx.batchSQL().length));
        }
    }

    @Override
    public void recordEnd(ExecuteContext ctx) {
        if (ctx.recordLevel() > 0) {
            return;
        }
        if (log.isTraceEnabled() && ctx.record() != null) {
            this.logMultiline("Record fetched", ctx.record().toString(), Level.FINER);
        }
        if (log.isDebugEnabled() && ctx.record() != null && !Boolean.FALSE.equals(ctx.data(DO_BUFFER))) {
            Result<Record> buffer = (Result<Record>)ctx.data(BUFFER);
            if (buffer == null) {
                buffer = ctx.dsl().newResult(ctx.record().fields());
                ctx.data(BUFFER, buffer);
            }
            if (buffer.size() < this.maxRows()) {
                buffer.add(ctx.record());
            }
        }
    }

    @Override
    public void resultStart(ExecuteContext ctx) {
        ctx.data(DO_BUFFER, false);
    }

    @Override
    public void resultEnd(ExecuteContext ctx) {
        if (ctx.resultLevel() > 0) {
            return;
        }
        if (ctx.result() != null && log.isDebugEnabled()) {
            this.log(ctx.configuration(), ctx.result());
            log.debug((Object)"Fetched row(s)", ctx.result().size());
        }
    }

    @Override
    public void fetchEnd(ExecuteContext ctx) {
        Result buffer = (Result)ctx.data(BUFFER);
        if (buffer != null && !buffer.isEmpty() && log.isDebugEnabled()) {
            this.log(ctx.configuration(), buffer);
            log.debug((Object)"Fetched row(s)", buffer.size() + (buffer.size() < this.maxRows() ? "" : " (or more)"));
        }
    }

    private void log(Configuration configuration, Result<?> result) {
        this.logMultiline("Fetched result", result.format(configuration.formattingProvider().txtFormat().maxRows(this.maxRows()).maxColWidth(this.maxColWidth())), Level.FINE);
    }

    private int maxRows() {
        if (log.isTraceEnabled()) {
            return 500;
        }
        if (log.isDebugEnabled()) {
            return 5;
        }
        return 0;
    }

    private int maxColWidth() {
        if (log.isTraceEnabled()) {
            return 500;
        }
        if (log.isDebugEnabled()) {
            return 50;
        }
        return 0;
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        if (ctx.rows() >= 0 && log.isDebugEnabled()) {
            log.debug((Object)"Affected row(s)", ctx.rows());
        }
    }

    @Override
    public void outEnd(ExecuteContext ctx) {
        if (ctx.routine() != null && log.isDebugEnabled()) {
            this.logMultiline("Fetched OUT parameters", String.valueOf(StringUtils.defaultIfNull(this.record(ctx.configuration(), ctx.routine()), "N/A")), Level.FINE);
        }
    }

    @Override
    public void exception(ExecuteContext ctx) {
        if (log.isDebugEnabled()) {
            log.debug((Object)"Exception", ctx.exception());
        }
    }

    private Record record(Configuration configuration, Routine<?> routine) {
        Record result = null;
        ArrayList fields = new ArrayList(1 + routine.getOutParameters().size());
        Parameter<?> returnParam = routine.getReturnParameter();
        if (returnParam != null) {
            fields.add(DSL.field(DSL.name(returnParam.getName()), returnParam.getDataType()));
        }
        for (Parameter<?> param2 : routine.getOutParameters()) {
            fields.add(DSL.field(DSL.name(param2.getName()), param2.getDataType()));
        }
        if (fields.size() > 0) {
            result = DSL.using(configuration).newRecord(fields.toArray(new Field[0]));
            int i = 0;
            if (returnParam != null) {
                result.setValue((Field)fields.get(i++), routine.getValue(returnParam));
            }
            for (Parameter<?> param3 : routine.getOutParameters()) {
                result.setValue((Field)fields.get(i++), routine.getValue(param3));
            }
            result.changed(false);
        }
        return result;
    }

    private void logMultiline(String comment, String message, Level level) {
        for (String line : message.split("\n")) {
            if (level == Level.FINE) {
                log.debug((Object)comment, line);
            } else {
                log.trace((Object)comment, line);
            }
            comment = "";
        }
    }

    private static class BindValueAbbreviator
    implements VisitListener {
        private boolean anyAbbreviations = false;

        private BindValueAbbreviator() {
        }

        @Override
        public void visitStart(VisitContext context) {
            QueryPart part;
            if (context.renderContext() != null && (part = context.queryPart()) instanceof Param) {
                Param param2 = (Param)part;
                Object value = param2.getValue();
                if (value instanceof String && ((String)value).length() > 2000) {
                    this.anyAbbreviations = true;
                    context.queryPart(DSL.val(StringUtils.abbreviate((String)value, 2000)));
                } else if (value instanceof byte[] && ((byte[])value).length > 2000) {
                    this.anyAbbreviations = true;
                    context.queryPart(DSL.val(Arrays.copyOf((byte[])value, 2000)));
                }
            }
        }

        @Override
        public void visitEnd(VisitContext context) {
            if (this.anyAbbreviations && context.queryPartsLength() == 1) {
                context.renderContext().sql(" -- Bind values may have been abbreviated for DEBUG logging. Use TRACE logging for very large bind variables.");
            }
        }
    }
}

