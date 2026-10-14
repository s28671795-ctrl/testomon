/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.TokenElementCallback;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class BinarySecurity {
    public static final QName TOKEN_BST = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
    public static final QName TOKEN_KI = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "KeyIdentifier");
    private static final Logger LOG = LoggerFactory.getLogger(BinarySecurity.class);
    private Element element;
    private byte[] data;
    private boolean storeBytesInAttachment;
    private CallbackHandler attachmentCallbackHandler;

    public CallbackHandler getAttachmentCallbackHandler() {
        return this.attachmentCallbackHandler;
    }

    public void setAttachmentCallbackHandler(CallbackHandler attachmentCallbackHandler) {
        this.attachmentCallbackHandler = attachmentCallbackHandler;
    }

    public BinarySecurity(Element elem, BSPEnforcer bspEnforcer) throws WSSecurityException {
        String valueType;
        this.element = elem;
        QName el = new QName(this.element.getNamespaceURI(), this.element.getLocalName());
        if (!el.equals(TOKEN_BST) && !el.equals(TOKEN_KI)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "unhandledToken", new Object[]{el});
        }
        String encoding = this.getEncodingType();
        if (encoding == null || "".equals(encoding)) {
            bspEnforcer.handleBSPRule(BSPRule.R3029);
        }
        if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encoding)) {
            bspEnforcer.handleBSPRule(BSPRule.R3030);
        }
        if ((valueType = this.getValueType()) == null || "".equals(valueType)) {
            bspEnforcer.handleBSPRule(BSPRule.R3031);
        }
    }

    public BinarySecurity(Document doc) {
        this.element = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:BinarySecurityToken");
        this.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
    }

    public BinarySecurity(CallbackHandler callbackHandler) throws WSSecurityException {
        if (callbackHandler == null) {
            LOG.debug("Trying to create a BinarySecurityToken via a null CallbackHandler");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
        }
        Callback[] callback = new TokenElementCallback[]{new TokenElementCallback()};
        try {
            callbackHandler.handle(callback);
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new IllegalStateException("Exception while creating a token element", e);
        }
        this.element = ((TokenElementCallback)callback[0]).getTokenElement();
        if (this.element == null) {
            LOG.debug("CallbackHandler did not return a token element");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
        }
    }

    public void addWSSENamespace() {
        org.apache.wss4j.common.util.XMLUtils.setNamespace(this.element, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse");
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public String getValueType() {
        return this.element.getAttributeNS(null, "ValueType");
    }

    public void setValueType(String type) {
        if (type != null) {
            this.element.setAttributeNS(null, "ValueType", type);
        }
    }

    public String getEncodingType() {
        return this.element.getAttributeNS(null, "EncodingType");
    }

    public void setEncodingType(String encoding) {
        if (encoding != null) {
            this.element.setAttributeNS(null, "EncodingType", encoding);
        }
    }

    public byte[] getToken() {
        if (this.data != null) {
            return this.data;
        }
        String text = org.apache.wss4j.common.util.XMLUtils.getElementText(this.element);
        if (text == null) {
            return null;
        }
        return XMLUtils.decode(text);
    }

    public void setToken(byte[] data) throws WSSecurityException {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        if (this.storeBytesInAttachment && this.attachmentCallbackHandler != null) {
            Document document = this.element.getOwnerDocument();
            String attachmentId = "_" + UUID.randomUUID().toString();
            this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xop", "http://www.w3.org/2004/08/xop/include");
            Element xopInclude = document.createElementNS("http://www.w3.org/2004/08/xop/include", "xop:Include");
            try {
                xopInclude.setAttributeNS(null, "href", "cid:" + URLEncoder.encode(attachmentId, StandardCharsets.UTF_8.name()));
            }
            catch (UnsupportedEncodingException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
            }
            this.element.appendChild(xopInclude);
            Attachment resultAttachment = new Attachment();
            resultAttachment.setId(attachmentId);
            resultAttachment.setMimeType("application/ciphervalue");
            resultAttachment.setSourceStream(new ByteArrayInputStream(data));
            AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
            attachmentResultCallback.setAttachmentId(attachmentId);
            attachmentResultCallback.setAttachment(resultAttachment);
            try {
                this.attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
            }
        }
        Text node = this.getFirstNode();
        node.setData(XMLUtils.encodeToString(data));
        this.setRawToken(data);
    }

    public void setRawToken(byte[] data) {
        this.data = Arrays.copyOf(data, data.length);
    }

    public void encodeRawToken() {
        if (this.data == null) {
            throw new IllegalArgumentException("data == null");
        }
        Text node = this.getFirstNode();
        node.setData(XMLUtils.encodeToString(this.data));
    }

    private Text getFirstNode() {
        Node node;
        for (node = this.element.getFirstChild(); node != null && 3 != node.getNodeType(); node = node.getNextSibling()) {
        }
        if (node instanceof Text) {
            return (Text)node;
        }
        node = this.element.getFirstChild();
        while (node != null) {
            Node nextNode = node.getNextSibling();
            this.element.removeChild(node);
            node = nextNode;
        }
        Text textNode = this.element.getOwnerDocument().createTextNode("");
        return (Text)this.element.appendChild(textNode);
    }

    public Element getElement() {
        return this.element;
    }

    public String getID() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public void setID(String id) {
        this.element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    public int hashCode() {
        int result = 17;
        byte[] token = this.getToken();
        if (token != null) {
            result = 31 * result + Arrays.hashCode(token);
        }
        result = 31 * result + this.getValueType().hashCode();
        result = 31 * result + this.getEncodingType().hashCode();
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof BinarySecurity)) {
            return false;
        }
        BinarySecurity binarySecurity = (BinarySecurity)object;
        byte[] token = binarySecurity.getToken();
        if (!Arrays.equals(token, this.getToken())) {
            return false;
        }
        String valueType = binarySecurity.getValueType();
        if (!valueType.equals(this.getValueType())) {
            return false;
        }
        String encodingType = binarySecurity.getEncodingType();
        return encodingType.equals(this.getEncodingType());
    }

    public boolean isStoreBytesInAttachment() {
        return this.storeBytesInAttachment;
    }

    public void setStoreBytesInAttachment(boolean storeBytesInAttachment) {
        this.storeBytesInAttachment = storeBytesInAttachment;
    }
}

