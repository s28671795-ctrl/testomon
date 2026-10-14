/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface Publication
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Publication";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "Publication", "mdrpi");
    public static final String TYPE_LOCAL_NAME = "PublicationType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "PublicationType", "mdrpi");
    public static final String PUBLISHER_ATTRIB_NAME = "publisher";
    public static final String CREATION_INSTANT_ATTRIB_NAME = "creationInstant";
    public static final String PUBLICATION_ID_ATTRIB_NAME = "publicationId";

    public String getPublisher();

    public void setPublisher(String var1);

    public DateTime getCreationInstant();

    public void setCreationInstant(DateTime var1);

    public String getPublicationId();

    public void setPublicationId(String var1);
}

