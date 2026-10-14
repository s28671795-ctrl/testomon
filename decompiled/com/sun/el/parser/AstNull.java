/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public final class AstNull
extends SimpleNode {
    public AstNull(int id) {
        super(id);
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        return null;
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        return null;
    }
}

