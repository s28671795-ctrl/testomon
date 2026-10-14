/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Proxiable;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.Unproxiable;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.ScopeInfo;
import org.jvnet.hk2.internal.AutoActiveDescriptor;
import org.jvnet.hk2.internal.ClazzCreator;
import org.jvnet.hk2.internal.Closeable;
import org.jvnet.hk2.internal.Collector;
import org.jvnet.hk2.internal.Creator;
import org.jvnet.hk2.internal.ErrorInformationImpl;
import org.jvnet.hk2.internal.FactoryCreator;
import org.jvnet.hk2.internal.IndexedListData;
import org.jvnet.hk2.internal.InstanceLifecycleEventImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.Utilities;

public class SystemDescriptor<T>
implements ActiveDescriptor<T>,
Closeable {
    private final Descriptor baseDescriptor;
    private final Long id;
    private final ActiveDescriptor<T> activeDescriptor;
    private final ServiceLocatorImpl sdLocator;
    private volatile boolean reified;
    private boolean reifying = false;
    private boolean preAnalyzed = false;
    private volatile boolean closed = false;
    private final Object cacheLock = new Object();
    private boolean cacheSet = false;
    private T cachedValue;
    private Class<?> implClass;
    private Annotation scopeAnnotation;
    private Class<? extends Annotation> scope;
    private Set<Type> contracts;
    private Set<Annotation> qualifiers;
    private Creator<T> creator;
    private Long factoryLocatorId;
    private Long factoryServiceId;
    private Type implType;
    private final HashMap<ValidationService, Boolean> validationServiceCache = new HashMap();
    private final List<InstanceLifecycleListener> instanceListeners = new LinkedList<InstanceLifecycleListener>();
    private final Set<IndexedListData> myLists = new HashSet<IndexedListData>();
    private int singletonGeneration = Integer.MAX_VALUE;

    SystemDescriptor(Descriptor baseDescriptor, boolean requiresDeepCopy, ServiceLocatorImpl locator, Long serviceId) {
        this.baseDescriptor = requiresDeepCopy ? BuilderHelper.deepCopyDescriptor(baseDescriptor) : baseDescriptor;
        this.sdLocator = locator;
        this.id = serviceId;
        if (baseDescriptor instanceof ActiveDescriptor) {
            ActiveDescriptor active = (ActiveDescriptor)baseDescriptor;
            if (active.isReified()) {
                this.activeDescriptor = active;
                this.reified = true;
                if (active instanceof AutoActiveDescriptor) {
                    ((AutoActiveDescriptor)active).setHK2Parent(this);
                }
            } else {
                this.activeDescriptor = null;
                this.preAnalyzed = true;
                this.implClass = active.getImplementationClass();
                this.implType = active.getImplementationType();
                this.scopeAnnotation = active.getScopeAsAnnotation();
                this.scope = active.getScopeAnnotation();
                this.contracts = Collections.unmodifiableSet(active.getContractTypes());
                this.qualifiers = Collections.unmodifiableSet(active.getQualifierAnnotations());
            }
        } else {
            this.activeDescriptor = null;
        }
    }

    @Override
    public String getImplementation() {
        return this.baseDescriptor.getImplementation();
    }

    @Override
    public Set<String> getAdvertisedContracts() {
        return this.baseDescriptor.getAdvertisedContracts();
    }

    @Override
    public String getScope() {
        return this.baseDescriptor.getScope();
    }

    @Override
    public String getName() {
        return this.baseDescriptor.getName();
    }

    @Override
    public Set<String> getQualifiers() {
        return this.baseDescriptor.getQualifiers();
    }

    @Override
    public DescriptorType getDescriptorType() {
        return this.baseDescriptor.getDescriptorType();
    }

    @Override
    public DescriptorVisibility getDescriptorVisibility() {
        return this.baseDescriptor.getDescriptorVisibility();
    }

    @Override
    public Map<String, List<String>> getMetadata() {
        return this.baseDescriptor.getMetadata();
    }

    @Override
    public HK2Loader getLoader() {
        return this.baseDescriptor.getLoader();
    }

    @Override
    public int getRanking() {
        return this.baseDescriptor.getRanking();
    }

    @Override
    public Boolean isProxiable() {
        return this.baseDescriptor.isProxiable();
    }

    @Override
    public Boolean isProxyForSameScope() {
        return this.baseDescriptor.isProxyForSameScope();
    }

    @Override
    public String getClassAnalysisName() {
        return this.baseDescriptor.getClassAnalysisName();
    }

    @Override
    public int setRanking(int ranking) {
        return this.sdLocator.unsortIndexes(ranking, this, this.myLists);
    }

    int setRankWithLock(int ranking) {
        return this.baseDescriptor.setRanking(ranking);
    }

    void addList(IndexedListData indexedList) {
        this.myLists.add(indexedList);
    }

    void removeList(IndexedListData indexedList) {
        this.myLists.remove(indexedList);
    }

    @Override
    public Long getServiceId() {
        return this.id;
    }

    @Override
    public T getCache() {
        return this.cachedValue;
    }

    @Override
    public boolean isCacheSet() {
        return this.cacheSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setCache(T cacheMe) {
        Object object = this.cacheLock;
        synchronized (object) {
            this.cachedValue = cacheMe;
            this.cacheSet = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseCache() {
        Object object = this.cacheLock;
        synchronized (object) {
            this.cacheSet = false;
            this.cachedValue = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isReified() {
        if (this.reified) {
            return true;
        }
        SystemDescriptor systemDescriptor = this;
        synchronized (systemDescriptor) {
            return this.reified;
        }
    }

    @Override
    public Class<?> getImplementationClass() {
        this.checkState();
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getImplementationClass();
        }
        return this.implClass;
    }

    @Override
    public Type getImplementationType() {
        this.checkState();
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getImplementationType();
        }
        return this.implType;
    }

    @Override
    public Set<Type> getContractTypes() {
        this.checkState();
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getContractTypes();
        }
        return this.contracts;
    }

    @Override
    public Annotation getScopeAsAnnotation() {
        this.checkState();
        return this.scopeAnnotation;
    }

    @Override
    public Class<? extends Annotation> getScopeAnnotation() {
        this.checkState();
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getScopeAnnotation();
        }
        return this.scope;
    }

    @Override
    public Set<Annotation> getQualifierAnnotations() {
        this.checkState();
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getQualifierAnnotations();
        }
        return this.qualifiers;
    }

    @Override
    public List<Injectee> getInjectees() {
        this.checkState();
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getInjectees();
        }
        return this.creator.getInjectees();
    }

    @Override
    public Long getFactoryServiceId() {
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getFactoryServiceId();
        }
        return this.factoryServiceId;
    }

    @Override
    public Long getFactoryLocatorId() {
        if (this.activeDescriptor != null) {
            return this.activeDescriptor.getFactoryLocatorId();
        }
        return this.factoryLocatorId;
    }

    void setFactoryIds(Long factoryLocatorId, Long factoryServiceId) {
        this.factoryLocatorId = factoryLocatorId;
        this.factoryServiceId = factoryServiceId;
    }

    void invokeInstanceListeners(InstanceLifecycleEvent event) {
        for (InstanceLifecycleListener listener : this.instanceListeners) {
            listener.lifecycleEvent(event);
        }
    }

    @Override
    public T create(ServiceHandle<?> root) {
        this.checkState();
        try {
            T retVal;
            if (this.activeDescriptor != null) {
                if (!(this.activeDescriptor instanceof AutoActiveDescriptor)) {
                    this.invokeInstanceListeners(new InstanceLifecycleEventImpl(InstanceLifecycleEventType.PRE_PRODUCTION, null, this));
                }
                retVal = this.activeDescriptor.create(root);
                if (!(this.activeDescriptor instanceof AutoActiveDescriptor)) {
                    this.invokeInstanceListeners(new InstanceLifecycleEventImpl(InstanceLifecycleEventType.POST_PRODUCTION, retVal, this));
                }
            } else {
                retVal = this.creator.create(root, this);
            }
            return retVal;
        }
        catch (Throwable re2) {
            MultiException reported;
            MultiException re2;
            if (!(re2 instanceof MultiException)) {
                re2 = new MultiException(re2);
            }
            if (!(reported = (MultiException)re2).getReportToErrorService()) {
                throw (RuntimeException)re2;
            }
            LinkedList<ErrorService> errorHandlers = this.sdLocator.getErrorHandlers();
            for (ErrorService es : errorHandlers) {
                ErrorInformationImpl ei = new ErrorInformationImpl(ErrorType.SERVICE_CREATION_FAILURE, this, null, reported);
                try {
                    es.onFailure(ei);
                }
                catch (Throwable throwable) {}
            }
            throw (RuntimeException)re2;
        }
    }

    @Override
    public void dispose(T instance) {
        this.checkState();
        InstanceLifecycleEventImpl event = new InstanceLifecycleEventImpl(InstanceLifecycleEventType.PRE_DESTRUCTION, instance, this);
        this.invokeInstanceListeners(event);
        try {
            if (this.activeDescriptor != null) {
                this.activeDescriptor.dispose(instance);
                return;
            }
            this.creator.dispose(instance);
        }
        catch (Throwable re2) {
            MultiException reported;
            MultiException re2;
            if (!(re2 instanceof MultiException)) {
                re2 = new MultiException(re2);
            }
            if (!(reported = (MultiException)re2).getReportToErrorService()) {
                throw (RuntimeException)re2;
            }
            LinkedList<ErrorService> errorHandlers = this.sdLocator.getErrorHandlers();
            for (ErrorService es : errorHandlers) {
                ErrorInformationImpl ei = new ErrorInformationImpl(ErrorType.SERVICE_DESTRUCTION_FAILURE, this, null, reported);
                try {
                    es.onFailure(ei);
                }
                catch (Throwable throwable) {}
            }
            throw (RuntimeException)re2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkState() {
        if (this.reified) {
            return;
        }
        SystemDescriptor systemDescriptor = this;
        synchronized (systemDescriptor) {
            if (!this.reified) {
                throw new IllegalStateException();
            }
        }
    }

    private ActiveDescriptor<?> getFactoryDescriptor(Method provideMethod, Type factoryProvidedType, ServiceLocatorImpl locator, Collector collector) {
        if (this.factoryServiceId != null && this.factoryLocatorId != null) {
            final Long fFactoryServiceId = this.factoryServiceId;
            final Long fFactoryLocatorId = this.factoryLocatorId;
            ActiveDescriptor<?> retVal = locator.getBestDescriptor(new IndexedFilter(){

                @Override
                public boolean matches(Descriptor d) {
                    if (d.getServiceId().longValue() != fFactoryServiceId.longValue()) {
                        return false;
                    }
                    return d.getLocatorId().longValue() == fFactoryLocatorId.longValue();
                }

                @Override
                public String getAdvertisedContract() {
                    return Factory.class.getName();
                }

                @Override
                public String getName() {
                    return null;
                }
            });
            if (retVal == null) {
                collector.addThrowable(new IllegalStateException("Could not find a pre-determined factory service for " + factoryProvidedType));
            }
            return retVal;
        }
        List<ServiceHandle<?>> factoryHandles = locator.getAllServiceHandles(new ParameterizedTypeImpl((Type)((Object)Factory.class), factoryProvidedType), new Annotation[0]);
        ServiceHandle<?> factoryHandle = null;
        for (ServiceHandle<?> candidate : factoryHandles) {
            if (this.qualifiers.isEmpty()) {
                factoryHandle = candidate;
                break;
            }
            ActiveDescriptor<?> descriptorUnderTest = candidate.getActiveDescriptor();
            try {
                descriptorUnderTest = locator.reifyDescriptor(descriptorUnderTest);
            }
            catch (MultiException me) {
                collector.addThrowable(me);
                continue;
            }
            Method candidateMethod = Utilities.getFactoryProvideMethod(descriptorUnderTest.getImplementationClass());
            Set<Annotation> candidateQualifiers = Utilities.getAllQualifiers(candidateMethod, Utilities.getDefaultNameFromMethod(candidateMethod, collector), collector);
            if (!ReflectionHelper.annotationContainsAll(candidateQualifiers, this.qualifiers)) continue;
            factoryHandle = candidate;
            break;
        }
        if (factoryHandle == null) {
            collector.addThrowable(new IllegalStateException("Could not find a factory service for " + factoryProvidedType));
            return null;
        }
        ActiveDescriptor retVal = factoryHandle.getActiveDescriptor();
        this.factoryServiceId = retVal.getServiceId();
        this.factoryLocatorId = retVal.getLocatorId();
        return retVal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void reify(Class<?> implClass, Collector collector) {
        if (this.reified) {
            return;
        }
        SystemDescriptor systemDescriptor = this;
        synchronized (systemDescriptor) {
            if (this.reified) {
                return;
            }
            while (this.reifying) {
                try {
                    this.wait();
                }
                catch (InterruptedException e) {
                    collector.addThrowable(e);
                    return;
                }
            }
            if (this.reified) {
                return;
            }
            this.reifying = true;
        }
        try {
            this.internalReify(implClass, collector);
        }
        finally {
            systemDescriptor = this;
            synchronized (systemDescriptor) {
                this.reifying = false;
                this.notifyAll();
                if (!collector.hasErrors()) {
                    this.reified = true;
                } else {
                    collector.addThrowable(new IllegalArgumentException("Errors were discovered while reifying " + this));
                }
            }
        }
    }

    private void internalReify(Class<?> implClass, Collector collector) {
        String scopeName;
        if (!this.preAnalyzed) {
            this.implClass = implClass;
            this.implType = implClass;
        } else if (!implClass.equals(this.implClass)) {
            collector.addThrowable(new IllegalArgumentException("During reification a class mistmatch was found " + implClass.getName() + " is not the same as " + this.implClass.getName()));
        }
        if (this.getDescriptorType().equals((Object)DescriptorType.CLASS)) {
            if (!this.preAnalyzed) {
                this.qualifiers = Collections.unmodifiableSet(Utilities.getAllQualifiers(implClass, this.baseDescriptor.getName(), collector));
            }
            ClazzCreator myClazzCreator = new ClazzCreator(this.sdLocator, implClass);
            myClazzCreator.initialize(this, collector);
            this.creator = myClazzCreator;
            if (!this.preAnalyzed) {
                ScopeInfo si = Utilities.getScopeAnnotationType(implClass, this.baseDescriptor, collector);
                this.scopeAnnotation = si.getScope();
                this.scope = si.getAnnoType();
                this.contracts = Collections.unmodifiableSet(ReflectionHelper.getTypeClosure(implClass, this.baseDescriptor.getAdvertisedContracts()));
            }
        } else {
            ActiveDescriptor<?> factoryDescriptor;
            Type factoryProvidedType;
            Utilities.checkFactoryType(implClass, collector);
            Method provideMethod = Utilities.getFactoryProvideMethod(implClass);
            if (provideMethod == null) {
                collector.addThrowable(new IllegalArgumentException("Could not find the provide method on the class " + implClass.getName()));
                return;
            }
            if (!this.preAnalyzed) {
                this.qualifiers = Collections.unmodifiableSet(Utilities.getAllQualifiers(provideMethod, Utilities.getDefaultNameFromMethod(provideMethod, collector), collector));
            }
            if ((factoryProvidedType = provideMethod.getGenericReturnType()) instanceof TypeVariable) {
                factoryProvidedType = Utilities.getFactoryProductionType(implClass);
            }
            if ((factoryDescriptor = this.getFactoryDescriptor(provideMethod, factoryProvidedType, this.sdLocator, collector)) != null) {
                this.creator = new FactoryCreator(this.sdLocator, factoryDescriptor);
            }
            if (!this.preAnalyzed) {
                ScopeInfo si = Utilities.getScopeAnnotationType(provideMethod, this.baseDescriptor, collector);
                this.scopeAnnotation = si.getScope();
                this.scope = si.getAnnoType();
                this.contracts = Collections.unmodifiableSet(ReflectionHelper.getTypeClosure(factoryProvidedType, this.baseDescriptor.getAdvertisedContracts()));
            }
        }
        if (this.baseDescriptor.getScope() == null && this.scope == null) {
            this.scope = PerLookup.class;
        }
        if (this.baseDescriptor.getScope() != null && this.scope != null && !(scopeName = this.scope.getName()).equals(this.baseDescriptor.getScope())) {
            collector.addThrowable(new IllegalArgumentException("The scope name given in the descriptor (" + this.baseDescriptor.getScope() + ") did not match the scope annotation on the class (" + this.scope.getName() + ") in class " + Pretty.clazz(implClass)));
        }
        if (this.scope.isAnnotationPresent(Proxiable.class) && this.scope.isAnnotationPresent(Unproxiable.class)) {
            collector.addThrowable(new IllegalArgumentException("The scope " + this.scope.getName() + " is marked both @Proxiable and @Unproxiable"));
        }
        if (this.isProxiable() != null && this.isProxiable().booleanValue() && Utilities.isUnproxiableScope(this.scope)) {
            collector.addThrowable(new IllegalArgumentException("The descriptor is in an Unproxiable scope but has  isProxiable set to true"));
        }
    }

    @Override
    public Long getLocatorId() {
        return this.sdLocator.getLocatorId();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean close() {
        if (this.closed) {
            return true;
        }
        SystemDescriptor systemDescriptor = this;
        synchronized (systemDescriptor) {
            if (this.closed) {
                return true;
            }
            this.closed = true;
            return false;
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    boolean isValidating(ValidationService service2) {
        Boolean cachedResult = this.validationServiceCache.get(service2);
        if (cachedResult != null) {
            return cachedResult;
        }
        boolean decision = true;
        try {
            decision = BuilderHelper.filterMatches(this, service2.getLookupFilter());
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (decision) {
            this.validationServiceCache.put(service2, Boolean.TRUE);
        } else {
            this.validationServiceCache.put(service2, Boolean.FALSE);
        }
        return decision;
    }

    void reupInstanceListeners(List<InstanceLifecycleListener> listeners) {
        this.instanceListeners.clear();
        for (InstanceLifecycleListener listener : listeners) {
            Filter filter2 = listener.getFilter();
            if (!BuilderHelper.filterMatches(this, filter2)) continue;
            this.instanceListeners.add(listener);
        }
    }

    Class<?> getPreAnalyzedClass() {
        return this.implClass;
    }

    int getSingletonGeneration() {
        return this.singletonGeneration;
    }

    void setSingletonGeneration(int gen) {
        this.singletonGeneration = gen;
    }

    public int hashCode() {
        int low32 = this.id.intValue();
        int high32 = (int)(this.id >> 32);
        int locatorLow32 = (int)this.sdLocator.getLocatorId();
        int locatorHigh32 = (int)(this.sdLocator.getLocatorId() >> 32);
        return low32 ^ high32 ^ locatorLow32 ^ locatorHigh32;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof SystemDescriptor)) {
            return false;
        }
        SystemDescriptor sd = (SystemDescriptor)o;
        if (!sd.getServiceId().equals(this.id)) {
            return false;
        }
        return sd.getLocatorId().equals(this.sdLocator.getLocatorId());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("SystemDescriptor(");
        DescriptorImpl.pretty(sb, this);
        sb.append("\n\treified=" + this.reified);
        sb.append(")");
        return sb.toString();
    }
}

