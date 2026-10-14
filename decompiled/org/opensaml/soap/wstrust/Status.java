/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.soap.wstrust.Code;
import org.opensaml.soap.wstrust.Reason;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface Status
extends WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "Status";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Status", "wst");

    public Code getCode();

    public void setCode(Code var1);

    public Reason getReason();

    public void setReason(Reason var1);
}

