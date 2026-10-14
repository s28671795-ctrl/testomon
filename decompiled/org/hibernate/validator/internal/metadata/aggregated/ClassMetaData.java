/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import java.util.List;
import java.util.Set;
import javax.validation.ElementKind;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.aggregated.AbstractConstraintMetaData;
import org.hibernate.validator.internal.metadata.aggregated.MetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.ClassDescriptorImpl;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;

public class ClassMetaData
extends AbstractConstraintMetaData {
    private ClassMetaData(Class<?> beanClass, Set<MetaConstraint<?>> constraints, Set<MetaConstraint<?>> containerElementsConstraints) {
        super(beanClass.getSimpleName(), beanClass, constraints, containerElementsConstraints, false, !constraints.isEmpty() || !containerElementsConstraints.isEmpty());
    }

    @Override
    public ClassDescriptorImpl asDescriptor(boolean defaultGroupSequenceRedefined, List<Class<?>> defaultGroupSequence) {
        return new ClassDescriptorImpl(this.getType(), this.asDescriptors(this.getDirectConstraints()), defaultGroupSequenceRedefined, defaultGroupSequence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("[ ");
        sb.append("type=").append(this.getType());
        sb.append("]");
        return sb.toString();
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.BEAN;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    public static class Builder
    extends MetaDataBuilder {
        public Builder(Class<?> beanClass, ConstrainedType constrainedType, ConstraintCreationContext constraintCreationContext) {
            super(beanClass, constraintCreationContext);
            this.add(constrainedType);
        }

        @Override
        public boolean accepts(ConstrainedElement constrainedElement) {
            return constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.TYPE;
        }

        @Override
        public final void add(ConstrainedElement constrainedElement) {
            super.add(constrainedElement);
        }

        @Override
        public ClassMetaData build() {
            return new ClassMetaData(this.getBeanClass(), this.adaptOriginsAndImplicitGroups(this.getDirectConstraints()), this.adaptOriginsAndImplicitGroups(this.getContainerElementConstraints()));
        }
    }
}

