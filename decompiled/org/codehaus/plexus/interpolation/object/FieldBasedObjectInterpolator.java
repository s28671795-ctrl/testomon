/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.object;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import org.codehaus.plexus.interpolation.BasicInterpolator;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.SimpleRecursionInterceptor;
import org.codehaus.plexus.interpolation.object.ObjectInterpolationWarning;
import org.codehaus.plexus.interpolation.object.ObjectInterpolator;

public class FieldBasedObjectInterpolator
implements ObjectInterpolator {
    public static final Set<String> DEFAULT_BLACKLISTED_FIELD_NAMES;
    public static final Set<String> DEFAULT_BLACKLISTED_PACKAGE_PREFIXES;
    private static final Map<Class, Field[]> fieldsByClass;
    private static final Map<Class, Boolean> fieldIsPrimitiveByClass;
    private Set<String> blacklistedFieldNames;
    private Set<String> blacklistedPackagePrefixes;
    private List<ObjectInterpolationWarning> warnings = new ArrayList<ObjectInterpolationWarning>();

    public static void clearCaches() {
        fieldsByClass.clear();
        fieldIsPrimitiveByClass.clear();
    }

    public FieldBasedObjectInterpolator() {
        this.blacklistedFieldNames = DEFAULT_BLACKLISTED_FIELD_NAMES;
        this.blacklistedPackagePrefixes = DEFAULT_BLACKLISTED_PACKAGE_PREFIXES;
    }

    public FieldBasedObjectInterpolator(Set<String> blacklistedFieldNames, Set<String> blacklistedPackagePrefixes) {
        this.blacklistedFieldNames = blacklistedFieldNames;
        this.blacklistedPackagePrefixes = blacklistedPackagePrefixes;
    }

    @Override
    public boolean hasWarnings() {
        return this.warnings != null && !this.warnings.isEmpty();
    }

    @Override
    public List<ObjectInterpolationWarning> getWarnings() {
        return new ArrayList<ObjectInterpolationWarning>(this.warnings);
    }

    @Override
    public void interpolate(Object target, BasicInterpolator interpolator) throws InterpolationException {
        this.interpolate(target, interpolator, new SimpleRecursionInterceptor());
    }

    @Override
    public void interpolate(Object target, BasicInterpolator interpolator, RecursionInterceptor recursionInterceptor) throws InterpolationException {
        this.warnings.clear();
        InterpolateObjectAction action = new InterpolateObjectAction(target, interpolator, recursionInterceptor, this.blacklistedFieldNames, this.blacklistedPackagePrefixes, this.warnings);
        InterpolationException error = (InterpolationException)AccessController.doPrivileged(action);
        if (error != null) {
            throw error;
        }
    }

    static /* synthetic */ Map access$300() {
        return fieldsByClass;
    }

    static {
        fieldsByClass = new WeakHashMap<Class, Field[]>();
        fieldIsPrimitiveByClass = new WeakHashMap<Class, Boolean>();
        HashSet<String> blacklistedFields = new HashSet<String>();
        blacklistedFields.add("parent");
        DEFAULT_BLACKLISTED_FIELD_NAMES = Collections.unmodifiableSet(blacklistedFields);
        HashSet<String> blacklistedPackages = new HashSet<String>();
        blacklistedPackages.add("java");
        DEFAULT_BLACKLISTED_PACKAGE_PREFIXES = Collections.unmodifiableSet(blacklistedPackages);
    }

    private static final class InterpolationTarget {
        private Object value;
        private String path;

        private InterpolationTarget(Object value, String path) {
            this.value = value;
            this.path = path;
        }

        static /* synthetic */ String access$200(InterpolationTarget x0) {
            return x0.path;
        }
    }

    private static final class InterpolateObjectAction
    implements PrivilegedAction {
        private final LinkedList<InterpolationTarget> interpolationTargets;
        private final BasicInterpolator interpolator;
        private final Set blacklistedFieldNames;
        private final String[] blacklistedPackagePrefixes;
        private final List<ObjectInterpolationWarning> warningCollector;
        private final RecursionInterceptor recursionInterceptor;

        public InterpolateObjectAction(Object target, BasicInterpolator interpolator, RecursionInterceptor recursionInterceptor, Set blacklistedFieldNames, Set blacklistedPackagePrefixes, List<ObjectInterpolationWarning> warningCollector) {
            this.recursionInterceptor = recursionInterceptor;
            this.blacklistedFieldNames = blacklistedFieldNames;
            this.warningCollector = warningCollector;
            this.blacklistedPackagePrefixes = blacklistedPackagePrefixes.toArray(new String[blacklistedPackagePrefixes.size()]);
            this.interpolationTargets = new LinkedList();
            this.interpolationTargets.add(new InterpolationTarget(target, ""));
            this.interpolator = interpolator;
        }

        public Object run() {
            while (!this.interpolationTargets.isEmpty()) {
                InterpolationTarget target = this.interpolationTargets.removeFirst();
                try {
                    this.traverseObjectWithParents(target.value.getClass(), target);
                }
                catch (InterpolationException e) {
                    return e;
                }
            }
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Unable to fully structure code
         */
        private void traverseObjectWithParents(Class cls, InterpolationTarget target) throws InterpolationException {
            block18: {
                block17: {
                    obj = InterpolationTarget.access$100(target);
                    basePath = InterpolationTarget.access$200(target);
                    if (cls == null) {
                        return;
                    }
                    if (!cls.isArray()) break block17;
                    this.evaluateArray(obj, basePath);
                    break block18;
                }
                if (!this.isQualifiedForInterpolation(cls)) break block18;
                fields = (Field[])FieldBasedObjectInterpolator.access$300().get(cls);
                if (fields == null) {
                    fields = cls.getDeclaredFields();
                    FieldBasedObjectInterpolator.access$300().put(cls, fields);
                }
                for (Field field : fields) {
                    type = field.getType();
                    if (!this.isQualifiedForInterpolation(field, type)) continue;
                    isAccessible = field.isAccessible();
                    var12_12 = cls;
                    synchronized (var12_12) {
                        block16: {
                            block15: {
                                field.setAccessible(true);
                                if (String.class == type) {
                                    this.interpolateString(obj, field);
                                }
                                if (!Collection.class.isAssignableFrom(type)) break block15;
                                if (!this.interpolateCollection(obj, basePath, field)) ** GOTO lbl52
                                field.setAccessible(isAccessible);
                                continue;
                            }
                            try {
                                try {
                                    if (Map.class.isAssignableFrom(type)) {
                                        this.interpolateMap(obj, basePath, field);
                                        break block16;
                                    }
                                    this.interpolateObject(obj, basePath, field);
                                }
                                catch (IllegalArgumentException e) {
                                    this.warningCollector.add(new ObjectInterpolationWarning("Failed to interpolate field. Skipping.", basePath + "." + field.getName(), e));
                                }
                                catch (IllegalAccessException e) {
                                    this.warningCollector.add(new ObjectInterpolationWarning("Failed to interpolate field. Skipping.", basePath + "." + field.getName(), e));
                                }
                            }
                            catch (Throwable var14_16) {
                                throw var14_16;
                            }
                            finally {
                                field.setAccessible(isAccessible);
                            }
                        }
                    }
                }
                this.traverseObjectWithParents(cls.getSuperclass(), target);
            }
        }

        private void interpolateObject(Object obj, String basePath, Field field2) throws IllegalAccessException, InterpolationException {
            Object value = field2.get(obj);
            if (value != null) {
                if (field2.getType().isArray()) {
                    this.evaluateArray(value, basePath + "." + field2.getName());
                } else {
                    this.interpolationTargets.add(new InterpolationTarget(value, basePath + "." + field2.getName()));
                }
            }
        }

        private void interpolateMap(Object obj, String basePath, Field field2) throws IllegalAccessException, InterpolationException {
            Map m4 = (Map)field2.get(obj);
            if (m4 != null && !m4.isEmpty()) {
                for (Map.Entry entry : m4.entrySet()) {
                    Map.Entry entry2 = entry;
                    Object value = entry2.getValue();
                    if (value == null) continue;
                    if (String.class == value.getClass()) {
                        String interpolated = this.interpolator.interpolate((String)value, this.recursionInterceptor);
                        if (interpolated.equals(value)) continue;
                        try {
                            entry2.setValue(interpolated);
                        }
                        catch (UnsupportedOperationException e) {
                            this.warningCollector.add(new ObjectInterpolationWarning("Field is an unmodifiable collection. Skipping interpolation.", basePath + "." + field2.getName(), e));
                        }
                        continue;
                    }
                    if (value.getClass().isArray()) {
                        this.evaluateArray(value, basePath + "." + field2.getName());
                        continue;
                    }
                    this.interpolationTargets.add(new InterpolationTarget(value, basePath + "." + field2.getName()));
                }
            }
        }

        private boolean interpolateCollection(Object obj, String basePath, Field field2) throws IllegalAccessException, InterpolationException {
            Collection c = (Collection)field2.get(obj);
            if (c != null && !c.isEmpty()) {
                ArrayList originalValues = new ArrayList(c);
                try {
                    c.clear();
                }
                catch (UnsupportedOperationException e) {
                    this.warningCollector.add(new ObjectInterpolationWarning("Field is an unmodifiable collection. Skipping interpolation.", basePath + "." + field2.getName(), e));
                    return true;
                }
                for (Object value : originalValues) {
                    if (value != null) {
                        if (String.class == value.getClass()) {
                            String interpolated = this.interpolator.interpolate((String)value, this.recursionInterceptor);
                            if (!interpolated.equals(value)) {
                                c.add(interpolated);
                                continue;
                            }
                            c.add(value);
                            continue;
                        }
                        c.add(value);
                        if (value.getClass().isArray()) {
                            this.evaluateArray(value, basePath + "." + field2.getName());
                            continue;
                        }
                        this.interpolationTargets.add(new InterpolationTarget(value, basePath + "." + field2.getName()));
                        continue;
                    }
                    c.add(value);
                }
            }
            return false;
        }

        private void interpolateString(Object obj, Field field2) throws IllegalAccessException, InterpolationException {
            String interpolated;
            String value = (String)field2.get(obj);
            if (value != null && !(interpolated = this.interpolator.interpolate(value, this.recursionInterceptor)).equals(value)) {
                field2.set(obj, interpolated);
            }
        }

        private boolean isQualifiedForInterpolation(Class cls) {
            String pkgName = cls.getPackage().getName();
            for (String prefix : this.blacklistedPackagePrefixes) {
                if (!pkgName.startsWith(prefix)) continue;
                return false;
            }
            return true;
        }

        private boolean isQualifiedForInterpolation(Field field2, Class fieldType) {
            if (!fieldIsPrimitiveByClass.containsKey(fieldType)) {
                fieldIsPrimitiveByClass.put(fieldType, fieldType.isPrimitive());
            }
            if (((Boolean)fieldIsPrimitiveByClass.get(fieldType)).booleanValue()) {
                return false;
            }
            return !this.blacklistedFieldNames.contains(field2.getName());
        }

        private void evaluateArray(Object target, String basePath) throws InterpolationException {
            int len = Array.getLength(target);
            for (int i = 0; i < len; ++i) {
                Object value = Array.get(target, i);
                if (value == null) continue;
                if (String.class == value.getClass()) {
                    String interpolated = this.interpolator.interpolate((String)value, this.recursionInterceptor);
                    if (interpolated.equals(value)) continue;
                    Array.set(target, i, interpolated);
                    continue;
                }
                this.interpolationTargets.add(new InterpolationTarget(value, basePath + "[" + i + "]"));
            }
        }
    }
}

