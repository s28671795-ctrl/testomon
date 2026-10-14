/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.transforms.implementations;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class TransformBase64Decode
extends TransformSpi {
    public static final String implementedTransformURI = "http://www.w3.org/2000/09/xmldsig#base64";

    @Override
    protected String engineGetURI() {
        return implementedTransformURI;
    }

    @Override
    protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, Transform transformObject) throws IOException, CanonicalizationException, TransformationException {
        return this.enginePerformTransform(input, null, transformObject);
    }

    @Override
    protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws IOException, CanonicalizationException, TransformationException {
        if (input.isElement()) {
            Node el = input.getSubNode();
            if (input.getSubNode().getNodeType() == 3) {
                el = el.getParentNode();
            }
            StringBuilder sb = new StringBuilder();
            this.traverseElement((Element)el, sb);
            if (os == null) {
                byte[] decodedBytes = XMLUtils.decode(sb.toString());
                XMLSignatureInput output = new XMLSignatureInput(decodedBytes);
                output.setSecureValidation(this.secureValidation);
                return output;
            }
            byte[] bytes = XMLUtils.decode(sb.toString());
            os.write(bytes);
            XMLSignatureInput output = new XMLSignatureInput((byte[])null);
            output.setSecureValidation(this.secureValidation);
            output.setOutputStream(os);
            return output;
        }
        if (input.isOctetStream() || input.isNodeSet()) {
            if (os == null) {
                byte[] base64Bytes = input.getBytes();
                byte[] decodedBytes = XMLUtils.decode(base64Bytes);
                XMLSignatureInput output = new XMLSignatureInput(decodedBytes);
                output.setSecureValidation(this.secureValidation);
                return output;
            }
            if (input.isByteArray() || input.isNodeSet()) {
                byte[] bytes = XMLUtils.decode(input.getBytes());
                os.write(bytes);
            } else {
                byte[] inputBytes = JavaUtils.getBytesFromStream(input.getOctetStreamReal());
                byte[] bytes = XMLUtils.decode(inputBytes);
                os.write(bytes);
            }
            XMLSignatureInput output = new XMLSignatureInput((byte[])null);
            output.setSecureValidation(this.secureValidation);
            output.setOutputStream(os);
            return output;
        }
        try {
            Document doc = XMLUtils.read(input.getOctetStream(), this.secureValidation);
            Element rootNode = doc.getDocumentElement();
            StringBuilder sb = new StringBuilder();
            this.traverseElement(rootNode, sb);
            byte[] decodedBytes = XMLUtils.decode(sb.toString());
            XMLSignatureInput output = new XMLSignatureInput(decodedBytes);
            output.setSecureValidation(this.secureValidation);
            return output;
        }
        catch (ParserConfigurationException e) {
            throw new TransformationException(e, "c14n.Canonicalizer.Exception");
        }
        catch (SAXException e) {
            throw new TransformationException(e, "SAX exception");
        }
    }

    void traverseElement(Element node, StringBuilder sb) {
        block4: for (Node sibling = node.getFirstChild(); sibling != null; sibling = sibling.getNextSibling()) {
            switch (sibling.getNodeType()) {
                case 1: {
                    this.traverseElement((Element)sibling, sb);
                    continue block4;
                }
                case 3: {
                    sb.append(((Text)sibling).getData());
                }
            }
        }
    }
}

