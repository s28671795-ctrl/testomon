/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.validation.Schema;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.CachingXmlEventWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;

public abstract class AbstractOutDatabindingInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String DISABLE_OUTPUTSTREAM_OPTIMIZATION = "disable.outputstream.optimization";
    public static final String OUT_BUFFERING = "org.apache.cxf.output.buffering";

    public AbstractOutDatabindingInterceptor(String phase) {
        super(phase);
    }

    public AbstractOutDatabindingInterceptor(String id, String phase) {
        super(id, phase);
    }

    protected boolean shouldBuffer(Message message) {
        Object en = message.getContextualProperty(OUT_BUFFERING);
        boolean allowBuffer = true;
        boolean buffer = false;
        if (en != null) {
            buffer = Boolean.TRUE.equals(en) || "true".equals(en);
            allowBuffer = !Boolean.FALSE.equals(en) && !"false".equals(en);
        }
        return buffer || allowBuffer && this.shouldValidate(message) && !this.isRequestor(message);
    }

    protected void writeParts(Message message, Exchange exchange, BindingOperationInfo operation, MessageContentsList objs, List<MessagePartInfo> parts) {
        OutputStream out = message.getContent(OutputStream.class);
        XMLStreamWriter origXmlWriter = message.getContent(XMLStreamWriter.class);
        Service service2 = exchange.getService();
        XMLStreamWriter xmlWriter = origXmlWriter;
        CachingXmlEventWriter cache = null;
        this.setOperationSchemaValidation(message);
        if (this.shouldBuffer(message)) {
            if (!(xmlWriter instanceof CachingXmlEventWriter)) {
                cache = new CachingXmlEventWriter();
                try {
                    cache.setNamespaceContext(origXmlWriter.getNamespaceContext());
                }
                catch (XMLStreamException xMLStreamException) {
                    // empty catch block
                }
                xmlWriter = cache;
            }
            out = null;
        }
        if (out != null && this.writeToOutputStream(message, operation.getBinding(), service2) && !MessageUtils.getContextualBoolean(message, DISABLE_OUTPUTSTREAM_OPTIMIZATION, false)) {
            if (xmlWriter != null) {
                try {
                    xmlWriter.writeCharacters("");
                    xmlWriter.flush();
                }
                catch (XMLStreamException e) {
                    throw new Fault(e);
                }
            }
            DataWriter<OutputStream> osWriter = this.getDataWriter(message, service2, OutputStream.class);
            for (MessagePartInfo part : parts) {
                if (!objs.hasValue(part)) continue;
                Object o = objs.get(part);
                osWriter.write(o, part, out);
            }
        } else {
            DataWriter<XMLStreamWriter> dataWriter = this.getDataWriter(message, service2, XMLStreamWriter.class);
            for (MessagePartInfo part : parts) {
                if (!objs.hasValue(part)) continue;
                NamespaceContext c = null;
                if (!part.isElement() && xmlWriter instanceof CachingXmlEventWriter) {
                    try {
                        c = xmlWriter.getNamespaceContext();
                        xmlWriter.setNamespaceContext(new CachingXmlEventWriter.NSContext(null));
                    }
                    catch (XMLStreamException xMLStreamException) {
                        // empty catch block
                    }
                }
                Object o = objs.get(part);
                dataWriter.write(o, part, xmlWriter);
                if (c == null) continue;
                try {
                    xmlWriter.setNamespaceContext(c);
                }
                catch (XMLStreamException xMLStreamException) {}
            }
        }
        if (cache != null) {
            try {
                for (XMLEvent event : cache.getEvents()) {
                    StaxUtils.writeEvent(event, origXmlWriter);
                }
            }
            catch (XMLStreamException e) {
                throw new Fault(e);
            }
        }
    }

    protected void setOperationSchemaValidation(Message message) {
        SchemaValidation.SchemaValidationType validationType = ServiceUtils.getSchemaValidationType(message);
        message.put("schema-validation-enabled", validationType);
    }

    protected boolean shouldValidate(Message m4) {
        return ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.OUT, m4);
    }

    protected boolean writeToOutputStream(Message m4, BindingInfo info, Service s2) {
        if (s2 == null) {
            return false;
        }
        String enc = (String)m4.get(Message.ENCODING);
        return "org.apache.cxf.binding.soap.model.SoapBindingInfo".equals(info.getClass().getName()) && "org.apache.cxf.jaxb.JAXBDataBinding".equals(s2.getDataBinding().getClass().getName()) && !MessageUtils.isDOMPresent(m4) && (enc == null || StandardCharsets.UTF_8.name().equals(enc));
    }

    protected <T> DataWriter<T> getDataWriter(Message message, Service service2, Class<T> output) {
        DataWriter<T> writer = service2.getDataBinding().createWriter(output);
        Collection<Attachment> atts = message.getAttachments();
        if (MessageUtils.getContextualBoolean(message, "mtom-enabled", false) && atts == null) {
            atts = new ArrayList<Attachment>();
            message.setAttachments(atts);
        }
        writer.setAttachments(atts);
        writer.setProperty(DataWriter.ENDPOINT, message.getExchange().getEndpoint());
        writer.setProperty(Message.class.getName(), message);
        this.setDataWriterValidation(service2, message, writer);
        return writer;
    }

    private void setDataWriterValidation(Service service2, Message message, DataWriter<?> writer) {
        if (this.shouldValidate(message)) {
            Schema schema = EndpointReferenceUtils.getSchema(service2.getServiceInfos().get(0), message.getExchange().getBus());
            writer.setSchema(schema);
        }
    }

    protected XMLStreamWriter getXMLStreamWriter(Message message) {
        return message.getContent(XMLStreamWriter.class);
    }
}

