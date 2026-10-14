/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import io.r2dbc.spi.ConnectionFactory;
import java.sql.Connection;
import java.time.Clock;
import java.util.Map;
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
import org.jooq.DiagnosticsListenerProvider;
import org.jooq.ExecuteListenerProvider;
import org.jooq.ExecutorProvider;
import org.jooq.FormattingProvider;
import org.jooq.MetaProvider;
import org.jooq.MigrationListenerProvider;
import org.jooq.ParseListenerProvider;
import org.jooq.Pro;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.RecordUnmapper;
import org.jooq.RecordUnmapperProvider;
import org.jooq.SQLDialect;
import org.jooq.SchemaMapping;
import org.jooq.TransactionListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.TransformProvider;
import org.jooq.Unwrapper;
import org.jooq.UnwrapperProvider;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.Settings;
import org.jooq.impl.AbstractConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.tools.jdbc.MockConnectionProvider;
import org.jooq.tools.jdbc.MockDataProvider;

public class MockConfiguration
extends AbstractConfiguration {
    private final Configuration delegate;
    private final MockDataProvider provider;

    public MockConfiguration(Configuration delegate, MockDataProvider provider) {
        this.delegate = delegate;
        this.provider = provider;
    }

    @Override
    public DSLContext dsl() {
        return new DefaultDSLContext(this);
    }

    @Override
    public Map<Object, Object> data() {
        return this.delegate.data();
    }

    @Override
    public Object data(Object key) {
        return this.delegate.data(key);
    }

    @Override
    public Object data(Object key, Object value) {
        return this.delegate.data(key, value);
    }

    @Override
    public Clock clock() {
        return this.delegate.clock();
    }

    @Override
    public ConnectionProvider connectionProvider() {
        return new MockConnectionProvider(this.delegate.connectionProvider(), this.provider);
    }

    @Override
    public ConnectionProvider interpreterConnectionProvider() {
        return new MockConnectionProvider(this.delegate.interpreterConnectionProvider(), this.provider);
    }

    @Override
    public ConnectionProvider systemConnectionProvider() {
        return new MockConnectionProvider(this.delegate.systemConnectionProvider(), this.provider);
    }

    @Override
    public ConnectionFactory connectionFactory() {
        throw new UnsupportedOperationException("TODO: Add support for MockConnectionFactory");
    }

    @Override
    public MetaProvider metaProvider() {
        return this.delegate.metaProvider();
    }

    @Override
    public CommitProvider commitProvider() {
        return this.delegate.commitProvider();
    }

    @Override
    public ExecutorProvider executorProvider() {
        return this.delegate.executorProvider();
    }

    @Override
    public CacheProvider cacheProvider() {
        return this.delegate.cacheProvider();
    }

    @Override
    public TransactionProvider transactionProvider() {
        return this.delegate.transactionProvider();
    }

    @Override
    public RecordMapperProvider recordMapperProvider() {
        return this.delegate.recordMapperProvider();
    }

    @Override
    public RecordUnmapperProvider recordUnmapperProvider() {
        return this.delegate.recordUnmapperProvider();
    }

    @Override
    public RecordListenerProvider[] recordListenerProviders() {
        return this.delegate.recordListenerProviders();
    }

    @Override
    public ExecuteListenerProvider[] executeListenerProviders() {
        return this.delegate.executeListenerProviders();
    }

    @Override
    public MigrationListenerProvider[] migrationListenerProviders() {
        return this.delegate.migrationListenerProviders();
    }

    @Override
    public VisitListenerProvider[] visitListenerProviders() {
        return this.delegate.visitListenerProviders();
    }

    @Override
    public TransactionListenerProvider[] transactionListenerProviders() {
        return this.delegate.transactionListenerProviders();
    }

    @Override
    public DiagnosticsListenerProvider[] diagnosticsListenerProviders() {
        return this.delegate.diagnosticsListenerProviders();
    }

    @Override
    @Pro
    public ParseListenerProvider[] parseListenerProviders() {
        return this.delegate.parseListenerProviders();
    }

    @Override
    public AuditProvider auditProvider() {
        return this.delegate.auditProvider();
    }

    @Override
    public TransformProvider transformProvider() {
        return this.delegate.transformProvider();
    }

    @Override
    public UnwrapperProvider unwrapperProvider() {
        return this.delegate.unwrapperProvider();
    }

    @Override
    public CharsetProvider charsetProvider() {
        return this.delegate.charsetProvider();
    }

    @Override
    public ConverterProvider converterProvider() {
        return this.delegate.converterProvider();
    }

    @Override
    public FormattingProvider formattingProvider() {
        return this.delegate.formattingProvider();
    }

    @Override
    public SchemaMapping schemaMapping() {
        return this.delegate.schemaMapping();
    }

    @Override
    public SQLDialect dialect() {
        return this.delegate.dialect();
    }

    @Override
    public SQLDialect family() {
        return this.delegate.family();
    }

    @Override
    public Settings settings() {
        return this.delegate.settings();
    }

    @Override
    public Configuration set(Clock newClock) {
        this.delegate.set(newClock);
        return this;
    }

    @Override
    public Configuration set(ConnectionProvider newConnectionProvider) {
        this.delegate.set(newConnectionProvider);
        return this;
    }

    @Override
    public Configuration set(MetaProvider newMetaProvider) {
        this.delegate.set(newMetaProvider);
        return this;
    }

    @Override
    public Configuration set(CommitProvider newCommitProvider) {
        this.delegate.set(newCommitProvider);
        return this;
    }

    @Override
    public Configuration set(Connection newConnection) {
        this.delegate.set(newConnection);
        return this;
    }

    @Override
    public Configuration set(DataSource newDataSource) {
        this.delegate.set(newDataSource);
        return this;
    }

    @Override
    public Configuration set(ConnectionFactory newConnectionFactory) {
        this.delegate.set(newConnectionFactory);
        return this;
    }

    @Override
    public Configuration set(Executor newExecutor) {
        this.delegate.set(newExecutor);
        return this;
    }

    @Override
    public Configuration set(ExecutorProvider newExecutorProvider) {
        this.delegate.set(newExecutorProvider);
        return this;
    }

    @Override
    public Configuration set(CacheProvider newCacheProvider) {
        this.delegate.set(newCacheProvider);
        return this;
    }

    @Override
    public Configuration set(TransactionProvider newTransactionProvider) {
        this.delegate.set(newTransactionProvider);
        return this;
    }

    @Override
    public Configuration set(RecordMapper<?, ?> newRecordMapper) {
        this.delegate.set(newRecordMapper);
        return this;
    }

    @Override
    public Configuration set(RecordMapperProvider newRecordMapperProvider) {
        this.delegate.set(newRecordMapperProvider);
        return this;
    }

    @Override
    public Configuration set(RecordUnmapper<?, ?> newRecordUnmapper) {
        this.delegate.set(newRecordUnmapper);
        return this;
    }

    @Override
    public Configuration set(RecordUnmapperProvider newRecordUnmapperProvider) {
        this.delegate.set(newRecordUnmapperProvider);
        return this;
    }

    @Override
    public Configuration set(RecordListenerProvider ... newRecordListenerProviders) {
        this.delegate.set(newRecordListenerProviders);
        return this;
    }

    @Override
    public Configuration set(ExecuteListenerProvider ... newExecuteListenerProviders) {
        this.delegate.set(newExecuteListenerProviders);
        return this;
    }

    @Override
    public Configuration set(MigrationListenerProvider ... newMigrationListenerProviders) {
        this.delegate.set(newMigrationListenerProviders);
        return this;
    }

    @Override
    public Configuration set(VisitListenerProvider ... newVisitListenerProviders) {
        this.delegate.set(newVisitListenerProviders);
        return this;
    }

    @Override
    public Configuration set(TransactionListenerProvider ... newTransactionListenerProviders) {
        this.delegate.set(newTransactionListenerProviders);
        return this;
    }

    @Override
    public Configuration set(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        this.delegate.set(newDiagnosticsListenerProviders);
        return this;
    }

    @Override
    @Pro
    public Configuration set(ParseListenerProvider ... newParseListenerProviders) {
        this.delegate.set(newParseListenerProviders);
        return this;
    }

    @Override
    public Configuration set(AuditProvider newAuditProvider) {
        this.delegate.set(newAuditProvider);
        return this;
    }

    @Override
    public Configuration set(TransformProvider newTransformProvider) {
        this.delegate.set(newTransformProvider);
        return this;
    }

    @Override
    public Configuration set(Unwrapper newUnwrapper) {
        this.delegate.set(newUnwrapper);
        return this;
    }

    @Override
    public Configuration set(UnwrapperProvider newUnwrapperProvider) {
        this.delegate.set(newUnwrapperProvider);
        return this;
    }

    @Override
    public Configuration set(CharsetProvider newCharsetProvider) {
        this.delegate.set(newCharsetProvider);
        return this;
    }

    @Override
    public Configuration set(ConverterProvider newConverterProvider) {
        this.delegate.set(newConverterProvider);
        return this;
    }

    @Override
    public Configuration set(FormattingProvider newFormattingProvider) {
        this.delegate.set(newFormattingProvider);
        return this;
    }

    @Override
    public Configuration set(SQLDialect newDialect) {
        this.delegate.set(newDialect);
        return this;
    }

    @Override
    public Configuration set(Settings newSettings) {
        this.delegate.set(newSettings);
        return this;
    }

    @Override
    public Configuration derive() {
        return new MockConfiguration(this.delegate.derive(), this.provider);
    }

    @Override
    public Configuration derive(Clock newClock) {
        return new MockConfiguration(this.delegate.derive(newClock), this.provider);
    }

    @Override
    public Configuration derive(Connection newConnection) {
        return new MockConfiguration(this.delegate.derive(newConnection), this.provider);
    }

    @Override
    public Configuration derive(DataSource newDataSource) {
        return new MockConfiguration(this.delegate.derive(newDataSource), this.provider);
    }

    @Override
    public Configuration derive(ConnectionFactory newConnectionFactory) {
        return new MockConfiguration(this.delegate.derive(newConnectionFactory), this.provider);
    }

    @Override
    public Configuration derive(ConnectionProvider newConnectionProvider) {
        return new MockConfiguration(this.delegate.derive(newConnectionProvider), this.provider);
    }

    @Override
    public Configuration derive(MetaProvider newMetaProvider) {
        return new MockConfiguration(this.delegate.derive(newMetaProvider), this.provider);
    }

    @Override
    public Configuration derive(CommitProvider newCommitProvider) {
        return new MockConfiguration(this.delegate.derive(newCommitProvider), this.provider);
    }

    @Override
    public Configuration derive(Executor newExecutor) {
        return new MockConfiguration(this.delegate.derive(newExecutor), this.provider);
    }

    @Override
    public Configuration derive(ExecutorProvider newExecutorProvider) {
        return new MockConfiguration(this.delegate.derive(newExecutorProvider), this.provider);
    }

    @Override
    public Configuration derive(CacheProvider newCacheProvider) {
        return new MockConfiguration(this.delegate.derive(newCacheProvider), this.provider);
    }

    @Override
    public Configuration derive(TransactionProvider newTransactionProvider) {
        return new MockConfiguration(this.delegate.derive(newTransactionProvider), this.provider);
    }

    @Override
    public Configuration derive(RecordMapper<?, ?> newRecordMapper) {
        return new MockConfiguration(this.delegate.derive(newRecordMapper), this.provider);
    }

    @Override
    public Configuration derive(RecordMapperProvider newRecordMapperProvider) {
        return new MockConfiguration(this.delegate.derive(newRecordMapperProvider), this.provider);
    }

    @Override
    public Configuration derive(RecordUnmapper<?, ?> newRecordUnmapper) {
        return new MockConfiguration(this.delegate.derive(newRecordUnmapper), this.provider);
    }

    @Override
    public Configuration derive(RecordUnmapperProvider newRecordUnmapperProvider) {
        return new MockConfiguration(this.delegate.derive(newRecordUnmapperProvider), this.provider);
    }

    @Override
    public Configuration derive(RecordListenerProvider ... newRecordListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newRecordListenerProviders), this.provider);
    }

    @Override
    public Configuration derive(ExecuteListenerProvider ... newExecuteListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newExecuteListenerProviders), this.provider);
    }

    @Override
    public Configuration derive(MigrationListenerProvider ... newMigrationListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newMigrationListenerProviders), this.provider);
    }

    @Override
    public Configuration derive(VisitListenerProvider ... newVisitListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newVisitListenerProviders), this.provider);
    }

    @Override
    public Configuration derive(TransactionListenerProvider ... newTransactionListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newTransactionListenerProviders), this.provider);
    }

    @Override
    public Configuration derive(DiagnosticsListenerProvider ... newDiagnosticsListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newDiagnosticsListenerProviders), this.provider);
    }

    @Override
    @Pro
    public Configuration derive(ParseListenerProvider ... newParseListenerProviders) {
        return new MockConfiguration(this.delegate.derive(newParseListenerProviders), this.provider);
    }

    @Override
    @Pro
    public Configuration derive(AuditProvider newAuditProvider) {
        return new MockConfiguration(this.delegate.derive(newAuditProvider), this.provider);
    }

    @Override
    @Pro
    public Configuration derive(TransformProvider newTransformProvider) {
        return new MockConfiguration(this.delegate.derive(newTransformProvider), this.provider);
    }

    @Override
    public Configuration derive(Unwrapper newUnwrapper) {
        return new MockConfiguration(this.delegate.derive(newUnwrapper), this.provider);
    }

    @Override
    public Configuration derive(UnwrapperProvider newUnwrapperProvider) {
        return new MockConfiguration(this.delegate.derive(newUnwrapperProvider), this.provider);
    }

    @Override
    public Configuration derive(CharsetProvider newCharsetProvider) {
        return new MockConfiguration(this.delegate.derive(newCharsetProvider), this.provider);
    }

    @Override
    public Configuration derive(ConverterProvider newConverterProvider) {
        return new MockConfiguration(this.delegate.derive(newConverterProvider), this.provider);
    }

    @Override
    public Configuration derive(FormattingProvider newFormattingProvider) {
        return new MockConfiguration(this.delegate.derive(newFormattingProvider), this.provider);
    }

    @Override
    public Configuration derive(SQLDialect newDialect) {
        return new MockConfiguration(this.delegate.derive(newDialect), this.provider);
    }

    @Override
    public Configuration derive(Settings newSettings) {
        return new MockConfiguration(this.delegate.derive(newSettings), this.provider);
    }

    @Override
    public Configuration deriveSettings(Function<? super Settings, ? extends Settings> newSettings) {
        return new MockConfiguration(this.delegate.deriveSettings(newSettings), this.provider);
    }
}

