/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.Unqualified;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class SystemInjecteeImpl
implements Injectee {
    private final Type requiredType;
    private final Set<Annotation> qualifiers;
    private final int position;
    private final Class<?> pClass;
    private final AnnotatedElement parent;
    private final boolean isOptional;
    private final boolean isSelf;
    private final Unqualified unqualified;
    private ActiveDescriptor<?> injecteeDescriptor;
    private final Object parentIdentifier;

    SystemInjecteeImpl(Type requiredType, Set<Annotation> qualifiers, int position, AnnotatedElement parent, boolean isOptional, boolean isSelf, Unqualified unqualified, ActiveDescriptor<?> injecteeDescriptor) {
        this.requiredType = requiredType;
        this.position = position;
        this.parent = parent;
        this.qualifiers = Collections.unmodifiableSet(qualifiers);
        this.isOptional = isOptional;
        this.isSelf = isSelf;
        this.unqualified = unqualified;
        this.injecteeDescriptor = injecteeDescriptor;
        if (parent instanceof Field) {
            this.pClass = ((Field)parent).getDeclaringClass();
            this.parentIdentifier = ((Field)parent).getName();
        } else if (parent instanceof Constructor) {
            this.parentIdentifier = this.pClass = ((Constructor)parent).getDeclaringClass();
        } else {
            this.pClass = ((Method)parent).getDeclaringClass();
            this.parentIdentifier = ReflectionHelper.createMethodWrapper((Method)parent);
        }
    }

    @Override
    public Type getRequiredType() {
        ParameterizedType pt;
        TypeVariable tv;
        Type translatedRequiredType;
        if (this.requiredType instanceof TypeVariable && this.injecteeDescriptor != null && this.injecteeDescriptor.getImplementationType() != null && this.injecteeDescriptor.getImplementationType() instanceof ParameterizedType && (translatedRequiredType = ReflectionHelper.resolveKnownType(tv = (TypeVariable)this.requiredType, pt = (ParameterizedType)this.injecteeDescriptor.getImplementationType(), this.pClass)) != null) {
            return translatedRequiredType;
        }
        return this.requiredType;
    }

    @Override
    public Set<Annotation> getRequiredQualifiers() {
        return this.qualifiers;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public Class<?> getInjecteeClass() {
        return this.pClass;
    }

    @Override
    public AnnotatedElement getParent() {
        return this.parent;
    }

    @Override
    public boolean isOptional() {
        return this.isOptional;
    }

    @Override
    public boolean isSelf() {
        return this.isSelf;
    }

    @Override
    public Unqualified getUnqualified() {
        return this.unqualified;
    }

    @Override
    public ActiveDescriptor<?> getInjecteeDescriptor() {
        return this.injecteeDescriptor;
    }

    void resetInjecteeDescriptor(ActiveDescriptor<?> injecteeDescriptor) {
        this.injecteeDescriptor = injecteeDescriptor;
    }

    public int hashCode() {
        return this.position ^ this.parentIdentifier.hashCode() ^ this.pClass.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof SystemInjecteeImpl)) {
            return false;
        }
        SystemInjecteeImpl other = (SystemInjecteeImpl)o;
        if (this.position != other.getPosition()) {
            return false;
        }
        if (!this.pClass.equals(other.getInjecteeClass())) {
            return false;
        }
        return this.parentIdentifier.equals(other.parentIdentifier);
    }

    public String toString() {
        return "SystemInjecteeImpl(requiredType=" + Pretty.type(this.requiredType) + ",parent=" + Pretty.clazz(this.pClass) + ",qualifiers=" + Pretty.collection(this.qualifiers) + ",position=" + this.position + ",optional=" + this.isOptional + ",self=" + this.isSelf + ",unqualified=" + this.unqualified + "," + System.identityHashCode(this) + ")";
    }
}

