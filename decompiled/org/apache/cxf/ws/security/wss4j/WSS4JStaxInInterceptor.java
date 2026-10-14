/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Provider;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.StartBodyInterceptor;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JStaxInterceptor;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.StaxActionInInterceptor;
import org.apache.cxf.ws.security.wss4j.StaxSecurityContextInInterceptor;
import org.apache.cxf.ws.security.wss4j.TokenStoreCallbackHandler;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.ThreadLocalSecurityProvider;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.setup.ConfigurationConverter;
import org.apache.wss4j.stax.setup.InboundWSSec;
import org.apache.wss4j.stax.setup.WSSec;
import org.apache.wss4j.stax.validate.Validator;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;

public class WSS4JStaxInInterceptor
extends AbstractWSS4JStaxInterceptor {
    public static final String SECURITY_PROCESSED = WSS4JStaxInInterceptor.class.getName() + ".DONE";
    private static final Logger LOG = LogUtils.getL7dLogger(WSS4JStaxInInterceptor.class);

    public WSS4JStaxInInterceptor(WSSSecurityProperties securityProperties) {
        super(securityProperties);
        WSSec.init();
        this.setPhase("post-stream");
        this.getAfter().add(StaxInInterceptor.class.getName());
    }

    public WSS4JStaxInInterceptor(Map<String, Object> props) {
        super(props);
        WSSec.init();
        this.setPhase("post-stream");
        this.getAfter().add(StaxInInterceptor.class.getName());
    }

    public WSS4JStaxInInterceptor() {
        WSSec.init();
        this.setPhase("post-stream");
        this.getAfter().add(StaxInInterceptor.class.getName());
    }

    public final boolean isGET(SoapMessage message) {
        String method2 = (String)message.get("org.apache.cxf.request.method");
        return "GET".equals(method2) && message.getContent(XMLStreamReader.class) == null;
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        if (soapMessage.containsKey(SECURITY_PROCESSED) || this.isGET(soapMessage)) {
            return;
        }
        soapMessage.getInterceptorChain().add(new StaxStartBodyInterceptor());
        XMLStreamReader originalXmlStreamReader = soapMessage.getContent(XMLStreamReader.class);
        soapMessage.getInterceptorChain().add(new StaxSecurityContextInInterceptor());
        try {
            List requestSecurityEvents = (List)soapMessage.getExchange().get(SecurityEvent.class.getName() + ".out");
            WSSSecurityProperties secProps = this.createSecurityProperties();
            this.translateProperties(soapMessage, secProps);
            this.configureCallbackHandler(soapMessage, secProps);
            this.configureProperties(soapMessage, secProps);
            if (secProps.getActions() != null && secProps.getActions().size() > 0) {
                soapMessage.getInterceptorChain().add(new StaxActionInInterceptor(secProps.getActions()));
            }
            if (secProps.getAttachmentCallbackHandler() == null) {
                secProps.setAttachmentCallbackHandler(new AttachmentCallbackHandler(soapMessage));
            }
            TokenStoreCallbackHandler callbackHandler = new TokenStoreCallbackHandler(secProps.getCallbackHandler(), TokenStoreUtils.getTokenStore(soapMessage));
            secProps.setCallbackHandler(callbackHandler);
            this.setTokenValidators(secProps, soapMessage);
            secProps.setMsgContext(soapMessage);
            List<SecurityEventListener> securityEventListeners = this.configureSecurityEventListeners(soapMessage, secProps);
            boolean returnSecurityError = MessageUtils.getContextualBoolean(soapMessage, "ws-security.return.security.error", false);
            InboundWSSec inboundWSSec = WSSec.getInboundWSSec(secProps, MessageUtils.isRequestor(soapMessage), returnSecurityError);
            XMLStreamReader newXmlStreamReader = inboundWSSec.processInMessage(originalXmlStreamReader, (List<SecurityEvent>)requestSecurityEvents, securityEventListeners);
            final Provider provider = soapMessage.getExchange().get(Provider.class);
            if (provider != null && ThreadLocalSecurityProvider.isInstalled()) {
                newXmlStreamReader = new StreamReaderDelegate(newXmlStreamReader){

                    @Override
                    public int next() throws XMLStreamException {
                        try {
                            ThreadLocalSecurityProvider.setProvider((Provider)provider);
                            int n = super.next();
                            return n;
                        }
                        finally {
                            ThreadLocalSecurityProvider.unsetProvider();
                        }
                    }
                };
            }
            soapMessage.setContent(XMLStreamReader.class, newXmlStreamReader);
            soapMessage.put(SECURITY_PROCESSED, (Object)Boolean.TRUE);
        }
        catch (WSSecurityException e) {
            throw WSS4JUtils.createSoapFault(soapMessage, soapMessage.getVersion(), e);
        }
        catch (XMLSecurityException e) {
            throw new SoapFault(new Message("STAX_EX", LOG, new Object[0]), (Throwable)e, soapMessage.getVersion().getSender());
        }
        catch (WSSPolicyException e) {
            throw new SoapFault(e.getMessage(), (Throwable)e, soapMessage.getVersion().getSender());
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new Message("STAX_EX", LOG, new Object[0]), (Throwable)e, soapMessage.getVersion().getSender());
        }
    }

    protected List<SecurityEventListener> configureSecurityEventListeners(SoapMessage msg, WSSSecurityProperties securityProperties) throws WSSPolicyException {
        final LinkedList incomingSecurityEventList = new LinkedList();
        msg.getExchange().put(SecurityEvent.class.getName() + ".in", incomingSecurityEventList);
        msg.put(SecurityEvent.class.getName() + ".in", (Object)incomingSecurityEventList);
        SecurityEventListener securityEventListener = new SecurityEventListener(){

            @Override
            public void registerSecurityEvent(SecurityEvent securityEvent) throws WSSecurityException {
                if (securityEvent.getSecurityEventType() != WSSecurityEventConstants.AlgorithmSuite) {
                    incomingSecurityEventList.add(securityEvent);
                }
            }
        };
        return Collections.singletonList(securityEventListener);
    }

    protected void configureProperties(SoapMessage msg, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        ReplayCache nonceCache = null;
        if (this.isNonceCacheRequired(msg, securityProperties)) {
            nonceCache = WSS4JUtils.getReplayCache(msg, "ws-security.enable.nonce.cache", "ws-security.nonce.cache.instance");
        }
        securityProperties.setNonceReplayCache(nonceCache);
        ReplayCache timestampCache = null;
        if (this.isTimestampCacheRequired(msg, securityProperties)) {
            timestampCache = WSS4JUtils.getReplayCache(msg, "ws-security.enable.timestamp.cache", "ws-security.timestamp.cache.instance");
        }
        securityProperties.setTimestampReplayCache(timestampCache);
        ReplayCache samlCache = null;
        if (this.isSamlCacheRequired(msg, securityProperties)) {
            samlCache = WSS4JUtils.getReplayCache(msg, "ws-security.enable.saml.cache", "ws-security.saml.cache.instance");
        }
        securityProperties.setSamlOneTimeUseReplayCache(samlCache);
        boolean enableRevocation = PropertyUtils.isTrue(SecurityUtils.getSecurityPropertyValue("security.enableRevocation", msg));
        securityProperties.setEnableRevocation(enableRevocation);
        Map<String, Object> config = this.getProperties();
        if (config != null && !config.isEmpty()) {
            Crypto decCrypto;
            Crypto sigVerCrypto = this.loadCrypto(msg, "signatureVerificationPropFile", "signatureVerificationPropRefId", securityProperties);
            if (sigVerCrypto == null) {
                sigVerCrypto = this.loadCrypto(msg, "signaturePropFile", "signaturePropRefId", securityProperties);
            }
            if (sigVerCrypto != null) {
                config.put("signatureVerificationPropRefId", "RefId-" + sigVerCrypto.hashCode());
                config.put("RefId-" + sigVerCrypto.hashCode(), sigVerCrypto);
            }
            if ((decCrypto = this.loadCrypto(msg, "decryptionPropFile", "decryptionPropRefId", securityProperties)) != null) {
                config.put("decryptionPropRefId", "RefId-" + decCrypto.hashCode());
                config.put("RefId-" + decCrypto.hashCode(), decCrypto);
            }
            ConfigurationConverter.parseCrypto(config, securityProperties);
        }
        securityProperties.setAudienceRestrictions(SAMLUtils.getAudienceRestrictions(msg, true));
    }

    protected boolean isNonceCacheRequired(SoapMessage msg, WSSSecurityProperties securityProperties) {
        if (securityProperties != null && securityProperties.getActions() != null) {
            for (XMLSecurityConstants.Action action : securityProperties.getActions()) {
                if (action != WSSConstants.USERNAMETOKEN) continue;
                return true;
            }
        }
        return false;
    }

    protected boolean isTimestampCacheRequired(SoapMessage msg, WSSSecurityProperties securityProperties) {
        if (securityProperties != null && securityProperties.getActions() != null) {
            for (XMLSecurityConstants.Action action : securityProperties.getActions()) {
                if (action != WSSConstants.TIMESTAMP) continue;
                return true;
            }
        }
        return false;
    }

    protected boolean isSamlCacheRequired(SoapMessage msg, WSSSecurityProperties securityProperties) {
        if (securityProperties != null && securityProperties.getActions() != null) {
            for (XMLSecurityConstants.Action action : securityProperties.getActions()) {
                if (action != WSSConstants.SAML_TOKEN_UNSIGNED && action != WSSConstants.SAML_TOKEN_SIGNED) continue;
                return true;
            }
        }
        return false;
    }

    private void setTokenValidators(WSSSecurityProperties properties, SoapMessage message) throws WSSecurityException {
        Validator validator = this.loadValidator("ws-security.saml1.validator", message);
        if (validator != null) {
            properties.addValidator(WSSConstants.TAG_SAML_ASSERTION, validator);
        }
        if ((validator = this.loadValidator("ws-security.saml2.validator", message)) != null) {
            properties.addValidator(WSSConstants.TAG_SAML2_ASSERTION, validator);
        }
        if ((validator = this.loadValidator("ws-security.ut.validator", message)) != null) {
            properties.addValidator(WSSConstants.TAG_WSSE_USERNAME_TOKEN, validator);
        }
        if ((validator = this.loadValidator("ws-security.signature.validator", message)) != null) {
            properties.addValidator(XMLSecurityConstants.TAG_dsig_Signature, validator);
        }
        if ((validator = this.loadValidator("ws-security.timestamp.validator", message)) != null) {
            properties.addValidator(WSSConstants.TAG_WSU_TIMESTAMP, validator);
        }
        if ((validator = this.loadValidator("ws-security.bst.validator", message)) != null) {
            properties.addValidator(WSSConstants.TAG_WSSE_BINARY_SECURITY_TOKEN, validator);
        }
        if ((validator = this.loadValidator("ws-security.sct.validator", message)) != null) {
            properties.addValidator(WSSConstants.TAG_WSC0502_SCT, validator);
            properties.addValidator(WSSConstants.TAG_WSC0512_SCT, validator);
        }
    }

    private Validator loadValidator(String validatorKey, SoapMessage message) throws WSSecurityException {
        Object o = message.getContextualProperty(validatorKey);
        if (o == null) {
            return null;
        }
        try {
            if (o instanceof Validator) {
                return (Validator)o;
            }
            if (o instanceof Class) {
                return (Validator)((Class)o).newInstance();
            }
            if (o instanceof String) {
                return (Validator)ClassLoaderUtils.loadClass(o.toString(), WSS4JStaxInInterceptor.class).newInstance();
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "Cannot load Validator: " + o);
        }
        catch (RuntimeException t2) {
            throw t2;
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
    }

    private class StaxStartBodyInterceptor
    extends AbstractSoapInterceptor {
        StaxStartBodyInterceptor() {
            super("read");
            super.addAfter(StartBodyInterceptor.class.getName());
        }

        StaxStartBodyInterceptor(String phase) {
            super(phase);
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            if (this.isGET(message)) {
                LOG.fine("StartBodyInterceptor skipped in HTTP GET method");
                return;
            }
            XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
            try {
                int i = xmlReader.getEventType();
                while (i == 13 || i == 10 || i == 4) {
                    i = xmlReader.next();
                }
            }
            catch (XMLStreamException e) {
                throw new SoapFault(new Message("XML_STREAM_EXC", LOG, e.getMessage()), (Throwable)e, message.getVersion().getSender());
            }
        }
    }
}

