/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.wsdl.service.factory.XercesSchemaValidationUtils;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaSerializer;
import org.w3c.dom.DOMErrorHandler;

class XercesXsdValidationImpl {
    private static final Logger LOG = LogUtils.getL7dLogger(XercesXsdValidationImpl.class);
    private XercesSchemaValidationUtils utils;

    XercesXsdValidationImpl() {
        try {
            this.utils = new XercesSchemaValidationUtils();
        }
        finally {
            return;
        }
    }

    public void validateSchemas(XmlSchemaCollection schemas, DOMErrorHandler errorHandler) {
        if (this.utils == null) {
            return;
        }
        try {
            this.utils.tryToParseSchemas(schemas, errorHandler);
        }
        catch (XmlSchemaSerializer.XmlSchemaSerializerException e) {
            LOG.log(Level.WARNING, "XML Schema serialization error", e);
        }
        catch (TransformerException e) {
            LOG.log(Level.SEVERE, "TraX failure converting DOM to string", e);
        }
        catch (Exception e) {
            LOG.log(Level.SEVERE, "XML failure", e);
        }
    }
}

