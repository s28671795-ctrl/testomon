/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import java.math.BigInteger;
import javax.el.ELException;

public final class AstInteger
extends SimpleNode {
    private Number number;

    public AstInteger(int id) {
        super(id);
    }

    protected Number getInteger() {
        if (this.number == null) {
            try {
                this.number = Long.valueOf(this.image);
            }
            catch (ArithmeticException e1) {
                this.number = new BigInteger(this.image);
            }
        }
        return this.number;
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        return this.getInteger().getClass();
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        return this.getInteger();
    }
}

