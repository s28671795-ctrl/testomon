/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.CacheLabel;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Extractor;
import org.simpleframework.xml.core.ExtractorFactory;
import org.simpleframework.xml.core.Group;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.TextListLabel;
import org.simpleframework.xml.stream.Format;

class GroupExtractor
implements Group {
    private final ExtractorFactory factory;
    private final Annotation label;
    private final Registry registry;
    private final LabelMap elements;

    public GroupExtractor(Contact contact, Annotation label, Format format) throws Exception {
        this.factory = new ExtractorFactory(contact, label, format);
        this.elements = new LabelMap();
        this.registry = new Registry(this.elements);
        this.label = label;
        this.extract();
    }

    public String[] getNames() throws Exception {
        return this.elements.getKeys();
    }

    public String[] getPaths() throws Exception {
        return this.elements.getPaths();
    }

    @Override
    public LabelMap getElements() throws Exception {
        return this.elements.getLabels();
    }

    @Override
    public Label getLabel(Class type) {
        return this.registry.resolve(type);
    }

    @Override
    public Label getText() {
        return this.registry.resolveText();
    }

    public boolean isValid(Class type) {
        return this.registry.resolve(type) != null;
    }

    public boolean isDeclared(Class type) {
        return this.registry.containsKey(type);
    }

    @Override
    public boolean isInline() {
        for (Label label : this.registry) {
            if (label.isInline()) continue;
            return false;
        }
        return !this.registry.isEmpty();
    }

    @Override
    public boolean isTextList() {
        return this.registry.isText();
    }

    private void extract() throws Exception {
        Extractor extractor = this.factory.getInstance();
        if (extractor != null) {
            this.extract(extractor);
        }
    }

    private void extract(Extractor extractor) throws Exception {
        Annotation[] list;
        for (Annotation label : list = extractor.getAnnotations()) {
            this.extract(extractor, label);
        }
    }

    private void extract(Extractor extractor, Annotation value) throws Exception {
        Label label = extractor.getLabel(value);
        Class type = extractor.getType(value);
        if (this.registry != null) {
            this.registry.register(type, label);
        }
    }

    @Override
    public String toString() {
        return this.label.toString();
    }

    private static class Registry
    extends LinkedHashMap<Class, Label>
    implements Iterable<Label> {
        private LabelMap elements;
        private Label text;

        public Registry(LabelMap elements) {
            this.elements = elements;
        }

        public boolean isText() {
            return this.text != null;
        }

        @Override
        public Iterator<Label> iterator() {
            return this.values().iterator();
        }

        public Label resolveText() {
            return this.resolveText(String.class);
        }

        public Label resolve(Class type) {
            Label label = this.resolveText(type);
            if (label == null) {
                return this.resolveElement(type);
            }
            return label;
        }

        private Label resolveText(Class type) {
            if (this.text != null && type == String.class) {
                return this.text;
            }
            return null;
        }

        private Label resolveElement(Class type) {
            while (type != null) {
                Label label = (Label)this.get(type);
                if (label != null) {
                    return label;
                }
                type = type.getSuperclass();
            }
            return null;
        }

        public void register(Class type, Label label) throws Exception {
            CacheLabel cache = new CacheLabel(label);
            this.registerElement(type, cache);
            this.registerText(cache);
        }

        private void registerElement(Class type, Label label) throws Exception {
            String name = label.getName();
            if (!this.elements.containsKey(name)) {
                this.elements.put(name, label);
            }
            if (!this.containsKey(type)) {
                this.put(type, label);
            }
        }

        private void registerText(Label label) throws Exception {
            Contact contact = label.getContact();
            Text value = contact.getAnnotation(Text.class);
            if (value != null) {
                this.text = new TextListLabel(label, value);
            }
        }
    }
}

