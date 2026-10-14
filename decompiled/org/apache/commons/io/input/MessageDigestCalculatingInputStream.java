/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.input.ObservableInputStream;

public class MessageDigestCalculatingInputStream
extends ObservableInputStream {
    private final MessageDigest messageDigest;

    public MessageDigestCalculatingInputStream(InputStream inputStream2, MessageDigest MessageDigest2) {
        super(inputStream2);
        this.messageDigest = MessageDigest2;
        this.add(new MessageDigestMaintainingObserver(MessageDigest2));
    }

    public MessageDigestCalculatingInputStream(InputStream inputStream2, String algorithm) throws NoSuchAlgorithmException {
        this(inputStream2, MessageDigest.getInstance(algorithm));
    }

    public MessageDigestCalculatingInputStream(InputStream inputStream2) throws NoSuchAlgorithmException {
        this(inputStream2, MessageDigest.getInstance("MD5"));
    }

    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }

    public static class MessageDigestMaintainingObserver
    extends ObservableInputStream.Observer {
        private final MessageDigest messageDigest;

        public MessageDigestMaintainingObserver(MessageDigest messageDigest) {
            this.messageDigest = messageDigest;
        }

        @Override
        public void data(int input) throws IOException {
            this.messageDigest.update((byte)input);
        }

        @Override
        public void data(byte[] input, int offset, int length) throws IOException {
            this.messageDigest.update(input, offset, length);
        }
    }
}

