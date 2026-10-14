/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.props.BlockingConnectionPoolPropertyInvoker;
import org.ldaptive.transport.Transport;

public class PooledConnectionFactoryPropertyInvoker
extends BlockingConnectionPoolPropertyInvoker {
    public PooledConnectionFactoryPropertyInvoker(Class<?> c) {
        super(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = Transport.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(Transport.class, value) : super.convertValue(type, value);
        }
        return newValue;
    }
}

