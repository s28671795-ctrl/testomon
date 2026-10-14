/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.security.Provider;

public class AttachmentContentSignatureTransformProvider
extends Provider {
    private static final long serialVersionUID = -9148982936620100249L;

    public AttachmentContentSignatureTransformProvider() {
        super("AttachmentContentSignatureTransform", 2.2, "Attachment Content Signature Transform Provider");
        this.put("TransformService.http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform", "org.apache.wss4j.dom.transform.AttachmentContentSignatureTransform");
        this.put("TransformService.http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform MechanismType", "DOM");
    }
}

