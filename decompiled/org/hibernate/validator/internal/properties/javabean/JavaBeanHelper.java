/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Optional;
import org.hibernate.validator.internal.properties.javabean.JavaBeanConstructor;
import org.hibernate.validator.internal.properties.javabean.JavaBeanExecutable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanField;
import org.hibernate.validator.internal.properties.javabean.JavaBeanGetter;
import org.hibernate.validator.internal.properties.javabean.JavaBeanMethod;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredConstructor;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredField;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;
import org.hibernate.validator.internal.util.privilegedactions.GetMethodFromGetterNameCandidates;
import org.hibernate.validator.spi.nodenameprovider.JavaBeanProperty;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.hibernate.validator.spi.properties.ConstrainableExecutable;
import org.hibernate.validator.spi.properties.GetterPropertySelectionStrategy;

public class JavaBeanHelper {
    private final GetterPropertySelectionStrategy getterPropertySelectionStrategy;
    private final PropertyNodeNameProvider propertyNodeNameProvider;

    public JavaBeanHelper(GetterPropertySelectionStrategy getterPropertySelectionStrategy, PropertyNodeNameProvider propertyNodeNameProvider) {
        this.getterPropertySelectionStrategy = getterPropertySelectionStrategy;
        this.propertyNodeNameProvider = propertyNodeNameProvider;
    }

    public GetterPropertySelectionStrategy getGetterPropertySelectionStrategy() {
        return this.getterPropertySelectionStrategy;
    }

    public PropertyNodeNameProvider getPropertyNodeNameProvider() {
        return this.propertyNodeNameProvider;
    }

    public Optional<JavaBeanField> findDeclaredField(Class<?> declaringClass, String property) {
        Contracts.assertNotNull(declaringClass, Messages.MESSAGES.classCannotBeNull());
        Field field2 = this.run(GetDeclaredField.action(declaringClass, property));
        return Optional.ofNullable(field2).map(this::field);
    }

    public Optional<JavaBeanGetter> findDeclaredGetter(Class<?> declaringClass, String property) {
        Contracts.assertNotNull(declaringClass, Messages.MESSAGES.classCannotBeNull());
        return this.findGetter(declaringClass, property, false);
    }

    public Optional<JavaBeanGetter> findGetter(Class<?> declaringClass, String property) {
        Contracts.assertNotNull(declaringClass, Messages.MESSAGES.classCannotBeNull());
        return this.findGetter(declaringClass, property, true);
    }

    private Optional<JavaBeanGetter> findGetter(Class<?> declaringClass, String property, boolean lookForMethodsOnSuperClass) {
        Method getter2 = this.run(GetMethodFromGetterNameCandidates.action(declaringClass, this.getterPropertySelectionStrategy.getGetterMethodNameCandidates(property), lookForMethodsOnSuperClass));
        if (getter2 == null) {
            return Optional.empty();
        }
        return Optional.of(new JavaBeanGetter(declaringClass, getter2, property, this.propertyNodeNameProvider.getName(new JavaBeanPropertyImpl(declaringClass, property))));
    }

    public Optional<JavaBeanMethod> findDeclaredMethod(Class<?> declaringClass, String methodName, Class<?> ... parameterTypes) {
        Method method2 = this.run(GetDeclaredMethod.action(declaringClass, methodName, parameterTypes));
        if (method2 == null) {
            return Optional.empty();
        }
        return Optional.of(this.executable(declaringClass, method2));
    }

    public <T> Optional<JavaBeanConstructor> findDeclaredConstructor(Class<T> declaringClass, Class<?> ... parameterTypes) {
        Constructor constructor = (Constructor)this.run(GetDeclaredConstructor.action(declaringClass, parameterTypes));
        if (constructor == null) {
            return Optional.empty();
        }
        return Optional.of(new JavaBeanConstructor(constructor));
    }

    public JavaBeanExecutable<?> executable(Executable executable) {
        return this.executable(executable.getDeclaringClass(), executable);
    }

    public JavaBeanExecutable<?> executable(Class<?> declaringClass, Executable executable) {
        if (executable instanceof Constructor) {
            return new JavaBeanConstructor((Constructor)executable);
        }
        return this.executable(declaringClass, (Method)executable);
    }

    public JavaBeanMethod executable(Class<?> declaringClass, Method method2) {
        JavaBeanConstrainableExecutable executable = new JavaBeanConstrainableExecutable(method2);
        Optional<String> correspondingProperty = this.getterPropertySelectionStrategy.getProperty(executable);
        if (correspondingProperty.isPresent()) {
            return new JavaBeanGetter(declaringClass, method2, correspondingProperty.get(), this.propertyNodeNameProvider.getName(new JavaBeanPropertyImpl(declaringClass, correspondingProperty.get())));
        }
        return new JavaBeanMethod(method2);
    }

    public JavaBeanField field(Field field2) {
        return new JavaBeanField(field2, this.propertyNodeNameProvider.getName(new JavaBeanPropertyImpl(field2.getDeclaringClass(), field2.getName())));
    }

    private <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static class JavaBeanPropertyImpl
    implements JavaBeanProperty {
        private final Class<?> declaringClass;
        private final String name;

        private JavaBeanPropertyImpl(Class<?> declaringClass, String name) {
            this.declaringClass = declaringClass;
            this.name = name;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return this.declaringClass;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    private static class JavaBeanConstrainableExecutable
    implements ConstrainableExecutable {
        private final Method method;

        private JavaBeanConstrainableExecutable(Method method2) {
            this.method = method2;
        }

        @Override
        public Class<?> getReturnType() {
            return this.method.getReturnType();
        }

        @Override
        public String getName() {
            return this.method.getName();
        }

        @Override
        public Class<?>[] getParameterTypes() {
            return this.method.getParameterTypes();
        }
    }
}

