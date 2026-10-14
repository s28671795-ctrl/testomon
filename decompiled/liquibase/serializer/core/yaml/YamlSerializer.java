/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.yaml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import liquibase.change.Change;
import liquibase.change.ConstraintsConfig;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RollbackContainer;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.yaml.YamlParser;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.LiquibaseSerializer;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.introspector.GenericProperty;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

public abstract class YamlSerializer
implements LiquibaseSerializer {
    protected Yaml yaml = this.createYaml();

    protected DumperOptions createDumperOptions() {
        DumperOptions dumperOptions = new DumperOptions();
        if (this.isJson()) {
            dumperOptions.setPrettyFlow(true);
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
            dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
            dumperOptions.setWidth(Integer.MAX_VALUE);
        } else {
            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        }
        return dumperOptions;
    }

    protected Yaml createYaml() {
        DumperOptions dumperOptions = this.createDumperOptions();
        return new Yaml((BaseConstructor)new SafeConstructor(YamlParser.createLoaderOptions()), (Representer)this.getLiquibaseRepresenter(dumperOptions), dumperOptions, this.getLiquibaseResolver());
    }

    protected LiquibaseRepresenter getLiquibaseRepresenter(DumperOptions options) {
        return new LiquibaseRepresenter(options);
    }

    protected LiquibaseResolver getLiquibaseResolver() {
        return new LiquibaseResolver();
    }

    protected boolean isJson() {
        return "json".equals(this.getValidFileExtensions()[0]);
    }

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"yaml", "yml"};
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        if (this.isJson()) {
            String out = this.yaml.dumpAs(this.toMap(object), Tag.MAP, DumperOptions.FlowStyle.FLOW);
            return YamlSerializer.removeClassTypeMarksFromSerializedJson(out);
        }
        return this.yaml.dumpAsMap(this.toMap(object));
    }

    protected Set<String> getSerializableObjectFields(LiquibaseSerializable object) {
        return object.getSerializableFields();
    }

    protected Object toMap(LiquibaseSerializable object) {
        Comparator<String> comparator = this.getComparator(object);
        TreeMap objectMap = new TreeMap(comparator);
        for (String field2 : this.getSerializableObjectFields(object)) {
            Object value = object.getSerializableFieldValue(field2);
            if (value == null) continue;
            if (value instanceof DataType) {
                value = ((Map)this.toMap((DataType)value)).values().iterator().next();
            }
            if (value instanceof Column.AutoIncrementInformation) {
                value = ((Map)this.toMap((Column.AutoIncrementInformation)value)).values().iterator().next();
            }
            if (value instanceof ConstraintsConfig) {
                value = ((Map)this.toMap((ConstraintsConfig)value)).values().iterator().next();
            }
            if (value instanceof LiquibaseSerializable) {
                List<Change> changesToRollback;
                value = value instanceof RollbackContainer ? ((changesToRollback = ((RollbackContainer)value).getChanges()).size() == 1 ? this.toMap(changesToRollback.get(0)) : this.toMap((LiquibaseSerializable)value)) : this.toMap((LiquibaseSerializable)value);
            }
            if (value instanceof Collection) {
                ArrayList<Object> valueAsList = new ArrayList<Object>((Collection)value);
                if (valueAsList.isEmpty()) continue;
                for (int i = 0; i < valueAsList.size(); ++i) {
                    if (!(valueAsList.get(i) instanceof LiquibaseSerializable)) continue;
                    Object m4 = this.convertToMap(valueAsList, i);
                    valueAsList.set(i, m4);
                }
                value = valueAsList;
            }
            if (value instanceof Map) {
                if (((Map)value).isEmpty()) continue;
                for (Object key : new HashSet(((Map)value).keySet())) {
                    ArrayList<Object> valueAsList;
                    Object mapValue = ((Map)value).get(key);
                    if (mapValue == null) {
                        ((Map)value).remove(key);
                    }
                    if (mapValue instanceof LiquibaseSerializable) {
                        ((Map)value).put(key, this.toMap((LiquibaseSerializable)mapValue));
                        continue;
                    }
                    if (!(mapValue instanceof Collection) || (valueAsList = new ArrayList<Object>((Collection)mapValue)).isEmpty()) continue;
                    for (int i = 0; i < valueAsList.size(); ++i) {
                        if (!(valueAsList.get(i) instanceof LiquibaseSerializable)) continue;
                        valueAsList.set(i, this.toMap((LiquibaseSerializable)valueAsList.get(i)));
                    }
                    ((Map)value).put(key, valueAsList);
                }
            }
            objectMap.put(field2, value);
        }
        HashMap containerMap = new HashMap();
        containerMap.put(object.getSerializedObjectName(), objectMap);
        return containerMap;
    }

    protected Object convertToMap(List valueAsList, int index) {
        return this.toMap((LiquibaseSerializable)valueAsList.get(index));
    }

    protected Comparator<String> getComparator(LiquibaseSerializable object) {
        return Comparator.naturalOrder();
    }

    public static String removeClassTypeMarksFromSerializedJson(String json) {
        json = json.replaceAll("!!int \"(-?\\d+)\"", "$1");
        json = json.replaceAll("!!bool \"(\\w+)\"", "$1");
        json = json.replaceAll("!!timestamp \"([^\"]*)\"", "$1");
        json = json.replaceAll("!!float \"([^\"]*)\"", "$1");
        json = json.replaceAll("!!liquibase.[^\\s]+ (\"\\w+\")", "$1");
        return json;
    }

    public static class LiquibaseRepresenter
    extends Representer {
        public LiquibaseRepresenter(DumperOptions options) {
            super(options);
            this.init();
        }

        protected void init() {
            this.multiRepresenters.put(DatabaseFunction.class, new AsStringRepresenter());
            this.multiRepresenters.put(SequenceNextValueFunction.class, new AsStringRepresenter());
            this.multiRepresenters.put(SequenceCurrentValueFunction.class, new AsStringRepresenter());
        }

        @Override
        protected Set<Property> getProperties(Class<? extends Object> type) {
            LiquibaseSerializable serializableType;
            HashSet<Property> returnSet;
            block5: {
                returnSet = new HashSet<Property>();
                serializableType = null;
                try {
                    if (type.equals(ChangeSet.class)) {
                        serializableType = new ChangeSet("x", "y", false, false, null, null, null, null);
                        break block5;
                    }
                    if (LiquibaseSerializable.class.isAssignableFrom(type)) {
                        serializableType = (LiquibaseSerializable)type.getConstructor(new Class[0]).newInstance(new Object[0]);
                        break block5;
                    }
                    return super.getProperties(type);
                }
                catch (ReflectiveOperationException e) {
                    throw new UnexpectedLiquibaseException(e);
                }
            }
            for (String property : serializableType.getSerializableFields()) {
                returnSet.add(new LiquibaseProperty(property, String.class, (Type)((Object)String.class)));
            }
            return returnSet;
        }

        private class AsStringRepresenter
        implements Represent {
            private AsStringRepresenter() {
            }

            @Override
            public Node representData(Object data) {
                return LiquibaseRepresenter.this.representScalar(Tag.STR, data.toString());
            }
        }

        private static class LiquibaseProperty
        extends GenericProperty {
            private LiquibaseProperty(String name, Class<?> aClass, Type aType) {
                super(name, aClass, aType);
            }

            @Override
            public void set(Object object, Object value) throws Exception {
            }

            @Override
            public Object get(Object object) {
                return ((LiquibaseSerializable)object).getSerializableFieldValue(this.getName());
            }

            @Override
            public List<Annotation> getAnnotations() {
                throw new UnsupportedOperationException();
            }

            @Override
            public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
                throw new UnsupportedOperationException();
            }
        }
    }

    public static class LiquibaseResolver
    extends Resolver {
        @Override
        protected void addImplicitResolvers() {
            this.addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
            this.addImplicitResolver(Tag.INT, INT, "-+0123456789");
            this.addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
            this.addImplicitResolver(Tag.MERGE, MERGE, "<");
            this.addImplicitResolver(Tag.NULL, NULL, "~nN\u0000");
            this.addImplicitResolver(Tag.NULL, EMPTY, null);
            this.addImplicitResolver(Tag.YAML, YAML, "!&*");
        }
    }
}

