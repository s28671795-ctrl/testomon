/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.validation;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.validation.Stax2ValidationUtils;

public class WoodstoxValidationImpl {
    private static final Logger LOG = LogUtils.getL7dLogger(WoodstoxValidationImpl.class);
    private Stax2ValidationUtils utils;

    public WoodstoxValidationImpl() {
        try {
            this.utils = new Stax2ValidationUtils();
        }
        catch (Throwable e) {
            LOG.log(Level.FINE, "Problem initializing MSV validation", e);
            return;
        }
    }

    public boolean canValidate() {
        return this.utils != null;
    }

    public void setupValidation(XMLStreamReader reader, Endpoint endpoint, ServiceInfo serviceInfo) throws XMLStreamException {
        if (this.utils != null && reader != null && !this.utils.setupValidation(reader, endpoint, serviceInfo)) {
            this.utils = null;
        }
    }

    public void setupValidation(XMLStreamWriter writer, Endpoint endpoint, ServiceInfo serviceInfo) throws XMLStreamException {
        if (this.utils != null && writer != null && !this.utils.setupValidation(writer, endpoint, serviceInfo)) {
            this.utils = null;
        }
    }
}

