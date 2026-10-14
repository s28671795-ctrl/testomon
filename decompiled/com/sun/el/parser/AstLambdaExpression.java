/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.ValueExpressionImpl;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.AstLambdaParameters;
import com.sun.el.parser.AstMethodArguments;
import com.sun.el.parser.SimpleNode;
import com.sun.el.util.MessageFactory;
import java.util.List;
import javax.el.ELException;
import javax.el.LambdaExpression;

public class AstLambdaExpression
extends SimpleNode {
    public AstLambdaExpression(int id) {
        super(id);
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        ValueExpressionImpl expr = new ValueExpressionImpl("#{Lambda Expression}", this.children[1], ctx.getFunctionMapper(), ctx.getVariableMapper(), null);
        List<String> parameters = ((AstLambdaParameters)this.children[0]).getParameters();
        LambdaExpression lambda = new LambdaExpression(parameters, expr);
        if (this.children.length <= 2) {
            return lambda;
        }
        Object ret = null;
        for (int i = 2; i < this.children.length; ++i) {
            if (ret != null) {
                if (!(ret instanceof LambdaExpression)) {
                    throw new ELException(MessageFactory.get("error.lambda.call"));
                }
                lambda = (LambdaExpression)ret;
            }
            AstMethodArguments args2 = (AstMethodArguments)this.children[i];
            ret = lambda.invoke(ctx, args2.getParameters(ctx));
        }
        return ret;
    }
}

