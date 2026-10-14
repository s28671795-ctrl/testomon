/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.NamedItem;

public class OperationInfo
extends AbstractPropertiesHolder
implements NamedItem {
    private static final Logger LOG = LogUtils.getL7dLogger(OperationInfo.class);
    InterfaceInfo intf;
    QName opName;
    String inName;
    MessageInfo inputMessage;
    String outName;
    MessageInfo outputMessage;
    Map<QName, FaultInfo> faults;
    OperationInfo unwrappedOperation;
    List<String> parameterOrdering;

    public OperationInfo() {
    }

    OperationInfo(InterfaceInfo it, QName n) {
        this.intf = it;
        this.setName(n);
    }

    OperationInfo(OperationInfo op) {
        this.intf = op.getInterface();
        this.setName(op.getName());
    }

    @Override
    public QName getName() {
        return this.opName;
    }

    public final void setName(QName name) {
        if (name == null) {
            throw new NullPointerException("Operation Name cannot be null.");
        }
        this.opName = name;
    }

    public InterfaceInfo getInterface() {
        return this.intf;
    }

    public MessageInfo createMessage(QName nm, MessageInfo.Type type) {
        return new MessageInfo(this, type, nm);
    }

    public MessageInfo getOutput() {
        return this.outputMessage;
    }

    public String getOutputName() {
        return this.outName;
    }

    public void setOutput(String nm, MessageInfo out) {
        this.outName = nm;
        this.outputMessage = out;
        if (this.unwrappedOperation != null && this.unwrappedOperation.getOutput() != null) {
            this.unwrappedOperation.getOutput().setDelegate(out, false);
        }
    }

    public boolean hasOutput() {
        return this.outputMessage != null;
    }

    public MessageInfo getInput() {
        return this.inputMessage;
    }

    public String getInputName() {
        return this.inName;
    }

    public void setInput(String nm, MessageInfo in) {
        this.inName = nm;
        this.inputMessage = in;
        if (this.unwrappedOperation != null && this.unwrappedOperation.getInput() != null) {
            this.unwrappedOperation.getInput().setDelegate(in, false);
        }
    }

    public boolean hasInput() {
        return this.inputMessage != null;
    }

    public boolean isOneWay() {
        return this.inputMessage != null && this.outputMessage == null;
    }

    public boolean isUnwrappedCapable() {
        return this.unwrappedOperation != null;
    }

    public OperationInfo getUnwrappedOperation() {
        return this.unwrappedOperation;
    }

    public void setUnwrappedOperation(OperationInfo op) {
        this.unwrappedOperation = op;
    }

    public boolean isUnwrapped() {
        return false;
    }

    public FaultInfo addFault(QName name, QName message) {
        if (name == null) {
            throw new NullPointerException(new Message("FAULT.NAME.NOT.NULL", LOG, new Object[0]).toString());
        }
        if (this.faults != null && this.faults.containsKey(name)) {
            throw new IllegalArgumentException(new Message("DUPLICATED.FAULT.NAME", LOG, name).toString());
        }
        FaultInfo fault = new FaultInfo(name, message, this);
        this.addFault(fault);
        return fault;
    }

    public synchronized void addFault(FaultInfo fault) {
        if (this.faults == null) {
            this.faults = new ConcurrentHashMap<QName, FaultInfo>(4, 0.75f, 2);
        }
        this.faults.put(fault.getFaultName(), fault);
    }

    public void removeFault(QName name) {
        if (this.faults != null) {
            this.faults.remove(name);
        }
    }

    public FaultInfo getFault(QName name) {
        if (this.faults != null) {
            return this.faults.get(name);
        }
        return null;
    }

    public boolean hasFaults() {
        return this.faults != null && !this.faults.isEmpty();
    }

    public Collection<FaultInfo> getFaults() {
        if (this.faults == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(this.faults.values());
    }

    public void setParameterOrdering(List<String> o) {
        this.parameterOrdering = o;
    }

    public List<String> getParameterOrdering() {
        return this.parameterOrdering;
    }

    public String toString() {
        return "[OperationInfo: " + this.opName + ']';
    }

    public int hashCode() {
        return this.opName == null ? -1 : this.opName.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OperationInfo)) {
            return false;
        }
        OperationInfo oi = (OperationInfo)o;
        return OperationInfo.equals(this.opName, oi.opName) && OperationInfo.equals(this.inputMessage, oi.inputMessage) && OperationInfo.equals(this.outputMessage, oi.outputMessage) && OperationInfo.equals(this.faults, oi.faults) && OperationInfo.equals(this.intf.getName(), oi.intf.getName());
    }
}

