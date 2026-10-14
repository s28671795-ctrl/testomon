/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Validator;

public class CXFRequestData
extends RequestData {
    private static Map<QName, String> validatorKeys = new HashMap<QName, String>();

    @Override
    public Validator getValidator(QName qName) throws WSSecurityException {
        String key = validatorKeys.get(qName);
        if (key != null && this.getMsgContext() != null) {
            Object o = ((SoapMessage)this.getMsgContext()).getContextualProperty(key);
            try {
                if (o instanceof Validator) {
                    return (Validator)o;
                }
                if (o instanceof Class) {
                    return (Validator)((Class)o).newInstance();
                }
                if (o instanceof String) {
                    return (Validator)ClassLoaderUtils.loadClass(o.toString(), CXFRequestData.class).newInstance();
                }
                if (o != null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "Cannot load Validator: " + o);
                }
            }
            catch (RuntimeException t2) {
                throw t2;
            }
            catch (Exception ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
            }
        }
        return super.getValidator(qName);
    }

    static {
        validatorKeys.put(WSConstants.SAML_TOKEN, "ws-security.saml1.validator");
        validatorKeys.put(WSConstants.SAML2_TOKEN, "ws-security.saml2.validator");
        validatorKeys.put(WSConstants.USERNAME_TOKEN, "ws-security.ut.validator");
        validatorKeys.put(WSConstants.SIGNATURE, "ws-security.signature.validator");
        validatorKeys.put(WSConstants.TIMESTAMP, "ws-security.timestamp.validator");
        validatorKeys.put(WSConstants.BINARY_TOKEN, "ws-security.bst.validator");
        validatorKeys.put(WSConstants.SECURITY_CONTEXT_TOKEN_05_02, "ws-security.sct.validator");
        validatorKeys.put(WSConstants.SECURITY_CONTEXT_TOKEN_05_12, "ws-security.sct.validator");
    }
}

