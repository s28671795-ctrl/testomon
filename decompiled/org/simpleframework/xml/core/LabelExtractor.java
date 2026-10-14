/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.ElementMapUnion;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.AttributeLabel;
import org.simpleframework.xml.core.CacheLabel;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.ElementArrayLabel;
import org.simpleframework.xml.core.ElementLabel;
import org.simpleframework.xml.core.ElementListLabel;
import org.simpleframework.xml.core.ElementListUnionLabel;
import org.simpleframework.xml.core.ElementMapLabel;
import org.simpleframework.xml.core.ElementMapUnionLabel;
import org.simpleframework.xml.core.ElementUnionLabel;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelGroup;
import org.simpleframework.xml.core.LabelKey;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.TextLabel;
import org.simpleframework.xml.core.VersionLabel;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class LabelExtractor {
    private final Cache<LabelGroup> cache = new ConcurrentCache<LabelGroup>();
    private final Format format;

    public LabelExtractor(Format format) {
        this.format = format;
    }

    public Label getLabel(Contact contact, Annotation label) throws Exception {
        Object key = this.getKey(contact, label);
        LabelGroup list = this.getGroup(contact, label, key);
        if (list != null) {
            return list.getPrimary();
        }
        return null;
    }

    public List<Label> getList(Contact contact, Annotation label) throws Exception {
        Object key = this.getKey(contact, label);
        LabelGroup list = this.getGroup(contact, label, key);
        if (list != null) {
            return list.getList();
        }
        return Collections.emptyList();
    }

    private LabelGroup getGroup(Contact contact, Annotation label, Object key) throws Exception {
        LabelGroup value = this.cache.fetch(key);
        if (value == null) {
            LabelGroup list = this.getLabels(contact, label);
            if (list != null) {
                this.cache.cache(key, list);
            }
            return list;
        }
        return value;
    }

    private LabelGroup getLabels(Contact contact, Annotation label) throws Exception {
        if (label instanceof ElementUnion) {
            return this.getUnion(contact, label);
        }
        if (label instanceof ElementListUnion) {
            return this.getUnion(contact, label);
        }
        if (label instanceof ElementMapUnion) {
            return this.getUnion(contact, label);
        }
        return this.getSingle(contact, label);
    }

    private LabelGroup getSingle(Contact contact, Annotation label) throws Exception {
        Label value = this.getLabel(contact, label, null);
        if (value != null) {
            value = new CacheLabel(value);
        }
        return new LabelGroup(value);
    }

    private LabelGroup getUnion(Contact contact, Annotation label) throws Exception {
        Annotation[] list = this.getAnnotations(label);
        if (list.length > 0) {
            LinkedList<Label> labels = new LinkedList<Label>();
            for (Annotation value : list) {
                Label entry = this.getLabel(contact, label, value);
                if (entry != null) {
                    entry = new CacheLabel(entry);
                }
                labels.add(entry);
            }
            return new LabelGroup(labels);
        }
        return null;
    }

    private Annotation[] getAnnotations(Annotation label) throws Exception {
        Class<? extends Annotation> union = label.annotationType();
        Method[] list = union.getDeclaredMethods();
        if (list.length > 0) {
            Method method2 = list[0];
            Object value = method2.invoke((Object)label, new Object[0]);
            return (Annotation[])value;
        }
        return new Annotation[0];
    }

    private Label getLabel(Contact contact, Annotation label, Annotation entry) throws Exception {
        Constructor factory2 = this.getConstructor(label);
        if (entry != null) {
            return (Label)factory2.newInstance(contact, label, entry, this.format);
        }
        return (Label)factory2.newInstance(contact, label, this.format);
    }

    private Object getKey(Contact contact, Annotation label) {
        return new LabelKey(contact, label);
    }

    private Constructor getConstructor(Annotation label) throws Exception {
        LabelBuilder builder = this.getBuilder(label);
        Constructor factory2 = builder.getConstructor();
        if (!factory2.isAccessible()) {
            factory2.setAccessible(true);
        }
        return factory2;
    }

    private LabelBuilder getBuilder(Annotation label) throws Exception {
        if (label instanceof Element) {
            return new LabelBuilder(ElementLabel.class, Element.class);
        }
        if (label instanceof ElementList) {
            return new LabelBuilder(ElementListLabel.class, ElementList.class);
        }
        if (label instanceof ElementArray) {
            return new LabelBuilder(ElementArrayLabel.class, ElementArray.class);
        }
        if (label instanceof ElementMap) {
            return new LabelBuilder(ElementMapLabel.class, ElementMap.class);
        }
        if (label instanceof ElementUnion) {
            return new LabelBuilder(ElementUnionLabel.class, ElementUnion.class, Element.class);
        }
        if (label instanceof ElementListUnion) {
            return new LabelBuilder(ElementListUnionLabel.class, ElementListUnion.class, ElementList.class);
        }
        if (label instanceof ElementMapUnion) {
            return new LabelBuilder(ElementMapUnionLabel.class, ElementMapUnion.class, ElementMap.class);
        }
        if (label instanceof Attribute) {
            return new LabelBuilder(AttributeLabel.class, Attribute.class);
        }
        if (label instanceof Version) {
            return new LabelBuilder(VersionLabel.class, Version.class);
        }
        if (label instanceof Text) {
            return new LabelBuilder(TextLabel.class, Text.class);
        }
        throw new PersistenceException("Annotation %s not supported", label);
    }

    private static class LabelBuilder {
        private final Class label;
        private final Class entry;
        private final Class type;

        public LabelBuilder(Class type, Class label) {
            this(type, label, null);
        }

        public LabelBuilder(Class type, Class label, Class entry) {
            this.entry = entry;
            this.label = label;
            this.type = type;
        }

        public Constructor getConstructor() throws Exception {
            if (this.entry != null) {
                return this.getConstructor(this.label, this.entry);
            }
            return this.getConstructor(this.label);
        }

        private Constructor getConstructor(Class label) throws Exception {
            return this.type.getConstructor(Contact.class, label, Format.class);
        }

        private Constructor getConstructor(Class label, Class entry) throws Exception {
            return this.type.getConstructor(Contact.class, label, entry, Format.class);
        }
    }
}

