/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.Id
 *  jakarta.persistence.Table
 */
package org.jooq.impl;

import io.r2dbc.spi.R2dbcException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.ArrayOrAssociativeArrayRecord;
import org.jooq.ArrayRecord;
import org.jooq.AssociativeArrayRecord;
import org.jooq.Asterisk;
import org.jooq.Attachable;
import org.jooq.BindContext;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.ConverterContext;
import org.jooq.Converters;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.EmbeddableRecord;
import org.jooq.EnumType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.Fields;
import org.jooq.ForeignKey;
import org.jooq.Function1;
import org.jooq.Function2;
import org.jooq.Function3;
import org.jooq.Generator;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.JSONEntry;
import org.jooq.JoinType;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QualifiedAsterisk;
import org.jooq.QualifiedRecord;
import org.jooq.QuantifiedSelect;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordQualifier;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.ResultOrRows;
import org.jooq.ResultQuery;
import org.jooq.Results;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Scope;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SortField;
import org.jooq.Source;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.XML;
import org.jooq.conf.BackslashEscaping;
import org.jooq.conf.NestedCollectionEmulation;
import org.jooq.conf.ParamType;
import org.jooq.conf.ParseNameCase;
import org.jooq.conf.RenderDefaultNullability;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.ThrowExceptions;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.DetachedException;
import org.jooq.exception.ExceptionTools;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.jooq.exception.MappingException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.exception.SQLStateClass;
import org.jooq.exception.TemplatingException;
import org.jooq.exception.TooManyRowsException;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractDMLQueryAsResultQuery;
import org.jooq.impl.AbstractDelegatingDMLQuery;
import org.jooq.impl.AbstractDelegatingQuery;
import org.jooq.impl.AbstractDelegatingTable;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractResultQuery;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.Alias;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.Cache;
import org.jooq.impl.CacheType;
import org.jooq.impl.Cast;
import org.jooq.impl.Coerce;
import org.jooq.impl.Convert;
import org.jooq.impl.ConvertedVal;
import org.jooq.impl.CursorImpl;
import org.jooq.impl.DBMS_SQL;
import org.jooq.impl.DDLStatementType;
import org.jooq.impl.DSL;
import org.jooq.impl.DateAsTimestampBinding;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultConverterContext;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultParseContext;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.DeleteQueryImpl;
import org.jooq.impl.DerivedTable;
import org.jooq.impl.EmbeddableTableField;
import org.jooq.impl.FieldAlias;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Identifiers;
import org.jooq.impl.IdentityConverter;
import org.jooq.impl.InsertQueryImpl;
import org.jooq.impl.Intern;
import org.jooq.impl.Internal;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.JoinTable;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.LocalDateAsLocalDateTimeBinding;
import org.jooq.impl.MetaDataFieldProvider;
import org.jooq.impl.ObjIntPredicate;
import org.jooq.impl.QOM;
import org.jooq.impl.QuantifiedSelectImpl;
import org.jooq.impl.RecordDelegate;
import org.jooq.impl.RecordImpl1;
import org.jooq.impl.RecordImpl10;
import org.jooq.impl.RecordImpl11;
import org.jooq.impl.RecordImpl12;
import org.jooq.impl.RecordImpl13;
import org.jooq.impl.RecordImpl14;
import org.jooq.impl.RecordImpl15;
import org.jooq.impl.RecordImpl16;
import org.jooq.impl.RecordImpl17;
import org.jooq.impl.RecordImpl18;
import org.jooq.impl.RecordImpl19;
import org.jooq.impl.RecordImpl2;
import org.jooq.impl.RecordImpl20;
import org.jooq.impl.RecordImpl21;
import org.jooq.impl.RecordImpl22;
import org.jooq.impl.RecordImpl3;
import org.jooq.impl.RecordImpl4;
import org.jooq.impl.RecordImpl5;
import org.jooq.impl.RecordImpl6;
import org.jooq.impl.RecordImpl7;
import org.jooq.impl.RecordImpl8;
import org.jooq.impl.RecordImpl9;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.ReferenceImpl;
import org.jooq.impl.ResultsImpl;
import org.jooq.impl.RowImpl1;
import org.jooq.impl.RowImpl10;
import org.jooq.impl.RowImpl11;
import org.jooq.impl.RowImpl12;
import org.jooq.impl.RowImpl13;
import org.jooq.impl.RowImpl14;
import org.jooq.impl.RowImpl15;
import org.jooq.impl.RowImpl16;
import org.jooq.impl.RowImpl17;
import org.jooq.impl.RowImpl18;
import org.jooq.impl.RowImpl19;
import org.jooq.impl.RowImpl2;
import org.jooq.impl.RowImpl20;
import org.jooq.impl.RowImpl21;
import org.jooq.impl.RowImpl22;
import org.jooq.impl.RowImpl3;
import org.jooq.impl.RowImpl4;
import org.jooq.impl.RowImpl5;
import org.jooq.impl.RowImpl6;
import org.jooq.impl.RowImpl7;
import org.jooq.impl.RowImpl8;
import org.jooq.impl.RowImpl9;
import org.jooq.impl.RowImplN;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SQLField;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SelectImpl;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.SeparatedQueryPart;
import org.jooq.impl.SimpleCheckQueryPart;
import org.jooq.impl.SimpleQueryPart;
import org.jooq.impl.TableAlias;
import org.jooq.impl.TableImpl;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.ThrowingIntFunction;
import org.jooq.impl.ThrowingIntIntFunction;
import org.jooq.impl.ThrowingIntPredicate;
import org.jooq.impl.ThrowingObjIntFunction;
import org.jooq.impl.ThrowingPredicate;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.UpdateQueryImpl;
import org.jooq.impl.Val;
import org.jooq.tools.Ints;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

final class Tools {
    static final JooqLogger log = JooqLogger.getLogger(Tools.class);
    static final byte[] EMPTY_BYTE = new byte[0];
    static final Catalog[] EMPTY_CATALOG = new Catalog[0];
    static final Check<?>[] EMPTY_CHECK = new Check[0];
    static final Clause[] EMPTY_CLAUSE = new Clause[0];
    static final Collection<?>[] EMPTY_COLLECTION = new Collection[0];
    static final CommonTableExpression<?>[] EMPTY_COMMON_TABLE_EXPRESSION = new CommonTableExpression[0];
    static final ExecuteListener[] EMPTY_EXECUTE_LISTENER = new ExecuteListener[0];
    static final Field<?>[] EMPTY_FIELD = new Field[0];
    static final FieldOrRow[] EMPTY_FIELD_OR_ROW = new FieldOrRow[0];
    static final int[] EMPTY_INT = new int[0];
    static final JSONEntry<?>[] EMPTY_JSONENTRY = new JSONEntry[0];
    static final Name[] EMPTY_NAME = new Name[0];
    static final Object[] EMPTY_OBJECT = new Object[0];
    static final Param<?>[] EMPTY_PARAM = new Param[0];
    static final OrderField<?>[] EMPTY_ORDERFIELD = new OrderField[0];
    static final Query[] EMPTY_QUERY = new Query[0];
    static final QueryPart[] EMPTY_QUERYPART = new QueryPart[0];
    static final Record[] EMPTY_RECORD = new Record[0];
    static final Row[] EMPTY_ROW = new Row[0];
    static final Schema[] EMTPY_SCHEMA = new Schema[0];
    static final SortField<?>[] EMPTY_SORTFIELD = new SortField[0];
    static final Source[] EMPTY_SOURCE = new Source[0];
    static final String[] EMPTY_STRING = new String[0];
    static final Table<?>[] EMPTY_TABLE = new Table[0];
    static final TableField<?, ?>[] EMPTY_TABLE_FIELD = new TableField[0];
    static final TableRecord<?>[] EMPTY_TABLE_RECORD = new TableRecord[0];
    static final UpdatableRecord<?>[] EMPTY_UPDATABLE_RECORD = new UpdatableRecord[0];
    static final DataKey[] DATAKEY_RESET_IN_SUBQUERY_SCOPE = (DataKey[])Stream.concat(Stream.concat(Stream.of(BooleanDataKey.values()), Stream.of(SimpleDataKey.values())), Stream.of(ExtendedDataKey.values())).filter(t2 -> ((DataKey)((Object)t2)).resetInSubqueryScope()).toArray(DataKey[]::new);
    static final char ESCAPE = '!';
    private static final Object initLock = new Object();
    private static volatile JPANamespace jpaNamespace;
    private static volatile Boolean isKotlinAvailable;
    private static volatile Reflect ktJvmClassMapping;
    private static volatile Reflect ktKClasses;
    private static volatile Reflect ktKClass;
    private static volatile Reflect ktKTypeParameter;
    static int maxConsumedExceptions;
    static int maxConsumedResults;
    private static final Pattern DASH_PATTERN;
    private static final Pattern PIPE_PATTERN;
    private static final Pattern PLUS_PATTERN;
    private static final char[] WHITESPACE_CHARACTERS;
    private static final char[][] JDBC_ESCAPE_PREFIXES;
    private static final char[] TOKEN_SINGLE_LINE_COMMENT;
    private static final char[] TOKEN_SINGLE_LINE_COMMENT_C;
    private static final char[] TOKEN_HASH;
    private static final char[] TOKEN_MULTI_LINE_COMMENT_OPEN;
    private static final char[] TOKEN_MULTI_LINE_COMMENT_CLOSE;
    private static final char[] TOKEN_APOS;
    private static final char[] TOKEN_ESCAPED_APOS;
    private static final char[][] NON_BIND_VARIABLE_SUFFIXES;
    private static final char[][] BIND_VARIABLE_SUFFIXES;
    private static final char[] HEX_DIGITS;
    private static final byte[] HEX_LOOKUP;
    static final Set<SQLDialect> REQUIRES_BACKSLASH_ESCAPING;
    static final Set<SQLDialect> NO_SUPPORT_NULL;
    static final Set<SQLDialect> NO_SUPPORT_BINARY_TYPE_LENGTH;
    static final Set<SQLDialect> NO_SUPPORT_CAST_TYPE_IN_DDL;
    static final Set<SQLDialect> SUPPORT_NON_BIND_VARIABLE_SUFFIXES;
    static final Set<SQLDialect> SUPPORT_POSTGRES_LITERALS;
    static final Set<SQLDialect> DEFAULT_BEFORE_NULL;
    static final Set<SQLDialect> NO_SUPPORT_TIMESTAMP_PRECISION;
    static final Set<SQLDialect> DEFAULT_TIMESTAMP_NOT_NULL;
    @Pro
    static final Set<SQLDialect> COMPUTED_EXCLUDES_NULL;
    @Pro
    static final Set<SQLDialect> COMPUTED_NO_TYPE_DECLARATION;
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_STRING_LENGTH;
    @Pro
    static final Set<SQLDialect> REQUIRE_CAST_TYPE_FOR_MAX_LENGTH;
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_NUMERIC_PRECISION;
    @Pro
    static final Set<SQLDialect> DEFAULT_NOT_NULL;
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_INTEGER_TYPES;
    private static final int FIELD_NAME_CACHE_SIZE = 128;
    private static final String[] FIELD_NAME_STRINGS;
    private static final Name[] FIELD_NAMES;
    static final Lazy<Configuration> CONFIG;
    static final Lazy<Configuration> CONFIG_UNQUOTED;
    static final Lazy<DSLContext> CTX;
    private static final Pattern NEW_LINES;
    private static final Pattern P_PARSE_HTML_ROW;
    private static final Pattern P_PARSE_HTML_COL_HEAD;
    private static final Pattern P_PARSE_HTML_COL_BODY;
    private static final Set<SQLDialect> REQUIRE_IDENTITY_AFTER_NULL;
    private static final Set<SQLDialect> SUPPORT_PG_IDENTITY;
    @Pro
    private static final Set<SQLDialect> REQUIRE_COMPUTED_AFTER_NULL;

    Tools() {
    }

    static final List<Row> rows(Result<?> result) {
        return Tools.map(result, (? super T r) -> r.valuesRow());
    }

    @Pro
    static final <R extends ArrayOrAssociativeArrayRecord<?>> R newArrayRecord(Class<R> type) {
        try {
            return (R)((ArrayOrAssociativeArrayRecord)type.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("ArrayRecord type does not provide a constructor with signature ArrayRecord() : " + String.valueOf(type) + ". Exception : " + e.getMessage());
        }
    }

    @Pro
    static final <R extends AssociativeArrayRecord<?, ?>> R newAssociativeArrayRecord(Class<R> type) {
        try {
            return (R)((AssociativeArrayRecord)type.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("AssociativeArrayRecord type does not provide a constructor with signature AssociativeArrayRecord() : " + String.valueOf(type) + ". Exception : " + e.getMessage());
        }
    }

    @Pro
    static final Name dbmsSQLType(DataType<?> dataType) {
        Class<?> type = dataType.getType();
        if (dataType.isNumeric()) {
            return DSL.unquotedName("DBMS_SQL", "NUMBER_TABLE");
        }
        if (dataType.isLob()) {
            if (dataType.isString()) {
                return DSL.unquotedName("DBMS_SQL", "CLOB_TABLE");
            }
            return DSL.unquotedName("DBMS_SQL", "BLOB_TABLE");
        }
        if (dataType.isString()) {
            return DSL.unquotedName("DBMS_SQL", "VARCHAR2_TABLE");
        }
        if (dataType.isBoolean()) {
            return DSL.unquotedName("DBMS_SQL", "VARCHAR2_TABLE");
        }
        if (Tools.isDate(type)) {
            return DSL.unquotedName("DBMS_SQL", "DATE_TABLE");
        }
        if (Tools.isTime(type)) {
            return DSL.unquotedName("DBMS_SQL", "TIME_TABLE");
        }
        if (Tools.isTimestamp(type)) {
            return DSL.unquotedName("DBMS_SQL", "TIMESTAMP_TABLE");
        }
        if (type == OffsetTime.class) {
            return DSL.unquotedName("DBMS_SQL", "TIME_WITH_TIME_ZONE_TABLE");
        }
        if (type == OffsetDateTime.class) {
            return DSL.unquotedName("DBMS_SQL", "TIMESTAMP_WITH_TIME_ZONE_TABLE");
        }
        if (type == Instant.class) {
            return DSL.unquotedName("DBMS_SQL", "TIMESTAMP_WITH_TIME_ZONE_TABLE");
        }
        throw new IllegalArgumentException("Data Type cannot be mapped to DBMS_SQL.XXX_TABLE type: " + String.valueOf(dataType));
    }

    @SafeVarargs
    @Pro
    static final <T> ArrayRecord<T> dbmsSQLTable(DataType<T> dataType, T ... array) {
        return Tools.dbmsSQLTable(dataType, Arrays.asList(array));
    }

    @Pro
    static final <T> ArrayRecord<T> dbmsSQLTable(DataType<T> dataType, Collection<T> collection) {
        Class<T> type = dataType.getType();
        if (dataType.isNumeric()) {
            return new DBMS_SQL.NUMBER_TABLE(collection);
        }
        if (dataType.isLob()) {
            if (dataType.isString()) {
                return new DBMS_SQL.CLOB_TABLE(collection);
            }
            return new DBMS_SQL.BLOB_TABLE(collection);
        }
        if (dataType.isString()) {
            return new DBMS_SQL.VARCHAR2_TABLE(collection);
        }
        if (dataType.isBoolean()) {
            return new DBMS_SQL.VARCHAR2_TABLE(Tools.map(collection, (? super T v) -> Convert.convert((Object)Convert.convert(v, Integer.class), String.class)));
        }
        if (Tools.isDate(type)) {
            return new DBMS_SQL.DATE_TABLE(collection);
        }
        if (Tools.isTime(type)) {
            return new DBMS_SQL.TIME_TABLE(collection);
        }
        if (Tools.isTimestamp(type)) {
            return new DBMS_SQL.TIMESTAMP_TABLE(collection);
        }
        if (type == OffsetTime.class) {
            return new DBMS_SQL.TIME_WITH_TIME_ZONE_TABLE(collection);
        }
        if (type == OffsetDateTime.class) {
            return new DBMS_SQL.TIMESTAMP_WITH_TIME_ZONE_TABLE(collection);
        }
        throw new IllegalArgumentException("Data Type cannot be mapped to DBMS_SQL.XXX_TABLE type: " + String.valueOf(dataType));
    }

    static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type) {
        return Tools.newRecord(fetched, type, null);
    }

    static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<R> type, AbstractRow<R> fields) {
        return Tools.newRecord(fetched, type, fields, null);
    }

