/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb.attachment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public final class JAXBAttachmentSchemaValidationHack
extends AbstractPhaseInterceptor<Message> {
    public static final JAXBAttachmentSchemaValidationHack INSTANCE = new JAXBAttachmentSchemaValidationHack();
    private static final String SAVED_DATASOURCES = JAXBAttachmentSchemaValidationHack.class.getName() + ".SAVED_DATASOURCES";

    private JAXBAttachmentSchemaValidationHack() {
        super("post-protocol");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.IN, message) && message.getAttachments() != null) {
            ArrayList<AttachmentDataSource> dss = new ArrayList<AttachmentDataSource>();
            for (Attachment at : message.getAttachments()) {
                if (!(at.getDataHandler().getDataSource() instanceof AttachmentDataSource)) continue;
                AttachmentDataSource ds = (AttachmentDataSource)at.getDataHandler().getDataSource();
                try {
                    ds.hold(message);
                }
                catch (IOException e) {
                    throw new Fault(e);
                }
                dss.add(ds);
            }
            if (!dss.isEmpty()) {
                message.put(SAVED_DATASOURCES, dss);
                message.getInterceptorChain().add(EndingInterceptor.INSTANCE);
            }
        }
    }

    static class EndingInterceptor
    extends AbstractPhaseInterceptor<Message> {
        static final EndingInterceptor INSTANCE = new EndingInterceptor();

        EndingInterceptor() {
            super("pre-logical");
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            List<AttachmentDataSource> dss = CastUtils.cast((List)message.get(SAVED_DATASOURCES));
            for (AttachmentDataSource ds : dss) {
                ds.release();
            }
        }
    }
}

