/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import java.util.List;
import javax.jms.JMSException;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.function.FilterFunction;

public class inListFunction
implements FilterFunction {
    @Override
    public boolean isValid(FunctionCallExpression expr) {
        return expr.getNumArguments() == 2;
    }

    @Override
    public boolean returnsBoolean(FunctionCallExpression expr) {
        return true;
    }

    @Override
    public Object evaluate(FunctionCallExpression expr, MessageEvaluationContext message_ctx) throws JMSException {
        List arr = (List)expr.getArgument(0).evaluate(message_ctx);
        Object cand = expr.getArgument(1).evaluate(message_ctx);
        boolean found_f = false;
        for (int cur = 0; cur < arr.size() && !found_f; ++cur) {
            found_f = arr.get(cur).equals(cand);
        }
        return found_f;
    }
}

