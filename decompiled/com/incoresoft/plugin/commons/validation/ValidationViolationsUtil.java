/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.validation;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;
import com.incoresoft.middleware.validation.ValidationViolation;
import com.incoresoft.plugin.commons.validation.SpecifyViolationType;
import com.incoresoft.plugin.commons.validation.ValidationParametersArgument;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolation;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.jetbrains.annotations.NotNull;

public class ValidationViolationsUtil {
    private static final Map<String, String> violationTypeMappings = Map.of("MIN", "MINIMAL_VALUE", "MAX", "MAXIMAL_VALUE");
    private static final Set<String> filteredAnnotationMethods = Set.of("message", "groups", "payload");
    private static final Set<String> hardcodedNodeNames = Set.of("<return value>", "<cross-parameter>", "<iterable element>", "<list element>", "<map key>", "<map value>");

    public static <T> ValidationViolation obtainValidationViolation(@NotNull T object, ConstraintViolation<?> violation) {
        Object annotation = violation.getConstraintDescriptor().getAnnotation();
        return new ValidationViolation(ValidationViolationsUtil.getViolationType(annotation), ValidationViolationsUtil.getFullFieldName(object, violation), ValidationViolationsUtil.getValidationArgument(annotation));
    }

    private static <T> String getFullFieldName(@NotNull T object, ConstraintViolation<?> violation) {
        return StreamSupport.stream(violation.getPropertyPath().spliterator(), false).map(it -> (NodeImpl)it).filter(ValidationViolationsUtil::isNotHardcodedNodeName).map(it -> ValidationViolationsUtil.getJSONFieldName(object, it, true)).collect(Collectors.joining("."));
    }

    private static boolean isNotHardcodedNodeName(NodeImpl node) {
        return !hardcodedNodeNames.contains(node.getName());
    }

    private static String getViolationType(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        return ValidationViolationsUtil.getViolationTypeFromMetadata(annotationType).orElse(ValidationViolationsUtil.getViolationTypeFromClassName(annotationType));
    }

    private static Optional<String> getViolationTypeFromMetadata(Class<? extends Annotation> annotationType) {
        SpecifyViolationType metaAnnotation = annotationType.getAnnotation(SpecifyViolationType.class);
        if (metaAnnotation != null) {
            return Optional.of(metaAnnotation.value());
        }
        return Optional.empty();
    }

    private static String getViolationTypeFromClassName(Class<? extends Annotation> annotationType) {
        String violationType = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, annotationType.getSimpleName());
        return violationTypeMappings.getOrDefault(violationType, violationType);
    }

    private static <T> String getJSONFieldName(T rootObject, NodeImpl node, boolean appendIndex) {
        Object parentValue = node.getParent().getValue();
        if (parentValue == null) {
            parentValue = rootObject;
        }
        String fieldName = node.toString();
        int bracketIndex = fieldName.indexOf("[");
        String namePart = fieldName;
        String indexPart = "";
        if (bracketIndex != -1) {
            namePart = fieldName.substring(0, bracketIndex);
            indexPart = fieldName.substring(bracketIndex);
        }
        try {
            return ValidationViolationsUtil.getJSONFieldName(parentValue.getClass(), namePart) + (appendIndex ? indexPart : "");
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getJSONFieldName(Class<?> clazz, String name) throws NoSuchFieldException {
        Field field2 = ValidationViolationsUtil.getFields(clazz).get(name);
        if (field2 == null) {
            throw new NoSuchFieldException(name);
        }
        if (field2.isAnnotationPresent(JsonProperty.class) && !field2.getAnnotation(JsonProperty.class).value().isBlank()) {
            return field2.getAnnotation(JsonProperty.class).value();
        }
        if (field2.isAnnotationPresent(JSONField.class) && !field2.getAnnotation(JSONField.class).name().isBlank()) {
            return field2.getAnnotation(JSONField.class).name();
        }
        return field2.getName();
    }

    private static Map<String, Field> getFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyMap();
        }
        Map<String, Field> result = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Function.identity()));
        result.putAll(ValidationViolationsUtil.getFields(clazz.getSuperclass()));
        return result;
    }

    private static ValidationParametersArgument getValidationArgument(Annotation annotation) {
        if (annotation == null) {
            return new ValidationParametersArgument(Map.of());
        }
        Map<String, Object> validationParameters = Arrays.stream(annotation.annotationType().getDeclaredMethods()).filter(method2 -> !filteredAnnotationMethods.contains(method2.getName())).collect(Collectors.toMap(method2 -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, method2.getName()), method2 -> ValidationViolationsUtil.getAnnotationMethodValue(method2, annotation)));
        return new ValidationParametersArgument(validationParameters);
    }

    private static Object getAnnotationMethodValue(Method method2, Annotation annotation) {
        try {
            return method2.invoke((Object)annotation, new Object[0]);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

