/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.transform;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.TransformException;
import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.jcp.xml.dsig.internal.dom.ApacheOctetStreamData;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.common.util.CRLFOutputStream;
import org.apache.wss4j.dom.transform.AttachmentTransformParameterSpec;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.xml.sax.SAXException;

public class AttachmentContentSignatureTransform
extends TransformService {
    public static final String TRANSFORM_URI = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform";
    public static final String ATTACHMENT_CALLBACKHANDLER = "AttachmentContentTransform.attachmentCallbackHandler";
    private AttachmentTransformParameterSpec attachmentTransformParameterSpec;

    @Override
    public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {
        if (!(params instanceof AttachmentTransformParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Expected AttachmentTransformParameterSpec");
        }
        this.attachmentTransformParameterSpec = (AttachmentTransformParameterSpec)params;
    }

    protected AttachmentTransformParameterSpec getAttachmentTransformParameterSpec() {
        return this.attachmentTransformParameterSpec;
    }

    @Override
    public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {
    }

    @Override
    public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {
    }

    @Override
    public AlgorithmParameterSpec getParameterSpec() {
        return this.attachmentTransformParameterSpec;
    }

    @Override
    public Data transform(Data data, XMLCryptoContext context) throws TransformException {
        return this.transform(data, context, null);
    }

    @Override
    public Data transform(Data data, XMLCryptoContext context, OutputStream os) throws TransformException {
        Attachment attachment;
        String attachmentUri = ((ApacheOctetStreamData)data).getURI();
        String attachmentId = null;
        try {
            attachmentId = AttachmentUtils.getAttachmentId(attachmentUri);
        }
        catch (WSSecurityException e) {
            throw new TransformException(e);
        }
        if (this.attachmentTransformParameterSpec != null) {
            attachment = this.attachmentTransformParameterSpec.getAttachment();
            context.setProperty(ATTACHMENT_CALLBACKHANDLER, this.attachmentTransformParameterSpec.getAttachmentCallbackHandler());
        } else {
            attachment = this.attachmentRequestCallback(context, attachmentId);
        }
        return this.processAttachment(context, os, attachmentUri, attachment);
    }

    protected Attachment attachmentRequestCallback(XMLCryptoContext context, String attachmentId) throws TransformException {
        CallbackHandler attachmentCallbackHandler = (CallbackHandler)context.getProperty(ATTACHMENT_CALLBACKHANDLER);
        if (attachmentCallbackHandler == null) {
            throw new TransformException("No attachment callbackhandler supplied");
        }
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        attachmentRequestCallback.setAttachmentId(attachmentId);
        try {
            attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
        }
        catch (Exception e) {
            throw new TransformException(e);
        }
        List<Attachment> attachments = attachmentRequestCallback.getAttachments();
        if (attachments == null || attachments.isEmpty() || !attachmentId.equals(attachments.get(0).getId())) {
            throw new TransformException("Attachment not found");
        }
        return attachments.get(0);
    }

    protected void attachmentResultCallback(XMLCryptoContext context, Attachment attachment) throws TransformException {
        CallbackHandler attachmentCallbackHandler = (CallbackHandler)context.getProperty(ATTACHMENT_CALLBACKHANDLER);
        if (attachmentCallbackHandler == null) {
            throw new TransformException("No attachment callbackhandler supplied");
        }
        AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
        attachmentResultCallback.setAttachmentId(attachment.getId());
        attachmentResultCallback.setAttachment(attachment);
        try {
            attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
        }
        catch (Exception e) {
            throw new TransformException(e);
        }
    }

    protected Data processAttachment(XMLCryptoContext context, OutputStream os, String attachmentUri, Attachment attachment) throws TransformException {
        try {
            String mimeType;
            InputStream inputStream2 = attachment.getSourceStream();
            if (!inputStream2.markSupported()) {
                inputStream2 = new BufferedInputStream(inputStream2);
            }
            inputStream2.mark(Integer.MAX_VALUE);
            inputStream2 = new FilterInputStream(inputStream2){

                @Override
                public void close() throws IOException {
                }
            };
            OutputStream outputStream2 = os;
            if (outputStream2 == null) {
                outputStream2 = new ByteArrayOutputStream();
            }
            if ((mimeType = attachment.getMimeType()) != null && (mimeType.matches("(?i)(text/xml).*") || mimeType.matches("(?i)(application/xml).*") || mimeType.matches("(?i)(application|image)/.*\\+xml.*"))) {
                Canonicalizer canon = Canonicalizer.getInstance("http://www.w3.org/2001/10/xml-exc-c14n#");
                canon.setWriter(outputStream2);
                XMLSignatureInput xmlSignatureInput = new XMLSignatureInput(inputStream2);
                canon.canonicalizeXPathNodeSet(xmlSignatureInput.getNodeSet());
            } else if (mimeType != null && mimeType.matches("(?i)(text/).*")) {
                int numBytes;
                CRLFOutputStream crlfOutputStream = new CRLFOutputStream(outputStream2);
                byte[] buf = new byte[8192];
                while ((numBytes = inputStream2.read(buf)) != -1) {
                    crlfOutputStream.write(buf, 0, numBytes);
                }
            } else {
                int numBytes;
                byte[] buf = new byte[8192];
                while ((numBytes = inputStream2.read(buf)) != -1) {
                    outputStream2.write(buf, 0, numBytes);
                }
            }
            inputStream2.reset();
            Attachment resultAttachment = new Attachment();
            resultAttachment.setId(attachment.getId());
            resultAttachment.setMimeType(mimeType);
            resultAttachment.addHeaders(attachment.getHeaders());
            resultAttachment.setSourceStream(inputStream2);
            this.attachmentResultCallback(context, resultAttachment);
            if (os == null) {
                return new OctetStreamData(new ByteArrayInputStream(((ByteArrayOutputStream)outputStream2).toByteArray()), attachmentUri, mimeType);
            }
            return null;
        }
        catch (IOException | ParserConfigurationException | CanonicalizationException | InvalidCanonicalizerException | SAXException e) {
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

