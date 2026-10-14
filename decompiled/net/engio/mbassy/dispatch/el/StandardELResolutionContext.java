/*
 * Decompiled with CFR 0.152.
 */
package net.engio.mbassy.dispatch.el;

import java.lang.reflect.Method;
import javax.el.BeanELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import net.engio.mbassy.dispatch.el.ElFilter;

public class StandardELResolutionContext
extends ELContext {
    private final ELResolver resolver;
    private final FunctionMapper functionMapper;
    private final VariableMapper variableMapper;
    private final Object message;

    public StandardELResolutionContext(Object message) {
        this.message = message;
        this.functionMapper = new NoopFunctionMapper();
        this.variableMapper = new MsgMapper();
        this.resolver = new BeanELResolver(true);
    }

    @Override
    public ELResolver getELResolver() {
        return this.resolver;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return this.functionMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return this.variableMapper;
    }

    private class NoopFunctionMapper
    extends FunctionMapper {
        private NoopFunctionMapper() {
        }

        @Override
        public Method resolveFunction(String s2, String s1) {
            return null;
        }
    }

    private class MsgMapper
    extends VariableMapper {
        private static final String msg = "msg";
        private final ValueExpression msgExpression;

        private MsgMapper() {
            this.msgExpression = ElFilter.ELFactory().createValueExpression(StandardELResolutionContext.this.message, StandardELResolutionContext.this.message.getClass());
        }

        @Override
        public ValueExpression resolveVariable(String s2) {
            return !s2.equals(msg) ? null : this.msgExpression;
        }

        @Override
        public ValueExpression setVariable(String s2, ValueExpression valueExpression) {
            return null;
        }
    }
}

