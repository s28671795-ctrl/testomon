/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.SimpleNode;
import com.sun.el.util.MessageFactory;
import javax.el.ELClass;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.el.VariableMapper;

public final class AstIdentifier
extends SimpleNode {
    public AstIdentifier(int id) {
        super(id);
    }

    @Override
    public Class getType(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        if (ctx.isLambdaArgument(this.image)) {
            return Object.class;
        }
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.getType(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        Class<?> ret = ctx.getELResolver().getType(ctx, null, this.image);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
        return ret;
    }

    @Override
    public ValueReference getValueReference(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.getValueReference(ctx.getELContext());
        }
        return new ValueReference(null, this.image);
    }

    @Override
    public Object getValue(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        if (ctx.isLambdaArgument(this.image)) {
            return ctx.getLambdaArgument(this.image);
        }
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.getValue(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        Object ret = ctx.getELResolver().getValue(ctx, null, this.image);
        if (!ctx.isPropertyResolved()) {
            Class<?> c;
            if (ctx.getImportHandler() != null && (c = ctx.getImportHandler().resolveStatic(this.image)) != null) {
                return ctx.getELResolver().getValue(ctx, new ELClass(c), this.image);
            }
            ELSupport.throwUnhandled(null, this.image);
        }
        return ret;
    }

    @Override
    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        ValueExpression expr;
        if (ctx.isLambdaArgument(this.image)) {
            return true;
        }
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            return expr.isReadOnly(ctx.getELContext());
        }
        ctx.setPropertyResolved(false);
        boolean ret = ctx.getELResolver().isReadOnly(ctx, null, this.image);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
        return ret;
    }

    @Override
    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        ValueExpression expr;
        if (ctx.isLambdaArgument(this.image)) {
            throw new PropertyNotWritableException(MessageFactory.get("error.lambda.parameter.readonly", this.image));
        }
        VariableMapper varMapper = ctx.getVariableMapper();
        if (varMapper != null && (expr = varMapper.resolveVariable(this.image)) != null) {
            expr.setValue(ctx.getELContext(), value);
            return;
        }
        ctx.setPropertyResolved(false);
        ELResolver elResolver = ctx.getELResolver();
        elResolver.setValue(ctx, null, this.image, value);
        if (!ctx.isPropertyResolved()) {
            ELSupport.throwUnhandled(null, this.image);
        }
    }

    private final Object invokeTarget(EvaluationContext ctx, Object target, Object[] paramValues) throws ELException {
        if (target instanceof MethodExpression) {
            MethodExpression me = (MethodExpression)target;
            return me.invoke(ctx.getELContext(), paramValues);
        }
        if (target == null) {
            throw new MethodNotFoundException("Identity '" + this.image + "' was null and was unable to invoke");
        }
        throw new ELException("Identity '" + this.image + "' does not reference a MethodExpression instance, returned type: " + target.getClass().getName());
    }

    @Override
    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        return this.getMethodExpression(ctx).invoke(ctx.getELContext(), paramValues);
    }

    @Override
    public MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException {
        return this.getMethodExpression(ctx).getMethodInfo(ctx.getELContext());
    }

    private final MethodExpression getMethodExpression(EvaluationContext ctx) throws ELException {
        Object obj = null;
        VariableMapper varMapper = ctx.getVariableMapper();
        ValueExpression ve = null;
        if (varMapper != null && (ve = varMapper.resolveVariable(this.image)) != null) {
            obj = ve.getValue(ctx);
        }
        if (ve == null) {
            ctx.setPropertyResolved(false);
            obj = ctx.getELResolver().getValue(ctx, null, this.image);
        }
        if (obj instanceof MethodExpression) {
            return (MethodExpression)obj;
        }
        if (obj == null) {
            throw new MethodNotFoundException("Identity '" + this.image + "' was null and was unable to invoke");
        }
        throw new ELException("Identity '" + this.image + "' does not reference a MethodExpression instance, returned type: " + obj.getClass().getName());
    }
}

