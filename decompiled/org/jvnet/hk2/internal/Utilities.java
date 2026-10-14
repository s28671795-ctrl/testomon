/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;
import javax.inject.Singleton;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ClassAnalyzer;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.ContractIndicator;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.DynamicConfigurationListener;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ErrorService;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.api.MethodParameter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.PreDestroy;
import org.glassfish.hk2.api.Proxiable;
import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.api.ProxyForSameScope;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.Self;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Unproxiable;
import org.glassfish.hk2.api.Unqualified;
import org.glassfish.hk2.api.UseProxy;
import org.glassfish.hk2.api.ValidationService;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.NamedImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.Constants;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.ScopeInfo;
import org.glassfish.hk2.utilities.reflection.TypeChecker;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.internal.AnnotatedElementAnnotationInfo;
import org.jvnet.hk2.internal.AutoActiveDescriptor;
import org.jvnet.hk2.internal.ClazzCreator;
import org.jvnet.hk2.internal.Collector;
import org.jvnet.hk2.internal.ConstantActiveDescriptor;
import org.jvnet.hk2.internal.ErrorInformationImpl;
import org.jvnet.hk2.internal.ErrorResults;
import org.jvnet.hk2.internal.FactoryCreator;
import org.jvnet.hk2.internal.NarrowResults;
import org.jvnet.hk2.internal.ServiceHandleImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SystemInjecteeImpl;
import org.jvnet.hk2.internal.ThreeThirtyResolver;

public class Utilities {
    private static final String USE_SOFT_REFERENCE_PROPERTY = "org.jvnet.hk2.properties.useSoftReference";
    static final boolean USE_SOFT_REFERENCE = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

