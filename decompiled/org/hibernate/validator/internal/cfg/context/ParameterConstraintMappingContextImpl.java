/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.reflect.Type;
import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.hibernate.validator.cfg.context.CrossParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.CascadableConstraintMappingContextImplBase;
import org.hibernate.validator.internal.cfg.context.ConfiguredConstraint;
import org.hibernate.validator.internal.cfg.context.ExecutableConstraintMappingContextImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;

final class ParameterConstraintMappingContextImpl
extends CascadableConstraintMappingContextImplBase<ParameterConstraintMappingContext>
implements ParameterConstraintMappingContext {
    private final ExecutableConstraintMappingContextImpl executableContext;
    private final int parameterIndex;

    ParameterConstraintMappingContextImpl(ExecutableConstraintMappingContextImpl executableContext, int parameterIndex) {
        super(executableContext.getTypeContext().getConstraintMapping(), executableContext.callable.getParameterGenericType(parameterIndex));
        this.executableContext = executableContext;
        this.parameterIndex = parameterIndex;
    }

    @Override
    protected ParameterConstraintMappingContext getThis() {
        return this;
    }

    @Override
    public ParameterConstraintMappingContext constraint(ConstraintDef<?, ?> definition) {
        super.addConstraint(ConfiguredConstraint.forParameter(definition, this.executableContext.getCallable(), this.parameterIndex));
        return this;
    }

    @Override
    public ParameterConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
        this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnParameter(this.executableContext.getCallable(), this.parameterIndex, ignoreAnnotations);
        return this;
    }

    @Override
    public ParameterConstraintMappingContext parameter(int index) {
        return this.executableContext.parameter(index);
    }

    @Override
    public CrossParameterConstraintMappingContext crossParameter() {
        return this.executableContext.crossParameter();
    }

    @Override
    public ReturnValueConstraintMappingContext returnValue() {
        return this.executableContext.returnValue();
    }

    @Override
    public ConstructorConstraintMappingContext constructor(Class<?> ... parameterTypes) {
        return this.executableContext.getTypeContext().constructor(parameterTypes);
    }

    @Override
    public MethodConstraintMappingContext method(String name, Class<?> ... parameterTypes) {
        return this.executableContext.getTypeContext().method(name, parameterTypes);
    }

    @Override
    public ContainerElementConstraintMappingContext containerElementType() {
        return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forParameter(this.executableContext.getCallable(), this.parameterIndex));
    }

    @Override
    public ContainerElementConstraintMappingContext containerElementType(int index, int ... nestedIndexes) {
        return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forParameter(this.executableContext.getCallable(), this.parameterIndex), index, nestedIndexes);
    }

    public ConstrainedParameter build(ConstraintCreationContext constraintCreationContext) {
        Type parameterType = this.executableContext.getCallable().getParameterGenericType(this.parameterIndex);
        return new ConstrainedParameter(ConfigurationSource.API, this.executableContext.getCallable(), parameterType, this.parameterIndex, this.getConstraints(constraintCreationContext), this.getTypeArgumentConstraints(constraintCreationContext), this.getCascadingMetaDataBuilder());
    }

    @Override
    protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
        return ConstraintDescriptorImpl.ConstraintType.GENERIC;
    }
}

