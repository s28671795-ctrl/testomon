/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.DiagnosticsContext;
import org.jooq.Pro;

public interface DiagnosticsListener {
    default public void tooManyRowsFetched(DiagnosticsContext ctx) {
    }

    default public void tooManyColumnsFetched(DiagnosticsContext ctx) {
    }

    default public void unnecessaryWasNullCall(DiagnosticsContext ctx) {
    }

    default public void missingWasNullCall(DiagnosticsContext ctx) {
    }

    default public void duplicateStatements(DiagnosticsContext ctx) {
    }

    default public void repeatedStatements(DiagnosticsContext ctx) {
    }

    @Pro
    default public void consecutiveAggregation(DiagnosticsContext ctx) {
    }

    @Pro
    default public void concatenationInPredicate(DiagnosticsContext ctx) {
    }

    @Pro
    default public void possiblyWrongExpression(DiagnosticsContext ctx) {
    }

    @Pro
    default public void trivialCondition(DiagnosticsContext ctx) {
    }

    @Pro
    default public void transformPattern(DiagnosticsContext ctx) {
    }

    @Pro
    default public void nullCondition(DiagnosticsContext ctx) {
    }

    default public void exception(DiagnosticsContext ctx) {
    }
}

