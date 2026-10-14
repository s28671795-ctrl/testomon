/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.List;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;

public class BindingMessageInfo
extends AbstractPropertiesHolder {
    MessageInfo msg;
    BindingOperationInfo op;
    private List<MessagePartInfo> messageParts;

    public BindingMessageInfo() {
    }

    protected BindingMessageInfo(MessageInfo m4, BindingOperationInfo boi) {
        this.op = boi;
        this.msg = m4;
        this.messageParts = this.msg.getMessageParts();
    }

    public MessageInfo getMessageInfo() {
        return this.msg;
    }

    public BindingOperationInfo getBindingOperation() {
        return this.op;
    }

    public List<MessagePartInfo> getMessageParts() {
        return this.messageParts;
    }

    public void setMessageParts(List<MessagePartInfo> messageParts) {
        this.messageParts = messageParts;
    }
}

