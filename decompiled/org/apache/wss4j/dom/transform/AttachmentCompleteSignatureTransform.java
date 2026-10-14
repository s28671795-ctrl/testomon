/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.crypto.Data;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.TransformException;
import org.apache.jcp.xml.dsig.internal.dom.ApacheOctetStreamData;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.dom.transform.AttachmentContentSignatureTransform;
import org.apache.wss4j.dom.transform.AttachmentTransformParameterSpec;

public class AttachmentCompleteSignatureTransform
extends AttachmentContentSignatureTransform {
    public static final String TRANSFORM_URI = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform";

    @Override
    public Data transform(Data data, XMLCryptoContext context) throws TransformException {
        return this.transform(data, context, null);
    }

    @Override
    public Data transform(Data data, XMLCryptoContext context, OutputStream os) throws TransformException {
        Attachment attachment;
        String attachmentUri = ((ApacheOctetStreamData)data).getURI();
        String attachmentId = attachmentUri.substring(4);
        AttachmentTransformParameterSpec attachmentTransformParameterSpec = this.getAttachmentTransformParameterSpec();
        if (attachmentTransformParameterSpec != null) {
            attachment = attachmentTransformParameterSpec.getAttachment();
            context.setProperty("AttachmentContentTransform.attachmentCallbackHandler", attachmentTransformParameterSpec.getAttachmentCallbackHandler());
        } else {
            attachment = this.attachmentRequestCallback(context, attachmentId);
        }
        try {
            OutputStream outputStream2 = os;
            if (outputStream2 == null) {
                outputStream2 = new ByteArrayOutputStream();
            }
            AttachmentUtils.canonizeMimeHeaders(os, attachment.getHeaders());
            this.processAttachment(context, os, attachmentUri, attachment);
            if (os == null) {
                String mimeType = attachment.getMimeType();
                return new OctetStreamData(new ByteArrayInputStream(((ByteArrayOutputStream)outputStream2).toByteArray()), attachmentUri, mimeType);
            }
            return null;
        }
        catch (IOException e) {
            throw new TransformException(e);
        }
    }

    @Override
    public boolean isFeatureSupported(String feature) {
        if (feature == null) {
            throw new NullPointerException();
        }
        return false;
    }
}

