/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Function;
import org.simpleframework.xml.core.Instantiator;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.ParameterMap;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Section;
import org.simpleframework.xml.core.Signature;

class PrimitiveScanner
implements Scanner {
    private final Section section = new EmptySection(this);
    private final Detail detail;

    public PrimitiveScanner(Detail detail) {
        this.detail = detail;
    }

    @Override
    public Signature getSignature() {
        return null;
    }

    @Override
    public List<Signature> getSignatures() {
        return new LinkedList<Signature>();
    }

    @Override
    public ParameterMap getParameters() {
        return new ParameterMap();
    }

    @Override
    public Instantiator getInstantiator() {
        return null;
    }

    @Override
    public Class getType() {
        return this.detail.getType();
    }

    @Override
    public Decorator getDecorator() {
        return null;
    }

    @Override
    public Caller getCaller(Context context) {
        return new Caller(this, context);
    }

    @Override
    public Section getSection() {
        return this.section;
    }

    @Override
    public Version getRevision() {
        return null;
    }

    @Override
    public Order getOrder() {
        return null;
    }

    @Override
    public Label getVersion() {
        return null;
    }

    @Override
    public Label getText() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Function getCommit() {
        return null;
    }

    @Override
    public Function getValidate() {
        return null;
    }

    @Override
    public Function getPersist() {
        return null;
    }

    @Override
    public Function getComplete() {
        return null;
    }

    @Override
    public Function getReplace() {
        return null;
    }

    @Override
    public Function getResolve() {
        return null;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isStrict() {
        return true;
    }

    private static class EmptySection
    implements Section {
        private final List<String> list = new LinkedList<String>();
        private final Scanner scanner;

        public EmptySection(Scanner scanner) {
            this.scanner = scanner;
        }

        @Override
        public Iterator<String> iterator() {
            return this.list.iterator();
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getPrefix() {
            return null;
        }

        @Override
        public Label getText() {
            return null;
        }

        @Override
        public LabelMap getElements() {
            return new LabelMap(this.scanner);
        }

        @Override
        public LabelMap getAttributes() {
            return new LabelMap(this.scanner);
        }

        @Override
        public Label getElement(String name) {
            return null;
        }

        @Override
        public Section getSection(String name) {
            return null;
        }

        @Override
        public String getPath(String name) {
            return null;
        }

        @Override
        public String getAttribute(String name) {
            return null;
        }

        @Override
        public boolean isSection(String name) {
            return false;
        }
    }
}

