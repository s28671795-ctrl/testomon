/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;

public abstract class AbstractWSS4JInterceptor
extends WSHandler
implements SoapInterceptor,
PhaseInterceptor<SoapMessage> {
    private static final Set<QName> HEADERS = new HashSet<QName>();
    private Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
    private final Set<String> before = new HashSet<String>();
    private final Set<String> after = new HashSet<String>();
    private String phase;
    private String id = this.getClass().getName();

    @Override
    public Set<URI> getRoles() {
        return null;
    }

    @Override
    public void handleFault(SoapMessage message) {
    }

    public void postHandleMessage(SoapMessage message) throws Fault {
    }

    @Override
    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
        return null;
    }

    @Override
    public String getPhase() {
        return this.phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @Override
    public Object getOption(String key) {
        return this.properties.get(key);
    }

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    @Override
    public String getPassword(Object msgContext) {
        return (String)((Message)msgContext).getContextualProperty("password");
    }

    @Override
    public Object getProperty(Object msgContext, String key) {
        if (msgContext == null) {
            return null;
        }
        Object obj = SecurityUtils.getSecurityPropertyValue(key, (Message)msgContext);
        if (obj == null) {
            obj = this.getOption(key);
        }
        return obj;
    }

    @Override
    public void setPassword(Object msgContext, String password) {
        ((Message)msgContext).put("password", password);
    }

    @Override
    public void setProperty(Object msgContext, String key, Object value) {
        ((Message)msgContext).put(key, value);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Set<QName> getUnderstoodHeaders() {
        return HEADERS;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public Set<String> getAfter() {
        return this.after;
    }

    @Override
    public Set<String> getBefore() {
        return this.before;
    }

    protected boolean isRequestor(SoapMessage message) {
        return MessageUtils.isRequestor(message);
    }

    protected void translateProperties(SoapMessage msg) {
        String certConstraintsSeparator;
        String certConstraints;
        String utTTL;
        String utFutureTTL;
        String ttl;
        String futureTTL;
        String bspCompliant = (String)msg.getContextualProperty("ws-security.is-bsp-compliant");
        if (bspCompliant != null) {
            msg.put("isBSPCompliant", (Object)bspCompliant);
        }
        if ((futureTTL = (String)msg.getContextualProperty("ws-security.timestamp.futureTimeToLive")) != null) {
            msg.put("futureTimeToLive", (Object)futureTTL);
        }
        if ((ttl = (String)msg.getContextualProperty("ws-security.timestamp.timeToLive")) != null) {
            msg.put("timeToLive", (Object)ttl);
        }
        if ((utFutureTTL = (String)msg.getContextualProperty("ws-security.usernametoken.futureTimeToLive")) != null) {
            msg.put("utFutureTimeToLive", (Object)utFutureTTL);
        }
        if ((utTTL = (String)msg.getContextualProperty("ws-security.usernametoken.timeToLive")) != null) {
            msg.put("utTimeToLive", (Object)utTTL);
        }
        if ((certConstraints = (String)SecurityUtils.getSecurityPropertyValue("security.subject.cert.constraints", msg)) != null) {
            msg.put("sigSubjectCertConstraints", (Object)certConstraints);
        }
        if ((certConstraintsSeparator = (String)SecurityUtils.getSecurityPropertyValue("security.cert.constraints.separator", msg)) != null && !certConstraintsSeparator.isEmpty()) {
            msg.put("sigCertConstraintsSeparator", (Object)certConstraintsSeparator);
        }
        String valSAMLSubjectConf = (String)SecurityUtils.getSecurityPropertyValue("security.validate.saml.subject.conf", msg);
        boolean validateSAMLSubjectConf = true;
        if (valSAMLSubjectConf != null) {
            validateSAMLSubjectConf = Boolean.parseBoolean(valSAMLSubjectConf);
        }
        msg.put("validateSamlSubjectConfirmation", (Object)Boolean.toString(validateSAMLSubjectConf));
        PasswordEncryptor passwordEncryptor = (PasswordEncryptor)msg.getContextualProperty("ws-security.password.encryptor.instance");
        if (passwordEncryptor != null) {
            msg.put("passwordEncryptorInstance", (Object)passwordEncryptor);
        }
    }

    @Override
    protected Crypto loadCryptoFromPropertiesFile(String propFilename, RequestData reqData) throws WSSecurityException {
        Message message = (Message)reqData.getMsgContext();
        ClassLoader classLoader = this.getClassLoader(reqData.getMsgContext());
        PasswordEncryptor passwordEncryptor = this.getPasswordEncryptor(reqData);
        return WSS4JUtils.loadCryptoFromPropertiesFile(message, propFilename, classLoader, passwordEncryptor);
    }

    static {
        HEADERS.add(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security"));
        HEADERS.add(new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData"));
        HEADERS.add(new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader"));
    }
}

