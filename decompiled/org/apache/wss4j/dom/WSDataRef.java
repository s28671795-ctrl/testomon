/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom;

import java.util.List;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public class WSDataRef {
    private String wsuId;
    private QName name;
    private String xpath;
    private String algorithm;
    private List<String> transformAlgorithms;
    private String digestAlgorithm;
    private byte[] digestValue;
    private boolean content;
    private Element protectedElement;
    private boolean attachment;
    private Element encryptedElement;

    public String getWsuId() {
        return this.wsuId;
    }

    public void setWsuId(String wsuId) {
        this.wsuId = wsuId;
    }

    public QName getName() {
        return this.name;
    }

    public void setName(QName name) {
        this.name = name;
    }

    public void setProtectedElement(Element element) {
        this.protectedElement = element;
        String prefix = element.getPrefix();
        this.name = prefix == null ? new QName(element.getNamespaceURI(), element.getLocalName()) : new QName(element.getNamespaceURI(), element.getLocalName(), prefix);
    }

    public Element getProtectedElement() {
        return this.protectedElement;
    }

    public String getXpath() {
        return this.xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public boolean isContent() {
        return this.content;
    }

    public void setContent(boolean content) {
        this.content = content;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String algo) {
        this.algorithm = algo;
    }

    public String getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public void setTransformAlgorithms(List<String> transformAlgorithms) {
        this.transformAlgorithms = transformAlgorithms;
    }

    public List<String> getTransformAlgorithms() {
        return this.transformAlgorithms;
    }

    public boolean isAttachment() {
        return this.attachment;
    }

    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }

    public byte[] getDigestValue() {
        return this.digestValue;
    }

    public void setDigestValue(byte[] digestValue) {
        this.digestValue = digestValue;
    }

    public Element getEncryptedElement() {
        return this.encryptedElement;
    }

    public void setEncryptedElement(Element encryptedElement) {
        this.encryptedElement = encryptedElement;
    }
}

