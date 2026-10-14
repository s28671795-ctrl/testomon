/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RenewTarget
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RenewTarget";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewTarget", "wst");
    public static final String TYPE_LOCAL_NAME = "RenewTargetType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewTargetType", "wst");

    public XMLObject getUnknownXMLObject();

    public void setUnknownXMLObject(XMLObject var1);
}

