/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.X509CRL;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.X509SubjectName;

public interface X509Data
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509Data";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data", "ds");
    public static final String TYPE_LOCAL_NAME = "X509DataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509DataType", "ds");

    @Nonnull
    public List<XMLObject> getXMLObjects();

    @Nonnull
    public List<XMLObject> getXMLObjects(@Nonnull QName var1);

    @Nonnull
    public List<X509IssuerSerial> getX509IssuerSerials();

    @Nonnull
    public List<X509SKI> getX509SKIs();

    @Nonnull
    public List<X509SubjectName> getX509SubjectNames();

    @Nonnull
    public List<X509Certificate> getX509Certificates();

    @Nonnull
    public List<X509CRL> getX509CRLs();

    @Nonnull
    public List<X509Digest> getX509Digests();
}

