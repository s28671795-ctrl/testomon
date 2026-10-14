/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

@Pro
final class ConvertDateTime<T>
extends AbstractField<T>
implements QOM.Convert<T> {
    private final Field<?> field;
    private final int style;
    static final EnumMap<SQLDialect, Map<Integer, Field<String>>> FORMATS = new EnumMap(SQLDialect.class);
    static final EnumMap<SQLDialect, Map<String, Integer>> STYLES = new EnumMap(SQLDialect.class);

    ConvertDateTime(DataType<T> type, Field<?> field2, int style) {
        super(Names.N_CONVERT, type);
        this.field = field2;
        this.style = style;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: 
            case POSTGRES: 
            case REDSHIFT: {
                Field<String> s2 = FORMATS.get((Object)ctx.family()).get(this.style);
                if (s2 == null) break;
                ctx.visit(Names.N_TO_CHAR).sql('(').visit(this.field).sql(", ").visit(s2).sql(')');
                return;
            }
        }
        ctx.visit(DSL.function(Names.N_CONVERT, this.getDataType(), DSL.field(this.getDataType().getCastTypeName(ctx.configuration())), this.field, DSL.inline(this.style)));
    }

    static final void init(String format, int ... styles) {
        for (int style : styles) {
            for (SQLDialect family : Arrays.asList(SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB)) {
                ConvertDateTime.init(format, style, family);
            }
        }
    }

    static final void init(String format, int style, SQLDialect family) {
        if (family == SQLDialect.ORACLE) {
            format = format.replace(".MS", ".FF");
        }
        FORMATS.computeIfAbsent(family, d -> new HashMap()).put(style, DSL.inline(format));
        STYLES.computeIfAbsent(family, d -> new HashMap()).put(format, style);
    }

    @Override
    public final Field<?> $field() {
        return this.field;
    }

    @Override
    public final int $style() {
        return this.style;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$dataType(), this.$field());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$dataType(), this.$field(), this.$style(), ConvertDateTime::new, replacer);
    }

    static {
        ConvertDateTime.init("MM/DD/YY", 1);
        ConvertDateTime.init("MM/DD/YYYY", 101);
        ConvertDateTime.init("YY.MM.DD", 2);
        ConvertDateTime.init("YYYY.MM.DD", 102);
        ConvertDateTime.init("DD/MM/YY", 3);
        ConvertDateTime.init("DD/MM/YYYY", 103);
        ConvertDateTime.init("DD.MM.YY", 4);
        ConvertDateTime.init("DD.MM.YYYY", 104);
        ConvertDateTime.init("DD-MM-YY", 5);
        ConvertDateTime.init("DD-MM-YYYY", 105);
        ConvertDateTime.init("DD MON YY", 6);
        ConvertDateTime.init("DD MON YYYY", 106);
        ConvertDateTime.init("MON DD, YY", 7);
        ConvertDateTime.init("MON DD, YYYY", 107);
        ConvertDateTime.init("HH24:MI:SS", 8, 108);
        ConvertDateTime.init("MON DD YYYY HH:MI:SS.MS AM", 9, 109);
        ConvertDateTime.init("MM-DD-YY", 10);
        ConvertDateTime.init("MM-DD-YYYY", 110);
        ConvertDateTime.init("YY/MM/DD", 11);
        ConvertDateTime.init("YYYY/MM/DD", 111);
        ConvertDateTime.init("YYMMDD", 12);
        ConvertDateTime.init("YYYYMMDD", 112);
        ConvertDateTime.init("DD MON YYYY HH24:MI:SS.MS", 13, 113);
        ConvertDateTime.init("HH24:MI:SS.MS", 14, 114);
        ConvertDateTime.init("YYYY-MM-DD HH24:MI:SS", 20, 120);
        ConvertDateTime.init("YYYY-MM-DD HH24:MI:SS.MS", 21, 121);
        ConvertDateTime.init("YYYY-MM-DD\"T\"HH24:MI:SS.MS", 126);
        ConvertDateTime.init("YYYY-MM-DD\"T\"HH24:MI:SS.MS", 127);
    }
}

