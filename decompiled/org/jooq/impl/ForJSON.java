/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Pro;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

@Pro
final class ForJSON
extends AbstractQueryPart
implements QOM.UNotYetImplemented {
    static final Lazy<Field<JSON>> F_JSON = Lazy.of(() -> DSL.field(DSL.name("json"), SQLDataType.JSON));
    static final Lazy<Field<JSONB>> F_JSONB = Lazy.of(() -> DSL.field(DSL.name("json"), SQLDataType.JSONB));
    boolean forJSONB;
    boolean forJSONAuto;
    boolean forJSONPath;
    boolean forJSONRoot;
    String forJSONRootElement;
    boolean forJSONIncludeNullValues;
    boolean forJSONWithoutArrayWrapper;

    ForJSON() {
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_JSON);
        if (this.forJSONAuto) {
            ctx.sql(' ').visit(Keywords.K_AUTO);
        } else if (this.forJSONPath) {
            ctx.sql(' ').visit(Keywords.K_PATH);
        }
        if (this.forJSONRoot) {
            ctx.sql(", ").visit(Keywords.K_ROOT);
            if (this.forJSONRootElement != null) {
                ctx.sql(" (").visit(DSL.inline(this.forJSONRootElement)).sql(')');
            }
        }
        if (this.forJSONIncludeNullValues) {
            ctx.sql(", ").visit(Keywords.K_INCLUDE_NULL_VALUES);
        }
        if (this.forJSONWithoutArrayWrapper) {
            ctx.sql(", ").visit(Keywords.K_WITHOUT_ARRAY_WRAPPER);
        }
    }
}

