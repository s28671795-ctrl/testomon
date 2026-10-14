/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.props.AbstractPropertyInvoker;

public class SimplePropertyInvoker
extends AbstractPropertyInvoker {
    public SimplePropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        return this.convertSimpleType(type, value);
    }
}

