/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.security.cache.CXFEHCacheReplayCache;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.cache.ReplayCacheFactory;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.CryptoFactory;
import org.apache.wss4j.common.crypto.JasyptPasswordEncryptor;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;

public final class WSS4JUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(WSS4JUtils.class);
    private static final String DEFAULT_CONFIG_FILE = "cxf-ehcache.xml";

    private WSS4JUtils() {
    }

    public static long getSecurityTokenLifetime(Message message) {
        String tokenLifetime;
        if (message != null && (tokenLifetime = (String)message.getContextualProperty("ws-security.security.token.lifetime")) != null) {
            return Long.parseLong(tokenLifetime);
        }
        return 300000L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ReplayCache getReplayCache(SoapMessage message, String booleanKey, String instanceKey) {
        boolean specified = false;
        Object o = message.getContextualProperty(booleanKey);
        if (o != null) {
            if (!PropertyUtils.isTrue(o)) {
                return null;
            }
            specified = true;
        }
        if (!specified && MessageUtils.isRequestor(message)) {
            return null;
        }
        Endpoint ep = message.getExchange().getEndpoint();
        if (ep != null && ep.getEndpointInfo() != null) {
            EndpointInfo info;
            EndpointInfo endpointInfo = info = ep.getEndpointInfo();
            synchronized (endpointInfo) {
                ReplayCache replayCache = (ReplayCache)message.getContextualProperty(instanceKey);
                if (replayCache == null) {
                    replayCache = (ReplayCache)info.getProperty(instanceKey);
                }
                if (replayCache == null) {
                    URL configFile;
                    String cacheKey = instanceKey;
                    if (info.getName() != null) {
                        int hashcode = info.getName().toString().hashCode();
                        cacheKey = hashcode < 0 ? cacheKey + hashcode : cacheKey + "-" + hashcode;
                    }
                    if ((configFile = SecurityUtils.getConfigFileURL(message, "ws-security.cache.config.file", DEFAULT_CONFIG_FILE)) == null) {
                        configFile = Loader.getResource(WSS4JUtils.class.getClassLoader(), DEFAULT_CONFIG_FILE);
                    }
                    if (ReplayCacheFactory.isEhCacheInstalled()) {
                        Bus bus = message.getExchange().getBus();
                        replayCache = new CXFEHCacheReplayCache(cacheKey, bus, configFile);
                    } else {
                        ReplayCacheFactory replayCacheFactory = ReplayCacheFactory.newInstance();
                        replayCache = replayCacheFactory.newReplayCache(cacheKey, configFile);
                    }
                    info.setProperty(instanceKey, replayCache);
                }
                return replayCache;
            }
        }
        return null;
    }

    public static String parseAndStoreStreamingSecurityToken(org.apache.xml.security.stax.securityToken.SecurityToken securityToken, Message message) throws XMLSecurityException {
        if (securityToken == null) {
            return null;
        }
        SecurityToken existingToken = TokenStoreUtils.getTokenStore(message).getToken(securityToken.getId());
        if (existingToken == null || existingToken.isExpired()) {
            Instant created = Instant.now();
            Instant expires = created.plusSeconds(WSS4JUtils.getSecurityTokenLifetime(message) / 1000L);
            SecurityToken cachedTok = new SecurityToken(securityToken.getId(), created, expires);
            cachedTok.setSHA1(securityToken.getSha1Identifier());
            if (securityToken.getTokenType() != null) {
                if (securityToken.getTokenType() == WSSecurityTokenConstants.EncryptedKeyToken) {
                    cachedTok.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
                } else if (securityToken.getTokenType() == WSSecurityTokenConstants.KERBEROS_TOKEN) {
                    cachedTok.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ");
                } else if (securityToken.getTokenType() == WSSecurityTokenConstants.SAML_11_TOKEN) {
                    cachedTok.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");
                } else if (securityToken.getTokenType() == WSSecurityTokenConstants.SAML_20_TOKEN) {
                    cachedTok.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
                } else if (securityToken.getTokenType() == WSSecurityTokenConstants.SECURE_CONVERSATION_TOKEN || securityToken.getTokenType() == WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN) {
                    cachedTok.setTokenType("http://schemas.xmlsoap.org/ws/2005/02/sc");
                }
            }
            for (Map.Entry<String, Key> entry : securityToken.getSecretKey().entrySet()) {
                if (entry.getValue() == null) continue;
                cachedTok.setKey(entry.getValue());
                if (!(entry.getValue() instanceof SecretKey)) break;
                cachedTok.setSecret(entry.getValue().getEncoded());
                break;
            }
            TokenStoreUtils.getTokenStore(message).add(cachedTok);
            return cachedTok.getId();
        }
        return existingToken.getId();
    }

    public static SoapFault createSoapFault(SoapMessage message, SoapVersion version, WSSecurityException e) {
        SoapFault fault;
        String errorMessage = null;
        QName faultCode = null;
        boolean returnSecurityError = MessageUtils.getContextualBoolean(message, "ws-security.return.security.error", false);
        if (returnSecurityError || MessageUtils.isRequestor(message)) {
            errorMessage = e.getMessage();
            faultCode = e.getFaultCode();
        } else {
            errorMessage = e.getSafeExceptionMessage();
            faultCode = e.getSafeFaultCode();
        }
        if (version.getVersion() == 1.1 && faultCode != null) {
            fault = new SoapFault(errorMessage, (Throwable)e, faultCode);
        } else {
            fault = new SoapFault(errorMessage, (Throwable)e, version.getSender());
            if (version.getVersion() != 1.1 && faultCode != null) {
                fault.setSubCode(faultCode);
            }
        }
        return fault;
    }

    public static Properties getProps(Object o, URL propsURL) {
        Properties properties = null;
        if (o instanceof Properties) {
            properties = (Properties)o;
        } else if (propsURL != null) {
            try {
                properties = new Properties();
                try (InputStream ins = propsURL.openStream();){
                    properties.load(ins);
                }
            }
            catch (IOException e) {
                properties = null;
            }
        }
        return properties;
    }

    public static PasswordEncryptor getPasswordEncryptor(Message message) {
        if (message == null) {
            return null;
        }
        PasswordEncryptor passwordEncryptor = (PasswordEncryptor)message.getContextualProperty("ws-security.password.encryptor.instance");
        if (passwordEncryptor != null) {
            return passwordEncryptor;
        }
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", message);
        try {
            CallbackHandler callbackHandler = SecurityUtils.getCallbackHandler(o);
            if (callbackHandler != null) {
                return new JasyptPasswordEncryptor(callbackHandler);
            }
        }
        catch (Exception ex) {
            return null;
        }
        return null;
    }

    public static Crypto loadCryptoFromPropertiesFile(Message message, String propFilename, ClassLoader classLoader, PasswordEncryptor passwordEncryptor) throws WSSecurityException {
        block14: {
            try {
                URL url2 = SecurityUtils.loadResource(message, (Object)propFilename);
                if (url2 == null) break block14;
                Properties props = new Properties();
                try (InputStream in = url2.openStream();){
                    props.load(in);
                }
                return CryptoFactory.getInstance(props, classLoader, passwordEncryptor);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return CryptoFactory.getInstance(propFilename, classLoader);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Crypto getEncryptionCrypto(Object e, SoapMessage message, PasswordEncryptor passwordEncryptor) throws WSSecurityException {
        Crypto encrCrypto = null;
        if (e instanceof Crypto) {
            encrCrypto = (Crypto)e;
        } else if (e != null) {
            EndpointInfo info;
            URL propsURL = SecurityUtils.loadResource(message, e);
            Properties props = WSS4JUtils.getProps(e, propsURL);
            if (props == null) {
                LOG.fine("Cannot find Crypto Encryption properties: " + e);
                Exception ex = new Exception("Cannot find Crypto Encryption properties: " + e);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
            }
            encrCrypto = CryptoFactory.getInstance(props, Loader.getClassLoader(CryptoFactory.class), passwordEncryptor);
            EndpointInfo endpointInfo = info = message.getExchange().getEndpoint().getEndpointInfo();
            synchronized (endpointInfo) {
                info.setProperty("security.encryption.crypto", encrCrypto);
            }
        }
        return encrCrypto;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Crypto getSignatureCrypto(Object s2, SoapMessage message, PasswordEncryptor passwordEncryptor) throws WSSecurityException {
        Crypto signCrypto = null;
        if (s2 instanceof Crypto) {
            signCrypto = (Crypto)s2;
        } else if (s2 != null) {
            EndpointInfo info;
            URL propsURL = SecurityUtils.loadResource(message, s2);
            Properties props = WSS4JUtils.getProps(s2, propsURL);
            if (props == null) {
                LOG.fine("Cannot find Crypto Signature properties: " + s2);
                Exception ex = new Exception("Cannot find Crypto Signature properties: " + s2);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
            }
            signCrypto = CryptoFactory.getInstance(props, Loader.getClassLoader(CryptoFactory.class), passwordEncryptor);
            EndpointInfo endpointInfo = info = message.getExchange().getEndpoint().getEndpointInfo();
            synchronized (endpointInfo) {
                info.setProperty("security.signature.crypto", signCrypto);
            }
        }
        return signCrypto;
    }

    public static X509Certificate getReqSigCert(List<WSHandlerResult> results) {
        if (results == null || results.isEmpty()) {
            return null;
        }
        for (WSHandlerResult rResult : results) {
            List<WSSecurityEngineResult> signedResults = rResult.getActionResults().get(2);
            if (signedResults == null || signedResults.isEmpty()) continue;
            for (WSSecurityEngineResult signedResult : signedResults) {
                if (!signedResult.containsKey("x509-certificate")) continue;
                return (X509Certificate)signedResult.get("x509-certificate");
            }
        }
        return null;
    }
}

