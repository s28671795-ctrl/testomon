/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.crypto;

import org.apache.cxf.rt.security.crypto.CryptoUtils;

public class BouncyCastleInstaller {
    public void install() throws Exception {
        CryptoUtils.installBouncyCastleProvider();
    }

    public void uninstall() {
        CryptoUtils.removeBouncyCastleProvider();
    }
}

