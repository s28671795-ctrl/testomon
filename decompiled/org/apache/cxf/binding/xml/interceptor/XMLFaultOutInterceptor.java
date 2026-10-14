/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml.interceptor;

import java.util.ResourceBundle;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.binding.xml.XMLFault;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.helpers.NSStack;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLFaultOutInterceptor
extends AbstractOutDatabindingInterceptor {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(XMLFaultOutInterceptor.class);

    public XMLFaultOutInterceptor() {
        super("marshal");
    }

    public XMLFaultOutInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        if (this.mustPropogateException(message)) {
            throw (Fault)message.getContent(Exception.class);
        }
        Fault f = (Fault)message.getContent(Exception.class);
        message.put(Message.RESPONSE_CODE, f.getStatusCode());
        NSStack nsStack = new NSStack();
        nsStack.push();
        XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
        XMLFault xmlFault = XMLFault.createFault(f);
        try {
            nsStack.add("http://cxf.apache.org/bindings/xformat");
            String prefix = nsStack.getPrefix("http://cxf.apache.org/bindings/xformat");
            StaxUtils.writeStartElement(writer, prefix, "XMLFault", "http://cxf.apache.org/bindings/xformat");
            StaxUtils.writeStartElement(writer, prefix, "faultstring", "http://cxf.apache.org/bindings/xformat");
            Throwable t2 = xmlFault.getCause();
            writer.writeCharacters(t2 == null ? xmlFault.getMessage() : t2.toString());
            writer.writeEndElement();
            if (xmlFault.getDetail() != null) {
                Element detail = xmlFault.getDetail();
                StaxUtils.writeStartElement(writer, prefix, "detail", "http://cxf.apache.org/bindings/xformat");
                for (Node node = detail.getFirstChild(); node != null; node = node.getNextSibling()) {
                    StaxUtils.writeNode(node, writer, false);
                }
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.flush();
        }
        catch (XMLStreamException xe) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("XML_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)xe);
        }
    }

    @Override
    public void handleFault(Message message) throws Fault {
        if (this.mustPropogateException(message)) {
            throw (Fault)message.getContent(Exception.class);
        }
    }

    protected boolean mustPropogateException(Message m4) {
        return Boolean.TRUE.equals(m4.getExchange().get(Message.PROPOGATE_EXCEPTION));
    }
}

