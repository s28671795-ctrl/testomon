/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.lang.ExpressionBuilder;
import com.sun.el.parser.AstLiteralExpression;
import com.sun.el.parser.Node;
import com.sun.el.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.FunctionMapper;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.el.VariableMapper;

public final class ValueExpressionImpl
extends ValueExpression
implements Externalizable {
    private Class expectedType;
    private String expr;
    private FunctionMapper fnMapper;
    private VariableMapper varMapper;
    private transient Node node;

    public ValueExpressionImpl() {
    }

    public ValueExpressionImpl(String expr, Node node, FunctionMapper fnMapper, VariableMapper varMapper, Class expectedType) {
        this.expr = expr;
        this.node = node;
        this.fnMapper = fnMapper;
        this.varMapper = varMapper;
        this.expectedType = expectedType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueExpressionImpl) {
            ValueExpressionImpl v = (ValueExpressionImpl)obj;
            return this.getNode().equals(v.getNode());
        }
        return false;
    }

    public Class getExpectedType() {
        return this.expectedType;
    }

    @Override
    public String getExpressionString() {
        return this.expr;
    }

    private Node getNode() throws ELException {
        if (this.node == null) {
            this.node = ExpressionBuilder.createNode(this.expr);
        }
        return this.node;
    }

    public Class getType(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().getType(ctx);
    }

    @Override
    public ValueReference getValueReference(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().getValueReference(ctx);
    }

    @Override
    public Object getValue(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        ctx.notifyBeforeEvaluation(this.expr);
        Object value = this.getNode().getValue(ctx);
        if (this.expectedType != null) {
            try {
                value = context.convertToType(value, this.expectedType);
            }
            catch (IllegalArgumentException ex) {
                throw new ELException(ex);
            }
        }
        ctx.notifyAfterEvaluation(this.expr);
        return value;
    }

    @Override
    public int hashCode() {
        return this.getNode().hashCode();
    }

    @Override
    public boolean isLiteralText() {
        try {
            return this.getNode() instanceof AstLiteralExpression;
        }
        catch (ELException ele) {
            return false;
        }
    }

    @Override
    public boolean isReadOnly(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().isReadOnly(ctx);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.expr = in.readUTF();
        String type = in.readUTF();
        if (!"".equals(type)) {
            this.expectedType = ReflectionUtil.forName(type);
        }
        this.fnMapper = (FunctionMapper)in.readObject();
        this.varMapper = (VariableMapper)in.readObject();
    }

    @Override
    public void setValue(ELContext context, Object value) throws PropertyNotFoundException, PropertyNotWritableException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        this.getNode().setValue(ctx, value);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.expr);
        out.writeUTF(this.expectedType != null ? this.expectedType.getName() : "");
        out.writeObject(this.fnMapper);
        out.writeObject(this.varMapper);
    }

    public String toString() {
        return "ValueExpression[" + this.expr + "]";
    }
}

