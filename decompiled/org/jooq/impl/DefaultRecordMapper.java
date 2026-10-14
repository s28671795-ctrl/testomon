/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.beans.ConstructorProperties;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jooq.Attachable;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordType;
import org.jooq.exception.MappingException;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.RecordDelegate;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;

public class DefaultRecordMapper<R extends Record, E>
implements RecordMapper<R, E> {
    private final Field<?>[] fields;
    private final RecordType<R> rowType;
    private final Class<? extends E> type;
    private final Configuration configuration;
    private final String namePathSeparator;
    private RecordMapper<R, E> delegate;
    private transient Map<String, Integer> prefixes;

    public DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type) {
        this(rowType, type, null, null);
    }

    public DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, Configuration configuration) {
        this(rowType, type, null, configuration);
    }

    DefaultRecordMapper(RecordType<R> rowType, Class<? extends E> type, E instance, Configuration configuration) {
        this.rowType = rowType;
        this.fields = rowType.fields();
        this.type = type;
        this.configuration = Tools.configuration(configuration);
        this.namePathSeparator = this.configuration.settings().getNamePathSeparator();
        this.init(instance);
    }

    private final void init(E instance) {
        Constructor<?> constructor;
        Parameter[] parameters;
        Constructor<?>[] constructors;
        Boolean debugStaticNestedClass;
        Boolean debugTopLevelClass;
        Boolean debugMatchDegreeNested;
        Boolean debugMatchDegreeFlat;
        Boolean debugKSettings;
        Boolean debugKClass;
        Boolean debugRCSettings;
        Boolean debugRC;
        Boolean debugCPSettings;
        Boolean debugMutableConstructors;
        Boolean debugMutable;
        Boolean debugVTCP;
        Boolean debugVTFL;
        block29: {
            debugVTFL = null;
            debugVTCP = null;
            debugMutable = null;
            debugMutableConstructors = null;
            debugCPSettings = null;
            debugRC = null;
            debugRCSettings = null;
            debugKClass = null;
            debugKSettings = null;
            debugMatchDegreeFlat = null;
            debugMatchDegreeNested = null;
            debugTopLevelClass = null;
            debugStaticNestedClass = null;
            if (this.type.isArray()) {
                this.delegate = new ArrayMapper(instance);
                return;
            }
            if (instance == null) {
                if (Stream.class.isAssignableFrom(this.type)) {
                    this.delegate = r -> Stream.of(((FieldsImpl)this.rowType).mapper(this.configuration, Object[].class).map(r));
                    return;
                }
                if (Modifier.isAbstract(this.type.getModifiers()) && !this.type.isPrimitive()) {
                    this.delegate = new ProxyMapper();
                    return;
                }
            }
            if (AbstractRecord.class.isAssignableFrom(this.type)) {
                this.delegate = new RecordToRecordMapper(this, (AbstractRecord)instance);
                return;
            }
            debugVTFL = this.fields.length == 1;
            if (debugVTFL.booleanValue() && instance == null && (debugVTCP = Boolean.valueOf(Tools.converter(this.configuration, instance, this.fields[0].getType(), this.type) != null)).booleanValue()) {
                this.delegate = new ValueTypeMapper();
                return;
            }
            try {
                MutablePOJOMapper m4 = instance != null ? new MutablePOJOMapper(null, instance) : new MutablePOJOMapper(new ConstructorCall<E>(Reflect.accessible(this.type.getDeclaredConstructor(new Class[0]))), null);
                debugMutable = m4.isMutable();
                if (debugMutable.booleanValue() || (debugMutableConstructors = Boolean.valueOf(this.type.getDeclaredConstructors().length <= 1)).booleanValue()) {
                    this.delegate = m4;
                    return;
                }
            }
            catch (NoSuchMethodException ignore) {
                debugMutable = false;
            }
            constructors = this.type.getDeclaredConstructors();
            Arrays.sort(constructors, (c1, c2) -> (c2.getModifiers() & 1) - (c1.getModifiers() & 1));
            debugCPSettings = !Boolean.FALSE.equals(this.configuration.settings().isMapConstructorPropertiesParameterNames());
            if (debugCPSettings.booleanValue()) {
                for (Constructor<?> constructor2 : constructors) {
                    ConstructorProperties properties = constructor2.getAnnotation(ConstructorProperties.class);
                    if (properties == null) continue;
                    this.delegate = new ImmutablePOJOMapper(constructor2, constructor2.getParameterTypes(), Arrays.asList(properties.value()), true);
                    return;
                }
            }
            if ((debugRCSettings = Boolean.valueOf(Boolean.TRUE.equals(this.configuration.settings().isMapRecordComponentParameterNames()))).booleanValue() && (debugRC = Boolean.valueOf(this.type.isRecord())).booleanValue()) {
                RecordComponent[] rc = this.type.getRecordComponents();
                List<Class> types = Tools.map(rc, RecordComponent::getType);
                for (Constructor<?> constructor3 : constructors) {
                    Class<?>[] parameterTypes = constructor3.getParameterTypes();
                    if (!types.equals(Arrays.asList(parameterTypes))) continue;
                    this.delegate = new ImmutablePOJOMapper(constructor3, parameterTypes, Tools.map(rc, RecordComponent::getName), true);
                    return;
                }
            }
            if ((debugKClass = Boolean.valueOf(Tools.isKotlinAvailable())).booleanValue() && (debugKSettings = Boolean.valueOf(!Boolean.FALSE.equals(this.configuration.settings().isMapConstructorParameterNamesInKotlin()))).booleanValue()) {
                try {
                    Reflect jvmClassMappingKt = Tools.ktJvmClassMapping();
                    Reflect kClasses = Tools.ktKClasses();
                    Reflect kTypeParameter = Tools.ktKTypeParameter();
                    Object klass = jvmClassMappingKt.call("getKotlinClass", this.type).get();
                    Reflect primaryConstructor = kClasses.call("getPrimaryConstructor", klass);
                    debugKClass = primaryConstructor.get() != null;
                    if (!debugKClass.booleanValue()) break block29;
                    List parameters2 = (List)primaryConstructor.call("getParameters").get();
                    Class<?> klassType = Tools.ktKClass().type();
                    Method getJavaClass = jvmClassMappingKt.type().getMethod("getJavaClass", klassType);
                    ArrayList<String> parameterNames = new ArrayList<String>(parameters2.size());
                    Class[] parameterTypes = new Class[parameters2.size()];
                    for (int i = 0; i < parameterTypes.length; ++i) {
                        Reflect parameter = Reflect.on(parameters2.get(i));
                        Object typeClassifier = parameter.call("getType").call("getClassifier").get();
                        String name = (String)parameter.call("getName").get();
                        try {
                            while (((Boolean)Reflect.on(typeClassifier).call("isValue").get()).booleanValue()) {
                                typeClassifier = kClasses.call("getPrimaryConstructor", typeClassifier).call("getParameters").call("get", 0).call("getType").call("getClassifier").get();
                            }
                        }
                        catch (ReflectException reflectException) {
                            // empty catch block
                        }
                        parameterTypes[i] = (Class)getJavaClass.invoke(jvmClassMappingKt.get(), kTypeParameter.type().isInstance(typeClassifier) ? Reflect.on(typeClassifier).call("getUpperBounds").call("get", 0).call("getClassifier").get() : typeClassifier);
                        String typeName = parameterTypes[i].getName();
                        if (name.startsWith("is") && (Boolean.TYPE.getName().equalsIgnoreCase(typeName) || Boolean.class.getName().equals(typeName))) {
                            name = Tools.getPropertyName(name);
                        }
                        parameterNames.add(name);
                    }
                    Constructor<E> javaConstructor = Reflect.accessible(this.type.getDeclaredConstructor(parameterTypes));
                    this.delegate = new ImmutablePOJOMapper(javaConstructor, javaConstructor.getParameterTypes(), parameterNames, true);
                    return;
                }
                catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | ReflectException jvmClassMappingKt) {
                    // empty catch block
                }
            }
        }
        boolean mapConstructorParameterNames = Boolean.TRUE.equals(this.configuration.settings().isMapConstructorParameterNames());
        for (boolean supportsNesting : new boolean[]{true, false}) {
            for (Constructor<?> constructor4 : constructors) {
                Parameter[] parameters3;
                Class<?>[] parameterTypes = constructor4.getParameterTypes();
                if (parameterTypes.length != (supportsNesting ? this.prefixes().size() : this.fields.length)) continue;
                if (supportsNesting) {
                    debugMatchDegreeNested = true;
                } else {
                    debugMatchDegreeFlat = true;
                }
                if (mapConstructorParameterNames && (parameters3 = constructor4.getParameters()) != null && parameters3.length > 0) {
                    this.delegate = new ImmutablePOJOMapper(constructor4, parameterTypes, this.collectParameterNames(parameters3), supportsNesting);
                }
                if (this.delegate == null) {
                    this.delegate = new ImmutablePOJOMapper(constructor4, parameterTypes, Collections.emptyList(), supportsNesting);
                }
                return;
            }
            if (supportsNesting) {
                debugMatchDegreeNested = false;
                continue;
            }
            debugMatchDegreeFlat = false;
        }
        if (mapConstructorParameterNames && (parameters = (constructor = constructors[0]).getParameters()) != null && parameters.length > 0) {
            this.delegate = new ImmutablePOJOMapper(constructor, constructor.getParameterTypes(), this.collectParameterNames(parameters), false);
            return;
        }
        debugTopLevelClass = !this.type.isMemberClass();
        debugStaticNestedClass = this.type.isMemberClass() && Modifier.isStatic(this.type.getModifiers());
        throw new MappingException("No DefaultRecordMapper strategy applies to type $type for row type $rowType. Attempted strategies include (in this order):\n- Is type an array (false)?\n- Is type a Stream (false)?\n- Does row type have only 1 column ($debugVTFL) and did ConverterProvider provide a Converter for type ($debugVTCP)?\n- Is type abstract (false)?\n- Is type a org.jooq.Record (false)?\n- Is type a mutable POJO (a POJO with setters or non-final members: $debugMutable) and has a no-args constructor ($debugMutableConstructors)?\n- Does type have a @ConstructorProperties annotated constructor (false) and is Settings.mapConstructorPropertiesParameterNames enabled ($debugCPSettings)?\n- Is type a java.lang.Record ($debugRC) and is Settings.mapRecordComponentParameterNames enabled ($debugRCSettings)?\n- Is type a kotlin class ($debugKClass) and is Settings.mapConstructorParameterNamesInKotlin enabled ($debugKSettings)?\n- Is there a constructor that matches row type's degrees with nested fields ($debugMatchDegreeNested) or flat fields ($debugMatchDegreeFlat)\n- Is the type a top level class ($debugTopLevelClass) or static nested class ($debugStaticNestedClass)?\n-   (Inner classes cannot be created via reflection)\n- Is Settings.mapConstructorParameterNames enabled ($debugMatchNames)\n".replace("$type", this.type.toString()).replace("$rowType", this.rowType.toString()).replace("$debugVTFL", DefaultRecordMapper.debug(debugVTFL)).replace("$debugVTCP", DefaultRecordMapper.debug(debugVTCP)).replace("$debugCPSettings", DefaultRecordMapper.debug(debugCPSettings)).replace("$debugMutableConstructors", DefaultRecordMapper.debug(debugMutableConstructors)).replace("$debugMutable", DefaultRecordMapper.debug(debugMutable)).replace("$debugRCSettings", DefaultRecordMapper.debug(debugRCSettings)).replace("$debugRC", DefaultRecordMapper.debug(debugRC)).replace("$debugKClass", DefaultRecordMapper.debug(debugKClass)).replace("$debugKSettings", DefaultRecordMapper.debug(debugKSettings)).replace("$debugMatchDegreeNested", DefaultRecordMapper.debug(debugMatchDegreeNested)).replace("$debugMatchDegreeFlat", DefaultRecordMapper.debug(debugMatchDegreeFlat)).replace("$debugMatchNames", DefaultRecordMapper.debug(mapConstructorParameterNames)).replace("$debugTopLevelClass", DefaultRecordMapper.debug(debugTopLevelClass)).replace("$debugStaticNestedClass", DefaultRecordMapper.debug(debugStaticNestedClass)));
    }

    private static final String debug(Boolean debug) {
        return debug == null ? "check skipped" : debug.toString();
    }

    private List<String> collectParameterNames(Parameter[] parameters) {
        return Arrays.stream(parameters).map(Parameter::getName).collect(Collectors.toList());
    }

    @Override
    public final E map(R record) {
        if (record == null) {
            return null;
        }
        try {
            return DefaultRecordMapper.attach(this.delegate.map(record), record);
        }
        catch (MappingException e) {
            throw e;
        }
        catch (Exception e) {
            throw new MappingException("An error occurred when mapping record to " + String.valueOf(this.type), e);
        }
    }

    private static <E> E attach(E attachable, Record record) {
        if (attachable instanceof Attachable) {
            Attachable a = (Attachable)attachable;
            if (Tools.attachRecords(record.configuration())) {
                a.attach(record.configuration());
            }
        }
        return attachable;
    }

    private final Map<String, Integer> prefixes() {
        if (this.prefixes == null) {
            this.prefixes = new LinkedHashMap<String, Integer>();
            int[] i = new int[]{0};
            for (Field<?> field2 : this.fields) {
                String name = field2.getName();
                int separator = name.indexOf(this.namePathSeparator);
                this.prefixes.computeIfAbsent(separator > -1 ? name.substring(0, separator) : name, k -> {
                    int n = i[0];
                    i[0] = n + 1;
                    return n;
                });
            }
        }
        return this.prefixes;
    }

    public String toString() {
        return this.delegate.toString();
    }

    private class ArrayMapper
    extends AbstractDelegateMapper<R, E> {
        private final E instance;

        ArrayMapper(E instance) {
            this.instance = instance;
        }

        @Override
        public final E map(R record) {
            int size = record.size();
            Class<?> componentType = DefaultRecordMapper.this.type.getComponentType();
            Object[] result = (Object[])(this.instance != null ? this.instance : Array.newInstance(componentType, size));
            if (size > result.length) {
                result = (Object[])Array.newInstance(componentType, size);
            }
            for (int i = 0; i < size; ++i) {
                result[i] = Convert.convert(record.get(i), componentType);
            }
            return result;
        }
    }

    private class ProxyMapper
    extends AbstractDelegateMapper<R, E> {
        private final MutablePOJOMapper pojomapper;

        ProxyMapper() {
            this.pojomapper = new MutablePOJOMapper(() -> Reflect.on(new HashMap()).as(DefaultRecordMapper.this.type), null);
        }

        @Override
        public final E map(R record) {
            return this.pojomapper.map(record);
        }
    }

    private static class RecordToRecordMapper<E extends AbstractRecord>
    extends AbstractDelegateMapper<R, AbstractRecord> {
        private final E instance;
        final /* synthetic */ DefaultRecordMapper this$0;

        RecordToRecordMapper(E instance) {
            this.this$0 = var1_1;
            this.instance = instance;
        }

        @Override
        public final AbstractRecord map(R record) {
            try {
                if (record instanceof AbstractRecord) {
                    AbstractRecord a = (AbstractRecord)record;
                    if (this.instance != null) {
                        return (AbstractRecord)a.intoRecord(this.instance);
                    }
                    return (AbstractRecord)a.intoRecord(this.this$0.type);
                }
                throw new MappingException("Cannot map record " + String.valueOf(record) + " to type " + String.valueOf(this.this$0.type));
            }
            catch (Exception e) {
                throw new MappingException("An error occurred when mapping record to " + String.valueOf(this.this$0.type), e);
            }
        }
    }

    private class ValueTypeMapper
    extends AbstractDelegateMapper<R, E> {
        private ValueTypeMapper() {
        }

        @Override
        public final E map(R record) {
            int size = record.size();
            if (size != 1) {
                throw new MappingException("Cannot map multi-column record of degree " + size + " to value type " + String.valueOf(DefaultRecordMapper.this.type));
            }
            return record.get(0, DefaultRecordMapper.this.type);
        }
    }

    private class MutablePOJOMapper
    extends AbstractDelegateMapper<R, E> {
        private final Callable<E> constructor;
        private final boolean useAnnotations;
        private final List<java.lang.reflect.Field>[] members;
        private final List<Method>[] methods;
        private final Map<String, NestedMappingInfo> nestedMappingInfos;
        private final E instance;

        MutablePOJOMapper(Callable<E> constructor, E instance) {
            this.constructor = constructor;
            this.useAnnotations = Tools.hasColumnAnnotations(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type);
            this.members = new List[DefaultRecordMapper.this.fields.length];
            this.methods = new List[DefaultRecordMapper.this.fields.length];
            this.instance = instance;
            this.nestedMappingInfos = new HashMap<String, NestedMappingInfo>();
            HashMap<String, List> nestedMappedFields = null;
            for (int i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
                Field<?> field2 = DefaultRecordMapper.this.fields[i];
                String name = field2.getName();
                if (this.useAnnotations) {
                    this.members[i] = Tools.getAnnotatedMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, true);
                    this.methods[i] = Tools.getAnnotatedSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, true);
                    continue;
                }
                int separator = name.indexOf(DefaultRecordMapper.this.namePathSeparator);
                if (separator > -1) {
                    String prefix2 = name.substring(0, separator);
                    if (nestedMappedFields == null) {
                        nestedMappedFields = new HashMap<String, List>();
                    }
                    nestedMappedFields.computeIfAbsent(prefix2, p -> new ArrayList()).add(DSL.field(DSL.name(name.substring(prefix2.length() + 1)), field2.getDataType()));
                    this.nestedMappingInfos.computeIfAbsent((String)prefix2, (Function<String, NestedMappingInfo>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, lambda$new$1(java.lang.String ), (Ljava/lang/String;)Lorg/jooq/impl/DefaultRecordMapper$NestedMappingInfo;)()).indexLookup.add(i);
                    this.members[i] = Collections.emptyList();
                    this.methods[i] = Collections.emptyList();
                    continue;
                }
                this.members[i] = Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, true);
                this.methods[i] = Tools.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, true);
            }
            if (nestedMappedFields != null) {
                nestedMappedFields.forEach((prefix, list) -> {
                    NestedMappingInfo nestedMappingInfo = this.nestedMappingInfos.get(prefix);
                    nestedMappingInfo.row = Tools.row0(list);
                    nestedMappingInfo.recordDelegate = Tools.newRecord(true, Tools.recordType(nestedMappingInfo.row.size()), nestedMappingInfo.row, DefaultRecordMapper.this.configuration);
                    for (java.lang.reflect.Field member : Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix, true)) {
                        nestedMappingInfo.mappers.add(nestedMappingInfo.row.fields.mapper(DefaultRecordMapper.this.configuration, member.getType()));
                    }
                    for (Method method2 : Tools.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix, true)) {
                        nestedMappingInfo.mappers.add(nestedMappingInfo.row.fields.mapper(DefaultRecordMapper.this.configuration, method2.getParameterTypes()[0]));
                    }
                });
            }
        }

        final boolean isMutable() {
            for (List<Method> list : this.methods) {
                if (list.isEmpty()) continue;
                return true;
            }
            for (List<AccessibleObject> list : this.members) {
                for (java.lang.reflect.Field field2 : list) {
                    if ((field2.getModifiers() & 0x10) != 0) continue;
                    return true;
                }
            }
            return false;
        }

        @Override
        public final E map(R record) {
            try {
                Object result = this.instance != null ? this.instance : this.constructor.call();
                for (int i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
                    for (java.lang.reflect.Field member : this.members[i]) {
                        if ((member.getModifiers() & 0x10) != 0) continue;
                        this.map((Record)record, result, member, i);
                    }
                    for (Method method2 : this.methods[i]) {
                        Class<?> mType = method2.getParameterTypes()[0];
                        Object value = record.get(i, mType);
                        List<?> list = this.tryConvertToList(value, mType, method2.getGenericParameterTypes()[0]);
                        if (list != null) {
                            method2.invoke(result, list);
                            continue;
                        }
                        method2.invoke(result, record.get(i, mType));
                    }
                }
                for (Map.Entry<String, NestedMappingInfo> entry : this.nestedMappingInfos.entrySet()) {
                    String prefix = entry.getKey();
                    for (RecordMapper<AbstractRecord, Object> mapper2 : entry.getValue().mappers) {
                        entry.getValue().recordDelegate.operate(rec -> {
                            List<Integer> indexes = ((NestedMappingInfo)entry.getValue()).indexLookup;
                            for (int index = 0; index < indexes.size(); ++index) {
                                rec.set(index, record.get(indexes.get(index)));
                            }
                            Object value = mapper2.map((AbstractRecord)rec);
                            for (java.lang.reflect.Field member : Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix, true)) {
                                if ((member.getModifiers() & 0x10) != 0) continue;
                                this.map(value, result, member);
                            }
                            for (Method method2 : Tools.getMatchingSetters(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, prefix, true)) {
                                method2.invoke(result, value);
                            }
                            return rec;
                        });
                    }
                }
                return result;
            }
            catch (Exception e) {
                throw new MappingException("An error occurred when mapping record to " + String.valueOf(DefaultRecordMapper.this.type), e);
            }
        }

        private final void map(Record record, Object result, java.lang.reflect.Field member, int index) throws IllegalAccessException {
            Class<?> mType = member.getType();
            if (mType.isPrimitive()) {
                if (mType == Byte.TYPE) {
                    this.map(record.get(index, Byte.TYPE), result, member);
                } else if (mType == Short.TYPE) {
                    this.map(record.get(index, Short.TYPE), result, member);
                } else if (mType == Integer.TYPE) {
                    this.map(record.get(index, Integer.TYPE), result, member);
                } else if (mType == Long.TYPE) {
                    this.map(record.get(index, Long.TYPE), result, member);
                } else if (mType == Float.TYPE) {
                    this.map(record.get(index, Float.TYPE), result, member);
                } else if (mType == Double.TYPE) {
                    this.map(record.get(index, Double.TYPE), result, member);
                } else if (mType == Boolean.TYPE) {
                    this.map(record.get(index, Boolean.TYPE), result, member);
                } else if (mType == Character.TYPE) {
                    this.map(record.get(index, Character.TYPE), result, member);
                }
            } else {
                Object value = record.get(index, mType);
                List<?> list = this.tryConvertToList(value, mType, member.getGenericType());
                if (list != null) {
                    member.set(result, list);
                } else {
                    this.map(value, result, member);
                }
            }
        }

        private final List<?> tryConvertToList(Object value, Class<?> mType, Type genericType) {
            if (value instanceof Collection && (mType == List.class || mType == ArrayList.class) && genericType instanceof ParameterizedType) {
                Class componentType = (Class)((ParameterizedType)genericType).getActualTypeArguments()[0];
                return Convert.convert((Collection)value, componentType);
            }
            return null;
        }

        private final void map(Object value, Object result, java.lang.reflect.Field member) throws IllegalAccessException {
            Class<?> mType = member.getType();
            if (mType.isPrimitive()) {
                if (mType == Byte.TYPE) {
                    member.setByte(result, (Byte)value);
                } else if (mType == Short.TYPE) {
                    member.setShort(result, (Short)value);
                } else if (mType == Integer.TYPE) {
                    member.setInt(result, (Integer)value);
                } else if (mType == Long.TYPE) {
                    member.setLong(result, (Long)value);
                } else if (mType == Float.TYPE) {
                    member.setFloat(result, ((Float)value).floatValue());
                } else if (mType == Double.TYPE) {
                    member.setDouble(result, (Double)value);
                } else if (mType == Boolean.TYPE) {
                    member.setBoolean(result, (Boolean)value);
                } else if (mType == Character.TYPE) {
                    member.setChar(result, ((Character)value).charValue());
                }
            } else {
                member.set(result, value);
            }
        }

        private static /* synthetic */ NestedMappingInfo lambda$new$1(String p) {
            return new NestedMappingInfo();
        }
    }

    private record ConstructorCall<E>(Constructor<? extends E> constructor) implements Callable<E>
    {
        @Override
        public E call() throws Exception {
            return this.constructor.newInstance(new Object[0]);
        }
    }

    private class ImmutablePOJOMapper
    extends AbstractDelegateMapper<R, E> {
        final Constructor<E> constructor;
        final Class<?>[] parameterTypes;
        private final boolean nested;
        private final NestedMappingInfo[] nestedMappingInfo;
        private final Integer[] propertyIndexes;
        private final List<String> propertyNames;
        private final boolean useAnnotations;
        private final List<java.lang.reflect.Field>[] members;
        private final Method[] methods;

        ImmutablePOJOMapper(Constructor<E> constructor, Class<?>[] parameterTypes, List<String> propertyNames, boolean supportsNesting) {
            int size = DefaultRecordMapper.this.prefixes().size();
            this.constructor = Reflect.accessible(constructor);
            this.parameterTypes = parameterTypes;
            this.nestedMappingInfo = new NestedMappingInfo[size];
            this.propertyIndexes = new Integer[DefaultRecordMapper.this.fields.length];
            this.propertyNames = propertyNames;
            this.useAnnotations = Tools.hasColumnAnnotations(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type);
            this.members = new List[DefaultRecordMapper.this.fields.length];
            this.methods = new Method[DefaultRecordMapper.this.fields.length];
            if (propertyNames.isEmpty()) {
                if (!supportsNesting) {
                    for (i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
                        this.propertyIndexes[i] = i;
                    }
                } else {
                    for (i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
                        field = DefaultRecordMapper.this.fields[i];
                        name = field.getName();
                        int separator = name.indexOf(DefaultRecordMapper.this.namePathSeparator);
                        this.propertyIndexes[i] = DefaultRecordMapper.this.prefixes().get(separator > -1 ? name.substring(0, separator) : name);
                    }
                }
            } else {
                block2: for (i = 0; i < DefaultRecordMapper.this.fields.length; ++i) {
                    int j;
                    field = DefaultRecordMapper.this.fields[i];
                    name = field.getName();
                    String nameLC = StringUtils.toCamelCaseLC(name);
                    if (this.useAnnotations) {
                        this.members[i] = Tools.getAnnotatedMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, false);
                        this.methods[i] = Tools.getAnnotatedGetter(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, true);
                    } else {
                        this.members[i] = Tools.getMatchingMembers(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, false);
                        this.methods[i] = Tools.getMatchingGetter(DefaultRecordMapper.this.configuration, DefaultRecordMapper.this.type, name, true);
                    }
                    for (j = 0; j < propertyNames.size(); ++j) {
                        if (!name.equals(propertyNames.get(j)) && !nameLC.equals(propertyNames.get(j))) continue;
                        this.propertyIndexes[i] = j;
                        continue block2;
                    }
                    for (j = 0; j < propertyNames.size(); ++j) {
                        if (!name.startsWith(propertyNames.get(j) + DefaultRecordMapper.this.namePathSeparator)) continue;
                        this.propertyIndexes[i] = j;
                        continue block2;
                    }
                }
            }
            boolean hasNestedFields = false;
            List[] nestedMappedFields = new List[size];
            if (supportsNesting) {
                block5: for (Map.Entry<String, Integer> entry : DefaultRecordMapper.this.prefixes().entrySet()) {
                    int j;
                    String prefix = entry.getKey();
                    int i = entry.getValue();
                    if (this.nestedMappingInfo[i] == null) {
                        this.nestedMappingInfo[i] = new NestedMappingInfo();
                    }
                    for (j = 0; j < DefaultRecordMapper.this.fields.length; ++j) {
                        if (!DefaultRecordMapper.this.fields[j].getName().equals(prefix)) continue;
                        this.nestedMappingInfo[i].indexLookup.add(j);
                        continue block5;
                    }
                    for (j = 0; j < DefaultRecordMapper.this.fields.length; ++j) {
                        if (!DefaultRecordMapper.this.fields[j].getName().startsWith(prefix + DefaultRecordMapper.this.namePathSeparator)) continue;
                        hasNestedFields = true;
                        if (nestedMappedFields[i] == null) {
                            nestedMappedFields[i] = new ArrayList();
                        }
                        nestedMappedFields[i].add(DSL.field(DSL.name(DefaultRecordMapper.this.fields[j].getName().substring(prefix.length() + 1)), DefaultRecordMapper.this.fields[j].getDataType()));
                        this.nestedMappingInfo[i].indexLookup.add(j);
                    }
                    if (nestedMappedFields[i] == null) continue;
                    this.nestedMappingInfo[i].row = Tools.row0(nestedMappedFields[i].toArray(Tools.EMPTY_FIELD));
                    this.nestedMappingInfo[i].recordDelegate = Tools.newRecord(true, Tools.recordType(this.nestedMappingInfo[i].row.size()), this.nestedMappingInfo[i].row, DefaultRecordMapper.this.configuration);
                    this.nestedMappingInfo[i].mappers.add(this.nestedMappingInfo[i].row.fields.mapper(DefaultRecordMapper.this.configuration, parameterTypes[this.propertyIndexes[this.nestedMappingInfo[i].indexLookup.get(0)]]));
                }
            }
            this.nested = hasNestedFields;
        }

        @Override
        public final E map(R record) {
            try {
                return this.constructor.newInstance(this.nested ? this.mapNested(record) : this.mapNonnested(record));
            }
            catch (Exception e) {
                throw new MappingException("An error occurred when mapping record to " + String.valueOf(DefaultRecordMapper.this.type), e);
            }
        }

        private final Object[] mapNonnested(R record) {
            Object[] converted = Tools.map(this.parameterTypes, c -> Reflect.initValue(c), Object[]::new);
            for (int i = 0; i < record.size(); ++i) {
                this.set((Record)record, i, converted, this.propertyIndexes[i]);
            }
            return converted;
        }

        final void set(Record from, int fromIndex, Object[] to, Integer toIndex) {
            if (toIndex != null) {
                to[toIndex.intValue()] = from.get(fromIndex, this.parameterTypes[toIndex]);
            } else {
                String name;
                int index;
                for (java.lang.reflect.Field member : this.members[fromIndex]) {
                    int index2 = this.propertyNames.indexOf(member.getName());
                    if (index2 < 0) continue;
                    to[index2] = from.get(fromIndex, member.getType());
                }
                if (this.methods[fromIndex] != null && (index = this.propertyNames.indexOf(name = Tools.getPropertyName(this.methods[fromIndex].getName()))) >= 0) {
                    to[index] = from.get(fromIndex, this.methods[fromIndex].getReturnType());
                }
            }
        }

        private final Object[] mapNested(R record) {
            Object[] converted = new Object[this.parameterTypes.length];
            for (int i = 0; i < this.nestedMappingInfo.length; ++i) {
                NestedMappingInfo info = this.nestedMappingInfo[i];
                List<Integer> indexLookup = info.indexLookup;
                Integer j = indexLookup.get(0);
                Integer k = this.propertyIndexes[j];
                if (k == null) continue;
                converted[k.intValue()] = info.row == null ? record.get((int)j, this.parameterTypes[k]) : info.mappers.get(0).map(info.recordDelegate.operate(rec -> {
                    for (int x = 0; x < indexLookup.size(); ++x) {
                        rec.set(x, record.get((Integer)indexLookup.get(x)));
                    }
                    return rec;
                }));
            }
            return converted;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " [ (" + String.valueOf(DefaultRecordMapper.this.rowType) + ") -> " + String.valueOf(this.constructor) + "]";
        }
    }

    static class NestedMappingInfo {
        final List<RecordMapper<AbstractRecord, Object>> mappers = new ArrayList<RecordMapper<AbstractRecord, Object>>();
        AbstractRow row;
        final List<Integer> indexLookup = new ArrayList<Integer>();
        RecordDelegate<? extends AbstractRecord> recordDelegate;

        NestedMappingInfo() {
        }

        public String toString() {
            return "NestedMappingInfo " + String.valueOf(this.indexLookup) + "; (" + String.valueOf(this.row) + ")";
        }
    }

    private abstract class AbstractDelegateMapper<R0 extends Record, E0>
    implements RecordMapper<R0, E0> {
        private AbstractDelegateMapper() {
        }

        public String toString() {
            return this.getClass().getSimpleName() + " [ (" + String.valueOf(DefaultRecordMapper.this.rowType) + ") -> " + String.valueOf(DefaultRecordMapper.this.type) + "]";
        }
    }
}

