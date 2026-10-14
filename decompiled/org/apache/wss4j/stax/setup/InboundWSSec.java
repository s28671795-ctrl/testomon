/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.setup;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.InboundWSSecurityContextImpl;
import org.apache.wss4j.stax.impl.WSSecurityStreamReader;
import org.apache.wss4j.stax.impl.processor.input.OperationInputProcessor;
import org.apache.wss4j.stax.impl.processor.input.SecurityHeaderInputProcessor;
import org.apache.wss4j.stax.impl.processor.input.SignatureConfirmationInputProcessor;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessor;
import org.apache.xml.security.stax.impl.DocumentContextImpl;
import org.apache.xml.security.stax.impl.InputProcessorChainImpl;
import org.apache.xml.security.stax.impl.processor.input.LogInputProcessor;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboundWSSec {
    protected static final transient Logger LOG = LoggerFactory.getLogger(InboundWSSec.class);
    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();
    private final WSSSecurityProperties securityProperties;
    private final boolean initiator;
    private final boolean returnSecurityError;

    public InboundWSSec(WSSSecurityProperties securityProperties) {
        this(securityProperties, false, false);
    }

    public InboundWSSec(WSSSecurityProperties securityProperties, boolean initiator, boolean returnSecurityError) {
        this.securityProperties = securityProperties;
        this.initiator = initiator;
        this.returnSecurityError = returnSecurityError;
    }

    public XMLStreamReader processInMessage(XMLStreamReader xmlStreamReader) throws XMLStreamException, WSSecurityException {
        return this.processInMessage(xmlStreamReader, null, (SecurityEventListener)null);
    }

    public XMLStreamReader processInMessage(XMLStreamReader xmlStreamReader, List<SecurityEvent> requestSecurityEvents) throws XMLStreamException, WSSecurityException {
        return this.processInMessage(xmlStreamReader, requestSecurityEvents, (SecurityEventListener)null);
    }

    public XMLStreamReader processInMessage(XMLStreamReader xmlStreamReader, List<SecurityEvent> requestSecurityEvents, SecurityEventListener securityEventListener) throws XMLStreamException, WSSecurityException {
        return this.processInMessage(xmlStreamReader, requestSecurityEvents, Collections.singletonList(securityEventListener));
    }

    public XMLStreamReader processInMessage(XMLStreamReader xmlStreamReader, List<SecurityEvent> requestSecurityEvents, List<SecurityEventListener> securityEventListeners) throws XMLStreamException, WSSecurityException {
        List<InputProcessor> additionalInputProcessors;
        if (requestSecurityEvents == null) {
            requestSecurityEvents = Collections.emptyList();
        }
        InboundWSSecurityContextImpl securityContextImpl = new InboundWSSecurityContextImpl();
        securityContextImpl.putList(SecurityEvent.class, requestSecurityEvents);
        if (securityEventListeners != null) {
            for (SecurityEventListener securityEventListener : securityEventListeners) {
                securityContextImpl.addSecurityEventListener(securityEventListener);
            }
        }
        securityContextImpl.ignoredBSPRules(this.securityProperties.getIgnoredBSPRules());
        securityContextImpl.setDisableBSPEnforcement(this.securityProperties.isDisableBSPEnforcement());
        securityContextImpl.setAllowRSA15KeyTransportAlgorithm(this.securityProperties.isAllowRSA15KeyTransportAlgorithm());
        if (!requestSecurityEvents.isEmpty()) {
            try {
                for (SecurityEvent securityEvent : requestSecurityEvents) {
                    if (!(securityEvent instanceof TokenSecurityEvent)) continue;
                    final TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
                    if (WSSecurityEventConstants.HTTPS_TOKEN.equals(securityEvent.getSecurityEventType())) {
                        securityContextImpl.registerSecurityEvent(securityEvent);
                        securityContextImpl.put("transportSecurityActive", Boolean.TRUE);
                    }
                    SecurityTokenProvider<InboundSecurityToken> securityTokenProvider = new SecurityTokenProvider<InboundSecurityToken>(){
                        private String id;

                        @Override
                        public InboundSecurityToken getSecurityToken() throws XMLSecurityException {
                            return (InboundSecurityToken)tokenSecurityEvent.getSecurityToken();
                        }

                        @Override
                        public String getId() {
                            if (this.id == null) {
                                this.id = ((InboundSecurityToken)tokenSecurityEvent.getSecurityToken()).getId();
                                if (this.id == null) {
                                    this.id = IDGenerator.generateID(null);
                                }
                            }
                            return this.id;
                        }
                    };
                    securityContextImpl.registerSecurityTokenProvider(securityTokenProvider.getId(), (SecurityTokenProvider<? extends InboundSecurityToken>)securityTokenProvider);
                }
            }
            catch (XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)e);
            }
        }
        securityContextImpl.put("XMLInputFactory", XML_INPUT_FACTORY);
        DocumentContextImpl documentContext = new DocumentContextImpl();
        documentContext.setEncoding(xmlStreamReader.getEncoding() != null ? xmlStreamReader.getEncoding() : StandardCharsets.UTF_8.name());
        InputProcessorChainImpl inputProcessorChain = new InputProcessorChainImpl((InboundSecurityContext)securityContextImpl, documentContext);
        inputProcessorChain.addProcessor(new XMLEventReaderInputProcessor(this.securityProperties, xmlStreamReader));
        inputProcessorChain.addProcessor(new SecurityHeaderInputProcessor(this.securityProperties));
        inputProcessorChain.addProcessor(new OperationInputProcessor(this.securityProperties));
        if (this.securityProperties.isEnableSignatureConfirmationVerification()) {
            inputProcessorChain.addProcessor(new SignatureConfirmationInputProcessor(this.securityProperties));
        }
        if (LOG.isTraceEnabled()) {
            LogInputProcessor logInputProcessor = new LogInputProcessor(this.securityProperties);
            logInputProcessor.addAfterProcessor(SecurityHeaderInputProcessor.class.getName());
            inputProcessorChain.addProcessor(logInputProcessor);
        }
        if (!(additionalInputProcessors = this.securityProperties.getInputProcessorList()).isEmpty()) {
            for (InputProcessor inputProcessor : additionalInputProcessors) {
                inputProcessorChain.addProcessor(inputProcessor);
            }
        }
        return new WSSecurityStreamReader(inputProcessorChain, this.securityProperties, this.initiator, this.returnSecurityError);
    }

    static {
        XML_INPUT_FACTORY.setProperty("javax.xml.stream.supportDTD", false);
        XML_INPUT_FACTORY.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
        try {
            XML_INPUT_FACTORY.setProperty("org.codehaus.stax2.internNames", true);
            XML_INPUT_FACTORY.setProperty("org.codehaus.stax2.internNsUris", true);
            XML_INPUT_FACTORY.setProperty("org.codehaus.stax2.preserveLocation", false);
        }
        catch (IllegalArgumentException e) {
            LOG.debug(e.getMessage(), e);
        }
    }
}

