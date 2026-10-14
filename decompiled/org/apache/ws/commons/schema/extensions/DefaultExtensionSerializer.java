/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.extensions;

import java.util.Iterator;
import java.util.Map;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.extensions.ExtensionSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DefaultExtensionSerializer
implements ExtensionSerializer {
    @Override
    public void serialize(XmlSchemaObject schemaObject, Class<?> classOfType, Node node) {
        Iterator it;
        Map<Object, Object> metaInfoMap = schemaObject.getMetaInfoMap();
        Document parentDoc = node.getOwnerDocument();
        if (metaInfoMap.containsKey("EXTERNAL_ATTRIBUTES")) {
            Map attribMap = (Map)metaInfoMap.get("EXTERNAL_ATTRIBUTES");
            it = attribMap.values().iterator();
            while (it.hasNext()) {
                if (node.getNodeType() != 1) continue;
                ((Element)node).setAttributeNodeNS((Attr)parentDoc.importNode((Node)it.next(), true));
            }
        }
        if (metaInfoMap.containsKey("EXTERNAL_ELEMENTS")) {
            Map elementMap = (Map)metaInfoMap.get("EXTERNAL_ELEMENTS");
            it = elementMap.values().iterator();
            while (it.hasNext()) {
                node.appendChild(parentDoc.importNode((Node)it.next(), true));
            }
        }
    }
}

