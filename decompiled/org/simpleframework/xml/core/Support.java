/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.ContactList;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.DetailExtractor;
import org.simpleframework.xml.core.EmptyMatcher;
import org.simpleframework.xml.core.Instance;
import org.simpleframework.xml.core.InstanceFactory;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelExtractor;
import org.simpleframework.xml.core.Reflector;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.ScannerFactory;
import org.simpleframework.xml.filter.Filter;
import org.simpleframework.xml.filter.PlatformFilter;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;
import org.simpleframework.xml.transform.Transformer;

class Support
implements Filter {
    private final InstanceFactory instances;
    private final ScannerFactory scanners;
    private final DetailExtractor defaults = new DetailExtractor(this, DefaultType.FIELD);
    private final DetailExtractor details;
    private final LabelExtractor labels;
    private final Transformer transform;
    private final Matcher matcher;
    private final Filter filter;
    private final Format format;

    public Support() {
        this(new PlatformFilter());
    }

    public Support(Filter filter2) {
        this(filter2, new EmptyMatcher());
    }

    public Support(Filter filter2, Matcher matcher) {
        this(filter2, matcher, new Format());
    }

    public Support(Filter filter2, Matcher matcher, Format format) {
        this.transform = new Transformer(matcher);
        this.scanners = new ScannerFactory(this);
        this.details = new DetailExtractor(this);
        this.labels = new LabelExtractor(format);
        this.instances = new InstanceFactory();
        this.matcher = matcher;
        this.filter = filter2;
        this.format = format;
    }

    @Override
    public String replace(String text) {
        return this.filter.replace(text);
    }

    public Style getStyle() {
        return this.format.getStyle();
    }

    public Format getFormat() {
        return this.format;
    }

    public Instance getInstance(Value value) {
        return this.instances.getInstance(value);
    }

    public Instance getInstance(Class type) {
        return this.instances.getInstance(type);
    }

    public Transform getTransform(Class type) throws Exception {
        return this.matcher.match(type);
    }

    public Label getLabel(Contact contact, Annotation label) throws Exception {
        return this.labels.getLabel(contact, label);
    }

    public List<Label> getLabels(Contact contact, Annotation label) throws Exception {
        return this.labels.getList(contact, label);
    }

    public Detail getDetail(Class type) {
        return this.getDetail(type, null);
    }

    public Detail getDetail(Class type, DefaultType access) {
        if (access != null) {
            return this.defaults.getDetail(type);
        }
        return this.details.getDetail(type);
    }

    public ContactList getFields(Class type) throws Exception {
        return this.getFields(type, null);
    }

    public ContactList getFields(Class type, DefaultType access) throws Exception {
        if (access != null) {
            return this.defaults.getFields(type);
        }
        return this.details.getFields(type);
    }

    public ContactList getMethods(Class type) throws Exception {
        return this.getMethods(type, null);
    }

    public ContactList getMethods(Class type, DefaultType access) throws Exception {
        if (access != null) {
            return this.defaults.getMethods(type);
        }
        return this.details.getMethods(type);
    }

    public Scanner getScanner(Class type) throws Exception {
        return this.scanners.getInstance(type);
    }

    public Object read(String value, Class type) throws Exception {
        return this.transform.read(value, type);
    }

    public String write(Object value, Class type) throws Exception {
        return this.transform.write(value, type);
    }

    public boolean valid(Class type) throws Exception {
        return this.transform.valid(type);
    }

    public String getName(Class type) throws Exception {
        Scanner schema = this.getScanner(type);
        String name = schema.getName();
        if (name != null) {
            return name;
        }
        return this.getClassName(type);
    }

    private String getClassName(Class type) throws Exception {
        if (type.isArray()) {
            type = type.getComponentType();
        }
        String name = type.getSimpleName();
        if (type.isPrimitive()) {
            return name;
        }
        return Reflector.getName(name);
    }

    public boolean isPrimitive(Class type) throws Exception {
        if (type == String.class) {
            return true;
        }
        if (type == Float.class) {
            return true;
        }
        if (type == Double.class) {
            return true;
        }
        if (type == Long.class) {
            return true;
        }
        if (type == Integer.class) {
            return true;
        }
        if (type == Boolean.class) {
            return true;
        }
        if (type.isEnum()) {
            return true;
        }
        if (type.isPrimitive()) {
            return true;
        }
        return this.transform.valid(type);
    }

    public boolean isContainer(Class type) {
        if (Collection.class.isAssignableFrom(type)) {
            return true;
        }
        if (Map.class.isAssignableFrom(type)) {
            return true;
        }
        return type.isArray();
    }

    public static boolean isFloat(Class type) throws Exception {
        if (type == Double.class) {
            return true;
        }
        if (type == Float.class) {
            return true;
        }
        if (type == Float.TYPE) {
            return true;
        }
        return type == Double.TYPE;
    }

    public static boolean isAssignable(Class expect, Class actual) {
        if (expect.isPrimitive()) {
            expect = Support.getPrimitive(expect);
        }
        if (actual.isPrimitive()) {
            actual = Support.getPrimitive(actual);
        }
        return actual.isAssignableFrom(expect);
    }

    public static Class getPrimitive(Class type) {
        if (type == Double.TYPE) {
            return Double.class;
        }
        if (type == Float.TYPE) {
            return Float.class;
        }
        if (type == Integer.TYPE) {
            return Integer.class;
        }
        if (type == Long.TYPE) {
            return Long.class;
        }
        if (type == Boolean.TYPE) {
            return Boolean.class;
        }
        if (type == Character.TYPE) {
            return Character.class;
        }
        if (type == Short.TYPE) {
            return Short.class;
        }
        if (type == Byte.TYPE) {
            return Byte.class;
        }
        return type;
    }
}

