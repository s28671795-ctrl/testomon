/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.encryption.AbstractSerializer;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocumentSerializer
extends AbstractSerializer {
    @Override
    public Node deserialize(byte[] source2, Node ctx) throws XMLEncryptionException, IOException {
        byte[] fragment = DocumentSerializer.createContext(source2, ctx);
        try (ByteArrayInputStream is = new ByteArrayInputStream(fragment);){
            Node node = this.deserialize(ctx, new InputSource(is));
            return node;
        }
    }

    @Override
    public Node deserialize(String source2, Node ctx) throws XMLEncryptionException {
        String fragment = DocumentSerializer.createContext(source2, ctx);
        return this.deserialize(ctx, new InputSource(new StringReader(fragment)));
    }

    private Node deserialize(Node ctx, InputSource inputSource) throws XMLEncryptionException {
        try {
            Document d = XMLUtils.read(inputSource, this.secureValidation);
            Document contextDocument = null;
            contextDocument = 9 == ctx.getNodeType() ? (Document)ctx : ctx.getOwnerDocument();
            Element fragElt = (Element)contextDocument.importNode(d.getDocumentElement(), true);
            DocumentFragment result = contextDocument.createDocumentFragment();
            Node child = fragElt.getFirstChild();
            while (child != null) {
                fragElt.removeChild(child);
                result.appendChild(child);
                child = fragElt.getFirstChild();
            }
            return result;
        }
        catch (SAXException se) {
            throw new XMLEncryptionException(se);
        }
        catch (ParserConfigurationException pce) {
            throw new XMLEncryptionException(pce);
        }
        catch (IOException ioe) {
            throw new XMLEncryptionException(ioe);
        }
    }
}

