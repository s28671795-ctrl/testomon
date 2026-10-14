/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;

public interface EntitiesDescriptor
extends SignableSAMLObject,
TimeBoundSAMLObject,
CacheableSAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EntitiesDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "EntitiesDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptorType", "md");
    public static final QName ELEMENT_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptor");
    public static final String ID_ATTRIB_NAME = "ID";
    public static final String NAME_ATTRIB_NAME = "Name";

    public String getName();

    public void setName(String var1);

    public String getID();

    public void setID(String var1);

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);

    public List<EntitiesDescriptor> getEntitiesDescriptors();

    public List<EntityDescriptor> getEntityDescriptors();
}

