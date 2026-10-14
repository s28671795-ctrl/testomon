/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.nio.charset.Charset;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import org.h2.util.Bits;

public class CharsetCollator
extends Collator {
    static final Comparator<byte[]> COMPARATOR = Bits::compareNotNullSigned;
    private final Charset charset;

    public CharsetCollator(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return this.charset;
    }

    @Override
    public int compare(String string, String string2) {
        return COMPARATOR.compare(this.toBytes(string), this.toBytes(string2));
    }

    byte[] toBytes(String string) {
        if (this.getStrength() <= 1) {
            string = string.toUpperCase(Locale.ROOT);
        }
        return string.getBytes(this.charset);
    }

    @Override
    public CollationKey getCollationKey(String string) {
        return new CharsetCollationKey(string);
    }

    @Override
    public int hashCode() {
        return 255;
    }

    private class CharsetCollationKey
    extends CollationKey {
        private final byte[] bytes;

        CharsetCollationKey(String string) {
            super(string);
            this.bytes = CharsetCollator.this.toBytes(string);
        }

        @Override
        public int compareTo(CollationKey collationKey) {
            return COMPARATOR.compare(this.bytes, collationKey.toByteArray());
        }

        @Override
        public byte[] toByteArray() {
            return this.bytes;
        }
    }
}

