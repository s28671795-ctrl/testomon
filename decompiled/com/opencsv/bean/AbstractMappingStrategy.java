/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.AbstractCsvConverter;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.ComplexFieldMapEntry;
import com.opencsv.bean.ConverterCurrency;
import com.opencsv.bean.ConverterDate;
import com.opencsv.bean.ConverterEnum;
import com.opencsv.bean.ConverterNumber;
import com.opencsv.bean.ConverterPrimitiveTypes;
import com.opencsv.bean.ConverterUUID;
import com.opencsv.bean.CsvConverter;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvDates;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;
import com.opencsv.bean.CsvNumbers;
import com.opencsv.bean.CsvRecurse;
import com.opencsv.bean.FieldAccess;
import com.opencsv.bean.FieldMap;
import com.opencsv.bean.HeaderIndex;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import com.opencsv.exceptions.CsvRecursionException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Function;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public abstract class AbstractMappingStrategy<I, K extends Comparable<K>, C extends ComplexFieldMapEntry<I, K, T>, T>
implements MappingStrategy<T> {
    private static final Set<Class> FORBIDDEN_CLASSES_FOR_RECURSION = new HashSet<Class>(Arrays.asList(Byte.TYPE, Short.TYPE, Integer.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Long.TYPE, Character.TYPE));
    protected Class<? extends T> type;
    protected final HeaderIndex headerIndex = new HeaderIndex();
    protected RecursiveType recursiveTypeTree;
    private MultiValuedMap<Class<?>, Field> ignoredFields = new ArrayListValuedHashMap();
    protected Locale errorLocale = Locale.getDefault();
    protected String profile = "";

    protected abstract K chooseMultivaluedFieldIndexFromHeaderIndex(int var1);

    protected abstract FieldMap<I, K, ? extends C, T> getFieldMap();

    protected Set<Class<? extends Annotation>> getBindingAnnotations() {
        return Collections.emptySet();
    }

    protected void loadAnnotatedFieldMap(ListValuedMap<Class<?>, Field> fields) {
    }

    protected abstract void loadUnadornedFieldMap(ListValuedMap<Class<?>, Field> var1);

    protected abstract void initializeFieldMap();

    protected abstract BeanField<T, K> findField(int var1);

    protected abstract void verifyLineLength(int var1) throws CsvRequiredFieldEmptyException;

    protected Map<Class<?>, Object> createBean() throws CsvBeanIntrospectionException, IllegalStateException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("type.unset"));
        }
        HashMap instanceMap = new HashMap();
        try {
            T rootBean = this.type.newInstance();
            instanceMap.put(this.type, rootBean);
            AbstractMappingStrategy.createSubordinateBeans(this.recursiveTypeTree, instanceMap, rootBean);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            CsvBeanIntrospectionException csve = new CsvBeanIntrospectionException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("bean.instantiation.impossible"));
            csve.initCause(e);
            throw csve;
        }
        return instanceMap;
    }

    private static void createSubordinateBeans(RecursiveType typeTree, Map<Class<?>, Object> instanceMap, Object containingObject) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Map.Entry<FieldAccess<Object>, RecursiveType> entry : typeTree.getRecursiveMembers().entrySet()) {
            Object childObject = entry.getKey().getField(containingObject);
            if (childObject == null) {
                childObject = entry.getValue().type.newInstance();
                entry.getKey().setField(containingObject, childObject);
            }
            instanceMap.put(entry.getValue().getType(), childObject);
            AbstractMappingStrategy.createSubordinateBeans(entry.getValue(), instanceMap, childObject);
        }
    }

    protected Map<Class<?>, Object> indexBean(T bean) throws IllegalAccessException, InvocationTargetException {
        HashMap instanceMap = new HashMap();
        instanceMap.put(this.type, bean);
        AbstractMappingStrategy.indexSubordinateBeans(this.recursiveTypeTree, instanceMap, bean);
        return instanceMap;
    }

    private static void indexSubordinateBeans(RecursiveType typeTree, Map<Class<?>, Object> instanceMap, Object containingObject) throws IllegalAccessException, InvocationTargetException {
        for (Map.Entry<FieldAccess<Object>, RecursiveType> entry : typeTree.getRecursiveMembers().entrySet()) {
            Object childObject = containingObject == null ? null : entry.getKey().getField(containingObject);
            instanceMap.put(entry.getValue().getType(), childObject);
            AbstractMappingStrategy.indexSubordinateBeans(entry.getValue(), instanceMap, childObject);
        }
    }

    public abstract String findHeader(int var1);

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        if (this.type == null) {
            throw new IllegalStateException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("type.before.header"));
        }
        if (this.headerIndex.isEmpty()) {
            String[] header = this.getFieldMap().generateHeader(bean);
            this.headerIndex.initializeHeaderIndex(header);
            return header;
        }
        return this.headerIndex.getHeaderIndex();
    }

    protected String getColumnName(int col) {
        return this.headerIndex.getByPosition(col);
    }

    public Class<? extends T> getType() {
        return this.type;
    }

    @Override
    public T populateNewBean(String[] line) throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
        this.verifyLineLength(line.length);
        Map<Class<?>, Object> beanTree = this.createBean();
        CsvChainedException chainedException = null;
        for (int col = 0; col < line.length; ++col) {
            try {
                this.setFieldValue(beanTree, line[col], col);
                continue;
            }
            catch (CsvFieldAssignmentException e) {
                if (chainedException != null) {
                    chainedException.add(e);
                    continue;
                }
                chainedException = new CsvChainedException(e);
            }
        }
        if (chainedException != null) {
            if (chainedException.hasOnlyOneException()) {
                throw chainedException.getFirstException();
            }
            throw chainedException;
        }
        return (T)beanTree.get(this.type);
    }

    @Override
    public void setType(Class<? extends T> type) throws CsvBadConverterException {
        this.type = type;
        this.loadFieldMap();
    }

    @Override
    public void setProfile(String profile) {
        this.profile = StringUtils.defaultString(profile);
    }

    @Override
    public void ignoreFields(MultiValuedMap<Class<?>, Field> fields) throws IllegalArgumentException {
        if (fields == null) {
            this.ignoredFields = new ArrayListValuedHashMap();
        } else {
            this.ignoredFields = fields;
            MapIterator<Class<?>, Field> it = this.ignoredFields.mapIterator();
            it.forEachRemaining(t2 -> {
                Field f = (Field)it.getValue();
                if (t2 == null || f == null || !f.getDeclaringClass().isAssignableFrom((Class<?>)t2)) {
                    throw new IllegalArgumentException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("ignore.field.inconsistent"));
                }
            });
        }
        if (this.type != null) {
            this.loadFieldMap();
        }
    }

    protected List<Field> filterIgnoredFields(Class<?> type, Field[] fields) {
        LinkedList<Field> filteredFields = new LinkedList<Field>();
        for (Field f : fields) {
            HashSet<String> ignoredProfiles;
            CsvIgnore ignoreAnnotation = f.getAnnotation(CsvIgnore.class);
            HashSet<String> hashSet = ignoredProfiles = ignoreAnnotation == null ? SetUtils.emptySet() : new HashSet<String>(Arrays.asList(ignoreAnnotation.profiles()));
            if (this.ignoredFields.containsMapping(type, f) || ignoredProfiles.contains(this.profile) || ignoredProfiles.contains("")) continue;
            filteredFields.add(f);
        }
        return filteredFields;
    }

    protected void loadFieldMap() throws CsvBadConverterException {
        this.initializeFieldMap();
        this.recursiveTypeTree = this.loadRecursiveClasses(this.type, new HashSet());
        Map<Boolean, ListValuedMap<Class<?>, Field>> partitionedFields = this.partitionFields();
        if (!partitionedFields.get(Boolean.TRUE).isEmpty()) {
            this.loadAnnotatedFieldMap(partitionedFields.get(Boolean.TRUE));
        } else {
            this.loadUnadornedFieldMap(partitionedFields.get(Boolean.FALSE));
        }
    }

    protected boolean isForbiddenClassForRecursion(Class<?> type) {
        return FORBIDDEN_CLASSES_FOR_RECURSION.contains(type);
    }

    protected RecursiveType loadRecursiveClasses(Class<?> newType, Set<Class<?>> encounteredTypes) {
        if (this.isForbiddenClassForRecursion(newType)) {
            throw new CsvRecursionException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("recursion.on.primitive"), newType);
        }
        if (encounteredTypes.contains(newType)) {
            throw new CsvRecursionException(String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("recursive.type.encountered.twice"), newType.toString()), newType);
        }
        encounteredTypes.add(newType);
        RecursiveType localRecursiveTypeTree = new RecursiveType(newType);
        for (Field f : this.filterIgnoredFields(newType, FieldUtils.getFieldsWithAnnotation(newType, CsvRecurse.class))) {
            Set<Class<Annotation>> bindingAnnotations = this.getBindingAnnotations();
            if (bindingAnnotations.stream().anyMatch(f::isAnnotationPresent)) {
                throw new CsvRecursionException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("recursion.binding.mutually.exclusive"), f.getType());
            }
            localRecursiveTypeTree.addRecursiveMember(new FieldAccess<Object>(f), this.loadRecursiveClasses(f.getType(), encounteredTypes));
        }
        return localRecursiveTypeTree;
    }

    private void assembleCompleteFieldList(RecursiveType root, ListValuedMap<Class<?>, Field> encounteredFields) {
        encounteredFields.putAll(root.type, this.filterIgnoredFields(root.type, FieldUtils.getAllFields(root.type)));
        root.getRecursiveMembers().values().forEach(f -> this.assembleCompleteFieldList((RecursiveType)f, encounteredFields));
    }

    protected Map<Boolean, ListValuedMap<Class<?>, Field>> partitionFields() {
        ArrayListValuedHashMap allFields = new ArrayListValuedHashMap();
        this.assembleCompleteFieldList(this.recursiveTypeTree, allFields);
        Set<Class<Annotation>> bindingAnnotations = this.getBindingAnnotations();
        TreeMap returnValue = new TreeMap();
        returnValue.put(Boolean.TRUE, new ArrayListValuedHashMap());
        returnValue.put(Boolean.FALSE, new ArrayListValuedHashMap());
        allFields.entries().stream().filter(entry -> !((Field)entry.getValue()).isSynthetic()).forEach(entry -> {
            if (bindingAnnotations.stream().anyMatch(a -> ((Field)entry.getValue()).isAnnotationPresent((Class<? extends Annotation>)a))) {
                ((ListValuedMap)returnValue.get(Boolean.TRUE)).put(entry.getKey(), entry.getValue());
            } else {
                ((ListValuedMap)returnValue.get(Boolean.FALSE)).put(entry.getKey(), entry.getValue());
            }
        });
        return returnValue;
    }

    protected BeanField<T, K> instantiateCustomConverter(Class<? extends AbstractBeanField<T, K>> converter) throws CsvBadConverterException {
        try {
            BeanField c = converter.newInstance();
            c.setErrorLocale(this.errorLocale);
            return c;
        }
        catch (IllegalAccessException | InstantiationException oldEx) {
            CsvBadConverterException newEx = new CsvBadConverterException(converter, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("custom.converter.invalid"), converter.getCanonicalName()));
            newEx.initCause(oldEx);
            throw newEx;
        }
    }

    @Override
    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
        if (this.getFieldMap() != null) {
            this.getFieldMap().setErrorLocale(this.errorLocale);
            this.getFieldMap().values().forEach(f -> f.setErrorLocale(this.errorLocale));
        }
    }

    protected void setFieldValue(Map<Class<?>, Object> beanTree, String value, int column) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, CsvConstraintViolationException, CsvValidationException {
        BeanField<T, K> beanField = this.findField(column);
        if (beanField != null) {
            Object subordinateBean = beanTree.get(beanField.getType());
            beanField.setFieldValue(subordinateBean, value, this.findHeader(column));
        }
    }

    @Override
    public String[] transmuteBean(T bean) throws CsvFieldAssignmentException, CsvChainedException {
        Map<Class<?>, Object> instanceMap;
        int numColumns = this.headerIndex.findMaxIndex() + 1;
        ArrayList<String> contents = new ArrayList<String>(Math.max(numColumns, 0));
        try {
            instanceMap = this.indexBean(bean);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            CsvBeanIntrospectionException csve = new CsvBeanIntrospectionException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("error.introspecting.beans"));
            csve.initCause(e);
            throw csve;
        }
        CsvChainedException chainedException = null;
        int i = 0;
        while (i < numColumns) {
            BeanField<T, K> firstBeanField = this.findField(i);
            K firstIndex = this.chooseMultivaluedFieldIndexFromHeaderIndex(i);
            String[] fields = ArrayUtils.EMPTY_STRING_ARRAY;
            if (firstBeanField != null) {
                try {
                    fields = firstBeanField.write(instanceMap.get(firstBeanField.getType()), firstIndex);
                }
                catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                    if (chainedException != null) {
                        chainedException.add(e);
                    }
                    chainedException = new CsvChainedException(e);
                }
            }
            if (fields.length == 0) {
                contents.add("");
                ++i;
                continue;
            }
            contents.add(StringUtils.defaultString(fields[0]));
            int j = 1;
            int displacedIndex = i + j;
            BeanField<T, K> subsequentBeanField = this.findField(displacedIndex);
            K subsequentIndex = this.chooseMultivaluedFieldIndexFromHeaderIndex(displacedIndex);
            while (j < fields.length && displacedIndex < numColumns && Objects.equals(firstBeanField, subsequentBeanField) && Objects.equals(firstIndex, subsequentIndex)) {
                contents.add(StringUtils.defaultString(fields[j]));
                displacedIndex = i + ++j;
                subsequentBeanField = this.findField(displacedIndex);
                subsequentIndex = this.chooseMultivaluedFieldIndexFromHeaderIndex(displacedIndex);
            }
            i = displacedIndex;
            if (i >= numColumns) continue;
            subsequentBeanField = this.findField(i);
            subsequentIndex = this.chooseMultivaluedFieldIndexFromHeaderIndex(i);
            while (Objects.equals(firstBeanField, subsequentBeanField) && Objects.equals(firstIndex, subsequentIndex) && i < numColumns) {
                contents.add("");
                subsequentBeanField = this.findField(++i);
                subsequentIndex = this.chooseMultivaluedFieldIndexFromHeaderIndex(i);
            }
        }
        if (chainedException != null) {
            if (chainedException.hasOnlyOneException()) {
                throw chainedException.getFirstException();
            }
            throw chainedException;
        }
        return contents.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    protected CsvConverter determineConverter(Field field2, Class<?> elementType, String locale, String writeLocale, Class<? extends AbstractCsvConverter> customConverter) throws CsvBadConverterException {
        CsvConverter converter;
        if (customConverter != null && !customConverter.equals(AbstractCsvConverter.class)) {
            try {
                converter = customConverter.newInstance();
            }
            catch (IllegalAccessException | InstantiationException oldEx) {
                CsvBadConverterException newEx = new CsvBadConverterException(customConverter, String.format(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("custom.converter.invalid"), customConverter.getCanonicalName()));
                newEx.initCause(oldEx);
                throw newEx;
            }
            converter.setType(elementType);
            converter.setLocale(locale);
            converter.setWriteLocale(writeLocale);
            converter.setErrorLocale(this.errorLocale);
        } else if (field2.isAnnotationPresent(CsvDate.class) || field2.isAnnotationPresent(CsvDates.class)) {
            CsvDate annotation = (CsvDate)this.selectAnnotationForProfile(field2.getAnnotationsByType(CsvDate.class), CsvDate::profiles);
            if (annotation == null) {
                throw new CsvBadConverterException(CsvDate.class, String.format(ResourceBundle.getBundle("opencsv").getString("profile.not.found.date"), this.profile));
            }
            String readFormat = annotation.value();
            String writeFormat = annotation.writeFormatEqualsReadFormat() ? readFormat : annotation.writeFormat();
            String readChrono = annotation.chronology();
            String writeChrono = annotation.writeChronologyEqualsReadChronology() ? readChrono : annotation.writeChronology();
            converter = new ConverterDate(elementType, locale, writeLocale, this.errorLocale, readFormat, writeFormat, readChrono, writeChrono);
        } else if (field2.isAnnotationPresent(CsvNumber.class) || field2.isAnnotationPresent(CsvNumbers.class)) {
            CsvNumber annotation = (CsvNumber)this.selectAnnotationForProfile(field2.getAnnotationsByType(CsvNumber.class), CsvNumber::profiles);
            if (annotation == null) {
                throw new CsvBadConverterException(CsvNumber.class, String.format(ResourceBundle.getBundle("opencsv").getString("profile.not.found.number"), this.profile));
            }
            String readFormat = annotation.value();
            String writeFormat = annotation.writeFormatEqualsReadFormat() ? readFormat : annotation.writeFormat();
            converter = new ConverterNumber(elementType, locale, writeLocale, this.errorLocale, readFormat, writeFormat, annotation.roundingMode());
        } else {
            converter = elementType.equals(Currency.class) ? new ConverterCurrency(this.errorLocale) : (elementType.isEnum() ? new ConverterEnum(elementType, locale, writeLocale, this.errorLocale) : (elementType.equals(UUID.class) ? new ConverterUUID(this.errorLocale) : new ConverterPrimitiveTypes(elementType, locale, writeLocale, this.errorLocale)));
        }
        return converter;
    }

    protected <A extends Annotation> A selectAnnotationForProfile(A[] annotations, Function<A, String[]> getProfiles) {
        A defaultAnnotation = null;
        for (A annotation : annotations) {
            String[] profilesForAnnotation;
            for (String p : profilesForAnnotation = getProfiles.apply(annotation)) {
                if (this.profile.equals(p)) {
                    return annotation;
                }
                if (!"".equals(p)) continue;
                defaultAnnotation = annotation;
            }
        }
        return defaultAnnotation;
    }

    protected static class RecursiveType {
        private final Class<?> type;
        private final Map<FieldAccess<Object>, RecursiveType> recursiveMembers = new HashMap<FieldAccess<Object>, RecursiveType>();

        protected RecursiveType(Class<?> type) {
            this.type = type;
        }

        public Class<?> getType() {
            return this.type;
        }

        public void addRecursiveMember(FieldAccess<Object> member, RecursiveType memberType) {
            this.recursiveMembers.put(member, memberType);
        }

        public Map<FieldAccess<Object>, RecursiveType> getRecursiveMembers() {
            return this.recursiveMembers;
        }
    }
}

