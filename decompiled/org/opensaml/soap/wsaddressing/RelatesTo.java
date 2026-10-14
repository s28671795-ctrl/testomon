/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface RelatesTo
extends XSURI,
AttributeExtensibleXMLObject,
WSAddressingObject {
    public static final String ELEMENT_LOCAL_NAME = "RelatesTo";
    public static final QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "RelatesTo", "wsa");
    public static final String TYPE_LOCAL_NAME = "RelatesToType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "RelatesToType", "wsa");
    public static final String RELATIONSHIP_TYPE_ATTRIB_NAME = "RelationshipType";
    public static final String RELATIONSHIP_TYPE_REPLY = "http://www.w3.org/2005/08/addressing/reply";

    public String getRelationshipType();

    public void setRelationshipType(String var1);
}

