/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.annotation.Annotation;
import java.util.List;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.ClassScanner;
import org.simpleframework.xml.core.Contact;
import org.simpleframework.xml.core.ContactList;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Function;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Section;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.Structure;
import org.simpleframework.xml.core.StructureBuilder;
import org.simpleframework.xml.core.Support;

class ObjectScanner
implements Scanner {
    private StructureBuilder builder;
    private ClassScanner scanner;
    private Structure structure;
    private Support support;
    private Detail detail;

    public ObjectScanner(Detail detail, Support support) throws Exception {
        this.scanner = new ClassScanner(detail, support);
        this.builder = new StructureBuilder(this, detail, support);
        this.support = support;
        this.detail = detail;
        this.scan(detail);
    }

    @Override
    public Signature getSignature() {
        return this.scanner.getSignature();
    }

    @Override
    public List<Signature> getSignatures() {
        return this.scanner.getSignatures();
    }

    @Override
    public ParameterMap getParameters() {
        return this.scanner.getParameters();
    }

    @Override
    public Instantiator getInstantiator() {
        return this.structure.getInstantiator();
    }

    @Override
    public Class getType() {
        return this.detail.getType();
    }

    @Override
    public Decorator getDecorator() {
        return this.scanner.getDecorator();
    }

    @Override
    public Caller getCaller(Context context) {
        return new Caller(this, context);
    }

    @Override
    public Section getSection() {
        return this.structure.getSection();
    }

    @Override
    public Version getRevision() {
        return this.structure.getRevision();
    }

    @Override
    public Order getOrder() {
        return this.scanner.getOrder();
    }

    @Override
    public Label getVersion() {
        return this.structure.getVersion();
    }

    @Override
    public Label getText() {
        return this.structure.getText();
    }

    @Override
    public String getName() {
        return this.detail.getName();
    }

    @Override
    public Function getCommit() {
        return this.scanner.getCommit();
    }

    @Override
    public Function getValidate() {
        return this.scanner.getValidate();
    }

    @Override
    public Function getPersist() {
        return this.scanner.getPersist();
    }

    @Override
    public Function getComplete() {
        return this.scanner.getComplete();
    }

    @Override
    public Function getReplace() {
        return this.scanner.getReplace();
    }

    @Override
    public Function getResolve() {
        return this.scanner.getResolve();
    }

    @Override
    public boolean isPrimitive() {
        return this.structure.isPrimitive();
    }

    @Override
    public boolean isEmpty() {
        return this.scanner.getRoot() == null;
    }

    @Override
    public boolean isStrict() {
        return this.detail.isStrict();
    }

    private void scan(Detail detail) throws Exception {
        this.order(detail);
        this.field(detail);
        this.method(detail);
        this.validate(detail);
        this.commit(detail);
    }

    private void order(Detail detail) throws Exception {
        Class type = detail.getType();
        this.builder.assemble(type);
    }

    private void commit(Detail detail) throws Exception {
        Class type = detail.getType();
        if (this.structure == null) {
            this.structure = this.builder.build(type);
        }
        this.builder = null;
    }

    private void validate(Detail detail) throws Exception {
        Class type = detail.getType();
        this.builder.commit(type);
        this.builder.validate(type);
    }

    private void field(Detail detail) throws Exception {
        Class type = detail.getType();
        DefaultType access = detail.getOverride();
        ContactList list = this.support.getFields(type, access);
        for (Contact contact : list) {
            Annotation label = contact.getAnnotation();
            if (label == null) continue;
            this.builder.process(contact, label);
        }
    }

    private void method(Detail detail) throws Exception {
        Class type = detail.getType();
        DefaultType access = detail.getOverride();
        ContactList list = this.support.getMethods(type, access);
        for (Contact contact : list) {
            Annotation label = contact.getAnnotation();
            if (label == null) continue;
            this.builder.process(contact, label);
        }
    }
}

