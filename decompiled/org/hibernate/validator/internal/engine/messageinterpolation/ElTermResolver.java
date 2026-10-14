/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.messageinterpolation;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.validation.MessageInterpolator;
import org.hibernate.validator.internal.engine.messageinterpolation.FormatterWrapper;
import org.hibernate.validator.internal.engine.messageinterpolation.TermResolver;
import org.hibernate.validator.internal.engine.messageinterpolation.el.BeanMethodsELContext;
import org.hibernate.validator.internal.engine.messageinterpolation.el.BeanPropertiesElContext;
import org.hibernate.validator.internal.engine.messageinterpolation.el.DisabledFeatureELException;
import org.hibernate.validator.internal.engine.messageinterpolation.el.VariablesELContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;

public class ElTermResolver
implements TermResolver {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String VALIDATED_VALUE_NAME = "validatedValue";
    private final Locale locale;
    private final ExpressionFactory expressionFactory;

    public ElTermResolver(Locale locale, ExpressionFactory expressionFactory) {
        this.locale = locale;
        this.expressionFactory = expressionFactory;
    }

    @Override
    public String interpolate(MessageInterpolator.Context context, String expression) {
        String resolvedExpression = expression;
        ELContext elContext = this.getElContext(context);
        try {
            ValueExpression valueExpression = this.bindContextValues(expression, context, elContext);
            resolvedExpression = (String)valueExpression.getValue(elContext);
        }
        catch (DisabledFeatureELException dfee) {
            LOG.disabledFeatureInExpressionLanguage(expression, dfee);
        }
        catch (PropertyNotFoundException pnfe) {
            LOG.unknownPropertyInExpressionLanguage(expression, pnfe);
        }
        catch (MethodNotFoundException mnfe) {
            LOG.unknownMethodInExpressionLanguage(expression, mnfe);
        }
        catch (ELException e) {
            LOG.errorInExpressionLanguage(expression, e);
        }
        catch (Exception e) {
            LOG.evaluatingExpressionLanguageExpressionCausedException(expression, e);
        }
        return resolvedExpression;
    }

    private ELContext getElContext(MessageInterpolator.Context context) {
        if (!(context instanceof HibernateMessageInterpolatorContext)) {
            return new VariablesELContext(this.expressionFactory);
        }
        switch (((HibernateMessageInterpolatorContext)context).getExpressionLanguageFeatureLevel()) {
            case NONE: {
                throw LOG.expressionsNotResolvedWhenExpressionLanguageFeaturesDisabled();
            }
            case VARIABLES: {
                return new VariablesELContext(this.expressionFactory);
            }
            case BEAN_PROPERTIES: {
                return new BeanPropertiesElContext(this.expressionFactory);
            }
            case BEAN_METHODS: {
                return new BeanMethodsELContext(this.expressionFactory);
            }
        }
        throw LOG.expressionsLanguageFeatureLevelNotSupported();
    }

    private ValueExpression bindContextValues(String messageTemplate, MessageInterpolator.Context messageInterpolatorContext, ELContext elContext) {
        ValueExpression valueExpression = this.expressionFactory.createValueExpression(messageInterpolatorContext.getValidatedValue(), Object.class);
        elContext.getVariableMapper().setVariable(VALIDATED_VALUE_NAME, valueExpression);
        valueExpression = this.expressionFactory.createValueExpression(new FormatterWrapper(this.locale), FormatterWrapper.class);
        elContext.getVariableMapper().setVariable("formatter", valueExpression);
        this.addVariablesToElContext(elContext, messageInterpolatorContext.getConstraintDescriptor().getAttributes());
        if (messageInterpolatorContext instanceof HibernateMessageInterpolatorContext) {
            this.addVariablesToElContext(elContext, ((HibernateMessageInterpolatorContext)messageInterpolatorContext).getExpressionVariables());
        }
        return this.expressionFactory.createValueExpression(elContext, messageTemplate, String.class);
    }

    private void addVariablesToElContext(ELContext elContext, Map<String, Object> variables) {
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            ValueExpression valueExpression = this.expressionFactory.createValueExpression(entry.getValue(), Object.class);
            elContext.getVariableMapper().setVariable(entry.getKey(), valueExpression);
        }
    }
}

