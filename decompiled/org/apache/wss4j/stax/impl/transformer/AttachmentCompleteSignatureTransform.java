/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.transformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.impl.transformer.AttachmentContentSignatureTransform;
import org.apache.xml.security.stax.impl.util.MultiInputStream;

public class AttachmentCompleteSignatureTransform
extends AttachmentContentSignatureTransform {
    @Override
    public void transform(InputStream inputStream2) throws XMLStreamException {
        InputStream is = inputStream2;
        try {
            OutputStream outputStream2 = this.getOutputStream();
            if (outputStream2 == null) {
                outputStream2 = new ByteArrayOutputStream();
                is = new MultiInputStream(new ByteArrayInputStream(((ByteArrayOutputStream)outputStream2).toByteArray()), inputStream2);
            }
            AttachmentUtils.canonizeMimeHeaders(outputStream2, this.getAttachment().getHeaders());
        }
        catch (IOException e) {
            throw new XMLStreamException(e);
        }
        super.transform(is);
    }
}

