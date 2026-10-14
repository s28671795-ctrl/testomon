/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.ext;

import java.security.Key;
import javax.security.auth.callback.Callback;
import org.w3c.dom.Element;

public class WSPasswordCallback
implements Callback {
    public static final int UNKNOWN = 0;
    public static final int DECRYPT = 1;
    public static final int USERNAME_TOKEN = 2;
    public static final int SIGNATURE = 3;
    @Deprecated
    public static final int KEY_NAME = 4;
    @Deprecated
    public static final int USERNAME_TOKEN_UNKNOWN = 5;
    public static final int SECURITY_CONTEXT_TOKEN = 6;
    public static final int CUSTOM_TOKEN = 7;
    @Deprecated
    public static final int ENCRYPTED_KEY_TOKEN = 8;
    public static final int SECRET_KEY = 9;
    public static final int PASSWORD_ENCRYPTOR_PASSWORD = 10;
    private String identifier;
    private String password;
    private byte[] secret;
    private byte[] encryptedSecret;
    private Key key;
    private int usage;
    private String type;
    private Element customToken;
    private String algorithm;
    private Element keyInfoReference;

    public WSPasswordCallback(String id, int usage) {
        this(id, null, null, usage);
    }

    public WSPasswordCallback(String id, String pw, String type, int usage) {
        this.identifier = id;
        this.password = pw;
        this.type = type;
        this.usage = usage;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String ident) {
        this.identifier = ident;
    }

    public void setPassword(String passwd) {
        this.password = passwd;
    }

    public String getPassword() {
        return this.password;
    }

    public void setKey(byte[] secret) {
        this.secret = secret;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public byte[] getKey() {
        return this.secret;
    }

    public Key getKeyObject() {
        return this.key;
    }

    public int getUsage() {
        return this.usage;
    }

    public String getType() {
        return this.type;
    }

    public Element getCustomToken() {
        return this.customToken;
    }

    public void setCustomToken(Element customToken) {
        this.customToken = customToken;
    }

    public byte[] getEncryptedSecret() {
        return this.encryptedSecret;
    }

    public void setEncryptedSecret(byte[] encryptedSecret) {
        this.encryptedSecret = encryptedSecret;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Element getKeyInfoReference() {
        return this.keyInfoReference;
    }

    public void setKeyInfoReference(Element keyInfoReference) {
        this.keyInfoReference = keyInfoReference;
    }
}

