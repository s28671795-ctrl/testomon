/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SignatureValueSecurityEvent;
import org.apache.xml.security.utils.XMLUtils;

public class SignatureConfirmationOutputProcessor
extends AbstractOutputProcessor {
    public SignatureConfirmationOutputProcessor() throws XMLSecurityException {
        this.addBeforeProcessor(WSSSignatureOutputProcessor.class.getName());
        this.addBeforeProcessor(EncryptOutputProcessor.class.getName());
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        outputProcessorChain.processEvent(xmlSecEvent);
        if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
            QName headerElementName = WSSConstants.TAG_WSSE11_SIG_CONF;
            OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
            boolean aSignatureFound = false;
            List requestSecurityEvents = outputProcessorChain.getSecurityContext().getAsList(SecurityEvent.class);
            for (int i = 0; i < requestSecurityEvents.size(); ++i) {
                SecurityEvent securityEvent = (SecurityEvent)requestSecurityEvents.get(i);
                if (!SecurityEventConstants.SignatureValue.equals(securityEvent.getSecurityEventType())) continue;
                aSignatureFound = true;
                SignatureValueSecurityEvent signatureValueSecurityEvent = (SignatureValueSecurityEvent)securityEvent;
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(2);
                attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
                String base64SigValue = XMLUtils.encodeToString(signatureValueSecurityEvent.getSignatureValue());
                attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE, base64SigValue));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, true, attributes);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
            }
            if (!aSignatureFound) {
                OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, headerElementName, true, attributes);
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, headerElementName);
            }
            outputProcessorChain.removeProcessor(this);
        }
    }
}

