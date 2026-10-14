/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.Variable;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.Tools;

@Pro
final class Assignment<T>
extends AbstractStatement
implements QOM.Assignment<T> {
    final Variable<T> target;
    final Field<T> value;

    Assignment(Variable<T> target, Field<T> value) {
        this.target = target;
        this.value = value;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: {
                Field<T> field2 = this.value;
                if (field2 instanceof ScalarSubquery) {
                    ScalarSubquery s2 = (ScalarSubquery)field2;
                    ArrayList list = new ArrayList();
                    ctx.data("org.jooq.impl.BlockImpl.statement-variables", list);
                    ctx.sqlIndentStart("try (PreparedStatement s = c.prepareStatement(").sql('\"').sql(ctx.render(s2.query).replace("\"", "\\\"").replace("\n", "\\n\" +\n\"")).sql('\"').sqlIndentEnd(")) {").formatIndentStart().formatSeparator();
                    for (int j = 0; j < list.size(); ++j) {
                        ctx.sql("s.setObject(" + (j + 1) + ", ").sql(((Variable)list.get(j)).getName()).sql(");").formatSeparator();
                    }
                    ctx.sql("try (ResultSet rs = s.executeQuery()) {").formatIndentStart().formatSeparator().sql("if (rs.next())").formatIndentStart().formatSeparator().sql(this.target.getName()).sql(" = ").sql('(').sql(BlockImpl.variableType(this.target)).sql(") rs.getObject(1);").formatIndentEnd().formatIndentEnd().formatSeparator().sql('}').formatIndentEnd().formatSeparator().sql('}');
                    ctx.data().remove("org.jooq.impl.BlockImpl.statement-variables");
                    break;
                }
                ctx.sql(this.target.getName()).sql(" = ").visit(this.value);
                break;
            }
            case HANA: 
            case ORACLE: {
                Field<T> field3 = this.value;
                if (field3 instanceof ScalarSubquery) {
                    ScalarSubquery s3 = (ScalarSubquery)field3;
                    ctx.data(Tools.SimpleDataKey.DATA_SELECT_INTO_VARIABLES, this.target, c -> c.visit(s2.query));
                    break;
                }
                ctx.visit(this.target).sql(ctx.family() == SQLDialect.ORACLE ? " := " : " = ").visit(this.value);
                break;
            }
            case EXASOL: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(this.target).sql(" := ").visit(this.value);
                break;
            }
            case FIREBIRD: {
                ctx.visit(this.target).sql(" = ").visit(this.value);
                break;
            }
            case INFORMIX: {
                ctx.visit(Keywords.K_LET).sql(' ').visit(this.target).sql(" = ").visit(this.value);
                break;
            }
            default: {
                ctx.visit(Keywords.K_SET).sql(' ').visit(this.target).sql(" = ").visit(this.value);
            }
        }
    }

    @Override
    public final Variable<T> $variable() {
        return this.target;
    }

    @Override
    public final Field<T> $value() {
        return this.value;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.target, this.value);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.target, this.value, Assignment::new, replacer);
    }
}

