/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.XPath;

public interface Transform
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Transform";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform", "ds");
    public static final String TYPE_LOCAL_NAME = "TransformType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "TransformType", "ds");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);

    @Nonnull
    public List<XMLObject> getXMLObjects(@Nonnull QName var1);

    @Nonnull
    public List<XPath> getXPaths();

    @Nonnull
    public List<XMLObject> getAllChildren();
}

