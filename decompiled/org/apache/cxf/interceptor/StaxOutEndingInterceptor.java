/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.OutputStream;
import java.io.Writer;
import java.util.ResourceBundle;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;

public class StaxOutEndingInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(StaxOutEndingInterceptor.class);
    private String outStreamHolder;
    private String writerHolder;

    public StaxOutEndingInterceptor(String outStreamHolder) {
        this(outStreamHolder, null);
    }

    public StaxOutEndingInterceptor(String outStreamHolder, String writerHolder) {
        super("pre-stream-ending");
        this.getAfter().add(AttachmentOutInterceptor.AttachmentOutEndingInterceptor.class.getName());
        this.outStreamHolder = outStreamHolder;
        this.writerHolder = writerHolder;
    }

    @Override
    public void handleMessage(Message message) {
        try {
            Writer w;
            OutputStream os;
            XMLStreamWriter xtw = message.getContent(XMLStreamWriter.class);
            if (xtw != null) {
                try {
                    xtw.writeEndDocument();
                    xtw.flush();
                }
                finally {
                    StaxUtils.close(xtw);
                }
            }
            if ((os = (OutputStream)message.get(this.outStreamHolder)) != null) {
                message.setContent(OutputStream.class, os);
            }
            if (this.writerHolder != null && (w = (Writer)message.get(this.writerHolder)) != null) {
                message.setContent(Writer.class, w);
            }
            message.removeContent(XMLStreamWriter.class);
        }
        catch (XMLStreamException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e);
        }
    }
}

