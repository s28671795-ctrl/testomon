/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.AbstractPropertyCascadable;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Field;

public class FieldCascadable
extends AbstractPropertyCascadable<Field> {
    FieldCascadable(Field field2, CascadingMetaData cascadingMetaData) {
        super(field2, cascadingMetaData);
    }

    @Override
    public ConstraintLocation.ConstraintLocationKind getConstraintLocationKind() {
        return ConstraintLocation.ConstraintLocationKind.FIELD;
    }

    public static class Builder
    extends AbstractPropertyCascadable.AbstractBuilder<Field> {
        protected Builder(ValueExtractorManager valueExtractorManager, Field field2, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
            super(valueExtractorManager, field2, cascadingMetaDataBuilder);
        }

        @Override
        protected Cascadable create(Field field2, CascadingMetaData cascadingMetaData) {
            return new FieldCascadable(field2, cascadingMetaData);
        }
    }
}

