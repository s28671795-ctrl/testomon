/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import io.r2dbc.spi.ConnectionFactory;
import java.io.Serializable;
import java.sql.Connection;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.AuditProvider;
import org.jooq.CacheProvider;
import org.jooq.CharsetProvider;
import org.jooq.CommitProvider;
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
import org.jooq.MigrationListener;
import org.jooq.MigrationListenerProvider;
import org.jooq.ParseListener;
import org.jooq.ParseListenerProvider;
import org.jooq.Pro;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
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
import org.jooq.exception.DataAccessException;

public interface Configuration
extends Serializable {
    @NotNull
    public DSLContext dsl();

    @NotNull
    public Map<Object, Object> data();

    @Nullable
    public Object data(Object var1);

    @Nullable
    public Object data(Object var1, Object var2);

    @NotNull
    public Clock clock();

    @NotNull
    public ConnectionProvider connectionProvider();

    @NotNull
    public ConnectionFactory connectionFactory();

    @NotNull
    public ConnectionProvider interpreterConnectionProvider();

    @NotNull
    public ConnectionProvider systemConnectionProvider();

    @NotNull
    public MetaProvider metaProvider();

    @NotNull
    public CommitProvider commitProvider();

    @NotNull
    public ExecutorProvider executorProvider();

    @NotNull
    public CacheProvider cacheProvider();

    @NotNull
    public TransactionProvider transactionProvider();

    @NotNull
    public @NotNull TransactionListenerProvider @NotNull [] transactionListenerProviders();

    @NotNull
    public @NotNull DiagnosticsListenerProvider @NotNull [] diagnosticsListenerProviders();

    @NotNull
    public UnwrapperProvider unwrapperProvider();

    @NotNull
    public CharsetProvider charsetProvider();

    @NotNull
    public RecordMapperProvider recordMapperProvider();

    @NotNull
    public RecordUnmapperProvider recordUnmapperProvider();

    @NotNull
    public @NotNull RecordListenerProvider @NotNull [] recordListenerProviders();

    @NotNull
    public @NotNull ExecuteListenerProvider @NotNull [] executeListenerProviders();

    @NotNull
    public @NotNull MigrationListenerProvider @NotNull [] migrationListenerProviders();

    @NotNull
    public @NotNull VisitListenerProvider @NotNull [] visitListenerProviders();

    @NotNull
    public ConverterProvider converterProvider();

    @NotNull
    public FormattingProvider formattingProvider();

    @Pro
    @NotNull
    public @NotNull ParseListenerProvider @NotNull [] parseListenerProviders();

    @Pro
    @NotNull
    public AuditProvider auditProvider();

    @Pro
    @NotNull
    public TransformProvider transformProvider();

    @Deprecated(forRemoval=true, since="2.0")
    @NotNull
    public SchemaMapping schemaMapping();

    @NotNull
    public SQLDialect dialect();

    @NotNull
    public SQLDialect family();

    @NotNull
    public Settings settings();

    @NotNull
    public Configuration set(Clock var1);

    @NotNull
    public Configuration set(ConnectionProvider var1);

    @NotNull
    public Configuration set(MetaProvider var1);

    @NotNull
    public Configuration set(CommitProvider var1);

    @NotNull
    public Configuration set(ExecutorProvider var1);

    @NotNull
    public Configuration set(CacheProvider var1);

    @NotNull
    public Configuration set(Executor var1);

    @NotNull
    public Configuration set(Connection var1);

    @NotNull
    public Configuration set(DataSource var1);

    @NotNull
    public Configuration set(ConnectionFactory var1);

    @NotNull
    public Configuration set(TransactionProvider var1);

    @NotNull
    public Configuration set(RecordMapper<?, ?> var1);

    @NotNull
    public Configuration set(RecordMapperProvider var1);

    @NotNull
    public Configuration set(RecordUnmapper<?, ?> var1);

    @NotNull
    public Configuration set(RecordUnmapperProvider var1);

    @NotNull
    public Configuration set(RecordListener ... var1);

    @NotNull
    public Configuration setAppending(RecordListener ... var1);

    @NotNull
    public Configuration set(RecordListenerProvider ... var1);

    @NotNull
    public Configuration setAppending(RecordListenerProvider ... var1);

    @NotNull
    public Configuration set(ExecuteListener ... var1);

    @NotNull
    public Configuration setAppending(ExecuteListener ... var1);

    @NotNull
    public Configuration set(ExecuteListenerProvider ... var1);

    @NotNull
    public Configuration setAppending(ExecuteListenerProvider ... var1);

    @NotNull
    public Configuration set(MigrationListener ... var1);

    @NotNull
    public Configuration setAppending(MigrationListener ... var1);

    @NotNull
    public Configuration set(MigrationListenerProvider ... var1);

    @NotNull
    public Configuration setAppending(MigrationListenerProvider ... var1);

    @NotNull
    public Configuration set(VisitListener ... var1);

    @NotNull
    public Configuration setAppending(VisitListener ... var1);

    @NotNull
    public Configuration set(VisitListenerProvider ... var1);

    @NotNull
    public Configuration setAppending(VisitListenerProvider ... var1);

    @NotNull
    public Configuration set(TransactionListener ... var1);

    @NotNull
    public Configuration setAppending(TransactionListener ... var1);

    @NotNull
    public Configuration set(TransactionListenerProvider ... var1);

    @NotNull
    public Configuration setAppending(TransactionListenerProvider ... var1);

    @NotNull
    public Configuration set(DiagnosticsListener ... var1);

    @NotNull
    public Configuration setAppending(DiagnosticsListener ... var1);

    @NotNull
    public Configuration set(DiagnosticsListenerProvider ... var1);

    @NotNull
    public Configuration setAppending(DiagnosticsListenerProvider ... var1);

    @NotNull
    public Configuration set(Unwrapper var1);

    @NotNull
    public Configuration set(UnwrapperProvider var1);

    @NotNull
    public Configuration set(CharsetProvider var1);

    @NotNull
    public Configuration set(ConverterProvider var1);

    @NotNull
    public Configuration set(FormattingProvider var1);

    @Pro
    @NotNull
    public Configuration set(ParseListener ... var1);

    @Pro
    @NotNull
    public Configuration setAppending(ParseListener ... var1);

    @Pro
    @NotNull
    public Configuration set(ParseListenerProvider ... var1);

    @Pro
    @NotNull
    public Configuration setAppending(ParseListenerProvider ... var1);

    @Pro
    @NotNull
    public Configuration set(AuditProvider var1);

    @Pro
    @NotNull
    public Configuration set(TransformProvider var1);

    @NotNull
    public Configuration set(SQLDialect var1);

    @NotNull
    public Configuration set(Settings var1);

    @NotNull
    public Configuration derive();

    @NotNull
    public Configuration derive(Clock var1);

    @NotNull
    public Configuration derive(Connection var1);

    @NotNull
    public Configuration derive(DataSource var1);

    @NotNull
    public Configuration derive(ConnectionFactory var1);

    @NotNull
    public Configuration derive(ConnectionProvider var1);

    @NotNull
    public Configuration derive(MetaProvider var1);

    @NotNull
    public Configuration derive(CommitProvider var1);

    @NotNull
    public Configuration derive(Executor var1);

    @NotNull
    public Configuration derive(ExecutorProvider var1);

    @NotNull
    public Configuration derive(CacheProvider var1);

    @NotNull
    public Configuration derive(TransactionProvider var1);

    @NotNull
    public Configuration derive(RecordMapper<?, ?> var1);

    @NotNull
    public Configuration derive(RecordMapperProvider var1);

    @NotNull
    public Configuration derive(RecordUnmapper<?, ?> var1);

    @NotNull
    public Configuration derive(RecordUnmapperProvider var1);

    @NotNull
    public Configuration derive(RecordListener ... var1);

    @NotNull
    public Configuration deriveAppending(RecordListener ... var1);

    @NotNull
    public Configuration derive(RecordListenerProvider ... var1);

    @NotNull
    public Configuration deriveAppending(RecordListenerProvider ... var1);

    @NotNull
    public Configuration derive(ExecuteListener ... var1);

    @NotNull
    public Configuration deriveAppending(ExecuteListener ... var1);

    @NotNull
    public Configuration derive(ExecuteListenerProvider ... var1);

    @NotNull
    public Configuration deriveAppending(ExecuteListenerProvider ... var1);

    @NotNull
    public Configuration derive(MigrationListener ... var1);

    @NotNull
    public Configuration deriveAppending(MigrationListener ... var1);

    @NotNull
    public Configuration derive(MigrationListenerProvider ... var1);

    @NotNull
    public Configuration deriveAppending(MigrationListenerProvider ... var1);

    @NotNull
    public Configuration derive(VisitListener ... var1);

    @NotNull
    public Configuration deriveAppending(VisitListener ... var1);

    @NotNull
    public Configuration derive(VisitListenerProvider ... var1);

    @NotNull
    public Configuration deriveAppending(VisitListenerProvider ... var1);

    @NotNull
    public Configuration derive(TransactionListener ... var1);

    @NotNull
    public Configuration deriveAppending(TransactionListener ... var1);

    @NotNull
    public Configuration derive(TransactionListenerProvider ... var1);

    @NotNull
    public Configuration deriveAppending(TransactionListenerProvider ... var1);

    @NotNull
    public Configuration derive(DiagnosticsListener ... var1);

    @NotNull
    public Configuration deriveAppending(DiagnosticsListener ... var1);

    @NotNull
    public Configuration derive(DiagnosticsListenerProvider ... var1);

    @NotNull
    public Configuration deriveAppending(DiagnosticsListenerProvider ... var1);

    @NotNull
    public Configuration derive(Unwrapper var1);

    @NotNull
    public Configuration derive(UnwrapperProvider var1);

    @NotNull
    public Configuration derive(CharsetProvider var1);

    @NotNull
    public Configuration derive(ConverterProvider var1);

    @NotNull
    public Configuration derive(FormattingProvider var1);

    @Pro
    @NotNull
    public Configuration derive(ParseListener ... var1);

    @Pro
    @NotNull
    public Configuration deriveAppending(ParseListener ... var1);

    @Pro
    @NotNull
    public Configuration derive(ParseListenerProvider ... var1);

    @Pro
    @NotNull
    public Configuration deriveAppending(ParseListenerProvider ... var1);

    @Pro
    @NotNull
    public Configuration derive(AuditProvider var1);

    @Pro
    @NotNull
    public Configuration derive(TransformProvider var1);

    @NotNull
    public Configuration derive(SQLDialect var1);

    @NotNull
    public Configuration derive(Settings var1);

    @NotNull
    public Configuration deriveSettings(Function<? super Settings, ? extends Settings> var1);

    default public boolean commercial() {
        return true;
    }

    public boolean commercial(Supplier<String> var1);

    public boolean requireCommercial(Supplier<String> var1) throws DataAccessException;
}

