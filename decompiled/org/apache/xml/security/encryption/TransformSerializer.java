/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xml.security.encryption.AbstractSerializer;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

public class TransformSerializer
extends AbstractSerializer {
    private TransformerFactory transformerFactory;

    @Override
    public Node deserialize(byte[] source2, Node ctx) throws XMLEncryptionException, IOException {
        byte[] fragment = TransformSerializer.createContext(source2, ctx);
        try (ByteArrayInputStream is = new ByteArrayInputStream(fragment);){
            Node node = this.deserialize(ctx, new StreamSource(is));
            return node;
        }
    }

    @Override
    public Node deserialize(String source2, Node ctx) throws XMLEncryptionException {
        String fragment = TransformSerializer.createContext(source2, ctx);
        return this.deserialize(ctx, new StreamSource(new StringReader(fragment)));
    }

    private Node deserialize(Node ctx, Source source2) throws XMLEncryptionException {
        try {
            Document contextDocument = null;
            contextDocument = 9 == ctx.getNodeType() ? (Document)ctx : ctx.getOwnerDocument();
            if (this.transformerFactory == null) {
                this.transformerFactory = TransformerFactory.newInstance();
                this.transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            }
            Transformer transformer = this.transformerFactory.newTransformer();
            DOMResult res = new DOMResult();
            DocumentFragment placeholder = contextDocument.createDocumentFragment();
            res.setNode(placeholder);
            transformer.transform(source2, res);
            Node dummyChild = placeholder.getFirstChild();
            Node child = dummyChild.getFirstChild();
            if (child != null && child.getNextSibling() == null) {
                return child;
            }
            DocumentFragment docfrag = contextDocument.createDocumentFragment();
            while (child != null) {
                dummyChild.removeChild(child);
                docfrag.appendChild(child);
                child = dummyChild.getFirstChild();
            }
            return docfrag;
        }
        catch (Exception e) {
            throw new XMLEncryptionException(e);
        }
    }
}

