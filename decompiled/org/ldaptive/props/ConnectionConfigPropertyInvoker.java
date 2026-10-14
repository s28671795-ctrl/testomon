/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.ActivePassiveConnectionStrategy;
import org.ldaptive.ConnectionInitializer;
import org.ldaptive.ConnectionStrategy;
import org.ldaptive.DnsSrvConnectionStrategy;
import org.ldaptive.RandomConnectionStrategy;
import org.ldaptive.RoundRobinConnectionStrategy;
import org.ldaptive.props.AbstractPropertyInvoker;

public class ConnectionConfigPropertyInvoker
extends AbstractPropertyInvoker {
    public ConnectionConfigPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = ConnectionInitializer[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(ConnectionInitializer.class, value) : (ConnectionStrategy.class.isAssignableFrom(type) ? ("ACTIVE_PASSIVE".equals(value) ? new ActivePassiveConnectionStrategy() : ("ROUND_ROBIN".equals(value) ? new RoundRobinConnectionStrategy() : ("RANDOM".equals(value) ? new RandomConnectionStrategy() : ("DNS_SRV".equals(value) ? new DnsSrvConnectionStrategy() : this.createTypeFromPropertyValue(ConnectionStrategy.class, value))))) : this.convertSimpleType(type, value));
        }
        return newValue;
    }
}