    static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, RecordQualifier<R> type) {
        return Tools.newRecord(fetched, type, null);
    }

    static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, RecordQualifier<R> type, Configuration configuration) {
        return Tools.newRecord(fetched, type.getRecordType(), (AbstractRow)type.fieldsRow(), configuration);
    }

    static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Class<? extends R> type, AbstractRow<? extends R> fields, Configuration configuration) {
        return Tools.newRecord(fetched, Tools.recordFactory(type, fields), configuration);
    }

    static final <R extends Record> RecordDelegate<R> newRecord(boolean fetched, Supplier<R> factory2, Configuration configuration) {
        return new RecordDelegate<R>(configuration, factory2, fetched);
    }

    static final <R extends Record> AbstractRow<R> row0(FieldsImpl<R> fields) {
        switch (fields.size()) {
            case 1: {
                return new RowImpl1((FieldsImpl<?>)fields);
            }
            case 2: {
                return new RowImpl2((FieldsImpl<?>)fields);
            }
            case 3: {
                return new RowImpl3((FieldsImpl<?>)fields);
            }
            case 4: {
                return new RowImpl4((FieldsImpl<?>)fields);
            }
            case 5: {
                return new RowImpl5((FieldsImpl<?>)fields);
            }
            case 6: {
                return new RowImpl6((FieldsImpl<?>)fields);
            }
            case 7: {
                return new RowImpl7((FieldsImpl<?>)fields);
            }
            case 8: {
                return new RowImpl8((FieldsImpl<?>)fields);
            }
            case 9: {
                return new RowImpl9((FieldsImpl<?>)fields);
            }
            case 10: {
                return new RowImpl10((FieldsImpl<?>)fields);
            }
            case 11: {
                return new RowImpl11((FieldsImpl<?>)fields);
            }
            case 12: {
                return new RowImpl12((FieldsImpl<?>)fields);
            }
            case 13: {
                return new RowImpl13((FieldsImpl<?>)fields);
            }
            case 14: {
                return new RowImpl14((FieldsImpl<?>)fields);
            }
            case 15: {
                return new RowImpl15((FieldsImpl<?>)fields);
            }
            case 16: {
                return new RowImpl16((FieldsImpl<?>)fields);
            }
            case 17: {
                return new RowImpl17((FieldsImpl<?>)fields);
            }
            case 18: {
                return new RowImpl18((FieldsImpl<?>)fields);
            }
            case 19: {
                return new RowImpl19((FieldsImpl<?>)fields);
            }
            case 20: {
                return new RowImpl20((FieldsImpl<?>)fields);
            }
            case 21: {
                return new RowImpl21((FieldsImpl<?>)fields);
            }
            case 22: {
                return new RowImpl22((FieldsImpl<?>)fields);
            }
        }
        return new RowImplN((FieldsImpl<?>)fields);
    }

    static final AbstractRow<?> row0(Collection<? extends Field<?>> fields) {
        return Tools.row0(fields.toArray(EMPTY_FIELD));
    }

    static final AbstractRow<?> row0(Field<?> ... fields) {
        return Tools.row0(new FieldsImpl(fields));
    }

    static final Class<? extends AbstractRecord> recordType(int length) {
        switch (length) {
            case 1: {
                return RecordImpl1.class;
            }
            case 2: {
                return RecordImpl2.class;
            }
            case 3: {
                return RecordImpl3.class;
            }
            case 4: {
                return RecordImpl4.class;
            }
            case 5: {
                return RecordImpl5.class;
            }
            case 6: {
                return RecordImpl6.class;
            }
            case 7: {
                return RecordImpl7.class;
            }
            case 8: {
                return RecordImpl8.class;
            }
            case 9: {
                return RecordImpl9.class;
            }
            case 10: {
                return RecordImpl10.class;
            }
            case 11: {
                return RecordImpl11.class;
            }
            case 12: {
                return RecordImpl12.class;
            }
            case 13: {
                return RecordImpl13.class;
            }
            case 14: {
                return RecordImpl14.class;
            }
            case 15: {
                return RecordImpl15.class;
            }
            case 16: {
                return RecordImpl16.class;
            }
            case 17: {
                return RecordImpl17.class;
            }
            case 18: {
                return RecordImpl18.class;
            }
            case 19: {
                return RecordImpl19.class;
            }
            case 20: {
                return RecordImpl20.class;
            }
            case 21: {
                return RecordImpl21.class;
            }
            case 22: {
                return RecordImpl22.class;
            }
        }
        return RecordImplN.class;
    }

    static final <R extends Record> Supplier<R> recordFactory(Class<? extends R> type, AbstractRow<? extends R> row) {
        if (type == AbstractRecord.class || type == Record.class || InternalRecord.class.isAssignableFrom(type)) {
            switch (row.size()) {
                case 1: {
                    return () -> new RecordImpl1(row);
                }
                case 2: {
                    return () -> new RecordImpl2(row);
                }
                case 3: {
                    return () -> new RecordImpl3(row);
                }
                case 4: {
                    return () -> new RecordImpl4(row);
                }
                case 5: {
                    return () -> new RecordImpl5(row);
                }
                case 6: {
                    return () -> new RecordImpl6(row);
                }
                case 7: {
                    return () -> new RecordImpl7(row);
                }
                case 8: {
                    return () -> new RecordImpl8(row);
                }
                case 9: {
                    return () -> new RecordImpl9(row);
                }
                case 10: {
                    return () -> new RecordImpl10(row);
                }
                case 11: {
                    return () -> new RecordImpl11(row);
                }
                case 12: {
                    return () -> new RecordImpl12(row);
                }
                case 13: {
                    return () -> new RecordImpl13(row);
                }
                case 14: {
                    return () -> new RecordImpl14(row);
                }
                case 15: {
                    return () -> new RecordImpl15(row);
                }
                case 16: {
                    return () -> new RecordImpl16(row);
                }
                case 17: {
                    return () -> new RecordImpl17(row);
                }
                case 18: {
                    return () -> new RecordImpl18(row);
                }
                case 19: {
                    return () -> new RecordImpl19(row);
                }
                case 20: {
                    return () -> new RecordImpl20(row);
                }
                case 21: {
                    return () -> new RecordImpl21(row);
                }
                case 22: {
                    return () -> new RecordImpl22(row);
                }
            }
            return () -> new RecordImplN(row);
        }
        try {
            Constructor constructor = Reflect.accessible(type.getDeclaredConstructor(new Class[0]));
            return () -> {
                try {
                    return (Record)constructor.newInstance(new Object[0]);
                }
                catch (Exception e) {
                    throw new IllegalStateException("Could not construct new record", e);
                }
            };
        }
        catch (Exception e) {
            throw new IllegalStateException("Could not construct new record", e);
        }
    }

    static final void resetChangedOnNotNull(Record record) {
        int size = record.size();
        for (int i = 0; i < size; ++i) {
            if (record.get(i) != null || record.field(i).getDataType().nullable()) continue;
            record.changed(i, false);
        }
    }

    static final Configuration configurationOrThrow(Attachable attachable) {
        if (attachable.configuration() == null) {
            throw new DetachedException("No configuration attached: " + String.valueOf(attachable));
        }
        return Tools.configuration(attachable.configuration());
    }

    static final Configuration configuration(Attachable attachable) {
        return Tools.configuration(attachable.configuration());
    }

    static final Configuration configuration(Configuration configuration) {
        return configuration != null ? configuration : new DefaultConfiguration();
    }

    static final Configuration configuration(Scope scope) {
        return Tools.configuration(scope != null ? scope.configuration() : null);
    }

    static final <T, U> ContextConverter<T, U> converter(Configuration configuration, T instance, Class<T> tType, Class<U> uType) {
        Converter<T, U> result = Tools.configuration(configuration).converterProvider().provide(tType, uType);
        if (result == null) {
            result = CONFIG.get().converterProvider().provide(tType, uType);
        }
        if (result == null && tType == Converters.UnknownType.class) {
            result = Tools.converter(configuration, instance, instance == null ? Object.class : instance.getClass(), uType);
        }
        return result == null ? null : ContextConverter.scoped(result);
    }

    static final <T, U> ContextConverter<T, U> converterOrFail(Configuration configuration, T instance, Class<T> tType, Class<U> uType) {
        ContextConverter<T, U> result = Tools.converter(configuration, instance, tType, uType);
        if (result == null) {
            throw new DataTypeException("No Converter found for types " + tType.getName() + " and " + uType.getName());
        }
        return result;
    }

    static final <T, U> ContextConverter<T, U> converterOrFail(Attachable attachable, T instance, Class<T> tType, Class<U> uType) {
        return Tools.converterOrFail(Tools.configuration(attachable), instance, tType, uType);
    }

    static final Settings settings(Attachable attachable) {
        return Tools.configuration(attachable).settings();
    }

    static final Settings settings(Configuration configuration) {
        return Tools.configuration(configuration).settings();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final <T> T attach(Attachable attachable, Configuration configuration, Supplier<T> supplier) {
        Configuration previous = attachable.configuration();
        try {
            attachable.attach(configuration);
            T t2 = supplier.get();
            return t2;
        }
        finally {
            attachable.attach(previous);
        }
    }

    static final boolean attachRecords(Configuration configuration) {
        Settings settings;
        if (configuration != null && (settings = configuration.settings()) != null) {
            return !Boolean.FALSE.equals(settings.isAttachRecords());
        }
        return true;
    }

    static final Field<?>[] fieldArray(Collection<? extends Field<?>> fields) {
        return fields == null ? null : fields.toArray(EMPTY_FIELD);
    }

    static final DataType<?>[] dataTypes(Class<?>[] types) {
        return Tools.map(types, (? super T t2) -> t2 != null ? DSL.getDataType(t2) : DSL.getDataType(Object.class), DataType[]::new);
    }

    static final <T> SortField<T> sortField(OrderField<T> field2) {
        if (field2 instanceof SortField) {
            SortField s2 = (SortField)field2;
            return s2;
        }
        if (field2 instanceof Field) {
            Field f = (Field)field2;
            return f.sortDefault();
        }
        throw new IllegalArgumentException("Field not supported : " + String.valueOf(field2));
    }

    static final SortField<?>[] sortFields(OrderField<?>[] fields) {
        if (fields instanceof SortField[]) {
            SortField[] s2 = (SortField[])fields;
            return s2;
        }
        return Tools.map(fields, (? super T o) -> Tools.sortField(o), SortField[]::new);
    }

    static final List<SortField<?>> sortFields(Collection<? extends OrderField<?>> fields) {
        return Tools.map(fields, (? super T o) -> Tools.sortField(o));
    }

    private static final String fieldNameString0(int index) {
        return "v" + index;
    }

    static final String fieldNameString(int index) {
        return index < 128 ? FIELD_NAME_STRINGS[index] : Tools.fieldNameString0(index);
    }

    static final Name fieldName(int index) {
        return index < 128 ? FIELD_NAMES[index] : DSL.name(Tools.fieldNameString0(index));
    }

    static final Name[] fieldNames(int length) {
        Name[] result = new Name[length];
        for (int i = 0; i < length; ++i) {
            result[i] = Tools.fieldName(i);
        }
        return result;
    }

    static final String[] fieldNameStrings(int length) {
        String[] result = new String[length];
        for (int i = 0; i < length; ++i) {
            result[i] = Tools.fieldNameString(i);
        }
        return result;
    }

    static final Field<?>[] fields(int length) {
        return Tools.fields(length, SQLDataType.OTHER);
    }

    static final <T> Field<T>[] fields(int length, DataType<T> type) {
        Field[] result = new Field[length];
        for (int i = 0; i < length; ++i) {
            result[i] = DSL.field(Tools.fieldName(i), type);
        }
        return result;
    }

    static final boolean reference(Field<?> field2) {
        return field2 instanceof TableField || field2 instanceof SQLField && ((SQLField)field2).delegate.isName;
    }

    static final <T> Field<T> unqualified(Field<T> field2) {
        return DSL.field(field2.getUnqualifiedName(), field2.getDataType());
    }

    static final <T> SortField<T> unqualified(SortField<T> field2) {
        return field2.$field(Tools.unqualified(field2.$field()));
    }

    static final List<Field<?>> unaliasedFields(Collection<? extends Field<?>> fields) {
        return Tools.map(fields, (? super T f, int i) -> DSL.field(Tools.fieldName(i), f.getDataType()).as((Field)f));
    }

    static final <R extends Record, O extends Record> ReferenceImpl<R, O> aliasedKey(ForeignKey<R, O> key, Table<R> child, Table<O> parent) {
        return new ReferenceImpl<R, O>(child, key.getQualifiedName(), Tools.fieldsByName(child, key.getFieldsArray()), key.getKey(), Tools.fieldsByName(parent, key.getKeyFieldsArray()), key.enforced());
    }

    static final List<Field<?>> aliasedFields(Collection<? extends Field<?>> fields) {
        return Tools.map(fields, (? super T f, int i) -> f.as(Tools.fieldName(i)));
    }

    static final Field<?>[] fieldsByName(String[] fieldNames) {
        return Tools.fieldsByName(null, fieldNames);
    }

    static final Field<?>[] fieldsByName(Name tableName, int length) {
        Field[] result = new Field[length];
        if (tableName == null) {
            for (int i = 0; i < length; ++i) {
                result[i] = DSL.field(Tools.fieldName(i));
            }
        } else {
            for (int i = 0; i < length; ++i) {
                result[i] = DSL.field(DSL.name(tableName, Tools.fieldName(i)));
            }
        }
        return result;
    }

    static final <R extends Record> TableField<R, ?>[] fieldsByName(Table<R> tableName, Field<?>[] fieldNames) {
        if (tableName == null) {
            return Tools.map(fieldNames, (? super T n) -> (TableField)DSL.field(n.getUnqualifiedName(), n.getDataType()), TableField[]::new);
        }
        return Tools.map(fieldNames, (? super T n) -> (TableField)DSL.field(tableName.getQualifiedName().append(n.getUnqualifiedName()), n.getDataType()), TableField[]::new);
    }

    static final Field<?>[] fieldsByName(Name tableName, Name[] fieldNames) {
        if (tableName == null) {
            return Tools.map(fieldNames, (? super T n) -> DSL.field(n), Field[]::new);
        }
        return Tools.map(fieldNames, (? super T n) -> DSL.field(DSL.name(tableName, n)), Field[]::new);
    }

    static final Field<?>[] fieldsByName(String tableName, String[] fieldNames) {
        if (StringUtils.isEmpty(tableName)) {
            return Tools.map(fieldNames, (? super T n) -> DSL.field(DSL.name(n)), Field[]::new);
        }
        return Tools.map(fieldNames, (? super T n) -> DSL.field(DSL.name(tableName, n)), Field[]::new);
    }

    static final Field<?>[] fieldsByName(Name[] names) {
        return Tools.map(names, (? super T n) -> DSL.field(n), Field[]::new);
    }

    static final Name[] names(String[] names) {
        return Tools.map(names, (? super T n) -> DSL.name(n), Name[]::new);
    }

    static final List<Name> names(Collection<?> names) {
        return Tools.map(names, (? super T n) -> {
            Name name;
            return n instanceof Name ? (name = (Name)n) : DSL.name(String.valueOf(n));
        });
    }

    static final String sanitiseName(Configuration configuration, String name) {
        switch (configuration.family()) {
            case ORACLE: {
                return name.replace("\"", "");
            }
        }
        return name;
    }

    static final List<JSONEntry<?>> jsonEntries(Field<?>[] entries) {
        return Tools.map(entries, (? super T f) -> DSL.jsonEntry(f));
    }

    private static final IllegalArgumentException fieldExpected(Object value) {
        return new IllegalArgumentException("Cannot interpret argument of type " + String.valueOf(value.getClass()) + " as a Field: " + String.valueOf(value));
    }

    static final <T> Field<T>[] castAllIfNeeded(Field<?>[] fields, Class<T> type) {
        Field[] castFields = new Field[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            castFields[i] = Tools.castIfNeeded(fields[i], type);
        }
        return castFields;
    }

    static final <T> Field<T>[] castAllIfNeeded(Field<?>[] fields, DataType<T> type) {
        Field[] castFields = new Field[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            castFields[i] = Tools.castIfNeeded(fields[i], type);
        }
        return castFields;
    }

    static final <T> Field<T> castIfNeeded(Field<?> field2, Class<T> type) {
        if (field2.getType().equals(type)) {
            return field2;
        }
        return field2.cast(type);
    }

    static final <T> Field<T> castIfNeeded(Field<?> field2, DataType<T> type) {
        if (field2.getDataType().equals(type)) {
            return field2;
        }
        return field2.cast(type);
    }

    static final <T> Field<T> castIfNeeded(Field<?> field2, Field<T> type) {
        if (field2.getDataType().equals(type.getDataType())) {
            return field2;
        }
        return field2.cast(type);
    }

    static final Param<Byte> field(byte value) {
        return DSL.val((Object)value, SQLDataType.TINYINT);
    }

    static final Param<Byte> field(Byte value) {
        return DSL.val((Object)value, SQLDataType.TINYINT);
    }

    static final Param<UByte> field(UByte value) {
        return DSL.val((Object)value, SQLDataType.TINYINTUNSIGNED);
    }

    static final Param<Short> field(short value) {
        return DSL.val((Object)value, SQLDataType.SMALLINT);
    }

    static final Param<Short> field(Short value) {
        return DSL.val((Object)value, SQLDataType.SMALLINT);
    }

    static final Param<UShort> field(UShort value) {
        return DSL.val((Object)value, SQLDataType.SMALLINTUNSIGNED);
    }

    static final Param<Integer> field(int value) {
        return DSL.val((Object)value, SQLDataType.INTEGER);
    }

    static final Param<Integer> field(Integer value) {
        return DSL.val((Object)value, SQLDataType.INTEGER);
    }

    static final Param<UInteger> field(UInteger value) {
        return DSL.val((Object)value, SQLDataType.INTEGERUNSIGNED);
    }

    static final Param<Long> field(long value) {
        return DSL.val((Object)value, SQLDataType.BIGINT);
    }

    static final Param<Long> field(Long value) {
        return DSL.val((Object)value, SQLDataType.BIGINT);
    }

    static final Param<ULong> field(ULong value) {
        return DSL.val((Object)value, SQLDataType.BIGINTUNSIGNED);
    }

    static final Param<Float> field(float value) {
        return DSL.val((Object)Float.valueOf(value), SQLDataType.REAL);
    }

    static final Param<Float> field(Float value) {
        return DSL.val((Object)value, SQLDataType.REAL);
    }

    static final Param<Double> field(double value) {
        return DSL.val((Object)value, SQLDataType.DOUBLE);
    }

    static final Param<Double> field(Double value) {
        return DSL.val((Object)value, SQLDataType.DOUBLE);
    }

    static final Param<Boolean> field(boolean value) {
        return DSL.val((Object)value, SQLDataType.BOOLEAN);
    }

    static final Param<Boolean> field(Boolean value) {
        return DSL.val((Object)value, SQLDataType.BOOLEAN);
    }

    static final Param<BigDecimal> field(BigDecimal value) {
        return DSL.val((Object)value, SQLDataType.DECIMAL);
    }

    static final Param<BigInteger> field(BigInteger value) {
        return DSL.val((Object)value, SQLDataType.DECIMAL_INTEGER);
    }

    static final Param<byte[]> field(byte[] value) {
        return DSL.val((Object)value, SQLDataType.VARBINARY);
    }

    static final Param<String> field(String value) {
        return DSL.val((Object)value, SQLDataType.VARCHAR);
    }

    static final Param<Date> field(Date value) {
        return DSL.val((Object)value, SQLDataType.DATE);
    }

    static final Param<Time> field(Time value) {
        return DSL.val((Object)value, SQLDataType.TIME);
    }

    static final Param<Timestamp> field(Timestamp value) {
        return DSL.val((Object)value, SQLDataType.TIMESTAMP);
    }

    static final Param<LocalDate> field(LocalDate value) {
        return DSL.val((Object)value, SQLDataType.LOCALDATE);
    }

    static final Param<LocalTime> field(LocalTime value) {
        return DSL.val((Object)value, SQLDataType.LOCALTIME);
    }

    static final Param<LocalDateTime> field(LocalDateTime value) {
        return DSL.val((Object)value, SQLDataType.LOCALDATETIME);
    }

    static final Param<OffsetTime> field(OffsetTime value) {
        return DSL.val((Object)value, SQLDataType.OFFSETTIME);
    }

    static final Param<OffsetDateTime> field(OffsetDateTime value) {
        return DSL.val((Object)value, SQLDataType.OFFSETDATETIME);
    }

    static final Param<Instant> field(Instant value) {
        return DSL.val((Object)value, SQLDataType.INSTANT);
    }

    static final Param<UUID> field(UUID value) {
        return DSL.val((Object)value, SQLDataType.UUID);
    }

    static final Param<JSON> field(JSON value) {
        return DSL.val((Object)value, SQLDataType.JSON);
    }

    static final Param<JSONB> field(JSONB value) {
        return DSL.val((Object)value, SQLDataType.JSONB);
    }

    static final Param<XML> field(XML value) {
        return DSL.val((Object)value, SQLDataType.XML);
    }

    @Deprecated
    static final Field<Object> field(Name name) {
        return DSL.field(name);
    }

    private static final <T> Field<T> field(Object value, boolean defaultInferred, Function<? super Object, ? extends Param<T>> defaultValue) {
        if (value instanceof Val) {
            Val p1 = (Val)value;
            if (p1.inferredDataType && !defaultInferred) {
                Val p2 = (Val)defaultValue.apply(p1.getValue());
                p2.setInline0(p1.isInline());
                return p2;
            }
            return p1;
        }
        if (value instanceof Field) {
            return (Field)value;
        }
        if (value instanceof Select && Tools.degree((Select)value) == 1) {
            return DSL.field((Select)value);
        }
        if (value instanceof AbstractRow) {
            AbstractRow r = (AbstractRow)value;
            return r.rf();
        }
        if (value instanceof AbstractTable) {
            AbstractTable t2 = (AbstractTable)value;
            return t2.tf();
        }
        if (value instanceof QueryPart) {
            throw Tools.fieldExpected(value);
        }
        return defaultValue.apply(value);
    }

    static final <T> Field<T> field(T value) {
        return Tools.field(value, true, v -> DSL.val0(v, true));
    }

    static final <T> Field<T> field(Object value, Field<T> field2) {
        return Tools.field(value, false, v -> DSL.val(v, field2));
    }

    static final <T> Field<T> field(Object value, Class<T> type) {
        return Tools.field(value, false, v -> DSL.val(v, type));
    }

    static final <T> Field<T> field(Object value, DataType<T> type) {
        return Tools.field(value, false, v -> DSL.val(v, type));
    }

    static final <T> List<Field<T>> fields(T[] values2) {
        return Arrays.asList(Tools.fieldsArray(values2));
    }

    static final <T> Field<T>[] fieldsArray(T[] values2) {
        return Tools.map(values2, (? super T v) -> Tools.field(v), Field[]::new);
    }

    static final <T> List<Field<T>> fields(Object[] values2, Field<T> field2) {
        if (field2 == null) {
            return new ArrayList<Field<T>>();
        }
        return Tools.map(values2, (? super T v) -> Tools.field(v, field2));
    }

    static final List<Field<?>> fields(Object[] values2, Field<?>[] fields) {
        return Arrays.asList(Tools.fieldsArray(values2, fields));
    }

    static final Field<?>[] fieldsArray(Object[] values2, Field<?>[] fields) {
        return Tools.map(values2, (? super T v, int i) -> Tools.field(v, fields[i]), Field[]::new);
    }

    static final <T> List<Field<T>> fields(Object[] values2, DataType<T> type) {
        return Arrays.asList(Tools.fieldsArray(values2, type));
    }

    static final <T> List<Field<T>> fields(Collection<?> values2, DataType<T> type) {
        return Tools.map(values2, (? super T v) -> Tools.field(v, type));
    }

    static final <T> Field<T>[] fieldsArray(Object[] values2, DataType<T> type) {
        return Tools.map(values2, (? super T v) -> Tools.field(v, type), Field[]::new);
    }

    static final List<Field<?>> fields(Object[] values2, DataType<?>[] types) {
        return Arrays.asList(Tools.fieldsArray(values2, types));
    }

    static final Field<?>[] fieldsArray(Object[] values2, DataType<?>[] types) {
        return Tools.map(values2, (? super T v, int i) -> Tools.field(v, types[i]), Field[]::new);
    }

    static final IllegalArgumentException indexFail(Fields row, Field<?> field2) {
        return new IllegalArgumentException("Field (" + String.valueOf(field2) + ") is not contained in Row " + String.valueOf(row));
    }

    static final int indexOrFail(Fields row, Field<?> field2) {
        int result = row.indexOf(field2);
        if (result < 0) {
            throw Tools.indexFail(row, field2);
        }
        return result;
    }

    static final IllegalArgumentException indexFail(Fields row, String fieldName) {
        throw new IllegalArgumentException("Field (" + fieldName + ") is not contained in Row " + String.valueOf(row));
    }

    static final int indexOrFail(Fields row, String fieldName) {
        int result = row.indexOf(fieldName);
        if (result < 0) {
            throw Tools.indexFail(row, fieldName);
        }
        return result;
    }

    static final IllegalArgumentException indexFail(Fields row, Name fieldName) {
        throw new IllegalArgumentException("Field (" + String.valueOf(fieldName) + ") is not contained in Row " + String.valueOf(row));
    }

    static final int indexOrFail(Fields row, Name fieldName) {
        int result = row.indexOf(fieldName);
        if (result < 0) {
            throw Tools.indexFail(row, fieldName);
        }
        return result;
    }

    static final IllegalArgumentException indexFail(Fields row, int fieldIndex) {
        throw new IllegalArgumentException("Field (" + fieldIndex + ") is not contained in Row " + String.valueOf(row));
    }

    static final int indexOrFail(Fields row, int fieldIndex) {
        Field<?> result = row.field(fieldIndex);
        if (result == null) {
            throw Tools.indexFail(row, fieldIndex);
        }
        return fieldIndex;
    }

    private static final <T> List<T> newListWithCapacity(Iterable<?> it) {
        ArrayList arrayList;
        if (it instanceof Collection) {
            Collection c = (Collection)it;
            arrayList = new ArrayList(c.size());
        } else {
            arrayList = new ArrayList();
        }
        return arrayList;
    }

    static final <T, R, X extends Throwable> R apply(@Nullable T t2, ThrowingFunction<? super @NotNull T, ? extends R, ? extends X> f) throws X {
        return t2 == null ? null : (R)f.apply((T)t2);
    }

    static final <T, R, X extends Throwable> R applyOrElse(@Nullable T t2, ThrowingFunction<? super @NotNull T, ? extends R, ? extends X> f, ThrowingSupplier<? extends R, ? extends X> s2) throws X {
        return t2 == null ? s2.get() : f.apply(t2);
    }

    static final <T, X extends Throwable> T orElse(@Nullable T t2, ThrowingSupplier<? extends T, ? extends X> s2) throws X {
        return t2 == null ? s2.get() : t2;
    }

    static final <T> T let(@Nullable T t2, Consumer<? super @NotNull T> consumer) {
        if (t2 != null) {
            consumer.accept(t2);
        }
        return t2;
    }

    static final <T, E extends Exception> boolean allMatch(T[] array, ThrowingPredicate<? super T, E> test) throws E {
        return !Tools.anyMatch(array, test.negate());
    }

    static final <T, E extends Exception> boolean allMatch(T[] array, ThrowingIntPredicate<? super T, E> test) throws E {
        return !Tools.anyMatch(array, test.negate());
    }

    static final <T, E extends Exception> boolean allMatch(Iterable<? extends T> it, ThrowingPredicate<? super T, E> test) throws E {
        return !Tools.anyMatch(it, test.negate());
    }

    static final <T, E extends Exception> boolean allMatch(Iterable<? extends T> it, ThrowingIntPredicate<? super T, E> test) throws E {
        return !Tools.anyMatch(it, test.negate());
    }

    static final <T, E extends Exception> boolean anyMatch(T[] array, ThrowingPredicate<? super T, E> test) throws E {
        return Tools.findAny(array, test, (? super T t2) -> Boolean.TRUE) != null;
    }

    static final <T, E extends Exception> boolean anyMatch(T[] array, ThrowingIntPredicate<? super T, E> test) throws E {
        return Tools.findAny(array, test, (? super T t2) -> Boolean.TRUE) != null;
    }

    static final <T, E extends Exception> boolean anyMatch(Iterable<? extends T> it, ThrowingPredicate<? super T, E> test) throws E {
        return Tools.findAny(it, test, (? super T t2) -> Boolean.TRUE) != null;
    }

    static final <T, E extends Exception> boolean anyMatch(Iterable<? extends T> it, ThrowingIntPredicate<? super T, E> test) throws E {
        return Tools.findAny(it, test, (? super T t2) -> Boolean.TRUE) != null;
    }

    static final <T, E extends Exception> T findAny(T[] array, ThrowingPredicate<? super T, E> test) throws E {
        return (T)Tools.findAny(array, test, (? super T t2) -> t2);
    }

    static final <T, E extends Exception> T findAny(T[] array, ThrowingIntPredicate<? super T, E> test) throws E {
        return (T)Tools.findAny(array, test, (? super T t2) -> t2);
    }

    static final <T, E extends Exception> T findAny(Iterable<? extends T> it, ThrowingPredicate<? super T, E> test) throws E {
        return (T)Tools.findAny(it, test, (? super T t2) -> t2);
    }

    static final <T, E extends Exception> T findAny(Iterable<? extends T> it, ThrowingIntPredicate<? super T, E> test) throws E {
        return (T)Tools.findAny(it, test, (? super T t2) -> t2);
    }

    static final <T, U, E extends Exception> U findAny(T[] array, ThrowingPredicate<? super T, E> test, ThrowingFunction<? super T, ? extends U, E> function) throws E {
        if (array != null) {
            for (T t2 : array) {
                if (!test.test(t2)) continue;
                return function.apply(t2);
            }
        }
        return null;
    }

    static final <T, U, E extends Exception> U findAny(T[] array, ThrowingIntPredicate<? super T, E> test, ThrowingFunction<? super T, ? extends U, E> function) throws E {
        if (array != null) {
            int i = 0;
            for (T t2 : array) {
                if (!test.test(t2, i++)) continue;
                return function.apply(t2);
            }
        }
        return null;
    }

    static final <T, U, E extends Exception> U findAny(Iterable<? extends T> it, ThrowingPredicate<? super T, E> test, ThrowingFunction<? super T, ? extends U, E> function) throws E {
        if (it != null) {
            for (T t2 : it) {
                if (!test.test(t2)) continue;
                return function.apply(t2);
            }
        }
        return null;
    }

    static final <T, U, E extends Exception> U findAny(Iterable<? extends T> it, ThrowingIntPredicate<? super T, E> test, ThrowingFunction<? super T, ? extends U, E> function) throws E {
        if (it != null) {
            int i = 0;
            for (T t2 : it) {
                if (!test.test(t2, i++)) continue;
                return function.apply(t2);
            }
        }
        return null;
    }

    static final Condition allNull(Field<?>[] fields) {
        return DSL.and(Tools.map(fields, Field::isNull));
    }

    static final Condition allNotNull(Field<?>[] fields) {
        return DSL.and(Tools.map(fields, Field::isNotNull));
    }

    static final <T, U, E extends Exception> U[] map(T[] array, ThrowingFunction<? super T, ? extends U, E> mapper2, IntFunction<U[]> constructor) throws E {
        if (array == null) {
            return constructor.apply(0);
        }
        U[] result = constructor.apply(array.length);
        for (int i = 0; i < array.length; ++i) {
            result[i] = mapper2.apply(array[i]);
        }
        return result;
    }

    static final <U, E extends Exception> U[] map(int[] array, ThrowingIntFunction<? extends U, E> mapper2, IntFunction<U[]> constructor) throws E {
        if (array == null) {
            return constructor.apply(0);
        }
        U[] result = constructor.apply(array.length);
        for (int i = 0; i < array.length; ++i) {
            result[i] = mapper2.apply(array[i]);
        }
        return result;
    }

    static final <T, U, E extends Exception> U[] map(Collection<? extends T> collection, ThrowingFunction<? super T, ? extends U, E> mapper2, IntFunction<U[]> constructor) throws E {
        if (collection == null) {
            return constructor.apply(0);
        }
        U[] result = constructor.apply(collection.size());
        int i = 0;
        for (T t2 : collection) {
            result[i++] = mapper2.apply(t2);
        }
        return result;
    }

    static final <T, U, E extends Exception> U[] map(T[] array, ThrowingObjIntFunction<? super T, ? extends U, E> mapper2, IntFunction<U[]> constructor) throws E {
        if (array == null) {
            return constructor.apply(0);
        }
        U[] result = constructor.apply(array.length);
        for (int i = 0; i < array.length; ++i) {
            result[i] = mapper2.apply(array[i], i);
        }
        return result;
    }

    static final <U, E extends Exception> U[] map(int[] array, ThrowingIntIntFunction<? extends U, E> mapper2, IntFunction<U[]> constructor) throws E {
        if (array == null) {
            return constructor.apply(0);
        }
        U[] result = constructor.apply(array.length);
        for (int i = 0; i < array.length; ++i) {
            result[i] = mapper2.apply(array[i], i);
        }
        return result;
    }

    static final <T, U, E extends Exception> U[] map(Collection<? extends T> collection, ThrowingObjIntFunction<? super T, ? extends U, E> mapper2, IntFunction<U[]> constructor) throws E {
        if (collection == null) {
            return constructor.apply(0);
        }
        U[] result = constructor.apply(collection.size());
        int i = 0;
        for (T t2 : collection) {
            result[i] = mapper2.apply(t2, i++);
        }
        return result;
    }

    static final <T, U, E extends Exception> List<U> map(T[] array, ThrowingFunction<? super T, ? extends U, E> mapper2) throws E {
        if (array == null) {
            return Collections.emptyList();
        }
        ArrayList<U> result = new ArrayList<U>(array.length);
        for (T t2 : array) {
            result.add(mapper2.apply(t2));
        }
        return result;
    }

    static final <U, E extends Exception> List<U> map(int[] array, ThrowingIntFunction<? extends U, E> mapper2) throws E {
        if (array == null) {
            return Collections.emptyList();
        }
        ArrayList<U> result = new ArrayList<U>(array.length);
        for (int t2 : array) {
            result.add(mapper2.apply(t2));
        }
        return result;
    }

    static final <T, U, E extends Exception> List<U> map(Iterable<? extends T> it, ThrowingFunction<? super T, ? extends U, E> mapper2) throws E {
        if (it == null) {
            return Collections.emptyList();
        }
        List<T> result = Tools.newListWithCapacity(it);
        for (T t2 : it) {
            result.add(mapper2.apply(t2));
        }
        return result;
    }

    static final <T, U, E extends Exception> List<U> flatMap(Iterable<? extends T> it, ThrowingFunction<? super T, ? extends List<? extends U>, E> mapper2) throws E {
        if (it == null) {
            return Collections.emptyList();
        }
        List<T> result = Tools.newListWithCapacity(it);
        for (T t2 : it) {
            result.addAll((Collection)mapper2.apply(t2));
        }
        return result;
    }

    static final <T, U, E extends Exception> List<U> map(T[] array, ThrowingObjIntFunction<? super T, ? extends U, E> mapper2) throws E {
        if (array == null) {
            return Collections.emptyList();
        }
        ArrayList<U> result = new ArrayList<U>(array.length);
        for (int i = 0; i < array.length; ++i) {
            result.add(mapper2.apply(array[i], i));
        }
        return result;
    }

    static final <U, E extends Exception> List<U> map(int[] array, ThrowingIntIntFunction<? extends U, E> mapper2) throws E {
        if (array == null) {
            return Collections.emptyList();
        }
        ArrayList<U> result = new ArrayList<U>(array.length);
        for (int i = 0; i < array.length; ++i) {
            result.add(mapper2.apply(array[i], i));
        }
        return result;
    }

    static final <T, U, E extends Exception> List<U> map(Iterable<? extends T> it, ThrowingObjIntFunction<? super T, ? extends U, E> mapper2) throws E {
        if (it == null) {
            return Collections.emptyList();
        }
        List<T> result = Tools.newListWithCapacity(it);
        int i = 0;
        for (T t2 : it) {
            result.add(mapper2.apply(t2, i++));
        }
        return result;
    }

    @SafeVarargs
    static final <T> T[] reverse(T ... array) {
        if (array == null) {
            return null;
        }
        for (int i = 0; i < array.length / 2; ++i) {
            T tmp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = tmp;
        }
        return array;
    }

    static final <T, U> Iterator<U> iterator(final Iterator<? extends T> iterator2, final Function<? super T, ? extends U> mapper2) {
        return new Iterator<U>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public U next() {
                return mapper2.apply(iterator2.next());
            }

            @Override
            public void remove() {
                iterator2.remove();
            }
        };
    }

    @SafeVarargs
    static final <T> Iterable<T> reverseIterable(T ... array) {
        return Tools.reverseIterable(Arrays.asList(array));
    }

    @SafeVarargs
    static final <T> Iterator<T> reverseIterator(T ... array) {
        return Tools.reverseIterator(Arrays.asList(array));
    }

    static final <T> Iterable<T> reverseIterable(List<T> list) {
        return () -> Tools.reverseIterator(list);
    }

    static final <T> Iterator<T> reverseIterator(final List<T> list) {
        return new Iterator<T>(){
            final ListIterator<T> li;
            {
                this.li = list.listIterator(list.size());
            }

            @Override
            public boolean hasNext() {
                return this.li.hasPrevious();
            }

            @Override
            public T next() {
                return this.li.previous();
            }

            @Override
            public void remove() {
                this.li.remove();
            }
        };
    }

    @SafeVarargs
    static final <T> List<T> list(T ... array) {
        return array == null ? Collections.emptyList() : Arrays.asList(array);
    }

    static final Map<Field<?>, Object> mapOfChangedValues(Record record) {
        LinkedHashMap result = new LinkedHashMap();
        int size = record.size();
        for (int i = 0; i < size; ++i) {
            if (!record.changed(i)) continue;
            result.put(record.field(i), record.get(i));
        }
        return result;
    }

    static final <T> T first(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return null;
        }
        Iterator<T> it = iterable.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    static final <T> T last(Collection<T> collection) {
        if (collection.isEmpty()) {
            return null;
        }
        if (collection instanceof List) {
            List l = (List)collection;
            return (T)l.get(collection.size() - 1);
        }
        T last2 = null;
        for (T last2 : collection) {
        }
        return last2;
    }

    static final void setFetchSize(ExecuteContext ctx, int fetchSize) throws SQLException {
        int f = SettingsTools.getFetchSize(fetchSize, ctx.settings());
        if (f != 0) {
            PreparedStatement statement;
            if (log.isDebugEnabled()) {
                log.debug((Object)"Setting fetch size", f);
            }
            if ((statement = ctx.statement()) != null) {
                statement.setFetchSize(f);
            }
        }
    }

    @Deprecated
    static final <R extends Record> R filterOne(List<R> list) throws TooManyRowsException {
        int size = list.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return (R)((Record)list.get(0));
        }
        throw new TooManyRowsException("Too many rows selected : " + size);
    }

    static final <R extends Record> R fetchOne(Cursor<R> cursor) throws TooManyRowsException {
        return Tools.fetchOne(cursor, false);
    }

    static final <R extends Record> R fetchOne(Cursor<R> cursor, boolean hasLimit1) throws TooManyRowsException {
        try {
            Result<R> result = cursor.fetchNext(hasLimit1 ? 1 : 2);
            int size = result.size();
            if (size == 0) {
                R r = null;
                return r;
            }
            if (size == 1) {
                Record record = (Record)result.get(0);
                return (R)record;
            }
            throw Tools.exception(cursor, new TooManyRowsException("Cursor returned more than one result"));
        }
        finally {
            cursor.close();
        }
    }

    static final <R extends Record> R fetchSingle(Cursor<R> cursor) throws NoDataFoundException, TooManyRowsException {
        return Tools.fetchSingle(cursor, false);
    }

    static final <R extends Record> R fetchSingle(Cursor<R> cursor, boolean hasLimit1) throws NoDataFoundException, TooManyRowsException {
        try {
            Result<R> result = cursor.fetchNext(hasLimit1 ? 1 : 2);
            int size = result.size();
            if (size == 0) {
                throw Tools.exception(cursor, new NoDataFoundException("Cursor returned no rows"));
            }
            if (size == 1) {
                Record record = (Record)result.get(0);
                return (R)record;
            }
            throw Tools.exception(cursor, new TooManyRowsException("Cursor returned more than one result"));
        }
        finally {
            cursor.close();
        }
    }

    private static final RuntimeException exception(Cursor<?> cursor, RuntimeException e) {
        if (cursor instanceof CursorImpl) {
            CursorImpl c = (CursorImpl)cursor;
            c.ctx.exception(e);
            c.listener.exception(c.ctx);
            return c.ctx.exception();
        }
        return e;
    }

    static final <Q extends QueryPart> void visitAutoAliased(Context<?> ctx, Q q, Predicate<? super Context<?>> declaring, BiConsumer<? super Context<?>, ? super Q> visit) {
        Q alternative;
        if (declaring.test(ctx) && q instanceof AutoAlias && (alternative = ((AutoAlias)q).autoAlias(ctx, q)) != null) {
            visit.accept(ctx, alternative);
        } else {
            visit.accept(ctx, q);
        }
    }

    static final void visitSubquery(Context<?> ctx, QueryPart query) {
        Tools.visitSubquery(ctx, query, 0, true);
    }

    static final void visitSubquery(Context<?> ctx, QueryPart query, int characteristics) {
        Tools.visitSubquery(ctx, query, characteristics, true);
    }

    static final void visitSubquery(Context<?> ctx, QueryPart query, int characteristics, boolean parentheses) {
        if (ctx.family() == SQLDialect.JAVA) {
            parentheses = false;
        }
        if (parentheses) {
            ctx.sql('(');
        }
        boolean previousPredicandSubquery = ctx.predicandSubquery();
        boolean previousDerivedTableSubquery = ctx.derivedTableSubquery();
        boolean previousSetOperationSubquery = ctx.setOperationSubquery();
        ctx.subquery(true).predicandSubquery((characteristics & 0x100) != 0).derivedTableSubquery((characteristics & 1) != 0).setOperationSubquery((characteristics & 2) != 0).formatIndentStart().formatNewLine().visit(query).formatIndentEnd().formatNewLine().setOperationSubquery(previousSetOperationSubquery).derivedTableSubquery(previousDerivedTableSubquery).predicandSubquery(previousPredicandSubquery).subquery(false);
        if (parentheses) {
            ctx.sql(')');
        }
    }

    static final <C extends Context<?>> C visitAll(C ctx, Collection<? extends QueryPart> parts) {
        if (parts != null) {
            for (QueryPart queryPart : parts) {
                ctx.visit(queryPart);
            }
        }
        return ctx;
    }

    static final <C extends Context<?>> C visitAll(C ctx, QueryPart[] parts) {
        if (parts != null) {
            for (QueryPart part : parts) {
                ctx.visit(part);
            }
        }
        return ctx;
    }

    static final <C extends Context<?>> C visitMappedSchema(C ctx, Name qualifiedName) {
        if (qualifiedName.qualified()) {
            Schema s2 = Tools.getMappedSchema(ctx, new SchemaImpl(qualifiedName.qualifier()));
            if (s2 != null) {
                ctx.visit(s2).sql('.');
            }
            ctx.visit(qualifiedName.unqualifiedName());
        } else {
            ctx.visit(qualifiedName);
        }
        return ctx;
    }

    static final void renderAndBind(Context<?> ctx, String sql, List<QueryPart> substitutes) {
        BindContext b;
        RenderContext r;
        if (Boolean.TRUE.equals(ctx.settings().isRenderPlainSQLTemplatesAsRaw())) {
            ctx.sql(sql);
            return;
        }
        RenderContext render = ctx instanceof RenderContext ? (r = (RenderContext)ctx) : null;
        BindContext bind = ctx instanceof BindContext ? (b = (BindContext)ctx) : null;
        int substituteIndex = 0;
        char[] sqlChars = sql.toCharArray();
        if (render == null) {
            render = new DefaultRenderContext(bind.configuration(), ctx.executeContext());
        }
        SQLDialect family = render.family();
        boolean mysql = DefaultParseContext.SUPPORTS_HASH_COMMENT_SYNTAX.contains((Object)render.dialect());
        char[][][] quotes = Identifiers.QUOTES.get((Object)family);
        boolean needsBackslashEscaping = Tools.needsBackslashEscaping(ctx.configuration());
        block6: for (int i = 0; i < sqlChars.length; ++i) {
            block50: {
                if (Tools.peek(sqlChars, i, TOKEN_SINGLE_LINE_COMMENT) || Tools.peek(sqlChars, i, TOKEN_SINGLE_LINE_COMMENT_C) || mysql && Tools.peek(sqlChars, i, TOKEN_HASH)) {
                    while (i < sqlChars.length && sqlChars[i] != '\r' && sqlChars[i] != '\n') {
                        render.sql(sqlChars[i++]);
                    }
                    if (i >= sqlChars.length) continue;
                    render.sql(sqlChars[i]);
                    continue;
                }
                if (Tools.peek(sqlChars, i, TOKEN_MULTI_LINE_COMMENT_OPEN)) {
                    int nestedMultilineCommentLevel = 1;
                    do {
                        render.sql(sqlChars[i++]);
                        if (Tools.peek(sqlChars, i, TOKEN_MULTI_LINE_COMMENT_OPEN)) {
                            ++nestedMultilineCommentLevel;
                            continue;
                        }
                        if (!Tools.peek(sqlChars, i, TOKEN_MULTI_LINE_COMMENT_CLOSE)) continue;
                        --nestedMultilineCommentLevel;
                    } while (nestedMultilineCommentLevel != 0);
                    render.sql(sqlChars[i]);
                    continue;
                }
                if (sqlChars[i] != '\'') break block50;
                render.sql(sqlChars[i++]);
                while (i < sqlChars.length) {
                    block54: {
                        block52: {
                            block53: {
                                block51: {
                                    if (sqlChars[i] != '\\' || !needsBackslashEscaping) break block51;
                                    render.sql(sqlChars[i++]);
                                    break block52;
                                }
                                if (!Tools.peek(sqlChars, i, TOKEN_ESCAPED_APOS)) break block53;
                                render.sql(sqlChars[i++]);
                                break block52;
                            }
                            if (Tools.peek(sqlChars, i, TOKEN_APOS)) break block54;
                        }
                        render.sql(sqlChars[i++]);
                        continue;
                    }
                    render.sql(sqlChars[i]);
                    continue block6;
                }
                break;
            }
            if ((sqlChars[i] == 'e' || sqlChars[i] == 'E') && SUPPORT_POSTGRES_LITERALS.contains((Object)ctx.dialect()) && i + 1 < sqlChars.length && sqlChars[i + 1] == '\'') {
                render.sql(sqlChars[i++]);
                render.sql(sqlChars[i++]);
                while (true) {
                    if (sqlChars[i] == '\\') {
                        render.sql(sqlChars[i++]);
                    } else if (Tools.peek(sqlChars, i, TOKEN_ESCAPED_APOS)) {
                        render.sql(sqlChars[i++]);
                    } else if (Tools.peek(sqlChars, i, TOKEN_APOS)) break;
                    render.sql(sqlChars[i++]);
                }
                render.sql(sqlChars[i]);
                continue;
            }
            if ((sqlChars[i] == 'q' || sqlChars[i] == 'Q') && ctx.family() == SQLDialect.ORACLE && i + 1 < sqlChars.length && sqlChars[i + 1] == '\'') {
                render.sql(sqlChars[i++]);
                render.sql(sqlChars[i++]);
                char[] end = switch (sqlChars[i]) {
                    case '[' -> "]'".toCharArray();
                    case '{' -> "}'".toCharArray();
                    case '(' -> ")'".toCharArray();
                    case '<' -> ">'".toCharArray();
                    default -> new char[]{sqlChars[i], '\''};
                };
                render.sql(sqlChars[i++]);
                while (!Tools.peek(sqlChars, i, end)) {
                    render.sql(sqlChars[i++]);
                }
                render.sql(sqlChars[i++]);
                render.sql(sqlChars[i]);
                continue;
            }
            if (Tools.peekAny(sqlChars, i, quotes[0])) {
                int d;
                int delimiter = 0;
                for (d = 0; d < quotes[0].length; ++d) {
                    if (!Tools.peek(sqlChars, i, quotes[0][d])) continue;
                    delimiter = d;
                    break;
                }
                for (d = 0; d < quotes[0][delimiter].length; ++d) {
                    render.sql(sqlChars[i++]);
                }
                while (i < sqlChars.length) {
                    if (Tools.peek(sqlChars, i, quotes[2][delimiter])) {
                        for (d = 0; d < quotes[2][delimiter].length; ++d) {
                            render.sql(sqlChars[i++]);
                        }
                        continue;
                    }
                    if (!Tools.peek(sqlChars, i, quotes[1][delimiter])) {
                        render.sql(sqlChars[i++]);
                        continue;
                    }
                    for (d = 0; d < quotes[1][delimiter].length; ++d) {
                        if (d > 0) {
                            ++i;
                        }
                        render.sql(sqlChars[i]);
                    }
                    continue block6;
                }
                break;
            }
            if (substituteIndex < substitutes.size() && (sqlChars[i] == '?' || sqlChars[i] == ':' && i + 1 < sqlChars.length && Character.isJavaIdentifierPart(sqlChars[i + 1]) && (i - 1 < 0 || sqlChars[i - 1] != ':'))) {
                if (sqlChars[i] == '?' && i + 1 < sqlChars.length && SUPPORT_NON_BIND_VARIABLE_SUFFIXES.contains((Object)ctx.dialect())) {
                    block17: for (char[] candidate : NON_BIND_VARIABLE_SUFFIXES) {
                        if (!Tools.peek(sqlChars, i + 1, candidate)) continue;
                        for (char[] exclude : BIND_VARIABLE_SUFFIXES) {
                            if (Tools.peek(sqlChars, i + 1, exclude)) continue block17;
                        }
                        int j = i;
                        while (i - j <= candidate.length) {
                            render.sql(sqlChars[i]);
                            ++i;
                        }
                        if (i >= sqlChars.length) continue block6;
                        render.sql(sqlChars[i]);
                        continue block6;
                    }
                }
                if (sqlChars[i] == ':') {
                    while (i + 1 < sqlChars.length && Character.isJavaIdentifierPart(sqlChars[i + 1])) {
                        ++i;
                    }
                }
                QueryPart substitute = substitutes.get(substituteIndex++);
                if (render.paramType() == ParamType.INLINED || render.paramType() == ParamType.NAMED || render.paramType() == ParamType.NAMED_OR_INLINED) {
                    render.visit(substitute);
                } else {
                    RenderContext.CastMode previous = render.castMode();
                    ((RenderContext)((RenderContext)render.castMode(RenderContext.CastMode.NEVER)).visit(substitute)).castMode(previous);
                }
                if (bind == null) continue;
                bind.visit(substitute);
                continue;
            }
            if (sqlChars[i] == '{') {
                if (Tools.peekAny(sqlChars, i, JDBC_ESCAPE_PREFIXES, true)) {
                    render.sql(sqlChars[i]);
                    continue;
                }
                int start2 = ++i;
                while (i < sqlChars.length && sqlChars[i] != '}') {
                    ++i;
                }
                int end = i;
                Integer index = Ints.tryParse(sql, start2, end);
                if (index != null) {
                    if (index < 0 || index >= substitutes.size()) {
                        throw new TemplatingException("No substitute QueryPart provided for placeholder {" + index + "} in plain SQL template: " + sql);
                    }
                    QueryPart substitute = substitutes.get(index);
                    render.visit(substitute);
                    if (bind == null) continue;
                    bind.visit(substitute);
                    continue;
                }
                render.visit(DSL.keyword(sql.substring(start2, end)));
                continue;
            }
            render.sql(sqlChars[i]);
        }
    }

    static final boolean needsBackslashEscaping(Configuration configuration) {
        BackslashEscaping escaping = SettingsTools.getBackslashEscaping(configuration.settings());
        return escaping == BackslashEscaping.ON || escaping == BackslashEscaping.DEFAULT && REQUIRES_BACKSLASH_ESCAPING.contains((Object)configuration.dialect());
    }

    static final boolean peek(char[] sqlChars, int index, char[] peek) {
        return Tools.peek(sqlChars, index, peek, false);
    }

    static final boolean peek(char[] sqlChars, int index, char[] peek, boolean anyWhitespace) {
        block0: for (int i = 0; i < peek.length; ++i) {
            if (index + i >= sqlChars.length) {
                return false;
            }
            if (sqlChars[index + i] == peek[i]) continue;
            if (anyWhitespace && peek[i] == ' ') {
                for (char c : WHITESPACE_CHARACTERS) {
                    if (sqlChars[index + i] == c) continue block0;
                }
            }
            return false;
        }
        return true;
    }

    static final boolean peekAny(char[] sqlChars, int index, char[][] peekAny) {
        return Tools.peekAny(sqlChars, index, peekAny, false);
    }

    static final boolean peekAny(char[] sqlChars, int index, char[][] peekAny, boolean anyWhitespace) {
        for (char[] peek : peekAny) {
            if (!Tools.peek(sqlChars, index, peek, anyWhitespace)) continue;
            return true;
        }
        return false;
    }

    static final List<QueryPart> queryParts(Object ... substitutes) {
        if (substitutes == null) {
            return Tools.queryParts(new Object[]{null});
        }
        ArrayList<QueryPart> result = new ArrayList<QueryPart>(substitutes.length);
        for (Object substitute : substitutes) {
            if (substitute instanceof QueryPart) {
                QueryPart q = (QueryPart)substitute;
                result.add(q);
                continue;
            }
            Class type = substitute != null ? substitute.getClass() : Object.class;
            result.add(new Val<Object>(substitute, DSL.getDataType(type), true));
        }
        return result;
    }

    static final <T> T[] combine(T value, T[] array) {
        Object[] result = (Object[])Array.newInstance(array.getClass().getComponentType(), array.length + 1);
        result[0] = value;
        System.arraycopy(array, 0, result, 1, array.length);
        return result;
    }

    static final <T> T[] combine(T[] array, T value) {
        T[] result = Arrays.copyOf(array, array.length + 1);
        result[array.length] = value;
        return result;
    }

    static final <T> T[] combine(T[] a1, T[] a2) {
        T[] result = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    static final Field<?>[] combine(Field<?> field2, Field<?> ... fields) {
        if (fields == null) {
            return new Field[]{field2};
        }
        Field[] result = new Field[fields.length + 1];
        result[0] = field2;
        System.arraycopy(fields, 0, result, 1, fields.length);
        return result;
    }

    static final Field<?>[] combine(Field<?> field1, Field<?> field2, Field<?> ... fields) {
        if (fields == null) {
            return new Field[]{field1, field2};
        }
        Field[] result = new Field[fields.length + 2];
        result[0] = field1;
        result[1] = field2;
        System.arraycopy(fields, 0, result, 2, fields.length);
        return result;
    }

    static final Field<?>[] combine(Field<?> field1, Field<?> field2, Field<?> field3, Field<?> ... fields) {
        if (fields == null) {
            return new Field[]{field1, field2, field3};
        }
        Field[] result = new Field[fields.length + 3];
        result[0] = field1;
        result[1] = field2;
        result[2] = field3;
        System.arraycopy(fields, 0, result, 3, fields.length);
        return result;
    }

    static final RuntimeException translate(String sql, Throwable t2) {
        if (t2 instanceof R2dbcException) {
            R2dbcException e = (R2dbcException)t2;
            return Tools.translate(sql, e);
        }
        if (t2 instanceof SQLException) {
            SQLException e = (SQLException)t2;
            return Tools.translate(sql, e);
        }
        if (t2 instanceof RuntimeException) {
            RuntimeException e = (RuntimeException)t2;
            return Tools.translate(sql, e);
        }
        if (t2 != null) {
            return new DataAccessException("SQL [" + sql + "]; Unspecified Throwable", t2);
        }
        return new DataAccessException("SQL [" + sql + "]; Unspecified Throwable");
    }

    static final DataAccessException translate(String sql, R2dbcException e) {
        if (e != null) {
            return Tools.translate(sql, e, DataAccessException.sqlStateClass(e));
        }
        return new DataAccessException("SQL [" + sql + "]; Unspecified R2dbcException");
    }

    static final DataAccessException translate(String sql, SQLException e) {
        if (e != null) {
            return Tools.translate(sql, e, DataAccessException.sqlStateClass(e));
        }
        return new DataAccessException("SQL [" + sql + "]; Unspecified SQLException");
    }

    private static final DataAccessException translate(String sql, Exception e, SQLStateClass sqlState) {
        switch (sqlState) {
            case C22_DATA_EXCEPTION: {
                return new DataException("SQL [" + sql + "]; " + e.getMessage(), e);
            }
            case C23_INTEGRITY_CONSTRAINT_VIOLATION: {
                return new IntegrityConstraintViolationException("SQL [" + sql + "]; " + e.getMessage(), e);
            }
        }
        return new DataAccessException("SQL [" + sql + "]; " + e.getMessage(), e);
    }

    static final RuntimeException translate(String sql, RuntimeException e) {
        if (e != null) {
            return e;
        }
        return new DataAccessException("SQL [" + sql + "]; Unspecified RuntimeException");
    }

    static final void safeClose(ExecuteListener listener, ExecuteContext ctx) {
        Tools.safeClose(listener, ctx, false);
    }

    static final void safeClose(ExecuteListener listener, ExecuteContext ctx, boolean keepStatement) {
        Tools.safeClose(listener, ctx, keepStatement, true);
    }

    static final void safeClose(ExecuteListener listener, ExecuteContext ctx, boolean keepStatement, boolean keepResultSet) {
        JDBCUtils.safeClose(ctx.resultSet());
        ctx.resultSet(null);
        PreparedStatement statement = ctx.statement();
        if (statement != null) {
            Tools.consumeWarnings(ctx, listener);
        }
        if (!keepStatement) {
            if (statement != null) {
                JDBCUtils.safeClose(statement);
                ctx.statement(null);
            } else {
                Connection connection = DefaultExecuteContext.localConnection();
                if (connection != null && ((DefaultExecuteContext)ctx).connectionProvider != null) {
                    ((DefaultExecuteContext)ctx).connectionProvider.release(connection);
                }
            }
        }
        if (keepResultSet) {
            listener.end(ctx);
        }
        DefaultExecuteContext.clean();
    }

    static final <T> void setValue(Record target, Field<T> targetField, Record source2, Field<?> sourceField) {
        Tools.setValue(target, targetField, source2.get(sourceField));
    }

    static final <T> void setValue(AbstractRecord target, Field<T> targetField, int targetIndex, Record source2, int sourceIndex) {
        Tools.setValue(target, targetField, targetIndex, source2.get(sourceIndex));
    }

    static final <T> void setValue(Record target, Field<T> targetField, Object value) {
        target.set(targetField, targetField.getDataType().convert(value));
    }

    static final <T> void setValue(AbstractRecord target, Field<T> targetField, int targetIndex, Object value) {
        target.set(targetField, targetIndex, targetField.getDataType().convert(value));
    }

    static final <T> void copyValue(AbstractRecord target, Field<T> targetField, Record source2, Field<?> sourceField) {
        DataType targetType = targetField.getDataType();
        int targetIndex = Tools.indexOrFail((Fields)target.fieldsRow(), targetField);
        int sourceIndex = Tools.indexOrFail((Fields)source2.fieldsRow(), sourceField);
        target.values[targetIndex] = targetType.convert(source2.get(sourceIndex));
        target.originals[targetIndex] = targetType.convert(source2.original(sourceIndex));
        target.changed.set(targetIndex, source2.changed(sourceIndex));
    }

    static final Catalog getMappedCatalog(Scope scope, Catalog catalog) {
        if (scope != null) {
            return scope.configuration().schemaMapping().map(catalog);
        }
        return catalog;
    }

    static final Schema getMappedSchema(Scope scope, Schema schema) {
        if (scope != null) {
            return scope.configuration().schemaMapping().map(schema);
        }
        return schema;
    }

    static final <R extends Record> Table<R> getMappedTable(Scope scope, Table<R> table) {
        if (scope != null) {
            return scope.configuration().schemaMapping().map(table);
        }
        return table;
    }

    static final String getMappedUDTName(Scope scope, Class<? extends QualifiedRecord<?>> type) {
        return Tools.getMappedUDTName(scope, Tools.newRecord(false, type).operate(null));
    }

    static final String getMappedUDTName(Scope scope, QualifiedRecord<?> record) {
        RecordQualifier<?> udt = record.getQualifier();
        Schema mapped = Tools.getMappedSchema(scope, udt.getSchema());
        StringBuilder sb = new StringBuilder();
        if (mapped != null && !"".equals(mapped.getName())) {
            sb.append(mapped.getName()).append('.');
        }
        if (udt.getPackage() != null) {
            sb.append(udt.getPackage().getName()).append('.');
        }
        sb.append(record.getQualifier().getName());
        if (record.getQualifier() instanceof Table && scope.family() == SQLDialect.ORACLE) {
            sb.append("%ROWTYPE");
        }
        return sb.toString();
    }

    @Pro
    static final Name getMappedArrayName(Scope scope, Class<? extends ArrayOrAssociativeArrayRecord<?>> type) {
        return Tools.getMappedArrayName(scope, Tools.newArrayRecord(type));
    }

    @Pro
    static final Name getMappedArrayName(Scope scope, ArrayOrAssociativeArrayRecord<?> array) {
        Schema mapped = Tools.getMappedSchema(scope, array.getSchema());
        return DSL.name(mapped == null ? null : mapped.getUnqualifiedName(), array.getPackage() == null ? null : array.getPackage().getUnqualifiedName(), DSL.name(array.getName()));
    }

    @Pro
    static final String getMappedArrayNameString(Scope scope, Class<? extends ArrayOrAssociativeArrayRecord<?>> type) {
        return Tools.getMappedArrayNameString(scope, Tools.newArrayRecord(type));
    }

    @Pro
    static final String getMappedArrayNameString(Scope scope, ArrayOrAssociativeArrayRecord<?> array) {
        Schema mapped = Tools.getMappedSchema(scope, array.getSchema());
        StringBuilder sb = new StringBuilder();
        if (mapped != null && !"".equals(mapped.getName())) {
            sb.append(mapped.getName()).append('.');
        }
        if (array.getPackage() != null) {
            sb.append(array.getPackage().getName()).append('.');
        }
        sb.append(array.getName());
        return sb.toString();
    }

    @Pro
    static final Name getMappedAssociativeArrayName(Scope scope, Class<? extends AssociativeArrayRecord<?, ?>> type) {
        return Tools.getMappedAssociativeArrayName(scope, Tools.newAssociativeArrayRecord(type));
    }

    @Pro
    static final Name getMappedAssociativeArrayName(Scope scope, AssociativeArrayRecord<?, ?> array) {
        Schema mapped = Tools.getMappedSchema(scope, array.getSchema());
        return DSL.name(mapped == null ? null : mapped.getUnqualifiedName(), array.getPackage() == null ? null : array.getPackage().getUnqualifiedName(), DSL.name(array.getName()));
    }

    @Pro
    static final String getMappedAssociativeArrayNameString(Scope scope, Class<? extends AssociativeArrayRecord<?, ?>> type) {
        return Tools.getMappedAssociativeArrayNameString(scope, Tools.newAssociativeArrayRecord(type));
    }

    @Pro
    static final String getMappedAssociativeArrayNameString(Scope scope, AssociativeArrayRecord<?, ?> array) {
        Schema mapped = Tools.getMappedSchema(scope, array.getSchema());
        StringBuilder sb = new StringBuilder();
        if (mapped != null && !"".equals(mapped.getName())) {
            sb.append(mapped.getName()).append('.');
        }
        if (array.getPackage() != null) {
            sb.append(array.getPackage().getName()).append('.');
        }
        sb.append(array.getName());
        return sb.toString();
    }

    static final RecordQualifier<?> getRecordQualifier(DataType<?> t2) {
        return Tools.getRecordQualifier(t2.getType());
    }

    static final RecordQualifier<?> getRecordQualifier(Class<?> t2) {
        try {
            return ((QualifiedRecord)t2.getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).getQualifier();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Pro
    static final QualifiedRecord<?> newQualifiedRecord(DataType<?> t2) {
        try {
            return (QualifiedRecord)t2.getType().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Pro
    static final AssociativeArrayRecord<?, ?> newAssociativeArrayRecord(DataType<?> t2) {
        try {
            return (AssociativeArrayRecord)t2.getType().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    static final String autoAlias(QueryPart part) {
        return "alias_" + Internal.hash(part);
    }

    static final Name autoAliasName(QueryPart part) {
        return DSL.name(Tools.autoAlias(part));
    }

    static final Field<String> escapeForLike(Field<?> field2) {
        return Tools.escapeForLike(field2, new DefaultConfiguration());
    }

    static final Field<String> escapeForLike(Field<?> field2, Configuration configuration) {
        if (Tools.nullSafeDataType(field2).isString()) {
            if (configuration.family() == SQLDialect.ACCESS) {
                return DSL.concat(DSL.inline("["), field2, DSL.inline("]"));
            }
            return DSL.escape(field2, '!');
        }
        return Tools.castIfNeeded(field2, String.class);
    }

    static final boolean isParam(Field<?> field2) {
        return field2 instanceof Param;
    }

    static final boolean isParamOrCastParam(Field<?> field2) {
        return field2 instanceof Param || field2 instanceof Cast && Tools.isParamOrCastParam(((Cast)field2).$field());
    }

    static final boolean isVal(Field<?> field2) {
        return field2 instanceof Val || field2 instanceof ConvertedVal && ((ConvertedVal)field2).delegate instanceof Val;
    }

    static final boolean isWindow(QueryPart part) {
        return part instanceof AbstractWindowFunction && ((AbstractWindowFunction)part).isWindow();
    }

    static final boolean isSimple(Context<?> ctx, QueryPart part) {
        return part instanceof SimpleQueryPart || part instanceof SimpleCheckQueryPart && ((SimpleCheckQueryPart)part).isSimple(ctx);
    }

    static final boolean isSimple(Context<?> ctx, QueryPart ... parts) {
        for (QueryPart part : parts) {
            if (Tools.isSimple(ctx, part)) continue;
            return false;
        }
        return true;
    }

    static final boolean isRendersSeparator(QueryPart part) {
        return part instanceof SeparatedQueryPart && ((SeparatedQueryPart)((Object)part)).rendersSeparator();
    }

    static final boolean isPossiblyNullable(Field<?> f) {
        return f instanceof AbstractField && ((AbstractField)f).isPossiblyNullable();
    }

    static final Val<?> extractVal(Field<?> field2) {
        Val val;
        if (field2 instanceof Val) {
            Val v;
            val = v = (Val)field2;
        } else if (field2 instanceof ConvertedVal) {
            ConvertedVal v = (ConvertedVal)field2;
            val = (Val)v.delegate;
        } else {
            val = null;
        }
        return val;
    }

    static final boolean hasDefaultConverter(Field<?> field2) {
        return field2.getConverter() instanceof IdentityConverter;
    }

    static final <T> T extractParamValue(Field<T> field2) {
        if (Tools.isParam(field2)) {
            return ((Param)field2).getValue();
        }
        return null;
    }

    @Pro
    static final CommonTableExpression<?> extractUpdatableCte(Context<?> ctx) {
        Table dmlTable = (Table)ctx.data(SimpleDataKey.DATA_DML_TABLE_AS_CTE);
        if (dmlTable == null) {
            return null;
        }
        Select<?> updatableSelect = Tools.extractSelectFromDerivedTable(dmlTable);
        return dmlTable.getUnqualifiedName().as(updatableSelect);
    }

    @Pro
    static final Select<?> extractSelectFromDerivedTable(Table<?> table) {
        return Tools.extractSelectFromDerivedTable(table, false);
    }

    @Pro
    private static final Select<?> extractSelectFromDerivedTable(Table<?> table, boolean force) {
        if (table instanceof DerivedTable) {
            DerivedTable t2 = (DerivedTable)table;
            return t2.query();
        }
        if (table instanceof AliasedSelect) {
            AliasedSelect s2 = (AliasedSelect)table;
            return s2.query();
        }
        Table<?> aliased = Tools.aliased(table);
        if (aliased != null) {
            return Tools.extractSelectFromDerivedTable(aliased, true);
        }
        if (force) {
            return DSL.select(DSL.asterisk()).from((TableLike<?>)table);
        }
        return null;
    }

    static final <R extends Record> SelectQueryImpl<R> selectQueryImpl(QueryPart part) {
        if (part instanceof SelectQueryImpl) {
            SelectQueryImpl s2 = (SelectQueryImpl)part;
            return s2;
        }
        if (part instanceof SelectImpl) {
            SelectImpl s3 = (SelectImpl)part;
            return (SelectQueryImpl)s3.getDelegate();
        }
        if (part instanceof ScalarSubquery) {
            ScalarSubquery s4 = (ScalarSubquery)part;
            return Tools.selectQueryImpl(s4.query);
        }
        if (part instanceof QuantifiedSelectImpl) {
            QuantifiedSelectImpl s5 = (QuantifiedSelectImpl)part;
            return Tools.selectQueryImpl(s5.query);
        }
        return null;
    }

    static final AbstractResultQuery<?> abstractResultQuery(Query query) {
        if (query instanceof AbstractResultQuery) {
            AbstractResultQuery q = (AbstractResultQuery)query;
            return q;
        }
        if (query instanceof AbstractDelegatingQuery) {
            AbstractDelegatingQuery q = (AbstractDelegatingQuery)query;
            return Tools.abstractResultQuery(q.getDelegate());
        }
        return null;
    }

    static final InsertQueryImpl<?> insertQueryImpl(Query query) {
        AbstractDMLQuery<?> result = Tools.abstractDMLQuery(query);
        if (result instanceof InsertQueryImpl) {
            InsertQueryImpl q = (InsertQueryImpl)result;
            return q;
        }
        return null;
    }

    static final UpdateQueryImpl<?> updateQueryImpl(Query query) {
        AbstractDMLQuery<?> result = Tools.abstractDMLQuery(query);
        if (result instanceof UpdateQueryImpl) {
            UpdateQueryImpl q = (UpdateQueryImpl)result;
            return q;
        }
        return null;
    }

    static final DeleteQueryImpl<?> deleteQueryImpl(Query query) {
        AbstractDMLQuery<?> result = Tools.abstractDMLQuery(query);
        if (result instanceof DeleteQueryImpl) {
            DeleteQueryImpl q = (DeleteQueryImpl)result;
            return q;
        }
        return null;
    }

    static final AbstractDMLQuery<?> abstractDMLQuery(Query query) {
        if (query instanceof AbstractDMLQuery) {
            AbstractDMLQuery q = (AbstractDMLQuery)query;
            return q;
        }
        if (query instanceof AbstractDelegatingDMLQuery) {
            AbstractDelegatingDMLQuery q = (AbstractDelegatingDMLQuery)query;
            return Tools.abstractDMLQuery(q.getDelegate());
        }
        if (query instanceof AbstractDMLQueryAsResultQuery) {
            AbstractDMLQueryAsResultQuery q = (AbstractDMLQueryAsResultQuery)query;
            return q.getDelegate();
        }
        return null;
    }

    static final int degree(ResultQuery<?> query) {
        SelectQueryImpl s2 = Tools.selectQueryImpl(query);
        if (s2 != null && (s2.hasForJSON() || s2.hasForXML())) {
            return 1;
        }
        return query.types().length;
    }

    static final List<DataType<?>> dataTypes(ResultQuery<?> query) {
        SelectQueryImpl s2 = Tools.selectQueryImpl(query);
        if (s2 != null) {
            if (s2.hasForJSON()) {
                return Arrays.asList(s2.forJSON().forJSONB ? SQLDataType.JSONB : SQLDataType.JSON);
            }
            if (s2.hasForXML()) {
                return Arrays.asList(SQLDataType.XML);
            }
        }
        return Arrays.asList(query.dataTypes());
    }

    static final DataType<?> scalarType(ResultQuery<?> query) {
        List<DataType<?>> list = Tools.dataTypes(query);
        if (list.size() != 1) {
            throw new IllegalStateException("Only single-column selects have a scalar type");
        }
        return list.get(0);
    }

    static final void addConditions(ConditionProvider query, Record record, Field<?> ... keys2) {
        for (Field<?> field2 : keys2) {
            Tools.addCondition(query, record, field2);
        }
    }

    static final <T> void addCondition(ConditionProvider provider, Record record, Field<T> field2) {
        if (SettingsTools.updatablePrimaryKeys(Tools.settings(record))) {
            provider.addConditions(Tools.condition(field2, record.original(field2)));
        } else {
            provider.addConditions(Tools.condition(field2, record.get(field2)));
        }
    }

    static final <T> Condition condition(Field<T> field2, T value) {
        return value == null ? field2.isNull() : field2.eq(value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final JPANamespace jpaNamespace() {
        if (jpaNamespace == null) {
            Object object = initLock;
            synchronized (object) {
                if (jpaNamespace == null) {
                    try {
                        Class.forName(Column.class.getName());
                        jpaNamespace = JPANamespace.JAKARTA;
                    }
                    catch (Throwable e) {
                        try {
                            Class.forName(new String("javax.persistence.") + new String("Column"));
                            jpaNamespace = JPANamespace.JAVAX;
                            JooqLogger.getLogger(Tools.class, "isJPAAvailable", 1).info("javax.persistence.Column was found on the classpath instead of jakarta.persistence.Column. jOOQ 3.16 requires you to upgrade to Jakarta EE if you wish to use JPA annotations in your DefaultRecordMapper");
                        }
                        catch (Throwable ignore) {
                            jpaNamespace = JPANamespace.NONE;
                        }
                    }
                }
            }
        }
        return jpaNamespace;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final boolean isKotlinAvailable() {
        if (isKotlinAvailable == null) {
            Object object = initLock;
            synchronized (object) {
                if (isKotlinAvailable == null) {
                    try {
                        if (Tools.ktJvmClassMapping() != null) {
                            if (Tools.ktKClasses() != null) {
                                isKotlinAvailable = true;
                            } else {
                                isKotlinAvailable = false;
                                log.info("Kotlin is available, but not kotlin-reflect. Add the kotlin-reflect dependency to better use Kotlin features like data classes");
                            }
                        } else {
                            isKotlinAvailable = false;
                        }
                    }
                    catch (ReflectException e) {
                        isKotlinAvailable = false;
                    }
                }
            }
        }
        return isKotlinAvailable;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final Reflect ktJvmClassMapping() {
        if (ktJvmClassMapping == null) {
            Object object = initLock;
            synchronized (object) {
                if (ktJvmClassMapping == null) {
                    try {
                        ktJvmClassMapping = Reflect.onClass("kotlin.jvm.JvmClassMappingKt");
                    }
                    catch (ReflectException reflectException) {
                        // empty catch block
                    }
                }
            }
        }
        return ktJvmClassMapping;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final Reflect ktKClasses() {
        if (ktKClasses == null) {
            Object object = initLock;
            synchronized (object) {
                if (ktKClasses == null) {
                    try {
                        ktKClasses = Reflect.onClass("kotlin.reflect.full.KClasses");
                    }
                    catch (ReflectException reflectException) {
                        // empty catch block
                    }
                }
            }
        }
        return ktKClasses;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final Reflect ktKClass() {
        if (ktKClass == null) {
            Object object = initLock;
            synchronized (object) {
                if (ktKClass == null) {
                    try {
                        ktKClass = Reflect.onClass("kotlin.reflect.KClass");
                    }
                    catch (ReflectException reflectException) {
                        // empty catch block
                    }
                }
            }
        }
        return ktKClass;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final Reflect ktKTypeParameter() {
        if (ktKTypeParameter == null) {
            Object object = initLock;
            synchronized (object) {
                if (ktKTypeParameter == null) {
                    try {
                        ktKTypeParameter = Reflect.onClass("kotlin.reflect.KTypeParameter");
                    }
                    catch (ReflectException reflectException) {
                        // empty catch block
                    }
                }
            }
        }
        return ktKTypeParameter;
    }

    static final boolean hasColumnAnnotations(Configuration configuration, Class<?> type) {
        return Cache.run(configuration, () -> {
            switch (Tools.jpaNamespace()) {
                case JAVAX: {
                    if (Tools.anyMatch(type.getAnnotations(), Tools.isJavaxPersistenceAnnotation())) {
                        JooqLogger.getLogger(Tools.class, "hasColumnAnnotations", 1).warn("Type " + String.valueOf(type) + " is annotated with javax.persistence annotation for usage in DefaultRecordMapper, but starting from jOOQ 3.16, only JakartaEE annotations are supported.");
                    }
                    if (Tools.anyMatch(Tools.map(type.getMethods(), (? super T m4) -> Tools.anyMatch(m4.getAnnotations(), Tools.isJavaxPersistenceAnnotation())), (? super T b) -> b)) {
                        JooqLogger.getLogger(Tools.class, "hasColumnAnnotations", 1).warn("Type " + String.valueOf(type) + " has methods annotated with javax.persistence annotation for usage in DefaultRecordMapper, but starting from jOOQ 3.16, only JakartaEE annotations are supported.");
                    }
                    if (Tools.anyMatch(Tools.map(type.getDeclaredMethods(), (? super T m4) -> Tools.anyMatch(m4.getAnnotations(), Tools.isJavaxPersistenceAnnotation())), (? super T b) -> b)) {
                        JooqLogger.getLogger(Tools.class, "hasColumnAnnotations", 1).warn("Type " + String.valueOf(type) + " has methods annotated with javax.persistence annotation for usage in DefaultRecordMapper, but starting from jOOQ 3.16, only JakartaEE annotations are supported.");
                    }
                    if (Tools.anyMatch(Tools.map(type.getFields(), (? super T f) -> Tools.anyMatch(f.getAnnotations(), Tools.isJavaxPersistenceAnnotation())), (? super T b) -> b)) {
                        JooqLogger.getLogger(Tools.class, "hasColumnAnnotations", 1).warn("Type " + String.valueOf(type) + " has fields annotated with javax.persistence annotation for usage in DefaultRecordMapper, but starting from jOOQ 3.16, only JakartaEE annotations are supported.");
                    }
                    if (Tools.anyMatch(Tools.map(type.getDeclaredFields(), (? super T f) -> Tools.anyMatch(f.getAnnotations(), Tools.isJavaxPersistenceAnnotation())), (? super T b) -> b)) {
                        JooqLogger.getLogger(Tools.class, "hasColumnAnnotations", 1).warn("Type " + String.valueOf(type) + " has fields annotated with javax.persistence annotation for usage in DefaultRecordMapper, but starting from jOOQ 3.16, only JakartaEE annotations are supported.");
                    }
                    return false;
                }
                case JAKARTA: {
                    if (type.getAnnotation(Entity.class) != null) {
                        return true;
                    }
                    if (type.getAnnotation(jakarta.persistence.Table.class) != null) {
                        return true;
                    }
                    if (Tools.anyMatch(Tools.getInstanceMembers(type), (? super T m4) -> m4.getAnnotation(Column.class) != null || m4.getAnnotation(Id.class) != null)) {
                        return true;
                    }
                    return Tools.anyMatch(Tools.getInstanceMethods(type), (? super T m4) -> m4.getAnnotation(Column.class) != null);
                }
            }
            return false;
        }, CacheType.REFLECTION_CACHE_HAS_COLUMN_ANNOTATIONS, () -> type);
    }

    private static final ThrowingPredicate<? super Annotation, RuntimeException> isJavaxPersistenceAnnotation() {
        return a -> a.annotationType().getName().startsWith("javax.persistence.");
    }

    static final <T extends AccessibleObject> T accessible(T object, boolean makeAccessible) {
        return makeAccessible ? Reflect.accessible(object) : object;
    }

    static final List<java.lang.reflect.Field> getAnnotatedMembers(Configuration configuration, Class<?> type, String name, boolean makeAccessible) {
        return Cache.run(configuration, () -> {
            ArrayList<java.lang.reflect.Field> result = new ArrayList<java.lang.reflect.Field>();
            for (java.lang.reflect.Field member : Tools.getInstanceMembers(type)) {
                Column column = member.getAnnotation(Column.class);
                if (column != null) {
                    if (!Tools.namesMatch(name, column.name())) continue;
                    result.add(Tools.accessible(member, makeAccessible));
                    continue;
                }
                Id id = member.getAnnotation(Id.class);
                if (id == null || !Tools.namesMatch(name, member.getName())) continue;
                result.add(Tools.accessible(member, makeAccessible));
            }
            return result;
        }, CacheType.REFLECTION_CACHE_GET_ANNOTATED_MEMBERS, () -> Cache.key(type, name, makeAccessible));
    }

    private static final boolean namesMatch(String name, String annotation) {
        return annotation.startsWith("\"") ? ("\"" + name + "\"").equals(annotation) : name.equalsIgnoreCase(annotation);
    }

    static final List<java.lang.reflect.Field> getMatchingMembers(Configuration configuration, Class<?> type, String name, boolean makeAccessible) {
        return Cache.run(configuration, () -> {
            ArrayList<java.lang.reflect.Field> result = new ArrayList<java.lang.reflect.Field>();
            String camelCaseLC = StringUtils.toCamelCaseLC(name);
            for (java.lang.reflect.Field member : Tools.getInstanceMembers(type)) {
                if (name.equals(member.getName())) {
                    result.add(Tools.accessible(member, makeAccessible));
                    continue;
                }
                if (!camelCaseLC.equals(member.getName())) continue;
                result.add(Tools.accessible(member, makeAccessible));
            }
            return result;
        }, CacheType.REFLECTION_CACHE_GET_MATCHING_MEMBERS, () -> Cache.key(type, name, makeAccessible));
    }

    static final List<Method> getAnnotatedSetters(Configuration configuration, Class<?> type, String name, boolean makeAccessible) {
        return Cache.run(configuration, () -> {
            LinkedHashSet<SourceMethod> set = new LinkedHashSet<SourceMethod>();
            for (Method method2 : Tools.getInstanceMethods(type)) {
                String m4;
                String suffix;
                Column column = method2.getAnnotation(Column.class);
                if (column == null || !Tools.namesMatch(name, column.name())) continue;
                if (method2.getParameterTypes().length == 1) {
                    set.add(new SourceMethod(Tools.accessible(method2, makeAccessible)));
                    continue;
                }
                if (method2.getParameterTypes().length != 0 || (suffix = (m4 = method2.getName()).startsWith("get") ? m4.substring(3) : (m4.startsWith("is") ? m4.substring(2) : null)) == null) continue;
                try {
                    Method setter2 = Tools.getInstanceMethod(type, "set" + suffix, new Class[]{method2.getReturnType()});
                    if (setter2.getAnnotation(Column.class) != null) continue;
                    set.add(new SourceMethod(Tools.accessible(setter2, makeAccessible)));
                }
                catch (NoSuchMethodException noSuchMethodException) {}
            }
            return SourceMethod.methods(set);
        }, CacheType.REFLECTION_CACHE_GET_ANNOTATED_SETTERS, () -> Cache.key(type, name, makeAccessible));
    }

    static final Method getAnnotatedGetter(Configuration configuration, Class<?> type, String name, boolean makeAccessible) {
        return Cache.run(configuration, () -> {
            for (Method method2 : Tools.getInstanceMethods(type)) {
                String m4;
                Column column = method2.getAnnotation(Column.class);
                if (column == null || !Tools.namesMatch(name, column.name())) continue;
                if (method2.getParameterTypes().length == 0) {
                    return Tools.accessible(method2, makeAccessible);
                }
                if (method2.getParameterTypes().length != 1 || !(m4 = method2.getName()).startsWith("set")) continue;
                try {
                    Method getter1 = type.getMethod("get" + m4.substring(3), new Class[0]);
                    if (getter1.getAnnotation(Column.class) == null) {
                        return Tools.accessible(getter1, makeAccessible);
                    }
                }
                catch (NoSuchMethodException getter1) {
                    // empty catch block
                }
                try {
                    Method getter2 = type.getMethod("is" + m4.substring(3), new Class[0]);
                    if (getter2.getAnnotation(Column.class) != null) continue;
                    return Tools.accessible(getter2, makeAccessible);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                }
            }
            return null;
        }, CacheType.REFLECTION_CACHE_GET_ANNOTATED_GETTER, () -> Cache.key(type, name, makeAccessible));
    }

    static final List<Method> getMatchingSetters(Configuration configuration, Class<?> type, String name, boolean makeAccessible) {
        return Cache.run(configuration, () -> {
            LinkedHashSet<SourceMethod> set = new LinkedHashSet<SourceMethod>();
            String camelCase = StringUtils.toCamelCase(name);
            String camelCaseLC = StringUtils.toLC(camelCase);
            for (Method method2 : Tools.getInstanceMethods(type)) {
                Class<?>[] parameterTypes = method2.getParameterTypes();
                if (parameterTypes.length != 1) continue;
                if (name.equals(method2.getName())) {
                    set.add(new SourceMethod(Tools.accessible(method2, makeAccessible)));
                    continue;
                }
                if (camelCaseLC.equals(method2.getName())) {
                    set.add(new SourceMethod(Tools.accessible(method2, makeAccessible)));
                    continue;
                }
                if (("set" + name).equals(method2.getName())) {
                    set.add(new SourceMethod(Tools.accessible(method2, makeAccessible)));
                    continue;
                }
                if (!("set" + camelCase).equals(method2.getName())) continue;
                set.add(new SourceMethod(Tools.accessible(method2, makeAccessible)));
            }
            return SourceMethod.methods(set);
        }, CacheType.REFLECTION_CACHE_GET_MATCHING_SETTERS, () -> Cache.key(type, name, makeAccessible));
    }

    static final Method getMatchingGetter(Configuration configuration, Class<?> type, String name, boolean makeAccessible) {
        return Cache.run(configuration, () -> {
            String camelCase = StringUtils.toCamelCase(name);
            String camelCaseLC = StringUtils.toLC(camelCase);
            for (Method method2 : Tools.getInstanceMethods(type)) {
                if (method2.getParameterTypes().length != 0) continue;
                if (name.equals(method2.getName())) {
                    return Tools.accessible(method2, makeAccessible);
                }
                if (camelCaseLC.equals(method2.getName())) {
                    return Tools.accessible(method2, makeAccessible);
                }
                if (("get" + name).equals(method2.getName())) {
                    return Tools.accessible(method2, makeAccessible);
                }
                if (("get" + camelCase).equals(method2.getName())) {
                    return Tools.accessible(method2, makeAccessible);
                }
                if (("is" + name).equals(method2.getName())) {
                    return Tools.accessible(method2, makeAccessible);
                }
                if (!("is" + camelCase).equals(method2.getName())) continue;
                return Tools.accessible(method2, makeAccessible);
            }
            return null;
        }, CacheType.REFLECTION_CACHE_GET_MATCHING_GETTER, () -> Cache.key(type, name, makeAccessible));
    }

    private static final Method getInstanceMethod(Class<?> type, String name, Class<?>[] parameters) throws NoSuchMethodException {
        try {
            return type.getMethod(name, parameters);
        }
        catch (NoSuchMethodException e) {
            while (true) {
                try {
                    return type.getDeclaredMethod(name, parameters);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    if ((type = type.getSuperclass()) != null) continue;
                    throw new NoSuchMethodException();
                }
                break;
            }
        }
    }

    private static final Set<Method> getInstanceMethods(Class<?> type) {
        LinkedHashSet<Method> result = new LinkedHashSet<Method>();
        for (Method method2 : type.getMethods()) {
            if ((method2.getModifiers() & 8) != 0) continue;
            result.add(method2);
        }
        do {
            for (Method method2 : type.getDeclaredMethods()) {
                if ((method2.getModifiers() & 8) != 0) continue;
                result.add(method2);
            }
        } while ((type = type.getSuperclass()) != null);
        return result;
    }

    private static final List<java.lang.reflect.Field> getInstanceMembers(Class<?> type) {
        ArrayList<java.lang.reflect.Field> result = new ArrayList<java.lang.reflect.Field>();
        for (java.lang.reflect.Field field2 : type.getFields()) {
            if ((field2.getModifiers() & 8) != 0) continue;
            result.add(field2);
        }
        do {
            for (java.lang.reflect.Field field2 : type.getDeclaredFields()) {
                if ((field2.getModifiers() & 8) != 0) continue;
                result.add(field2);
            }
        } while ((type = type.getSuperclass()) != null);
        return result;
    }

    static final String getPropertyName(String methodName) {
        Object name = methodName;
        if (((String)name).startsWith("is") && ((String)name).length() > 2) {
            name = ((String)name).substring(2, 3).toLowerCase() + ((String)name).substring(3);
        } else if (((String)name).startsWith("get") && ((String)name).length() > 3) {
            name = ((String)name).substring(3, 4).toLowerCase() + ((String)name).substring(4);
        } else if (((String)name).startsWith("set") && ((String)name).length() > 3) {
            name = ((String)name).substring(3, 4).toLowerCase() + ((String)name).substring(4);
        }
        return name;
    }

    static final void consumeExceptions(Configuration configuration, PreparedStatement stmt, SQLException previous) {
        ThrowExceptions exceptions = configuration.settings().getThrowExceptions();
        if (exceptions == ThrowExceptions.THROW_FIRST) {
            return;
        }
        int i = 0;
        block1 : switch (configuration.family()) {
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                for (i = 0; i < maxConsumedExceptions; ++i) {
                    try {
                        if (stmt.getMoreResults() || stmt.getUpdateCount() != -1) continue;
                        break block1;
                    }
                    catch (SQLException e) {
                        previous.setNextException(e);
                        previous = e;
                    }
                }
                break;
            }
        }
        if (i == maxConsumedExceptions) {
            log.warn("Maximum consumed results reached: " + maxConsumedExceptions + ". This is probably a bug. Please report to https://jooq.org/bug");
        }
    }

    static final void consumeWarnings(ExecuteContext ctx, ExecuteListener listener) {
        if (!Boolean.FALSE.equals(ctx.settings().isFetchWarnings())) {
            try {
                ctx.sqlWarning(ctx.statement().getWarnings());
            }
            catch (SQLException e) {
                ctx.sqlWarning(new SQLWarning("Could not fetch SQLWarning", e));
            }
        }
        if (ctx.sqlWarning() != null) {
            listener.warning(ctx);
        }
    }

    static final SQLException executeStatementAndGetFirstResultSet(ExecuteContext ctx, int skipUpdateCounts) throws SQLException {
        PreparedStatement stmt = ctx.statement();
        try {
            if (ctx.family() == SQLDialect.ASE && Tools.isJtds(stmt)) {
                ctx.resultSet(stmt.executeQuery());
            } else if (ctx.family() == SQLDialect.ORACLE) {
                if (stmt.execute()) {
                    try {
                        ctx.resultSet(stmt.getResultSet());
                        ctx.rows(stmt.getUpdateCount());
                    }
                    catch (SQLException e) {
                        if (e.getErrorCode() == 17283 && stmt.getMoreResults()) {
                            ctx.resultSet(stmt.getResultSet());
                            ctx.rows(stmt.getUpdateCount());
                        }
                        throw e;
                    }
                } else {
                    ctx.resultSet(null);
                    ctx.rows(stmt.getUpdateCount());
                    if (ctx.rows() == -1 && stmt.getMoreResults()) {
                        ctx.resultSet(stmt.getResultSet());
                        ctx.rows(stmt.getUpdateCount());
                    }
                }
            } else if (skipUpdateCounts > 0) {
                for (int i = 0; i < maxConsumedResults; ++i) {
                    boolean result;
                    boolean bl = result = i == 0 ? stmt.execute() : stmt.getMoreResults();
                    if (result) {
                        ctx.resultSet(stmt.getResultSet());
                    } else {
                        int updateCount = stmt.getUpdateCount();
                        if (i == 0) {
                            ctx.resultSet(null);
                            ctx.rows(updateCount);
                        }
                        if (updateCount != -1 && skipUpdateCounts-- != 0) {
                            continue;
                        }
                    }
                    break;
                }
            } else if (stmt.execute()) {
                ctx.resultSet(stmt.getResultSet());
            } else {
                ctx.resultSet(null);
                ctx.rows(stmt.getUpdateCount());
            }
            return null;
        }
        catch (SQLException e) {
            if (ctx.settings().getThrowExceptions() != ThrowExceptions.THROW_NONE) {
                Tools.consumeExceptions(ctx.configuration(), ctx.statement(), e);
                throw e;
            }
            return e;
        }
    }

    @Pro
    static final boolean isJtds(Statement stmt) throws SQLException {
        return stmt.unwrap(Statement.class).getClass().getName().contains("jtds");
    }

    static final void consumeResultSets(ExecuteContext ctx, ExecuteListener listener, Results results, Intern intern, SQLException prev) throws SQLException {
        int i;
        boolean anyResults = false;
        int rows = ctx.resultSet() == null ? ctx.rows() : 0;
        for (i = 0; i < maxConsumedResults; ++i) {
            try {
                if (ctx.resultSet() != null) {
                    anyResults = true;
                    Field<?>[] fields = new MetaDataFieldProvider(ctx.configuration(), ctx.resultSet().getMetaData()).getFields();
                    CursorImpl c = new CursorImpl(ctx, listener, fields, intern != null ? intern.internIndexes(fields) : null, true, false);
                    results.resultsOrRows().add(new ResultsImpl.ResultOrRowsImpl(c.fetch()));
                } else if (prev == null) {
                    if (rows == -1) break;
                    results.resultsOrRows().add(new ResultsImpl.ResultOrRowsImpl(rows));
                }
                if (ctx.statement().getMoreResults()) {
                    ctx.resultSet(ctx.statement().getResultSet());
                } else {
                    rows = ctx.statement().getUpdateCount();
                    ctx.rows(rows);
                    if (rows == -1) break;
                    ctx.resultSet(null);
                }
                prev = null;
                continue;
            }
            catch (SQLException e) {
                prev = e;
                if (ctx.settings().getThrowExceptions() == ThrowExceptions.THROW_NONE) {
                    ctx.sqlException(e);
                    results.resultsOrRows().add(new ResultsImpl.ResultOrRowsImpl(Tools.translate(ctx.sql(), e)));
                    continue;
                }
                Tools.consumeExceptions(ctx.configuration(), ctx.statement(), e);
                throw e;
            }
        }
        if (i == maxConsumedResults) {
            log.warn("Maximum consumed results reached: " + maxConsumedResults + ". This is probably a bug. Please report to https://jooq.org/bug");
        }
        if (anyResults) {
            if (ctx.family() == SQLDialect.TERADATA) {
                ctx.statement().getMoreResults(1);
            } else {
                ctx.statement().getMoreResults(3);
            }
        }
        if (ctx.settings().getThrowExceptions() == ThrowExceptions.THROW_NONE) {
            SQLException s1 = null;
            for (ResultOrRows r : results.resultsOrRows()) {
                DataAccessException d = r.exception();
                if (d == null || !(d.getCause() instanceof SQLException)) continue;
                SQLException s2 = (SQLException)d.getCause();
                if (s1 != null) {
                    s1.setNextException(s2);
                }
                s1 = s2;
            }
        }
    }

    static final List<String[]> parseTXT(String string, String nullLiteral) {
        boolean formattedOracle;
        String[] strings = NEW_LINES.split(string);
        if (strings.length < 2) {
            throw new DataAccessException("String must contain at least two lines");
        }
        boolean formattedJOOQ = string.charAt(0) == '+';
        boolean bl = formattedOracle = string.charAt(0) == '-';
        if (formattedJOOQ) {
            return Tools.parseTXTLines(nullLiteral, strings, PLUS_PATTERN, 0, 1, 3, strings.length - 1);
        }
        if (formattedOracle) {
            return Tools.parseTXTLines(nullLiteral, strings, PIPE_PATTERN, 1, 1, 3, strings.length - 1);
        }
        return Tools.parseTXTLines(nullLiteral, strings, DASH_PATTERN, 1, 0, 2, strings.length);
    }

    private static final List<String[]> parseTXTLines(String nullLiteral, String[] strings, Pattern pattern, int matchLine, int headerLine, int dataLineStart, int dataLineEnd) {
        ArrayList<int[]> positions = new ArrayList<int[]>();
        Matcher m4 = pattern.matcher(strings[matchLine]);
        while (m4.find()) {
            positions.add(new int[]{m4.start(1), m4.end(1)});
        }
        ArrayList<String[]> result = new ArrayList<String[]>();
        Tools.parseTXTLine(positions, result, strings[headerLine], nullLiteral);
        for (int j = dataLineStart; j < dataLineEnd; ++j) {
            Tools.parseTXTLine(positions, result, strings[j], nullLiteral);
        }
        return result;
    }

    private static final void parseTXTLine(List<int[]> positions, List<String[]> result, String string, String nullLiteral) {
        String[] fields = new String[positions.size()];
        result.add(fields);
        int length = string.length();
        for (int i = 0; i < fields.length; ++i) {
            int[] position = positions.get(i);
            fields[i] = position[0] < length ? string.substring(position[0], Math.min(position[1], length)).trim() : null;
            if (!StringUtils.equals(fields[i], nullLiteral)) continue;
            fields[i] = null;
        }
    }

    static final List<String[]> parseHTML(String string) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        Matcher mRow = P_PARSE_HTML_ROW.matcher(string);
        while (mRow.find()) {
            String row = mRow.group(1);
            ArrayList<String> col = new ArrayList<String>();
            if (result.isEmpty()) {
                Matcher mColHead = P_PARSE_HTML_COL_HEAD.matcher(row);
                while (mColHead.find()) {
                    col.add(mColHead.group(1));
                }
            }
            if (col.isEmpty()) {
                Matcher mColBody = P_PARSE_HTML_COL_BODY.matcher(row);
                while (mColBody.find()) {
                    col.add(mColBody.group(1));
                }
                if (result.isEmpty()) {
                    result.add(Tools.fieldNameStrings(col.size()));
                }
            }
            result.add(col.toArray(EMPTY_STRING));
        }
        return result;
    }

    static final void begin(Context<?> ctx, Consumer<? super Context<?>> runnable2) {
        Tools.begin(ctx);
        runnable2.accept(ctx);
        Tools.end(ctx);
    }

    private static final void begin(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: 
            case DB2: 
            case SYBASE: {
                ctx.visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
                break;
            }
            case HANA: {
                ctx.visit(Keywords.K_DO).sql(' ').visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
                break;
            }
            case FIREBIRD: {
                ctx.visit(Keywords.K_EXECUTE_BLOCK).formatSeparator().visit(Keywords.K_AS).formatSeparator().visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
                break;
            }
            case MARIADB: {
                ctx.visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_NOT).sql(' ').visit(Keywords.K_ATOMIC).formatIndentStart().formatSeparator();
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (Tools.increment(ctx.data(), SimpleDataKey.DATA_BLOCK_NESTING)) {
                    ctx.visit(Keywords.K_DO).sql(" $$").formatSeparator();
                }
                ctx.visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
            }
        }
    }

    private static final void end(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: 
            case SYBASE: 
            case HANA: {
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END).sql(';');
                break;
            }
            case DB2: 
            case FIREBIRD: 
            case MARIADB: {
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END);
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END);
                if (!Tools.decrement(ctx.data(), SimpleDataKey.DATA_BLOCK_NESTING)) break;
                ctx.sql(" $$");
            }
        }
    }

    static final void executeImmediateIf(boolean wrap, Context<?> ctx, Consumer<? super Context<?>> runnable2) {
        if (wrap) {
            Tools.executeImmediate(ctx, runnable2);
        } else {
            runnable2.accept(ctx);
            ctx.sql(';');
        }
    }

    static final void executeImmediate(Context<?> ctx, Consumer<? super Context<?>> runnable2) {
        Tools.beginExecuteImmediate(ctx);
        runnable2.accept(ctx);
        Tools.endExecuteImmediate(ctx);
    }

    static final void beginExecuteImmediate(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: 
            case DB2: 
            case HANA: {
                ctx.visit(Keywords.K_EXECUTE_IMMEDIATE).sql(" '").stringLiteral(true).formatIndentStart().formatSeparator();
                break;
            }
            case FIREBIRD: {
                ctx.visit(Keywords.K_EXECUTE_STATEMENT).sql(" '").stringLiteral(true).formatIndentStart().formatSeparator();
            }
        }
    }

    static final void endExecuteImmediate(Context<?> ctx) {
        ctx.formatIndentEnd().formatSeparator().stringLiteral(false).sql("';");
    }

    static final void tryCatch(Context<?> ctx, DDLStatementType type, Consumer<? super Context<?>> runnable2) {
        Tools.tryCatch(ctx, type, null, null, runnable2);
    }

    static final void tryCatch(Context<?> ctx, DDLStatementType type, Boolean container, Boolean element, Consumer<? super Context<?>> runnable2) {
        switch (ctx.family()) {
            case BIGQUERY: {
                ArrayList<String> prefixes = new ArrayList<String>();
                switch (type) {
                    case CREATE_FUNCTION: 
                    case CREATE_PROCEDURE: 
                    case CREATE_TABLE: 
                    case CREATE_VIEW: {
                        prefixes.add("Already Exists%");
                        break;
                    }
                    case ALTER_FUNCTION: 
                    case ALTER_PROCEDURE: 
                    case DROP_FUNCTION: 
                    case DROP_PROCEDURE: {
                        prefixes.add("Not found: Routine%");
                        break;
                    }
                    case ALTER_TABLE: 
                    case DROP_TABLE: {
                        prefixes.add("Not found: Table%");
                        break;
                    }
                    case ALTER_VIEW: 
                    case DROP_VIEW: {
                        prefixes.add("Not found: View%");
                    }
                }
                Tools.begin(ctx, c -> {
                    runnable2.accept((Context<?>)c);
                    c.formatIndentEnd().formatSeparator();
                    if (!prefixes.isEmpty()) {
                        c.visit(Keywords.K_EXCEPTION).formatIndentStart().formatSeparator().visit(Keywords.K_WHEN).sql(' ').visit(Keywords.K_ERROR).sql(' ').visit(Keywords.K_THEN).formatIndentStart().formatSeparator();
                        Condition and = DSL.noCondition();
                        for (String p : prefixes) {
                            and = and.and(DSL.field("@@error.message", SQLDataType.VARCHAR).notLike(DSL.inline(p)));
                        }
                        c.visit(Keywords.K_IF).sql(' ').visit(and).sql(' ').visit(Keywords.K_THEN).sql(' ').visit(Keywords.K_RAISE).sql("; ").visit(Keywords.K_END_IF).sql(';').formatIndentEnd().formatSeparator();
                    }
                });
                break;
            }
            case DB2: {
                ArrayList<String> sqlstates = new ArrayList<String>();
                if (type == DDLStatementType.CREATE_INDEX) {
                    sqlstates.add("42710");
                } else if (type == DDLStatementType.CREATE_SCHEMA) {
                    sqlstates.add("42710");
                } else if (type == DDLStatementType.CREATE_SEQUENCE) {
                    sqlstates.add("42710");
                } else if (type == DDLStatementType.CREATE_VIEW) {
                    sqlstates.add("42710");
                } else if (type == DDLStatementType.CREATE_TABLE) {
                    sqlstates.add("42710");
                } else if (type == DDLStatementType.ALTER_TABLE) {
                    if (Boolean.TRUE.equals(container)) {
                        sqlstates.add("42704");
                    }
                    if (Boolean.TRUE.equals(element)) {
                        sqlstates.add("42703");
                    } else if (Boolean.FALSE.equals(element)) {
                        sqlstates.add("42711");
                    }
                } else {
                    sqlstates.add("42704");
                }
                if (sqlstates.isEmpty()) {
                    runnable2.accept(ctx);
                    break;
                }
                Tools.begin(ctx, c -> {
                    for (String sqlstate : sqlstates) {
                        c.visit(DSL.keyword("declare continue handler for sqlstate")).sql(' ').visit(DSL.inline(sqlstate)).sql(' ').visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_END).sql(';').formatSeparator();
                    }
                    Tools.executeImmediate(c, runnable2);
                });
                break;
            }
            case HANA: {
                ArrayList<Integer> sqlstates = new ArrayList<Integer>();
                if (type == DDLStatementType.CREATE_SCHEMA) {
                    sqlstates.add(386);
                } else if (type == DDLStatementType.ALTER_SCHEMA) {
                    sqlstates.add(362);
                } else if (type == DDLStatementType.DROP_SCHEMA) {
                    sqlstates.add(362);
                } else if (type == DDLStatementType.CREATE_SEQUENCE) {
                    sqlstates.add(324);
                } else if (type == DDLStatementType.DROP_SEQUENCE) {
                    sqlstates.add(313);
                } else if (type == DDLStatementType.CREATE_VIEW) {
                    sqlstates.add(322);
                } else if (type == DDLStatementType.DROP_VIEW) {
                    sqlstates.add(321);
                } else if (type == DDLStatementType.CREATE_TABLE) {
                    sqlstates.add(288);
                } else if (type == DDLStatementType.ALTER_TABLE) {
                    if (Boolean.TRUE.equals(container)) {
                        sqlstates.add(259);
                    }
                    if (Boolean.TRUE.equals(element)) {
                        sqlstates.addAll(Arrays.asList(257, 260, 397));
                    } else if (Boolean.FALSE.equals(element)) {
                        sqlstates.add(308);
                    }
                } else if (type == DDLStatementType.DROP_TABLE) {
                    sqlstates.add(259);
                } else if (type == DDLStatementType.CREATE_INDEX) {
                    sqlstates.add(289);
                } else if (type == DDLStatementType.DROP_INDEX) {
                    sqlstates.add(261);
                } else {
                    sqlstates.add(259);
                }
                Tools.begin(ctx, c -> {
                    for (Integer sqlstate : sqlstates) {
                        c.visit(DSL.keyword("declare exit handler for sql_error_code")).sql(' ').visit(DSL.inline(sqlstate)).sql(' ').visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_END).sql(';').formatSeparator();
                    }
                    Tools.executeImmediate(c, runnable2);
                });
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ArrayList<String> err = new ArrayList<String>();
                if (type == DDLStatementType.DROP_INDEX) {
                    err.add("3701");
                } else if (type == DDLStatementType.DROP_SEQUENCE) {
                    err.add("3701");
                } else if (type == DDLStatementType.DROP_TABLE) {
                    err.add("3701");
                } else if (type == DDLStatementType.DROP_VIEW) {
                    err.add("3701");
                } else if (type == DDLStatementType.DROP_SCHEMA) {
                    err.add("15151");
                } else if (type == DDLStatementType.ALTER_TABLE) {
                    if (Boolean.TRUE.equals(container)) {
                        err.addAll(Arrays.asList("4902", "15165", "15225"));
                    }
                    if (Boolean.TRUE.equals(element)) {
                        err.add("4924");
                    } else if (Boolean.FALSE.equals(element)) {
                        err.add("2705");
                    }
                } else if (type == DDLStatementType.ALTER_VIEW) {
                    err.add("15225");
                } else if (type == DDLStatementType.CREATE_DATABASE) {
                    err.add("1801");
                } else if (type == DDLStatementType.CREATE_DOMAIN) {
                    err.add("219");
                } else if (type == DDLStatementType.CREATE_SCHEMA) {
                    err.addAll(Arrays.asList("2714", "2759"));
                } else if (type == DDLStatementType.CREATE_INDEX) {
                    err.addAll(Arrays.asList("2714", "1913"));
                } else {
                    err.add("2714");
                }
                ctx.visit(Keywords.K_BEGIN_TRY).formatIndentStart().formatSeparator();
                switch (type) {
                    case CREATE_VIEW: 
                    case ALTER_TABLE: {
                        ctx.visit(Keywords.K_EXEC).sql(' ').sql("sp_executesql").sql(" N'").stringLiteral(true).formatIndentStart().formatSeparator();
                        runnable2.accept(ctx);
                        Tools.endExecuteImmediate(ctx);
                        break;
                    }
                    default: {
                        runnable2.accept(ctx);
                    }
                }
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END_TRY).formatSeparator().visit(Keywords.K_BEGIN_CATCH).formatIndentStart().formatSeparator();
                ctx.visit(Keywords.K_IF);
                for (int i = 0; i < err.size(); ++i) {
                    ctx.sql(i == 0 ? "" : " and").sql(' ').sql("error_number() != ").sql((String)err.get(i));
                }
                ctx.sql(' ');
                if (ctx.dialect().supports(SQLDialect.SQLSERVER2012)) {
                    ctx.visit(Keywords.K_THROW).sql(';').formatIndentEnd().formatSeparator();
                } else {
                    ctx.visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator().visit(Keywords.K_DECLARE).sql(" @ErrorMessage ").visit(Keywords.K_NVARCHAR).sql("(4000) = ERROR_MESSAGE();").formatSeparator().visit(Keywords.K_DECLARE).sql(" @ErrorSeverity ").visit(Keywords.K_INT).sql(" = ERROR_SEVERITY();").formatSeparator().visit(Keywords.K_DECLARE).sql(" @ErrorState ").visit(Keywords.K_INT).sql(" = ERROR_STATE();").formatSeparator().visit(Keywords.K_RAISERROR).sql(" (@ErrorMessage, @ErrorSeverity, @ErrorState);").formatIndentEnd().formatSeparator().visit(Keywords.K_END).sql(';').formatIndentEnd().formatSeparator();
                }
                ctx.visit(Keywords.K_END_CATCH);
                break;
            }
            case ORACLE: {
                ArrayList<String> ora = new ArrayList<String>();
                switch (type) {
                    case DROP_FUNCTION: 
                    case DROP_PROCEDURE: 
                    case ALTER_SEQUENCE: {
                        ora.add("ORA-04043");
                        break;
                    }
                    case DROP_INDEX: 
                    case ALTER_INDEX: {
                        ora.add("ORA-01418");
                        break;
                    }
                    case DROP_SEQUENCE: {
                        ora.add("ORA-02289");
                        break;
                    }
                    case DROP_TABLE: 
                    case DROP_VIEW: {
                        ora.add("ORA-00942");
                        break;
                    }
                    case DROP_TRIGGER: {
                        ora.add("ORA-04080");
                        break;
                    }
                    case ALTER_TABLE: {
                        if (Boolean.TRUE.equals(container)) {
                            ora.add("ORA-00942");
                        }
                        if (Boolean.TRUE.equals(element)) {
                            ora.add("ORA-00904");
                            break;
                        }
                        if (!Boolean.FALSE.equals(element)) break;
                        ora.add("ORA-01430");
                        break;
                    }
                    case ALTER_VIEW: {
                        ora.addAll(Arrays.asList("ORA-00942", "ORA-04043"));
                        break;
                    }
                    case CREATE_FUNCTION: 
                    case CREATE_PROCEDURE: 
                    case CREATE_TABLE: 
                    case CREATE_VIEW: 
                    case CREATE_INDEX: 
                    case CREATE_SEQUENCE: {
                        ora.add("ORA-00955");
                        break;
                    }
                    default: {
                        ora.add("ORA-xxxxx");
                    }
                }
                Tools.begin(ctx, c -> {
                    Tools.executeImmediate(c, runnable2);
                    c.formatIndentEnd().formatSeparator();
                    if (!ora.isEmpty()) {
                        c.visit(Keywords.K_EXCEPTION).formatIndentStart().formatSeparator().visit(Keywords.K_WHEN).sql(" others ").visit(Keywords.K_THEN).formatIndentStart().formatSeparator();
                        for (int i = 0; i < ora.size(); ++i) {
                            c.visit(i == 0 ? Keywords.K_IF : Keywords.K_ELSIF).sql(" sqlerrm ").visit(Keywords.K_LIKE).sql(" '" + (String)ora.get(i) + "%' ").visit(Keywords.K_THEN).sql(' ').visit(Keywords.K_NULL).sql(';').formatSeparator();
                        }
                        c.visit(Keywords.K_ELSE).sql(' ').visit(Keywords.K_RAISE).sql(';').formatSeparator().visit(Keywords.K_END_IF).sql(';').formatIndentEnd();
                    }
                });
                break;
            }
            case SYBASE: {
                Tools.begin(ctx, c -> {
                    runnable2.accept((Context<?>)c);
                    c.sql(';').formatIndentEnd().formatSeparator().visit(Keywords.K_EXCEPTION).formatIndentStart().formatSeparator().visit(Keywords.K_WHEN).sql(" others ").visit(Keywords.K_THEN).formatIndentEnd();
                });
            }
            case FIREBIRD: {
                Tools.begin(ctx, c -> {
                    Tools.executeImmediate(c, runnable2);
                    c.formatSeparator().visit(Keywords.K_WHEN).sql(" sqlcode -607 ").visit(Keywords.K_DO).formatIndentStart().formatSeparator().visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_END).formatIndentEnd();
                });
                break;
            }
            case MARIADB: {
                ArrayList<String> sqlstates = new ArrayList<String>();
                sqlstates.add("42S02");
                Tools.begin(ctx, c -> {
                    for (String sqlstate : sqlstates) {
                        c.visit(DSL.keyword("declare continue handler for sqlstate")).sql(' ').visit(DSL.inline(sqlstate)).sql(' ').visit(Keywords.K_BEGIN).sql(' ').visit(Keywords.K_END).sql(';').formatSeparator();
                    }
                    runnable2.accept((Context<?>)c);
                    c.sql(';');
                });
                break;
            }
            case AURORA_POSTGRES: 
            case POSTGRES: 
            case YUGABYTEDB: {
                Tools.begin(ctx, c -> {
                    String sqlstate = switch (type) {
                        case DDLStatementType.ALTER_DATABASE -> "3D000";
                        case DDLStatementType.ALTER_DOMAIN -> "42704";
                        case DDLStatementType.CREATE_DOMAIN -> "42710";
                        default -> "42P07";
                    };
                    runnable2.accept((Context<?>)c);
                    c.sql(';').formatIndentEnd().formatSeparator().visit(Keywords.K_EXCEPTION).formatIndentStart().formatSeparator().visit(Keywords.K_WHEN).sql(' ').visit(Keywords.K_SQLSTATE).sql(' ').visit(DSL.inline(sqlstate)).sql(' ').visit(Keywords.K_THEN).sql(' ').visit(Keywords.K_NULL).sql(';').formatIndentEnd();
                });
                break;
            }
            default: {
                runnable2.accept(ctx);
            }
        }
    }

    static final void toSQLDDLTypeDeclarationForAddition(Context<?> ctx, DataType<?> type) {
        boolean qualify = ctx.qualify();
        Tools.toSQLDDLTypeDeclaration(ctx, type);
        ctx.qualify(false);
        Tools.toSQLDDLTypeDeclarationIdentityBeforeNull(ctx, type);
        Tools.toSQLDDLTypeDeclarationComputedBeforeNull(ctx, type);
        if (DEFAULT_BEFORE_NULL.contains((Object)ctx.dialect())) {
            Tools.toSQLDDLTypeDeclarationDefault(ctx, type);
        }
        if (!type.computedOnServer(ctx.configuration()) || !COMPUTED_EXCLUDES_NULL.contains((Object)ctx.dialect()) || ctx.family() == SQLDialect.SQLSERVER && type.generationOption() == QOM.GenerationOption.STORED) {
            Tools.toSQLDDLTypeDeclarationForAdditionNullability(ctx, type);
        }
        if (!DEFAULT_BEFORE_NULL.contains((Object)ctx.dialect())) {
            Tools.toSQLDDLTypeDeclarationDefault(ctx, type);
        }
        Tools.toSQLDDLTypeDeclarationIdentityAfterNull(ctx, type);
        Tools.toSQLDDLTypeDeclarationComputedAfterNull(ctx, type);
        ctx.qualify(qualify);
    }

    private static final void toSQLDDLTypeDeclarationForAdditionNullability(Context<?> ctx, DataType<?> type) {
        block0 : switch (type.nullability()) {
            case NOT_NULL: {
                ctx.sql(' ').visit(Keywords.K_NOT_NULL);
                break;
            }
            case NULL: {
                if (NO_SUPPORT_NULL.contains((Object)ctx.dialect())) break;
                ctx.sql(' ').visit(Keywords.K_NULL);
                break;
            }
            case DEFAULT: {
                RenderDefaultNullability nullability = StringUtils.defaultIfNull(ctx.settings().getRenderDefaultNullability(), RenderDefaultNullability.IMPLICIT_NULL);
                switch (nullability) {
                    case EXPLICIT_NULL: {
                        if (NO_SUPPORT_NULL.contains((Object)ctx.dialect())) break block0;
                        ctx.sql(' ').visit(Keywords.K_NULL);
                        break block0;
                    }
                    case IMPLICIT_DEFAULT: {
                        break block0;
                    }
                    case IMPLICIT_NULL: {
                        if (DEFAULT_NOT_NULL.contains((Object)ctx.dialect())) {
                            ctx.sql(' ').visit(Keywords.K_NULL);
                            break block0;
                        }
                        if (!DEFAULT_TIMESTAMP_NOT_NULL.contains((Object)ctx.dialect()) || !type.isTimestamp()) break block0;
                        ctx.sql(' ').visit(Keywords.K_NULL);
                        break block0;
                    }
                    default: {
                        throw new UnsupportedOperationException("Nullability not supported: " + String.valueOf((Object)nullability));
                    }
                }
            }
            default: {
                throw new UnsupportedOperationException("Nullability not supported: " + String.valueOf((Object)type.nullability()));
            }
        }
    }

    static final void toSQLDDLTypeDeclarationIdentityBeforeNull(Context<?> ctx, DataType<?> type) {
        if (REQUIRE_IDENTITY_AFTER_NULL.contains((Object)ctx.dialect())) {
            return;
        }
        if (type.identity()) {
            switch (ctx.family()) {
                case ASE: 
                case EXASOL: 
                case SNOWFLAKE: {
                    ctx.sql(' ').visit(Keywords.K_IDENTITY);
                    break;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case REDSHIFT: {
                    ctx.sql(' ').visit(Keywords.K_IDENTITY).sql("(1, 1)");
                    break;
                }
                case CUBRID: {
                    ctx.sql(' ').visit(Keywords.K_AUTO_INCREMENT);
                    break;
                }
                case ORACLE: 
                case HSQLDB: {
                    ctx.sql(' ').visit(Keywords.K_GENERATED).sql(' ').visit(Keywords.K_BY).sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_IDENTITY).sql('(').visit(Keywords.K_START_WITH).sql(" 1)");
                    break;
                }
                case SQLITE: {
                    ctx.sql(' ').visit(Keywords.K_PRIMARY_KEY).sql(' ').visit(Keywords.K_AUTOINCREMENT);
                    break;
                }
                case POSTGRES: {
                    if (!SUPPORT_PG_IDENTITY.contains((Object)ctx.dialect())) break;
                    ctx.sql(' ').visit(Keywords.K_GENERATED).sql(' ').visit(Keywords.K_BY).sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_IDENTITY);
                    break;
                }
                case DB2: 
                case FIREBIRD: 
                case YUGABYTEDB: 
                case DERBY: {
                    ctx.sql(' ').visit(Keywords.K_GENERATED).sql(' ').visit(Keywords.K_BY).sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_IDENTITY);
                }
            }
        }
    }

    static final void toSQLDDLTypeDeclarationIdentityAfterNull(Context<?> ctx, DataType<?> type) {
        if (!REQUIRE_IDENTITY_AFTER_NULL.contains((Object)ctx.dialect())) {
            return;
        }
        if (type.identity()) {
            switch (ctx.family()) {
                case HANA: 
                case INGRES: 
                case TERADATA: 
                case H2: {
                    ctx.sql(' ').visit(Keywords.K_GENERATED).sql(' ').visit(Keywords.K_BY).sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(Keywords.K_AS).sql(' ').visit(Keywords.K_IDENTITY);
                    break;
                }
                case SYBASE: {
                    ctx.sql(' ').visit(Keywords.K_IDENTITY);
                    break;
                }
                case MARIADB: 
                case AURORA_MYSQL: 
                case MEMSQL: 
                case MYSQL: {
                    ctx.sql(' ').visit(Keywords.K_AUTO_INCREMENT);
                }
            }
        }
    }

    @Pro
    static final boolean anyComputedOnClientStoredFields(Configuration configuration, Table<?> table) {
        return Tools.anyMatch(Tools.flattenCollection(Arrays.asList(table.fields())), (? super T x) -> x.getDataType().computedOnClientStored(configuration));
    }

    @Pro
    static final void toSQLDDLTypeDeclarationComputedBeforeNull(Context<?> ctx, DataType<?> type) {
        if (REQUIRE_COMPUTED_AFTER_NULL.contains((Object)ctx.dialect())) {
            return;
        }
        if (type.computedOnServer(ctx.configuration())) {
            switch (ctx.family()) {
                case MEMSQL: {
                    ctx.visit(Keywords.K_AS).sql(' ').visit(type.generatedAlwaysAs()).sql(' ').visit(Keywords.K_PERSISTED).sql(' ');
                    Tools.toSQLDDLTypeDeclaration0(ctx, type);
                    break;
                }
                case SQLSERVER: {
                    ctx.visit(Keywords.K_AS).sql(' ').visit(type.generatedAlwaysAs());
                    break;
                }
                default: {
                    ctx.sql(' ').visit(Keywords.K_GENERATED).sql(' ').visit(Keywords.K_ALWAYS).sql(' ').visit(Keywords.K_AS).sql(" (").visit(type.generatedAlwaysAs()).sql(')');
                }
            }
            Tools.toSQLDDLTypeDeclarationComputedGenerationOption(ctx, type);
        }
    }

    @Pro
    static final void toSQLDDLTypeDeclarationComputedGenerationOption(Context<?> ctx, DataType<?> type) {
        block0 : switch (ctx.family()) {
            case DB2: 
            case HANA: 
            case FIREBIRD: 
            case HSQLDB: 
            case DERBY: 
            case H2: 
            case MEMSQL: {
                break;
            }
            case ORACLE: {
                if (type.generationOption() != QOM.GenerationOption.VIRTUAL) break;
                ctx.sql(' ').visit(Keywords.K_VIRTUAL);
                break;
            }
            case SQLSERVER: {
                if (type.generationOption() != QOM.GenerationOption.STORED) break;
                ctx.sql(' ').visit(Keywords.K_PERSISTED);
                break;
            }
            case COCKROACHDB: {
                switch (type.generationOption()) {
                    case VIRTUAL: {
                        ctx.sql(' ').visit(Keywords.K_VIRTUAL);
                        break block0;
                    }
                }
                ctx.sql(' ').visit(Keywords.K_STORED);
                break;
            }
            case POSTGRES: {
                ctx.sql(' ').visit(Keywords.K_STORED);
                break;
            }
            default: {
                switch (type.generationOption()) {
                    case VIRTUAL: {
                        ctx.sql(' ').visit(Keywords.K_VIRTUAL);
                        break block0;
                    }
                    case STORED: {
                        ctx.sql(' ').visit(Keywords.K_STORED);
                    }
                }
            }
        }
    }

    @Pro
    static final void toSQLDDLTypeDeclarationComputedAfterNull(Context<?> ctx, DataType<?> type) {
        if (!REQUIRE_COMPUTED_AFTER_NULL.contains((Object)ctx.dialect())) {
            return;
        }
        if (type.computedOnServer(ctx.configuration())) {
            switch (ctx.family()) {
                case H2: {
                    ctx.sql(' ').visit(Keywords.K_AS).sql(" (").visit(type.generatedAlwaysAs()).sql(')');
                    break;
                }
                default: {
                    ctx.sql(' ').visit(Keywords.K_GENERATED).sql(' ').visit(Keywords.K_ALWAYS).sql(' ').visit(Keywords.K_AS).sql(" (").visit(type.generatedAlwaysAs()).sql(')');
                }
            }
            Tools.toSQLDDLTypeDeclarationComputedGenerationOption(ctx, type);
        }
    }

    private static final void toSQLDDLTypeDeclarationDefault(Context<?> ctx, DataType<?> type) {
        if (type.defaulted()) {
            ctx.sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(type.defaultValue());
        }
    }

    static final void toSQLDDLTypeDeclaration(Context<?> ctx, DataType<?> type) {
        if (type.computedOnServer(ctx.configuration()) && COMPUTED_NO_TYPE_DECLARATION.contains((Object)ctx.dialect())) {
            return;
        }
        Tools.toSQLDDLTypeDeclaration0(ctx, type);
    }

    static final void toSQLDDLTypeDeclaration0(Context<?> ctx, DataType<?> type) {
        DataType<?> elementType = type.getArrayBaseDataType();
        if (type.identity()) {
            switch (ctx.family()) {
                case ACCESS: {
                    ctx.visit(Keywords.K_AUTOINCREMENT);
                    return;
                }
                case INFORMIX: {
                    ctx.visit(Keywords.K_SERIAL);
                    return;
                }
                case VERTICA: {
                    ctx.visit(Keywords.K_IDENTITY).sql("(1, 1)");
                    return;
                }
                case COCKROACHDB: {
                    if (type.getType() == Byte.class) {
                        ctx.visit(SQLDataType.SMALLINT).sql(' ').visit(Keywords.K_DEFAULT).sql(" (unique_rowid() % 2 ^ 7)");
                    } else if (type.getType() == Short.class) {
                        ctx.visit(SQLDataType.SMALLINT).sql(' ').visit(Keywords.K_DEFAULT).sql(" (unique_rowid() % 2 ^ 15)");
                    } else if (type.getType() == Integer.class) {
                        ctx.visit(SQLDataType.INTEGER).sql(' ').visit(Keywords.K_DEFAULT).sql(" (unique_rowid() % 2 ^ 31)");
                    } else {
                        ctx.visit(type.getType() == Long.class ? Keywords.K_SERIAL8 : Keywords.K_SERIAL4);
                    }
                    return;
                }
                case AURORA_POSTGRES: 
                case POSTGRES: {
                    switch (ctx.dialect()) {
                        case AURORA_POSTGRES: 
                        case POSTGRES_9_3: 
                        case POSTGRES_9_4: 
                        case POSTGRES_9_5: {
                            ctx.visit(type.getType() == Long.class ? Keywords.K_SERIAL8 : Keywords.K_SERIAL4);
                            return;
                        }
                    }
                }
            }
        }
        if (EnumType.class.isAssignableFrom(type.getType())) {
            DataType<EnumType> enumType = type;
            switch (ctx.family()) {
                case MARIADB: 
                case H2: 
                case AURORA_MYSQL: 
                case MEMSQL: 
                case MYSQL: {
                    ctx.visit(Keywords.K_ENUM).sql('(');
                    String separator = "";
                    for (EnumType e : Tools.enumConstants(enumType)) {
                        ctx.sql(separator).visit(DSL.inline(e.getLiteral()));
                        separator = ", ";
                    }
                    ctx.sql(')');
                    return;
                }
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case YUGABYTEDB: 
                case COCKROACHDB: {
                    if (Tools.storedEnumType(enumType)) break;
                    type = Tools.emulateEnumType(enumType);
                    break;
                }
                default: {
                    type = Tools.emulateEnumType(enumType);
                }
            }
        }
        if (type.getType() == UUID.class && NO_SUPPORT_CAST_TYPE_IN_DDL.contains((Object)ctx.dialect())) {
            Tools.toSQLDDLTypeDeclaration(ctx, SQLDataType.VARCHAR(36));
            return;
        }
        if (type.isTimestamp() && (type.getBinding() instanceof DateAsTimestampBinding || type.getBinding() instanceof LocalDateAsLocalDateTimeBinding)) {
            type = SQLDataType.DATE;
        }
        String typeName = type.getTypeName(ctx.configuration());
        if (type.isTimestamp() && ctx.family() == SQLDialect.INFORMIX) {
            typeName = type.getCastTypeName(ctx.configuration());
        }
        if (type.hasLength() || elementType.hasLength()) {
            if (type.isBinary() && NO_SUPPORT_BINARY_TYPE_LENGTH.contains((Object)ctx.dialect())) {
                ctx.sql(typeName);
            } else if (type.isString() && NO_SUPPORT_STRING_LENGTH.contains((Object)ctx.dialect())) {
                ctx.sql(typeName);
            } else if (type.length() > 0) {
                ctx.sql(typeName).sql('(').sql(type.length()).sql(')');
            } else if (NO_SUPPORT_CAST_TYPE_IN_DDL.contains((Object)ctx.dialect())) {
                if (type.isBinary()) {
                    ctx.sql(SQLDataType.BLOB.getTypeName(ctx.configuration()));
                } else {
                    ctx.sql(SQLDataType.CLOB.getTypeName(ctx.configuration()));
                }
            } else {
                String castTypeName = type.getCastTypeName(ctx.configuration());
                if (!typeName.equals(castTypeName)) {
                    ctx.sql(castTypeName);
                } else {
                    ctx.sql(typeName);
                }
            }
        } else if (!(!type.hasPrecision() || type.precision() <= 0 || type.isTimestamp() && NO_SUPPORT_TIMESTAMP_PRECISION.contains((Object)ctx.dialect()))) {
            if (NO_SUPPORT_CAST_TYPE_IN_DDL.contains((Object)ctx.dialect())) {
                if (type.hasScale()) {
                    ctx.sql(typeName).sql('(').sql(type.precision()).sql(", ").sql(type.scale()).sql(')');
                } else {
                    ctx.sql(typeName).sql('(').sql(type.precision()).sql(')');
                }
            } else if (NO_SUPPORT_NUMERIC_PRECISION.contains((Object)ctx.dialect())) {
                ctx.sql(typeName);
            } else {
                ctx.sql(type.getCastTypeName(ctx.configuration()));
            }
        } else if (type.identity() && ctx.family() == SQLDialect.SQLITE && type.isNumeric()) {
            ctx.sql("integer");
        } else if (type.getType() == Byte.class && ctx.family() == SQLDialect.SQLSERVER) {
            ctx.sql("tinyint");
        } else if (type.isInteger() && NO_SUPPORT_INTEGER_TYPES.contains((Object)ctx.dialect())) {
            ctx.sql(type.getCastTypeName(ctx.configuration()));
        } else if (type.isLob() && REQUIRE_CAST_TYPE_FOR_MAX_LENGTH.contains((Object)ctx.dialect())) {
            ctx.sql(type.getCastTypeName(ctx.configuration()));
        } else if (type.getType() == Object.class && !(type instanceof BuiltInDataType)) {
            ctx.visit(type.getQualifiedName());
        } else {
            ctx.sql(typeName);
        }
        if (type.characterSet() != null && ctx.configuration().data("org.jooq.ddl.ignore-storage-clauses") == null) {
            ctx.sql(' ').visit(Keywords.K_CHARACTER_SET).sql(' ').visit(type.characterSet());
        }
        if (type.collation() != null && ctx.configuration().data("org.jooq.ddl.ignore-storage-clauses") == null) {
            ctx.sql(' ').visit(Keywords.K_COLLATE).sql(' ').visit(type.collation());
        }
    }

    static final boolean storedEnumType(DataType<EnumType> enumType) {
        return Tools.enumConstants(enumType)[0].getSchema() != null;
    }

    private static final EnumType[] enumConstants(DataType<? extends EnumType> type) {
        EnumType[] enums = type.getType().getEnumConstants();
        if (enums == null) {
            throw new DataTypeException("EnumType must be a Java enum");
        }
        return enums;
    }

    static final DataType<String> emulateEnumType(DataType<? extends EnumType> type) {
        return Tools.emulateEnumType(type, Tools.enumConstants(type));
    }

    private static final DataType<String> emulateEnumType(DataType<? extends EnumType> type, EnumType[] enums) {
        int length = 0;
        for (EnumType e : enums) {
            length = Math.max(length, e.getLiteral().length());
        }
        return SQLDataType.VARCHAR(length).nullability(type.nullability()).defaultValue(type.defaultValue());
    }

    static final <C extends Context<? extends C>> C prependInline(C ctx, String prepend, Field<?> inline2, String append) {
        if (inline2 instanceof Param) {
            Param p = (Param)inline2;
            return ctx.visit(DSL.inline(prepend + String.valueOf(p.getValue()) + append));
        }
        return ctx.visit(DSL.inline(prepend).concat(inline2).concat(DSL.inline(append)), ParamType.INLINED);
    }

    static final <C extends Context<? extends C>> C prependSQL(C ctx, Query ... queries) {
        return Tools.preOrAppendSQL(SimpleDataKey.DATA_PREPEND_SQL, ctx, queries);
    }

    static final <C extends Context<? extends C>> C appendSQL(C ctx, Query ... queries) {
        return Tools.preOrAppendSQL(SimpleDataKey.DATA_APPEND_SQL, ctx, queries);
    }

    private static final <C extends Context<? extends C>> C preOrAppendSQL(SimpleDataKey key, C ctx, Query ... queries) {
        ctx.data().compute(key, (k, v) -> {
            String sql = ctx.dsl().renderInlined(ctx.dsl().queries(queries));
            if (v == null) {
                return sql;
            }
            return String.valueOf(v) + sql;
        });
        return ctx;
    }

    static final <T> Supplier<T> blocking(Supplier<T> supplier) {
        return Tools.blocking(supplier, false);
    }

    static final <T> Supplier<T> blocking(final Supplier<T> supplier, boolean threadLocal) {
        return threadLocal ? supplier : new Supplier<T>(){
            volatile T asyncResult;

            @Override
            public T get() {
                try {
                    ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker(){

                        @Override
                        public boolean block() {
                            asyncResult = supplier.get();
                            return true;
                        }

                        @Override
                        public boolean isReleasable() {
                            return asyncResult != null;
                        }
                    });
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return this.asyncResult;
            }
        };
    }

    static final <E extends EnumType> E[] enums(Class<? extends E> type) {
        if (Enum.class.isAssignableFrom(type)) {
            return (EnumType[])type.getEnumConstants();
        }
        try {
            Class<?> companionClass2 = Thread.currentThread().getContextClassLoader().loadClass(type.getName() + "$");
            java.lang.reflect.Field module = companionClass2.getField("MODULE$");
            Object companion = module.get(companionClass2);
            return (EnumType[])companionClass2.getMethod("values", new Class[0]).invoke(companion, new Object[0]);
        }
        catch (Exception e) {
            throw new MappingException("Error while looking up Scala enum", e);
        }
    }

    static final boolean isTime(Class<?> t2) {
        return t2 == Time.class || t2 == LocalTime.class;
    }

    static final boolean isTimestamp(Class<?> t2) {
        return t2 == Timestamp.class || t2 == LocalDateTime.class;
    }

    static final boolean isDate(Class<?> t2) {
        return t2 == Date.class || t2 == LocalDate.class;
    }

    static final boolean hasAmbiguousNames(Collection<? extends Field<?>> fields) {
        if (fields == null) {
            return false;
        }
        HashSet names = new HashSet();
        return Tools.anyMatch(fields, (? super T f) -> !names.add(f.getName()));
    }

    static final SelectFieldOrAsterisk qualify(Table<?> table, SelectFieldOrAsterisk field2) {
        if (field2 instanceof Field) {
            Field f = (Field)field2;
            return Tools.qualify(table, f);
        }
        if (field2 instanceof Asterisk) {
            return table.asterisk();
        }
        if (field2 instanceof QualifiedAsterisk) {
            return table.asterisk();
        }
        throw new UnsupportedOperationException("Unsupported field : " + String.valueOf(field2));
    }

    static final <T> Field<T> qualify(Table<?> table, Field<T> field2) {
        Field<T> result = table.field(field2);
        if (result != null) {
            return result;
        }
        Name[] part = table.getQualifiedName().parts();
        Name[] name = new Name[part.length + 1];
        System.arraycopy(part, 0, name, 0, part.length);
        name[part.length] = field2.getUnqualifiedName();
        return DSL.field(DSL.name(name), field2.getDataType());
    }

    static final <T> Field<T> field(OrderField<T> orderField) {
        if (orderField instanceof Field) {
            Field f = (Field)orderField;
            return f;
        }
        return ((SortField)orderField).$field();
    }

    static final Field<?>[] fields(OrderField<?>[] orderFields) {
        return Tools.map(orderFields, (? super T f) -> Tools.field(f), Field[]::new);
    }

    static final List<Field<?>> fields(Collection<? extends OrderField<?>> orderFields) {
        return Tools.map(orderFields, (? super T f) -> Tools.field(f));
    }

    static final <T> Field<T> unalias(Field<T> field2) {
        Field<T> result = Tools.aliased(field2);
        return result != null ? result : field2;
    }

    static final <T> Field<T> unaliasTable(Field<T> field2) {
        TableField tf;
        Table t2;
        if (field2 instanceof TableField && (t2 = Tools.aliased((tf = (TableField)field2).getTable())) != null) {
            return t2.field(field2.getName());
        }
        return field2;
    }

    static final <T> Field<T> aliased(Field<T> field2) {
        if (field2 instanceof FieldAlias) {
            FieldAlias f = (FieldAlias)field2;
            return f.getAliasedField();
        }
        return null;
    }

    static final <R extends Record> Table<R> unalias(Table<R> table) {
        Table<R> result = Tools.aliased(table);
        return result != null ? result : table;
    }

    static final TableElement uncollate(TableElement field2) {
        if (field2 instanceof QOM.Collated) {
            QOM.Collated c = (QOM.Collated)field2;
            return Tools.uncollate(c.$field());
        }
        return field2;
    }

    static final boolean isScalarSubquery(Field<?> field2) {
        return Tools.uncoerce(field2) instanceof ScalarSubquery;
    }

    static final Field<?> uncoerce(Field<?> field2) {
        Field<?> field3;
        if (field2 instanceof Coerce) {
            Coerce f = (Coerce)field2;
            field3 = f.field;
        } else {
            field3 = field2;
        }
        return field3;
    }

    static final <R extends Record> Table<R> unwrap(Table<R> table) {
        return Tools.unwrap(table, true);
    }

    static final <R extends Record> Table<R> unwrap(Table<R> table, boolean unalias) {
        Table<R> r = table;
        if (table instanceof AbstractDelegatingTable) {
            AbstractDelegatingTable t2 = (AbstractDelegatingTable)table;
            return Tools.unwrap(t2.delegate);
        }
        if (unalias && (r = Tools.unalias(table)) != table) {
            return Tools.unwrap(r);
        }
        return r;
    }

    static final <R extends Record> Table<R> aliased(Table<R> table) {
        if (table instanceof TableImpl) {
            TableImpl t2 = (TableImpl)table;
            return t2.getAliasedTable();
        }
        if (table instanceof TableAlias) {
            TableAlias t3 = (TableAlias)table;
            return t3.getAliasedTable();
        }
        return null;
    }

    static final <R extends Record> Alias<Table<R>> alias(Table<R> table) {
        if (table instanceof TableImpl) {
            TableImpl t2 = (TableImpl)table;
            return t2.alias;
        }
        if (table instanceof TableAlias) {
            TableAlias t3 = (TableAlias)table;
            return t3.alias;
        }
        return null;
    }

    static final boolean toplevel(Map<Object, Object> data, SimpleDataKey key) {
        Integer updateCounts = (Integer)data.get(key);
        if (updateCounts == null) {
            throw new IllegalStateException();
        }
        return updateCounts == 1;
    }

    static final void increment(Map<Object, Object> data, SimpleDataKey key, Runnable runnable2) {
        Tools.increment(data, key);
        runnable2.run();
        Tools.decrement(data, key);
    }

    static final boolean increment(Map<Object, Object> data, SimpleDataKey key) {
        boolean result = true;
        Integer updateCounts = (Integer)data.get(key);
        if (updateCounts == null) {
            updateCounts = 0;
        } else {
            result = false;
        }
        data.put(key, updateCounts + 1);
        return result;
    }

    static final boolean decrement(Map<Object, Object> data, SimpleDataKey key) {
        boolean result = false;
        Integer updateCounts = (Integer)data.get(key);
        if (updateCounts == null || updateCounts == 0) {
            throw new IllegalStateException("Unmatching increment / decrement on key: " + String.valueOf(key));
        }
        if (updateCounts == 1) {
            result = true;
        }
        data.put(key, updateCounts - 1);
        return result;
    }

    static final Field<?> tableField(Table<?> table, Object field2) {
        if (field2 instanceof Field) {
            Field f = (Field)field2;
            return f;
        }
        if (field2 instanceof Name) {
            Name n = (Name)field2;
            if (table.fieldsRow().size() == 0) {
                return DSL.field(table.getQualifiedName().append(n.unqualifiedName()));
            }
            return table.field(n);
        }
        if (field2 instanceof String) {
            String s2 = (String)field2;
            if (table.fieldsRow().size() == 0) {
                return DSL.field(table.getQualifiedName().append(s2));
            }
            return table.field(s2);
        }
        throw new IllegalArgumentException("Field type not supported: " + String.valueOf(field2));
    }

    static final String convertBytesToHex(byte[] value, int offset, int len) {
        len = Math.min(value.length - offset, len);
        char[] buff = new char[2 * len];
        char[] hex = HEX_DIGITS;
        for (int i = 0; i < len; ++i) {
            int c = value[i + offset] & 0xFF;
            buff[i + i] = hex[c >> 4];
            buff[i + i + 1] = hex[c & 0xF];
        }
        return new String(buff);
    }

    static final String convertBytesToHex(byte[] value) {
        return Tools.convertBytesToHex(value, 0, value.length);
    }

    static final byte[] convertHexToBytes(String value, int offset, int len) {
        len = Math.min(value.length() / 2 - offset, len);
        byte[] buff = new byte[len];
        byte[] lookup = HEX_LOOKUP;
        int max = lookup.length;
        for (int i = 0; i < len; ++i) {
            int pos = (i + offset) * 2;
            char c1 = value.charAt(pos);
            char c2 = value.charAt(pos + 1);
            byte v1 = c1 < max ? lookup[c1] : (byte)0;
            byte v2 = c2 < max ? lookup[c2] : (byte)0;
            buff[i] = (byte)((v1 << 4) + v2);
        }
        return buff;
    }

    static final byte[] convertHexToBytes(String value) {
        return Tools.convertHexToBytes(value, 0, value.length());
    }

    static final boolean isNotEmpty(Collection<?> collection) {
        return !Tools.isEmpty(collection);
    }

    static final boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    static final boolean isNotEmpty(Iterable<?> it) {
        return !Tools.isEmpty(it);
    }

    static final boolean isEmpty(Iterable<?> it) {
        if (it == null) {
            return true;
        }
        if (it instanceof Collection) {
            Collection c = (Collection)it;
            return Tools.isEmpty(c);
        }
        Iterator<?> i = it.iterator();
        return !i.hasNext();
    }

    static final boolean isNotEmpty(Object[] array) {
        return !Tools.isEmpty(array);
    }

    static final boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    static final boolean nonReplacingEmbeddable(Field<?> field2) {
        return field2 instanceof EmbeddableTableField && !((EmbeddableTableField)field2).replacesFields;
    }

    static final Class<? extends AbstractRecord> embeddedRecordType(Field<?> field2) {
        Class<Object> clazz;
        if (field2 instanceof EmbeddableTableField) {
            EmbeddableTableField e = (EmbeddableTableField)field2;
            clazz = e.recordType;
        } else {
            clazz = field2 instanceof Val && ((Val)field2).value instanceof EmbeddableRecord ? ((AbstractRecord)((Val)field2).value).getClass() : (field2.getDataType().isEmbeddable() ? field2.getType() : null);
        }
        return clazz;
    }

    static final Field<?>[] embeddedFields(Field<?> field2) {
        Object object;
        if (field2 instanceof EmbeddableTableField) {
            EmbeddableTableField e = (EmbeddableTableField)field2;
            object = e.fields;
        } else if (field2 instanceof Val && ((Val)field2).value instanceof EmbeddableRecord) {
            object = ((EmbeddableRecord)((Val)field2).value).valuesRow().fields();
        } else if (field2 instanceof ScalarSubquery) {
            ScalarSubquery s2 = (ScalarSubquery)field2;
            object = Tools.embeddedFields(s2);
        } else {
            object = field2.getDataType().isEmbeddable() ? Tools.newInstance(field2.getType()).valuesRow().fields() : null;
        }
        return object;
    }

    static final Row embeddedFieldsRow(Row row) {
        if (Tools.hasEmbeddedFields(row.fields())) {
            return DSL.row(Tools.map(Tools.flattenCollection(Arrays.asList(row.fields())), (? super T f) -> f));
        }
        return row;
    }

    static final Field<?>[] embeddedFields(ScalarSubquery<?> field2) {
        List<Field<?>> select = field2.query.getSelect();
        List<Field<?>> result = Tools.collect(Tools.flattenCollection(select));
        Name tableName = DSL.name("t");
        Name[] fieldNames = Tools.fieldNames(result.size());
        SelectField t2 = new AliasedSelect(field2.query, true, true, false, fieldNames).as("t");
        for (int i = 0; i < result.size(); ++i) {
            result.set(i, DSL.field(DSL.select(DSL.field(tableName.append(fieldNames[i]), result.get(i).getDataType())).from((TableLike<?>)((Object)t2))));
        }
        return result.toArray(EMPTY_FIELD);
    }

    private static final EmbeddableRecord<?> newInstance(Class<? extends EmbeddableRecord<?>> type) {
        try {
            return type.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new MappingException("Cannot create EmbeddableRecord type", e);
        }
    }

    static final boolean hasEmbeddedFields(Field<?>[] fields) {
        return Tools.anyMatch(fields, (? super T f) -> f.getDataType().isEmbeddable());
    }

    static final boolean hasEmbeddedFields(Iterable<? extends Field<?>> fields) {
        return Tools.anyMatch(fields, (? super T f) -> f.getDataType().isEmbeddable());
    }

    static final <E> List<E> collect(Iterable<E> iterable) {
        if (iterable instanceof List) {
            List l = (List)iterable;
            return l;
        }
        ArrayList<E> result = new ArrayList<E>();
        for (E e : iterable) {
            result.add(e);
        }
        return result;
    }

    static final <E> Iterator<E> filter(Iterator<E> iterator2, Predicate<? super E> predicate) {
        return Tools.filter(iterator2, (? super E e, int i) -> predicate.test((Object)e));
    }

    static final <E> Iterator<E> filter(final Iterator<E> iterator2, final ObjIntPredicate<? super E> predicate) {
        return new Iterator<E>(){
            boolean uptodate;
            E next;
            int index;

            private void move() {
                if (!this.uptodate) {
                    this.uptodate = true;
                    while (iterator2.hasNext()) {
                        this.next = iterator2.next();
                        if (!predicate.test(this.next, this.index++)) continue;
                        return;
                    }
                    this.uptodate = false;
                }
            }

            @Override
            public boolean hasNext() {
                this.move();
                return this.uptodate;
            }

            @Override
            public E next() {
                this.move();
                if (!this.uptodate) {
                    throw new NoSuchElementException();
                }
                this.uptodate = false;
                return this.next;
            }
        };
    }

    static final <E> Iterable<E> filter(Iterable<E> iterable, Predicate<? super E> predicate) {
        return () -> Tools.filter(iterable.iterator(), predicate);
    }

    static final <E> Iterable<E> filter(Iterable<E> iterable, ObjIntPredicate<? super E> predicate) {
        return () -> Tools.filter(iterable.iterator(), predicate);
    }

    static final <E> Iterable<E> filter(E[] array, Predicate<? super E> predicate) {
        return Tools.filter(Arrays.asList(array), predicate);
    }

    static final <E> Iterable<E> filter(E[] array, ObjIntPredicate<? super E> predicate) {
        return Tools.filter(Arrays.asList(array), predicate);
    }

    static final Iterable<Field<?>> flattenFieldOrRow(FieldOrRow fr) {
        if (fr instanceof Field) {
            Field f = (Field)fr;
            return Tools.flatten(f);
        }
        return Arrays.asList(((Row)fr).fields());
    }

    static final <C extends Collection<Field<?>>> C flattenFieldOrRows(Collection<? extends FieldOrRow> frs, C c) {
        for (FieldOrRow fieldOrRow : frs) {
            for (Field<?> f : Tools.flattenFieldOrRow(fieldOrRow)) {
                c.add(f);
            }
        }
        return c;
    }

    static final <E extends Field<?>> Iterable<E> flatten(E field2) {
        FlatteningIterator<Field> it1 = Collections.singletonList(field2).iterator();
        FlatteningIterator<Field> it2 = field2.getDataType().isEmbeddable() ? new FlatteningIterator<Field>(it1, (e, duplicates) -> Arrays.asList(Tools.embeddedFields(field2))) : it1;
        return () -> it2;
    }

    static final Iterable<Field<?>> flattenCollection(Iterable<? extends Field<?>> iterable) {
        return Tools.flattenCollection(iterable, false, false);
    }

    static final Iterable<Field<?>> flattenCollection(Iterable<? extends Field<?>> iterable, boolean removeDuplicates, boolean flattenRowFields) {
        return () -> new FlatteningIterator<Field>(iterable.iterator(), (e, duplicates) -> {
            if (removeDuplicates && e instanceof EmbeddableTableField) {
                ArrayList result = new ArrayList();
                for (TableField field2 : ((EmbeddableTableField)e).fields) {
                    if (!duplicates.test(field2)) continue;
                    result.add(field2);
                }
                return result;
            }
            if (flattenRowFields && e instanceof AbstractRowAsField) {
                AbstractRowAsField r = (AbstractRowAsField)e;
                ArrayList result = new ArrayList();
                for (Field<?> field3 : Tools.flattenCollection(Arrays.asList(r.fields0().fields()), removeDuplicates, flattenRowFields)) {
                    if (!duplicates.test(field3)) continue;
                    result.add(field3);
                }
                return result;
            }
            return Tools.flatten(e);
        });
    }

    static final Iterable<Map.Entry<FieldOrRow, FieldOrRowOrSelect>> flattenEntrySet(Iterable<Map.Entry<FieldOrRow, FieldOrRowOrSelect>> iterable, boolean removeDuplicates) {
        return () -> new FlatteningIterator<Map.Entry>(iterable.iterator(), (e, duplicates) -> {
            Object patt246654$temp = e.getKey();
            if (patt246654$temp instanceof EmbeddableTableField) {
                EmbeddableTableField key = (EmbeddableTableField)patt246654$temp;
                ArrayList result = new ArrayList();
                Field<?>[] keys2 = Tools.embeddedFields(key);
                Field<?>[] values2 = Tools.embeddedFields((Field)e.getValue());
                for (int i = 0; i < keys2.length; ++i) {
                    if (removeDuplicates && !duplicates.test(keys2[i])) continue;
                    result.add(new AbstractMap.SimpleImmutableEntry(keys2[i], values2[i]));
                }
                return result;
            }
            return null;
        });
    }

    static final <T> Set<T> lazy(Set<T> set) {
        return set == null ? new HashSet() : set;
    }

    static final <T> List<T> lazy(List<T> list) {
        return list == null ? new ArrayList() : list;
    }

    static final <T> List<T> lazy(List<T> list, int size) {
        List<T> result = Tools.lazy(list);
        if (result.size() < size) {
            result.addAll(Collections.nCopies(size - result.size(), null));
        }
        return result;
    }

    static final <T> Supplier<T> cached(final Supplier<T> s2) {
        return new Supplier<T>(){
            transient T cached;

            @Override
            public T get() {
                if (this.cached == null) {
                    this.cached = s2.get();
                }
                return this.cached;
            }
        };
    }

    static final boolean anyQuoted(Settings settings, Name ... names) {
        RenderQuotedNames renderQuotedNames = SettingsTools.getRenderQuotedNames(settings);
        switch (renderQuotedNames) {
            case ALWAYS: {
                return true;
            }
            case NEVER: {
                return false;
            }
            case EXPLICIT_DEFAULT_QUOTED: 
            case EXPLICIT_DEFAULT_UNQUOTED: {
                block9: for (Name name : names) {
                    Name n = name.unqualifiedName();
                    switch (n.quoted()) {
                        case QUOTED: {
                            return true;
                        }
                        case DEFAULT: {
                            if (renderQuotedNames != RenderQuotedNames.EXPLICIT_DEFAULT_QUOTED) continue block9;
                            return true;
                        }
                    }
                }
                break;
            }
        }
        return false;
    }

    static final String asString(Name name) {
        if (!name.qualified()) {
            return name.first();
        }
        StringBuilder sb = new StringBuilder();
        Name[] parts = name.parts();
        for (int i = 0; i < parts.length; ++i) {
            sb.append(parts[i].first());
            if (i >= parts.length - 1) continue;
            sb.append('.');
        }
        return sb.toString();
    }

    static final String normaliseNameCase(Configuration configuration, String name, boolean quoted, Locale locale) {
        switch (Tools.parseNameCase(configuration)) {
            case LOWER_IF_UNQUOTED: {
                if (quoted) {
                    return name;
                }
            }
            case LOWER: {
                return name.toLowerCase(locale);
            }
            case UPPER_IF_UNQUOTED: {
                if (quoted) {
                    return name;
                }
            }
            case UPPER: {
                return name.toUpperCase(locale);
            }
        }
        return name;
    }

    private static final ParseNameCase parseNameCase(Configuration configuration) {
        ParseNameCase result = StringUtils.defaultIfNull(configuration.settings().getParseNameCase(), ParseNameCase.DEFAULT);
        if (result == ParseNameCase.DEFAULT) {
            switch (StringUtils.defaultIfNull(configuration.settings().getParseDialect(), configuration.family()).family()) {
                case REDSHIFT: {
                    return ParseNameCase.LOWER;
                }
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case YUGABYTEDB: 
                case COCKROACHDB: 
                case INFORMIX: 
                case VERTICA: {
                    return ParseNameCase.LOWER_IF_UNQUOTED;
                }
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case SYBASE: 
                case MARIADB: 
                case ASE: 
                case SQLITE: 
                case AURORA_MYSQL: 
                case MEMSQL: 
                case MYSQL: {
                    return ParseNameCase.AS_IS;
                }
            }
            return ParseNameCase.UPPER_IF_UNQUOTED;
        }
        return result;
    }

    static final NestedCollectionEmulation emulateMultiset(Configuration configuration) {
        NestedCollectionEmulation result = StringUtils.defaultIfNull(configuration.settings().getEmulateMultiset(), NestedCollectionEmulation.DEFAULT);
        if (result == NestedCollectionEmulation.DEFAULT) {
            switch (configuration.family()) {
                case AURORA_POSTGRES: 
                case POSTGRES: 
                case YUGABYTEDB: 
                case REDSHIFT: 
                case COCKROACHDB: {
                    return NestedCollectionEmulation.JSONB;
                }
                case MARIADB: 
                case SNOWFLAKE: 
                case SQLITE: 
                case H2: 
                case MYSQL: {
                    return NestedCollectionEmulation.JSON;
                }
                case SQLSERVER: {
                    return NestedCollectionEmulation.XML;
                }
                case ORACLE: {
                    if (SQLDialect.ORACLE18C.precedes(configuration.dialect())) {
                        return NestedCollectionEmulation.JSON;
                    }
                    return NestedCollectionEmulation.XML;
                }
                case DB2: 
                case TERADATA: {
                    return NestedCollectionEmulation.XML;
                }
            }
            return NestedCollectionEmulation.NATIVE;
        }
        return result;
    }

    static final DataType<?> dataType(Field<?> field2) {
        return Tools.dataType(SQLDataType.OTHER, field2, false);
    }

    static final <T> DataType<T> dataType(DataType<T> defaultType, Field<?> field2, boolean preferDefault) {
        return field2 == null ? defaultType : (preferDefault && field2.getType() != defaultType.getType() ? defaultType.nullable(field2.getDataType().nullable()) : field2.getDataType());
    }

    static final <T> DataType<T> allNotNull(DataType<T> defaultType) {
        return defaultType.notNull();
    }

    static final <T> DataType<T> allNotNull(DataType<T> defaultType, Field<?> f1) {
        return Tools.dataType(defaultType, f1, true);
    }

    static final <T> DataType<T> allNotNull(DataType<T> defaultType, Field<?> f1, Field<?> f2) {
        DataType<T> result = Tools.dataType(defaultType, f1, true);
        if (result.nullable()) {
            return result;
        }
        if (Tools.dataType(f2).nullable()) {
            return result.null_();
        }
        return result;
    }

    static final <T> DataType<T> allNotNull(DataType<T> defaultType, Field<?> f1, Field<?> f2, Field<?> f3) {
        DataType<T> result = Tools.dataType(defaultType, f1, true);
        if (result.nullable()) {
            return result;
        }
        if (Tools.dataType(f2).nullable()) {
            return result.null_();
        }
        if (Tools.dataType(f3).nullable()) {
            return result.null_();
        }
        return result;
    }

    static final <T> DataType<T> allNotNull(DataType<T> defaultType, Field<?> ... fields) {
        DataType<T> result = Tools.dataType(defaultType, Tools.isEmpty(fields) ? null : fields[0], true);
        if (result.nullable()) {
            return result;
        }
        for (Field<?> field2 : fields) {
            if (!Tools.dataType(field2).nullable()) continue;
            return result.null_();
        }
        return result;
    }

    static final <T> DataType<T> anyNotNull(DataType<T> defaultType, Field<?> f1) {
        return Tools.dataType(defaultType, f1, false);
    }

    static final <T> DataType<T> anyNotNull(DataType<T> defaultType, Field<?> f1, Field<?> f2) {
        DataType<T> result = Tools.dataType(defaultType, f1, false);
        if (!result.nullable()) {
            return result;
        }
        if (!Tools.dataType(f2).nullable()) {
            return result.notNull();
        }
        return result;
    }

    static final <T> DataType<T> anyNotNull(DataType<T> defaultType, Field<?> f1, Field<?> f2, Field<?> f3) {
        DataType<T> result = Tools.dataType(defaultType, f1, false);
        if (!result.nullable()) {
            return result;
        }
        if (!Tools.dataType(f2).nullable()) {
            return result.notNull();
        }
        if (!Tools.dataType(f3).nullable()) {
            return result.notNull();
        }
        return result;
    }

    static final <T> DataType<T> anyNotNull(DataType<T> defaultType, Field<?> ... fields) {
        DataType<T> result = Tools.dataType(defaultType, Tools.isEmpty(fields) ? null : fields[0], false);
        if (!result.nullable()) {
            return result;
        }
        for (Field<?> field2 : fields) {
            if (Tools.dataType(field2).nullable()) continue;
            return result.notNull();
        }
        return result;
    }

    static final <T> DataType<T> nullable(DataType<T> defaultType, Field<?> f1) {
        return Tools.dataType(defaultType, f1, false).null_();
    }

    static final <T> DataType<T> nullable(DataType<T> defaultType, Field<?> f1, Field<?> f2) {
        return Tools.dataType(defaultType, f1, false).null_();
    }

    static final <T> DataType<T> nullable(DataType<T> defaultType, Field<?> f1, Field<?> f2, Field<?> f3) {
        return Tools.dataType(defaultType, f1, false).null_();
    }

    static final <T> DataType<T> nullable(DataType<T> defaultType, Field<?> ... fields) {
        return Tools.dataType(defaultType, Tools.isEmpty(fields) ? null : fields[0], false).null_();
    }

    static final <T> Field<T> nullSafe(Field<T> field2) {
        return field2 == null ? DSL.val(null) : field2;
    }

    static final <T> Field<T> nullSafe(Field<T> field2, DataType<?> type) {
        Field<Object> field3;
        if (field2 == null) {
            field3 = DSL.val(null, type);
        } else if (field2 instanceof Condition) {
            Condition c = (Condition)field2;
            field3 = DSL.field(c);
        } else {
            field3 = Tools.convertVal(field2, type);
        }
        return field3;
    }

    static final Field<?> nullSafeNoConvertVal(Object field2, DataType<?> type) {
        Field<Object> field3;
        if (field2 == null) {
            field3 = DSL.val(null, type);
        } else if (field2 instanceof Condition) {
            Condition c = (Condition)field2;
            field3 = DSL.field(c);
        } else {
            field3 = Tools.field(field2);
        }
        return field3;
    }

    static final <T> Field<T> convertVal(Field<T> field2, DataType<?> type) {
        return Tools.isVal(field2) ? Tools.extractVal(field2).convertTo(type) : field2;
    }

    static final Field<?>[] nullSafe(Field<?> ... fields) {
        if (fields == null) {
            return EMPTY_FIELD;
        }
        Field[] result = new Field[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            result[i] = Tools.nullSafe(fields[i]);
        }
        return result;
    }

    static final Field<?>[] nullSafe(Field<?>[] fields, DataType<?> type) {
        if (fields == null) {
            return EMPTY_FIELD;
        }
        Field[] result = new Field[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            result[i] = Tools.nullSafe(fields[i], type);
        }
        return result;
    }

    static final List<Field<?>> nullSafeList(Field<?> ... fields) {
        if (fields == null) {
            return Arrays.asList(EMPTY_FIELD);
        }
        return Tools.map(fields, (? super T f) -> Tools.nullSafe(f));
    }

    static final List<Field<?>> nullSafeList(Field<?>[] fields, DataType<?> type) {
        if (fields == null) {
            return Arrays.asList(EMPTY_FIELD);
        }
        return Tools.map(fields, (? super T f) -> Tools.nullSafe(f, type));
    }

    static final <T> DataType<T> nullSafeDataType(Field<T> field2) {
        return field2 == null ? SQLDataType.OTHER : field2.getDataType();
    }

    static final <T> DataType<T> nullSafeDataType(Field<?>[] values2) {
        return Tools.isEmpty(values2) ? SQLDataType.OTHER : values2[0].getDataType();
    }

    static final <T> Field<T> nullSafeNotNull(Field<T> field2, DataType<?> type) {
        return Tools.nullableIf(false, Tools.nullSafe(field2, type));
    }

    static final Field<?> nullSafeNoConvertValNotNull(Field<?> field2, DataType<?> type) {
        return Tools.nullableIf(false, Tools.nullSafeNoConvertVal(field2, type));
    }

    static final <T> Field<T> nullableIf(boolean nullable, Field<T> field2) {
        return Tools.isVal(field2) ? Tools.extractVal(field2).convertTo(field2.getDataType().nullable(nullable)) : field2;
    }

    static final boolean containsDeclaredTable(Table<?> in, Table<?> search) {
        return Tools.traverseJoins(in, Boolean.valueOf(false), (? super T r) -> r, Tools.search(search, t2 -> t2));
    }

    static final boolean containsDeclaredTable(Iterable<? extends Table<?>> in, Table<?> search) {
        return Tools.traverseJoins(in, Boolean.valueOf(false), (? super T r) -> r, Tools.search(search, t2 -> t2));
    }

    private static final BiFunction<Boolean, Table<?>, Boolean> search(Table<?> table, Function<? super Table<?>, ? extends Table<?>> f) {
        Table<?> unaliased = f.apply(table);
        return (r, t2) -> r != false || unaliased.equals(f.apply((Table<?>)t2));
    }

    static final boolean containsTable(Table<?> in, Table<?> search, boolean unalias) {
        return Tools.traverseJoins(in, Boolean.valueOf(false), (? super T r) -> r, Tools.search(search, t2 -> Tools.unwrap(t2, unalias)));
    }

    static final boolean containsTable(Iterable<? extends Table<?>> in, Table<?> search, boolean unalias) {
        return Tools.traverseJoins(in, Boolean.valueOf(false), (? super T r) -> r, Tools.search(search, t2 -> Tools.unwrap(t2, unalias)));
    }

    static final boolean containsUnaliasedTable(Table<?> in, Table<?> search) {
        return Tools.traverseJoins(in, Boolean.valueOf(false), (? super T r) -> r, Tools.search(search, Tools::unwrap));
    }

    static final boolean containsUnaliasedTable(Iterable<? extends Table<?>> in, Table<?> search) {
        return Tools.traverseJoins(in, Boolean.valueOf(false), (? super T r) -> r, Tools.search(search, Tools::unwrap));
    }

    static final void traverseJoins(Iterable<? extends Table<?>> i, Consumer<? super Table<?>> consumer) {
        for (Table<?> t2 : i) {
            Tools.traverseJoins(t2, consumer);
        }
    }

    static final void traverseJoins(Table<?> t2, Consumer<? super Table<?>> consumer) {
        Tools.traverseJoins(t2, null, null, (? super T result, ? super Table<?> x) -> {
            consumer.accept((Table<?>)x);
            return result;
        });
    }

    static final <T> T traverseJoins(Iterable<? extends Table<?>> i, T result, Predicate<? super T> abort, BiFunction<? super T, ? super Table<?>, ? extends T> function) {
        for (Table<?> t2 : i) {
            if (abort != null && abort.test(result)) {
                return result;
            }
            result = Tools.traverseJoins(t2, result, abort, function);
        }
        return result;
    }

    static final <T> T traverseJoins(Table<?> t2, T result, Predicate<? super T> abort, BiFunction<? super T, ? super Table<?>, ? extends T> function) {
        return Tools.traverseJoins(t2, result, abort, null, null, null, function);
    }

    static final <T> T traverseJoins(Iterable<? extends Table<?>> i, T result, Predicate<? super T> abort, Predicate<? super JoinTable> recurseLhs, Predicate<? super JoinTable> recurseRhs, BiFunction<? super T, ? super JoinType, ? extends T> joinTypeFunction, BiFunction<? super T, ? super Table<?>, ? extends T> tableFunction) {
        for (Table<?> t2 : i) {
            if (abort != null && abort.test(result)) {
                return result;
            }
            result = Tools.traverseJoins(t2, result, abort, recurseLhs, recurseRhs, joinTypeFunction, tableFunction);
        }
        return result;
    }

    static final <T> T traverseJoins(Table<?> t2, T result, Predicate<? super T> abort, Predicate<? super JoinTable> recurseLhs, Predicate<? super JoinTable> recurseRhs, BiFunction<? super T, ? super JoinType, ? extends T> joinTypeFunction, BiFunction<? super T, ? super Table<?>, ? extends T> tableFunction) {
        if (abort != null && abort.test(result)) {
            return result;
        }
        if (t2 instanceof JoinTable) {
            JoinTable j = (JoinTable)t2;
            if (recurseLhs == null || recurseLhs.test(j)) {
                result = Tools.traverseJoins(j.lhs, result, abort, recurseLhs, recurseRhs, joinTypeFunction, tableFunction);
                if (abort != null && abort.test(result)) {
                    return result;
                }
            }
            if (joinTypeFunction != null) {
                result = joinTypeFunction.apply(result, j.type);
                if (abort != null && abort.test(result)) {
                    return result;
                }
            }
            if (recurseRhs == null || recurseRhs.test(j)) {
                result = Tools.traverseJoins(j.rhs, result, abort, recurseLhs, recurseRhs, joinTypeFunction, tableFunction);
            }
        } else if (tableFunction != null) {
            result = tableFunction.apply(result, t2);
        }
        return result;
    }

    static final String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    static final String decapitalize(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    static final String pascalCase(String snakeCase) {
        return Stream.of(snakeCase.toLowerCase().split("_")).map(Tools::capitalize).collect(Collectors.joining());
    }

    static final String camelCase(String snakeCase) {
        return Tools.decapitalize(Tools.pascalCase(snakeCase));
    }

    static final String characterLiteral(char character) {
        return "'" + ("" + character).replace("\\", "\\\\").replace("'", "\\'") + "'";
    }

    static final String stringLiteral(String string) {
        return "\"" + string.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n\" + \n\"") + "\"";
    }

    static final String replaceAll(String string, Matcher matcher, Function<MatchResult, String> replacer) {
        return matcher.replaceAll(replacer);
    }

    static final int asInt(long value) {
        return Tools.asInt(value, () -> new DataTypeException("Long value too large for int downcast: " + value));
    }

    static final <E extends Exception> int asInt(long value, Supplier<E> e) throws E {
        if (value > Integer.MAX_VALUE) {
            throw (Exception)e.get();
        }
        return (int)value;
    }

    static final <T, E extends Exception> T ignoreNPE(ThrowingSupplier<? extends T, ? extends E> supplier, Supplier<? extends T> ifNPE) throws E {
        try {
            return supplier.get();
        }
        catch (NullPointerException e) {
            return ifNPE.get();
        }
        catch (Exception e) {
            if (ExceptionTools.getCause(e, NullPointerException.class) != null) {
                return ifNPE.get();
            }
            throw e;
        }
    }

    static final <T> DataType<T> removeGenerator(Configuration configuration, DataType<T> dataType) {
        if (dataType.computedOnClientVirtual(configuration)) {
            return dataType.generatedAlwaysAs((Generator)null);
        }
        return dataType;
    }

    static final ConverterContext converterContext(Attachable attachable) {
        return new DefaultConverterContext(Tools.configuration(attachable));
    }

    static final ConverterContext converterContext(Configuration configuration) {
        return new DefaultConverterContext(Tools.configuration(configuration));
    }

    static final <T1, R> Field<R> derivedTable(Context<?> ctx, Field<T1> f1, Function1<? super Field<T1>, ? extends Field<R>> f) {
        return Tools.derivedTableIf(ctx, true, f1, f);
    }

    static final <T1, T2, R> Field<R> derivedTable(Context<?> ctx, Field<T1> f1, Field<T2> f2, Function2<? super Field<T1>, ? super Field<T2>, ? extends Field<R>> f) {
        return Tools.derivedTableIf(ctx, true, f1, f2, f);
    }

    static final <T1, T2, T3, R> Field<R> derivedTable(Context<?> ctx, Field<T1> f1, Field<T2> f2, Field<T3> f3, Function3<? super Field<T1>, ? super Field<T2>, ? super Field<T3>, ? extends Field<R>> f) {
        return Tools.derivedTableIf(ctx, true, f1, f2, f3, f);
    }

    static final <T1, R> Field<R> derivedTableIf(Context<?> ctx, boolean condition, Field<T1> f1, Function1<? super Field<T1>, ? extends Field<R>> f) {
        if (condition && Tools.derivedTableEnabled(ctx) && !Tools.isSimple(ctx, f1)) {
            return DSL.field(DSL.select((SelectField)f.apply(DSL.field(DSL.name("f1"), f1.getDataType()))).from((TableLike<?>)DSL.select(f1.as("f1")).asTable("t")));
        }
        return f.apply(f1);
    }

    static final <T1, T2, R> Field<R> derivedTableIf(Context<?> ctx, boolean condition, Field<T1> f1, Field<T2> f2, Function2<? super Field<T1>, ? super Field<T2>, ? extends Field<R>> f) {
        if (condition && Tools.derivedTableEnabled(ctx) && !Tools.isSimple(ctx, f1) && !Tools.isSimple(ctx, f2)) {
            return DSL.field(DSL.select((SelectField)f.apply(DSL.field(DSL.name("f1"), f1.getDataType()), DSL.field(DSL.name("f2"), f2.getDataType()))).from((TableLike<?>)DSL.select(f1.as("f1"), f2.as("f2")).asTable("t")));
        }
        return f.apply(f1, f2);
    }

    static final <T1, T2, T3, R> Field<R> derivedTableIf(Context<?> ctx, boolean condition, Field<T1> f1, Field<T2> f2, Field<T3> f3, Function3<? super Field<T1>, ? super Field<T2>, ? super Field<T3>, ? extends Field<R>> f) {
        if (condition && Tools.derivedTableEnabled(ctx) && !Tools.isSimple(ctx, f1) && !Tools.isSimple(ctx, f2) && !Tools.isSimple(ctx, f3)) {
            return DSL.field(DSL.select((SelectField)f.apply(DSL.field(DSL.name("f1"), f1.getDataType()), DSL.field(DSL.name("f2"), f2.getDataType()), DSL.field(DSL.name("f3"), f3.getDataType()))).from((TableLike<?>)DSL.select(f1.as("f1"), f2.as("f2"), f3.as("f3")).asTable("t")));
        }
        return f.apply(f1, f2, f3);
    }

    private static boolean derivedTableEnabled(Context<?> ctx) {
        return !Boolean.FALSE.equals(ctx.settings().isRenderVariablesInDerivedTablesForEmulations()) && !DerivedTable.NO_SUPPORT_CORRELATED_DERIVED_TABLE.contains((Object)ctx.dialect());
    }

    static final DataType<?> componentDataType(Object[] array) {
        Object object;
        if (!Tools.isEmpty(array) && (object = array[0]) instanceof Field) {
            Field f = (Field)object;
            return f.getDataType();
        }
        return DSL.getDataType(array.getClass().getComponentType());
    }

    static final Object[] mostSpecificArray(Object[] array) {
        if (Tools.isEmpty(array)) {
            return array;
        }
        Class<?> type = null;
        for (Object o : array) {
            if (o == null) continue;
            if (type == null) {
                type = o.getClass();
                continue;
            }
            if (type == o.getClass()) continue;
            return array;
        }
        if (type == null) {
            return array;
        }
        return Convert.convertArray(array, type);
    }

    static final <R extends Record> QuantifiedSelect<R> quantify(QOM.Quantifier q, Select<R> select) {
        switch (q) {
            case ANY: {
                return DSL.any(select);
            }
            case ALL: {
                return DSL.all(select);
            }
        }
        throw new IllegalArgumentException("Unsupported quantifier: " + String.valueOf((Object)q));
    }

    static {
        maxConsumedExceptions = 256;
        maxConsumedResults = 65536;
        DASH_PATTERN = Pattern.compile("(-+)");
        PIPE_PATTERN = Pattern.compile("(?<=\\|)([^|]+)(?=\\|)");
        PLUS_PATTERN = Pattern.compile("\\+(-+)(?=\\+)");
        WHITESPACE_CHARACTERS = " \t\n\u000b\f\r".toCharArray();
        JDBC_ESCAPE_PREFIXES = new char[][]{"{fn ".toCharArray(), "{d ".toCharArray(), "{t ".toCharArray(), "{ts ".toCharArray()};
        TOKEN_SINGLE_LINE_COMMENT = new char[]{'-', '-'};
        TOKEN_SINGLE_LINE_COMMENT_C = new char[]{'/', '/'};
        TOKEN_HASH = new char[]{'#'};
        TOKEN_MULTI_LINE_COMMENT_OPEN = new char[]{'/', '*'};
        TOKEN_MULTI_LINE_COMMENT_CLOSE = new char[]{'*', '/'};
        TOKEN_APOS = new char[]{'\''};
        TOKEN_ESCAPED_APOS = new char[]{'\'', '\''};
        NON_BIND_VARIABLE_SUFFIXES = new char[][]{{'?'}, {'|'}, {'&'}, {'@'}, {'<'}, {'~'}, {'#'}, {'-'}};
        BIND_VARIABLE_SUFFIXES = new char[][]{{'<', '>'}};
        HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        HEX_LOOKUP = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        REQUIRES_BACKSLASH_ESCAPING = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE);
        NO_SUPPORT_NULL = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2_2_1_214, SQLDialect.HSQLDB);
        NO_SUPPORT_BINARY_TYPE_LENGTH = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);
        NO_SUPPORT_CAST_TYPE_IN_DDL = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
        SUPPORT_NON_BIND_VARIABLE_SUFFIXES = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);
        SUPPORT_POSTGRES_LITERALS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.YUGABYTEDB);
        DEFAULT_BEFORE_NULL = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.ORACLE);
        NO_SUPPORT_TIMESTAMP_PRECISION = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DERBY, SQLDialect.EXASOL);
        DEFAULT_TIMESTAMP_NOT_NULL = SQLDialect.supportedBy(SQLDialect.MARIADB);
        COMPUTED_EXCLUDES_NULL = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.SQLSERVER);
        COMPUTED_NO_TYPE_DECLARATION = SQLDialect.supportedBy(SQLDialect.MEMSQL, SQLDialect.SQLSERVER);
        NO_SUPPORT_STRING_LENGTH = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
        REQUIRE_CAST_TYPE_FOR_MAX_LENGTH = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE);
        NO_SUPPORT_NUMERIC_PRECISION = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
        DEFAULT_NOT_NULL = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SYBASE);
        NO_SUPPORT_INTEGER_TYPES = SQLDialect.supportedBy(SQLDialect.ORACLE, SQLDialect.SNOWFLAKE);
        FIELD_NAME_STRINGS = (String[])IntStream.range(0, 128).mapToObj(Tools::fieldNameString0).toArray(String[]::new);
        FIELD_NAMES = (Name[])IntStream.range(0, 128).mapToObj(i -> DSL.name(FIELD_NAME_STRINGS[i])).toArray(Name[]::new);
        CONFIG = Lazy.of(() -> new DefaultConfiguration());
        CONFIG_UNQUOTED = Lazy.of(() -> {
            DefaultConfiguration c = new DefaultConfiguration();
            c.settings().setRenderQuotedNames(RenderQuotedNames.NEVER);
            return c;
        });
        CTX = Lazy.of(() -> DSL.using(CONFIG.get()));
        NEW_LINES = Pattern.compile("[\\r\\n]+");
        P_PARSE_HTML_ROW = Pattern.compile("<tr>(.*?)</tr>");
        P_PARSE_HTML_COL_HEAD = Pattern.compile("<th>(.*?)</th>");
        P_PARSE_HTML_COL_BODY = Pattern.compile("<td>(.*?)</td>");
        REQUIRE_IDENTITY_AFTER_NULL = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.MEMSQL, SQLDialect.SYBASE, SQLDialect.TERADATA);
        SUPPORT_PG_IDENTITY = SQLDialect.supportedBy(SQLDialect.POSTGRES_10);
        REQUIRE_COMPUTED_AFTER_NULL = SQLDialect.supportedBy(SQLDialect.H2, SQLDialect.HANA, SQLDialect.INGRES, SQLDialect.SYBASE, SQLDialect.TERADATA);
    }

    static enum SimpleDataKey implements DataKey
    {
        DATA_BLOCK_NESTING,
        DATA_RENDERING_DATA_CHANGE_DELTA_TABLE,
        DATA_WINDOW_DEFINITIONS(true, null, 0),
        DATA_DEFAULT_TRANSACTION_PROVIDER_SAVEPOINTS,
        DATA_DEFAULT_TRANSACTION_PROVIDER_CONNECTION,
        DATA_OVERRIDE_ALIASES_IN_ORDER_BY,
        DATA_SELECT_INTO_TABLE,
        DATA_COLLECTED_SEMI_ANTI_JOIN,
        DATA_PREPEND_SQL,
        DATA_APPEND_SQL,
        DATA_SELECT_INTO_VARIABLES,
        DATA_DML_TABLE_AS_CTE,
        DATA_MATERIALIZED_CTE,
        DATA_GENERATOR_EXACTLY_ONCE,
        DATA_DML_TARGET_TABLE,
        DATA_DML_USING_TABLES,
        DATA_ON_DUPLICATE_KEY_WHERE,
        DATA_TOP_LEVEL_CTE,
        DATA_SELECT_ALIASES;

        private final boolean resetInSubqueryScope;
        private final Object resetValue;
        private final int resetThreshold;

        private SimpleDataKey() {
            this(false, null, 0);
        }

        private SimpleDataKey(boolean resetInSubqueryScope, Object resetValue, int resetThreshold) {
            this.resetInSubqueryScope = resetInSubqueryScope;
            this.resetValue = resetValue;
            this.resetThreshold = resetThreshold;
        }

        @Override
        public final boolean resetInSubqueryScope() {
            return this.resetInSubqueryScope;
        }

        @Override
        public final Object resetValue() {
            return this.resetValue;
        }

        @Override
        public final int resetThreshold() {
            return this.resetThreshold;
        }
    }

    static enum JPANamespace {
        JAVAX,
        JAKARTA,
        NONE;

    }

    static final class FlatteningIterator<E>
    implements Iterator<E> {
        private final Iterator<? extends E> delegate;
        private final BiFunction<? super E, Predicate<Object>, ? extends Iterable<E>> flattener;
        private final Predicate<Object> checkDuplicates;
        private Iterator<E> flatten;
        private E next;
        private Set<Object> duplicates;

        FlatteningIterator(Iterator<? extends E> delegate, BiFunction<? super E, Predicate<Object>, ? extends Iterable<E>> flattener) {
            this.delegate = delegate;
            this.flattener = flattener;
            this.checkDuplicates = e -> {
                this.duplicates = Tools.lazy(this.duplicates);
                return this.duplicates.add(e);
            };
        }

        private final void move() {
            if (this.next == null) {
                if (this.flatten != null) {
                    if (this.flatten.hasNext()) {
                        this.next = this.flatten.next();
                        return;
                    }
                    this.flatten = null;
                }
                if (this.delegate.hasNext()) {
                    this.next = this.delegate.next();
                    Iterable<E> flattened = this.flattener.apply(this.next, this.checkDuplicates);
                    if (flattened == null) {
                        return;
                    }
                    this.next = null;
                    this.flatten = flattened.iterator();
                    this.move();
                    return;
                }
            }
        }

        @Override
        public final boolean hasNext() {
            this.move();
            return this.next != null;
        }

        @Override
        public final E next() {
            this.move();
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            E result = this.next;
            this.next = null;
            return result;
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    private static final class SourceMethod {
        final Method method;

        SourceMethod(Method method2) {
            this.method = method2;
        }

        static List<Method> methods(Collection<? extends SourceMethod> methods) {
            return Tools.map(methods, s2 -> s2.method);
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.method == null ? 0 : this.method.getName().hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (obj instanceof SourceMethod) {
                SourceMethod s2 = (SourceMethod)obj;
                Method other = s2.method;
                if (this.method.getName().equals(other.getName())) {
                    Object[] p1 = this.method.getParameterTypes();
                    Object[] p2 = other.getParameterTypes();
                    return Arrays.equals(p1, p2);
                }
            }
            return false;
        }

        public String toString() {
            return this.method.toString();
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static interface DataKey {
        public boolean resetInSubqueryScope();

        public Object resetValue();

        public int resetThreshold();
    }

    static enum BooleanDataKey implements DataKey
    {
        DATA_MANDATORY_WHERE_CLAUSE(true, null, 1),
        DATA_COUNT_BIND_VALUES,
        DATA_FORCE_STATIC_STATEMENT,
        DATA_OMIT_CLAUSE_EVENT_EMISSION,
        DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES,
        DATA_ROW_VALUE_EXPRESSION_PREDICATE_SUBQUERY,
        DATA_PREFER_TOP_OVER_FETCH,
        DATA_EMULATE_BULK_INSERT_RETURNING,
        DATA_DEFAULT_TRANSACTION_PROVIDER_AUTOCOMMIT,
        DATA_UNALIAS_ALIASED_EXPRESSIONS,
        DATA_SELECT_NO_DATA,
        DATA_OMIT_INTO_CLAUSE,
        DATA_RENDER_TRAILING_LIMIT_IF_APPLICABLE,
        DATA_FORCE_LIMIT_WITH_ORDER_BY,
        DATA_LIST_ALREADY_INDENTED,
        DATA_CONSTRAINT_REFERENCE,
        DATA_COLLECT_SEMI_ANTI_JOIN,
        DATA_INSERT_SELECT,
        DATA_INSERT_SELECT_WITHOUT_INSERT_COLUMN_LIST,
        DATA_NESTED_SET_OPERATIONS,
        DATA_AS_REQUIRED(true, null, 0),
        DATA_MULTISET_CONDITION,
        DATA_MULTISET_CONTENT,
        DATA_FORCE_CASE_ELSE_NULL,
        DATA_GROUP_CONCAT_MAX_LEN_SET,
        DATA_LOCK_WAIT_TIMEOUT_SET,
        DATA_PARSE_ON_CONFLICT,
        DATA_STORE_ASSIGNMENT;

        private final boolean resetInSubqueryScope;
        private final Object resetValue;
        private final int resetThreshold;

        private BooleanDataKey() {
            this(false, null, 0);
        }

        private BooleanDataKey(boolean resetInSubqueryScope, Object resetValue, int resetThreshold) {
            this.resetInSubqueryScope = resetInSubqueryScope;
            this.resetValue = resetValue;
            this.resetThreshold = resetThreshold;
        }

        @Override
        public final boolean resetInSubqueryScope() {
            return this.resetInSubqueryScope;
        }

        @Override
        public final Object resetValue() {
            return this.resetValue;
        }

        @Override
        public final int resetThreshold() {
            return this.resetThreshold;
        }
    }

    static enum ExtendedDataKey implements DataKey
    {
        DATA_INSERT_ON_DUPLICATE_KEY_UPDATE,
        DATA_FOR_LOCK,
        DATA_TRANSFORM_UNNEEDED_ARITHMETIC_EXPRESSIONS,
        DATA_SCOPE_DECLARATIONS,
        DATA_TOP_LEVEL_BLOCK,
        DATA_SCOPE_LOOP,
        DATA_SCOPE_WITH_TABLE_HINTS(true, null, 0),
        DATA_TOP_SCOPE_ROUTINE_NAME,
        DATA_TOP_SCOPE_TRIGGER_NAME,
        DATA_TRANSFORM_SELECT_INTO_TO_SELECT_ALIASED,
        DATA_TRANSFORM_ROWNUM_TO_LIMIT,
        DATA_WINDOW_FUNCTION,
        DATA_RENDER_TABLE(true, null, 0);

        private final boolean resetInSubqueryScope;
        private final Object resetValue;
        private final int resetThreshold;

        private ExtendedDataKey() {
            this(false, null, 0);
        }

        private ExtendedDataKey(boolean resetInSubqueryScope, Object resetValue, int resetThreshold) {
            this.resetInSubqueryScope = resetInSubqueryScope;
            this.resetValue = resetValue;
            this.resetThreshold = resetThreshold;
        }

        @Override
        public final boolean resetInSubqueryScope() {
            return this.resetInSubqueryScope;
        }

        @Override
        public final Object resetValue() {
            return this.resetValue;
        }

        @Override
        public final int resetThreshold() {
            return this.resetThreshold;
        }
    }

    static final class DataKeyScopeStackPart
    extends AbstractQueryPart
    implements QOM.UEmpty {
        static final DataKeyScopeStackPart INSTANCE = new DataKeyScopeStackPart();

        private DataKeyScopeStackPart() {
        }

        @Override
        public final void accept(Context<?> ctx) {
        }

        @Override
        public boolean equals(Object that) {
            return this == that;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}

