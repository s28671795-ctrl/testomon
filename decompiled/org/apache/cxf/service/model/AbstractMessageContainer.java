/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.NamedItem;
import org.apache.cxf.service.model.OperationInfo;

public abstract class AbstractMessageContainer
extends AbstractPropertiesHolder
implements NamedItem {
    protected QName mName;
    private OperationInfo operation;
    private Map<QName, MessagePartInfo> messageParts = new LinkedHashMap<QName, MessagePartInfo>(4);
    private List<MessagePartInfo> outOfBandParts;
    private String documentation;

    AbstractMessageContainer(OperationInfo op, QName nm) {
        this.operation = op;
        this.mName = nm;
    }

    public String getMessageDocumentation() {
        return this.documentation;
    }

    public void setMessageDocumentation(String doc) {
        this.documentation = doc;
    }

    @Override
    public QName getName() {
        return this.mName;
    }

    public OperationInfo getOperation() {
        return this.operation;
    }

    public MessagePartInfo addMessagePart(QName name) {
        if (name == null) {
            throw new IllegalArgumentException("Invalid name [null]");
        }
        MessagePartInfo part = new MessagePartInfo(name, this);
        this.addMessagePart(part);
        return part;
    }

    public QName getMessagePartQName(String name) {
        return new QName(this.getOperation().getInterface().getName().getNamespaceURI(), name);
    }

    public MessagePartInfo addMessagePart(String name) {
        return this.addMessagePart(this.getMessagePartQName(name));
    }

    public void addMessagePart(MessagePartInfo part) {
        if (this.messageParts.containsKey(part.getName())) {
            part.setIndex(this.messageParts.get(part.getName()).getIndex());
        } else {
            part.setIndex(this.messageParts.size());
        }
        this.messageParts.put(part.getName(), part);
    }

    public int getMessagePartIndex(MessagePartInfo part) {
        int i = 0;
        for (MessagePartInfo p : this.messageParts.values()) {
            if (part == p) {
                return i;
            }
            ++i;
        }
        for (MessagePartInfo p : this.getOutOfBandParts()) {
            if (part == p) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public MessagePartInfo getMessagePartByIndex(int i) {
        for (MessagePartInfo p : this.messageParts.values()) {
            if (p.getIndex() != i) continue;
            return p;
        }
        for (MessagePartInfo p : this.getOutOfBandParts()) {
            if (p.getIndex() != i) continue;
            return p;
        }
        return null;
    }

    public void removeMessagePart(QName name) {
        MessagePartInfo messagePart = this.getMessagePart(name);
        if (messagePart != null) {
            this.messageParts.remove(name);
        }
    }

    public MessagePartInfo getMessagePart(QName name) {
        MessagePartInfo mpi = this.messageParts.get(name);
        if (mpi != null) {
            return mpi;
        }
        for (MessagePartInfo mpi2 : this.messageParts.values()) {
            if (!name.equals(mpi2.getConcreteName())) continue;
            return mpi2;
        }
        for (MessagePartInfo mpi2 : this.getOutOfBandParts()) {
            if (!name.equals(mpi2.getName()) && !name.equals(mpi2.getConcreteName())) continue;
            return mpi2;
        }
        return mpi;
    }

    public MessagePartInfo getMessagePart(int n) {
        if (n == -1) {
            return null;
        }
        List<MessagePartInfo> mpis = this.getMessageParts();
        return n < mpis.size() ? mpis.get(n) : null;
    }

    public MessagePartInfo addOutOfBandMessagePart(QName name) {
        if (name == null) {
            throw new IllegalArgumentException("Invalid name [null]");
        }
        MessagePartInfo part = new MessagePartInfo(name, this);
        if (this.outOfBandParts == null) {
            this.outOfBandParts = new ArrayList<MessagePartInfo>(1);
        }
        part.setIndex(this.messageParts.size() + this.outOfBandParts.size());
        this.outOfBandParts.add(part);
        return part;
    }

    public List<MessagePartInfo> getMessageParts() {
        if (this.outOfBandParts == null) {
            return new ArrayList<MessagePartInfo>(this.messageParts.values());
        }
        ArrayList<MessagePartInfo> parts = new ArrayList<MessagePartInfo>(this.messageParts.values());
        parts.addAll(this.outOfBandParts);
        return parts;
    }

    public int getMessagePartsNumber() {
        if (this.outOfBandParts == null) {
            return this.messageParts.size();
        }
        return this.messageParts.size() + this.outOfBandParts.size();
    }

    public MessagePartInfo getFirstMessagePart() {
        if (!this.messageParts.isEmpty()) {
            return this.messageParts.values().iterator().next();
        }
        if (this.outOfBandParts != null && !this.outOfBandParts.isEmpty()) {
            return this.outOfBandParts.get(0);
        }
        return null;
    }

    public List<MessagePartInfo> getOutOfBandParts() {
        if (this.outOfBandParts == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.outOfBandParts);
    }

    public int size() {
        return this.messageParts.size() + this.getOutOfBandParts().size();
    }

    public int hashCode() {
        return this.mName == null ? -1 : this.mName.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AbstractMessageContainer)) {
            return false;
        }
        AbstractMessageContainer oi = (AbstractMessageContainer)o;
        return AbstractMessageContainer.equals(this.mName, oi.mName) && AbstractMessageContainer.equals(this.messageParts, oi.messageParts) && AbstractMessageContainer.equals(this.outOfBandParts, oi.outOfBandParts);
    }
}

