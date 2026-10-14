/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.simpleframework.xml.core.CacheParameter;
import org.simpleframework.xml.core.ClassInstantiator;
import org.simpleframework.xml.core.Comparer;
import org.simpleframework.xml.core.ConstructorException;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Creator;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.Parameter;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.SignatureCreator;
import org.simpleframework.xml.core.Support;

class InstantiatorBuilder {
    private List<Creator> options = new ArrayList<Creator>();
    private Instantiator factory;
    private LabelMap attributes;
    private LabelMap elements;
    private LabelMap texts;
    private Comparer comparer = new Comparer();
    private Scanner scanner;
    private Detail detail;

    public InstantiatorBuilder(Scanner scanner, Detail detail) {
        this.attributes = new LabelMap();
        this.elements = new LabelMap();
        this.texts = new LabelMap();
        this.scanner = scanner;
        this.detail = detail;
    }

    public Instantiator build() throws Exception {
        if (this.factory == null) {
            this.populate(this.detail);
            this.build(this.detail);
            this.validate(this.detail);
        }
        return this.factory;
    }

    private Instantiator build(Detail detail) throws Exception {
        if (this.factory == null) {
            this.factory = this.create(detail);
        }
        return this.factory;
    }

    private Instantiator create(Detail detail) throws Exception {
        Signature primary = this.scanner.getSignature();
        ParameterMap registry = this.scanner.getParameters();
        SignatureCreator creator = null;
        if (primary != null) {
            creator = new SignatureCreator(primary);
        }
        return new ClassInstantiator(this.options, creator, registry, detail);
    }

    private Creator create(Signature signature) {
        SignatureCreator creator = new SignatureCreator(signature);
        if (signature != null) {
            this.options.add(creator);
        }
        return creator;
    }

    private Parameter create(Parameter original) throws Exception {
        Label label = this.resolve(original);
        if (label != null) {
            return new CacheParameter(original, label);
        }
        return null;
    }

    private void populate(Detail detail) throws Exception {
        List<Signature> list = this.scanner.getSignatures();
        for (Signature signature : list) {
            this.populate(signature);
        }
    }

    private void populate(Signature signature) throws Exception {
        Signature substitute = new Signature(signature);
        for (Parameter parameter : signature) {
            Parameter replace = this.create(parameter);
            if (replace == null) continue;
            substitute.add(replace);
        }
        this.create(substitute);
    }

    private void validate(Detail detail) throws Exception {
        ParameterMap registry = this.scanner.getParameters();
        List<Parameter> list = registry.getAll();
        for (Parameter parameter : list) {
            Label label = this.resolve(parameter);
            String path = parameter.getPath();
            if (label == null) {
                throw new ConstructorException("Parameter '%s' does not have a match in %s", path, detail);
            }
            this.validateParameter(label, parameter);
        }
        this.validateConstructors();
    }

    private void validateParameter(Label label, Parameter parameter) throws Exception {
        Class actual;
        Contact contact = label.getContact();
        String name = parameter.getName();
        Class expect = parameter.getType();
        if (!Support.isAssignable(expect, actual = contact.getType())) {
            throw new ConstructorException("Type is not compatible with %s for '%s' in %s", label, name, parameter);
        }
        this.validateNames(label, parameter);
        this.validateAnnotations(label, parameter);
    }

    private void validateNames(Label label, Parameter parameter) throws Exception {
        String require;
        String name;
        String[] options = label.getNames();
        if (!this.contains(options, name = parameter.getName()) && name != (require = label.getName())) {
            if (name == null || require == null) {
                throw new ConstructorException("Annotation does not match %s for '%s' in %s", label, name, parameter);
            }
            if (!name.equals(require)) {
                throw new ConstructorException("Annotation does not match %s for '%s' in %s", label, name, parameter);
            }
        }
    }

    private void validateAnnotations(Label label, Parameter parameter) throws Exception {
        Class<? extends Annotation> actual;
        Class<? extends Annotation> expect;
        Annotation field2 = label.getAnnotation();
        Annotation argument = parameter.getAnnotation();
        String name = parameter.getName();
        if (!this.comparer.equals(field2, argument) && !(expect = field2.annotationType()).equals(actual = argument.annotationType())) {
            throw new ConstructorException("Annotation %s does not match %s for '%s' in %s", actual, expect, name, parameter);
        }
    }

    private void validateConstructors() throws Exception {
        List<Creator> list = this.factory.getCreators();
        if (this.factory.isDefault()) {
            this.validateConstructors(this.elements);
            this.validateConstructors(this.attributes);
        }
        if (!list.isEmpty()) {
            this.validateConstructors(this.elements, list);
            this.validateConstructors(this.attributes, list);
        }
    }

    private void validateConstructors(LabelMap map) throws Exception {
        for (Label label : map) {
            Contact contact;
            if (label == null || !(contact = label.getContact()).isReadOnly()) continue;
            throw new ConstructorException("Default constructor can not accept read only %s in %s", label, this.detail);
        }
    }

    private void validateConstructors(LabelMap map, List<Creator> list) throws Exception {
        for (Label label : map) {
            if (label == null) continue;
            this.validateConstructor(label, list);
        }
        if (list.isEmpty()) {
            throw new ConstructorException("No constructor accepts all read only values in %s", this.detail);
        }
    }

    private void validateConstructor(Label label, List<Creator> list) throws Exception {
        Iterator<Creator> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            Parameter value;
            Creator instantiator = iterator2.next();
            Signature signature = instantiator.getSignature();
            Contact contact = label.getContact();
            Object key = label.getKey();
            if (!contact.isReadOnly() || (value = signature.get(key)) != null) continue;
            iterator2.remove();
        }
    }

    public void register(Label label) throws Exception {
        if (label.isAttribute()) {
            this.register(label, this.attributes);
        } else if (label.isText()) {
            this.register(label, this.texts);
        } else {
            this.register(label, this.elements);
        }
    }

    private void register(Label label, LabelMap map) throws Exception {
        String name = label.getName();
        String path = label.getPath();
        if (map.containsKey(name)) {
            Label current = (Label)map.get(name);
            String key = current.getPath();
            if (!key.equals(name)) {
                map.remove(name);
            }
        } else {
            map.put(name, label);
        }
        map.put(path, label);
    }

    private Label resolve(Parameter parameter) throws Exception {
        if (parameter.isAttribute()) {
            return this.resolve(parameter, this.attributes);
        }
        if (parameter.isText()) {
            return this.resolve(parameter, this.texts);
        }
        return this.resolve(parameter, this.elements);
    }

    private Label resolve(Parameter parameter, LabelMap map) throws Exception {
        String name = parameter.getName();
        String path = parameter.getPath();
        Label label = (Label)map.get(path);
        if (label == null) {
            return (Label)map.get(name);
        }
        return label;
    }

    private boolean contains(String[] list, String value) throws Exception {
        for (String entry : list) {
            if (entry == value) {
                return true;
            }
            if (!entry.equals(value)) continue;
            return true;
        }
        return false;
    }
}

