/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service;

import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.UnwrappedOperationInfo;

public class ServiceModelVisitor {
    protected ServiceInfo serviceInfo;

    public ServiceModelVisitor(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public void walk() {
        this.begin(this.serviceInfo);
        this.begin(this.serviceInfo.getInterface());
        for (OperationInfo o : this.serviceInfo.getInterface().getOperations()) {
            this.begin(o);
            this.visitOperation(o);
            this.end(o);
        }
        this.end(this.serviceInfo.getInterface());
        for (EndpointInfo endpointInfo : this.serviceInfo.getEndpoints()) {
            this.begin(endpointInfo);
            this.end(endpointInfo);
        }
        for (BindingInfo bindingInfo : this.serviceInfo.getBindings()) {
            this.begin(bindingInfo);
            this.end(bindingInfo);
        }
        this.end(this.serviceInfo);
    }

    private void visitOperation(OperationInfo o) {
        MessageInfo out;
        MessageInfo in = o.getInput();
        if (in != null) {
            this.begin(in);
            for (MessagePartInfo messagePartInfo : in.getMessageParts()) {
                this.begin(messagePartInfo);
                this.end(messagePartInfo);
            }
            this.end(in);
        }
        if ((out = o.getOutput()) != null) {
            this.begin(out);
            for (MessagePartInfo part : out.getMessageParts()) {
                this.begin(part);
                this.end(part);
            }
            this.end(out);
        }
        for (FaultInfo f : o.getFaults()) {
            this.begin(f);
            for (MessagePartInfo part : f.getMessageParts()) {
                this.begin(part);
                this.end(part);
            }
            this.end(f);
        }
        if (o.isUnwrappedCapable()) {
            OperationInfo operationInfo = o.getUnwrappedOperation();
            this.begin(operationInfo);
            this.visitOperation(o.getUnwrappedOperation());
            this.end(operationInfo);
        }
    }

    public void begin(ServiceInfo service2) {
    }

    public void begin(InterfaceInfo intf) {
    }

    public void begin(OperationInfo op) {
    }

    public void begin(UnwrappedOperationInfo op) {
    }

    public void begin(MessageInfo msg) {
    }

    public void begin(MessagePartInfo part) {
    }

    public void begin(FaultInfo fault) {
    }

    public void end(ServiceInfo service2) {
    }

    public void end(InterfaceInfo intf) {
    }

    public void end(OperationInfo op) {
    }

    public void end(UnwrappedOperationInfo op) {
    }

    public void end(MessageInfo msg) {
    }

    public void end(MessagePartInfo part) {
    }

    public void end(FaultInfo fault) {
    }

    public void begin(EndpointInfo endpointInfo) {
    }

    public void end(EndpointInfo endpointInfo) {
    }

    public void begin(BindingInfo bindingInfo) {
    }

    public void end(BindingInfo bindingInfo) {
    }
}