        @Override
        public Boolean run() {
            return Boolean.parseBoolean(System.getProperty(Utilities.USE_SOFT_REFERENCE_PROPERTY, "true"));
        }
    });
    private static final AnnotationInformation DEFAULT_ANNOTATION_INFORMATION = new AnnotationInformation(Collections.emptySet(), false, false, null);
    private static final String PROVIDE_METHOD = "provide";
    private static final HashSet<String> NOT_INTERCEPTED = new HashSet();
    private static final Interceptors EMTPY_INTERCEPTORS;
    private static Boolean proxiesAvailable;

    public static ClassAnalyzer getClassAnalyzer(ServiceLocatorImpl sli, String analyzerName, Collector errorCollector) {
        return sli.getAnalyzer(analyzerName, errorCollector);
    }

    public static <T> Constructor<T> getConstructor(Class<T> implClass, ClassAnalyzer analyzer, Collector collector) {
        Constructor<T> element = null;
        try {
            element = analyzer.getConstructor(implClass);
        }
        catch (MultiException me) {
            collector.addMultiException(me);
            return element;
        }
        catch (Throwable th) {
            collector.addThrowable(th);
            return element;
        }
        if (element == null) {
            collector.addThrowable((Throwable)((Object)new AssertionError((Object)("null return from getConstructor method of analyzer " + analyzer + " for class " + implClass.getName()))));
            return element;
        }
        final Constructor<T> result = element;
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                result.setAccessible(true);
                return null;
            }
        });
        return element;
    }

    public static Set<Method> getInitMethods(Class<?> implClass, ClassAnalyzer analyzer, Collector collector) {
        Set<Method> retVal;
        try {
            retVal = analyzer.getInitializerMethods(implClass);
        }
        catch (MultiException me) {
            collector.addMultiException(me);
            return Collections.emptySet();
        }
        catch (Throwable th) {
            collector.addThrowable(th);
            return Collections.emptySet();
        }
        if (retVal == null) {
            collector.addThrowable((Throwable)((Object)new AssertionError((Object)("null return from getInitializerMethods method of analyzer " + analyzer + " for class " + implClass.getName()))));
            return Collections.emptySet();
        }
        return retVal;
    }

    public static Set<Field> getInitFields(Class<?> implClass, ClassAnalyzer analyzer, Collector collector) {
        Set<Field> retVal;
        try {
            retVal = analyzer.getFields(implClass);
        }
        catch (MultiException me) {
            collector.addMultiException(me);
            return Collections.emptySet();
        }
        catch (Throwable th) {
            collector.addThrowable(th);
            return Collections.emptySet();
        }
        if (retVal == null) {
            collector.addThrowable((Throwable)((Object)new AssertionError((Object)("null return from getFields method of analyzer " + analyzer + " for class " + implClass.getName()))));
            return Collections.emptySet();
        }
        return retVal;
    }

    public static Method getPostConstruct(Class<?> implClass, ClassAnalyzer analyzer, Collector collector) {
        try {
            return analyzer.getPostConstructMethod(implClass);
        }
        catch (MultiException me) {
            collector.addMultiException(me);
            return null;
        }
        catch (Throwable th) {
            collector.addThrowable(th);
            return null;
        }
    }

    public static Method getPreDestroy(Class<?> implClass, ClassAnalyzer analyzer, Collector collector) {
        try {
            return analyzer.getPreDestroyMethod(implClass);
        }
        catch (MultiException me) {
            collector.addMultiException(me);
            return null;
        }
        catch (Throwable th) {
            collector.addThrowable(th);
            return null;
        }
    }

    public static Class<?> getFactoryAwareImplementationClass(ActiveDescriptor<?> descriptor) {
        if (descriptor.getDescriptorType().equals((Object)DescriptorType.CLASS)) {
            return descriptor.getImplementationClass();
        }
        return Utilities.getFactoryProductionClass(descriptor);
    }

    public static void checkLookupType(Class<?> checkMe) {
        if (!checkMe.isAnnotation()) {
            return;
        }
        if (checkMe.isAnnotationPresent(Scope.class)) {
            return;
        }
        if (checkMe.isAnnotationPresent(Qualifier.class)) {
            return;
        }
        throw new IllegalArgumentException("Lookup type " + checkMe + " must be a scope or annotation");
    }

    public static Class<?> translatePrimitiveType(Class<?> type) {
        Class<?> translation = Constants.PRIMITIVE_MAP.get(type);
        if (translation == null) {
            return type;
        }
        return translation;
    }

    public static void handleErrors(NarrowResults results, LinkedList<ErrorService> callThese) {
        Collector collector = new Collector();
        for (ErrorResults errorResult : results.getErrors()) {
            for (ErrorService eService : callThese) {
                try {
                    eService.onFailure(new ErrorInformationImpl(ErrorType.FAILURE_TO_REIFY, errorResult.getDescriptor(), errorResult.getInjectee(), errorResult.getMe()));
                }
                catch (MultiException me) {
                    for (Throwable th : me.getErrors()) {
                        collector.addThrowable(th);
                    }
                }
                catch (Throwable th) {
                    collector.addThrowable(th);
                }
            }
        }
        collector.throwIfErrors();
    }

    public static Class<?> loadClass(String loadMe, Descriptor fromMe, Collector collector) {
        HK2Loader loader = fromMe.getLoader();
        if (loader == null) {
            ClassLoader cl = Utilities.class.getClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            try {
                return cl.loadClass(loadMe);
            }
            catch (Throwable th) {
                collector.addThrowable(th);
                return null;
            }
        }
        try {
            return loader.loadClass(loadMe);
        }
        catch (Throwable th) {
            if (th instanceof MultiException) {
                MultiException me = (MultiException)th;
                for (Throwable th2 : me.getErrors()) {
                    collector.addThrowable(th2);
                }
            } else {
                collector.addThrowable(th);
            }
            return null;
        }
    }

    public static Class<?> loadClass(String implementation, Injectee injectee) {
        AnnotatedElement parent;
        ClassLoader loader = injectee != null ? ((parent = injectee.getParent()) instanceof Constructor ? ((Constructor)parent).getDeclaringClass().getClassLoader() : (parent instanceof Method ? ((Method)parent).getDeclaringClass().getClassLoader() : (parent instanceof Field ? ((Field)parent).getDeclaringClass().getClassLoader() : injectee.getClass().getClassLoader()))) : Utilities.class.getClassLoader();
        try {
            return loader.loadClass(implementation);
        }
        catch (Throwable th) {
            ClassLoader ccl = Thread.currentThread().getContextClassLoader();
            if (ccl != null) {
                try {
                    return ccl.loadClass(implementation);
                }
                catch (Throwable th2) {
                    MultiException me = new MultiException(th);
                    me.addError(th2);
                    throw me;
                }
            }
            throw new MultiException(th);
        }
    }

    public static Class<? extends Annotation> getInjectionResolverType(ActiveDescriptor<?> desc) {
        for (Type advertisedType : desc.getContractTypes()) {
            Class<?> rawClass = ReflectionHelper.getRawClass(advertisedType);
            if (!InjectionResolver.class.equals(rawClass)) continue;
            if (!(advertisedType instanceof ParameterizedType)) {
                return null;
            }
            Type firstType = ReflectionHelper.getFirstTypeArgument(advertisedType);
            if (!(firstType instanceof Class)) {
                return null;
            }
            Class retVal = (Class)firstType;
            if (!Annotation.class.isAssignableFrom(retVal)) {
                return null;
            }
            return retVal;
        }
        return null;
    }

    private static Class<?> getFactoryProductionClass(ActiveDescriptor<?> descriptor) {
        Class<?> factoryClass = descriptor.getImplementationClass();
        Type factoryProvidedType = Utilities.getFactoryProductionType(factoryClass);
        Class<?> retVal = ReflectionHelper.getRawClass(factoryProvidedType);
        if (retVal == null && descriptor.getContractTypes().size() == 1) {
            Type contract = descriptor.getContractTypes().iterator().next();
            retVal = ReflectionHelper.getRawClass(contract);
        }
        if (retVal == null) {
            throw new MultiException((Throwable)((Object)new AssertionError((Object)("Could not find true produced type of factory " + factoryClass.getName()))));
        }
        return retVal;
    }

    public static Type getFactoryProductionType(Class<?> factoryClass) {
        Set<Type> factoryTypes = ReflectionHelper.getTypeClosure(factoryClass, Collections.singleton(Factory.class.getName()));
        ParameterizedType parameterizedType = (ParameterizedType)factoryTypes.iterator().next();
        Type factoryProvidedType = parameterizedType.getActualTypeArguments()[0];
        return factoryProvidedType;
    }

    public static void checkFactoryType(Class<?> factoryClass, Collector collector) {
        for (Type type : factoryClass.getGenericInterfaces()) {
            Type firstType;
            Class<?> rawClass = ReflectionHelper.getRawClass(type);
            if (rawClass == null || !Factory.class.equals(rawClass) || !((firstType = ReflectionHelper.getFirstTypeArgument(type)) instanceof WildcardType)) continue;
            collector.addThrowable(new IllegalArgumentException("The class " + Pretty.clazz(factoryClass) + " has a Wildcard as its type"));
        }
    }

    private static boolean hasContract(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.isAnnotationPresent(Contract.class)) {
            return true;
        }
        for (Annotation clazzAnnotation : clazz.getAnnotations()) {
            if (!clazzAnnotation.annotationType().isAnnotationPresent(ContractIndicator.class)) continue;
            return true;
        }
        return false;
    }

    private static Set<Type> getAutoAdvertisedTypes(Type t2) {
        LinkedHashSet<Type> retVal = new LinkedHashSet<Type>();
        retVal.add(t2);
        Class<?> rawClass = ReflectionHelper.getRawClass(t2);
        if (rawClass == null) {
            return retVal;
        }
        ContractsProvided provided = rawClass.getAnnotation(ContractsProvided.class);
        if (provided != null) {
            retVal.clear();
            for (Class<?> providedContract : provided.value()) {
                retVal.add(providedContract);
            }
            return retVal;
        }
        Set<Type> allTypes = ReflectionHelper.getAllTypes(t2);
        for (Type candidate : allTypes) {
            if (!Utilities.hasContract(ReflectionHelper.getRawClass(candidate))) continue;
            retVal.add(candidate);
        }
        return retVal;
    }

    public static <T> AutoActiveDescriptor<T> createAutoDescriptor(Class<T> clazz, ServiceLocatorImpl locator) throws MultiException, IllegalArgumentException, IllegalStateException {
        ProxyForSameScope pfss;
        if (clazz == null) {
            throw new IllegalArgumentException();
        }
        Collector collector = new Collector();
        Boolean proxy = null;
        Boolean proxyForSameScope = null;
        String serviceMetadata = null;
        String serviceName = null;
        Service serviceAnno = clazz.getAnnotation(Service.class);
        if (serviceAnno != null) {
            if (!"".equals(serviceAnno.name())) {
                serviceName = serviceAnno.name();
            }
            if (!"".equals(serviceAnno.metadata())) {
                serviceMetadata = serviceAnno.metadata();
            }
        }
        Set<Annotation> qualifiers = ReflectionHelper.getQualifierAnnotations(clazz);
        String name = ReflectionHelper.getNameFromAllQualifiers(qualifiers, clazz);
        if (serviceName != null && name != null) {
            if (!serviceName.equals(name)) {
                throw new IllegalArgumentException("The class " + clazz.getName() + " has an @Service name of " + serviceName + " and has an @Named value of " + name + " which names do not match");
            }
        } else if (name == null && serviceName != null) {
            name = serviceName;
        }
        qualifiers = Utilities.getAllQualifiers(clazz, name, collector);
        Set<Type> contracts = Utilities.getAutoAdvertisedTypes(clazz);
        ScopeInfo scopeInfo = Utilities.getScopeInfo(clazz, null, collector);
        Class<? extends Annotation> scope = scopeInfo.getAnnoType();
        String analyzerName = locator.getPerLocatorUtilities().getAutoAnalyzerName(clazz);
        ClazzCreator creator = new ClazzCreator(locator, clazz);
        HashMap<String, List<String>> metadata = new HashMap<String, List<String>>();
        if (serviceMetadata != null) {
            try {
                ReflectionHelper.readMetadataMap(serviceMetadata, metadata);
            }
            catch (IOException ioe) {
                metadata.clear();
                ReflectionHelper.parseServiceMetadataString(serviceMetadata, metadata);
            }
        }
        collector.throwIfErrors();
        if (scopeInfo.getScope() != null) {
            BuilderHelper.getMetadataValues(scopeInfo.getScope(), metadata);
        }
        for (Annotation qualifier : qualifiers) {
            BuilderHelper.getMetadataValues(qualifier, metadata);
        }
        UseProxy useProxy = clazz.getAnnotation(UseProxy.class);
        if (useProxy != null) {
            proxy = useProxy.value();
        }
        if ((pfss = clazz.getAnnotation(ProxyForSameScope.class)) != null) {
            proxyForSameScope = pfss.value();
        }
        DescriptorVisibility visibility = DescriptorVisibility.NORMAL;
        Visibility vi = clazz.getAnnotation(Visibility.class);
        if (vi != null) {
            visibility = vi.value();
        }
        int rank = BuilderHelper.getRank(clazz);
        AutoActiveDescriptor retVal = new AutoActiveDescriptor(clazz, creator, contracts, scope, name, qualifiers, visibility, rank, proxy, proxyForSameScope, analyzerName, metadata, DescriptorType.CLASS, clazz);
        retVal.setScopeAsAnnotation(scopeInfo.getScope());
        creator.initialize(retVal, analyzerName, collector);
        collector.throwIfErrors();
        return retVal;
    }

    public static <T> AutoActiveDescriptor<T> createAutoFactoryDescriptor(Class<T> parentClazz, ActiveDescriptor<?> factoryDescriptor, ServiceLocatorImpl locator) throws MultiException, IllegalArgumentException, IllegalStateException {
        ProxyForSameScope pfss;
        if (parentClazz == null) {
            throw new IllegalArgumentException();
        }
        Collector collector = new Collector();
        Type factoryProductionType = Utilities.getFactoryProductionType(parentClazz);
        Method provideMethod = Utilities.getFactoryProvideMethod(parentClazz);
        if (provideMethod == null) {
            collector.addThrowable(new IllegalArgumentException("Could not find the provide method on the class " + parentClazz.getName()));
            collector.throwIfErrors();
        }
        Boolean proxy = null;
        Boolean proxyForSameScope = null;
        Set<Annotation> qualifiers = ReflectionHelper.getQualifierAnnotations(provideMethod);
        String name = ReflectionHelper.getNameFromAllQualifiers(qualifiers, provideMethod);
        Set<Type> contracts = Utilities.getAutoAdvertisedTypes(factoryProductionType);
        ScopeInfo scopeInfo = Utilities.getScopeInfo(provideMethod, null, collector);
        Class<? extends Annotation> scope = scopeInfo.getAnnoType();
        FactoryCreator creator = new FactoryCreator(locator, factoryDescriptor);
        collector.throwIfErrors();
        HashMap<String, List<String>> metadata = new HashMap<String, List<String>>();
        if (scopeInfo.getScope() != null) {
            BuilderHelper.getMetadataValues(scopeInfo.getScope(), metadata);
        }
        for (Annotation qualifier : qualifiers) {
            BuilderHelper.getMetadataValues(qualifier, metadata);
        }
        UseProxy useProxy = provideMethod.getAnnotation(UseProxy.class);
        if (useProxy != null) {
            proxy = useProxy.value();
        }
        if ((pfss = provideMethod.getAnnotation(ProxyForSameScope.class)) != null) {
            proxyForSameScope = pfss.value();
        }
        DescriptorVisibility visibility = DescriptorVisibility.NORMAL;
        Visibility vi = provideMethod.getAnnotation(Visibility.class);
        if (vi != null) {
            visibility = vi.value();
        }
        int rank = 0;
        Rank ranking = provideMethod.getAnnotation(Rank.class);
        if (ranking != null) {
            rank = ranking.value();
        }
        AutoActiveDescriptor retVal = new AutoActiveDescriptor(factoryDescriptor.getImplementationClass(), creator, contracts, scope, name, qualifiers, visibility, rank, proxy, proxyForSameScope, null, metadata, DescriptorType.PROVIDE_METHOD, null);
        retVal.setScopeAsAnnotation(scopeInfo.getScope());
        collector.throwIfErrors();
        return retVal;
    }

    public static void justPreDestroy(Object preMe, ServiceLocatorImpl locator, String strategy) {
        if (preMe == null) {
            throw new IllegalArgumentException();
        }
        Collector collector = new Collector();
        ClassAnalyzer analyzer = Utilities.getClassAnalyzer(locator, strategy, collector);
        collector.throwIfErrors();
        collector.throwIfErrors();
        Class<?> baseClass = preMe.getClass();
        Method preDestroy = Utilities.getPreDestroy(baseClass, analyzer, collector);
        collector.throwIfErrors();
        if (preDestroy == null) {
            return;
        }
        try {
            ReflectionHelper.invoke(preMe, preDestroy, new Object[0], locator.getNeutralContextClassLoader());
        }
        catch (Throwable e) {
            throw new MultiException(e);
        }
    }

    public static void justPostConstruct(Object postMe, ServiceLocatorImpl locator, String strategy) {
        if (postMe == null) {
            throw new IllegalArgumentException();
        }
        Collector collector = new Collector();
        ClassAnalyzer analyzer = Utilities.getClassAnalyzer(locator, strategy, collector);
        collector.throwIfErrors();
        Class<?> baseClass = postMe.getClass();
        Method postConstruct = Utilities.getPostConstruct(baseClass, analyzer, collector);
        collector.throwIfErrors();
        if (postConstruct == null) {
            return;
        }
        try {
            ReflectionHelper.invoke(postMe, postConstruct, new Object[0], locator.getNeutralContextClassLoader());
        }
        catch (Throwable e) {
            throw new MultiException(e);
        }
    }

    public static Object justAssistedInject(Object injectMe, Method method2, ServiceLocatorImpl locator, ServiceHandle<?> root, MethodParameter ... givenValues) {
        if (injectMe == null || method2 == null) {
            throw new IllegalArgumentException("injectMe=" + injectMe + " method=" + method2);
        }
        if (givenValues == null) {
            givenValues = new MethodParameter[]{};
        }
        int numParameters = method2.getParameterTypes().length;
        HashMap<Integer, MethodParameter> knownValues = new HashMap<Integer, MethodParameter>();
        for (MethodParameter mp : givenValues) {
            int index = mp.getParameterPosition();
            if (knownValues.containsKey(index)) {
                throw new IllegalArgumentException("The given values contain more than one value for index " + index);
            }
            knownValues.put(index, mp);
            if (index >= 0 && index < numParameters) continue;
            throw new IllegalArgumentException("Index of " + mp + " is out of range of the method parameters " + method2);
        }
        List<SystemInjecteeImpl> injectees = Utilities.getMethodInjectees(injectMe.getClass(), method2, null, knownValues);
        Object[] args2 = new Object[numParameters];
        for (int lcv = 0; lcv < injectees.size(); ++lcv) {
            SystemInjecteeImpl injectee = injectees.get(lcv);
            if (injectee == null) {
                MethodParameter mp = (MethodParameter)knownValues.get(lcv);
                if (mp == null) {
                    throw new AssertionError((Object)("Error getting values " + lcv + " method=" + method2 + " injectMe=" + injectMe + " knownValues=" + knownValues));
                }
                args2[lcv] = mp.getParameterValue();
                continue;
            }
            InjectionResolver<?> resolver2 = locator.getPerLocatorUtilities().getInjectionResolver(locator, injectee);
            args2[lcv] = resolver2.resolve(injectee, root);
        }
        try {
            return ReflectionHelper.invoke(injectMe, method2, args2, locator.getNeutralContextClassLoader());
        }
        catch (MultiException me) {
            throw me;
        }
        catch (Throwable e) {
            throw new MultiException(e);
        }
    }

    public static void justInject(Object injectMe, ServiceLocatorImpl locator, String strategy) {
        if (injectMe == null) {
            throw new IllegalArgumentException();
        }
        Collector collector = new Collector();
        ClassAnalyzer analyzer = Utilities.getClassAnalyzer(locator, strategy, collector);
        collector.throwIfErrors();
        Class<?> baseClass = injectMe.getClass();
        Set<Field> fields = Utilities.getInitFields(baseClass, analyzer, collector);
        Set<Method> methods = Utilities.getInitMethods(baseClass, analyzer, collector);
        collector.throwIfErrors();
        for (Field field2 : fields) {
            InjectionResolver<?> resolver2 = locator.getPerLocatorUtilities().getInjectionResolver(locator, field2);
            List<SystemInjecteeImpl> injecteeFields = Utilities.getFieldInjectees(baseClass, field2, null);
            Utilities.validateSelfInjectees(null, injecteeFields, collector);
            collector.throwIfErrors();
            Injectee injectee = injecteeFields.get(0);
            Object fieldValue = resolver2.resolve(injectee, null);
            try {
                ReflectionHelper.setField(field2, injectMe, fieldValue);
            }
            catch (MultiException me) {
                throw me;
            }
            catch (Throwable th) {
                throw new MultiException(th);
            }
        }
        for (Method method2 : methods) {
            List<SystemInjecteeImpl> injectees = Utilities.getMethodInjectees(baseClass, method2, null);
            Utilities.validateSelfInjectees(null, injectees, collector);
            collector.throwIfErrors();
            Object[] args2 = new Object[injectees.size()];
            for (SystemInjecteeImpl injectee : injectees) {
                InjectionResolver<?> resolver3 = locator.getPerLocatorUtilities().getInjectionResolver(locator, injectee);
                args2[injectee.getPosition()] = resolver3.resolve(injectee, null);
            }
            try {
                ReflectionHelper.invoke(injectMe, method2, args2, locator.getNeutralContextClassLoader());
            }
            catch (MultiException me) {
                throw me;
            }
            catch (Throwable e) {
                throw new MultiException(e);
            }
        }
    }

    public static <T> T justCreate(Class<T> createMe, ServiceLocatorImpl locator, String strategy) {
        if (createMe == null) {
            throw new IllegalArgumentException();
        }
        Collector collector = new Collector();
        ClassAnalyzer analyzer = Utilities.getClassAnalyzer(locator, strategy, collector);
        collector.throwIfErrors();
        Constructor<T> c = Utilities.getConstructor(createMe, analyzer, collector);
        collector.throwIfErrors();
        List<SystemInjecteeImpl> injectees = Utilities.getConstructorInjectees(c, null);
        Utilities.validateSelfInjectees(null, injectees, collector);
        collector.throwIfErrors();
        Object[] args2 = new Object[injectees.size()];
        for (SystemInjecteeImpl injectee : injectees) {
            InjectionResolver<?> resolver2 = locator.getPerLocatorUtilities().getInjectionResolver(locator, injectee);
            args2[injectee.getPosition()] = resolver2.resolve(injectee, null);
        }
        try {
            return (T)ReflectionHelper.makeMe(c, args2, locator.getNeutralContextClassLoader());
        }
        catch (Throwable th) {
            throw new MultiException(th);
        }
    }

    public static Class<?>[] getInterfacesForProxy(Set<Type> contracts) {
        LinkedList retVal = new LinkedList();
        retVal.add(ProxyCtl.class);
        for (Type type : contracts) {
            Class<?> rawClass = ReflectionHelper.getRawClass(type);
            if (rawClass == null || !rawClass.isInterface()) continue;
            retVal.add(rawClass);
        }
        return retVal.toArray(new Class[retVal.size()]);
    }

    public static boolean isProxiableScope(Class<? extends Annotation> scope) {
        return scope.isAnnotationPresent(Proxiable.class);
    }

    public static boolean isUnproxiableScope(Class<? extends Annotation> scope) {
        return scope.isAnnotationPresent(Unproxiable.class);
    }

    private static boolean isProxiable(ActiveDescriptor<?> desc, Injectee injectee) {
        Boolean directed = desc.isProxiable();
        if (directed != null) {
            if (injectee == null) {
                return directed;
            }
            if (!directed.booleanValue()) {
                return false;
            }
            ActiveDescriptor<?> injecteeDescriptor = injectee.getInjecteeDescriptor();
            if (injecteeDescriptor == null) {
                return true;
            }
            Boolean sameScope = desc.isProxyForSameScope();
            if (sameScope == null || sameScope.booleanValue()) {
                return true;
            }
            return !desc.getScope().equals(injecteeDescriptor.getScope());
        }
        Class<Annotation> scopeAnnotation = desc.getScopeAnnotation();
        if (!scopeAnnotation.isAnnotationPresent(Proxiable.class)) {
            return false;
        }
        if (injectee == null) {
            return true;
        }
        ActiveDescriptor<?> injecteeDescriptor = injectee.getInjecteeDescriptor();
        if (injecteeDescriptor == null) {
            return true;
        }
        Proxiable proxiable = scopeAnnotation.getAnnotation(Proxiable.class);
        Boolean proxyForSameScope = desc.isProxyForSameScope();
        if (proxyForSameScope != null ? proxyForSameScope != false : proxiable == null || proxiable.proxyForSameScope()) {
            return true;
        }
        return !desc.getScope().equals(injecteeDescriptor.getScope());
    }

    public static <T> T getFirstThingInList(List<T> set) {
        Iterator<T> iterator2 = set.iterator();
        if (iterator2.hasNext()) {
            T t2 = iterator2.next();
            return t2;
        }
        return null;
    }

    public static ActiveDescriptor<ServiceLocator> getLocatorDescriptor(ServiceLocator locator) {
        HashSet<Type> contracts = new HashSet<Type>();
        contracts.add((Type)((Object)ServiceLocator.class));
        Set<Annotation> qualifiers = Collections.emptySet();
        ConstantActiveDescriptor<ServiceLocator> retVal = new ConstantActiveDescriptor<ServiceLocator>(locator, contracts, PerLookup.class, null, qualifiers, DescriptorVisibility.LOCAL, 0, null, null, null, locator.getLocatorId(), null);
        return retVal;
    }

    public static ActiveDescriptor<InjectionResolver<Inject>> getThreeThirtyDescriptor(ServiceLocatorImpl locator) {
        ThreeThirtyResolver threeThirtyResolver = new ThreeThirtyResolver(locator);
        HashSet<Type> contracts = new HashSet<Type>();
        Type[] actuals = new Type[]{Inject.class};
        contracts.add(new ParameterizedTypeImpl((Type)((Object)InjectionResolver.class), actuals));
        HashSet<Annotation> qualifiers = new HashSet<Annotation>();
        qualifiers.add(new NamedImpl("SystemInjectResolver"));
        ConstantActiveDescriptor<InjectionResolver<Inject>> retVal = new ConstantActiveDescriptor<InjectionResolver<Inject>>(threeThirtyResolver, contracts, Singleton.class, "SystemInjectResolver", qualifiers, DescriptorVisibility.LOCAL, 0, null, null, null, locator.getLocatorId(), null);
        return retVal;
    }

    public static Constructor<?> findProducerConstructor(Class<?> annotatedType, ServiceLocatorImpl locator, Collector collector) {
        Constructor<?> zeroArgConstructor = null;
        Constructor<?> aConstructorWithInjectAnnotation = null;
        Set<Constructor<?>> allConstructors = Utilities.getAllConstructors(annotatedType);
        for (Constructor<?> constructor : allConstructors) {
            Type[] rawParameters = constructor.getGenericParameterTypes();
            if (rawParameters.length <= 0) {
                zeroArgConstructor = constructor;
            }
            if (locator.hasInjectAnnotation(constructor)) {
                if (aConstructorWithInjectAnnotation != null) {
                    collector.addThrowable(new IllegalArgumentException("There is more than one constructor on class " + Pretty.clazz(annotatedType)));
                    return null;
                }
                aConstructorWithInjectAnnotation = constructor;
            }
            if (Utilities.isProperConstructor(constructor)) continue;
            collector.addThrowable(new IllegalArgumentException("The constructor for " + Pretty.clazz(annotatedType) + " may not have an annotation as a parameter"));
            return null;
        }
        if (aConstructorWithInjectAnnotation != null) {
            return aConstructorWithInjectAnnotation;
        }
        if (zeroArgConstructor == null) {
            collector.addThrowable(new NoSuchMethodException("The class " + Pretty.clazz(annotatedType) + " has no constructor marked @Inject and no zero argument constructor"));
            return null;
        }
        return zeroArgConstructor;
    }

    private static boolean isProperConstructor(Constructor<?> c) {
        for (Class<?> pClazz : c.getParameterTypes()) {
            if (!pClazz.isAnnotation()) continue;
            return false;
        }
        return true;
    }

    private static Set<Constructor<?>> getAllConstructors(final Class<?> clazz) {
        Constructor<?>[] constructors;
        LinkedHashSet retVal = new LinkedHashSet();
        for (Constructor<?> constructor : constructors = AccessController.doPrivileged(new PrivilegedAction<Constructor<?>[]>(){

            @Override
            public Constructor<?>[] run() {
                return clazz.getDeclaredConstructors();
            }
        })) {
            retVal.add(constructor);
        }
        return retVal;
    }

    private static boolean hasSubscribeToAnnotation(Method method2) {
        Annotation[][] paramAnnotations = method2.getParameterAnnotations();
        for (int outer = 0; outer < paramAnnotations.length; ++outer) {
            Annotation[] paramAnnos = paramAnnotations[outer];
            for (int inner = 0; inner < paramAnnos.length; ++inner) {
                if (!SubscribeTo.class.equals(paramAnnos[inner].annotationType())) continue;
                return true;
            }
        }
        return false;
    }

    public static Set<Method> findInitializerMethods(Class<?> annotatedType, ServiceLocatorImpl locator, Collector errorCollector) {
        LinkedHashSet<Method> retVal = new LinkedHashSet<Method>();
        ClassReflectionHelper crh = locator.getClassReflectionHelper();
        for (MethodWrapper methodWrapper : crh.getAllMethods(annotatedType)) {
            Method method2 = methodWrapper.getMethod();
            if (!locator.hasInjectAnnotation(method2) || method2.isSynthetic() || method2.isBridge() || Utilities.hasSubscribeToAnnotation(method2)) continue;
            if (!Utilities.isProperMethod(method2)) {
                errorCollector.addThrowable(new IllegalArgumentException("An initializer method " + Pretty.method(method2) + " is static, abstract or has a parameter that is an annotation"));
                continue;
            }
            retVal.add(method2);
        }
        return retVal;
    }

    public static Method findPostConstruct(Class<?> clazz, ServiceLocatorImpl locator, Collector collector) {
        try {
            return locator.getClassReflectionHelper().findPostConstruct(clazz, PostConstruct.class);
        }
        catch (IllegalArgumentException iae) {
            collector.addThrowable(iae);
            return null;
        }
    }

    public static Method findPreDestroy(Class<?> clazz, ServiceLocatorImpl locator, Collector collector) {
        try {
            return locator.getClassReflectionHelper().findPreDestroy(clazz, PreDestroy.class);
        }
        catch (IllegalArgumentException iae) {
            collector.addThrowable(iae);
            return null;
        }
    }

    public static Set<Field> findInitializerFields(Class<?> annotatedType, ServiceLocatorImpl locator, Collector errorCollector) {
        LinkedHashSet<Field> retVal = new LinkedHashSet<Field>();
        ClassReflectionHelper crh = locator.getClassReflectionHelper();
        Set<Field> fields = crh.getAllFields(annotatedType);
        for (Field field2 : fields) {
            if (!locator.hasInjectAnnotation(field2)) continue;
            if (!Utilities.isProperField(field2)) {
                errorCollector.addThrowable(new IllegalArgumentException("The field " + Pretty.field(field2) + " may not be static, final or have an Annotation type"));
                continue;
            }
            retVal.add(field2);
        }
        return retVal;
    }

    static AnnotatedElementAnnotationInfo computeAEAI(AnnotatedElement annotatedElement) {
        if (annotatedElement instanceof Method) {
            Method m4 = (Method)annotatedElement;
            return new AnnotatedElementAnnotationInfo(m4.getAnnotations(), true, m4.getParameterAnnotations(), false);
        }
        if (annotatedElement instanceof Constructor) {
            Constructor c = (Constructor)annotatedElement;
            return new AnnotatedElementAnnotationInfo(c.getAnnotations(), true, c.getParameterAnnotations(), true);
        }
        return new AnnotatedElementAnnotationInfo(annotatedElement.getAnnotations(), false, new Annotation[0][], false);
    }

    private static boolean isProperMethod(Method member) {
        if (ReflectionHelper.isStatic(member)) {
            return false;
        }
        if (Utilities.isAbstract(member)) {
            return false;
        }
        for (Class<?> paramClazz : member.getParameterTypes()) {
            if (!paramClazz.isAnnotation()) continue;
            return false;
        }
        return true;
    }

    private static boolean isProperField(Field field2) {
        if (ReflectionHelper.isStatic(field2)) {
            return false;
        }
        if (Utilities.isFinal(field2)) {
            return false;
        }
        Class<?> type = field2.getType();
        return !type.isAnnotation();
    }

    public static boolean isAbstract(Member member) {
        int modifiers = member.getModifiers();
        return (modifiers & 0x400) != 0;
    }

    public static boolean isFinal(Member member) {
        int modifiers = member.getModifiers();
        return (modifiers & 0x10) != 0;
    }

    private static boolean isFinal(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        return (modifiers & 0x10) != 0;
    }

    private static ScopeInfo getScopeInfo(AnnotatedElement annotatedGuy, Descriptor defaultScope, Collector collector) {
        Class<?> descScope;
        Class topLevelElement = annotatedGuy;
        Annotation winnerScope = null;
        block0: while (annotatedGuy != null) {
            Annotation current = Utilities.internalGetScopeAnnotationType(annotatedGuy, collector);
            if (current != null) {
                if (annotatedGuy.equals(topLevelElement)) {
                    winnerScope = current;
                    break;
                }
                if (!current.annotationType().isAnnotationPresent(Inherited.class)) break;
                winnerScope = current;
                break;
            }
            if (annotatedGuy instanceof Class) {
                annotatedGuy = ((Class)annotatedGuy).getSuperclass();
                continue;
            }
            Method theMethod = (Method)annotatedGuy;
            Class<?> methodClass = theMethod.getDeclaringClass();
            annotatedGuy = null;
            for (Class<?> methodSuperclass = methodClass.getSuperclass(); methodSuperclass != null; methodSuperclass = methodSuperclass.getSuperclass()) {
                if (!Factory.class.isAssignableFrom(methodSuperclass)) continue;
                annotatedGuy = Utilities.getFactoryProvideMethod(methodSuperclass);
                continue block0;
            }
        }
        if (winnerScope != null) {
            return new ScopeInfo(winnerScope, winnerScope.annotationType());
        }
        if (topLevelElement.isAnnotationPresent(Service.class)) {
            return new ScopeInfo(ServiceLocatorUtilities.getSingletonAnnotation(), Singleton.class);
        }
        if (defaultScope != null && defaultScope.getScope() != null && (descScope = Utilities.loadClass(defaultScope.getScope(), defaultScope, collector)) != null) {
            return new ScopeInfo(null, descScope);
        }
        return new ScopeInfo(ServiceLocatorUtilities.getPerLookupAnnotation(), PerLookup.class);
    }

    public static Class<? extends Annotation> getScopeAnnotationType(Class<?> fromThis, Descriptor defaultScope) {
        Collector collector = new Collector();
        ScopeInfo si = Utilities.getScopeInfo(fromThis, defaultScope, collector);
        collector.throwIfErrors();
        return si.getAnnoType();
    }

    public static ScopeInfo getScopeAnnotationType(AnnotatedElement annotatedGuy, Descriptor defaultScope, Collector collector) {
        ScopeInfo si = Utilities.getScopeInfo(annotatedGuy, defaultScope, collector);
        return si;
    }

    private static Annotation internalGetScopeAnnotationType(AnnotatedElement annotatedGuy, Collector collector) {
        boolean epicFail = false;
        Annotation retVal = null;
        for (Annotation annotation : annotatedGuy.getDeclaredAnnotations()) {
            if (!annotation.annotationType().isAnnotationPresent(Scope.class)) continue;
            if (retVal != null) {
                collector.addThrowable(new IllegalArgumentException("The type " + annotatedGuy + " may not have more than one scope.  It has at least " + Pretty.clazz(retVal.annotationType()) + " and " + Pretty.clazz(annotation.annotationType())));
                epicFail = true;
                continue;
            }
            retVal = annotation;
        }
        if (epicFail) {
            return null;
        }
        return retVal;
    }

    public static Method getFactoryProvideMethod(Class<?> clazz) {
        try {
            return clazz.getMethod(PROVIDE_METHOD, new Class[0]);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static String getDefaultNameFromMethod(Method parent, Collector collector) {
        Named named = parent.getAnnotation(Named.class);
        if (named == null) {
            return null;
        }
        if (named.value() == null || named.value().equals("")) {
            collector.addThrowable(new IllegalArgumentException("@Named on the provide method of a factory must have an explicit value"));
        }
        return named.value();
    }

    public static Set<Annotation> getAllQualifiers(AnnotatedElement annotatedGuy, String name, Collector collector) {
        Named namedQualifier = null;
        Set<Annotation> retVal = ReflectionHelper.getQualifierAnnotations(annotatedGuy);
        for (Annotation anno : retVal) {
            if (!(anno instanceof Named)) continue;
            namedQualifier = (Named)anno;
            break;
        }
        if (name == null) {
            if (namedQualifier != null) {
                collector.addThrowable(new IllegalArgumentException("No name was in the descriptor, but this element(" + annotatedGuy + " has a Named annotation with value: " + namedQualifier.value()));
                retVal.remove(namedQualifier);
            }
            return retVal;
        }
        if (namedQualifier == null || namedQualifier.value().equals("")) {
            if (namedQualifier != null) {
                retVal.remove(namedQualifier);
            }
            namedQualifier = new NamedImpl(name);
            retVal.add(namedQualifier);
        }
        if (!name.equals(namedQualifier.value())) {
            collector.addThrowable(new IllegalArgumentException("The class had an @Named qualifier that was inconsistent.  The expected name is " + name + " but the annotation has name " + namedQualifier.value()));
        }
        return retVal;
    }

    private static AnnotationInformation getParamInformation(Annotation[] memberAnnotations) {
        boolean useDefault = true;
        HashSet<Annotation> qualifiers = null;
        boolean optional = false;
        boolean self = false;
        Unqualified unqualified = null;
        for (Annotation anno : memberAnnotations) {
            if (ReflectionHelper.isAnnotationAQualifier(anno)) {
                if (qualifiers == null) {
                    qualifiers = new HashSet<Annotation>();
                }
                qualifiers.add(anno);
                useDefault = false;
                continue;
            }
            if (Optional.class.equals(anno.annotationType())) {
                optional = true;
                useDefault = false;
                continue;
            }
            if (Self.class.equals(anno.annotationType())) {
                self = true;
                useDefault = false;
                continue;
            }
            if (!Unqualified.class.equals(anno.annotationType())) continue;
            unqualified = (Unqualified)anno;
            useDefault = false;
        }
        if (useDefault) {
            return DEFAULT_ANNOTATION_INFORMATION;
        }
        if (qualifiers == null) {
            qualifiers = DEFAULT_ANNOTATION_INFORMATION.qualifiers;
        }
        return new AnnotationInformation(qualifiers, optional, self, unqualified);
    }

    public static List<SystemInjecteeImpl> getConstructorInjectees(Constructor<?> c, ActiveDescriptor<?> injecteeDescriptor) {
        Type[] genericTypeParams = c.getGenericParameterTypes();
        Annotation[][] paramAnnotations = c.getParameterAnnotations();
        LinkedList<SystemInjecteeImpl> retVal = new LinkedList<SystemInjecteeImpl>();
        for (int lcv = 0; lcv < genericTypeParams.length; ++lcv) {
            AnnotationInformation ai = Utilities.getParamInformation(paramAnnotations[lcv]);
            retVal.add(new SystemInjecteeImpl(genericTypeParams[lcv], ai.qualifiers, lcv, c, ai.optional, ai.self, ai.unqualified, injecteeDescriptor));
        }
        return retVal;
    }

    public static List<SystemInjecteeImpl> getMethodInjectees(Class<?> actualClass, Method c, ActiveDescriptor<?> injecteeDescriptor) {
        return Utilities.getMethodInjectees(actualClass, c, injecteeDescriptor, Collections.emptyMap());
    }

    public static List<SystemInjecteeImpl> getMethodInjectees(Class<?> actualClass, Method c, ActiveDescriptor<?> injecteeDescriptor, Map<Integer, MethodParameter> knownValues) {
        Type[] genericTypeParams = c.getGenericParameterTypes();
        Annotation[][] paramAnnotations = c.getParameterAnnotations();
        ArrayList<SystemInjecteeImpl> retVal = new ArrayList<SystemInjecteeImpl>();
        Class<?> declaringClass = c.getDeclaringClass();
        for (int lcv = 0; lcv < genericTypeParams.length; ++lcv) {
            if (knownValues.containsKey(lcv)) {
                retVal.add(null);
                continue;
            }
            AnnotationInformation ai = Utilities.getParamInformation(paramAnnotations[lcv]);
            Type adjustedType = ReflectionHelper.resolveMember(actualClass, genericTypeParams[lcv], declaringClass);
            retVal.add(new SystemInjecteeImpl(adjustedType, ai.qualifiers, lcv, c, ai.optional, ai.self, ai.unqualified, injecteeDescriptor));
        }
        return retVal;
    }

    private static Set<Annotation> getFieldAdjustedQualifierAnnotations(Field f, Set<Annotation> qualifiers) {
        Named n = f.getAnnotation(Named.class);
        if (n == null) {
            return qualifiers;
        }
        if (n.value() != null && !"".equals(n.value())) {
            return qualifiers;
        }
        HashSet<Annotation> retVal = new HashSet<Annotation>();
        for (Annotation qualifier : qualifiers) {
            if (qualifier.annotationType().equals(Named.class)) {
                retVal.add(new NamedImpl(f.getName()));
                continue;
            }
            retVal.add(qualifier);
        }
        return retVal;
    }

    public static List<SystemInjecteeImpl> getFieldInjectees(Class<?> actualClass, Field f, ActiveDescriptor<?> injecteeDescriptor) {
        LinkedList<SystemInjecteeImpl> retVal = new LinkedList<SystemInjecteeImpl>();
        AnnotationInformation ai = Utilities.getParamInformation(f.getAnnotations());
        Type adjustedType = ReflectionHelper.resolveField(actualClass, f);
        retVal.add(new SystemInjecteeImpl(adjustedType, Utilities.getFieldAdjustedQualifierAnnotations(f, ai.qualifiers), -1, f, ai.optional, ai.self, ai.unqualified, injecteeDescriptor));
        return retVal;
    }

    public static void validateSelfInjectees(ActiveDescriptor<?> givenDescriptor, List<SystemInjecteeImpl> injectees, Collector collector) {
        for (Injectee injectee : injectees) {
            if (!injectee.isSelf()) continue;
            Class<?> requiredRawClass = ReflectionHelper.getRawClass(injectee.getRequiredType());
            if (requiredRawClass == null || !ActiveDescriptor.class.equals(requiredRawClass)) {
                collector.addThrowable(new IllegalArgumentException("Injection point " + injectee + " does not have the required type of ActiveDescriptor"));
            }
            if (injectee.isOptional()) {
                collector.addThrowable(new IllegalArgumentException("Injection point " + injectee + " is marked both @Optional and @Self"));
            }
            if (!injectee.getRequiredQualifiers().isEmpty()) {
                collector.addThrowable(new IllegalArgumentException("Injection point " + injectee + " is marked @Self but has other qualifiers"));
            }
            if (givenDescriptor != null) continue;
            collector.addThrowable(new IllegalArgumentException("A class with injection point " + injectee + " is being created or injected via the non-managed ServiceLocator API"));
        }
    }

    public static Set<Annotation> fixAndCheckQualifiers(Annotation[] qualifiers, String name) {
        HashSet<Annotation> retVal = new HashSet<Annotation>();
        HashSet<String> dupChecker = new HashSet<String>();
        Named named = null;
        for (Annotation qualifier : qualifiers) {
            String annotationType = qualifier.annotationType().getName();
            if (dupChecker.contains(annotationType)) {
                throw new IllegalArgumentException(annotationType + " appears more than once in the qualifier list");
            }
            dupChecker.add(annotationType);
            retVal.add(qualifier);
            if (!(qualifier instanceof Named)) continue;
            named = (Named)qualifier;
            if (named.value().equals("")) {
                throw new IllegalArgumentException("The @Named qualifier must have a value");
            }
            if (name == null || name.equals(named.value())) continue;
            throw new IllegalArgumentException("The name passed to the method (" + name + ") does not match the value of the @Named qualifier (" + named.value() + ")");
        }
        if (named == null && name != null) {
            retVal.add(new NamedImpl(name));
        }
        return retVal;
    }

    public static <T> T createService(ActiveDescriptor<T> root, Injectee injectee, ServiceLocatorImpl locator, ServiceHandle<T> handle2, Class<?> requestedClass) {
        Context<T> context;
        if (root == null) {
            throw new IllegalArgumentException();
        }
        Object service2 = null;
        if (!root.isReified()) {
            root = locator.reifyDescriptor(root, injectee);
        }
        if (Utilities.isProxiable(root, injectee)) {
            if (!Utilities.proxiesAvailable()) {
                throw new IllegalStateException("A descriptor " + root + " requires a proxy, but the proxyable library is not on the classpath");
            }
            return (T)locator.getPerLocatorUtilities().getProxyUtilities().generateProxy(requestedClass, locator, root, (ServiceHandleImpl)handle2, injectee);
        }
        try {
            context = locator.resolveContext(root.getScopeAnnotation());
        }
        catch (Throwable th) {
            if (injectee != null && injectee.isOptional()) {
                return null;
            }
            IllegalStateException addMe = new IllegalStateException("While attempting to create a service for " + root + " in scope " + root.getScope() + " an error occured while locating the context");
            if (th instanceof MultiException) {
                MultiException me = (MultiException)th;
                me.addError(addMe);
                throw me;
            }
            MultiException me = new MultiException(th);
            me.addError(addMe);
            throw me;
        }
        try {
            service2 = context.findOrCreate(root, handle2);
        }
        catch (MultiException me) {
            throw me;
        }
        catch (Throwable th) {
            throw new MultiException(th);
        }
        if (service2 == null && !context.supportsNullCreation()) {
            throw new MultiException(new IllegalStateException("Context " + context + " findOrCreate returned a null for descriptor " + root + " and handle " + handle2));
        }
        return (T)service2;
    }

    static Interceptors getAllInterceptors(ServiceLocatorImpl impl, ActiveDescriptor<?> descriptor, Class<?> clazz, Constructor<?> c) {
        if (descriptor == null || clazz == null || Utilities.isFinal(clazz)) {
            return EMTPY_INTERCEPTORS;
        }
        ClassReflectionHelper crh = impl.getClassReflectionHelper();
        List<InterceptionService> interceptionServices = impl.getInterceptionServices();
        if (interceptionServices == null || interceptionServices.isEmpty()) {
            return EMTPY_INTERCEPTORS;
        }
        for (String contract : descriptor.getAdvertisedContracts()) {
            if (!NOT_INTERCEPTED.contains(contract)) continue;
            return EMTPY_INTERCEPTORS;
        }
        final LinkedHashMap<Method, ArrayList<MethodInterceptor>> retVal = new LinkedHashMap<Method, ArrayList<MethodInterceptor>>();
        final ArrayList<ConstructorInterceptor> cRetVal = new ArrayList<ConstructorInterceptor>();
        for (InterceptionService interceptionService : interceptionServices) {
            Filter filter2 = interceptionService.getDescriptorFilter();
            if (!BuilderHelper.filterMatches(descriptor, filter2)) continue;
            for (MethodWrapper methodWrapper : crh.getAllMethods(clazz)) {
                List<MethodInterceptor> interceptors;
                Method method2 = methodWrapper.getMethod();
                if (Utilities.isFinal(method2) || (interceptors = interceptionService.getMethodInterceptors(method2)) == null || interceptors.isEmpty()) continue;
                ArrayList<MethodInterceptor> addToMe = (ArrayList<MethodInterceptor>)retVal.get(method2);
                if (addToMe == null) {
                    addToMe = new ArrayList<MethodInterceptor>();
                    retVal.put(method2, addToMe);
                }
                addToMe.addAll(interceptors);
            }
            List<ConstructorInterceptor> cInterceptors = interceptionService.getConstructorInterceptors(c);
            if (cInterceptors == null || cInterceptors.isEmpty()) continue;
            cRetVal.addAll(cInterceptors);
        }
        return new Interceptors(){

            @Override
            public Map<Method, List<MethodInterceptor>> getMethodInterceptors() {
                return retVal;
            }

            @Override
            public List<ConstructorInterceptor> getConstructorInterceptors() {
                return cRetVal;
            }
        };
    }

    public static boolean isTypeSafe(Type requiredType, Type beanType) {
        if (TypeChecker.isRawTypeSafe(requiredType, beanType)) {
            return true;
        }
        Class<?> requiredClass = ReflectionHelper.getRawClass(requiredType);
        if (requiredClass == null) {
            return false;
        }
        if (!requiredClass.isAnnotation()) {
            return false;
        }
        Class<?> beanClass = ReflectionHelper.getRawClass(beanType);
        if (beanClass == null) {
            return false;
        }
        if (beanClass.isAnnotationPresent(requiredClass)) {
            return true;
        }
        Class<? extends Annotation> trueScope = Utilities.getScopeAnnotationType(beanClass, null);
        return trueScope.equals(requiredClass);
    }

    public static synchronized boolean proxiesAvailable() {
        if (proxiesAvailable != null) {
            return proxiesAvailable;
        }
        ClassLoader loader = Utilities.class.getClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }
        try {
            loader.loadClass("javassist.util.proxy.MethodHandler");
            proxiesAvailable = true;
            return true;
        }
        catch (Throwable th) {
            proxiesAvailable = false;
            return false;
        }
    }

    static {
        NOT_INTERCEPTED.add(ServiceLocator.class.getName());
        NOT_INTERCEPTED.add(InstanceLifecycleListener.class.getName());
        NOT_INTERCEPTED.add(InjectionResolver.class.getName());
        NOT_INTERCEPTED.add(ErrorService.class.getName());
        NOT_INTERCEPTED.add(ClassAnalyzer.class.getName());
        NOT_INTERCEPTED.add(DynamicConfigurationListener.class.getName());
        NOT_INTERCEPTED.add(DynamicConfigurationService.class.getName());
        NOT_INTERCEPTED.add(InterceptionService.class.getName());
        NOT_INTERCEPTED.add(ValidationService.class.getName());
        NOT_INTERCEPTED.add(Context.class.getName());
        EMTPY_INTERCEPTORS = new Interceptors(){

            @Override
            public Map<Method, List<MethodInterceptor>> getMethodInterceptors() {
                return null;
            }

            @Override
            public List<ConstructorInterceptor> getConstructorInterceptors() {
                return null;
            }
        };
        proxiesAvailable = null;
    }

    private static class AnnotationInformation {
        private final Set<Annotation> qualifiers;
        private final boolean optional;
        private final boolean self;
        private final Unqualified unqualified;

        private AnnotationInformation(Set<Annotation> qualifiers, boolean optional, boolean self, Unqualified unqualified) {
            this.qualifiers = qualifiers;
            this.optional = optional;
            this.self = self;
            this.unqualified = unqualified;
        }
    }

    public static interface Interceptors {
        public Map<Method, List<MethodInterceptor>> getMethodInterceptors();

        public List<ConstructorInterceptor> getConstructorInterceptors();
    }
}

