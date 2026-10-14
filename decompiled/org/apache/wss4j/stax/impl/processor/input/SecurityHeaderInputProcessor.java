/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.NoSecuritySecurityEvent;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.SecurityHeaderHandlerMapper;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.processor.input.XMLEventReaderInputProcessor;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityHeaderInputProcessor
extends AbstractInputProcessor {
    protected static final Logger LOG = LoggerFactory.getLogger(SecurityHeaderInputProcessor.class);
    private final ArrayDeque<XMLSecEvent> xmlSecEventList = new ArrayDeque();
    private int startIndexForProcessor;

    public SecurityHeaderInputProcessor(WSSSecurityProperties securityProperties) {
        super(securityProperties);
        this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
    }

    @Override
    public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        return null;
    }

    @Override
    public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        XMLSecEvent xmlSecEvent;
        InputProcessorChain subInputProcessorChain = inputProcessorChain.createSubChain(this);
        InternalSecurityHeaderBufferProcessor internalSecurityHeaderBufferProcessor = new InternalSecurityHeaderBufferProcessor(this.getSecurityProperties());
        subInputProcessorChain.addProcessor(internalSecurityHeaderBufferProcessor);
        boolean responsibleSecurityHeaderFound = false;
        boolean timestampFound = false;
        block4: do {
            subInputProcessorChain.reset();
            xmlSecEvent = subInputProcessorChain.processHeaderEvent();
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
                    int documentLevel = xmlSecStartElement.getDocumentLevel();
                    if (documentLevel == 1) {
                        if (WSSUtils.getSOAPMessageVersionNamespace(xmlSecStartElement) != null) break;
                        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "notASOAPMessage");
                    }
                    if (documentLevel == 3 && xmlSecStartElement.getName().equals(WSSConstants.TAG_WSSE_SECURITY) && WSSUtils.isInSOAPHeader(xmlSecStartElement)) {
                        if (!WSSUtils.isResponsibleActorOrRole(xmlSecStartElement, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) continue block4;
                        responsibleSecurityHeaderFound = true;
                        break;
                    }
                    if (documentLevel == 4 && responsibleSecurityHeaderFound && WSSUtils.isInSecurityHeader(xmlSecStartElement, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
                        this.startIndexForProcessor = this.xmlSecEventList.size() - 1;
                        if (!WSSConstants.TAG_xenc_EncryptedData.equals(xmlSecStartElement.getName())) break;
                        this.engageSecurityHeaderHandler(subInputProcessorChain, this.getSecurityProperties(), this.xmlSecEventList, this.startIndexForProcessor, xmlSecStartElement.getName());
                        break;
                    }
                    if (documentLevel != 5 || !responsibleSecurityHeaderFound || !WSSUtils.isInSecurityHeader(xmlSecStartElement, ((WSSSecurityProperties)this.getSecurityProperties()).getActor()) || !WSSConstants.TAG_xenc_EncryptedData.equals(xmlSecStartElement.getName())) continue block4;
                    this.startIndexForProcessor = this.xmlSecEventList.size() - 1;
                    this.engageSecurityHeaderHandler(subInputProcessorChain, this.getSecurityProperties(), this.xmlSecEventList, this.startIndexForProcessor, xmlSecStartElement.getName());
                    break;
                }
                case 2: {
                    XMLSecEndElement xmlSecEndElement = xmlSecEvent.asEndElement();
                    int documentLevel = xmlSecEndElement.getDocumentLevel();
                    if (documentLevel == 3 && responsibleSecurityHeaderFound && xmlSecEndElement.getName().equals(WSSConstants.TAG_WSSE_SECURITY)) {
                        return this.finalizeHeaderProcessing(inputProcessorChain, subInputProcessorChain, internalSecurityHeaderBufferProcessor, this.xmlSecEventList);
                    }
                    if (documentLevel != 4 || !responsibleSecurityHeaderFound || !WSSUtils.isInSecurityHeader(xmlSecEndElement, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) continue block4;
                    if (!WSSConstants.TAG_xenc_EncryptedData.equals(xmlSecEndElement.getName())) {
                        this.engageSecurityHeaderHandler(subInputProcessorChain, this.getSecurityProperties(), this.xmlSecEventList, this.startIndexForProcessor, xmlSecEndElement.getName());
                    }
                    if (!xmlSecEndElement.getName().equals(WSSConstants.TAG_WSU_TIMESTAMP)) break;
                    if (timestampFound) {
                        WSInboundSecurityContext context = (WSInboundSecurityContext)subInputProcessorChain.getSecurityContext();
                        context.handleBSPRule(BSPRule.R3227);
                    }
                    timestampFound = true;
                }
            }
        } while (xmlSecEvent.getEventType() != 1 || !xmlSecEvent.asStartElement().getName().getLocalPart().equals("Body") || !xmlSecEvent.asStartElement().getName().getNamespaceURI().equals(WSSUtils.getSOAPMessageVersionNamespace(xmlSecEvent.asStartElement())));
        NoSecuritySecurityEvent noSecuritySecurityEvent = new NoSecuritySecurityEvent();
        noSecuritySecurityEvent.setCorrelationID(IDGenerator.generateID(null));
        inputProcessorChain.getSecurityContext().registerSecurityEvent(noSecuritySecurityEvent);
        return this.finalizeHeaderProcessing(inputProcessorChain, subInputProcessorChain, internalSecurityHeaderBufferProcessor, this.xmlSecEventList);
    }

    private XMLSecEvent finalizeHeaderProcessing(InputProcessorChain originalInputProcessorChain, InputProcessorChain subInputProcessorChain, InternalSecurityHeaderBufferProcessor internalSecurityHeaderBufferProcessor, Deque<XMLSecEvent> xmlSecEventList) {
        subInputProcessorChain.removeProcessor(internalSecurityHeaderBufferProcessor);
        subInputProcessorChain.addProcessor(new InternalSecurityHeaderReplayProcessor(this.getSecurityProperties()));
        subInputProcessorChain.removeProcessor(this);
        originalInputProcessorChain.getProcessors().clear();
        originalInputProcessorChain.getProcessors().addAll(subInputProcessorChain.getProcessors());
        return xmlSecEventList.pollLast();
    }

    private void engageSecurityHeaderHandler(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index, QName elementName) throws WSSecurityException, XMLStreamException {
        Class<?> clazz = SecurityHeaderHandlerMapper.getSecurityHeaderHandler(elementName);
        if (clazz == null) {
            LOG.warn("No matching handler found for " + elementName);
            return;
        }
        try {
            XMLSecurityHeaderHandler xmlSecurityHeaderHandler = (XMLSecurityHeaderHandler)clazz.newInstance();
            xmlSecurityHeaderHandler.handle(inputProcessorChain, securityProperties, eventQueue, index);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
        }
        catch (WSSecurityException e) {
            throw e;
        }
        catch (XMLSecurityException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
        }
    }

    public class InternalSecurityHeaderReplayProcessor
    extends AbstractInputProcessor {
        public InternalSecurityHeaderReplayProcessor(XMLSecurityProperties securityProperties) {
            super(securityProperties);
            this.setPhase(XMLSecurityConstants.Phase.PREPROCESSING);
            this.addBeforeProcessor(SecurityHeaderInputProcessor.class.getName());
            this.addAfterProcessor(XMLEventReaderInputProcessor.class.getName());
        }

        @Override
        public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            return null;
        }

        @Override
        public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            if (!SecurityHeaderInputProcessor.this.xmlSecEventList.isEmpty()) {
                return (XMLSecEvent)SecurityHeaderInputProcessor.this.xmlSecEventList.pollLast();
            }
            inputProcessorChain.removeProcessor(this);
            return inputProcessorChain.processEvent();
        }
    }

    public class InternalSecurityHeaderBufferProcessor
    extends AbstractInputProcessor {
        InternalSecurityHeaderBufferProcessor(XMLSecurityProperties securityProperties) {
            super(securityProperties);
            this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
            this.addBeforeProcessor(SecurityHeaderInputProcessor.class.getName());
        }

        @Override
        public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            XMLSecEvent xmlSecEvent = inputProcessorChain.processHeaderEvent();
            SecurityHeaderInputProcessor.this.xmlSecEventList.push(xmlSecEvent);
            return xmlSecEvent;
        }

        @Override
        public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
            return null;
        }
    }
}

