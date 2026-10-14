/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.util.List;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.ElementMapUnion;
import org.simpleframework.xml.ElementUnion;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.AttributeException;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.ElementException;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.ExpressionBuilder;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.InstantiatorBuilder;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.Model;
import org.simpleframework.xml.core.ModelAssembler;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Structure;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.TextException;
import org.simpleframework.xml.core.TreeModel;
import org.simpleframework.xml.core.UnionException;
import org.simpleframework.xml.strategy.Type;

class StructureBuilder {
    private InstantiatorBuilder resolver;
    private ExpressionBuilder builder;
    private ModelAssembler assembler;
    private Instantiator factory;
    private LabelMap attributes;
    private LabelMap elements;
    private LabelMap texts;
    private Scanner scanner;
    private Support support;
    private Label version;
    private Label text;
    private Model root;
    private boolean primitive;

    public StructureBuilder(Scanner scanner, Detail detail, Support support) throws Exception {
        this.builder = new ExpressionBuilder(detail, support);
        this.assembler = new ModelAssembler(this.builder, detail, support);
        this.resolver = new InstantiatorBuilder(scanner, detail);
        this.root = new TreeModel(scanner, detail);
        this.attributes = new LabelMap(scanner);
        this.elements = new LabelMap(scanner);
        this.texts = new LabelMap(scanner);
        this.scanner = scanner;
        this.support = support;
    }

    public void assemble(Class type) throws Exception {
        Order order = this.scanner.getOrder();
        if (order != null) {
            this.assembler.assemble(this.root, order);
        }
    }

    public void process(Contact field2, Annotation label) throws Exception {
        if (label instanceof Attribute) {
            this.process(field2, label, this.attributes);
        }
        if (label instanceof ElementUnion) {
            this.union(field2, label, this.elements);
        }
        if (label instanceof ElementListUnion) {
            this.union(field2, label, this.elements);
        }
        if (label instanceof ElementMapUnion) {
            this.union(field2, label, this.elements);
        }
        if (label instanceof ElementList) {
            this.process(field2, label, this.elements);
        }
        if (label instanceof ElementArray) {
            this.process(field2, label, this.elements);
        }
        if (label instanceof ElementMap) {
            this.process(field2, label, this.elements);
        }
        if (label instanceof Element) {
            this.process(field2, label, this.elements);
        }
        if (label instanceof Version) {
            this.version(field2, label);
        }
        if (label instanceof Text) {
            this.text(field2, label);
        }
    }

    private void union(Contact field2, Annotation type, LabelMap map) throws Exception {
        List<Label> list = this.support.getLabels(field2, type);
        for (Label label : list) {
            String path = label.getPath();
            String name = label.getName();
            if (map.get(path) != null) {
                throw new PersistenceException("Duplicate annotation of name '%s' on %s", name, label);
            }
            this.process(field2, label, map);
        }
    }

    private void process(Contact field2, Annotation type, LabelMap map) throws Exception {
        Label label = this.support.getLabel(field2, type);
        String path = label.getPath();
        String name = label.getName();
        if (map.get(path) != null) {
            throw new PersistenceException("Duplicate annotation of name '%s' on %s", name, field2);
        }
        this.process(field2, label, map);
    }

    private void process(Contact field2, Label label, LabelMap map) throws Exception {
        Expression expression = label.getExpression();
        String path = label.getPath();
        Model model = this.root;
        if (!expression.isEmpty()) {
            model = this.register(expression);
        }
        this.resolver.register(label);
        model.register(label);
        map.put(path, label);
    }

    private void text(Contact field2, Annotation type) throws Exception {
        Label label = this.support.getLabel(field2, type);
        Expression expression = label.getExpression();
        String path = label.getPath();
        Model model = this.root;
        if (!expression.isEmpty()) {
            model = this.register(expression);
        }
        if (this.texts.get(path) != null) {
            throw new TextException("Multiple text annotations in %s", type);
        }
        this.resolver.register(label);
        model.register(label);
        this.texts.put(path, label);
    }

    private void version(Contact field2, Annotation type) throws Exception {
        Label label = this.support.getLabel(field2, type);
        if (this.version != null) {
            throw new AttributeException("Multiple version annotations in %s", type);
        }
        this.version = label;
    }

    public Structure build(Class type) throws Exception {
        return new Structure(this.factory, this.root, this.version, this.text, this.primitive);
    }

