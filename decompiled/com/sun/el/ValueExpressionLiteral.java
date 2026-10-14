/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el;

import com.sun.el.util.MessageFactory;
import com.sun.el.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;

public final class ValueExpressionLiteral
extends ValueExpression
implements Externalizable {
    private static final long serialVersionUID = 1L;
    private Object value;
    private Class expectedType;

    public ValueExpressionLiteral() {
    }

    public ValueExpressionLiteral(Object value, Class expectedType) {
        this.value = value;
        this.expectedType = expectedType;
    }

    @Override
    public Object getValue(ELContext context) {
        if (this.expectedType != null) {
            try {
                return context.convertToType(this.value, this.expectedType);
            }
            catch (IllegalArgumentException ex) {
                throw new ELException(ex);
            }
        }
        return this.value;
    }

    @Override
    public void setValue(ELContext context, Object value) {
        throw new PropertyNotWritableException(MessageFactory.get("error.value.literal.write", this.value));
    }

    @Override
    public boolean isReadOnly(ELContext context) {
        return true;
    }

    public Class getType(ELContext context) {
        return this.value != null ? this.value.getClass() : null;
    }

    public Class getExpectedType() {
        return this.expectedType;
    }

    @Override
    public String getExpressionString() {
        return this.value != null ? this.value.toString() : null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ValueExpressionLiteral && this.equals((ValueExpressionLiteral)obj);
    }

    public boolean equals(ValueExpressionLiteral ve) {
        return ve != null && this.value != null && ve.value != null && (this.value == ve.value || this.value.equals(ve.value));
    }

    @Override
    public int hashCode() {
        return this.value != null ? this.value.hashCode() : 0;
    }

    @Override
    public boolean isLiteralText() {
        return true;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.value);
        out.writeUTF(this.expectedType != null ? this.expectedType.getName() : "");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.value = in.readObject();
        String type = in.readUTF();
        if (!"".equals(type)) {
            this.expectedType = ReflectionUtil.forName(type);
        }
    }
}

