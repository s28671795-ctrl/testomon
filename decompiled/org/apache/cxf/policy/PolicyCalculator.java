/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.policy;

import javax.xml.namespace.QName;
import org.apache.cxf.message.Message;

public interface PolicyCalculator<T> {
    public Class<T> getDataClass();

    public QName getDataClassName();

    public T intersect(T var1, T var2);

    public boolean isAsserted(Message var1, T var2, T var3);
}

