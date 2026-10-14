/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.Transform;

public interface Transforms
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Transforms";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "Transforms", "xenc");
    public static final String TYPE_LOCAL_NAME = "TransformsType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "TransformsType", "xenc");

    @Nonnull
    public List<Transform> getTransforms();
}

