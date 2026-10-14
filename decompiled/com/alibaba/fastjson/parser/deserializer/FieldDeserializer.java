/*
 * Decompiled with CFR 0.152.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class FieldDeserializer {
    public final FieldInfo fieldInfo;
    protected final Class<?> clazz;
    protected BeanContext beanContext;

    public FieldDeserializer(Class<?> clazz, FieldInfo fieldInfo) {
        this.clazz = clazz;
        this.fieldInfo = fieldInfo;
    }

    public Class<?> getOwnerClass() {
        return this.clazz;
    }

    public abstract void parseField(DefaultJSONParser var1, Object var2, Type var3, Map<String, Object> var4);

    public int getFastMatchToken() {
        return 0;
    }

    public void setValue(Object object, boolean value) {
        this.setValue(object, (Object)value);
    }

    public void setValue(Object object, int value) {
        this.setValue(object, (Object)value);
    }

    public void setValue(Object object, long value) {
        this.setValue(object, (Object)value);
    }

    public void setValue(Object object, String value) {
        this.setValue(object, (Object)value);
    }

    public void setValue(Object object, Object value) {
        block53: {
            if (value == null && this.fieldInfo.fieldClass.isPrimitive()) {
                return;
            }
            if (this.fieldInfo.fieldClass == String.class && this.fieldInfo.format != null && this.fieldInfo.format.equals("trim")) {
                value = ((String)value).trim();
            }
            try {
                Method method2 = this.fieldInfo.method;
                if (method2 != null) {
                    if (this.fieldInfo.getOnly) {
                        if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                            AtomicInteger atomic = (AtomicInteger)method2.invoke(object, new Object[0]);
                            if (atomic != null) {
                                atomic.set(((AtomicInteger)value).get());
                            } else {
                                FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                            }
                            break block53;
                        }
                        if (this.fieldInfo.fieldClass == AtomicLong.class) {
                            AtomicLong atomic = (AtomicLong)method2.invoke(object, new Object[0]);
                            if (atomic != null) {
                                atomic.set(((AtomicLong)value).get());
                            } else {
                                FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                            }
                            break block53;
                        }
                        if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                            AtomicBoolean atomic = (AtomicBoolean)method2.invoke(object, new Object[0]);
                            if (atomic != null) {
                                atomic.set(((AtomicBoolean)value).get());
                            } else {
                                FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                            }
                            break block53;
                        }
                        if (Map.class.isAssignableFrom(method2.getReturnType())) {
                            Map map = null;
                            try {
                                map = (Map)method2.invoke(object, new Object[0]);
                            }
                            catch (InvocationTargetException e) {
                                FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                                return;
                            }
                            if (map != null) {
                                if (map == Collections.emptyMap()) {
                                    return;
                                }
                                if (map.isEmpty() && ((Map)value).isEmpty()) {
                                    return;
                                }
                                String mapClassName = map.getClass().getName();
                                if (mapClassName.equals("java.util.ImmutableCollections$Map1") || mapClassName.equals("java.util.ImmutableCollections$MapN") || mapClassName.startsWith("java.util.Collections$Unmodifiable")) {
                                    return;
                                }
                                if (map.getClass().getName().equals("kotlin.collections.EmptyMap")) {
                                    FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                                    return;
                                }
                                map.putAll((Map)value);
                            } else if (value != null) {
                                FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                            }
                            break block53;
                        }
                        Collection collection = null;
                        try {
                            collection = (Collection)method2.invoke(object, new Object[0]);
                        }
                        catch (InvocationTargetException e) {
                            FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                            return;
                        }
                        if (collection != null && value != null) {
                            String collectionClassName = collection.getClass().getName();
                            if (collection == Collections.emptySet() || collection == Collections.emptyList() || collectionClassName == "java.util.ImmutableCollections$ListN" || collectionClassName == "java.util.ImmutableCollections$List12" || collectionClassName.startsWith("java.util.Collections$Unmodifiable")) {
                                return;
                            }
                            if (!collection.isEmpty()) {
                                collection.clear();
                            } else if (((Collection)value).isEmpty()) {
                                return;
                            }
                            if (collectionClassName.equals("kotlin.collections.EmptyList") || collectionClassName.equals("kotlin.collections.EmptySet")) {
                                FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                                return;
                            }
                            collection.addAll((Collection)value);
                        } else if (collection == null && value != null) {
                            FieldDeserializer.degradeValueAssignment(this.fieldInfo.field, method2, object, value);
                        }
                        break block53;
                    }
                    method2.invoke(object, value);
                    break block53;
                }
                Field field2 = this.fieldInfo.field;
                if (this.fieldInfo.getOnly) {
                    if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                        AtomicInteger atomic = (AtomicInteger)field2.get(object);
                        if (atomic != null) {
                            atomic.set(((AtomicInteger)value).get());
                        }
                    } else if (this.fieldInfo.fieldClass == AtomicLong.class) {
                        AtomicLong atomic = (AtomicLong)field2.get(object);
                        if (atomic != null) {
                            atomic.set(((AtomicLong)value).get());
                        }
                    } else if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                        AtomicBoolean atomic = (AtomicBoolean)field2.get(object);
                        if (atomic != null) {
                            atomic.set(((AtomicBoolean)value).get());
                        }
                    } else if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                        Map map = (Map)field2.get(object);
                        if (map != null) {
                            if (map == Collections.emptyMap() || map.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                                return;
                            }
                            map.putAll((Map)value);
                        }
                    } else {
                        Collection collection = (Collection)field2.get(object);
                        if (collection != null && value != null) {
                            if (collection == Collections.emptySet() || collection == Collections.emptyList() || collection.getClass().getName().startsWith("java.util.Collections$Unmodifiable")) {
                                return;
                            }
                            collection.clear();
                            collection.addAll((Collection)value);
                        }
                    }
                } else if (field2 != null) {
                    field2.set(object, value);
                }
            }
            catch (Exception e) {
                throw new JSONException("set property error, " + this.clazz.getName() + "#" + this.fieldInfo.name, e);
            }
        }
    }

    private static boolean degradeValueAssignment(Field field2, Method getMethod, Object object, Object value) throws InvocationTargetException, IllegalAccessException {
        if (FieldDeserializer.setFieldValue(field2, object, value)) {
            return true;
        }
        try {
            Method setMethod = object.getClass().getDeclaredMethod("set" + getMethod.getName().substring(3), getMethod.getReturnType());
            setMethod.invoke(object, value);
            return true;
        }
        catch (InvocationTargetException invocationTargetException) {
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        return false;
    }

    private static boolean setFieldValue(Field field2, Object object, Object value) throws IllegalAccessException {
        if (field2 != null && !Modifier.isFinal(field2.getModifiers())) {
            field2.set(object, value);
            return true;
        }
        return false;
    }

    public void setWrappedValue(String key, Object value) {
        throw new JSONException("TODO");
    }
}

