/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.wss4j.dom.SOAP11Constants;
import org.apache.wss4j.dom.SOAP12Constants;

public interface SOAPConstants
extends Serializable {
    public static final SOAP11Constants SOAP11_CONSTANTS = new SOAP11Constants();
    public static final SOAP12Constants SOAP12_CONSTANTS = new SOAP12Constants();

    public String getEnvelopeURI();

    public QName getHeaderQName();

    public QName getBodyQName();

    public QName getRoleAttributeQName();

    public String getNextRoleURI();

    public String getMustUnderstand();
}

