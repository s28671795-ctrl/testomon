/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;

public interface IdBearing {
    public static final String WSU_ID_ATTR_LOCAL_NAME = "Id";
    public static final QName WSU_ID_ATTR_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu");

    public String getWSUId();

    public void setWSUId(String var1);
}

