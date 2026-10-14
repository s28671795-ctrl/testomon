/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.metadata.aggregated.BeanMetaDataImpl;
import org.hibernate.validator.internal.metadata.aggregated.ClassMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ConstraintMetaData;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.hibernate.validator.internal.metadata.aggregated.MetaDataBuilder;
import org.hibernate.validator.internal.metadata.aggregated.PropertyMetaData;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

public class BeanMetaDataBuilder<T> {
    private final ConstraintCreationContext constraintCreationContext;
    private final ValidationOrderGenerator validationOrderGenerator;
    private final Class<T> beanClass;
    private final Set<BuilderDelegate> builders = CollectionHelper.newHashSet();
    private final ExecutableHelper executableHelper;
    private final ExecutableParameterNameProvider parameterNameProvider;
    private final MethodValidationConfiguration methodValidationConfiguration;
    private ConfigurationSource sequenceSource;
    private ConfigurationSource providerSource;
    private List<Class<?>> defaultGroupSequence;
    private DefaultGroupSequenceProvider<? super T> defaultGroupSequenceProvider;

    private BeanMetaDataBuilder(ConstraintCreationContext constraintCreationContext, ExecutableHelper executableHelper, ExecutableParameterNameProvider parameterNameProvider, ValidationOrderGenerator validationOrderGenerator, Class<T> beanClass, MethodValidationConfiguration methodValidationConfiguration) {
        this.beanClass = beanClass;
        this.constraintCreationContext = constraintCreationContext;
        this.validationOrderGenerator = validationOrderGenerator;
        this.executableHelper = executableHelper;
        this.parameterNameProvider = parameterNameProvider;
        this.methodValidationConfiguration = methodValidationConfiguration;
    }

    public static <T> BeanMetaDataBuilder<T> getInstance(ConstraintCreationContext constraintCreationContext, ExecutableHelper executableHelper, ExecutableParameterNameProvider parameterNameProvider, ValidationOrderGenerator validationOrderGenerator, Class<T> beanClass, MethodValidationConfiguration methodValidationConfiguration) {
        return new BeanMetaDataBuilder<T>(constraintCreationContext, executableHelper, parameterNameProvider, validationOrderGenerator, beanClass, methodValidationConfiguration);
    }

    public void add(BeanConfiguration<? super T> configuration) {
        if (configuration.getBeanClass().equals(this.beanClass)) {
            if (configuration.getDefaultGroupSequence() != null && (this.sequenceSource == null || configuration.getSource().getPriority() >= this.sequenceSource.getPriority())) {
                this.sequenceSource = configuration.getSource();
                this.defaultGroupSequence = configuration.getDefaultGroupSequence();
            }
            if (configuration.getDefaultGroupSequenceProvider() != null && (this.providerSource == null || configuration.getSource().getPriority() >= this.providerSource.getPriority())) {
                this.providerSource = configuration.getSource();
                this.defaultGroupSequenceProvider = configuration.getDefaultGroupSequenceProvider();
            }
        }
        for (ConstrainedElement constrainedElement : configuration.getConstrainedElements()) {
            this.addMetaDataToBuilder(constrainedElement, this.builders);
        }
    }

    private void addMetaDataToBuilder(ConstrainedElement constrainableElement, Set<BuilderDelegate> builders) {
        for (BuilderDelegate builder : builders) {
            boolean foundBuilder = builder.add(constrainableElement);
            if (!foundBuilder) continue;
            return;
        }
        builders.add(new BuilderDelegate(this.beanClass, constrainableElement, this.constraintCreationContext, this.executableHelper, this.parameterNameProvider, this.methodValidationConfiguration));
    }

    public BeanMetaDataImpl<T> build() {
        HashSet<ConstraintMetaData> aggregatedElements = CollectionHelper.newHashSet();
        for (BuilderDelegate builder : this.builders) {
            aggregatedElements.addAll(builder.build());
        }
        return new BeanMetaDataImpl<T>(this.beanClass, this.defaultGroupSequence, this.defaultGroupSequenceProvider, aggregatedElements, this.validationOrderGenerator);
    }

