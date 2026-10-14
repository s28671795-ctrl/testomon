/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.model;

import org.apache.cxf.service.model.MessagePartInfo;

public class SoapHeaderInfo {
    private MessagePartInfo part;
    private String use;

    public MessagePartInfo getPart() {
        return this.part;
    }

    public void setPart(MessagePartInfo part) {
        this.part = part;
    }

    public String getUse() {
        return this.use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}

