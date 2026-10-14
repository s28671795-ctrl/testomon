/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.handler;

import java.util.HashMap;
import java.util.Map;
import org.apache.wss4j.common.ConfigurationConstants;

public final class WSHandlerConstants
extends ConfigurationConstants {
    public static final String NO_SECURITY = "NoSecurity";
    public static final String HANDLER_ACTIONS = "handlerActions";
    public static final String USE_ENCODED_PASSWORDS = "useEncodedPasswords";
    public static final String RECV_RESULTS = "RECV_RESULTS";
    public static final String SEND_SIGV = "_sendSignatureValues_";
    public static final String SIG_CONF_DONE = "_sigConfDone_";
    private static Map<String, Integer> keyIdentifier = new HashMap<String, Integer>();

    private WSHandlerConstants() {
    }

    public static Integer getKeyIdentifier(String parameter) {
        return keyIdentifier.get(parameter);
    }

    static {
        keyIdentifier.put("DirectReference", 1);
        keyIdentifier.put("IssuerSerial", 2);
        keyIdentifier.put("X509KeyIdentifier", 3);
        keyIdentifier.put("SKIKeyIdentifier", 4);
        keyIdentifier.put("Thumbprint", 8);
        keyIdentifier.put("EncryptedKeySHA1", 10);
        keyIdentifier.put("KeyValue", 13);
    }
}

