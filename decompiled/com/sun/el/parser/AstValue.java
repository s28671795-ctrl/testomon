/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.AstBracketSuffix;
import com.sun.el.parser.AstDotSuffix;
import com.sun.el.parser.AstIdentifier;
import com.sun.el.parser.AstMethodArguments;
import com.sun.el.parser.Node;
import com.sun.el.parser.SimpleNode;
import com.sun.el.util.MessageFactory;
import com.sun.el.util.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.el.ELClass;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ImportHandler;
import javax.el.MethodInfo;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueReference;

public final class AstValue
extends SimpleNode {
    public AstValue(int id) {
        super(id);
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        Target t2 = this.getTarget(ctx);
        if (t2.isMethodCall()) {
            return null;
        }
        Object property = t2.suffixNode.getValue(ctx);
        ctx.setPropertyResolved(false);
        Class<?> ret = ctx.getELResolver().getType(ctx, t2.base, property);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(t2.base, property);
        }
        return ret;
    }

    @Override
    public ValueReference getValueReference(EvaluationContext ctx) throws ELException {
        Target t2 = this.getTarget(ctx);
        if (t2.isMethodCall()) {
            return null;
        }
        Object property = t2.suffixNode.getValue(ctx);
        return new ValueReference(t2.base, property);
    }

    private static AstMethodArguments getArguments(Node n) {
        if (n instanceof AstDotSuffix && n.jjtGetNumChildren() > 0) {
            return (AstMethodArguments)n.jjtGetChild(0);
        }
        if (n instanceof AstBracketSuffix && n.jjtGetNumChildren() > 1) {
            return (AstMethodArguments)n.jjtGetChild(1);
        }
        return null;
    }

    private Object getValue(Object base, Node child, EvaluationContext ctx) throws ELException {
        Object value = null;
        ELResolver resolver2 = ctx.getELResolver();
        Object property = child.getValue(ctx);
        AstMethodArguments args2 = AstValue.getArguments(child);
        if (args2 != null) {
            if (!(property instanceof String)) {
                throw new ELException(MessageFactory.get("error.method.name", property));
            }
            Class<?>[] paramTypes = args2.getParamTypes();
            Object[] params = args2.getParameters(ctx);
            ctx.setPropertyResolved(false);
            value = resolver2.invoke(ctx, base, property, paramTypes, params);
        } else if (property != null) {
            ctx.setPropertyResolved(false);
            value = resolver2.getValue(ctx, base, property);
            if (!ctx.isPropertyResolved()) {
                ELSupport.throwUnhandled(base, property);
            }
        }
        return value;
    }

    private final Object getBase(EvaluationContext ctx) {
        try {
            return this.children[0].getValue(ctx);
        }
        catch (PropertyNotFoundException ex) {
            if (this.children[0] instanceof AstIdentifier) {
                Class<?> c;
                String name = ((AstIdentifier)this.children[0]).image;
                ImportHandler importHandler = ctx.getImportHandler();
                if (importHandler != null && (c = importHandler.resolveClass(name)) != null) {
                    return new ELClass(c);
                }
            }
            throw ex;
        }
    }

    private final Target getTarget(EvaluationContext ctx) throws ELException {
        Object base = this.getBase(ctx);
        if (base == null) {
            throw new PropertyNotFoundException(MessageFactory.get("error.unreachable.base", this.children[0].getImage()));
        }
        Object property = null;
        int propCount = this.jjtGetNumChildren() - 1;
        if (propCount > 1) {
            for (int i = 1; base != null && i < propCount; ++i) {
                base = this.getValue(base, this.children[i], ctx);
            }
            if (base == null) {
                throw new PropertyNotFoundException(MessageFactory.get("error.unreachable.property", property));
            }
        }
        return new Target(base, this.children[propCount]);
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        Object base = this.getBase(ctx);
        int propCount = this.jjtGetNumChildren();
        for (int i = 1; base != null && i < propCount; ++i) {
            base = this.getValue(base, this.children[i], ctx);
        }
        return base;
    }

    @Override
    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        Target t2 = this.getTarget(ctx);
        if (t2.isMethodCall()) {
            return true;
        }
        Object property = t2.suffixNode.getValue(ctx);
        ctx.setPropertyResolved(false);
        boolean ret = ctx.getELResolver().isReadOnly(ctx, t2.base, property);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(t2.base, property);
        }
        return ret;
    }

    @Override
    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        Target t2 = this.getTarget(ctx);
        if (t2.isMethodCall()) {
            throw new PropertyNotWritableException(MessageFactory.get("error.syntax.set"));
        }
        Object property = t2.suffixNode.getValue(ctx);
        ctx.setPropertyResolved(false);
        ELResolver elResolver = ctx.getELResolver();
        value = ctx.convertToType(value, elResolver.getType(ctx, t2.base, property));
        elResolver.setValue(ctx, t2.base, property, value);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(t2.base, property);
        }
    }

    @Override
    public MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException {
        Target t2 = this.getTarget(ctx);
        if (t2.isMethodCall()) {
            return null;
        }
        Object property = t2.suffixNode.getValue(ctx);
        Method m4 = ReflectionUtil.getMethod(t2.base, property, paramTypes);
        return new MethodInfo(m4.getName(), m4.getReturnType(), m4.getParameterTypes());
    }

    @Override
    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        Target t2 = this.getTarget(ctx);
        if (t2.isMethodCall()) {
            AstMethodArguments args2 = AstValue.getArguments(t2.suffixNode);
            paramTypes = args2.getParamTypes();
            Object[] params = args2.getParameters(ctx);
            String method2 = (String)t2.suffixNode.getValue(ctx);
            ctx.setPropertyResolved(false);
            ELResolver resolver2 = ctx.getELResolver();
            return resolver2.invoke(ctx, t2.base, method2, paramTypes, params);
        }
        Object property = t2.suffixNode.getValue(ctx);
        Method m4 = ReflectionUtil.getMethod(t2.base, property, paramTypes);
        Object result = null;
        try {
            result = m4.invoke(t2.base, paramValues);
        }
        catch (IllegalAccessException iae) {
            throw new ELException(iae);
        }
        catch (InvocationTargetException ite) {
            throw new ELException(ite.getCause());
        }
        return result;
    }

    @Override
    public boolean isParametersProvided() {
        return AstValue.getArguments(this.children[this.jjtGetNumChildren() - 1]) != null;
    }

    protected static class Target {
        protected Object base;
        protected Node suffixNode;

        Target(Object base, Node suffixNode) {
            this.base = base;
            this.suffixNode = suffixNode;
        }

        boolean isMethodCall() {
            return AstValue.getArguments(this.suffixNode) != null;
        }
    }
}

