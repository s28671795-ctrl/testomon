/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.stream.Collectors;
import org.jooq.DiagnosticsContext;
import org.jooq.DiagnosticsListener;
import org.jooq.tools.JooqLogger;

public class LoggingDiagnosticsListener
implements DiagnosticsListener {
    private static final JooqLogger log = JooqLogger.getLogger(LoggingDiagnosticsListener.class);

    private void log(String text, DiagnosticsContext ctx) {
        this.log(text, ctx, null);
    }

    private void log(String text, DiagnosticsContext ctx, String additionalContext) {
        if (log.isInfoEnabled()) {
            if (additionalContext != null) {
                text = (String)text + "\n" + additionalContext;
            }
            text = ctx.actualStatement().equals(ctx.normalisedStatement()) ? (String)text + "\nStatement: " + ctx.actualStatement() : (String)text + "\nActual statement    : " + ctx.actualStatement() + "\nNormalised statement: " + ctx.normalisedStatement();
            log.info((Object)"Diagnostics", text);
        }
    }

    @Override
    public void duplicateStatements(DiagnosticsContext ctx) {
        this.log("Duplicate statements were encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-duplicate-statements/\n", ctx, "Recent statements include: " + String.valueOf(ctx.duplicateStatements().stream().limit(5L).map(s2 -> "\n  " + s2).collect(Collectors.toList())));
    }

    @Override
    public void repeatedStatements(DiagnosticsContext ctx) {
        this.log("Repeated statements were encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-repeated-statements/\n", ctx, "Recent statements include: " + String.valueOf(ctx.repeatedStatements().stream().limit(5L).map(s2 -> "\n  " + s2).collect(Collectors.toList())));
    }

    @Override
    public void tooManyColumnsFetched(DiagnosticsContext ctx) {
        this.log("Too many columns were fetched and never read. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-too-many-columns/\n", ctx, "Fetched columns : " + String.valueOf(ctx.resultSetFetchedColumnNames()) + "\nConsumed columns: " + String.valueOf(ctx.resultSetConsumedColumnNames()));
    }

    @Override
    public void tooManyRowsFetched(DiagnosticsContext ctx) {
        this.log("Too many rows were fetched and never read. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-too-many-rows/\n", ctx, "Fetched rows : " + ctx.resultSetFetchedRows() + "\nConsumed rows: " + ctx.resultSetConsumedRows());
    }

    @Override
    public void consecutiveAggregation(DiagnosticsContext ctx) {
        this.log("Consecutive aggregation was encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-consecutive-aggregation/\n", ctx, "Recent statements include: " + String.valueOf(ctx.repeatedStatements().stream().limit(5L).map(s2 -> "\n  " + s2).collect(Collectors.toList())));
    }

    @Override
    public void concatenationInPredicate(DiagnosticsContext ctx) {
        this.log("Concatenation in predicates was encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-concatenation-in-predicates/\n", ctx);
    }

    @Override
    public void possiblyWrongExpression(DiagnosticsContext ctx) {
        this.log("A possibly wrong expression was encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-possibly-wrong-expressions/\n", ctx);
    }

    @Override
    public void trivialCondition(DiagnosticsContext ctx) {
        this.log("A trivial condition was encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-trivial-condition/\n", ctx);
    }

    @Override
    public void nullCondition(DiagnosticsContext ctx) {
        this.log("A null condition was encountered. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-null-condition/\n", ctx);
    }

    @Override
    public void transformPattern(DiagnosticsContext ctx) {
        this.log("Pattern transformation was applied. Why is it bad? See: https://www.jooq.org/doc/latest/manual/sql-execution/diagnostics/diagnostics-transform-patterns/\n", ctx);
    }
}

