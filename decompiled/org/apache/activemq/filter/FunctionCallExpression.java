/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.jms.JMSException;
import org.apache.activemq.filter.BooleanFunctionCallExpr;
import org.apache.activemq.filter.Expression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.function.BuiltinFunctionRegistry;
import org.apache.activemq.filter.function.FilterFunction;

public class FunctionCallExpression
implements Expression {
    protected static final HashMap<String, functionRegistration> functionRegistry = new HashMap();
    protected String functionName;
    protected ArrayList arguments;
    protected FilterFunction filterFunc;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean registerFunction(String name, FilterFunction impl) {
        boolean result = true;
        HashMap<String, functionRegistration> hashMap = functionRegistry;
        synchronized (hashMap) {
            if (functionRegistry.containsKey(name)) {
                result = false;
            } else {
                functionRegistry.put(name, new functionRegistration(impl));
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void deregisterFunction(String name) {
        HashMap<String, functionRegistration> hashMap = functionRegistry;
        synchronized (hashMap) {
            functionRegistry.remove(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected FunctionCallExpression(String func_name, List<Expression> args2) throws invalidFunctionExpressionException {
        functionRegistration func_reg;
        HashMap<String, functionRegistration> hashMap = functionRegistry;
        synchronized (hashMap) {
            func_reg = functionRegistry.get(func_name);
        }
        if (func_reg == null) {
            throw new invalidFunctionExpressionException("invalid function name, \"" + func_name + "\"");
        }
        this.arguments = new ArrayList();
        this.arguments.addAll(args2);
        this.functionName = func_name;
        this.filterFunc = func_reg.getFilterFunction();
    }

    public static FunctionCallExpression createFunctionCall(String func_name, List<Expression> args2) throws invalidFunctionExpressionException {
        FunctionCallExpression result = new FunctionCallExpression(func_name, args2);
        if (result.filterFunc.isValid(result)) {
            if (result.filterFunc.returnsBoolean(result)) {
                result = new BooleanFunctionCallExpr(func_name, args2);
            }
        } else {
            throw new invalidFunctionExpressionException("invalid call of function " + func_name);
        }
        return result;
    }

    public int getNumArguments() {
        return this.arguments.size();
    }

    public Expression getArgument(int which) {
        return (Expression)this.arguments.get(which);
    }

    @Override
    public Object evaluate(MessageEvaluationContext message_ctx) throws JMSException {
        return this.filterFunc.evaluate(this, message_ctx);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.functionName);
        result.append("(");
        boolean first_f = true;
        for (Object arg : this.arguments) {
            if (first_f) {
                first_f = false;
            } else {
                result.append(", ");
            }
            result.append(arg.toString());
        }
        result.append(")");
        return result.toString();
    }

    static {
        BuiltinFunctionRegistry.register();
    }

    public static class invalidFunctionExpressionException
    extends Exception {
        public invalidFunctionExpressionException(String msg) {
            super(msg);
        }

        public invalidFunctionExpressionException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }

    protected static class functionRegistration {
        protected FilterFunction filterFunction;

        public functionRegistration(FilterFunction func) {
            this.filterFunction = func;
        }

        public FilterFunction getFilterFunction() {
            return this.filterFunction;
        }

        public void setFilterFunction(FilterFunction func) {
            this.filterFunction = func;
        }
    }
}

