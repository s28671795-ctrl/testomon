/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.Base64Exception;

public final class Base64Utility {
    private static final Logger LOG;
    private static final char[] BCS;
    private static final char[] BCS_URL_SAFE;
    private static final char PAD = '=';
    private static final int BDTSIZE = 128;
    private static final byte[] BDT;
    private static final int PAD_SIZE0 = 1;
    private static final int PAD_SIZE4 = 2;
    private static final int PAD_SIZE8 = 3;

    private Base64Utility() {
    }

    public static byte[] decodeChunk(char[] id, int o, int l) throws Base64Exception {
        if (id != null && id.length == 0 && l == 0) {
            return new byte[0];
        }
        if (l - o < 4) {
            return null;
        }
        char[] ib = new char[4];
        int ibcount = 0;
        int octetCount = 3 * (l / 4);
        if (id[l - 1] == '=') {
            octetCount -= id[l - 2] == '=' ? 2 : 1;
        }
        byte[] ob = new byte[octetCount];
        int obcount = 0;
        for (int i = o; i < o + l && i < id.length; ++i) {
            if (id[i] != '=' && (id[i] >= BDT.length || BDT[id[i]] == 127)) continue;
            ib[ibcount++] = id[i];
            if (ibcount != ib.length) continue;
            ibcount = 0;
            obcount += Base64Utility.processEncodeme(ib, ob, obcount);
        }
        if (obcount != ob.length) {
            byte[] tmp = new byte[obcount];
            System.arraycopy(ob, 0, tmp, 0, obcount);
            ob = tmp;
        }
        return ob;
    }

    public static byte[] decode(String id) throws Base64Exception {
        return Base64Utility.decode(id, false);
    }

    public static byte[] decode(String id, boolean urlSafe) throws Base64Exception {
        if (urlSafe) {
            id = id.replace('-', '+').replace('_', '/');
            switch (id.length() % 4) {
                case 0: {
                    break;
                }
                case 2: {
                    id = id + "==";
                    break;
                }
                case 3: {
                    id = id + "=";
                    break;
                }
                default: {
                    throw new Base64Exception(new Message("BASE64_RUNTIME_EXCEPTION", LOG, new Object[0]));
                }
            }
        }
        try {
            char[] cd2 = id.toCharArray();
            return Base64Utility.decodeChunk(cd2, 0, cd2.length);
        }
        catch (Exception e) {
            LOG.warning("Invalid base64 encoded string : " + id);
            throw new Base64Exception(new Message("BASE64_RUNTIME_EXCEPTION", LOG, new Object[0]), (Throwable)e);
        }
    }

    public static void decode(char[] id, int o, int l, OutputStream ostream) throws Base64Exception {
        try {
            ostream.write(Base64Utility.decodeChunk(id, o, l));
        }
        catch (Exception e) {
            LOG.warning("Invalid base64 encoded string : " + new String(id));
            throw new Base64Exception(new Message("BASE64_RUNTIME_EXCEPTION", LOG, new Object[0]), (Throwable)e);
        }
    }

    public static void decode(String id, OutputStream ostream) throws Base64Exception {
        try {
            char[] cd2 = id.toCharArray();
            ostream.write(Base64Utility.decodeChunk(cd2, 0, cd2.length));
        }
        catch (IOException ioe) {
            throw new Base64Exception(new Message("BASE64_DECODE_IOEXCEPTION", LOG, new Object[0]), (Throwable)ioe);
        }
        catch (Exception e) {
            LOG.warning("Invalid base64 encoded string : " + id);
            throw new Base64Exception(new Message("BASE64_RUNTIME_EXCEPTION", LOG, new Object[0]), (Throwable)e);
        }
    }

    public static String encode(byte[] id) {
        return Base64Utility.encode(id, false);
    }

    public static String encode(byte[] id, boolean urlSafe) {
        char[] cd2 = Base64Utility.encodeChunk(id, 0, id.length);
        return new String(cd2, 0, cd2.length);
    }

    public static char[] encodeChunk(byte[] id, int o, int l) {
        return Base64Utility.encodeChunk(id, o, l, false);
    }

    public static char[] encodeChunk(byte[] id, int o, int l, boolean urlSafe) {
        int i;
        int rest;
        char[] base64Table;
        char[] out;
        if (id != null && id.length == 0 && l == 0) {
            return new char[0];
        }
        if (l <= 0) {
            return null;
        }
        if (l % 3 == 0) {
            out = new char[l / 3 * 4];
        } else {
            int finalLen = !urlSafe ? 4 : (l % 3 == 1 ? 2 : 3);
            out = new char[l / 3 * 4 + finalLen];
        }
        int rindex = o;
        int windex = 0;
        char[] cArray = base64Table = urlSafe ? BCS_URL_SAFE : BCS;
        for (rest = l; rest >= 3; rest -= 3) {
            i = ((id[rindex] & 0xFF) << 16) + ((id[rindex + 1] & 0xFF) << 8) + (id[rindex + 2] & 0xFF);
            out[windex++] = base64Table[i >> 18];
            out[windex++] = base64Table[i >> 12 & 0x3F];
            out[windex++] = base64Table[i >> 6 & 0x3F];
            out[windex++] = base64Table[i & 0x3F];
            rindex += 3;
        }
        if (rest == 1) {
            i = id[rindex] & 0xFF;
            out[windex++] = base64Table[i >> 2];
            out[windex++] = base64Table[i << 4 & 0x3F];
            if (!urlSafe) {
                out[windex++] = 61;
                out[windex++] = 61;
            }
        } else if (rest == 2) {
            i = ((id[rindex] & 0xFF) << 8) + (id[rindex + 1] & 0xFF);
            out[windex++] = base64Table[i >> 10];
            out[windex++] = base64Table[i >> 4 & 0x3F];
            out[windex++] = base64Table[i << 2 & 0x3F];
            if (!urlSafe) {
                out[windex++] = 61;
            }
        }
        return out;
    }

