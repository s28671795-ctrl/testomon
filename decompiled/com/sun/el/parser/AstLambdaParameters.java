/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.parser.Node;
import com.sun.el.parser.SimpleNode;
import java.util.ArrayList;
import java.util.List;

public class AstLambdaParameters
extends SimpleNode {
    public AstLambdaParameters(int id) {
        super(id);
    }

    List<String> getParameters() {
        ArrayList<String> parameters = new ArrayList<String>();
        if (this.children != null) {
            for (Node child : this.children) {
                parameters.add(child.getImage());
            }
        }
        return parameters;
    }
}

