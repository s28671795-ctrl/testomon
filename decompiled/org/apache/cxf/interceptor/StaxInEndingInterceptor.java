/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;

public class StaxInEndingInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String STAX_IN_NOCLOSE = StaxInEndingInterceptor.class.getName() + ".dontClose";
    public static final StaxInEndingInterceptor INSTANCE = new StaxInEndingInterceptor();

    public StaxInEndingInterceptor() {
        super("pre-invoke");
    }

    public StaxInEndingInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(Message message) {
        XMLStreamReader xtr = message.getContent(XMLStreamReader.class);
        if (xtr != null && !MessageUtils.getContextualBoolean(message, STAX_IN_NOCLOSE, false)) {
            try {
                StaxUtils.close(xtr);
            }
            catch (XMLStreamException ex) {
                throw new Fault(ex);
            }
            message.removeContent(XMLStreamReader.class);
        }
    }
}

