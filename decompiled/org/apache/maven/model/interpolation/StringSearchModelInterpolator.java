/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.interpolation;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.interpolation.AbstractStringBasedModelInterpolator;
import org.apache.maven.model.interpolation.ModelInterpolator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.interpolation.InterpolationPostProcessor;
import org.codehaus.plexus.interpolation.Interpolator;
import org.codehaus.plexus.interpolation.StringSearchInterpolator;
import org.codehaus.plexus.interpolation.ValueSource;

@Component(role=ModelInterpolator.class)
public class StringSearchModelInterpolator
extends AbstractStringBasedModelInterpolator {
    private static final Map<Class<?>, InterpolateObjectAction.CacheItem> CACHED_ENTRIES = new ConcurrentHashMap(80, 0.75f, 2);

    @Override
    public Model interpolateModel(Model model, File projectDir, ModelBuildingRequest config, ModelProblemCollector problems) {
        this.interpolateObject(model, model, projectDir, config, problems);
        return model;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void interpolateObject(Object obj, Model model, File projectDir, ModelBuildingRequest config, ModelProblemCollector problems) {
        try {
            List<ValueSource> valueSources = this.createValueSources(model, projectDir, config, problems);
            List<? extends InterpolationPostProcessor> postProcessors = this.createPostProcessors(model, projectDir, config);
            InterpolateObjectAction action = new InterpolateObjectAction(obj, valueSources, postProcessors, this, problems);
            AccessController.doPrivileged(action);
        }
        finally {
            this.getInterpolator().clearAnswers();
        }
    }

    @Override
    protected Interpolator createInterpolator() {
        StringSearchInterpolator interpolator = new StringSearchInterpolator();
        interpolator.setCacheAnswers(true);
        return interpolator;
    }

    private static final class InterpolateObjectAction
    implements PrivilegedAction<Object> {
        private final LinkedList<Object> interpolationTargets;
        private final StringSearchModelInterpolator modelInterpolator;
        private final List<? extends ValueSource> valueSources;
        private final List<? extends InterpolationPostProcessor> postProcessors;
        private final ModelProblemCollector problems;

        InterpolateObjectAction(Object target, List<? extends ValueSource> valueSources, List<? extends InterpolationPostProcessor> postProcessors, StringSearchModelInterpolator modelInterpolator, ModelProblemCollector problems) {
            this.valueSources = valueSources;
            this.postProcessors = postProcessors;
            this.interpolationTargets = new LinkedList();
            this.interpolationTargets.add(target);
            this.modelInterpolator = modelInterpolator;
            this.problems = problems;
        }

        @Override
        public Object run() {
            while (!this.interpolationTargets.isEmpty()) {
                Object obj = this.interpolationTargets.removeFirst();
                this.traverseObjectWithParents(obj.getClass(), obj);
            }
            return null;
        }

        private String interpolate(String value) {
            return this.modelInterpolator.interpolateInternal(value, this.valueSources, this.postProcessors, this.problems);
        }

        private void traverseObjectWithParents(Class<?> cls, Object target) {
            if (cls == null) {
                return;
            }
            CacheItem cacheEntry = this.getCacheEntry(cls);
            if (cacheEntry.isArray()) {
                InterpolateObjectAction.evaluateArray(target, this);
            } else if (cacheEntry.isQualifiedForInterpolation) {
                cacheEntry.interpolate(target, this);
                this.traverseObjectWithParents(cls.getSuperclass(), target);
            }
        }

        private CacheItem getCacheEntry(Class<?> cls) {
            CacheItem cacheItem = (CacheItem)CACHED_ENTRIES.get(cls);
            if (cacheItem == null) {
                cacheItem = new CacheItem(cls);
                CACHED_ENTRIES.put(cls, cacheItem);
            }
            return cacheItem;
        }

        private static void evaluateArray(Object target, InterpolateObjectAction ctx) {
            int len = Array.getLength(target);
            for (int i = 0; i < len; ++i) {
                Object value = Array.get(target, i);
                if (value == null) continue;
                if (String.class == value.getClass()) {
                    String interpolated = ctx.interpolate((String)value);
                    if (interpolated.equals(value)) continue;
                    Array.set(target, i, interpolated);
                    continue;
                }
                ctx.interpolationTargets.add(value);
            }
        }

        static final class ObjectField
        extends CacheField {
            private final boolean isArray;

            ObjectField(Field field2) {
                super(field2);
                this.isArray = field2.getType().isArray();
            }

            @Override
            void doInterpolate(Object target, InterpolateObjectAction ctx) throws IllegalAccessException {
                Object value = this.field.get(target);
                if (value != null) {
                    if (this.isArray) {
                        InterpolateObjectAction.evaluateArray(value, ctx);
                    } else {
                        ctx.interpolationTargets.add(value);
                    }
                }
            }
        }

        static final class MapField
        extends CacheField {
            MapField(Field field2) {
                super(field2);
            }

            @Override
            void doInterpolate(Object target, InterpolateObjectAction ctx) throws IllegalAccessException {
                Map m4 = (Map)this.field.get(target);
                if (m4 == null || m4.isEmpty()) {
                    return;
                }
                for (Map.Entry entry : m4.entrySet()) {
                    Object value = entry.getValue();
                    if (value == null) continue;
                    if (String.class == value.getClass()) {
                        String interpolated = ctx.interpolate((String)value);
                        if (interpolated.equals(value)) continue;
                        try {
                            entry.setValue(interpolated);
                        }
                        catch (UnsupportedOperationException unsupportedOperationException) {}
                        continue;
                    }
                    if (value.getClass().isArray()) {
                        InterpolateObjectAction.evaluateArray(value, ctx);
                        continue;
                    }
                    ctx.interpolationTargets.add(value);
                }
            }
        }

        static final class ListField
        extends CacheField {
            ListField(Field field2) {
                super(field2);
            }

            @Override
            void doInterpolate(Object target, InterpolateObjectAction ctx) throws IllegalAccessException {
                List c = (List)this.field.get(target);
                if (c == null) {
                    return;
                }
                int size = c.size();
                for (int i = 0; i < size; ++i) {
                    Object value = c.get(i);
                    if (value == null) continue;
                    if (String.class == value.getClass()) {
                        String interpolated = ctx.interpolate((String)value);
                        if (interpolated.equals(value)) continue;
                        try {
                            c.set(i, interpolated);
                            continue;
                        }
                        catch (UnsupportedOperationException e) {
                            return;
                        }
                    }
                    if (value.getClass().isArray()) {
                        InterpolateObjectAction.evaluateArray(value, ctx);
                        continue;
                    }
                    ctx.interpolationTargets.add(value);
                }
            }
        }

        static final class StringField
        extends CacheField {
            StringField(Field field2) {
                super(field2);
            }

            @Override
            void doInterpolate(Object target, InterpolateObjectAction ctx) throws IllegalAccessException {
                String value = (String)this.field.get(target);
                if (value == null) {
                    return;
                }
                String interpolated = ctx.interpolate(value);
                if (!interpolated.equals(value)) {
                    this.field.set(target, interpolated);
                }
            }
        }

        static abstract class CacheField {
            protected final Field field;

            CacheField(Field field2) {
                this.field = field2;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            void interpolate(Object target, InterpolateObjectAction interpolateObjectAction) {
                Field field2 = this.field;
                synchronized (field2) {
                    boolean isAccessible = this.field.isAccessible();
                    this.field.setAccessible(true);
                    try {
                        this.doInterpolate(target, interpolateObjectAction);
                    }
                    catch (IllegalArgumentException e) {
                        interpolateObjectAction.problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage("Failed to interpolate field3: " + this.field + " on class: " + this.field.getType().getName()).setException(e));
                    }
                    catch (IllegalAccessException e) {
                        interpolateObjectAction.problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage("Failed to interpolate field4: " + this.field + " on class: " + this.field.getType().getName()).setException(e));
                    }
                    finally {
                        this.field.setAccessible(isAccessible);
                    }
                }
            }

            abstract void doInterpolate(Object var1, InterpolateObjectAction var2) throws IllegalAccessException;
        }

        private static class CacheItem {
            private final boolean isArray;
            private final boolean isQualifiedForInterpolation;
            private final CacheField[] fields;

            private boolean isQualifiedForInterpolation(Class<?> cls) {
                return !cls.getName().startsWith("java");
            }

            private boolean isQualifiedForInterpolation(Field field2, Class<?> fieldType) {
                if (Map.class.equals(fieldType) && "locations".equals(field2.getName())) {
                    return false;
                }
                if (fieldType.isPrimitive()) {
                    return false;
                }
                return !"parent".equals(field2.getName());
            }

            CacheItem(Class clazz) {
                this.isQualifiedForInterpolation = this.isQualifiedForInterpolation(clazz);
                this.isArray = clazz.isArray();
                ArrayList<CacheField> fields = new ArrayList<CacheField>();
                for (Field currentField : clazz.getDeclaredFields()) {
                    Class<?> type = currentField.getType();
                    if (!this.isQualifiedForInterpolation(currentField, type)) continue;
                    if (String.class == type) {
                        if (Modifier.isFinal(currentField.getModifiers())) continue;
                        fields.add(new StringField(currentField));
                        continue;
                    }
                    if (List.class.isAssignableFrom(type)) {
                        fields.add(new ListField(currentField));
                        continue;
                    }
                    if (Collection.class.isAssignableFrom(type)) {
                        throw new RuntimeException("We dont interpolate into collections, use a list instead");
                    }
                    if (Map.class.isAssignableFrom(type)) {
                        fields.add(new MapField(currentField));
                        continue;
                    }
                    fields.add(new ObjectField(currentField));
                }
                this.fields = fields.toArray(new CacheField[0]);
            }

            public void interpolate(Object target, InterpolateObjectAction interpolateObjectAction) {
                for (CacheField field2 : this.fields) {
                    field2.interpolate(target, interpolateObjectAction);
                }
            }

            public boolean isArray() {
                return this.isArray;
            }
        }
    }
}

