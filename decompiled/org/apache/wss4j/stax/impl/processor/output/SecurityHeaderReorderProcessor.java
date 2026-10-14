/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.SecurityHeaderOrder;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.processor.output.FinalOutputProcessor;

public class SecurityHeaderReorderProcessor
extends AbstractOutputProcessor {
    private final Map<XMLSecurityConstants.Action, Map<SecurityHeaderOrder, Deque<XMLSecEvent>>> actionEventMap = new LinkedHashMap<XMLSecurityConstants.Action, Map<SecurityHeaderOrder, Deque<XMLSecEvent>>>();
    private int securityHeaderIndex;
    private Deque<XMLSecEvent> currentDeque;

    public SecurityHeaderReorderProcessor() throws XMLSecurityException {
        this.setPhase(XMLSecurityConstants.Phase.POSTPROCESSING);
        this.addBeforeProcessor(FinalOutputProcessor.class.getName());
    }

    @Override
    public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
        super.init(outputProcessorChain);
        List<XMLSecurityConstants.Action> outActions = this.getSecurityProperties().getActions();
        for (int i = outActions.size() - 1; i >= 0; --i) {
            XMLSecurityConstants.Action outAction = outActions.get(i);
            this.actionEventMap.put(outAction, new TreeMap(new Comparator<SecurityHeaderOrder>(){

                @Override
                public int compare(SecurityHeaderOrder o1, SecurityHeaderOrder o2) {
                    if (WSSConstants.TAG_dsig_Signature.equals(o1.getSecurityHeaderElementName())) {
                        return 1;
                    }
                    if (WSSConstants.TAG_dsig_Signature.equals(o2.getSecurityHeaderElementName())) {
                        return -1;
                    }
                    return 1;
                }
            }));
        }
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        int documentLevel = xmlSecEvent.getDocumentLevel();
        if (documentLevel < 3 || !WSSUtils.isInSecurityHeader(xmlSecEvent, ((WSSSecurityProperties)this.getSecurityProperties()).getActor())) {
            outputProcessorChain.processEvent(xmlSecEvent);
            return;
        }
        if (documentLevel == 3) {
            if (xmlSecEvent.isEndElement() && xmlSecEvent.asEndElement().getName().equals(WSSConstants.TAG_WSSE_SECURITY)) {
                OutputProcessorChain subOutputProcessorChain = outputProcessorChain.createSubChain(this);
                for (Map.Entry<XMLSecurityConstants.Action, Map<SecurityHeaderOrder, Deque<XMLSecEvent>>> next : this.actionEventMap.entrySet()) {
                    boolean encryptAction = false;
                    Iterator<Map.Entry<SecurityHeaderOrder, Deque<XMLSecEvent>>> entryIterator = next.getValue().entrySet().iterator();
                    while (entryIterator.hasNext()) {
                        Map.Entry<SecurityHeaderOrder, Deque<XMLSecEvent>> entry = entryIterator.next();
                        if (!entry.getKey().isEncrypted()) {
                            Deque<XMLSecEvent> xmlSecEvents = entry.getValue();
                            while (!xmlSecEvents.isEmpty()) {
                                XMLSecEvent event = xmlSecEvents.pop();
                                subOutputProcessorChain.reset();
                                subOutputProcessorChain.processEvent(event);
                            }
                            entryIterator.remove();
                        }
                        if (!entry.getKey().getAction().getName().contains("Encrypt")) continue;
                        encryptAction = true;
                    }
                    if (!encryptAction) continue;
                    break;
                }
                for (Map.Entry<XMLSecurityConstants.Action, Map<SecurityHeaderOrder, Deque<XMLSecEvent>>> next : this.actionEventMap.entrySet()) {
                    for (Map.Entry<SecurityHeaderOrder, Deque<XMLSecEvent>> entry : next.getValue().entrySet()) {
                        Deque<XMLSecEvent> xmlSecEvents = entry.getValue();
                        while (!xmlSecEvents.isEmpty()) {
                            XMLSecEvent event = xmlSecEvents.pop();
                            subOutputProcessorChain.reset();
                            subOutputProcessorChain.processEvent(event);
                        }
                    }
                }
                outputProcessorChain.removeProcessor(this);
            }
            outputProcessorChain.processEvent(xmlSecEvent);
            return;
        }
        if (documentLevel == 4) {
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
                    List securityHeaderOrderList = outputProcessorChain.getSecurityContext().getAsList(SecurityHeaderOrder.class);
                    SecurityHeaderOrder securityHeaderOrder = (SecurityHeaderOrder)securityHeaderOrderList.get(this.securityHeaderIndex);
                    if (!xmlSecStartElement.getName().equals(WSSConstants.TAG_xenc_EncryptedData) && !xmlSecStartElement.getName().equals(securityHeaderOrder.getSecurityHeaderElementName())) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Invalid security header order. Expected " + securityHeaderOrder.getSecurityHeaderElementName() + " but got " + xmlSecStartElement.getName()});
                    }
                    Map<SecurityHeaderOrder, Deque<XMLSecEvent>> map = this.actionEventMap.get(securityHeaderOrder.getAction());
                    this.currentDeque = new ArrayDeque<XMLSecEvent>();
                    map.put(securityHeaderOrder, this.currentDeque);
                    ++this.securityHeaderIndex;
                }
            }
        }
        this.currentDeque.offer(xmlSecEvent);
    }
}

