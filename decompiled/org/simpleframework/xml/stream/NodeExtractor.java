/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.LinkedList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class NodeExtractor
extends LinkedList<Node> {
    public NodeExtractor(Document source2) {
        this.extract(source2);
    }

    private void extract(Document source2) {
        Element node = source2.getDocumentElement();
        if (node != null) {
            this.offer(node);
            this.extract(node);
        }
    }

    private void extract(Node source2) {
        NodeList list = source2.getChildNodes();
        int length = list.getLength();
        for (int i = 0; i < length; ++i) {
            Node node = list.item(i);
            short type = node.getNodeType();
            if (type == 8) continue;
            this.offer(node);
            this.extract(node);
        }
    }
}

