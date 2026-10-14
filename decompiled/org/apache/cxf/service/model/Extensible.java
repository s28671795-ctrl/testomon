/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface Extensible {
    public <T> T getExtensor(Class<T> var1);

    public <T> List<T> getExtensors(Class<T> var1);

    public void addExtensor(Object var1);

    public Object getExtensionAttribute(QName var1);

    public Map<QName, Object> getExtensionAttributes();

    public void addExtensionAttribute(QName var1, Object var2);

    public void setExtensionAttributes(Map<QName, Object> var1);
}

