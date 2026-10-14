/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.HibernateValidatorPermission;
import org.hibernate.validator.engine.HibernateValidatorEnhancedBean;
import org.hibernate.validator.internal.properties.Field;
import org.hibernate.validator.internal.properties.PropertyAccessor;
import org.hibernate.validator.internal.properties.javabean.JavaBeanAnnotatedConstrainable;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredField;

public class JavaBeanField
implements Field,
JavaBeanAnnotatedConstrainable {
    private final java.lang.reflect.Field field;
    private final String resolvedPropertyName;
    private final Type typeForValidatorResolution;
    private final Type type;

    public JavaBeanField(java.lang.reflect.Field field2, String resolvedPropertyName) {
        this.field = field2;
        this.type = ReflectionHelper.typeOf(field2);
        this.typeForValidatorResolution = ReflectionHelper.boxedType(this.type);
        this.resolvedPropertyName = resolvedPropertyName;
    }

    @Override
    public String getName() {
        return this.field.getName();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.field.getDeclaringClass();
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public Type getTypeForValidatorResolution() {
        return this.typeForValidatorResolution;
    }

    @Override
    public String getPropertyName() {
        return this.getName();
    }

    @Override
    public String getResolvedPropertyName() {
        return this.resolvedPropertyName;
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        return this.field.getAnnotatedType();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.field.getDeclaredAnnotations();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return this.field.getAnnotation(annotationClass);
    }

    @Override
    public Type getGenericType() {
        return ReflectionHelper.typeOf(this.field);
    }

    @Override
    public TypeVariable<?>[] getTypeParameters() {
        return this.field.getType().getTypeParameters();
    }

    @Override
    public PropertyAccessor createAccessor() {
        if (TypeHelper.isHibernateValidatorEnhancedBean(this.field.getDeclaringClass())) {
            return new EnhancedBeanFieldAccessor(this.field.getName());
        }
        return new FieldAccessor(this.field);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        JavaBeanField that = (JavaBeanField)o;
        if (!this.field.equals(that.field)) {
            return false;
        }
        if (!this.typeForValidatorResolution.equals(that.typeForValidatorResolution)) {
            return false;
        }
        return this.type.equals(that.type);
    }

    public int hashCode() {
        int result = this.field.hashCode();
        result = 31 * result + this.typeForValidatorResolution.hashCode();
        result = 31 * result + this.type.hashCode();
        return result;
    }

    public String toString() {
        return this.getName();
    }

    private static java.lang.reflect.Field getAccessible(java.lang.reflect.Field original) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(HibernateValidatorPermission.ACCESS_PRIVATE_MEMBERS);
        }
        Class<?> clazz = original.getDeclaringClass();
        return JavaBeanField.run(GetDeclaredField.andMakeAccessible(clazz, original.getName()));
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static class EnhancedBeanFieldAccessor
    implements PropertyAccessor {
        private final String name;

        private EnhancedBeanFieldAccessor(String name) {
            this.name = name;
        }

        @Override
        public Object getValueFrom(Object bean) {
            return ((HibernateValidatorEnhancedBean)bean).$$_hibernateValidator_getFieldValue(this.name);
        }
    }

    private static class FieldAccessor
    implements PropertyAccessor {
        private java.lang.reflect.Field accessibleField;

        private FieldAccessor(java.lang.reflect.Field field2) {
            this.accessibleField = JavaBeanField.getAccessible(field2);
        }

        @Override
        public Object getValueFrom(Object bean) {
            return ReflectionHelper.getValue(this.accessibleField, bean);
        }
    }
}

