/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import java.util.ArrayList;
import javax.jms.JMSException;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.function.FilterFunction;

public class makeListFunction
implements FilterFunction {
    @Override
    public boolean isValid(FunctionCallExpression expr) {
        return true;
    }

    @Override
    public boolean returnsBoolean(FunctionCallExpression expr) {
        return false;
    }

    @Override
    public Object evaluate(FunctionCallExpression expr, MessageEvaluationContext message) throws JMSException {
        int num_arg = expr.getNumArguments();
        ArrayList<Object> ele_arr = new ArrayList<Object>(num_arg);
        for (int cur = 0; cur < num_arg; ++cur) {
            ele_arr.add(expr.getArgument(cur).evaluate(message));
        }
        return ele_arr;
    }
}

