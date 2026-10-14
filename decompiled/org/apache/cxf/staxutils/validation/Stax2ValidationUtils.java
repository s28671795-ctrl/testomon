/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.validation;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.validation.EmbeddedSchema;
import org.apache.cxf.staxutils.validation.ResolvingGrammarReaderController;
import org.apache.cxf.staxutils.validation.W3CMultiSchemaFactory;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaExternal;
import org.codehaus.stax2.XMLStreamReader2;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.validation.ValidationProblemHandler;
import org.codehaus.stax2.validation.XMLValidationException;
import org.codehaus.stax2.validation.XMLValidationProblem;
import org.codehaus.stax2.validation.XMLValidationSchema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

class Stax2ValidationUtils {
    private static final Logger LOG = LogUtils.getL7dLogger(Stax2ValidationUtils.class);
    private static final String KEY = XMLValidationSchema.class.getName();
    private static final boolean HAS_WOODSTOX;

    Stax2ValidationUtils() {
        if (!HAS_WOODSTOX) {
            throw new RuntimeException("Could not load woodstox");
        }
    }

    public boolean setupValidation(XMLStreamReader reader, Endpoint endpoint, ServiceInfo serviceInfo) throws XMLStreamException {
        XMLStreamReader effectiveReader = reader;
        if (effectiveReader instanceof DepthXMLStreamReader) {
            effectiveReader = ((DepthXMLStreamReader)reader).getReader();
        }
        XMLStreamReader2 reader2 = (XMLStreamReader2)effectiveReader;
        XMLValidationSchema vs = this.getValidator(endpoint, serviceInfo);
        if (vs == null) {
            return false;
        }
        reader2.setValidationProblemHandler(new ValidationProblemHandler(){

            @Override
            public void reportProblem(XMLValidationProblem problem) throws XMLValidationException {
                throw new Fault(new Message("READ_VALIDATION_ERROR", LOG, problem.getMessage()), Fault.FAULT_CODE_CLIENT);
            }
        });
        reader2.validateAgainst(vs);
        return true;
    }

    public boolean setupValidation(XMLStreamWriter writer, Endpoint endpoint, ServiceInfo serviceInfo) throws XMLStreamException {
        XMLStreamWriter2 writer2 = (XMLStreamWriter2)writer;
        XMLValidationSchema vs = this.getValidator(endpoint, serviceInfo);
        if (vs == null) {
            return false;
        }
        writer2.setValidationProblemHandler(new ValidationProblemHandler(){

            @Override
            public void reportProblem(XMLValidationProblem problem) throws XMLValidationException {
                throw new Fault(problem.getMessage(), LOG);
            }
        });
        writer2.validateAgainst(vs);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private XMLValidationSchema getValidator(Endpoint endpoint, ServiceInfo serviceInfo) throws XMLStreamException {
        Endpoint endpoint2 = endpoint;
        synchronized (endpoint2) {
            XMLValidationSchema ret = (XMLValidationSchema)endpoint.get(KEY);
            if (ret == null) {
                if (endpoint.containsKey(KEY)) {
                    return null;
                }
                TreeMap<String, EmbeddedSchema> sources = new TreeMap<String, EmbeddedSchema>();
                for (SchemaInfo schemaInfo : serviceInfo.getSchemas()) {
                    XmlSchema sch = schemaInfo.getSchema();
                    String uri = sch.getTargetNamespace();
                    if ("http://www.w3.org/2001/XMLSchema".equals(uri)) continue;
                    if (sch.getTargetNamespace() == null && !sch.getExternals().isEmpty()) {
                        for (XmlSchemaExternal xmlSchemaExternal : sch.getExternals()) {
                            this.addSchema(sources, xmlSchemaExternal.getSchema(), this.getElement(xmlSchemaExternal.getSchema().getSourceURI()));
                        }
                        continue;
                    }
                    if (sch.getTargetNamespace() == null) {
                        throw new IllegalStateException("An Schema without imports must have a targetNamespace");
                    }
                    this.addSchema(sources, sch, schemaInfo.getElement());
                }
                W3CMultiSchemaFactory factory2 = new W3CMultiSchemaFactory();
                try {
                    ret = factory2.loadSchemas(null, sources);
                    endpoint.put(KEY, ret);
                }
                catch (XMLStreamException ex) {
                    LOG.log(Level.INFO, "Problem loading schemas. Falling back to slower method.", ret);
                    endpoint.put(KEY, null);
                }
            }
            return ret;
        }
    }

    private void addSchema(Map<String, EmbeddedSchema> sources, XmlSchema schema, Element element) throws XMLStreamException {
        String schemaSystemId = schema.getSourceURI();
        if (null == schemaSystemId) {
            schemaSystemId = schema.getTargetNamespace();
        }
        EmbeddedSchema embeddedSchema = new EmbeddedSchema(schemaSystemId, element);
        sources.put(schema.getTargetNamespace(), embeddedSchema);
    }

    private Element getElement(String path) throws XMLStreamException {
        InputSource in = new InputSource(path);
        Document doc = StaxUtils.read(in);
        return doc.getDocumentElement();
    }

    static {
        boolean hasw = false;
        try {
            new ResolvingGrammarReaderController(null, null);
            new W3CMultiSchemaFactory();
            hasw = true;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        HAS_WOODSTOX = hasw;
    }
}

