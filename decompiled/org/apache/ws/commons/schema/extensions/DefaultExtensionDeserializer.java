/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.extensions;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.extensions.ExtensionDeserializer;
import org.w3c.dom.Node;

public class DefaultExtensionDeserializer
implements ExtensionDeserializer {
    @Override
    public void deserialize(XmlSchemaObject schemaObject, QName name, Node node) {
        Map<Object, Object> metaInfoMap = schemaObject.getMetaInfoMap();
        if (metaInfoMap == null) {
            metaInfoMap = new HashMap<Object, Object>();
        }
        if (node.getNodeType() == 2) {
            Map<QName, Node> attribMap;
            if (metaInfoMap.containsKey("EXTERNAL_ATTRIBUTES")) {
                attribMap = (Map)metaInfoMap.get("EXTERNAL_ATTRIBUTES");
            } else {
                attribMap = new HashMap();
                metaInfoMap.put("EXTERNAL_ATTRIBUTES", attribMap);
            }
            attribMap.put(name, node);
        } else if (node.getNodeType() == 1) {
            Map<QName, Node> elementMap;
            if (metaInfoMap.containsKey("EXTERNAL_ELEMENTS")) {
                elementMap = (Map)metaInfoMap.get("EXTERNAL_ELEMENTS");
            } else {
                elementMap = new HashMap();
                metaInfoMap.put("EXTERNAL_ELEMENTS", elementMap);
            }
            elementMap.put(name, node);
        }
        if (!metaInfoMap.isEmpty()) {
            Map<Object, Object> metaInfoMapFromSchemaElement = schemaObject.getMetaInfoMap();
            if (metaInfoMapFromSchemaElement == null) {
                schemaObject.setMetaInfoMap(metaInfoMap);
            } else {
                metaInfoMapFromSchemaElement.putAll(metaInfoMap);
            }
        }
    }
}

