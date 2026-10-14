/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

@Pro
final class OracleJSONKeys
extends AbstractStatement
implements QOM.UTransient {
    OracleJSONKeys() {
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_FUNCTION).sql(' ').visit(Names.N_JSON_KEYS).sql("(j ").visit(SQLDataType.CLOB).sql(") ").visit(Keywords.K_RETURN).sql(' ').visit(SQLDataType.CLOB).sql(' ').visit(Keywords.K_AS).formatIndentStart().formatSeparator().sql("o json_object_t;").formatSeparator().sql("k json_key_list;").formatSeparator().sql("r ").visit(SQLDataType.CLOB).sql(';').formatIndentEnd().formatSeparator().visit(Keywords.K_BEGIN).formatIndentStart().formatSeparator().sql("o := json_object_t(j);").formatSeparator().sql("k := o.get_keys;").formatSeparator().visit(DSL.select(DSL.coalesce(DSL.jsonArrayAgg(DSL.field(DSL.unquotedName("column_value"), SQLDataType.CLOB)), new Field[]{DSL.jsonArray(new Field[0])})).into(DSL.variable(DSL.unquotedName("r"), SQLDataType.CLOB)).from("table (k);")).formatSeparator().visit(Keywords.K_RETURN).sql(" r;").formatIndentEnd().formatSeparator().visit(Keywords.K_END).sql(';').formatSeparator();
    }
}

