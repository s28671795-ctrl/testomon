/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;

public class ParameterConstraintLocation
implements ConstraintLocation {
    private final Callable callable;
    private final int index;
    private final Type typeForValidatorResolution;
    private final ConstraintLocation.ConstraintLocationKind kind;

    public ParameterConstraintLocation(Callable callable, int index) {
        this.callable = callable;
        this.index = index;
        this.typeForValidatorResolution = ReflectionHelper.boxedType(callable.getParameterGenericType(index));
        this.kind = ConstraintLocation.ConstraintLocationKind.of(callable.getConstrainedElementKind());
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.callable.getDeclaringClass();
    }

    @Override
    public Constrainable getConstrainable() {
        return this.callable;
    }

    @Override
    public Type getTypeForValidatorResolution() {
        return this.typeForValidatorResolution;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
        path.addParameterNode(this.callable.getParameterName(parameterNameProvider, this.index), this.index);
    }

    @Override
    public Object getValue(Object parent) {
        return ((Object[])parent)[this.index];
    }

    @Override
    public ConstraintLocation.ConstraintLocationKind getKind() {
        return this.kind;
    }

    public String toString() {
        return "ParameterConstraintLocation [callable=" + this.callable + ", index=" + this.index + "]";
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.callable.hashCode();
        result = 31 * result + this.index;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ParameterConstraintLocation other = (ParameterConstraintLocation)obj;
        if (!this.callable.equals(other.callable)) {
            return false;
        }
        return this.index == other.index;
    }
}

