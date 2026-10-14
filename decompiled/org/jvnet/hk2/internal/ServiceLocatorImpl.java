/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ClassAnalyzer;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.DuplicateServiceException;
import org.glassfish.hk2.api.DynamicConfigurationListener;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.api.MethodParameter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Operation;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.PreDestroy;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.api.ServiceLocatorState;
import org.glassfish.hk2.api.TwoPhaseResource;
import org.glassfish.hk2.api.TwoPhaseTransactionData;
import org.glassfish.hk2.api.Unqualified;
import org.glassfish.hk2.api.ValidationInformation;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.api.Validator;
import org.glassfish.hk2.api.messaging.Topic;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.InjecteeImpl;
import org.glassfish.hk2.utilities.cache.Cache;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.cache.CacheUtilities;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.ComputationErrorException;
import org.glassfish.hk2.utilities.cache.WeakCARCache;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;
import org.jvnet.hk2.internal.CacheKey;
import org.jvnet.hk2.internal.Collector;
import org.jvnet.hk2.internal.ConstantActiveDescriptor;
import org.jvnet.hk2.internal.DescriptorComparator;
import org.jvnet.hk2.internal.DynamicConfigurationImpl;
import org.jvnet.hk2.internal.ErrorInformationImpl;
import org.jvnet.hk2.internal.ImmediateResults;
import org.jvnet.hk2.internal.IndexedListData;
import org.jvnet.hk2.internal.IterableProviderImpl;
import org.jvnet.hk2.internal.NarrowResults;
import org.jvnet.hk2.internal.OptionalActiveDescriptor;
import org.jvnet.hk2.internal.PerLocatorUtilities;
import org.jvnet.hk2.internal.PerLookupContext;
import org.jvnet.hk2.internal.ServiceHandleComparator;
import org.jvnet.hk2.internal.ServiceHandleImpl;
import org.jvnet.hk2.internal.SingletonContext;
import org.jvnet.hk2.internal.SystemDescriptor;
import org.jvnet.hk2.internal.SystemInjecteeImpl;
import org.jvnet.hk2.internal.TopicImpl;
import org.jvnet.hk2.internal.TwoPhaseTransactionDataImpl;
import org.jvnet.hk2.internal.Utilities;
import org.jvnet.hk2.internal.ValidationInformationImpl;

