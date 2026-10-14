/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Collections;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.XMLAttributes;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.Tools;
import org.jooq.impl.XMLElement;

final class XMLAttributesImpl
extends AbstractQueryPart
implements XMLAttributes {
    static final XMLAttributes EMPTY = new XMLAttributesImpl(Collections.emptyList());
    final SelectFieldList<Field<?>> attributes;

    XMLAttributesImpl(Collection<? extends Field<?>> attributes) {
        this.attributes = new SelectFieldList((Iterable<Field<?>>)attributes);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("xmlattributes(").visit(this.attributes).sql(')');
            return;
        }
        boolean format = ctx.format() && (this.attributes.size() > 1 || this.attributes.size() == 1 && !Tools.isSimple(ctx, Tools.unalias((Field)this.attributes.get(0))));
        ctx.data(Tools.BooleanDataKey.DATA_AS_REQUIRED, true, c -> {
            c.visit(Names.N_XMLATTRIBUTES).sql('(');
            if (format) {
                c.formatIndentStart().formatNewLine();
            }
            c.declareFields(true, x -> x.visit(new SelectFieldList((Iterable<Field<?>>)this.attributes).map(XMLElement.xmlCastMapper(ctx))));
            if (format) {
                c.formatIndentEnd().formatNewLine();
            }
            c.sql(')');
        });
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $attributes() {
        return QOM.unmodifiable(this.attributes);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$attributes());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$attributes(), XMLAttributesImpl::new, replacer);
    }
}

