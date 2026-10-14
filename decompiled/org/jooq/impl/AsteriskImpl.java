/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Asterisk;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.Tools;

final class AsteriskImpl
extends AbstractQueryPart
implements Asterisk {
    static final Lazy<AsteriskImpl> INSTANCE = Lazy.of(() -> new AsteriskImpl(new QueryPartList()));
    static final Set<SQLDialect> SUPPORT_NATIVE_EXCEPT = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.H2, SQLDialect.SNOWFLAKE);
    static final Set<SQLDialect> NO_SUPPORT_UNQUALIFIED_COMBINED = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE);
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_QUALIFIED_EXCEPT = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.SNOWFLAKE);
    final QueryPartList<Field<?>> fields;

    private AsteriskImpl(QueryPartList<Field<?>> fields) {
        this.fields = fields;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("asterisk()");
            if (!this.fields.isEmpty()) {
                ctx.sql(".except(").visit(this.fields).sql(')');
            }
            return;
        }
        ctx.sql('*');
        if (!this.fields.isEmpty()) {
            if (NO_SUPPORT_QUALIFIED_EXCEPT.contains((Object)ctx.dialect())) {
                ctx.sql(' ').visit(AsteriskImpl.keyword(ctx)).sql(" (").qualify(false, c -> c.visit(this.fields)).sql(')');
            } else {
                ctx.sql(' ').visit(Keywords.K_EXCEPT).sql(" (").visit(this.fields).sql(')');
            }
        }
    }

    @Pro
    static final Keyword keyword(Context<?> ctx) {
        switch (ctx.family()) {
            case SNOWFLAKE: {
                return Keywords.K_EXCLUDE;
            }
        }
        return Keywords.K_EXCEPT;
    }

    @Override
    public final Asterisk except(String ... fieldNames) {
        return this.except(Tools.fieldsByName(fieldNames));
    }

    @Override
    public final Asterisk except(Name ... fieldNames) {
        return this.except(Tools.fieldsByName(fieldNames));
    }

    @Override
    public final Asterisk except(Field<?> ... f) {
        QueryPartList list = new QueryPartList();
        list.addAll((Collection<Field<?>>)this.fields);
        list.addAll((Collection<Field<?>>)Arrays.asList(f));
        return new AsteriskImpl(list);
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $except() {
        return QOM.unmodifiable(this.fields);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.fields);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.fields, AsteriskImpl::new, replacer);
    }
}

