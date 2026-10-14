/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.jms.interceptor;

import javax.xml.namespace.QName;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapBinding;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.jms.interceptor.JMSFault;
import org.apache.cxf.interceptor.Fault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SoapFaultFactory {
    private SoapVersion version;

    public SoapFaultFactory(Binding binding) {
        this.version = ((SoapBinding)binding).getSoapVersion();
    }

    public Fault createFault(JMSFault jmsFault) {
        Fault f = null;
        if (this.version == Soap11.getInstance()) {
            f = this.createSoap11Fault(jmsFault);
            f.initCause(jmsFault);
        } else {
            f = this.createSoap12Fault(jmsFault);
        }
        return f;
    }

    Fault createSoap11Fault(JMSFault jmsFault) {
        return new SoapFault(jmsFault.getReason(), jmsFault.getSubCode());
    }

    Fault createSoap12Fault(JMSFault jmsFault) {
        SoapFault fault = new SoapFault(jmsFault.getReason(), jmsFault.isSender() ? this.version.getSender() : this.version.getReceiver());
        QName subCode = jmsFault.getSubCode();
        fault.setSubCode(subCode);
        Object detail = jmsFault.getDetail();
        if (null == detail) {
            return fault;
        }
        this.setDetail(fault, detail);
        return fault;
    }

    void setDetail(SoapFault fault, Object detail) {
        Element el = null;
        if (detail instanceof Element) {
            el = (Element)detail;
        } else if (detail instanceof Document) {
            el = ((Document)detail).getDocumentElement();
        }
        if (el != null) {
            fault.setDetail(el);
        }
    }

    public String toString(Fault f) {
        return f.toString();
    }
}

