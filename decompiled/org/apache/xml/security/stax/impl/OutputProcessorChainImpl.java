/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.OutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.DocumentContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputProcessorChainImpl
implements OutputProcessorChain {
    protected static final transient Logger LOG = LoggerFactory.getLogger(OutputProcessorChainImpl.class);
    private List<OutputProcessor> outputProcessors;
    private int startPos;
    private int curPos;
    private XMLSecStartElement parentXmlSecStartElement;
    private final OutboundSecurityContext outboundSecurityContext;
    private final DocumentContextImpl documentContext;

    public OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext) {
        this(outboundSecurityContext, 0);
    }

    public OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext, int startPos) {
        this(outboundSecurityContext, new DocumentContextImpl(), startPos, new ArrayList<OutputProcessor>(20));
    }

    public OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext, DocumentContextImpl documentContext) {
        this(outboundSecurityContext, documentContext, 0, new ArrayList<OutputProcessor>(20));
    }

    protected OutputProcessorChainImpl(OutboundSecurityContext outboundSecurityContext, DocumentContextImpl documentContextImpl, int startPos, List<OutputProcessor> outputProcessors) {
        this.outboundSecurityContext = outboundSecurityContext;
        this.curPos = this.startPos = startPos;
        this.documentContext = documentContextImpl;
        this.outputProcessors = outputProcessors;
    }

    @Override
    public void reset() {
        this.curPos = this.startPos;
    }

    @Override
    public OutboundSecurityContext getSecurityContext() {
        return this.outboundSecurityContext;
    }

    @Override
    public DocumentContext getDocumentContext() {
        return this.documentContext;
    }

    @Override
    public void addProcessor(OutputProcessor newOutputProcessor) {
        OutputProcessor outputProcessor;
        int i;
        int endPhaseIdx;
        int startPhaseIdx = 0;
        int idxToInsert = endPhaseIdx = this.outputProcessors.size();
        XMLSecurityConstants.Phase targetPhase = newOutputProcessor.getPhase();
        for (i = this.outputProcessors.size() - 1; i >= 0; --i) {
            outputProcessor = this.outputProcessors.get(i);
            if (outputProcessor.getPhase().ordinal() >= targetPhase.ordinal()) continue;
            startPhaseIdx = i + 1;
            break;
        }
        for (i = startPhaseIdx; i < this.outputProcessors.size(); ++i) {
            outputProcessor = this.outputProcessors.get(i);
            if (outputProcessor.getPhase().ordinal() <= targetPhase.ordinal()) continue;
            endPhaseIdx = i;
            break;
        }
        if (newOutputProcessor.getBeforeProcessors().isEmpty() && newOutputProcessor.getAfterProcessors().isEmpty()) {
            this.outputProcessors.add(endPhaseIdx, newOutputProcessor);
        } else if (newOutputProcessor.getBeforeProcessors().isEmpty()) {
            idxToInsert = endPhaseIdx;
            for (i = endPhaseIdx - 1; i >= startPhaseIdx; --i) {
                outputProcessor = this.outputProcessors.get(i);
                if (!newOutputProcessor.getAfterProcessors().contains(outputProcessor) && !newOutputProcessor.getAfterProcessors().contains(outputProcessor.getClass().getName())) continue;
                idxToInsert = i + 1;
                break;
            }
            this.outputProcessors.add(idxToInsert, newOutputProcessor);
        } else if (newOutputProcessor.getAfterProcessors().isEmpty()) {
            idxToInsert = startPhaseIdx;
            for (i = startPhaseIdx; i < endPhaseIdx; ++i) {
                outputProcessor = this.outputProcessors.get(i);
                if (!newOutputProcessor.getBeforeProcessors().contains(outputProcessor) && !newOutputProcessor.getBeforeProcessors().contains(outputProcessor.getClass().getName())) continue;
                idxToInsert = i;
                break;
            }
            this.outputProcessors.add(idxToInsert, newOutputProcessor);
        } else {
            OutputProcessor outputProcessor2;
            int i2;
            boolean found = false;
            idxToInsert = endPhaseIdx;
            for (i2 = startPhaseIdx; i2 < endPhaseIdx; ++i2) {
                outputProcessor2 = this.outputProcessors.get(i2);
                if (!newOutputProcessor.getBeforeProcessors().contains(outputProcessor2) && !newOutputProcessor.getBeforeProcessors().contains(outputProcessor2.getClass().getName())) continue;
                idxToInsert = i2;
                found = true;
                break;
            }
            if (found) {
                this.outputProcessors.add(idxToInsert, newOutputProcessor);
            } else {
                for (i2 = endPhaseIdx - 1; i2 >= startPhaseIdx; --i2) {
                    outputProcessor2 = this.outputProcessors.get(i2);
                    if (!newOutputProcessor.getAfterProcessors().contains(outputProcessor2) && !newOutputProcessor.getAfterProcessors().contains(outputProcessor2.getClass().getName())) continue;
                    idxToInsert = i2 + 1;
                    break;
                }
                this.outputProcessors.add(idxToInsert, newOutputProcessor);
            }
        }
        if (idxToInsert < this.curPos) {
            ++this.curPos;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Added {} to output chain: ", (Object)newOutputProcessor.getClass().getName());
            for (i = 0; i < this.outputProcessors.size(); ++i) {
                OutputProcessor outputProcessor3 = this.outputProcessors.get(i);
                LOG.debug("Name: {} phase: {}", (Object)outputProcessor3.getClass().getName(), (Object)outputProcessor3.getPhase());
            }
        }
    }

    @Override
    public void removeProcessor(OutputProcessor outputProcessor) {
        LOG.debug("Removing processor {} from output chain", (Object)outputProcessor.getClass().getName());
        if (this.outputProcessors.indexOf(outputProcessor) <= this.curPos) {
            --this.curPos;
        }
        this.outputProcessors.remove(outputProcessor);
    }

    @Override
    public List<OutputProcessor> getProcessors() {
        return this.outputProcessors;
    }

    private void setParentXmlSecStartElement(XMLSecStartElement xmlSecStartElement) {
        this.parentXmlSecStartElement = xmlSecStartElement;
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent) throws XMLStreamException, XMLSecurityException {
        boolean reparent = false;
        if (this.curPos == this.startPos) {
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    if (xmlSecEvent == this.parentXmlSecStartElement) {
                        this.parentXmlSecStartElement = null;
                    }
                    xmlSecEvent.setParentXMLSecStartElement(this.parentXmlSecStartElement);
                    this.parentXmlSecStartElement = xmlSecEvent.asStartElement();
                    break;
                }
                case 2: {
                    xmlSecEvent.setParentXMLSecStartElement(this.parentXmlSecStartElement);
                    reparent = true;
                    break;
                }
                default: {
                    xmlSecEvent.setParentXMLSecStartElement(this.parentXmlSecStartElement);
                }
            }
        }
        this.outputProcessors.get(this.curPos++).processNextEvent(xmlSecEvent, this);
        if (reparent && this.parentXmlSecStartElement != null) {
            this.parentXmlSecStartElement = this.parentXmlSecStartElement.getParentXMLSecStartElement();
        }
    }

    @Override
    public void doFinal() throws XMLStreamException, XMLSecurityException {
        this.outputProcessors.get(this.curPos++).doFinal(this);
    }

    @Override
    public OutputProcessorChain createSubChain(OutputProcessor outputProcessor) throws XMLStreamException, XMLSecurityException {
        return this.createSubChain(outputProcessor, null);
    }

    @Override
    public OutputProcessorChain createSubChain(OutputProcessor outputProcessor, XMLSecStartElement parentXMLSecStartElement) throws XMLStreamException, XMLSecurityException {
        OutputProcessorChainImpl outputProcessorChain;
        try {
            outputProcessorChain = new OutputProcessorChainImpl(this.outboundSecurityContext, this.documentContext.clone(), this.outputProcessors.indexOf(outputProcessor) + 1, this.outputProcessors);
        }
        catch (CloneNotSupportedException e) {
            throw new XMLSecurityException(e);
        }
        if (parentXMLSecStartElement != null) {
            outputProcessorChain.setParentXmlSecStartElement(parentXMLSecStartElement);
        } else {
            outputProcessorChain.setParentXmlSecStartElement(this.parentXmlSecStartElement);
        }
        return outputProcessorChain;
    }
}

