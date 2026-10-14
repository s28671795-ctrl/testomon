/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.List;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.DefaultDetail;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Function;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.ObjectScanner;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Section;
import org.simpleframework.xml.core.Signature;
import org.simpleframework.xml.core.Support;

class DefaultScanner
implements Scanner {
    private Scanner scanner;
    private Detail detail;

    public DefaultScanner(Detail original, Support support) throws Exception {
        this.detail = new DefaultDetail(original, DefaultType.FIELD);
        this.scanner = new ObjectScanner(this.detail, support);
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
        return this.scanner.getInstantiator();
    }

    @Override
    public Class getType() {
        return this.scanner.getType();
    }

    @Override
    public Decorator getDecorator() {
        return this.scanner.getDecorator();
    }

    @Override
    public Caller getCaller(Context context) {
        return this.scanner.getCaller(context);
    }

    @Override
    public Section getSection() {
        return this.scanner.getSection();
    }

    @Override
    public Version getRevision() {
        return this.scanner.getRevision();
    }

    @Override
    public Order getOrder() {
        return this.scanner.getOrder();
    }

    @Override
    public Label getVersion() {
        return this.scanner.getVersion();
    }

    @Override
    public Label getText() {
        return this.scanner.getText();
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
        return this.scanner.isPrimitive();
    }

    @Override
    public boolean isEmpty() {
        return this.scanner.isEmpty();
    }

    @Override
    public boolean isStrict() {
        return this.scanner.isStrict();
    }
}

