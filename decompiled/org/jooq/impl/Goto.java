/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Label;
import org.jooq.Pro;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.tools.StringUtils;

@Pro
final class Goto
extends AbstractStatement
implements QOM.Goto {
    final Label label;

    Goto(Label label) {
        this.label = label;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(Keywords.K_GOTO).sql(' ').visit(this.label);
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("goto_(");
        ctx.visit(this.label);
        ctx.sql(')');
    }

    @Override
    public final Label $arg1() {
        return this.label;
    }

    @Override
    public final QOM.Goto $arg1(Label newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Label, ? extends QOM.Goto> $constructor() {
        return a1 -> new Goto((Label)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Goto) {
            QOM.Goto o = (QOM.Goto)that;
            return StringUtils.equals(this.$label(), o.$label());
        }
        return super.equals(that);
    }
}

