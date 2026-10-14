/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.stax.impl.processor.input.SecurityHeaderInputProcessor;
import org.apache.wss4j.stax.securityEvent.OperationSecurityEvent;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputProcessor;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.util.IDGenerator;

public class OperationInputProcessor
extends AbstractInputProcessor {
    public OperationInputProcessor(XMLSecurityProperties securityProperties) {
        super(securityProperties);
        this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
        this.addBeforeProcessor(SecurityHeaderInputProcessor.class.getName());
    }

    @Override
    public XMLSecEvent processNextHeaderEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        return inputProcessorChain.processHeaderEvent();
    }

    @Override
    public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        XMLSecStartElement xmlSecStartElement;
        List<QName> elementPath;
        XMLSecEvent xmlSecEvent = inputProcessorChain.processEvent();
        if (xmlSecEvent.getEventType() == 1 && (elementPath = (xmlSecStartElement = xmlSecEvent.asStartElement()).getElementPath()).size() == 3 && WSSUtils.isInSOAPBody(elementPath)) {
            OperationSecurityEvent operationSecurityEvent = new OperationSecurityEvent();
            operationSecurityEvent.setOperation(xmlSecEvent.asStartElement().getName());
            operationSecurityEvent.setCorrelationID(IDGenerator.generateID(null));
            inputProcessorChain.getSecurityContext().registerSecurityEvent(operationSecurityEvent);
            inputProcessorChain.removeProcessor(this);
        }
        return xmlSecEvent;
    }
}

