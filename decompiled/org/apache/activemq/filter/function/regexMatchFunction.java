/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jms.JMSException;
import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.function.FilterFunction;
import org.apache.activemq.util.LRUCache;

public class regexMatchFunction
implements FilterFunction {
    protected static final LRUCache<String, Pattern> compiledExprCache = new LRUCache(100);

    @Override
    public boolean isValid(FunctionCallExpression expr) {
        return expr.getNumArguments() == 2;
    }

    @Override
    public boolean returnsBoolean(FunctionCallExpression expr) {
        return true;
    }

    @Override
    public Object evaluate(FunctionCallExpression expr, MessageEvaluationContext message) throws JMSException {
        Object reg = expr.getArgument(0).evaluate(message);
        if (reg != null) {
            String reg_str = reg instanceof String ? (String)reg : reg.toString();
            Object cand = expr.getArgument(1).evaluate(message);
            if (cand != null) {
                String cand_str = cand instanceof String ? (String)cand : cand.toString();
                Pattern pat = this.getCompiledPattern(reg_str);
                Matcher match_eng = pat.matcher(cand_str);
                return match_eng.find();
            }
        }
        return Boolean.FALSE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Pattern getCompiledPattern(String reg_ex_str) {
        Pattern result;
        LRUCache<String, Pattern> lRUCache = compiledExprCache;
        synchronized (lRUCache) {
            result = (Pattern)compiledExprCache.get(reg_ex_str);
        }
        if (result == null) {
            result = Pattern.compile(reg_ex_str);
            lRUCache = compiledExprCache;
            synchronized (lRUCache) {
                compiledExprCache.put(reg_ex_str, result);
            }
        }
        return result;
    }
}

