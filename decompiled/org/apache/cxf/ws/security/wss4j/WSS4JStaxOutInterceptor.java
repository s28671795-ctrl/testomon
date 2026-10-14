/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.wss4j.AbstractWSS4JStaxInterceptor;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.ThreadLocalSecurityProvider;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.setup.ConfigurationConverter;
import org.apache.wss4j.stax.setup.OutboundWSSec;
import org.apache.wss4j.stax.setup.WSSec;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.impl.OutboundSecurityContextImpl;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;

public class WSS4JStaxOutInterceptor
extends AbstractWSS4JStaxInterceptor {
    public static final String OUTPUT_STREAM_HOLDER = WSS4JStaxOutInterceptor.class.getName() + ".outputstream";
    private static final Logger LOG = LogUtils.getL7dLogger(WSS4JStaxOutInterceptor.class);
    private WSS4JStaxOutInterceptorInternal ending;
    private boolean mtomEnabled;

    public WSS4JStaxOutInterceptor(WSSSecurityProperties securityProperties) {
        super(securityProperties);
        WSSec.init();
        this.setPhase("pre-stream");
        this.getBefore().add(StaxOutInterceptor.class.getName());
        this.getAfter().add("org.apache.cxf.interceptor.LoggingOutInterceptor");
        this.getAfter().add("org.apache.cxf.ext.logging.LoggingOutInterceptor");
        this.ending = this.createEndingInterceptor();
    }

    public WSS4JStaxOutInterceptor(Map<String, Object> props) {
        super(props);
        WSSec.init();
        this.setPhase("pre-stream");
        this.getBefore().add(StaxOutInterceptor.class.getName());
        this.getAfter().add("org.apache.cxf.interceptor.LoggingOutInterceptor");
        this.getAfter().add("org.apache.cxf.ext.logging.LoggingOutInterceptor");
        this.ending = this.createEndingInterceptor();
    }

    public WSS4JStaxOutInterceptor() {
        WSSec.init();
        this.setPhase("pre-stream");
        this.getBefore().add(StaxOutInterceptor.class.getName());
        this.getAfter().add("org.apache.cxf.interceptor.LoggingOutInterceptor");
        this.getAfter().add("org.apache.cxf.ext.logging.LoggingOutInterceptor");
        this.ending = this.createEndingInterceptor();
    }

    public boolean isAllowMTOM() {
        return this.mtomEnabled;
    }

    public void setAllowMTOM(boolean allowMTOM) {
        this.mtomEnabled = allowMTOM;
    }

    protected void handleSecureMTOM(SoapMessage mc, WSSSecurityProperties secProps) {
        if (this.mtomEnabled) {
            return;
        }
        String mtomKey = "mtom-enabled";
        if (Boolean.TRUE.equals(mc.get(mtomKey))) {
            LOG.warning("MTOM will be disabled as the WSS4JOutInterceptor.mtomEnabled property is set to false");
        }
        mc.put(mtomKey, (Object)Boolean.FALSE);
    }

    @Override
    public void handleMessage(SoapMessage mc) throws Fault {
        XMLStreamWriter newXMLStreamWriter;
        OutputStream os = mc.getContent(OutputStream.class);
        if (os == null) {
            String error = "The message outputstream is null - Multiple WSS4JStaxOutInterceptor instances are not supported";
            WSSecurityException exception = new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{error});
            LOG.warning(error);
            throw new Fault(exception);
        }
        String encoding = this.getEncoding(mc);
        try {
            WSSSecurityProperties secProps = this.createSecurityProperties();
            this.translateProperties(mc, secProps);
            this.configureCallbackHandler(mc, secProps);
            OutboundSecurityContextImpl outboundSecurityContext = new OutboundSecurityContextImpl();
            this.configureProperties(mc, outboundSecurityContext, secProps);
            if (secProps.getActions() == null || secProps.getActions().isEmpty()) {
                return;
            }
            this.handleSecureMTOM(mc, secProps);
            if (secProps.getAttachmentCallbackHandler() == null) {
                secProps.setAttachmentCallbackHandler(new AttachmentCallbackHandler(mc));
            }
            SecurityEventListener securityEventListener = this.configureSecurityEventListener(mc, secProps);
            OutboundWSSec outboundWSSec = WSSec.getOutboundWSSec(secProps);
            List requestSecurityEvents = (List)mc.getExchange().get(SecurityEvent.class.getName() + ".in");
            outboundSecurityContext.putList(SecurityEvent.class, requestSecurityEvents);
            outboundSecurityContext.addSecurityEventListener(securityEventListener);
            newXMLStreamWriter = outboundWSSec.processOutMessage((Object)os, encoding, (OutboundSecurityContext)outboundSecurityContext);
            mc.setContent(XMLStreamWriter.class, newXMLStreamWriter);
        }
        catch (WSSPolicyException | WSSecurityException e) {
            throw new Fault(e);
        }
        mc.put("disable.outputstream.optimization", (Object)Boolean.TRUE);
        try {
            newXMLStreamWriter.writeStartDocument(encoding, "1.0");
        }
        catch (XMLStreamException e) {
            throw new Fault(e);
        }
        mc.removeContent(OutputStream.class);
        mc.put(OUTPUT_STREAM_HOLDER, (Object)os);
        mc.getInterceptorChain().add(this.ending);
    }

    protected SecurityEventListener configureSecurityEventListener(final SoapMessage msg, WSSSecurityProperties securityProperties) throws WSSPolicyException {
        final LinkedList outgoingSecurityEventList = new LinkedList();
        msg.getExchange().put(SecurityEvent.class.getName() + ".out", outgoingSecurityEventList);
        msg.put(SecurityEvent.class.getName() + ".out", (Object)outgoingSecurityEventList);
        return new SecurityEventListener(){

            @Override
            public void registerSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
                if (securityEvent.getSecurityEventType() == WSSecurityEventConstants.SAML_TOKEN) {
                    TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
                    WSS4JUtils.parseAndStoreStreamingSecurityToken(tokenSecurityEvent.getSecurityToken(), msg);
                } else if (securityEvent.getSecurityEventType() == WSSecurityEventConstants.SignatureValue) {
                    outgoingSecurityEventList.add(securityEvent);
                }
            }
        };
    }

    protected void configureProperties(SoapMessage msg, OutboundSecurityContext outboundSecurityContext, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Map<String, Object> config;
        String encUser;
        String sigUser;
        String user = (String)SecurityUtils.getSecurityPropertyValue("security.username", msg);
        if (user != null) {
            securityProperties.setTokenUser(user);
        }
        if ((sigUser = (String)SecurityUtils.getSecurityPropertyValue("security.signature.username", msg)) != null) {
            securityProperties.setSignatureUser(sigUser);
        }
        if ((encUser = (String)SecurityUtils.getSecurityPropertyValue("security.encryption.username", msg)) != null) {
            securityProperties.setEncryptionUser(encUser);
        }
        if ((config = this.getProperties()) != null && !config.isEmpty()) {
            Crypto encCrypto;
            Crypto sigCrypto = this.loadCrypto(msg, "signaturePropFile", "signaturePropRefId", securityProperties);
            if (sigCrypto != null) {
                config.put("signaturePropRefId", "RefId-" + sigCrypto.hashCode());
                config.put("RefId-" + sigCrypto.hashCode(), sigCrypto);
                if (sigUser == null && sigCrypto.getDefaultX509Identifier() != null) {
                    securityProperties.setSignatureUser(sigCrypto.getDefaultX509Identifier());
                }
            }
            if ((encCrypto = this.loadCrypto(msg, "encryptionPropFile", "encryptionPropRefId", securityProperties)) != null) {
                config.put("encryptionPropRefId", "RefId-" + encCrypto.hashCode());
                config.put("RefId-" + encCrypto.hashCode(), encCrypto);
                if (encUser == null && encCrypto.getDefaultX509Identifier() != null) {
                    securityProperties.setEncryptionUser(encCrypto.getDefaultX509Identifier());
                }
            }
            ConfigurationConverter.parseCrypto(config, securityProperties);
        } else {
            Crypto encrCrypto;
            Crypto sigCrypto = securityProperties.getSignatureCrypto();
            if (sigCrypto != null && sigUser == null && sigCrypto.getDefaultX509Identifier() != null) {
                securityProperties.setSignatureUser(sigCrypto.getDefaultX509Identifier());
            }
            if ((encrCrypto = securityProperties.getEncryptionCrypto()) != null && encUser == null && encrCrypto.getDefaultX509Identifier() != null) {
                securityProperties.setEncryptionUser(encrCrypto.getDefaultX509Identifier());
            }
        }
        if (securityProperties.getSignatureUser() == null && user != null) {
            securityProperties.setSignatureUser(user);
        }
        if (securityProperties.getEncryptionUser() == null && user != null) {
            securityProperties.setEncryptionUser(user);
        }
    }

    public final WSS4JStaxOutInterceptorInternal createEndingInterceptor() {
        return new WSS4JStaxOutInterceptorInternal();
    }

    private String getEncoding(Message message) {
        Exchange ex = message.getExchange();
        String encoding = (String)message.get(Message.ENCODING);
        if (encoding == null && ex.getInMessage() != null) {
            encoding = (String)ex.getInMessage().get(Message.ENCODING);
            message.put(Message.ENCODING, encoding);
        }
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.name();
            message.put(Message.ENCODING, encoding);
        }
        return encoding;
    }

    final class WSS4JStaxOutInterceptorInternal
    extends AbstractPhaseInterceptor<Message> {
        WSS4JStaxOutInterceptorInternal() {
            super("pre-stream-ending");
            this.getBefore().add(AttachmentOutInterceptor.AttachmentOutEndingInterceptor.class.getName());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void handleMessage(Message message) throws Fault {
            Provider provider = message.getExchange().get(Provider.class);
            boolean useCustomProvider = provider != null && ThreadLocalSecurityProvider.isInstalled();
            try {
                if (useCustomProvider) {
                    ThreadLocalSecurityProvider.setProvider(provider);
                }
                this.handleMessageInternal(message);
            }
            finally {
                if (useCustomProvider) {
                    ThreadLocalSecurityProvider.unsetProvider();
                }
            }
        }

        private void handleMessageInternal(Message mc) throws Fault {
            try {
                OutputStream os;
                XMLStreamWriter xtw = mc.getContent(XMLStreamWriter.class);
                if (xtw != null) {
                    xtw.writeEndDocument();
                    xtw.flush();
                    xtw.close();
                }
                if ((os = (OutputStream)mc.get(OUTPUT_STREAM_HOLDER)) != null) {
                    mc.setContent(OutputStream.class, os);
                }
                mc.removeContent(XMLStreamWriter.class);
            }
            catch (XMLStreamException e) {
                throw new Fault(e);
            }
        }
    }
}

