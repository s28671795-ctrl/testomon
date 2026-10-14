/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

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
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;

final class ReturnValueConstraintMappingContextImpl
extends CascadableConstraintMappingContextImplBase<ReturnValueConstraintMappingContext>
implements ReturnValueConstraintMappingContext {
    private final ExecutableConstraintMappingContextImpl executableContext;

    ReturnValueConstraintMappingContextImpl(ExecutableConstraintMappingContextImpl executableContext) {
        super(executableContext.getTypeContext().getConstraintMapping(), executableContext.getCallable().getType());
        this.executableContext = executableContext;
    }

    @Override
    protected ReturnValueConstraintMappingContext getThis() {
        return this;
    }

    @Override
    public ReturnValueConstraintMappingContext constraint(ConstraintDef<?, ?> definition) {
        super.addConstraint(ConfiguredConstraint.forExecutable(definition, this.executableContext.getCallable()));
        return this;
    }

    @Override
    public ReturnValueConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
        this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsForReturnValue(this.executableContext.getCallable(), ignoreAnnotations);
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
    public MethodConstraintMappingContext method(String name, Class<?> ... parameterTypes) {
        return this.executableContext.getTypeContext().method(name, parameterTypes);
    }

    @Override
    public ConstructorConstraintMappingContext constructor(Class<?> ... parameterTypes) {
        return this.executableContext.getTypeContext().constructor(parameterTypes);
    }

    @Override
    public ContainerElementConstraintMappingContext containerElementType() {
        return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forReturnValue(this.executableContext.getCallable()));
    }

    @Override
    public ContainerElementConstraintMappingContext containerElementType(int index, int ... nestedIndexes) {
        return super.containerElement(this, this.executableContext.getTypeContext(), ConstraintLocation.forReturnValue(this.executableContext.getCallable()), index, nestedIndexes);
    }

    @Override
    protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
        return ConstraintDescriptorImpl.ConstraintType.GENERIC;
    }
}

