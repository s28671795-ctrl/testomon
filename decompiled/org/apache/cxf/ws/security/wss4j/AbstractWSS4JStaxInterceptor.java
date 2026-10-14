/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapInterceptor;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.JasyptPasswordEncryptor;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.setup.ConfigurationConverter;

public abstract class AbstractWSS4JStaxInterceptor
implements SoapInterceptor,
PhaseInterceptor<SoapMessage> {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractWSS4JStaxInterceptor.class);
    private static final Set<QName> HEADERS = new HashSet<QName>();
    private final Map<String, Object> properties;
    private final WSSSecurityProperties userSecurityProperties;
    private Map<String, Crypto> cryptos = new ConcurrentHashMap<String, Crypto>();
    private final Set<String> before = new HashSet<String>();
    private final Set<String> after = new HashSet<String>();
    private String phase;
    private String id = this.getClass().getName();

    public AbstractWSS4JStaxInterceptor(WSSSecurityProperties securityProperties) {
        this.userSecurityProperties = securityProperties;
        this.properties = null;
    }

    public AbstractWSS4JStaxInterceptor(Map<String, Object> properties) {
        this.properties = properties;
        this.userSecurityProperties = null;
    }

    public AbstractWSS4JStaxInterceptor() {
        this.userSecurityProperties = null;
        this.properties = null;
    }

    protected WSSSecurityProperties createSecurityProperties() {
        if (this.userSecurityProperties != null) {
            return new WSSSecurityProperties(this.userSecurityProperties);
        }
        WSSSecurityProperties securityProperties = new WSSSecurityProperties();
        ConfigurationConverter.parseActions(this.properties, securityProperties);
        ConfigurationConverter.parseUserProperties(this.properties, securityProperties);
        ConfigurationConverter.parseCallback(this.properties, securityProperties);
        ConfigurationConverter.parseBooleanProperties(this.properties, securityProperties);
        ConfigurationConverter.parseNonBooleanProperties(this.properties, securityProperties);
        securityProperties.setSkipDocumentEvents(true);
        return securityProperties;
    }

    protected void translateProperties(SoapMessage msg, WSSSecurityProperties securityProperties) {
        String actor;
        String validateSAMLSubjectConf;
        String certConstraints;
        String utTTL;
        String utFutureTTL;
        String ttl;
        String futureTTL;
        String bspCompliant = (String)msg.getContextualProperty("ws-security.is-bsp-compliant");
        if (bspCompliant != null) {
            securityProperties.setDisableBSPEnforcement(Boolean.valueOf(bspCompliant) == false);
        }
        if ((futureTTL = (String)msg.getContextualProperty("ws-security.timestamp.futureTimeToLive")) != null) {
            securityProperties.setTimeStampFutureTTL(Integer.parseInt(futureTTL));
        }
        if ((ttl = (String)msg.getContextualProperty("ws-security.timestamp.timeToLive")) != null) {
            securityProperties.setTimestampTTL(Integer.parseInt(ttl));
        }
        if ((utFutureTTL = (String)msg.getContextualProperty("ws-security.usernametoken.futureTimeToLive")) != null) {
            securityProperties.setUtFutureTTL(Integer.parseInt(utFutureTTL));
        }
        if ((utTTL = (String)msg.getContextualProperty("ws-security.usernametoken.timeToLive")) != null) {
            securityProperties.setUtTTL(Integer.parseInt(utTTL));
        }
        if ((certConstraints = (String)SecurityUtils.getSecurityPropertyValue("security.subject.cert.constraints", msg)) != null && !"".equals(certConstraints)) {
            String certConstraintsSeparator = (String)SecurityUtils.getSecurityPropertyValue("security.cert.constraints.separator", msg);
            if (certConstraintsSeparator == null || certConstraintsSeparator.isEmpty()) {
                certConstraintsSeparator = ",";
            }
            securityProperties.setSubjectCertConstraints(this.convertCertConstraints(certConstraints, certConstraintsSeparator));
        }
        if ((validateSAMLSubjectConf = (String)SecurityUtils.getSecurityPropertyValue("security.validate.saml.subject.conf", msg)) != null) {
            securityProperties.setValidateSamlSubjectConfirmation(Boolean.valueOf(validateSAMLSubjectConf));
        }
        if ((actor = (String)msg.getContextualProperty("ws-security.actor")) != null) {
            securityProperties.setActor(actor);
        }
        boolean mustUnderstand = MessageUtils.getContextualBoolean(msg, "ws-security.must-understand", true);
        securityProperties.setMustUnderstand(mustUnderstand);
        boolean validateSchemas = MessageUtils.getContextualBoolean(msg, "schema-validation-enabled", false);
        securityProperties.setDisableSchemaValidation(!validateSchemas);
    }

    private Collection<Pattern> convertCertConstraints(String certConstraints, String separator) {
        String[] certConstraintsList = certConstraints.split(separator);
        if (certConstraintsList.length > 0) {
            ArrayList<Pattern> subjectCertConstraints = new ArrayList<Pattern>(certConstraintsList.length);
            for (String certConstraint : certConstraintsList) {
                try {
                    subjectCertConstraints.add(Pattern.compile(certConstraint.trim()));
                }
                catch (PatternSyntaxException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
            return subjectCertConstraints;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void configureCallbackHandler(SoapMessage soapMessage, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", soapMessage);
        CallbackHandler callbackHandler = null;
        try {
            callbackHandler = SecurityUtils.getCallbackHandler(o);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        if (callbackHandler != null) {
            EndpointInfo info;
            EndpointInfo endpointInfo = info = soapMessage.getExchange().getEndpoint().getEndpointInfo();
            synchronized (endpointInfo) {
                info.setProperty("security.callback-handler", callbackHandler);
            }
            soapMessage.getExchange().getEndpoint().put("security.callback-handler", callbackHandler);
            soapMessage.getExchange().put("security.callback-handler", callbackHandler);
        }
        if (callbackHandler == null) {
            final boolean outbound = MessageUtils.isOutbound(soapMessage);
            final String password = this.getPassword(soapMessage);
            final String signatureUser = (String)SecurityUtils.getSecurityPropertyValue("security.signature.username", soapMessage);
            final String signaturePassword = (String)SecurityUtils.getSecurityPropertyValue("security.signature.password", soapMessage);
            if (!StringUtils.isEmpty(password) || !StringUtils.isEmpty(signaturePassword)) {
                callbackHandler = new CallbackHandler(){

                    @Override
                    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                        for (Callback callback : callbacks) {
                            if (!(callback instanceof WSPasswordCallback)) continue;
                            WSPasswordCallback wsPasswordCallback = (WSPasswordCallback)callback;
                            if (signaturePassword != null && wsPasswordCallback.getIdentifier() != null && wsPasswordCallback.getIdentifier().equals(signatureUser) && outbound && wsPasswordCallback.getUsage() == 3 || !outbound && wsPasswordCallback.getUsage() == 1) {
                                wsPasswordCallback.setPassword(signaturePassword);
                                continue;
                            }
                            if (password == null) continue;
                            wsPasswordCallback.setPassword(password);
                        }
                    }
                };
            }
        }
        if (callbackHandler != null) {
            securityProperties.setCallbackHandler(callbackHandler);
        }
    }

    protected String getPassword(Object msgContext) {
        String password = (String)((Message)msgContext).getContextualProperty("password");
        if (password == null) {
            password = (String)((Message)msgContext).getContextualProperty("security.password");
        }
        return password;
    }

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

    public Object getOption(String key) {
        if (this.properties != null) {
            return this.properties.get(key);
        }
        return null;
    }

    public Object getProperty(Object msgContext, String key) {
        Object obj = SecurityUtils.getSecurityPropertyValue(key, (Message)msgContext);
        if (obj == null) {
            obj = this.getOption(key);
        }
        return obj;
    }

    public void setPassword(Object msgContext, String password) {
        ((Message)msgContext).put("password", password);
    }

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
        if (this.properties != null) {
            return this.properties;
        }
        return Collections.emptyMap();
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

    protected Crypto loadCrypto(SoapMessage soapMessage, String cryptoPropertyFile, String cryptoPropertyRefId, WSSSecurityProperties securityProperties) throws WSSecurityException {
        String propFile;
        Crypto crypto = null;
        String refId = (String)this.getProperty(soapMessage, cryptoPropertyRefId);
        if (refId != null) {
            crypto = this.cryptos.get(refId);
            if (crypto == null) {
                Object obj = this.getProperty(soapMessage, refId);
                if (obj instanceof Properties) {
                    crypto = CryptoFactory.getInstance((Properties)obj, this.getClassLoader(), this.getPasswordEncryptor(soapMessage, securityProperties));
                    this.cryptos.put(refId, crypto);
                } else if (obj instanceof Crypto) {
                    crypto = (Crypto)obj;
                    this.cryptos.put(refId, crypto);
                }
            }
            if (crypto == null && LOG.isLoggable(Level.INFO)) {
                LOG.info("The Crypto reference " + refId + " specified by " + cryptoPropertyRefId + " could not be loaded");
            }
        }
        if (crypto == null && (propFile = (String)this.getProperty(soapMessage, cryptoPropertyFile)) != null) {
            crypto = this.cryptos.get(propFile);
            if (crypto == null) {
                crypto = this.loadCryptoFromPropertiesFile(soapMessage, propFile, securityProperties);
                this.cryptos.put(propFile, crypto);
            }
            if (crypto == null && LOG.isLoggable(Level.INFO)) {
                LOG.info("The Crypto properties file " + propFile + " specified by " + cryptoPropertyFile + " could not be loaded or found");
            }
        }
        return crypto;
    }

    protected Crypto loadCryptoFromPropertiesFile(SoapMessage soapMessage, String propFilename, WSSSecurityProperties securityProperties) throws WSSecurityException {
        PasswordEncryptor passwordEncryptor = this.getPasswordEncryptor(soapMessage, securityProperties);
        return WSS4JUtils.loadCryptoFromPropertiesFile(soapMessage, propFilename, this.getClassLoader(), passwordEncryptor);
    }

    protected PasswordEncryptor getPasswordEncryptor(SoapMessage soapMessage, WSSSecurityProperties securityProperties) {
        PasswordEncryptor passwordEncryptor = (PasswordEncryptor)soapMessage.getContextualProperty("ws-security.password.encryptor.instance");
        if (passwordEncryptor != null) {
            return passwordEncryptor;
        }
        CallbackHandler callbackHandler = securityProperties.getCallbackHandler();
        if (callbackHandler == null) {
            callbackHandler = (CallbackHandler)this.getProperties().get("passwordCallbackRef");
        }
        if (callbackHandler != null) {
            return new JasyptPasswordEncryptor(callbackHandler);
        }
        return null;
    }

    protected Crypto getEncryptionCrypto(Object e, SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        PasswordEncryptor passwordEncryptor = this.getPasswordEncryptor(message, securityProperties);
        return WSS4JUtils.getEncryptionCrypto(e, message, passwordEncryptor);
    }

    protected Crypto getSignatureCrypto(Object s2, SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        PasswordEncryptor passwordEncryptor = this.getPasswordEncryptor(message, securityProperties);
        return WSS4JUtils.getSignatureCrypto(s2, message, passwordEncryptor);
    }

    private ClassLoader getClassLoader() {
        try {
            return Loader.getTCL();
        }
        catch (Exception ex) {
            return null;
        }
    }

    static {
        HEADERS.add(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security"));
        HEADERS.add(new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData"));
        HEADERS.add(new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader"));
    }
}