    public static void encodeAndStream(byte[] id, int o, int l, OutputStream os) throws IOException {
        Base64Utility.encodeAndStream(id, o, l, false, os);
    }

    public static void encodeAndStream(byte[] id, int o, int l, boolean urlSafe, OutputStream os) throws IOException {
        int i;
        int rest;
        if (l <= 0) {
            return;
        }
        int rindex = o;
        char[] base64Table = urlSafe ? BCS_URL_SAFE : BCS;
        char[] chunk = new char[4];
        for (rest = l; rest >= 3; rest -= 3) {
            i = ((id[rindex] & 0xFF) << 16) + ((id[rindex + 1] & 0xFF) << 8) + (id[rindex + 2] & 0xFF);
            chunk[0] = base64Table[i >> 18];
            chunk[1] = base64Table[i >> 12 & 0x3F];
            chunk[2] = base64Table[i >> 6 & 0x3F];
            chunk[3] = base64Table[i & 0x3F];
            Base64Utility.writeCharArrayToStream(chunk, 4, os);
            rindex += 3;
        }
        if (rest == 0) {
            return;
        }
        if (rest == 1) {
            i = id[rindex] & 0xFF;
            chunk[0] = base64Table[i >> 2];
            chunk[1] = base64Table[i << 4 & 0x3F];
            if (!urlSafe) {
                chunk[2] = 61;
                chunk[3] = 61;
            }
        } else if (rest == 2) {
            i = ((id[rindex] & 0xFF) << 8) + (id[rindex + 1] & 0xFF);
            chunk[0] = base64Table[i >> 10];
            chunk[1] = base64Table[i >> 4 & 0x3F];
            chunk[2] = base64Table[i << 2 & 0x3F];
            if (!urlSafe) {
                chunk[3] = 61;
            }
        }
        int finalLenToWrite = !urlSafe ? 4 : (rest == 1 ? 2 : 3);
        Base64Utility.writeCharArrayToStream(chunk, finalLenToWrite, os);
    }

    private static void writeCharArrayToStream(char[] chunk, int len, OutputStream os) throws IOException {
        byte[] bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chunk, 0, len)).array();
        os.write(bytes);
    }

    public static void encodeChunk(byte[] id, int o, int l, OutputStream ostream) throws Base64Exception {
        try {
            ostream.write(new String(Base64Utility.encodeChunk(id, o, l)).getBytes());
        }
        catch (IOException e) {
            throw new Base64Exception(new Message("BASE64_ENCODE_IOEXCEPTION", LOG, new Object[0]), (Throwable)e);
        }
    }

    public static void encode(byte[] id, int o, int l, Writer writer) throws Base64Exception {
        try {
            writer.write(Base64Utility.encodeChunk(id, o, l));
        }
        catch (IOException e) {
            throw new Base64Exception(new Message("BASE64_ENCODE_WRITER_IOEXCEPTION", LOG, new Object[0]), (Throwable)e);
        }
    }

    private static int processEncodeme(char[] ib, byte[] ob, int p) throws Base64Exception {
        int spad = 3;
        if (ib[3] == '=') {
            spad = 2;
        }
        if (ib[2] == '=') {
            spad = 1;
        }
        byte b0 = BDT[ib[0]];
        byte b1 = BDT[ib[1]];
        byte b2 = BDT[ib[2]];
        byte b3 = BDT[ib[3]];
        switch (spad) {
            case 1: {
                ob[p] = (byte)(b0 << 2 & 0xFC | b1 >> 4 & 3);
                return 1;
            }
            case 2: {
                ob[p++] = (byte)(b0 << 2 & 0xFC | b1 >> 4 & 3);
                ob[p] = (byte)(b1 << 4 & 0xF0 | b2 >> 2 & 0xF);
                return 2;
            }
            case 3: {
                ob[p++] = (byte)(b0 << 2 & 0xFC | b1 >> 4 & 3);
                ob[p++] = (byte)(b1 << 4 & 0xF0 | b2 >> 2 & 0xF);
                ob[p] = (byte)(b2 << 6 & 0xC0 | b3 & 0x3F);
                return 3;
            }
        }
        throw new IllegalStateException();
    }

    public static boolean isValidBase64(int ch) {
        return ch == 61 || BDT[ch] != 127;
    }

    static {
        int i;
        LOG = LogUtils.getL7dLogger(Base64Utility.class);
        BCS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        BCS_URL_SAFE = Arrays.copyOf(BCS, BCS.length);
        BDT = new byte[128];
        for (i = 0; i < 128; ++i) {
            Base64Utility.BDT[i] = 127;
        }
        for (i = 0; i < BCS.length; ++i) {
            Base64Utility.BDT[Base64Utility.BCS[i]] = (byte)i;
        }
        Base64Utility.BCS_URL_SAFE[62] = 45;
        Base64Utility.BCS_URL_SAFE[63] = 95;
    }
}

