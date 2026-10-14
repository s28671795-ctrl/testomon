/*
 * Decompiled with CFR 0.152.
 */
package org.kxml2.wap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Vector;
import org.xmlpull.v1.XmlSerializer;

public class WbxmlSerializer
implements XmlSerializer {
    Hashtable stringTable = new Hashtable();
    OutputStream out;
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    ByteArrayOutputStream stringTableBuf = new ByteArrayOutputStream();
    String pending;
    int depth;
    String name;
    String namespace;
    Vector attributes = new Vector();
    Hashtable attrStartTable = new Hashtable();
    Hashtable attrValueTable = new Hashtable();
    Hashtable tagTable = new Hashtable();
    private int attrPage;
    private int tagPage;
    private String encoding;

    public XmlSerializer attribute(String string, String string2, String string3) {
        this.attributes.addElement(string2);
        this.attributes.addElement(string3);
        return this;
    }

    public void cdsect(String string) throws IOException {
        this.text(string);
    }

    public void comment(String string) {
    }

    public void docdecl(String string) {
        throw new RuntimeException("Cannot write docdecl for WBXML");
    }

    public void entityRef(String string) {
        throw new RuntimeException("EntityReference not supported for WBXML");
    }

    public int getDepth() {
        return this.depth;
    }

    public boolean getFeature(String string) {
        return false;
    }

    public String getNamespace() {
        throw new RuntimeException("NYI");
    }

    public String getName() {
        throw new RuntimeException("NYI");
    }

    public String getPrefix(String string, boolean bl) {
        throw new RuntimeException("NYI");
    }

    public Object getProperty(String string) {
        return null;
    }

    public void ignorableWhitespace(String string) {
    }

    public void endDocument() throws IOException {
        WbxmlSerializer.writeInt(this.out, this.stringTableBuf.size());
        this.out.write(this.stringTableBuf.toByteArray());
        this.out.write(this.buf.toByteArray());
        this.out.flush();
    }

    public void flush() {
    }

    public void checkPending(boolean bl) throws IOException {
        if (this.pending == null) {
            return;
        }
        int n = this.attributes.size();
        int[] nArray = (int[])this.tagTable.get(this.pending);
        if (nArray == null) {
            this.buf.write(n == 0 ? (bl ? 4 : 68) : (bl ? 132 : 196));
            this.writeStrT(this.pending, false);
        } else {
            if (nArray[0] != this.tagPage) {
                this.tagPage = nArray[0];
                this.buf.write(0);
                this.buf.write(this.tagPage);
            }
            this.buf.write(n == 0 ? (bl ? nArray[1] : nArray[1] | 0x40) : (bl ? nArray[1] | 0x80 : nArray[1] | 0xC0));
        }
        for (int i = 0; i < n; ++i) {
            nArray = (int[])this.attrStartTable.get(this.attributes.elementAt(i));
            if (nArray == null) {
                this.buf.write(4);
                this.writeStrT((String)this.attributes.elementAt(i), false);
            } else {
                if (nArray[0] != this.attrPage) {
                    this.attrPage = nArray[0];
                    this.buf.write(0);
                    this.buf.write(this.attrPage);
                }
                this.buf.write(nArray[1]);
            }
            nArray = (int[])this.attrValueTable.get(this.attributes.elementAt(++i));
            if (nArray == null) {
                this.writeStr((String)this.attributes.elementAt(i));
                continue;
            }
            if (nArray[0] != this.attrPage) {
                this.attrPage = nArray[0];
                this.buf.write(0);
                this.buf.write(this.attrPage);
            }
            this.buf.write(nArray[1]);
        }
        if (n > 0) {
            this.buf.write(1);
        }
        this.pending = null;
        this.attributes.removeAllElements();
    }

    public void processingInstruction(String string) {
        throw new RuntimeException("PI NYI");
    }

    public void setFeature(String string, boolean bl) {
        throw new IllegalArgumentException("unknown feature " + string);
    }

    public void setOutput(Writer writer) {
        throw new RuntimeException("Wbxml requires an OutputStream!");
    }

    public void setOutput(OutputStream outputStream2, String string) throws IOException {
        this.encoding = string == null ? "UTF-8" : string;
        this.out = outputStream2;
        this.buf = new ByteArrayOutputStream();
        this.stringTableBuf = new ByteArrayOutputStream();
    }

    public void setPrefix(String string, String string2) {
        throw new RuntimeException("NYI");
    }

    public void setProperty(String string, Object object) {
        throw new IllegalArgumentException("unknown property " + string);
    }

    public void startDocument(String string, Boolean bl) throws IOException {
        this.out.write(3);
        this.out.write(1);
        if (string != null) {
            this.encoding = string;
        }
        if (this.encoding.toUpperCase().equals("UTF-8")) {
            this.out.write(106);
        } else if (this.encoding.toUpperCase().equals("ISO-8859-1")) {
            this.out.write(4);
        } else {
            throw new UnsupportedEncodingException(string);
        }
    }

    public XmlSerializer startTag(String string, String string2) throws IOException {
        if (string != null && !"".equals(string)) {
            throw new RuntimeException("NSP NYI");
        }
        this.checkPending(false);
        this.pending = string2;
        ++this.depth;
        return this;
    }

    public XmlSerializer text(char[] cArray, int n, int n2) throws IOException {
        this.checkPending(false);
        this.writeStr(new String(cArray, n, n2));
        return this;
    }

    public XmlSerializer text(String string) throws IOException {
        this.checkPending(false);
        this.writeStr(string);
        return this;
    }

    private void writeStr(String string) throws IOException {
        int n = 0;
        int n2 = 0;
        int n3 = string.length();
        while (n < n3) {
            int n4;
            while (n < n3 && string.charAt(n) < 'A') {
                ++n;
            }
            for (n4 = n; n4 < n3 && string.charAt(n4) >= 'A'; ++n4) {
            }
            if (n4 - n > 10) {
                if (n > n2 && string.charAt(n - 1) == ' ' && this.stringTable.get(string.substring(n, n4)) == null) {
                    this.buf.write(131);
                    this.writeStrT(string.substring(n2, n4), false);
                } else {
                    if (n > n2 && string.charAt(n - 1) == ' ') {
                        --n;
                    }
                    if (n > n2) {
                        this.buf.write(131);
                        this.writeStrT(string.substring(n2, n), false);
                    }
                    this.buf.write(131);
                    this.writeStrT(string.substring(n, n4), true);
                }
                n2 = n4;
            }
            n = n4;
        }
        if (n2 < n3) {
            this.buf.write(131);
            this.writeStrT(string.substring(n2, n3), false);
        }
    }

    public XmlSerializer endTag(String string, String string2) throws IOException {
        if (this.pending != null) {
            this.checkPending(true);
        } else {
            this.buf.write(1);
        }
        --this.depth;
        return this;
    }

    public void writeWapExtension(int n, Object object) throws IOException {
        this.checkPending(false);
        this.buf.write(n);
        switch (n) {
            case 192: 
            case 193: 
            case 194: {
                break;
            }
            case 195: {
                byte[] byArray = (byte[])object;
                WbxmlSerializer.writeInt(this.buf, byArray.length);
                this.buf.write(byArray);
                break;
            }
            case 64: 
            case 65: 
            case 66: {
                this.writeStrI(this.buf, (String)object);
                break;
            }
            case 128: 
            case 129: 
            case 130: {
                this.writeStrT((String)object, false);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    static void writeInt(OutputStream outputStream2, int n) throws IOException {
        byte[] byArray = new byte[5];
        int n2 = 0;
        do {
            byArray[n2++] = (byte)(n & 0x7F);
        } while ((n >>= 7) != 0);
        while (n2 > 1) {
            outputStream2.write(byArray[--n2] | 0x80);
        }
        outputStream2.write(byArray[0]);
    }

    void writeStrI(OutputStream outputStream2, String string) throws IOException {
        byte[] byArray = string.getBytes(this.encoding);
        outputStream2.write(byArray);
        outputStream2.write(0);
    }

    private final void writeStrT(String string, boolean bl) throws IOException {
        Integer n = (Integer)this.stringTable.get(string);
        if (n != null) {
            WbxmlSerializer.writeInt(this.buf, n);
        } else {
            int n2;
            int n3 = this.stringTableBuf.size();
            if (string.charAt(0) >= '0' && bl) {
                string = ' ' + string;
                WbxmlSerializer.writeInt(this.buf, n3 + 1);
            } else {
                WbxmlSerializer.writeInt(this.buf, n3);
            }
            this.stringTable.put(string, new Integer(n3));
            if (string.charAt(0) == ' ') {
                this.stringTable.put(string.substring(1), new Integer(n3 + 1));
            }
            if ((n2 = string.lastIndexOf(32)) > 1) {
                this.stringTable.put(string.substring(n2), new Integer(n3 + n2));
                this.stringTable.put(string.substring(n2 + 1), new Integer(n3 + n2 + 1));
            }
            this.writeStrI(this.stringTableBuf, string);
            this.stringTableBuf.flush();
        }
    }

    public void setTagTable(int n, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            if (stringArray[i] == null) continue;
            int[] nArray = new int[]{n, i + 5};
            this.tagTable.put(stringArray[i], nArray);
        }
    }

    public void setAttrStartTable(int n, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            if (stringArray[i] == null) continue;
            int[] nArray = new int[]{n, i + 5};
            this.attrStartTable.put(stringArray[i], nArray);
        }
    }

    public void setAttrValueTable(int n, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            if (stringArray[i] == null) continue;
            int[] nArray = new int[]{n, i + 133};
            this.attrValueTable.put(stringArray[i], nArray);
        }
    }
}

