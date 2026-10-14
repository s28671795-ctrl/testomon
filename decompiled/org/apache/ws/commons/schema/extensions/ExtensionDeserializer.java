/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.extensions;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.w3c.dom.Node;

public interface ExtensionDeserializer {
    public void deserialize(XmlSchemaObject var1, QName var2, Node var3);
}

