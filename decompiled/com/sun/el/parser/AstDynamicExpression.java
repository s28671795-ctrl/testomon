/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public final class AstDynamicExpression
extends SimpleNode {
    public AstDynamicExpression(int id) {
        super(id);
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        return this.children[0].getType(ctx);
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        return this.children[0].getValue(ctx);
    }

    @Override
    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        return this.children[0].isReadOnly(ctx);
    }

    @Override
    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        this.children[0].setValue(ctx, value);
    }
}

