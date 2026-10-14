/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.ext;

import java.util.List;
import javax.security.auth.callback.Callback;
import org.apache.wss4j.common.ext.Attachment;

public class AttachmentRequestCallback
implements Callback {
    private String attachmentId;
    private List<Attachment> attachments;
    private boolean removeAttachments = true;

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public boolean isRemoveAttachments() {
        return this.removeAttachments;
    }

    public void setRemoveAttachments(boolean removeAttachments) {
        this.removeAttachments = removeAttachments;
    }
}

