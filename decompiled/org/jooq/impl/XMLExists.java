/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.XML;
import org.jooq.XMLExistsPassingStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.XMLTable;

final class XMLExists
extends AbstractCondition
implements XMLExistsPassingStep,
QOM.XMLExists,
QOM.UNotYetImplemented {
    private final Field<String> xpath;
    private final Field<XML> passing;
    private final QOM.XMLPassingMechanism passingMechanism;

    XMLExists(Field<String> xpath) {
        this(xpath, null, null);
    }

    private XMLExists(Field<String> xpath, Field<XML> passing, QOM.XMLPassingMechanism passingMechanism) {
        this.xpath = xpath;
        this.passing = passing;
        this.passingMechanism = passingMechanism;
    }

    @Override
    public final Condition passing(XML xml) {
        return this.passing(Tools.field(xml));
    }

    @Override
    public final Condition passing(Field<XML> xml) {
        return new XMLExists(this.xpath, xml, null);
    }

    @Override
    public final Condition passingByRef(XML xml) {
        return this.passingByRef(Tools.field(xml));
    }

    @Override
    public final Condition passingByRef(Field<XML> xml) {
        return new XMLExists(this.xpath, xml, QOM.XMLPassingMechanism.BY_REF);
    }

    @Override
    public final Condition passingByValue(XML xml) {
        return this.passingByRef(Tools.field(xml));
    }

    @Override
    public final Condition passingByValue(Field<XML> xml) {
        return new XMLExists(this.xpath, xml, QOM.XMLPassingMechanism.BY_VALUE);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLSERVER: {
                ctx.sql('(').visit(this.passing).sql('.').visit(Keywords.K_EXIST).sql('(').paramType(ParamType.INLINED, c -> c.visit(this.xpath)).sql(") = 1)");
                break;
            }
            default: {
                ctx.visit(Keywords.K_XMLEXISTS).sqlIndentStart('(');
                XMLTable.acceptXPath(ctx, this.xpath);
                XMLTable.acceptPassing(ctx, this.passing, this.passingMechanism);
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
        return QOM.replace(this, this.xpath, this.passing, (x, p) -> new XMLExists((Field<String>)x, this.passing, this.passingMechanism), replacer);
    }
}

