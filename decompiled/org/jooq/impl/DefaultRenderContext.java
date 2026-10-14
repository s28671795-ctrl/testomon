/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderFormatting;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractContext;
import org.jooq.impl.AbstractDelegatingQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.Identifiers;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ScopeMappable;
import org.jooq.impl.ScopeMappableWrapper;
import org.jooq.impl.ScopeMarker;
import org.jooq.impl.ScopeNestable;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

class DefaultRenderContext
extends AbstractContext<RenderContext>
implements RenderContext {
    private static final JooqLogger log = JooqLogger.getLogger(DefaultRenderContext.class);
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[A-Za-z][A-Za-z0-9_]*");
    private static final Pattern NEWLINE = Pattern.compile("[\\n\\r]");
    private static final Set<String> SQLITE_KEYWORDS = new HashSet<String>();
    final StringBuilder sql;
    private final QueryPartList<Param<?>> bindValues;
    private int alias;
    private int indent;
    private Deque<Integer> indentLock;
    private boolean separatorRequired;
    private boolean separator;
    private boolean newline;
    RenderKeywordCase cachedRenderKeywordCase;
    RenderNameCase cachedRenderNameCase;
    RenderQuotedNames cachedRenderQuotedNames;
    boolean cachedRenderFormatted;
    String cachedIndentation;
    int cachedIndentWidth;
    String cachedNewline;
    int cachedPrintMargin;

    DefaultRenderContext(Configuration configuration, ExecuteContext ctx) {
        super(configuration, ctx, null);
        Settings settings = configuration.settings();
        this.sql = new StringBuilder();
        this.bindValues = new QueryPartList();
        this.cachedRenderKeywordCase = SettingsTools.getRenderKeywordCase(settings);
        this.cachedRenderFormatted = Boolean.TRUE.equals(settings.isRenderFormatted());
        this.cachedRenderNameCase = SettingsTools.getRenderNameCase(settings);
        this.cachedRenderQuotedNames = SettingsTools.getRenderQuotedNames(settings);
        RenderFormatting formatting = settings.getRenderFormatting();
        if (formatting == null) {
            formatting = new RenderFormatting();
        }
        this.cachedNewline = formatting.getNewline() == null ? "\n" : formatting.getNewline();
        this.cachedIndentation = formatting.getIndentation() == null ? "  " : formatting.getIndentation();
        this.cachedIndentWidth = this.cachedIndentation.length();
        this.cachedPrintMargin = formatting.getPrintMargin() == null ? 80 : formatting.getPrintMargin();
    }

    DefaultRenderContext(RenderContext context) {
        this(context, true);
    }

    DefaultRenderContext(RenderContext context, boolean copyLocalState) {
        this(context.configuration(), context.executeContext());
        this.paramType(context.paramType());
        this.qualifyCatalog(context.qualifyCatalog());
        this.qualifySchema(context.qualifySchema());
        this.quote(context.quote());
        this.castMode(context.castMode());
        if (copyLocalState) {
            this.data().putAll(context.data());
            this.declareCTE = context.declareCTE();
            this.declareWindows = context.declareWindows();
            this.declareFields = context.declareFields();
            this.declareTables = context.declareTables();
            this.declareAliases = context.declareAliases();
        }
    }

    @Override
    public final BindContext bindValue(Object value, Field<?> field2) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    final QueryPartList<Param<?>> bindValues() {
        return this.bindValues;
    }

    @Override
    void scopeMarkStart0(QueryPart part) {
        this.applyNewLine();
        AbstractContext.ScopeStackElement e = (AbstractContext.ScopeStackElement)this.scopeStack.getOrCreate(part);
        e.positions = new int[]{this.sql.length(), -1};
        e.bindIndex = this.peekIndex();
        e.indent = this.indent;
        this.resetSeparatorFlags();
    }

    @Override
    void scopeMarkEnd0(QueryPart part) {
        this.applyNewLine();
        AbstractContext.ScopeStackElement e = (AbstractContext.ScopeStackElement)this.scopeStack.getOrCreate(part);
        e.positions[1] = this.sql.length();
    }

    @Override
    public QueryPart scopeMapping(QueryPart part) {
        AbstractContext.ScopeStackElement e;
        if (this.scopeStack.inScope() && part instanceof ScopeMappable && (e = (AbstractContext.ScopeStackElement)this.scopeStack.get(part)) != null && e.mapped != null) {
            QueryPart result = e.mapped;
            if (part instanceof ScopeMappableWrapper) {
                result = ((ScopeMappableWrapper)part).wrap(result);
            }
            return result;
        }
        return part;
    }

    @Override
    public RenderContext scopeRegister(QueryPart part, boolean forceNew, QueryPart mapped) {
        if (this.scopeStack.inScope()) {
            if (part instanceof TableImpl) {
                Table<?> root;
                Table<?> child = root = (Table<?>)part;
                ArrayList tables = new ArrayList();
                while (root instanceof TableImpl && (child = ((TableImpl)root).child) != null) {
                    tables.add(root);
                    root = child;
                }
                AbstractContext.ScopeStackElement scopeStackElement = e = forceNew ? (AbstractContext.ScopeStackElement)this.scopeStack.create(root) : (AbstractContext.ScopeStackElement)this.scopeStack.getOrCreate(root);
                if (e.joinNode == null) {
                    e.joinNode = new AbstractContext.JoinNode(this.configuration(), root);
                }
                AbstractContext.JoinNode childNode = e.joinNode;
                for (int i = tables.size() - 1; i >= 0; --i) {
                    Table t2 = (Table)tables.get(i);
                    ForeignKey k = ((TableImpl)t2).childPath;
                    AbstractContext.JoinNode next = childNode.children.get(k);
                    if (next == null) {
                        next = new AbstractContext.JoinNode(this.configuration(), t2);
                        childNode.children.put(k, next);
                    }
                    childNode = next;
                }
            } else {
                e = forceNew ? (AbstractContext.ScopeStackElement)this.scopeStack.create(part) : (AbstractContext.ScopeStackElement)this.scopeStack.getOrCreate(part);
            }
            e.mapped = mapped;
        }
        return this;
    }

    @Override
    void scopeStart0() {
        for (AbstractContext.ScopeStackElement e : this.scopeStack) {
            if (e.part == e.mapped || e.part instanceof ScopeNestable) continue;
            this.scopeStack.set(e.part, null);
        }
    }

    @Override
    void scopeEnd0() {
        int i;
        ScopeMarker[] markers = ScopeMarker.values();
        AbstractContext.ScopeStackElement[] beforeFirst = new AbstractContext.ScopeStackElement[markers.length];
        AbstractContext.ScopeStackElement[] afterLast = new AbstractContext.ScopeStackElement[markers.length];
        ScopeMarker.ScopeContent[] content = new ScopeMarker.ScopeContent[markers.length];
        for (ScopeMarker marker : markers) {
            if (marker.topLevelOnly && this.subqueryLevel() != 0) continue;
            i = marker.ordinal();
            content[i] = (ScopeMarker.ScopeContent)this.data(marker.key);
            ScopeMarker.ScopeContent o = content[i];
            if (o == null || o.isEmpty()) continue;
            beforeFirst[i] = (AbstractContext.ScopeStackElement)this.scopeStack.get(marker.beforeFirst);
            afterLast[i] = (AbstractContext.ScopeStackElement)this.scopeStack.get(marker.afterLast);
        }
        for (AbstractContext.ScopeStackElement e1 : this.scopeStack.iterable(e -> e.scopeLevel == this.scopeStack.scopeLevel())) {
            String replacedSQL = null;
            QueryPartList<Param<?>> insertedBindValues = null;
            if (e1.positions == null) continue;
            if (e1.joinNode != null && !e1.joinNode.children.isEmpty()) {
                replacedSQL = ((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)((RenderContext)this.configuration.dsl().renderContext().declareTables(true)).sql('(')).formatIndentStart(e1.indent)).formatIndentStart()).formatNewLine()).visit(e1.joinNode.joinTree())).formatNewLine()).sql(')')).render();
            } else {
                for (i = 0; i < beforeFirst.length; ++i) {
                    AbstractContext.ScopeStackElement e2 = beforeFirst[i];
                    ScopeMarker.ScopeContent c = content[i];
                    if (e1 != e2 || c == null) continue;
                    DefaultRenderContext ctx = new DefaultRenderContext(this, false);
                    markers[i].renderer.render((DefaultRenderContext)ctx.formatIndentStart(e2.indent), e2, afterLast[i], c);
                    replacedSQL = ctx.render();
                    insertedBindValues = ctx.bindValues();
                    break;
                }
            }
            if (replacedSQL == null) continue;
            this.sql.replace(e1.positions[0], e1.positions[1], replacedSQL);
            int shift = replacedSQL.length() - (e1.positions[1] - e1.positions[0]);
            for (AbstractContext.ScopeStackElement e2 : this.scopeStack) {
                if (e2.positions == null || e2.positions[0] <= e1.positions[0]) continue;
                e2.positions[0] = e2.positions[0] + shift;
                e2.positions[1] = e2.positions[1] + shift;
            }
            if (insertedBindValues == null) continue;
            this.bindValues.addAll(e1.bindIndex - 1, (Collection<Param<?>>)insertedBindValues);
            for (AbstractContext.ScopeStackElement e2 : this.scopeStack) {
                if (e2.positions == null || e2.bindIndex <= e1.bindIndex) continue;
                e2.bindIndex += insertedBindValues.size();
            }
        }
    }

    @Override
    public final String peekAlias() {
        return "alias_" + (this.alias + 1);
    }

    @Override
    public final String nextAlias() {
        return "alias_" + ++this.alias;
    }

    @Override
    public final String render() {
        String prepend = null;
        String append = null;
        if (this.topLevel instanceof Query) {
            prepend = (String)this.data(Tools.SimpleDataKey.DATA_PREPEND_SQL);
            append = (String)this.data(Tools.SimpleDataKey.DATA_APPEND_SQL);
        }
        String result = this.sql.toString();
        return prepend == null && append == null ? result : (this.format() ? (String)(prepend != null ? prepend + (prepend.endsWith(this.cachedNewline) ? "" : this.cachedNewline) : "") + result + (String)(append != null ? ";" + (append.endsWith(this.cachedNewline) ? "" : this.cachedNewline) + append : "") : (String)(prepend != null ? prepend + (prepend.endsWith(" ") ? "" : " ") : "") + result + (String)(append != null ? ";" + (append.endsWith(" ") ? "" : " ") + append : ""));
    }

    @Override
    public final String render(QueryPart part) {
        return ((RenderContext)new DefaultRenderContext(this).visit(part)).render();
    }

    @Override
    public final RenderContext keyword(String keyword) {
        return (RenderContext)this.visit(DSL.keyword(keyword));
    }

    @Override
    public final RenderContext sql(String s2) {
        return this.sql(s2, s2 == null || !this.cachedRenderFormatted);
    }

    @Override
    public final RenderContext sql(String s2, boolean literal) {
        if (!literal) {
            s2 = Tools.replaceAll(s2, NEWLINE.matcher(s2), r -> r.group() + this.indentation());
        }
        if (this.stringLiteral()) {
            s2 = StringUtils.replace(s2, "'", this.stringLiteralEscapedApos);
        }
        this.applyNewLine();
        this.sql.append(s2);
        this.resetSeparatorFlags();
        return this;
    }

    @Override
    public final RenderContext sqlIndentStart(String c) {
        return (RenderContext)this.sql(c).sqlIndentStart();
    }

    @Override
    public final RenderContext sqlIndentEnd(String c) {
        return (RenderContext)this.sqlIndentEnd().sql(c);
    }

    @Override
    public final RenderContext sqlIndentStart() {
        return (RenderContext)this.formatIndentStart().formatNewLine();
    }

    @Override
    public final RenderContext sqlIndentEnd() {
        return (RenderContext)this.formatIndentEnd().formatNewLine();
    }

    @Override
    public final RenderContext sql(char c) {
        this.applyNewLine();
        if (c == '\'' && this.stringLiteral()) {
            this.sql.append(this.stringLiteralEscapedApos);
        } else {
            this.sql.append(c);
        }
        this.resetSeparatorFlags();
        return this;
    }

    @Override
    public final RenderContext sqlIndentStart(char c) {
        return (RenderContext)this.sql(c).sqlIndentStart();
    }

    @Override
    public final RenderContext sqlIndentEnd(char c) {
        return (RenderContext)this.sqlIndentEnd().sql(c);
    }

    @Override
    public final RenderContext sql(int i) {
        this.applyNewLine();
        this.sql.append(i);
        this.resetSeparatorFlags();
        return this;
    }

    @Override
    public final RenderContext sql(long l) {
        this.applyNewLine();
        this.sql.append(l);
        this.resetSeparatorFlags();
        return this;
    }

    @Override
    public final RenderContext sql(float f) {
        this.applyNewLine();
        this.sql.append(this.floatFormat().format(f));
        this.resetSeparatorFlags();
        return this;
    }

    @Override
    public final RenderContext sql(double d) {
        this.applyNewLine();
        this.sql.append(this.doubleFormat().format(d));
        this.resetSeparatorFlags();
        return this;
    }

    private final void resetSeparatorFlags() {
        this.separatorRequired = false;
        this.separator = false;
        this.newline = false;
    }

    @Override
    public final RenderContext formatNewLine() {
        if (this.cachedRenderFormatted) {
            this.newline = true;
        }
        return this;
    }

    private final void applyNewLine() {
        if (this.newline) {
            this.sql.append(this.cachedNewline);
            this.sql.append(this.indentation());
        }
    }

    @Override
    public final RenderContext formatNewLineAfterPrintMargin() {
        if (this.cachedRenderFormatted && this.cachedPrintMargin > 0 && this.sql.length() - this.sql.lastIndexOf(this.cachedNewline) > this.cachedPrintMargin) {
            this.formatNewLine();
        }
        return this;
    }

    private final String indentation() {
        return StringUtils.leftPad("", this.indent, this.cachedIndentation);
    }

    @Override
    public final RenderContext format(boolean format) {
        this.cachedRenderFormatted = format;
        return this;
    }

    @Override
    public final boolean format() {
        return this.cachedRenderFormatted;
    }

    @Override
    public final RenderContext formatSeparator() {
        if (!this.separator && !this.newline) {
            if (this.cachedRenderFormatted) {
                this.formatNewLine();
            } else {
                this.sql(" ", true);
            }
            this.separator = true;
        }
        return this;
    }

    @Override
    public final RenderContext separatorRequired(boolean separatorRequired) {
        this.separatorRequired = separatorRequired;
        return this;
    }

    @Override
    public final boolean separatorRequired() {
        return this.separatorRequired && !this.separator && !this.newline;
    }

    @Override
    public final RenderContext formatIndentStart() {
        return this.formatIndentStart(this.cachedIndentWidth);
    }

    @Override
    public final RenderContext formatIndentEnd() {
        return this.formatIndentEnd(this.cachedIndentWidth);
    }

    @Override
    public final RenderContext formatIndentStart(int i) {
        if (this.cachedRenderFormatted) {
            this.indent += i;
        }
        return this;
    }

    @Override
    public final RenderContext formatIndentEnd(int i) {
        if (this.cachedRenderFormatted) {
            this.indent -= i;
        }
        return this;
    }

    private final Deque<Integer> indentLock() {
        if (this.indentLock == null) {
            this.indentLock = new ArrayDeque<Integer>();
        }
        return this.indentLock;
    }

    @Override
    public final RenderContext formatIndentLockStart() {
        if (this.cachedRenderFormatted) {
            this.indentLock().push(this.indent);
            String[] lines = NEWLINE.split(this.sql);
            this.indent = lines[lines.length - 1].length();
        }
        return this;
    }

    @Override
    public final RenderContext formatIndentLockEnd() {
        if (this.cachedRenderFormatted) {
            this.indent = this.indentLock().pop();
        }
        return this;
    }

    @Override
    public final RenderContext formatPrintMargin(int margin) {
        this.cachedPrintMargin = margin;
        return this;
    }

    @Override
    public final RenderContext literal(String literal) {
        if (literal == null) {
            return this;
        }
        SQLDialect family = this.family();
        boolean needsQuote = family != SQLDialect.SQLITE && this.quote() || family == SQLDialect.SQLITE && SQLITE_KEYWORDS.contains(literal.toUpperCase(SettingsTools.renderLocale(this.configuration().settings()))) || family == SQLDialect.SQLITE && !IDENTIFIER_PATTERN.matcher(literal).matches();
        literal = this.applyNameCase(literal);
        if (family == SQLDialect.JAVA) {
            this.sql(literal);
        } else if (needsQuote) {
            char[][][] quotes = Identifiers.QUOTES.get((Object)family);
            char start2 = quotes[0][0][0];
            char end = quotes[1][0][0];
            this.sql(start2);
            if (literal.indexOf(end) > -1) {
                this.sql(StringUtils.replace(literal, new String(quotes[1][0]), new String(quotes[2][0])), true);
            } else {
                this.sql(literal, true);
            }
            this.sql(end);
        } else {
            this.sql(literal, true);
        }
        return this;
    }

    @Override
    final String applyNameCase(String literal) {
        if (RenderNameCase.LOWER == this.cachedRenderNameCase || RenderNameCase.LOWER_IF_UNQUOTED == this.cachedRenderNameCase && !this.quote()) {
            return literal.toLowerCase(SettingsTools.renderLocale(this.configuration().settings()));
        }
        if (RenderNameCase.UPPER == this.cachedRenderNameCase || RenderNameCase.UPPER_IF_UNQUOTED == this.cachedRenderNameCase && !this.quote()) {
            return literal.toUpperCase(SettingsTools.renderLocale(this.configuration().settings()));
        }
        return literal;
    }

    @Override
    protected final void visit0(QueryPartInternal internal) {
        Param param2;
        if (this.family() == SQLDialect.JAVA && internal instanceof Query && !(internal instanceof Select) && !(internal instanceof AbstractDelegatingQuery)) {
            this.sql("/* Query type not yet supported: " + internal.getClass().getName() + ". See known limitations: https://github.com/jOOQ/jOOQ/issues/11911 */");
            this.formatSeparator();
        }
        int before = this.bindValues.size();
        internal.accept(this);
        int after = this.bindValues.size();
        if (after == before && this.paramType != ParamType.INLINED && internal instanceof Param && !(param2 = (Param)((Object)internal)).isInline()) {
            this.bindValues.add(param2);
            Integer threshold = this.settings().getInlineThreshold();
            if (threshold != null && threshold > 0) {
                this.checkForceInline(threshold);
            } else {
                switch (this.family()) {
                    case ACCESS: {
                        this.checkForceInline(768);
                        break;
                    }
                    case INGRES: {
                        this.checkForceInline(1024);
                        break;
                    }
                    case ASE: 
                    case SQLDATAWAREHOUSE: 
                    case SQLSERVER: {
                        this.checkForceInline(2000);
                        break;
                    }
                    case ORACLE: 
                    case AURORA_POSTGRES: 
                    case COCKROACHDB: 
                    case POSTGRES: 
                    case YUGABYTEDB: {
                        this.checkForceInline(Short.MAX_VALUE);
                        break;
                    }
                    case SQLITE: {
                        this.checkForceInline(999);
                        break;
                    }
                }
            }
        }
    }

    private final void checkForceInline(int max) throws ForceInlineSignal {
        if (this.bindValues.size() > max && Boolean.TRUE.equals(this.data(Tools.BooleanDataKey.DATA_COUNT_BIND_VALUES))) {
            throw new ForceInlineSignal();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("rendering    [").append(this.render()).append("]\n");
        sb.append("formatting   [").append(this.format()).append("]\n");
        sb.append("parameters   [").append((Object)this.paramType).append("]\n");
        this.toString(sb);
        return sb.toString();
    }

    static {
        SQLITE_KEYWORDS.addAll(Arrays.asList("ABORT", "ACTION", "ADD", "AFTER", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ATTACH", "AUTOINCREMENT", "BEFORE", "BEGIN", "BETWEEN", "BY", "CASCADE", "CASE", "CAST", "CHECK", "COLLATE", "COLUMN", "COMMIT", "CONFLICT", "CONSTRAINT", "CREATE", "CROSS", "CURRENT", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "DATABASE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DETACH", "DISTINCT", "DO", "DROP", "EACH", "ELSE", "END", "ESCAPE", "EXCEPT", "EXCLUDE", "EXCLUSIVE", "EXISTS", "EXPLAIN", "FAIL", "FILTER", "FOLLOWING", "FOR", "FOREIGN", "FROM", "FULL", "GLOB", "GROUP", "GROUPS", "HAVING", "IF", "IGNORE", "IMMEDIATE", "IN", "INDEX", "INDEXED", "INITIALLY", "INNER", "INSERT", "INSTEAD", "INTERSECT", "INTO", "IS", "ISNULL", "JOIN", "KEY", "LEFT", "LIKE", "LIMIT", "MATCH", "NATURAL", "NO", "NOT", "NOTHING", "NOTNULL", "NULL", "OF", "OFFSET", "ON", "OR", "ORDER", "OTHERS", "OUTER", "OVER", "PARTITION", "PLAN", "PRAGMA", "PRECEDING", "PRIMARY", "QUERY", "RAISE", "RANGE", "RECURSIVE", "REFERENCES", "REGEXP", "REINDEX", "RELEASE", "RENAME", "REPLACE", "RESTRICT", "RIGHT", "ROLLBACK", "ROW", "ROWS", "SAVEPOINT", "SELECT", "SET", "TABLE", "TEMP", "TEMPORARY", "THEN", "TIES", "TO", "TRANSACTION", "TRIGGER", "UNBOUNDED", "UNION", "UNIQUE", "UPDATE", "USING", "VACUUM", "VALUES", "VIEW", "VIRTUAL", "WHEN", "WHERE", "WINDOW", "WITH", "WITHOUT"));
    }

    class ForceInlineSignal
    extends ControlFlowSignal {
        public ForceInlineSignal() {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Re-render query", "Forcing bind variable inlining as " + String.valueOf((Object)DefaultRenderContext.this.configuration().dialect()) + " does not support " + DefaultRenderContext.this.peekIndex() + " bind variables (or more) in a single query");
            }
        }
    }

    static class Rendered {
        String sql;
        QueryPartList<Param<?>> bindValues;
        int skipUpdateCounts;

        Rendered(String sql, QueryPartList<Param<?>> bindValues, int skipUpdateCounts) {
            this.sql = sql;
            this.bindValues = bindValues;
            this.skipUpdateCounts = skipUpdateCounts;
        }

        public String toString() {
            return this.sql;
        }
    }
}

