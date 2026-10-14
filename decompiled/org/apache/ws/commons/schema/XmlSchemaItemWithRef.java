/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaItemWithRefBase;
import org.apache.ws.commons.schema.utils.XmlSchemaNamed;
import org.apache.ws.commons.schema.utils.XmlSchemaRef;

public interface XmlSchemaItemWithRef<T extends XmlSchemaNamed>
extends XmlSchemaItemWithRefBase {
    public XmlSchemaRef<T> getRef();
}

