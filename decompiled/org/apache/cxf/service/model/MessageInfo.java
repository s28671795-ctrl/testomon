/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;

public class MessageInfo
extends AbstractMessageContainer {
    private Type type;

    public MessageInfo(OperationInfo op, Type type, QName nm) {
        super(op, nm);
        this.type = type;
    }

    public void setName(QName qn) {
        this.mName = qn;
    }

    public Map<QName, MessagePartInfo> getMessagePartsMap() {
        HashMap<QName, MessagePartInfo> partsMap = new HashMap<QName, MessagePartInfo>();
        for (MessagePartInfo part : this.getMessageParts()) {
            partsMap.put(part.getName(), part);
        }
        return partsMap;
    }

    public List<MessagePartInfo> getOrderedParts(List<String> order) {
        if (StringUtils.isEmpty(order)) {
            return this.getMessageParts();
        }
        ArrayList<MessagePartInfo> orderedParts = new ArrayList<MessagePartInfo>();
        Map<QName, MessagePartInfo> partsMap = this.getMessagePartsMap();
        for (String part : order) {
            QName qname = this.getMessagePartQName(part);
            orderedParts.add(partsMap.get(qname));
        }
        return orderedParts;
    }

    public String toString() {
        return "[MessageInfo " + (Object)((Object)this.type) + ": " + this.mName.toString() + "]";
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        INPUT,
        OUTPUT;

    }
}

