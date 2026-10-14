/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.raw;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.raw.AbstractConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ConstrainedExecutable
extends AbstractConstrainedElement {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final Callable callable;
    private final List<ConstrainedParameter> parameterMetaData;
    private final boolean hasParameterConstraints;
    private final Set<MetaConstraint<?>> crossParameterConstraints;

    public ConstrainedExecutable(ConfigurationSource source2, Callable callable, Set<MetaConstraint<?>> returnValueConstraints, Set<MetaConstraint<?>> typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
        this(source2, callable, Collections.emptyList(), Collections.emptySet(), returnValueConstraints, typeArgumentConstraints, cascadingMetaDataBuilder);
    }

    public ConstrainedExecutable(ConfigurationSource source2, Callable callable, List<ConstrainedParameter> parameterMetaData, Set<MetaConstraint<?>> crossParameterConstraints, Set<MetaConstraint<?>> returnValueConstraints, Set<MetaConstraint<?>> typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
        super(source2, callable.getConstrainedElementKind(), returnValueConstraints, typeArgumentConstraints, cascadingMetaDataBuilder);
        this.callable = callable;
        if (parameterMetaData.size() != callable.getParameterCount()) {
            throw LOG.getInvalidLengthOfParameterMetaDataListException(callable, callable.getParameterCount(), parameterMetaData.size());
        }
        this.crossParameterConstraints = CollectionHelper.toImmutableSet(crossParameterConstraints);
        this.parameterMetaData = CollectionHelper.toImmutableList(parameterMetaData);
        this.hasParameterConstraints = this.hasParameterConstraints(parameterMetaData) || !crossParameterConstraints.isEmpty();
    }

    public ConstrainedParameter getParameterMetaData(int parameterIndex) {
        if (parameterIndex < 0 || parameterIndex > this.parameterMetaData.size() - 1) {
            throw LOG.getInvalidExecutableParameterIndexException(this.callable, parameterIndex);
        }
        return this.parameterMetaData.get(parameterIndex);
    }

    public List<ConstrainedParameter> getAllParameterMetaData() {
        return this.parameterMetaData;
    }

    public Set<MetaConstraint<?>> getCrossParameterConstraints() {
        return this.crossParameterConstraints;
    }

    @Override
    public boolean isConstrained() {
        return super.isConstrained() || this.hasParameterConstraints;
    }

    public boolean hasParameterConstraints() {
        return this.hasParameterConstraints;
    }

    public Callable getCallable() {
        return this.callable;
    }

    @Override
    public String toString() {
        return "ConstrainedExecutable [executable=" + this.callable + ", parameterMetaData=" + this.parameterMetaData + ", hasParameterConstraints=" + this.hasParameterConstraints + "]";
    }

    private boolean hasParameterConstraints(List<ConstrainedParameter> parameterMetaData) {
        for (ConstrainedParameter oneParameter : parameterMetaData) {
            if (!oneParameter.isConstrained()) continue;
            return true;
        }
        return false;
    }

    public boolean isEquallyParameterConstrained(ConstrainedExecutable other) {
        if (!this.getDescriptors(this.crossParameterConstraints).equals(this.getDescriptors(other.crossParameterConstraints))) {
            return false;
        }
        int i = 0;
        for (ConstrainedParameter parameter : this.parameterMetaData) {
            ConstrainedParameter otherParameter = other.getParameterMetaData(i);
            if (!parameter.getCascadingMetaDataBuilder().equals(otherParameter.getCascadingMetaDataBuilder()) || !this.getDescriptors(parameter.getConstraints()).equals(this.getDescriptors(otherParameter.getConstraints()))) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public ConstrainedExecutable merge(ConstrainedExecutable other) {
        ConfigurationSource mergedSource = ConfigurationSource.max(this.source, other.source);
        ArrayList<ConstrainedParameter> mergedParameterMetaData = CollectionHelper.newArrayList(this.parameterMetaData.size());
        int i = 0;
        for (ConstrainedParameter parameter : this.parameterMetaData) {
            mergedParameterMetaData.add(parameter.merge(other.getParameterMetaData(i)));
            ++i;
        }
        HashSet<MetaConstraint<?>> mergedCrossParameterConstraints = CollectionHelper.newHashSet(this.crossParameterConstraints);
        mergedCrossParameterConstraints.addAll(other.crossParameterConstraints);
        HashSet<MetaConstraint<?>> mergedReturnValueConstraints = CollectionHelper.newHashSet(this.constraints);
        mergedReturnValueConstraints.addAll(other.constraints);
        HashSet mergedTypeArgumentConstraints = new HashSet(this.typeArgumentConstraints);
        mergedTypeArgumentConstraints.addAll(other.typeArgumentConstraints);
        CascadingMetaDataBuilder mergedCascadingMetaDataBuilder = this.cascadingMetaDataBuilder.merge(other.cascadingMetaDataBuilder);
        return new ConstrainedExecutable(mergedSource, this.callable, mergedParameterMetaData, mergedCrossParameterConstraints, mergedReturnValueConstraints, mergedTypeArgumentConstraints, mergedCascadingMetaDataBuilder);
    }

    private Set<ConstraintDescriptor<?>> getDescriptors(Iterable<MetaConstraint<?>> constraints) {
        HashSet<ConstraintDescriptor<?>> descriptors = CollectionHelper.newHashSet();
        for (MetaConstraint<?> constraint : constraints) {
            descriptors.add(constraint.getDescriptor());
        }
        return descriptors;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + this.callable.hashCode();
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
        ConstrainedExecutable other = (ConstrainedExecutable)obj;
        return this.callable.equals(other.callable);
    }
}

