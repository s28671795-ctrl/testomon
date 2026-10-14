/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.AbstractBindingFactory;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.xml.XMLBinding;
import org.apache.cxf.binding.xml.interceptor.XMLFaultInInterceptor;
import org.apache.cxf.binding.xml.interceptor.XMLFaultOutInterceptor;
import org.apache.cxf.binding.xml.interceptor.XMLMessageInInterceptor;
import org.apache.cxf.binding.xml.interceptor.XMLMessageOutInterceptor;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.interceptor.AttachmentInInterceptor;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;
import org.apache.cxf.wsdl.interceptors.WrappedOutInterceptor;

@NoJSR250Annotations(unlessNull={"bus"})
public class XMLBindingFactory
extends AbstractBindingFactory {
    public static final Collection<String> DEFAULT_NAMESPACES = Collections.unmodifiableList(Arrays.asList("http://cxf.apache.org/bindings/xformat", "http://www.w3.org/2004/08/wsdl/http", "http://schemas.xmlsoap.org/wsdl/http/"));

    public XMLBindingFactory() {
    }

    public XMLBindingFactory(Bus b) {
        super(b, DEFAULT_NAMESPACES);
    }

    @Override
    public Binding createBinding(BindingInfo binding) {
        XMLBinding xb = new XMLBinding(binding);
        xb.getInInterceptors().add(new AttachmentInInterceptor());
        xb.getInInterceptors().add(new StaxInInterceptor());
        xb.getInInterceptors().add(new DocLiteralInInterceptor());
        xb.getInInterceptors().add(new XMLMessageInInterceptor());
        xb.getOutInterceptors().add(new AttachmentOutInterceptor());
        xb.getOutInterceptors().add(new StaxOutInterceptor());
        xb.getOutInterceptors().add(new WrappedOutInterceptor());
        xb.getOutInterceptors().add(new XMLMessageOutInterceptor());
        xb.getInFaultInterceptors().add(new XMLFaultInInterceptor());
        xb.getOutFaultInterceptors().add(new StaxOutInterceptor());
        xb.getOutFaultInterceptors().add(new XMLFaultOutInterceptor());
        return xb;
    }

    @Override
    public BindingInfo createBindingInfo(ServiceInfo service2, String namespace, Object config) {
        BindingInfo info = new BindingInfo(service2, "http://cxf.apache.org/bindings/xformat");
        info.setName(new QName(service2.getName().getNamespaceURI(), service2.getName().getLocalPart() + "XMLBinding"));
        for (OperationInfo op : service2.getInterface().getOperations()) {
            this.adjustConcreteNames(op.getInput());
            this.adjustConcreteNames(op.getOutput());
            BindingOperationInfo bop = info.buildOperation(op.getName(), op.getInputName(), op.getOutputName());
            info.addOperation(bop);
        }
        return info;
    }

    private void adjustConcreteNames(MessageInfo mi) {
        if (mi != null) {
            for (MessagePartInfo mpi : mi.getMessageParts()) {
                if (mpi.isElement()) continue;
                mpi.setConcreteName(mpi.getName());
            }
        }
    }
}

