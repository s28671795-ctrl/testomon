/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.saaj;

import java.lang.reflect.Method;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import org.apache.cxf.common.util.StringUtils;
import org.w3c.dom.Element;

public final class SAAJUtils {
    private SAAJUtils() {
    }

    public static SOAPHeader getHeader(SOAPMessage m4) throws SOAPException {
        try {
            return m4.getSOAPHeader();
        }
        catch (UnsupportedOperationException ex) {
            return m4.getSOAPPart().getEnvelope().getHeader();
        }
    }

    public static SOAPBody getBody(SOAPMessage m4) throws SOAPException {
        try {
            return m4.getSOAPBody();
        }
        catch (UnsupportedOperationException ex) {
            return m4.getSOAPPart().getEnvelope().getBody();
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static void setFaultCode(SOAPFault f, QName code) throws SOAPException {
        if (f.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope")) {
            try {
                f.setFaultCode(code);
            }
            catch (SOAPException ex) {
                f.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
                f.appendFaultSubcode(code);
            }
        } else {
            try {
                f.setFaultCode(code);
            }
            catch (Throwable t2) {
                int count = 1;
                String pfx = "fc1";
                while (!StringUtils.isEmpty(f.getNamespaceURI(pfx))) {
                    pfx = "fc" + ++count;
                }
                if (code.getNamespaceURI() != null && !"".equals(code.getNamespaceURI())) {
                    f.addNamespaceDeclaration(pfx, code.getNamespaceURI());
                } else {
                    f.addNamespaceDeclaration(pfx, f.getNamespaceURI());
                }
                f.setFaultCode(pfx + ":" + code.getLocalPart());
            }
        }
    }

    public static Element adjustPrefix(Element e, String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        try {
            String s2 = e.getPrefix();
            if (!prefix.equals(s2)) {
                e.setPrefix(prefix);
                if (e instanceof SOAPElement) {
                    ((SOAPElement)e).removeNamespaceDeclaration(s2);
                } else if (e.getClass().getName().equals("com.sun.org.apache.xerces.internal.dom.ElementNSImpl")) {
                    try {
                        Method method2 = e.getClass().getMethod("removeAttribute", String.class);
                        method2.invoke((Object)e, "xmlns:" + s2);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return e;
    }
}

