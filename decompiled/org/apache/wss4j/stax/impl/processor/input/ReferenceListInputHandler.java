/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.util.Deque;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.input.DecryptInputProcessor;
import org.apache.xml.security.binding.xmlenc.ReferenceList;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public class ReferenceListInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        ReferenceList referenceList = (ReferenceList)this.parseStructure(eventQueue, index, securityProperties);
        inputProcessorChain.addProcessor(new DecryptInputProcessor(null, referenceList, (WSSSecurityProperties)securityProperties, (WSInboundSecurityContext)inputProcessorChain.getSecurityContext()));
    }
}

