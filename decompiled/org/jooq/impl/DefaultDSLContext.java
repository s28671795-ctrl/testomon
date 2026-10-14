/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.jooq.AlterDatabaseStep;
import org.jooq.AlterDomainStep;
import org.jooq.AlterIndexOnStep;
import org.jooq.AlterSchemaStep;
import org.jooq.AlterSequenceStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterTypeStep;
import org.jooq.AlterViewStep;
import org.jooq.Attachable;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.BatchedCallable;
import org.jooq.BatchedRunnable;
import org.jooq.BindContext;
import org.jooq.Block;
import org.jooq.CallArgsStep;
import org.jooq.Catalog;
import org.jooq.CommentOnIsStep;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionCallable;
import org.jooq.ConnectionProvider;
import org.jooq.ConnectionRunnable;
import org.jooq.ContextTransactionalCallable;
import org.jooq.ContextTransactionalRunnable;
import org.jooq.CreateDatabaseFinalStep;
import org.jooq.CreateDomainAsStep;
import org.jooq.CreateFunctionParametersStep;
import org.jooq.CreateIndexStep;
import org.jooq.CreateProcedureParametersStep;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.CreateSequenceFlagsStep;
import org.jooq.CreateTableElementListStep;
import org.jooq.CreateTriggerEventStep;
import org.jooq.CreateTypeStep;
import org.jooq.CreateViewAsStep;
import org.jooq.Cursor;
import org.jooq.DDLExportConfiguration;
import org.jooq.DDLFlag;
import org.jooq.DDLQuery;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DeleteQuery;
import org.jooq.DeleteUsingStep;
import org.jooq.Domain;
import org.jooq.DropDatabaseFinalStep;
import org.jooq.DropDomainCascadeStep;
import org.jooq.DropIndexOnStep;
import org.jooq.DropSchemaStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropTypeStep;
import org.jooq.DropViewFinalStep;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Explain;
import org.jooq.Field;
import org.jooq.GrantOnStep;
import org.jooq.Index;
import org.jooq.InsertQuery;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.LoaderOptionsStep;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Meta;
import org.jooq.Migrations;
import org.jooq.Name;
import org.jooq.Param;
import org.jooq.Parser;
import org.jooq.Privilege;
import org.jooq.Pro;
import org.jooq.Publisher;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Records;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Results;
import org.jooq.RevokeOnStep;
import org.jooq.RollbackToSavepointStep;
import org.jooq.RowCountQuery;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectQuery;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.Source;
import org.jooq.Statement;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.TransactionProvider;
import org.jooq.TransactionalCallable;
import org.jooq.TransactionalPublishable;
import org.jooq.TransactionalRunnable;
import org.jooq.TruncateIdentityStep;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.UpdateSetFirstStep;
import org.jooq.WithAsStep;
import org.jooq.WithAsStep1;
import org.jooq.WithAsStep10;
import org.jooq.WithAsStep11;
import org.jooq.WithAsStep12;
import org.jooq.WithAsStep13;
import org.jooq.WithAsStep14;
import org.jooq.WithAsStep15;
import org.jooq.WithAsStep16;
import org.jooq.WithAsStep17;
import org.jooq.WithAsStep18;
import org.jooq.WithAsStep19;
import org.jooq.WithAsStep2;
import org.jooq.WithAsStep20;
import org.jooq.WithAsStep21;
import org.jooq.WithAsStep22;
import org.jooq.WithAsStep3;
import org.jooq.WithAsStep4;
import org.jooq.WithAsStep5;
import org.jooq.WithAsStep6;
import org.jooq.WithAsStep7;
import org.jooq.WithAsStep8;
import org.jooq.WithAsStep9;
import org.jooq.WithStep;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.exception.ConfigurationException;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DetachedException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.AlterDatabaseImpl;
import org.jooq.impl.AlterDomainImpl;
import org.jooq.impl.AlterIndexImpl;
import org.jooq.impl.AlterSchemaImpl;
import org.jooq.impl.AlterSequenceImpl;
import org.jooq.impl.AlterTableImpl;
import org.jooq.impl.AlterTypeImpl;
import org.jooq.impl.AlterViewImpl;
import org.jooq.impl.BatchCRUD;
import org.jooq.impl.BatchMultiple;
import org.jooq.impl.BatchSingle;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.Call;
import org.jooq.impl.CatalogMetaImpl;
import org.jooq.impl.CommentOnImpl;
import org.jooq.impl.Commit;
import org.jooq.impl.CreateDatabaseImpl;
import org.jooq.impl.CreateDomainImpl;
import org.jooq.impl.CreateFunctionImpl;
import org.jooq.impl.CreateIndexImpl;
import org.jooq.impl.CreateProcedureImpl;
import org.jooq.impl.CreateSchemaImpl;
import org.jooq.impl.CreateSequenceImpl;
import org.jooq.impl.CreateTableImpl;
import org.jooq.impl.CreateTriggerImpl;
import org.jooq.impl.CreateTypeImpl;
import org.jooq.impl.CreateViewImpl;
import org.jooq.impl.CursorImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultBindContext;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConnectionProvider;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.DefaultTransactionContext;
import org.jooq.impl.DeleteImpl;
import org.jooq.impl.DeleteQueryImpl;
import org.jooq.impl.DiagnosticsConnection;
import org.jooq.impl.DiagnosticsDataSource;
import org.jooq.impl.DropDatabaseImpl;
import org.jooq.impl.DropDomainImpl;
import org.jooq.impl.DropFunctionImpl;
import org.jooq.impl.DropIndexImpl;
import org.jooq.impl.DropProcedureImpl;
import org.jooq.impl.DropSchemaImpl;
import org.jooq.impl.DropSequenceImpl;
import org.jooq.impl.DropTableImpl;
import org.jooq.impl.DropTriggerImpl;
import org.jooq.impl.DropTypeImpl;
import org.jooq.impl.DropViewImpl;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.ExecutorProviderCompletionStage;
import org.jooq.impl.ExplainQuery;
import org.jooq.impl.FetchCount;
import org.jooq.impl.GrantImpl;
import org.jooq.impl.InformationSchemaExport;
import org.jooq.impl.InformationSchemaMetaImpl;
import org.jooq.impl.InsertImpl;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.InterpreterMetaProvider;
import org.jooq.impl.JSONReader;
import org.jooq.impl.LoaderImpl;
import org.jooq.impl.MergeImpl;
import org.jooq.impl.MetaDataFieldProvider;
import org.jooq.impl.MetaImpl;
import org.jooq.impl.MigrationsImpl;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.NoConnectionProvider;
import org.jooq.impl.ParamCollector;
import org.jooq.impl.ParserImpl;
import org.jooq.impl.ParsingConnection;
import org.jooq.impl.ParsingConnectionFactory;
import org.jooq.impl.ParsingDataSource;
import org.jooq.impl.QueriesImpl;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.R2DBC;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.ReleaseSavepoint;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.RevokeImpl;
import org.jooq.impl.Rollback;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SQLQuery;
import org.jooq.impl.SQLResultQuery;
import org.jooq.impl.Savepoint;
import org.jooq.impl.SelectImpl;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.SetCatalog;
import org.jooq.impl.SetCommand;
import org.jooq.impl.SetSchema;
import org.jooq.impl.SourceMetaProvider;
import org.jooq.impl.StartTransaction;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.jooq.impl.Tools;
import org.jooq.impl.TransactionListeners;
import org.jooq.impl.TruncateImpl;
import org.jooq.impl.UpdateImpl;
import org.jooq.impl.UpdateQueryImpl;
import org.jooq.impl.WithImpl;
import org.jooq.impl.XMLHandler;
import org.jooq.tools.csv.CSVReader;
import org.jooq.tools.jdbc.MockCallable;
import org.jooq.tools.jdbc.MockConfiguration;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockRunnable;
import org.jooq.util.xml.jaxb.InformationSchema;

