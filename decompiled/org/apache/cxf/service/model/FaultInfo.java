/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import javax.xml.namespace.QName;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.OperationInfo;

public class FaultInfo
extends AbstractMessageContainer {
    private QName faultName;

    public FaultInfo(QName fname, QName mname, OperationInfo info) {
        super(info, mname);
        this.faultName = fname;
    }

    public QName getFaultName() {
        return this.faultName;
    }

    public void setFaultName(QName fname) {
        this.faultName = fname;
    }

    @Override
    public int hashCode() {
        return this.faultName == null ? -1 : this.faultName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FaultInfo)) {
            return false;
        }
        FaultInfo oi = (FaultInfo)o;
        return FaultInfo.equals(this.faultName, oi.faultName) && super.equals(o);
    }
}

