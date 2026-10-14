/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.apache.cxf.transport.http.URLConnectionInfo;
import org.apache.cxf.transport.http.UntrustedURLConnectionIOException;
import org.apache.cxf.transport.https.HttpsURLConnectionInfo;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.WSS4JStaxInInterceptor;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.HttpsToken;
import org.apache.wss4j.stax.impl.securityToken.HttpsSecurityTokenImpl;
import org.apache.wss4j.stax.securityEvent.HttpsTokenSecurityEvent;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;

public class HttpsTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final Logger LOG = LogUtils.getL7dLogger(HttpsTokenInterceptorProvider.class);
    private static final long serialVersionUID = -13951002554477036L;

    public HttpsTokenInterceptorProvider() {
        super(Arrays.asList(SP11Constants.TRANSPORT_TOKEN, SP12Constants.TRANSPORT_TOKEN, SP11Constants.ISSUED_TOKEN, SP12Constants.ISSUED_TOKEN, SP11Constants.HTTPS_TOKEN, SP12Constants.HTTPS_TOKEN));
        HttpsTokenOutInterceptor outInterceptor = new HttpsTokenOutInterceptor();
        this.getOutInterceptors().add(outInterceptor);
        this.getOutFaultInterceptors().add(outInterceptor);
        HttpsTokenInInterceptor inInterceptor = new HttpsTokenInInterceptor();
        this.getInInterceptors().add(inInterceptor);
        this.getInFaultInterceptors().add(inInterceptor);
    }

    private static Map<String, List<String>> getProtocolHeaders(Message message) {
        Map<String, List<String>> headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
        if (null == headers) {
            return Collections.emptyMap();
        }
        return headers;
    }

    static class HttpsTokenInInterceptor
    extends AbstractPhaseInterceptor<Message> {
        HttpsTokenInInterceptor() {
            super("pre-stream");
            this.addBefore(WSS4JStaxInInterceptor.class.getName());
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim != null) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "HttpsToken");
                boolean requestor = this.isRequestor(message);
                if (ais.isEmpty()) {
                    if (!requestor) {
                        try {
                            this.assertNonHttpsTransportToken(message);
                        }
                        catch (XMLSecurityException e) {
                            LOG.fine(e.getMessage());
                        }
                    }
                    return;
                }
                if (!requestor) {
                    TLSSessionInfo tlsInfo;
                    try {
                        this.assertHttps(aim, ais, message);
                    }
                    catch (XMLSecurityException e) {
                        LOG.fine(e.getMessage());
                    }
                    SecurityContext sc = message.get(SecurityContext.class);
                    if ((sc == null || sc.getUserPrincipal() == null) && (tlsInfo = message.get(TLSSessionInfo.class)) != null && tlsInfo.getPeerCertificates() != null && tlsInfo.getPeerCertificates().length > 0 && tlsInfo.getPeerCertificates()[0] instanceof X509Certificate) {
                        X509Certificate cert = (X509Certificate)tlsInfo.getPeerCertificates()[0];
                        message.put(SecurityContext.class, this.createSecurityContext(cert.getSubjectX500Principal()));
                    }
                } else {
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                    PolicyUtils.assertPolicy(aim, "HttpDigestAuthentication");
                    PolicyUtils.assertPolicy(aim, "HttpBasicAuthentication");
                    PolicyUtils.assertPolicy(aim, "RequireClientCertificate");
                }
            }
        }

        private void assertHttps(AssertionInfoMap aim, Collection<AssertionInfo> ais, Message message) throws XMLSecurityException {
            List<SecurityEvent> securityEvents = this.getSecurityEventList(message);
            AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
            for (AssertionInfo ai : ais) {
                TLSSessionInfo tlsInfo;
                HttpsSecurityTokenImpl httpsSecurityToken;
                List auth;
                boolean asserted = true;
                HttpsToken token = (HttpsToken)ai.getAssertion();
                HttpsTokenSecurityEvent httpsTokenSecurityEvent = new HttpsTokenSecurityEvent();
                Map headers = HttpsTokenInterceptorProvider.getProtocolHeaders(message);
                if (token.getAuthenticationType() == HttpsToken.AuthenticationType.HttpBasicAuthentication) {
                    auth = (List)headers.get("Authorization");
                    if (auth == null || auth.isEmpty() || !((String)auth.get(0)).startsWith("Basic")) {
                        asserted = false;
                    } else {
                        httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpBasicAuthentication);
                        httpsSecurityToken = new HttpsSecurityTokenImpl(true, policy.getUserName());
                        httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                        httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                        PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), "HttpBasicAuthentication"));
                    }
                }
                if (token.getAuthenticationType() == HttpsToken.AuthenticationType.HttpDigestAuthentication) {
                    auth = (List)headers.get("Authorization");
                    if (auth == null || auth.isEmpty() || !((String)auth.get(0)).startsWith("Digest")) {
                        asserted = false;
                    } else {
                        httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpDigestAuthentication);
                        httpsSecurityToken = new HttpsSecurityTokenImpl(false, policy.getUserName());
                        httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                        httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                        PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), "HttpDigestAuthentication"));
                    }
                }
                if ((tlsInfo = message.get(TLSSessionInfo.class)) != null) {
                    if (token.getAuthenticationType() == HttpsToken.AuthenticationType.RequireClientCertificate) {
                        if (tlsInfo.getPeerCertificates() == null || tlsInfo.getPeerCertificates().length == 0) {
                            asserted = false;
                        } else {
                            PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), "RequireClientCertificate"));
                        }
                    }
                    if (tlsInfo.getPeerCertificates() != null && tlsInfo.getPeerCertificates().length > 0) {
                        httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpsClientCertificateAuthentication);
                        httpsSecurityToken = new HttpsSecurityTokenImpl((X509Certificate)tlsInfo.getPeerCertificates()[0]);
                        httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                        httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                    } else if (httpsTokenSecurityEvent.getAuthenticationType() == null) {
                        httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpsNoAuthentication);
                        httpsSecurityToken = new HttpsSecurityTokenImpl();
                        httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                        httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                    }
                } else {
                    asserted = false;
                }
                ai.setAsserted(asserted);
                if (!asserted) continue;
                securityEvents.add(httpsTokenSecurityEvent);
            }
        }

        private void assertNonHttpsTransportToken(Message message) throws XMLSecurityException {
            TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
            if (tlsInfo != null) {
                HttpsSecurityTokenImpl httpsSecurityToken;
                HttpsTokenSecurityEvent httpsTokenSecurityEvent = new HttpsTokenSecurityEvent();
                if (tlsInfo.getPeerCertificates() != null && tlsInfo.getPeerCertificates().length > 0) {
                    httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpsClientCertificateAuthentication);
                    httpsSecurityToken = new HttpsSecurityTokenImpl((X509Certificate)tlsInfo.getPeerCertificates()[0]);
                    httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                    httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                } else if (httpsTokenSecurityEvent.getAuthenticationType() == null) {
                    httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpsNoAuthentication);
                    httpsSecurityToken = new HttpsSecurityTokenImpl();
                    httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
                    httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                }
                List<SecurityEvent> securityEvents = this.getSecurityEventList(message);
                securityEvents.add(httpsTokenSecurityEvent);
            }
        }

        private List<SecurityEvent> getSecurityEventList(Message message) {
            ArrayList securityEvents = (ArrayList)message.getExchange().get(SecurityEvent.class.getName() + ".out");
            if (securityEvents == null) {
                securityEvents = new ArrayList();
                message.getExchange().put(SecurityEvent.class.getName() + ".out", securityEvents);
            }
            return securityEvents;
        }

        private SecurityContext createSecurityContext(final Principal p) {
            return new SecurityContext(){

                @Override
                public Principal getUserPrincipal() {
                    return p;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return false;
                }
            };
        }
    }

    static class HttpsTokenOutInterceptor
    extends AbstractPhaseInterceptor<Message> {
        HttpsTokenOutInterceptor() {
            super("pre-stream");
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim != null) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "HttpsToken");
                if (ais.isEmpty()) {
                    return;
                }
                if (this.isRequestor(message)) {
                    this.assertHttps(aim, ais, message);
                } else {
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                }
            }
        }

        private void assertHttps(AssertionInfoMap aim, Collection<AssertionInfo> ais, Message message) {
            for (AssertionInfo ai : ais) {
                HttpsToken token = (HttpsToken)ai.getAssertion();
                String scheme2 = (String)message.get("http.scheme");
                ai.setAsserted(true);
                Map headers = HttpsTokenInterceptorProvider.getProtocolHeaders(message);
                if ("https".equals(scheme2)) {
                    if (token.getAuthenticationType() == HttpsToken.AuthenticationType.RequireClientCertificate) {
                        boolean disableClientCertCheck = MessageUtils.getContextualBoolean(message, "ws-security.disable.require.client.cert.check", false);
                        if (!disableClientCertCheck) {
                            final MessageTrustDecider orig = message.get(MessageTrustDecider.class);
                            MessageTrustDecider trust = new MessageTrustDecider(){

                                @Override
                                public void establishTrust(String conduitName, URLConnectionInfo connectionInfo, Message message) throws UntrustedURLConnectionIOException {
                                    HttpsURLConnectionInfo info;
                                    if (orig != null) {
                                        orig.establishTrust(conduitName, connectionInfo, message);
                                    }
                                    if ((info = (HttpsURLConnectionInfo)connectionInfo).getLocalCertificates() == null || info.getLocalCertificates().length == 0) {
                                        throw new UntrustedURLConnectionIOException("RequireClientCertificate is set, but no local certificates were negotiated.  Is the server set to ask for client authorization?");
                                    }
                                }
                            };
                            message.put(MessageTrustDecider.class, trust);
                        }
                        PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), "RequireClientCertificate"));
                    }
                    if (token.getAuthenticationType() == HttpsToken.AuthenticationType.HttpBasicAuthentication) {
                        List auth = (List)headers.get("Authorization");
                        if (auth == null || auth.isEmpty() || !((String)auth.get(0)).startsWith("Basic")) {
                            ai.setNotAsserted("HttpBasicAuthentication is set, but not being used");
                        } else {
                            PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), "HttpBasicAuthentication"));
                        }
                    }
                    if (token.getAuthenticationType() == HttpsToken.AuthenticationType.HttpDigestAuthentication) {
                        List auth = (List)headers.get("Authorization");
                        if (auth == null || auth.isEmpty() || !((String)auth.get(0)).startsWith("Digest")) {
                            ai.setNotAsserted("HttpDigestAuthentication is set, but not being used");
                        } else {
                            PolicyUtils.assertPolicy(aim, new QName(token.getName().getNamespaceURI(), "HttpDigestAuthentication"));
                        }
                    }
                } else {
                    ai.setNotAsserted("Not an HTTPs connection");
                }
                if (ai.isAsserted()) continue;
                throw new PolicyException(ai);
            }
        }
    }
}

