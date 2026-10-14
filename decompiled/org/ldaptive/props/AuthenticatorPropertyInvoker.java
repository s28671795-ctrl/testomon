/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import org.ldaptive.auth.AuthenticationHandler;
import org.ldaptive.auth.AuthenticationResponseHandler;
import org.ldaptive.auth.DnResolver;
import org.ldaptive.auth.EntryResolver;
import org.ldaptive.props.AbstractPropertyInvoker;

public class AuthenticatorPropertyInvoker
extends AbstractPropertyInvoker {
    public AuthenticatorPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            newValue = DnResolver.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(DnResolver.class, value) : (AuthenticationHandler.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(AuthenticationHandler.class, value) : (AuthenticationResponseHandler[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(AuthenticationResponseHandler.class, value) : (EntryResolver.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(EntryResolver.class, value) : this.convertSimpleType(type, value))));
        }
        return newValue;
    }
}

