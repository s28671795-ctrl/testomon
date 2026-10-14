/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.SelectField;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldAlias;
import org.jooq.impl.JSONEntryImpl;
import org.jooq.impl.Multiset;
import org.jooq.impl.Names;
import org.jooq.impl.RowAsField;
import org.jooq.impl.Tools;

abstract class AbstractRowAsField<R extends Record>
extends AbstractField<R>
implements AutoAlias<SelectField<R>> {
    AbstractRowAsField(Name name, DataType<R> type) {
        super(name, type);
    }

    abstract Fields fields0();

    abstract Class<R> getRecordType();

    final AbstractRow<R> emulatedFields(Configuration configuration) {
        return Tools.row0(Tools.map(this.fields0().fields(), x -> x.as(Tools.sanitiseName(configuration, String.valueOf(this.getUnqualifiedName().unquotedName()) + configuration.settings().getNamePathSeparator() + x.getName())), Field[]::new));
    }

    @Override
    final int projectionSize() {
        int result = 0;
        for (Field<?> field2 : this.fields0().fields()) {
            result += ((AbstractField)field2).projectionSize();
        }
        return result;
    }

    @Override
    public final boolean declaresFields() {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (AbstractRowAsField.forceMultisetContent(ctx, () -> this.getDataType().getRow().size() > 1)) {
            AbstractRowAsField.acceptMultisetContent(ctx, this.getDataType().getRow(), this, this::acceptDefault);
        } else {
            this.acceptDefault(ctx);
        }
    }

    static final boolean forceMultisetContent(Context<?> ctx, BooleanSupplier degreeCheck) {
        return Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT)) || ctx.subquery() && RowAsField.NO_NATIVE_SUPPORT.contains((Object)ctx.dialect()) && !ctx.predicandSubquery() && !ctx.derivedTableSubquery() && !ctx.setOperationSubquery() && degreeCheck.getAsBoolean();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static final void acceptMultisetContent(Context<?> ctx, Row row, Field<?> field2, Consumer<? super Context<?>> acceptDefault) {
        Object previous = ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT);
        try {
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, true);
            Name alias = field2.getUnqualifiedName();
            switch (Tools.emulateMultiset(ctx.configuration())) {
                case JSON: {
                    switch (ctx.family()) {
                        case SQLSERVER: 
                        case SQLDATAWAREHOUSE: {
                            ctx.visit(AbstractRowAsField.alias(ctx, alias, JSONEntryImpl.unescapeNestedJSON(ctx, DSL.field(DSL.select(Tools.map(row.fields(), (f, i) -> f.as(Tools.fieldNameString(i)))).forJSON().path().withoutArrayWrapper().includeNullValues()))));
                            return;
                        }
                        case DB2: {
                            ctx.visit(AbstractRowAsField.alias(ctx, alias, Multiset.returningClob(ctx, DSL.jsonObject(Tools.map(row.fields(), (f, i) -> DSL.key(Tools.fieldNameString(i)).value(f))))));
                            return;
                        }
                    }
                    ctx.visit(AbstractRowAsField.alias(ctx, alias, Multiset.returningClob(ctx, DSL.jsonArray(row.fields()).nullOnNull())));
                    return;
                }
                case JSONB: {
                    switch (ctx.family()) {
                        case SQLSERVER: 
                        case SQLDATAWAREHOUSE: {
                            ctx.visit(AbstractRowAsField.alias(ctx, alias, JSONEntryImpl.unescapeNestedJSON(ctx, DSL.field(DSL.select(Tools.map(row.fields(), (f, i) -> f.as(Tools.fieldNameString(i)))).forJSONB().path().withoutArrayWrapper().includeNullValues()))));
                            return;
                        }
                        case DB2: {
                            ctx.visit(AbstractRowAsField.alias(ctx, alias, Multiset.returningClob(ctx, DSL.jsonbObject(Tools.map(row.fields(), (f, i) -> DSL.key(Tools.fieldNameString(i)).value(f))))));
                            return;
                        }
                    }
                    ctx.visit(AbstractRowAsField.alias(ctx, alias, Multiset.returningClob(ctx, DSL.jsonbArray(row.fields()).nullOnNull())));
                    return;
                }
                case XML: {
                    switch (ctx.family()) {
                        case SQLSERVER: 
                        case SQLDATAWAREHOUSE: {
                            ctx.visit(AbstractRowAsField.alias(ctx, alias, DSL.field(DSL.select(Tools.map(row.fields(), (f, i) -> f.as(Tools.fieldNameString(i)))).forXML().raw("record").elementsXsinil().type().binaryBase64())));
                            return;
                        }
                    }
                    ctx.visit(AbstractRowAsField.alias(ctx, alias, DSL.xmlelement(Names.N_RECORD, Tools.map(row.fields(), (f, i) -> DSL.xmlelement(Tools.fieldNameString(i), f)))));
                    return;
                }
                default: {
                    acceptDefault.accept(ctx);
                    return;
                }
            }
        }
        finally {
            ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT, previous);
        }
    }

    @Override
    public final SelectField<R> autoAlias(Context<?> ctx, SelectField<R> s2) {
        if (RowAsField.NO_NATIVE_SUPPORT.contains((Object)ctx.dialect())) {
            return s2;
        }
        if (AbstractRowAsField.forceMultisetContent(ctx, () -> this.getDataType().getRow().size() > 1)) {
            return s2;
        }
        return new FieldAlias<R>(DSL.field(s2), this.getUnqualifiedName());
    }

    private static final Field<?> alias(Context<?> ctx, Name alias, Field<?> field2) {
        return ctx.declareFields() ? field2.as(alias) : field2;
    }

    abstract void acceptDefault(Context<?> var1);
}

