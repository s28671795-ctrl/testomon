/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.XML;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

@Pro
final class ForXML
extends AbstractQueryPart
implements QOM.UNotYetImplemented {
    static final Lazy<Field<XML>> FIELD = Lazy.of(() -> DSL.field(DSL.name("xml"), SQLDataType.XML));
    boolean forXMLRaw;
    String forXMLRawElement;
    boolean forXMLAuto;
    boolean forXMLPath;
    String forXMLPathElement;
    boolean forXMLExplicit;
    boolean forXMLElements;
    boolean forXMLElementsXsinil;
    boolean forXMLElementsAbsent;
    boolean forXMLBinaryBase64;
    boolean forXMLType;
    boolean forXMLRoot;
    String forXMLRootElement;

    ForXML() {
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_XML);
        if (this.forXMLRaw) {
            ctx.sql(' ').visit(Keywords.K_RAW);
            if (this.forXMLRawElement != null) {
                ctx.sql(" (").visit(DSL.inline(this.forXMLRawElement)).sql(')');
            }
        } else if (this.forXMLAuto) {
            ctx.sql(' ').visit(Keywords.K_AUTO);
        } else if (this.forXMLPath) {
            ctx.sql(' ').visit(Keywords.K_PATH);
            if (this.forXMLPathElement != null) {
                ctx.sql(" (").visit(DSL.inline(this.forXMLPathElement)).sql(')');
            }
        } else if (this.forXMLExplicit) {
            ctx.sql(' ').visit(Keywords.K_EXPLICIT);
        }
        if (this.forXMLElements) {
            ctx.sql(", ").visit(Keywords.K_ELEMENTS);
        } else if (this.forXMLElementsXsinil) {
            ctx.sql(", ").visit(Keywords.K_ELEMENTS).sql(' ').visit(Keywords.K_XSINIL);
        } else if (this.forXMLElementsAbsent) {
            ctx.sql(", ").visit(Keywords.K_ELEMENTS).sql(' ').visit(Keywords.K_ABSENT);
        }
        if (this.forXMLBinaryBase64) {
            ctx.sql(", ").visit(Keywords.K_BINARY).sql(' ').visit(Keywords.K_BASE64);
        }
        if (this.forXMLType) {
            ctx.sql(", ").visit(Keywords.K_TYPE);
        }
        if (this.forXMLRoot) {
            ctx.sql(", ").visit(Keywords.K_ROOT);
            if (this.forXMLRootElement != null) {
                ctx.sql(" (").visit(DSL.inline(this.forXMLRootElement)).sql(')');
            }
        }
    }
}

