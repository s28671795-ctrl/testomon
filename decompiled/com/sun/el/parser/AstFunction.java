/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.AstMethodArguments;
import com.sun.el.parser.ELParserTreeConstants;
import com.sun.el.parser.SimpleNode;
import com.sun.el.util.MessageFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.el.ELClass;
import javax.el.ELException;
import javax.el.FunctionMapper;
import javax.el.LambdaExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public final class AstFunction
extends SimpleNode {
    protected String localName = "";
    protected String prefix = "";

    public AstFunction(int id) {
        super(id);
    }

    public String getLocalName() {
        return this.localName;
    }

    public String getOutputName() {
        if (this.prefix.length() == 0) {
            return this.localName;
        }
        return this.prefix + ":" + this.localName;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        FunctionMapper fnMapper = ctx.getFunctionMapper();
        if (fnMapper == null) {
            throw new ELException(MessageFactory.get("error.fnMapper.null"));
        }
        Method m4 = fnMapper.resolveFunction(this.prefix, this.localName);
        if (m4 == null) {
            throw new ELException(MessageFactory.get("error.fnMapper.method", this.getOutputName()));
        }
        return m4.getReturnType();
    }

    private Object findValue(EvaluationContext ctx, String name) {
        ValueExpression expr;
        if (ctx.isLambdaArgument(name)) {
            return ctx.getLambdaArgument(name);
        }
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(name)) != null) {
            return expr.getValue(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        Object ret = ctx.getELResolver().getValue(ctx, null, name);
        if (ctx.isPropertyResolved()) {
            return ret;
        }
        return null;
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        Object val;
        if (this.prefix.length() == 0 && (val = this.findValue(ctx, this.localName)) != null && val instanceof LambdaExpression) {
            for (int i = 0; i < this.children.length; ++i) {
                Object[] params = ((AstMethodArguments)this.children[i]).getParameters(ctx);
                if (!(val instanceof LambdaExpression)) {
                    throw new ELException(MessageFactory.get("error.function.syntax", this.getOutputName()));
                }
                val = ((LambdaExpression)val).invoke(ctx, params);
            }
            return val;
        }
        FunctionMapper fnMapper = ctx.getFunctionMapper();
        if (fnMapper == null) {
            throw new ELException(MessageFactory.get("error.fnMapper.null"));
        }
        Method m4 = fnMapper.resolveFunction(this.prefix, this.localName);
        if (m4 == null) {
            if (this.prefix.length() == 0 && ctx.getImportHandler() != null) {
                Class<?> c = null;
                c = ctx.getImportHandler().resolveClass(this.localName);
                String methodName = null;
                if (c != null) {
                    methodName = "<init>";
                } else {
                    c = ctx.getImportHandler().resolveStatic(this.localName);
                    methodName = this.localName;
                }
                if (c != null) {
                    Object[] params = ((AstMethodArguments)this.children[0]).getParameters(ctx);
                    return ctx.getELResolver().invoke(ctx, new ELClass(c), methodName, null, params);
                }
            }
            throw new ELException(MessageFactory.get("error.fnMapper.method", this.getOutputName()));
        }
        Class<?>[] paramTypes = m4.getParameterTypes();
        Object[] params = ((AstMethodArguments)this.children[0]).getParameters(ctx);
        Object result = null;
        for (int i = 0; i < params.length; ++i) {
            try {
                params[i] = ctx.convertToType(params[i], paramTypes[i]);
                continue;
            }
            catch (ELException ele) {
                throw new ELException(MessageFactory.get("error.function", this.getOutputName()), ele);
            }
        }
        try {
            result = m4.invoke(null, params);
        }
        catch (IllegalAccessException iae) {
            throw new ELException(MessageFactory.get("error.function", this.getOutputName()), iae);
        }
        catch (InvocationTargetException ite) {
            throw new ELException(MessageFactory.get("error.function", this.getOutputName()), ite.getCause());
        }
        return result;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return ELParserTreeConstants.jjtNodeName[this.id] + "[" + this.getOutputName() + "]";
    }
}

