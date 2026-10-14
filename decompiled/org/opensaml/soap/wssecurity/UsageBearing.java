/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import java.util.List;
import javax.xml.namespace.QName;

public interface UsageBearing {
    public static final String WSSE_USAGE_ATTR_LOCAL_NAME = "Usage";
    public static final QName WSSE_USAGE_ATTR_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Usage", "wsse");

    public List<String> getWSSEUsages();

    public void setWSSEUsages(List<String> var1);
}

