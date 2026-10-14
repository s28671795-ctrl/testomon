/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.FieldDetail;
import org.simpleframework.xml.core.MethodDetail;
import org.simpleframework.xml.core.Reflector;

class DetailScanner
implements Detail {
    private List<MethodDetail> methods = new LinkedList<MethodDetail>();
    private List<FieldDetail> fields = new LinkedList<FieldDetail>();
    private NamespaceList declaration;
    private Namespace namespace;
    private Annotation[] labels;
    private DefaultType override;
    private DefaultType access;
    private Order order;
    private Root root;
    private Class type;
    private String name;
    private boolean required;
    private boolean strict;

    public DetailScanner(Class type) {
        this(type, null);
    }

    public DetailScanner(Class type, DefaultType override) {
        this.labels = type.getDeclaredAnnotations();
        this.override = override;
        this.strict = true;
        this.type = type;
        this.scan(type);
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public boolean isStrict() {
        return this.strict;
    }

    @Override
    public boolean isPrimitive() {
        return this.type.isPrimitive();
    }

    @Override
    public boolean isInstantiable() {
        int modifiers = this.type.getModifiers();
        if (Modifier.isStatic(modifiers)) {
            return true;
        }
        return !this.type.isMemberClass();
    }

    @Override
    public Root getRoot() {
        return this.root;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class getType() {
        return this.type;
    }

    @Override
    public Order getOrder() {
        return this.order;
    }

    @Override
    public DefaultType getOverride() {
        return this.override;
    }

    @Override
    public DefaultType getAccess() {
        if (this.override != null) {
            return this.override;
        }
        return this.access;
    }

    @Override
    public Namespace getNamespace() {
        return this.namespace;
    }

    @Override
    public NamespaceList getNamespaceList() {
        return this.declaration;
    }

    @Override
    public List<MethodDetail> getMethods() {
        return this.methods;
    }

    @Override
    public List<FieldDetail> getFields() {
        return this.fields;
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.labels;
    }

    @Override
    public Constructor[] getConstructors() {
        return this.type.getDeclaredConstructors();
    }

    @Override
    public Class getSuper() {
        Class base = this.type.getSuperclass();
        if (base == Object.class) {
            return null;
        }
        return base;
    }

    private void scan(Class type) {
        this.methods(type);
        this.fields(type);
        this.extract(type);
    }

    private void extract(Class type) {
        for (Annotation label : this.labels) {
            if (label instanceof Namespace) {
                this.namespace(label);
            }
            if (label instanceof NamespaceList) {
                this.scope(label);
            }
            if (label instanceof Root) {
                this.root(label);
            }
            if (label instanceof Order) {
                this.order(label);
            }
            if (!(label instanceof Default)) continue;
            this.access(label);
        }
    }

    private void methods(Class type) {
        Method[] list;
        for (Method method2 : list = type.getDeclaredMethods()) {
            MethodDetail detail = new MethodDetail(method2);
            this.methods.add(detail);
        }
    }

    private void fields(Class type) {
        Field[] list;
        for (Field field2 : list = type.getDeclaredFields()) {
            FieldDetail detail = new FieldDetail(field2);
            this.fields.add(detail);
        }
    }

    private void root(Annotation label) {
        if (label != null) {
            String real;
            Root value = (Root)label;
            String text = real = this.type.getSimpleName();
            if (value != null) {
                text = value.name();
                if (this.isEmpty(text)) {
                    text = Reflector.getName(real);
                }
                this.strict = value.strict();
                this.root = value;
                this.name = text;
            }
        }
    }

    private boolean isEmpty(String value) {
        return value.length() == 0;
    }

    private void order(Annotation label) {
        if (label != null) {
            this.order = (Order)label;
        }
    }

    private void access(Annotation label) {
        if (label != null) {
            Default value = (Default)label;
            this.required = value.required();
            this.access = value.value();
        }
    }

    private void namespace(Annotation label) {
        if (label != null) {
            this.namespace = (Namespace)label;
        }
    }

    private void scope(Annotation label) {
        if (label != null) {
            this.declaration = (NamespaceList)label;
        }
    }

    public String toString() {
        return this.type.toString();
    }
}

