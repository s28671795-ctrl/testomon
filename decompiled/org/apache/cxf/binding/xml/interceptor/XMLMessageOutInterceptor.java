/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml.interceptor;

import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.bindings.xformat.XMLBindingMessageFormat;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.interceptors.BareOutInterceptor;
import org.apache.cxf.wsdl.interceptors.WrappedOutInterceptor;

public class XMLMessageOutInterceptor
extends AbstractOutDatabindingInterceptor {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(XMLMessageOutInterceptor.class);

    public XMLMessageOutInterceptor() {
        this("marshal");
    }

    public XMLMessageOutInterceptor(String phase) {
        super(phase);
        this.addAfter(WrappedOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        BindingMessageInfo bmi;
        MessageInfo mi;
        BindingOperationInfo boi = message.getExchange().getBindingOperationInfo();
        if (this.isRequestor(message)) {
            mi = boi.getOperationInfo().getInput();
            bmi = boi.getInput();
        } else {
            mi = boi.getOperationInfo().getOutput();
            bmi = boi.getOutput();
        }
        XMLBindingMessageFormat xmf = bmi.getExtensor(XMLBindingMessageFormat.class);
        QName rootInModel = null;
        if (xmf != null) {
            rootInModel = xmf.getRootNode();
        }
        int mpn = mi.getMessagePartsNumber();
        if (boi.isUnwrapped() || mpn == 1) {
            new BareOutInterceptor().handleMessage(message);
        } else {
            if (rootInModel == null) {
                rootInModel = boi.getName();
            }
            if (mpn == 0 && !boi.isUnwrapped()) {
                this.writeMessage(message, rootInModel, false);
            } else {
                this.writeMessage(message, rootInModel, true);
            }
        }
        XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
        try {
            writer.flush();
        }
        catch (XMLStreamException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_WRITE_EXC", BUNDLE, e));
        }
    }

    private void writeMessage(Message message, QName name, boolean executeBare) {
        XMLStreamWriter xmlWriter = message.getContent(XMLStreamWriter.class);
        try {
            String pfx = name.getPrefix();
            if (StringUtils.isEmpty(pfx)) {
                pfx = "ns1";
            }
            StaxUtils.writeStartElement(xmlWriter, pfx, name.getLocalPart(), name.getNamespaceURI());
            if (executeBare) {
                new BareOutInterceptor().handleMessage(message);
            }
            xmlWriter.writeEndElement();
        }
        catch (XMLStreamException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_WRITE_EXC", BUNDLE, e));
        }
    }
}

