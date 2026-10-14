/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.inject.Provider;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.messaging.Topic;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SystemInjecteeImpl;

public class OptionalActiveDescriptor<T>
extends AbstractActiveDescriptor<Optional> {
    private Injectee injectee;
    private Type requiredType;
    private ServiceLocatorImpl locator;

    public OptionalActiveDescriptor() {
    }

    OptionalActiveDescriptor(Injectee injectee, ServiceLocatorImpl locator, Type requiredType) {
        super(new HashSet<Type>(), PerLookup.class, null, new HashSet<Annotation>(), DescriptorType.CLASS, DescriptorVisibility.NORMAL, 0, null, null, locator.getPerLocatorUtilities().getAutoAnalyzerName(injectee.getInjecteeClass()), null);
        this.requiredType = requiredType;
        this.injectee = injectee;
        this.locator = locator;
    }

    @Override
    public Class<?> getImplementationClass() {
        return Optional.class;
    }

    @Override
    public Type getImplementationType() {
        return Optional.class;
    }

    @Override
    public Optional<T> create(ServiceHandle<?> root) {
        Set<Annotation> qualifierAnnotations = this.getQualifierAnnotations();
        Annotation[] annotationsArray = qualifierAnnotations.toArray(new Annotation[qualifierAnnotations.size()]);
        ServiceHandle handle2 = this.locator.getServiceHandle(this.requiredType, annotationsArray);
        if (handle2 == null) {
            Class<?> rawType = ReflectionHelper.getRawClass(this.requiredType);
            if (Provider.class.equals(rawType) || Iterable.class.equals(rawType) || IterableProvider.class.equals(rawType) || Topic.class.equals(rawType) || Optional.class.equals(rawType)) {
                SystemInjecteeImpl copy = new SystemInjecteeImpl(rawType, this.injectee.getRequiredQualifiers(), this.injectee.getPosition(), this.injectee.getParent(), true, this.injectee.isSelf(), this.injectee.getUnqualified(), this);
                ActiveDescriptor<?> descriptor = this.locator.getInjecteeDescriptor(copy);
                return Optional.of(descriptor.create(root));
            }
            ServiceHandle optionalHandle = this.locator.getServiceHandle(this.injectee.getRequiredType(), annotationsArray);
            if (optionalHandle == null) {
                return Optional.empty();
            }
            return (Optional)optionalHandle.getService();
        }
        Object service2 = handle2.getService();
        return Optional.ofNullable(service2);
    }
}

