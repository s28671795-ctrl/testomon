/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.soap.soap12.RelayBearing;
import org.opensaml.soap.soap12.RoleBearing;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.WSSecurityObject;
import org.opensaml.xmlsec.encryption.EncryptedData;

public interface EncryptedHeader
extends IdBearing,
MustUnderstandBearing,
ActorBearing,
org.opensaml.soap.soap12.MustUnderstandBearing,
RoleBearing,
RelayBearing,
WSSecurityObject {
    public static final String ELEMENT_LOCAL_NAME = "EncryptedHeader";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeader", "wsse11");
    public static final String TYPE_LOCAL_NAME = "EncryptedHeaderType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "EncryptedHeaderType", "wsse11");

    public EncryptedData getEncryptedData();

    public void setEncryptedData(EncryptedData var1);
}

