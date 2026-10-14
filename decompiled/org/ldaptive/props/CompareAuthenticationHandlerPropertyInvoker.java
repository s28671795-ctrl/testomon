/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.ConnectionFactory;
import org.ldaptive.control.RequestControl;
import org.ldaptive.props.AbstractPropertyInvoker;

public class CompareAuthenticationHandlerPropertyInvoker
extends AbstractPropertyInvoker {
    public CompareAuthenticationHandlerPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = ConnectionFactory.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(ConnectionFactory.class, value) : (RequestControl[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(RequestControl.class, value) : this.convertSimpleType(type, value));
        }
        return newValue;
    }
}

