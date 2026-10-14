/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.jvnet.hk2.internal.ClazzCreator;
import org.jvnet.hk2.internal.Creator;
import org.jvnet.hk2.internal.SystemDescriptor;

public class AutoActiveDescriptor<T>
extends AbstractActiveDescriptor<T> {
    private static final long serialVersionUID = -7921574114250721537L;
    private Class<?> implClass;
    private Creator<T> creator;
    private SystemDescriptor<?> hk2Parent;
    private Type implType;

    public AutoActiveDescriptor() {
    }

    public AutoActiveDescriptor(Class<?> clazz, Creator<T> creator, Set<Type> advertisedContracts, Class<? extends Annotation> scope, String name, Set<Annotation> qualifiers, DescriptorVisibility descriptorVisibility, int ranking, Boolean proxy, Boolean proxyForSameScope, String classAnalysisName, Map<String, List<String>> metadata, DescriptorType descriptorType, Type clazzType) {
        super(advertisedContracts, scope, name, qualifiers, DescriptorType.CLASS, descriptorVisibility, ranking, proxy, proxyForSameScope, classAnalysisName, metadata);
        this.implClass = clazz;
        this.creator = creator;
        this.setImplementation(this.implClass.getName());
        this.setDescriptorType(descriptorType);
        this.implType = clazzType == null ? clazz : clazzType;
    }

    void resetSelfDescriptor(ActiveDescriptor<?> toMe) {
        if (!(this.creator instanceof ClazzCreator)) {
            return;
        }
        ClazzCreator cc = (ClazzCreator)this.creator;
        cc.resetSelfDescriptor(toMe);
    }

    void setHK2Parent(SystemDescriptor<?> hk2Parent) {
        this.hk2Parent = hk2Parent;
    }

    @Override
    public Class<?> getImplementationClass() {
        return this.implClass;
    }

    @Override
    public Type getImplementationType() {
        return this.implType;
    }

    @Override
    public void setImplementationType(Type t2) {
        this.implType = t2;
    }

    @Override
    public T create(ServiceHandle<?> root) {
        return this.creator.create(root, this.hk2Parent);
    }

    @Override
    public void dispose(T instance) {
        this.creator.dispose(instance);
    }

    @Override
    public List<Injectee> getInjectees() {
        return this.creator.getInjectees();
    }
}

