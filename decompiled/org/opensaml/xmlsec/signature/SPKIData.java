/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.SPKISexp;

public interface SPKIData
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SPKIData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData", "ds");
    public static final String TYPE_LOCAL_NAME = "SPKIDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIDataType", "ds");

    @Nonnull
    public List<XMLObject> getXMLObjects();

    @Nonnull
    public List<XMLObject> getXMLObjects(@Nonnull QName var1);

    @Nonnull
    public List<SPKISexp> getSPKISexps();
}