    private boolean isElement(String path) throws Exception {
        Expression target = this.builder.build(path);
        Model model = this.lookup(target);
        if (model != null) {
            String name = target.getLast();
            int index = target.getIndex();
            if (model.isElement(name)) {
                return true;
            }
            if (model.isModel(name)) {
                Model element = model.lookup(name, index);
                return !element.isEmpty();
            }
        }
        return false;
    }

    private boolean isAttribute(String path) throws Exception {
        Expression target = this.builder.build(path);
        Model model = this.lookup(target);
        if (model != null) {
            String name = target.getLast();
            if (!target.isPath()) {
                return model.isAttribute(path);
            }
            return model.isAttribute(name);
        }
        return false;
    }

    private Model lookup(Expression path) throws Exception {
        Expression target = path.getPath(0, 1);
        if (path.isPath()) {
            return this.root.lookup(target);
        }
        return this.root;
    }

    private Model register(Expression path) throws Exception {
        Model model = this.root.lookup(path);
        if (model != null) {
            return model;
        }
        return this.create(path);
    }

    private Model create(Expression path) throws Exception {
        Model model = this.root;
        while (model != null) {
            String prefix = path.getPrefix();
            String name = path.getFirst();
            int index = path.getIndex();
            if (name != null) {
                model = model.register(name, prefix, index);
            }
            if (!path.isPath()) break;
            path = path.getPath(1);
        }
        return model;
    }

    public void commit(Class type) throws Exception {
        if (this.factory == null) {
            this.factory = this.resolver.build();
        }
    }

    public void validate(Class type) throws Exception {
        Order order = this.scanner.getOrder();
        this.validateUnions(type);
        this.validateElements(type, order);
        this.validateAttributes(type, order);
        this.validateModel(type);
        this.validateText(type);
        this.validateTextList(type);
    }

    private void validateModel(Class type) throws Exception {
        if (!this.root.isEmpty()) {
            this.root.validate(type);
        }
    }

    private void validateText(Class type) throws Exception {
        Label label = this.root.getText();
        if (label != null) {
            if (!label.isTextList()) {
                if (!this.elements.isEmpty()) {
                    throw new TextException("Elements used with %s in %s", label, type);
                }
                if (this.root.isComposite()) {
                    throw new TextException("Paths used with %s in %s", label, type);
                }
            }
        } else if (this.scanner.isEmpty()) {
            this.primitive = this.isEmpty();
        }
    }

    private void validateTextList(Class type) throws Exception {
        Label label = this.root.getText();
        if (label != null && label.isTextList()) {
            Object key = label.getKey();
            for (Label element : this.elements) {
                Object identity = element.getKey();
                if (!identity.equals(key)) {
                    throw new TextException("Elements used with %s in %s", label, type);
                }
                Type dependent = element.getDependent();
                Class actual = dependent.getType();
                if (actual != String.class) continue;
                throw new TextException("Illegal entry of %s with text annotations on %s in %s", actual, label, type);
            }
            if (this.root.isComposite()) {
                throw new TextException("Paths used with %s in %s", label, type);
            }
        }
    }

    private void validateUnions(Class type) throws Exception {
        for (Label label : this.elements) {
            String[] options = label.getPaths();
            Contact contact = label.getContact();
            for (String option : options) {
                Annotation union = contact.getAnnotation();
                Label other = (Label)this.elements.get(option);
                if (label.isInline() != other.isInline()) {
                    throw new UnionException("Inline must be consistent in %s for %s", union, contact);
                }
                if (label.isRequired() == other.isRequired()) continue;
                throw new UnionException("Required must be consistent in %s for %s", union, contact);
            }
        }
    }

    private void validateElements(Class type, Order order) throws Exception {
        if (order != null) {
            for (String name : order.elements()) {
                if (this.isElement(name)) continue;
                throw new ElementException("Ordered element '%s' missing for %s", name, type);
            }
        }
    }

    private void validateAttributes(Class type, Order order) throws Exception {
        if (order != null) {
            for (String name : order.attributes()) {
                if (this.isAttribute(name)) continue;
                throw new AttributeException("Ordered attribute '%s' missing in %s", name, type);
            }
        }
    }

    private boolean isEmpty() {
        if (this.text != null) {
            return false;
        }
        return this.root.isEmpty();
    }
}

