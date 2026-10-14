/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.location;

import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Property;
import org.hibernate.validator.internal.properties.PropertyAccessor;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;

public abstract class AbstractPropertyConstraintLocation<T extends Property>
implements ConstraintLocation {
    private final T property;
    private final PropertyAccessor propertyAccessor;

    AbstractPropertyConstraintLocation(T property) {
        this.property = property;
        this.propertyAccessor = property.createAccessor();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.property.getDeclaringClass();
    }

    public T getConstrainable() {
        return this.property;
    }

    public String getPropertyName() {
        return this.property.getPropertyName();
    }

    @Override
    public Type getTypeForValidatorResolution() {
        return this.property.getTypeForValidatorResolution();
    }

    @Override
    public void appendTo(ExecutableParameterNameProvider parameterNameProvider, PathImpl path) {
        path.addPropertyNode(this.property.getResolvedPropertyName());
    }

    @Override
    public Object getValue(Object parent) {
        return this.propertyAccessor.getValueFrom(parent);
    }

    public String toString() {
        return this.getClass().getSimpleName() + " [property=" + this.property + "]";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AbstractPropertyConstraintLocation that = (AbstractPropertyConstraintLocation)o;
        return this.property.equals(that.property);
    }

    public int hashCode() {
        return this.property.hashCode();
    }
}

