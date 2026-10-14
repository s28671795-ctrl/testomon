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
import org.hibernate.validator.internal.properties.Getter;

public class GetterCascadable
extends AbstractPropertyCascadable<Getter> {
    GetterCascadable(Getter getter2, CascadingMetaData cascadingMetaData) {
        super(getter2, cascadingMetaData);
    }

    @Override
    public ConstraintLocation.ConstraintLocationKind getConstraintLocationKind() {
        return ConstraintLocation.ConstraintLocationKind.GETTER;
    }

    public static class Builder
    extends AbstractPropertyCascadable.AbstractBuilder<Getter> {
        protected Builder(ValueExtractorManager valueExtractorManager, Getter getter2, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
            super(valueExtractorManager, getter2, cascadingMetaDataBuilder);
        }

        @Override
        protected Cascadable create(Getter getter2, CascadingMetaData cascadingMetaData) {
            return new GetterCascadable(getter2, cascadingMetaData);
        }
    }
}

