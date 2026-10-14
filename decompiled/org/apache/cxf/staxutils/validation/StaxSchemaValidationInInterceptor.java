/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.validation;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.validation.WoodstoxValidationImpl;

public class StaxSchemaValidationInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(StaxSchemaValidationInInterceptor.class);

    public StaxSchemaValidationInInterceptor() {
        super("pre-unmarshal");
    }

    @Override
    public void handleMessage(Message message) {
        XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
        try {
            this.setSchemaInMessage(message, xmlReader);
        }
        catch (XMLStreamException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("SCHEMA_ERROR", LOG, new Object[0]), (Throwable)e);
        }
    }

    private void setSchemaInMessage(Message message, XMLStreamReader reader) throws XMLStreamException {
        if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.IN, message)) {
            try {
                WoodstoxValidationImpl mgr = new WoodstoxValidationImpl();
                if (mgr.canValidate()) {
                    mgr.setupValidation(reader, message.getExchange().getEndpoint(), message.getExchange().getService().getServiceInfos().get(0));
                }
            }
            catch (Throwable t2) {
                LOG.log(Level.FINE, "Problem initializing MSV validation", t2);
            }
        }
    }
}

