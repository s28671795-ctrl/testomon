/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.security.Provider;

public class AttachmentCompleteSignatureTransformProvider
extends Provider {
    private static final long serialVersionUID = -9148982936620100249L;

    public AttachmentCompleteSignatureTransformProvider() {
        super("AttachmentCompleteSignatureTransform", 2.2, "Attachment Complete Signature Transform Provider");
        this.put("TransformService.http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform", "org.apache.wss4j.dom.transform.AttachmentCompleteSignatureTransform");
        this.put("TransformService.http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform MechanismType", "DOM");
    }
}

