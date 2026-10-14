/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.parser.Node;
import javax.el.ELException;

public interface NodeVisitor {
    public void visit(Node var1) throws ELException;
}

