/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.ConnectionValidator;
import org.ldaptive.pool.ConnectionActivator;
import org.ldaptive.pool.ConnectionPassivator;
import org.ldaptive.pool.PruneStrategy;
import org.ldaptive.props.AbstractPropertyInvoker;

public class BlockingConnectionPoolPropertyInvoker
extends AbstractPropertyInvoker {
    public BlockingConnectionPoolPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = ConnectionActivator.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(ConnectionActivator.class, value) : (ConnectionPassivator.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(ConnectionPassivator.class, value) : (ConnectionValidator.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(ConnectionValidator.class, value) : (PruneStrategy.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(PruneStrategy.class, value) : this.convertSimpleType(type, value))));
        }
        return newValue;
    }
}

