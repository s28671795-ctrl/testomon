/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import java.util.Arrays;
import javax.jms.JMSException;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.function.FilterFunction;

public class splitFunction
implements FilterFunction {
    @Override
    public boolean isValid(FunctionCallExpression expr) {
        return expr.getNumArguments() >= 2 && expr.getNumArguments() <= 3;
    }

    @Override
    public boolean returnsBoolean(FunctionCallExpression expr) {
        return false;
    }

    @Override
    public Object evaluate(FunctionCallExpression expr, MessageEvaluationContext message_ctx) throws JMSException {
        String[] result;
        String src = (String)expr.getArgument(0).evaluate(message_ctx);
        String split_pat = (String)expr.getArgument(1).evaluate(message_ctx);
        if (expr.getNumArguments() > 2) {
            Integer limit = (Integer)expr.getArgument(2).evaluate(message_ctx);
            result = src.split(split_pat, limit);
        } else {
            result = src.split(split_pat);
        }
        return Arrays.asList(result);
    }
}

