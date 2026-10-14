/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface AlgorithmIdentifierType
extends XMLObject {
    public static final String TYPE_LOCAL_NAME = "AlgorithmIdentifierType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "AlgorithmIdentifierType", "xenc11");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);

    @Nullable
    public XMLObject getParameters();

    public void setParameters(@Nullable XMLObject var1);
}

