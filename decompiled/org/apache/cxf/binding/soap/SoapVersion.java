/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import javax.xml.namespace.QName;

public interface SoapVersion {
    public String getBindingId();

    public double getVersion();

    public String getNamespace();

    public QName getEnvelope();

    public QName getHeader();

    public QName getBody();

    public QName getFault();

    public QName getReceiver();

    public QName getSender();

    public QName getMustUnderstand();

    public QName getVersionMismatch();

    public QName getDateEncodingUnknown();

    public String getSoapEncodingStyle();

    public String getAttrNameMustUnderstand();

    public String getAttrValueMustUnderstand(boolean var1);

    public String getAttrNameRole();

    public String getNoneRole();

    public String getUltimateReceiverRole();

    public String getNextRole();

    public String getContentType();
}

