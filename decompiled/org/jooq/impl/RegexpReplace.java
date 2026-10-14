/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class RegexpReplace
extends AbstractField<String>
implements QOM.UNotYetImplemented {
    private final Field<String> field;
    private final Field<String> pattern;
    private final Field<String> replacement;
    private final boolean all;

    RegexpReplace(Field<String> field2, Field<String> pattern, Field<String> replacement, boolean all) {
        super(Names.N_REGEXP_REPLACE, field2.getDataType());
        this.field = field2;
        this.pattern = pattern;
        this.replacement = replacement;
        this.all = all;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case MEMSQL: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Names.N_REGEXP_REPLACE).sql('(').visit(this.field).sql(", ").visit(this.pattern).sql(", ").visit(this.replacement);
                if (this.all) {
                    ctx.sql(", 'g')");
                    break;
                }
                ctx.sql(')');
                break;
            }
            case JAVA: {
                ctx.sql(this.all ? "regexpReplaceAll(" : "regexpReplaceFirst(").visit(this.field).sql(", ").visit(this.pattern).sql(", ").visit(this.replacement).sql(')');
                break;
            }
            case HANA: {
                ctx.visit(Names.N_REPLACE_REGEXPR).sql('(').visit(this.pattern).sql(' ').visit(Keywords.K_IN).sql(' ').visit(this.field).sql(' ').visit(Keywords.K_WITH).sql(' ').visit(this.replacement);
                if (!this.all) {
                    ctx.sql(' ').visit(Keywords.K_OCCURRENCE).sql(" 1");
                }
                ctx.sql(')');
                break;
            }
            case INFORMIX: {
                ctx.visit(Names.N_REGEX_REPLACE).sql('(').visit(this.field).sql(", ").visit(this.pattern).sql(", ").visit(this.replacement);
                if (this.all) {
                    ctx.sql(')');
                    break;
                }
                ctx.sql(", 1)");
                break;
            }
            default: {
                ctx.visit(Names.N_REGEXP_REPLACE).sql('(').visit(this.field).sql(", ").visit(this.pattern).sql(", ").visit(this.replacement);
                if (this.all) {
                    ctx.sql(')');
                    break;
                }
                ctx.sql(", 1, 1)");
            }
        }
    }

    static String replacement(Context<?> ctx, int group) {
        switch (ctx.family()) {
            case MYSQL: {
                return "$" + group;
            }
        }
        return "\\" + group;
    }
}

