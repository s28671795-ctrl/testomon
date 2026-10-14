/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.ContactList;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.MethodContact;
import org.simpleframework.xml.core.MethodDetail;
import org.simpleframework.xml.core.MethodException;
import org.simpleframework.xml.core.MethodPart;
import org.simpleframework.xml.core.MethodPartFactory;
import org.simpleframework.xml.core.MethodType;
import org.simpleframework.xml.core.Support;

class MethodScanner
extends ContactList {
    private final MethodPartFactory factory;
    private final Support support;
    private final PartMap write;
    private final PartMap read;
    private final Detail detail;

    public MethodScanner(Detail detail, Support support) throws Exception {
        this.factory = new MethodPartFactory(detail, support);
        this.write = new PartMap();
        this.read = new PartMap();
        this.support = support;
        this.detail = detail;
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
        this.validate();
    }

    private void extend(Class base, DefaultType access) throws Exception {
        ContactList list = this.support.getMethods(base, access);
        for (Contact contact : list) {
            this.process((MethodContact)contact);
        }
    }

    private void extract(Detail detail) throws Exception {
        List<MethodDetail> methods = detail.getMethods();
        for (MethodDetail entry : methods) {
            Annotation[] list = entry.getAnnotations();
            Method method2 = entry.getMethod();
            for (Annotation label : list) {
                this.scan(method2, label, list);
            }
        }
    }

    private void extract(Detail detail, DefaultType access) throws Exception {
        List<MethodDetail> methods = detail.getMethods();
        if (access == DefaultType.PROPERTY) {
            for (MethodDetail entry : methods) {
                Annotation[] list = entry.getAnnotations();
                Method method2 = entry.getMethod();
                Class value = this.factory.getType(method2);
                if (value == null) continue;
                this.process(method2, list);
            }
        }
    }

    private void scan(Method method2, Annotation label, Annotation[] list) throws Exception {
        if (label instanceof Attribute) {
            this.process(method2, label, list);
        }
        if (label instanceof ElementUnion) {
            this.process(method2, label, list);
        }
        if (label instanceof ElementListUnion) {
            this.process(method2, label, list);
        }
        if (label instanceof ElementMapUnion) {
            this.process(method2, label, list);
        }
        if (label instanceof ElementList) {
            this.process(method2, label, list);
        }
        if (label instanceof ElementArray) {
            this.process(method2, label, list);
        }
        if (label instanceof ElementMap) {
            this.process(method2, label, list);
        }
        if (label instanceof Element) {
            this.process(method2, label, list);
        }
        if (label instanceof Version) {
            this.process(method2, label, list);
        }
        if (label instanceof Text) {
            this.process(method2, label, list);
        }
        if (label instanceof Transient) {
            this.remove(method2, label, list);
        }
    }

    private void process(Method method2, Annotation label, Annotation[] list) throws Exception {
        MethodPart part = this.factory.getInstance(method2, label, list);
        MethodType type = part.getMethodType();
        if (type == MethodType.GET) {
            this.process(part, this.read);
        }
        if (type == MethodType.IS) {
            this.process(part, this.read);
        }
        if (type == MethodType.SET) {
            this.process(part, this.write);
        }
    }

    private void process(Method method2, Annotation[] list) throws Exception {
        MethodPart part = this.factory.getInstance(method2, list);
        MethodType type = part.getMethodType();
        if (type == MethodType.GET) {
            this.process(part, this.read);
        }
        if (type == MethodType.IS) {
            this.process(part, this.read);
        }
        if (type == MethodType.SET) {
            this.process(part, this.write);
        }
    }

    private void process(MethodPart method2, PartMap map) {
        String name = method2.getName();
        if (name != null) {
            map.put(name, method2);
        }
    }

    private void process(MethodContact contact) {
        MethodPart get2 = contact.getRead();
        MethodPart set = contact.getWrite();
        if (set != null) {
            this.insert(set, this.write);
        }
        this.insert(get2, this.read);
    }

    private void insert(MethodPart method2, PartMap map) {
        String name = method2.getName();
        MethodPart existing = (MethodPart)map.remove(name);
        if (existing != null && this.isText(method2)) {
            method2 = existing;
        }
        map.put(name, method2);
    }

    private boolean isText(MethodPart method2) {
        Annotation label = method2.getAnnotation();
        return label instanceof Text;
    }

    private void remove(Method method2, Annotation label, Annotation[] list) throws Exception {
        MethodPart part = this.factory.getInstance(method2, label, list);
        MethodType type = part.getMethodType();
        if (type == MethodType.GET) {
            this.remove(part, this.read);
        }
        if (type == MethodType.IS) {
            this.remove(part, this.read);
        }
        if (type == MethodType.SET) {
            this.remove(part, this.write);
        }
    }

    private void remove(MethodPart part, PartMap map) throws Exception {
        String name = part.getName();
        if (name != null) {
            map.remove(name);
        }
    }

    private void build() throws Exception {
        for (String name : this.read) {
            MethodPart part = (MethodPart)this.read.get(name);
            if (part == null) continue;
            this.build(part, name);
        }
    }

    private void build(MethodPart read, String name) throws Exception {
        MethodPart match = this.write.take(name);
        if (match != null) {
            this.build(read, match);
        } else {
            this.build(read);
        }
    }

    private void build(MethodPart read) throws Exception {
        this.add(new MethodContact(read));
    }

    private void build(MethodPart read, MethodPart write) throws Exception {
        Annotation label = read.getAnnotation();
        String name = read.getName();
        if (!write.getAnnotation().equals(label)) {
            throw new MethodException("Annotations do not match for '%s' in %s", name, this.detail);
        }
        Class type = read.getType();
        if (type != write.getType()) {
            throw new MethodException("Method types do not match for %s in %s", name, type);
        }
        this.add(new MethodContact(read, write));
    }

    private void validate() throws Exception {
        for (String name : this.write) {
            MethodPart part = (MethodPart)this.write.get(name);
            if (part == null) continue;
            this.validate(part, name);
        }
    }

    private void validate(MethodPart write, String name) throws Exception {
        MethodPart match = this.read.take(name);
        Method method2 = write.getMethod();
        if (match == null) {
            throw new MethodException("No matching get method for %s in %s", method2, this.detail);
        }
    }

    private static class PartMap
    extends LinkedHashMap<String, MethodPart>
    implements Iterable<String> {
        private PartMap() {
        }

        @Override
        public Iterator<String> iterator() {
            return this.keySet().iterator();
        }

        public MethodPart take(String name) {
            return (MethodPart)this.remove(name);
        }
    }
}

