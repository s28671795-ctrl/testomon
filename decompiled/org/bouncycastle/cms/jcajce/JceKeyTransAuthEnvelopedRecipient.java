/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.InputStreamWithMAC;
import org.bouncycastle.cms.RecipientOperator;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipient;
import org.bouncycastle.jcajce.io.CipherInputStream;
import org.bouncycastle.operator.InputAEADDecryptor;

public class JceKeyTransAuthEnvelopedRecipient
extends JceKeyTransRecipient {
    public JceKeyTransAuthEnvelopedRecipient(PrivateKey privateKey) {
        super(privateKey);
    }

    @Override
    public RecipientOperator getRecipientOperator(AlgorithmIdentifier algorithmIdentifier, final AlgorithmIdentifier algorithmIdentifier2, byte[] byArray) throws CMSException {
        Key key = this.extractSecretKey(algorithmIdentifier, algorithmIdentifier2, byArray);
        final Cipher cipher = this.contentHelper.createContentCipher(key, algorithmIdentifier2);
        return new RecipientOperator(new InputAEADDecryptor(){
            private InputStream inputStream;

            @Override
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier2;
            }

            @Override
            public InputStream getInputStream(InputStream inputStream2) {
                this.inputStream = inputStream2;
                return new CipherInputStream(inputStream2, cipher);
            }

            @Override
            public OutputStream getAADStream() {
                return new AADStream(cipher);
            }

            @Override
            public byte[] getMAC() {
                if (this.inputStream instanceof InputStreamWithMAC) {
                    return ((InputStreamWithMAC)this.inputStream).getMAC();
                }
                return null;
            }
        });
    }

    private static class AADStream
    extends OutputStream {
        private Cipher cipher;
        private byte[] oneByte = new byte[1];

        public AADStream(Cipher cipher) {
            this.cipher = cipher;
        }

        @Override
        public void write(byte[] byArray, int n, int n2) throws IOException {
            this.cipher.updateAAD(byArray, n, n2);
        }

        @Override
        public void write(int n) throws IOException {
            this.oneByte[0] = (byte)n;
            this.cipher.updateAAD(this.oneByte);
        }
    }
}

