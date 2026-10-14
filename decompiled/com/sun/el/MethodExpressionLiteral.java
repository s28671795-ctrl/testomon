/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el;

import com.sun.el.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;

public class MethodExpressionLiteral
extends MethodExpression
implements Externalizable {
    private Class expectedType;
    private String expr;
    private Class[] paramTypes;

    public MethodExpressionLiteral() {
    }

    public MethodExpressionLiteral(String expr, Class expectedType, Class[] paramTypes) {
        this.expr = expr;
        this.expectedType = expectedType;
        this.paramTypes = paramTypes;
    }

    @Override
    public MethodInfo getMethodInfo(ELContext context) throws ELException {
        return new MethodInfo(this.expr, this.expectedType, this.paramTypes);
    }

    @Override
    public Object invoke(ELContext context, Object[] params) throws ELException {
        if (this.expectedType == null) {
            return this.expr;
        }
        try {
            return context.convertToType(this.expr, this.expectedType);
        }
        catch (Exception ex) {
            throw new ELException(ex);
        }
    }

    @Override
    public String getExpressionString() {
        return this.expr;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MethodExpressionLiteral && this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return this.expr.hashCode();
    }

    @Override
    public boolean isLiteralText() {
        return true;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.expr = in.readUTF();
        String type = in.readUTF();
        if (!"".equals(type)) {
            this.expectedType = ReflectionUtil.forName(type);
        }
        this.paramTypes = ReflectionUtil.toTypeArray((String[])in.readObject());
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.expr);
        out.writeUTF(this.expectedType != null ? this.expectedType.getName() : "");
        out.writeObject(ReflectionUtil.toTypeNameArray(this.paramTypes));
    }
}

