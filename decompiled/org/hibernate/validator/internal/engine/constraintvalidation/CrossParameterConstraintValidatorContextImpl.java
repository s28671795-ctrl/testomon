/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.util.List;
import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ElementKind;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintViolationBuilder;
import org.hibernate.validator.constraintvalidation.HibernateCrossParameterConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

public class CrossParameterConstraintValidatorContextImpl
extends ConstraintValidatorContextImpl
implements HibernateCrossParameterConstraintValidatorContext {
    private final List<String> methodParameterNames;

    public CrossParameterConstraintValidatorContextImpl(List<String> methodParameterNames, ClockProvider clockProvider, PathImpl propertyPath, ConstraintDescriptor<?> constraintDescriptor, Object constraintValidatorPayload, ExpressionLanguageFeatureLevel constraintExpressionLanguageFeatureLevel, ExpressionLanguageFeatureLevel customViolationExpressionLanguageFeatureLevel) {
        super(clockProvider, propertyPath, constraintDescriptor, constraintValidatorPayload, constraintExpressionLanguageFeatureLevel, customViolationExpressionLanguageFeatureLevel);
        Contracts.assertTrue(propertyPath.getLeafNode().getKind() == ElementKind.CROSS_PARAMETER, "Context can only be used for corss parameter validation");
        this.methodParameterNames = methodParameterNames;
    }

    @Override
    public final HibernateConstraintViolationBuilder buildConstraintViolationWithTemplate(String messageTemplate) {
        return new CrossParameterConstraintViolationBuilderImpl(this.methodParameterNames, messageTemplate, this.getCopyOfBasePath());
    }

    @Override
    public List<String> getMethodParameterNames() {
        return this.methodParameterNames;
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        if (type.isAssignableFrom(HibernateCrossParameterConstraintValidatorContext.class)) {
            return type.cast(this);
        }
        return super.unwrap(type);
    }

    private class CrossParameterConstraintViolationBuilderImpl
    extends ConstraintValidatorContextImpl.ConstraintViolationBuilderImpl {
        private final List<String> methodParameterNames;

        private CrossParameterConstraintViolationBuilderImpl(List<String> methodParameterNames, String template, PathImpl path) {
            super(template, path);
            this.methodParameterNames = methodParameterNames;
        }

        @Override
        public ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext addParameterNode(int index) {
            this.dropLeafNode();
            this.propertyPath.addParameterNode(this.methodParameterNames.get(index), index);
            return new ConstraintValidatorContextImpl.NodeBuilder(this.messageTemplate, this.propertyPath);
        }

        private void dropLeafNode() {
            this.propertyPath = PathImpl.createCopyWithoutLeafNode(this.propertyPath);
        }
    }
}

