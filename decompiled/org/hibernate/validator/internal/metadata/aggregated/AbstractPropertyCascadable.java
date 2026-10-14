/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.aggregated.FieldCascadable;
import org.hibernate.validator.internal.metadata.aggregated.GetterCascadable;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.properties.Field;
import org.hibernate.validator.internal.properties.Getter;
import org.hibernate.validator.internal.properties.Property;
import org.hibernate.validator.internal.properties.PropertyAccessor;

public abstract class AbstractPropertyCascadable<T extends Property>
implements Cascadable {
    private final T property;
    private final PropertyAccessor propertyAccessor;
    private final Type cascadableType;
    private final CascadingMetaData cascadingMetaData;

    AbstractPropertyCascadable(T property, CascadingMetaData cascadingMetaData) {
        this.property = property;
        this.propertyAccessor = property.createAccessor();
        this.cascadableType = property.getType();
        this.cascadingMetaData = cascadingMetaData;
    }

    @Override
    public Type getCascadableType() {
        return this.cascadableType;
    }

    @Override
    public Object getValue(Object parent) {
        return this.propertyAccessor.getValueFrom(parent);
    }

    @Override
    public void appendTo(PathImpl path) {
        path.addPropertyNode(this.property.getResolvedPropertyName());
    }

    @Override
    public CascadingMetaData getCascadingMetaData() {
        return this.cascadingMetaData;
    }

    public static abstract class AbstractBuilder<T extends Property>
    implements Cascadable.Builder {
        private final ValueExtractorManager valueExtractorManager;
        private final T property;
        private CascadingMetaDataBuilder cascadingMetaDataBuilder;

        protected AbstractBuilder(ValueExtractorManager valueExtractorManager, T property, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
            this.valueExtractorManager = valueExtractorManager;
            this.property = property;
            this.cascadingMetaDataBuilder = cascadingMetaDataBuilder;
        }

        @Override
        public void mergeCascadingMetaData(CascadingMetaDataBuilder cascadingMetaData) {
            this.cascadingMetaDataBuilder = this.cascadingMetaDataBuilder.merge(cascadingMetaData);
        }

        @Override
        public Cascadable build() {
            return this.create(this.property, this.cascadingMetaDataBuilder.build(this.valueExtractorManager, this.property));
        }

        protected abstract Cascadable create(T var1, CascadingMetaData var2);

        public static Cascadable.Builder builder(ValueExtractorManager valueExtractorManager, Property property, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
            if (property instanceof Field) {
                return new FieldCascadable.Builder(valueExtractorManager, (Field)property, cascadingMetaDataBuilder);
            }
            if (property instanceof Getter) {
                return new GetterCascadable.Builder(valueExtractorManager, (Getter)property, cascadingMetaDataBuilder);
            }
            throw new IllegalStateException("It should be either a field or a getter.");
        }
    }
}

