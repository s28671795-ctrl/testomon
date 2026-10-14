/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.sql.DataSource;
import org.jooq.AuditProvider;
import org.jooq.CacheProvider;
import org.jooq.CharsetProvider;
import org.jooq.CommitProvider;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConverterProvider;
import org.jooq.DSLContext;
import org.jooq.DiagnosticsListener;
import org.jooq.DiagnosticsListenerProvider;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteListenerProvider;
import org.jooq.ExecutorProvider;
import org.jooq.FormattingProvider;
import org.jooq.MetaProvider;
import org.jooq.MigrationListenerProvider;
import org.jooq.ParseListener;
import org.jooq.ParseListenerProvider;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordType;
import org.jooq.RecordUnmapper;
import org.jooq.RecordUnmapperProvider;
import org.jooq.SQLDialect;
import org.jooq.SchemaMapping;
import org.jooq.TransactionListener;
import org.jooq.TransactionListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.TransformProvider;
import org.jooq.Unwrapper;
import org.jooq.UnwrapperProvider;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ConfigurationException;
import org.jooq.impl.AbstractConfiguration;
import org.jooq.impl.CacheType;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultAuditProvider;
import org.jooq.impl.DefaultCacheProvider;
import org.jooq.impl.DefaultCharsetProvider;
import org.jooq.impl.DefaultCommitProvider;
import org.jooq.impl.DefaultConnectionFactory;
import org.jooq.impl.DefaultConnectionProvider;
import org.jooq.impl.DefaultConverterProvider;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultExecutorProvider;
import org.jooq.impl.DefaultFormattingProvider;
import org.jooq.impl.DefaultInterpreterConnectionProvider;
import org.jooq.impl.DefaultMetaProvider;
import org.jooq.impl.DefaultRecordMapperProvider;
import org.jooq.impl.DefaultRecordUnmapperProvider;
import org.jooq.impl.DefaultTransactionProvider;
import org.jooq.impl.DefaultTransformProvider;
import org.jooq.impl.DefaultUnwrapperProvider;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.NoConnectionProvider;
import org.jooq.impl.NoTransactionProvider;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.jooq.impl.Tools;
import org.jooq.migrations.xml.jaxb.MigrationsType;

