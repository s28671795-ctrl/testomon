/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter.function;

import org.apache.activemq.filter.FunctionCallExpression;
import org.apache.activemq.filter.function.inListFunction;
import org.apache.activemq.filter.function.makeListFunction;
import org.apache.activemq.filter.function.regexMatchFunction;
import org.apache.activemq.filter.function.replaceFunction;
import org.apache.activemq.filter.function.splitFunction;

public class BuiltinFunctionRegistry {
    public static void register() {
        FunctionCallExpression.registerFunction("INLIST", new inListFunction());
        FunctionCallExpression.registerFunction("MAKELIST", new makeListFunction());
        FunctionCallExpression.registerFunction("REGEX", new regexMatchFunction());
        FunctionCallExpression.registerFunction("REPLACE", new replaceFunction());
        FunctionCallExpression.registerFunction("SPLIT", new splitFunction());
    }
}

