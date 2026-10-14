/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.ext;

import javax.security.auth.callback.Callback;
import org.apache.wss4j.common.ext.Attachment;

public class AttachmentResultCallback
implements Callback {
    private String attachmentId;
    private Attachment attachment;

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Attachment getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}

