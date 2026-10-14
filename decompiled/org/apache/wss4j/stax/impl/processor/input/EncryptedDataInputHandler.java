/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.Deque;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.input.DecryptInputProcessor;
import org.apache.xml.security.binding.xmlenc.ReferenceList;
import org.apache.xml.security.binding.xmlenc.ReferenceType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class EncryptedDataInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(final InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        XMLSecEvent xmlSecEvent = eventQueue.pollFirst();
        if (!(xmlSecEvent instanceof XMLSecStartElement)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
        }
        final XMLSecStartElement encryptedDataElement = xmlSecEvent.asStartElement();
        final Attribute idAttribute = encryptedDataElement.getAttributeByName(XMLSecurityConstants.ATT_NULL_Id);
        DecryptInputProcessor decryptInputProcessor = new DecryptInputProcessor(null, new ReferenceList(), (WSSSecurityProperties)securityProperties, (WSInboundSecurityContext)inputProcessorChain.getSecurityContext()){

            @Override
            protected ReferenceType matchesReferenceId(XMLSecStartElement xmlSecStartElement) {
                if (xmlSecStartElement == encryptedDataElement) {
                    ReferenceType referenceType = new ReferenceType();
                    if (idAttribute != null) {
                        String uri = idAttribute.getValue();
                        referenceType.setURI("#" + uri);
                        inputProcessorChain.getSecurityContext().putAsList("PROP_ENCRYPTED_DATA_REFS", uri);
                    }
                    return referenceType;
                }
                return null;
            }
        };
        inputProcessorChain.addProcessor(decryptInputProcessor);
        AbstractInputProcessor tmpProcessor = new AbstractInputProcessor(securityProperties){

            @Override
            public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
                inputProcessorChain.removeProcessor(this);
                return encryptedDataElement;
            }

            @Override
            public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
                inputProcessorChain.removeProcessor(this);
                return encryptedDataElement;
            }
        };
        tmpProcessor.addBeforeProcessor(decryptInputProcessor);
        inputProcessorChain.addProcessor(tmpProcessor);
    }
}

