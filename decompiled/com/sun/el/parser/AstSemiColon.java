/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public class AstSemiColon
extends SimpleNode {
    public AstSemiColon(int id) {
        super(id);
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        this.children[0].getValue(ctx);
        return this.children[1].getValue(ctx);
    }

    @Override
    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        this.children[0].getValue(ctx);
        this.children[1].setValue(ctx, value);
    }
}

