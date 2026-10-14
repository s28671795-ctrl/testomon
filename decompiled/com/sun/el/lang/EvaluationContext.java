/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.lang;

import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.EvaluationListener;
import javax.el.FunctionMapper;
import javax.el.ImportHandler;
import javax.el.VariableMapper;

public final class EvaluationContext
extends ELContext {
    private final ELContext elContext;
    private final FunctionMapper fnMapper;
    private final VariableMapper varMapper;

    public EvaluationContext(ELContext elContext, FunctionMapper fnMapper, VariableMapper varMapper) {
        this.elContext = elContext;
        this.fnMapper = fnMapper;
        this.varMapper = varMapper;
    }

    public ELContext getELContext() {
        return this.elContext;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return this.fnMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return this.varMapper;
    }

    @Override
    public Object getContext(Class key) {
        return this.elContext.getContext(key);
    }

    @Override
    public ELResolver getELResolver() {
        return this.elContext.getELResolver();
    }

    @Override
    public boolean isPropertyResolved() {
        return this.elContext.isPropertyResolved();
    }

    @Override
    public void putContext(Class key, Object contextObject) {
        this.elContext.putContext(key, contextObject);
    }

    @Override
    public void setPropertyResolved(boolean resolved) {
        this.elContext.setPropertyResolved(resolved);
    }

    @Override
    public void setPropertyResolved(Object base, Object property) {
        this.elContext.setPropertyResolved(base, property);
    }

    @Override
    public void addEvaluationListener(EvaluationListener listener) {
        this.elContext.addEvaluationListener(listener);
    }

    @Override
    public List<EvaluationListener> getEvaluationListeners() {
        return this.elContext.getEvaluationListeners();
    }

    @Override
    public void notifyBeforeEvaluation(String expr) {
        this.elContext.notifyBeforeEvaluation(expr);
    }

    @Override
    public void notifyAfterEvaluation(String expr) {
        this.elContext.notifyAfterEvaluation(expr);
    }

    @Override
    public void notifyPropertyResolved(Object base, Object property) {
        this.elContext.notifyPropertyResolved(base, property);
    }

    @Override
    public boolean isLambdaArgument(String arg) {
        return this.elContext.isLambdaArgument(arg);
    }

    @Override
    public Object getLambdaArgument(String arg) {
        return this.elContext.getLambdaArgument(arg);
    }

    @Override
    public void enterLambdaScope(Map<String, Object> args2) {
        this.elContext.enterLambdaScope(args2);
    }

    @Override
    public void exitLambdaScope() {
        this.elContext.exitLambdaScope();
    }

    @Override
    public Object convertToType(Object obj, Class<?> targetType) {
        return this.elContext.convertToType(obj, targetType);
    }

    @Override
    public ImportHandler getImportHandler() {
        return this.elContext.getImportHandler();
    }
}

