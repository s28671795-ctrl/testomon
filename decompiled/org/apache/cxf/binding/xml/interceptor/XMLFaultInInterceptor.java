/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml.interceptor;

import java.util.ResourceBundle;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.binding.xml.XMLFault;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.ClientFaultConverter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.FragmentStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Element;

public class XMLFaultInInterceptor
extends AbstractInDatabindingInterceptor {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(XMLFaultInInterceptor.class);

    public XMLFaultInInterceptor() {
        this("unmarshal");
    }

    public XMLFaultInInterceptor(String phase) {
        super(phase);
        this.addBefore(ClientFaultConverter.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        XMLStreamReader xsr = message.getContent(XMLStreamReader.class);
        DepthXMLStreamReader reader = new DepthXMLStreamReader(xsr);
        try {
            reader.nextTag();
            if (!StaxUtils.toNextElement(reader)) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("ILLEGAL_XMLFAULT_FORMAT", BUNDLE, new Object[0]));
            }
            String exMessage = reader.getElementText();
            XMLFault fault = new XMLFault(exMessage);
            reader.nextTag();
            if (StaxUtils.toNextElement(reader)) {
                Element detail = StaxUtils.read(new FragmentStreamReader(reader)).getDocumentElement();
                fault.setDetail(detail);
            }
            message.setContent(Exception.class, fault);
        }
        catch (XMLStreamException xse) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_READ_EXC", BUNDLE, new Object[0]));
        }
    }
}

