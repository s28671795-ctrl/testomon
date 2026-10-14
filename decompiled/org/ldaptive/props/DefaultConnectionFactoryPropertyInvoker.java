/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.props.AbstractPropertyInvoker;
import org.ldaptive.transport.Transport;

public class DefaultConnectionFactoryPropertyInvoker
extends AbstractPropertyInvoker {
    public DefaultConnectionFactoryPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = Transport.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(Transport.class, value) : this.convertSimpleType(type, value);
        }
        return newValue;
    }
}

