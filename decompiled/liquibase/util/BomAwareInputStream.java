/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BomAwareInputStream
extends PushbackInputStream {
    private static final byte _0xBF = -65;
    private static final byte _0x00 = 0;
    private static final byte _0xBB = -69;
    private static final byte _0xFF = -1;
    private static final byte _0xFE = -2;
    private static final byte _0xEF = -17;
    private Charset detectedCharset;

    public BomAwareInputStream(InputStream in) throws IOException {
        super(in, 4);
        this.init();
    }

    public Charset getDetectedCharset() {
        return this.detectedCharset;
    }

    protected void init() throws IOException {
        int unread;
        byte[] bom = new byte[4];
        int n = this.read(bom, 0, bom.length);
        if (bom[0] == -17 && bom[1] == -69 && bom[2] == -65) {
            this.detectedCharset = StandardCharsets.UTF_8;
            unread = n - 3;
        } else if (bom[0] == -2 && bom[1] == -1) {
            this.detectedCharset = StandardCharsets.UTF_16BE;
            unread = n - 2;
        } else if (bom[0] == -1 && bom[1] == -2) {
            if (n == 4 && bom[2] == 0 && bom[3] == 0) {
                this.detectedCharset = Charset.forName("UTF-32LE");
                unread = n - 4;
            } else {
                this.detectedCharset = StandardCharsets.UTF_16LE;
                unread = n - 2;
            }
        } else if (bom[0] == 0 && bom[1] == 0 && bom[2] == -2 && bom[3] == -1) {
            this.detectedCharset = Charset.forName("UTF-32BE");
            unread = n - 4;
        } else {
            unread = n;
        }
        if (unread > 0) {
            this.unread(bom, n - unread, unread);
        } else if (unread < -1) {
            this.unread(bom, 0, 0);
        }
    }
}

