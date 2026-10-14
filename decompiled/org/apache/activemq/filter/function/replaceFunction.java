/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import javax.jms.JMSException;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.function.FilterFunction;

public class replaceFunction
implements FilterFunction {
    @Override
    public boolean isValid(FunctionCallExpression expr) {
        return expr.getNumArguments() == 3;
    }

    @Override
    public boolean returnsBoolean(FunctionCallExpression expr) {
        return false;
    }

    @Override
    public Object evaluate(FunctionCallExpression expr, MessageEvaluationContext message_ctx) throws JMSException {
        String src = (String)expr.getArgument(0).evaluate(message_ctx);
        String match_regex = (String)expr.getArgument(1).evaluate(message_ctx);
        String repl_lit = (String)expr.getArgument(2).evaluate(message_ctx);
        String result = src.replaceAll(match_regex, repl_lit);
        return result;
    }
}

