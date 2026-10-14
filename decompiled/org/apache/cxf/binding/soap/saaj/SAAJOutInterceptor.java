/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.saaj;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.binding.soap.saaj.SAAJStreamWriter;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.w3c.dom.Node;

@NoJSR250Annotations
public class SAAJOutInterceptor
extends AbstractSoapInterceptor {
    public static final String ORIGINAL_XML_WRITER = SAAJOutInterceptor.class.getName() + ".original.xml.writer";
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(SAAJOutInterceptor.class);
    private MessageFactory factory11;
    private MessageFactory factory12;

    public SAAJOutInterceptor() {
        super("pre-protocol");
    }

    public synchronized MessageFactory getFactory(SoapMessage message) throws SOAPException {
        if (message.getVersion() instanceof Soap11) {
            if (this.factory11 == null) {
                this.factory11 = SAAJFactoryResolver.createMessageFactory(message.getVersion());
            }
            return this.factory11;
        }
        if (message.getVersion() instanceof Soap12) {
            if (this.factory12 == null) {
                this.factory12 = SAAJFactoryResolver.createMessageFactory(message.getVersion());
            }
            return this.factory12;
        }
        return SAAJFactoryResolver.createMessageFactory(null);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        SOAPMessage saaj = message.getContent(SOAPMessage.class);
        try {
            if (message.hasHeaders() && saaj != null && saaj.getSOAPPart().getEnvelope().getHeader() == null) {
                saaj.getSOAPPart().getEnvelope().addHeader();
            }
        }
        catch (SOAPException e) {
            throw new SoapFault(new Message("SOAPEXCEPTION", BUNDLE, e.getMessage()), (Throwable)e, message.getVersion().getSender());
        }
        if (saaj == null) {
            SoapVersion version = message.getVersion();
            try {
                MessageFactory factory2 = this.getFactory(message);
                SOAPMessage soapMessage = factory2.createMessage();
                SOAPPart soapPart = soapMessage.getSOAPPart();
                XMLStreamWriter origWriter = (XMLStreamWriter)message.get(ORIGINAL_XML_WRITER);
                if (origWriter == null) {
                    origWriter = message.getContent(XMLStreamWriter.class);
                }
                message.put(ORIGINAL_XML_WRITER, (Object)origWriter);
                SAAJStreamWriter writer = new SAAJStreamWriter(soapPart);
                message.setContent(XMLStreamWriter.class, writer);
                message.setContent(SOAPMessage.class, soapMessage);
                message.setContent(Node.class, soapMessage.getSOAPPart());
            }
            catch (SOAPException e) {
                throw new SoapFault(new Message("SOAPEXCEPTION", BUNDLE, e.getMessage()), (Throwable)e, version.getSender());
            }
        } else if (!message.containsKey(ORIGINAL_XML_WRITER)) {
            XMLStreamWriter origWriter = message.getContent(XMLStreamWriter.class);
            message.put(ORIGINAL_XML_WRITER, (Object)origWriter);
            XMLStreamWriter dummyWriter = StaxUtils.createXMLStreamWriter(new OutputStream(){

                @Override
                public void write(int b) throws IOException {
                }

                @Override
                public void write(byte[] b, int off, int len) throws IOException {
                }
            });
            message.setContent(XMLStreamWriter.class, dummyWriter);
        }
        message.getInterceptorChain().add(SAAJOutEndingInterceptor.INSTANCE);
    }

    @Override
    public void handleFault(SoapMessage message) {
        super.handleFault(message);
        message.removeContent(SOAPMessage.class);
        message.remove("wrote.envelope.start");
        XMLStreamWriter writer = (XMLStreamWriter)message.get(ORIGINAL_XML_WRITER);
        if (writer != null) {
            message.setContent(XMLStreamWriter.class, writer);
            message.remove(ORIGINAL_XML_WRITER);
        }
    }

    public static class SAAJOutEndingInterceptor
    extends AbstractSoapInterceptor {
        public static final SAAJOutEndingInterceptor INSTANCE = new SAAJOutEndingInterceptor();

        public SAAJOutEndingInterceptor() {
            super(SAAJOutEndingInterceptor.class.getName(), "pre-protocol-ending");
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            if (soapMessage != null) {
                if (soapMessage.countAttachments() > 0) {
                    if (message.getAttachments() == null) {
                        message.setAttachments(new ArrayList<Attachment>(soapMessage.countAttachments()));
                    }
                    Iterator it = CastUtils.cast(soapMessage.getAttachments());
                    while (it.hasNext()) {
                        AttachmentPart part = (AttachmentPart)it.next();
                        String id = AttachmentUtil.cleanContentId(part.getContentId());
                        AttachmentImpl att = new AttachmentImpl(id);
                        try {
                            att.setDataHandler(part.getDataHandler());
                        }
                        catch (SOAPException e) {
                            throw new Fault(e);
                        }
                        Iterator it2 = CastUtils.cast(part.getAllMimeHeaders());
                        while (it2.hasNext()) {
                            MimeHeader header = (MimeHeader)it2.next();
                            att.setHeader(header.getName(), header.getValue());
                        }
                        message.getAttachments().add(att);
                    }
                }
                XMLStreamWriter writer = (XMLStreamWriter)message.get(ORIGINAL_XML_WRITER);
                message.remove(ORIGINAL_XML_WRITER);
                try {
                    if (writer != null) {
                        StaxUtils.copy((XMLStreamReader)new W3CDOMStreamReader(soapMessage.getSOAPPart()), writer);
                        writer.flush();
                        message.setContent(XMLStreamWriter.class, writer);
                    }
                }
                catch (XMLStreamException e) {
                    if (e.getCause() instanceof ConnectException) {
                        throw new SoapFault(e.getCause().getMessage(), (Throwable)e, message.getVersion().getSender());
                    }
                    throw new SoapFault(new Message("SOAPEXCEPTION", BUNDLE, e.getMessage()), (Throwable)e, message.getVersion().getSender());
                }
            }
        }
    }
}

