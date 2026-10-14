/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.encryption.Serializer;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class AbstractSerializer
implements Serializer {
    protected Canonicalizer canon;
    protected boolean secureValidation;

    @Override
    public void setCanonicalizer(Canonicalizer canon) {
        this.canon = canon;
    }

    public String serialize(Element element) throws Exception {
        return this.canonSerialize(element);
    }

    @Override
    public byte[] serializeToByteArray(Element element) throws Exception {
        return this.canonSerializeToByteArray(element);
    }

    public String serialize(NodeList content) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            this.canon.setSecureValidation(this.secureValidation);
            this.canon.setWriter(baos);
            this.canon.notReset();
            for (int i = 0; i < content.getLength(); ++i) {
                this.canon.canonicalizeSubtree(content.item(i));
            }
            String ret = baos.toString(StandardCharsets.UTF_8.name());
            baos.reset();
            String string = ret;
            return string;
        }
    }

    @Override
    public byte[] serializeToByteArray(NodeList content) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            this.canon.setSecureValidation(this.secureValidation);
            this.canon.setWriter(baos);
            this.canon.notReset();
            for (int i = 0; i < content.getLength(); ++i) {
                this.canon.canonicalizeSubtree(content.item(i));
            }
            byte[] byArray = baos.toByteArray();
            return byArray;
        }
    }

    public String canonSerialize(Node node) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            this.canon.setSecureValidation(this.secureValidation);
            this.canon.setWriter(baos);
            this.canon.notReset();
            this.canon.canonicalizeSubtree(node);
            String ret = baos.toString(StandardCharsets.UTF_8.name());
            baos.reset();
            String string = ret;
            return string;
        }
    }

    @Override
    public byte[] canonSerializeToByteArray(Node node) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            this.canon.setSecureValidation(this.secureValidation);
            this.canon.setWriter(baos);
            this.canon.notReset();
            this.canon.canonicalizeSubtree(node);
            byte[] byArray = baos.toByteArray();
            return byArray;
        }
    }

    public abstract Node deserialize(String var1, Node var2) throws XMLEncryptionException;

    @Override
    public abstract Node deserialize(byte[] var1, Node var2) throws XMLEncryptionException, IOException;

    protected static byte[] createContext(byte[] source2, Node ctx) throws XMLEncryptionException {
        byte[] byArray;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Throwable throwable = null;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
            HashMap<String, String> storedNamespaces = new HashMap<String, String>();
            for (Node wk = ctx; wk != null; wk = wk.getParentNode()) {
                NamedNodeMap atts = wk.getAttributes();
                if (atts == null) continue;
                for (int i = 0; i < atts.getLength(); ++i) {
                    Node att = atts.item(i);
                    String nodeName = att.getNodeName();
                    if (!"xmlns".equals(nodeName) && !nodeName.startsWith("xmlns:") || storedNamespaces.containsKey(att.getNodeName())) continue;
                    outputStreamWriter.write(" ");
                    outputStreamWriter.write(nodeName);
                    outputStreamWriter.write("=\"");
                    outputStreamWriter.write(att.getNodeValue());
                    outputStreamWriter.write("\"");
                    storedNamespaces.put(nodeName, att.getNodeValue());
                }
            }
            outputStreamWriter.write(">");
            outputStreamWriter.flush();
            byteArrayOutputStream.write(source2);
            outputStreamWriter.write("</dummy>");
            outputStreamWriter.close();
            byArray = byteArrayOutputStream.toByteArray();
        }
        catch (Throwable throwable2) {
            try {
                try {
                    throwable = throwable2;
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    AbstractSerializer.$closeResource(throwable, byteArrayOutputStream);
                    throw throwable3;
                }
            }
            catch (UnsupportedEncodingException e) {
                throw new XMLEncryptionException(e);
            }
            catch (IOException e) {
                throw new XMLEncryptionException(e);
            }
        }
        AbstractSerializer.$closeResource(throwable, byteArrayOutputStream);
        return byArray;
    }

    protected static String createContext(String source2, Node ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
        HashMap<String, String> storedNamespaces = new HashMap<String, String>();
        for (Node wk = ctx; wk != null; wk = wk.getParentNode()) {
            NamedNodeMap atts = wk.getAttributes();
            if (atts == null) continue;
            for (int i = 0; i < atts.getLength(); ++i) {
                Node att = atts.item(i);
                String nodeName = att.getNodeName();
                if (!"xmlns".equals(nodeName) && !nodeName.startsWith("xmlns:") || storedNamespaces.containsKey(att.getNodeName())) continue;
                sb.append(" ");
                sb.append(nodeName);
                sb.append("=\"");
                sb.append(att.getNodeValue());
                sb.append("\"");
                storedNamespaces.put(nodeName, att.getNodeValue());
            }
        }
        sb.append(">");
        sb.append(source2);
        sb.append("</dummy>");
        return sb.toString();
    }

    public boolean isSecureValidation() {
        return this.secureValidation;
    }

    public void setSecureValidation(boolean secureValidation) {
        this.secureValidation = secureValidation;
    }
}

