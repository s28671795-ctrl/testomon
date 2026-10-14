/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.ElementMapUnion;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.Transient;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.AnnotationFactory;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.ContactList;
import org.simpleframework.xml.core.ContactMap;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.FieldContact;
import org.simpleframework.xml.core.FieldDetail;
import org.simpleframework.xml.core.Reflector;
import org.simpleframework.xml.core.Support;

class FieldScanner
extends ContactList {
    private final AnnotationFactory factory;
    private final ContactMap done;
    private final Support support;

    public FieldScanner(Detail detail, Support support) throws Exception {
        this.factory = new AnnotationFactory(detail, support);
        this.done = new ContactMap();
        this.support = support;
        this.scan(detail);
    }

    private void scan(Detail detail) throws Exception {
        DefaultType override = detail.getOverride();
        DefaultType access = detail.getAccess();
        Class base = detail.getSuper();
        if (base != null) {
            this.extend(base, override);
        }
        this.extract(detail, access);
        this.extract(detail);
        this.build();
    }

    private void extend(Class base, DefaultType access) throws Exception {
        ContactList list = this.support.getFields(base, access);
        if (list != null) {
            this.addAll(list);
        }
    }

    private void extract(Detail detail) {
        List<FieldDetail> fields = detail.getFields();
        for (FieldDetail entry : fields) {
            Annotation[] list = entry.getAnnotations();
            Field field2 = entry.getField();
            for (Annotation label : list) {
                this.scan(field2, label, list);
            }
        }
    }

    private void extract(Detail detail, DefaultType access) throws Exception {
        List<FieldDetail> fields = detail.getFields();
        if (access == DefaultType.FIELD) {
            for (FieldDetail entry : fields) {
                Annotation[] list = entry.getAnnotations();
                Field field2 = entry.getField();
                Class<?> real = field2.getType();
                if (this.isStatic(field2) || this.isTransient(field2)) continue;
                this.process(field2, real, list);
            }
        }
    }

    private void scan(Field field2, Annotation label, Annotation[] list) {
        if (label instanceof Attribute) {
            this.process(field2, label, list);
        }
        if (label instanceof ElementUnion) {
            this.process(field2, label, list);
        }
        if (label instanceof ElementListUnion) {
            this.process(field2, label, list);
        }
        if (label instanceof ElementMapUnion) {
            this.process(field2, label, list);
        }
        if (label instanceof ElementList) {
            this.process(field2, label, list);
        }
        if (label instanceof ElementArray) {
            this.process(field2, label, list);
        }
        if (label instanceof ElementMap) {
            this.process(field2, label, list);
        }
        if (label instanceof Element) {
            this.process(field2, label, list);
        }
        if (label instanceof Version) {
            this.process(field2, label, list);
        }
        if (label instanceof Text) {
            this.process(field2, label, list);
        }
        if (label instanceof Transient) {
            this.remove(field2, label);
        }
    }

    private void process(Field field2, Class type, Annotation[] list) throws Exception {
        Class[] dependents = Reflector.getDependents(field2);
        Annotation label = this.factory.getInstance(type, dependents);
        if (label != null) {
            this.process(field2, label, list);
        }
    }

    private void process(Field field2, Annotation label, Annotation[] list) {
        FieldContact contact = new FieldContact(field2, label, list);
        FieldKey key = new FieldKey(field2);
        if (!field2.isAccessible()) {
            field2.setAccessible(true);
        }
        this.insert(key, contact);
    }

    private void insert(Object key, Contact contact) {
        Contact existing = (Contact)this.done.remove(key);
        if (existing != null && this.isText(contact)) {
            contact = existing;
        }
        this.done.put(key, contact);
    }

    private boolean isText(Contact contact) {
        Annotation label = contact.getAnnotation();
        return label instanceof Text;
    }

    private void remove(Field field2, Annotation label) {
        this.done.remove(new FieldKey(field2));
    }

    private void build() {
        for (Contact contact : this.done) {
            this.add(contact);
        }
    }

    private boolean isStatic(Field field2) {
        int modifier = field2.getModifiers();
        return Modifier.isStatic(modifier);
    }

    private boolean isTransient(Field field2) {
        int modifier = field2.getModifiers();
        return Modifier.isTransient(modifier);
    }

    private static class FieldKey {
        private final Class type;
        private final String name;

        public FieldKey(Field field2) {
            this.type = field2.getDeclaringClass();
            this.name = field2.getName();
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public boolean equals(Object value) {
            if (value instanceof FieldKey) {
                return this.equals((FieldKey)value);
            }
            return false;
        }

        private boolean equals(FieldKey other) {
            if (other.type != this.type) {
                return false;
            }
            return other.name.equals(this.name);
        }
    }
}

