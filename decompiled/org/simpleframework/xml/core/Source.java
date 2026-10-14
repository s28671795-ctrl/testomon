/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.Version;
import org.simpleframework.xml.core.Caller;
import org.simpleframework.xml.core.ClassSchema;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Decorator;
import org.simpleframework.xml.core.Instance;
import org.simpleframework.xml.core.PersistenceException;
import org.simpleframework.xml.core.Scanner;
import org.simpleframework.xml.core.Schema;
import org.simpleframework.xml.core.Session;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.TemplateEngine;
import org.simpleframework.xml.core.TemplateFilter;
import org.simpleframework.xml.filter.Filter;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Style;

class Source
implements Context {
    private TemplateEngine engine;
    private Strategy strategy;
    private Support support;
    private Session session;
    private Filter filter;

    public Source(Strategy strategy, Support support, Session session) {
        this.filter = new TemplateFilter(this, support);
        this.engine = new TemplateEngine(this.filter);
        this.strategy = strategy;
        this.support = support;
        this.session = session;
    }

    @Override
    public boolean isStrict() {
        return this.session.isStrict();
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public Support getSupport() {
        return this.support;
    }

    @Override
    public Style getStyle() {
        return this.support.getStyle();
    }

    @Override
    public boolean isFloat(Class type) throws Exception {
        return Support.isFloat(type);
    }

    @Override
    public boolean isFloat(Type type) throws Exception {
        return this.isFloat(type.getType());
    }

    @Override
    public boolean isPrimitive(Class type) throws Exception {
        return this.support.isPrimitive(type);
    }

    @Override
    public boolean isPrimitive(Type type) throws Exception {
        return this.isPrimitive(type.getType());
    }

    @Override
    public Instance getInstance(Class type) {
        return this.support.getInstance(type);
    }

    @Override
    public Instance getInstance(Value value) {
        return this.support.getInstance(value);
    }

    @Override
    public String getName(Class type) throws Exception {
        return this.support.getName(type);
    }

    @Override
    public Version getVersion(Class type) throws Exception {
        return this.getScanner(type).getRevision();
    }

    private Scanner getScanner(Class type) throws Exception {
        return this.support.getScanner(type);
    }

    @Override
    public Decorator getDecorator(Class type) throws Exception {
        return this.getScanner(type).getDecorator();
    }

    @Override
    public Caller getCaller(Class type) throws Exception {
        return this.getScanner(type).getCaller(this);
    }

    @Override
    public Schema getSchema(Class type) throws Exception {
        Scanner schema = this.getScanner(type);
        if (schema == null) {
            throw new PersistenceException("Invalid schema class %s", type);
        }
        return new ClassSchema(schema, this);
    }

    @Override
    public Object getAttribute(Object key) {
        return this.session.get(key);
    }

    @Override
    public Value getOverride(Type type, InputNode node) throws Exception {
        NodeMap<InputNode> map = node.getAttributes();
        if (map == null) {
            throw new PersistenceException("No attributes for %s", node);
        }
        return this.strategy.read(type, map, this.session);
    }

    @Override
    public boolean setOverride(Type type, Object value, OutputNode node) throws Exception {
        NodeMap<OutputNode> map = node.getAttributes();
        if (map == null) {
            throw new PersistenceException("No attributes for %s", node);
        }
        return this.strategy.write(type, value, map, this.session);
    }

    @Override
    public Class getType(Type type, Object value) {
        if (value != null) {
            return value.getClass();
        }
        return type.getType();
    }

    @Override
    public String getProperty(String text) {
        return this.engine.process(text);
    }
}

