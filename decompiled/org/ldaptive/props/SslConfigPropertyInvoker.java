/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.props;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.TrustManager;
import org.ldaptive.props.AbstractPropertyInvoker;
import org.ldaptive.props.CredentialConfigParser;
import org.ldaptive.props.PropertyValueParser;
import org.ldaptive.ssl.CertificateHostnameVerifier;
import org.ldaptive.ssl.CredentialConfig;
import org.ldaptive.ssl.SslConfig;

public class SslConfigPropertyInvoker
extends AbstractPropertyInvoker {
    public SslConfigPropertyInvoker(Class<?> c) {
        this.initialize(c);
    }

    @Override
    protected Object convertValue(Class<?> type, String value) {
        Object newValue = value;
        if (type != String.class) {
            if (CredentialConfig.class.isAssignableFrom(type)) {
                if ("null".equals(value)) {
                    newValue = null;
                } else if (CredentialConfigParser.isCredentialConfig(value)) {
                    CredentialConfigParser configParser = new CredentialConfigParser(value);
                    newValue = configParser.initializeType();
                } else if (PropertyValueParser.isConfig(value)) {
                    PropertyValueParser configParser = new PropertyValueParser(value);
                    newValue = configParser.initializeType();
                } else {
                    newValue = SslConfigPropertyInvoker.instantiateType(SslConfig.class, value);
                }
            } else {
                newValue = TrustManager[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(TrustManager.class, value) : (CertificateHostnameVerifier.class.isAssignableFrom(type) ? this.createTypeFromPropertyValue(CertificateHostnameVerifier.class, value) : (HandshakeCompletedListener[].class.isAssignableFrom(type) ? this.createArrayTypeFromPropertyValue(HandshakeCompletedListener.class, value) : this.convertSimpleType(type, value)));
            }
        }
        return newValue;
    }
}

