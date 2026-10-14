/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.TableLike;
import org.jooq.TableOptions;
import org.jooq.XML;
import org.jooq.XMLTableColumnPathStep;
import org.jooq.XMLTablePassingStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class XMLTable
extends AbstractTable<Record>
implements XMLTablePassingStep,
XMLTableColumnPathStep,
QOM.UNotYetImplemented {
    private final Field<String> xpath;
    private final Field<XML> passing;
    private final QOM.XMLPassingMechanism passingMechanism;
    private final QueryPartList<XMLTableColumn> columns;
    private final boolean hasOrdinality;
    private transient FieldsImpl<Record> fields;

    XMLTable(Field<String> xpath) {
        this(xpath, null, null, null, false);
    }

    private XMLTable(Field<String> xpath, Field<XML> passing, QOM.XMLPassingMechanism passingMechanism, QueryPartList<XMLTableColumn> columns, boolean hasOrdinality) {
        super(TableOptions.expression(), Names.N_XMLTABLE);
        this.xpath = xpath;
        this.passing = passing;
        this.passingMechanism = passingMechanism;
        this.columns = columns == null ? new QueryPartList() : columns;
        this.hasOrdinality = hasOrdinality;
    }

    @Override
    public final XMLTable passing(XML xml) {
        return this.passing(Tools.field(xml));
    }

    @Override
    public final XMLTable passing(Field<XML> xml) {
        return new XMLTable(this.xpath, xml, null, this.columns, this.hasOrdinality);
    }

    @Override
    public final XMLTable passingByRef(XML xml) {
        return this.passingByRef(Tools.field(xml));
    }

    @Override
    public final XMLTable passingByRef(Field<XML> xml) {
        return new XMLTable(this.xpath, xml, QOM.XMLPassingMechanism.BY_REF, this.columns, this.hasOrdinality);
    }

    @Override
    public final XMLTable passingByValue(XML xml) {
        return this.passingByRef(Tools.field(xml));
    }

    @Override
    public final XMLTable passingByValue(Field<XML> xml) {
        return new XMLTable(this.xpath, xml, QOM.XMLPassingMechanism.BY_VALUE, this.columns, this.hasOrdinality);
    }

    @Override
    public final XMLTable column(String name) {
        return this.column(DSL.name(name));
    }

    @Override
    public final XMLTable column(Name name) {
        return this.column((Field)DSL.field(name));
    }

    @Override
    public final XMLTable column(Field<?> name) {
        return this.column((Field)name, name.getDataType());
    }

    @Override
    public final XMLTable column(String name, DataType<?> type) {
        return this.column(DSL.name(name), (DataType)type);
    }

    @Override
    public final XMLTable column(Name name, DataType<?> type) {
        return this.column((Field)DSL.field(name), (DataType)type);
    }

    @Override
    public final XMLTable column(Field<?> name, DataType<?> type) {
        QueryPartList<XMLTableColumn> c = new QueryPartList<XMLTableColumn>((Iterable<XMLTableColumn>)this.columns);
        c.add(new XMLTableColumn(name, type, false, null));
        return new XMLTable(this.xpath, this.passing, this.passingMechanism, c, this.hasOrdinality);
    }

    @Override
    public final XMLTable forOrdinality() {
        return this.path0(true, null);
    }

    @Override
    public final XMLTable path(String path) {
        return this.path0(false, path);
    }

    private final XMLTable path0(boolean forOrdinality, String path) {
        QueryPartList<XMLTableColumn> c = new QueryPartList<XMLTableColumn>((Iterable<XMLTableColumn>)this.columns);
        int i = c.size() - 1;
        XMLTableColumn last = (XMLTableColumn)c.get(i);
        c.set(i, new XMLTableColumn(last.field, last.type, forOrdinality, path));
        return new XMLTable(this.xpath, this.passing, this.passingMechanism, c, this.hasOrdinality || forOrdinality);
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<Record> fields0() {
        if (this.fields == null) {
            this.fields = new FieldsImpl(Tools.map(this.columns, c -> c.field.getDataType() == c.type ? c.field : DSL.field(c.field.getQualifiedName(), c.type)));
        }
        return this.fields;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLSERVER: {
                this.acceptSQLServer(ctx);
                break;
            }
            default: {
                this.acceptStandard(ctx);
            }
        }
    }

    @Pro
    private final void acceptSQLServer(Context<?> ctx) {
        Name t1x = DSL.name("t1", "x");
        Name t2x = DSL.name("t2", "x");
        Tools.visitSubquery(ctx, DSL.select(Tools.map(this.columns, col -> col.forOrdinality ? DSL.rowNumber().over().as((Field)col.field) : DSL.field("{0}.value({1}, {2})", col.field.getDataType(), new QueryPart[]{t2x, DSL.inline("(" + (col.path != null ? col.path : col.field.getName()) + ")[1]"), DSL.inline(col.type.getCastTypeName(ctx.configuration()))}).as((Field)col.field))).from((TableLike<?>)DSL.select(DSL.cast(this.passing, SQLDataType.XML)).asTable("t1", "x").crossApply(DSL.table("{0}.nodes({1})", new QueryPart[]{t1x, DSL.inlined(this.xpath)}).as("t2", "x"))), 1);
    }

    private final void acceptStandard(Context<?> ctx) {
        ctx.visit(Keywords.K_XMLTABLE).sqlIndentStart('(');
        XMLTable.acceptXPath(ctx, this.xpath);
        if (this.passing != null) {
            XMLTable.acceptPassing(ctx, this.passing, this.passingMechanism);
        }
        ctx.formatSeparator().visit(Keywords.K_COLUMNS).separatorRequired(true).visit(this.columns);
        ctx.sqlIndentEnd(')');
    }

    static final void acceptXPath(Context<?> ctx, Field<String> xpath) {
        if (ctx.family() == SQLDialect.DB2 || ctx.family() == SQLDialect.HANA) {
            ctx.visit(xpath, ParamType.INLINED);
        } else {
            ctx.visit(xpath);
        }
    }

    static final void acceptPassing(Context<?> ctx, Field<XML> passing, QOM.XMLPassingMechanism passingMechanism) {
        ctx.formatSeparator().visit(Keywords.K_PASSING);
        if (passingMechanism == QOM.XMLPassingMechanism.BY_REF) {
            ctx.sql(' ').visit(Keywords.K_BY).sql(' ').visit(Keywords.K_REF);
        } else if (passingMechanism == QOM.XMLPassingMechanism.BY_VALUE) {
            ctx.sql(' ').visit(Keywords.K_BY).sql(' ').visit(Keywords.K_VALUE);
        }
        if (ctx.family() == SQLDialect.HANA) {
            ctx.sql(' ').visit(passing, ParamType.INLINED);
        } else {
            ctx.sql(' ').visit(passing);
        }
    }

    private static class XMLTableColumn
    extends AbstractQueryPart
    implements QOM.UNotYetImplemented {
        final Field<?> field;
        final DataType<?> type;
        final boolean forOrdinality;
        final String path;

        XMLTableColumn(Field<?> field2, DataType<?> type, boolean forOrdinality, String path) {
            this.field = field2;
            this.type = type;
            this.forOrdinality = forOrdinality;
            this.path = path;
        }

        @Override
        public final void accept(Context<?> ctx) {
            ctx.qualify(false, c -> c.visit(this.field)).sql(' ');
            if (this.forOrdinality) {
                ctx.visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_ORDINALITY);
            } else {
                Tools.toSQLDDLTypeDeclaration(ctx, this.type);
            }
            if (this.path != null) {
                ctx.sql(' ').visit(Keywords.K_PATH).sql(' ').visit(DSL.inline(this.path));
            } else if (!this.forOrdinality && ctx.family() == SQLDialect.HANA) {
                ctx.sql(' ').visit(Keywords.K_PATH).sql(' ').visit(DSL.inline(this.field.getName()));
            }
        }
    }
}

