/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.valuecontext;

import javax.validation.groups.Default;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.facets.Validatable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public class ValueContext<T, V> {
    private final ExecutableParameterNameProvider parameterNameProvider;
    private final T currentBean;
    private PathImpl propertyPath;
    private Class<?> currentGroup;
    private V currentValue;
    private final Validatable currentValidatable;
    private ConstraintLocation.ConstraintLocationKind constraintLocationKind;

    ValueContext(ExecutableParameterNameProvider parameterNameProvider, T currentBean, Validatable validatable, PathImpl propertyPath) {
        this.parameterNameProvider = parameterNameProvider;
        this.currentBean = currentBean;
        this.currentValidatable = validatable;
        this.propertyPath = propertyPath;
    }

    public final PathImpl getPropertyPath() {
        return this.propertyPath;
    }

    public final Class<?> getCurrentGroup() {
        return this.currentGroup;
    }

    public final T getCurrentBean() {
        return this.currentBean;
    }

    public Validatable getCurrentValidatable() {
        return this.currentValidatable;
    }

    public final Object getCurrentValidatedValue() {
        return this.currentValue;
    }

    public final void appendNode(Cascadable node) {
        PathImpl newPath = PathImpl.createCopy(this.propertyPath);
        node.appendTo(newPath);
        this.propertyPath = newPath;
    }

    public final void appendNode(ConstraintLocation location) {
        PathImpl newPath = PathImpl.createCopy(this.propertyPath);
        location.appendTo(this.parameterNameProvider, newPath);
        this.propertyPath = newPath;
    }

    public final void appendTypeParameterNode(String nodeName) {
        PathImpl newPath = PathImpl.createCopy(this.propertyPath);
        newPath.addContainerElementNode(nodeName);
        this.propertyPath = newPath;
    }

    public final void markCurrentPropertyAsIterable() {
        this.propertyPath.makeLeafNodeIterable();
    }

    public final void markCurrentPropertyAsIterableAndSetKey(Object key) {
        this.propertyPath.makeLeafNodeIterableAndSetMapKey(key);
    }

    public final void markCurrentPropertyAsIterableAndSetIndex(Integer index) {
        this.propertyPath.makeLeafNodeIterableAndSetIndex(index);
    }

    public final void setTypeParameter(Class<?> containerClass, Integer typeParameterIndex) {
        if (containerClass == null) {
            return;
        }
        this.propertyPath.setLeafNodeTypeParameter(containerClass, typeParameterIndex);
    }

    public final void setCurrentGroup(Class<?> currentGroup) {
        this.currentGroup = currentGroup;
    }

    public final void setCurrentValidatedValue(V currentValue) {
        this.propertyPath.setLeafNodeValueIfRequired(currentValue);
        this.currentValue = currentValue;
    }

    public final boolean validatingDefault() {
        return this.getCurrentGroup() != null && this.getCurrentGroup().getName().equals(Default.class.getName());
    }

    public final ConstraintLocation.ConstraintLocationKind getConstraintLocationKind() {
        return this.constraintLocationKind;
    }

    public final void setConstraintLocationKind(ConstraintLocation.ConstraintLocationKind constraintLocationKind) {
        this.constraintLocationKind = constraintLocationKind;
    }

    public final ValueState<V> getCurrentValueState() {
        return new ValueState<V>(this.propertyPath, this.currentValue);
    }

    public final void resetValueState(ValueState<V> valueState) {
        this.propertyPath = valueState.getPropertyPath();
        this.currentValue = valueState.getCurrentValue();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueContext");
        sb.append("{currentBean=").append(this.currentBean);
        sb.append(", propertyPath=").append(this.propertyPath);
        sb.append(", currentGroup=").append(this.currentGroup);
        sb.append(", currentValue=").append(this.currentValue);
        sb.append(", constraintLocationKind=").append((Object)this.constraintLocationKind);
        sb.append('}');
        return sb.toString();
    }

    public Object getValue(Object parent, ConstraintLocation location) {
        return location.getValue(parent);
    }

    public static class ValueState<V> {
        private final PathImpl propertyPath;
        private final V currentValue;

        ValueState(PathImpl propertyPath, V currentValue) {
            this.propertyPath = propertyPath;
            this.currentValue = currentValue;
        }

        public PathImpl getPropertyPath() {
            return this.propertyPath;
        }

        public V getCurrentValue() {
            return this.currentValue;
        }
    }
}

