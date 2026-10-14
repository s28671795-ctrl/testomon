/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPathConstants;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.Soap11FaultInInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.XPathUtils;
import org.apache.cxf.interceptor.ClientFaultConverter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.staxutils.FragmentStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Soap12FaultInInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(Soap12FaultInInterceptor.class);

    public Soap12FaultInInterceptor() {
        super("unmarshal");
        this.addBefore(ClientFaultConverter.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        if (message.getVersion() == Soap11.getInstance()) {
            new Soap11FaultInInterceptor().handleMessage(message);
            return;
        }
        XMLStreamReader reader = message.getContent(XMLStreamReader.class);
        message.setContent(Exception.class, Soap12FaultInInterceptor.unmarshalFault(message, reader));
    }

    public static SoapFault unmarshalFault(SoapMessage message, XMLStreamReader reader) {
        String exMessage = null;
        QName faultCode = null;
        LinkedList<QName> subCodes = null;
        String role = null;
        String node = null;
        Element detail = null;
        String lang = null;
        HashMap<String, String> ns = new HashMap<String, String>();
        ns.put("s", "http://www.w3.org/2003/05/soap-envelope");
        XPathUtils xu = new XPathUtils(ns);
        try {
            Node mainNode = message.getContent(Node.class);
            Node fault = null;
            if (reader instanceof W3CDOMStreamReader) {
                W3CDOMStreamReader dr = (W3CDOMStreamReader)reader;
                fault = dr.getCurrentElement();
                dr.consumeFrame();
            } else if (mainNode != null) {
                Node bodyNode = (Node)xu.getValue("//s:Body", mainNode, XPathConstants.NODE);
                StaxUtils.readDocElements(bodyNode.getOwnerDocument(), bodyNode, new FragmentStreamReader(reader), false, false);
                fault = (Element)xu.getValue("//s:Fault", bodyNode, XPathConstants.NODE);
            } else {
                fault = StaxUtils.read(new FragmentStreamReader(reader));
            }
            fault = DOMUtils.getDomElement(fault);
            Element el = (Element)xu.getValue("//s:Fault/s:Code/s:Value", fault, XPathConstants.NODE);
            if (el != null) {
                faultCode = DOMUtils.createQName(el.getTextContent(), el);
            }
            if ((el = (Element)xu.getValue("//s:Fault/s:Code/s:Subcode", fault, XPathConstants.NODE)) != null) {
                subCodes = new LinkedList<QName>();
                NodeList vlist = el.getElementsByTagNameNS("http://www.w3.org/2003/05/soap-envelope", "Value");
                for (int i = 0; i < vlist.getLength(); ++i) {
                    Node v = vlist.item(i);
                    subCodes.add(DOMUtils.createQName(v.getTextContent(), v));
                }
            }
            exMessage = (String)xu.getValue("//s:Fault/s:Reason/s:Text/text()", fault, XPathConstants.STRING);
            lang = (String)xu.getValue("//s:Fault/s:Reason/s:Text/@xml:lang", fault, XPathConstants.STRING);
            Node detailNode = (Node)xu.getValue("//s:Fault/s:Detail", fault, XPathConstants.NODE);
            if (detailNode != null) {
                detail = (Element)detailNode;
            }
            role = (String)xu.getValue("//s:Fault/s:Role/text()", fault, XPathConstants.STRING);
            node = (String)xu.getValue("//s:Fault/s:Node/text()", fault, XPathConstants.STRING);
        }
        catch (XMLStreamException e) {
            throw new SoapFault("Could not parse message.", (Throwable)e, message.getVersion().getSender());
        }
        if (faultCode == null) {
            faultCode = Soap12.getInstance().getReceiver();
            exMessage = new Message("INVALID_FAULT", LOG, new Object[0]).toString();
        }
        SoapFault fault = new SoapFault(exMessage, faultCode);
        fault.setSubCodes(subCodes);
        fault.setDetail(detail);
        fault.setRole(role);
        fault.setNode(node);
        fault.setLang(lang);
        return fault;
    }
}

