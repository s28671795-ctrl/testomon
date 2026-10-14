/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.SecurityHeaderOrder;
import org.apache.wss4j.stax.impl.processor.output.EncryptOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.UsernameTokenOutputProcessor;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.EncryptionPartDef;
import org.apache.xml.security.stax.impl.processor.output.AbstractEncryptEndingOutputProcessor;

public class EncryptEndingOutputProcessor
extends AbstractEncryptEndingOutputProcessor {
    public EncryptEndingOutputProcessor() throws XMLSecurityException {
        this.addAfterProcessor(EncryptOutputProcessor.class.getName());
        this.addAfterProcessor(UsernameTokenOutputProcessor.class.getName());
    }

    @Override
    public void processHeaderEvent(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
        if (this.attachmentCount(outputProcessorChain) > 0) {
            WSSUtils.createEncryptedDataStructureForAttachments(this, subOutputProcessorChain);
        }
    }

    @Override
    public void flushBufferAndCallbackAfterHeader(OutputProcessorChain outputProcessorChain, Deque<XMLSecEvent> xmlSecEventDeque) throws XMLStreamException, XMLSecurityException {
        String actor = ((WSSSecurityProperties)this.getSecurityProperties()).getActor();
        block3: while (!xmlSecEventDeque.isEmpty()) {
            XMLSecEvent xmlSecEvent = xmlSecEventDeque.pop();
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, actor)) {
                        int attachmentCount = this.attachmentCount(outputProcessorChain);
                        for (int i = 0; i < attachmentCount; ++i) {
                            OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, WSSConstants.TAG_xenc_EncryptedData, this.getAction(), true);
                        }
                        List securityHeaderOrderList = outputProcessorChain.getSecurityContext().getAsList(SecurityHeaderOrder.class);
                        ArrayList tmpList = null;
                        if (securityHeaderOrderList != null) {
                            tmpList = new ArrayList(securityHeaderOrderList);
                            securityHeaderOrderList.clear();
                        }
                        outputProcessorChain.reset();
                        outputProcessorChain.processEvent(xmlSecEvent);
                        if (securityHeaderOrderList == null) break block3;
                        securityHeaderOrderList.addAll(tmpList);
                        break block3;
                    }
                }
                default: {
                    outputProcessorChain.reset();
                    outputProcessorChain.processEvent(xmlSecEvent);
                    continue block3;
                }
            }
        }
        super.flushBufferAndCallbackAfterHeader(outputProcessorChain, xmlSecEventDeque);
    }

    private int attachmentCount(OutputProcessorChain outputProcessorChain) {
        List encryptionPartDefs = outputProcessorChain.getSecurityContext().getAsList(EncryptionPartDef.class);
        if (encryptionPartDefs == null) {
            return 0;
        }
        int count = 0;
        for (EncryptionPartDef encryptionPartDef : encryptionPartDefs) {
            if (encryptionPartDef.getCipherReferenceId() == null) continue;
            ++count;
        }
        return count;
    }
}

