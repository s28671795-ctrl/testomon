/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.cxf.attachment.AttachmentSerializer;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class AttachmentOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String WRITE_ATTACHMENTS = "write.attachments";
    public static final String ATTACHMENT_OUT_CHECKED = "attachment.out.checked";
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AttachmentOutInterceptor.class);
    private AttachmentOutEndingInterceptor ending = new AttachmentOutEndingInterceptor();

    public AttachmentOutInterceptor() {
        super("pre-stream");
    }

    @Override
    public void handleMessage(Message message) {
        boolean writeAtts;
        if (message.get(ATTACHMENT_OUT_CHECKED) != null && ((Boolean)message.get(ATTACHMENT_OUT_CHECKED)).booleanValue()) {
            return;
        }
        message.put(ATTACHMENT_OUT_CHECKED, Boolean.TRUE);
        boolean mtomEnabled = AttachmentUtil.isMtomEnabled(message);
        boolean bl = writeAtts = MessageUtils.getContextualBoolean(message, WRITE_ATTACHMENTS, false) || message.getAttachments() != null && !message.getAttachments().isEmpty();
        if (!mtomEnabled && !writeAtts) {
            return;
        }
        if (message.getContent(OutputStream.class) == null) {
            return;
        }
        AttachmentSerializer serializer = new AttachmentSerializer(message, this.getMultipartType(), this.writeOptionalTypeParameters(), this.getRootHeaders());
        serializer.setXop(mtomEnabled);
        String contentTransferEncoding = (String)message.getContextualProperty(Message.CONTENT_TRANSFER_ENCODING);
        if (contentTransferEncoding != null) {
            serializer.setContentTransferEncoding(contentTransferEncoding);
        }
        try {
            serializer.writeProlog();
        }
        catch (IOException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("WRITE_ATTACHMENTS", BUNDLE, new Object[0]), (Throwable)e);
        }
        message.setContent(AttachmentSerializer.class, serializer);
        message.getInterceptorChain().add(this.ending);
    }

    protected String getMultipartType() {
        return "multipart/related";
    }

    protected boolean writeOptionalTypeParameters() {
        return true;
    }

    protected Map<String, List<String>> getRootHeaders() {
        return Collections.emptyMap();
    }

    public class AttachmentOutEndingInterceptor
    extends AbstractPhaseInterceptor<Message> {
        public AttachmentOutEndingInterceptor() {
            super("pre-stream-ending");
        }

        @Override
        public void handleMessage(Message message) {
            AttachmentSerializer ser = message.getContent(AttachmentSerializer.class);
            if (ser != null) {
                try {
                    String cte = (String)message.getContextualProperty(Message.CONTENT_TRANSFER_ENCODING);
                    if (cte != null) {
                        ser.setContentTransferEncoding(cte);
                    }
                    ser.writeAttachments();
                }
                catch (IOException e) {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("WRITE_ATTACHMENTS", BUNDLE, new Object[0]), (Throwable)e);
                }
            }
        }
    }
}

