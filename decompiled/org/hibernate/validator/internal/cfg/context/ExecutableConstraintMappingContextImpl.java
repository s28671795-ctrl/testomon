/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.validator.cfg.context.CrossParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.ParameterConstraintMappingContext;
import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.CrossParameterConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.ParameterConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.ReturnValueConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.TypeConstraintMappingContextImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

abstract class ExecutableConstraintMappingContextImpl {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    protected final TypeConstraintMappingContextImpl<?> typeContext;
    protected final Callable callable;
    private final ParameterConstraintMappingContextImpl[] parameterContexts;
    private ReturnValueConstraintMappingContextImpl returnValueContext;
    private CrossParameterConstraintMappingContextImpl crossParameterContext;

    protected ExecutableConstraintMappingContextImpl(TypeConstraintMappingContextImpl<?> typeContext, Callable callable) {
        this.typeContext = typeContext;
        this.callable = callable;
        this.parameterContexts = new ParameterConstraintMappingContextImpl[callable.getParameterTypes().length];
    }

    public ParameterConstraintMappingContext parameter(int index) {
        if (index < 0 || index >= this.callable.getParameterTypes().length) {
            throw LOG.getInvalidExecutableParameterIndexException(this.callable, index);
        }
        ParameterConstraintMappingContextImpl context = this.parameterContexts[index];
        if (context != null) {
            throw LOG.getParameterHasAlreadyBeConfiguredViaProgrammaticApiException(this.typeContext.getBeanClass(), this.callable, index);
        }
        this.parameterContexts[index] = context = new ParameterConstraintMappingContextImpl(this, index);
        return context;
    }

    public CrossParameterConstraintMappingContext crossParameter() {
        if (this.crossParameterContext != null) {
            throw LOG.getCrossParameterElementHasAlreadyBeConfiguredViaProgrammaticApiException(this.typeContext.getBeanClass(), this.callable);
        }
        this.crossParameterContext = new CrossParameterConstraintMappingContextImpl(this);
        return this.crossParameterContext;
    }

    public ReturnValueConstraintMappingContext returnValue() {
        if (this.returnValueContext != null) {
            throw LOG.getReturnValueHasAlreadyBeConfiguredViaProgrammaticApiException(this.typeContext.getBeanClass(), this.callable);
        }
        this.returnValueContext = new ReturnValueConstraintMappingContextImpl(this);
        return this.returnValueContext;
    }

    public Callable getCallable() {
        return this.callable;
    }

    public TypeConstraintMappingContextImpl<?> getTypeContext() {
        return this.typeContext;
    }

    public ConstrainedElement build(ConstraintCreationContext constraintCreationContext) {
        return new ConstrainedExecutable(ConfigurationSource.API, this.callable, this.getParameters(constraintCreationContext), this.crossParameterContext != null ? this.crossParameterContext.getConstraints(constraintCreationContext) : Collections.emptySet(), this.returnValueContext != null ? this.returnValueContext.getConstraints(constraintCreationContext) : Collections.emptySet(), this.returnValueContext != null ? this.returnValueContext.getTypeArgumentConstraints(constraintCreationContext) : Collections.emptySet(), this.returnValueContext != null ? this.returnValueContext.getCascadingMetaDataBuilder() : CascadingMetaDataBuilder.nonCascading());
    }

    private List<ConstrainedParameter> getParameters(ConstraintCreationContext constraintCreationContext) {
        ArrayList<ConstrainedParameter> constrainedParameters = CollectionHelper.newArrayList();
        for (int i = 0; i < this.parameterContexts.length; ++i) {
            ParameterConstraintMappingContextImpl parameter = this.parameterContexts[i];
            if (parameter != null) {
                constrainedParameters.add(parameter.build(constraintCreationContext));
                continue;
            }
            constrainedParameters.add(new ConstrainedParameter(ConfigurationSource.API, this.callable, this.callable.getParameterGenericType(i), i));
        }
        return constrainedParameters;
    }
}

