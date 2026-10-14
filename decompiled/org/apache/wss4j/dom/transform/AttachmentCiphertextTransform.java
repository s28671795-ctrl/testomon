/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import org.apache.xml.security.transforms.TransformSpi;

public class AttachmentCiphertextTransform
extends TransformSpi {
    @Override
    protected String engineGetURI() {
        return "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform";
    }
}

