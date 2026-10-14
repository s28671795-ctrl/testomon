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
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.NamedItem;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;

public class InterfaceInfo
extends AbstractDescriptionElement
implements NamedItem {
    private static final Logger LOG = LogUtils.getL7dLogger(InterfaceInfo.class);
    QName name;
    ServiceInfo service;
    Map<QName, OperationInfo> operations = new ConcurrentHashMap<QName, OperationInfo>(4, 0.75f, 2);

    public InterfaceInfo(ServiceInfo info, QName q) {
        this.name = q;
        this.service = info;
        info.setInterface(this);
    }

    @Override
    public DescriptionInfo getDescription() {
        if (this.service == null) {
            return null;
        }
        return this.service.getDescription();
    }

    public ServiceInfo getService() {
        return this.service;
    }

    public void setName(QName n) {
        this.name = n;
    }

    @Override
    public QName getName() {
        return this.name;
    }

    public OperationInfo addOperation(QName oname) {
        if (oname == null) {
            throw new NullPointerException(new Message("OPERATION.NAME.NOT.NULL", LOG, new Object[0]).toString());
        }
        if (this.operations.containsKey(oname)) {
            throw new IllegalArgumentException(new Message("DUPLICATED.OPERATION.NAME", LOG, oname).toString());
        }
        OperationInfo operation = new OperationInfo(this, oname);
        this.addOperation(operation);
        return operation;
    }

    void addOperation(OperationInfo operation) {
        this.operations.put(operation.getName(), operation);
    }

    public void removeOperation(OperationInfo operation) {
        this.operations.remove(operation.getName());
    }

    public OperationInfo getOperation(QName oname) {
        return this.operations.get(oname);
    }

    public Collection<OperationInfo> getOperations() {
        return Collections.unmodifiableCollection(this.operations.values());
    }
}

