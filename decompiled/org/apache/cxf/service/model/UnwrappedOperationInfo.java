/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.OperationInfo;

public class UnwrappedOperationInfo
extends OperationInfo {
    OperationInfo wrappedOp;

    public UnwrappedOperationInfo(OperationInfo op) {
        super(op);
        this.wrappedOp = op;
        this.setDelegate(this.wrappedOp, true);
    }

    public OperationInfo getWrappedOperation() {
        return this.wrappedOp;
    }

    @Override
    public boolean isUnwrapped() {
        return true;
    }

    @Override
    public FaultInfo addFault(QName name, QName message) {
        return this.wrappedOp.addFault(name, message);
    }

    @Override
    public FaultInfo getFault(QName name) {
        return this.wrappedOp.getFault(name);
    }

    @Override
    public Collection<FaultInfo> getFaults() {
        return this.wrappedOp.getFaults();
    }

    @Override
    public void setOutput(String nm, MessageInfo out) {
        super.setOutput(nm, out);
        out.setDelegate(this.wrappedOp.getOutput(), false);
    }

    @Override
    public void setInput(String nm, MessageInfo in) {
        super.setInput(nm, in);
        in.setDelegate(this.wrappedOp.getInput(), false);
    }
}

