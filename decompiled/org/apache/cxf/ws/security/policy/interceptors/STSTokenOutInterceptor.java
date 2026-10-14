/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.DefaultSTSTokenCacher;
import org.apache.cxf.ws.security.trust.STSAuthParams;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSTokenCacher;
import org.apache.cxf.ws.security.trust.STSTokenRetriever;
import org.apache.cxf.ws.security.trust.STSUtils;

public class STSTokenOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(STSTokenOutInterceptor.class);
    private static final String KEY_TYPE_X509 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey";
    private static final String WS_TRUST_NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/";
    private static final QName X509_ENDPOINT = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "X509_Port");
    private static final QName TRANSPORT_ENDPOINT = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "Transport_Port");
    private static final QName UT_ENDPOINT = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512/", "UT_Port");
    private STSClient stsClient;
    private STSTokenRetriever.TokenRequestParams tokenParams;
    private STSTokenCacher tokenCacher = new DefaultSTSTokenCacher();

    public STSTokenOutInterceptor(STSAuthParams authParams, String stsWsdlLocation, Bus bus) {
        this("prepare-send", authParams, stsWsdlLocation, bus);
    }

    public STSTokenOutInterceptor(String phase, STSAuthParams authParams, String stsWsdlLocation, Bus bus) {
        super(phase);
        this.stsClient = STSUtils.createSTSClient(authParams, stsWsdlLocation, bus);
        this.tokenParams = new STSTokenRetriever.TokenRequestParams();
    }

    public STSTokenOutInterceptor(STSClient stsClient) {
        this("prepare-send", stsClient, new STSTokenRetriever.TokenRequestParams());
    }

    public STSTokenOutInterceptor(STSClient stsClient, STSTokenRetriever.TokenRequestParams tokenParams) {
        this("prepare-send", stsClient, tokenParams);
    }

    public STSTokenOutInterceptor(String phase, STSClient stsClient, STSTokenRetriever.TokenRequestParams tokenParams) {
        super(phase);
        this.stsClient = stsClient;
        this.tokenParams = tokenParams;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        SecurityToken tok;
        if (this.stsClient != null) {
            message.put("security.sts.client", this.stsClient);
        }
        if ((tok = STSTokenRetriever.getToken(message, this.tokenParams, this.tokenCacher)) == null) {
            LOG.warning("Security token was not retrieved from STS");
        }
        this.processToken(message, tok);
    }

    protected void processToken(Message message, SecurityToken tok) {
    }

    public STSClient getSTSClient() {
        return this.stsClient;
    }

    public STSTokenCacher getTokenCacher() {
        return this.tokenCacher;
    }

    public void setTokenCacher(STSTokenCacher tokenCacher) {
        this.tokenCacher = tokenCacher;
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

    @Deprecated
    public static class AuthParams {
        private final AuthMode authMode;
        private final String userName;
        private final String callbackHandler;
        private final String alias;
        private final String keystoreProperties;

        public AuthParams(AuthMode authMode, String userName, String callbackHandler) {
            this(authMode, userName, callbackHandler, null, null);
        }

        public AuthParams(AuthMode authMode, String userName, String callbackHandler, String alias, String keystoreProperties) {
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
    }

    @Deprecated
    public static enum AuthMode {
        X509_ASSYMETRIC(STSTokenOutInterceptor.access$000(), "http://docs.oasis-open.org/ws-sx/ws-trust/200512/PublicKey"),
        UT_TRANSPORT(STSTokenOutInterceptor.access$100(), null),
        UT_SYMMETRIC(STSTokenOutInterceptor.access$200(), null);

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

