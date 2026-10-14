/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.List;
import javax.jms.JMSException;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;

public class BooleanFunctionCallExpr
extends FunctionCallExpression
implements BooleanExpression {
    public BooleanFunctionCallExpr(String func_name, List<Expression> args2) throws FunctionCallExpression.invalidFunctionExpressionException {
        super(func_name, args2);
    }

    @Override
    public boolean matches(MessageEvaluationContext message_ctx) throws JMSException {
        Boolean result = (Boolean)this.evaluate(message_ctx);
        if (result != null) {
            return result;
        }
        return false;
    }
}

