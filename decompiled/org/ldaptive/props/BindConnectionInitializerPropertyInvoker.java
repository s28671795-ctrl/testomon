/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import java.io.IOException;
import org.ldaptive.Credential;
import org.ldaptive.control.RequestControl;
import org.ldaptive.io.ResourceUtils;
import org.ldaptive.props.AbstractPropertyInvoker;
import org.ldaptive.props.PropertyValueParser;
import org.ldaptive.sasl.SaslConfig;

public class BindConnectionInitializerPropertyInvoker
extends AbstractPropertyInvoker {
    public BindConnectionInitializerPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            if (SaslConfig.class.isAssignableFrom(type)) {
                if ("null".equals(value)) {
                    newValue = null;
                } else if (PropertyValueParser.isParamsOnlyConfig(value)) {
                    PropertyValueParser configParser = new PropertyValueParser(value, "org.ldaptive.sasl.SaslConfig");
                    newValue = configParser.initializeType();
                } else if (PropertyValueParser.isConfig(value)) {
                    PropertyValueParser configParser = new PropertyValueParser(value);
                    newValue = configParser.initializeType();
                } else {
                    newValue = BindConnectionInitializerPropertyInvoker.instantiateType(SaslConfig.class, value);
                }
            } else if (RequestControl[].class.isAssignableFrom(type)) {
                newValue = this.createArrayTypeFromPropertyValue(RequestControl.class, value);
            } else if (Credential.class.isAssignableFrom(type)) {
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

