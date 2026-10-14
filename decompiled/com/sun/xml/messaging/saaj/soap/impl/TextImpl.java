/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.soap.impl;

import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
import com.sun.xml.messaging.saaj.soap.impl.NamedNodeMapImpl;
import com.sun.xml.messaging.saaj.soap.impl.NodeListImpl;
import java.util.logging.Logger;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.Text;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public abstract class TextImpl<T extends CharacterData>
implements Text,
CharacterData {
    protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.impl", "com.sun.xml.messaging.saaj.soap.impl.LocalStrings");
    private final T domNode;
    private final SOAPDocumentImpl soapDocument;

    protected TextImpl(SOAPDocumentImpl ownerDoc, String text) {
        this.soapDocument = ownerDoc;
        this.domNode = this.createN(ownerDoc, text);
        ownerDoc.register(this);
    }

    protected TextImpl(SOAPDocumentImpl ownerDoc, CharacterData data) {
        this.soapDocument = ownerDoc;
        this.domNode = this.createN(ownerDoc, data);
        ownerDoc.register(this);
    }

    protected abstract T createN(SOAPDocumentImpl var1, CharacterData var2);

    protected abstract T createN(SOAPDocumentImpl var1, String var2);

    public T getDomElement() {
        return this.domNode;
    }

    @Override
    public String getValue() {
        String nodeValue = this.getNodeValue();
        return nodeValue.equals("") ? null : nodeValue;
    }

    @Override
    public void setValue(String text) {
        this.setNodeValue(text);
    }

    @Override
    public void setParentElement(SOAPElement parent) throws SOAPException {
        if (parent == null) {
            log.severe("SAAJ0112.impl.no.null.to.parent.elem");
            throw new SOAPException("Cannot pass NULL to setParentElement");
        }
        ((ElementImpl)parent).addNode(this);
    }

    @Override
    public SOAPElement getParentElement() {
        return (SOAPElement)this.getParentNode();
    }

    @Override
    public void detachNode() {
        Node parent = this.getParentNode();
        if (parent != null) {
            parent.removeChild((Node)this.getDomElement());
        }
    }

    @Override
    public void recycleNode() {
        this.detachNode();
    }

    @Override
    public String getNodeName() {
        return this.domNode.getNodeName();
    }

    @Override
    public String getNodeValue() throws DOMException {
        return this.domNode.getNodeValue();
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        this.domNode.setNodeValue(nodeValue);
    }

    @Override
    public short getNodeType() {
        return this.domNode.getNodeType();
    }

    @Override
    public Node getParentNode() {
        return this.soapDocument.findIfPresent(this.domNode.getParentNode());
    }

    @Override
    public NodeList getChildNodes() {
        return new NodeListImpl(this.soapDocument, this.domNode.getChildNodes());
    }

    @Override
    public Node getFirstChild() {
        return this.soapDocument.findIfPresent(this.domNode.getFirstChild());
    }

    @Override
    public Node getLastChild() {
        return this.soapDocument.findIfPresent(this.domNode.getLastChild());
    }

    @Override
    public Node getPreviousSibling() {
        return this.soapDocument.findIfPresent(this.domNode.getPreviousSibling());
    }

    @Override
    public Node getNextSibling() {
        return this.soapDocument.findIfPresent(this.domNode.getNextSibling());
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new NamedNodeMapImpl(this.domNode.getAttributes(), this.soapDocument);
    }

    @Override
    public Document getOwnerDocument() {
        return this.soapDocument;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        return this.soapDocument.findIfPresent(this.domNode.insertBefore(newChild, refChild));
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        return this.soapDocument.findIfPresent(this.domNode.replaceChild(newChild, oldChild));
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException {
        return this.soapDocument.findIfPresent(this.domNode.removeChild(oldChild));
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        return this.soapDocument.findIfPresent(this.domNode.appendChild(newChild));
    }

    @Override
    public boolean hasChildNodes() {
        return this.domNode.hasChildNodes();
    }

    @Override
    public Node cloneNode(boolean deep) {
        return this.domNode.cloneNode(deep);
    }

    @Override
    public void normalize() {
        this.domNode.normalize();
    }

    @Override
    public boolean isSupported(String feature, String version) {
        return this.domNode.isSupported(feature, version);
    }

    @Override
    public String getNamespaceURI() {
        return this.domNode.getNamespaceURI();
    }

    @Override
    public String getPrefix() {
        return this.domNode.getPrefix();
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
        this.domNode.setPrefix(prefix);
    }

    @Override
    public String getLocalName() {
        return this.domNode.getLocalName();
    }

    @Override
    public boolean hasAttributes() {
        return this.domNode.hasAttributes();
    }

    @Override
    public String getBaseURI() {
        return this.domNode.getBaseURI();
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return this.domNode.compareDocumentPosition(other);
    }

    @Override
    public String getTextContent() throws DOMException {
        return this.domNode.getTextContent();
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {
        this.domNode.setTextContent(textContent);
    }

    @Override
    public boolean isSameNode(Node other) {
        return this.domNode.isSameNode(other);
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        return this.domNode.lookupPrefix(namespaceURI);
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        return this.domNode.isDefaultNamespace(namespaceURI);
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        return this.domNode.lookupNamespaceURI(prefix);
    }

    @Override
    public boolean isEqualNode(Node arg) {
        return this.domNode.isEqualNode(arg);
    }

    @Override
    public Object getFeature(String feature, String version) {
        return this.domNode.getFeature(feature, version);
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        return this.domNode.setUserData(key, data, handler);
    }

    @Override
    public Object getUserData(String key) {
        return this.domNode.getUserData(key);
    }

    @Override
    public String getData() throws DOMException {
        return this.domNode.getData();
    }

    @Override
    public void setData(String data) throws DOMException {
        this.domNode.setData(data);
    }

    @Override
    public int getLength() {
        return this.domNode.getLength();
    }

    @Override
    public String substringData(int offset, int count) throws DOMException {
        return this.domNode.substringData(offset, count);
    }

    @Override
    public void appendData(String arg) throws DOMException {
        this.domNode.appendData(arg);
    }

    @Override
    public void insertData(int offset, String arg) throws DOMException {
        this.domNode.insertData(offset, arg);
    }

    @Override
    public void deleteData(int offset, int count) throws DOMException {
        this.domNode.deleteData(offset, count);
    }

    @Override
    public void replaceData(int offset, int count, String arg) throws DOMException {
        this.domNode.replaceData(offset, count, arg);
    }

    public SOAPDocumentImpl getSoapDocument() {
        return this.soapDocument;
    }
}

