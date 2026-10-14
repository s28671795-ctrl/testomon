/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.PolicyBuilder;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.neethi.builders.converters.ConverterRegistry;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.apache.neethi.util.Service;

public class AssertionBuilderFactoryImpl
implements AssertionBuilderFactory {
    private Map<QName, AssertionBuilder<?>> registeredBuilders = new ConcurrentHashMap();
    protected AssertionBuilder<?> defaultBuilder;
    protected ConverterRegistry converters;
    protected PolicyBuilder engine;

    public AssertionBuilderFactoryImpl(PolicyBuilder eng) {
        this(eng, new ConverterRegistry());
    }

    public AssertionBuilderFactoryImpl(PolicyBuilder eng, ConverterRegistry reg) {
        this.engine = eng;
        if (reg == null) {
            throw new IllegalArgumentException("ConverterRegistry must not be null");
        }
        this.converters = reg;
        for (AssertionBuilder builder : Service.providers(AssertionBuilder.class)) {
            QName[] knownElements = builder.getKnownElements();
            for (int i = 0; i < knownElements.length; ++i) {
                this.registeredBuilders.put(knownElements[i], builder);
            }
        }
        this.defaultBuilder = new XMLPrimitiveAssertionBuilder();
    }

    @Override
    public ConverterRegistry getConverterRegistry() {
        return this.converters;
    }

    @Override
    public PolicyBuilder getPolicyEngine() {
        return this.engine;
    }

    protected void loadDynamic() {
    }

    @Override
    public void registerBuilder(QName key, AssertionBuilder<?> builder) {
        this.loadDynamic();
        this.registeredBuilders.put(key, builder);
    }

    @Override
    public void registerBuilder(AssertionBuilder<?> builder) {
        this.loadDynamic();
        for (QName q : builder.getKnownElements()) {
            this.registeredBuilders.put(q, builder);
        }
    }

    @Override
    public Assertion build(Object element) {
        this.loadDynamic();
        QName qname = this.converters.findQName(element);
        AssertionBuilder<?> builder = this.registeredBuilders.get(qname);
        if (builder == null) {
            builder = this.handleNoRegisteredBuilder(qname);
        }
        return this.invokeBuilder(element, builder);
    }

    protected AssertionBuilder<?> handleNoRegisteredBuilder(QName qname) {
        return this.defaultBuilder;
    }

    private Assertion invokeBuilder(Object element, AssertionBuilder<?> builder) {
        Class<?> type = this.findAssertionBuilderTarget(builder.getClass());
        Object o = this.converters.convert(element, type);
        AssertionBuilder<?> b = builder;
        return b.build(o, this);
    }

    private Class<?> findAssertionBuilderTarget(Class<?> c) {
        Class<?>[] interfaces = c.getInterfaces();
        for (int x = 0; x < interfaces.length; ++x) {
            if (interfaces[x] != AssertionBuilder.class) continue;
            ParameterizedType pt = (ParameterizedType)c.getGenericInterfaces()[x];
            return (Class)pt.getActualTypeArguments()[0];
        }
        if (c.getClass().getSuperclass() != null) {
            return this.findAssertionBuilderTarget(c.getSuperclass());
        }
        return null;
    }

    @Override
    public AssertionBuilder<?> getBuilder(QName qname) {
        this.loadDynamic();
        return this.registeredBuilders.get(qname);
    }
}

