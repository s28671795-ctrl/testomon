/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.RPCInInterceptor;
import org.apache.cxf.binding.soap.model.SoapHeaderInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.wsdl.interceptors.BareInInterceptor;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SoapHeaderInterceptor
extends AbstractInDatabindingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(SoapHeaderInterceptor.class);

    public SoapHeaderInterceptor() {
        super("unmarshal");
        this.addAfter(BareInInterceptor.class.getName());
        this.addAfter(RPCInInterceptor.class.getName());
        this.addAfter(DocLiteralInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message m4) throws Fault {
        boolean client;
        BindingMessageInfo bmi;
        BindingOperationInfo bop;
        SoapMessage message = (SoapMessage)m4;
        SoapVersion soapVersion = message.getVersion();
        Exchange exchange = message.getExchange();
        MessageContentsList parameters = MessageContentsList.getContentsList(message);
        if (null == parameters) {
            parameters = new MessageContentsList();
        }
        if (null == (bop = exchange.getBindingOperationInfo())) {
            return;
        }
        if (bop.isUnwrapped()) {
            bop = bop.getWrappedOperation();
        }
        BindingMessageInfo bindingMessageInfo = bmi = (client = this.isRequestor(message)) ? bop.getOutput() : bop.getInput();
        if (bmi == null) {
            return;
        }
        List<SoapHeaderInfo> headers = bmi.getExtensors(SoapHeaderInfo.class);
        if (headers == null || headers.isEmpty()) {
            return;
        }
        boolean supportsNode = this.supportsDataReader(message, Node.class);
        Service service2 = ServiceModelUtil.getService(message.getExchange());
        for (SoapHeaderInfo header : headers) {
            MessagePartInfo mpi = header.getPart();
            try {
                if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.IN, message)) {
                    Schema schema = EndpointReferenceUtils.getSchema(service2.getServiceInfos().get(0), message.getExchange().getBus());
                    this.validateHeader(message, mpi, schema);
                }
            }
            catch (Fault f) {
                if (!this.isRequestor(message)) {
                    f.setFaultCode(Fault.FAULT_CODE_CLIENT);
                }
                throw f;
            }
            if (mpi.getTypeClass() == null) continue;
            Header param2 = this.findHeader(message, mpi);
            Object object = null;
            if (param2 != null) {
                message.getHeaders().remove(param2);
                if (param2.getDataBinding() == null) {
                    Node source2 = (Node)param2.getObject();
                    if (source2 instanceof Element) {
                        Element el = (Element)source2;
                        el.removeAttributeNS(soapVersion.getNamespace(), soapVersion.getAttrNameMustUnderstand());
                        el.removeAttributeNS(soapVersion.getNamespace(), soapVersion.getAttrNameRole());
                    }
                    if (supportsNode) {
                        object = this.getNodeDataReader(message).read(mpi, source2);
                    } else {
                        W3CDOMStreamReader reader = new W3CDOMStreamReader((Element)source2);
                        try {
                            reader.nextTag();
                        }
                        catch (XMLStreamException xMLStreamException) {
                            // empty catch block
                        }
                        object = this.getDataReader(message, XMLStreamReader.class).read(mpi, reader);
                    }
                } else {
                    object = param2.getObject();
                }
            }
            parameters.put(mpi, object);
        }
        if (!parameters.isEmpty()) {
            message.setContent(List.class, parameters);
        }
    }

    private void validateHeader(final SoapMessage message, MessagePartInfo mpi, Schema schema) {
        Header param2 = this.findHeader(message, mpi);
        if (param2 != null && param2.getDataBinding() == null) {
            Node source2 = (Node)param2.getObject();
            if (!(source2 instanceof Element)) {
                return;
            }
            if (schema != null) {
                final Element el = (Element)source2;
                DOMSource ds = new DOMSource(el);
                try {
                    Validator v = schema.newValidator();
                    ErrorHandler errorHandler = new ErrorHandler(){

                        @Override
                        public void warning(SAXParseException exception) throws SAXException {
                        }

                        @Override
                        public void error(SAXParseException exception) throws SAXException {
                            String msg = exception.getMessage();
                            if (msg.contains(el.getLocalName()) && (msg.contains(":" + message.getVersion().getAttrNameRole()) || msg.contains(":" + message.getVersion().getAttrNameMustUnderstand()))) {
                                return;
                            }
                            throw exception;
                        }

                        @Override
                        public void fatalError(SAXParseException exception) throws SAXException {
                            throw exception;
                        }
                    };
                    v.setErrorHandler(errorHandler);
                    v.validate(ds);
                }
                catch (IOException | SAXException e) {
                    throw new Fault("COULD_NOT_VALIDATE_SOAP_HEADER_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
                }
            }
        }
    }

    private Header findHeader(SoapMessage message, MessagePartInfo mpi) {
        return message.getHeader(mpi.getConcreteName());
    }
}

