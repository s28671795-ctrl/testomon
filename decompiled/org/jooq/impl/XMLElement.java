/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SelectField;
import org.jooq.Traverser;
import org.jooq.XML;
import org.jooq.XMLAttributes;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.XMLAttributesImpl;

final class XMLElement
extends AbstractField<XML>
implements QOM.XMLElement {
    private final Name elementName;
    private final XMLAttributes attributes;
    private final QueryPartList<Field<?>> content;

    XMLElement(Name elementName, XMLAttributes attributes, Collection<? extends Field<?>> content) {
        super(Names.N_XMLELEMENT, SQLDataType.XML);
        this.elementName = elementName;
        this.attributes = attributes == null ? XMLAttributesImpl.EMPTY : attributes;
        this.content = new QueryPartList((Iterable<Field<?>>)content);
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean hasContent = !this.content.isEmpty();
        boolean hasAttributes = !((XMLAttributesImpl)this.attributes).attributes.isEmpty();
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("xmlelement(").visit(this.elementName);
                if (hasAttributes) {
                    ctx.sql(", ").visit(this.attributes);
                }
                if (hasContent) {
                    ctx.sql(", ").visit(this.content);
                }
                ctx.sql(')');
                return;
            }
            case SQLSERVER: {
                ArrayList<SelectField> f = new ArrayList<SelectField>();
                f.add(DSL.one().as("tag"));
                f.add(DSL.inline(null).as("parent"));
                for (Field field2 : this.attributes.$attributes()) {
                    f.add(field2.as(DSL.quotedName(this.elementName.last() + "!1!" + field2.getName())));
                }
                for (Field field3 : this.content) {
                    f.add(field3.as(DSL.quotedName(this.elementName.last() + "!1")));
                }
                if (f.size() == 2) {
                    f.add(DSL.inline(null).as(DSL.quotedName(this.elementName.last() + "!1")));
                }
                ctx.visit(DSL.field(DSL.select(f).forXML().explicit().type()));
                return;
            }
        }
        boolean format = hasAttributes || !this.content.isSimple(ctx);
        Consumer<Context> accept0 = c -> {
            c.visit(Keywords.K_NAME).sql(' ').visit(this.elementName);
            if (hasAttributes) {
                if (format) {
                    c.sql(',').formatSeparator().visit(this.attributes);
                } else {
                    c.sql(", ").visit(this.attributes);
                }
            }
            if (hasContent) {
                if (format) {
                    c.sql(',').formatSeparator().visit(QueryPartCollectionView.wrap(this.content).map(XMLElement.xmlCastMapper(ctx)));
                } else {
                    c.sql(", ").visit(QueryPartCollectionView.wrap(this.content).map(XMLElement.xmlCastMapper(ctx)));
                }
            }
        };
        ctx.visit(Names.N_XMLELEMENT).sql('(');
        if (format) {
            ctx.formatIndentStart().formatNewLine().data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, true, c -> accept0.accept((Context)c)).formatIndentEnd().formatNewLine();
        } else {
            accept0.accept(ctx);
        }
        ctx.sql(')');
    }

    static final Function<? super Field<?>, ? extends Field<?>> xmlCastMapper(Context<?> ctx) {
        return field2 -> XMLElement.xmlCast(ctx, field2);
    }

    static final Field<?> xmlCast(Context<?> ctx, Field<?> field2) {
        Field<Object> result = field2;
        DataType type = field2.getDataType();
        switch (ctx.family()) {
            case DB2: {
                if (!type.isTimestamp()) break;
                result = DSL.replace(DSL.toChar(Tools.unalias(field2), DSL.inline("YYYY-MM-DD HH24:MI:SS.FF")), DSL.inline(" "), DSL.inline("T"));
                break;
            }
        }
        if (result != field2 && Tools.aliased(field2) != null) {
            return result.as((Field)field2);
        }
        return result;
    }

    @Override
    public final Name $elementName() {
        return this.elementName;
    }

    @Override
    public final XMLAttributes $attributes() {
        return this.attributes;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $content() {
        return QOM.unmodifiable(this.content);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$elementName(), this.$attributes(), this.$content());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$elementName(), this.$attributes(), this.$content(), XMLElement::new, replacer);
    }
}

