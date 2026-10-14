/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.XML;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;

final class XMLParse
extends AbstractField<XML>
implements QOM.XMLParse {
    private final Field<String> content;
    private final QOM.DocumentOrContent documentOrContent;

    XMLParse(Field<String> content, QOM.DocumentOrContent documentOrContent) {
        super(Names.N_XMLPARSE, SQLDataType.XML);
        this.content = content;
        this.documentOrContent = documentOrContent;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: 
            case TERADATA: {
                if (this.documentOrContent == QOM.DocumentOrContent.DOCUMENT) {
                    XMLParse.acceptStandard(ctx, this.documentOrContent, this.content);
                    break;
                }
                ctx.visit(DSL.xmlquery(DSL.inline("/root/*")).passing(DSL.xmlparseDocument(DSL.cast(DSL.concat(DSL.inline("<root>"), this.content, DSL.inline("</root>")), SQLDataType.VARCHAR))));
                break;
            }
            default: {
                XMLParse.acceptStandard(ctx, this.documentOrContent, this.content);
            }
        }
    }

    private static final void acceptStandard(Context<?> ctx, QOM.DocumentOrContent documentOrContent, Field<String> content) {
        ctx.visit(Names.N_XMLPARSE).sql('(').visit(documentOrContent == QOM.DocumentOrContent.DOCUMENT ? Keywords.K_DOCUMENT : Keywords.K_CONTENT).sql(' ').visit(content);
        if (ctx.family() == SQLDialect.TERADATA) {
            ctx.sql(' ').visit(Keywords.K_PRESERVE).sql(' ').visit(Keywords.K_WHITESPACE);
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $content() {
        return this.content;
    }

    @Override
    public final QOM.DocumentOrContent $documentOrContent() {
        return this.documentOrContent;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.content);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.content, c -> new XMLParse((Field<String>)c, this.documentOrContent), replacer);
    }
}

