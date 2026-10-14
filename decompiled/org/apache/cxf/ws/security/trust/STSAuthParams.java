/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import javax.xml.namespace.QName;

public class STSAuthParams {
    private static final String WS_TRUST_NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/";
    private static final String KEY_TYPE_X509 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey";
    private static final QName X509_ENDPOINT = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "X509_Port");
    private static final QName TRANSPORT_ENDPOINT = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "Transport_Port");
    private static final QName UT_ENDPOINT = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "UT_Port");
    private final AuthMode authMode;
    private final String userName;
    private final String callbackHandler;
    private final String alias;
    private final String keystoreProperties;

    public STSAuthParams(AuthMode authMode, String userName, String callbackHandler) {
        this(authMode, userName, callbackHandler, null, null);
    }

    public STSAuthParams(AuthMode authMode, String userName, String callbackHandler, String alias, String keystoreProperties) {
        this.authMode = authMode;
        this.userName = userName;
        this.callbackHandler = callbackHandler;
        this.alias = alias;
        this.keystoreProperties = keystoreProperties;
    }

    public AuthMode getAuthMode() {
        return this.authMode;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getCallbackHandler() {
        return this.callbackHandler;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getKeystoreProperties() {
        return this.keystoreProperties;
    }

    static /* synthetic */ QName access$000() {
        return X509_ENDPOINT;
    }

    static /* synthetic */ QName access$100() {
        return TRANSPORT_ENDPOINT;
    }

    static /* synthetic */ QName access$200() {
        return UT_ENDPOINT;
    }

    public static enum AuthMode {
        X509_ASSYMETRIC(STSAuthParams.access$000(), "http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey"),
        UT_TRANSPORT(STSAuthParams.access$100(), null),
        UT_SYMMETRIC(STSAuthParams.access$200(), null);

        private final QName endpointName;
        private final String keyType;

        private AuthMode(QName endpointName, String keyType) {
            this.endpointName = endpointName;
            this.keyType = keyType;
        }

        public QName getEndpointName() {
            return this.endpointName;
        }

        public String getKeyType() {
            return this.keyType;
        }
    }
}

