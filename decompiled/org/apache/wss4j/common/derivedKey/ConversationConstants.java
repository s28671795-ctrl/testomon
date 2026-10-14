/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.derivedKey;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.ext.WSSecurityException;

public final class ConversationConstants {
    public static final int VERSION_05_02 = 1;
    public static final int VERSION_05_12 = 2;
    public static final int DEFAULT_VERSION = 2;
    public static final String WSC_NS_05_02 = "http://schemas.xmlsoap.org/ws/2005/02/sc";
    public static final String WSC_NS_05_12 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512";
    public static final String TOKEN_TYPE_DERIVED_KEY_TOKEN = "/dk";
    public static final String TOKEN_TYPE_SECURITY_CONTEXT_TOKEN = "/sct";
    public static final String WSC_PREFIX = "wsc";
    public static final String SECURITY_CONTEXT_TOKEN_LN = "SecurityContextToken";
    public static final String IDENTIFIER_LN = "Identifier";
    public static final String INSTANCE_LN = "Instance";
    public static final String EXPIRES_LN = "Expires";
    public static final String KEYS_LN = "Keys";
    public static final String SECURITY_TOKEN_REFERENCE_LN = "SecurityTokenReference";
    public static final String DERIVED_KEY_TOKEN_LN = "DerivedKeyToken";
    public static final String PROPERTIES_LN = "Properties";
    public static final String LENGTH_LN = "Length";
    public static final String GENERATION_LN = "Generation";
    public static final String OFFSET_LN = "Offset";
    public static final String LABEL_LN = "Label";
    public static final String NONCE_LN = "Nonce";
    public static final int DIRECT_GENERATED = 1;
    public static final int STS_GENERATED = 2;
    public static final int STSREQUEST_TOKEN = 3;
    public static final int INTEROP_SCENE1 = 4;
    public static final String IDENTIFIER = "SCT_Identifier";
    public static final int DK_SIGN = 1;
    public static final int DK_ENCRYPT = 2;
    public static final String DEFAULT_LABEL = "WS-SecureConversation";
    public static final QName SECURITY_CTX_TOKEN_QNAME_05_02 = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "SecurityContextToken");
    public static final QName SECURITY_CTX_TOKEN_QNAME_05_12 = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "SecurityContextToken");
    public static final QName DERIVED_KEY_TOKEN_QNAME_05_02 = new QName("http://schemas.xmlsoap.org/ws/2005/02/sc", "DerivedKeyToken");
    public static final QName DERIVED_KEY_TOKEN_QNAME_05_12 = new QName("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "DerivedKeyToken");
    public static final String KEY_CONTEXT_MAP = "contextMap";

    private ConversationConstants() {
    }

    public static String getWSCNs(int version) {
        if (1 == version) {
            return WSC_NS_05_02;
        }
        return WSC_NS_05_12;
    }

    public static int getWSTVersion(String ns) {
        if (WSC_NS_05_02.equals(ns)) {
            return 1;
        }
        return 2;
    }

    public static interface DerivationAlgorithm {
        public static final String P_SHA_1 = "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1";
        public static final String P_SHA_1_2005_12 = "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk/p_sha1";

        public byte[] createKey(byte[] var1, byte[] var2, int var3, long var4) throws WSSecurityException;
    }
}

