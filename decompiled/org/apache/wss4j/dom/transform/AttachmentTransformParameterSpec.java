/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import javax.security.auth.callback.CallbackHandler;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import org.apache.wss4j.common.ext.Attachment;

public class AttachmentTransformParameterSpec
implements TransformParameterSpec {
    private CallbackHandler attachmentCallbackHandler;
    private Attachment attachment;

    public AttachmentTransformParameterSpec(CallbackHandler attachmentCallbackHandler, Attachment attachment) {
        this.attachmentCallbackHandler = attachmentCallbackHandler;
        this.attachment = attachment;
    }

    public CallbackHandler getAttachmentCallbackHandler() {
        return this.attachmentCallbackHandler;
    }

    public Attachment getAttachment() {
        return this.attachment;
    }
}