public class DefaultDSLContext
extends AbstractScope
implements DSLContext,
Serializable {
    public DefaultDSLContext(SQLDialect dialect) {
        this(dialect, null);
    }

    public DefaultDSLContext(SQLDialect dialect, Settings settings) {
        this(new DefaultConfiguration(new NoConnectionProvider(), dialect, settings));
    }

    public DefaultDSLContext(Connection connection, SQLDialect dialect) {
        this(connection, dialect, null);
    }

    public DefaultDSLContext(Connection connection, SQLDialect dialect, Settings settings) {
        this(new DefaultConfiguration(new DefaultConnectionProvider(connection), dialect, settings));
    }

    public DefaultDSLContext(DataSource datasource, SQLDialect dialect) {
        this(datasource, dialect, null);
    }

    public DefaultDSLContext(DataSource datasource, SQLDialect dialect, Settings settings) {
        this(new DefaultConfiguration(new DataSourceConnectionProvider(datasource), dialect, settings));
    }

    public DefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect) {
        this(connectionProvider, dialect, null);
    }

    public DefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings) {
        this(new DefaultConfiguration(connectionProvider, dialect, settings));
    }

    public DefaultDSLContext(ConnectionFactory connectionFactory, SQLDialect dialect) {
        this(connectionFactory, dialect, null);
    }

    public DefaultDSLContext(ConnectionFactory connectionFactory, SQLDialect dialect, Settings settings) {
        this(new DefaultConfiguration().set(connectionFactory).set(dialect).set(settings));
    }

    public DefaultDSLContext(Configuration configuration) {
        super(configuration, configuration == null ? null : configuration.data());
    }

    @Override
    public Schema map(Schema schema) {
        return Tools.getMappedSchema(this, schema);
    }

    @Override
    public <R extends Record> Table<R> map(Table<R> table) {
        return Tools.getMappedTable(this, table);
    }

    @Override
    public Parser parser() {
        return new ParserImpl(this.configuration());
    }

    @Override
    public Connection parsingConnection() {
        return new ParsingConnection(this.configuration());
    }

    @Override
    public DataSource parsingDataSource() {
        return new ParsingDataSource(this.configuration());
    }

    @Override
    public ConnectionFactory parsingConnectionFactory() {
        return new ParsingConnectionFactory(this.configuration());
    }

    @Override
    public Connection diagnosticsConnection() {
        return new DiagnosticsConnection(this.configuration());
    }

    @Override
    public DataSource diagnosticsDataSource() {
        return new DiagnosticsDataSource(this.configuration());
    }

    @Override
    public Migrations migrations() {
        return new MigrationsImpl(this);
    }

    @Override
    public Meta meta() {
        return this.configuration().metaProvider().provide();
    }

    @Override
    public Meta meta(DatabaseMetaData meta) {
        return new MetaImpl(this.configuration(), meta);
    }

    @Override
    public Meta meta(Catalog ... catalogs) {
        return CatalogMetaImpl.filterCatalogs(this.configuration(), catalogs);
    }

    @Override
    public Meta meta(Schema ... schemas) {
        return CatalogMetaImpl.filterSchemas(this.configuration(), schemas);
    }

    @Override
    public Meta meta(Table<?> ... tables) {
        return CatalogMetaImpl.filterTables(this.configuration(), tables);
    }

    @Override
    public Meta meta(InformationSchema schema) {
        return new InformationSchemaMetaImpl(this.configuration(), schema);
    }

    @Override
    public Meta meta(String ... sources) {
        return new SourceMetaProvider(this.configuration(), Tools.map(sources, s2 -> Source.of(s2), Source[]::new)).provide();
    }

    @Override
    public Meta meta(Source ... sources) {
        return new SourceMetaProvider(this.configuration(), sources).provide();
    }

    @Override
    public Meta meta(Query ... queries) {
        return new InterpreterMetaProvider(this.configuration(), queries).provide();
    }

    @Override
    public InformationSchema informationSchema(Catalog catalog) {
        return InformationSchemaExport.exportCatalogs(this.configuration(), Arrays.asList(catalog));
    }

    @Override
    public InformationSchema informationSchema(Catalog ... catalogs) {
        return InformationSchemaExport.exportCatalogs(this.configuration(), Arrays.asList(catalogs));
    }

    @Override
    public InformationSchema informationSchema(Schema schema) {
        return InformationSchemaExport.exportSchemas(this.configuration(), Arrays.asList(schema));
    }

    @Override
    public InformationSchema informationSchema(Schema ... schemas) {
        return InformationSchemaExport.exportSchemas(this.configuration(), Arrays.asList(schemas));
    }

    @Override
    public InformationSchema informationSchema(Table<?> table) {
        return InformationSchemaExport.exportTables(this.configuration(), Arrays.asList(table));
    }

    @Override
    public InformationSchema informationSchema(Table<?> ... tables) {
        return InformationSchemaExport.exportTables(this.configuration(), Arrays.asList(tables));
    }

    @Override
    public Explain explain(Query query) {
        return ExplainQuery.explain(this, query);
    }

    @Override
    public <T> T transactionResult(ContextTransactionalCallable<T> transactional) {
        TransactionProvider tp = this.configuration().transactionProvider();
        if (!(tp instanceof ThreadLocalTransactionProvider)) {
            throw new ConfigurationException("Cannot use ContextTransactionalCallable with TransactionProvider of type " + String.valueOf(tp.getClass()));
        }
        return (T)DefaultDSLContext.transactionResult0(c -> transactional.run(), ((ThreadLocalTransactionProvider)tp).configuration(this.configuration()), true);
    }

    @Override
    public <T> T transactionResult(TransactionalCallable<T> transactional) {
        return DefaultDSLContext.transactionResult0(transactional, this.configuration(), false);
    }

    private static <T> T transactionResult0(TransactionalCallable<T> transactional, Configuration configuration, boolean threadLocal) {
        return (T)Tools.blocking(() -> {
            Object result;
            DefaultTransactionContext ctx = new DefaultTransactionContext(configuration.derive());
            TransactionProvider provider = ctx.configuration().transactionProvider();
            TransactionListeners listeners = new TransactionListeners(ctx.configuration());
            boolean committed = false;
            try {
                try {
                    listeners.beginStart(ctx);
                    provider.begin(ctx);
                }
                finally {
                    listeners.beginEnd(ctx);
                }
                result = transactional.run(ctx.configuration());
                try {
                    listeners.commitStart(ctx);
                    provider.commit(ctx);
                    committed = true;
                }
                finally {
                    listeners.commitEnd(ctx);
                }
            }
            catch (Throwable cause) {
                Exception e;
                if (!committed) {
                    if (cause instanceof Exception) {
                        e = (Exception)cause;
                        ctx.cause(e);
                    } else {
                        ctx.causeThrowable(cause);
                    }
                    listeners.rollbackStart(ctx);
                    try {
                        provider.rollback(ctx);
                    }
                    catch (Exception suppress) {
                        cause.addSuppressed(suppress);
                    }
                    listeners.rollbackEnd(ctx);
                }
                if (cause instanceof RuntimeException) {
                    e = (RuntimeException)cause;
                    throw e;
                }
                if (cause instanceof Error) {
                    Error e2 = (Error)cause;
                    throw e2;
                }
                throw new DataAccessException(committed ? "Exception after commit" : "Rollback caused", cause);
            }
            return result;
        }, threadLocal).get();
    }

    @Override
    public void transaction(ContextTransactionalRunnable transactional) {
        this.transactionResult(() -> {
            transactional.run();
            return null;
        });
    }

    @Override
    public void transaction(TransactionalRunnable transactional) {
        this.transactionResult((Configuration c) -> {
            transactional.run(c);
            return null;
        });
    }

    @Override
    public CompletionStage<Void> transactionAsync(TransactionalRunnable transactional) {
        return this.transactionAsync(Tools.configuration(this.configuration()).executorProvider().provide(), transactional);
    }

    @Override
    public CompletionStage<Void> transactionAsync(Executor executor2, TransactionalRunnable transactional) {
        if (this.configuration().transactionProvider() instanceof ThreadLocalTransactionProvider) {
            throw new ConfigurationException("Cannot use TransactionalRunnable with ThreadLocalTransactionProvider");
        }
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(() -> {
            this.transaction(transactional);
            return null;
        }, executor2), () -> executor2);
    }

    @Override
    public <T> CompletionStage<T> transactionResultAsync(TransactionalCallable<T> transactional) {
        return this.transactionResultAsync(Tools.configuration(this.configuration()).executorProvider().provide(), transactional);
    }

    @Override
    public <T> CompletionStage<T> transactionResultAsync(Executor executor2, TransactionalCallable<T> transactional) {
        if (this.configuration().transactionProvider() instanceof ThreadLocalTransactionProvider) {
            throw new ConfigurationException("Cannot use TransactionalCallable with ThreadLocalTransactionProvider");
        }
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(() -> this.transactionResult(transactional), executor2), () -> executor2);
    }

    @Override
    public <T> Publisher<T> transactionPublisher(TransactionalPublishable<T> transactional) {
        return subscriber -> {
            ConnectionFactory cf = this.configuration().connectionFactory();
            if (!(cf instanceof NoConnectionFactory)) {
                subscriber.onSubscribe(new R2DBC.TransactionSubscription(this, subscriber, transactional));
            } else {
                subscriber.onSubscribe(new R2DBC.BlockingTransactionSubscription(this, subscriber, transactional));
            }
        };
    }

    @Override
    public <T> T connectionResult(ConnectionCallable<T> callable) {
        if (this.data("org.jooq.workaround.issue13827") != null) {
            return this.connectionResult0(callable);
        }
        Connection connection = this.configuration().connectionProvider().acquire();
        if (connection == null) {
            throw new DetachedException("No JDBC Connection provided by ConnectionProvider");
        }
        try {
            T t2 = callable.run(connection);
            return t2;
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable t3) {
            throw new DataAccessException("Error while running ConnectionCallable", t3);
        }
        finally {
            this.configuration().connectionProvider().release(connection);
        }
    }

    private final <T> T connectionResult0(ConnectionCallable<T> callable) {
        DefaultExecuteContext ctx = new DefaultExecuteContext(this.configuration());
        Connection connection = ctx.connection();
        if (connection == null) {
            throw new DetachedException("No JDBC Connection provided by ConnectionProvider");
        }
        return (T)DefaultExecuteContext.localExecuteContext((ExecuteContext)ctx, () -> {
            try {
                Object t2 = callable.run(connection);
                return t2;
            }
            catch (Error | RuntimeException e) {
                throw e;
            }
            catch (Throwable t3) {
                throw new DataAccessException("Error while running ConnectionCallable", t3);
            }
            finally {
                Tools.safeClose(new DefaultExecuteListener(), ctx);
            }
        });
    }

    @Override
    public void connection(ConnectionRunnable runnable2) {
        this.connectionResult(connection -> {
            runnable2.run(connection);
            return null;
        });
    }

    @Override
    public <T> T mockResult(MockDataProvider provider, MockCallable<T> mockable) {
        try {
            return mockable.run(new MockConfiguration(this.configuration, provider));
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception cause) {
            throw new DataAccessException("Mock failed", cause);
        }
    }

    @Override
    public void mock(MockDataProvider provider, MockRunnable mockable) {
        this.mockResult(provider, c -> {
            mockable.run(c);
            return null;
        });
    }

    @Override
    public RenderContext renderContext() {
        return new DefaultRenderContext(this.configuration(), null);
    }

    @Override
    public String render(QueryPart part) {
        return ((RenderContext)this.renderContext().visit(part)).render();
    }

    @Override
    public String renderNamedParams(QueryPart part) {
        return ((RenderContext)((RenderContext)this.renderContext().paramType(ParamType.NAMED)).visit(part)).render();
    }

    @Override
    public String renderNamedOrInlinedParams(QueryPart part) {
        return ((RenderContext)((RenderContext)this.renderContext().paramType(ParamType.NAMED_OR_INLINED)).visit(part)).render();
    }

    @Override
    public String renderInlined(QueryPart part) {
        return ((RenderContext)((RenderContext)this.renderContext().paramType(ParamType.INLINED)).visit(part)).render();
    }

    @Override
    public List<Object> extractBindValues(QueryPart part) {
        ParamCollector collector = new ParamCollector(this.configuration(), false);
        collector.visit(part);
        return Collections.unmodifiableList(Tools.map(collector.resultList, e -> ((Param)e.getValue()).getValue()));
    }

    @Override
    public Map<String, Param<?>> extractParams(QueryPart part) {
        ParamCollector collector = new ParamCollector(this.configuration(), true);
        collector.visit(part);
        return Collections.unmodifiableMap(collector.resultFlat);
    }

    @Override
    public Param<?> extractParam(QueryPart part, String name) {
        return this.extractParams(part).get(name);
    }

    @Override
    public BindContext bindContext(PreparedStatement stmt) {
        return new DefaultBindContext(this.configuration(), null, stmt);
    }

    @Override
    public void attach(Attachable ... attachables) {
        this.attach(Arrays.asList(attachables));
    }

    @Override
    public void attach(Collection<? extends Attachable> attachables) {
        for (Attachable attachable : attachables) {
            attachable.attach(this.configuration());
        }
    }

    @Override
    public <R extends Record> LoaderOptionsStep<R> loadInto(Table<R> table) {
        return new LoaderImpl<R>(this.configuration(), table);
    }

    @Override
    public Queries queries(Query ... queries) {
        return this.queries(Arrays.asList(queries));
    }

    @Override
    public Queries queries(Collection<? extends Query> queries) {
        return new QueriesImpl(this.configuration(), queries);
    }

    @Override
    public Block begin(Statement ... statements) {
        return this.begin(Arrays.asList(statements));
    }

    @Override
    public Block begin(Collection<? extends Statement> statements) {
        return new BlockImpl(this.configuration(), statements, true);
    }

    @Override
    @Pro
    public Block statements(Statement ... statements) {
        return this.statements(Arrays.asList(statements));
    }

    @Override
    @Pro
    public Block statements(Collection<? extends Statement> statements) {
        return new BlockImpl(this.configuration(), statements, false);
    }

    @Override
    public RowCountQuery query(SQL sql) {
        return new SQLQuery(this.configuration(), sql);
    }

    @Override
    public RowCountQuery query(String sql) {
        return this.query(sql, new Object[0]);
    }

    @Override
    public RowCountQuery query(String sql, Object ... bindings) {
        return this.query(DSL.sql(sql, bindings));
    }

    @Override
    public RowCountQuery query(String sql, QueryPart ... parts) {
        return this.query(sql, (Object[])parts);
    }

    @Override
    public Result<Record> fetch(SQL sql) {
        return this.resultQuery(sql).fetch();
    }

    @Override
    public Result<Record> fetch(String sql) {
        return this.resultQuery(sql).fetch();
    }

    @Override
    public Result<Record> fetch(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetch();
    }

    @Override
    public Result<Record> fetch(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetch();
    }

    @Override
    public Cursor<Record> fetchLazy(SQL sql) {
        return this.resultQuery(sql).fetchLazy();
    }

    @Override
    public Cursor<Record> fetchLazy(String sql) {
        return this.resultQuery(sql).fetchLazy();
    }

    @Override
    public Cursor<Record> fetchLazy(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetchLazy();
    }

    @Override
    public Cursor<Record> fetchLazy(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetchLazy();
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(SQL sql) {
        return this.resultQuery(sql).fetchAsync();
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(String sql) {
        return this.resultQuery(sql).fetchAsync();
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetchAsync();
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetchAsync();
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, SQL sql) {
        return this.resultQuery(sql).fetchAsync(executor2);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, String sql) {
        return this.resultQuery(sql).fetchAsync(executor2);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetchAsync(executor2);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetchAsync(executor2);
    }

    @Override
    public Stream<Record> fetchStream(SQL sql) {
        return this.resultQuery(sql).stream();
    }

    @Override
    public Stream<Record> fetchStream(String sql) {
        return this.resultQuery(sql).stream();
    }

    @Override
    public Stream<Record> fetchStream(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).stream();
    }

    @Override
    public Stream<Record> fetchStream(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).stream();
    }

    @Override
    public Results fetchMany(SQL sql) {
        return this.resultQuery(sql).fetchMany();
    }

    @Override
    public Results fetchMany(String sql) {
        return this.resultQuery(sql).fetchMany();
    }

    @Override
    public Results fetchMany(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetchMany();
    }

    @Override
    public Results fetchMany(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetchMany();
    }

    @Override
    public Record fetchOne(SQL sql) {
        return this.resultQuery(sql).fetchOne();
    }

    @Override
    public Record fetchOne(String sql) {
        return this.resultQuery(sql).fetchOne();
    }

    @Override
    public Record fetchOne(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetchOne();
    }

    @Override
    public Record fetchOne(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetchOne();
    }

    @Override
    public Record fetchSingle(SQL sql) {
        return this.resultQuery(sql).fetchSingle();
    }

    @Override
    public Record fetchSingle(String sql) {
        return this.resultQuery(sql).fetchSingle();
    }

    @Override
    public Record fetchSingle(String sql, Object ... bindings) {
        return this.resultQuery(sql, bindings).fetchSingle();
    }

    @Override
    public Record fetchSingle(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, parts).fetchSingle();
    }

    @Override
    public Optional<Record> fetchOptional(SQL sql) {
        return Optional.ofNullable(this.fetchOne(sql));
    }

    @Override
    public Optional<Record> fetchOptional(String sql) {
        return Optional.ofNullable(this.fetchOne(sql));
    }

    @Override
    public Optional<Record> fetchOptional(String sql, Object ... bindings) {
        return Optional.ofNullable(this.fetchOne(sql, bindings));
    }

    @Override
    public Optional<Record> fetchOptional(String sql, QueryPart ... parts) {
        return Optional.ofNullable(this.fetchOne(sql, parts));
    }

    @Override
    public Object fetchValue(SQL sql) {
        return this.fetchValue(this.resultQuery(sql));
    }

    @Override
    public Object fetchValue(String sql) {
        return this.fetchValue(this.resultQuery(sql));
    }

    @Override
    public Object fetchValue(String sql, Object ... bindings) {
        return this.fetchValue(this.resultQuery(sql, bindings));
    }

    @Override
    public Object fetchValue(String sql, QueryPart ... parts) {
        return this.fetchValue(this.resultQuery(sql, parts));
    }

    @Override
    public Optional<?> fetchOptionalValue(SQL sql) {
        return Optional.ofNullable(this.fetchValue(sql));
    }

    @Override
    public Optional<?> fetchOptionalValue(String sql) {
        return Optional.ofNullable(this.fetchValue(sql));
    }

    @Override
    public Optional<?> fetchOptionalValue(String sql, Object ... bindings) {
        return Optional.ofNullable(this.fetchValue(sql, bindings));
    }

    @Override
    public Optional<?> fetchOptionalValue(String sql, QueryPart ... parts) {
        return Optional.ofNullable(this.fetchValue(sql, parts));
    }

    @Override
    public List<?> fetchValues(SQL sql) {
        return this.fetchValues(this.resultQuery(sql));
    }

    @Override
    public List<?> fetchValues(String sql) {
        return this.fetchValues(this.resultQuery(sql));
    }

    @Override
    public List<?> fetchValues(String sql, Object ... bindings) {
        return this.fetchValues(this.resultQuery(sql, bindings));
    }

    @Override
    public List<?> fetchValues(String sql, QueryPart ... parts) {
        return this.fetchValues(this.resultQuery(sql, parts));
    }

    @Override
    public int execute(SQL sql) {
        return this.query(sql).execute();
    }

    @Override
    public int execute(String sql) {
        return this.query(sql).execute();
    }

    @Override
    public int execute(String sql, Object ... bindings) {
        return this.query(sql, bindings).execute();
    }

    @Override
    public int execute(String sql, QueryPart ... parts) {
        return this.query(sql, (Object[])parts).execute();
    }

    @Override
    public ResultQuery<Record> resultQuery(SQL sql) {
        return new SQLResultQuery(this.configuration(), sql);
    }

    @Override
    public ResultQuery<Record> resultQuery(String sql) {
        return this.resultQuery(sql, new Object[0]);
    }

    @Override
    public ResultQuery<Record> resultQuery(String sql, Object ... bindings) {
        return this.resultQuery(DSL.sql(sql, bindings));
    }

    @Override
    public ResultQuery<Record> resultQuery(String sql, QueryPart ... parts) {
        return this.resultQuery(sql, (Object[])parts);
    }

    @Override
    public Result<Record> fetch(ResultSet rs) {
        return this.fetchLazy(rs).fetch();
    }

    @Override
    public Result<Record> fetch(ResultSet rs, Field<?> ... fields) {
        return this.fetchLazy(rs, fields).fetch();
    }

    @Override
    public Result<Record> fetch(ResultSet rs, DataType<?> ... types) {
        return this.fetchLazy(rs, types).fetch();
    }

    @Override
    public Result<Record> fetch(ResultSet rs, Class<?> ... types) {
        return this.fetchLazy(rs, types).fetch();
    }

    @Override
    public Record fetchOne(ResultSet rs) {
        return Tools.fetchOne(this.fetchLazy(rs));
    }

    @Override
    public Record fetchOne(ResultSet rs, Field<?> ... fields) {
        return Tools.fetchOne(this.fetchLazy(rs, fields));
    }

    @Override
    public Record fetchOne(ResultSet rs, DataType<?> ... types) {
        return Tools.fetchOne(this.fetchLazy(rs, types));
    }

    @Override
    public Record fetchOne(ResultSet rs, Class<?> ... types) {
        return Tools.fetchOne(this.fetchLazy(rs, types));
    }

    @Override
    public Record fetchSingle(ResultSet rs) {
        return Tools.fetchSingle(this.fetchLazy(rs));
    }

    @Override
    public Record fetchSingle(ResultSet rs, Field<?> ... fields) {
        return Tools.fetchSingle(this.fetchLazy(rs, fields));
    }

    @Override
    public Record fetchSingle(ResultSet rs, DataType<?> ... types) {
        return Tools.fetchSingle(this.fetchLazy(rs, types));
    }

    @Override
    public Record fetchSingle(ResultSet rs, Class<?> ... types) {
        return Tools.fetchSingle(this.fetchLazy(rs, types));
    }

    @Override
    public Optional<Record> fetchOptional(ResultSet rs) {
        return Optional.ofNullable(this.fetchOne(rs));
    }

    @Override
    public Optional<Record> fetchOptional(ResultSet rs, Field<?> ... fields) {
        return Optional.ofNullable(this.fetchOne(rs, fields));
    }

    @Override
    public Optional<Record> fetchOptional(ResultSet rs, DataType<?> ... types) {
        return Optional.ofNullable(this.fetchOne(rs, types));
    }

    @Override
    public Optional<Record> fetchOptional(ResultSet rs, Class<?> ... types) {
        return Optional.ofNullable(this.fetchOne(rs, types));
    }

    @Override
    public Object fetchValue(ResultSet rs) {
        return this.value1((Record1)this.fetchOne(rs));
    }

    @Override
    public <T> T fetchValue(ResultSet rs, Field<T> field2) {
        return this.value1((Record1)this.fetchOne(rs, field2));
    }

    @Override
    public <T> T fetchValue(ResultSet rs, DataType<T> type) {
        return this.value1((Record1)this.fetchOne(rs, type));
    }

    @Override
    public <T> T fetchValue(ResultSet rs, Class<T> type) {
        return this.value1((Record1)this.fetchOne(rs, type));
    }

    @Override
    public Optional<?> fetchOptionalValue(ResultSet rs) {
        return Optional.ofNullable(this.fetchValue(rs));
    }

    @Override
    public <T> Optional<T> fetchOptionalValue(ResultSet rs, Field<T> field2) {
        return Optional.ofNullable(this.fetchValue(rs, field2));
    }

    @Override
    public <T> Optional<T> fetchOptionalValue(ResultSet rs, DataType<T> type) {
        return Optional.ofNullable(this.fetchValue(rs, type));
    }

    @Override
    public <T> Optional<T> fetchOptionalValue(ResultSet rs, Class<T> type) {
        return Optional.ofNullable(this.fetchValue(rs, type));
    }

    @Override
    public List<?> fetchValues(ResultSet rs) {
        return this.fetch(rs).getValues(0);
    }

    @Override
    public <T> List<T> fetchValues(ResultSet rs, Field<T> field2) {
        return this.fetch(rs).getValues(field2);
    }

    @Override
    public <T> List<T> fetchValues(ResultSet rs, DataType<T> type) {
        return this.fetch(rs).getValues(0, type.getType());
    }

    @Override
    public <T> List<T> fetchValues(ResultSet rs, Class<T> type) {
        return this.fetch(rs).getValues(0, type);
    }

    @Override
    public Cursor<Record> fetchLazy(ResultSet rs) {
        try {
            return this.fetchLazy(rs, new MetaDataFieldProvider(this.configuration(), rs.getMetaData()).getFields());
        }
        catch (SQLException e) {
            throw new DataAccessException("Error while accessing ResultSet meta data", e);
        }
    }

    @Override
    public Cursor<Record> fetchLazy(ResultSet rs, Field<?> ... fields) {
        DefaultExecuteContext ctx = new DefaultExecuteContext(this.configuration());
        ExecuteListener listener = ExecuteListeners.getAndStart(ctx);
        ctx.resultSet(rs);
        return new CursorImpl<Record>(ctx, listener, fields, null, false, true);
    }

    @Override
    public Cursor<Record> fetchLazy(ResultSet rs, DataType<?> ... types) {
        try {
            Field[] fields = new Field[types.length];
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();
            for (int i = 0; i < types.length && i < columns; ++i) {
                fields[i] = DSL.field(meta.getColumnLabel(i + 1), types[i]);
            }
            return this.fetchLazy(rs, fields);
        }
        catch (SQLException e) {
            throw new DataAccessException("Error while accessing ResultSet meta data", e);
        }
    }

    @Override
    public Cursor<Record> fetchLazy(ResultSet rs, Class<?> ... types) {
        return this.fetchLazy(rs, Tools.dataTypes(types));
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(ResultSet rs) {
        return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(ResultSet rs, Field<?> ... fields) {
        return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs, fields);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(ResultSet rs, DataType<?> ... types) {
        return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs, types);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(ResultSet rs, Class<?> ... types) {
        return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), rs, types);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, ResultSet rs) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> this.fetch(rs)), executor2), () -> executor2);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, ResultSet rs, Field<?> ... fields) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> this.fetch(rs, fields)), executor2), () -> executor2);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, ResultSet rs, DataType<?> ... types) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> this.fetch(rs, types)), executor2), () -> executor2);
    }

    @Override
    public CompletionStage<Result<Record>> fetchAsync(Executor executor2, ResultSet rs, Class<?> ... types) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> this.fetch(rs, types)), executor2), () -> executor2);
    }

    @Override
    public Stream<Record> fetchStream(ResultSet rs) {
        return this.fetchLazy(rs).stream();
    }

    @Override
    public Stream<Record> fetchStream(ResultSet rs, Field<?> ... fields) {
        return this.fetchLazy(rs, fields).stream();
    }

    @Override
    public Stream<Record> fetchStream(ResultSet rs, DataType<?> ... types) {
        return this.fetchLazy(rs, types).stream();
    }

    @Override
    public Stream<Record> fetchStream(ResultSet rs, Class<?> ... types) {
        return this.fetchLazy(rs, types).stream();
    }

    @Override
    public Result<Record> fetchFromTXT(String string) {
        return this.fetchFromTXT(string, "{null}");
    }

    @Override
    public Result<Record> fetchFromTXT(String string, String nullLiteral) {
        return this.fetchFromStringData(Tools.parseTXT(string, nullLiteral));
    }

    @Override
    public Result<Record> fetchFromHTML(String string) {
        return this.fetchFromStringData(Tools.parseHTML(string));
    }

    @Override
    public Result<Record> fetchFromCSV(String string) {
        return this.fetchFromCSV(string, true, ',');
    }

    @Override
    public Result<Record> fetchFromCSV(String string, char delimiter) {
        return this.fetchFromCSV(string, true, delimiter);
    }

    @Override
    public Result<Record> fetchFromCSV(String string, boolean header) {
        return this.fetchFromCSV(string, header, ',');
    }

    @Override
    public Result<Record> fetchFromCSV(String string, boolean header, char delimiter) {
        CSVReader reader = new CSVReader(new StringReader(string), delimiter);
        List<String[]> list = null;
        try {
            list = reader.readAll();
        }
        catch (IOException e) {
            throw new DataAccessException("Could not read the CSV string", e);
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException iOException) {}
        }
        return this.fetchFromStringData(list, header);
    }

    @Override
    public Result<Record> fetchFromJSON(String string) {
        return new JSONReader(this, null, null, false).read(string);
    }

    @Override
    public Result<Record> fetchFromXML(String string) {
        return new XMLHandler(this, null, null).read(string);
    }

    @Override
    public Result<Record> fetchFromStringData(String[] ... strings) {
        return this.fetchFromStringData(Tools.list(strings), true);
    }

    @Override
    public Result<Record> fetchFromStringData(List<String[]> strings) {
        return this.fetchFromStringData(strings, true);
    }

    @Override
    public Result<Record> fetchFromStringData(List<String[]> strings, boolean header) {
        if (strings.size() == 0) {
            return new ResultImpl<Record>(this.configuration(), new Field[0]);
        }
        String[] firstRow = strings.get(0);
        int firstRowIndex = header ? 1 : 0;
        Field[] fields = header ? Tools.map(firstRow, s2 -> DSL.field(DSL.name(s2), String.class), Field[]::new) : Tools.map(firstRow, (s2, i) -> DSL.field(DSL.name("COL" + (i + 1)), String.class), Field[]::new);
        AbstractRow<?> row = Tools.row0(fields);
        ResultImpl<Record> result = new ResultImpl<Record>(this.configuration(), row);
        if (strings.size() > firstRowIndex) {
            for (String[] values2 : strings.subList(firstRowIndex, strings.size())) {
                RecordImplN record = new RecordImplN(row);
                for (int i2 = 0; i2 < Math.min(values2.length, fields.length); ++i2) {
                    record.values[i2] = values2[i2];
                    record.originals[i2] = values2[i2];
                }
                result.add(record);
            }
        }
        return result;
    }

    @Override
    public WithAsStep with(String alias) {
        return new WithImpl(this.configuration(), false).with(alias);
    }

    @Override
    public WithAsStep with(String alias, String ... fieldAliases) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAliases);
    }

    @Override
    public WithAsStep with(String alias, Collection<String> fieldAliases) {
        return new WithImpl(this.configuration(), false).with(alias, (Collection)fieldAliases);
    }

    @Override
    public WithAsStep with(Name alias) {
        return new WithImpl(this.configuration(), false).with(alias);
    }

    @Override
    public WithAsStep with(Name alias, Name ... fieldAliases) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAliases);
    }

    @Override
    public WithAsStep with(Name alias, Collection<? extends Name> fieldAliases) {
        return new WithImpl(this.configuration(), false).with(alias, (Collection)fieldAliases);
    }

    @Override
    public WithAsStep with(String alias, Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return new WithImpl(this.configuration(), false).with(alias, (Function)fieldNameFunction);
    }

    @Override
    public WithAsStep with(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        return new WithImpl(this.configuration(), false).with(alias, (BiFunction)fieldNameFunction);
    }

    @Override
    public WithAsStep1 with(String alias, String fieldAlias1) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1);
    }

    @Override
    public WithAsStep2 with(String alias, String fieldAlias1, String fieldAlias2) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2);
    }

    @Override
    public WithAsStep3 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Override
    public WithAsStep4 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Override
    public WithAsStep5 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Override
    public WithAsStep6 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Override
    public WithAsStep7 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Override
    public WithAsStep8 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Override
    public WithAsStep9 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Override
    public WithAsStep10 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Override
    public WithAsStep11 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Override
    public WithAsStep12 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Override
    public WithAsStep13 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Override
    public WithAsStep14 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Override
    public WithAsStep15 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Override
    public WithAsStep16 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Override
    public WithAsStep17 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Override
    public WithAsStep18 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Override
    public WithAsStep19 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Override
    public WithAsStep20 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Override
    public WithAsStep21 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Override
    public WithAsStep22 with(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Override
    public WithAsStep1 with(Name alias, Name fieldAlias1) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1);
    }

    @Override
    public WithAsStep2 with(Name alias, Name fieldAlias1, Name fieldAlias2) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2);
    }

    @Override
    public WithAsStep3 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Override
    public WithAsStep4 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Override
    public WithAsStep5 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Override
    public WithAsStep6 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Override
    public WithAsStep7 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Override
    public WithAsStep8 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Override
    public WithAsStep9 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Override
    public WithAsStep10 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Override
    public WithAsStep11 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Override
    public WithAsStep12 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Override
    public WithAsStep13 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Override
    public WithAsStep14 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Override
    public WithAsStep15 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Override
    public WithAsStep16 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Override
    public WithAsStep17 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Override
    public WithAsStep18 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Override
    public WithAsStep19 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Override
    public WithAsStep20 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Override
    public WithAsStep21 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Override
    public WithAsStep22 with(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21, Name fieldAlias22) {
        return new WithImpl(this.configuration(), false).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Override
    public WithStep with(CommonTableExpression<?> ... tables) {
        return new WithImpl(this.configuration(), false).with((CommonTableExpression[])tables);
    }

    @Override
    public WithStep with(Collection<? extends CommonTableExpression<?>> tables) {
        return new WithImpl(this.configuration(), false).with((Collection)tables);
    }

    @Override
    public WithAsStep withRecursive(String alias) {
        return new WithImpl(this.configuration(), true).with(alias);
    }

    @Override
    public WithAsStep withRecursive(String alias, String ... fieldAliases) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAliases);
    }

    @Override
    public WithAsStep withRecursive(String alias, Collection<String> fieldAliases) {
        return new WithImpl(this.configuration(), true).with(alias, (Collection)fieldAliases);
    }

    @Override
    public WithAsStep withRecursive(Name alias) {
        return new WithImpl(this.configuration(), true).with(alias);
    }

    @Override
    public WithAsStep withRecursive(Name alias, Name ... fieldAliases) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAliases);
    }

    @Override
    public WithAsStep withRecursive(Name alias, Collection<? extends Name> fieldAliases) {
        return new WithImpl(this.configuration(), true).with(alias, (Collection)fieldAliases);
    }

    @Override
    public WithAsStep withRecursive(String alias, Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return new WithImpl(this.configuration(), true).with(alias, (Function)fieldNameFunction);
    }

    @Override
    public WithAsStep withRecursive(String alias, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        return new WithImpl(this.configuration(), true).with(alias, (BiFunction)fieldNameFunction);
    }

    @Override
    public WithAsStep1 withRecursive(String alias, String fieldAlias1) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1);
    }

    @Override
    public WithAsStep2 withRecursive(String alias, String fieldAlias1, String fieldAlias2) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2);
    }

    @Override
    public WithAsStep3 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Override
    public WithAsStep4 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Override
    public WithAsStep5 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Override
    public WithAsStep6 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Override
    public WithAsStep7 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Override
    public WithAsStep8 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Override
    public WithAsStep9 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Override
    public WithAsStep10 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Override
    public WithAsStep11 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Override
    public WithAsStep12 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Override
    public WithAsStep13 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Override
    public WithAsStep14 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Override
    public WithAsStep15 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Override
    public WithAsStep16 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Override
    public WithAsStep17 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Override
    public WithAsStep18 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Override
    public WithAsStep19 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Override
    public WithAsStep20 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Override
    public WithAsStep21 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Override
    public WithAsStep22 withRecursive(String alias, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Override
    public WithAsStep1 withRecursive(Name alias, Name fieldAlias1) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1);
    }

    @Override
    public WithAsStep2 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2);
    }

    @Override
    public WithAsStep3 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3);
    }

    @Override
    public WithAsStep4 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4);
    }

    @Override
    public WithAsStep5 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5);
    }

    @Override
    public WithAsStep6 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6);
    }

    @Override
    public WithAsStep7 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7);
    }

    @Override
    public WithAsStep8 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8);
    }

    @Override
    public WithAsStep9 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9);
    }

    @Override
    public WithAsStep10 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10);
    }

    @Override
    public WithAsStep11 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11);
    }

    @Override
    public WithAsStep12 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12);
    }

    @Override
    public WithAsStep13 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13);
    }

    @Override
    public WithAsStep14 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14);
    }

    @Override
    public WithAsStep15 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15);
    }

    @Override
    public WithAsStep16 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16);
    }

    @Override
    public WithAsStep17 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17);
    }

    @Override
    public WithAsStep18 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18);
    }

    @Override
    public WithAsStep19 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19);
    }

    @Override
    public WithAsStep20 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20);
    }

    @Override
    public WithAsStep21 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21);
    }

    @Override
    public WithAsStep22 withRecursive(Name alias, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21, Name fieldAlias22) {
        return new WithImpl(this.configuration(), true).with(alias, fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22);
    }

    @Override
    public WithStep withRecursive(CommonTableExpression<?> ... tables) {
        return new WithImpl(this.configuration(), true).with((CommonTableExpression[])tables);
    }

    @Override
    public WithStep withRecursive(Collection<? extends CommonTableExpression<?>> tables) {
        return new WithImpl(this.configuration(), true).with((Collection)tables);
    }

    @Override
    public <R extends Record> SelectWhereStep<R> selectFrom(TableLike<R> table) {
        return new SelectImpl(this.configuration(), null).from((TableLike)table);
    }

    @Override
    public SelectWhereStep<Record> selectFrom(Name table) {
        return new SelectImpl(this.configuration(), null).from(table);
    }

    @Override
    public SelectWhereStep<Record> selectFrom(SQL sql) {
        return new SelectImpl(this.configuration(), null).from(sql);
    }

    @Override
    public SelectWhereStep<Record> selectFrom(String sql) {
        return new SelectImpl(this.configuration(), null).from(sql);
    }

    @Override
    public SelectWhereStep<Record> selectFrom(String sql, Object ... bindings) {
        return new SelectImpl(this.configuration(), null).from(sql, bindings);
    }

    @Override
    public SelectWhereStep<Record> selectFrom(String sql, QueryPart ... parts) {
        return new SelectImpl(this.configuration(), null).from(sql, parts);
    }

    @Override
    public SelectSelectStep<Record> select(Collection<? extends SelectFieldOrAsterisk> fields) {
        return new SelectImpl(this.configuration(), null).select((Collection)fields);
    }

    @Override
    public SelectSelectStep<Record> select(SelectFieldOrAsterisk ... fields) {
        return new SelectImpl(this.configuration(), null).select(fields);
    }

    @Override
    public <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> field1) {
        return this.select(new SelectField[]{field1});
    }

    @Override
    public <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> field1, SelectField<T2> field2) {
        return this.select(new SelectField[]{field1, field2});
    }

    @Override
    public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.select(new SelectField[]{field1, field2, field3});
    }

    @Override
    public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.select(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public SelectSelectStep<Record> selectDistinct(Collection<? extends SelectFieldOrAsterisk> fields) {
        return new SelectImpl(this.configuration(), null, true).select((Collection)fields);
    }

    @Override
    public SelectSelectStep<Record> selectDistinct(SelectFieldOrAsterisk ... fields) {
        return new SelectImpl(this.configuration(), null, true).select(fields);
    }

    @Override
    public <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> field1) {
        return this.selectDistinct(new SelectField[]{field1});
    }

    @Override
    public <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2) {
        return this.selectDistinct(new SelectField[]{field1, field2});
    }

    @Override
    public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3});
    }

    @Override
    public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public SelectSelectStep<Record1<Integer>> selectZero() {
        return new SelectImpl(this.configuration(), null).select(DSL.zero().as("zero"));
    }

    @Override
    public SelectSelectStep<Record1<Integer>> selectOne() {
        return new SelectImpl(this.configuration(), null).select(DSL.one().as("one"));
    }

    @Override
    public SelectSelectStep<Record1<Integer>> selectCount() {
        return new SelectImpl(this.configuration(), null).select(DSL.count());
    }

    @Override
    public SelectQuery<Record> selectQuery() {
        return new SelectQueryImpl<Record>(this.configuration(), null);
    }

    @Override
    public <R extends Record> SelectQuery<R> selectQuery(TableLike<R> table) {
        return new SelectQueryImpl<R>(this.configuration(), null, table);
    }

    @Override
    public <R extends Record> InsertQuery<R> insertQuery(Table<R> into) {
        return new InsertQueryImpl<R>(this.configuration(), null, into);
    }

    @Override
    public <R extends Record> InsertSetStep<R> insertInto(Table<R> into) {
        return new InsertImpl(this.configuration(), null, into, Collections.emptyList());
    }

    @Override
    public <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> into, Field<T1> field1) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1));
    }

    @Override
    public <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2));
    }

    @Override
    public <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    @Override
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    @Override
    public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Field<?> ... fields) {
        return new InsertImpl(this.configuration(), null, into, Arrays.asList(fields));
    }

    @Override
    public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields) {
        return new InsertImpl(this.configuration(), null, into, fields);
    }

    @Override
    public <R extends Record> UpdateQuery<R> updateQuery(Table<R> table) {
        return new UpdateQueryImpl<R>(this.configuration(), null, table);
    }

    @Override
    public <R extends Record> UpdateSetFirstStep<R> update(Table<R> table) {
        return new UpdateImpl<R>(this.configuration(), null, table);
    }

    @Override
    public <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table) {
        return new MergeImpl(this.configuration(), null, table);
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> table, Field<T1> field1) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    @Override
    @Deprecated(forRemoval=true, since="3.14")
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return new MergeImpl(this.configuration(), null, table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    @Override
    public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Field<?> ... fields) {
        return this.mergeInto(table, Arrays.asList(fields));
    }

    @Override
    public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Collection<? extends Field<?>> fields) {
        return new MergeImpl(this.configuration(), null, table, fields);
    }

    @Override
    public <R extends Record> DeleteQuery<R> deleteQuery(Table<R> table) {
        return new DeleteQueryImpl<R>(this.configuration(), null, table);
    }

    @Override
    public <R extends Record> DeleteUsingStep<R> delete(Table<R> table) {
        return this.deleteFrom(table);
    }

    @Override
    public <R extends Record> DeleteUsingStep<R> deleteFrom(Table<R> table) {
        return new DeleteImpl<R>(this.configuration(), null, table);
    }

    @Override
    public void batched(BatchedRunnable runnable2) {
        this.batchedResult(c -> {
            runnable2.run(c);
            return null;
        });
    }

    @Override
    public <T> T batchedResult(BatchedCallable<T> callable) {
        return (T)this.connectionResult(connection -> {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1050)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        });
    }

    @Override
    public Batch batch(Query ... queries) {
        return new BatchMultiple(this.configuration(), queries);
    }

    @Override
    public Batch batch(Queries queries) {
        return this.batch(queries.queries());
    }

    @Override
    public Batch batch(String ... queries) {
        return this.batch(Tools.map(queries, q -> this.query((String)q), Query[]::new));
    }

    @Override
    public Batch batch(Collection<? extends Query> queries) {
        return this.batch(queries.toArray(Tools.EMPTY_QUERY));
    }

    @Override
    public BatchBindStep batch(Query query) {
        return new BatchSingle(this.configuration(), query);
    }

    @Override
    public BatchBindStep batch(String sql) {
        return this.batch((Query)this.query(sql));
    }

    @Override
    public Batch batch(Query query, Object[] ... bindings) {
        return this.batch(query).bind(bindings);
    }

    @Override
    public Batch batch(String sql, Object[] ... bindings) {
        return this.batch((Query)this.query(sql), bindings);
    }

    @Override
    public Batch batchStore(UpdatableRecord<?> ... records) {
        return new BatchCRUD(this.configuration(), BatchCRUD.Action.STORE, records);
    }

    @Override
    public Batch batchStore(Collection<? extends UpdatableRecord<?>> records) {
        return this.batchStore(records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
    }

    @Override
    public Batch batchInsert(TableRecord<?> ... records) {
        return new BatchCRUD(this.configuration(), BatchCRUD.Action.INSERT, records);
    }

    @Override
    public Batch batchInsert(Collection<? extends TableRecord<?>> records) {
        return this.batchInsert(records.toArray(Tools.EMPTY_TABLE_RECORD));
    }

    @Override
    public Batch batchUpdate(UpdatableRecord<?> ... records) {
        return new BatchCRUD(this.configuration(), BatchCRUD.Action.UPDATE, records);
    }

    @Override
    public Batch batchUpdate(Collection<? extends UpdatableRecord<?>> records) {
        return this.batchUpdate(records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
    }

    @Override
    public Batch batchMerge(UpdatableRecord<?> ... records) {
        return new BatchCRUD(this.configuration(), BatchCRUD.Action.MERGE, records);
    }

    @Override
    public Batch batchMerge(Collection<? extends UpdatableRecord<?>> records) {
        return this.batchMerge(records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
    }

    @Override
    public Batch batchDelete(UpdatableRecord<?> ... records) {
        return new BatchCRUD(this.configuration(), BatchCRUD.Action.DELETE, records);
    }

    @Override
    public Batch batchDelete(Collection<? extends UpdatableRecord<?>> records) {
        return this.batchDelete(records.toArray(Tools.EMPTY_UPDATABLE_RECORD));
    }

    @Override
    public AlterDatabaseStep alterDatabase(String database) {
        return new AlterDatabaseImpl(this.configuration(), DSL.catalog(DSL.name(database)), false);
    }

    @Override
    public AlterDatabaseStep alterDatabase(Name database) {
        return new AlterDatabaseImpl(this.configuration(), DSL.catalog(database), false);
    }

    @Override
    public AlterDatabaseStep alterDatabase(Catalog database) {
        return new AlterDatabaseImpl(this.configuration(), database, false);
    }

    @Override
    public AlterDatabaseStep alterDatabaseIfExists(String database) {
        return new AlterDatabaseImpl(this.configuration(), DSL.catalog(DSL.name(database)), true);
    }

    @Override
    public AlterDatabaseStep alterDatabaseIfExists(Name database) {
        return new AlterDatabaseImpl(this.configuration(), DSL.catalog(database), true);
    }

    @Override
    public AlterDatabaseStep alterDatabaseIfExists(Catalog database) {
        return new AlterDatabaseImpl(this.configuration(), database, true);
    }

    @Override
    public <T> AlterDomainStep<T> alterDomain(String domain) {
        return new AlterDomainImpl(this.configuration(), DSL.domain(DSL.name(domain)), false);
    }

    @Override
    public <T> AlterDomainStep<T> alterDomain(Name domain) {
        return new AlterDomainImpl(this.configuration(), DSL.domain(domain), false);
    }

    @Override
    public <T> AlterDomainStep<T> alterDomain(Domain<T> domain) {
        return new AlterDomainImpl<T>(this.configuration(), domain, false);
    }

    @Override
    public <T> AlterDomainStep<T> alterDomainIfExists(String domain) {
        return new AlterDomainImpl(this.configuration(), DSL.domain(DSL.name(domain)), true);
    }

    @Override
    public <T> AlterDomainStep<T> alterDomainIfExists(Name domain) {
        return new AlterDomainImpl(this.configuration(), DSL.domain(domain), true);
    }

    @Override
    public <T> AlterDomainStep<T> alterDomainIfExists(Domain<T> domain) {
        return new AlterDomainImpl<T>(this.configuration(), domain, true);
    }

    @Override
    public AlterIndexOnStep alterIndex(String index) {
        return new AlterIndexImpl(this.configuration(), DSL.index(DSL.name(index)), false);
    }

    @Override
    public AlterIndexOnStep alterIndex(Name index) {
        return new AlterIndexImpl(this.configuration(), DSL.index(index), false);
    }

    @Override
    public AlterIndexOnStep alterIndex(Index index) {
        return new AlterIndexImpl(this.configuration(), index, false);
    }

    @Override
    public AlterIndexOnStep alterIndexIfExists(String index) {
        return new AlterIndexImpl(this.configuration(), DSL.index(DSL.name(index)), true);
    }

    @Override
    public AlterIndexOnStep alterIndexIfExists(Name index) {
        return new AlterIndexImpl(this.configuration(), DSL.index(index), true);
    }

    @Override
    public AlterIndexOnStep alterIndexIfExists(Index index) {
        return new AlterIndexImpl(this.configuration(), index, true);
    }

    @Override
    public AlterSchemaStep alterSchema(String schema) {
        return new AlterSchemaImpl(this.configuration(), DSL.schema(DSL.name(schema)), false);
    }

    @Override
    public AlterSchemaStep alterSchema(Name schema) {
        return new AlterSchemaImpl(this.configuration(), DSL.schema(schema), false);
    }

    @Override
    public AlterSchemaStep alterSchema(Schema schema) {
        return new AlterSchemaImpl(this.configuration(), schema, false);
    }

    @Override
    public AlterSchemaStep alterSchemaIfExists(String schema) {
        return new AlterSchemaImpl(this.configuration(), DSL.schema(DSL.name(schema)), true);
    }

    @Override
    public AlterSchemaStep alterSchemaIfExists(Name schema) {
        return new AlterSchemaImpl(this.configuration(), DSL.schema(schema), true);
    }

    @Override
    public AlterSchemaStep alterSchemaIfExists(Schema schema) {
        return new AlterSchemaImpl(this.configuration(), schema, true);
    }

    @Override
    public AlterSequenceStep<Number> alterSequence(String sequence2) {
        return new AlterSequenceImpl<Number>(this.configuration(), DSL.sequence(DSL.name(sequence2)), false);
    }

    @Override
    public AlterSequenceStep<Number> alterSequence(Name sequence2) {
        return new AlterSequenceImpl<Number>(this.configuration(), DSL.sequence(sequence2), false);
    }

    @Override
    public <T extends Number> AlterSequenceStep<T> alterSequence(Sequence<T> sequence2) {
        return new AlterSequenceImpl<T>(this.configuration(), sequence2, false);
    }

    @Override
    public AlterSequenceStep<Number> alterSequenceIfExists(String sequence2) {
        return new AlterSequenceImpl<Number>(this.configuration(), DSL.sequence(DSL.name(sequence2)), true);
    }

    @Override
    public AlterSequenceStep<Number> alterSequenceIfExists(Name sequence2) {
        return new AlterSequenceImpl<Number>(this.configuration(), DSL.sequence(sequence2), true);
    }

    @Override
    public <T extends Number> AlterSequenceStep<T> alterSequenceIfExists(Sequence<T> sequence2) {
        return new AlterSequenceImpl<T>(this.configuration(), sequence2, true);
    }

    @Override
    public AlterTypeStep alterType(String type) {
        return new AlterTypeImpl(this.configuration(), DSL.name(type));
    }

    @Override
    public AlterTypeStep alterType(Name type) {
        return new AlterTypeImpl(this.configuration(), type);
    }

    @Override
    public AlterViewStep alterView(String view) {
        return new AlterViewImpl(this.configuration(), DSL.table(DSL.name(view)), null, false);
    }

    @Override
    public AlterViewStep alterView(Name view) {
        return new AlterViewImpl(this.configuration(), DSL.table(view), null, false);
    }

    @Override
    public AlterViewStep alterView(Table<?> view) {
        return new AlterViewImpl(this.configuration(), view, null, false);
    }

    @Override
    public AlterViewStep alterViewIfExists(String view) {
        return new AlterViewImpl(this.configuration(), DSL.table(DSL.name(view)), null, true);
    }

    @Override
    public AlterViewStep alterViewIfExists(Name view) {
        return new AlterViewImpl(this.configuration(), DSL.table(view), null, true);
    }

    @Override
    public AlterViewStep alterViewIfExists(Table<?> view) {
        return new AlterViewImpl(this.configuration(), view, null, true);
    }

    @Override
    public AlterViewStep alterView(Table<?> view, Field<?> ... fields) {
        return new AlterViewImpl(this.configuration(), view, Arrays.asList(fields), false);
    }

    @Override
    public AlterViewStep alterView(Table<?> view, Collection<? extends Field<?>> fields) {
        return new AlterViewImpl(this.configuration(), view, new QueryPartList((Iterable<? extends Field<?>>)fields), false);
    }

    @Override
    public CommentOnIsStep commentOnTable(String table) {
        return new CommentOnImpl(this.configuration(), DSL.table(DSL.name(table)), false, null);
    }

    @Override
    public CommentOnIsStep commentOnTable(Name table) {
        return new CommentOnImpl(this.configuration(), DSL.table(table), false, null);
    }

    @Override
    public CommentOnIsStep commentOnTable(Table<?> table) {
        return new CommentOnImpl(this.configuration(), table, false, null);
    }

    @Override
    public CommentOnIsStep commentOnView(String view) {
        return new CommentOnImpl(this.configuration(), DSL.table(DSL.name(view)), true, null);
    }

    @Override
    public CommentOnIsStep commentOnView(Name view) {
        return new CommentOnImpl(this.configuration(), DSL.table(view), true, null);
    }

    @Override
    public CommentOnIsStep commentOnView(Table<?> view) {
        return new CommentOnImpl(this.configuration(), view, true, null);
    }

    @Override
    public CommentOnIsStep commentOnColumn(String field2) {
        return new CommentOnImpl(this.configuration(), null, false, DSL.field(DSL.name(field2)));
    }

    @Override
    public CommentOnIsStep commentOnColumn(Name field2) {
        return new CommentOnImpl(this.configuration(), null, false, DSL.field(field2));
    }

    @Override
    public CommentOnIsStep commentOnColumn(Field<?> field2) {
        return new CommentOnImpl(this.configuration(), null, false, field2);
    }

    @Override
    public CreateDatabaseFinalStep createDatabase(String database) {
        return new CreateDatabaseImpl(this.configuration(), DSL.catalog(DSL.name(database)), false);
    }

    @Override
    public CreateDatabaseFinalStep createDatabase(Name database) {
        return new CreateDatabaseImpl(this.configuration(), DSL.catalog(database), false);
    }

    @Override
    public CreateDatabaseFinalStep createDatabase(Catalog database) {
        return new CreateDatabaseImpl(this.configuration(), database, false);
    }

    @Override
    public CreateDatabaseFinalStep createDatabaseIfNotExists(String database) {
        return new CreateDatabaseImpl(this.configuration(), DSL.catalog(DSL.name(database)), true);
    }

    @Override
    public CreateDatabaseFinalStep createDatabaseIfNotExists(Name database) {
        return new CreateDatabaseImpl(this.configuration(), DSL.catalog(database), true);
    }

    @Override
    public CreateDatabaseFinalStep createDatabaseIfNotExists(Catalog database) {
        return new CreateDatabaseImpl(this.configuration(), database, true);
    }

    @Override
    public CreateDomainAsStep createDomain(String domain) {
        return new CreateDomainImpl(this.configuration(), DSL.domain(DSL.name(domain)), false);
    }

    @Override
    public CreateDomainAsStep createDomain(Name domain) {
        return new CreateDomainImpl(this.configuration(), DSL.domain(domain), false);
    }

    @Override
    public CreateDomainAsStep createDomain(Domain<?> domain) {
        return new CreateDomainImpl(this.configuration(), domain, false);
    }

    @Override
    public CreateDomainAsStep createDomainIfNotExists(String domain) {
        return new CreateDomainImpl(this.configuration(), DSL.domain(DSL.name(domain)), true);
    }

    @Override
    public CreateDomainAsStep createDomainIfNotExists(Name domain) {
        return new CreateDomainImpl(this.configuration(), DSL.domain(domain), true);
    }

    @Override
    public CreateDomainAsStep createDomainIfNotExists(Domain<?> domain) {
        return new CreateDomainImpl(this.configuration(), domain, true);
    }

    @Override
    @Pro
    public CreateFunctionParametersStep createFunction(String function) {
        return new CreateFunctionImpl(this.configuration(), DSL.name(function), false);
    }

    @Override
    @Pro
    public CreateFunctionParametersStep createFunction(Name function) {
        return new CreateFunctionImpl(this.configuration(), function, false);
    }

    @Override
    @Pro
    public CreateFunctionParametersStep createOrReplaceFunction(String function) {
        return new CreateFunctionImpl(this.configuration(), DSL.name(function), true);
    }

    @Override
    @Pro
    public CreateFunctionParametersStep createOrReplaceFunction(Name function) {
        return new CreateFunctionImpl(this.configuration(), function, true);
    }

    @Override
    public CreateIndexStep createIndex(String index) {
        return new CreateIndexImpl(this.configuration(), false, DSL.index(DSL.name(index)), false);
    }

    @Override
    public CreateIndexStep createIndex(Name index) {
        return new CreateIndexImpl(this.configuration(), false, DSL.index(index), false);
    }

    @Override
    public CreateIndexStep createIndex(Index index) {
        return new CreateIndexImpl(this.configuration(), false, index, false);
    }

    @Override
    public CreateIndexStep createIndex() {
        return new CreateIndexImpl(this.configuration(), false, false);
    }

    @Override
    public CreateIndexStep createIndexIfNotExists(String index) {
        return new CreateIndexImpl(this.configuration(), false, DSL.index(DSL.name(index)), true);
    }

    @Override
    public CreateIndexStep createIndexIfNotExists(Name index) {
        return new CreateIndexImpl(this.configuration(), false, DSL.index(index), true);
    }

    @Override
    public CreateIndexStep createIndexIfNotExists(Index index) {
        return new CreateIndexImpl(this.configuration(), false, index, true);
    }

    @Override
    public CreateIndexStep createIndexIfNotExists() {
        return new CreateIndexImpl(this.configuration(), false, true);
    }

    @Override
    public CreateIndexStep createUniqueIndex(String index) {
        return new CreateIndexImpl(this.configuration(), true, DSL.index(DSL.name(index)), false);
    }

    @Override
    public CreateIndexStep createUniqueIndex(Name index) {
        return new CreateIndexImpl(this.configuration(), true, DSL.index(index), false);
    }

    @Override
    public CreateIndexStep createUniqueIndex(Index index) {
        return new CreateIndexImpl(this.configuration(), true, index, false);
    }

    @Override
    public CreateIndexStep createUniqueIndex() {
        return new CreateIndexImpl(this.configuration(), true, false);
    }

    @Override
    public CreateIndexStep createUniqueIndexIfNotExists(String index) {
        return new CreateIndexImpl(this.configuration(), true, DSL.index(DSL.name(index)), true);
    }

    @Override
    public CreateIndexStep createUniqueIndexIfNotExists(Name index) {
        return new CreateIndexImpl(this.configuration(), true, DSL.index(index), true);
    }

    @Override
    public CreateIndexStep createUniqueIndexIfNotExists(Index index) {
        return new CreateIndexImpl(this.configuration(), true, index, true);
    }

    @Override
    public CreateIndexStep createUniqueIndexIfNotExists() {
        return new CreateIndexImpl(this.configuration(), true, true);
    }

    @Override
    @Pro
    public CreateProcedureParametersStep createProcedure(String procedure) {
        return new CreateProcedureImpl(this.configuration(), DSL.name(procedure), false);
    }

    @Override
    @Pro
    public CreateProcedureParametersStep createProcedure(Name procedure) {
        return new CreateProcedureImpl(this.configuration(), procedure, false);
    }

    @Override
    @Pro
    public CreateProcedureParametersStep createOrReplaceProcedure(String procedure) {
        return new CreateProcedureImpl(this.configuration(), DSL.name(procedure), true);
    }

    @Override
    @Pro
    public CreateProcedureParametersStep createOrReplaceProcedure(Name procedure) {
        return new CreateProcedureImpl(this.configuration(), procedure, true);
    }

    @Override
    public CreateTableElementListStep createTable(String table) {
        return new CreateTableImpl(this.configuration(), DSL.table(DSL.name(table)), false, false);
    }

    @Override
    public CreateTableElementListStep createTable(Name table) {
        return new CreateTableImpl(this.configuration(), DSL.table(table), false, false);
    }

    @Override
    public CreateTableElementListStep createTable(Table<?> table) {
        return new CreateTableImpl(this.configuration(), table, false, false);
    }

    @Override
    public CreateTableElementListStep createTableIfNotExists(String table) {
        return new CreateTableImpl(this.configuration(), DSL.table(DSL.name(table)), false, true);
    }

    @Override
    public CreateTableElementListStep createTableIfNotExists(Name table) {
        return new CreateTableImpl(this.configuration(), DSL.table(table), false, true);
    }

    @Override
    public CreateTableElementListStep createTableIfNotExists(Table<?> table) {
        return new CreateTableImpl(this.configuration(), table, false, true);
    }

    @Override
    public CreateTableElementListStep createTemporaryTable(String table) {
        return new CreateTableImpl(this.configuration(), DSL.table(DSL.name(table)), true, false);
    }

    @Override
    public CreateTableElementListStep createTemporaryTable(Name table) {
        return new CreateTableImpl(this.configuration(), DSL.table(table), true, false);
    }

    @Override
    public CreateTableElementListStep createTemporaryTable(Table<?> table) {
        return new CreateTableImpl(this.configuration(), table, true, false);
    }

    @Override
    public CreateTableElementListStep createTemporaryTableIfNotExists(String table) {
        return new CreateTableImpl(this.configuration(), DSL.table(DSL.name(table)), true, true);
    }

    @Override
    public CreateTableElementListStep createTemporaryTableIfNotExists(Name table) {
        return new CreateTableImpl(this.configuration(), DSL.table(table), true, true);
    }

    @Override
    public CreateTableElementListStep createTemporaryTableIfNotExists(Table<?> table) {
        return new CreateTableImpl(this.configuration(), table, true, true);
    }

    @Override
    public CreateTableElementListStep createGlobalTemporaryTable(String table) {
        return new CreateTableImpl(this.configuration(), DSL.table(DSL.name(table)), true, false);
    }

    @Override
    public CreateTableElementListStep createGlobalTemporaryTable(Name table) {
        return new CreateTableImpl(this.configuration(), DSL.table(table), true, false);
    }

    @Override
    public CreateTableElementListStep createGlobalTemporaryTable(Table<?> table) {
        return new CreateTableImpl(this.configuration(), table, true, false);
    }

    @Override
    public CreateTableElementListStep createGlobalTemporaryTableIfNotExists(String table) {
        return new CreateTableImpl(this.configuration(), DSL.table(DSL.name(table)), true, true);
    }

    @Override
    public CreateTableElementListStep createGlobalTemporaryTableIfNotExists(Name table) {
        return new CreateTableImpl(this.configuration(), DSL.table(table), true, true);
    }

    @Override
    public CreateTableElementListStep createGlobalTemporaryTableIfNotExists(Table<?> table) {
        return new CreateTableImpl(this.configuration(), table, true, true);
    }

    @Override
    @Pro
    public CreateTriggerEventStep createTrigger(String trigger) {
        return new CreateTriggerImpl(this.configuration(), DSL.name(trigger), false);
    }

    @Override
    @Pro
    public CreateTriggerEventStep createTrigger(Name trigger) {
        return new CreateTriggerImpl(this.configuration(), trigger, false);
    }

    @Override
    @Pro
    public CreateTriggerEventStep createOrReplaceTrigger(String trigger) {
        return new CreateTriggerImpl(this.configuration(), DSL.name(trigger), true);
    }

    @Override
    @Pro
    public CreateTriggerEventStep createOrReplaceTrigger(Name trigger) {
        return new CreateTriggerImpl(this.configuration(), trigger, true);
    }

    @Override
    public CreateSchemaFinalStep createSchema(String schema) {
        return new CreateSchemaImpl(this.configuration(), DSL.schema(DSL.name(schema)), false);
    }

    @Override
    public CreateSchemaFinalStep createSchema(Name schema) {
        return new CreateSchemaImpl(this.configuration(), DSL.schema(schema), false);
    }

    @Override
    public CreateSchemaFinalStep createSchema(Schema schema) {
        return new CreateSchemaImpl(this.configuration(), schema, false);
    }

    @Override
    public CreateSchemaFinalStep createSchemaIfNotExists(String schema) {
        return new CreateSchemaImpl(this.configuration(), DSL.schema(DSL.name(schema)), true);
    }

    @Override
    public CreateSchemaFinalStep createSchemaIfNotExists(Name schema) {
        return new CreateSchemaImpl(this.configuration(), DSL.schema(schema), true);
    }

    @Override
    public CreateSchemaFinalStep createSchemaIfNotExists(Schema schema) {
        return new CreateSchemaImpl(this.configuration(), schema, true);
    }

    @Override
    public CreateSequenceFlagsStep createSequence(String sequence2) {
        return new CreateSequenceImpl(this.configuration(), DSL.sequence(DSL.name(sequence2)), false);
    }

    @Override
    public CreateSequenceFlagsStep createSequence(Name sequence2) {
        return new CreateSequenceImpl(this.configuration(), DSL.sequence(sequence2), false);
    }

    @Override
    public CreateSequenceFlagsStep createSequence(Sequence<?> sequence2) {
        return new CreateSequenceImpl(this.configuration(), sequence2, false);
    }

    @Override
    public CreateSequenceFlagsStep createSequenceIfNotExists(String sequence2) {
        return new CreateSequenceImpl(this.configuration(), DSL.sequence(DSL.name(sequence2)), true);
    }

    @Override
    public CreateSequenceFlagsStep createSequenceIfNotExists(Name sequence2) {
        return new CreateSequenceImpl(this.configuration(), DSL.sequence(sequence2), true);
    }

    @Override
    public CreateSequenceFlagsStep createSequenceIfNotExists(Sequence<?> sequence2) {
        return new CreateSequenceImpl(this.configuration(), sequence2, true);
    }

    @Override
    public DropDatabaseFinalStep dropDatabase(String database) {
        return new DropDatabaseImpl(this.configuration(), DSL.catalog(DSL.name(database)), false);
    }

    @Override
    public DropDatabaseFinalStep dropDatabase(Name database) {
        return new DropDatabaseImpl(this.configuration(), DSL.catalog(database), false);
    }

    @Override
    public DropDatabaseFinalStep dropDatabase(Catalog database) {
        return new DropDatabaseImpl(this.configuration(), database, false);
    }

    @Override
    public DropDatabaseFinalStep dropDatabaseIfExists(String database) {
        return new DropDatabaseImpl(this.configuration(), DSL.catalog(DSL.name(database)), true);
    }

    @Override
    public DropDatabaseFinalStep dropDatabaseIfExists(Name database) {
        return new DropDatabaseImpl(this.configuration(), DSL.catalog(database), true);
    }

    @Override
    public DropDatabaseFinalStep dropDatabaseIfExists(Catalog database) {
        return new DropDatabaseImpl(this.configuration(), database, true);
    }

    @Override
    public DropDomainCascadeStep dropDomain(String domain) {
        return new DropDomainImpl(this.configuration(), DSL.domain(DSL.name(domain)), false);
    }

    @Override
    public DropDomainCascadeStep dropDomain(Name domain) {
        return new DropDomainImpl(this.configuration(), DSL.domain(domain), false);
    }

    @Override
    public DropDomainCascadeStep dropDomain(Domain<?> domain) {
        return new DropDomainImpl(this.configuration(), domain, false);
    }

    @Override
    public DropDomainCascadeStep dropDomainIfExists(String domain) {
        return new DropDomainImpl(this.configuration(), DSL.domain(DSL.name(domain)), true);
    }

    @Override
    public DropDomainCascadeStep dropDomainIfExists(Name domain) {
        return new DropDomainImpl(this.configuration(), DSL.domain(domain), true);
    }

    @Override
    public DropDomainCascadeStep dropDomainIfExists(Domain<?> domain) {
        return new DropDomainImpl(this.configuration(), domain, true);
    }

    @Override
    @Pro
    public DDLQuery dropFunction(String function) {
        return new DropFunctionImpl(this.configuration(), DSL.name(function), false);
    }

    @Override
    @Pro
    public DDLQuery dropFunction(Name function) {
        return new DropFunctionImpl(this.configuration(), function, false);
    }

    @Override
    @Pro
    public DDLQuery dropFunctionIfExists(String function) {
        return new DropFunctionImpl(this.configuration(), DSL.name(function), true);
    }

    @Override
    @Pro
    public DDLQuery dropFunctionIfExists(Name function) {
        return new DropFunctionImpl(this.configuration(), function, true);
    }

    @Override
    public DropIndexOnStep dropIndex(String index) {
        return new DropIndexImpl(this.configuration(), DSL.index(DSL.name(index)), false);
    }

    @Override
    public DropIndexOnStep dropIndex(Name index) {
        return new DropIndexImpl(this.configuration(), DSL.index(index), false);
    }

    @Override
    public DropIndexOnStep dropIndex(Index index) {
        return new DropIndexImpl(this.configuration(), index, false);
    }

    @Override
    public DropIndexOnStep dropIndexIfExists(String index) {
        return new DropIndexImpl(this.configuration(), DSL.index(DSL.name(index)), true);
    }

    @Override
    public DropIndexOnStep dropIndexIfExists(Name index) {
        return new DropIndexImpl(this.configuration(), DSL.index(index), true);
    }

    @Override
    public DropIndexOnStep dropIndexIfExists(Index index) {
        return new DropIndexImpl(this.configuration(), index, true);
    }

    @Override
    @Pro
    public DDLQuery dropProcedure(String procedure) {
        return new DropProcedureImpl(this.configuration(), DSL.name(procedure), false);
    }

    @Override
    @Pro
    public DDLQuery dropProcedure(Name procedure) {
        return new DropProcedureImpl(this.configuration(), procedure, false);
    }

    @Override
    @Pro
    public DDLQuery dropProcedureIfExists(String procedure) {
        return new DropProcedureImpl(this.configuration(), DSL.name(procedure), true);
    }

    @Override
    @Pro
    public DDLQuery dropProcedureIfExists(Name procedure) {
        return new DropProcedureImpl(this.configuration(), procedure, true);
    }

    @Override
    public DropSchemaStep dropSchema(String schema) {
        return new DropSchemaImpl(this.configuration(), DSL.schema(DSL.name(schema)), false);
    }

    @Override
    public DropSchemaStep dropSchema(Name schema) {
        return new DropSchemaImpl(this.configuration(), DSL.schema(schema), false);
    }

    @Override
    public DropSchemaStep dropSchema(Schema schema) {
        return new DropSchemaImpl(this.configuration(), schema, false);
    }

    @Override
    public DropSchemaStep dropSchemaIfExists(String schema) {
        return new DropSchemaImpl(this.configuration(), DSL.schema(DSL.name(schema)), true);
    }

    @Override
    public DropSchemaStep dropSchemaIfExists(Name schema) {
        return new DropSchemaImpl(this.configuration(), DSL.schema(schema), true);
    }

    @Override
    public DropSchemaStep dropSchemaIfExists(Schema schema) {
        return new DropSchemaImpl(this.configuration(), schema, true);
    }

    @Override
    public DropSequenceFinalStep dropSequence(String sequence2) {
        return new DropSequenceImpl(this.configuration(), DSL.sequence(DSL.name(sequence2)), false);
    }

    @Override
    public DropSequenceFinalStep dropSequence(Name sequence2) {
        return new DropSequenceImpl(this.configuration(), DSL.sequence(sequence2), false);
    }

    @Override
    public DropSequenceFinalStep dropSequence(Sequence<?> sequence2) {
        return new DropSequenceImpl(this.configuration(), sequence2, false);
    }

    @Override
    public DropSequenceFinalStep dropSequenceIfExists(String sequence2) {
        return new DropSequenceImpl(this.configuration(), DSL.sequence(DSL.name(sequence2)), true);
    }

    @Override
    public DropSequenceFinalStep dropSequenceIfExists(Name sequence2) {
        return new DropSequenceImpl(this.configuration(), DSL.sequence(sequence2), true);
    }

    @Override
    public DropSequenceFinalStep dropSequenceIfExists(Sequence<?> sequence2) {
        return new DropSequenceImpl(this.configuration(), sequence2, true);
    }

    @Override
    public DropTableStep dropTable(String table) {
        return new DropTableImpl(this.configuration(), false, DSL.table(DSL.name(table)), false);
    }

    @Override
    public DropTableStep dropTable(Name table) {
        return new DropTableImpl(this.configuration(), false, DSL.table(table), false);
    }

    @Override
    public DropTableStep dropTable(Table<?> table) {
        return new DropTableImpl(this.configuration(), false, table, false);
    }

    @Override
    public DropTableStep dropTableIfExists(String table) {
        return new DropTableImpl(this.configuration(), false, DSL.table(DSL.name(table)), true);
    }

    @Override
    public DropTableStep dropTableIfExists(Name table) {
        return new DropTableImpl(this.configuration(), false, DSL.table(table), true);
    }

    @Override
    public DropTableStep dropTableIfExists(Table<?> table) {
        return new DropTableImpl(this.configuration(), false, table, true);
    }

    @Override
    public DropTableStep dropTemporaryTable(String table) {
        return new DropTableImpl(this.configuration(), true, DSL.table(DSL.name(table)), false);
    }

    @Override
    public DropTableStep dropTemporaryTable(Name table) {
        return new DropTableImpl(this.configuration(), true, DSL.table(table), false);
    }

    @Override
    public DropTableStep dropTemporaryTable(Table<?> table) {
        return new DropTableImpl(this.configuration(), true, table, false);
    }

    @Override
    public DropTableStep dropTemporaryTableIfExists(String table) {
        return new DropTableImpl(this.configuration(), true, DSL.table(DSL.name(table)), true);
    }

    @Override
    public DropTableStep dropTemporaryTableIfExists(Name table) {
        return new DropTableImpl(this.configuration(), true, DSL.table(table), true);
    }

    @Override
    public DropTableStep dropTemporaryTableIfExists(Table<?> table) {
        return new DropTableImpl(this.configuration(), true, table, true);
    }

    @Override
    @Pro
    public DDLQuery dropTrigger(String trigger) {
        return new DropTriggerImpl(this.configuration(), DSL.name(trigger), false);
    }

    @Override
    @Pro
    public DDLQuery dropTrigger(Name trigger) {
        return new DropTriggerImpl(this.configuration(), trigger, false);
    }

    @Override
    @Pro
    public DDLQuery dropTriggerIfExists(String trigger) {
        return new DropTriggerImpl(this.configuration(), DSL.name(trigger), true);
    }

    @Override
    @Pro
    public DDLQuery dropTriggerIfExists(Name trigger) {
        return new DropTriggerImpl(this.configuration(), trigger, true);
    }

    @Override
    public DropViewFinalStep dropView(String view) {
        return new DropViewImpl(this.configuration(), DSL.table(DSL.name(view)), false);
    }

    @Override
    public DropViewFinalStep dropView(Name view) {
        return new DropViewImpl(this.configuration(), DSL.table(view), false);
    }

    @Override
    public DropViewFinalStep dropView(Table<?> view) {
        return new DropViewImpl(this.configuration(), view, false);
    }

    @Override
    public DropViewFinalStep dropViewIfExists(String view) {
        return new DropViewImpl(this.configuration(), DSL.table(DSL.name(view)), true);
    }

    @Override
    public DropViewFinalStep dropViewIfExists(Name view) {
        return new DropViewImpl(this.configuration(), DSL.table(view), true);
    }

    @Override
    public DropViewFinalStep dropViewIfExists(Table<?> view) {
        return new DropViewImpl(this.configuration(), view, true);
    }

    @Override
    public GrantOnStep grant(Privilege privileges) {
        return new GrantImpl(this.configuration(), Arrays.asList(privileges));
    }

    @Override
    public GrantOnStep grant(Privilege ... privileges) {
        return new GrantImpl(this.configuration(), Arrays.asList(privileges));
    }

    @Override
    public GrantOnStep grant(Collection<? extends Privilege> privileges) {
        return new GrantImpl(this.configuration(), new QueryPartList<Privilege>((Iterable<? extends Privilege>)privileges));
    }

    @Override
    public RevokeOnStep revoke(Privilege privileges) {
        return new RevokeImpl(this.configuration(), Arrays.asList(privileges), false);
    }

    @Override
    public RevokeOnStep revoke(Privilege ... privileges) {
        return new RevokeImpl(this.configuration(), Arrays.asList(privileges), false);
    }

    @Override
    public RevokeOnStep revoke(Collection<? extends Privilege> privileges) {
        return new RevokeImpl(this.configuration(), new QueryPartList<Privilege>((Iterable<? extends Privilege>)privileges), false);
    }

    @Override
    public RevokeOnStep revokeGrantOptionFor(Privilege privileges) {
        return new RevokeImpl(this.configuration(), Arrays.asList(privileges), true);
    }

    @Override
    public RevokeOnStep revokeGrantOptionFor(Privilege ... privileges) {
        return new RevokeImpl(this.configuration(), Arrays.asList(privileges), true);
    }

    @Override
    public RevokeOnStep revokeGrantOptionFor(Collection<? extends Privilege> privileges) {
        return new RevokeImpl(this.configuration(), new QueryPartList<Privilege>((Iterable<? extends Privilege>)privileges), true);
    }

    @Override
    public RowCountQuery set(String name, Param<?> value) {
        return new SetCommand(this.configuration(), DSL.name(name), value, false);
    }

    @Override
    public RowCountQuery set(Name name, Param<?> value) {
        return new SetCommand(this.configuration(), name, value, false);
    }

    @Override
    public RowCountQuery setLocal(String name, Param<?> value) {
        return new SetCommand(this.configuration(), DSL.name(name), value, true);
    }

    @Override
    public RowCountQuery setLocal(Name name, Param<?> value) {
        return new SetCommand(this.configuration(), name, value, true);
    }

    @Override
    public RowCountQuery setCatalog(String catalog) {
        return new SetCatalog(this.configuration(), DSL.catalog(DSL.name(catalog)));
    }

    @Override
    public RowCountQuery setCatalog(Name catalog) {
        return new SetCatalog(this.configuration(), DSL.catalog(catalog));
    }

    @Override
    public RowCountQuery setCatalog(Catalog catalog) {
        return new SetCatalog(this.configuration(), catalog);
    }

    @Override
    public RowCountQuery setSchema(String schema) {
        return new SetSchema(this.configuration(), DSL.schema(DSL.name(schema)));
    }

    @Override
    public RowCountQuery setSchema(Name schema) {
        return new SetSchema(this.configuration(), DSL.schema(schema));
    }

    @Override
    public RowCountQuery setSchema(Schema schema) {
        return new SetSchema(this.configuration(), schema);
    }

    @Override
    public TruncateIdentityStep<Record> truncate(String table) {
        return new TruncateImpl<Record>(this.configuration(), DSL.table(DSL.name(table)));
    }

    @Override
    public TruncateIdentityStep<Record> truncate(Name table) {
        return new TruncateImpl<Record>(this.configuration(), DSL.table(table));
    }

    @Override
    public <R extends Record> TruncateIdentityStep<R> truncate(Table<R> table) {
        return new TruncateImpl<R>(this.configuration(), table);
    }

    @Override
    public TruncateIdentityStep<Record> truncateTable(String table) {
        return this.truncate(table);
    }

    @Override
    public TruncateIdentityStep<Record> truncateTable(Name table) {
        return this.truncate(table);
    }

    @Override
    public <R extends Record> TruncateIdentityStep<R> truncateTable(Table<R> table) {
        return this.truncate(table);
    }

    @Override
    @Pro
    public CallArgsStep call(String procedure) {
        return new Call(this.configuration(), DSL.name(procedure));
    }

    @Override
    @Pro
    public CallArgsStep call(Name procedure) {
        return new Call(this.configuration(), procedure);
    }

    @Override
    public Query startTransaction() {
        return new StartTransaction(this.configuration());
    }

    @Override
    public Query savepoint(String name) {
        return new Savepoint(this.configuration(), DSL.name(name));
    }

    @Override
    public Query savepoint(Name name) {
        return new Savepoint(this.configuration(), name);
    }

    @Override
    public Query releaseSavepoint(String name) {
        return new ReleaseSavepoint(this.configuration(), DSL.name(name));
    }

    @Override
    public Query releaseSavepoint(Name name) {
        return new ReleaseSavepoint(this.configuration(), name);
    }

    @Override
    public Query commit() {
        return new Commit(this.configuration());
    }

    @Override
    public RollbackToSavepointStep rollback() {
        return new Rollback(this.configuration());
    }

    @Override
    public Queries ddl(Catalog catalog) {
        return this.ddl(catalog, new DDLExportConfiguration());
    }

    @Override
    public Queries ddl(Catalog catalog, DDLFlag ... flags) {
        return this.ddl(catalog, new DDLExportConfiguration().flags(flags));
    }

    @Override
    public Queries ddl(Catalog catalog, DDLExportConfiguration exportConfiguration) {
        return this.meta(catalog).ddl(exportConfiguration);
    }

    @Override
    public Queries ddl(Schema schema) {
        return this.ddl(schema, new DDLExportConfiguration());
    }

    @Override
    public Queries ddl(Schema schema, DDLFlag ... flags) {
        return this.ddl(schema, new DDLExportConfiguration().flags(flags));
    }

    @Override
    public Queries ddl(Schema schema, DDLExportConfiguration exportConfiguration) {
        return this.meta(schema).ddl(exportConfiguration);
    }

    @Override
    public Queries ddl(Table<?> table) {
        return this.ddl(new Table[]{table});
    }

    @Override
    public Queries ddl(Table<?> table, DDLFlag ... flags) {
        return this.ddl(new Table[]{table}, flags);
    }

    @Override
    public Queries ddl(Table<?> table, DDLExportConfiguration exportConfiguration) {
        return this.ddl(new Table[]{table}, exportConfiguration);
    }

    public Queries ddl(Table ... tables) {
        return this.ddl(tables, new DDLExportConfiguration());
    }

    public Queries ddl(Table[] tables, DDLFlag ... flags) {
        return this.ddl(tables, new DDLExportConfiguration().flags(flags));
    }

    public Queries ddl(Table[] tables, DDLExportConfiguration exportConfiguration) {
        return this.meta(tables).ddl(exportConfiguration);
    }

    @Override
    public Queries ddl(Collection<? extends Table<?>> tables) {
        return this.ddl((Table[])tables.toArray(Tools.EMPTY_TABLE));
    }

    @Override
    public Queries ddl(Collection<? extends Table<?>> tables, DDLFlag ... flags) {
        return this.ddl((Table[])tables.toArray(Tools.EMPTY_TABLE), flags);
    }

    @Override
    public Queries ddl(Collection<? extends Table<?>> tables, DDLExportConfiguration exportConfiguration) {
        return this.ddl((Table[])tables.toArray(Tools.EMPTY_TABLE), exportConfiguration);
    }

    @Override
    public CreateViewAsStep<Record> createView(String view, String ... fields) {
        return this.createView(DSL.table(DSL.name(view)), Tools.fieldsByName(view, fields));
    }

    @Override
    public CreateViewAsStep<Record> createView(Name view, Name ... fields) {
        return this.createView(DSL.table(view), Tools.fieldsByName(fields));
    }

    @Override
    public CreateViewAsStep<Record> createView(Table<?> view, Field<?> ... fields) {
        return new CreateViewImpl<Record>(this.configuration(), view, fields, false, false);
    }

    @Override
    public CreateViewAsStep<Record> createView(String view, Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return this.createView(DSL.table(DSL.name(view)), (? super Field<?> f, ? super Integer i) -> DSL.field(DSL.name((String)fieldNameFunction.apply((Field<?>)f))));
    }

    @Override
    public CreateViewAsStep<Record> createView(String view, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        return this.createView(DSL.table(DSL.name(view)), (? super Field<?> f, ? super Integer i) -> DSL.field(DSL.name((String)fieldNameFunction.apply((Field<?>)f, (Integer)i))));
    }

    @Override
    public CreateViewAsStep<Record> createView(Name view, Function<? super Field<?>, ? extends Name> fieldNameFunction) {
        return this.createView(DSL.table(view), (? super Field<?> f, ? super Integer i) -> DSL.field((Name)fieldNameFunction.apply((Field<?>)f)));
    }

    @Override
    public CreateViewAsStep<Record> createView(Name view, BiFunction<? super Field<?>, ? super Integer, ? extends Name> fieldNameFunction) {
        return this.createView(DSL.table(view), (? super Field<?> f, ? super Integer i) -> DSL.field((Name)fieldNameFunction.apply((Field<?>)f, (Integer)i)));
    }

    @Override
    public CreateViewAsStep<Record> createView(Table<?> view, Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
        return this.createView(view, (? super Field<?> f, ? super Integer i) -> (Field)fieldNameFunction.apply((Field<?>)f));
    }

    @Override
    public CreateViewAsStep<Record> createView(Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction) {
        return new CreateViewImpl<Record>(this.configuration(), view, fieldNameFunction, false, false);
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(String view, String ... fields) {
        return this.createOrReplaceView(DSL.table(DSL.name(view)), Tools.fieldsByName(view, fields));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(Name view, Name ... fields) {
        return this.createOrReplaceView(DSL.table(view), Tools.fieldsByName(fields));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(Table<?> view, Field<?> ... fields) {
        return new CreateViewImpl<Record>(this.configuration(), view, fields, false, true);
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(String view, Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return this.createOrReplaceView(DSL.table(DSL.name(view)), (? super Field<?> f, ? super Integer i) -> DSL.field(DSL.name((String)fieldNameFunction.apply((Field<?>)f))));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(String view, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        return this.createOrReplaceView(DSL.table(DSL.name(view)), (? super Field<?> f, ? super Integer i) -> DSL.field(DSL.name((String)fieldNameFunction.apply((Field<?>)f, (Integer)i))));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(Name view, Function<? super Field<?>, ? extends Name> fieldNameFunction) {
        return this.createOrReplaceView(DSL.table(view), (? super Field<?> f, ? super Integer i) -> DSL.field((Name)fieldNameFunction.apply((Field<?>)f)));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(Name view, BiFunction<? super Field<?>, ? super Integer, ? extends Name> fieldNameFunction) {
        return this.createOrReplaceView(DSL.table(view), (? super Field<?> f, ? super Integer i) -> DSL.field((Name)fieldNameFunction.apply((Field<?>)f, (Integer)i)));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(Table<?> view, Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
        return this.createOrReplaceView(view, (? super Field<?> f, ? super Integer i) -> (Field)fieldNameFunction.apply((Field<?>)f));
    }

    @Override
    public CreateViewAsStep<Record> createOrReplaceView(Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction) {
        return new CreateViewImpl<Record>(this.configuration(), view, fieldNameFunction, false, true);
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(String view, String ... fields) {
        return this.createViewIfNotExists(DSL.table(DSL.name(view)), Tools.fieldsByName(view, fields));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(Name view, Name ... fields) {
        return this.createViewIfNotExists(DSL.table(view), Tools.fieldsByName(fields));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, Field<?> ... fields) {
        return new CreateViewImpl<Record>(this.configuration(), view, fields, true, false);
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(String view, Function<? super Field<?>, ? extends String> fieldNameFunction) {
        return this.createViewIfNotExists(DSL.table(DSL.name(view)), (? super Field<?> f, ? super Integer i) -> DSL.field(DSL.name((String)fieldNameFunction.apply((Field<?>)f))));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(String view, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        return this.createViewIfNotExists(DSL.table(DSL.name(view)), (? super Field<?> f, ? super Integer i) -> DSL.field(DSL.name((String)fieldNameFunction.apply((Field<?>)f, (Integer)i))));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(Name view, Function<? super Field<?>, ? extends Name> fieldNameFunction) {
        return this.createViewIfNotExists(DSL.table(view), (? super Field<?> f, ? super Integer i) -> DSL.field((Name)fieldNameFunction.apply((Field<?>)f)));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(Name view, BiFunction<? super Field<?>, ? super Integer, ? extends Name> fieldNameFunction) {
        return this.createViewIfNotExists(DSL.table(view), (? super Field<?> f, ? super Integer i) -> DSL.field((Name)fieldNameFunction.apply((Field<?>)f, (Integer)i)));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, Function<? super Field<?>, ? extends Field<?>> fieldNameFunction) {
        return this.createViewIfNotExists(view, (? super Field<?> f, ? super Integer i) -> (Field)fieldNameFunction.apply((Field<?>)f));
    }

    @Override
    public CreateViewAsStep<Record> createViewIfNotExists(Table<?> view, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> fieldNameFunction) {
        return new CreateViewImpl<Record>(this.configuration(), view, fieldNameFunction, true, false);
    }

    @Override
    public CreateTypeStep createType(String type) {
        return this.createType(DSL.name(type));
    }

    @Override
    public CreateTypeStep createType(Name type) {
        return new CreateTypeImpl(this.configuration(), type);
    }

    @Override
    public DropTypeStep dropType(String type) {
        return this.dropType(DSL.name(type));
    }

    @Override
    public DropTypeStep dropType(Name type) {
        return this.dropType(Arrays.asList(type));
    }

    @Override
    public DropTypeStep dropType(String ... type) {
        return this.dropType(Tools.names(type));
    }

    @Override
    public DropTypeStep dropType(Name ... type) {
        return this.dropType(Arrays.asList(type));
    }

    @Override
    public DropTypeStep dropType(Collection<?> type) {
        return new DropTypeImpl(this.configuration(), type, false);
    }

    @Override
    public DropTypeStep dropTypeIfExists(String type) {
        return this.dropTypeIfExists(DSL.name(type));
    }

    @Override
    public DropTypeStep dropTypeIfExists(Name type) {
        return this.dropTypeIfExists(Arrays.asList(type));
    }

    @Override
    public DropTypeStep dropTypeIfExists(String ... type) {
        return this.dropTypeIfExists(Tools.names(type));
    }

    @Override
    public DropTypeStep dropTypeIfExists(Name ... type) {
        return this.dropTypeIfExists(Arrays.asList(type));
    }

    @Override
    public DropTypeStep dropTypeIfExists(Collection<?> type) {
        return new DropTypeImpl(this.configuration(), type, true);
    }

    @Override
    public AlterTableStep alterTable(String table) {
        return this.alterTable(DSL.name(table));
    }

    @Override
    public AlterTableStep alterTable(Name table) {
        return this.alterTable(DSL.table(table));
    }

    @Override
    public AlterTableStep alterTable(Table<?> table) {
        return new AlterTableImpl(this.configuration(), table);
    }

    @Override
    public AlterTableStep alterTableIfExists(String table) {
        return this.alterTableIfExists(DSL.name(table));
    }

    @Override
    public AlterTableStep alterTableIfExists(Name table) {
        return this.alterTableIfExists(DSL.table(table));
    }

    @Override
    public AlterTableStep alterTableIfExists(Table<?> table) {
        return new AlterTableImpl(this.configuration(), table, true);
    }

    @Override
    public BigInteger lastID() {
        switch (this.family()) {
            case DERBY: {
                return this.fetchValue(DSL.field("identity_val_local()", BigInteger.class));
            }
            case H2: 
            case HSQLDB: {
                return this.fetchValue(DSL.field("identity()", BigInteger.class));
            }
            case AURORA_MYSQL: 
            case MEMSQL: 
            case VERTICA: 
            case CUBRID: 
            case MARIADB: 
            case MYSQL: {
                return this.fetchValue(DSL.field("last_insert_id()", BigInteger.class));
            }
            case SQLITE: {
                return this.fetchValue(DSL.field("last_insert_rowid()", BigInteger.class));
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return this.fetchValue(DSL.field("lastval()", BigInteger.class));
            }
            case INFORMIX: {
                return this.resultQuery("select dbinfo('sqlca.sqlerrd1') from systables where tabid = 1").fetchSingle(0, BigInteger.class);
            }
            case INGRES: {
                return this.fetchValue(DSL.field("last_identity()", BigInteger.class));
            }
            case ACCESS: 
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: {
                return this.fetchValue(DSL.field("@@identity", BigInteger.class));
            }
        }
        throw new SQLDialectNotSupportedException("identity functionality not supported by " + String.valueOf((Object)this.configuration().dialect()));
    }

    @Override
    public BigInteger nextval(String sequence2) {
        return this.nextval(DSL.name(sequence2));
    }

    @Override
    public BigInteger nextval(Name sequence2) {
        return this.nextval(DSL.sequence(sequence2));
    }

    @Override
    public <T extends Number> T nextval(Sequence<T> sequence2) {
        return (T)((Number)this.fetchValue(sequence2.nextval()));
    }

    @Override
    public <T extends Number> List<T> nextvals(Sequence<T> sequence2, int size) {
        return this.fetchValues(sequence2.nextvals(size));
    }

    @Override
    public BigInteger currval(String sequence2) {
        return this.currval(DSL.name(sequence2));
    }

    @Override
    public BigInteger currval(Name sequence2) {
        return this.currval(DSL.sequence(sequence2));
    }

    @Override
    public <T extends Number> T currval(Sequence<T> sequence2) {
        return (T)((Number)this.fetchValue(sequence2.currval()));
    }

    @Override
    public Record newRecord(Field<?> ... fields) {
        return Tools.newRecord(false, RecordImplN.class, Tools.row0(fields), this.configuration()).operate(null);
    }

    @Override
    public Record newRecord(Collection<? extends Field<?>> fields) {
        return this.newRecord(fields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public <T1> Record1<T1> newRecord(Field<T1> field1) {
        return (Record1)this.newRecord(new Field[]{field1});
    }

    @Override
    public <T1, T2> Record2<T1, T2> newRecord(Field<T1> field1, Field<T2> field2) {
        return (Record2)this.newRecord(new Field[]{field1, field2});
    }

    @Override
    public <T1, T2, T3> Record3<T1, T2, T3> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return (Record3)this.newRecord(new Field[]{field1, field2, field3});
    }

    @Override
    public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return (Record4)this.newRecord(new Field[]{field1, field2, field3, field4});
    }

    @Override
    public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return (Record5)this.newRecord(new Field[]{field1, field2, field3, field4, field5});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return (Record6)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return (Record7)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return (Record8)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return (Record9)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return (Record10)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return (Record11)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return (Record12)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return (Record13)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return (Record14)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return (Record15)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return (Record16)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return (Record17)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return (Record18)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return (Record19)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return (Record20)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return (Record21)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return (Record22)this.newRecord(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public <R extends UDTRecord<R>> R newRecord(UDT<R> type) {
        return (R)((UDTRecord)Tools.newRecord(false, type, this.configuration()).operate(null));
    }

    @Override
    public <R extends Record> R newRecord(Table<R> table) {
        return Tools.newRecord(false, table, this.configuration()).operate(null);
    }

    @Override
    public <R extends Record> R newRecord(Table<R> table, Object source2) {
        return (R)Tools.newRecord(false, table, this.configuration()).operate(record -> {
            record.from(source2);
            return record;
        });
    }

    @Override
    public <R extends Record> Result<R> newResult(Table<R> table) {
        return new ResultImpl(this.configuration(), (AbstractRow)table.fieldsRow());
    }

    @Override
    public Result<Record> newResult(Field<?> ... fields) {
        return new ResultImpl<Record>(this.configuration(), fields);
    }

    @Override
    public Result<Record> newResult(Collection<? extends Field<?>> fields) {
        return new ResultImpl<Record>(this.configuration(), fields);
    }

    @Override
    public <T1> Result<Record1<T1>> newResult(Field<T1> field1) {
        return this.newResult(new Field[]{field1});
    }

    @Override
    public <T1, T2> Result<Record2<T1, T2>> newResult(Field<T1> field1, Field<T2> field2) {
        return this.newResult(new Field[]{field1, field2});
    }

    @Override
    public <T1, T2, T3> Result<Record3<T1, T2, T3>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.newResult(new Field[]{field1, field2, field3});
    }

    @Override
    public <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.newResult(new Field[]{field1, field2, field3, field4});
    }

    @Override
    public <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.newResult(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public <R extends Record> Result<R> fetch(ResultQuery<R> query) {
        return Tools.attach(query, this.configuration(), query::fetch);
    }

    @Override
    public <R extends Record> Cursor<R> fetchLazy(ResultQuery<R> query) {
        return Tools.attach(query, this.configuration(), query::fetchLazy);
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(ResultQuery<R> query) {
        return this.fetchAsync(Tools.configuration(this.configuration()).executorProvider().provide(), query);
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor2, ResultQuery<R> query) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(() -> this.fetch(query))), () -> executor2);
    }

    @Override
    public <R extends Record> Stream<R> fetchStream(ResultQuery<R> query) {
        return Tools.attach(query, this.configuration(), query::stream);
    }

    @Override
    public <R extends Record> Results fetchMany(ResultQuery<R> query) {
        return Tools.attach(query, this.configuration(), query::fetchMany);
    }

    @Override
    public <R extends Record> R fetchOne(ResultQuery<R> query) {
        return (R)Tools.attach(query, this.configuration(), query::fetchOne);
    }

    @Override
    public <R extends Record> R fetchSingle(ResultQuery<R> query) {
        return (R)Tools.attach(query, this.configuration(), query::fetchSingle);
    }

    @Override
    public <R extends Record> Optional<R> fetchOptional(ResultQuery<R> query) {
        return Optional.ofNullable(this.fetchOne(query));
    }

    @Override
    public <T> T fetchValue(Table<? extends Record1<T>> table) {
        return this.fetchValue(this.selectFrom(table));
    }

    @Override
    public <T, R extends Record1<T>> T fetchValue(ResultQuery<R> query) {
        return (T)Tools.attach(query, this.configuration(), () -> this.value1((Record1)this.fetchOne(query)));
    }

    @Override
    public <T> T fetchValue(TableField<?, T> field2) {
        return this.fetchValue(field2, DSL.noCondition());
    }

    @Override
    public <T> T fetchValue(TableField<?, T> field2, Condition condition) {
        return this.fetchValue(this.select(field2).from((TableLike<?>)field2.getTable()).where(condition));
    }

    @Override
    public <T> T fetchValue(SelectField<T> field2) {
        return field2 instanceof TableField ? this.fetchValue((TableField)field2) : (field2 instanceof Table ? this.fetchValue(this.select(field2).from((TableLike<?>)((Table)field2))) : this.fetchValue(this.select(field2)));
    }

    @Override
    public <T, R extends Record1<T>> Optional<T> fetchOptionalValue(ResultQuery<R> query) {
        return Optional.ofNullable(this.fetchValue(query));
    }

    @Override
    public <T> Optional<T> fetchOptionalValue(TableField<?, T> field2) {
        return Optional.ofNullable(this.fetchValue(field2));
    }

    @Override
    public <T> Optional<T> fetchOptionalValue(TableField<?, T> field2, Condition condition) {
        return Optional.ofNullable(this.fetchValue(field2, condition));
    }

    @Override
    public <T> List<T> fetchValues(Table<? extends Record1<T>> table) {
        return this.fetchValues(this.selectFrom(table));
    }

    @Override
    public <T, R extends Record1<T>> List<T> fetchValues(ResultQuery<R> query) {
        return this.fetch(query).getValues(0);
    }

    @Override
    public <T> List<T> fetchValues(TableField<?, T> field2) {
        return this.fetchValues(field2, DSL.noCondition());
    }

    @Override
    public <T> List<T> fetchValues(TableField<?, T> field2, Condition condition) {
        return this.fetchValues(this.select(field2).from((TableLike<?>)field2.getTable()).where(condition));
    }

    @Override
    public <K, V> Map<K, V> fetchMap(ResultQuery<? extends Record2<K, V>> query) {
        return Tools.attach(query, this.configuration(), () -> query.collect(Records.intoMap()));
    }

    @Override
    public <K, V> Map<K, List<V>> fetchGroups(ResultQuery<? extends Record2<K, V>> query) {
        return Tools.attach(query, this.configuration(), () -> query.collect(Records.intoGroups()));
    }

    private final <T, R extends Record1<T>> T value1(R record) {
        if (record == null) {
            return null;
        }
        if (record.size() != 1) {
            throw new InvalidResultException("Record contains more than one value : " + String.valueOf(record));
        }
        return record.value1();
    }

    @Override
    public <R extends TableRecord<R>> Result<R> fetchByExample(R example) {
        return this.selectFrom(example.getTable()).where(DSL.condition(example)).fetch();
    }

    @Override
    public int fetchCount(Select<?> query) {
        return (Integer)((Record1)new FetchCount(this.configuration(), query).fetchOne()).value1();
    }

    @Override
    public int fetchCount(Table<?> table) {
        return this.fetchCount(table, DSL.noCondition());
    }

    @Override
    public int fetchCount(Table<?> table, Condition condition) {
        return this.selectCount().from((TableLike<?>)table).where(condition).fetchOne(0, Integer.TYPE);
    }

    @Override
    public int fetchCount(Table<?> table, Condition ... conditions) {
        return this.fetchCount(table, DSL.and(conditions));
    }

    @Override
    public int fetchCount(Table<?> table, Collection<? extends Condition> conditions) {
        return this.fetchCount(table, DSL.and(conditions));
    }

    @Override
    public boolean fetchExists(Select<?> query) {
        return this.fetchValue(DSL.exists(query));
    }

    @Override
    public boolean fetchExists(Table<?> table) {
        return this.fetchExists(table, DSL.noCondition());
    }

    @Override
    public boolean fetchExists(Table<?> table, Condition condition) {
        return this.fetchExists(this.selectOne().from((TableLike<?>)table).where(condition));
    }

    @Override
    public boolean fetchExists(Table<?> table, Condition ... conditions) {
        return this.fetchExists(table, DSL.and(conditions));
    }

    @Override
    public boolean fetchExists(Table<?> table, Collection<? extends Condition> conditions) {
        return this.fetchExists(table, DSL.and(conditions));
    }

    @Override
    public int execute(Query query) {
        return Tools.attach(query, this.configuration(), query::execute);
    }

    @Override
    public <R extends Record> Result<R> fetch(Table<R> table) {
        return this.fetch(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> Result<R> fetch(Table<R> table, Condition condition) {
        return this.selectFrom(table).where(condition).fetch();
    }

    @Override
    public <R extends Record> Result<R> fetch(Table<R> table, Condition ... conditions) {
        return this.fetch(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> Result<R> fetch(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetch(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> R fetchOne(Table<R> table) {
        return this.fetchOne(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> R fetchOne(Table<R> table, Condition condition) {
        return Tools.fetchOne(this.fetchLazy(table, condition));
    }

    @Override
    public <R extends Record> R fetchOne(Table<R> table, Condition ... conditions) {
        return this.fetchOne(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> R fetchOne(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchOne(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table) {
        return this.fetchSingle(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition condition) {
        return Tools.fetchSingle(this.fetchLazy(table, condition));
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition ... conditions) {
        return this.fetchSingle(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchSingle(table, DSL.and(conditions));
    }

    @Override
    public Record fetchSingle(SelectField<?> ... fields) throws DataAccessException {
        return this.fetchSingle(Arrays.asList(fields));
    }

    @Override
    public Record fetchSingle(Collection<? extends SelectField<?>> fields) throws DataAccessException {
        return this.fetchSingle(DSL.select(fields));
    }

    @Override
    public <T1> Record1<T1> fetchSingle(SelectField<T1> field1) {
        return (Record1)this.fetchSingle(new SelectField[]{field1});
    }

    @Override
    public <T1, T2> Record2<T1, T2> fetchSingle(SelectField<T1> field1, SelectField<T2> field2) {
        return (Record2)this.fetchSingle(new SelectField[]{field1, field2});
    }

    @Override
    public <T1, T2, T3> Record3<T1, T2, T3> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return (Record3)this.fetchSingle(new SelectField[]{field1, field2, field3});
    }

    @Override
    public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return (Record4)this.fetchSingle(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return (Record5)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return (Record6)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return (Record7)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return (Record8)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return (Record9)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return (Record10)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return (Record11)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return (Record12)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return (Record13)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return (Record14)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return (Record15)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return (Record16)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return (Record17)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return (Record18)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return (Record19)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return (Record20)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return (Record21)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> fetchSingle(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return (Record22)this.fetchSingle(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2) {
        return this.fetchSingle(table, new Condition[]{c1, c2});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16, Condition c17) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16, Condition c17, Condition c18) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16, Condition c17, Condition c18, Condition c19) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16, Condition c17, Condition c18, Condition c19, Condition c20) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16, Condition c17, Condition c18, Condition c19, Condition c20, Condition c21) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21});
    }

    @Override
    public <R extends Record> R fetchSingle(Table<R> table, Condition c1, Condition c2, Condition c3, Condition c4, Condition c5, Condition c6, Condition c7, Condition c8, Condition c9, Condition c10, Condition c11, Condition c12, Condition c13, Condition c14, Condition c15, Condition c16, Condition c17, Condition c18, Condition c19, Condition c20, Condition c21, Condition c22) {
        return this.fetchSingle(table, new Condition[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22});
    }

    @Override
    public <R extends Record> Optional<R> fetchOptional(Table<R> table) {
        return this.fetchOptional(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> Optional<R> fetchOptional(Table<R> table, Condition condition) {
        return Optional.ofNullable(this.fetchOne(table, condition));
    }

    @Override
    public <R extends Record> Optional<R> fetchOptional(Table<R> table, Condition ... conditions) {
        return this.fetchOptional(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> Optional<R> fetchOptional(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchOptional(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> R fetchAny(Table<R> table) {
        return this.fetchAny(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> R fetchAny(Table<R> table, Condition condition) {
        return this.selectFrom(table).where(condition).limit(1).fetchOne();
    }

    @Override
    public <R extends Record> R fetchAny(Table<R> table, Condition ... conditions) {
        return this.fetchAny(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> R fetchAny(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchAny(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> Cursor<R> fetchLazy(Table<R> table) {
        return this.fetchLazy(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> Cursor<R> fetchLazy(Table<R> table, Condition condition) {
        return this.selectFrom(table).where(condition).fetchLazy();
    }

    @Override
    public <R extends Record> Cursor<R> fetchLazy(Table<R> table, Condition ... conditions) {
        return this.fetchLazy(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> Cursor<R> fetchLazy(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchLazy(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> table) {
        return this.fetchAsync(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> table, Condition condition) {
        return this.selectFrom(table).where(condition).fetchAsync();
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> table, Condition ... conditions) {
        return this.fetchAsync(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchAsync(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor2, Table<R> table) {
        return this.fetchAsync(executor2, table, DSL.noCondition());
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor2, Table<R> table, Condition condition) {
        return this.selectFrom(table).where(condition).fetchAsync(executor2);
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor2, Table<R> table, Condition ... conditions) {
        return this.fetchAsync(executor2, table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor executor2, Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchAsync(executor2, table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> Stream<R> fetchStream(Table<R> table) {
        return this.fetchStream(table, DSL.noCondition());
    }

    @Override
    public <R extends Record> Stream<R> fetchStream(Table<R> table, Condition condition) {
        return this.selectFrom(table).where(condition).stream();
    }

    @Override
    public <R extends Record> Stream<R> fetchStream(Table<R> table, Condition ... conditions) {
        return this.fetchStream(table, DSL.and(conditions));
    }

    @Override
    public <R extends Record> Stream<R> fetchStream(Table<R> table, Collection<? extends Condition> conditions) {
        return this.fetchStream(table, DSL.and(conditions));
    }

    @Override
    public int executeInsert(TableRecord<?> record) {
        InsertQuery<?> insert = this.insertQuery(record.getTable());
        insert.setRecord(record);
        return insert.execute();
    }

    @Override
    public int executeUpdate(UpdatableRecord<?> record) {
        UpdateQuery<UpdatableRecord<?>> update = this.updateQuery(record.getTable());
        Tools.addConditions(update, record, record.getTable().getPrimaryKey().getFieldsArray());
        update.setRecord(record);
        return update.execute();
    }

    @Override
    public int executeUpdate(TableRecord<?> record, Condition condition) {
        UpdateQuery<?> update = this.updateQuery(record.getTable());
        update.addConditions(condition);
        update.setRecord(record);
        return update.execute();
    }

    @Override
    public int executeDelete(UpdatableRecord<?> record) {
        DeleteQuery delete = this.deleteQuery(record.getTable());
        Tools.addConditions(delete, record, record.getTable().getPrimaryKey().getFieldsArray());
        return delete.execute();
    }

    @Override
    public int executeDelete(TableRecord<?> record, Condition condition) {
        DeleteQuery<?> delete = this.deleteQuery(record.getTable());
        delete.addConditions(condition);
        return delete.execute();
    }

    public String toString() {
        return this.configuration().toString();
    }

    static {
        try {
            Class.forName(SQLDataType.class.getName());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

