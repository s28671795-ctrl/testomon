/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.IOException;
import org.ldaptive.Credential;
import org.ldaptive.io.ResourceUtils;
import org.ldaptive.props.AbstractPropertyInvoker;

public class AuthenticationRequestPropertyInvoker
extends AbstractPropertyInvoker {
    public AuthenticationRequestPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            if (Credential.class.isAssignableFrom(type)) {
                if (ResourceUtils.isResource(value)) {
                    try {
                        newValue = new Credential(ResourceUtils.readResource(value));
                    }
                    catch (IOException e) {
                        throw new IllegalArgumentException("Could not read resource: " + value, e);
                    }
                } else {
                    newValue = new Credential(value);
                }
            } else {
                newValue = this.convertSimpleType(type, value);
            }
        }
        return newValue;
    }
}

