/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.exception.UnexpectedLiquibaseException;

public class ReflectionSerializer {
    private static final ReflectionSerializer instance = new ReflectionSerializer();
    private final Map<Class, Map<String, Field>> reflectionCache = new ConcurrentHashMap<Class, Map<String, Field>>();

    public static ReflectionSerializer getInstance() {
        return instance;
    }

    private ReflectionSerializer() {
    }

    public Set<String> getFields(Object object) {
        if (!this.reflectionCache.containsKey(object.getClass())) {
            HashMap<String, Field> fields = new HashMap<String, Field>();
            HashSet<Field> allFields = new HashSet<Field>();
            Class<?> classToExtractFieldsFrom = object.getClass();
            while (!classToExtractFieldsFrom.equals(Object.class)) {
                allFields.addAll(Arrays.asList(classToExtractFieldsFrom.getDeclaredFields()));
                classToExtractFieldsFrom = classToExtractFieldsFrom.getSuperclass();
            }
            for (Field field2 : allFields) {
                if ("serialVersionUID".equals(field2.getName()) || "serializableFields".equals(field2.getName()) || field2.isSynthetic() || "$VRc".equals(field2.getName())) continue;
                fields.put(field2.getName(), field2);
                field2.setAccessible(true);
            }
            this.reflectionCache.put(object.getClass(), fields);
        }
        return this.reflectionCache.get(object.getClass()).keySet();
    }

    private Field findField(Object object, String field2) {
        Field foundField = null;
        Class<?> classToCheck = object.getClass();
        while (foundField == null && !classToCheck.equals(Object.class)) {
            try {
                foundField = classToCheck.getDeclaredField(field2);
            }
            catch (NoSuchFieldException e) {
                classToCheck = classToCheck.getSuperclass();
            }
        }
        if (foundField == null) {
            throw new UnexpectedLiquibaseException("No field " + field2 + " on " + object.getClass());
        }
        return foundField;
    }

    public Object getValue(Object object, String field2) {
        if (!this.reflectionCache.containsKey(object.getClass())) {
            this.getFields(object);
        }
        Map<String, Field> fieldsByName = this.reflectionCache.get(object.getClass());
        Field foundField = fieldsByName.get(field2);
        try {
            if (foundField == null) {
                foundField = this.findField(object, field2);
                foundField.setAccessible(true);
                fieldsByName.put(field2, foundField);
            }
            return foundField.get(object);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public void setValue(Object object, String field2, Object value) {
        try {
            Field foundField = this.findField(object, field2);
            foundField.setAccessible(true);
            foundField.set(object, value);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public Class getDataTypeClass(Object object, String field2) {
        try {
            Field foundField = this.findField(object, field2);
            Type dataType = foundField.getGenericType();
            if (dataType instanceof Class) {
                return (Class)dataType;
            }
            if (dataType instanceof ParameterizedType) {
                return (Class)((ParameterizedType)dataType).getRawType();
            }
            return Object.class;
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public Type[] getDataTypeClassParameters(Object object, String field2) {
        try {
            Field foundField = this.findField(object, field2);
            Type dataType = foundField.getGenericType();
            if (dataType instanceof ParameterizedType) {
                return ((ParameterizedType)dataType).getActualTypeArguments();
            }
            return new Type[0];
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

