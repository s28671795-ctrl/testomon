/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import javax.jms.JMSException;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;

public interface FilterFunction {
    public boolean isValid(FunctionCallExpression var1);

    public boolean returnsBoolean(FunctionCallExpression var1);

    public Object evaluate(FunctionCallExpression var1, MessageEvaluationContext var2) throws JMSException;
}

