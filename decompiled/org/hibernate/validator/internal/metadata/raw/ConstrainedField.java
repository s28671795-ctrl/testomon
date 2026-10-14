/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.raw;

import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.raw.AbstractConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.Field;

public class ConstrainedField
extends AbstractConstrainedElement {
    private final Field field;

    public ConstrainedField(ConfigurationSource source2, Field field2, Set<MetaConstraint<?>> constraints, Set<MetaConstraint<?>> typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
        super(source2, ConstrainedElement.ConstrainedElementKind.FIELD, constraints, typeArgumentConstraints, cascadingMetaDataBuilder);
        this.field = field2;
    }

    public Field getField() {
        return this.field;
    }

    @Override
    public String toString() {
        return "ConstrainedField [field=" + this.field.getName() + "]";
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.field.hashCode();
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
        ConstrainedField other = (ConstrainedField)obj;
        return this.field.equals(other.field);
    }
}

