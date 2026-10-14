/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.message.Message;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRemovalCallback;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;

public class AttachmentCallbackHandler
implements CallbackHandler {
    private final Collection<org.apache.cxf.message.Attachment> attachments;

    public AttachmentCallbackHandler(Message message) {
        if (message.getAttachments() == null) {
            message.setAttachments(new ArrayList<org.apache.cxf.message.Attachment>());
        }
        this.attachments = message.getAttachments();
    }

    public AttachmentCallbackHandler(Collection<org.apache.cxf.message.Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        block0: for (Callback callback : callbacks) {
            if (callback instanceof AttachmentRequestCallback) {
                AttachmentRequestCallback attachmentRequestCallback = (AttachmentRequestCallback)callback;
                ArrayList<Attachment> attachmentList = new ArrayList<Attachment>();
                attachmentRequestCallback.setAttachments(attachmentList);
                String attachmentId = attachmentRequestCallback.getAttachmentId();
                if ("Attachments".equals(attachmentId)) {
                    attachmentId = null;
                }
                this.loadAttachments(attachmentList, attachmentId, attachmentRequestCallback.isRemoveAttachments());
                continue;
            }
            if (callback instanceof AttachmentResultCallback) {
                AttachmentResultCallback attachmentResultCallback = (AttachmentResultCallback)callback;
                AttachmentImpl securedAttachment = new AttachmentImpl(attachmentResultCallback.getAttachmentId(), new DataHandler(new AttachmentDataSource(attachmentResultCallback.getAttachment().getMimeType(), attachmentResultCallback.getAttachment().getSourceStream())));
                Map<String, String> headers = attachmentResultCallback.getAttachment().getHeaders();
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    securedAttachment.setHeader(entry.getKey(), entry.getValue());
                }
                this.attachments.add(securedAttachment);
                continue;
            }
            if (callback instanceof AttachmentRemovalCallback) {
                AttachmentRemovalCallback attachmentRemovalCallback = (AttachmentRemovalCallback)callback;
                String attachmentId = attachmentRemovalCallback.getAttachmentId();
                if (attachmentId == null || this.attachments == null || this.attachments.size() <= 0) continue;
                Iterator<org.apache.cxf.message.Attachment> iterator2 = this.attachments.iterator();
                while (iterator2.hasNext()) {
                    org.apache.cxf.message.Attachment attachment = iterator2.next();
                    if (!attachmentId.equals(attachment.getId())) continue;
                    iterator2.remove();
                    continue block0;
                }
                continue;
            }
            throw new UnsupportedCallbackException(callback, "Unsupported callback");
        }
    }

    private void loadAttachments(List<Attachment> attachmentList, String attachmentId, boolean removeAttachments) throws IOException {
        if (this.attachments != null && this.attachments.size() > 0) {
            Iterator<org.apache.cxf.message.Attachment> iterator2 = this.attachments.iterator();
            while (iterator2.hasNext()) {
                org.apache.cxf.message.Attachment attachment = iterator2.next();
                if (attachmentId != null && !attachmentId.equals(attachment.getId()) && !attachmentId.equals(AttachmentCallbackHandler.getDecodedAttachmentId(attachment.getId()))) continue;
                Attachment att = new Attachment();
                att.setMimeType(attachment.getDataHandler().getContentType());
                att.setId(attachmentId == null ? attachment.getId() : attachmentId);
                att.setSourceStream(attachment.getDataHandler().getInputStream());
                Iterator<String> headerIterator = attachment.getHeaderNames();
                while (headerIterator.hasNext()) {
                    String next = headerIterator.next();
                    att.addHeader(next, attachment.getHeader(next));
                }
                attachmentList.add(att);
                if (!removeAttachments) continue;
                iterator2.remove();
            }
        }
    }

    private static String getDecodedAttachmentId(String attachmentId) throws IOException {
        return URLDecoder.decode(attachmentId, StandardCharsets.UTF_8.name());
    }
}

