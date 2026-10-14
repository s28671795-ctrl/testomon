/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.ext;

import javax.security.auth.callback.Callback;

public class AttachmentRemovalCallback
implements Callback {
    private String attachmentId;

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }
}

