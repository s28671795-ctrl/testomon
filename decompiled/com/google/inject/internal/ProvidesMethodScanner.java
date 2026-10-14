/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.RealMapBinder;
import com.google.inject.internal.RealMultibinder;
import com.google.inject.internal.RealOptionalBinder;
import com.google.inject.multibindings.MapKey;
import com.google.inject.multibindings.ProvidesIntoMap;
import com.google.inject.multibindings.ProvidesIntoOptional;
import com.google.inject.multibindings.ProvidesIntoSet;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

final class ProvidesMethodScanner
extends ModuleAnnotatedMethodScanner {
    static final ProvidesMethodScanner INSTANCE = new ProvidesMethodScanner();
    private static final ImmutableSet<Class<? extends Annotation>> ANNOTATIONS = ImmutableSet.of(Provides.class, ProvidesIntoSet.class, ProvidesIntoMap.class, ProvidesIntoOptional.class);

    private ProvidesMethodScanner() {
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotationClasses() {
        return ANNOTATIONS;
    }

    @Override
    public <T> Key<T> prepareMethod(Binder binder, Annotation annotation, Key<T> key, InjectionPoint injectionPoint) {
        Method method2 = (Method)injectionPoint.getMember();
        AnnotationOrError mapKey = ProvidesMethodScanner.findMapKeyAnnotation(binder, method2);
        if (annotation instanceof Provides) {
            if (mapKey.annotation != null) {
                binder.addError("Found a MapKey annotation on non map binding at %s.", method2);
            }
            return key;
        }
        if (annotation instanceof ProvidesIntoSet) {
            if (mapKey.annotation != null) {
                binder.addError("Found a MapKey annotation on non map binding at %s.", method2);
            }
            return RealMultibinder.newRealSetBinder(binder, key).getKeyForNewItem();
        }
        if (annotation instanceof ProvidesIntoMap) {
            if (mapKey.error) {
                return key;
            }
            if (mapKey.annotation == null) {
                binder.addError("No MapKey found for map binding at %s.", method2);
                return key;
            }
            TypeAndValue<?> typeAndValue = ProvidesMethodScanner.typeAndValueOfMapKey(mapKey.annotation);
            return RealMapBinder.newRealMapBinder(binder, typeAndValue.type, key).getKeyForNewValue(typeAndValue.value);
        }
        if (annotation instanceof ProvidesIntoOptional) {
            if (mapKey.annotation != null) {
                binder.addError("Found a MapKey annotation on non map binding at %s.", method2);
            }
            switch (((ProvidesIntoOptional)annotation).value()) {
                case DEFAULT: {
                    return RealOptionalBinder.newRealOptionalBinder(binder, key).getKeyForDefaultBinding();
                }
                case ACTUAL: {
                    return RealOptionalBinder.newRealOptionalBinder(binder, key).getKeyForActualBinding();
                }
            }
        }
        throw new IllegalStateException("Invalid annotation: " + annotation);
    }

    private static AnnotationOrError findMapKeyAnnotation(Binder binder, Method method2) {
        Annotation foundAnnotation = null;
        for (Annotation annotation : method2.getAnnotations()) {
            MapKey mapKey = annotation.annotationType().getAnnotation(MapKey.class);
            if (mapKey == null) continue;
            if (foundAnnotation != null) {
                binder.addError("Found more than one MapKey annotations on %s.", method2);
                return AnnotationOrError.forError();
            }
            if (mapKey.unwrapValue()) {
                try {
                    Method valueMethod = annotation.annotationType().getDeclaredMethod("value", new Class[0]);
                    if (valueMethod.getReturnType().isArray()) {
                        binder.addError("Array types are not allowed in a MapKey with unwrapValue=true: %s", annotation.annotationType());
                        return AnnotationOrError.forError();
                    }
                }
                catch (NoSuchMethodException invalid) {
                    binder.addError("No 'value' method in MapKey with unwrapValue=true: %s", annotation.annotationType());
                    return AnnotationOrError.forError();
                }
            }
            foundAnnotation = annotation;
        }
        return AnnotationOrError.forPossiblyNullAnnotation(foundAnnotation);
    }

    static TypeAndValue<?> typeAndValueOfMapKey(Annotation mapKeyAnnotation) {
        if (!mapKeyAnnotation.annotationType().getAnnotation(MapKey.class).unwrapValue()) {
            return new TypeAndValue<Annotation>(TypeLiteral.get(mapKeyAnnotation.annotationType()), mapKeyAnnotation);
        }
        try {
            Method valueMethod = mapKeyAnnotation.annotationType().getDeclaredMethod("value", new Class[0]);
            valueMethod.setAccessible(true);
            TypeLiteral<?> returnType = TypeLiteral.get(mapKeyAnnotation.annotationType()).getReturnType(valueMethod);
            return new TypeAndValue<Object>(returnType, valueMethod.invoke((Object)mapKeyAnnotation, new Object[0]));
        }
        catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
        catch (SecurityException e) {
            throw new IllegalStateException(e);
        }
        catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private static class TypeAndValue<T> {
        final TypeLiteral<T> type;
        final T value;

        TypeAndValue(TypeLiteral<T> type, T value) {
            this.type = type;
            this.value = value;
        }
    }

    private static class AnnotationOrError {
        final Annotation annotation;
        final boolean error;

        AnnotationOrError(Annotation annotation, boolean error) {
            this.annotation = annotation;
            this.error = error;
        }

        static AnnotationOrError forPossiblyNullAnnotation(Annotation annotation) {
            return new AnnotationOrError(annotation, false);
        }

        static AnnotationOrError forError() {
            return new AnnotationOrError(null, true);
        }
    }
}

