/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import java.util.ArrayList;

public class AstListData
extends SimpleNode {
    public AstListData(int id) {
        super(id);
    }

    @Override
    public Object getValue(EvaluationContext ctx) {
        ArrayList<Object> list = new ArrayList<Object>();
        int paramCount = this.jjtGetNumChildren();
        for (int i = 0; i < paramCount; ++i) {
            list.add(this.children[i].getValue(ctx));
        }
        return list;
    }
}

