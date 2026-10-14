/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public class AstMethodArguments
extends SimpleNode {
    public AstMethodArguments(int id) {
        super(id);
    }

    Class<?>[] getParamTypes() {
        return null;
    }

    public Object[] getParameters(EvaluationContext ctx) throws ELException {
        if (this.children == null) {
            return new Object[0];
        }
        Object[] obj = new Object[this.children.length];
        for (int i = 0; i < obj.length; ++i) {
            obj[i] = this.children[i].getValue(ctx);
        }
        return obj;
    }

    public int getParameterCount() {
        return this.children == null ? 0 : this.children.length;
    }

    @Override
    public boolean isParametersProvided() {
        return true;
    }
}

