/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.service.model.AbstractDescriptionElement;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.NamedItem;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;

public class BindingInfo
extends AbstractDescriptionElement
implements NamedItem {
    private static final Logger LOG = LogUtils.getL7dLogger(BindingInfo.class);
    QName name;
    ServiceInfo service;
    final String bindingId;
    Map<QName, BindingOperationInfo> operations = new ConcurrentHashMap<QName, BindingOperationInfo>(4, 0.75f, 2);

    public BindingInfo(ServiceInfo service2, String bindingId) {
        this.service = service2;
        this.bindingId = bindingId;
    }

    @Override
    public DescriptionInfo getDescription() {
        if (this.service == null) {
            return null;
        }
        return this.service.getDescription();
    }

    public InterfaceInfo getInterface() {
        return this.service.getInterface();
    }

    public ServiceInfo getService() {
        return this.service;
    }

    public String getBindingId() {
        return this.bindingId;
    }

    public void setName(QName n) {
        this.name = n;
    }

    @Override
    public QName getName() {
        return this.name;
    }

    private static boolean nameEquals(String a, String b, String def) {
        if (a == null) {
            return true;
        }
        return a.isEmpty() ? b.isEmpty() : a.equals(b != null ? b : def);
    }

    public BindingOperationInfo buildOperation(QName opName, String inName, String outName) {
        for (OperationInfo op : this.getInterface().getOperations()) {
            if (!opName.equals(op.getName()) || !BindingInfo.nameEquals(inName, op.getInputName(), op.getName().getLocalPart() + "Request") || !BindingInfo.nameEquals(outName, op.getOutputName(), op.getName().getLocalPart() + "Response")) continue;
            return new BindingOperationInfo(this, op);
        }
        return null;
    }

    public void addOperation(BindingOperationInfo operation) {
        if (operation.getName() == null) {
            throw new NullPointerException(new Message("BINDING.OPERATION.NAME.NOT.NULL", LOG, new Object[0]).toString());
        }
        if (this.operations.containsKey(operation.getName())) {
            throw new IllegalArgumentException(new Message("DUPLICATED.OPERATION.NAME", LOG, operation.getName()).toString());
        }
        this.operations.put(operation.getName(), operation);
    }

    public void removeOperation(BindingOperationInfo operation) {
        if (operation.getName() == null) {
            throw new NullPointerException(new Message("BINDING.OPERATION.NAME.NOT.NULL", LOG, new Object[0]).toString());
        }
        this.operations.remove(operation.getName());
    }

    public BindingOperationInfo getOperation(QName oname) {
        return this.operations.get(oname);
    }

    public Collection<BindingOperationInfo> getOperations() {
        return Collections.unmodifiableCollection(this.operations.values());
    }

    public BindingOperationInfo getOperation(OperationInfo oi) {
        for (BindingOperationInfo b : this.operations.values()) {
            if (b.getOperationInfo() == oi) {
                return b;
            }
            if (!b.isUnwrappedCapable() || b.getUnwrappedOperation().getOperationInfo() != oi) continue;
            return b.getUnwrappedOperation();
        }
        return null;
    }

    public String toString() {
        return "[BindingInfo " + this.getBindingId() + "]";
    }
}

