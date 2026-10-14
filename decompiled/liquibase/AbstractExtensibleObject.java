/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.ExtensibleObject;
import liquibase.ExtensibleObjectAttribute;
import liquibase.ObjectMetaData;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.ObjectUtil;
import liquibase.util.SmartMap;
import liquibase.util.StringUtil;

public class AbstractExtensibleObject
implements ExtensibleObject {
    private final SmartMap attributes = new SmartMap();
    private static final Map<Class, Map<String, Field>> attributeFieldCache = new HashMap<Class, Map<String, Field>>();

    public AbstractExtensibleObject() {
    }

    public AbstractExtensibleObject(Map<String, ?> values2) {
        for (Map.Entry<String, ?> entry : values2.entrySet()) {
            this.set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public SortedSet<String> getAttributes() {
        TreeSet<String> returnSet = new TreeSet<String>(this.attributes.keySet());
        for (String field2 : this.getAttributeFields().keySet()) {
            if (!this.has(field2)) continue;
            returnSet.add(field2);
        }
        return Collections.unmodifiableSortedSet(returnSet);
    }

    @Override
    public ObjectMetaData getObjectMetaData() {
        ObjectMetaData metaData = new ObjectMetaData();
        for (Field field2 : this.getAttributeFields().values()) {
            ObjectMetaData.Attribute attribute = new ObjectMetaData.Attribute(field2.getName());
            attribute.type = field2.getGenericType();
            ExtensibleObjectAttribute annotation = field2.getAnnotation(ExtensibleObjectAttribute.class);
            if (annotation != null) {
                attribute.description = annotation.description();
                attribute.required = annotation.required();
            }
            metaData.attributes.add(attribute);
        }
        return metaData;
    }

    @Override
    public boolean has(String key) {
        return this.get(key, (Object)Object.class) != null;
    }

    @Override
    public <T> T get(String attribute, Class<T> type) {
        return this.get(attribute, null, type);
    }

    @Override
    public <T> T get(String attribute, T defaultValue) {
        Class type = Object.class;
        if (defaultValue != null) {
            type = defaultValue.getClass();
        }
        return (T)this.get(attribute, defaultValue, type);
    }

    private Map<String, Field> getAttributeFields() {
        Map<String, Field> fields = attributeFieldCache.get(this.getClass());
        if (fields == null) {
            fields = new HashMap<String, Field>();
            for (Field field2 : this.getClass().getFields()) {
                int modifiers = field2.getModifiers();
                if (!Modifier.isPublic(modifiers) || field2.isSynthetic() || Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) continue;
                fields.put(field2.getName(), field2);
            }
            attributeFieldCache.put(this.getClass(), fields);
        }
        return fields;
    }

    protected <T> T get(String attribute, T defaultValue, Class<T> type) {
        Object value;
        if (attribute.contains(".")) {
            List path = this.getValuePath(attribute, type);
            value = path.get(path.size() - 1);
        } else {
            value = this.getFieldValue(attribute, type);
        }
        if (value == null) {
            return defaultValue;
        }
        return (T)value;
    }

    @Override
    public List getValuePath(String attributes, Class lastType) {
        String baseField;
        ArrayList<Class<Object>> path = new ArrayList<Class<Object>>();
        String remainingAttribute = null;
        int separatorIndex = attributes.indexOf(46);
        if (separatorIndex < 0) {
            baseField = attributes;
        } else {
            baseField = attributes.substring(0, separatorIndex);
            remainingAttribute = attributes.substring(separatorIndex + 1);
        }
        Object lastValue = this;
        while (baseField != null) {
            ArrayList newValue;
            Class<Object> typeToGet;
            boolean isLastField = remainingAttribute == null;
            Class<Object> clazz = typeToGet = isLastField ? lastType : Object.class;
            if (lastValue == null) {
                newValue = null;
            } else if (lastValue instanceof ExtensibleObject) {
                newValue = ((ExtensibleObject)lastValue).get(baseField, typeToGet);
            } else if (lastValue instanceof Collection) {
                newValue = new ArrayList();
                boolean foundNonNullValue = false;
                for (Object object : (Collection)lastValue) {
                    if (object == null) {
                        ((Collection)newValue).add(null);
                        continue;
                    }
                    if (object instanceof ExtensibleObject) {
                        ((Collection)newValue).add(((ExtensibleObject)object).get(baseField, typeToGet));
                        foundNonNullValue = true;
                        continue;
                    }
                    throw new UnexpectedLiquibaseException("Cannot traverse field(s) " + baseField + " on a " + object.getClass().getName());
                }
                if (!foundNonNullValue) {
                    newValue = null;
                }
            } else {
                throw new UnexpectedLiquibaseException("Cannot traverse field(s) " + baseField + " on a " + lastValue.getClass().getName());
            }
            if (newValue instanceof Collection) {
                ArrayList flattenedCollection = new ArrayList();
                for (Object obj : (Collection)newValue) {
                    if (obj instanceof Collection) {
                        flattenedCollection.addAll((Collection)obj);
                        continue;
                    }
                    flattenedCollection.add(obj);
                }
                newValue = flattenedCollection;
            }
            path.add((Class<Object>)((Object)newValue));
            lastValue = newValue;
            if (remainingAttribute == null) {
                baseField = null;
                continue;
            }
            separatorIndex = remainingAttribute.indexOf(46);
            if (separatorIndex < 0) {
                baseField = remainingAttribute;
                remainingAttribute = null;
                continue;
            }
            baseField = remainingAttribute.substring(0, separatorIndex);
            remainingAttribute = remainingAttribute.substring(separatorIndex + 1);
        }
        return path;
    }

    protected Object getFieldValue(String attribute, Class type) {
        Class value;
        Field field2 = this.getAttributeFields().get(attribute);
        if (field2 == null) {
            value = this.attributes.get(attribute, type);
        } else {
            try {
                value = ObjectUtil.convert(field2.get(this), type);
            }
            catch (IllegalAccessException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return value;
    }

    @Override
    public ExtensibleObject set(String attribute, Object value) {
        Field field2 = this.getAttributeFields().get(attribute);
        if (field2 == null) {
            this.attributes.set(attribute, value);
        } else {
            try {
                field2.set(this, ObjectUtil.convert(value, field2.getType()));
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException("Error setting " + this.getClass().getName() + "." + attribute, e);
            }
        }
        return this;
    }

    @Override
    public String describe() {
        String name = this.getClass().getSimpleName();
        return name + "{" + StringUtil.join(this, ", ", (StringUtil.StringUtilFormatter)new StringUtil.DefaultFormatter()) + "}";
    }

    public String toString() {
        return this.describe();
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof AbstractExtensibleObject && this.describe().equals(((AbstractExtensibleObject)obj).describe());
    }

    @Override
    public Object clone() {
        try {
            AbstractExtensibleObject clone = (AbstractExtensibleObject)super.clone();
            for (String attr : this.getAttributes()) {
                Object valueClone2;
                Class<Object> value = this.get(attr, (Object)Object.class);
                if (value instanceof Collection) {
                    try {
                        valueClone2 = (Collection)value.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                        valueClone2.addAll((Collection)((Object)value));
                        value = valueClone2;
                    }
                    catch (Exception valueClone2) {}
                } else if (value instanceof Map) {
                    try {
                        valueClone2 = (Map)value.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                        valueClone2.putAll((Map)((Object)value));
                        value = valueClone2;
                    }
                    catch (Exception exception) {}
                } else if (value instanceof AbstractExtensibleObject) {
                    value = ((AbstractExtensibleObject)((Object)value)).clone();
                }
                clone.set(attr, value);
            }
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

