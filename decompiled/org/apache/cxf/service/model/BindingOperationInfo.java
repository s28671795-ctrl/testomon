/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.OperationInfo;

public class BindingOperationInfo
extends AbstractPropertiesHolder {
    protected OperationInfo opInfo;
    BindingInfo bindingInfo;
    BindingMessageInfo inputMessage;
    BindingMessageInfo outputMessage;
    Map<QName, BindingFaultInfo> faults;
    BindingOperationInfo opHolder;

    public BindingOperationInfo() {
    }

    public BindingOperationInfo(BindingInfo bi, OperationInfo opinfo) {
        this.bindingInfo = bi;
        this.opInfo = opinfo;
        this.inputMessage = this.opInfo.getInput() != null ? new BindingMessageInfo(this.opInfo.getInput(), this) : null;
        this.outputMessage = this.opInfo.getOutput() != null ? new BindingMessageInfo(this.opInfo.getOutput(), this) : null;
        Collection<FaultInfo> of = opinfo.getFaults();
        if (of != null && !of.isEmpty()) {
            this.faults = new ConcurrentHashMap<QName, BindingFaultInfo>(of.size(), 0.75f, 2);
            for (FaultInfo fault : of) {
                this.faults.put(fault.getFaultName(), new BindingFaultInfo(fault, this));
            }
        }
        if (opinfo.isUnwrappedCapable()) {
            this.opHolder = new BindingOperationInfo(bi, opinfo.getUnwrappedOperation(), this);
        }
    }

    BindingOperationInfo(BindingInfo bi, OperationInfo opinfo, BindingOperationInfo wrapped) {
        this(bi, opinfo);
        this.opHolder = wrapped;
    }

    public void updateUnwrappedOperation() {
        if (this.opInfo.isUnwrappedCapable() && this.opHolder == null) {
            this.opHolder = new BindingOperationInfo(this.bindingInfo, this.opInfo.getUnwrappedOperation(), this);
        }
    }

    public BindingInfo getBinding() {
        return this.bindingInfo;
    }

    public QName getName() {
        return this.opInfo.getName();
    }

    public OperationInfo getOperationInfo() {
        return this.opInfo;
    }

    public BindingMessageInfo getInput() {
        return this.inputMessage;
    }

    public BindingMessageInfo getOutput() {
        return this.outputMessage;
    }

    public BindingFaultInfo getFault(QName name) {
        if (this.faults != null) {
            return this.faults.get(name);
        }
        return null;
    }

    public Collection<BindingFaultInfo> getFaults() {
        if (this.faults == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(this.faults.values());
    }

    public boolean isUnwrappedCapable() {
        return this.opInfo.isUnwrappedCapable();
    }

    public BindingOperationInfo getUnwrappedOperation() {
        return this.opHolder;
    }

    public void setUnwrappedOperation(BindingOperationInfo op) {
        this.opHolder = op;
    }

    public boolean isUnwrapped() {
        return this.opInfo.isUnwrapped();
    }

    public BindingOperationInfo getWrappedOperation() {
        return this.opHolder;
    }

    public String toString() {
        return "[BindingOperationInfo: " + (this.getName() == null ? "" : this.getName()) + ']';
    }

    public int hashCode() {
        return this.opInfo.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BindingOperationInfo)) {
            return false;
        }
        return BindingOperationInfo.equals(this.opInfo, ((BindingOperationInfo)o).opInfo);
    }
}

