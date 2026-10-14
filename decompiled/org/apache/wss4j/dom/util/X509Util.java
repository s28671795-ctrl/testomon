/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.util;

import java.io.IOException;
import java.security.KeyException;
import java.security.PublicKey;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class X509Util {
    private static final Logger LOG = LoggerFactory.getLogger(X509Util.class);

    private X509Util() {
    }

    public static boolean isContent(Node encBodyData) {
        String typeStr;
        if (encBodyData != null && (typeStr = ((Element)encBodyData).getAttributeNS(null, "Type")) != null) {
            return typeStr.equals("http://www.w3.org/2001/04/xmlenc#Content");
        }
        return false;
    }

    public static String getEncAlgo(Node encBodyData) throws WSSecurityException {
        Element tmpE = XMLUtils.getDirectChildElement(encBodyData, "EncryptionMethod", "http://www.w3.org/2001/04/xmlenc#");
        String symEncAlgo = null;
        if (tmpE != null && ((symEncAlgo = tmpE.getAttributeNS(null, "Algorithm")) == null || "".equals(symEncAlgo))) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, "noEncAlgo");
        }
        LOG.debug("Sym Enc Algo: {}", (Object)symEncAlgo);
        return symEncAlgo;
    }

    public static byte[] getSecretKey(Element keyInfoElem, String algorithm, CallbackHandler cb, byte[] encryptedKey) throws WSSecurityException {
        String keyName = null;
        Element keyNmElem = XMLUtils.getDirectChildElement(keyInfoElem, "KeyName", "http://www.w3.org/2000/09/xmldsig#");
        if (keyNmElem != null) {
            keyName = XMLUtils.getElementText(keyNmElem);
        }
        if (keyName == null || keyName.length() <= 0) {
            LOG.debug("No Key Name available");
        }
        WSPasswordCallback pwCb = new WSPasswordCallback(keyName, 9);
        pwCb.setEncryptedSecret(encryptedKey);
        pwCb.setAlgorithm(algorithm);
        try {
            cb.handle(new Callback[]{pwCb});
        }
        catch (IOException | UnsupportedCallbackException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "noPassword", new Object[]{keyName});
        }
        byte[] decryptedData = pwCb.getKey();
        if (decryptedData == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noPassword", new Object[]{keyName});
        }
        return decryptedData;
    }

    public static PublicKey parseKeyValue(Element keyInfoElement, XMLSignatureFactory signatureFactory) throws WSSecurityException {
        KeyValue keyValue = null;
        try {
            keyValue = X509Util.getKeyValue(keyInfoElement, signatureFactory);
        }
        catch (MarshalException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)ex);
        }
        if (keyValue != null) {
            try {
                return keyValue.getPublicKey();
            }
            catch (KeyException ex) {
                LOG.error(ex.getMessage(), ex);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, (Exception)ex);
            }
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "unsupportedKeyInfo");
    }

    public static KeyValue getKeyValue(Element keyInfoElement, XMLSignatureFactory signatureFactory) throws MarshalException {
        DOMStructure keyInfoStructure = new DOMStructure(keyInfoElement);
        KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
        KeyInfo keyInfo = keyInfoFactory.unmarshalKeyInfo(keyInfoStructure);
        List<XMLStructure> list = keyInfo.getContent();
        for (int i = 0; i < list.size(); ++i) {
            XMLStructure xmlStructure = list.get(i);
            if (!(xmlStructure instanceof KeyValue)) continue;
            return (KeyValue)xmlStructure;
        }
        return null;
    }
}

