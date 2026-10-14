/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.interceptors;

import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.interceptors.BareOutInterceptor;

public class WrappedOutInterceptor
extends AbstractOutDatabindingInterceptor {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(WrappedOutInterceptor.class);
    private final WrappedOutEndingInterceptor ending;

    public WrappedOutInterceptor() {
        this("marshal");
    }

    public WrappedOutInterceptor(String phase) {
        super(phase);
        this.ending = new WrappedOutEndingInterceptor(phase + "-ending");
        this.addBefore(BareOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) {
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop != null && bop.isUnwrapped()) {
            XMLStreamWriter xmlWriter = message.getContent(XMLStreamWriter.class);
            MessageInfo messageInfo = this.isRequestor(message) ? bop.getWrappedOperation().getOperationInfo().getInput() : bop.getWrappedOperation().getOperationInfo().getOutput();
            QName name = messageInfo.getFirstMessagePart().getConcreteName();
            try {
                String pfx = null;
                Service service2 = message.getExchange().getService();
                if (service2.getDataBinding().getDeclaredNamespaceMappings() != null) {
                    pfx = service2.getDataBinding().getDeclaredNamespaceMappings().get(name.getNamespaceURI());
                }
                if (pfx == null) {
                    pfx = StaxUtils.getUniquePrefix(xmlWriter, name.getNamespaceURI(), false);
                }
                xmlWriter.setPrefix(pfx, name.getNamespaceURI());
                xmlWriter.writeStartElement(pfx, name.getLocalPart(), name.getNamespaceURI());
                if (StringUtils.isEmpty(pfx)) {
                    xmlWriter.writeDefaultNamespace(name.getNamespaceURI());
                } else {
                    xmlWriter.writeNamespace(pfx, name.getNamespaceURI());
                }
            }
            catch (XMLStreamException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e);
            }
            message.getInterceptorChain().add(this.ending);
        }
    }

    public static class WrappedOutEndingInterceptor
    extends AbstractOutDatabindingInterceptor {
        public WrappedOutEndingInterceptor(String phase) {
            super(phase);
        }

        @Override
        public void handleMessage(Message message) {
            try {
                XMLStreamWriter xtw = message.getContent(XMLStreamWriter.class);
                if (xtw != null) {
                    xtw.writeEndElement();
                }
            }
            catch (XMLStreamException e) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_WRITE_EXC", BUNDLE, new Object[0]), (Throwable)e);
            }
        }
    }
}

