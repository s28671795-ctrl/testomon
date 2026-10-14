/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.utils.XmlSchemaRefBase;

public interface XmlSchemaItemWithRefBase {
    public boolean isRef();

    public QName getTargetQName();

    public XmlSchemaRefBase getRefBase();
}