public class DefaultConfiguration
extends AbstractConfiguration {
    private SQLDialect dialect;
    private Settings settings;
    private Clock clock;
    private transient ConnectionProvider connectionProvider;
    private transient ConnectionProvider interpreterConnectionProvider;
    private transient ConnectionProvider systemConnectionProvider;
    private transient ConnectionFactory connectionFactory;
    private transient MetaProvider metaProvider;
    private transient CommitProvider commitProvider;
    private transient ExecutorProvider executorProvider;
    private transient CacheProvider cacheProvider;
    private transient TransactionProvider transactionProvider;
    private transient RecordMapperProvider recordMapperProvider;
    private transient RecordUnmapperProvider recordUnmapperProvider;
    private transient RecordListenerProvider[] recordListenerProviders;
    private transient ExecuteListenerProvider[] executeListenerProviders;
    private transient MigrationListenerProvider[] migrationListenerProviders;
    private transient VisitListenerProvider[] visitListenerProviders;
    private transient TransactionListenerProvider[] transactionListenerProviders;
    private transient DiagnosticsListenerProvider[] diagnosticsListenerProviders;
    private transient UnwrapperProvider unwrapperProvider;
    private transient CharsetProvider charsetProvider;
    private transient ConverterProvider converterProvider;
    private transient FormattingProvider formattingProvider;
    @Pro
    private transient ParseListenerProvider[] parseListenerProviders;
    @Pro
    private transient AuditProvider auditProvider;
    @Pro
    private transient TransformProvider transformProvider;
    private transient ConcurrentHashMap<Object, Object> data;
    private SchemaMapping mapping;
    private static final String END_OF_MAP_MARKER = "EOM";

    public DefaultConfiguration() {
        this(SQLDialect.DEFAULT);
    }

    DefaultConfiguration(SQLDialect dialect) {
        this(null, dialect, null);
    }

    DefaultConfiguration(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
        this(connectionProvider, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, dialect, settings, null);
    }

    DefaultConfiguration(DefaultConfiguration configuration) {
        this(configuration.connectionProvider, configuration.interpreterConnectionProvider, configuration.systemConnectionProvider, configuration.connectionFactory, configuration.metaProvider, configuration.commitProvider, configuration.executorProvider, configuration.cacheProvider, configuration.transactionProvider, configuration.recordMapperProvider, configuration.recordUnmapperProvider, configuration.recordListenerProviders, configuration.executeListenerProviders, configuration.migrationListenerProviders, configuration.visitListenerProviders, configuration.transactionListenerProviders, configuration.diagnosticsListenerProviders, configuration.unwrapperProvider, configuration.charsetProvider, configuration.converterProvider, configuration.formattingProvider, configuration.parseListenerProviders, configuration.auditProvider, configuration.transformProvider, configuration.clock, configuration.dialect, configuration.settings, configuration.data);
    }

    DefaultConfiguration(ConnectionProvider connectionProvider, ConnectionProvider interpreterConnectionProvider, ConnectionProvider systemConnectionProvider, ConnectionFactory connectionFactory, MetaProvider metaProvider, CommitProvider commitProvider, ExecutorProvider executorProvider, CacheProvider cacheProvider, TransactionProvider transactionProvider, RecordMapperProvider recordMapperProvider, RecordUnmapperProvider recordUnmapperProvider, RecordListenerProvider[] recordListenerProviders, ExecuteListenerProvider[] executeListenerProviders, MigrationListenerProvider[] migrationListenerProviders, VisitListenerProvider[] visitListenerProviders, TransactionListenerProvider[] transactionListenerProviders, DiagnosticsListenerProvider[] diagnosticsListenerProviders, UnwrapperProvider unwrapperProvider, CharsetProvider charsetProvider, ConverterProvider converterProvider, FormattingProvider formattingProvider, ParseListenerProvider[] parseListenerProviders, AuditProvider auditProvider, TransformProvider transformProvider, Clock clock, SQLDialect dialect, Settings settings, Map<Object, Object> data) {
        this.set(connectionProvider);
        this.setInterpreterConnectionProvider(interpreterConnectionProvider);
        this.setSystemConnectionProvider(systemConnectionProvider);
        this.set(connectionFactory);
        this.set(metaProvider);
        this.set(commitProvider);
        this.set(executorProvider);
        this.set(cacheProvider);
        this.set(transactionProvider);
        this.set(recordMapperProvider);
        this.set(recordUnmapperProvider);
        this.set(recordListenerProviders);
        this.set(executeListenerProviders);
        this.set(migrationListenerProviders);
        this.set(visitListenerProviders);
        this.set(transactionListenerProviders);
        this.set(diagnosticsListenerProviders);
        this.set(unwrapperProvider);
        this.set(charsetProvider);
        this.set(converterProvider);
        this.set(formattingProvider);
        this.set(parseListenerProviders);
        this.set(auditProvider);
        this.set(transformProvider);
        this.set(clock);
        this.set(dialect);
        this.set(settings);
        this.data = data != null ? new ConcurrentHashMap<Object, Object>(data) : new ConcurrentHashMap();
    }

    @Override
    public final DSLContext dsl() {
        return DSL.using(this);
    }

    @Override
    public final Configuration derive() {
        return new DefaultConfiguration(this);
    }

    @Override
    public final Configuration derive(Connection newConnection) {
        return this.derive(new DefaultConnectionProvider(newConnection));
    }

    @Override
    public final Configuration derive(DataSource newDataSource) {
        return this.derive(new DataSourceConnectionProvider(newDataSource));
    }

    @Override
    public final Configuration derive(ConnectionFactory newConnectionFactory) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, newConnectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(ConnectionProvider newConnectionProvider) {
        return new DefaultConfiguration(newConnectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(MetaProvider newMetaProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, newMetaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(CommitProvider newCommitProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, newCommitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(Executor newExecutor) {
        return this.derive(new ExecutorWrapper(newExecutor));
    }

    @Override
    public final Configuration derive(ExecutorProvider newExecutorProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, newExecutorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(CacheProvider newCacheProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, newCacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(TransactionProvider newTransactionProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, newTransactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(RecordMapper<?, ?> newRecordMapper) {
        return this.derive(new RecordMapperWrapper(newRecordMapper));
    }

    @Override
    public final Configuration derive(RecordMapperProvider newRecordMapperProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, newRecordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(RecordUnmapper<?, ?> newRecordUnmapper) {
        return this.derive(new RecordUnmapperWrapper(newRecordUnmapper));
    }

    @Override
    public final Configuration derive(RecordUnmapperProvider newRecordUnmapperProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, newRecordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(RecordListenerProvider ... newRecordListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, newRecordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(ExecuteListenerProvider ... newExecuteListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, newExecuteListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(MigrationListenerProvider ... newMigrationListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, newMigrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(VisitListenerProvider ... newVisitListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, newVisitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(TransactionListenerProvider ... newTransactionListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, newTransactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, newDiagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(Unwrapper newUnwrapper) {
        return this.derive(new UnwrapperWrapper(newUnwrapper));
    }

    @Override
    public final Configuration derive(UnwrapperProvider newUnwrapperProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, newUnwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(CharsetProvider newCharsetProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, newCharsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(ConverterProvider newConverterProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, newConverterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(FormattingProvider newFormattingProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, newFormattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    @Pro
    public final Configuration derive(ParseListenerProvider ... newParseListenerProviders) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, newParseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    @Pro
    public final Configuration derive(AuditProvider newAuditProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, newAuditProvider, this.transformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    @Pro
    public final Configuration derive(TransformProvider newTransformProvider) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, newTransformProvider, this.clock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(Clock newClock) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, newClock, this.dialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(SQLDialect newDialect) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, newDialect, this.settings, this.data);
    }

    @Override
    public final Configuration derive(Settings newSettings) {
        return new DefaultConfiguration(this.connectionProvider, this.interpreterConnectionProvider, this.systemConnectionProvider, this.connectionFactory, this.metaProvider, this.commitProvider, this.executorProvider, this.cacheProvider, this.transactionProvider, this.recordMapperProvider, this.recordUnmapperProvider, this.recordListenerProviders, this.executeListenerProviders, this.migrationListenerProviders, this.visitListenerProviders, this.transactionListenerProviders, this.diagnosticsListenerProviders, this.unwrapperProvider, this.charsetProvider, this.converterProvider, this.formattingProvider, this.parseListenerProviders, this.auditProvider, this.transformProvider, this.clock, this.dialect, newSettings, this.data);
    }

    @Override
    public final Configuration deriveSettings(Function<? super Settings, ? extends Settings> newSettings) {
        return this.derive(newSettings.apply(SettingsTools.clone(this.settings)));
    }

    @Override
    public final Configuration set(Connection newConnection) {
        return this.set(new DefaultConnectionProvider(newConnection));
    }

    @Override
    public final Configuration set(DataSource newDataSource) {
        return this.set(new DataSourceConnectionProvider(newDataSource));
    }

    @Override
    public final Configuration set(ConnectionProvider newConnectionProvider) {
        if (newConnectionProvider != null) {
            if (this.transactionProvider instanceof ThreadLocalTransactionProvider && !(newConnectionProvider instanceof ThreadLocalTransactionProvider.ThreadLocalConnectionProvider)) {
                throw new ConfigurationException("Cannot specify custom ConnectionProvider when Configuration contains a ThreadLocalTransactionProvider");
            }
            this.connectionProvider = newConnectionProvider;
        } else {
            this.connectionProvider = new NoConnectionProvider();
        }
        return this;
    }

    @Override
    public final Configuration set(ConnectionFactory newConnectionFactory) {
        this.connectionFactory = newConnectionFactory;
        return this;
    }

    @Override
    public final Configuration set(MetaProvider newMetaProvider) {
        this.metaProvider = newMetaProvider;
        return this;
    }

    @Override
    public final Configuration set(CommitProvider newCommitProvider) {
        this.commitProvider = newCommitProvider;
        return this;
    }

    @Override
    public final Configuration set(Executor newExecutor) {
        return this.set(new ExecutorWrapper(newExecutor));
    }

    @Override
    public final Configuration set(ExecutorProvider newExecutorProvider) {
        this.executorProvider = newExecutorProvider;
        return this;
    }

    @Override
    public final Configuration set(CacheProvider newCacheProvider) {
        this.cacheProvider = newCacheProvider;
        return this;
    }

    @Override
    public final Configuration set(TransactionProvider newTransactionProvider) {
        if (newTransactionProvider != null && !(this.connectionProvider instanceof DefaultExecuteContext.ExecuteContextConnectionProvider)) {
            this.transactionProvider = newTransactionProvider;
            if (newTransactionProvider instanceof ThreadLocalTransactionProvider) {
                ThreadLocalTransactionProvider t2 = (ThreadLocalTransactionProvider)newTransactionProvider;
                this.connectionProvider = t2.localConnectionProvider;
            }
        } else {
            this.transactionProvider = new NoTransactionProvider();
        }
        return this;
    }

    @Override
    public final Configuration set(RecordMapper<?, ?> newRecordMapper) {
        return this.set(new RecordMapperWrapper(newRecordMapper));
    }

    @Override
    public final Configuration set(RecordMapperProvider newRecordMapperProvider) {
        this.recordMapperProvider = newRecordMapperProvider;
        return this;
    }

    @Override
    public final Configuration set(RecordUnmapper<?, ?> newRecordUnmapper) {
        return this.set(new RecordUnmapperWrapper(newRecordUnmapper));
    }

    @Override
    public final Configuration set(RecordUnmapperProvider newRecordUnmapperProvider) {
        this.recordUnmapperProvider = newRecordUnmapperProvider;
        return this;
    }

    @Override
    public final Configuration set(RecordListenerProvider ... newRecordListenerProviders) {
        this.recordListenerProviders = newRecordListenerProviders != null ? newRecordListenerProviders : new RecordListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(ExecuteListenerProvider ... newExecuteListenerProviders) {
        this.executeListenerProviders = newExecuteListenerProviders != null ? newExecuteListenerProviders : new ExecuteListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(MigrationListenerProvider ... newMigrationListenerProviders) {
        this.migrationListenerProviders = newMigrationListenerProviders != null ? newMigrationListenerProviders : new MigrationListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(VisitListenerProvider ... newVisitListenerProviders) {
        this.visitListenerProviders = newVisitListenerProviders != null ? newVisitListenerProviders : new VisitListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(TransactionListenerProvider ... newTransactionListenerProviders) {
        this.transactionListenerProviders = newTransactionListenerProviders != null ? newTransactionListenerProviders : new TransactionListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        this.diagnosticsListenerProviders = newDiagnosticsListenerProviders != null ? newDiagnosticsListenerProviders : new DiagnosticsListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(Unwrapper newUnwrapper) {
        return newUnwrapper != null ? this.set(new UnwrapperWrapper(newUnwrapper)) : this.set((UnwrapperProvider)null);
    }

    @Override
    public final Configuration set(UnwrapperProvider newUnwrapperProvider) {
        this.unwrapperProvider = newUnwrapperProvider;
        return this;
    }

    @Override
    public final Configuration set(CharsetProvider newCharsetProvider) {
        this.charsetProvider = newCharsetProvider;
        return this;
    }

    @Override
    public final Configuration set(ConverterProvider newConverterProvider) {
        this.converterProvider = newConverterProvider;
        return this;
    }

    @Override
    public final Configuration set(FormattingProvider newFormattingProvider) {
        this.formattingProvider = newFormattingProvider;
        return this;
    }

    @Override
    @Pro
    public final Configuration set(ParseListenerProvider ... newParseListenerProviders) {
        this.parseListenerProviders = newParseListenerProviders != null ? newParseListenerProviders : new ParseListenerProvider[]{};
        return this;
    }

    @Override
    public final Configuration set(AuditProvider newAuditProvider) {
        this.auditProvider = newAuditProvider;
        return this;
    }

    @Override
    public final Configuration set(TransformProvider newTransformProvider) {
        this.transformProvider = newTransformProvider;
        return this;
    }

    @Override
    public final Configuration set(Clock newClock) {
        this.clock = newClock == null ? Clock.systemUTC() : newClock;
        return this;
    }

    @Override
    public final Configuration set(SQLDialect newDialect) {
        this.dialect = newDialect == null ? SQLDialect.DEFAULT : newDialect;
        return this;
    }

    @Override
    public final Configuration set(Settings newSettings) {
        this.settings = newSettings != null ? SettingsTools.clone(newSettings) : SettingsTools.defaultSettings();
        this.mapping = new SchemaMapping(this);
        return this;
    }

    public final void setConnection(Connection newConnection) {
        this.set(newConnection);
    }

    public final void setDataSource(DataSource newDataSource) {
        this.set(newDataSource);
    }

    public final void setConnectionFactory(ConnectionFactory newConnectionFactory) {
        this.set(newConnectionFactory);
    }

    public final void setConnectionProvider(ConnectionProvider newConnectionProvider) {
        this.set(newConnectionProvider);
    }

    public final void setInterpreterConnectionProvider(ConnectionProvider newInterpreterConnectionProvider) {
        this.interpreterConnectionProvider = newInterpreterConnectionProvider;
    }

    public final void setSystemConnectionProvider(ConnectionProvider newSystemConnectionProvider) {
        this.systemConnectionProvider = newSystemConnectionProvider;
    }

    public final void setMetaProvider(MetaProvider newMetaProvider) {
        this.set(newMetaProvider);
    }

    public final void setVersionProvider(CommitProvider newCommitProvider) {
        this.set(newCommitProvider);
    }

    public final void setExecutor(Executor newExecutor) {
        this.set(newExecutor);
    }

    public final void setExecutorProvider(ExecutorProvider newExecutorProvider) {
        this.set(newExecutorProvider);
    }

    public final void setCacheProvider(CacheProvider newCacheProvider) {
        this.set(newCacheProvider);
    }

    public final void setTransactionProvider(TransactionProvider newTransactionProvider) {
        this.set(newTransactionProvider);
    }

    public final void setRecordMapper(RecordMapper<?, ?> newRecordMapper) {
        this.set(newRecordMapper);
    }

    public final void setRecordMapperProvider(RecordMapperProvider newRecordMapperProvider) {
        this.set(newRecordMapperProvider);
    }

    public final void setRecordUnmapper(RecordUnmapper<?, ?> newRecordUnmapper) {
        this.set(newRecordUnmapper);
    }

    public final void setRecordUnmapperProvider(RecordUnmapperProvider newRecordUnmapperProvider) {
        this.set(newRecordUnmapperProvider);
    }

    public final void setRecordListener(RecordListener ... newRecordListeners) {
        this.set(newRecordListeners);
    }

    public final void setRecordListenerProvider(RecordListenerProvider ... newRecordListenerProviders) {
        this.set(newRecordListenerProviders);
    }

    public final void setExecuteListener(ExecuteListener ... newExecuteListeners) {
        this.set(newExecuteListeners);
    }

    public final void setExecuteListenerProvider(ExecuteListenerProvider ... newExecuteListenerProviders) {
        this.set(newExecuteListenerProviders);
    }

    public final void setVisitListener(VisitListener ... newVisitListeners) {
        this.set(newVisitListeners);
    }

    public final void setVisitListenerProvider(VisitListenerProvider ... newVisitListenerProviders) {
        this.set(newVisitListenerProviders);
    }

    public final void setTransactionListener(TransactionListener ... newTransactionListeners) {
        this.set(newTransactionListeners);
    }

    public final void setTransactionListenerProvider(TransactionListenerProvider ... newTransactionListenerProviders) {
        this.set(newTransactionListenerProviders);
    }

    public final void setDiagnosticsListener(DiagnosticsListener ... newDiagnosticsListener) {
        this.set(newDiagnosticsListener);
    }

    public final void setDiagnosticsListenerProvider(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        this.set(newDiagnosticsListenerProviders);
    }

    @Pro
    public final void setParseListener(ParseListener ... newParseListener) {
        this.set(newParseListener);
    }

    @Pro
    public final void setParseListenerProvider(ParseListenerProvider ... newParseListenerProviders) {
        this.set(newParseListenerProviders);
    }

    @Pro
    public final void setAuditProvider(AuditProvider newAuditProvider) {
        this.set(newAuditProvider);
    }

    @Pro
    public final void setTransformProvider(TransformProvider newTransformProvider) {
        this.set(newTransformProvider);
    }

    public final void setUnwrapper(Unwrapper newUnwrapper) {
        this.set(newUnwrapper);
    }

    public final void setUnwrapperProvider(UnwrapperProvider newUnwrapperProvider) {
        this.set(newUnwrapperProvider);
    }

    public final void setClock(Clock newClock) {
        this.set(newClock);
    }

    public final void setSQLDialect(SQLDialect newDialect) {
        this.set(newDialect);
    }

    public final void setSettings(Settings newSettings) {
        this.set(newSettings);
    }

    @Override
    public final ConnectionProvider connectionProvider() {
        ConnectionProvider transactional;
        TransactionProvider tp = this.transactionProvider();
        if (tp instanceof ThreadLocalTransactionProvider) {
            ThreadLocalTransactionProvider t2 = (ThreadLocalTransactionProvider)tp;
            v0 = t2.localConnectionProvider;
        } else {
            v0 = transactional = (ConnectionProvider)this.data(Tools.SimpleDataKey.DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION);
        }
        return transactional != null ? transactional : (this.connectionProvider != null ? this.connectionProvider : new NoConnectionProvider());
    }

    @Override
    public final ConnectionProvider interpreterConnectionProvider() {
        return this.interpreterConnectionProvider != null ? this.interpreterConnectionProvider : new DefaultInterpreterConnectionProvider(this);
    }

    @Override
    public final ConnectionProvider systemConnectionProvider() {
        return this.systemConnectionProvider != null ? this.systemConnectionProvider : this.connectionProvider();
    }

    @Override
    public final ConnectionFactory connectionFactory() {
        return this.connectionFactory != null ? this.connectionFactory : new NoConnectionFactory();
    }

    @Override
    public final MetaProvider metaProvider() {
        return this.metaProvider != null ? this.metaProvider : new DefaultMetaProvider(this);
    }

    @Override
    public final CommitProvider commitProvider() {
        return this.commitProvider != null ? this.commitProvider : new DefaultCommitProvider(this, new MigrationsType());
    }

    @Override
    public final ExecutorProvider executorProvider() {
        return this.executorProvider != null ? this.executorProvider : new DefaultExecutorProvider();
    }

    @Override
    public final CacheProvider cacheProvider() {
        return this.cacheProvider != null ? this.cacheProvider : new DefaultCacheProvider();
    }

    @Override
    public final TransactionProvider transactionProvider() {
        if (this.transactionProvider == null || this.transactionProvider instanceof NoTransactionProvider) {
            return new DefaultTransactionProvider(this.connectionProvider);
        }
        return this.transactionProvider;
    }

    @Override
    public final RecordMapperProvider recordMapperProvider() {
        return this.recordMapperProvider != null ? this.recordMapperProvider : new DefaultRecordMapperProvider(this);
    }

    @Override
    public final RecordUnmapperProvider recordUnmapperProvider() {
        return this.recordUnmapperProvider != null ? this.recordUnmapperProvider : new DefaultRecordUnmapperProvider(this);
    }

    @Override
    public final RecordListenerProvider[] recordListenerProviders() {
        return this.recordListenerProviders;
    }

    @Override
    public final ExecuteListenerProvider[] executeListenerProviders() {
        return this.executeListenerProviders;
    }

    @Override
    public final MigrationListenerProvider[] migrationListenerProviders() {
        return this.migrationListenerProviders;
    }

    @Override
    public final VisitListenerProvider[] visitListenerProviders() {
        return this.visitListenerProviders;
    }

    @Override
    public final TransactionListenerProvider[] transactionListenerProviders() {
        return this.transactionListenerProviders;
    }

    @Override
    public final DiagnosticsListenerProvider[] diagnosticsListenerProviders() {
        return this.diagnosticsListenerProviders;
    }

    @Override
    public final UnwrapperProvider unwrapperProvider() {
        return this.unwrapperProvider != null ? this.unwrapperProvider : new DefaultUnwrapperProvider();
    }

    @Override
    public final CharsetProvider charsetProvider() {
        return this.charsetProvider != null ? this.charsetProvider : new DefaultCharsetProvider();
    }

    @Override
    public final ConverterProvider converterProvider() {
        return this.converterProvider != null ? this.converterProvider : new DefaultConverterProvider();
    }

    @Override
    public final FormattingProvider formattingProvider() {
        return this.formattingProvider != null ? this.formattingProvider : new DefaultFormattingProvider();
    }

    @Override
    public final ParseListenerProvider[] parseListenerProviders() {
        return this.parseListenerProviders;
    }

    @Override
    public final AuditProvider auditProvider() {
        return this.auditProvider != null ? this.auditProvider : new DefaultAuditProvider();
    }

    @Override
    public final TransformProvider transformProvider() {
        return this.transformProvider != null ? this.transformProvider : new DefaultTransformProvider();
    }

    @Override
    public final Clock clock() {
        return this.clock;
    }

    @Override
    public final SQLDialect dialect() {
        return this.dialect;
    }

    @Override
    public final SQLDialect family() {
        return this.dialect.family();
    }

    @Override
    public final Settings settings() {
        return this.settings;
    }

    public final ConcurrentHashMap<Object, Object> data() {
        return this.data;
    }

    @Override
    public final Object data(Object key) {
        return this.data.get(key);
    }

    @Override
    public final Object data(Object key, Object value) {
        return this.data.put(key, value);
    }

    @Override
    @Deprecated
    public final SchemaMapping schemaMapping() {
        if (this.mapping == null) {
            this.mapping = new SchemaMapping(this);
        }
        return this.mapping;
    }

    public String toString() {
        return "DefaultConfiguration [\n\tconnected=" + (this.connectionProvider != null && !(this.connectionProvider instanceof NoConnectionProvider) || this.connectionFactory != null && !(this.connectionFactory instanceof NoConnectionFactory)) + ",\n\ttransactional=" + (this.transactionProvider != null && !(this.transactionProvider instanceof NoTransactionProvider) || this.connectionFactory instanceof DefaultConnectionFactory && ((DefaultConnectionFactory)this.connectionFactory).connection instanceof DefaultConnectionFactory.NonClosingConnection) + ",\n\tdialect=" + String.valueOf((Object)this.dialect) + ",\n\tdata=" + String.valueOf(this.data) + ",\n\tsettings=\n\t\t" + String.valueOf(this.settings) + "\n]";
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeObject(this.serializableOrNull(this.connectionProvider));
        oos.writeObject(this.serializableOrNull(this.interpreterConnectionProvider));
        oos.writeObject(this.serializableOrNull(this.systemConnectionProvider));
        oos.writeObject(this.serializableOrNull(this.metaProvider));
        oos.writeObject(this.serializableOrNull(this.commitProvider));
        oos.writeObject(this.serializableOrNull(this.transactionProvider));
        oos.writeObject(this.serializableOrNull(this.recordMapperProvider));
        oos.writeObject(this.serializableOrNull(this.recordUnmapperProvider));
        oos.writeObject(this.cloneSerializables(this.executeListenerProviders));
        oos.writeObject(this.cloneSerializables(this.recordListenerProviders));
        oos.writeObject(this.cloneSerializables(this.visitListenerProviders));
        oos.writeObject(this.cloneSerializables(this.transactionListenerProviders));
        oos.writeObject(this.cloneSerializables(this.diagnosticsListenerProviders));
        oos.writeObject(this.cloneSerializables(this.parseListenerProviders));
        oos.writeObject(this.serializableOrNull(this.auditProvider));
        oos.writeObject(this.serializableOrNull(this.transformProvider));
        oos.writeObject(this.serializableOrNull(this.unwrapperProvider));
        oos.writeObject(this.serializableOrNull(this.charsetProvider));
        oos.writeObject(this.serializableOrNull(this.converterProvider));
        oos.writeObject(this.serializableOrNull(this.formattingProvider));
        for (Map.Entry<Object, Object> entry : this.data.entrySet()) {
            if (entry.getKey() instanceof CacheType) continue;
            oos.writeObject(entry.getKey());
            oos.writeObject(entry.getValue());
        }
        oos.writeObject(END_OF_MAP_MARKER);
    }

    private final Serializable serializableOrNull(Object o) {
        Serializable s2;
        return o instanceof Serializable ? (s2 = (Serializable)o) : null;
    }

    private final <E> E[] cloneSerializables(E[] array) {
        Object[] clone = (Object[])array.clone();
        for (int i = 0; i < clone.length; ++i) {
            if (clone[i] instanceof Serializable) continue;
            clone[i] = null;
        }
        return clone;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        Object key;
        ois.defaultReadObject();
        this.connectionProvider = (ConnectionProvider)ois.readObject();
        this.interpreterConnectionProvider = (ConnectionProvider)ois.readObject();
        this.systemConnectionProvider = (ConnectionProvider)ois.readObject();
        this.metaProvider = (MetaProvider)ois.readObject();
        this.commitProvider = (CommitProvider)ois.readObject();
        this.transactionProvider = (TransactionProvider)ois.readObject();
        this.recordMapperProvider = (RecordMapperProvider)ois.readObject();
        this.recordUnmapperProvider = (RecordUnmapperProvider)ois.readObject();
        this.executeListenerProviders = (ExecuteListenerProvider[])ois.readObject();
        this.recordListenerProviders = (RecordListenerProvider[])ois.readObject();
        this.visitListenerProviders = (VisitListenerProvider[])ois.readObject();
        this.transactionListenerProviders = (TransactionListenerProvider[])ois.readObject();
        this.diagnosticsListenerProviders = (DiagnosticsListenerProvider[])ois.readObject();
        this.parseListenerProviders = (ParseListenerProvider[])ois.readObject();
        this.auditProvider = (AuditProvider)ois.readObject();
        this.transformProvider = (TransformProvider)ois.readObject();
        this.unwrapperProvider = (UnwrapperProvider)ois.readObject();
        this.charsetProvider = (CharsetProvider)ois.readObject();
        this.converterProvider = (ConverterProvider)ois.readObject();
        this.formattingProvider = (FormattingProvider)ois.readObject();
        this.data = new ConcurrentHashMap();
        while (!END_OF_MAP_MARKER.equals(key = ois.readObject())) {
            Object value = ois.readObject();
            this.data.put(key, value);
        }
    }

    private final class ExecutorWrapper
    implements ExecutorProvider {
        private final Executor newExecutor;

        private ExecutorWrapper(Executor newExecutor) {
            this.newExecutor = newExecutor;
        }

        @Override
        public Executor provide() {
            return this.newExecutor;
        }
    }

    private final class RecordMapperWrapper
    implements RecordMapperProvider {
        private final RecordMapper<?, ?> newRecordMapper;

        private RecordMapperWrapper(RecordMapper<?, ?> newRecordMapper) {
            this.newRecordMapper = newRecordMapper;
        }

        @Override
        public <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> recordType, Class<? extends E> type) {
            return this.newRecordMapper;
        }
    }

    private final class RecordUnmapperWrapper
    implements RecordUnmapperProvider {
        private final RecordUnmapper<?, ?> newRecordUnmapper;

        private RecordUnmapperWrapper(RecordUnmapper<?, ?> newRecordUnmapper) {
            this.newRecordUnmapper = newRecordUnmapper;
        }

        @Override
        public <E, R extends Record> RecordUnmapper<E, R> provide(Class<? extends E> type, RecordType<R> recordType) {
            return this.newRecordUnmapper;
        }
    }

    private final class UnwrapperWrapper
    implements UnwrapperProvider {
        private final Unwrapper newUnwrapper;

        private UnwrapperWrapper(Unwrapper newUnwrapper) {
            this.newUnwrapper = newUnwrapper;
        }

        @Override
        public Unwrapper provide() {
            return this.newUnwrapper;
        }
    }
}

