/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.Node;
import com.sun.el.parser.SimpleNode;
import java.util.HashMap;
import java.util.HashSet;
import javax.el.ELException;

public class AstMapData
extends SimpleNode {
    public AstMapData(int id) {
        super(id);
    }

    @Override
    public Object getValue(EvaluationContext ctx) {
        HashSet<Object> set = new HashSet<Object>();
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        int paramCount = this.jjtGetNumChildren();
        for (int i = 0; i < paramCount; ++i) {
            Node entry = this.children[i];
            Object v1 = entry.jjtGetChild(0).getValue(ctx);
            if (entry.jjtGetNumChildren() > 1) {
                map.put(v1, entry.jjtGetChild(1).getValue(ctx));
                continue;
            }
            set.add(v1);
        }
        if (set.size() > 0 && map.size() > 0) {
            throw new ELException("Cannot mix set entry with map entry.");
        }
        if (map.size() > 0) {
            return map;
        }
        return set;
    }
}

