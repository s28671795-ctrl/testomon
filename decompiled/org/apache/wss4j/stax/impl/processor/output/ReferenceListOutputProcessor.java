/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public class ReferenceListOutputProcessor
extends AbstractOutputProcessor {
    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        outputProcessorChain.processEvent(xmlSecEvent);
        if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
            QName headerElementName = WSSConstants.TAG_xenc_ReferenceList;
            OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, headerElementName, this.getAction(), false);
            OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
            WSSUtils.createReferenceListStructureForEncryption(this, subOutputProcessorChain);
            outputProcessorChain.removeProcessor(this);
        }
    }
}

