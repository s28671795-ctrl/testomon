/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.PortInfo;

public class PortInfoImpl
implements PortInfo {
    private String bindingID;
    private QName portName;
    private QName serviceName;
    private String address;

    public PortInfoImpl(String bindingID, QName portName, QName serviceName) {
        this.bindingID = bindingID;
        this.portName = portName;
        this.serviceName = serviceName;
    }

    @Override
    public String getBindingID() {
        return this.bindingID;
    }

    @Override
    public QName getPortName() {
        return this.portName;
    }

    @Override
    public QName getServiceName() {
        return this.serviceName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

