/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.c14n;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class CanonicalizerSpi {
    protected boolean reset = false;
    protected boolean secureValidation;

    public byte[] engineCanonicalize(byte[] inputBytes) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
        Document document = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(inputBytes);){
            InputSource in = new InputSource(bais);
            document = XMLUtils.read(in, this.secureValidation);
        }
        return this.engineCanonicalizeSubTree(document);
    }

    public byte[] engineCanonicalizeXPathNodeSet(NodeList xpathNodeSet) throws CanonicalizationException {
        return this.engineCanonicalizeXPathNodeSet(XMLUtils.convertNodelistToSet(xpathNodeSet));
    }

    public byte[] engineCanonicalizeXPathNodeSet(NodeList xpathNodeSet, String inclusiveNamespaces) throws CanonicalizationException {
        return this.engineCanonicalizeXPathNodeSet(XMLUtils.convertNodelistToSet(xpathNodeSet), inclusiveNamespaces);
    }

    public abstract String engineGetURI();

    public abstract boolean engineGetIncludeComments();

    public abstract byte[] engineCanonicalizeXPathNodeSet(Set<Node> var1) throws CanonicalizationException;

    public abstract byte[] engineCanonicalizeXPathNodeSet(Set<Node> var1, String var2) throws CanonicalizationException;

    public abstract byte[] engineCanonicalizeSubTree(Node var1) throws CanonicalizationException;

    public abstract byte[] engineCanonicalizeSubTree(Node var1, String var2) throws CanonicalizationException;

    public abstract byte[] engineCanonicalizeSubTree(Node var1, String var2, boolean var3) throws CanonicalizationException;

    public abstract void setWriter(OutputStream var1);

    public boolean isSecureValidation() {
        return this.secureValidation;
    }

    public void setSecureValidation(boolean secureValidation) {
        this.secureValidation = secureValidation;
    }
}

