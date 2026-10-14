/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.HibernateValidatorPermission;
import org.hibernate.validator.engine.HibernateValidatorEnhancedBean;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.Getter;
import org.hibernate.validator.internal.properties.PropertyAccessor;
import org.hibernate.validator.internal.properties.javabean.JavaBeanMethod;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;

public class JavaBeanGetter
extends JavaBeanMethod
implements Getter {
    private final String propertyName;
    private final String resolvedPropertyName;
    private final Class<?> declaringClass;

    public JavaBeanGetter(Class<?> declaringClass, Method method2, String propertyName, String resolvedPropertyName) {
        super(method2);
        Contracts.assertNotNull(propertyName, "Property name cannot be null.");
        this.declaringClass = declaringClass;
        this.propertyName = propertyName;
        this.resolvedPropertyName = resolvedPropertyName;
    }

    @Override
    public String getPropertyName() {
        return this.propertyName;
    }

    @Override
    public String getResolvedPropertyName() {
        return this.resolvedPropertyName;
    }

    @Override
    public boolean hasReturnValue() {
        return true;
    }

    @Override
    public boolean hasParameters() {
        return false;
    }

    @Override
    public String getParameterName(ExecutableParameterNameProvider parameterNameProvider, int parameterIndex) {
        throw new IllegalStateException("Getters may not have parameters");
    }

    @Override
    public Class<?> getDeclaringClass() {
        return this.declaringClass;
    }

    @Override
    public ConstrainedElement.ConstrainedElementKind getConstrainedElementKind() {
        return ConstrainedElement.ConstrainedElementKind.GETTER;
    }

    @Override
    public PropertyAccessor createAccessor() {
        if (TypeHelper.isHibernateValidatorEnhancedBean(((Method)this.executable).getDeclaringClass())) {
            return new EnhancedBeanGetterAccessor(((Method)this.executable).getName());
        }
        return new GetterAccessor((Method)this.executable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JavaBeanGetter that = (JavaBeanGetter)o;
        return this.propertyName.equals(that.propertyName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.propertyName.hashCode();
        return result;
    }

    private static Method getAccessible(Method original) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(HibernateValidatorPermission.ACCESS_PRIVATE_MEMBERS);
        }
        Class<?> clazz = original.getDeclaringClass();
        Method accessibleMethod = JavaBeanGetter.run(GetDeclaredMethod.andMakeAccessible(clazz, original.getName(), new Class[0]));
        return accessibleMethod;
    }

    private static <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static class EnhancedBeanGetterAccessor
    implements PropertyAccessor {
        private final String getterFullName;

        private EnhancedBeanGetterAccessor(String getterFullName) {
            this.getterFullName = getterFullName;
        }

        @Override
        public Object getValueFrom(Object bean) {
            return ((HibernateValidatorEnhancedBean)bean).$$_hibernateValidator_getGetterValue(this.getterFullName);
        }
    }

    private static class GetterAccessor
    implements PropertyAccessor {
        private Method accessibleGetter;

        private GetterAccessor(Method getter2) {
            this.accessibleGetter = JavaBeanGetter.getAccessible(getter2);
        }

        @Override
        public Object getValueFrom(Object bean) {
            return ReflectionHelper.getValue(this.accessibleGetter, bean);
        }
    }
}

