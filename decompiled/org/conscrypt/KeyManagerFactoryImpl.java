/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.ManagerFactoryParameters;
import org.conscrypt.EmptyArray;
import org.conscrypt.KeyManagerImpl;
import org.conscrypt.io.IoUtils;

public class KeyManagerFactoryImpl
extends KeyManagerFactorySpi {
    private KeyStore keyStore;
    private char[] pwd;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void engineInit(KeyStore ks, char[] password) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (ks != null) {
            this.keyStore = ks;
            if (password != null) {
                this.pwd = (char[])password.clone();
                return;
            }
            this.pwd = EmptyArray.CHAR;
            return;
        }
        this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        String keyStoreName = System.getProperty("javax.net.ssl.keyStore");
        String keyStorePwd = null;
        if (keyStoreName == null || keyStoreName.equalsIgnoreCase("NONE") || keyStoreName.isEmpty()) {
            try {
                this.keyStore.load(null, null);
                return;
            }
            catch (IOException e) {
                throw new KeyStoreException(e);
            }
            catch (CertificateException e) {
                throw new KeyStoreException(e);
            }
        }
        keyStorePwd = System.getProperty("javax.net.ssl.keyStorePassword");
        this.pwd = keyStorePwd == null ? EmptyArray.CHAR : keyStorePwd.toCharArray();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(keyStoreName));
            this.keyStore.load(fis, this.pwd);
        }
        catch (FileNotFoundException e) {
            try {
                throw new KeyStoreException(e);
                catch (IOException e2) {
                    throw new KeyStoreException(e2);
                }
                catch (CertificateException e3) {
                    throw new KeyStoreException(e3);
                }
            }
            catch (Throwable throwable) {
                IoUtils.closeQuietly(fis);
                throw throwable;
            }
        }
        IoUtils.closeQuietly(fis);
    }

    @Override
    protected void engineInit(ManagerFactoryParameters spec) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }

    @Override
    protected KeyManager[] engineGetKeyManagers() {
        if (this.keyStore == null) {
            throw new IllegalStateException("KeyManagerFactory is not initialized");
        }
        return new KeyManager[]{new KeyManagerImpl(this.keyStore, this.pwd)};
    }
}

