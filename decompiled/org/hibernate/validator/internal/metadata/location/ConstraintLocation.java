/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.location;

import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.location.BeanConstraintLocation;
import org.hibernate.validator.internal.metadata.location.CrossParameterConstraintLocation;
import org.hibernate.validator.internal.metadata.location.FieldConstraintLocation;
import org.hibernate.validator.internal.metadata.location.GetterConstraintLocation;
import org.hibernate.validator.internal.metadata.location.ParameterConstraintLocation;
import org.hibernate.validator.internal.metadata.location.ReturnValueConstraintLocation;
import org.hibernate.validator.internal.metadata.location.TypeArgumentConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.Field;
import org.hibernate.validator.internal.properties.Getter;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.StringHelper;

public interface ConstraintLocation {
    public static ConstraintLocation forClass(Class<?> declaringClass) {
        return new BeanConstraintLocation(declaringClass);
    }

    public static ConstraintLocation forField(Field field2) {
        return new FieldConstraintLocation(field2);
    }

    public static ConstraintLocation forGetter(Getter getter2) {
        return new GetterConstraintLocation(getter2);
    }

    public static ConstraintLocation forTypeArgument(ConstraintLocation delegate, TypeVariable<?> typeParameter, Type typeOfAnnotatedElement) {
        return new TypeArgumentConstraintLocation(delegate, typeParameter, typeOfAnnotatedElement);
    }

    public static ConstraintLocation forReturnValue(Callable callable) {
        return new ReturnValueConstraintLocation(callable);
    }

    public static ConstraintLocation forCrossParameter(Callable callable) {
        return new CrossParameterConstraintLocation(callable);
    }

    public static ConstraintLocation forParameter(Callable callable, int index) {
        return new ParameterConstraintLocation(callable, index);
    }

    public Class<?> getDeclaringClass();

    public Constrainable getConstrainable();

    public Type getTypeForValidatorResolution();

    public void appendTo(ExecutableParameterNameProvider var1, PathImpl var2);

    public Object getValue(Object var1);

    public ConstraintLocationKind getKind();

    public static enum ConstraintLocationKind {
        TYPE(ElementType.TYPE),
        CONSTRUCTOR(ElementType.CONSTRUCTOR),
        METHOD(ElementType.METHOD),
        PARAMETER(ElementType.PARAMETER),
        FIELD(ElementType.FIELD),
        GETTER(ElementType.METHOD),
        TYPE_USE(ElementType.TYPE_USE);

        private final ElementType elementType;

        private ConstraintLocationKind(ElementType elementType) {
            this.elementType = elementType;
        }

        public ElementType getElementType() {
            return this.elementType;
        }

        public boolean isExecutable() {
            return this == CONSTRUCTOR || this.isMethod();
        }

        public boolean isMethod() {
            return this == METHOD || this == GETTER;
        }

        public static ConstraintLocationKind of(ConstrainedElement.ConstrainedElementKind constrainedElementKind) {
            switch (constrainedElementKind) {
                case CONSTRUCTOR: {
                    return CONSTRUCTOR;
                }
                case FIELD: {
                    return FIELD;
                }
                case METHOD: {
                    return METHOD;
                }
                case PARAMETER: {
                    return PARAMETER;
                }
                case TYPE: {
                    return TYPE;
                }
                case GETTER: {
                    return GETTER;
                }
            }
            throw new IllegalArgumentException(StringHelper.format("Constrained element kind '%1$s' not supported.", new Object[]{constrainedElementKind}));
        }
    }
}

