/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.transformer;

import java.io.InputStream;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.util.CRLFOutputStream;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.transformer.TransformIdentity;
import org.apache.xml.security.stax.impl.transformer.canonicalizer.Canonicalizer20010315_ExclOmitCommentsTransformer;

public class AttachmentContentSignatureTransform
extends TransformIdentity {
    public static final String ATTACHMENT = "attachment";
    private Attachment attachment;

    @Override
    public XMLSecurityConstants.TransformMethod getPreferredTransformMethod(XMLSecurityConstants.TransformMethod forInput) {
        switch (forInput) {
            case XMLSecEvent: {
                return XMLSecurityConstants.TransformMethod.InputStream;
            }
            case InputStream: {
                return XMLSecurityConstants.TransformMethod.InputStream;
            }
        }
        throw new IllegalArgumentException("Unsupported class " + forInput.name());
    }

    @Override
    public void setProperties(Map<String, Object> properties) throws XMLSecurityException {
        this.attachment = (Attachment)properties.get(ATTACHMENT);
    }

    protected Attachment getAttachment() {
        return this.attachment;
    }

    @Override
    public void transform(XMLSecEvent xmlSecEvent) throws XMLStreamException {
        throw new UnsupportedOperationException("transform(XMLSecEvent) not allowed");
    }

    @Override
    public void transform(InputStream inputStream2) throws XMLStreamException {
        String mimeType = this.getAttachment().getMimeType();
        if (mimeType != null && (mimeType.matches("(?i)(text/xml).*") || mimeType.matches("(?i)(application/xml).*") || mimeType.matches("(?i)(application|image)/.*\\+xml.*"))) {
            Canonicalizer20010315_ExclOmitCommentsTransformer canon = new Canonicalizer20010315_ExclOmitCommentsTransformer();
            try {
                canon.setOutputStream(this.getOutputStream());
            }
            catch (XMLSecurityException e) {
                throw new XMLStreamException(e);
            }
            canon.transform(inputStream2);
        } else if (mimeType != null && mimeType.matches("(?i)(text/).*")) {
            CRLFOutputStream crlfOutputStream = new CRLFOutputStream(this.getOutputStream());
            try {
                this.setOutputStream(crlfOutputStream);
            }
            catch (XMLSecurityException e) {
                throw new XMLStreamException(e);
            }
            super.transform(inputStream2);
        } else {
            super.transform(inputStream2);
        }
    }
}

