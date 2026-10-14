/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public final class AstCompositeExpression
extends SimpleNode {
    public AstCompositeExpression(int id) {
        super(id);
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        return String.class;
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        StringBuffer sb = new StringBuffer(16);
        Object obj = null;
        if (this.children != null) {
            for (int i = 0; i < this.children.length; ++i) {
                obj = this.children[i].getValue(ctx);
                if (obj == null) continue;
                sb.append(obj);
            }
        }
        return sb.toString();
    }
}

