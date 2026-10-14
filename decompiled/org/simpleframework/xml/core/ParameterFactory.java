/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.ElementMapUnion;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.AttributeParameter;
import org.simpleframework.xml.core.ElementArrayParameter;
import org.simpleframework.xml.core.ElementListParameter;
import org.simpleframework.xml.core.ElementListUnionParameter;
import org.simpleframework.xml.core.ElementMapParameter;
import org.simpleframework.xml.core.ElementMapUnionParameter;
import org.simpleframework.xml.core.ElementParameter;
import org.simpleframework.xml.core.ElementUnionParameter;
import org.simpleframework.xml.core.Parameter;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.TextParameter;
import org.simpleframework.xml.stream.Format;

class ParameterFactory {
    private final Format format;

    public ParameterFactory(Support support) {
        this.format = support.getFormat();
    }

    public Parameter getInstance(Constructor factory2, Annotation label, int index) throws Exception {
        return this.getInstance(factory2, label, null, index);
    }

    public Parameter getInstance(Constructor factory2, Annotation label, Annotation entry, int index) throws Exception {
        Constructor builder = this.getConstructor(label);
        if (entry != null) {
            return (Parameter)builder.newInstance(factory2, label, entry, this.format, index);
        }
        return (Parameter)builder.newInstance(factory2, label, this.format, index);
    }

    private Constructor getConstructor(Annotation label) throws Exception {
        ParameterBuilder builder = this.getBuilder(label);
        Constructor factory2 = builder.getConstructor();
        if (!factory2.isAccessible()) {
            factory2.setAccessible(true);
        }
        return factory2;
    }

    private ParameterBuilder getBuilder(Annotation label) throws Exception {
        if (label instanceof Element) {
            return new ParameterBuilder(ElementParameter.class, Element.class);
        }
        if (label instanceof ElementList) {
            return new ParameterBuilder(ElementListParameter.class, ElementList.class);
        }
        if (label instanceof ElementArray) {
            return new ParameterBuilder(ElementArrayParameter.class, ElementArray.class);
        }
        if (label instanceof ElementMapUnion) {
            return new ParameterBuilder(ElementMapUnionParameter.class, ElementMapUnion.class, ElementMap.class);
        }
        if (label instanceof ElementListUnion) {
            return new ParameterBuilder(ElementListUnionParameter.class, ElementListUnion.class, ElementList.class);
        }
        if (label instanceof ElementUnion) {
            return new ParameterBuilder(ElementUnionParameter.class, ElementUnion.class, Element.class);
        }
        if (label instanceof ElementMap) {
            return new ParameterBuilder(ElementMapParameter.class, ElementMap.class);
        }
        if (label instanceof Attribute) {
            return new ParameterBuilder(AttributeParameter.class, Attribute.class);
        }
        if (label instanceof Text) {
            return new ParameterBuilder(TextParameter.class, Text.class);
        }
        throw new PersistenceException("Annotation %s not supported", label);
    }

    private static class ParameterBuilder {
        private final Class entry;
        private final Class label;
        private final Class type;

        public ParameterBuilder(Class type, Class label) {
            this(type, label, null);
        }

        public ParameterBuilder(Class type, Class label, Class entry) {
            this.label = label;
            this.entry = entry;
            this.type = type;
        }

        public Constructor getConstructor() throws Exception {
            if (this.entry != null) {
                return this.getConstructor(this.label, this.entry);
            }
            return this.getConstructor(this.label);
        }

        public Constructor getConstructor(Class label) throws Exception {
            return this.getConstructor(Constructor.class, label, Format.class, Integer.TYPE);
        }

        public Constructor getConstructor(Class label, Class entry) throws Exception {
            return this.getConstructor(Constructor.class, label, entry, Format.class, Integer.TYPE);
        }

        private Constructor getConstructor(Class ... types) throws Exception {
            return this.type.getConstructor(types);
        }
    }
}

