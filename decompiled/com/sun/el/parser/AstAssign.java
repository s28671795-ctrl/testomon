/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public class AstAssign
extends SimpleNode {
    public AstAssign(int id) {
        super(id);
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        Object value = this.children[1].getValue(ctx);
        this.children[0].setValue(ctx, value);
        return value;
    }
}

