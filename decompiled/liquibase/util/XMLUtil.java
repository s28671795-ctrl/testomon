/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public abstract class XMLUtil {
    public static String getTextContent(Node element) {
        StringBuilder text = new StringBuilder();
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node child = childNodes.item(i);
            if (!(child instanceof Text)) continue;
            text.append(child.getNodeValue());
        }
        return text.toString();
    }
}

