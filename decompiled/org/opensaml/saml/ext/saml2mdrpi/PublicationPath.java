/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.Publication;

public interface PublicationPath
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PublicationPath";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationPath", "mdrpi");
    public static final String TYPE_LOCAL_NAME = "PublicationPathType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationPathType", "mdrpi");

    public List<Publication> getPublications();
}

