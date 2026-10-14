/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import javax.el.ELException;

public class BooleanNode
extends SimpleNode {
    public BooleanNode(int i) {
        super(i);
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        return Boolean.class;
    }
}

