/*
 * Decompiled with CFR 0.152.
 */
package io.swagger.v3.core.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.util.PrimitiveType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    public static Type typeFromString(String type) {
        PrimitiveType primitive = PrimitiveType.fromName(type);
        if (primitive != null) {
            return primitive.getKeyClass();
        }
        try {
            return ReflectionUtils.loadClassByName(type);
        }
        catch (Exception e) {
            LOGGER.error(String.format("Failed to resolve '%s' into class", type), e);
            return null;
        }
    }

    public static Class<?> loadClassByName(String className) throws ClassNotFoundException {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        }
    }

    public static boolean isOverriddenMethod(Method methodToFind, Class<?> cls) {
        if (!ReflectionUtils.hasOverriddenMethods(methodToFind, cls)) {
            return false;
        }
        HashSet superClasses = new HashSet();
        Collections.addAll(superClasses, cls.getInterfaces());
        if (cls.getSuperclass() != null) {
            superClasses.add(cls.getSuperclass());
        }
        for (Class clazz : superClasses) {
            if (clazz == null || clazz.equals(Object.class)) continue;
            try {
                Method found = clazz.getMethod(methodToFind.getName(), methodToFind.getParameterTypes());
                if (found.getReturnType().equals(methodToFind.getReturnType())) {
                    if (!methodToFind.getDeclaringClass().equals(clazz)) {
                        return true;
                    }
                    if (ReflectionUtils.getOverriddenMethod(found) == null) {
                        return true;
                    }
                }
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            if (!ReflectionUtils.isOverriddenMethod(methodToFind, clazz)) continue;
            return true;
        }
        return false;
    }

    public static boolean hasOverriddenMethods(Method methodToFind, Class<?> cls) {
        if (cls == null || methodToFind == null) {
            return false;
        }
        boolean found = false;
        for (Method method2 : cls.getMethods()) {
            Class<?>[] paramsToFind;
            boolean equalsParamCount;
            boolean equalsMethodName = method2.getName().equals(methodToFind.getName());
            boolean superClassReturnAssignable = method2.getReturnType().isAssignableFrom(methodToFind.getReturnType());
            boolean classReturnAssignable = methodToFind.getReturnType().isAssignableFrom(method2.getReturnType());
            boolean bl = equalsParamCount = method2.getParameterCount() == methodToFind.getParameterCount();
            if (!equalsMethodName || !equalsParamCount || !superClassReturnAssignable && !classReturnAssignable || (paramsToFind = methodToFind.getParameterTypes()) == null || paramsToFind.length == 0) continue;
            boolean assignableParams = true;
            for (int i = 0; i < paramsToFind.length; ++i) {
                boolean superClassParamAssignable = method2.getParameterTypes()[i].isAssignableFrom(paramsToFind[i]);
                boolean classParamAssignable = paramsToFind[i].isAssignableFrom(method2.getParameterTypes()[i]);
                if (superClassParamAssignable || classParamAssignable) continue;
                assignableParams = false;
            }
            if (!assignableParams) continue;
            if (!found) {
                found = true;
                continue;
            }
            return true;
        }
        return false;
    }

    public static Method getOverriddenMethod(Method method2) {
        Class<?> declaringClass = method2.getDeclaringClass();
        Class<?> superClass = declaringClass.getSuperclass();
        Method result = null;
        if (superClass != null && !superClass.equals(Object.class)) {
            result = ReflectionUtils.findMethod(method2, superClass);
        }
        if (result == null) {
            for (Class<?> anInterface : declaringClass.getInterfaces()) {
                result = ReflectionUtils.findMethod(method2, anInterface);
                if (result == null) continue;
                return result;
            }
        }
        return result;
    }

    public static Field findField(String name, Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            return cls.getField(name);
        }
        catch (NoSuchFieldException nsfe) {
            return null;
        }
    }

    public static Method findMethod(Method methodToFind, Class<?> cls) {
        if (cls == null) {
            return null;
        }
        String methodToSearch = methodToFind.getName();
        Class<?>[] soughtForParameterType = methodToFind.getParameterTypes();
        Type[] soughtForGenericParameterType = methodToFind.getGenericParameterTypes();
        for (Method method2 : cls.getMethods()) {
            if (!method2.getName().equals(methodToSearch) || !method2.getReturnType().isAssignableFrom(methodToFind.getReturnType())) continue;
            Class<?>[] srcParameterTypes = method2.getParameterTypes();
            Type[] srcGenericParameterTypes = method2.getGenericParameterTypes();
            if (soughtForParameterType.length != srcParameterTypes.length || soughtForGenericParameterType.length != srcGenericParameterTypes.length || !ReflectionUtils.hasIdenticalParameters(srcParameterTypes, soughtForParameterType, srcGenericParameterTypes, soughtForGenericParameterType)) continue;
            return method2;
        }
        return null;
    }

    private static boolean hasIdenticalParameters(Class<?>[] srcParameterTypes, Class<?>[] soughtForParameterType, Type[] srcGenericParameterTypes, Type[] soughtForGenericParameterType) {
        for (int j = 0; j < soughtForParameterType.length; ++j) {
            Class<?> parameterType = soughtForParameterType[j];
            if (srcParameterTypes[j].equals(parameterType) || !srcGenericParameterTypes[j].equals(soughtForGenericParameterType[j]) && srcParameterTypes[j].isAssignableFrom(parameterType)) continue;
            return false;
        }
        return true;
    }

    public static boolean isInject(List<Annotation> annotations) {
        for (Annotation annotation : annotations) {
            if (!"javax.inject.Inject".equals(annotation.annotationType().getName())) continue;
            return true;
        }
        return false;
    }

    public static boolean isConstructorCompatible(Constructor<?> constructor) {
        if (!Modifier.isPublic(constructor.getModifiers())) {
            int access = 7;
            return constructor.getParameterTypes().length == 0 && (constructor.getDeclaringClass().getModifiers() & 7) == constructor.getModifiers();
        }
        return true;
    }

    public static List<Field> getDeclaredFields(Class<?> cls) {
        if (cls == null || Object.class.equals(cls)) {
            return Collections.emptyList();
        }
        ArrayList<Field> fields = new ArrayList<Field>();
        HashSet<String> fieldNames = new HashSet<String>();
        for (Field field2 : cls.getDeclaredFields()) {
            fields.add(field2);
            fieldNames.add(field2.getName());
        }
        for (Field field3 : ReflectionUtils.getDeclaredFields(cls.getSuperclass())) {
            if (fieldNames.contains(field3.getName())) continue;
            fields.add(field3);
        }
        fields.sort(Comparator.comparing(Field::getName));
        return fields;
    }

    public static <A extends Annotation> A getAnnotation(Method method2, Class<A> annotationClass) {
        A annotation = method2.getAnnotation(annotationClass);
        if (annotation == null) {
            for (Annotation metaAnnotation : method2.getAnnotations()) {
                annotation = metaAnnotation.annotationType().getAnnotation(annotationClass);
                if (annotation == null) continue;
                return annotation;
            }
            Method superclassMethod = ReflectionUtils.getOverriddenMethod(method2);
            if (superclassMethod != null) {
                annotation = ReflectionUtils.getAnnotation(superclassMethod, annotationClass);
            }
        }
        return annotation;
    }

    public static <A extends Annotation> A getAnnotation(Class<?> cls, Class<A> annotationClass) {
        A annotation = cls.getAnnotation(annotationClass);
        if (annotation == null) {
            for (Annotation metaAnnotation : cls.getAnnotations()) {
                annotation = metaAnnotation.annotationType().getAnnotation(annotationClass);
                if (annotation == null) continue;
                return annotation;
            }
            Class<?> superClass = cls.getSuperclass();
            if (superClass != null && !superClass.equals(Object.class)) {
                annotation = ReflectionUtils.getAnnotation(superClass, annotationClass);
            }
        }
        if (annotation == null) {
            for (Class<?> anInterface : cls.getInterfaces()) {
                for (Annotation metaAnnotation : anInterface.getAnnotations()) {
                    annotation = metaAnnotation.annotationType().getAnnotation(annotationClass);
                    if (annotation == null) continue;
                    return annotation;
                }
                annotation = ReflectionUtils.getAnnotation(anInterface, annotationClass);
                if (annotation == null) continue;
                return annotation;
            }
        }
        return annotation;
    }

    public static <A extends Annotation> List<A> getRepeatableAnnotations(Method method2, Class<A> annotationClass) {
        List<A> superAnnotations;
        LinkedHashSet<Annotation> annotationsSet = new LinkedHashSet<Annotation>();
        Annotation[] annotations = method2.getAnnotationsByType(annotationClass);
        if (annotations != null) {
            annotationsSet.addAll(Arrays.asList(annotations));
        }
        for (Annotation metaAnnotation : method2.getAnnotations()) {
            annotations = metaAnnotation.annotationType().getAnnotationsByType(annotationClass);
            if (annotations == null || annotations.length <= 0) continue;
            annotationsSet.addAll(Arrays.asList(annotations));
        }
        Method superclassMethod = ReflectionUtils.getOverriddenMethod(method2);
        if (superclassMethod != null && (superAnnotations = ReflectionUtils.getRepeatableAnnotations(superclassMethod, annotationClass)) != null) {
            annotationsSet.addAll(superAnnotations);
        }
        if (annotationsSet.isEmpty()) {
            return null;
        }
        return new ArrayList(annotationsSet);
    }

    public static <A extends Annotation> List<A> getRepeatableAnnotations(Class<?> cls, Class<A> annotationClass) {
        Annotation[] annotations = ReflectionUtils.getRepeatableAnnotationsArray(cls, annotationClass);
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        return Arrays.asList(annotations);
    }

    public static <A extends Annotation> A[] getRepeatableAnnotationsArray(Class<?> cls, Class<A> annotationClass) {
        Annotation[] annotations = cls.getAnnotationsByType(annotationClass);
        if (annotations == null || annotations.length == 0) {
            for (Annotation metaAnnotation : cls.getAnnotations()) {
                annotations = metaAnnotation.annotationType().getAnnotationsByType(annotationClass);
                if (annotations == null || annotations.length <= 0) continue;
                return annotations;
            }
            Class<?> superClass = cls.getSuperclass();
            if (superClass != null && !superClass.equals(Object.class)) {
                annotations = ReflectionUtils.getRepeatableAnnotationsArray(superClass, annotationClass);
            }
        }
        if (annotations == null || annotations.length == 0) {
            for (Class<?> anInterface : cls.getInterfaces()) {
                for (Annotation metaAnnotation : anInterface.getAnnotations()) {
                    annotations = metaAnnotation.annotationType().getAnnotationsByType(annotationClass);
                    if (annotations == null || annotations.length <= 0) continue;
                    return annotations;
                }
                annotations = ReflectionUtils.getRepeatableAnnotationsArray((Class)anInterface, annotationClass);
                if (annotations == null) continue;
                return annotations;
            }
        }
        return annotations;
    }

    public static Annotation[][] getParameterAnnotations(Method method2) {
        Annotation[][] methodAnnotations = method2.getParameterAnnotations();
        Method overriddenmethod = ReflectionUtils.getOverriddenMethod(method2);
        while (overriddenmethod != null) {
            Annotation[][] overriddenAnnotations = overriddenmethod.getParameterAnnotations();
            for (int i = 0; i < methodAnnotations.length; ++i) {
                int j;
                ArrayList<Class<? extends Annotation>> types = new ArrayList<Class<? extends Annotation>>();
                for (j = 0; j < methodAnnotations[i].length; ++j) {
                    types.add(methodAnnotations[i][j].annotationType());
                }
                for (j = 0; j < overriddenAnnotations[i].length; ++j) {
                    if (types.contains(overriddenAnnotations[i][j].annotationType())) continue;
                    methodAnnotations[i] = ArrayUtils.add(methodAnnotations[i], overriddenAnnotations[i][j]);
                }
            }
            overriddenmethod = ReflectionUtils.getOverriddenMethod(overriddenmethod);
        }
        return methodAnnotations;
    }

    public static boolean isVoid(Type type) {
        Class<?> cls = TypeFactory.defaultInstance().constructType(type).getRawClass();
        return Void.class.isAssignableFrom(cls) || Void.TYPE.isAssignableFrom(cls);
    }

    public static boolean isSystemType(JavaType type) {
        for (String systemPrefix : PrimitiveType.systemPrefixes()) {
            if (!type.getRawClass().getName().startsWith(systemPrefix) || PrimitiveType.nonSystemTypes().contains(type.getRawClass().getName()) || PrimitiveType.nonSystemTypePackages().contains(type.getRawClass().getPackage().getName())) continue;
            return true;
        }
        return type.isArrayType();
    }

    public static Optional<Object> safeInvoke(Method method2, Object obj, Object ... args2) {
        try {
            return Optional.ofNullable(method2.invoke(obj, args2));
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            return Optional.empty();
        }
    }

    public static Optional<Object> safeGet(Field field2, Object obj) {
        try {
            return Optional.ofNullable(field2.get(obj));
        }
        catch (IllegalAccessException e) {
            return Optional.empty();
        }
    }
}

