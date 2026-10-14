/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Predicate;
import org.jooq.Configuration;
import org.jooq.DiagnosticsContext;
import org.jooq.DiagnosticsListener;
import org.jooq.DiagnosticsListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDiagnosticsListenerProvider;
import org.jooq.impl.LoggingDiagnosticsListener;
import org.jooq.impl.Tools;

final class DiagnosticsListeners
implements DiagnosticsListener {
    final DiagnosticsListener[] listeners;

    DiagnosticsListeners(DiagnosticsListenerProvider[] providers) {
        this.listeners = Tools.map(providers, p -> p.provide(), DiagnosticsListener[]::new);
    }

    static final DiagnosticsListeners get(Configuration configuration) {
        DiagnosticsListenerProvider[] p = configuration.diagnosticsListenerProviders();
        if (!Boolean.FALSE.equals(configuration.settings().isDiagnosticsLogging())) {
            p = Tools.combine(DefaultDiagnosticsListenerProvider.providers(new LoggingDiagnosticsListener()), p);
        }
        return new DiagnosticsListeners(p);
    }

    private static final boolean check(DiagnosticsContext ctx, Predicate<? super Settings> test) {
        return DiagnosticsListeners.check(ctx.settings(), test);
    }

    static final boolean check(Settings settings, Predicate<? super Settings> test) {
        return !Boolean.FALSE.equals(test.test(settings));
    }

    private static final boolean checkPattern(DiagnosticsContext ctx, Predicate<? super Settings> test) {
        return DiagnosticsListeners.checkPattern(ctx.settings(), test);
    }

    static final boolean checkPattern(Settings settings, Predicate<? super Settings> test) {
        return !Boolean.FALSE.equals(settings.isDiagnosticsPatterns()) && DiagnosticsListeners.check(settings, test);
    }

    @Override
    public final void tooManyRowsFetched(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsTooManyRowsFetched)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.tooManyRowsFetched(ctx);
            }
        }
    }

    @Override
    public final void tooManyColumnsFetched(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsTooManyColumnsFetched)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.tooManyColumnsFetched(ctx);
            }
        }
    }

    @Override
    public final void unnecessaryWasNullCall(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsUnnecessaryWasNullCall)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.unnecessaryWasNullCall(ctx);
            }
        }
    }

    @Override
    public final void missingWasNullCall(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsMissingWasNullCall)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.missingWasNullCall(ctx);
            }
        }
    }

    @Override
    public final void duplicateStatements(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsDuplicateStatements)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.duplicateStatements(ctx);
            }
        }
    }

    @Override
    public final void repeatedStatements(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsRepeatedStatements)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.repeatedStatements(ctx);
            }
        }
    }

    @Override
    public final void consecutiveAggregation(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsConsecutiveAggregation)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.consecutiveAggregation(ctx);
            }
        }
    }

    @Override
    public final void concatenationInPredicate(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsConcatenationInPredicate)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.concatenationInPredicate(ctx);
            }
        }
    }

    @Override
    public final void possiblyWrongExpression(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsPossiblyWrongExpression)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.possiblyWrongExpression(ctx);
            }
        }
    }

    @Override
    public final void trivialCondition(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsTrivialCondition)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.trivialCondition(ctx);
            }
        }
    }

    @Override
    public final void transformPattern(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsPatterns)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.transformPattern(ctx);
            }
        }
    }

    @Override
    public final void nullCondition(DiagnosticsContext ctx) {
        if (DiagnosticsListeners.check(ctx, Settings::isDiagnosticsNullCondition)) {
            for (DiagnosticsListener listener : this.listeners) {
                listener.nullCondition(ctx);
            }
        }
    }

    @Override
    public final void exception(DiagnosticsContext ctx) {
        for (DiagnosticsListener listener : this.listeners) {
            listener.exception(ctx);
        }
    }
}

