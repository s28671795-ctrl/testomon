/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source.mime;

import java.util.Map;
import org.apache.cxf.databinding.source.mime.MimeAttribute;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.extensions.ExtensionSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MimeSerializer
implements ExtensionSerializer {
    public void serialize(XmlSchemaObject schemaObject, Class classOfType, Node domNode) {
        Map<Object, Object> metaInfoMap = schemaObject.getMetaInfoMap();
        MimeAttribute mimeType = (MimeAttribute)metaInfoMap.get(MimeAttribute.MIME_QNAME);
        Element elt = (Element)domNode;
        Attr att1 = elt.getOwnerDocument().createAttributeNS(MimeAttribute.MIME_QNAME.getNamespaceURI(), MimeAttribute.MIME_QNAME.getLocalPart());
        att1.setValue(mimeType.getValue());
        elt.setAttributeNodeNS(att1);
    }
}

