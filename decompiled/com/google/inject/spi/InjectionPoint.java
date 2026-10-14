/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.DeclaredMembers;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.KotlinSupport;
import com.google.inject.internal.MoreTypes;
import com.google.inject.internal.Nullability;
import com.google.inject.internal.util.Classes;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Toolable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class InjectionPoint {
    private static final Logger logger = Logger.getLogger(InjectionPoint.class.getName());
    private final boolean optional;
    private final Member member;
    private final TypeLiteral<?> declaringType;
    private final ImmutableList<Dependency<?>> dependencies;

    InjectionPoint(TypeLiteral<?> declaringType, Method method2, boolean optional) {
        this.member = method2;
        this.declaringType = declaringType;
        this.optional = optional;
        this.dependencies = this.forMember(new Errors(method2), method2, declaringType, method2.getAnnotatedParameterTypes(), method2.getParameterAnnotations(), KotlinSupport.getInstance().getIsParameterKotlinNullablePredicate(method2));
    }

    InjectionPoint(TypeLiteral<?> declaringType, Constructor<?> constructor) {
        this.member = constructor;
        this.declaringType = declaringType;
        this.optional = false;
        Errors errors2 = new Errors(constructor);
        KotlinSupport.getInstance().checkConstructorParameterAnnotations(constructor, errors2);
        this.dependencies = this.forMember(errors2, constructor, declaringType, constructor.getAnnotatedParameterTypes(), constructor.getParameterAnnotations(), KotlinSupport.getInstance().getIsParameterKotlinNullablePredicate(constructor));
    }

    InjectionPoint(TypeLiteral<?> declaringType, Field field2, boolean optional) {
        this.member = field2;
        this.declaringType = declaringType;
        this.optional = optional;
        Annotation[] annotations = InjectionPoint.getAnnotations(field2);
        Annotation[] typeUseAnnotations = field2.getAnnotatedType().getAnnotations();
        Errors errors2 = new Errors(field2);
        Key<?> key = null;
        try {
            key = Annotations.getKey(declaringType.getFieldType(field2), field2, annotations, errors2);
        }
        catch (ConfigurationException e) {
            errors2.merge(e.getErrorMessages());
        }
        catch (ErrorsException e) {
            errors2.merge(e.getErrors());
        }
        errors2.throwConfigurationExceptionIfErrorsExist();
        boolean allowsNull = Nullability.hasNullableAnnotation(annotations) || Nullability.hasNullableAnnotation(typeUseAnnotations) || KotlinSupport.getInstance().isNullable(field2);
        this.dependencies = ImmutableList.of(this.newDependency(key, allowsNull, -1));
    }

    private ImmutableList<Dependency<?>> forMember(Errors errors2, Member member, TypeLiteral<?> type, AnnotatedType[] annotatedTypes, Annotation[][] parameterAnnotationsPerParameter, Predicate<Integer> isParameterKotlinNullable) {
        ArrayList<Dependency<?>> dependencies = Lists.newArrayList();
        int index = 0;
        for (TypeLiteral<?> parameterType : type.getParameterTypes(member)) {
            try {
                Annotation[] typeAnnotations = annotatedTypes[index].getAnnotations();
                Annotation[] parameterAnnotations = parameterAnnotationsPerParameter[index];
                Key<?> key = Annotations.getKey(parameterType, member, parameterAnnotations, errors2);
                boolean isNullable = Nullability.hasNullableAnnotation(parameterAnnotations) || Nullability.hasNullableAnnotation(typeAnnotations) || isParameterKotlinNullable.test(index);
                dependencies.add(this.newDependency(key, isNullable, index));
                ++index;
            }
            catch (ConfigurationException e) {
                errors2.merge(e.getErrorMessages());
            }
            catch (ErrorsException e) {
                errors2.merge(e.getErrors());
            }
        }
        errors2.throwConfigurationExceptionIfErrorsExist();
        return ImmutableList.copyOf(dependencies);
    }

    private <T> Dependency<T> newDependency(Key<T> key, boolean allowsNull, int parameterIndex) {
        return new Dependency<T>(this, key, allowsNull, parameterIndex);
    }

    public Member getMember() {
        return this.member;
    }

    public List<Dependency<?>> getDependencies() {
        return this.dependencies;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public boolean isToolable() {
        return ((AnnotatedElement)((Object)this.member)).isAnnotationPresent(Toolable.class);
    }

    public TypeLiteral<?> getDeclaringType() {
        return this.declaringType;
    }

    public boolean equals(Object o) {
        return o instanceof InjectionPoint && this.member.equals(((InjectionPoint)o).member) && this.declaringType.equals(((InjectionPoint)o).declaringType);
    }

    public int hashCode() {
        return this.member.hashCode() ^ this.declaringType.hashCode();
    }

    public String toString() {
        return Classes.toString(this.member);
    }

    public static <T> InjectionPoint forConstructor(Constructor<T> constructor) {
        return new InjectionPoint(TypeLiteral.get(constructor.getDeclaringClass()), constructor);
    }

    public static <T> InjectionPoint forConstructor(Constructor<T> constructor, TypeLiteral<? extends T> type) {
        if (type.getRawType() != constructor.getDeclaringClass()) {
            new Errors(type).constructorNotDefinedByType(constructor, type).throwConfigurationExceptionIfErrorsExist();
        }
        return new InjectionPoint(type, constructor);
    }

    public static InjectionPoint forConstructorOf(TypeLiteral<?> type) {
        return InjectionPoint.forConstructorOf(type, false);
    }

    public static InjectionPoint forConstructorOf(TypeLiteral<?> type, boolean atInjectRequired) {
        Class<?> rawType = MoreTypes.getRawType(type.getType());
        Errors errors2 = new Errors(rawType);
        List atInjectConstructors = Arrays.stream(rawType.getDeclaredConstructors()).filter(InjectionPoint::isInjectableConstructor).collect(Collectors.toList());
        Constructor injectableConstructor = null;
        atInjectConstructors.stream().filter(constructor -> constructor.isAnnotationPresent(Inject.class)).filter(constructor -> constructor.getAnnotation(Inject.class).optional()).forEach(errors2::optionalConstructor);
        if (atInjectConstructors.size() > 1) {
            errors2.tooManyConstructors(rawType);
        } else {
            injectableConstructor = Iterables.getOnlyElement(atInjectConstructors, null);
            if (injectableConstructor != null) {
                InjectionPoint.checkForMisplacedBindingAnnotations(injectableConstructor, errors2);
            }
        }
        if (atInjectRequired && injectableConstructor == null) {
            errors2.atInjectRequired(type);
        }
        errors2.throwConfigurationExceptionIfErrorsExist();
        if (injectableConstructor != null) {
            return new InjectionPoint(type, injectableConstructor);
        }
        try {
            Constructor<?> noArgConstructor = rawType.getDeclaredConstructor(new Class[0]);
            if (Modifier.isPrivate(noArgConstructor.getModifiers()) && !Modifier.isPrivate(rawType.getModifiers())) {
                errors2.missingConstructor(type);
                throw new ConfigurationException(errors2.getMessages());
            }
            InjectionPoint.checkForMisplacedBindingAnnotations(noArgConstructor, errors2);
            return new InjectionPoint(type, noArgConstructor);
        }
        catch (NoSuchMethodException e) {
            errors2.missingConstructor(type);
            throw new ConfigurationException(errors2.getMessages());
        }
    }

    private static boolean isInjectableConstructor(Constructor<?> constructor) {
        return constructor.isAnnotationPresent(Inject.class) || constructor.isAnnotationPresent(jakarta.inject.Inject.class);
    }

    public static InjectionPoint forConstructorOf(Class<?> type) {
        return InjectionPoint.forConstructorOf(TypeLiteral.get(type));
    }

    public static <T> InjectionPoint forMethod(Method method2, TypeLiteral<T> type) {
        return new InjectionPoint(type, method2, false);
    }

    public static Set<InjectionPoint> forStaticMethodsAndFields(TypeLiteral<?> type) {
        Set<InjectionPoint> result;
        Errors errors2 = new Errors();
        if (type.getRawType().isInterface()) {
            errors2.staticInjectionOnInterface(type.getRawType());
            result = null;
        } else {
            result = InjectionPoint.getInjectionPoints(type, true, errors2);
        }
        if (errors2.hasErrors()) {
            throw new ConfigurationException(errors2.getMessages()).withPartialValue(result);
        }
        return result;
    }

    public static Set<InjectionPoint> forStaticMethodsAndFields(Class<?> type) {
        return InjectionPoint.forStaticMethodsAndFields(TypeLiteral.get(type));
    }

    public static Set<InjectionPoint> forInstanceMethodsAndFields(TypeLiteral<?> type) {
        Errors errors2 = new Errors();
        Set<InjectionPoint> result = InjectionPoint.getInjectionPoints(type, false, errors2);
        if (errors2.hasErrors()) {
            throw new ConfigurationException(errors2.getMessages()).withPartialValue(result);
        }
        return result;
    }

    public static Set<InjectionPoint> forInstanceMethodsAndFields(Class<?> type) {
        return InjectionPoint.forInstanceMethodsAndFields(TypeLiteral.get(type));
    }

    private static boolean checkForMisplacedBindingAnnotations(Member member, Errors errors2) {
        Annotation misplacedBindingAnnotation = Annotations.findBindingAnnotation(errors2, member, ((AnnotatedElement)((Object)member)).getAnnotations());
        if (misplacedBindingAnnotation == null) {
            return false;
        }
        if (member instanceof Method) {
            try {
                if (member.getDeclaringClass().getDeclaredField(member.getName()) != null) {
                    return false;
                }
            }
            catch (NoSuchFieldException noSuchFieldException) {
                // empty catch block
            }
        }
        errors2.misplacedBindingAnnotation(member, misplacedBindingAnnotation);
        return true;
    }

    static Annotation getAtInject(AnnotatedElement member) {
        jakarta.inject.Inject a = member.getAnnotation(jakarta.inject.Inject.class);
        return a == null ? member.getAnnotation(Inject.class) : a;
    }

    private static Set<InjectionPoint> getInjectionPoints(TypeLiteral<?> type, boolean statics, Errors errors2) {
        int topIndex;
        InjectableMembers injectableMembers = new InjectableMembers();
        OverrideIndex overrideIndex = null;
        List<TypeLiteral<?>> hierarchy = InjectionPoint.hierarchyFor(type);
        for (int i = topIndex = hierarchy.size() - 1; i >= 0; --i) {
            Annotation atInject;
            if (overrideIndex != null && i < topIndex) {
                overrideIndex.position = i == 0 ? Position.BOTTOM : Position.MIDDLE;
            }
            TypeLiteral<?> current = hierarchy.get(i);
            for (Field field2 : InjectionPoint.getDeclaredFields(current)) {
                if (Modifier.isStatic(field2.getModifiers()) != statics || (atInject = InjectionPoint.getAtInject(field2)) == null) continue;
                InjectableField injectableField = new InjectableField(current, field2, atInject);
                if (injectableField.specInject && Modifier.isFinal(field2.getModifiers())) {
                    errors2.cannotInjectFinalField(field2);
                }
                injectableMembers.add(injectableField);
            }
            for (AccessibleObject accessibleObject : InjectionPoint.getDeclaredMethods(current)) {
                boolean removed;
                if (!InjectionPoint.isEligibleForInjection((Method)accessibleObject, statics)) continue;
                atInject = InjectionPoint.getAtInject(accessibleObject);
                if (atInject != null) {
                    InjectableMethod injectableMethod = new InjectableMethod(current, (Method)accessibleObject, atInject);
                    if (InjectionPoint.checkForMisplacedBindingAnnotations((Member)((Object)accessibleObject), errors2) || !InjectionPoint.isValidMethod(injectableMethod, errors2)) {
                        boolean removed2;
                        if (overrideIndex == null || !(removed2 = overrideIndex.removeIfOverriddenBy((Method)accessibleObject, false, injectableMethod))) continue;
                        logger.log(Level.WARNING, "Method: {0} is not a valid injectable method (because it either has misplaced binding annotations or specifies type parameters) but is overriding a method that is valid. Because it is not valid, the method will not be injected. To fix this, make the method a valid injectable method.", accessibleObject);
                        continue;
                    }
                    if (statics) {
                        injectableMembers.add(injectableMethod);
                        continue;
                    }
                    if (overrideIndex == null) {
                        overrideIndex = new OverrideIndex(injectableMembers);
                    } else {
                        overrideIndex.removeIfOverriddenBy((Method)accessibleObject, true, injectableMethod);
                    }
                    overrideIndex.add(injectableMethod);
                    continue;
                }
                if (overrideIndex == null || !(removed = overrideIndex.removeIfOverriddenBy((Method)accessibleObject, false, null))) continue;
                logger.log(Level.WARNING, "Method: {0} is not annotated with @Inject but is overriding a method that is annotated with @jakarta.inject.Inject.Because it is not annotated with @Inject, the method will not be injected. To fix this, annotate the method with @Inject.", accessibleObject);
            }
        }
        if (injectableMembers.isEmpty()) {
            return Collections.emptySet();
        }
        ImmutableSet.Builder builder = ImmutableSet.builder();
        InjectableMember im = injectableMembers.head;
        while (im != null) {
            block14: {
                try {
                    builder.add(im.toInjectionPoint());
                }
                catch (ConfigurationException ignorable) {
                    if (im.optional) break block14;
                    errors2.merge(ignorable.getErrorMessages());
                }
            }
            im = im.next;
        }
        return builder.build();
    }

    private static Field[] getDeclaredFields(TypeLiteral<?> type) {
        return DeclaredMembers.getDeclaredFields(type.getRawType());
    }

    private static Method[] getDeclaredMethods(TypeLiteral<?> type) {
        return DeclaredMembers.getDeclaredMethods(type.getRawType());
    }

    private static boolean isEligibleForInjection(Method method2, boolean statics) {
        return Modifier.isStatic(method2.getModifiers()) == statics && !method2.isBridge() && !method2.isSynthetic();
    }

    private static boolean isValidMethod(InjectableMethod injectableMethod, Errors errors2) {
        boolean result = true;
        if (injectableMethod.specInject) {
            Method method2 = injectableMethod.method;
            if (Modifier.isAbstract(method2.getModifiers())) {
                errors2.cannotInjectAbstractMethod(method2);
                result = false;
            }
            if (method2.getTypeParameters().length > 0) {
                errors2.cannotInjectMethodWithTypeParameters(method2);
                result = false;
            }
        }
        return result;
    }

    private static List<TypeLiteral<?>> hierarchyFor(TypeLiteral<?> type) {
        ArrayList hierarchy = new ArrayList();
        TypeLiteral<?> current = type;
        while (current.getRawType() != Object.class) {
            hierarchy.add(current);
            current = current.getSupertype(current.getRawType().getSuperclass());
        }
        return hierarchy;
    }

    private static boolean overrides(Method a, Method b) {
        int modifiers = b.getModifiers();
        if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
            return true;
        }
        if (Modifier.isPrivate(modifiers)) {
            return false;
        }
        return a.getDeclaringClass().getPackage().equals(b.getDeclaringClass().getPackage());
    }

    public static Annotation[] getAnnotations(Field field2) {
        Annotation[] javaAnnotations = field2.getAnnotations();
        Annotation[] kotlinAnnotations = KotlinSupport.getInstance().getAnnotations(field2);
        if (kotlinAnnotations.length == 0) {
            return javaAnnotations;
        }
        return ObjectArrays.concat(javaAnnotations, kotlinAnnotations, Annotation.class);
    }

    static class Signature {
        final String name;
        final Class<?>[] parameterTypes;
        final int hash;

        Signature(Method method2) {
            this.name = method2.getName();
            this.parameterTypes = method2.getParameterTypes();
            int h2 = this.name.hashCode();
            h2 = h2 * 31 + this.parameterTypes.length;
            for (Class<?> parameterType : this.parameterTypes) {
                h2 = h2 * 31 + parameterType.hashCode();
            }
            this.hash = h2;
        }

        public int hashCode() {
            return this.hash;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Signature)) {
                return false;
            }
            Signature other = (Signature)o;
            if (!this.name.equals(other.name)) {
                return false;
            }
            if (this.parameterTypes.length != other.parameterTypes.length) {
                return false;
            }
            for (int i = 0; i < this.parameterTypes.length; ++i) {
                if (this.parameterTypes[i] == other.parameterTypes[i]) continue;
                return false;
            }
            return true;
        }
    }

    static class OverrideIndex {
        final InjectableMembers injectableMembers;
        Map<Signature, List<InjectableMethod>> bySignature;
        Position position = Position.TOP;
        Method lastMethod;
        Signature lastSignature;

        OverrideIndex(InjectableMembers injectableMembers) {
            this.injectableMembers = injectableMembers;
        }

        boolean removeIfOverriddenBy(Method method2, boolean alwaysRemove, InjectableMethod injectableMethod) {
            if (this.position == Position.TOP) {
                return false;
            }
            if (this.bySignature == null) {
                this.bySignature = new HashMap<Signature, List<InjectableMethod>>();
                InjectableMember member = this.injectableMembers.head;
                while (member != null) {
                    InjectableMethod im;
                    if (member instanceof InjectableMethod && !(im = (InjectableMethod)member).isFinal()) {
                        ArrayList<InjectableMethod> methods = new ArrayList<InjectableMethod>();
                        methods.add(im);
                        this.bySignature.put(new Signature(im.method), methods);
                    }
                    member = member.next;
                }
            }
            this.lastMethod = method2;
            Signature signature = this.lastSignature = new Signature(method2);
            List<InjectableMethod> methods = this.bySignature.get(signature);
            boolean removed = false;
            if (methods != null) {
                Iterator<InjectableMethod> iterator2 = methods.iterator();
                while (iterator2.hasNext()) {
                    boolean wasGuiceInject;
                    InjectableMethod possiblyOverridden = iterator2.next();
                    if (!InjectionPoint.overrides(method2, possiblyOverridden.method)) continue;
                    boolean bl = wasGuiceInject = !possiblyOverridden.specInject || possiblyOverridden.overrodeGuiceInject;
                    if (injectableMethod != null) {
                        injectableMethod.overrodeGuiceInject = wasGuiceInject;
                    }
                    if (!alwaysRemove && wasGuiceInject) continue;
                    removed = true;
                    iterator2.remove();
                    this.injectableMembers.remove(possiblyOverridden);
                }
            }
            return removed;
        }

        void add(InjectableMethod injectableMethod) {
            this.injectableMembers.add(injectableMethod);
            if (this.position == Position.BOTTOM || injectableMethod.isFinal()) {
                return;
            }
            if (this.bySignature != null) {
                Signature signature = injectableMethod.method == this.lastMethod ? this.lastSignature : new Signature(injectableMethod.method);
                this.bySignature.computeIfAbsent(signature, k -> new ArrayList()).add(injectableMethod);
            }
        }
    }

    static enum Position {
        TOP,
        MIDDLE,
        BOTTOM;

    }

    static class InjectableMembers {
        InjectableMember head;
        InjectableMember tail;

        InjectableMembers() {
        }

        void add(InjectableMember member) {
            if (this.head == null) {
                this.head = this.tail = member;
            } else {
                member.previous = this.tail;
                this.tail.next = member;
                this.tail = member;
            }
        }

        void remove(InjectableMember member) {
            if (member.previous != null) {
                member.previous.next = member.next;
            }
            if (member.next != null) {
                member.next.previous = member.previous;
            }
            if (this.head == member) {
                this.head = member.next;
            }
            if (this.tail == member) {
                this.tail = member.previous;
            }
        }

        boolean isEmpty() {
            return this.head == null;
        }
    }

    static class InjectableMethod
    extends InjectableMember {
        final Method method;
        boolean overrodeGuiceInject;

        InjectableMethod(TypeLiteral<?> declaringType, Method method2, Annotation atInject) {
            super(declaringType, atInject);
            this.method = method2;
        }

        @Override
        InjectionPoint toInjectionPoint() {
            return new InjectionPoint(this.declaringType, this.method, this.optional);
        }

        public boolean isFinal() {
            return Modifier.isFinal(this.method.getModifiers());
        }
    }

    static class InjectableField
    extends InjectableMember {
        final Field field;

        InjectableField(TypeLiteral<?> declaringType, Field field2, Annotation atInject) {
            super(declaringType, atInject);
            this.field = field2;
        }

        @Override
        InjectionPoint toInjectionPoint() {
            return new InjectionPoint(this.declaringType, this.field, this.optional);
        }
    }

    static abstract class InjectableMember {
        final TypeLiteral<?> declaringType;
        final boolean optional;
        final boolean specInject;
        InjectableMember previous;
        InjectableMember next;

        InjectableMember(TypeLiteral<?> declaringType, Annotation atInject) {
            this.declaringType = declaringType;
            if (atInject.annotationType() == jakarta.inject.Inject.class) {
                this.optional = false;
                this.specInject = true;
                return;
            }
            this.specInject = false;
            this.optional = ((Inject)atInject).optional();
        }

        abstract InjectionPoint toInjectionPoint();
    }
}

