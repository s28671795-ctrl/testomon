/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Label;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.LabelledStatement;
import org.jooq.impl.QOM;

@Pro
final class LabelImpl
extends AbstractNamed
implements Label,
QOM.Label {
    private final Name label;

    LabelImpl(Name label) {
        super(label, null);
        this.label = label;
    }

    @Override
    public final Statement label(Statement statement) {
        return new LabelledStatement(this, statement);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(this.label.unqualifiedName().unquotedName());
                break;
            }
            default: {
                ctx.visit(this.label.unqualifiedName());
            }
        }
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.label);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.label, LabelImpl::new, replacer);
    }
}

