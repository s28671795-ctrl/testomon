/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.logical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.jaxws.handler.logical.LogicalMessageContextImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.message.XMLMessage;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LogicalMessageImpl
implements LogicalMessage {
    private static final Logger LOG = LogUtils.getL7dLogger(LogicalMessageImpl.class);
    private final LogicalMessageContextImpl msgContext;

    public LogicalMessageImpl(LogicalMessageContextImpl lmctx) {
        this.msgContext = lmctx;
    }

    @Override
    public Source getPayload() {
        Source source2 = null;
        Service.Mode mode = this.msgContext.getWrappedMessage().getExchange().get(Service.Mode.class);
        if (mode != null) {
            source2 = this.handleDispatchProviderCase(mode);
        } else {
            Message message = this.msgContext.getWrappedMessage();
            source2 = message.getContent(Source.class);
            if (source2 == null) {
                SOAPMessage msg = message.getContent(SOAPMessage.class);
                XMLStreamReader reader = null;
                if (msg != null) {
                    try {
                        Node node;
                        for (node = SAAJUtils.getBody(msg).getFirstChild(); node != null && !(node instanceof Element); node = node.getNextSibling()) {
                        }
                        source2 = new DOMSource(node);
                        reader = StaxUtils.createXMLStreamReader(source2);
                    }
                    catch (SOAPException e) {
                        throw new Fault(e);
                    }
                }
                if (source2 == null) {
                    try {
                        DocumentFragment doc = DOMUtils.getEmptyDocument().createDocumentFragment();
                        W3CDOMStreamWriter writer = new W3CDOMStreamWriter(doc);
                        reader = message.getContent(XMLStreamReader.class);
                        StaxUtils.toNextTag(reader);
                        StaxUtils.copy(reader, writer, true);
                        doc.appendChild(DOMUtils.getFirstElement(writer.getCurrentFragment()));
                        source2 = new DOMSource(DOMUtils.getFirstElement(doc));
                        reader = StaxUtils.createXMLStreamReader(DOMUtils.getFirstElement(doc));
                    }
                    catch (XMLStreamException e) {
                        throw new Fault(e);
                    }
                }
                message.setContent(XMLStreamReader.class, reader);
                message.setContent(Source.class, source2);
            } else if (!(source2 instanceof DOMSource)) {
                W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
                XMLStreamReader reader = message.getContent(XMLStreamReader.class);
                if (reader == null) {
                    reader = StaxUtils.createXMLStreamReader(source2);
                }
                try {
                    StaxUtils.copy(reader, (XMLStreamWriter)writer);
                }
                catch (XMLStreamException e) {
                    throw new Fault(e);
                }
                source2 = new DOMSource(writer.getDocument().getDocumentElement());
                reader = StaxUtils.createXMLStreamReader(writer.getDocument());
                message.setContent(XMLStreamReader.class, reader);
                message.setContent(Source.class, source2);
            }
        }
        return source2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Source handleDispatchProviderCase(Service.Mode mode) {
        Source source2 = null;
        Message message = this.msgContext.getWrappedMessage();
        Source obj = message.getContent(Source.class);
        if (message instanceof SoapMessage) {
            Throwable throwable;
            CachedOutputStream cos;
            if (obj instanceof StreamSource) {
                try {
                    cos = new CachedOutputStream();
                    throwable = null;
                    try {
                        StaxUtils.copy(obj, (OutputStream)cos);
                        obj = new StreamSource(cos.getInputStream());
                        message.setContent(Source.class, new StreamSource(cos.getInputStream()));
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (cos != null) {
                            if (throwable != null) {
                                try {
                                    cos.close();
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                            } else {
                                cos.close();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    throw new Fault(e);
                }
            }
            if (mode == Service.Mode.PAYLOAD) {
                return obj;
            }
            try {
                cos = new CachedOutputStream();
                throwable = null;
                try {
                    StaxUtils.copy(obj, (OutputStream)cos);
                    InputStream in = cos.getInputStream();
                    SOAPMessage msg = this.initSOAPMessage(in);
                    source2 = new DOMSource(SAAJUtils.getBody(msg).getFirstChild());
                    in.close();
                    return source2;
                }
                catch (Throwable throwable4) {
                    throwable = throwable4;
                    throw throwable4;
                }
                finally {
                    if (cos != null) {
                        if (throwable != null) {
                            try {
                                cos.close();
                            }
                            catch (Throwable throwable5) {
                                throwable.addSuppressed(throwable5);
                            }
                        } else {
                            cos.close();
                        }
                    }
                }
            }
            catch (Exception e) {
                throw new Fault(e);
            }
        }
        if (!(message instanceof XMLMessage)) return source2;
        if (obj != null) {
            return obj;
        }
        if (message.getContent(DataSource.class) == null) return source2;
        throw new Fault(new org.apache.cxf.common.i18n.Message("GETPAYLOAD_OF_DATASOURCE_NOT_VALID_XMLHTTPBINDING", LOG, new Object[0]));
    }

    @Override
    public void setPayload(Source s2) {
        Message message = this.msgContext.getWrappedMessage();
        Service.Mode mode = (Service.Mode)((Object)this.msgContext.getWrappedMessage().getContextualProperty(Service.Mode.class.getName()));
        SOAPMessage m4 = message.getContent(SOAPMessage.class);
        if (m4 != null && !MessageUtils.isOutbound(message)) {
            try {
                SAAJUtils.getBody(m4).removeContents();
                W3CDOMStreamWriter writer = new W3CDOMStreamWriter(SAAJUtils.getBody(m4));
                StaxUtils.copy(s2, (XMLStreamWriter)writer);
                writer.flush();
                writer.close();
                s2 = mode == Service.Mode.MESSAGE ? new DOMSource(m4.getSOAPPart()) : new DOMSource(SAAJUtils.getBody(m4).getFirstChild());
                W3CDOMStreamReader r = new W3CDOMStreamReader(DOMUtils.getFirstElement(SAAJUtils.getBody(m4)));
                message.setContent(XMLStreamReader.class, r);
            }
            catch (Exception e) {
                throw new Fault(e);
            }
        } else if (mode != null) {
            if (message instanceof SoapMessage) {
                if (mode == Service.Mode.MESSAGE) {
                    try {
                        SOAPMessage msg = this.initSOAPMessage(null);
                        this.write(s2, SAAJUtils.getBody(msg));
                        s2 = new DOMSource(msg.getSOAPPart());
                    }
                    catch (Exception e) {
                        throw new Fault(e);
                    }
                }
            } else if (message instanceof XMLMessage && message.getContent(DataSource.class) != null) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("GETPAYLOAD_OF_DATASOURCE_NOT_VALID_XMLHTTPBINDING", LOG, new Object[0]));
            }
        } else {
            XMLStreamReader reader = StaxUtils.createXMLStreamReader(s2);
            this.msgContext.getWrappedMessage().setContent(XMLStreamReader.class, reader);
        }
        this.msgContext.getWrappedMessage().setContent(Source.class, s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object getPayload(JAXBContext arg0) {
        Object object;
        Source s2 = this.getPayload();
        if (!(s2 instanceof DOMSource)) return JAXBUtils.unmarshall(arg0, this.getPayload());
        DOMSource ds = (DOMSource)s2;
        ds.setNode(DOMUtils.getDomElement(ds.getNode()));
        Node parent = ds.getNode().getParentNode();
        Node next = ds.getNode().getNextSibling();
        if (parent instanceof DocumentFragment) {
            parent.removeChild(ds.getNode());
        }
        try {
            object = JAXBUtils.unmarshall(arg0, ds);
        }
        catch (Throwable throwable) {
            try {
                if (!(parent instanceof DocumentFragment)) throw throwable;
                parent.insertBefore(ds.getNode(), next);
                throw throwable;
            }
            catch (JAXBException e) {
                throw new WebServiceException(e);
            }
        }
        if (!(parent instanceof DocumentFragment)) return object;
        parent.insertBefore(ds.getNode(), next);
        return object;
    }

    @Override
    public void setPayload(Object arg0, JAXBContext arg1) {
        try {
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
            arg1.createMarshaller().marshal(arg0, writer);
            DOMSource source2 = new DOMSource(writer.getDocument().getDocumentElement());
            this.setPayload(source2);
        }
        catch (JAXBException e) {
            throw new WebServiceException(e);
        }
    }

    private void write(Source source2, Node n) {
        try {
            if (source2 instanceof DOMSource && ((DOMSource)source2).getNode() == null) {
                return;
            }
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter((Element)n);
            StaxUtils.copy(source2, (XMLStreamWriter)writer);
        }
        catch (XMLStreamException e) {
            throw new Fault(e);
        }
    }

    private SOAPMessage initSOAPMessage(InputStream is) throws SOAPException, IOException {
        SOAPMessage msg = null;
        msg = is != null ? SAAJFactoryResolver.createMessageFactory(null).createMessage(null, is) : SAAJFactoryResolver.createMessageFactory(null).createMessage();
        msg.setProperty("javax.xml.soap.write-xml-declaration", "true");
        msg.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
        msg.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        return msg;
    }
}

