/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el;

import com.sun.el.ValueExpressionLiteral;
import com.sun.el.lang.ELSupport;
import com.sun.el.lang.ExpressionBuilder;
import com.sun.el.stream.StreamELResolver;
import com.sun.el.util.MessageFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

public class ExpressionFactoryImpl
extends ExpressionFactory {
    public Object coerceToType(Object obj, Class type) {
        Object ret;
        try {
            ret = ELSupport.coerceToType(obj, type);
        }
        catch (IllegalArgumentException ex) {
            throw new ELException(ex);
        }
        return ret;
    }

    public MethodExpression createMethodExpression(ELContext context, String expression, Class expectedReturnType, Class[] expectedParamTypes) {
        ExpressionBuilder builder = new ExpressionBuilder(expression, context);
        MethodExpression me = builder.createMethodExpression(expectedReturnType, expectedParamTypes);
        if (expectedParamTypes == null && !me.isParametersProvided()) {
            throw new NullPointerException(MessageFactory.get("error.method.nullParms"));
        }
        return me;
    }

    public ValueExpression createValueExpression(ELContext context, String expression, Class expectedType) {
        if (expectedType == null) {
            throw new NullPointerException(MessageFactory.get("error.value.expectedType"));
        }
        ExpressionBuilder builder = new ExpressionBuilder(expression, context);
        return builder.createValueExpression(expectedType);
    }

    public ValueExpression createValueExpression(Object instance, Class expectedType) {
        if (expectedType == null) {
            throw new NullPointerException(MessageFactory.get("error.value.expectedType"));
        }
        return new ValueExpressionLiteral(instance, expectedType);
    }

    @Override
    public ELResolver getStreamELResolver() {
        return new StreamELResolver();
    }

    @Override
    public Map<String, Method> getInitFunctionMap() {
        HashMap<String, Method> funcs = new HashMap<String, Method>();
        return funcs;
    }
}