public class ServiceLocatorImpl
implements ServiceLocator {
    private static final String BIND_TRACING_PATTERN_PROPERTY = "org.jvnet.hk2.properties.bind.tracing.pattern";
    private static final String BIND_TRACING_PATTERN = AccessController.doPrivileged(new PrivilegedAction<String>(){

        @Override
        public String run() {
            return System.getProperty(ServiceLocatorImpl.BIND_TRACING_PATTERN_PROPERTY);
        }
    });
    private static final String BIND_TRACING_STACKS_PROPERTY = "org.jvnet.hk2.properties.bind.tracing.stacks";
    private static boolean BIND_TRACING_STACKS = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

        @Override
        public Boolean run() {
            return Boolean.parseBoolean(System.getProperty(ServiceLocatorImpl.BIND_TRACING_STACKS_PROPERTY, "false"));
        }
    });
    private static final int CACHE_SIZE = 20000;
    private static final Object sLock = new Object();
    private static long currentLocatorId = 0L;
    static final DescriptorComparator DESCRIPTOR_COMPARATOR = new DescriptorComparator();
    private static final ServiceHandleComparator HANDLE_COMPARATOR = new ServiceHandleComparator();
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.WriteLock wLock = this.readWriteLock.writeLock();
    private final ReentrantReadWriteLock.ReadLock rLock = this.readWriteLock.readLock();
    private final AtomicLong nextServiceId = new AtomicLong();
    private final String locatorName;
    private final long id;
    private final ServiceLocatorImpl parent;
    private volatile boolean neutralContextClassLoader = true;
    private final ClassReflectionHelper classReflectionHelper = new ClassReflectionHelperImpl();
    private final PerLocatorUtilities perLocatorUtilities = new PerLocatorUtilities(this);
    private final IndexedListData allDescriptors = new IndexedListData();
    private final HashMap<String, IndexedListData> descriptorsByAdvertisedContract = new HashMap();
    private final HashMap<String, IndexedListData> descriptorsByName = new HashMap();
    private final Context<Singleton> singletonContext = new SingletonContext(this);
    private final Context<PerLookup> perLookupContext = new PerLookupContext();
    private final LinkedHashSet<ValidationService> allValidators = new LinkedHashSet();
    private final LinkedList<ErrorService> errorHandlers = new LinkedList();
    private final LinkedList<ServiceHandle<?>> configListeners = new LinkedList();
    private volatile boolean hasInterceptionServices = false;
    private final LinkedList<InterceptionService> interceptionServices = new LinkedList();
    private final Cache<Class<? extends Annotation>, Context<?>> contextCache = new Cache(new Computable<Class<? extends Annotation>, Context<?>>(){

        @Override
        public Context<?> compute(Class<? extends Annotation> a) {
            return ServiceLocatorImpl.this._resolveContext(a);
        }
    });
    private final Map<ServiceLocatorImpl, ServiceLocatorImpl> children = new WeakHashMap<ServiceLocatorImpl, ServiceLocatorImpl>();
    private final Object classAnalyzerLock = new Object();
    private final HashMap<String, ClassAnalyzer> classAnalyzers = new HashMap();
    private String defaultClassAnalyzer = "default";
    private volatile Unqualified defaultUnqualified = null;
    private ConcurrentHashMap<Class<? extends Annotation>, InjectionResolver<?>> allResolvers = new ConcurrentHashMap();
    private final Cache<SystemInjecteeImpl, InjectionResolver<?>> injecteeToResolverCache = new Cache(new Computable<SystemInjecteeImpl, InjectionResolver<?>>(){

        @Override
        public InjectionResolver<?> compute(SystemInjecteeImpl key) {
            return ServiceLocatorImpl.this.perLocatorUtilities.getInjectionResolver(ServiceLocatorImpl.this.getMe(), key);
        }
    });
    private ServiceLocatorState state = ServiceLocatorState.RUNNING;
    private final WeakCARCache<IgdCacheKey, IgdValue> igdCache = CacheUtilities.createWeakCARCache(new Computable<IgdCacheKey, IgdValue>(){

        @Override
        public IgdValue compute(IgdCacheKey key) {
            return ServiceLocatorImpl.this.igdCacheCompute(key);
        }
    }, 20000, false);
    private final WeakCARCache<IgdCacheKey, IgdValue> igashCache = CacheUtilities.createWeakCARCache(new Computable<IgdCacheKey, IgdValue>(){

        @Override
        public IgdValue compute(IgdCacheKey key) {
            List candidates = ServiceLocatorImpl.this.getDescriptors(key.filter, null, true, false, true);
            ImmediateResults immediate = ServiceLocatorImpl.this.narrow(ServiceLocatorImpl.this, candidates, key.contractOrImpl, null, null, false, true, null, key.filter, key.qualifiers);
            NarrowResults results = immediate.getTimelessResults();
            if (!results.getErrors().isEmpty()) {
                Utilities.handleErrors(results, new LinkedList<ErrorService>(ServiceLocatorImpl.this.errorHandlers));
                throw new ComputationErrorException(new IgdValue(results, immediate));
            }
            return new IgdValue(results, immediate);
        }
    }, 20000, false);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static long getAndIncrementLocatorId() {
        Object object = sLock;
        synchronized (object) {
            return currentLocatorId++;
        }
    }

    public ServiceLocatorImpl(String name, ServiceLocatorImpl parent) {
        this.locatorName = name;
        this.parent = parent;
        if (parent != null) {
            parent.addChild(this);
        }
        this.id = ServiceLocatorImpl.getAndIncrementLocatorId();
        Logger.getLogger().debug("Created ServiceLocator " + this);
        if (BIND_TRACING_PATTERN != null) {
            Logger.getLogger().debug("HK2 will trace binds and unbinds of " + BIND_TRACING_PATTERN + " with stacks " + BIND_TRACING_STACKS + " in " + this);
        }
    }

    private boolean callValidate(ValidationService vs, ValidationInformation vi) {
        try {
            return vs.getValidator().validate(vi);
        }
        catch (Throwable th) {
            LinkedList<ErrorService> localErrorServices = new LinkedList<ErrorService>(this.errorHandlers);
            MultiException useException = th instanceof MultiException ? (MultiException)th : new MultiException(th);
            ErrorInformationImpl ei = new ErrorInformationImpl(ErrorType.VALIDATE_FAILURE, vi.getCandidate(), vi.getInjectee(), useException);
            for (ErrorService errorService : localErrorServices) {
                try {
                    errorService.onFailure(ei);
                }
                catch (Throwable th2) {
                    Logger.getLogger().debug("ServiceLocatorImpl", "callValidate", th2);
                }
            }
            return false;
        }
    }

    private boolean validate(SystemDescriptor<?> descriptor, Injectee onBehalfOf, Filter filter2) {
        for (ValidationService vs : this.getAllValidators()) {
            if (!descriptor.isValidating(vs) || this.callValidate(vs, new ValidationInformationImpl(Operation.LOOKUP, descriptor, onBehalfOf, filter2))) continue;
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<SystemDescriptor<?>> getDescriptors(Filter filter2, Injectee onBehalfOf, boolean getParents, boolean doValidation, boolean getLocals) {
        LinkedList retVal;
        if (filter2 == null) {
            throw new IllegalArgumentException("filter is null");
        }
        this.rLock.lock();
        try {
            Collection<SystemDescriptor<?>> sortMeOut;
            if (filter2 instanceof IndexedFilter) {
                IndexedFilter df = (IndexedFilter)filter2;
                if (df.getName() != null) {
                    Collection<SystemDescriptor<?>> scopedByName;
                    String name = df.getName();
                    IndexedListData ild = this.descriptorsByName.get(name);
                    Collection<SystemDescriptor<?>> collection = scopedByName = ild == null ? null : ild.getSortedList();
                    if (scopedByName == null) {
                        scopedByName = Collections.emptyList();
                    }
                    if (df.getAdvertisedContract() != null) {
                        sortMeOut = new LinkedList();
                        for (SystemDescriptor<?> candidate : scopedByName) {
                            if (!candidate.getAdvertisedContracts().contains(df.getAdvertisedContract())) continue;
                            sortMeOut.add(candidate);
                        }
                    } else {
                        sortMeOut = scopedByName;
                    }
                } else if (df.getAdvertisedContract() != null) {
                    String advertisedContract = df.getAdvertisedContract();
                    IndexedListData ild = this.descriptorsByAdvertisedContract.get(advertisedContract);
                    Collection<SystemDescriptor<?>> collection = sortMeOut = ild == null ? null : ild.getSortedList();
                    if (sortMeOut == null) {
                        sortMeOut = Collections.emptyList();
                    }
                } else {
                    sortMeOut = this.allDescriptors.getSortedList();
                }
            } else {
                sortMeOut = this.allDescriptors.getSortedList();
            }
            retVal = new LinkedList();
            for (SystemDescriptor<?> candidate : sortMeOut) {
                if (!getLocals && DescriptorVisibility.LOCAL.equals((Object)candidate.getDescriptorVisibility()) || doValidation && !this.validate(candidate, onBehalfOf, filter2) || !filter2.matches(candidate)) continue;
                retVal.add(candidate);
            }
        }
        finally {
            this.rLock.unlock();
        }
        if (getParents && this.parent != null) {
            TreeSet<Descriptor> sorter = new TreeSet<Descriptor>(DESCRIPTOR_COMPARATOR);
            sorter.addAll(retVal);
            sorter.addAll(this.parent.getDescriptors(filter2, onBehalfOf, getParents, doValidation, false));
            retVal.clear();
            retVal.addAll(sorter);
        }
        return retVal;
    }

    private List<ActiveDescriptor<?>> protectedGetDescriptors(final Filter filter2) {
        return (List)AccessController.doPrivileged(new PrivilegedAction<List<ActiveDescriptor<?>>>(){

            @Override
            public List<ActiveDescriptor<?>> run() {
                return ServiceLocatorImpl.this.getDescriptors(filter2);
            }
        });
    }

    @Override
    public List<ActiveDescriptor<?>> getDescriptors(Filter filter2) {
        this.checkState();
        return (List)ReflectionHelper.cast(this.getDescriptors(filter2, null, true, true, true));
    }

    @Override
    public ActiveDescriptor<?> getBestDescriptor(Filter filter2) {
        if (filter2 == null) {
            throw new IllegalArgumentException("filter is null");
        }
        this.checkState();
        List<ActiveDescriptor<?>> sorted2 = this.getDescriptors(filter2);
        return Utilities.getFirstThingInList(sorted2);
    }

    @Override
    public ActiveDescriptor<?> reifyDescriptor(Descriptor descriptor, Injectee injectee) throws MultiException {
        this.checkState();
        if (descriptor == null) {
            throw new IllegalArgumentException();
        }
        if (!(descriptor instanceof ActiveDescriptor)) {
            SystemDescriptor sd = new SystemDescriptor(descriptor, true, this, null);
            Class<?> implClass = this.loadClass(descriptor, injectee);
            Collector collector = new Collector();
            sd.reify(implClass, collector);
            collector.throwIfErrors();
            return sd;
        }
        ActiveDescriptor active = (ActiveDescriptor)descriptor;
        if (active.isReified()) {
            return active;
        }
        SystemDescriptor sd = active instanceof SystemDescriptor ? (SystemDescriptor)active : new SystemDescriptor(descriptor, true, this, null);
        Class<?> implClass = sd.getPreAnalyzedClass();
        if (implClass == null) {
            implClass = this.loadClass(descriptor, injectee);
        }
        Collector collector = new Collector();
        sd.reify(implClass, collector);
        collector.throwIfErrors();
        return sd;
    }

    @Override
    public ActiveDescriptor<?> reifyDescriptor(Descriptor descriptor) throws MultiException {
        this.checkState();
        return this.reifyDescriptor(descriptor, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ActiveDescriptor<?> secondChanceResolve(Injectee injectee) {
        Collector collector = new Collector();
        List jitResolvers = (List)ReflectionHelper.cast(this.getAllServiceHandles(JustInTimeInjectionResolver.class, new Annotation[0]));
        try {
            Object object;
            boolean modified = false;
            boolean aJITFailed = false;
            for (ServiceHandle handle2 : jitResolvers) {
                Object jitResolver;
                if (injectee.getInjecteeClass() != null && injectee.getInjecteeClass().getName().equals(handle2.getActiveDescriptor().getImplementation())) continue;
                try {
                    jitResolver = (JustInTimeInjectionResolver)handle2.getService();
                }
                catch (MultiException me) {
                    Logger.getLogger().debug(handle2.toString(), "secondChanceResolver", me);
                    continue;
                }
                boolean jitModified = false;
                try {
                    jitModified = jitResolver.justInTimeResolution(injectee);
                }
                catch (Throwable th) {
                    collector.addThrowable(th);
                    aJITFailed = true;
                }
                modified = jitModified || modified;
            }
            if (aJITFailed) {
                collector.throwIfErrors();
            }
            if (!modified) {
                object = null;
                return object;
            }
            object = this.internalGetInjecteeDescriptor(injectee, true);
            return object;
        }
        finally {
            for (Object jitResolver : jitResolvers) {
                if (jitResolver.getActiveDescriptor().getScope() != null && !PerLookup.class.getName().equals(jitResolver.getActiveDescriptor().getScope())) continue;
                jitResolver.destroy();
            }
        }
    }

    private ActiveDescriptor<?> internalGetInjecteeDescriptor(Injectee injectee, boolean calledFromSecondChanceResolveMethod) {
        if (injectee == null) {
            throw new IllegalArgumentException();
        }
        this.checkState();
        Type requiredType = injectee.getRequiredType();
        Class<?> rawType = ReflectionHelper.getRawClass(requiredType);
        if (rawType == null) {
            throw new MultiException(new IllegalArgumentException("Invalid injectee with required type of " + injectee.getRequiredType() + " passed to getInjecteeDescriptor"));
        }
        if (Provider.class.equals(rawType) || Iterable.class.equals(rawType) || IterableProvider.class.equals(rawType)) {
            boolean isIterable = IterableProvider.class.equals(rawType);
            IterableProviderImpl value = new IterableProviderImpl(this, ReflectionHelper.getFirstTypeArgument(requiredType), injectee.getRequiredQualifiers(), injectee.getUnqualified(), injectee, isIterable);
            return new ConstantActiveDescriptor(value, this);
        }
        if (Optional.class.equals(rawType)) {
            return new OptionalActiveDescriptor(injectee, this, ReflectionHelper.getFirstTypeArgument(requiredType));
        }
        if (Topic.class.equals(rawType)) {
            TopicImpl value = new TopicImpl(this, ReflectionHelper.getFirstTypeArgument(requiredType), injectee.getRequiredQualifiers());
            return new ConstantActiveDescriptor(value, this);
        }
        Set<Annotation> qualifiersAsSet = injectee.getRequiredQualifiers();
        String name = ReflectionHelper.getNameFromAllQualifiers(qualifiersAsSet, injectee.getParent());
        Annotation[] qualifiers = qualifiersAsSet.toArray(new Annotation[qualifiersAsSet.size()]);
        return this.internalGetDescriptor(injectee, requiredType, name, injectee.getUnqualified(), false, calledFromSecondChanceResolveMethod, qualifiers);
    }

    @Override
    public ActiveDescriptor<?> getInjecteeDescriptor(Injectee injectee) throws MultiException {
        return this.internalGetInjecteeDescriptor(injectee, false);
    }

    @Override
    public <T> ServiceHandle<T> getServiceHandle(ActiveDescriptor<T> activeDescriptor, Injectee injectee) throws MultiException {
        if (activeDescriptor != null) {
            if (!(activeDescriptor instanceof SystemDescriptor) && !(activeDescriptor instanceof ConstantActiveDescriptor)) {
                throw new IllegalArgumentException("The descriptor passed to getServiceHandle must have been bound into a ServiceLocator.  The descriptor is of type " + activeDescriptor.getClass().getName());
            }
            Long sdLocator = activeDescriptor.getLocatorId();
            if (sdLocator == null) {
                throw new IllegalArgumentException("The descriptor passed to getServiceHandle is not associated with any ServiceLocator");
            }
            if (sdLocator != this.id) {
                if (this.parent != null) {
                    return this.parent.getServiceHandle(activeDescriptor, injectee);
                }
                throw new IllegalArgumentException("The descriptor passed to getServiceHandle is not associated with this ServiceLocator (id=" + this.id + ").  It is associated ServiceLocator id=" + sdLocator);
            }
            Long sdSID = activeDescriptor.getServiceId();
            if (activeDescriptor instanceof SystemDescriptor && sdSID == null) {
                throw new IllegalArgumentException("The descriptor passed to getServiceHandle was never added to this ServiceLocator (id=" + this.id + ")");
            }
        }
        return this.getServiceHandleImpl(activeDescriptor, injectee);
    }

    private <T> ServiceHandleImpl<T> getServiceHandleImpl(ActiveDescriptor<T> activeDescriptor, Injectee injectee) throws MultiException {
        if (activeDescriptor == null) {
            throw new IllegalArgumentException();
        }
        this.checkState();
        return new ServiceHandleImpl<T>(this, activeDescriptor, injectee);
    }

    @Override
    public <T> ServiceHandle<T> getServiceHandle(ActiveDescriptor<T> activeDescriptor) throws MultiException {
        return this.getServiceHandle(activeDescriptor, null);
    }

    private <T> ServiceHandleImpl<T> internalGetServiceHandle(ActiveDescriptor<T> activeDescriptor, Type requestedType, Injectee originalRequest) {
        if (activeDescriptor == null) {
            throw new IllegalArgumentException();
        }
        this.checkState();
        if (requestedType == null) {
            return this.getServiceHandleImpl(activeDescriptor, null);
        }
        Injectee useInjectee = originalRequest != null ? originalRequest : new InjecteeImpl(requestedType);
        return this.getServiceHandleImpl(activeDescriptor, useInjectee);
    }

    @Override
    @Deprecated
    public <T> T getService(ActiveDescriptor<T> activeDescriptor, ServiceHandle<?> root) throws MultiException {
        return this.getService(activeDescriptor, root, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T getService(ActiveDescriptor<T> activeDescriptor, ServiceHandle<?> root, Injectee originalRequest) throws MultiException {
        this.checkState();
        Type contractOrImpl = originalRequest == null ? null : originalRequest.getRequiredType();
        Class<?> rawClass = ReflectionHelper.getRawClass(contractOrImpl);
        if (root == null) {
            ServiceHandleImpl<T> tmpRoot = new ServiceHandleImpl<T>(this, activeDescriptor, originalRequest);
            return Utilities.createService(activeDescriptor, originalRequest, this, tmpRoot, rawClass);
        }
        ServiceHandleImpl rootImpl = (ServiceHandleImpl)root;
        ServiceHandleImpl<?> subHandle = this.internalGetServiceHandle(activeDescriptor, contractOrImpl, originalRequest);
        if (PerLookup.class.equals(activeDescriptor.getScopeAnnotation())) {
            rootImpl.addSubHandle(subHandle);
        }
        rootImpl.pushInjectee(originalRequest);
        try {
            T t2 = subHandle.getService(root);
            return t2;
        }
        finally {
            rootImpl.popInjectee();
        }
    }

    @Override
    public <T> T getService(Class<T> contractOrImpl, Annotation ... qualifiers) throws MultiException {
        return this.internalGetService(contractOrImpl, null, null, qualifiers);
    }

    @Override
    public <T> T getService(Type contractOrImpl, Annotation ... qualifiers) throws MultiException {
        return this.internalGetService(contractOrImpl, null, null, qualifiers);
    }

    @Override
    public <T> T getService(Class<T> contractOrImpl, String name, Annotation ... qualifiers) throws MultiException {
        return this.internalGetService(contractOrImpl, name, null, qualifiers);
    }

    @Override
    public <T> T getService(Type contractOrImpl, String name, Annotation ... qualifiers) throws MultiException {
        return this.internalGetService(contractOrImpl, name, null, qualifiers);
    }

    private <T> T internalGetService(Type contractOrImpl, String name, Unqualified unqualified, Annotation ... qualifiers) {
        return this.internalGetService(contractOrImpl, name, unqualified, false, qualifiers);
    }

    private <T> T internalGetService(Type contractOrImpl, String name, Unqualified unqualified, boolean calledFromSecondChanceResolveMethod, Annotation ... qualifiers) {
        this.checkState();
        Class<?> rawType = ReflectionHelper.getRawClass(contractOrImpl);
        if (rawType != null && (Provider.class.equals(rawType) || IterableProvider.class.equals(rawType))) {
            boolean isIterable = IterableProvider.class.equals(rawType);
            Type requiredType = ReflectionHelper.getFirstTypeArgument(contractOrImpl);
            HashSet<Annotation> requiredQualifiers = new HashSet<Annotation>();
            for (Annotation qualifier : qualifiers) {
                requiredQualifiers.add(qualifier);
            }
            InjecteeImpl injectee = new InjecteeImpl(requiredType);
            injectee.setRequiredQualifiers(requiredQualifiers);
            injectee.setUnqualified(unqualified);
            IterableProviderImpl retVal = new IterableProviderImpl(this, requiredType, requiredQualifiers, unqualified, injectee, isIterable);
            return (T)retVal;
        }
        ActiveDescriptor<T> ad = this.internalGetDescriptor(null, contractOrImpl, name, unqualified, false, calledFromSecondChanceResolveMethod, qualifiers);
        if (ad == null) {
            return null;
        }
        T retVal = Utilities.createService(ad, null, this, null, rawType);
        return retVal;
    }

    <T> T getUnqualifiedService(Type contractOrImpl, Unqualified unqualified, boolean isIterable, Annotation ... qualifiers) throws MultiException {
        return this.internalGetService(contractOrImpl, null, unqualified, true, qualifiers);
    }

    private <T> List<T> protectedGetAllServices(final Type contractOrImpl, final Annotation ... qualifiers) {
        return (List)AccessController.doPrivileged(new PrivilegedAction<List<T>>(){

            @Override
            public List<T> run() {
                return ServiceLocatorImpl.this.getAllServices(contractOrImpl, qualifiers);
            }
        });
    }

    @Override
    public <T> List<T> getAllServices(Class<T> contractOrImpl, Annotation ... qualifiers) throws MultiException {
        return this.getAllServices((Type)contractOrImpl, qualifiers);
    }

    @Override
    public <T> List<T> getAllServices(Type contractOrImpl, Annotation ... qualifiers) throws MultiException {
        this.checkState();
        List<?> retVal = this.internalGetAllServiceHandles(contractOrImpl, null, false, false, qualifiers);
        return retVal;
    }

    @Override
    public <T> List<T> getAllServices(Annotation qualifier, Annotation ... qualifiers) throws MultiException {
        this.checkState();
        List<ServiceHandle<?>> services = this.getAllServiceHandles(qualifier, qualifiers);
        LinkedList retVal = new LinkedList();
        for (ServiceHandle<?> service2 : services) {
            retVal.add(service2.getService());
        }
        return retVal;
    }

    @Override
    public List<?> getAllServices(Filter searchCriteria) throws MultiException {
        this.checkState();
        List<ServiceHandle<?>> handleSet = this.getAllServiceHandles(searchCriteria);
        LinkedList retVal = new LinkedList();
        for (ServiceHandle<?> handle2 : handleSet) {
            retVal.add(handle2.getService());
        }
        return retVal;
    }

    @Override
    public String getName() {
        return this.locatorName;
    }

    @Override
    public ServiceLocatorState getState() {
        this.rLock.lock();
        try {
            ServiceLocatorState serviceLocatorState = this.state;
            return serviceLocatorState;
        }
        finally {
            this.rLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        this.wLock.lock();
        try {
            if (this.state.equals((Object)ServiceLocatorState.SHUTDOWN)) {
                return;
            }
            Map<ServiceLocatorImpl, ServiceLocatorImpl> map = this.children;
            synchronized (map) {
                Iterator<ServiceLocatorImpl> childIterator = this.children.keySet().iterator();
                while (childIterator.hasNext()) {
                    ServiceLocatorImpl child = childIterator.next();
                    childIterator.remove();
                    child.shutdown();
                }
            }
            if (this.parent != null) {
                this.parent.removeChild(this);
            }
        }
        finally {
            this.wLock.unlock();
        }
        List<ServiceHandle<?>> handles = this.getAllServiceHandles(new IndexedFilter(){

            @Override
            public boolean matches(Descriptor d) {
                return d.getLocatorId().equals(ServiceLocatorImpl.this.id);
            }

            @Override
            public String getAdvertisedContract() {
                return Context.class.getName();
            }

            @Override
            public String getName() {
                return null;
            }
        });
        for (ServiceHandle<?> handle2 : handles) {
            if (!handle2.isActive()) continue;
            Context context = (Context)handle2.getService();
            context.shutdown();
        }
        this.singletonContext.shutdown();
        this.wLock.lock();
        try {
            this.state = ServiceLocatorState.SHUTDOWN;
            this.allDescriptors.clear();
            this.descriptorsByAdvertisedContract.clear();
            this.descriptorsByName.clear();
            this.allResolvers.clear();
            this.injecteeToResolverCache.clear();
            this.allValidators.clear();
            this.errorHandlers.clear();
            this.igdCache.clear();
            this.igashCache.clear();
            this.classReflectionHelper.dispose();
            this.contextCache.clear();
            this.perLocatorUtilities.shutdown();
            Map<ServiceLocatorImpl, ServiceLocatorImpl> map = this.children;
            synchronized (map) {
                this.children.clear();
            }
            Logger.getLogger().debug("Shutdown ServiceLocator " + this);
        }
        finally {
            this.wLock.unlock();
        }
        ServiceLocatorFactory.getInstance().destroy(this);
        Logger.getLogger().debug("ServiceLocator " + this + " has been shutdown");
    }

    @Override
    public <T> T create(Class<T> createMe) {
        return this.create(createMe, null);
    }

    @Override
    public <T> T create(Class<T> createMe, String strategy) {
        this.checkState();
        return Utilities.justCreate(createMe, this, strategy);
    }

    @Override
    public void inject(Object injectMe) {
        this.inject(injectMe, null);
    }

    @Override
    public Object assistedInject(Object injectMe, Method method2, MethodParameter ... params) {
        return this.assistedInject(injectMe, method2, (ServiceHandle<?>)null, params);
    }

    @Override
    public Object assistedInject(Object injectMe, Method method2, ServiceHandle<?> root, MethodParameter ... params) {
        this.checkState();
        return Utilities.justAssistedInject(injectMe, method2, this, root, params);
    }

    @Override
    public void inject(Object injectMe, String strategy) {
        this.checkState();
        Utilities.justInject(injectMe, this, strategy);
    }

    @Override
    public void postConstruct(Object postConstructMe) {
        this.postConstruct(postConstructMe, null);
    }

    @Override
    public void postConstruct(Object postConstructMe, String strategy) {
        this.checkState();
        if (postConstructMe == null) {
            throw new IllegalArgumentException();
        }
        if ((strategy == null || strategy.equals("default")) && postConstructMe instanceof PostConstruct) {
            ((PostConstruct)postConstructMe).postConstruct();
        } else {
            Utilities.justPostConstruct(postConstructMe, this, strategy);
        }
    }

    @Override
    public void preDestroy(Object preDestroyMe) {
        this.preDestroy(preDestroyMe, null);
    }

    @Override
    public void preDestroy(Object preDestroyMe, String strategy) {
        this.checkState();
        if (preDestroyMe == null) {
            throw new IllegalArgumentException();
        }
        if ((strategy == null || strategy.equals("default")) && preDestroyMe instanceof PreDestroy) {
            ((PreDestroy)preDestroyMe).preDestroy();
        } else {
            Utilities.justPreDestroy(preDestroyMe, this, strategy);
        }
    }

    @Override
    public <U> U createAndInitialize(Class<U> createMe) {
        return this.createAndInitialize(createMe, null);
    }

    @Override
    public <U> U createAndInitialize(Class<U> createMe, String strategy) {
        U retVal = this.create(createMe, strategy);
        this.inject(retVal, strategy);
        this.postConstruct(retVal, strategy);
        return retVal;
    }

    private static String getName(String name, Annotation ... qualifiers) {
        if (name != null) {
            return name;
        }
        for (Annotation qualifier : qualifiers) {
            Named named;
            if (!(qualifier instanceof Named) || (named = (Named)qualifier).value() == null || named.value().isEmpty()) continue;
            return named.value();
        }
        return null;
    }

    private IgdValue igdCacheCompute(IgdCacheKey key) {
        List<SystemDescriptor<?>> candidates = this.getDescriptors(key.filter, key.onBehalfOf, true, false, true);
        ImmediateResults immediate = this.narrow(this, candidates, key.contractOrImpl, key.name, key.onBehalfOf, true, true, null, key.filter, key.qualifiers);
        NarrowResults results = immediate.getTimelessResults();
        if (!results.getErrors().isEmpty()) {
            Utilities.handleErrors(results, new LinkedList<ErrorService>(this.errorHandlers));
            throw new ComputationErrorException(new IgdValue(results, immediate));
        }
        return new IgdValue(results, immediate);
    }

    private Unqualified getEffectiveUnqualified(Unqualified givenUnqualified, boolean isIterable, Annotation[] qualifiers) {
        if (givenUnqualified != null) {
            return givenUnqualified;
        }
        if (qualifiers.length > 0) {
            return null;
        }
        if (isIterable) {
            return null;
        }
        return this.defaultUnqualified;
    }

    private <T> ActiveDescriptor<T> internalGetDescriptor(Injectee onBehalfOf, Type contractOrImpl, String name, Unqualified unqualified, boolean isIterable, Annotation ... qualifiers) throws MultiException {
        return this.internalGetDescriptor(onBehalfOf, contractOrImpl, name, unqualified, isIterable, false, qualifiers);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T> ActiveDescriptor<T> internalGetDescriptor(Injectee onBehalfOf, Type contractOrImpl, String name, Unqualified unqualified, boolean isIterable, boolean calledFromSecondChanceResolveMethod, Annotation ... qualifiers) throws MultiException {
        ActiveDescriptor<?> postValidateResult;
        if (contractOrImpl == null) {
            throw new IllegalArgumentException();
        }
        Class<?> rawClass = ReflectionHelper.getRawClass(contractOrImpl);
        if (rawClass == null) {
            return null;
        }
        Utilities.checkLookupType(rawClass);
        rawClass = Utilities.translatePrimitiveType(rawClass);
        name = ServiceLocatorImpl.getName(name, qualifiers);
        NarrowResults results = null;
        LinkedList<ErrorService> currentErrorHandlers = null;
        ImmediateResults immediate = null;
        unqualified = this.getEffectiveUnqualified(unqualified, isIterable, qualifiers);
        CacheKey cacheKey = new CacheKey(contractOrImpl, name, unqualified, qualifiers);
        UnqualifiedIndexedFilter filter2 = new UnqualifiedIndexedFilter(rawClass.getName(), name, unqualified);
        IgdCacheKey igdCacheKey = new IgdCacheKey(cacheKey, name, onBehalfOf, contractOrImpl, rawClass, qualifiers, filter2);
        this.rLock.lock();
        try {
            IgdValue value = this.igdCache.compute(igdCacheKey);
            boolean freshOne = value.freshnessKeeper.compareAndSet(1, 2);
            if (!freshOne) {
                immediate = this.narrow(this, null, contractOrImpl, name, onBehalfOf, true, true, value.results, filter2, qualifiers);
                results = immediate.getTimelessResults();
            } else {
                results = value.results;
                immediate = value.immediate;
            }
            if (!results.getErrors().isEmpty()) {
                currentErrorHandlers = new LinkedList<ErrorService>(this.errorHandlers);
            }
        }
        finally {
            this.rLock.unlock();
        }
        if (currentErrorHandlers != null) {
            Utilities.handleErrors(results, currentErrorHandlers);
        }
        ActiveDescriptor<?> activeDescriptor = postValidateResult = immediate.getImmediateResults().isEmpty() ? null : immediate.getImmediateResults().get(0);
        if (!calledFromSecondChanceResolveMethod && postValidateResult == null) {
            Injectee injectee;
            if (onBehalfOf == null) {
                HashSet<Annotation> requiredQualifiers = new HashSet<Annotation>();
                if (qualifiers != null && qualifiers.length > 0) {
                    for (Annotation qualifier : qualifiers) {
                        if (qualifier == null) continue;
                        requiredQualifiers.add(qualifier);
                    }
                }
                InjecteeImpl injecteeImpl = new InjecteeImpl(contractOrImpl);
                injecteeImpl.setRequiredQualifiers(requiredQualifiers);
                injecteeImpl.setUnqualified(unqualified);
                injectee = injecteeImpl;
            } else {
                injectee = onBehalfOf;
            }
            postValidateResult = this.secondChanceResolve(injectee);
        }
        return postValidateResult;
    }

    @Override
    public <T> ServiceHandle<T> getServiceHandle(Class<T> contractOrImpl, Annotation ... qualifiers) throws MultiException {
        return this.getServiceHandle((Type)contractOrImpl, qualifiers);
    }

    @Override
    public <T> ServiceHandle<T> getServiceHandle(Type contractOrImpl, Annotation ... qualifiers) throws MultiException {
        this.checkState();
        ActiveDescriptor<T> ad = this.internalGetDescriptor(null, contractOrImpl, null, null, false, qualifiers);
        if (ad == null) {
            return null;
        }
        return this.getServiceHandle(ad, new InjecteeImpl(contractOrImpl));
    }

    <T> ServiceHandle<T> getUnqualifiedServiceHandle(Type contractOrImpl, Unqualified unqualified, boolean isIterable, Annotation ... qualifiers) throws MultiException {
        this.checkState();
        ActiveDescriptor<T> ad = this.internalGetDescriptor(null, contractOrImpl, null, unqualified, isIterable, qualifiers);
        if (ad == null) {
            return null;
        }
        return this.getServiceHandle(ad, new InjecteeImpl(contractOrImpl));
    }

    private List<ServiceHandle<?>> protectedGetAllServiceHandles(final Type contractOrImpl, final Annotation ... qualifiers) {
        return (List)AccessController.doPrivileged(new PrivilegedAction<List<ServiceHandle<?>>>(){

            @Override
            public List<ServiceHandle<?>> run() {
                return ServiceLocatorImpl.this.getAllServiceHandles(contractOrImpl, qualifiers);
            }
        });
    }

    @Override
    public <T> List<ServiceHandle<T>> getAllServiceHandles(Class<T> contractOrImpl, Annotation ... qualifiers) throws MultiException {
        return (List)ReflectionHelper.cast(this.getAllServiceHandles((Type)contractOrImpl, qualifiers));
    }

    @Override
    public List<ServiceHandle<?>> getAllServiceHandles(Type contractOrImpl, Annotation ... qualifiers) throws MultiException {
        return this.internalGetAllServiceHandles(contractOrImpl, null, true, false, qualifiers);
    }

    List<ServiceHandle<?>> getAllUnqualifiedServiceHandles(Type contractOrImpl, Unqualified unqualified, boolean isIterable, Annotation ... qualifiers) throws MultiException {
        return this.internalGetAllServiceHandles(contractOrImpl, unqualified, true, isIterable, qualifiers);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<?> internalGetAllServiceHandles(Type contractOrImpl, Unqualified unqualified, boolean getHandles, boolean isIterable, Annotation ... qualifiers) throws MultiException {
        if (contractOrImpl == null) {
            throw new IllegalArgumentException();
        }
        this.checkState();
        Class<?> rawClass = ReflectionHelper.getRawClass(contractOrImpl);
        if (rawClass == null) {
            throw new MultiException(new IllegalArgumentException("Type must be a class or parameterized type, it was " + contractOrImpl));
        }
        String name = rawClass.getName();
        NarrowResults results = null;
        LinkedList<ErrorService> currentErrorHandlers = null;
        ImmediateResults immediate = null;
        unqualified = this.getEffectiveUnqualified(unqualified, isIterable, qualifiers);
        CacheKey cacheKey = new CacheKey(contractOrImpl, null, unqualified, qualifiers);
        UnqualifiedIndexedFilter filter2 = new UnqualifiedIndexedFilter(name, null, unqualified);
        IgdCacheKey igdCacheKey = new IgdCacheKey(cacheKey, name, null, contractOrImpl, rawClass, qualifiers, filter2);
        this.rLock.lock();
        try {
            IgdValue value = this.igashCache.compute(igdCacheKey);
            boolean freshOne = value.freshnessKeeper.compareAndSet(1, 2);
            if (!freshOne) {
                immediate = this.narrow(this, null, contractOrImpl, null, null, false, true, value.results, filter2, qualifiers);
                results = immediate.getTimelessResults();
            } else {
                results = value.results;
                immediate = value.immediate;
            }
            if (!results.getErrors().isEmpty()) {
                currentErrorHandlers = new LinkedList<ErrorService>(this.errorHandlers);
            }
        }
        finally {
            this.rLock.unlock();
        }
        if (currentErrorHandlers != null) {
            Utilities.handleErrors(results, currentErrorHandlers);
        }
        LinkedList retVal = new LinkedList();
        for (ActiveDescriptor<?> candidate : immediate.getImmediateResults()) {
            if (getHandles) {
                retVal.add(this.internalGetServiceHandle(candidate, contractOrImpl, null));
                continue;
            }
            Object service2 = Utilities.createService(candidate, null, this, null, rawClass);
            retVal.add((ServiceHandleImpl<?>)service2);
        }
        return retVal;
    }

    @Override
    public <T> ServiceHandle<T> getServiceHandle(Class<T> contractOrImpl, String name, Annotation ... qualifiers) throws MultiException {
        return this.getServiceHandle((Type)contractOrImpl, name, qualifiers);
    }

    @Override
    public <T> ServiceHandle<T> getServiceHandle(Type contractOrImpl, String name, Annotation ... qualifiers) throws MultiException {
        this.checkState();
        ActiveDescriptor<T> ad = this.internalGetDescriptor(null, contractOrImpl, name, null, false, qualifiers);
        if (ad == null) {
            return null;
        }
        return this.internalGetServiceHandle(ad, contractOrImpl, null);
    }

    @Override
    public List<ServiceHandle<?>> getAllServiceHandles(Filter searchCriteria) throws MultiException {
        this.checkState();
        LinkedList<ErrorService> currentErrorHandlers = null;
        List candidates = (List)ReflectionHelper.cast(this.getDescriptors(searchCriteria));
        ImmediateResults immediate = this.narrow(this, candidates, null, null, null, false, false, null, searchCriteria, new Annotation[0]);
        NarrowResults results = immediate.getTimelessResults();
        if (!results.getErrors().isEmpty()) {
            currentErrorHandlers = new LinkedList<ErrorService>(this.errorHandlers);
        }
        if (currentErrorHandlers != null) {
            Utilities.handleErrors(results, currentErrorHandlers);
        }
        TreeSet retVal = new TreeSet(HANDLE_COMPARATOR);
        for (ActiveDescriptor<?> candidate : results.getResults()) {
            retVal.add(this.getServiceHandle(candidate));
        }
        return new LinkedList(retVal);
    }

    @Override
    public List<ServiceHandle<?>> getAllServiceHandles(Annotation qualifier, Annotation ... qualifiers) throws MultiException {
        this.checkState();
        if (qualifier == null) {
            throw new IllegalArgumentException("qualifier is null");
        }
        final LinkedHashSet<String> allQualifiers = new LinkedHashSet<String>();
        allQualifiers.add(qualifier.annotationType().getName());
        for (Annotation anno : qualifiers) {
            String addMe = anno.annotationType().getName();
            if (allQualifiers.contains(addMe)) {
                throw new IllegalArgumentException("Multiple qualifiers with name " + addMe);
            }
            allQualifiers.add(addMe);
        }
        return this.getAllServiceHandles(new Filter(){

            @Override
            public boolean matches(Descriptor d) {
                return d.getQualifiers().containsAll(allQualifiers);
            }
        });
    }

    List<InterceptionService> getInterceptionServices() {
        if (!this.hasInterceptionServices) {
            return null;
        }
        this.rLock.lock();
        try {
            LinkedList<InterceptionService> linkedList = new LinkedList<InterceptionService>(this.interceptionServices);
            return linkedList;
        }
        finally {
            this.rLock.unlock();
        }
    }

    private CheckConfigurationData checkConfiguration(DynamicConfigurationImpl dci) {
        LinkedList retVal = new LinkedList();
        boolean addOrRemoveOfInstanceListener = false;
        boolean addOrRemoveOfInjectionResolver = false;
        boolean addOrRemoveOfErrorHandler = false;
        boolean addOrRemoveOfClazzAnalyzer = false;
        boolean addOrRemoveOfConfigListener = false;
        boolean addOrRemoveOfInterceptionService = false;
        HashSet<String> affectedContracts = new HashSet<String>();
        TwoPhaseTransactionDataImpl transactionData = new TwoPhaseTransactionDataImpl();
        for (Filter filter2 : dci.getUnbindFilters()) {
            List<SystemDescriptor<?>> results = this.getDescriptors(filter2, null, false, false, true);
            for (SystemDescriptor<?> systemDescriptor : results) {
                affectedContracts.addAll(ServiceLocatorImpl.getAllContracts(systemDescriptor));
                if (retVal.contains(systemDescriptor)) continue;
                for (ValidationService vs : this.getAllValidators()) {
                    if (this.callValidate(vs, new ValidationInformationImpl(Operation.UNBIND, systemDescriptor))) continue;
                    throw new MultiException(new IllegalArgumentException("Descriptor " + systemDescriptor + " did not pass the UNBIND validation"));
                }
                if (systemDescriptor.getAdvertisedContracts().contains(InstanceLifecycleListener.class.getName())) {
                    addOrRemoveOfInstanceListener = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(InjectionResolver.class.getName())) {
                    addOrRemoveOfInjectionResolver = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(ErrorService.class.getName())) {
                    addOrRemoveOfErrorHandler = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(ClassAnalyzer.class.getName())) {
                    addOrRemoveOfClazzAnalyzer = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(DynamicConfigurationListener.class.getName())) {
                    addOrRemoveOfConfigListener = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(InterceptionService.class.getName())) {
                    addOrRemoveOfInterceptionService = true;
                }
                retVal.add(systemDescriptor);
                transactionData.toRemove(systemDescriptor);
            }
        }
        for (SystemDescriptor systemDescriptor : dci.getAllDescriptors()) {
            transactionData.toAdd(systemDescriptor);
            affectedContracts.addAll(ServiceLocatorImpl.getAllContracts(systemDescriptor));
            boolean checkScope = false;
            if (systemDescriptor.getAdvertisedContracts().contains(ValidationService.class.getName()) || systemDescriptor.getAdvertisedContracts().contains(ErrorService.class.getName()) || systemDescriptor.getAdvertisedContracts().contains(InterceptionService.class.getName()) || systemDescriptor.getAdvertisedContracts().contains(InstanceLifecycleListener.class.getName())) {
                this.reifyDescriptor(systemDescriptor);
                checkScope = true;
                if (systemDescriptor.getAdvertisedContracts().contains(ErrorService.class.getName())) {
                    addOrRemoveOfErrorHandler = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(InstanceLifecycleListener.class.getName())) {
                    addOrRemoveOfInstanceListener = true;
                }
                if (systemDescriptor.getAdvertisedContracts().contains(InterceptionService.class.getName())) {
                    addOrRemoveOfInterceptionService = true;
                }
            }
            if (systemDescriptor.getAdvertisedContracts().contains(InjectionResolver.class.getName())) {
                this.reifyDescriptor(systemDescriptor);
                checkScope = true;
                if (Utilities.getInjectionResolverType(systemDescriptor) == null) {
                    throw new MultiException(new IllegalArgumentException("An implementation of InjectionResolver must be a parameterized type and the actual type must be an annotation"));
                }
                addOrRemoveOfInjectionResolver = true;
            }
            if (systemDescriptor.getAdvertisedContracts().contains(DynamicConfigurationListener.class.getName())) {
                this.reifyDescriptor(systemDescriptor);
                checkScope = true;
                addOrRemoveOfConfigListener = true;
            }
            if (systemDescriptor.getAdvertisedContracts().contains(Context.class.getName())) {
                checkScope = true;
            }
            if (systemDescriptor.getAdvertisedContracts().contains(ClassAnalyzer.class.getName())) {
                addOrRemoveOfClazzAnalyzer = true;
            }
            if (checkScope) {
                String scope;
                String string = scope = systemDescriptor.getScope() == null ? PerLookup.class.getName() : systemDescriptor.getScope();
                if (!scope.equals(Singleton.class.getName())) {
                    throw new MultiException(new IllegalArgumentException("The implementation class " + systemDescriptor.getImplementation() + " must be in the Singleton scope"));
                }
            }
            for (ValidationService validationService : this.getAllValidators()) {
                Validator validator = validationService.getValidator();
                if (validator == null) {
                    throw new MultiException(new IllegalArgumentException("Validator was null from validation service" + validationService));
                }
                if (this.callValidate(validationService, new ValidationInformationImpl(Operation.BIND, systemDescriptor))) continue;
                throw new MultiException(new IllegalArgumentException("Descriptor " + systemDescriptor + " did not pass the BIND validation"));
            }
        }
        LinkedList<Filter> idempotentFilters = dci.getIdempotentFilters();
        if (!idempotentFilters.isEmpty()) {
            List<ActiveDescriptor<?>> list = this.getDescriptors(BuilderHelper.allFilter());
            LinkedList<Throwable> idempotentFailures = new LinkedList<Throwable>();
            for (ActiveDescriptor<Object> activeDescriptor : list) {
                for (Filter idempotentFilter : idempotentFilters) {
                    if (!BuilderHelper.filterMatches(activeDescriptor, idempotentFilter)) continue;
                    idempotentFailures.add(new DuplicateServiceException(activeDescriptor));
                }
            }
            if (!idempotentFailures.isEmpty()) {
                throw new MultiException(idempotentFailures);
            }
        }
        LinkedList<TwoPhaseResource> linkedList = dci.getResources();
        LinkedList<TwoPhaseResource> completedPrepares = new LinkedList<TwoPhaseResource>();
        for (TwoPhaseResource twoPhaseResource : linkedList) {
            try {
                twoPhaseResource.prepareDynamicConfiguration(transactionData);
                completedPrepares.add(twoPhaseResource);
            }
            catch (Throwable th) {
                for (TwoPhaseResource rollMe : completedPrepares) {
                    try {
                        rollMe.rollbackDynamicConfiguration(transactionData);
                    }
                    catch (Throwable ignore) {
                        Logger.getLogger().debug("Rollback of TwoPhaseResource " + twoPhaseResource + " failed with exception", ignore);
                    }
                }
                if (th instanceof RuntimeException) {
                    throw (RuntimeException)th;
                }
                throw new RuntimeException(th);
            }
        }
        return new CheckConfigurationData(retVal, addOrRemoveOfInstanceListener, addOrRemoveOfInjectionResolver, addOrRemoveOfErrorHandler, addOrRemoveOfClazzAnalyzer, addOrRemoveOfConfigListener, affectedContracts, addOrRemoveOfInterceptionService, transactionData);
    }

    private static List<String> getAllContracts(ActiveDescriptor<?> desc) {
        LinkedList<String> allContracts = new LinkedList<String>(desc.getAdvertisedContracts());
        allContracts.addAll(desc.getQualifiers());
        String scope = desc.getScope() == null ? PerLookup.class.getName() : desc.getScope();
        allContracts.add(scope);
        return allContracts;
    }

    private void removeConfigurationInternal(List<SystemDescriptor<?>> unbinds) {
        for (SystemDescriptor<?> unbind : unbinds) {
            IndexedListData ild;
            if (BIND_TRACING_PATTERN != null && ServiceLocatorImpl.doTrace(unbind)) {
                Logger.getLogger().debug("HK2 Bind Tracing: Removing Descriptor " + unbind);
                if (BIND_TRACING_STACKS) {
                    Logger.getLogger().debug("ServiceLocatorImpl", "removeConfigurationInternal", new Throwable());
                }
            }
            this.allDescriptors.removeDescriptor(unbind);
            for (String advertisedContract : ServiceLocatorImpl.getAllContracts(unbind)) {
                IndexedListData ild2 = this.descriptorsByAdvertisedContract.get(advertisedContract);
                if (ild2 == null) continue;
                ild2.removeDescriptor(unbind);
                if (!ild2.isEmpty()) continue;
                this.descriptorsByAdvertisedContract.remove(advertisedContract);
            }
            String unbindName = unbind.getName();
            if (unbindName != null && (ild = this.descriptorsByName.get(unbindName)) != null) {
                ild.removeDescriptor(unbind);
                if (ild.isEmpty()) {
                    this.descriptorsByName.remove(unbindName);
                }
            }
            if (unbind.getAdvertisedContracts().contains(ValidationService.class.getName())) {
                ServiceHandle<?> handle2 = this.getServiceHandle(unbind);
                ValidationService vs = (ValidationService)handle2.getService();
                this.allValidators.remove(vs);
            }
            if (!unbind.isReified()) continue;
            for (Injectee injectee : unbind.getInjectees()) {
                if (!(injectee instanceof SystemInjecteeImpl)) continue;
                this.injecteeToResolverCache.remove((SystemInjecteeImpl)injectee);
            }
            this.classReflectionHelper.clean(unbind.getImplementationClass());
        }
        boolean hasOneUnbind = false;
        for (SystemDescriptor<?> unbind : unbinds) {
            hasOneUnbind = true;
            unbind.close();
        }
        if (hasOneUnbind) {
            this.perLocatorUtilities.releaseCaches();
        }
    }

    private static boolean doTrace(ActiveDescriptor<?> desc) {
        if (BIND_TRACING_PATTERN == null) {
            return false;
        }
        if ("*".equals(BIND_TRACING_PATTERN)) {
            return true;
        }
        if (desc.getImplementation() == null) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(BIND_TRACING_PATTERN, "|");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (desc.getImplementation().contains(token)) {
                return true;
            }
            for (String contract : desc.getAdvertisedContracts()) {
                if (!contract.contains(token)) continue;
                return true;
            }
        }
        return false;
    }

    private List<SystemDescriptor<?>> addConfigurationInternal(DynamicConfigurationImpl dci) {
        LinkedList thingsAdded = new LinkedList();
        for (SystemDescriptor systemDescriptor : dci.getAllDescriptors()) {
            if (BIND_TRACING_PATTERN != null && ServiceLocatorImpl.doTrace(systemDescriptor)) {
                Logger.getLogger().debug("HK2 Bind Tracing: Adding Descriptor " + systemDescriptor);
                if (BIND_TRACING_STACKS) {
                    Logger.getLogger().debug("ServiceLocatorImpl", "addConfigurationInternal", new Throwable());
                }
            }
            thingsAdded.add(systemDescriptor);
            this.allDescriptors.addDescriptor(systemDescriptor);
            List<String> allContracts = ServiceLocatorImpl.getAllContracts(systemDescriptor);
            for (String advertisedContract : allContracts) {
                IndexedListData ild = this.descriptorsByAdvertisedContract.get(advertisedContract);
                if (ild == null) {
                    ild = new IndexedListData();
                    this.descriptorsByAdvertisedContract.put(advertisedContract, ild);
                }
                ild.addDescriptor(systemDescriptor);
            }
            if (systemDescriptor.getName() != null) {
                String name = systemDescriptor.getName();
                IndexedListData ild = this.descriptorsByName.get(name);
                if (ild == null) {
                    ild = new IndexedListData();
                    this.descriptorsByName.put(name, ild);
                }
                ild.addDescriptor(systemDescriptor);
            }
            if (!systemDescriptor.getAdvertisedContracts().contains(ValidationService.class.getName())) continue;
            ServiceHandle handle2 = this.getServiceHandle(systemDescriptor);
            ValidationService vs = (ValidationService)handle2.getService();
            this.allValidators.add(vs);
        }
        return thingsAdded;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void reupInjectionResolvers() {
        HashMap<Class<? extends Annotation>, InjectionResolver> newResolvers = new HashMap<Class<? extends Annotation>, InjectionResolver>();
        IndexedFilter injectionResolverFilter = BuilderHelper.createContractFilter(InjectionResolver.class.getName());
        List<ActiveDescriptor<?>> resolverDescriptors = this.protectedGetDescriptors(injectionResolverFilter);
        for (ActiveDescriptor<?> resolverDescriptor : resolverDescriptors) {
            Class<? extends Annotation> iResolve = Utilities.getInjectionResolverType(resolverDescriptor);
            if (iResolve == null || newResolvers.containsKey(iResolve)) continue;
            InjectionResolver resolver2 = (InjectionResolver)this.getServiceHandle(resolverDescriptor).getService();
            newResolvers.put(iResolve, resolver2);
        }
        ConcurrentHashMap<Class<? extends Annotation>, InjectionResolver<?>> concurrentHashMap = this.allResolvers;
        synchronized (concurrentHashMap) {
            this.allResolvers.clear();
            this.allResolvers.putAll(newResolvers);
        }
        this.injecteeToResolverCache.clear();
    }

    private void reupInterceptionServices() {
        List allInterceptionServices = this.protectedGetAllServices((Type)((Object)InterceptionService.class), new Annotation[0]);
        this.interceptionServices.clear();
        this.interceptionServices.addAll(allInterceptionServices);
        this.hasInterceptionServices = !this.interceptionServices.isEmpty();
    }

    private void reupErrorHandlers() {
        List allErrorServices = this.protectedGetAllServices((Type)((Object)ErrorService.class), new Annotation[0]);
        this.errorHandlers.clear();
        this.errorHandlers.addAll(allErrorServices);
    }

    private void reupConfigListeners() {
        List<ServiceHandle<?>> allConfigListeners = this.protectedGetAllServiceHandles((Type)((Object)DynamicConfigurationListener.class), new Annotation[0]);
        this.configListeners.clear();
        this.configListeners.addAll(allConfigListeners);
    }

    private void reupInstanceListenersHandlers(Collection<SystemDescriptor<?>> checkList) {
        List<InstanceLifecycleListener> allLifecycleListeners = this.protectedGetAllServices((Type)((Object)InstanceLifecycleListener.class), new Annotation[0]);
        for (SystemDescriptor<?> descriptor : checkList) {
            descriptor.reupInstanceListeners(allLifecycleListeners);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void reupClassAnalyzers() {
        List<ServiceHandle<?>> allAnalyzers = this.protectedGetAllServiceHandles((Type)((Object)ClassAnalyzer.class), new Annotation[0]);
        Object object = this.classAnalyzerLock;
        synchronized (object) {
            this.classAnalyzers.clear();
            for (ServiceHandle<?> handle2 : allAnalyzers) {
                ClassAnalyzer created;
                ActiveDescriptor<?> descriptor = handle2.getActiveDescriptor();
                String name = descriptor.getName();
                if (name == null || (created = (ClassAnalyzer)handle2.getService()) == null) continue;
                this.classAnalyzers.put(name, created);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void reupCache(HashSet<String> affectedContracts) {
        this.wLock.lock();
        try {
            Iterator<String> iterator2 = affectedContracts.iterator();
            while (iterator2.hasNext()) {
                String affectedContract;
                final String fAffectedContract = affectedContract = iterator2.next();
                CacheKeyFilter<IgdCacheKey> cacheKeyFilter = new CacheKeyFilter<IgdCacheKey>(){

                    @Override
                    public boolean matches(IgdCacheKey key) {
                        return key.cacheKey.matchesRemovalName(fAffectedContract);
                    }
                };
                this.igdCache.releaseMatching(cacheKeyFilter);
                this.igashCache.releaseMatching(cacheKeyFilter);
            }
        }
        finally {
            this.wLock.unlock();
        }
    }

    private void reup(List<SystemDescriptor<?>> thingsAdded, boolean instanceListenersModified, boolean injectionResolversModified, boolean errorHandlersModified, boolean classAnalyzersModified, boolean dynamicConfigurationListenersModified, HashSet<String> affectedContracts, boolean interceptionServicesModified) {
        this.reupCache(affectedContracts);
        if (injectionResolversModified) {
            this.reupInjectionResolvers();
        }
        if (errorHandlersModified) {
            this.reupErrorHandlers();
        }
        if (dynamicConfigurationListenersModified) {
            this.reupConfigListeners();
        }
        if (instanceListenersModified) {
            this.reupInstanceListenersHandlers(this.allDescriptors.getSortedList());
        } else {
            this.reupInstanceListenersHandlers(thingsAdded);
        }
        if (classAnalyzersModified) {
            this.reupClassAnalyzers();
        }
        if (interceptionServicesModified) {
            this.reupInterceptionServices();
        }
        this.contextCache.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getAllChildren(LinkedList<ServiceLocatorImpl> allMyChildren) {
        LinkedList<ServiceLocatorImpl> addMe;
        Map<ServiceLocatorImpl, ServiceLocatorImpl> map = this.children;
        synchronized (map) {
            addMe = new LinkedList<ServiceLocatorImpl>(this.children.keySet());
        }
        allMyChildren.addAll(addMe);
        for (ServiceLocatorImpl sli : addMe) {
            sli.getAllChildren(allMyChildren);
        }
    }

    private void callAllConfigurationListeners(List<ServiceHandle<?>> allListeners) {
        if (allListeners == null) {
            return;
        }
        for (ServiceHandle<?> listener : allListeners) {
            ActiveDescriptor<?> listenerDescriptor = listener.getActiveDescriptor();
            if (listenerDescriptor.getLocatorId() != this.id) continue;
            try {
                ((DynamicConfigurationListener)listener.getService()).configurationChanged();
            }
            catch (Throwable throwable) {}
        }
    }

    void addConfiguration(DynamicConfigurationImpl dci) {
        CheckConfigurationData checkData;
        LinkedList allConfigurationListeners = null;
        MultiException configurationError = null;
        this.wLock.lock();
        try {
            checkData = this.checkConfiguration(dci);
            this.removeConfigurationInternal(checkData.getUnbinds());
            List<SystemDescriptor<?>> thingsAdded = this.addConfigurationInternal(dci);
            this.reup(thingsAdded, checkData.getInstanceLifecycleModificationsMade(), checkData.getInjectionResolverModificationMade(), checkData.getErrorHandlerModificationMade(), checkData.getClassAnalyzerModificationMade(), checkData.getDynamicConfigurationListenerModificationMade(), checkData.getAffectedContracts(), checkData.getInterceptionServiceModificationMade());
            allConfigurationListeners = new LinkedList(this.configListeners);
        }
        catch (MultiException me) {
            configurationError = me;
            throw me;
        }
        finally {
            LinkedList<ErrorService> errorServices = null;
            if (configurationError != null) {
                errorServices = new LinkedList<ErrorService>(this.errorHandlers);
            }
            this.wLock.unlock();
            if (errorServices != null && !errorServices.isEmpty()) {
                for (ErrorService errorService : errorServices) {
                    try {
                        errorService.onFailure(new ErrorInformationImpl(ErrorType.DYNAMIC_CONFIGURATION_FAILURE, null, null, configurationError));
                    }
                    catch (Throwable throwable) {}
                }
            }
        }
        LinkedList<ServiceLocatorImpl> allMyChildren = new LinkedList<ServiceLocatorImpl>();
        this.getAllChildren(allMyChildren);
        for (ServiceLocatorImpl sli : allMyChildren) {
            sli.reupCache(checkData.getAffectedContracts());
        }
        this.callAllConfigurationListeners(allConfigurationListeners);
        LinkedList<TwoPhaseResource> resources = dci.getResources();
        for (TwoPhaseResource resource : resources) {
            try {
                resource.activateDynamicConfiguration(checkData.getTransactionData());
            }
            catch (Throwable ignore) {
                Logger.getLogger().debug("Activate of TwoPhaseResource " + resource + " failed with exception", ignore);
            }
        }
    }

    boolean isInjectAnnotation(Annotation annotation) {
        return this.allResolvers.containsKey(annotation.annotationType());
    }

    boolean isInjectAnnotation(Annotation annotation, boolean isConstructor) {
        InjectionResolver<?> resolver2 = this.allResolvers.get(annotation.annotationType());
        if (resolver2 == null) {
            return false;
        }
        if (isConstructor) {
            return resolver2.isConstructorParameterIndicator();
        }
        return resolver2.isMethodParameterIndicator();
    }

    InjectionResolver<?> getInjectionResolver(Class<? extends Annotation> annoType) {
        return this.allResolvers.get(annoType);
    }

    private Context<?> _resolveContext(Class<? extends Annotation> scope) throws IllegalStateException {
        Context retVal = null;
        Type[] actuals = new Type[]{scope};
        ParameterizedTypeImpl findContext = new ParameterizedTypeImpl((Type)((Object)Context.class), actuals);
        List contextHandles = (List)ReflectionHelper.cast(this.protectedGetAllServiceHandles(findContext, new Annotation[0]));
        for (ServiceHandle contextHandle : contextHandles) {
            Context context = (Context)contextHandle.getService();
            if (!context.isActive()) continue;
            if (retVal != null) {
                throw new IllegalStateException("There is more than one active context for " + scope.getName());
            }
            retVal = context;
        }
        if (retVal == null) {
            throw new IllegalStateException("Could not find an active context for " + scope.getName());
        }
        return retVal;
    }

    Context<?> resolveContext(Class<? extends Annotation> scope) throws IllegalStateException {
        if (scope.equals(Singleton.class)) {
            return this.singletonContext;
        }
        if (scope.equals(PerLookup.class)) {
            return this.perLookupContext;
        }
        Context<?> retVal = this.contextCache.compute(scope);
        if (retVal.isActive()) {
            return retVal;
        }
        this.contextCache.remove(scope);
        return this.contextCache.compute(scope);
    }

    private Class<?> loadClass(Descriptor descriptor, Injectee injectee) {
        Class<?> retVal;
        if (descriptor == null) {
            throw new IllegalArgumentException();
        }
        HK2Loader loader = descriptor.getLoader();
        if (loader == null) {
            return Utilities.loadClass(descriptor.getImplementation(), injectee);
        }
        try {
            retVal = loader.loadClass(descriptor.getImplementation());
        }
        catch (MultiException me) {
            me.addError(new IllegalStateException("Could not load descriptor " + descriptor));
            throw me;
        }
        catch (Throwable th) {
            MultiException me = new MultiException(th);
            me.addError(new IllegalStateException("Could not load descriptor " + descriptor));
            throw me;
        }
        return retVal;
    }

    private ImmediateResults narrow(ServiceLocator locator, List<SystemDescriptor<?>> candidates, Type requiredType, String name, Injectee injectee, boolean onlyOne, boolean doValidation, NarrowResults cachedResults, Filter filter2, Annotation ... qualifiers) {
        ActiveDescriptor<?> candidate;
        ImmediateResults retVal = new ImmediateResults(cachedResults);
        cachedResults = retVal.getTimelessResults();
        if (candidates != null) {
            List lCandidates = (List)ReflectionHelper.cast(candidates);
            cachedResults.setUnnarrowedResults(lCandidates);
        }
        Set<Annotation> requiredAnnotations = Utilities.fixAndCheckQualifiers(qualifiers, name);
        for (ActiveDescriptor<?> previousResult : cachedResults.getResults()) {
            if (doValidation && !this.validate((SystemDescriptor)previousResult, injectee, filter2)) continue;
            retVal.addValidatedResult(previousResult);
            if (!onlyOne) continue;
            return retVal;
        }
        if (requiredType != null && requiredType instanceof Class && ((Class)requiredType).isAnnotation()) {
            requiredType = null;
        }
        while ((candidate = cachedResults.removeUnnarrowedResult()) != null) {
            Set<Annotation> candidateAnnotations;
            boolean doReify = false;
            if (!(requiredType == null && requiredAnnotations.isEmpty() || candidate.isReified())) {
                doReify = true;
            }
            if (doReify) {
                try {
                    candidate = locator.reifyDescriptor(candidate, injectee);
                }
                catch (MultiException me) {
                    cachedResults.addError(candidate, injectee, me);
                    continue;
                }
                catch (Throwable th) {
                    cachedResults.addError(candidate, injectee, new MultiException(th));
                    continue;
                }
            }
            if (requiredType != null) {
                boolean safe = false;
                for (Type candidateType : candidate.getContractTypes()) {
                    if (!Utilities.isTypeSafe(requiredType, candidateType)) continue;
                    safe = true;
                    break;
                }
                if (!safe) continue;
            }
            if (!requiredAnnotations.isEmpty() && !ReflectionHelper.annotationContainsAll(candidateAnnotations = candidate.getQualifierAnnotations(), requiredAnnotations)) continue;
            cachedResults.addGoodResult(candidate);
            if (doValidation && !this.validate((SystemDescriptor)candidate, injectee, filter2)) continue;
            retVal.addValidatedResult(candidate);
            if (!onlyOne) continue;
            return retVal;
        }
        return retVal;
    }

    @Override
    public long getLocatorId() {
        return this.id;
    }

    long getNextServiceId() {
        return this.nextServiceId.getAndIncrement();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addChild(ServiceLocatorImpl child) {
        Map<ServiceLocatorImpl, ServiceLocatorImpl> map = this.children;
        synchronized (map) {
            this.children.put(child, null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeChild(ServiceLocatorImpl child) {
        Map<ServiceLocatorImpl, ServiceLocatorImpl> map = this.children;
        synchronized (map) {
            this.children.remove(child);
        }
    }

    private void checkState() {
        if (ServiceLocatorState.SHUTDOWN.equals((Object)this.state)) {
            throw new IllegalStateException(this + " has been shut down");
        }
    }

    private LinkedHashSet<ValidationService> getAllValidators() {
        if (this.parent == null) {
            return this.allValidators;
        }
        LinkedHashSet<ValidationService> retVal = new LinkedHashSet<ValidationService>();
        retVal.addAll(this.parent.getAllValidators());
        retVal.addAll(this.allValidators);
        return retVal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getDefaultClassAnalyzerName() {
        Object object = this.classAnalyzerLock;
        synchronized (object) {
            return this.defaultClassAnalyzer;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setDefaultClassAnalyzerName(String defaultClassAnalyzer) {
        Object object = this.classAnalyzerLock;
        synchronized (object) {
            this.defaultClassAnalyzer = defaultClassAnalyzer == null ? "default" : defaultClassAnalyzer;
        }
    }

    @Override
    public Unqualified getDefaultUnqualified() {
        this.rLock.lock();
        try {
            Unqualified unqualified = this.defaultUnqualified;
            return unqualified;
        }
        finally {
            this.rLock.unlock();
        }
    }

    @Override
    public void setDefaultUnqualified(Unqualified unqualified) {
        this.wLock.lock();
        try {
            this.defaultUnqualified = unqualified;
        }
        finally {
            this.wLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ClassAnalyzer getAnalyzer(String name, Collector collector) {
        ClassAnalyzer retVal;
        Object object = this.classAnalyzerLock;
        synchronized (object) {
            if (name == null) {
                name = this.defaultClassAnalyzer;
            }
            retVal = this.classAnalyzers.get(name);
        }
        if (retVal == null) {
            collector.addThrowable(new IllegalStateException("Could not find an implementation of ClassAnalyzer with name " + name));
            return null;
        }
        return retVal;
    }

    @Override
    public ServiceLocator getParent() {
        return this.parent;
    }

    @Override
    public boolean getNeutralContextClassLoader() {
        return this.neutralContextClassLoader;
    }

    @Override
    public void setNeutralContextClassLoader(boolean neutralContextClassLoader) {
        this.wLock.lock();
        try {
            this.neutralContextClassLoader = neutralContextClassLoader;
        }
        finally {
            this.wLock.unlock();
        }
    }

    private ServiceLocatorImpl getMe() {
        return this;
    }

    boolean hasInjectAnnotation(AnnotatedElement annotated) {
        return this.perLocatorUtilities.hasInjectAnnotation(annotated);
    }

    InjectionResolver<?> getInjectionResolverForInjectee(SystemInjecteeImpl injectee) {
        return this.injecteeToResolverCache.compute(injectee);
    }

    ClassReflectionHelper getClassReflectionHelper() {
        return this.classReflectionHelper;
    }

    LinkedList<ErrorService> getErrorHandlers() {
        this.rLock.lock();
        try {
            LinkedList<ErrorService> linkedList = new LinkedList<ErrorService>(this.errorHandlers);
            return linkedList;
        }
        finally {
            this.rLock.unlock();
        }
    }

    PerLocatorUtilities getPerLocatorUtilities() {
        return this.perLocatorUtilities;
    }

    int getNumberOfDescriptors() {
        this.rLock.lock();
        try {
            int n = this.allDescriptors.size();
            return n;
        }
        finally {
            this.rLock.unlock();
        }
    }

    int getNumberOfChildren() {
        return this.children.size();
    }

    int getServiceCacheSize() {
        return this.igdCache.getValueSize();
    }

    int getServiceCacheMaximumSize() {
        return this.igdCache.getMaxSize();
    }

    void clearServiceCache() {
        this.igdCache.clear();
    }

    int getReflectionCacheSize() {
        return this.classReflectionHelper.size();
    }

    void clearReflectionCache() {
        this.wLock.lock();
        try {
            this.classReflectionHelper.dispose();
        }
        finally {
            this.wLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    int unsortIndexes(int newRank, SystemDescriptor<?> desc, Set<IndexedListData> myLists) {
        this.wLock.lock();
        try {
            int retVal = desc.setRankWithLock(newRank);
            for (IndexedListData myList : myLists) {
                myList.unSort();
            }
            int n = retVal;
            return n;
        }
        finally {
            this.wLock.unlock();
        }
    }

    public String toString() {
        return "ServiceLocatorImpl(" + this.locatorName + "," + this.id + "," + System.identityHashCode(this) + ")";
    }

    private static class UnqualifiedIndexedFilter
    implements IndexedFilter {
        private final String contract;
        private final String name;
        private final Unqualified unqualified;

        private UnqualifiedIndexedFilter(String contract, String name, Unqualified unqualified) {
            this.contract = contract;
            this.name = name;
            this.unqualified = unqualified;
        }

        @Override
        public boolean matches(Descriptor d) {
            if (this.unqualified == null) {
                return true;
            }
            Class<? extends Annotation>[] unqualifiedAnnos = this.unqualified.value();
            if (unqualifiedAnnos.length <= 0) {
                return d.getQualifiers().isEmpty();
            }
            HashSet<String> notAllowed = new HashSet<String>();
            for (Class<? extends Annotation> notMe : unqualifiedAnnos) {
                notAllowed.add(notMe.getName());
            }
            for (String qualifier : d.getQualifiers()) {
                if (!notAllowed.contains(qualifier)) continue;
                return false;
            }
            return true;
        }

        @Override
        public String getAdvertisedContract() {
            return this.contract;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String toString() {
            return "UnqualifiedIndexFilter(" + this.contract + "," + this.name + "," + this.unqualified + "," + System.identityHashCode(this) + ")";
        }
    }

    private static class CheckConfigurationData {
        private final List<SystemDescriptor<?>> unbinds;
        private final boolean instanceLifeycleModificationMade;
        private final boolean injectionResolverModificationMade;
        private final boolean errorHandlerModificationMade;
        private final boolean classAnalyzerModificationMade;
        private final boolean dynamicConfigurationListenerModificationMade;
        private final HashSet<String> affectedContracts;
        private final boolean interceptionServiceModificationMade;
        private final TwoPhaseTransactionData transactionData;

        private CheckConfigurationData(List<SystemDescriptor<?>> unbinds, boolean instanceLifecycleModificationMade, boolean injectionResolverModificationMade, boolean errorHandlerModificationMade, boolean classAnalyzerModificationMade, boolean dynamicConfigurationListenerModificationMade, HashSet<String> affectedContracts, boolean interceptionServiceModificationMade, TwoPhaseTransactionData transactionData) {
            this.unbinds = unbinds;
            this.instanceLifeycleModificationMade = instanceLifecycleModificationMade;
            this.injectionResolverModificationMade = injectionResolverModificationMade;
            this.errorHandlerModificationMade = errorHandlerModificationMade;
            this.classAnalyzerModificationMade = classAnalyzerModificationMade;
            this.dynamicConfigurationListenerModificationMade = dynamicConfigurationListenerModificationMade;
            this.affectedContracts = affectedContracts;
            this.interceptionServiceModificationMade = interceptionServiceModificationMade;
            this.transactionData = transactionData;
        }

        private List<SystemDescriptor<?>> getUnbinds() {
            return this.unbinds;
        }

        private boolean getInstanceLifecycleModificationsMade() {
            return this.instanceLifeycleModificationMade;
        }

        private boolean getInjectionResolverModificationMade() {
            return this.injectionResolverModificationMade;
        }

        private boolean getErrorHandlerModificationMade() {
            return this.errorHandlerModificationMade;
        }

        private boolean getClassAnalyzerModificationMade() {
            return this.classAnalyzerModificationMade;
        }

        private boolean getDynamicConfigurationListenerModificationMade() {
            return this.dynamicConfigurationListenerModificationMade;
        }

        private HashSet<String> getAffectedContracts() {
            return this.affectedContracts;
        }

        private boolean getInterceptionServiceModificationMade() {
            return this.interceptionServiceModificationMade;
        }

        private TwoPhaseTransactionData getTransactionData() {
            return this.transactionData;
        }
    }

    private class IgdValue {
        final NarrowResults results;
        final ImmediateResults immediate;
        final AtomicInteger freshnessKeeper = new AtomicInteger(1);

        public IgdValue(NarrowResults results, ImmediateResults immediate) {
            this.results = results;
            this.immediate = immediate;
        }
    }

    private static final class IgdCacheKey {
        private final CacheKey cacheKey;
        private final String name;
        private final Injectee onBehalfOf;
        private final Type contractOrImpl;
        private final Annotation[] qualifiers;
        private final Filter filter;
        private final int hashCode;

        IgdCacheKey(CacheKey key, String name, Injectee onBehalfOf, Type contractOrImpl, Class<?> rawClass, Annotation[] qualifiers, Filter filter2) {
            this.cacheKey = key;
            this.name = name;
            this.onBehalfOf = onBehalfOf;
            this.contractOrImpl = contractOrImpl;
            this.qualifiers = qualifiers;
            this.filter = filter2;
            int hash = 5;
            this.hashCode = hash = 41 * hash + this.cacheKey.hashCode();
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof IgdCacheKey)) {
                return false;
            }
            IgdCacheKey other = (IgdCacheKey)obj;
            if (this.hashCode != other.hashCode) {
                return false;
            }
            return !(this.cacheKey == null ? other.cacheKey != null : !this.cacheKey.equals(other.cacheKey));
        }

        public String toString() {
            return "IgdCacheKey(" + this.cacheKey + "," + this.name + "," + this.onBehalfOf + "," + this.contractOrImpl + "," + Arrays.toString(this.qualifiers) + "," + this.filter + "," + this.filter + "," + System.identityHashCode(this) + ")";
        }
    }
}

