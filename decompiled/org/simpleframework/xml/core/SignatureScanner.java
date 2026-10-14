/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
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
import org.simpleframework.xml.core.ConstructorException;
import org.simpleframework.xml.core.Parameter;
import org.simpleframework.xml.core.ParameterFactory;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.SignatureBuilder;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.UnionException;

class SignatureScanner {
    private final SignatureBuilder builder;
    private final ParameterFactory factory;
    private final ParameterMap registry;
    private final Constructor constructor;
    private final Class type;

    public SignatureScanner(Constructor constructor, ParameterMap registry, Support support) throws Exception {
        this.builder = new SignatureBuilder(constructor);
        this.factory = new ParameterFactory(support);
        this.type = constructor.getDeclaringClass();
        this.constructor = constructor;
        this.registry = registry;
        this.scan(this.type);
    }

    public boolean isValid() {
        return this.builder.isValid();
    }

    public List<Signature> getSignatures() throws Exception {
        return this.builder.build();
    }

    private void scan(Class type) throws Exception {
        Class<?>[] types = this.constructor.getParameterTypes();
        for (int i = 0; i < types.length; ++i) {
            this.scan(types[i], i);
        }
    }

    private void scan(Class type, int index) throws Exception {
        Annotation[][] labels = this.constructor.getParameterAnnotations();
        for (int j = 0; j < labels[index].length; ++j) {
            List<Parameter> value = this.process(labels[index][j], index);
            for (Parameter parameter : value) {
                this.builder.insert(parameter, index);
            }
        }
    }

    private List<Parameter> process(Annotation label, int ordinal) throws Exception {
        if (label instanceof Attribute) {
            return this.create(label, ordinal);
        }
        if (label instanceof Element) {
            return this.create(label, ordinal);
        }
        if (label instanceof ElementList) {
            return this.create(label, ordinal);
        }
        if (label instanceof ElementArray) {
            return this.create(label, ordinal);
        }
        if (label instanceof ElementMap) {
            return this.create(label, ordinal);
        }
        if (label instanceof ElementListUnion) {
            return this.union(label, ordinal);
        }
        if (label instanceof ElementMapUnion) {
            return this.union(label, ordinal);
        }
        if (label instanceof ElementUnion) {
            return this.union(label, ordinal);
        }
        if (label instanceof Text) {
            return this.create(label, ordinal);
        }
        return Collections.emptyList();
    }

    private List<Parameter> union(Annotation label, int ordinal) throws Exception {
        Annotation[] list;
        Signature signature = new Signature(this.constructor);
        for (Annotation value : list = this.extract(label)) {
            Parameter parameter = this.factory.getInstance(this.constructor, label, value, ordinal);
            String path = parameter.getPath();
            if (signature.contains(path)) {
                throw new UnionException("Annotation name '%s' used more than once in %s for %s", path, label, this.type);
            }
            signature.set(path, parameter);
            this.register(parameter);
        }
        return signature.getAll();
    }

    private List<Parameter> create(Annotation label, int ordinal) throws Exception {
        Parameter parameter = this.factory.getInstance(this.constructor, label, ordinal);
        if (parameter != null) {
            this.register(parameter);
        }
        return Collections.singletonList(parameter);
    }

    private Annotation[] extract(Annotation label) throws Exception {
        Class<? extends Annotation> union = label.annotationType();
        Method[] list = union.getDeclaredMethods();
        if (list.length != 1) {
            throw new UnionException("Annotation '%s' is not a valid union for %s", label, this.type);
        }
        Method method2 = list[0];
        Object value = method2.invoke((Object)label, new Object[0]);
        return (Annotation[])value;
    }

    private void register(Parameter parameter) throws Exception {
        String path = parameter.getPath();
        Object key = parameter.getKey();
        if (this.registry.containsKey(key)) {
            this.validate(parameter, key);
        }
        if (this.registry.containsKey(path)) {
            this.validate(parameter, path);
        }
        this.registry.put(path, parameter);
        this.registry.put(key, parameter);
    }

    private void validate(Parameter parameter, Object key) throws Exception {
        Parameter other = (Parameter)this.registry.get(key);
        if (parameter.isText() != other.isText()) {
            Annotation expect = parameter.getAnnotation();
            Annotation actual = other.getAnnotation();
            String path = parameter.getPath();
            if (!expect.equals(actual)) {
                throw new ConstructorException("Annotations do not match for '%s' in %s", path, this.type);
            }
            Class real = other.getType();
            if (real != parameter.getType()) {
                throw new ConstructorException("Parameter types do not match for '%s' in %s", path, this.type);
            }
        }
    }
}

