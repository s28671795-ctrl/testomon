/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import io.r2dbc.spi.ConnectionFactory;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import org.jooq.CheckReturnValue;
import org.jooq.CommentOnIsStep;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.ConnectionCallable;
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
import org.jooq.PlainSQL;
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
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectQuery;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.Source;
import org.jooq.Statement;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableRecord;
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
import org.jooq.exception.ConfigurationException;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataDefinitionException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.TooManyRowsException;
import org.jooq.impl.ParserException;
import org.jooq.tools.jdbc.MockCallable;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockRunnable;
import org.jooq.util.xml.jaxb.InformationSchema;

public interface DSLContext
extends Scope {
    @Nullable
    public Schema map(Schema var1);

    @Nullable
    public <R extends Record> Table<R> map(Table<R> var1);

    @NotNull
    public Parser parser();

    @NotNull
    public Connection parsingConnection();

    @NotNull
    public DataSource parsingDataSource();

    @NotNull
    public ConnectionFactory parsingConnectionFactory();

    @NotNull
    public Connection diagnosticsConnection();

    @NotNull
    public DataSource diagnosticsDataSource();

    @ApiStatus.Experimental
    @NotNull
    public Migrations migrations();

    @NotNull
    public Meta meta();

    @NotNull
    public Meta meta(DatabaseMetaData var1);

    @NotNull
    public Meta meta(Catalog ... var1);

    @NotNull
    public Meta meta(Schema ... var1);

    @NotNull
    public Meta meta(Table<?> ... var1);

    @NotNull
    public Meta meta(InformationSchema var1);

    @NotNull
    public Meta meta(String ... var1) throws DataDefinitionException, ParserException;

    @NotNull
    public Meta meta(Source ... var1) throws DataDefinitionException, ParserException;

    @NotNull
    public Meta meta(Query ... var1) throws DataDefinitionException;

    @NotNull
    public InformationSchema informationSchema(Catalog var1);

    @NotNull
    public InformationSchema informationSchema(Catalog ... var1);

    @NotNull
    public InformationSchema informationSchema(Schema var1);

    @NotNull
    public InformationSchema informationSchema(Schema ... var1);

    @NotNull
    public InformationSchema informationSchema(Table<?> var1);

    @NotNull
    public InformationSchema informationSchema(Table<?> ... var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE})
    @NotNull
    public Explain explain(Query var1);

    @Blocking
    public <T> T transactionResult(TransactionalCallable<T> var1);

    @Blocking
    public <T> T transactionResult(ContextTransactionalCallable<T> var1) throws ConfigurationException;

    @Blocking
    public void transaction(TransactionalRunnable var1);

    @Blocking
    public void transaction(ContextTransactionalRunnable var1) throws ConfigurationException;

    @NotNull
    public <T> CompletionStage<T> transactionResultAsync(TransactionalCallable<T> var1) throws ConfigurationException;

    @NotNull
    public CompletionStage<Void> transactionAsync(TransactionalRunnable var1) throws ConfigurationException;

    @NotNull
    public <T> CompletionStage<T> transactionResultAsync(Executor var1, TransactionalCallable<T> var2) throws ConfigurationException;

    @NotNull
    public CompletionStage<Void> transactionAsync(Executor var1, TransactionalRunnable var2) throws ConfigurationException;

    @NotNull
    public <T> Publisher<T> transactionPublisher(TransactionalPublishable<T> var1);

    @Blocking
    public <T> T connectionResult(ConnectionCallable<T> var1);

    @Blocking
    public void connection(ConnectionRunnable var1);

    public <T> T mockResult(MockDataProvider var1, MockCallable<T> var2);

    public void mock(MockDataProvider var1, MockRunnable var2);

    @Deprecated(forRemoval=true, since="3.10")
    @ApiStatus.Internal
    @NotNull
    public RenderContext renderContext();

    @NotNull
    public String render(QueryPart var1);

    @NotNull
    public String renderNamedParams(QueryPart var1);

    @NotNull
    public String renderNamedOrInlinedParams(QueryPart var1);

    @NotNull
    public String renderInlined(QueryPart var1);

    @NotNull
    public List<Object> extractBindValues(QueryPart var1);

    @NotNull
    public Map<String, Param<?>> extractParams(QueryPart var1);

    @Nullable
    public Param<?> extractParam(QueryPart var1, String var2);

    @Deprecated(forRemoval=true, since="3.10")
    @ApiStatus.Internal
    @NotNull
    public BindContext bindContext(PreparedStatement var1);

    public void attach(Attachable ... var1);

    public void attach(Collection<? extends Attachable> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> LoaderOptionsStep<R> loadInto(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Queries queries(Query ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Queries queries(Collection<? extends Query> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Block begin(Statement ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Block begin(Collection<? extends Statement> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public Block statements(Statement ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public Block statements(Collection<? extends Statement> var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public RowCountQuery query(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public RowCountQuery query(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public RowCountQuery query(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public RowCountQuery query(String var1, QueryPart ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    @Blocking
    public Result<Record> fetch(SQL var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Result<Record> fetch(String var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Result<Record> fetch(String var1, Object ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Result<Record> fetch(String var1, QueryPart ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(SQL var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(String var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(String var1, Object ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(String var1, QueryPart ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(String var1);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(String var1, QueryPart ... var2);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, SQL var2);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, String var2);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, String var2, Object ... var3);

    @Support
    @PlainSQL
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, String var2, QueryPart ... var3);

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(SQL var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(String var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(String var1, Object ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(String var1, QueryPart ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Results fetchMany(SQL var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Results fetchMany(String var1) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Results fetchMany(String var1, Object ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Results fetchMany(String var1, QueryPart ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Record fetchOne(SQL var1) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Record fetchOne(String var1) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Record fetchOne(String var1, Object ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Record fetchOne(String var1, QueryPart ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Record fetchSingle(SQL var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Record fetchSingle(String var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Record fetchSingle(String var1, Object ... var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Record fetchSingle(String var1, QueryPart ... var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(SQL var1) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(String var1) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(String var1, Object ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(String var1, QueryPart ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Object fetchValue(SQL var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Object fetchValue(String var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Object fetchValue(String var1, Object ... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @Nullable
    @Blocking
    public Object fetchValue(String var1, QueryPart ... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<?> fetchOptionalValue(SQL var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<?> fetchOptionalValue(String var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<?> fetchOptionalValue(String var1, Object ... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public Optional<?> fetchOptionalValue(String var1, QueryPart ... var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public List<?> fetchValues(SQL var1) throws DataAccessException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public List<?> fetchValues(String var1) throws DataAccessException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public List<?> fetchValues(String var1, Object ... var2) throws DataAccessException, InvalidResultException;

    @Support
    @PlainSQL
    @NotNull
    @Blocking
    public List<?> fetchValues(String var1, QueryPart ... var2) throws DataAccessException, InvalidResultException;

    @Support
    @PlainSQL
    @Blocking
    public int execute(SQL var1) throws DataAccessException;

    @Support
    @PlainSQL
    @Blocking
    public int execute(String var1) throws DataAccessException;

    @Support
    @PlainSQL
    @Blocking
    public int execute(String var1, Object ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @Blocking
    public int execute(String var1, QueryPart ... var2) throws DataAccessException;

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public ResultQuery<Record> resultQuery(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public ResultQuery<Record> resultQuery(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public ResultQuery<Record> resultQuery(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public ResultQuery<Record> resultQuery(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @Blocking
    public Result<Record> fetch(ResultSet var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Result<Record> fetch(ResultSet var1, Field<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Result<Record> fetch(ResultSet var1, DataType<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Result<Record> fetch(ResultSet var1, Class<?> ... var2) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public Record fetchOne(ResultSet var1) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public Record fetchOne(ResultSet var1, Field<?> ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public Record fetchOne(ResultSet var1, DataType<?> ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public Record fetchOne(ResultSet var1, Class<?> ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Record fetchSingle(ResultSet var1) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Record fetchSingle(ResultSet var1, Field<?> ... var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Record fetchSingle(ResultSet var1, DataType<?> ... var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Record fetchSingle(ResultSet var1, Class<?> ... var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(ResultSet var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(ResultSet var1, Field<?> ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(ResultSet var1, DataType<?> ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Optional<Record> fetchOptional(ResultSet var1, Class<?> ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public Object fetchValue(ResultSet var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @Nullable
    @Blocking
    public <T> T fetchValue(ResultSet var1, Field<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @Nullable
    @Blocking
    public <T> T fetchValue(ResultSet var1, DataType<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @Nullable
    @Blocking
    public <T> T fetchValue(ResultSet var1, Class<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public Optional<?> fetchOptionalValue(ResultSet var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> Optional<T> fetchOptionalValue(ResultSet var1, Field<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> Optional<T> fetchOptionalValue(ResultSet var1, DataType<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> Optional<T> fetchOptionalValue(ResultSet var1, Class<T> var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public List<?> fetchValues(ResultSet var1) throws DataAccessException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> List<T> fetchValues(ResultSet var1, Field<T> var2) throws DataAccessException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> List<T> fetchValues(ResultSet var1, DataType<T> var2) throws DataAccessException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> List<T> fetchValues(ResultSet var1, Class<T> var2) throws DataAccessException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(ResultSet var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(ResultSet var1, Field<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(ResultSet var1, DataType<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Cursor<Record> fetchLazy(ResultSet var1, Class<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(ResultSet var1);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(ResultSet var1, Field<?> ... var2);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(ResultSet var1, DataType<?> ... var2);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(ResultSet var1, Class<?> ... var2);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2, Field<?> ... var3);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2, DataType<?> ... var3);

    @Support
    @NotNull
    public CompletionStage<Result<Record>> fetchAsync(Executor var1, ResultSet var2, Class<?> ... var3);

    @Support
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(ResultSet var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(ResultSet var1, Field<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(ResultSet var1, DataType<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Stream<Record> fetchStream(ResultSet var1, Class<?> ... var2) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromTXT(String var1) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromTXT(String var1, String var2) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromHTML(String var1) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromCSV(String var1) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromCSV(String var1, char var2) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromCSV(String var1, boolean var2) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromCSV(String var1, boolean var2, char var3) throws DataAccessException;

    @Support
    @NotNull
    public Result<Record> fetchFromJSON(String var1);

    @Support
    @NotNull
    public Result<Record> fetchFromXML(String var1);

    @Support
    @NotNull
    public Result<Record> fetchFromStringData(String[] ... var1);

    @Support
    @NotNull
    public Result<Record> fetchFromStringData(List<String[]> var1);

    @Support
    @NotNull
    public Result<Record> fetchFromStringData(List<String[]> var1, boolean var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, String ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, Collection<String> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(Name var1, Name ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(Name var1, Collection<? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep with(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep1 with(String var1, String var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep2 with(String var1, String var2, String var3);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep3 with(String var1, String var2, String var3, String var4);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep4 with(String var1, String var2, String var3, String var4, String var5);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep5 with(String var1, String var2, String var3, String var4, String var5, String var6);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep6 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep7 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep8 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep9 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep10 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep11 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep12 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep13 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep14 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep15 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep16 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep17 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep18 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep19 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep20 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep21 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep22 with(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22, String var23);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep1 with(Name var1, Name var2);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep2 with(Name var1, Name var2, Name var3);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep3 with(Name var1, Name var2, Name var3, Name var4);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep4 with(Name var1, Name var2, Name var3, Name var4, Name var5);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep5 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep6 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep7 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep8 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep9 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep10 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep11 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep12 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep13 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep14 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep15 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep16 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep17 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep18 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep19 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep20 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep21 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22);

    @Support
    @NotNull
    @CheckReturnValue
    public WithAsStep22 with(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22, Name var23);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep with(CommonTableExpression<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public WithStep with(Collection<? extends CommonTableExpression<?>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(String var1, String ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(String var1, Collection<String> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(Name var1, Name ... var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(Name var1, Collection<? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep withRecursive(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep1 withRecursive(String var1, String var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep2 withRecursive(String var1, String var2, String var3);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep3 withRecursive(String var1, String var2, String var3, String var4);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep4 withRecursive(String var1, String var2, String var3, String var4, String var5);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep5 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep6 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep7 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep8 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep9 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep10 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep11 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep12 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep13 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep14 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep15 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep16 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep17 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep18 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep19 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep20 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep21 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep22 withRecursive(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, String var13, String var14, String var15, String var16, String var17, String var18, String var19, String var20, String var21, String var22, String var23);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep1 withRecursive(Name var1, Name var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep2 withRecursive(Name var1, Name var2, Name var3);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep3 withRecursive(Name var1, Name var2, Name var3, Name var4);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep4 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep5 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep6 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep7 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep8 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep9 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep10 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep11 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep12 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep13 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep14 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep15 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep16 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep17 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep18 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep19 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep20 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep21 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithAsStep22 withRecursive(Name var1, Name var2, Name var3, Name var4, Name var5, Name var6, Name var7, Name var8, Name var9, Name var10, Name var11, Name var12, Name var13, Name var14, Name var15, Name var16, Name var17, Name var18, Name var19, Name var20, Name var21, Name var22, Name var23);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithStep withRecursive(CommonTableExpression<?> ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public WithStep withRecursive(Collection<? extends CommonTableExpression<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> SelectWhereStep<R> selectFrom(TableLike<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(Name var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(String var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public SelectWhereStep<Record> selectFrom(String var1, QueryPart ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> select(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> select(SelectFieldOrAsterisk ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> var1, SelectField<T2> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> selectDistinct(Collection<? extends SelectFieldOrAsterisk> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record> selectDistinct(SelectFieldOrAsterisk ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21);

    @Support
    @NotNull
    @CheckReturnValue
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22);

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record1<Integer>> selectZero();

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record1<Integer>> selectOne();

    @Support
    @NotNull
    @CheckReturnValue
    public SelectSelectStep<Record1<Integer>> selectCount();

    @Support
    @NotNull
    @CheckReturnValue
    public SelectQuery<Record> selectQuery();

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> SelectQuery<R> selectQuery(TableLike<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertQuery<R> insertQuery(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertSetStep<R> insertInto(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> var1, Field<T1> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22, Field<T22> var23);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> var1, Field<?> ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> var1, Collection<? extends Field<?>> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> UpdateQuery<R> updateQuery(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> UpdateSetFirstStep<R> update(Table<R> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record> MergeUsingStep<R> mergeInto(Table<R> var1);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> var1, Field<T1> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> var1, Field<T1> var2, Field<T2> var3, Field<T3> var4, Field<T4> var5, Field<T5> var6, Field<T6> var7, Field<T7> var8, Field<T8> var9, Field<T9> var10, Field<T10> var11, Field<T11> var12, Field<T12> var13, Field<T13> var14, Field<T14> var15, Field<T15> var16, Field<T16> var17, Field<T17> var18, Field<T18> var19, Field<T19> var20, Field<T20> var21, Field<T21> var22, Field<T22> var23);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> var1, Field<?> ... var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_9_5, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> var1, Collection<? extends Field<?>> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> DeleteQuery<R> deleteQuery(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> DeleteUsingStep<R> deleteFrom(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> DeleteUsingStep<R> delete(Table<R> var1);

    public void batched(BatchedRunnable var1);

    public <T> T batchedResult(BatchedCallable<T> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batch(Query ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batch(Queries var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public Batch batch(String ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batch(Collection<? extends Query> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public BatchBindStep batch(Query var1);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public BatchBindStep batch(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batch(Query var1, Object[] ... var2);

    @Support
    @PlainSQL
    @NotNull
    @CheckReturnValue
    public Batch batch(String var1, Object[] ... var2);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchStore(UpdatableRecord<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchStore(Collection<? extends UpdatableRecord<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchInsert(TableRecord<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchInsert(Collection<? extends TableRecord<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchUpdate(UpdatableRecord<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchUpdate(Collection<? extends UpdatableRecord<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchMerge(UpdatableRecord<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchMerge(Collection<? extends UpdatableRecord<?>> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchDelete(UpdatableRecord<?> ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Batch batchDelete(Collection<? extends UpdatableRecord<?>> var1);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Catalog var1);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Catalog var1, DDLExportConfiguration var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Catalog var1, DDLFlag ... var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Schema var1);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Schema var1, DDLExportConfiguration var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Schema var1, DDLFlag ... var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Table<?> var1);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Table<?> var1, DDLExportConfiguration var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Table<?> var1, DDLFlag ... var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Table<?> ... var1);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Table<?>[] var1, DDLExportConfiguration var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Table<?>[] var1, DDLFlag ... var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Collection<? extends Table<?>> var1);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Collection<? extends Table<?>> var1, DDLFlag ... var2);

    @NotNull
    @CheckReturnValue
    public Queries ddl(Collection<? extends Table<?>> var1, DDLExportConfiguration var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseStep alterDatabase(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseStep alterDatabase(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseStep alterDatabase(Catalog var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseStep alterDatabaseIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseStep alterDatabaseIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterDatabaseStep alterDatabaseIfExists(Catalog var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> AlterDomainStep<T> alterDomain(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> AlterDomainStep<T> alterDomain(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> AlterDomainStep<T> alterDomain(Domain<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> AlterDomainStep<T> alterDomainIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> AlterDomainStep<T> alterDomainIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T> AlterDomainStep<T> alterDomainIfExists(Domain<T> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexOnStep alterIndex(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexOnStep alterIndex(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexOnStep alterIndex(Index var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexOnStep alterIndexIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexOnStep alterIndexIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2016})
    @NotNull
    @CheckReturnValue
    public AlterIndexOnStep alterIndexIfExists(Index var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSchemaStep alterSchema(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSchemaStep alterSchema(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA})
    @NotNull
    @CheckReturnValue
    public AlterSchemaStep alterSchema(Schema var1);

    @Support(value={SQLDialect.H2, SQLDialect.HANA, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    public AlterSchemaStep alterSchemaIfExists(String var1);

    @Support(value={SQLDialect.H2, SQLDialect.HANA, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    public AlterSchemaStep alterSchemaIfExists(Name var1);

    @Support(value={SQLDialect.H2, SQLDialect.HANA, SQLDialect.SNOWFLAKE})
    @NotNull
    @CheckReturnValue
    public AlterSchemaStep alterSchemaIfExists(Schema var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceStep<Number> alterSequence(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceStep<Number> alterSequence(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T extends Number> AlterSequenceStep<T> alterSequence(Sequence<T> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceStep<Number> alterSequenceIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterSequenceStep<Number> alterSequenceIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public <T extends Number> AlterSequenceStep<T> alterSequenceIfExists(Sequence<T> var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeStep alterType(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.POSTGRES})
    @NotNull
    @CheckReturnValue
    public AlterTypeStep alterType(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterView(String var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterView(Name var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterView(Table<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterViewIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterViewIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterViewIfExists(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterView(Table<?> var1, Field<?> ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterViewStep alterView(Table<?> var1, Collection<? extends Field<?>> var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnTable(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnTable(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnTable(Table<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnView(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnView(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnView(Table<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnColumn(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnColumn(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CommentOnIsStep commentOnColumn(Field<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDatabaseFinalStep createDatabase(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDatabaseFinalStep createDatabase(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDatabaseFinalStep createDatabase(Catalog var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDatabaseFinalStep createDatabaseIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDatabaseFinalStep createDatabaseIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDatabaseFinalStep createDatabaseIfNotExists(Catalog var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainAsStep createDomain(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainAsStep createDomain(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainAsStep createDomain(Domain<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainAsStep createDomainIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainAsStep createDomainIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateDomainAsStep createDomainIfNotExists(Domain<?> var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateFunctionParametersStep createFunction(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateFunctionParametersStep createFunction(Name var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateFunctionParametersStep createOrReplaceFunction(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateFunctionParametersStep createOrReplaceFunction(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndex(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndex(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndex(Index var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndex();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndexIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndexIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndexIfNotExists(Index var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createIndexIfNotExists();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndex(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndex(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndex(Index var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndex();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndexIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndexIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndexIfNotExists(Index var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateIndexStep createUniqueIndexIfNotExists();

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateProcedureParametersStep createProcedure(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateProcedureParametersStep createProcedure(Name var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateProcedureParametersStep createOrReplaceProcedure(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES_11, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateProcedureParametersStep createOrReplaceProcedure(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTable(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTable(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTable(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTableIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTableIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTableIfNotExists(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTemporaryTable(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTemporaryTable(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTemporaryTable(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTemporaryTableIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTemporaryTableIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createTemporaryTableIfNotExists(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createGlobalTemporaryTable(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createGlobalTemporaryTable(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createGlobalTemporaryTable(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createGlobalTemporaryTableIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createGlobalTemporaryTableIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTableElementListStep createGlobalTemporaryTableIfNotExists(Table<?> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateTriggerEventStep createTrigger(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateTriggerEventStep createTrigger(Name var1);

    @Support(value={SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateTriggerEventStep createOrReplaceTrigger(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public CreateTriggerEventStep createOrReplaceTrigger(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSchemaFinalStep createSchema(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSchemaFinalStep createSchema(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSchemaFinalStep createSchema(Schema var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSchemaFinalStep createSchemaIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSchemaFinalStep createSchemaIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSchemaFinalStep createSchemaIfNotExists(Schema var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep createSequence(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep createSequence(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep createSequence(Sequence<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep createSequenceIfNotExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep createSequenceIfNotExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateSequenceFlagsStep createSequenceIfNotExists(Sequence<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDatabaseFinalStep dropDatabase(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDatabaseFinalStep dropDatabase(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDatabaseFinalStep dropDatabase(Catalog var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDatabaseFinalStep dropDatabaseIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDatabaseFinalStep dropDatabaseIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDatabaseFinalStep dropDatabaseIfExists(Catalog var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainCascadeStep dropDomain(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainCascadeStep dropDomain(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainCascadeStep dropDomain(Domain<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainCascadeStep dropDomainIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainCascadeStep dropDomainIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropDomainCascadeStep dropDomainIfExists(Domain<?> var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropFunction(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropFunction(Name var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropFunctionIfExists(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropFunctionIfExists(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropIndexOnStep dropIndex(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropIndexOnStep dropIndex(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropIndexOnStep dropIndex(Index var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropIndexOnStep dropIndexIfExists(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropIndexOnStep dropIndexIfExists(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropIndexOnStep dropIndexIfExists(Index var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropProcedure(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropProcedure(Name var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropProcedureIfExists(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropProcedureIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSchemaStep dropSchema(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSchemaStep dropSchema(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSchemaStep dropSchema(Schema var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSchemaStep dropSchemaIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSchemaStep dropSchemaIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSchemaStep dropSchemaIfExists(Schema var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSequenceFinalStep dropSequence(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSequenceFinalStep dropSequence(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSequenceFinalStep dropSequence(Sequence<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSequenceFinalStep dropSequenceIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSequenceFinalStep dropSequenceIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropSequenceFinalStep dropSequenceIfExists(Sequence<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTable(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTable(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTable(Table<?> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTableIfExists(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTableIfExists(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTableIfExists(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTemporaryTable(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTemporaryTable(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTemporaryTable(Table<?> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTemporaryTableIfExists(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTemporaryTableIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTableStep dropTemporaryTableIfExists(Table<?> var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropTrigger(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropTrigger(Name var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropTriggerIfExists(String var1);

    @Support(value={SQLDialect.DB2, SQLDialect.FIREBIRD_3_0, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public DDLQuery dropTriggerIfExists(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropViewFinalStep dropView(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropViewFinalStep dropView(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropViewFinalStep dropView(Table<?> var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropViewFinalStep dropViewIfExists(String var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropViewFinalStep dropViewIfExists(Name var1);

    @Support(value={SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropViewFinalStep dropViewIfExists(Table<?> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public GrantOnStep grant(Privilege var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public GrantOnStep grant(Privilege ... var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public GrantOnStep grant(Collection<? extends Privilege> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RevokeOnStep revoke(Privilege var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RevokeOnStep revoke(Privilege ... var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RevokeOnStep revoke(Collection<? extends Privilege> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB_22, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RevokeOnStep revokeGrantOptionFor(Privilege var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB_22, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RevokeOnStep revokeGrantOptionFor(Privilege ... var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB_22, SQLDialect.HSQLDB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RevokeOnStep revokeGrantOptionFor(Collection<? extends Privilege> var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery set(String var1, Param<?> var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery set(Name var1, Param<?> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setLocal(String var1, Param<?> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setLocal(Name var1, Param<?> var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setCatalog(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setCatalog(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.TERADATA})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setCatalog(Catalog var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setSchema(String var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setSchema(Name var1);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RowCountQuery setSchema(Schema var1);

    @Support
    @NotNull
    @CheckReturnValue
    public TruncateIdentityStep<Record> truncate(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public TruncateIdentityStep<Record> truncate(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> TruncateIdentityStep<R> truncate(Table<R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public TruncateIdentityStep<Record> truncateTable(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public TruncateIdentityStep<Record> truncateTable(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public <R extends Record> TruncateIdentityStep<R> truncateTable(Table<R> var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CallArgsStep call(String var1);

    @Support(value={SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    @Pro
    public CallArgsStep call(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Query startTransaction();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Query savepoint(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Query savepoint(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Query releaseSavepoint(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Query releaseSavepoint(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public Query commit();

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public RollbackToSavepointStep rollback();

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(String var1, String ... var2);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(Name var1, Name ... var2);

    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(Table<?> var1, Field<?> ... var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(Name var1, Function<? super Field<?>, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(Name var1, BiFunction<? super Field<?>, ? super Integer, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createView(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(String var1, String ... var2);

    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(Name var1, Name ... var2);

    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(Table<?> var1, Field<?> ... var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(Name var1, Function<? super Field<?>, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(Name var1, BiFunction<? super Field<?>, ? super Integer, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ASE_16_0, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB_21, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createOrReplaceView(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(String var1, String ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(Name var1, Name ... var2);

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(Table<?> var1, Field<?> ... var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(String var1, Function<? super Field<?>, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(String var1, BiFunction<? super Field<?>, ? super Integer, ? extends String> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(Name var1, Function<? super Field<?>, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(Name var1, BiFunction<? super Field<?>, ? super Integer, ? extends Name> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(Table<?> var1, Function<? super Field<?>, ? extends Field<?>> var2);

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateViewAsStep<Record> createViewIfNotExists(Table<?> var1, BiFunction<? super Field<?>, ? super Integer, ? extends Field<?>> var2);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTypeStep createType(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public CreateTypeStep createType(Name var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropType(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropType(Name var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropType(String ... var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropType(Name ... var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropType(Collection<?> var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropTypeIfExists(String var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropTypeIfExists(Name var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropTypeIfExists(String ... var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropTypeIfExists(Name ... var1);

    @Support(value={SQLDialect.COCKROACHDB, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public DropTypeStep dropTypeIfExists(Collection<?> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public AlterTableStep alterTable(String var1);

    @Support
    @NotNull
    @CheckReturnValue
    public AlterTableStep alterTable(Name var1);

    @Support
    @NotNull
    @CheckReturnValue
    public AlterTableStep alterTable(Table<?> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterTableStep alterTableIfExists(String var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterTableStep alterTableIfExists(Name var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.H2, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    @CheckReturnValue
    public AlterTableStep alterTableIfExists(Table<?> var1);

    @Support(value={SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE})
    @NotNull
    public BigInteger lastID() throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public BigInteger nextval(String var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public BigInteger nextval(Name var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T extends Number> T nextval(Sequence<T> var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T extends Number> List<T> nextvals(Sequence<T> var1, int var2) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public BigInteger currval(String var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public BigInteger currval(Name var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB_10_3, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB})
    @NotNull
    public <T extends Number> T currval(Sequence<T> var1) throws DataAccessException;

    @NotNull
    public <R extends UDTRecord<R>> R newRecord(UDT<R> var1);

    @NotNull
    public <R extends Record> R newRecord(Table<R> var1);

    @NotNull
    public <R extends Record> R newRecord(Table<R> var1, Object var2);

    @NotNull
    public Record newRecord(Field<?> ... var1);

    @NotNull
    public Record newRecord(Collection<? extends Field<?>> var1);

    @NotNull
    public <T1> Record1<T1> newRecord(Field<T1> var1);

    @NotNull
    public <T1, T2> Record2<T1, T2> newRecord(Field<T1> var1, Field<T2> var2);

    @NotNull
    public <T1, T2, T3> Record3<T1, T2, T3> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @NotNull
    public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @NotNull
    public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @NotNull
    public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> newRecord(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

    @NotNull
    public <R extends Record> Result<R> newResult(Table<R> var1);

    @NotNull
    public Result<Record> newResult(Field<?> ... var1);

    @NotNull
    public Result<Record> newResult(Collection<? extends Field<?>> var1);

    @NotNull
    public <T1> Result<Record1<T1>> newResult(Field<T1> var1);

    @NotNull
    public <T1, T2> Result<Record2<T1, T2>> newResult(Field<T1> var1, Field<T2> var2);

    @NotNull
    public <T1, T2, T3> Result<Record3<T1, T2, T3>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @NotNull
    public <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4);

    @NotNull
    public <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5);

    @NotNull
    public <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21);

    @NotNull
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> newResult(Field<T1> var1, Field<T2> var2, Field<T3> var3, Field<T4> var4, Field<T5> var5, Field<T6> var6, Field<T7> var7, Field<T8> var8, Field<T9> var9, Field<T10> var10, Field<T11> var11, Field<T12> var12, Field<T13> var13, Field<T14> var14, Field<T15> var15, Field<T16> var16, Field<T17> var17, Field<T18> var18, Field<T19> var19, Field<T20> var20, Field<T21> var21, Field<T22> var22);

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Result<R> fetch(ResultQuery<R> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Cursor<R> fetchLazy(ResultQuery<R> var1) throws DataAccessException;

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(ResultQuery<R> var1);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, ResultQuery<R> var2);

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Stream<R> fetchStream(ResultQuery<R> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Results fetchMany(ResultQuery<R> var1) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchOne(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(ResultQuery<R> var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Optional<R> fetchOptional(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @Blocking
    public <T> T fetchValue(Table<? extends Record1<T>> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @Blocking
    public <T, R extends Record1<T>> T fetchValue(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @Blocking
    public <T> T fetchValue(TableField<?, T> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @Blocking
    public <T> T fetchValue(TableField<?, T> var1, Condition var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Blocking
    public <T> T fetchValue(SelectField<T> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T, R extends Record1<T>> Optional<T> fetchOptionalValue(ResultQuery<R> var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> Optional<T> fetchOptionalValue(TableField<?, T> var1) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> Optional<T> fetchOptionalValue(TableField<?, T> var1, Condition var2) throws DataAccessException, TooManyRowsException, InvalidResultException;

    @Support
    @NotNull
    @Blocking
    public <T> List<T> fetchValues(Table<? extends Record1<T>> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T, R extends Record1<T>> List<T> fetchValues(ResultQuery<R> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T> List<T> fetchValues(TableField<?, T> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T> List<T> fetchValues(TableField<?, T> var1, Condition var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <K, V> Map<K, V> fetchMap(ResultQuery<? extends Record2<K, V>> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <K, V> Map<K, List<V>> fetchGroups(ResultQuery<? extends Record2<K, V>> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends TableRecord<R>> Result<R> fetchByExample(R var1) throws DataAccessException;

    @Support
    @Blocking
    public int fetchCount(Select<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public int fetchCount(Table<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public int fetchCount(Table<?> var1, Condition var2) throws DataAccessException;

    @Support
    @Blocking
    public int fetchCount(Table<?> var1, Condition ... var2) throws DataAccessException;

    @Support
    @Blocking
    public int fetchCount(Table<?> var1, Collection<? extends Condition> var2) throws DataAccessException;

    @Support
    @Blocking
    public boolean fetchExists(Select<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public boolean fetchExists(Table<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public boolean fetchExists(Table<?> var1, Condition var2) throws DataAccessException;

    @Support
    @Blocking
    public boolean fetchExists(Table<?> var1, Condition ... var2) throws DataAccessException;

    @Support
    @Blocking
    public boolean fetchExists(Table<?> var1, Collection<? extends Condition> var2) throws DataAccessException;

    @Support
    @Blocking
    public int execute(Query var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Result<R> fetch(Table<R> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Result<R> fetch(Table<R> var1, Condition var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Result<R> fetch(Table<R> var1, Condition ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Result<R> fetch(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchOne(Table<R> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchOne(Table<R> var1, Condition var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchOne(Table<R> var1, Condition ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchOne(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition ... var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public Record fetchSingle(SelectField<?> ... var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public Record fetchSingle(Collection<? extends SelectField<?>> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1> Record1<T1> fetchSingle(SelectField<T1> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2> Record2<T1, T2> fetchSingle(SelectField<T1> var1, SelectField<T2> var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3> Record3<T1, T2, T3> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> fetchSingle(SelectField<T1> var1, SelectField<T2> var2, SelectField<T3> var3, SelectField<T4> var4, SelectField<T5> var5, SelectField<T6> var6, SelectField<T7> var7, SelectField<T8> var8, SelectField<T9> var9, SelectField<T10> var10, SelectField<T11> var11, SelectField<T12> var12, SelectField<T13> var13, SelectField<T14> var14, SelectField<T15> var15, SelectField<T16> var16, SelectField<T17> var17, SelectField<T18> var18, SelectField<T19> var19, SelectField<T20> var20, SelectField<T21> var21, SelectField<T22> var22) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17, Condition var18) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17, Condition var18, Condition var19) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17, Condition var18, Condition var19, Condition var20) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17, Condition var18, Condition var19, Condition var20, Condition var21) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17, Condition var18, Condition var19, Condition var20, Condition var21, Condition var22) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> R fetchSingle(Table<R> var1, Condition var2, Condition var3, Condition var4, Condition var5, Condition var6, Condition var7, Condition var8, Condition var9, Condition var10, Condition var11, Condition var12, Condition var13, Condition var14, Condition var15, Condition var16, Condition var17, Condition var18, Condition var19, Condition var20, Condition var21, Condition var22, Condition var23) throws DataAccessException, NoDataFoundException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Optional<R> fetchOptional(Table<R> var1) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Optional<R> fetchOptional(Table<R> var1, Condition var2) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Optional<R> fetchOptional(Table<R> var1, Condition ... var2) throws DataAccessException, TooManyRowsException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Optional<R> fetchOptional(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException, TooManyRowsException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchAny(Table<R> var1) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchAny(Table<R> var1, Condition var2) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchAny(Table<R> var1, Condition ... var2) throws DataAccessException;

    @Support
    @Nullable
    @Blocking
    public <R extends Record> R fetchAny(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Cursor<R> fetchLazy(Table<R> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Cursor<R> fetchLazy(Table<R> var1, Condition var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Cursor<R> fetchLazy(Table<R> var1, Condition ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Cursor<R> fetchLazy(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException;

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> var1);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> var1, Condition var2);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> var1, Condition ... var2);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Table<R> var1, Collection<? extends Condition> var2);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, Table<R> var2);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, Table<R> var2, Condition var3);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, Table<R> var2, Condition ... var3);

    @Support
    @NotNull
    public <R extends Record> CompletionStage<Result<R>> fetchAsync(Executor var1, Table<R> var2, Collection<? extends Condition> var3);

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Stream<R> fetchStream(Table<R> var1) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Stream<R> fetchStream(Table<R> var1, Condition var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Stream<R> fetchStream(Table<R> var1, Condition ... var2) throws DataAccessException;

    @Support
    @NotNull
    @Blocking
    public <R extends Record> Stream<R> fetchStream(Table<R> var1, Collection<? extends Condition> var2) throws DataAccessException;

    @Support
    @Blocking
    public int executeInsert(TableRecord<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public int executeUpdate(UpdatableRecord<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public int executeUpdate(TableRecord<?> var1, Condition var2) throws DataAccessException;

    @Support
    @Blocking
    public int executeDelete(UpdatableRecord<?> var1) throws DataAccessException;

    @Support
    @Blocking
    public int executeDelete(TableRecord<?> var1, Condition var2) throws DataAccessException;
}

