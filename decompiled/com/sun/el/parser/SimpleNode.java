/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.ELParserTreeConstants;
import com.sun.el.parser.Node;
import com.sun.el.parser.NodeVisitor;
import com.sun.el.util.MessageFactory;
import javax.el.ELException;
import javax.el.MethodInfo;
import javax.el.PropertyNotWritableException;
import javax.el.ValueReference;

public abstract class SimpleNode
extends ELSupport
implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected String image;

    public SimpleNode(int i) {
        this.id = i;
    }

    @Override
    public void jjtOpen() {
    }

    @Override
    public void jjtClose() {
    }

    @Override
    public void jjtSetParent(Node n) {
        this.parent = n;
    }

    @Override
    public Node jjtGetParent() {
        return this.parent;
    }

    @Override
    public void jjtAddChild(Node n, int i) {
        if (this.children == null) {
            this.children = new Node[i + 1];
        } else if (i >= this.children.length) {
            Node[] c = new Node[i + 1];
            System.arraycopy(this.children, 0, c, 0, this.children.length);
            this.children = c;
        }
        this.children[i] = n;
    }

    @Override
    public Node jjtGetChild(int i) {
        return this.children[i];
    }

    @Override
    public int jjtGetNumChildren() {
        return this.children == null ? 0 : this.children.length;
    }

    public String toString() {
        if (this.image != null) {
            return ELParserTreeConstants.jjtNodeName[this.id] + "[" + this.image + "]";
        }
        return ELParserTreeConstants.jjtNodeName[this.id];
    }

    public String toString(String prefix) {
        return prefix + this.toString();
    }

    public void dump(String prefix) {
        System.out.println(this.toString(prefix));
        if (this.children != null) {
            for (int i = 0; i < this.children.length; ++i) {
                SimpleNode n = (SimpleNode)this.children[i];
                if (n == null) continue;
                n.dump(prefix + " ");
            }
        }
    }

    @Override
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueReference getValueReference(EvaluationContext ctx) throws ELException {
        return null;
    }

    @Override
    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        return true;
    }

    @Override
    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        throw new PropertyNotWritableException(MessageFactory.get("error.syntax.set"));
    }

    @Override
    public void accept(NodeVisitor visitor) throws ELException {
        visitor.visit(this);
        if (this.children != null && this.children.length > 0) {
            for (int i = 0; i < this.children.length; ++i) {
                this.children[i].accept(visitor);
            }
        }
    }

    @Override
    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        throw new UnsupportedOperationException();
    }

    @Override
    public MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object node) {
        if (!(node instanceof SimpleNode)) {
            return false;
        }
        SimpleNode n = (SimpleNode)node;
        if (this.id != n.id) {
            return false;
        }
        if (this.children == null && n.children == null) {
            if (this.image == null) {
                return n.image == null;
            }
            return this.image.equals(n.image);
        }
        if (this.children == null || n.children == null) {
            return false;
        }
        if (this.children.length != n.children.length) {
            return false;
        }
        if (this.children.length == 0) {
            if (this.image == null) {
                return n.image == null;
            }
            return this.image.equals(n.image);
        }
        for (int i = 0; i < this.children.length; ++i) {
            if (this.children[i].equals(n.children[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isParametersProvided() {
        return false;
    }

    @Override
    public int hashCode() {
        if (this.children == null || this.children.length == 0) {
            if (this.image != null) {
                return this.image.hashCode();
            }
            return this.id;
        }
        int h2 = 0;
        for (int i = this.children.length - 1; i >= 0; --i) {
            h2 = h2 + h2 + h2 + this.children[i].hashCode();
        }
        h2 = h2 + h2 + h2 + this.id;
        return h2;
    }
}

