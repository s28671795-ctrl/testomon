/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.FaultInfo;

public class BindingFaultInfo
extends AbstractPropertiesHolder {
    FaultInfo fault;
    BindingOperationInfo opinfo;

    public BindingFaultInfo(FaultInfo f, BindingOperationInfo info) {
        this.fault = f;
        this.opinfo = info;
    }

    public FaultInfo getFaultInfo() {
        return this.fault;
    }

    public BindingOperationInfo getBindingOperation() {
        return this.opinfo;
    }
}

