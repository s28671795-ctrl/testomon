/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.jooq.AggregateFunction;
import org.jooq.ArrayRecord;
import org.jooq.AssociativeArrayRecord;
import org.jooq.BindContext;
import org.jooq.Binding;
import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Internal;
import org.jooq.Name;
import org.jooq.Package;
import org.jooq.Param;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.QualifiedRecord;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordQualifier;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.Routine;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Scope;
import org.jooq.Table;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.XMLFormat;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.ThrowExceptions;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.MappingException;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.AssociativeArrayConstant;
import org.jooq.impl.Cast;
import org.jooq.impl.ClobBinding;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultAggregateFunction;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.DefaultBindingGetStatementContext;
import org.jooq.impl.DefaultBindingRegisterContext;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.Function;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedRecordConstant;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ResultsImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;
import org.jooq.tools.reflect.Reflect;

@Internal
public abstract class AbstractRoutine<T>
extends AbstractNamed
implements Routine<T>,
QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES = new Clause[]{Clause.FIELD, Clause.FIELD_FUNCTION};
    @Pro
    private static final Set<SQLDialect> SUPPORT_SYNTHETIC_RETURN_PARAMETER = SQLDialect.supportedBy(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE);
    private static final Set<SQLDialect> REQUIRE_SELECT_FROM = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> REQUIRE_DISAMBIGUATE_OVERLOADS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private final Schema schema;
    @Pro
    private final Package pkg;
    private final List<Parameter<?>> allParameters;
    private final List<Parameter<?>> inParameters;
    private final List<Parameter<?>> outParameters;
    @Pro
    private final List<Parameter<?>> plsqlBooleanParameters;
    private final DataType<T> type;
    private Parameter<T> returnParameter;
    private ResultsImpl results;
    private Boolean isSQLUsable;
    private boolean overloaded;
    private boolean hasUnnamedParameters;
    @Pro
    private boolean hasSyntheticReturnParameter;
    @Pro
    private boolean hasDefaultedParameters;
    @Pro
    private boolean hasBooleanParameters;
    @Pro
    private boolean hasRecordParameters;
    @Pro
    private boolean hasAssociativeArrayParameters;
    @Pro
    private boolean hasRefCursorParameters;
    @Pro
    private boolean pipelined;
    private final Map<Parameter<?>, Field<?>> inValues;
    private final Set<Parameter<?>> inValuesDefaulted;
    private final Set<Parameter<?>> inValuesNonDefaulted;
    private transient Field<T> function;
    private Configuration configuration;
    private final Map<Parameter<?>, Object> outValues;
    private final Map<Parameter<?>, Integer> resultIndexes = new HashMap();

    protected AbstractRoutine(String name, Schema schema) {
        this(name, schema, null, null, null, null);
    }

    protected AbstractRoutine(String name, Schema schema, Package pkg) {
        this(name, schema, pkg, null, null, null);
    }

    protected AbstractRoutine(String name, Schema schema, DataType<T> type) {
        this(name, schema, null, type, null, null);
    }

    protected <X> AbstractRoutine(String name, Schema schema, DataType<X> type, Converter<X, T> converter) {
        this(name, schema, null, type, converter, null);
    }

    protected <X> AbstractRoutine(String name, Schema schema, DataType<X> type, Binding<X, T> binding) {
        this(name, schema, null, type, null, binding);
    }

    protected <X, Y> AbstractRoutine(String name, Schema schema, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding) {
        this(name, schema, null, type, converter, binding);
    }

    protected AbstractRoutine(String name, Schema schema, Package pkg, DataType<T> type) {
        this(name, schema, pkg, type, null, null);
    }

    protected <X> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Converter<X, T> converter) {
        this(name, schema, pkg, type, converter, null);
    }

    protected <X> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Binding<X, T> binding) {
        this(name, schema, pkg, type, null, binding);
    }

    protected <X, Y> AbstractRoutine(String name, Schema schema, Package pkg, DataType<X> type, Converter<Y, T> converter, Binding<X, Y> binding) {
        super(AbstractRoutine.qualify(pkg != null ? pkg : schema, DSL.name(name)), CommentImpl.NO_COMMENT);
        this.schema = schema;
        this.pkg = pkg;
        this.allParameters = new ArrayList();
        this.inParameters = new ArrayList();
        this.outParameters = new ArrayList();
        this.plsqlBooleanParameters = new ArrayList();
        this.results = new ResultsImpl(null);
        this.inValues = new HashMap();
        this.inValuesDefaulted = new HashSet();
        this.inValuesNonDefaulted = new HashSet();
        this.outValues = new HashMap();
        this.type = converter == null && binding == null ? type : type.asConvertedDataType(DefaultBinding.newBinding(converter, type, binding));
    }

    protected final <N extends Number> void setNumber(Parameter<N> parameter, Number value) {
        this.setValue(parameter, (Number)Convert.convert((Object)value, parameter.getType()));
    }

    protected final void setNumber(Parameter<? extends Number> parameter, Field<? extends Number> value) {
        this.setField(parameter, value);
    }

    @Override
    public final <Z> void setValue(Parameter<Z> parameter, Z value) {
        this.set(parameter, value);
    }

    @Override
    public final <Z> void set(Parameter<Z> parameter, Z value) {
        this.setField(parameter, DSL.val(value, parameter.getDataType()));
    }

    protected final void setField(Parameter<?> parameter, Field<?> value) {
        if (value == null) {
            this.setField(parameter, DSL.val(null, parameter.getDataType()));
        } else {
            this.inValues.put(parameter, value);
            this.inValuesDefaulted.remove(parameter);
            this.inValuesNonDefaulted.add(parameter);
        }
    }

    @Override
    public final void attach(Configuration c) {
        this.configuration = c;
    }

    @Override
    public final void detach() {
        this.attach(null);
    }

    @Override
    public final Configuration configuration() {
        return this.configuration;
    }

    @Override
    public final int execute(Configuration c) {
        return Tools.attach(this, c, this::execute);
    }

    @Override
    public final int execute() {
        Configuration config = Tools.configurationOrThrow(this);
        SQLDialect family = config.family();
        this.results.clear();
        this.outValues.clear();
        if (this.isSQLUsable() && REQUIRE_SELECT_FROM.contains((Object)config.dialect())) {
            return this.executeSelectFromPOSTGRES();
        }
        if (family == SQLDialect.SNOWFLAKE) {
            if (this.isSQLUsable()) {
                return this.executeSelect();
            }
            return this.executeCallSnowflake();
        }
        if (this.type == null) {
            return this.executeCallableStatement();
        }
        if (this.isPipelined()) {
            return this.executeSelectFromOracle();
        }
        if (this.hasSyntheticReturnParameter && !SUPPORT_SYNTHETIC_RETURN_PARAMETER.contains((Object)config.dialect())) {
            return this.executeCallableStatement();
        }
        switch (family) {
            case HSQLDB: {
                if (SQLDataType.RESULT.equals(this.type.getSQLDataType())) {
                    return this.executeSelectFromHSQLDB();
                }
            }
            case FIREBIRD: 
            case H2: 
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case VERTICA: {
                return this.executeSelect();
            }
        }
        return this.executeCallableStatement();
    }

    @Pro
    private final int executeSelectFromOracle() {
        DSLContext create = this.create(this.configuration);
        Result result = create.selectFrom(DSL.table(this.asField())).fetch();
        try {
            ArrayRecord array = (ArrayRecord)this.type.getType().newInstance();
            Class target = array.getDataType().getType();
            if (Record.class.isAssignableFrom(target)) {
                for (Record record : result) {
                    array.add(record.into(target));
                }
            } else {
                for (Record record : result) {
                    array.add(record.get(0));
                }
            }
            this.outValues.put(this.returnParameter, array);
        }
        catch (Exception e) {
            throw new MappingException("Cannot convert to " + String.valueOf(this.type.getType()), e);
        }
        return 0;
    }

    private final int executeCallSnowflake() {
        DSLContext create = this.create(this.configuration);
        Result<Record> result = create.fetch("call {0}", this.asField());
        this.outValues.put(this.returnParameter, result.getValue(0, 0));
        return 0;
    }

    private final int executeSelectFromHSQLDB() {
        DSLContext create = this.create(this.configuration);
        Result result = create.selectFrom(DSL.table(this.asField())).fetch();
        this.outValues.put(this.returnParameter, result);
        return 0;
    }

    private final int executeSelectFromPOSTGRES() {
        DSLContext create = this.create(this.configuration);
        ArrayList fields = new ArrayList(1 + this.outParameters.size());
        if (this.returnParameter != null) {
            fields.add(DSL.field(DSL.name(this.getName()), this.returnParameter.getDataType()));
        }
        for (Parameter<?> p : this.outParameters) {
            fields.add(DSL.field(DSL.name(p.getName()), p.getDataType()));
        }
        Result result = fields.size() == 1 && ((Field)fields.get(0)).getDataType().isUDT() ? create.select(DSL.field("row(t.*)", ((Field)fields.get(0)).getDataType())).from("{0} as t", this.asField()).fetch() : (fields.size() == 1 && ((Field)fields.get(0)).getDataType().isRecord() ? create.select(this.asField()).fetch() : create.select(fields).from("{0}", this.asField()).fetch());
        int i = 0;
        if (this.returnParameter != null) {
            this.outValues.put(this.returnParameter, this.returnParameter.getDataType().convert(result.getValue(0, i++)));
        }
        for (Parameter<?> p : this.outParameters) {
            this.outValues.put(p, p.getDataType().convert(result.getValue(0, i++)));
        }
        return 0;
    }

    private final int executeSelect() {
        Field<T> field2 = this.asField();
        this.outValues.put(this.returnParameter, this.create(this.configuration).select(field2).fetchOne(field2));
        return 0;
    }

    private final int executeCallableStatement() {
        DefaultExecuteContext ctx = new DefaultExecuteContext(this.configuration, this);
        ExecuteListener listener = ExecuteListeners.get(ctx);
        try {
            listener.start(ctx);
            listener.renderStart(ctx);
            ctx.sql(this.create(this.configuration).render(this));
            listener.renderEnd(ctx);
            listener.prepareStart(ctx);
            if (ctx.statement() == null) {
                ctx.statement(ctx.connection().prepareCall(ctx.sql()));
            }
            Tools.setFetchSize(ctx, 0);
            listener.prepareEnd(ctx);
            int t2 = SettingsTools.getQueryTimeout(0, ctx.settings());
            if (t2 != 0) {
                ctx.statement().setQueryTimeout(t2);
            }
            listener.bindStart(ctx);
            DSL.using(this.configuration).bindContext(ctx.statement()).visit(this);
            this.registerOutParameters(ctx);
            listener.bindEnd(ctx);
            SQLException e = this.execute0(ctx, listener);
            if (ctx.family() != SQLDialect.FIREBIRD) {
                Tools.consumeResultSets(ctx, listener, this.results, null, e);
            }
            listener.outStart(ctx);
            this.fetchOutParameters(ctx);
            listener.outEnd(ctx);
            int n = 0;
            return n;
        }
        catch (ControlFlowSignal e) {
            throw e;
        }
        catch (RuntimeException e) {
            ctx.exception(e);
            listener.exception(ctx);
            throw ctx.exception();
        }
        catch (SQLException e) {
            ctx.sqlException(e);
            listener.exception(ctx);
            throw ctx.exception();
        }
        finally {
            Tools.safeClose(listener, ctx);
        }
    }

    private final SQLException execute0(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        listener.executeStart(ctx);
        SQLException e = Tools.executeStatementAndGetFirstResultSet(ctx, 0);
        listener.executeEnd(ctx);
        if (e != null) {
            this.results.resultsOrRows().add(new ResultsImpl.ResultOrRowsImpl(Tools.translate(ctx.sql(), e)));
        }
        return e;
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx instanceof RenderContext) {
            this.toSQL0((RenderContext)ctx);
        } else {
            this.bind0((BindContext)ctx);
        }
    }

    final void bind0(BindContext context) {
        List<Parameter<?>> all = this.getParameters0(context.configuration());
        List<Parameter<?>> in = this.getInParameters0(context.configuration());
        if (context.family() == SQLDialect.ORACLE && (this.hasBooleanParameters() || this.hasRecordParameters() || this.hasAssociativeArrayParameters() || this.hasRefCursorParameters())) {
            ArrayList leading = new ArrayList();
            ArrayList others = new ArrayList();
            ArrayList trailing = new ArrayList();
            for (Parameter<Object> parameter : all) {
                if (in.contains(parameter) && this.inValuesDefaulted.contains(parameter)) continue;
                if (this.booleanParameter(parameter) || this.isRecordParameter(parameter) || this.isAssociativeArrayParameter(parameter)) {
                    if (in.contains(parameter)) {
                        leading.add(parameter);
                    }
                    if (!this.resultParameter(context.configuration(), parameter)) continue;
                    trailing.add(parameter);
                    continue;
                }
                if (this.isRefCursorParameter(parameter)) {
                    if (in.contains(parameter)) {
                        leading.add(parameter);
                    }
                    if (!this.resultParameter(context.configuration(), parameter)) continue;
                    trailing.add(parameter);
                    continue;
                }
                others.add(parameter);
            }
            for (Parameter<Object> parameter : leading) {
                this.bind1(context, parameter, true, false);
            }
            for (Parameter<Object> parameter : others) {
                this.bind1(context, parameter, this.getInValues().get(parameter) != null && !leading.contains(parameter), this.resultParameter(context.configuration(), parameter));
            }
            for (Parameter<Object> parameter : trailing) {
                this.bind1(context, parameter, false, true);
            }
        } else {
            for (Parameter<?> parameter : all) {
                if (in.contains(parameter) && this.inValuesDefaulted.contains(parameter)) continue;
                this.bind1(context, parameter, this.getInValues().get(parameter) != null, this.resultParameter(context.configuration(), parameter));
            }
        }
    }

    private final void bind1(BindContext context, Parameter<?> parameter, boolean bindAsIn, boolean bindAsOut) {
        int index = context.peekIndex();
        if (bindAsOut) {
            this.resultIndexes.put(parameter, index);
            if (this.isRecordParameter(parameter)) {
                this.skipRecordIndexes(context, Tools.getRecordQualifier(parameter.getDataType()));
            } else if (this.isAssociativeArrayParameter(parameter)) {
                this.skipAssociativeArrayIndexes(context, Tools.newAssociativeArrayRecord(parameter.getDataType()));
            }
        }
        if (bindAsIn) {
            if (this.isRecordParameter(parameter)) {
                QualifiedRecordConstant c = (QualifiedRecordConstant)this.getInValues().get(parameter);
                if (c != null) {
                    this.bindRecordAsIn(context, Tools.getRecordQualifier(parameter.getDataType()), (QualifiedRecord)c.value);
                }
            } else if (this.isAssociativeArrayParameter(parameter)) {
                AssociativeArrayRecord a = (AssociativeArrayRecord)((AssociativeArrayConstant)this.getInValues().get(parameter)).value;
                if (a != null) {
                    context.visit(DSL.val(Tools.dbmsSQLTable(a.getIndexDataType(), a.keySet())));
                    DataType t2 = a.getDataType();
                    Collection values2 = a.values();
                    this.bindRecurse1(context, t2, values2);
                }
            } else if (this.isRefCursorParameter(parameter)) {
                Result<Record> r = this.refCursor(parameter);
                if (r != null) {
                    context.visit(this.refCursorAsXml(context.configuration(), r));
                }
            } else {
                context.visit(this.getInValues().get(parameter));
                if (index == context.peekIndex() && bindAsOut) {
                    context.nextIndex();
                }
            }
        } else if (!this.isRecordParameter(parameter) && !this.isAssociativeArrayParameter(parameter)) {
            context.nextIndex();
        }
    }

    @Pro
    private final void bindRecurse1(BindContext context, DataType<?> t2, Collection<?> values2) {
        if (t2.isUDT()) {
            for (Field<?> f : Tools.getRecordQualifier(t2).fields()) {
                this.bindRecurse1(context, f.getDataType(), values2.stream().map(u -> u == null ? null : ((UDTRecord)u).get(f)).collect(Collectors.toList()));
            }
        } else {
            context.visit(DSL.val(Tools.dbmsSQLTable(t2, values2)));
        }
    }

    final void toSQL0(RenderContext context) {
        this.toSQLDeclare(context);
        this.toSQLBegin(context);
        if (this.getReturnParameter0(context.configuration()) != null) {
            this.toSQLAssign(context);
        }
        this.toSQLCall(context);
        context.sql(" (");
        String separator = "";
        List<Parameter<?>> all = this.getParameters0(context.configuration());
        boolean defaulted = false;
        LinkedHashMap indexes = new LinkedHashMap();
        for (int i = 0; i < all.size(); ++i) {
            Parameter<?> parameter = all.get(i);
            if (parameter.equals(this.getReturnParameter0(context.configuration())) || this.inValuesDefaulted.contains(parameter) && (defaulted |= true)) continue;
            indexes.put(i, parameter);
        }
        boolean indent = false;
        if (this.needsPLSQLBlock() && context.family() == SQLDialect.ORACLE) {
            boolean bl = indent = indexes.size() > 1;
        }
        if (indent) {
            ((RenderContext)context.formatIndentStart()).formatNewLine();
        }
        int i = 0;
        for (Map.Entry entry : indexes.entrySet()) {
            Parameter parameter = (Parameter)entry.getValue();
            int index = (Integer)entry.getKey();
            context.sql(separator);
            if (indent && i++ > 0) {
                context.formatNewLine();
            }
            if (defaulted && context.family() == SQLDialect.POSTGRES) {
                ((RenderContext)context.visit(parameter.getUnqualifiedName())).sql(" := ");
            }
            if (this.getOutParameters0(context.configuration()).contains(parameter)) {
                this.toSQLOutParam(context, parameter, index);
            } else {
                this.toSQLInParam(context, parameter, index, this.getInValues().get(parameter));
            }
            separator = ", ";
        }
        if (indent) {
            ((RenderContext)context.formatIndentEnd()).formatNewLine();
        }
        context.sql(')');
        this.toSQLEnd(context);
    }

    private final void toSQLEnd(RenderContext context) {
        if ((this.isSQLUsable() || context.family() != SQLDialect.POSTGRES) && context.family() != SQLDialect.SNOWFLAKE) {
            if (this.needsPLSQLBlock() && context.family() == SQLDialect.ORACLE) {
                context.sql(';');
                if (this.hasBooleanParameters() || this.hasRecordParameters() || this.hasAssociativeArrayParameters() || this.hasRefCursorParameters()) {
                    List<Parameter<?>> all = this.getParameters0(context.configuration());
                    for (int i = 0; i < all.size(); ++i) {
                        int j = i + 1;
                        Parameter<?> parameter = all.get(i);
                        if (!this.resultParameter(context.configuration(), parameter)) continue;
                        if (this.booleanParameter(parameter)) {
                            ((RenderContext)context.formatSeparator()).sql("? := ");
                            this.toSQLPLSQLBooleanToField(context, c -> c.visit(DSL.field(DSL.name("b" + j))));
                            context.sql(";");
                            continue;
                        }
                        if (this.isRecordParameter(parameter)) {
                            this.toSQLRecordOutBinding(context, Tools.getRecordQualifier(parameter.getDataType()), DSL.name("r" + j));
                            continue;
                        }
                        if (this.isAssociativeArrayParameter(parameter)) {
                            this.toSQLAssociativeArrayOutBinding(context, Tools.newAssociativeArrayRecord(parameter.getDataType()), i, this.getInParameters0(context.configuration()).contains(parameter));
                            continue;
                        }
                        if (!this.isRefCursorParameter(parameter)) continue;
                        ((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).sql("? := ")).visit(DSL.name("c" + (i + 1)))).sql(';');
                    }
                }
                ((RenderContext)((RenderContext)((RenderContext)context.formatIndentEnd()).formatSeparator()).visit(Keywords.K_END)).sql(';');
            } else {
                context.sql(" }");
            }
        }
    }

    private final void toSQLDeclare(RenderContext context) {
        if (context.family() == SQLDialect.ORACLE && (this.hasBooleanParameters() || this.hasRecordParameters() || this.hasAssociativeArrayParameters() || this.hasRefCursorParameters())) {
            Parameter<?> parameter;
            int i;
            ((RenderContext)context.visit(Keywords.K_DECLARE)).formatIndentStart();
            List<Parameter<?>> all = this.getParameters0(context.configuration());
            List<Parameter<?>> in = this.getInParameters0(context.configuration());
            if (this.hasRecordParameters()) {
                for (i = 0; i < all.size(); ++i) {
                    parameter = all.get(i);
                    if (!this.isRowtypeParameter(parameter)) continue;
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(Keywords.K_TYPE)).sql(" rowtype")).sql(i + 1)).sql(' ')).visit(Keywords.K_IS)).sql(' ')).visit(Keywords.K_RECORD)).sqlIndentStart('(');
                    RecordQualifier<?> qualifier = Tools.getRecordQualifier(parameter.getDataType());
                    Field<?>[] fields = qualifier.fields();
                    for (int j = 0; j < fields.length; ++j) {
                        Field<?> f = fields[j];
                        if (j > 0) {
                            ((RenderContext)context.sql(',')).formatSeparator();
                        }
                        ((RenderContext)context.visit(f.getUnqualifiedName())).sql(' ');
                        Tools.toSQLDDLTypeDeclaration(context, f.getDataType());
                    }
                    context.sqlIndentEnd(");");
                }
            }
            for (i = 0; i < all.size(); ++i) {
                parameter = all.get(i);
                if (this.inValuesDefaulted.contains(parameter)) continue;
                if (this.booleanParameter(parameter)) {
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("b" + (i + 1)))).sql(' ')).visit(Keywords.K_BOOLEAN)).sql(';');
                    continue;
                }
                if (this.isRowtypeParameter(parameter)) {
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("r" + (i + 1)))).sql(" rowtype")).sql(i + 1)).sql(';');
                    continue;
                }
                if (this.isRecordParameter(parameter)) {
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("r" + (i + 1)))).sql(' ')).visit(Tools.getRecordQualifier(parameter.getDataType()))).sql(';');
                    continue;
                }
                if (this.isAssociativeArrayParameter(parameter)) {
                    AssociativeArrayRecord<?, ?> record = Tools.newAssociativeArrayRecord(parameter.getDataType());
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("a" + (i + 1)))).sql(' ')).visit(Tools.getMappedAssociativeArrayName((Scope)context, record))).sql(';');
                    ((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("e" + (i + 1)))).sql(' ');
                    Tools.toSQLDDLTypeDeclaration(context, record.getIndexDataType());
                    context.sql(';');
                    ((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("i" + (i + 1)))).sql(" pls_integer := 1;");
                    ((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("k" + (i + 1)))).sql(' ')).visit(Tools.dbmsSQLType(record.getIndexDataType()))).sql(';');
                    for (Flattened f : this.flatten("" + (i + 1), record.getDataType())) {
                        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("v" + f.prefix()))).sql(' ')).visit(Tools.dbmsSQLType(f.type()))).sql(';');
                    }
                    continue;
                }
                if (!this.isRefCursorParameter(parameter)) continue;
                ((RenderContext)((RenderContext)context.formatSeparator()).visit(DSL.name("c" + (i + 1)))).sql(" sys_refcursor;");
            }
            ((RenderContext)context.formatIndentEnd()).formatSeparator();
        }
    }

    @Pro
    private final List<Flattened> flatten(String prefix, DataType<?> dataType) {
        return this.flatten(prefix, dataType, null, new ArrayList<Flattened>());
    }

    @Pro
    private final List<Flattened> flatten(String prefix, DataType<?> dataType, Name path, List<Flattened> result) {
        if (dataType.isRecord()) {
            Field<?>[] fields = Tools.getRecordQualifier(dataType).fields();
            for (int i = 0; i < fields.length; ++i) {
                this.flatten(prefix + (i + 1), fields[i].getDataType(), path == null ? fields[i].getUnqualifiedName() : path.append(fields[i].getUnqualifiedName()), result);
            }
        } else {
            result.add(new Flattened(prefix, path, dataType));
        }
        return result;
    }

    @Pro
    private final void toSQLFieldToPLSQLBoolean(RenderContext context, Consumer<? super Context<?>> field2) {
        this.toSQLFieldToPLSQLBoolean(context, field2, "0", "1");
    }

    @Pro
    private final void toSQLFieldToPLSQLBoolean(RenderContext context, Consumer<? super Context<?>> field2, String f, String t2) {
        ((RenderContext)context.visit(Keywords.K_CASE)).sql(' ');
        field2.accept(context);
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.sql(' ')).visit(Keywords.K_WHEN)).sql(' ')).sql(f)).sql(' ')).visit(Keywords.K_THEN)).sql(' ')).visit(Keywords.K_FALSE)).sql(' ')).visit(Keywords.K_WHEN)).sql(' ')).sql(t2)).sql(' ')).visit(Keywords.K_THEN)).sql(' ')).visit(Keywords.K_TRUE)).sql(' ')).visit(Keywords.K_END);
    }

    @Pro
    private final void toSQLPLSQLBooleanToField(Context<?> context, Consumer<? super Context<?>> field2) {
        this.toSQLPLSQLBooleanToField(context, field2, "0", "1");
    }

    @Pro
    private final void toSQLPLSQLBooleanToField(Context<?> context, Consumer<? super Context<?>> field2, String f, String t2) {
        context.visit(Keywords.K_CASE).sql(' ');
        field2.accept(context);
        context.sql(' ').visit(Keywords.K_WHEN).sql(' ').visit(Keywords.K_FALSE).sql(' ').visit(Keywords.K_THEN).sql(' ').sql(f).sql(' ').visit(Keywords.K_WHEN).sql(' ').visit(Keywords.K_TRUE).sql(' ').visit(Keywords.K_THEN).sql(' ').sql(t2).sql(' ').visit(Keywords.K_END);
    }

    private final void toSQLBegin(RenderContext context) {
        if ((this.isSQLUsable() || context.family() != SQLDialect.POSTGRES) && context.family() != SQLDialect.SNOWFLAKE) {
            if (this.needsPLSQLBlock() && context.family() == SQLDialect.ORACLE) {
                ((RenderContext)((RenderContext)context.visit(Keywords.K_BEGIN)).formatIndentStart()).formatSeparator();
                if (this.hasBooleanParameters() || this.hasRecordParameters() || this.hasAssociativeArrayParameters() || this.hasRefCursorParameters()) {
                    List<Parameter<?>> all = this.getParameters0(context.configuration());
                    List<Parameter<?>> in = this.getInParameters0(context.configuration());
                    for (int i = 0; i < all.size(); ++i) {
                        Parameter<?> parameter = all.get(i);
                        if (parameter.isDefaulted() && this.inValuesDefaulted.contains(parameter) || !in.contains(parameter)) continue;
                        if (this.booleanParameter(parameter)) {
                            ((RenderContext)context.visit(DSL.name("b" + (i + 1)))).sql(" := ");
                            this.toSQLFieldToPLSQLBoolean(context, c -> c.visit(this.getInValues().get(parameter)));
                            context.sql(';');
                            context.formatSeparator();
                            continue;
                        }
                        if (this.isRecordParameter(parameter)) {
                            this.toSQLRecordInBinding(context, Tools.getRecordQualifier(parameter.getDataType()), (QualifiedRecord)((QualifiedRecordConstant)this.getInValue(parameter)).value, DSL.name("r" + (i + 1)));
                            context.formatSeparator();
                            continue;
                        }
                        if (this.isRefCursorParameter(parameter)) {
                            this.toSQLRefCursorInBinding(context, DSL.name("c" + (i + 1)), this.refCursor(parameter));
                            context.formatSeparator();
                            continue;
                        }
                        if (!this.isAssociativeArrayParameter(parameter) || ((AssociativeArrayConstant)this.getInValue(parameter)).value == null) continue;
                        this.toSQLAssociativeArrayInBinding(context, Tools.newAssociativeArrayRecord(parameter.getDataType()), i);
                    }
                }
            } else {
                context.sql("{ ");
            }
        }
    }

    @Pro
    private final Result<Record> refCursor(Parameter<?> parameter) {
        Field<?> field2 = this.getInValues().get(parameter);
        if (field2 instanceof Param) {
            return (Result)((Param)field2).getValue();
        }
        throw new IllegalStateException();
    }

    @Pro
    private final void toSQLRecordInBinding(RenderContext context, RecordQualifier<?> qualifier, QualifiedRecord<?> value, Name path) {
        for (Field<?> field2 : qualifier.fields()) {
            RecordQualifier<?> nestedQualifier;
            if (qualifier instanceof UDT && value != null && !value.changed(field2)) continue;
            Name nestedPath = path.append(field2.getName());
            if (field2.getDataType().isUDT() && !this.isSQLUsable(nestedQualifier = Tools.getRecordQualifier(field2.getDataType()))) {
                this.toSQLRecordInBinding(context, nestedQualifier, value == null ? null : value.get(field2), nestedPath);
                continue;
            }
            if (field2.getDataType().isBoolean()) {
                ((RenderContext)((RenderContext)context.formatSeparator()).visit(nestedPath)).sql(" := ");
                this.toSQLFieldToPLSQLBoolean(context, c -> c.visit(DSL.field("?")));
                context.sql(';');
                continue;
            }
            ((RenderContext)((RenderContext)context.formatSeparator()).visit(nestedPath)).sql(" := ?;");
        }
    }

    @Pro
    private final void toSQLRecordOutBinding(RenderContext context, RecordQualifier<?> qualifier, Name path) {
        for (Field<?> field2 : qualifier.fields()) {
            RecordQualifier<?> nestedQualifier;
            Name nestedPath = path.append(field2.getName());
            if (field2.getDataType().isUDT() && !this.isSQLUsable(nestedQualifier = Tools.getRecordQualifier(field2.getDataType()))) {
                this.toSQLRecordOutBinding(context, nestedQualifier, nestedPath);
                continue;
            }
            if (field2.getDataType().isBoolean()) {
                ((RenderContext)context.formatSeparator()).sql("? := ");
                this.toSQLPLSQLBooleanToField(context, c -> c.visit(DSL.field(nestedPath)));
                context.sql(";");
                continue;
            }
            ((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).sql("? := ")).visit(nestedPath)).sql(';');
        }
    }

    @Pro
    private final void toSQLAssociativeArrayInBinding(RenderContext context, AssociativeArrayRecord<?, ?> qualifier, int p) {
        Name i = DSL.name("i");
        Name a = DSL.name("a" + (p + 1));
        Name k = DSL.name("k" + (p + 1));
        ArrayList<Name> v = new ArrayList<Name>();
        ArrayList<Name> suffix = new ArrayList<Name>();
        for (Flattened f : this.flatten("" + (p + 1), qualifier.getDataType())) {
            v.add(DSL.name("v" + f.prefix()));
            suffix.add(f.path());
        }
        ((RenderContext)((RenderContext)context.formatSeparator()).visit(k)).sql(" := ?;");
        for (Name n : v) {
            ((RenderContext)((RenderContext)context.formatSeparator()).visit(n)).sql(" := ?;");
        }
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(Keywords.K_IF)).sql(' ')).visit(k)).sql('.')).visit(Keywords.K_FIRST)).sql(' ')).visit(Keywords.K_IS_NOT_NULL)).sql(' ')).visit(Keywords.K_THEN)).formatIndentStart()).formatSeparator()).visit(Keywords.K_FOR)).sql(' ')).visit(i)).sql(' ')).visit(Keywords.K_IN)).sql(' ')).visit(k)).sql('.')).visit(Keywords.K_FIRST)).sql(" .. ")).visit(k)).sql('.')).visit(Keywords.K_LAST)).sql(' ')).visit(Keywords.K_LOOP)).formatIndentStart()).formatSeparator()).visit(a)).sql('(')).visit(k)).sql('(')).visit(i)).sql(")) := ");
        DataType t2 = qualifier.getDataType();
        if (t2.isUDT()) {
            this.toSQLAssociativeArrayInBindingRecurse(context, t2, "v" + (p + 1));
        } else {
            this.toSQLAssociativeArrayInBindingScalar(context, t2, c -> c.visit((QueryPart)v.get(0)).sql('(').visit(i).sql(')'));
        }
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.sql(';')).formatIndentEnd()).formatSeparator()).visit(Keywords.K_END_LOOP)).sql(';')).formatIndentEnd()).formatSeparator()).visit(Keywords.K_END_IF)).sql(';')).formatSeparator();
    }

    private final void toSQLAssociativeArrayInBindingScalar(RenderContext context, DataType<?> t2, Consumer<? super Context<?>> consumer) {
        if (t2.isBoolean()) {
            this.toSQLFieldToPLSQLBoolean(context, consumer, "'0'", "'1'");
        } else {
            consumer.accept(context);
        }
    }

    @Pro
    private final void toSQLAssociativeArrayInBindingRecurse(RenderContext context, DataType<?> ut, String prefix) {
        ((RenderContext)context.sql(Tools.getMappedUDTName((Scope)context, ut.getType()))).sqlIndentStart('(');
        Field<?>[] fields = Tools.getRecordQualifier(ut).fields();
        for (int i = 0; i < fields.length; ++i) {
            if (i > 0) {
                ((RenderContext)context.sql(',')).formatSeparator();
            }
            Field<?> f = fields[i];
            DataType ft = f.getDataType();
            String n = prefix + (i + 1);
            if (ft.isUDT()) {
                this.toSQLAssociativeArrayInBindingRecurse(context, ft, n);
                continue;
            }
            this.toSQLAssociativeArrayInBindingScalar(context, ft, c -> c.visit(DSL.name(n)).sql('(').visit(DSL.name("i")).sql(')'));
        }
        context.sqlIndentEnd(')');
    }

    @Pro
    private final void toSQLAssociativeArrayOutBinding(RenderContext context, AssociativeArrayRecord<?, ?> qualifier, int p, boolean deleteInOut) {
        int j;
        Name a = DSL.name("a" + (p + 1));
        Name e = DSL.name("e" + (p + 1));
        Name i = DSL.name("i" + (p + 1));
        Name k = DSL.name("k" + (p + 1));
        ArrayList<Name> v = new ArrayList<Name>();
        List<Flattened> flattened = this.flatten("" + (p + 1), qualifier.getDataType());
        for (Flattened f : flattened) {
            v.add(DSL.name("v" + f.prefix()));
        }
        if (deleteInOut) {
            ((RenderContext)((RenderContext)context.formatSeparator()).visit(k)).sql(".delete();");
            for (Name n : v) {
                ((RenderContext)((RenderContext)context.formatSeparator()).visit(n)).sql(".delete();");
            }
        }
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(e)).sql(" := ")).visit(a)).sql('.')).visit(Keywords.K_FIRST)).sql(';');
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(Keywords.K_WHILE)).sql(' ')).visit(e)).sql(' ')).visit(Keywords.K_IS_NOT_NULL)).sql(' ')).visit(Keywords.K_LOOP)).formatIndentStart()).formatSeparator()).visit(k)).sql('(')).visit(i)).sql(") := ")).visit(e)).sql(';')).formatSeparator();
        for (j = 0; j < v.size(); ++j) {
            Flattened f;
            f = flattened.get(j);
            ((RenderContext)((RenderContext)((RenderContext)context.visit((QueryPart)v.get(j))).sql('(')).visit(i)).sql(") := ");
            Consumer<Context> consumer = c -> {
                c.visit(a).sql('(').visit(e).sql(')');
                if (f.path() != null) {
                    c.sql('.').visit(f.path());
                }
            };
            if (f.type().isBoolean()) {
                this.toSQLPLSQLBooleanToField(context, consumer, "'0'", "'1'");
            } else {
                consumer.accept(context);
            }
            ((RenderContext)context.sql(';')).formatSeparator();
        }
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.visit(i)).sql(" := ")).visit(i)).sql(" + 1;")).formatSeparator()).visit(e)).sql(" := ")).visit(a)).sql('.')).visit(Keywords.K_NEXT)).sql('(')).visit(e)).sql(");")).formatIndentEnd()).formatSeparator()).visit(Keywords.K_END_LOOP)).sql(';');
        ((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).sql("? := ")).visit(k)).sql(';');
        for (j = 0; j < v.size(); ++j) {
            ((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).sql("? := ")).visit((QueryPart)v.get(j))).sql(';');
        }
    }

    @Pro
    private final void toSQLRefCursorInBinding(RenderContext context, Name cursorName, Result<Record> result) {
        QueryPartList<SQL> columns = new QueryPartList<SQL>();
        for (Field<?> field2 : result.fields()) {
            columns.add(DSL.sql("{0} {1} {path} {2}", DSL.name(field2.getName()), DSL.sql(field2.getDataType().getCastTypeName(context.configuration())), DSL.inline(field2.getName())));
        }
        ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)context.formatSeparator()).visit(Keywords.K_OPEN)).sql(' ')).visit(cursorName)).sql(' ')).visit(Keywords.K_FOR)).formatIndentStart()).formatSeparator()).visit(Keywords.K_SELECT)).sql(" *")).formatSeparator()).visit(Keywords.K_FROM)).sql(' ')).visit(Keywords.K_XMLTABLE)).sql("('/result/record'")).formatIndentStart()).formatSeparator()).visit(Keywords.K_PASSING)).sql(" xmltype")).sql('(')).visit(this.refCursorAsXml(context.configuration(), result))).sql(')')).formatSeparator()).visit(Keywords.K_COLUMNS)).sql(' ')).visit(columns)).formatIndentEnd()).formatSeparator()).sql(") t;")).formatIndentEnd();
    }

    @Pro
    private final Param<String> refCursorAsXml(Configuration configuration, Result<Record> result) {
        return DSL.val((Object)result.formatXML(configuration.formattingProvider().xmlFormatForResults().header(false).recordFormat(XMLFormat.RecordFormat.COLUMN_NAME_ELEMENTS).xmlns(false)), SQLDataType.CLOB.asConvertedDataType(new ClobBinding()));
    }

    @Pro
    private final void bindRecordAsIn(BindContext context, RecordQualifier<?> qualifier, QualifiedRecord<?> value) {
        if (this.isSQLUsable(qualifier)) {
            context.visit(DSL.val(value, qualifier.getDataType()));
        } else {
            for (Field<?> field2 : qualifier.fields()) {
                if (field2.getDataType().isUDT()) {
                    this.bindRecordAsIn(context, Tools.getRecordQualifier(field2.getDataType()), value == null ? null : value.get(field2));
                    continue;
                }
                if (value == null) {
                    context.visit(DSL.val(null, field2.getDataType()));
                    continue;
                }
                if (!(qualifier instanceof Table) && !value.changed(field2)) continue;
                context.visit(DSL.val(value.get(field2), field2.getDataType()));
            }
        }
    }

    @Pro
    private final void skipRecordIndexes(BindContext context, RecordQualifier<?> qualifier) {
        if (this.isSQLUsable(qualifier)) {
            context.nextIndex();
        } else {
            this.skipRecordIndexes0(context, qualifier);
        }
    }

    @Pro
    private final void skipRecordIndexes0(BindContext context, RecordQualifier<?> qualifier) {
        for (Field<?> field2 : qualifier.fields()) {
            if (field2.getDataType().isUDT()) {
                this.skipRecordIndexes(context, Tools.getRecordQualifier(field2.getDataType()));
                continue;
            }
            context.nextIndex();
        }
    }

    @Pro
    private final void skipAssociativeArrayIndexes(BindContext context, AssociativeArrayRecord<?, ?> qualifier) {
        context.nextIndex();
        if (qualifier.getDataType().isRecord()) {
            for (Field<?> field2 : Tools.newQualifiedRecord(qualifier.getDataType()).fields()) {
                if (field2.getDataType().isRecord()) {
                    this.skipRecordIndexes0(context, Tools.getRecordQualifier(field2.getDataType()));
                    continue;
                }
                context.nextIndex();
            }
        } else {
            context.nextIndex();
        }
    }

    @Pro
    private final int fetchRecordOutParameter(ExecuteContext ctx, RecordQualifier<?> qualifier, QualifiedRecord<?> value, int base) throws SQLException {
        int offset = 0;
        for (Field<?> field2 : qualifier.fields()) {
            RecordQualifier<?> nestedQualifier;
            if (field2.getDataType().isUDT() && !this.isSQLUsable(nestedQualifier = Tools.getRecordQualifier(field2.getDataType()))) {
                QualifiedRecord v = (QualifiedRecord)Reflect.on(nestedQualifier.getRecordType()).create().get();
                offset += this.fetchRecordOutParameter(ctx, nestedQualifier, v, base + offset);
                value.set(field2, v);
                continue;
            }
            DefaultBindingGetStatementContext out = new DefaultBindingGetStatementContext(ctx, (CallableStatement)ctx.statement(), base + offset);
            field2.getBinding().get(out);
            value.set(field2, out.value());
            ++offset;
        }
        return offset;
    }

    @Pro
    private final void fetchAssociativeArrayOutParameter(ExecuteContext ctx, AssociativeArrayRecord<?, ?> value, int base) throws SQLException {
        CallableStatement c = (CallableStatement)ctx.statement();
        int offset = 0;
        ?[] r0 = value.getIndexDataType().convert((Object[])c.getArray(base + offset++).getArray());
        if (value.getDataType().isRecord()) {
            List<Flattened> flattened = this.flatten("1", value.getDataType());
            ArrayList<Object[]> rx = new ArrayList<Object[]>();
            for (Flattened f : flattened) {
                rx.add(f.type().convert((Object[])c.getArray(base + offset++).getArray()));
            }
            for (int i = 0; i < r0.length; ++i) {
                value.put(r0[i], this.newRecord(rx, i, value.getDataType()));
            }
        } else {
            E[] r1 = value.getDataType().convert((Object[])c.getArray(base + offset++).getArray());
            for (int i = 0; i < r0.length; ++i) {
                value.put(r0[i], value.getDataType().convert(r1[i]));
            }
        }
    }

    private final QualifiedRecord<?> newRecord(List<Object[]> rx, int i, DataType<?> recordType) {
        return this.newRecord(rx, i, recordType, new int[1]);
    }

    private final QualifiedRecord<?> newRecord(List<Object[]> rx, int i, DataType<?> recordType, int[] offset) {
        QualifiedRecord<?> ry = Tools.newQualifiedRecord(recordType);
        for (int j = 0; j < ry.size(); ++j) {
            DataType nestedType = ry.field(j).getDataType();
            if (nestedType.isRecord()) {
                offset[0] = offset[0] + j;
                QualifiedRecord nestedRecord = this.newRecord(rx, i, nestedType, offset);
                ((AbstractRecord)((Object)ry)).set(j, nestedRecord);
                offset[0] = offset[0] + (nestedRecord.size() - j - 1);
                continue;
            }
            ((AbstractRecord)((Object)ry)).set(j, nestedType.convert(rx.get(j + offset[0])[i]));
        }
        return ry;
    }

    @Pro
    private final int registerRecordOutParameter(ExecuteContext ctx, CallableStatement statement, RecordQualifier<?> qualifier, int base) throws SQLException {
        int offset = 0;
        for (Field<?> field2 : qualifier.fields()) {
            RecordQualifier<?> nestedQualifier;
            if (field2.getDataType().isUDT() && !this.isSQLUsable(nestedQualifier = Tools.getRecordQualifier(field2.getDataType()))) {
                offset += this.registerRecordOutParameter(ctx, statement, nestedQualifier, base + offset);
                continue;
            }
            field2.getBinding().register(new DefaultBindingRegisterContext(ctx, statement, base + offset++));
        }
        return offset;
    }

    @Pro
    private final int registerAssociativeArrayOutParameter(ExecuteContext ctx, CallableStatement statement, AssociativeArrayRecord<?, ?> qualifier, int base) throws SQLException {
        int offset = 0;
        statement.registerOutParameter(base + offset++, 2003, Tools.dbmsSQLType(qualifier.getIndexDataType()).toString());
        for (Flattened f : this.flatten("1", qualifier.getDataType())) {
            statement.registerOutParameter(base + offset++, 2003, Tools.dbmsSQLType(f.type()).toString());
        }
        return offset;
    }

    private final void toSQLAssign(RenderContext context) {
        if (this.needsPLSQLBlock() && context.family() == SQLDialect.ORACLE) {
            Parameter<T> ret = this.getReturnParameter0(context.configuration());
            if (this.booleanParameter(ret)) {
                ((RenderContext)context.visit(DSL.name("b1"))).sql(" := ");
            } else if (this.isRecordParameter(ret)) {
                ((RenderContext)context.visit(DSL.name("r1"))).sql(" := ");
            } else if (this.isAssociativeArrayParameter(ret)) {
                ((RenderContext)context.visit(DSL.name("a1"))).sql(" := ");
            } else if (this.isRefCursorParameter(ret)) {
                ((RenderContext)context.visit(DSL.name("c1"))).sql(" := ");
            } else {
                context.sql("? := ");
            }
        } else if (context.family() != SQLDialect.SNOWFLAKE) {
            context.sql("? = ");
        }
    }

    private final void toSQLCall(RenderContext context) {
        if (!this.needsPLSQLBlock() || context.family() != SQLDialect.ORACLE) {
            context.sql("call ");
        }
        context.visit(this.getQualifiedName(context));
    }

    private final void toSQLOutParam(RenderContext ctx, Parameter<?> parameter, int index) {
        boolean sqlserver;
        boolean oracle = ctx.family() == SQLDialect.ORACLE;
        boolean bl = sqlserver = ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE;
        if (oracle && this.needsPLSQLBlock()) {
            ((RenderContext)ctx.visit(parameter)).sql(" => ");
        } else if (sqlserver && this.hasDefaultedParameters()) {
            ((RenderContext)((RenderContext)ctx.sql('@')).visit(parameter.getUnqualifiedName().unquotedName())).sql(" = ");
        }
        if (oracle && this.booleanParameter(parameter)) {
            ctx.visit(DSL.name("b" + (index + 1)));
        } else if (oracle && this.isRecordParameter(parameter)) {
            ctx.visit(DSL.name("r" + (index + 1)));
        } else if (oracle && this.isAssociativeArrayParameter(parameter)) {
            ctx.visit(DSL.name("a" + (index + 1)));
        } else if (oracle && this.isRefCursorParameter(parameter)) {
            ctx.visit(DSL.name("c" + (index + 1)));
        } else {
            ctx.sql('?');
        }
    }

    private final void toSQLInParam(RenderContext ctx, Parameter<?> parameter, int index, Field<?> value) {
        boolean sqlserver;
        boolean oracle = ctx.family() == SQLDialect.ORACLE;
        boolean bl = sqlserver = ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE;
        if (oracle && this.needsPLSQLBlock()) {
            ((RenderContext)ctx.visit(parameter)).sql(" => ");
        } else if (sqlserver && this.hasDefaultedParameters()) {
            ((RenderContext)((RenderContext)ctx.sql('@')).visit(parameter.getUnqualifiedName().unquotedName())).sql(" = ");
        }
        if (oracle && this.booleanParameter(parameter)) {
            ctx.visit(DSL.name("b" + (index + 1)));
        } else if (oracle && this.isRecordParameter(parameter)) {
            ctx.visit(DSL.name("r" + (index + 1)));
        } else if (oracle && this.isAssociativeArrayParameter(parameter)) {
            ctx.visit(DSL.name("a" + (index + 1)));
        } else if (oracle && this.isRefCursorParameter(parameter)) {
            ctx.visit(DSL.name("c" + (index + 1)));
        } else {
            ctx.visit(value);
        }
    }

    private final Name getQualifiedName(Context<?> ctx) {
        ArrayList<Name> list = new ArrayList<Name>();
        if (ctx.qualify()) {
            Schema mapped = Tools.getMappedSchema(ctx, this.getSchema());
            if (mapped != null && !"".equals(mapped.getName())) {
                list.addAll(Arrays.asList(mapped.getQualifiedName().parts()));
            } else if (this.getSchema() != null && (ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE)) {
                list.addAll(Arrays.asList(this.getSchema().getQualifiedName().parts()));
            }
            if (this.getPackage() != null) {
                list.add(this.getPackage().getUnqualifiedName());
            }
        }
        list.add(this.getUnqualifiedName());
        return DSL.name(list.toArray(Tools.EMPTY_NAME));
    }

    private final void fetchOutParameters(ExecuteContext ctx) throws SQLException {
        for (Parameter<?> parameter : this.getParameters0(ctx.configuration())) {
            if (!this.resultParameter(ctx.configuration(), parameter)) continue;
            try {
                this.fetchOutParameter(ctx, parameter);
            }
            catch (SQLException e) {
                if (ctx.settings().getThrowExceptions() == ThrowExceptions.THROW_NONE) continue;
                throw e;
            }
        }
    }

    private final <U> void fetchOutParameter(ExecuteContext ctx, Parameter<U> parameter) throws SQLException {
        if (this.isRecordParameter(parameter)) {
            RecordQualifier<?> qualifier = Tools.getRecordQualifier(parameter.getDataType());
            QualifiedRecord value = (QualifiedRecord)Reflect.onClass(qualifier.getRecordType()).create().get();
            this.fetchRecordOutParameter(ctx, qualifier, value, this.resultIndexes.get(parameter));
            this.outValues.put(parameter, value);
        } else if (this.isAssociativeArrayParameter(parameter)) {
            AssociativeArrayRecord<?, ?> value = Tools.newAssociativeArrayRecord(parameter.getDataType());
            this.fetchAssociativeArrayOutParameter(ctx, value, this.resultIndexes.get(parameter));
            this.outValues.put(parameter, value);
        } else {
            DefaultBindingGetStatementContext out = new DefaultBindingGetStatementContext(ctx, (CallableStatement)ctx.statement(), this.resultIndexes.get(parameter));
            parameter.getBinding().get(out);
            this.outValues.put(parameter, out.value());
        }
    }

    private final void registerOutParameters(ExecuteContext ctx) throws SQLException {
        Configuration c = ctx.configuration();
        CallableStatement statement = (CallableStatement)ctx.statement();
        for (Parameter<?> parameter : this.getParameters0(ctx.configuration())) {
            if (!this.resultParameter(c, parameter)) continue;
            this.registerOutParameter(ctx, statement, parameter);
        }
    }

    private final <U> void registerOutParameter(ExecuteContext ctx, CallableStatement statement, Parameter<U> parameter) throws SQLException {
        if (this.isRecordParameter(parameter)) {
            this.registerRecordOutParameter(ctx, statement, Tools.getRecordQualifier(parameter.getDataType()), this.resultIndexes.get(parameter));
        } else if (this.isAssociativeArrayParameter(parameter)) {
            this.registerAssociativeArrayOutParameter(ctx, statement, Tools.newAssociativeArrayRecord(parameter.getDataType()), this.resultIndexes.get(parameter));
        } else {
            parameter.getBinding().register(new DefaultBindingRegisterContext(ctx, statement, this.resultIndexes.get(parameter)));
        }
    }

    @Override
    public final T getReturnValue() {
        if (this.returnParameter != null) {
            return this.getValue(this.returnParameter);
        }
        return null;
    }

    @Override
    public final Results getResults() {
        return this.results;
    }

    @Override
    public final <Z> Z getValue(Parameter<Z> parameter) {
        return this.get(parameter);
    }

    @Override
    public final <Z> Z get(Parameter<Z> parameter) {
        return (Z)this.outValues.get(parameter);
    }

    @Override
    public final <Z> Z getInValue(Parameter<Z> parameter) {
        return (Z)this.inValues.get(parameter);
    }

    protected final Map<Parameter<?>, Field<?>> getInValues() {
        return this.inValues;
    }

    @Override
    public final List<Parameter<?>> getOutParameters() {
        return Collections.unmodifiableList(this.outParameters);
    }

    @Override
    public final List<Parameter<?>> getInParameters() {
        return Collections.unmodifiableList(this.inParameters);
    }

    @Override
    public final List<Parameter<?>> getParameters() {
        return Collections.unmodifiableList(this.allParameters);
    }

    private final List<Parameter<?>> getOutParameters0(Configuration c) {
        if (this.hasSyntheticReturnParameter && !SUPPORT_SYNTHETIC_RETURN_PARAMETER.contains((Object)c.dialect())) {
            ArrayList result = new ArrayList(this.getOutParameters());
            result.remove(this.getReturnParameter());
            return result;
        }
        return this.getOutParameters();
    }

    private final List<Parameter<?>> getInParameters0(Configuration c) {
        return this.getInParameters();
    }

    private final List<Parameter<?>> getParameters0(Configuration c) {
        if (this.hasSyntheticReturnParameter && !SUPPORT_SYNTHETIC_RETURN_PARAMETER.contains((Object)c.dialect())) {
            ArrayList result = new ArrayList(this.getParameters());
            result.remove(this.getReturnParameter());
            return result;
        }
        return this.getParameters();
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public final Schema getSchema() {
        return this.schema;
    }

    @Override
    @Pro
    public final Package getPackage() {
        return this.pkg;
    }

    @Override
    public final Parameter<T> getReturnParameter() {
        return this.returnParameter;
    }

    private final Parameter<T> getReturnParameter0(Configuration c) {
        if (this.hasSyntheticReturnParameter && !SUPPORT_SYNTHETIC_RETURN_PARAMETER.contains((Object)c.dialect())) {
            return null;
        }
        return this.getReturnParameter();
    }

    protected final void setOverloaded(boolean overloaded) {
        this.overloaded = overloaded;
    }

    protected final boolean isOverloaded() {
        return this.overloaded;
    }

    protected final void setSQLUsable(boolean isSQLUsable) {
        this.isSQLUsable = isSQLUsable;
    }

    protected final boolean isSQLUsable() {
        return !Boolean.FALSE.equals(this.isSQLUsable);
    }

    private final boolean pgArgNeedsCasting(Parameter<?> parameter) {
        return this.isOverloaded() || parameter.getType() == Byte.class || parameter.getType() == Short.class;
    }

    @Pro
    protected final void setPipelined(boolean pipelined) {
        this.pipelined = pipelined;
    }

    @Pro
    protected final boolean isPipelined() {
        return this.pipelined;
    }

    @Pro
    private final boolean hasBooleanParameters() {
        return this.hasBooleanParameters;
    }

    @Pro
    private final boolean hasRecordParameters() {
        return this.hasRecordParameters;
    }

    @Pro
    private final boolean hasAssociativeArrayParameters() {
        return this.hasAssociativeArrayParameters;
    }

    @Pro
    private final boolean hasRefCursorParameters() {
        return this.hasRefCursorParameters;
    }

    @Pro
    private final boolean hasDefaultedParameters() {
        return this.hasDefaultedParameters && !this.inValuesDefaulted.isEmpty();
    }

    @Pro
    private final boolean needsPLSQLBlock() {
        return this.hasBooleanParameters() || this.hasRecordParameters() || this.hasAssociativeArrayParameters() || this.hasDefaultedParameters() || this.hasRefCursorParameters();
    }

    private final boolean hasUnnamedParameters() {
        return this.hasUnnamedParameters;
    }

    private final void addParameter(Parameter<?> parameter) {
        this.allParameters.add(parameter);
        this.hasUnnamedParameters |= parameter.isUnnamed();
        this.hasDefaultedParameters |= parameter.isDefaulted();
        this.hasRecordParameters |= this.isRecordParameter(parameter);
        this.hasAssociativeArrayParameters |= this.isAssociativeArrayParameter(parameter);
        this.hasRefCursorParameters |= this.isRefCursorParameter(parameter);
    }

    @Pro
    private final boolean isRecordParameter(Parameter<?> parameter) {
        return parameter.getDataType().isUDT() && !this.isSQLUsable(parameter.getDataType());
    }

    @Pro
    private final boolean isAssociativeArrayParameter(Parameter<?> parameter) {
        return parameter.getDataType().isAssociativeArray();
    }

    @Pro
    private final boolean isRowtypeParameter(Parameter<?> parameter) {
        return this.isRecordParameter(parameter) && this.isRowtype(Tools.getRecordQualifier(parameter.getDataType()));
    }

    @Pro
    private final boolean isRowtype(RecordQualifier<?> qualifier) {
        return !(qualifier instanceof UDT) || ((UDT)qualifier).isSynthetic();
    }

    @Pro
    private final boolean isRefCursorParameter(Parameter<?> parameter) {
        return parameter.getType() == Result.class;
    }

    @Pro
    private final boolean isSQLUsable(DataType<?> dataType) {
        return this.isSQLUsable(Tools.getRecordQualifier(dataType));
    }

    @Pro
    private final boolean isSQLUsable(RecordQualifier<?> qualifier) {
        return qualifier instanceof UDT && ((UDT)qualifier).isSQLUsable();
    }

    @Pro
    private final boolean booleanParameter(Parameter<?> parameter) {
        return this.plsqlBooleanParameters.contains(parameter);
    }

    private final boolean resultParameter(Configuration c, Parameter<?> parameter) {
        return parameter.equals(this.getReturnParameter0(c)) || this.getOutParameters0(c).contains(parameter);
    }

    protected final void addInParameter(Parameter<?> parameter) {
        this.addParameter(parameter);
        this.inParameters.add(parameter);
        this.inValues.put(parameter, DSL.val(null, parameter.getDataType()));
        if (parameter.isDefaulted()) {
            this.inValuesDefaulted.add(parameter);
        } else {
            this.inValuesNonDefaulted.add(parameter);
        }
    }

    protected final void addInOutParameter(Parameter<?> parameter) {
        this.addInParameter(parameter);
        this.outParameters.add(parameter);
    }

    protected final void addOutParameter(Parameter<?> parameter) {
        this.addParameter(parameter);
        this.outParameters.add(parameter);
    }

    protected final void setReturnParameter(Parameter<T> parameter) {
        this.addParameter(parameter);
        this.returnParameter = parameter;
    }

    @Pro
    protected final void setSyntheticReturnParameter(Parameter<T> parameter) {
        this.setReturnParameter(parameter);
        this.hasSyntheticReturnParameter = true;
    }

    @Pro
    protected final void setPLSQLBooleanParameter(Parameter<?> parameter) {
        this.hasBooleanParameters |= true;
        this.plsqlBooleanParameters.add(parameter);
    }

    public final Field<T> asField() {
        if (this.function == null) {
            this.function = new RoutineField();
        }
        return this.function;
    }

    public final Field<T> asField(String alias) {
        return this.asField().as(alias);
    }

    public final AggregateFunction<T> asAggregateFunction() {
        Field[] array = new Field[this.getInParameters().size()];
        int i = 0;
        for (Parameter<?> p : this.getInParameters()) {
            array[i] = this.getInValues().get(p);
            ++i;
        }
        return new DefaultAggregateFunction<T>(false, this.getQualifiedName(), this.type, array);
    }

    @Deprecated
    protected static final <T> Parameter<T> createParameter(String name, DataType<T> type) {
        return AbstractRoutine.createParameter(name, type, false, null, null);
    }

    @Deprecated
    protected static final <T> Parameter<T> createParameter(String name, DataType<T> type, boolean isDefaulted) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, null, null);
    }

    @Deprecated
    protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Converter<T, U> converter) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, converter, null);
    }

    @Deprecated
    protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Binding<T, U> binding) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, null, binding);
    }

    @Deprecated
    protected static final <T, X, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, Converter<X, U> converter, Binding<T, X> binding) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, false, converter, binding);
    }

    @Deprecated
    protected static final <T> Parameter<T> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, isUnnamed, null, null);
    }

    @Deprecated
    protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Converter<T, U> converter) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, isUnnamed, converter, null);
    }

    @Deprecated
    protected static final <T, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Binding<T, U> binding) {
        return AbstractRoutine.createParameter(name, type, isDefaulted, isUnnamed, null, binding);
    }

    @Deprecated
    protected static final <T, X, U> Parameter<U> createParameter(String name, DataType<T> type, boolean isDefaulted, boolean isUnnamed, Converter<X, U> converter, Binding<T, X> binding) {
        return org.jooq.impl.Internal.createParameter(name, type, isDefaulted, isUnnamed, converter, binding);
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }

    record Flattened(String prefix, Name path, DataType<?> type) {
    }

    private class RoutineField
    extends AbstractField<T>
    implements QOM.UNotYetImplemented {
        RoutineField() {
            super(AbstractRoutine.this.getQualifiedName(), AbstractRoutine.this.type == null ? SQLDataType.RESULT : AbstractRoutine.this.type);
        }

        @Override
        public void accept(Context<?> ctx) {
            String name;
            SQLDialect family = ctx.family();
            ArrayList fields = new ArrayList(AbstractRoutine.this.getInParameters0(ctx.configuration()).size());
            DataType<Object> returnType = family == SQLDialect.ORACLE && this.getDataType().isBoolean() ? SQLDataType.INTEGER : this.getDataType();
            if (family == SQLDialect.ORACLE && AbstractRoutine.this.hasBooleanParameters()) {
                name = this.getName() + "_";
                TopLevelCte.addIfAbsent(ctx, p -> p instanceof FunctionSpec && name.equals(((FunctionSpec)p).name.last()), () -> new FunctionSpec(DSL.name(name), returnType, fields));
            } else {
                name = null;
            }
            for (Parameter<?> parameter : AbstractRoutine.this.getInParameters0(ctx.configuration())) {
                if (AbstractRoutine.this.inValuesDefaulted.contains(parameter)) continue;
                if (REQUIRE_DISAMBIGUATE_OVERLOADS.contains((Object)ctx.dialect())) {
                    if (AbstractRoutine.this.hasUnnamedParameters()) {
                        if (AbstractRoutine.this.pgArgNeedsCasting(parameter)) {
                            fields.add(new Cast(AbstractRoutine.this.getInValues().get(parameter), parameter.getDataType()));
                            continue;
                        }
                        fields.add(AbstractRoutine.this.getInValues().get(parameter));
                        continue;
                    }
                    if (AbstractRoutine.this.pgArgNeedsCasting(parameter)) {
                        fields.add(DSL.field("{0} := {1}", DSL.name(parameter.getName()), new Cast(AbstractRoutine.this.getInValues().get(parameter), parameter.getDataType())));
                        continue;
                    }
                    fields.add(DSL.field("{0} := {1}", DSL.name(parameter.getName()), AbstractRoutine.this.getInValues().get(parameter)));
                    continue;
                }
                if (family == SQLDialect.ORACLE) {
                    if (AbstractRoutine.this.hasDefaultedParameters() && !AbstractRoutine.this.hasUnnamedParameters()) {
                        if (parameter.getDataType().isBoolean()) {
                            fields.add(DSL.field("{0} => {1}", DSL.name(parameter.getName()), this.translateOracleBooleanParameter(AbstractRoutine.this.getInValues().get(parameter))));
                            continue;
                        }
                        fields.add(DSL.field("{0} => {1}", DSL.name(parameter.getName()), AbstractRoutine.this.getInValues().get(parameter)));
                        continue;
                    }
                    if (parameter.getDataType().isBoolean()) {
                        fields.add(this.translateOracleBooleanParameter(AbstractRoutine.this.getInValues().get(parameter)));
                        continue;
                    }
                    fields.add(AbstractRoutine.this.getInValues().get(parameter));
                    continue;
                }
                fields.add(AbstractRoutine.this.getInValues().get(parameter));
            }
            Field result = new Function(name != null ? DSL.name(name) : AbstractRoutine.this.getQualifiedName(ctx), returnType, false, fields.toArray(Tools.EMPTY_FIELD));
            if (Boolean.TRUE.equals(Tools.settings(ctx.configuration()).isRenderScalarSubqueriesForStoredFunctions())) {
                result = DSL.select(result).asField();
            }
            ctx.visit(result);
        }

        @Pro
        private final Field<?> translateOracleBooleanParameter(Field<?> field2) {
            if (field2 instanceof Param) {
                Param param2 = (Param)field2;
                Boolean value = (Boolean)param2.getValue();
                if (param2.isInline()) {
                    return value == null ? DSL.inline(null, SQLDataType.INTEGER) : DSL.inline(value != false ? 1 : 0);
                }
                if (param2.getParamName() != null) {
                    return value == null ? DSL.param(param2.getParamName(), SQLDataType.NUMERIC) : DSL.param(param2.getParamName(), value != false ? 1 : 0);
                }
                return value == null ? DSL.val(null, SQLDataType.INTEGER) : DSL.val(value != false ? 1 : 0);
            }
            return field2.coerce(SQLDataType.INTEGER);
        }

        @Pro
        private class FunctionSpec
        extends AbstractStatement
        implements QOM.UTransient {
            final Name name;
            final DataType<?> returnType;
            final List<Field<?>> fields;

            FunctionSpec(Name name, DataType<?> returnType, List<Field<?>> fields) {
                this.name = name;
                this.returnType = returnType;
                this.fields = fields;
            }

            @Override
            public final void accept(Context<?> ctx) {
                int i;
                Name r = DSL.name("r");
                ctx.visit(Keywords.K_FUNCTION).sql(' ').visit(this.name).sql('(');
                List<Parameter<?>> list = AbstractRoutine.this.getInParameters0(ctx.configuration());
                for (i = 0; i < list.size(); ++i) {
                    ctx.sql(list.get(i).getName()).sql(" ").sql(this.fields.get(i).getDataType().getTypeName());
                }
                ctx.sql(')').formatSeparator().visit(Keywords.K_RETURN).sql(' ').sql(this.returnType.getTypeName()).formatSeparator().visit(Keywords.K_IS);
                if (RoutineField.this.getDataType().isBoolean()) {
                    ctx.formatIndentStart().formatSeparator().visit(r).sql(' ').visit(Keywords.K_BOOLEAN).sql(';').formatIndentEnd();
                }
                ctx.formatSeparator().visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator();
                if (RoutineField.this.getDataType().isBoolean()) {
                    ctx.visit(r).sql(" := ");
                } else {
                    ctx.visit(Keywords.K_RETURN).sql(' ');
                }
                ctx.visit(AbstractRoutine.this.getQualifiedName(ctx)).sql('(');
                for (i = 0; i < list.size(); ++i) {
                    Parameter<?> p = list.get(i);
                    ctx.sql(i > 0 ? "," : "");
                    if (p.getDataType().isBoolean()) {
                        ctx.visit(Keywords.K_NOT).sql(' ').sql(p.getName()).sql(" = 0");
                        continue;
                    }
                    ctx.sql(p.getName());
                }
                ctx.sql(");");
                if (RoutineField.this.getDataType().isBoolean()) {
                    ctx.formatSeparator().visit(Keywords.K_RETURN).sql(' ');
                    AbstractRoutine.this.toSQLPLSQLBooleanToField(ctx, c -> c.visit(r));
                    ctx.sql(';');
                }
                ctx.formatIndentEnd().formatSeparator().visit(Keywords.K_END).sql(' ').visit(this.name).sql(';').formatSeparator();
            }
        }
    }
}

