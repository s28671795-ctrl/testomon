/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.service.model.MessagePartInfo;

public class SoapBodyInfo {
    private List<MessagePartInfo> parts = new ArrayList<MessagePartInfo>();
    private List<MessagePartInfo> attachments = new ArrayList<MessagePartInfo>();
    private String use;

    public List<MessagePartInfo> getParts() {
        return this.parts;
    }

    public void setParts(List<MessagePartInfo> parts) {
        this.parts = parts;
    }

    public String getUse() {
        return this.use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public List<MessagePartInfo> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<MessagePartInfo> attachments) {
        this.attachments = attachments;
    }
}

