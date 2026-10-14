/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.io.EOFException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.validation.Schema;
import org.apache.cxf.Bus;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.model.SoapHeaderInfo;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.headers.Header;
import org.apache.cxf.headers.HeaderManager;
import org.apache.cxf.headers.HeaderProcessor;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.WriteOnCloseOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.staxutils.DelegatingXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.w3c.dom.Element;

public class SoapOutInterceptor
extends AbstractSoapInterceptor {
    public static final String WROTE_ENVELOPE_START = "wrote.envelope.start";
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(SoapOutInterceptor.class);
    private Bus bus;

    public SoapOutInterceptor(Bus b) {
        super("write");
        this.bus = b;
    }

    public SoapOutInterceptor(Bus b, String phase) {
        super(phase);
        this.bus = b;
    }

    @Override
    public void handleMessage(SoapMessage message) {
        String cte;
        boolean wroteStart = PropertyUtils.isTrue(message.get(WROTE_ENVELOPE_START));
        if (!wroteStart) {
            this.writeSoapEnvelopeStart(message);
            OutputStream os = message.getContent(OutputStream.class);
            if (!(os instanceof WriteOnCloseOutputStream) && !MessageUtils.isDOMPresent(message)) {
                message.put(WROTE_ENVELOPE_START, (Object)Boolean.TRUE);
            }
        }
        if ((cte = (String)message.get("soap.attachement.content.transfer.encoding")) != null) {
            message.put(Message.CONTENT_TRANSFER_ENCODING, (Object)cte);
        }
        message.getInterceptorChain().add(new SoapOutEndingInterceptor());
    }

    private void writeSoapEnvelopeStart(SoapMessage message) {
        SoapVersion soapVersion = message.getVersion();
        try {
            XMLStreamWriter xtw = message.getContent(XMLStreamWriter.class);
            String soapPrefix = xtw.getPrefix(soapVersion.getNamespace());
            if (StringUtils.isEmpty(soapPrefix)) {
                soapPrefix = "soap";
            }
            if (message.hasAdditionalEnvNs()) {
                Map<String, String> nsMap = message.getEnvelopeNs();
                for (Map.Entry<String, String> entry : nsMap.entrySet()) {
                    if (!soapVersion.getNamespace().equals(entry.getValue())) continue;
                    soapPrefix = entry.getKey();
                }
                xtw.setPrefix(soapPrefix, soapVersion.getNamespace());
                xtw.writeStartElement(soapPrefix, soapVersion.getEnvelope().getLocalPart(), soapVersion.getNamespace());
                xtw.writeNamespace(soapPrefix, soapVersion.getNamespace());
                for (Map.Entry<String, String> entry : nsMap.entrySet()) {
                    if (soapVersion.getNamespace().equals(entry.getValue())) continue;
                    xtw.writeNamespace(entry.getKey(), entry.getValue());
                }
            } else {
                xtw.setPrefix(soapPrefix, soapVersion.getNamespace());
                xtw.writeStartElement(soapPrefix, soapVersion.getEnvelope().getLocalPart(), soapVersion.getNamespace());
                String s2 = xtw.getPrefix(soapVersion.getNamespace());
                if (StringUtils.isEmpty(s2) || soapPrefix.equals(s2)) {
                    xtw.writeNamespace(soapPrefix, soapVersion.getNamespace());
                } else {
                    soapPrefix = s2;
                }
            }
            boolean preexistingHeaders = message.hasHeaders();
            if (preexistingHeaders) {
                xtw.writeStartElement(soapPrefix, soapVersion.getHeader().getLocalPart(), soapVersion.getNamespace());
                List<Header> hdrList = message.getHeaders();
                for (Header header : hdrList) {
                    HeaderProcessor hp;
                    DataBinding b;
                    XMLStreamWriter writer = xtw;
                    if (xtw instanceof W3CDOMStreamWriter) {
                        Element nd = ((W3CDOMStreamWriter)xtw).getCurrentNode();
                        if (header.getObject() instanceof Element && nd.isSameNode(((Element)header.getObject()).getParentNode())) continue;
                    }
                    if (header instanceof SoapHeader) {
                        SoapHeader soapHeader = (SoapHeader)header;
                        writer = new SOAPHeaderWriter(xtw, soapHeader, soapVersion, soapPrefix);
                    }
                    if ((b = header.getDataBinding()) == null && (hp = this.bus.getExtension(HeaderManager.class).getHeaderProcessor(header.getName().getNamespaceURI())) != null) {
                        b = hp.getDataBinding();
                    }
                    if (b != null) {
                        MessagePartInfo part = new MessagePartInfo(header.getName(), null);
                        part.setConcreteName(header.getName());
                        b.createWriter(XMLStreamWriter.class).write(header.getObject(), part, writer);
                        continue;
                    }
                    Element node = (Element)header.getObject();
                    StaxUtils.copy(node, writer);
                }
            }
            boolean endedHeader = this.handleHeaderPart(preexistingHeaders, message, soapPrefix);
            if (preexistingHeaders && !endedHeader) {
                xtw.writeEndElement();
            }
            xtw.writeStartElement(soapPrefix, soapVersion.getBody().getLocalPart(), soapVersion.getNamespace());
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new org.apache.cxf.common.i18n.Message("XML_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e, soapVersion.getSender());
        }
    }

    private boolean handleHeaderPart(boolean preexistingHeaders, SoapMessage message, String soapPrefix) {
        BindingMessageInfo wrappedBmi;
        boolean client;
        boolean endedHeader = false;
        Exchange exchange = message.getExchange();
        BindingOperationInfo bop = exchange.getBindingOperationInfo();
        if (bop == null) {
            return endedHeader;
        }
        XMLStreamWriter xtw = message.getContent(XMLStreamWriter.class);
        boolean startedHeader = false;
        BindingOperationInfo unwrappedOp = bop;
        if (bop.isUnwrapped()) {
            unwrappedOp = bop.getWrappedOperation();
        }
        BindingMessageInfo bmi = (client = this.isRequestor(message)) ? unwrappedOp.getInput() : unwrappedOp.getOutput();
        BindingMessageInfo bindingMessageInfo = wrappedBmi = client ? bop.getInput() : bop.getOutput();
        if (bmi == null) {
            return endedHeader;
        }
        if (wrappedBmi.getMessageInfo().getMessagePartsNumber() > 0) {
            MessageContentsList objs = MessageContentsList.getContentsList(message);
            if (objs == null) {
                return endedHeader;
            }
            SoapVersion soapVersion = message.getVersion();
            List<SoapHeaderInfo> headers = bmi.getExtensors(SoapHeaderInfo.class);
            if (headers == null) {
                return endedHeader;
            }
            for (SoapHeaderInfo header : headers) {
                Object arg;
                MessagePartInfo part = header.getPart();
                if (wrappedBmi != bmi) {
                    part = wrappedBmi.getMessageInfo().addMessagePart(part.getName());
                }
                if (part.getIndex() >= objs.size() || (arg = objs.get(part)) == null) continue;
                objs.remove(part);
                if (!startedHeader && !preexistingHeaders) {
                    try {
                        xtw.writeStartElement(soapPrefix, soapVersion.getHeader().getLocalPart(), soapVersion.getNamespace());
                    }
                    catch (XMLStreamException e) {
                        throw new SoapFault(new org.apache.cxf.common.i18n.Message("XML_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e, soapVersion.getSender());
                    }
                    startedHeader = true;
                }
                DataWriter<XMLStreamWriter> dataWriter = this.getDataWriter(message);
                dataWriter.write(arg, header.getPart(), xtw);
            }
            if (startedHeader || preexistingHeaders) {
                try {
                    xtw.writeEndElement();
                    endedHeader = true;
                }
                catch (XMLStreamException e) {
                    throw new SoapFault(new org.apache.cxf.common.i18n.Message("XML_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e, soapVersion.getSender());
                }
            }
        }
        return endedHeader;
    }

    protected DataWriter<XMLStreamWriter> getDataWriter(Message message) {
        Service service2 = ServiceModelUtil.getService(message.getExchange());
        DataWriter<XMLStreamWriter> dataWriter = service2.getDataBinding().createWriter(XMLStreamWriter.class);
        if (dataWriter == null) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("NO_DATAWRITER", BUNDLE, service2.getName()));
        }
        dataWriter.setAttachments(message.getAttachments());
        this.setDataWriterValidation(service2, message, dataWriter);
        return dataWriter;
    }

    private void setDataWriterValidation(Service service2, Message message, DataWriter<?> writer) {
        if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.OUT, message)) {
            Schema schema = EndpointReferenceUtils.getSchema(service2.getServiceInfos().get(0), message.getExchange().getBus());
            writer.setSchema(schema);
        }
    }

    public static class SOAPHeaderWriter
    extends DelegatingXMLStreamWriter {
        final SoapHeader soapHeader;
        final SoapVersion soapVersion;
        final String soapPrefix;
        boolean firstDone;

        public SOAPHeaderWriter(XMLStreamWriter writer, SoapHeader header, SoapVersion version, String pfx) {
            super(writer);
            this.soapHeader = header;
            this.soapVersion = version;
            this.soapPrefix = pfx;
        }

        @Override
        public void writeAttribute(String prefix, String uri, String local, String value) throws XMLStreamException {
            if (this.soapVersion.getNamespace().equals(uri) && (local.equals(this.soapVersion.getAttrNameMustUnderstand()) || local.equals(this.soapVersion.getAttrNameRole()))) {
                return;
            }
            super.writeAttribute(prefix, uri, local, value);
        }

        @Override
        public void writeAttribute(String uri, String local, String value) throws XMLStreamException {
            if (this.soapVersion.getNamespace().equals(uri) && (local.equals(this.soapVersion.getAttrNameMustUnderstand()) || local.equals(this.soapVersion.getAttrNameRole()))) {
                return;
            }
            super.writeAttribute(uri, local, value);
        }

        private void writeSoapAttributes() throws XMLStreamException {
            if (!this.firstDone) {
                boolean mu;
                this.firstDone = true;
                if (!StringUtils.isEmpty(this.soapHeader.getActor())) {
                    super.writeAttribute(this.soapPrefix, this.soapVersion.getNamespace(), this.soapVersion.getAttrNameRole(), this.soapHeader.getActor());
                }
                if (mu = this.soapHeader.isMustUnderstand()) {
                    String mul = this.soapVersion.getAttrValueMustUnderstand(mu);
                    super.writeAttribute(this.soapPrefix, this.soapVersion.getNamespace(), this.soapVersion.getAttrNameMustUnderstand(), mul);
                }
            }
        }

        @Override
        public void writeStartElement(String arg0, String arg1, String arg2) throws XMLStreamException {
            super.writeStartElement(arg0, arg1, arg2);
            this.writeSoapAttributes();
        }

        @Override
        public void writeStartElement(String arg0, String arg1) throws XMLStreamException {
            super.writeStartElement(arg0, arg1);
            this.writeSoapAttributes();
        }

        @Override
        public void writeStartElement(String arg0) throws XMLStreamException {
            super.writeStartElement(arg0);
            this.writeSoapAttributes();
        }
    }

    public class SoapOutEndingInterceptor
    extends AbstractSoapInterceptor {
        public SoapOutEndingInterceptor() {
            super(SoapOutEndingInterceptor.class.getName(), "write-ending");
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            block3: {
                try {
                    XMLStreamWriter xtw = message.getContent(XMLStreamWriter.class);
                    if (xtw != null) {
                        xtw.writeEndElement();
                        xtw.writeEndElement();
                        xtw.writeEndDocument();
                        xtw.flush();
                    }
                }
                catch (XMLStreamException e) {
                    if (e.getCause() instanceof EOFException) break block3;
                    SoapVersion soapVersion = message.getVersion();
                    throw new SoapFault(new org.apache.cxf.common.i18n.Message("XML_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e, soapVersion.getSender());
                }
            }
        }
    }
}