    private static class BuilderDelegate {
        private final Class<?> beanClass;
        private final ConstrainedElement constrainedElement;
        private final ConstraintCreationContext constraintCreationContext;
        private final ExecutableHelper executableHelper;
        private final ExecutableParameterNameProvider parameterNameProvider;
        private MetaDataBuilder metaDataBuilder;
        private ExecutableMetaData.Builder methodBuilder;
        private final MethodValidationConfiguration methodValidationConfiguration;
        private final int hashCode;

        public BuilderDelegate(Class<?> beanClass, ConstrainedElement constrainedElement, ConstraintCreationContext constraintCreationContext, ExecutableHelper executableHelper, ExecutableParameterNameProvider parameterNameProvider, MethodValidationConfiguration methodValidationConfiguration) {
            this.beanClass = beanClass;
            this.constrainedElement = constrainedElement;
            this.constraintCreationContext = constraintCreationContext;
            this.executableHelper = executableHelper;
            this.parameterNameProvider = parameterNameProvider;
            this.methodValidationConfiguration = methodValidationConfiguration;
            switch (constrainedElement.getKind()) {
                case FIELD: {
                    ConstrainedField constrainedField = (ConstrainedField)constrainedElement;
                    this.metaDataBuilder = new PropertyMetaData.Builder(beanClass, constrainedField, constraintCreationContext);
                    break;
                }
                case CONSTRUCTOR: 
                case METHOD: 
                case GETTER: {
                    ConstrainedExecutable constrainedExecutable = (ConstrainedExecutable)constrainedElement;
                    Callable callable = constrainedExecutable.getCallable();
                    if (!callable.isPrivate() || beanClass == callable.getDeclaringClass()) {
                        this.methodBuilder = new ExecutableMetaData.Builder(beanClass, constrainedExecutable, constraintCreationContext, executableHelper, parameterNameProvider, methodValidationConfiguration);
                    }
                    if (constrainedElement.getKind() != ConstrainedElement.ConstrainedElementKind.GETTER) break;
                    this.metaDataBuilder = new PropertyMetaData.Builder(beanClass, constrainedExecutable, constraintCreationContext);
                    break;
                }
                case TYPE: {
                    ConstrainedType constrainedType = (ConstrainedType)constrainedElement;
                    this.metaDataBuilder = new ClassMetaData.Builder(beanClass, constrainedType, constraintCreationContext);
                    break;
                }
                default: {
                    throw new IllegalStateException(StringHelper.format("Constrained element kind '%1$s' not supported here.", new Object[]{constrainedElement.getKind()}));
                }
            }
            this.hashCode = this.buildHashCode();
        }

        public boolean add(ConstrainedElement constrainedElement) {
            boolean added = false;
            if (this.methodBuilder != null && this.methodBuilder.accepts(constrainedElement)) {
                this.methodBuilder.add(constrainedElement);
                added = true;
            }
            if (this.metaDataBuilder != null && this.metaDataBuilder.accepts(constrainedElement)) {
                this.metaDataBuilder.add(constrainedElement);
                if (!added && constrainedElement.getKind().isMethod() && this.methodBuilder == null) {
                    ConstrainedExecutable constrainedMethod = (ConstrainedExecutable)constrainedElement;
                    this.methodBuilder = new ExecutableMetaData.Builder(this.beanClass, constrainedMethod, this.constraintCreationContext, this.executableHelper, this.parameterNameProvider, this.methodValidationConfiguration);
                }
                added = true;
            }
            return added;
        }

        public Set<ConstraintMetaData> build() {
            HashSet<ConstraintMetaData> metaDataSet = CollectionHelper.newHashSet();
            if (this.metaDataBuilder != null) {
                metaDataSet.add(this.metaDataBuilder.build());
            }
            if (this.methodBuilder != null) {
                metaDataSet.add(this.methodBuilder.build());
            }
            return metaDataSet;
        }

        public int hashCode() {
            return this.hashCode;
        }

        private int buildHashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.beanClass.hashCode();
            result = 31 * result + this.constrainedElement.hashCode();
            return result;
        }

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
            BuilderDelegate other = (BuilderDelegate)obj;
            if (!this.beanClass.equals(other.beanClass)) {
                return false;
            }
            return this.constrainedElement.equals(other.constrainedElement);
        }
    }
}

