/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class AttachmentInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(AttachmentInInterceptor.class);
    private static final List<String> TYPES = Collections.singletonList("multipart/related");

    public AttachmentInInterceptor() {
        super("receive");
    }

    @Override
    public void handleMessage(Message message) {
        if (this.isGET(message)) {
            LOG.fine("AttachmentInInterceptor skipped in HTTP GET method");
            return;
        }
        if (message.getContent(InputStream.class) == null) {
            return;
        }
        String contentType = (String)message.get("Content-Type");
        if (AttachmentUtil.isTypeSupported(contentType, this.getSupportedTypes())) {
            AttachmentDeserializer ad = new AttachmentDeserializer(message, this.getSupportedTypes());
            try {
                ad.initializeAttachments();
            }
            catch (IOException e) {
                throw new Fault(e);
            }
        }
    }

    @Override
    public void handleFault(Message messageParam) {
    }

    protected List<String> getSupportedTypes() {
        return TYPES;
    }
}

