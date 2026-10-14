/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.json;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.h2.util.json.JSONByteArrayTarget;
import org.h2.util.json.JSONStringSource;
import org.h2.util.json.JSONTarget;
import org.h2.util.json.JSONTextSource;

public final class JSONBytesSource
extends JSONTextSource {
    private final byte[] bytes;
    private final int length;
    private int index;

    public static <R> R parse(byte[] byArray, JSONTarget<R> jSONTarget) {
        Charset charset;
        block18: {
            int n;
            block17: {
                n = byArray.length;
                charset = null;
                if (n < 4) break block17;
                byte by = byArray[0];
                byte by2 = byArray[1];
                byte by3 = byArray[2];
                byte by4 = byArray[3];
                switch (by) {
                    case -2: {
                        if (by2 == -1) {
                            charset = StandardCharsets.UTF_16BE;
                            break;
                        }
                        break block18;
                    }
                    case -1: {
                        if (by2 == -2) {
                            if (by3 == 0 && by4 == 0) {
                                charset = Charset.forName("UTF-32LE");
                                break;
                            }
                            charset = StandardCharsets.UTF_16LE;
                            break;
                        }
                        break block18;
                    }
                    case 0: {
                        if (by2 != 0) {
                            charset = StandardCharsets.UTF_16BE;
                            break;
                        }
                        if (by3 == 0 && by4 != 0 || by3 == -2 && by4 == -1) {
                            charset = Charset.forName("UTF-32BE");
                            break;
                        }
                        break block18;
                    }
                    default: {
                        if (by2 == 0) {
                            if (by3 == 0 && by4 == 0) {
                                charset = Charset.forName("UTF-32LE");
                                break;
                            }
                            charset = StandardCharsets.UTF_16LE;
                            break;
                        }
                        break block18;
                    }
                }
                break block18;
            }
            if (n >= 2) {
                byte by = byArray[0];
                byte by5 = byArray[1];
                if (by != 0) {
                    if (by5 == 0) {
                        charset = StandardCharsets.UTF_16LE;
                    }
                } else if (by5 != 0) {
                    charset = StandardCharsets.UTF_16BE;
                }
            }
        }
        (charset == null ? new JSONBytesSource(byArray, jSONTarget) : new JSONStringSource(new String(byArray, charset), jSONTarget)).parse();
        return jSONTarget.getResult();
    }

    public static byte[] normalize(byte[] byArray) {
        return JSONBytesSource.parse(byArray, new JSONByteArrayTarget());
    }

    JSONBytesSource(byte[] byArray, JSONTarget<?> jSONTarget) {
        super(jSONTarget);
        this.bytes = byArray;
        this.length = byArray.length;
        if (this.nextChar() != 65279) {
            this.index = 0;
        }
    }

    @Override
    int nextCharAfterWhitespace() {
        int n = this.index;
        block3: while (n < this.length) {
            byte by = this.bytes[n++];
            switch (by) {
                case 9: 
                case 10: 
                case 13: 
                case 32: {
                    continue block3;
                }
            }
            if (by < 0) {
                throw new IllegalArgumentException();
            }
            this.index = n;
            return by;
        }
        return -1;
    }

    @Override
    void readKeyword1(String string) {
        int n = string.length() - 1;
        if (this.index + n > this.length) {
            throw new IllegalArgumentException();
        }
        int n2 = this.index;
        for (int i = 1; i <= n; ++i) {
            if (this.bytes[n2] != string.charAt(i)) {
                throw new IllegalArgumentException();
            }
            ++n2;
        }
        this.index += n;
    }

    @Override
    void parseNumber(boolean bl) {
        int n;
        int n2;
        block6: {
            byte by;
            block7: {
                n2 = this.index;
                n = n2 - 1;
                if ((n2 = this.skipInt(n2, bl)) >= this.length) break block6;
                by = this.bytes[n2];
                if (by != 46) break block7;
                if ((n2 = this.skipInt(n2 + 1, false)) >= this.length) break block6;
                by = this.bytes[n2];
            }
            if (by == 69 || by == 101) {
                if (++n2 >= this.length) {
                    throw new IllegalArgumentException();
                }
                by = this.bytes[n2];
                if (by == 43 || by == 45) {
                    ++n2;
                }
                n2 = this.skipInt(n2, false);
            }
        }
        this.target.valueNumber(new BigDecimal(new String(this.bytes, n, n2 - n, StandardCharsets.ISO_8859_1)));
        this.index = n2;
    }

    private int skipInt(int n, boolean bl) {
        byte by;
        while (n < this.length && (by = this.bytes[n]) >= 48 && by <= 57) {
            bl = true;
            ++n;
        }
        if (!bl) {
            throw new IllegalArgumentException();
        }
        return n;
    }

    @Override
    int nextChar() {
        int n;
        if (this.index >= this.length) {
            throw new IllegalArgumentException();
        }
        if ((n = this.bytes[this.index++] & 0xFF) >= 128) {
            if (n >= 224) {
                if (n >= 240) {
                    int n2;
                    int n3;
                    int n4;
                    if (this.index + 2 >= this.length) {
                        throw new IllegalArgumentException();
                    }
                    if ((n = ((n & 0xF) << 18) + (((n4 = this.bytes[this.index++] & 0xFF) & 0x3F) << 12) + (((n3 = this.bytes[this.index++] & 0xFF) & 0x3F) << 6) + ((n2 = this.bytes[this.index++] & 0xFF) & 0x3F)) < 65536 || n > 0x10FFFF || (n4 & 0xC0) != 128 || (n3 & 0xC0) != 128 || (n2 & 0xC0) != 128) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    int n5;
                    int n6;
                    if (this.index + 1 >= this.length) {
                        throw new IllegalArgumentException();
                    }
                    if ((n = ((n & 0xF) << 12) + (((n6 = this.bytes[this.index++] & 0xFF) & 0x3F) << 6) + ((n5 = this.bytes[this.index++] & 0xFF) & 0x3F)) < 2048 || (n6 & 0xC0) != 128 || (n5 & 0xC0) != 128) {
                        throw new IllegalArgumentException();
                    }
                }
            } else {
                int n7;
                if (this.index >= this.length) {
                    throw new IllegalArgumentException();
                }
                if ((n = ((n & 0x1F) << 6) + ((n7 = this.bytes[this.index++] & 0xFF) & 0x3F)) < 128 || (n7 & 0xC0) != 128) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return n;
    }

    @Override
    char readHex() {
        int n;
        if (this.index + 3 >= this.length) {
            throw new IllegalArgumentException();
        }
        try {
            n = Integer.parseInt(new String(this.bytes, this.index, 4, StandardCharsets.ISO_8859_1), 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException();
        }
        this.index += 4;
        return (char)n;
    }

    public String toString() {
        return new String(this.bytes, 0, this.index, StandardCharsets.UTF_8) + "[*]" + new String(this.bytes, this.index, this.length, StandardCharsets.UTF_8);
    }
}

