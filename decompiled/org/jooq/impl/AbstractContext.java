/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.ConverterContext;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.JoinType;
import org.jooq.LanguageContext;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.VisitContext;
import org.jooq.VisitListener;
import org.jooq.VisitListenerProvider;
import org.jooq.conf.InvocationOrder;
import org.jooq.conf.ParamCastMode;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderImplicitJoinType;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.StatementType;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.BooleanConsumer;
import org.jooq.impl.DSL;
import org.jooq.impl.InternalVisitListener;
import org.jooq.impl.Patterns;
import org.jooq.impl.ScopeStack;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

abstract class AbstractContext<C extends Context<C>>
extends AbstractScope
implements Context<C> {
    @Pro
    private static final Set<SQLDialect> USE_INTERNAL_VISIT_LISTENER = SQLDialect.supportedUntil(SQLDialect.DB2, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER2008, SQLDialect.SYBASE, SQLDialect.TERADATA);
    final ExecuteContext ctx;
    final PreparedStatement stmt;
    boolean declareFields;
    boolean declareTables;
    boolean declareAliases;
    boolean declareWindows;
    boolean declareCTE;
    @Pro
    boolean declareParameters;
    int subquery;
    BitSet subqueryScopedNestedSetOperations;
    boolean predicandSubquery;
    boolean derivedTableSubquery;
    boolean setOperationSubquery;
    int stringLiteral;
    String stringLiteralEscapedApos = "'";
    int index;
    int scopeMarking;
    final ScopeStack<QueryPart, ScopeStackElement> scopeStack;
    int skipUpdateCounts;
    private final VisitListener[] visitListenersStart;
    private final VisitListener[] visitListenersEnd;
    private final Deque<Clause> visitClauses;
    private final DefaultVisitContext visitContext;
    private final Deque<QueryPart> visitParts;
    final ParamType forcedParamType;
    final boolean castModeOverride;
    RenderContext.CastMode castMode;
    LanguageContext languageContext;
    ParamType paramType = ParamType.INDEXED;
    boolean quote = true;
    boolean qualifySchema = true;
    boolean qualifyCatalog = true;
    QueryPart topLevel;
    QueryPart topLevelForLanguageContext;
    private transient DecimalFormat doubleFormat;
    private transient DecimalFormat floatFormat;

    AbstractContext(Configuration configuration, ExecuteContext ctx, PreparedStatement stmt) {
        super(configuration);
        VisitListener[] visitListeners;
        this.ctx = ctx;
        this.stmt = stmt;
        VisitListenerProvider[] providers = configuration.visitListenerProviders();
        boolean useInternalVisitListener = USE_INTERNAL_VISIT_LISTENER.contains((Object)configuration.dialect());
        VisitListener[] visitListenerArray = providers.length > 0 || useInternalVisitListener ? new VisitListener[providers.length + (useInternalVisitListener ? 1 : 0)] : (visitListeners = null);
        if (visitListeners != null) {
            for (int i = 0; i < providers.length; ++i) {
                visitListeners[i] = providers[i].provide();
            }
            if (useInternalVisitListener) {
                visitListeners[providers.length] = new InternalVisitListener();
            }
            this.visitContext = new DefaultVisitContext();
            this.visitParts = new ArrayDeque<QueryPart>();
            this.visitClauses = new ArrayDeque<Clause>();
            this.visitListenersStart = configuration.settings().getVisitListenerStartInvocationOrder() != InvocationOrder.REVERSE ? visitListeners : Tools.reverse((VisitListener[])visitListeners.clone());
            this.visitListenersEnd = configuration.settings().getVisitListenerEndInvocationOrder() != InvocationOrder.REVERSE ? visitListeners : Tools.reverse((VisitListener[])visitListeners.clone());
        } else {
            this.visitContext = null;
            this.visitParts = null;
            this.visitClauses = null;
            this.visitListenersStart = null;
            this.visitListenersEnd = null;
        }
        this.forcedParamType = SettingsTools.getStatementType(this.settings()) == StatementType.STATIC_STATEMENT ? ParamType.INLINED : (SettingsTools.getParamType(this.settings()) == ParamType.FORCE_INDEXED ? ParamType.INDEXED : null);
        ParamCastMode m4 = this.settings().getParamCastMode();
        boolean bl = this.castModeOverride = m4 != ParamCastMode.DEFAULT && m4 != null;
        this.castMode = m4 == ParamCastMode.ALWAYS ? RenderContext.CastMode.ALWAYS : (m4 == ParamCastMode.NEVER ? RenderContext.CastMode.NEVER : RenderContext.CastMode.DEFAULT);
        this.languageContext = LanguageContext.QUERY;
        this.scopeStack = new ScopeStack<QueryPart, ScopeStackElement>(ScopeStackElement::new);
    }

    @Override
    public final ConverterContext converterContext() {
        return this.ctx != null ? this.ctx.converterContext() : Tools.converterContext(this.configuration());
    }

    @Override
    public final ExecuteContext executeContext() {
        return this.ctx;
    }

    @Override
    public final C visit(Condition part) {
        return this.visit((QueryPart)part);
    }

    @Override
    public final C visit(Field<?> part) {
        C c;
        if (part instanceof Condition) {
            Condition c2 = (Condition)part;
            c = this.visit((QueryPart)DSL.field(c2));
        } else {
            c = this.visit((QueryPart)part);
        }
        return c;
    }

    @Override
    public final C visit(QueryPart part) {
        if (part != null) {
            QueryPart replacement;
            Clause[] clauses;
            if (this.topLevel == null) {
                this.topLevel = this.topLevelForLanguageContext = part;
                if (this.executeContext() == null && Boolean.TRUE.equals(this.settings().isTransformPatterns()) && this.configuration().requireCommercial(() -> "SQL transformations are a commercial only feature. Please consider upgrading to the jOOQ Professional Edition or jOOQ Enterprise Edition.")) {
                    part = part.$replace(new Patterns(this.configuration(), false).transformPatterns());
                }
            }
            Clause[] clauseArray = clauses = Tools.isNotEmpty(this.visitListenersStart) ? this.clause(part) : null;
            if (clauses != null) {
                for (int i = 0; i < clauses.length; ++i) {
                    this.start(clauses[i]);
                }
            }
            if ((replacement = this.start(part)) != null) {
                QueryPartInternal internal = (QueryPartInternal)this.scopeMapping(replacement);
                if (this.declareFields() && !internal.declaresFields()) {
                    boolean aliases = this.declareAliases();
                    this.declareFields(false);
                    this.visit0(internal);
                    this.declareFields(true);
                    this.declareAliases(aliases);
                } else if (this.declareTables() && !internal.declaresTables()) {
                    boolean aliases = this.declareAliases();
                    this.declareTables(false);
                    this.visit0(internal);
                    this.declareTables(true);
                    this.declareAliases(aliases);
                } else if (this.declareWindows() && !internal.declaresWindows()) {
                    this.declareWindows(false);
                    this.visit0(internal);
                    this.declareWindows(true);
                } else if (this.declareCTE() && !internal.declaresCTE()) {
                    this.declareCTE(false);
                    this.visit0(internal);
                    this.declareCTE(true);
                } else if (this.declareParameters() && !internal.declaresParameters()) {
                    this.declareParameters(false);
                    this.visit0(internal);
                    this.declareParameters(true);
                } else if (!this.castModeOverride && this.castMode() != RenderContext.CastMode.DEFAULT && !internal.generatesCast()) {
                    RenderContext.CastMode previous = this.castMode();
                    this.castMode(RenderContext.CastMode.DEFAULT);
                    this.visit0(internal);
                    this.castMode(previous);
                } else {
                    this.visit0(internal);
                }
            }
            this.end(replacement);
            if (clauses != null) {
                for (int i = clauses.length - 1; i >= 0; --i) {
                    this.end(clauses[i]);
                }
            }
        }
        return (C)this;
    }

    @Override
    public final C visitSubquery(QueryPart part) {
        Tools.visitSubquery(this, part);
        return (C)this;
    }

    protected abstract void visit0(QueryPartInternal var1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final C toggle(boolean b, BooleanSupplier get2, BooleanConsumer set, Consumer<? super C> consumer) {
        boolean previous = get2.getAsBoolean();
        try {
            set.accept(b);
            consumer.accept(this);
        }
        finally {
            set.accept(previous);
        }
        return (C)this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final <T> C toggle(T t2, Supplier<T> get2, Consumer<T> set, Consumer<? super C> consumer) {
        T previous = get2.get();
        try {
            set.accept(t2);
            consumer.accept(this);
        }
        finally {
            set.accept(previous);
        }
        return (C)this;
    }

    @Override
    public final C data(Object key, Object value, Consumer<? super C> consumer) {
        return this.toggle(value, () -> this.data(key), (T v) -> {
            if (v == null) {
                this.data().remove(key);
            } else {
                this.data(key, v);
            }
        }, consumer);
    }

    private final Clause[] clause(QueryPart part) {
        if (part instanceof QueryPartInternal && !Boolean.TRUE.equals(this.data(Tools.BooleanDataKey.DATA_OMIT_CLAUSE_EVENT_EMISSION))) {
            return ((QueryPartInternal)part).clauses(this);
        }
        return null;
    }

    @Override
    public final C start(Clause clause) {
        if (clause != null && this.visitClauses != null) {
            this.visitClauses.addLast(clause);
            for (VisitListener listener : this.visitListenersStart) {
                listener.clauseStart(this.visitContext);
            }
        }
        return (C)this;
    }

    @Override
    public final C end(Clause clause) {
        if (clause != null && this.visitClauses != null) {
            for (VisitListener listener : this.visitListenersEnd) {
                listener.clauseEnd(this.visitContext);
            }
            if (this.visitClauses.removeLast() != clause) {
                throw new IllegalStateException("Mismatch between visited clauses!");
            }
        }
        return (C)this;
    }

    private final QueryPart start(QueryPart part) {
        if (this.visitParts != null) {
            this.visitParts.addLast(part);
            for (VisitListener listener : this.visitListenersStart) {
                listener.visitStart(this.visitContext);
            }
            return this.visitParts.peekLast();
        }
        return part;
    }

    private final void end(QueryPart part) {
        if (this.visitParts != null) {
            for (VisitListener listener : this.visitListenersEnd) {
                listener.visitEnd(this.visitContext);
            }
            if (this.visitParts.removeLast() != part) {
                throw new RuntimeException("Mismatch between visited query parts");
            }
        }
    }

    @Override
    public final boolean declareFields() {
        return this.declareFields;
    }

    @Override
    public final C declareFields(boolean d) {
        this.declareFields = d;
        this.declareAliases(d);
        return (C)this;
    }

    @Override
    public C declareFields(boolean f, Consumer<? super C> consumer) {
        return this.toggle(f, this::declareFields, this::declareFields, consumer);
    }

    @Override
    public final boolean declareTables() {
        return this.declareTables;
    }

    @Override
    public final C declareTables(boolean d) {
        this.declareTables = d;
        this.declareAliases(d);
        return (C)this;
    }

    @Override
    public C declareTables(boolean f, Consumer<? super C> consumer) {
        return this.toggle(f, this::declareTables, this::declareTables, consumer);
    }

    @Override
    public final boolean declareAliases() {
        return this.declareAliases;
    }

    @Override
    public final C declareAliases(boolean d) {
        this.declareAliases = d;
        return (C)this;
    }

    @Override
    public C declareAliases(boolean f, Consumer<? super C> consumer) {
        return this.toggle(f, this::declareAliases, this::declareAliases, consumer);
    }

    @Override
    public final boolean declareWindows() {
        return this.declareWindows;
    }

    @Override
    public final C declareWindows(boolean d) {
        this.declareWindows = d;
        return (C)this;
    }

    @Override
    public C declareWindows(boolean f, Consumer<? super C> consumer) {
        return this.toggle(f, this::declareWindows, this::declareWindows, consumer);
    }

    @Override
    @Pro
    public final boolean declareParameters() {
        return this.declareParameters;
    }

    @Override
    @Pro
    public final C declareParameters(boolean d) {
        this.declareParameters = d;
        return (C)this;
    }

    @Override
    @Pro
    public C declareParameters(boolean f, Consumer<? super C> consumer) {
        return this.toggle(f, this::declareParameters, this::declareParameters, consumer);
    }

    @Override
    public final boolean declareCTE() {
        return this.declareCTE;
    }

    @Override
    public final C declareCTE(boolean d) {
        this.declareCTE = d;
        return (C)this;
    }

    @Override
    public C declareCTE(boolean f, Consumer<? super C> consumer) {
        return this.toggle(f, this::declareCTE, this::declareCTE, consumer);
    }

    @Override
    public final int scopeLevel() {
        return this.scopeStack.scopeLevel();
    }

    @Override
    public final QueryPart topLevel() {
        return this.topLevel;
    }

    @Override
    public final C topLevel(QueryPart t2) {
        this.topLevel = t2;
        return (C)this;
    }

    @Override
    public final QueryPart topLevelForLanguageContext() {
        return this.topLevelForLanguageContext;
    }

    @Override
    public final C topLevelForLanguageContext(QueryPart t2) {
        this.topLevelForLanguageContext = t2;
        return (C)this;
    }

    @Override
    public final int subqueryLevel() {
        return this.subquery;
    }

    @Override
    public final boolean predicandSubquery() {
        return this.predicandSubquery;
    }

    @Override
    public final C predicandSubquery(boolean s2) {
        this.predicandSubquery = s2;
        return (C)this;
    }

    @Override
    public final boolean derivedTableSubquery() {
        return this.derivedTableSubquery;
    }

    @Override
    public final C derivedTableSubquery(boolean s2) {
        this.derivedTableSubquery = s2;
        return (C)this;
    }

    @Override
    public final boolean setOperationSubquery() {
        return this.setOperationSubquery;
    }

    @Override
    public final C setOperationSubquery(boolean s2) {
        this.setOperationSubquery = s2;
        return (C)this;
    }

    @Override
    public final boolean subquery() {
        return this.subquery > 0;
    }

    final C subquery0(boolean s2, boolean setOperation) {
        this.setOperationSubquery(setOperation);
        if (s2) {
            ++this.subquery;
            if (!setOperation && Boolean.TRUE.equals(this.data(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS))) {
                this.data().remove(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS);
                if (this.subqueryScopedNestedSetOperations == null) {
                    this.subqueryScopedNestedSetOperations = new BitSet();
                }
                this.subqueryScopedNestedSetOperations.set(this.subquery);
            }
            this.scopeStart();
        } else {
            this.scopeEnd();
            if (!setOperation) {
                if (this.subqueryScopedNestedSetOperations != null && this.subqueryScopedNestedSetOperations.get(this.subquery)) {
                    this.data(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS, true);
                } else {
                    this.data().remove(Tools.BooleanDataKey.DATA_NESTED_SET_OPERATIONS);
                }
            }
            --this.subquery;
        }
        return (C)this;
    }

    @Override
    public final C subquery(boolean s2) {
        return this.subquery0(s2, false);
    }

    @Override
    public final C scopeStart() {
        this.scopeStack.scopeStart();
        this.scopeStart0();
        this.resetDataKeys(this.scopeStack.getOrCreate(Tools.DataKeyScopeStackPart.INSTANCE));
        return (C)this;
    }

    @Override
    public final C scopeRegister(QueryPart part) {
        return this.scopeRegister(part, false);
    }

    @Override
    public final C scopeRegister(QueryPart part, boolean forceNew) {
        return this.scopeRegister(part, forceNew, null);
    }

    @Override
    public C scopeRegister(QueryPart part, boolean forceNew, QueryPart mapped) {
        return (C)this;
    }

    @Override
    public QueryPart scopeMapping(QueryPart part) {
        return part;
    }

    @Override
    public final C scopeRegisterAndMark(QueryPart part, boolean forceNew) {
        return this.scopeRegister(part, forceNew).scopeMarkStart(part).scopeMarkEnd(part);
    }

    @Override
    public final C scopeMarkStart(QueryPart part) {
        if (this.scopeStack.inScope() && this.scopeMarking++ == 0) {
            this.scopeMarkStart0(part);
        }
        return (C)this;
    }

    @Override
    public final C scopeMarkEnd(QueryPart part) {
        if (this.scopeStack.inScope() && --this.scopeMarking == 0) {
            this.scopeMarkEnd0(part);
        }
        return (C)this;
    }

    @Override
    public final C scopeEnd() {
        this.restoreDataKeys(this.scopeStack.getOrCreate(Tools.DataKeyScopeStackPart.INSTANCE));
        this.scopeEnd0();
        this.scopeStack.scopeEnd();
        return (C)this;
    }

    void scopeStart0() {
    }

    void scopeMarkStart0(QueryPart part) {
    }

    void scopeMarkEnd0(QueryPart part) {
    }

    void scopeEnd0() {
    }

    final void resetDataKeys(ScopeStackElement e) {
        for (int i = 0; i < Tools.DATAKEY_RESET_IN_SUBQUERY_SCOPE.length; ++i) {
            Tools.DataKey key = Tools.DATAKEY_RESET_IN_SUBQUERY_SCOPE[i];
            if (this.subqueryLevel() < key.resetThreshold() || !this.data().containsKey(key)) continue;
            e.restoreDataKeys = Tools.lazy(e.restoreDataKeys, i + 1);
            e.restoreDataKeys.set(i, this.data().put(key, key.resetValue()));
        }
    }

    final void restoreDataKeys(ScopeStackElement e) {
        for (int i = 0; i < Tools.DATAKEY_RESET_IN_SUBQUERY_SCOPE.length; ++i) {
            Tools.DataKey key = Tools.DATAKEY_RESET_IN_SUBQUERY_SCOPE[i];
            if (this.subqueryLevel() < key.resetThreshold() || e.restoreDataKeys == null || i >= e.restoreDataKeys.size()) continue;
            this.data().put(key, e.restoreDataKeys.get(i));
        }
    }

    String applyNameCase(String literal) {
        return literal;
    }

    @Override
    public final boolean stringLiteral() {
        return this.stringLiteral > 0;
    }

    @Override
    public final C stringLiteral(boolean s2) {
        if (s2) {
            ++this.stringLiteral;
            this.stringLiteralEscapedApos = this.stringLiteralEscapedApos + this.stringLiteralEscapedApos;
        } else {
            --this.stringLiteral;
            this.stringLiteralEscapedApos = this.stringLiteralEscapedApos.substring(0, this.stringLiteralEscapedApos.length() / 2);
        }
        return (C)this;
    }

    @Override
    public final int nextIndex() {
        return ++this.index;
    }

    @Override
    public final int peekIndex() {
        return this.index + 1;
    }

    @Override
    public final int skipUpdateCounts() {
        return this.skipUpdateCounts;
    }

    @Override
    public final C skipUpdateCount() {
        return this.skipUpdateCounts(1);
    }

    @Override
    public final C skipUpdateCounts(int skip) {
        this.skipUpdateCounts += skip;
        return (C)this;
    }

    @Override
    public final DecimalFormat floatFormat() {
        if (this.floatFormat == null) {
            this.floatFormat = new DecimalFormat("0.#######E0", DecimalFormatSymbols.getInstance(Locale.US));
        }
        return this.floatFormat;
    }

    @Override
    public final DecimalFormat doubleFormat() {
        if (this.doubleFormat == null) {
            this.doubleFormat = new DecimalFormat("0.################E0", DecimalFormatSymbols.getInstance(Locale.US));
        }
        return this.doubleFormat;
    }

    @Override
    public final ParamType paramType() {
        return this.forcedParamType != null ? this.forcedParamType : this.paramType;
    }

    @Override
    public final C paramType(ParamType p) {
        this.paramType = p == null ? ParamType.INDEXED : p;
        return (C)this;
    }

    @Override
    public final C visit(QueryPart part, ParamType p) {
        return (C)this.paramType(p, c -> c.visit(part));
    }

    @Override
    public final C paramTypeIf(ParamType p, boolean condition) {
        if (condition) {
            this.paramType(p);
        }
        return (C)this;
    }

    @Override
    public final C paramType(ParamType p, Consumer<? super C> runnable2) {
        return this.toggle(p, this::paramType, this::paramType, runnable2);
    }

    @Override
    public final C paramTypeIf(ParamType p, boolean condition, Consumer<? super C> runnable2) {
        if (condition) {
            this.paramType(p, runnable2);
        } else {
            runnable2.accept(this);
        }
        return (C)this;
    }

    @Override
    public final boolean quote() {
        return this.quote;
    }

    @Override
    public final C quote(boolean q) {
        this.quote = q;
        return (C)this;
    }

    @Override
    public final C quote(boolean q, Consumer<? super C> consumer) {
        return this.toggle(q, this::quote, this::quote, consumer);
    }

    @Override
    public final boolean qualify() {
        return this.qualifySchema();
    }

    @Override
    public final C qualify(boolean q) {
        return this.qualifySchema(q);
    }

    @Override
    public final C qualify(boolean q, Consumer<? super C> consumer) {
        return this.toggle(q, this::qualify, this::qualify, consumer);
    }

    @Override
    public final boolean qualifySchema() {
        return this.qualifySchema;
    }

    @Override
    public final C qualifySchema(boolean q) {
        this.qualifySchema = q;
        return (C)this;
    }

    @Override
    public final C qualifySchema(boolean q, Consumer<? super C> consumer) {
        return this.toggle(q, this::qualifySchema, this::qualifySchema, consumer);
    }

    @Override
    public final boolean qualifyCatalog() {
        return this.qualifyCatalog;
    }

    @Override
    public final C qualifyCatalog(boolean q) {
        this.qualifyCatalog = q;
        return (C)this;
    }

    @Override
    public final C qualifyCatalog(boolean q, Consumer<? super C> consumer) {
        return this.toggle(q, this::qualifyCatalog, this::qualifyCatalog, consumer);
    }

    @Override
    public final LanguageContext languageContext() {
        return this.languageContext;
    }

    @Override
    public final C languageContext(LanguageContext context) {
        this.languageContext = context;
        return (C)this;
    }

    @Override
    public final C languageContext(LanguageContext context, Consumer<? super C> consumer) {
        return this.toggle(context, this::languageContext, this::languageContext, consumer);
    }

    @Override
    public final C languageContext(LanguageContext context, QueryPart newTopLevelForLanguageContext, Consumer<? super C> consumer) {
        return (C)this.toggle(context, this::languageContext, this::languageContext, (? super C c) -> this.toggle(newTopLevelForLanguageContext, this::topLevelForLanguageContext, this::topLevelForLanguageContext, consumer));
    }

    @Override
    public final C languageContextIf(LanguageContext context, boolean condition) {
        if (condition) {
            this.languageContext(context);
        }
        return (C)this;
    }

    @Override
    public final RenderContext.CastMode castMode() {
        return this.castMode;
    }

    @Override
    public final C castMode(RenderContext.CastMode mode) {
        this.castMode = mode;
        return (C)this;
    }

    @Override
    public final C castMode(RenderContext.CastMode mode, Consumer<? super C> consumer) {
        return this.toggle(mode, this::castMode, this::castMode, consumer);
    }

    @Override
    public final C castModeIf(RenderContext.CastMode mode, boolean condition) {
        if (condition) {
            this.castMode(mode);
        }
        return (C)this;
    }

    @Override
    public final PreparedStatement statement() {
        return this.stmt;
    }

    void toString(StringBuilder sb) {
        sb.append("bind index   [");
        sb.append(this.index);
        sb.append("]");
        sb.append("\ncast mode    [");
        sb.append((Object)this.castMode);
        sb.append("]");
        sb.append("\ndeclaring    [");
        if (this.declareFields) {
            sb.append("fields");
            if (this.declareAliases) {
                sb.append(" and aliases");
            }
        } else if (this.declareTables) {
            sb.append("tables");
            if (this.declareAliases) {
                sb.append(" and aliases");
            }
        } else if (this.declareWindows) {
            sb.append("windows");
        } else if (this.declareCTE) {
            sb.append("cte");
        } else {
            sb.append("-");
        }
        sb.append("]\nsubquery     [");
        sb.append(this.subquery);
        sb.append("]");
    }

    private class DefaultVisitContext
    implements VisitContext {
        private DefaultVisitContext() {
        }

        @Override
        public final Instant creationTime() {
            return AbstractContext.this.creationTime();
        }

        @Override
        public final Map<Object, Object> data() {
            return AbstractContext.this.data();
        }

        @Override
        public final Object data(Object key) {
            return AbstractContext.this.data(key);
        }

        @Override
        public final Object data(Object key, Object value) {
            return AbstractContext.this.data(key, value);
        }

        @Override
        public final Configuration configuration() {
            return AbstractContext.this.configuration();
        }

        @Override
        public final DSLContext dsl() {
            return AbstractContext.this.dsl();
        }

        @Override
        public final Settings settings() {
            return Tools.settings(this.configuration());
        }

        @Override
        public final SQLDialect dialect() {
            return Tools.configuration(this.configuration()).dialect();
        }

        @Override
        public final SQLDialect family() {
            return this.dialect().family();
        }

        @Override
        public final Clause clause() {
            return AbstractContext.this.visitClauses.peekLast();
        }

        @Override
        public final Clause[] clauses() {
            return AbstractContext.this.visitClauses.toArray(Tools.EMPTY_CLAUSE);
        }

        @Override
        public final int clausesLength() {
            return AbstractContext.this.visitClauses.size();
        }

        @Override
        public final QueryPart queryPart() {
            return AbstractContext.this.visitParts.peekLast();
        }

        @Override
        public final void queryPart(QueryPart part) {
            AbstractContext.this.visitParts.pollLast();
            AbstractContext.this.visitParts.addLast(part);
        }

        @Override
        public final QueryPart[] queryParts() {
            return AbstractContext.this.visitParts.toArray(Tools.EMPTY_QUERYPART);
        }

        @Override
        public final int queryPartsLength() {
            return AbstractContext.this.visitParts.size();
        }

        @Override
        public final Context<?> context() {
            return AbstractContext.this;
        }

        @Override
        public final RenderContext renderContext() {
            RenderContext c;
            Context<?> context = this.context();
            return context instanceof RenderContext ? (c = (RenderContext)context) : null;
        }

        @Override
        public final BindContext bindContext() {
            BindContext c;
            Context<?> context = this.context();
            return context instanceof BindContext ? (c = (BindContext)context) : null;
        }
    }

    static class ScopeStackElement {
        final int scopeLevel;
        final QueryPart part;
        QueryPart mapped;
        int[] positions;
        int bindIndex;
        int indent;
        JoinNode joinNode;
        List<Object> restoreDataKeys;

        ScopeStackElement(QueryPart part, int scopeLevel) {
            this.part = part;
            this.mapped = part;
            this.scopeLevel = scopeLevel;
        }

        public String toString() {
            return (String)(this.positions != null ? Arrays.toString(this.positions) + ": " : "") + String.valueOf(this.part) + (String)(this.mapped != null ? " (" + String.valueOf(this.mapped) + ")" : "");
        }
    }

    static class JoinNode {
        final Configuration configuration;
        final Table<?> table;
        final Map<ForeignKey<?, ?>, JoinNode> children;

        JoinNode(Configuration configuration, Table<?> table) {
            this.configuration = configuration;
            this.table = table;
            this.children = new LinkedHashMap();
        }

        public Table<?> joinTree() {
            Table<?> result = this.table;
            for (Map.Entry<ForeignKey<?, ?>, JoinNode> e : this.children.entrySet()) {
                result = result.join(e.getValue().joinTree(), switch (StringUtils.defaultIfNull(Tools.settings(this.configuration).getRenderImplicitJoinType(), RenderImplicitJoinType.DEFAULT)) {
                    case RenderImplicitJoinType.INNER_JOIN -> JoinType.JOIN;
                    case RenderImplicitJoinType.LEFT_JOIN -> JoinType.LEFT_OUTER_JOIN;
                    default -> e.getKey().nullable() ? JoinType.LEFT_OUTER_JOIN : JoinType.JOIN;
                }).onKey(e.getKey());
            }
            return result;
        }

        public String toString() {
            return this.joinTree().toString();
        }
    }
}

