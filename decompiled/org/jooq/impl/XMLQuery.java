/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.XML;
import org.jooq.XMLQueryPassingStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.XMLTable;

final class XMLQuery
extends AbstractField<XML>
implements XMLQueryPassingStep,
QOM.XMLQuery {
    private final Field<String> xpath;
    private final Field<XML> passing;
    private final QOM.XMLPassingMechanism passingMechanism;

    XMLQuery(Field<String> xpath) {
        this(xpath, null, null);
    }

    private XMLQuery(Field<String> xpath, Field<XML> passing, QOM.XMLPassingMechanism passingMechanism) {
        super(Names.N_XMLQUERY, SQLDataType.XML);
        this.xpath = xpath;
        this.passing = passing;
        this.passingMechanism = passingMechanism;
    }

    @Override
    public final Field<XML> passing(XML xml) {
        return this.passing(Tools.field(xml));
    }

    @Override
    public final Field<XML> passing(Field<XML> xml) {
        return new XMLQuery(this.xpath, xml, null);
    }

    @Override
    public final Field<XML> passingByRef(XML xml) {
        return this.passingByRef(Tools.field(xml));
    }

    @Override
    public final Field<XML> passingByRef(Field<XML> xml) {
        return new XMLQuery(this.xpath, xml, QOM.XMLPassingMechanism.BY_REF);
    }

    @Override
    public final Field<XML> passingByValue(XML xml) {
        return this.passingByValue(Tools.field(xml));
    }

    @Override
    public final Field<XML> passingByValue(Field<XML> xml) {
        return new XMLQuery(this.xpath, xml, QOM.XMLPassingMechanism.BY_VALUE);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case POSTGRES: {
                Field<XML> x = DSL.field(DSL.name("x"), SQLDataType.XML);
                ctx.sql('(').visit(DSL.select(DSL.xmlagg(x)).from((TableLike<?>)DSL.unnest(DSL.field("xpath({0}, {1})", SQLDataType.XML.getArrayDataType(), new QueryPart[]{this.xpath, this.passing})).as("t", x.getName()))).sql(')');
                break;
            }
            case SQLSERVER: {
                ctx.visit(this.passing).sql('.').visit(Keywords.K_QUERY).sql('(').paramType(ParamType.INLINED, c -> c.visit(this.xpath)).sql(')');
                break;
            }
            default: {
                ctx.visit(Names.N_XMLQUERY).sqlIndentStart('(');
                XMLTable.acceptXPath(ctx, this.xpath);
                XMLTable.acceptPassing(ctx, this.passing, this.passingMechanism);
                if (ctx.family() == SQLDialect.ORACLE) {
                    ctx.formatSeparator().visit(Keywords.K_RETURNING).sql(' ').visit(Keywords.K_CONTENT);
                }
                ctx.sqlIndentEnd(')');
            }
        }
    }

    @Override
    public final Field<String> $xpath() {
        return this.xpath;
    }

    @Override
    public final Field<XML> $passing() {
        return this.passing;
    }

    @Override
    public final QOM.XMLPassingMechanism $passingMechanism() {
        return this.passingMechanism;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.xpath, this.passing);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.xpath, this.passing, (x, p) -> new XMLQuery((Field<String>)x, this.passing, this.passingMechanism), replacer);
    }
}

