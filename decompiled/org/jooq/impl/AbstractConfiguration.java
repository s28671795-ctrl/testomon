/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.Supplier;
import org.jooq.Configuration;
import org.jooq.DiagnosticsListener;
import org.jooq.DiagnosticsListenerProvider;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.MigrationListener;
import org.jooq.MigrationListenerProvider;
import org.jooq.ParseListener;
import org.jooq.ParseListenerProvider;
import org.jooq.Pro;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DefaultDiagnosticsListenerProvider;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultMigrationListenerProvider;
import org.jooq.impl.DefaultParseListenerProvider;
import org.jooq.impl.DefaultRecordListenerProvider;
import org.jooq.impl.DefaultTransactionListenerProvider;
import org.jooq.impl.DefaultVisitListenerProvider;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;

public abstract class AbstractConfiguration
implements Configuration {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractConfiguration.class);

    @Override
    public final Configuration set(RecordListener ... newRecordListeners) {
        return this.set(DefaultRecordListenerProvider.providers(newRecordListeners));
    }

    @Override
    public final Configuration setAppending(RecordListener ... newRecordListeners) {
        return this.setAppending(DefaultRecordListenerProvider.providers(newRecordListeners));
    }

    @Override
    public final Configuration setAppending(RecordListenerProvider ... newRecordListenerProviders) {
        return this.set(Tools.combine(this.recordListenerProviders(), newRecordListenerProviders));
    }

    @Override
    public final Configuration set(ExecuteListener ... newExecuteListeners) {
        return this.set(DefaultExecuteListenerProvider.providers(newExecuteListeners));
    }

    @Override
    public final Configuration setAppending(ExecuteListener ... newExecuteListeners) {
        return this.setAppending(DefaultExecuteListenerProvider.providers(newExecuteListeners));
    }

    @Override
    public final Configuration setAppending(ExecuteListenerProvider ... newExecuteListenerProviders) {
        return this.set(Tools.combine(this.executeListenerProviders(), newExecuteListenerProviders));
    }

    @Override
    public final Configuration set(MigrationListener ... newMigrationListeners) {
        return this.set(DefaultMigrationListenerProvider.providers(newMigrationListeners));
    }

    @Override
    public final Configuration setAppending(MigrationListener ... newMigrationListeners) {
        return this.setAppending(DefaultMigrationListenerProvider.providers(newMigrationListeners));
    }

    @Override
    public final Configuration setAppending(MigrationListenerProvider ... newMigrationListenerProviders) {
        return this.set(Tools.combine(this.migrationListenerProviders(), newMigrationListenerProviders));
    }

    @Override
    public final Configuration set(VisitListener ... newVisitListeners) {
        return this.set(DefaultVisitListenerProvider.providers(newVisitListeners));
    }

    @Override
    public final Configuration setAppending(VisitListener ... newVisitListeners) {
        return this.setAppending(DefaultVisitListenerProvider.providers(newVisitListeners));
    }

    @Override
    public final Configuration setAppending(VisitListenerProvider ... newVisitListenerProviders) {
        return this.set(Tools.combine(this.visitListenerProviders(), newVisitListenerProviders));
    }

    @Override
    public final Configuration set(TransactionListener ... newTransactionListeners) {
        return this.set(DefaultTransactionListenerProvider.providers(newTransactionListeners));
    }

    @Override
    public final Configuration setAppending(TransactionListener ... newTransactionListeners) {
        return this.setAppending(DefaultTransactionListenerProvider.providers(newTransactionListeners));
    }

    @Override
    public final Configuration setAppending(TransactionListenerProvider ... newTransactionListenerProviders) {
        return this.set(Tools.combine(this.transactionListenerProviders(), newTransactionListenerProviders));
    }

    @Override
    public final Configuration set(DiagnosticsListener ... newDiagnosticsListeners) {
        return this.set(DefaultDiagnosticsListenerProvider.providers(newDiagnosticsListeners));
    }

    @Override
    public final Configuration setAppending(DiagnosticsListener ... newDiagnosticsListeners) {
        return this.setAppending(DefaultDiagnosticsListenerProvider.providers(newDiagnosticsListeners));
    }

    @Override
    public final Configuration setAppending(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        return this.set(Tools.combine(this.diagnosticsListenerProviders(), newDiagnosticsListenerProviders));
    }

    @Override
    @Pro
    public final Configuration set(ParseListener ... newParseListeners) {
        return this.set(DefaultParseListenerProvider.providers(newParseListeners));
    }

    @Override
    @Pro
    public final Configuration setAppending(ParseListener ... newParseListeners) {
        return this.setAppending(DefaultParseListenerProvider.providers(newParseListeners));
    }

    @Override
    @Pro
    public final Configuration setAppending(ParseListenerProvider ... newParseListenerProviders) {
        return this.set(Tools.combine(this.parseListenerProviders(), newParseListenerProviders));
    }

    @Override
    public final Configuration derive(RecordListener ... newRecordListeners) {
        return this.derive(DefaultRecordListenerProvider.providers(newRecordListeners));
    }

    @Override
    public final Configuration deriveAppending(RecordListener ... newRecordListeners) {
        return this.deriveAppending(DefaultRecordListenerProvider.providers(newRecordListeners));
    }

    @Override
    public final Configuration deriveAppending(RecordListenerProvider ... newRecordListenerProviders) {
        return this.derive(Tools.combine(this.recordListenerProviders(), newRecordListenerProviders));
    }

    @Override
    public final Configuration derive(ExecuteListener ... newExecuteListeners) {
        return this.derive(DefaultExecuteListenerProvider.providers(newExecuteListeners));
    }

    @Override
    public final Configuration deriveAppending(ExecuteListener ... newExecuteListeners) {
        return this.deriveAppending(DefaultExecuteListenerProvider.providers(newExecuteListeners));
    }

    @Override
    public final Configuration deriveAppending(ExecuteListenerProvider ... newExecuteListenerProviders) {
        return this.derive(Tools.combine(this.executeListenerProviders(), newExecuteListenerProviders));
    }

    @Override
    public final Configuration derive(MigrationListener ... newMigrationListeners) {
        return this.derive(DefaultMigrationListenerProvider.providers(newMigrationListeners));
    }

    @Override
    public final Configuration deriveAppending(MigrationListener ... newMigrationListeners) {
        return this.deriveAppending(DefaultMigrationListenerProvider.providers(newMigrationListeners));
    }

    @Override
    public final Configuration deriveAppending(MigrationListenerProvider ... newMigrationListenerProviders) {
        return this.derive(Tools.combine(this.migrationListenerProviders(), newMigrationListenerProviders));
    }

    @Override
    public final Configuration derive(VisitListener ... newVisitListeners) {
        return this.derive(DefaultVisitListenerProvider.providers(newVisitListeners));
    }

    @Override
    public final Configuration deriveAppending(VisitListener ... newVisitListeners) {
        return this.deriveAppending(DefaultVisitListenerProvider.providers(newVisitListeners));
    }

    @Override
    public final Configuration deriveAppending(VisitListenerProvider ... newVisitListenerProviders) {
        return this.derive(Tools.combine(this.visitListenerProviders(), newVisitListenerProviders));
    }

    @Override
    public final Configuration derive(TransactionListener ... newTransactionListeners) {
        return this.derive(DefaultTransactionListenerProvider.providers(newTransactionListeners));
    }

    @Override
    public final Configuration deriveAppending(TransactionListener ... newTransactionListeners) {
        return this.deriveAppending(DefaultTransactionListenerProvider.providers(newTransactionListeners));
    }

    @Override
    public final Configuration deriveAppending(TransactionListenerProvider ... newTransactionListenerProviders) {
        return this.derive(Tools.combine(this.transactionListenerProviders(), newTransactionListenerProviders));
    }

    @Override
    public final Configuration derive(DiagnosticsListener ... newDiagnosticsListeners) {
        return this.derive(DefaultDiagnosticsListenerProvider.providers(newDiagnosticsListeners));
    }

    @Override
    public final Configuration deriveAppending(DiagnosticsListener ... newDiagnosticsListeners) {
        return this.deriveAppending(DefaultDiagnosticsListenerProvider.providers(newDiagnosticsListeners));
    }

    @Override
    public final Configuration deriveAppending(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        return this.derive(Tools.combine(this.diagnosticsListenerProviders(), newDiagnosticsListenerProviders));
    }

    @Override
    @Pro
    public final Configuration derive(ParseListener ... newParseListeners) {
        return this.derive(DefaultParseListenerProvider.providers(newParseListeners));
    }

    @Override
    @Pro
    public final Configuration deriveAppending(ParseListener ... newParseListeners) {
        return this.deriveAppending(DefaultParseListenerProvider.providers(newParseListeners));
    }

    @Override
    @Pro
    public final Configuration deriveAppending(ParseListenerProvider ... newParseListenerProviders) {
        return this.derive(Tools.combine(this.parseListenerProviders(), newParseListenerProviders));
    }

    @Override
    public boolean commercial(Supplier<String> logMessage) {
        if (this.commercial()) {
            return true;
        }
        log.warn(logMessage.get());
        return false;
    }

    @Override
    public boolean requireCommercial(Supplier<String> logMessage) throws DataAccessException {
        if (this.commercial()) {
            return true;
        }
        throw new DataAccessException(logMessage.get());
    }
}

