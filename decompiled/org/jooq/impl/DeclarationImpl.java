/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Declaration;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.Variable;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.Assignment;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.Tools;

@Pro
final class DeclarationImpl<T>
extends AbstractStatement
implements Declaration<T>,
QOM.Declaration<T> {
    private static final Set<SQLDialect> NO_SUPPORT_DEFAULT = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.INFORMIX);
    private static final Set<SQLDialect> NO_SUPPORT_DEFAULT_EXPRESSIONS = SQLDialect.supportedBy(SQLDialect.DB2);
    private static final Set<SQLDialect> NO_SUPPORT_DEFAULT_KEYWORD = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.SQLSERVER);
    final Variable<T> variable;
    final Field<T> value;

    DeclarationImpl(Variable<T> variable, Field<T> value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public final Statement set(T v) {
        return this.set(Tools.field(v, this.variable.getDataType()));
    }

    @Override
    public final Statement set(Field<T> v) {
        return new DeclarationImpl<T>(this.variable, v);
    }

    @Override
    public final Statement set(Select<? extends Record1<T>> v) {
        return this.set(DSL.field(v));
    }

    @Override
    public final Statement setNull() {
        return this.set((T)null);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: {
                ctx.sql(BlockImpl.variableType(this.variable)).sql(' ').sql(this.variable.getName());
                if (this.value instanceof ScalarSubquery) {
                    ctx.sql(" = null;").formatSeparator();
                    ctx.visit(this.variable.set(this.value));
                    break;
                }
                if (this.value != null) {
                    ctx.sql(" = ").visit(this.value);
                    break;
                }
                ctx.sql(" = null");
                break;
            }
            default: {
                BlockImpl.ScopeDeclarations declarations;
                if ((BlockImpl.REQUIRES_DECLARE_BLOCK.contains((Object)ctx.dialect()) || BlockImpl.NO_SUPPORT_NESTED_DECLARATIONS.contains((Object)ctx.dialect()) || BlockImpl.NO_SUPPORT_BLOCK_SCOPE.contains((Object)ctx.dialect())) && (declarations = (BlockImpl.ScopeDeclarations)ctx.data(Tools.ExtendedDataKey.DATA_SCOPE_DECLARATIONS)) != null) {
                    declarations.variables.put(this.variable, DSL.declare(this.variable));
                    if (this.value == null) break;
                    ctx.visit(DSL.statements(this.variable.set(this.value)));
                    break;
                }
                if (this.value != null && (NO_SUPPORT_DEFAULT.contains((Object)ctx.dialect()) || NO_SUPPORT_DEFAULT_EXPRESSIONS.contains((Object)ctx.dialect()) && !(this.value instanceof Param))) {
                    ctx.visit(DSL.statements(DSL.declare(this.variable), this.variable.set(this.value)));
                    break;
                }
                this.acceptDeclareNameAndType(ctx);
                if (this.value == null) break;
                if (NO_SUPPORT_DEFAULT_KEYWORD.contains((Object)ctx.dialect())) {
                    ctx.sql(" = ").visit(this.value);
                    break;
                }
                ctx.sql(' ').visit(Keywords.K_DEFAULT).sql(' ').visit(this.value, ParamType.INLINED);
            }
        }
    }

    private final void acceptDeclareNameAndType(Context<?> ctx) {
        switch (ctx.family()) {
            case EXASOL: 
            case ORACLE: 
            case POSTGRES: 
            case YUGABYTEDB: {
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_DEFINE).sql(' ');
                break;
            }
            default: {
                ctx.visit(Keywords.K_DECLARE).sql(' ');
            }
        }
        this.acceptNameAndType(ctx);
    }

    private final void acceptNameAndType(Context<?> ctx) {
        ctx.visit(ctx.family() == SQLDialect.FIREBIRD ? this.variable.getUnqualifiedName() : this.variable).sql(' ');
        Tools.toSQLDDLTypeDeclaration(ctx, this.variable.getDataType());
    }

    @Override
    public final Variable<T> $variable() {
        return this.variable;
    }

    @Override
    public final Field<T> $value() {
        return this.value;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.variable, this.value);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.variable, this.value, Assignment::new, replacer);
    }
}

