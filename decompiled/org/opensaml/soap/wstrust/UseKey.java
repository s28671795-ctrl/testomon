/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface UseKey
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "UseKey";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKey", "wst");
    public static final String TYPE_LOCAL_NAME = "UseKeyType";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKeyType", "wst");
    public static final String SIG_ATTRIB_NAME = "Sig";

    public XMLObject getUnknownXMLObject();

    public void setUnknownXMLObject(XMLObject var1);

    public String getSig();

    public void setSig(String var1);
}

