/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.AttributeException;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.Collector;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Criteria;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.ElementException;
import org.simpleframework.xml.core.Instance;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.ObjectFactory;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.Primitive;
import org.simpleframework.xml.core.Revision;
import org.simpleframework.xml.core.Schema;
import org.simpleframework.xml.core.Section;
import org.simpleframework.xml.core.TextException;
import org.simpleframework.xml.core.ValueRequiredException;
import org.simpleframework.xml.core.Variable;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Position;

class Composite
implements Converter {
    private final ObjectFactory factory;
    private final Primitive primitive;
    private final Criteria criteria;
    private final Revision revision;
    private final Context context;
    private final Type type;

    public Composite(Context context, Type type) {
        this(context, type, null);
    }

    public Composite(Context context, Type type, Class override) {
        this.factory = new ObjectFactory(context, type, override);
        this.primitive = new Primitive(context, type);
        this.criteria = new Collector();
        this.revision = new Revision();
        this.context = context;
        this.type = type;
    }

    @Override
    public Object read(InputNode node) throws Exception {
        Instance value = this.factory.getInstance(node);
        Class type = value.getType();
        if (value.isReference()) {
            return value.getInstance();
        }
        if (this.context.isPrimitive(type)) {
            return this.readPrimitive(node, value);
        }
        return this.read(node, value, type);
    }

    @Override
    public Object read(InputNode node, Object source2) throws Exception {
        Class<?> type = source2.getClass();
        Schema schema = this.context.getSchema(type);
        Caller caller = schema.getCaller();
        this.read(node, source2, schema);
        this.criteria.commit(source2);
        caller.validate(source2);
        caller.commit(source2);
        return this.readResolve(node, source2, caller);
    }

    private Object read(InputNode node, Instance value, Class real) throws Exception {
        Schema schema = this.context.getSchema(real);
        Caller caller = schema.getCaller();
        Builder builder = this.read(schema, value);
        Object source2 = builder.read(node);
        caller.validate(source2);
        caller.commit(source2);
        value.setInstance(source2);
        return this.readResolve(node, source2, caller);
    }

    private Builder read(Schema schema, Instance value) throws Exception {
        Instantiator creator = schema.getInstantiator();
        if (creator.isDefault()) {
            return new Builder(this, this.criteria, schema, value);
        }
        return new Injector(this, this.criteria, schema, value);
    }

    private Object readPrimitive(InputNode node, Instance value) throws Exception {
        Class type = value.getType();
        Object result = this.primitive.read(node, type);
        if (type != null) {
            value.setInstance(result);
        }
        return result;
    }

    private Object readResolve(InputNode node, Object source2, Caller caller) throws Exception {
        if (source2 != null) {
            Class<?> real;
            Position line = node.getPosition();
            Object value = caller.resolve(source2);
            Class expect = this.type.getType();
            if (!expect.isAssignableFrom(real = value.getClass())) {
                throw new ElementException("Type %s does not match %s at %s", real, expect, line);
            }
            return value;
        }
        return source2;
    }

    private void read(InputNode node, Object source2, Schema schema) throws Exception {
        Section section = schema.getSection();
        this.readVersion(node, source2, schema);
        this.readSection(node, source2, section);
    }

    private void readSection(InputNode node, Object source2, Section section) throws Exception {
        this.readText(node, source2, section);
        this.readAttributes(node, source2, section);
        this.readElements(node, source2, section);
    }

    private void readVersion(InputNode node, Object source2, Schema schema) throws Exception {
        Label label = schema.getVersion();
        Class expect = this.type.getType();
        if (label != null) {
            String name = label.getName();
            NodeMap<InputNode> map = node.getAttributes();
            InputNode value = map.remove(name);
            if (value != null) {
                this.readVersion(value, source2, label);
            } else {
                Version version = this.context.getVersion(expect);
                Double start2 = this.revision.getDefault();
                Double expected = version.revision();
                this.criteria.set(label, start2);
                this.revision.compare(expected, start2);
            }
        }
    }

    private void readVersion(InputNode node, Object source2, Label label) throws Exception {
        Object value = this.readInstance(node, source2, label);
        Class expect = this.type.getType();
        if (value != null) {
            Version version = this.context.getVersion(expect);
            Double actual = version.revision();
            if (!value.equals(this.revision)) {
                this.revision.compare(actual, value);
            }
        }
    }

    private void readAttributes(InputNode node, Object source2, Section section) throws Exception {
        NodeMap<InputNode> list = node.getAttributes();
        LabelMap map = section.getAttributes();
        for (String name : list) {
            InputNode value = node.getAttribute(name);
            if (value == null) continue;
            this.readAttribute(value, source2, section, map);
        }
        this.validate(node, map, source2);
    }

    private void readElements(InputNode node, Object source2, Section section) throws Exception {
        LabelMap map = section.getElements();
        InputNode child = node.getNext();
        while (child != null) {
            String name = child.getName();
            Section block = section.getSection(name);
            if (block != null) {
                this.readSection(child, source2, block);
            } else {
                this.readElement(child, source2, section, map);
            }
            child = node.getNext();
        }
        this.validate(node, map, source2);
    }

    private void readText(InputNode node, Object source2, Section section) throws Exception {
        Label label = section.getText();
        if (label != null) {
            this.readInstance(node, source2, label);
        }
    }

    private void readAttribute(InputNode node, Object source2, Section section, LabelMap map) throws Exception {
        String name = node.getName();
        String path = section.getAttribute(name);
        Label label = map.getLabel(path);
        if (label == null) {
            Position line = node.getPosition();
            Class expect = this.context.getType(this.type, source2);
            if (map.isStrict(this.context) && this.revision.isEqual()) {
                throw new AttributeException("Attribute '%s' does not have a match in %s at %s", path, expect, line);
            }
        } else {
            this.readInstance(node, source2, label);
        }
    }

    private void readElement(InputNode node, Object source2, Section section, LabelMap map) throws Exception {
        String name = node.getName();
        String path = section.getPath(name);
        Label label = map.getLabel(path);
        if (label == null) {
            label = this.criteria.resolve(path);
        }
        if (label == null) {
            Position line = node.getPosition();
            Class expect = this.context.getType(this.type, source2);
            if (map.isStrict(this.context) && this.revision.isEqual()) {
                throw new ElementException("Element '%s' does not have a match in %s at %s", path, expect, line);
            }
            node.skip();
        } else {
            this.readUnion(node, source2, map, label);
        }
    }

    private void readUnion(InputNode node, Object source2, LabelMap map, Label label) throws Exception {
        String[] list;
        Object value = this.readInstance(node, source2, label);
        for (String key : list = label.getPaths()) {
            map.getLabel(key);
        }
        if (label.isInline()) {
            this.criteria.set(label, value);
        }
    }

    private Object readInstance(InputNode node, Object source2, Label label) throws Exception {
        Object object = this.readVariable(node, source2, label);
        if (object == null) {
            Position line = node.getPosition();
            Class expect = this.context.getType(this.type, source2);
            if (label.isRequired() && this.revision.isEqual()) {
                throw new ValueRequiredException("Empty value for %s in %s at %s", label, expect, line);
            }
        } else if (object != label.getEmpty(this.context)) {
            this.criteria.set(label, object);
        }
        return object;
    }

    private Object readVariable(InputNode node, Object source2, Label label) throws Exception {
        Converter reader = label.getConverter(this.context);
        if (label.isCollection()) {
            Object value;
            Variable variable = this.criteria.get(label);
            Contact contact = label.getContact();
            if (variable != null) {
                Object value2 = variable.getValue();
                return reader.read(node, value2);
            }
            if (source2 != null && (value = contact.get(source2)) != null) {
                return reader.read(node, value);
            }
        }
        return reader.read(node);
    }

    private void validate(InputNode node, LabelMap map, Object source2) throws Exception {
        Class expect = this.context.getType(this.type, source2);
        Position line = node.getPosition();
        for (Label label : map) {
            if (label.isRequired() && this.revision.isEqual()) {
                throw new ValueRequiredException("Unable to satisfy %s for %s at %s", label, expect, line);
            }
            Object value = label.getEmpty(this.context);
            if (value == null) continue;
            this.criteria.set(label, value);
        }
    }

    @Override
    public boolean validate(InputNode node) throws Exception {
        Instance value = this.factory.getInstance(node);
        if (!value.isReference()) {
            Object result = value.setInstance(null);
            Class type = value.getType();
            return this.validate(node, type);
        }
        return true;
    }

    private boolean validate(InputNode node, Class type) throws Exception {
        Schema schema = this.context.getSchema(type);
        Section section = schema.getSection();
        this.validateText(node, schema);
        this.validateSection(node, section);
        return node.isElement();
    }

    private void validateSection(InputNode node, Section section) throws Exception {
        this.validateAttributes(node, section);
        this.validateElements(node, section);
    }

    private void validateAttributes(InputNode node, Section section) throws Exception {
        NodeMap<InputNode> list = node.getAttributes();
        LabelMap map = section.getAttributes();
        for (String name : list) {
            InputNode value = node.getAttribute(name);
            if (value == null) continue;
            this.validateAttribute(value, section, map);
        }
        this.validate(node, map);
    }

    private void validateElements(InputNode node, Section section) throws Exception {
        LabelMap map = section.getElements();
        InputNode next = node.getNext();
        while (next != null) {
            String name = next.getName();
            Section child = section.getSection(name);
            if (child != null) {
                this.validateSection(next, child);
            } else {
                this.validateElement(next, section, map);
            }
            next = node.getNext();
        }
        this.validate(node, map);
    }

    private void validateText(InputNode node, Schema schema) throws Exception {
        Label label = schema.getText();
        if (label != null) {
            this.validate(node, label);
        }
    }

    private void validateAttribute(InputNode node, Section section, LabelMap map) throws Exception {
        Position line = node.getPosition();
        String name = node.getName();
        String path = section.getAttribute(name);
        Label label = map.getLabel(path);
        if (label == null) {
            Class expect = this.type.getType();
            if (map.isStrict(this.context) && this.revision.isEqual()) {
                throw new AttributeException("Attribute '%s' does not exist for %s at %s", path, expect, line);
            }
        } else {
            this.validate(node, label);
        }
    }

    private void validateElement(InputNode node, Section section, LabelMap map) throws Exception {
        String name = node.getName();
        String path = section.getPath(name);
        Label label = map.getLabel(path);
        if (label == null) {
            label = this.criteria.resolve(path);
        }
        if (label == null) {
            Position line = node.getPosition();
            Class expect = this.type.getType();
            if (map.isStrict(this.context) && this.revision.isEqual()) {
                throw new ElementException("Element '%s' does not exist for %s at %s", path, expect, line);
            }
            node.skip();
        } else {
            this.validateUnion(node, map, label);
        }
    }

    private void validateUnion(InputNode node, LabelMap map, Label label) throws Exception {
        String[] list;
        for (String key : list = label.getPaths()) {
            map.getLabel(key);
        }
        if (label.isInline()) {
            this.criteria.set(label, null);
        }
        this.validate(node, label);
    }

    private void validate(InputNode node, Label label) throws Exception {
        Converter reader = label.getConverter(this.context);
        Position line = node.getPosition();
        Class expect = this.type.getType();
        boolean valid = reader.validate(node);
        if (!valid) {
            throw new PersistenceException("Invalid value for %s in %s at %s", label, expect, line);
        }
        this.criteria.set(label, null);
    }

    private void validate(InputNode node, LabelMap map) throws Exception {
        Position line = node.getPosition();
        for (Label label : map) {
            Class expect = this.type.getType();
            if (!label.isRequired() || !this.revision.isEqual()) continue;
            throw new ValueRequiredException("Unable to satisfy %s for %s at %s", label, expect, line);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(OutputNode node, Object source2) throws Exception {
        Class<?> type = source2.getClass();
        Schema schema = this.context.getSchema(type);
        Caller caller = schema.getCaller();
        try {
            if (schema.isPrimitive()) {
                this.primitive.write(node, source2);
            } else {
                caller.persist(source2);
                this.write(node, source2, schema);
            }
        }
        finally {
            caller.complete(source2);
        }
    }

    private void write(OutputNode node, Object source2, Schema schema) throws Exception {
        Section section = schema.getSection();
        this.writeVersion(node, source2, schema);
        this.writeSection(node, source2, section);
    }

    private void writeSection(OutputNode node, Object source2, Section section) throws Exception {
        NamespaceMap scope = node.getNamespaces();
        String prefix = section.getPrefix();
        if (prefix != null) {
            String reference = scope.getReference(prefix);
            if (reference == null) {
                throw new ElementException("Namespace prefix '%s' in %s is not in scope", prefix, this.type);
            }
            node.setReference(reference);
        }
        this.writeAttributes(node, source2, section);
        this.writeElements(node, source2, section);
        this.writeText(node, source2, section);
    }

    private void writeVersion(OutputNode node, Object source2, Schema schema) throws Exception {
        Version version = schema.getRevision();
        Label label = schema.getVersion();
        if (version != null) {
            Double start2 = this.revision.getDefault();
            Double value = version.revision();
            if (this.revision.compare(value, start2)) {
                if (label.isRequired()) {
                    this.writeAttribute(node, value, label);
                }
            } else {
                this.writeAttribute(node, value, label);
            }
        }
    }

    private void writeAttributes(OutputNode node, Object source2, Section section) throws Exception {
        LabelMap attributes = section.getAttributes();
        for (Label label : attributes) {
            Contact contact = label.getContact();
            Object value = contact.get(source2);
            Class expect = this.context.getType(this.type, source2);
            if (value == null) {
                value = label.getEmpty(this.context);
            }
            if (value == null && label.isRequired()) {
                throw new AttributeException("Value for %s is null in %s", label, expect);
            }
            this.writeAttribute(node, value, label);
        }
    }

    private void writeElements(OutputNode node, Object source2, Section section) throws Exception {
        for (String name : section) {
            Section child = section.getSection(name);
            if (child != null) {
                OutputNode next = node.getChild(name);
                this.writeSection(next, source2, child);
                continue;
            }
            String path = section.getPath(name);
            Label label = section.getElement(path);
            Class expect = this.context.getType(this.type, source2);
            Variable value = this.criteria.get(label);
            if (value != null) continue;
            if (label == null) {
                throw new ElementException("Element '%s' not defined in %s", name, expect);
            }
            this.writeUnion(node, source2, section, label);
        }
    }

    private void writeUnion(OutputNode node, Object source2, Section section, Label label) throws Exception {
        Contact contact = label.getContact();
        Object value = contact.get(source2);
        Class expect = this.context.getType(this.type, source2);
        if (value == null && label.isRequired()) {
            throw new ElementException("Value for %s is null in %s", label, expect);
        }
        Object replace = this.writeReplace(value);
        if (replace != null) {
            this.writeElement(node, replace, label);
        }
        this.criteria.set(label, replace);
    }

    private Object writeReplace(Object source2) throws Exception {
        if (source2 != null) {
            Class<?> type = source2.getClass();
            Caller caller = this.context.getCaller(type);
            return caller.replace(source2);
        }
        return source2;
    }

    private void writeText(OutputNode node, Object source2, Section section) throws Exception {
        Label label = section.getText();
        if (label != null) {
            Contact contact = label.getContact();
            Object value = contact.get(source2);
            Class expect = this.context.getType(this.type, source2);
            if (value == null) {
                value = label.getEmpty(this.context);
            }
            if (value == null && label.isRequired()) {
                throw new TextException("Value for %s is null in %s", label, expect);
            }
            this.writeText(node, value, label);
        }
    }

    private void writeAttribute(OutputNode node, Object value, Label label) throws Exception {
        if (value != null) {
            Decorator decorator = label.getDecorator();
            String name = label.getName();
            String text = this.factory.getText(value);
            OutputNode done = node.setAttribute(name, text);
            decorator.decorate(done);
        }
    }

    private void writeElement(OutputNode node, Object value, Label label) throws Exception {
        if (value != null) {
            Class<?> real = value.getClass();
            Label match = label.getLabel(real);
            String name = match.getName();
            Type type = label.getType(real);
            OutputNode next = node.getChild(name);
            if (!match.isInline()) {
                this.writeNamespaces(next, type, match);
            }
            if (match.isInline() || !this.isOverridden(next, value, type)) {
                Converter convert = match.getConverter(this.context);
                boolean data = match.isData();
                next.setData(data);
                this.writeElement(next, value, convert);
            }
        }
    }

    private void writeElement(OutputNode node, Object value, Converter convert) throws Exception {
        convert.write(node, value);
    }

    private void writeNamespaces(OutputNode node, Type type, Label label) throws Exception {
        Class expect = type.getType();
        Decorator primary = this.context.getDecorator(expect);
        Decorator decorator = label.getDecorator();
        decorator.decorate(node, primary);
    }

    private void writeText(OutputNode node, Object value, Label label) throws Exception {
        if (value != null && !label.isTextList()) {
            String text = this.factory.getText(value);
            boolean data = label.isData();
            node.setData(data);
            node.setValue(text);
        }
    }

    private boolean isOverridden(OutputNode node, Object value, Type type) throws Exception {
        return this.factory.setOverride(type, value, node);
    }

    private class Injector
    extends Builder {
        private Injector(Composite composite2, Criteria criteria, Schema schema, Instance value) {
            super(composite2, criteria, schema, value);
        }

        @Override
        public Object read(InputNode node) throws Exception {
            Section section = this.schema.getSection();
            this.composite.readVersion(node, null, this.schema);
            this.composite.readText(node, null, section);
            this.composite.readAttributes(node, null, section);
            this.composite.readElements(node, null, section);
            return this.readInject(node);
        }

        private Object readInject(InputNode node) throws Exception {
            Instantiator creator = this.schema.getInstantiator();
            Object source2 = creator.getInstance(this.criteria);
            this.value.setInstance(source2);
            this.criteria.commit(source2);
            return source2;
        }
    }

    private static class Builder {
        protected final Composite composite;
        protected final Criteria criteria;
        protected final Schema schema;
        protected final Instance value;

        public Builder(Composite composite, Criteria criteria, Schema schema, Instance value) {
            this.composite = composite;
            this.criteria = criteria;
            this.schema = schema;
            this.value = value;
        }

        public Object read(InputNode node) throws Exception {
            Object source2 = this.value.getInstance();
            Section section = this.schema.getSection();
            this.value.setInstance(source2);
            this.composite.readVersion(node, source2, this.schema);
            this.composite.readText(node, source2, section);
            this.composite.readAttributes(node, source2, section);
            this.composite.readElements(node, source2, section);
            this.criteria.commit(source2);
            return source2;
        }
    }
}

