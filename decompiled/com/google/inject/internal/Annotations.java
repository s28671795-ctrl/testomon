/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;
import com.google.inject.ScopeAnnotation;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.util.Classes;
import com.google.inject.name.Names;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;
import jakarta.inject.Scope;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class Annotations {
    private static final LoadingCache<Class<? extends Annotation>, Annotation> cache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<? extends Annotation>, Annotation>(){

        @Override
        public Annotation load(Class<? extends Annotation> input) {
            return Annotations.generateAnnotationImpl(input);
        }
    });
    private static final Joiner.MapJoiner JOINER = Joiner.on(", ").withKeyValueSeparator("=");
    private static final AnnotationToStringConfig ANNOTATION_TO_STRING_CONFIG = Annotations.determineAnnotationToStringConfig();
    private static final AnnotationChecker scopeChecker = new AnnotationChecker(Arrays.asList(ScopeAnnotation.class, Scope.class));
    private static final AnnotationChecker bindingAnnotationChecker = new AnnotationChecker(Arrays.asList(BindingAnnotation.class, Qualifier.class));

    public static boolean isMarker(Class<? extends Annotation> annotationType) {
        return annotationType.getDeclaredMethods().length == 0;
    }

    public static boolean isAllDefaultMethods(Class<? extends Annotation> annotationType) {
        boolean hasMethods = false;
        for (Method m4 : annotationType.getDeclaredMethods()) {
            hasMethods = true;
            if (m4.getDefaultValue() != null) continue;
            return false;
        }
        return hasMethods;
    }

    public static <T extends Annotation> T generateAnnotation(Class<T> annotationType) {
        Preconditions.checkState(Annotations.isAllDefaultMethods(annotationType), "%s is not all default methods", annotationType);
        return (T)cache.getUnchecked(annotationType);
    }

    private static <T extends Annotation> T generateAnnotationImpl(final Class<T> annotationType) {
        final ImmutableMap<String, Object> members = Annotations.resolveMembers(annotationType);
        return (T)((Annotation)annotationType.cast(Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType}, new InvocationHandler(){

            @Override
            public Object invoke(Object proxy, Method method2, Object[] args2) throws Exception {
                String name = method2.getName();
                if (name.equals("annotationType")) {
                    return annotationType;
                }
                if (name.equals("toString")) {
                    return Annotations.annotationToString(annotationType, members);
                }
                if (name.equals("hashCode")) {
                    return Annotations.annotationHashCode(annotationType, members);
                }
                if (name.equals("equals")) {
                    return Annotations.annotationEquals(annotationType, members, args2[0]);
                }
                return members.get(name);
            }
        })));
    }

    private static ImmutableMap<String, Object> resolveMembers(Class<? extends Annotation> annotationType) {
        ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();
        for (Method method2 : annotationType.getDeclaredMethods()) {
            result.put(method2.getName(), method2.getDefaultValue());
        }
        return result.buildOrThrow();
    }

    private static boolean annotationEquals(Class<? extends Annotation> type, Map<String, Object> members, Object other) throws Exception {
        if (!type.isInstance(other)) {
            return false;
        }
        for (Method method2 : type.getDeclaredMethods()) {
            String name = method2.getName();
            if (Arrays.deepEquals(new Object[]{method2.invoke(other, new Object[0])}, new Object[]{members.get(name)})) continue;
            return false;
        }
        return true;
    }

    private static int annotationHashCode(Class<? extends Annotation> type, Map<String, Object> members) throws Exception {
        int result = 0;
        for (Method method2 : type.getDeclaredMethods()) {
            String name = method2.getName();
            Object value = members.get(name);
            result += 127 * name.hashCode() ^ Arrays.deepHashCode(new Object[]{value}) - 31;
        }
        return result;
    }

    private static String annotationToString(Class<? extends Annotation> type, Map<String, Object> members) throws Exception {
        StringBuilder sb = new StringBuilder().append('@').append(type.getName()).append('(');
        JOINER.appendTo(sb, Maps.transformValues(members, arg -> {
            String s2 = Arrays.deepToString(new Object[]{arg});
            return s2.substring(1, s2.length() - 1);
        }));
        return sb.append(')').toString();
    }

    public static boolean isRetainedAtRuntime(Class<? extends Annotation> annotationType) {
        Retention retention = annotationType.getAnnotation(Retention.class);
        return retention != null && retention.value() == RetentionPolicy.RUNTIME;
    }

    public static Class<? extends Annotation> findScopeAnnotation(Errors errors2, Class<?> implementation) {
        return Annotations.findScopeAnnotation(errors2, implementation.getAnnotations());
    }

    public static Class<? extends Annotation> findScopeAnnotation(Errors errors2, Annotation[] annotations) {
        Class<? extends Annotation> found = null;
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (!Annotations.isScopeAnnotation(annotationType)) continue;
            if (found != null) {
                errors2.duplicateScopeAnnotations(found, annotationType);
                continue;
            }
            found = annotationType;
        }
        return found;
    }

    static boolean containsComponentAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (!annotation.annotationType().getSimpleName().equals("Component")) continue;
            return true;
        }
        return false;
    }

    public static String memberValueString(String value) {
        return Annotations.ANNOTATION_TO_STRING_CONFIG.quote ? "\"" + value + "\"" : value;
    }

    public static String memberValueString(String memberName, Object value) {
        StringBuilder sb = new StringBuilder();
        boolean quote = Annotations.ANNOTATION_TO_STRING_CONFIG.quote;
        boolean includeMemberName = Annotations.ANNOTATION_TO_STRING_CONFIG.includeMemberName;
        if (includeMemberName) {
            sb.append(memberName).append('=');
        }
        if (quote && value instanceof String) {
            sb.append('\"').append(value).append('\"');
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    public static String annotationInstanceClassString(Class<? extends Annotation> ann, boolean includePackage) {
        if (Annotations.ANNOTATION_TO_STRING_CONFIG.dollarSeparator) {
            if (includePackage || ann.getPackage() == null) {
                return "@" + ann.getName();
            }
            return "@" + ann.getName().substring(ann.getPackage().getName().length() + 1);
        }
        return "@" + Annotations.dotSeparatedParentOrPackage(ann, includePackage);
    }

    private static String dotSeparatedParentOrPackage(Class<?> ann, boolean includePackage) {
        if (ann.getDeclaringClass() == null) {
            if (includePackage) {
                return ann.getPackage().getName() + "." + ann.getSimpleName();
            }
            return ann.getSimpleName();
        }
        return Annotations.dotSeparatedParentOrPackage(ann.getDeclaringClass(), includePackage) + "." + ann.getSimpleName();
    }

    @TestAnnotation(value="determineAnnotationToStringConfig")
    private static AnnotationToStringConfig determineAnnotationToStringConfig() {
        try {
            String annotation = Annotations.class.getDeclaredMethod("determineAnnotationToStringConfig", new Class[0]).getAnnotation(TestAnnotation.class).toString();
            boolean quote = annotation.contains("\"determineAnnotationToStringConfig\"");
            boolean includeMemberName = annotation.contains("value=");
            boolean dollarSeparator = annotation.contains("Annotations$TestAnnotation");
            return new AnnotationToStringConfig(quote, includeMemberName, dollarSeparator);
        }
        catch (NoSuchMethodException e) {
            throw new AssertionError((Object)e);
        }
    }

    public static boolean isScopeAnnotation(Class<? extends Annotation> annotationType) {
        return scopeChecker.hasAnnotations(annotationType);
    }

    public static void checkForMisplacedScopeAnnotations(Class<?> type, Object source2, Errors errors2) {
        if (Classes.isConcrete(type)) {
            return;
        }
        Class<? extends Annotation> scopeAnnotation = Annotations.findScopeAnnotation(errors2, type);
        if (scopeAnnotation != null && !Annotations.containsComponentAnnotation(type.getAnnotations())) {
            errors2.withSource(type).scopeAnnotationOnAbstractType(scopeAnnotation, type, source2);
        }
    }

    public static Key<?> getKey(TypeLiteral<?> type, Member member, Annotation[] annotations, Errors errors2) throws ErrorsException {
        int numErrorsBefore = errors2.size();
        Annotation found = Annotations.findBindingAnnotation(errors2, member, annotations);
        errors2.throwIfNewErrors(numErrorsBefore);
        return found == null ? Key.get(type) : Key.get(type, found);
    }

    public static Annotation findBindingAnnotation(Errors errors2, Member member, Annotation[] annotations) {
        Annotation found = null;
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (!Annotations.isBindingAnnotation(annotationType)) continue;
            if (found != null) {
                errors2.duplicateBindingAnnotations(member, found.annotationType(), annotationType);
                continue;
            }
            found = annotation;
        }
        return found;
    }

    public static boolean isBindingAnnotation(Class<? extends Annotation> annotationType) {
        return bindingAnnotationChecker.hasAnnotations(annotationType);
    }

    public static Annotation canonicalizeIfNamed(Annotation annotation) {
        if (annotation instanceof Named) {
            return Names.named(((Named)annotation).value());
        }
        return annotation;
    }

    public static Class<? extends Annotation> canonicalizeIfNamed(Class<? extends Annotation> annotationType) {
        if (annotationType == Named.class) {
            return com.google.inject.name.Named.class;
        }
        return annotationType;
    }

    public static String nameOf(Key<?> key) {
        Annotation annotation = key.getAnnotation();
        Class<Annotation> annotationType = key.getAnnotationType();
        if (annotation != null && !Annotations.isMarker(annotationType)) {
            return key.getAnnotation().toString();
        }
        if (key.getAnnotationType() != null) {
            return '@' + key.getAnnotationType().getName();
        }
        return "";
    }

    static class AnnotationChecker {
        private final Collection<Class<? extends Annotation>> annotationTypes;
        private CacheLoader<Class<? extends Annotation>, Boolean> hasAnnotations = new CacheLoader<Class<? extends Annotation>, Boolean>(){

            @Override
            public Boolean load(Class<? extends Annotation> annotationType) {
                for (Annotation annotation : annotationType.getAnnotations()) {
                    if (!annotationTypes.contains(annotation.annotationType())) continue;
                    return true;
                }
                return false;
            }
        };
        final LoadingCache<Class<? extends Annotation>, Boolean> cache = CacheBuilder.newBuilder().weakKeys().build(this.hasAnnotations);

        AnnotationChecker(Collection<Class<? extends Annotation>> annotationTypes) {
            this.annotationTypes = annotationTypes;
        }

        boolean hasAnnotations(Class<? extends Annotation> annotated) {
            return this.cache.getUnchecked(annotated);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    private static @interface TestAnnotation {
        public String value();
    }

    private static class AnnotationToStringConfig {
        final boolean quote;
        final boolean includeMemberName;
        final boolean dollarSeparator;

        AnnotationToStringConfig(boolean quote, boolean includeMemberName, boolean dollarSeparator) {
            this.quote = quote;
            this.includeMemberName = includeMemberName;
            this.dollarSeparator = dollarSeparator;
        }
    }
}

