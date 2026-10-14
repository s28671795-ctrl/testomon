/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.ReferenceType;

public interface ReferenceList
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ReferenceList";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceList", "xenc");

    @Nonnull
    public List<ReferenceType> getReferences();

    @Nonnull
    public List<DataReference> getDataReferences();

    @Nonnull
    public List<KeyReference> getKeyReferences();
}

