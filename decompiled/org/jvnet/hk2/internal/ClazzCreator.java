/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ClassAnalyzer;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.PreDestroy;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.internal.Collector;
import org.jvnet.hk2.internal.ConstructorActionImpl;
import org.jvnet.hk2.internal.ConstructorInterceptorHandler;
import org.jvnet.hk2.internal.Creator;
import org.jvnet.hk2.internal.InstanceLifecycleEventImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SystemDescriptor;
import org.jvnet.hk2.internal.SystemInjecteeImpl;
import org.jvnet.hk2.internal.Utilities;

public class ClazzCreator<T>
implements Creator<T> {
    private final ServiceLocatorImpl locator;
    private final Class<?> implClass;
    private final Set<ResolutionInfo> myInitializers = new LinkedHashSet<ResolutionInfo>();
    private final Set<ResolutionInfo> myFields = new LinkedHashSet<ResolutionInfo>();
    private ActiveDescriptor<?> selfDescriptor;
    private ResolutionInfo myConstructor;
    private List<SystemInjecteeImpl> allInjectees;
    private Method postConstructMethod;
    private Method preDestroyMethod;

    ClazzCreator(ServiceLocatorImpl locator, Class<?> implClass) {
        this.locator = locator;
        this.implClass = implClass;
    }

    void initialize(ActiveDescriptor<?> selfDescriptor, String analyzerName, Collector collector) {
        ClassAnalyzer analyzer;
        this.selfDescriptor = selfDescriptor;
        if (selfDescriptor != null && selfDescriptor.getAdvertisedContracts().contains(ClassAnalyzer.class.getName())) {
            String incomingAnalyzerName;
            String descriptorAnalyzerName = selfDescriptor.getName();
            if (descriptorAnalyzerName == null) {
                descriptorAnalyzerName = this.locator.getDefaultClassAnalyzerName();
            }
            if ((incomingAnalyzerName = analyzerName) == null) {
                incomingAnalyzerName = this.locator.getDefaultClassAnalyzerName();
            }
            if (descriptorAnalyzerName.equals(incomingAnalyzerName)) {
                collector.addThrowable(new IllegalArgumentException("The ClassAnalyzer named " + descriptorAnalyzerName + " is its own ClassAnalyzer. Ensure that an implementation of ClassAnalyzer is not its own ClassAnalyzer"));
                this.myConstructor = null;
                return;
            }
        }
        if ((analyzer = Utilities.getClassAnalyzer(this.locator, analyzerName, collector)) == null) {
            this.myConstructor = null;
            return;
        }
        LinkedList<SystemInjecteeImpl> baseAllInjectees = new LinkedList<SystemInjecteeImpl>();
        AccessibleObject element = Utilities.getConstructor(this.implClass, analyzer, collector);
        if (element == null) {
            this.myConstructor = null;
            return;
        }
        List<SystemInjecteeImpl> injectees = Utilities.getConstructorInjectees(element, selfDescriptor);
        if (injectees == null) {
            this.myConstructor = null;
            return;
        }
        baseAllInjectees.addAll(injectees);
        this.myConstructor = new ResolutionInfo(element, injectees);
        Set<Method> initMethods = Utilities.getInitMethods(this.implClass, analyzer, collector);
        for (Method initMethod : initMethods) {
            element = initMethod;
            injectees = Utilities.getMethodInjectees(this.implClass, initMethod, selfDescriptor);
            if (injectees == null) {
                return;
            }
            baseAllInjectees.addAll(injectees);
            this.myInitializers.add(new ResolutionInfo(element, injectees));
        }
        Set<Field> fields = Utilities.getInitFields(this.implClass, analyzer, collector);
        for (Field field2 : fields) {
            element = field2;
            injectees = Utilities.getFieldInjectees(this.implClass, field2, selfDescriptor);
            if (injectees == null) {
                return;
            }
            baseAllInjectees.addAll(injectees);
            this.myFields.add(new ResolutionInfo(element, injectees));
        }
        this.postConstructMethod = Utilities.getPostConstruct(this.implClass, analyzer, collector);
        this.preDestroyMethod = Utilities.getPreDestroy(this.implClass, analyzer, collector);
        this.allInjectees = Collections.unmodifiableList(baseAllInjectees);
        Utilities.validateSelfInjectees(selfDescriptor, this.allInjectees, collector);
    }

    void initialize(ActiveDescriptor<?> selfDescriptor, Collector collector) {
        this.initialize(selfDescriptor, selfDescriptor == null ? null : selfDescriptor.getClassAnalysisName(), collector);
    }

    void resetSelfDescriptor(ActiveDescriptor<?> selfDescriptor) {
        this.selfDescriptor = selfDescriptor;
        for (Injectee injectee : this.allInjectees) {
            if (!(injectee instanceof SystemInjecteeImpl)) continue;
            ((SystemInjecteeImpl)injectee).resetInjecteeDescriptor(selfDescriptor);
        }
    }

    private void resolve(Map<SystemInjecteeImpl, Object> addToMe, InjectionResolver<?> resolver2, SystemInjecteeImpl injectee, ServiceHandle<?> root, Collector errorCollection) {
        if (injectee.isSelf()) {
            addToMe.put(injectee, this.selfDescriptor);
            return;
        }
        Object addIn = null;
        try {
            addIn = resolver2.resolve(injectee, root);
        }
        catch (Throwable th) {
            errorCollection.addThrowable(th);
        }
        if (addIn != null) {
            addToMe.put(injectee, addIn);
        }
    }

    private Map<SystemInjecteeImpl, Object> resolveAllDependencies(ServiceHandle<?> root) throws MultiException, IllegalStateException {
        InjectionResolver<?> resolver2;
        Collector errorCollector = new Collector();
        LinkedHashMap<SystemInjecteeImpl, Object> retVal = new LinkedHashMap<SystemInjecteeImpl, Object>();
        for (SystemInjecteeImpl injectee : this.myConstructor.injectees) {
            InjectionResolver<?> resolver3 = this.locator.getInjectionResolverForInjectee(injectee);
            this.resolve(retVal, resolver3, injectee, root, errorCollector);
        }
        for (ResolutionInfo fieldRI : this.myFields) {
            for (SystemInjecteeImpl injectee : fieldRI.injectees) {
                resolver2 = this.locator.getInjectionResolverForInjectee(injectee);
                this.resolve(retVal, resolver2, injectee, root, errorCollector);
            }
        }
        for (ResolutionInfo methodRI : this.myInitializers) {
            for (SystemInjecteeImpl injectee : methodRI.injectees) {
                resolver2 = this.locator.getInjectionResolverForInjectee(injectee);
                this.resolve(retVal, resolver2, injectee, root, errorCollector);
            }
        }
        if (errorCollector.hasErrors()) {
            errorCollector.addThrowable(new IllegalArgumentException("While attempting to resolve the dependencies of " + this.implClass.getName() + " errors were found"));
            errorCollector.throwIfErrors();
        }
        return retVal;
    }

    private Object createMe(Map<SystemInjecteeImpl, Object> resolved) throws Throwable {
        Constructor c = (Constructor)this.myConstructor.baseElement;
        List injectees = this.myConstructor.injectees;
        Object[] args2 = new Object[injectees.size()];
        for (Injectee injectee : injectees) {
            args2[injectee.getPosition()] = resolved.get(injectee);
        }
        Utilities.Interceptors interceptors = Utilities.getAllInterceptors(this.locator, this.selfDescriptor, this.implClass, c);
        Map<Method, List<MethodInterceptor>> methodInterceptors = interceptors.getMethodInterceptors();
        List<ConstructorInterceptor> constructorInterceptors = interceptors.getConstructorInterceptors();
        if ((methodInterceptors == null || methodInterceptors.isEmpty()) && (constructorInterceptors == null || constructorInterceptors.isEmpty())) {
            return ReflectionHelper.makeMe(c, args2, this.locator.getNeutralContextClassLoader());
        }
        if (!Utilities.proxiesAvailable()) {
            throw new IllegalStateException("A service " + this.selfDescriptor + " needs either method or constructor interception, but proxies are not available");
        }
        boolean neutral = this.locator.getNeutralContextClassLoader();
        if (methodInterceptors == null || methodInterceptors.isEmpty()) {
            return ConstructorInterceptorHandler.construct(c, args2, neutral, constructorInterceptors);
        }
        return ConstructorInterceptorHandler.construct(c, args2, neutral, constructorInterceptors, new ConstructorActionImpl(this, methodInterceptors));
    }

    private void fieldMe(Map<SystemInjecteeImpl, Object> resolved, T t2) throws Throwable {
        for (ResolutionInfo ri : this.myFields) {
            Field field2 = (Field)ri.baseElement;
            List injectees = ri.injectees;
            Injectee fieldInjectee = null;
            Iterator iterator2 = injectees.iterator();
            while (iterator2.hasNext()) {
                Injectee candidate;
                fieldInjectee = candidate = (Injectee)iterator2.next();
            }
            Object putMeIn = resolved.get(fieldInjectee);
            ReflectionHelper.setField(field2, t2, putMeIn);
        }
    }

    private void methodMe(Map<SystemInjecteeImpl, Object> resolved, T t2) throws Throwable {
        for (ResolutionInfo ri : this.myInitializers) {
            Method m4 = (Method)ri.baseElement;
            List injectees = ri.injectees;
            Object[] args2 = new Object[injectees.size()];
            for (Injectee injectee : injectees) {
                args2[injectee.getPosition()] = resolved.get(injectee);
            }
            ReflectionHelper.invoke(t2, m4, args2, this.locator.getNeutralContextClassLoader());
        }
    }

    private void postConstructMe(T t2) throws Throwable {
        if (t2 == null) {
            return;
        }
        if (t2 instanceof PostConstruct) {
            ((PostConstruct)t2).postConstruct();
            return;
        }
        if (this.postConstructMethod == null) {
            return;
        }
        ReflectionHelper.invoke(t2, this.postConstructMethod, new Object[0], this.locator.getNeutralContextClassLoader());
    }

    private void preDestroyMe(T t2) throws Throwable {
        if (t2 == null) {
            return;
        }
        if (t2 instanceof PreDestroy) {
            ((PreDestroy)t2).preDestroy();
            return;
        }
        if (this.preDestroyMethod == null) {
            return;
        }
        ReflectionHelper.invoke(t2, this.preDestroyMethod, new Object[0], this.locator.getNeutralContextClassLoader());
    }

    @Override
    public T create(ServiceHandle<?> root, SystemDescriptor<?> eventThrower) {
        String failureLocation = "resolve";
        try {
            Map<SystemInjecteeImpl, Object> allResolved = this.resolveAllDependencies(root);
            if (eventThrower != null) {
                eventThrower.invokeInstanceListeners(new InstanceLifecycleEventImpl(InstanceLifecycleEventType.PRE_PRODUCTION, null, (Map)ReflectionHelper.cast(allResolved), eventThrower));
            }
            failureLocation = "create";
            Object retVal = this.createMe(allResolved);
            failureLocation = "field inject";
            this.fieldMe(allResolved, retVal);
            failureLocation = "method inject";
            this.methodMe(allResolved, retVal);
            failureLocation = "post construct";
            this.postConstructMe(retVal);
            if (eventThrower != null) {
                eventThrower.invokeInstanceListeners(new InstanceLifecycleEventImpl(InstanceLifecycleEventType.POST_PRODUCTION, retVal, (Map)ReflectionHelper.cast(allResolved), eventThrower));
            }
            return (T)retVal;
        }
        catch (Throwable th) {
            if (th instanceof MultiException) {
                MultiException me = (MultiException)th;
                me.addError(new IllegalStateException("Unable to perform operation: " + failureLocation + " on " + this.implClass.getName()));
                throw me;
            }
            MultiException me = new MultiException(th);
            me.addError(new IllegalStateException("Unable to perform operation: " + failureLocation + " on " + this.implClass.getName()));
            throw me;
        }
    }

    @Override
    public void dispose(T instance) {
        try {
            this.preDestroyMe(instance);
        }
        catch (Throwable th) {
            if (th instanceof MultiException) {
                throw (MultiException)th;
            }
            throw new MultiException(th);
        }
    }

    @Override
    public List<Injectee> getInjectees() {
        return (List)ReflectionHelper.cast(this.allInjectees);
    }

    ServiceLocatorImpl getServiceLocator() {
        return this.locator;
    }

    Class<?> getImplClass() {
        return this.implClass;
    }

    ActiveDescriptor<?> getUnderlyingDescriptor() {
        return this.selfDescriptor;
    }

    public String toString() {
        return "ClazzCreator(" + this.locator + "," + this.implClass.getName() + "," + System.identityHashCode(this) + ")";
    }

    private static class ResolutionInfo {
        private final AnnotatedElement baseElement;
        private final List<SystemInjecteeImpl> injectees = new LinkedList<SystemInjecteeImpl>();

        private ResolutionInfo(AnnotatedElement baseElement, List<SystemInjecteeImpl> injectees) {
            this.baseElement = baseElement;
            this.injectees.addAll(injectees);
        }

        public String toString() {
            return "ResolutionInfo(" + this.baseElement + "," + this.injectees + "," + System.identityHashCode(this) + ")";
        }
    }
}

