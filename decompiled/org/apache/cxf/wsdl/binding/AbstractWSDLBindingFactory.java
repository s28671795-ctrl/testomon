/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.binding;

import java.util.Collection;
import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.extensions.ElementExtensible;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.AbstractBindingFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.wsdl.binding.WSDLBindingFactory;

public abstract class AbstractWSDLBindingFactory
extends AbstractBindingFactory
implements WSDLBindingFactory {
    public AbstractWSDLBindingFactory() {
    }

    public AbstractWSDLBindingFactory(Collection<String> ns) {
        super(ns);
    }

    public AbstractWSDLBindingFactory(Bus b) {
        super(b);
    }

    public AbstractWSDLBindingFactory(Bus b, Collection<String> ns) {
        super(b, ns);
    }

    @Override
    public BindingInfo createBindingInfo(ServiceInfo service2, Binding binding, String ns) {
        BindingInfo bi = this.createBindingInfo(service2, ns, null);
        return this.initializeBindingInfo(service2, binding, bi);
    }

    protected BindingInfo initializeBindingInfo(ServiceInfo service2, Binding binding, BindingInfo bi) {
        bi.setName(binding.getQName());
        this.copyExtensors(bi, binding, null);
        for (BindingOperation bop : CastUtils.cast(binding.getBindingOperations(), BindingOperation.class)) {
            String portTypeNs;
            QName opName;
            BindingOperationInfo bop2;
            String inName = null;
            String outName = null;
            if (bop.getBindingInput() != null) {
                inName = bop.getBindingInput().getName();
            }
            if (bop.getBindingOutput() != null) {
                outName = bop.getBindingOutput().getName();
            }
            if ((bop2 = bi.getOperation(opName = new QName(portTypeNs = binding.getPortType().getQName().getNamespaceURI(), bop.getName()))) == null && (bop2 = bi.buildOperation(opName, inName, outName)) != null) {
                bi.addOperation(bop2);
            }
            if (bop2 == null) continue;
            this.copyExtensors(bop2, bop, bop2);
            if (bop.getBindingInput() != null) {
                this.copyExtensors(bop2.getInput(), bop.getBindingInput(), bop2);
            }
            if (bop.getBindingOutput() != null) {
                this.copyExtensors(bop2.getOutput(), bop.getBindingOutput(), bop2);
            }
            for (BindingFault f : CastUtils.cast(bop.getBindingFaults().values(), BindingFault.class)) {
                if (StringUtils.isEmpty(f.getName())) {
                    throw new IllegalArgumentException("wsdl:fault and soap:fault elements must have a name attribute.");
                }
                this.copyExtensors(bop2.getFault(new QName(service2.getTargetNamespace(), f.getName())), bop.getBindingFault(f.getName()), bop2);
            }
        }
        return bi;
    }

    private void copyExtensors(AbstractPropertiesHolder info, ElementExtensible extElement, BindingOperationInfo bop) {
        if (info != null) {
            for (ExtensibilityElement ext : CastUtils.cast(extElement.getExtensibilityElements(), ExtensibilityElement.class)) {
                info.addExtensor(ext);
                if (bop != null && extElement instanceof BindingInput) {
                    this.addMessageFromBinding(ext, bop, true);
                }
                if (bop == null || !(extElement instanceof BindingOutput)) continue;
                this.addMessageFromBinding(ext, bop, false);
            }
        }
    }

    protected void addMessageFromBinding(ExtensibilityElement ext, BindingOperationInfo bop, boolean isInput) {
    }
}

