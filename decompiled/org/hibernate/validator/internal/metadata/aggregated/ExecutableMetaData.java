/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ElementKind;
import javax.validation.metadata.ParameterDescriptor;
import javax.validation.metadata.ReturnValueDescriptor;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.metadata.aggregated.AbstractConstraintMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.aggregated.MetaDataBuilder;
import org.hibernate.validator.internal.metadata.aggregated.ParameterMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ReturnValueMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ValidatableParametersMetaData;
import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.ExecutableDescriptorImpl;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Signature;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public class ExecutableMetaData
extends AbstractConstraintMetaData {
    private final Class<?>[] parameterTypes;
    private final List<ParameterMetaData> parameterMetaDataList;
    private final ValidatableParametersMetaData validatableParametersMetaData;
    private final Set<MetaConstraint<?>> crossParameterConstraints;
    private final boolean isGetter;
    private final Set<Signature> signatures;
    private final ReturnValueMetaData returnValueMetaData;
    private final ElementKind kind;

    private ExecutableMetaData(String name, Type returnType, Class<?>[] parameterTypes, ElementKind kind, Set<Signature> signatures, Set<MetaConstraint<?>> returnValueConstraints, Set<MetaConstraint<?>> returnValueContainerElementConstraints, List<ParameterMetaData> parameterMetaDataList, Set<MetaConstraint<?>> crossParameterConstraints, CascadingMetaData cascadingMetaData, boolean isConstrained, boolean isGetter) {
        super(name, returnType, returnValueConstraints, returnValueContainerElementConstraints, cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements(), isConstrained);
        this.parameterTypes = parameterTypes;
        this.parameterMetaDataList = CollectionHelper.toImmutableList(parameterMetaDataList);
        this.validatableParametersMetaData = new ValidatableParametersMetaData(parameterMetaDataList);
        this.crossParameterConstraints = CollectionHelper.toImmutableSet(crossParameterConstraints);
        this.signatures = signatures;
        this.returnValueMetaData = new ReturnValueMetaData(returnType, returnValueConstraints, returnValueContainerElementConstraints, cascadingMetaData);
        this.isGetter = isGetter;
        this.kind = kind;
    }

    public ParameterMetaData getParameterMetaData(int parameterIndex) {
        return this.parameterMetaDataList.get(parameterIndex);
    }

    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }

    public Set<Signature> getSignatures() {
        return this.signatures;
    }

    public Set<MetaConstraint<?>> getCrossParameterConstraints() {
        return this.crossParameterConstraints;
    }

    public ValidatableParametersMetaData getValidatableParametersMetaData() {
        return this.validatableParametersMetaData;
    }

    public ReturnValueMetaData getReturnValueMetaData() {
        return this.returnValueMetaData;
    }

    @Override
    public ExecutableDescriptorImpl asDescriptor(boolean defaultGroupSequenceRedefined, List<Class<?>> defaultGroupSequence) {
        return new ExecutableDescriptorImpl(this.getType(), this.getName(), this.asDescriptors(this.getCrossParameterConstraints()), (ReturnValueDescriptor)this.returnValueMetaData.asDescriptor(defaultGroupSequenceRedefined, (List)defaultGroupSequence), this.parametersAsDescriptors(defaultGroupSequenceRedefined, defaultGroupSequence), defaultGroupSequenceRedefined, this.isGetter, defaultGroupSequence);
    }

    private List<ParameterDescriptor> parametersAsDescriptors(boolean defaultGroupSequenceRedefined, List<Class<?>> defaultGroupSequence) {
        ArrayList<ParameterDescriptor> parameterDescriptorList = CollectionHelper.newArrayList();
        for (ParameterMetaData parameterMetaData : this.parameterMetaDataList) {
            parameterDescriptorList.add((ParameterDescriptor)parameterMetaData.asDescriptor(defaultGroupSequenceRedefined, (List)defaultGroupSequence));
        }
        return parameterDescriptorList;
    }

    @Override
    public ElementKind getKind() {
        return this.kind;
    }

    @Override
    public String toString() {
        StringBuilder parameterBuilder = new StringBuilder();
        for (Class<?> oneParameterType : this.getParameterTypes()) {
            parameterBuilder.append(oneParameterType.getSimpleName());
            parameterBuilder.append(", ");
        }
        String parameters = parameterBuilder.length() > 0 ? parameterBuilder.substring(0, parameterBuilder.length() - 2) : parameterBuilder.toString();
        return "ExecutableMetaData [executable=" + this.getType() + " " + this.getName() + "(" + parameters + "), isCascading=" + this.isCascading() + ", isConstrained=" + this.isConstrained() + "]";
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(this.parameterTypes);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ExecutableMetaData other = (ExecutableMetaData)obj;
        return Arrays.equals(this.parameterTypes, other.parameterTypes);
    }

    public static class Builder
    extends MetaDataBuilder {
        private final Set<Signature> signatures = CollectionHelper.newHashSet();
        private final ConstrainedElement.ConstrainedElementKind kind;
        private final Set<ConstrainedExecutable> constrainedExecutables = CollectionHelper.newHashSet();
        private Callable callable;
        private final Set<MetaConstraint<?>> crossParameterConstraints = CollectionHelper.newHashSet();
        private final Set<MethodConfigurationRule> rules;
        private boolean isConstrained = false;
        private CascadingMetaDataBuilder cascadingMetaDataBuilder;
        private final Map<Class<?>, ConstrainedExecutable> executablesByDeclaringType = CollectionHelper.newHashMap();
        private final ExecutableHelper executableHelper;
        private final ExecutableParameterNameProvider parameterNameProvider;

        public Builder(Class<?> beanClass, ConstrainedExecutable constrainedExecutable, ConstraintCreationContext constraintCreationContext, ExecutableHelper executableHelper, ExecutableParameterNameProvider parameterNameProvider, MethodValidationConfiguration methodValidationConfiguration) {
            super(beanClass, constraintCreationContext);
            this.executableHelper = executableHelper;
            this.parameterNameProvider = parameterNameProvider;
            this.kind = constrainedExecutable.getKind();
            this.callable = constrainedExecutable.getCallable();
            this.rules = methodValidationConfiguration.getConfiguredRuleSet();
            this.add(constrainedExecutable);
        }

        @Override
        public boolean accepts(ConstrainedElement constrainedElement) {
            if (this.kind != constrainedElement.getKind()) {
                return false;
            }
            Callable candidate = ((ConstrainedExecutable)constrainedElement).getCallable();
            return this.isResolvedToSameMethodInHierarchy(this.callable, candidate);
        }

        private boolean isResolvedToSameMethodInHierarchy(Callable first, Callable other) {
            if (this.isConstructor(first) || this.isConstructor(other)) {
                return first.equals(other);
            }
            return first.isResolvedToSameMethodInHierarchy(this.executableHelper, this.getBeanClass(), other);
        }

        private boolean overrides(Callable first, Callable other) {
            if (this.isConstructor(first) || this.isConstructor(other)) {
                return false;
            }
            return this.executableHelper.overrides(first, other);
        }

        private boolean isConstructor(Callable callable) {
            return callable.getConstrainedElementKind() == ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR;
        }

        @Override
        public final void add(ConstrainedElement constrainedElement) {
            super.add(constrainedElement);
            ConstrainedExecutable constrainedExecutable = (ConstrainedExecutable)constrainedElement;
            this.signatures.add(constrainedExecutable.getCallable().getSignature());
            this.constrainedExecutables.add(constrainedExecutable);
            this.isConstrained = this.isConstrained || constrainedExecutable.isConstrained();
            this.crossParameterConstraints.addAll(constrainedExecutable.getCrossParameterConstraints());
            this.cascadingMetaDataBuilder = this.cascadingMetaDataBuilder == null ? constrainedExecutable.getCascadingMetaDataBuilder() : this.cascadingMetaDataBuilder.merge(constrainedExecutable.getCascadingMetaDataBuilder());
            this.addToExecutablesByDeclaringType(constrainedExecutable);
            if (this.callable != null && this.overrides(constrainedExecutable.getCallable(), this.callable)) {
                this.callable = constrainedExecutable.getCallable();
            }
        }

        private void addToExecutablesByDeclaringType(ConstrainedExecutable executable) {
            Class<?> beanClass = executable.getCallable().getDeclaringClass();
            ConstrainedExecutable mergedExecutable = this.executablesByDeclaringType.get(beanClass);
            mergedExecutable = mergedExecutable != null ? mergedExecutable.merge(executable) : executable;
            this.executablesByDeclaringType.put(beanClass, mergedExecutable);
        }

        @Override
        public ExecutableMetaData build() {
            this.assertCorrectnessOfConfiguration();
            return new ExecutableMetaData(this.callable.getName(), this.callable.getType(), this.callable.getParameterTypes(), this.kind == ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR ? ElementKind.CONSTRUCTOR : ElementKind.METHOD, this.kind == ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR ? Collections.singleton(this.callable.getSignature()) : CollectionHelper.toImmutableSet(this.signatures), this.adaptOriginsAndImplicitGroups(this.getDirectConstraints()), this.adaptOriginsAndImplicitGroups(this.getContainerElementConstraints()), this.findParameterMetaData(), this.adaptOriginsAndImplicitGroups(this.crossParameterConstraints), this.cascadingMetaDataBuilder.build(this.constraintCreationContext.getValueExtractorManager(), this.callable), this.isConstrained, this.kind == ConstrainedElement.ConstrainedElementKind.GETTER);
        }

        private List<ParameterMetaData> findParameterMetaData() {
            ArrayList<ParameterMetaData.Builder> parameterBuilders = null;
            for (ConstrainedExecutable oneExecutable : this.constrainedExecutables) {
                if (parameterBuilders == null) {
                    parameterBuilders = CollectionHelper.newArrayList();
                    for (ConstrainedParameter oneParameter : oneExecutable.getAllParameterMetaData()) {
                        parameterBuilders.add(new ParameterMetaData.Builder(this.callable.getDeclaringClass(), oneParameter, this.constraintCreationContext, this.parameterNameProvider));
                    }
                    continue;
                }
                int i = 0;
                for (ConstrainedParameter oneParameter : oneExecutable.getAllParameterMetaData()) {
                    ((ParameterMetaData.Builder)parameterBuilders.get(i)).add(oneParameter);
                    ++i;
                }
            }
            ArrayList<ParameterMetaData> parameterMetaDatas = CollectionHelper.newArrayList();
            for (ParameterMetaData.Builder oneBuilder : parameterBuilders) {
                parameterMetaDatas.add(oneBuilder.build());
            }
            return parameterMetaDatas;
        }

        private void assertCorrectnessOfConfiguration() {
            for (Map.Entry<Class<?>, ConstrainedExecutable> entry : this.executablesByDeclaringType.entrySet()) {
                for (Map.Entry<Class<?>, ConstrainedExecutable> otherEntry : this.executablesByDeclaringType.entrySet()) {
                    for (MethodConfigurationRule rule : this.rules) {
                        rule.apply(entry.getValue(), otherEntry.getValue());
                    }
                }
            }
        }
    }
}

